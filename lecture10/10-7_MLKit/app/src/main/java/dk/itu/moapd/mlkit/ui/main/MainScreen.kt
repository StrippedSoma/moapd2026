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
package dk.itu.moapd.mlkit.ui.main

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.google.mlkit.vision.common.InputImage
import dk.itu.moapd.mlkit.R
import dk.itu.moapd.mlkit.domain.vision.ObjectDetectionProcessor
import dk.itu.moapd.mlkit.ui.common.tag
import kotlin.math.min

/**
 * A composable function that represents the main screen of the application.
 */
@Composable
fun MainScreen() {
    val context = LocalContext.current

    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }
    var renderedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var imageViewSize by remember { mutableStateOf(IntSize.Zero) }

    val processor = remember { ObjectDetectionProcessor() }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            handleCameraResult(
                context = context,
                result = result,
                capturedImageUri = capturedImageUri,
                imageViewSize = imageViewSize,
                processor = processor,
                onRendered = { renderedBitmap = it },
            )
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    launchCameraIntent(
                        context = context,
                        onUriCreated = { uri -> capturedImageUri = uri },
                        onLaunch = { intent -> cameraLauncher.launch(intent) },
                    )
                },
                modifier = Modifier.padding(80.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.CameraAlt,
                    contentDescription = stringResource(R.string.button_capture_description)
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val bitmap = renderedBitmap
            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = stringResource(R.string.image_view_description),
                    modifier = Modifier
                        .fillMaxSize()
                        .onSizeChanged { imageViewSize = it },
                    contentScale = ContentScale.Fit
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.mlkit_firebase),
                    contentDescription = stringResource(R.string.image_view_description),
                    modifier = Modifier
                        .fillMaxSize()
                        .onSizeChanged { imageViewSize = it },
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

/**
 * Launches the camera intent to capture a photo.
 *
 * @param context The context of the application.
 * @param onUriCreated The callback to be invoked when the URI is created.
 * @param onLaunch The callback to be invoked when the camera intent is launched.
 */
private fun launchCameraIntent(
    context: Context,
    onUriCreated: (Uri) -> Unit,
    onLaunch: (Intent) -> Unit,
) {
    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    if (cameraIntent.resolveActivity(context.packageManager) == null) return

    val values = ContentValues().apply {
        put(MediaStore.Images.Media.TITLE, "ML Kit")
    }

    val uri = context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        values
    ) ?: return

    onUriCreated(uri)
    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
    onLaunch(cameraIntent)
}

/**
 * Handles the result of the camera intent.
 *
 * @param context The context of the application.
 * @param result The result of the camera intent.
 * @param capturedImageUri The URI of the captured image.
 * @param imageViewSize The size of the image view.
 * @param processor The object detection processor.
 * @param onRendered The callback to be invoked when the image is rendered.
 */
private fun handleCameraResult(
    context: Context,
    result: ActivityResult,
    capturedImageUri: Uri?,
    imageViewSize: IntSize,
    processor: ObjectDetectionProcessor,
    onRendered: (Bitmap) -> Unit,
) {
    if (result.resultCode != android.app.Activity.RESULT_OK) return
    val uri = capturedImageUri ?: return

    val bitmap = getCapturedImageBitmap(context, uri, imageViewSize) ?: return

    processor.process(
        bitmap = bitmap,
        onSuccess = { visualized -> onRendered(visualized) },
        onFailure = { e -> Log.e(context.tag(), e.message.orEmpty(), e) }
    )
}

/**
 * Gets the captured image bitmap.
 *
 * @param context The context of the application.
 * @param uri The URI of the captured image.
 * @param imageViewSize The size of the image view.
 *
 * @return The captured image bitmap.
 */
private fun getCapturedImageBitmap(context: Context, uri: Uri, imageViewSize: IntSize): Bitmap? {
    val bitmap = InputImage.fromFilePath(context, uri).bitmapInternal ?: return null

    val viewWidth = imageViewSize.width.toFloat()
    val viewHeight = imageViewSize.height.toFloat()
    if (viewWidth <= 0f || viewHeight <= 0f) return bitmap

    val imageWidth = bitmap.width.toFloat()
    val imageHeight = bitmap.height.toFloat()

    val scaleFactor = min(imageWidth / viewWidth, imageHeight / viewHeight)
    val scaledWidth = viewWidth * scaleFactor
    val scaledHeight = viewHeight * scaleFactor

    val left = (imageWidth - scaledWidth) / 2
    val top = (imageHeight - scaledHeight) / 2

    return Bitmap.createBitmap(
        bitmap,
        left.toInt().coerceAtLeast(0),
        top.toInt().coerceAtLeast(0),
        scaledWidth.toInt().coerceAtMost(bitmap.width),
        scaledHeight.toInt().coerceAtMost(bitmap.height),
    )
}
