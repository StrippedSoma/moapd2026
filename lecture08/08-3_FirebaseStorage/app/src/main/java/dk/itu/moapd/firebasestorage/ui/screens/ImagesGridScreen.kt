/*
 * MIT License
 *
 * Copyright (c) 2026 Fabricio Batista Narcizo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package dk.itu.moapd.firebasestorage.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.storage
import dk.itu.moapd.firebasestorage.R
import dk.itu.moapd.firebasestorage.core.FirebaseConfig.BUCKET_URL
import dk.itu.moapd.firebasestorage.data.repository.ImageRepository
import dk.itu.moapd.firebasestorage.domain.model.Image
import kotlinx.coroutines.launch

/**
 * A data class that represents an image with its key.
 *
 * @property key The key of the image.
 * @property image The image object.
 */
private data class UiImage(
    val key: String,
    val image: Image,
)

/**
 * A data class that represents the state of the images.
 *
 * @property isLoading A boolean value indicating whether the images are being loaded.
 * @property images A list of images.
 */
private data class ImagesUiState(
    val isLoading: Boolean = true,
    val images: List<UiImage> = emptyList(),
)

/**
 * A composable function that displays a grid of images.
 *
 * @param contentPadding The padding values to be applied to the content.
 * @param onOpenImage A function to be called when an image is clicked.
 * @param imageRepository The image repository to be used.
 * @param auth The authentication instance to be used.
 * @param snackbarHostState The snackbar host state to be used.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagesGridScreen(
    contentPadding: PaddingValues,
    onOpenImage: (path: String) -> Unit,
    imageRepository: ImageRepository = remember { ImageRepository() },
    auth: FirebaseAuth = remember { FirebaseAuth.getInstance() },
    snackbarHostState: SnackbarHostState,
) {
    val scope = rememberCoroutineScope()

    val configuration = LocalConfiguration.current
    val columns = remember(configuration.orientation) {
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 2
    }

    var uiState by remember { mutableStateOf(ImagesUiState()) }
    var deleteTarget by rememberSaveable { mutableStateOf<String?>(null) } // guarda só a key
    var isDeleting by rememberSaveable { mutableStateOf(false) }

    val messageDeleted = stringResource(R.string.message_image_deleted)
    val messageErrorDelete = stringResource(R.string.error_delete_image)

    // Observe user's images from Firebase Realtime Database.
    DisposableEffect(auth.currentUser?.uid) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            uiState = ImagesUiState(isLoading = false, images = emptyList())
            return@DisposableEffect onDispose { }
        }

        val query = imageRepository.imagesQuery(uid)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = snapshot.children.mapNotNull { child ->
                    val img = child.getValue(Image::class.java) ?: return@mapNotNull null
                    val key = child.key ?: return@mapNotNull null
                    UiImage(key = key, image = img)
                }
                uiState = ImagesUiState(isLoading = false, images = list)
            }

            override fun onCancelled(error: DatabaseError) {
                uiState = ImagesUiState(isLoading = false, images = emptyList())
            }
        }

        query.addValueEventListener(listener)
        onDispose { query.removeEventListener(listener) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            ImagesGridContent(
                columns = columns,
                images = uiState.images,
                onOpenImage = onOpenImage,
                onLongPress = { key -> deleteTarget = key },
            )
        }

        val target = deleteTarget?.let { key -> uiState.images.firstOrNull { it.key == key } }
        if (target != null) {
            DeleteImageDialog(
                isDeleting = isDeleting,
                onConfirm = {
                    val path = target.image.path ?: return@DeleteImageDialog
                    isDeleting = true

                    val deleteTask = imageRepository.deleteImage(target.key, path)
                    if (deleteTask == null) {
                        isDeleting = false
                        scope.launch { snackbarHostState.showSnackbar(messageErrorDelete) }
                    } else {
                        deleteTask
                            .addOnSuccessListener {
                                isDeleting = false
                                deleteTarget = null
                                scope.launch { snackbarHostState.showSnackbar(messageDeleted) }
                            }
                            .addOnFailureListener {
                                isDeleting = false
                                deleteTarget = null
                                scope.launch { snackbarHostState.showSnackbar(messageErrorDelete) }
                            }
                    }
                },
                onCancel = { deleteTarget = null },
            )
        }
    }
}

/**
 * A composable function that displays a grid of images.
 *
 * @param columns The number of columns to be displayed.
 * @param images The list of images to be displayed.
 * @param onOpenImage A function to be called when an image is clicked.
 * @param onLongPress A function to be called when an image is long pressed.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ImagesGridContent(
    columns: Int,
    images: List<UiImage>,
    onOpenImage: (path: String) -> Unit,
    onLongPress: (key: String) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(2.dp),
    ) {
        items(
            items = images,
            key = { it.key },
        ) { item ->
            val path = item.image.path ?: return@items

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .aspectRatio(1f)
                    .combinedClickable(
                        onClick = { onOpenImage(path) },
                        onLongClick = { onLongPress(item.key) },
                    ),
                contentAlignment = Alignment.Center,
            ) {
                FirebaseStorageImage(
                    path = path,
                    contentDescription = stringResource(R.string.content_description_image),
                )
            }
        }
    }
}

/**
 * A composable function that displays a dialog to confirm the deletion of an image.
 *
 * @param isDeleting A boolean value indicating whether the image is being deleted.
 * @param onConfirm A function to be called when the user confirms the deletion.
 * @param onCancel A function to be called when the user cancels the deletion.
 */
@Composable
private fun DeleteImageDialog(
    isDeleting: Boolean,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { /* Don't close the dialog */ },
        title = { Text(text = stringResource(R.string.dialog_delete_title)) },
        text = { Text(text = stringResource(R.string.dialog_delete_message)) },
        confirmButton = {
            TextButton(
                enabled = !isDeleting,
                onClick = onConfirm,
            ) {
                Text(text = stringResource(R.string.button_delete))
            }
        },
        dismissButton = {
            TextButton(
                enabled = !isDeleting,
                onClick = onCancel,
            ) {
                Text(text = stringResource(R.string.button_cancel))
            }
        },
    )
}

/**
 * A composable function that displays a Firebase Storage image.
 *
 * @param path The path of the image to be displayed.
 * @param contentDescription The content description of the image.
 * @param modifier The modifier to be applied to the image.
 */
private val downloadUrlCache = mutableMapOf<String, String>()

@Composable
private fun FirebaseStorageImage(
    path: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    var url by remember(path) { mutableStateOf<String?>(downloadUrlCache[path]) }

    LaunchedEffect(path) {
        // If we already have a cached URL for this path, reuse it and skip the network call.
        val cached = downloadUrlCache[path]
        if (cached != null) {
            url = cached
            return@LaunchedEffect
        }

        val ref = Firebase.storage(BUCKET_URL).reference.child(path)
        ref.downloadUrl
            .addOnSuccessListener { downloadUrl ->
                val resolvedUrl = downloadUrl.toString()
                downloadUrlCache[path] = resolvedUrl
                url = resolvedUrl
            }
            .addOnFailureListener {
                url = null
            }
    }

    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        modifier = modifier.fillMaxSize(),
    )
}
