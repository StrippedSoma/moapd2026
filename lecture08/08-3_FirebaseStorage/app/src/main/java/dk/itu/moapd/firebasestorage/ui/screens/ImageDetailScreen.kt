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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import dk.itu.moapd.firebasestorage.R
import dk.itu.moapd.firebasestorage.core.FirebaseConfig.BUCKET_URL

/**
 * A composable function that displays the details of an image.
 *
 * @param contentPadding The padding values to be applied to the content.
 * @param path The path of the image to be displayed.
 * @param onBack A function to be called when the back button is clicked.
 */
@Composable
fun ImageDetailScreen(
    contentPadding: PaddingValues,
    path: String,
    onBack: () -> Unit,
) {
    var url by remember(path) { mutableStateOf<String?>(null) }

    DisposableEffect(path) {
        val ref = Firebase.storage(BUCKET_URL).reference.child(path)
        ref.downloadUrl
            .addOnSuccessListener { downloadUrl -> url = downloadUrl.toString() }
            .addOnFailureListener { url = null }
        onDispose { }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
    ) {
        if (url == null) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            AsyncImage(
                model = url,
                contentDescription = stringResource(id = R.string.content_description_image),
                modifier = Modifier.fillMaxSize(),
            )
        }

        Box(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.TopStart)
                .size(32.dp)
                .clickable(onClick = onBack),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.content_description_back),
            )
        }
    }
}
