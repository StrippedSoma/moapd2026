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
package dk.itu.moapd.opencv.ui.main

import android.Manifest
import android.hardware.camera2.CameraCharacteristics
import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.SwitchCamera
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import dk.itu.moapd.opencv.R
import dk.itu.moapd.opencv.camera.CameraController
import dk.itu.moapd.opencv.media.capture.PhotoCaptureManager
import dk.itu.moapd.opencv.permissions.CameraPermissionHelper
import kotlinx.coroutines.launch
import org.opencv.android.JavaCamera2View
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import androidx.core.graphics.createBitmap
import dk.itu.moapd.opencv.ui.session.OpenCvSession
import dk.itu.moapd.opencv.ui.widgets.OutlinedIconCircleButton

/**
 * The main screen component.
 *
 * @param viewModel A view model sensitive to changes in the `MainActivity` UI components.
 * @param onOpenLastPhoto Callback to open the image viewer.
 * @param onFinish Callback to finish the activity.
 */
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onOpenLastPhoto: (String) -> Unit,
    onFinish: () -> Unit,
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var cameraLensFacing by remember { mutableIntStateOf(CameraCharacteristics.LENS_FACING_BACK) }
    var lastImageUri by remember { mutableStateOf<Uri?>(null) }
    var methodId by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        OpenCVLoader.initLocal()
    }

    DisposableEffect(Unit) {
        val selectorObs = androidx.lifecycle.Observer<Int> { v ->
            cameraLensFacing = v
        }
        val uriObs = androidx.lifecycle.Observer<Uri?> { v -> lastImageUri = v }
        val methodObs = androidx.lifecycle.Observer<Int> { v -> methodId = v }

        viewModel.selector.observeForever(selectorObs)
        viewModel.imageUri.observeForever(uriObs)
        viewModel.methodId.observeForever(methodObs)

        onDispose {
            viewModel.selector.removeObserver(selectorObs)
            viewModel.imageUri.removeObserver(uriObs)
            viewModel.methodId.removeObserver(methodObs)
        }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) onFinish()
    }

    LaunchedEffect(Unit) {
        if (!CameraPermissionHelper.hasCameraPermission(context)) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    val session = remember { OpenCvSession() }
    // Keep session in sync with UI state.
    LaunchedEffect(cameraLensFacing, methodId) {
        session.lensFacing = cameraLensFacing
        session.methodId = methodId
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val density = LocalDensity.current
            val wPx = with(density) { maxWidth.toPx().toInt() }
            val hPx = with(density) { maxHeight.toPx().toInt() }

            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    JavaCamera2View(ctx, cameraLensFacing).apply {
                        layoutParams = FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )

                        setMaxFrameSize(wPx, hPx)

                        session.cameraView = this
                        visibility = android.view.SurfaceView.VISIBLE
                        setCvCameraViewListener(session)

                        setOnClickListener {
                            viewModel.onMethodChanged((methodId + 1) % 4)
                        }
                    }
                },
                update = { view ->

                    view.layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    view.setMaxFrameSize(wPx, hPx)

                    session.cameraView = view
                    if (CameraPermissionHelper.hasCameraPermission(context)) {
                        CameraController.startCamera(
                            context = context,
                            cameraView = view,
                            lensFacing = cameraLensFacing,
                            listener = session,
                            onCanSwitchCamera = { },
                            onError = { msg -> scope.launch { snackbarHostState.showSnackbar(msg) } },
                        )
                    }
                }
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        val bottomMargin = dimensionResource(id = R.dimen.margin_xlarge)
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = bottomMargin)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedIconCircleButton(
                enabled = true,
                imageVector = Icons.Filled.SwitchCamera,
                contentDescription = stringResource(R.string.button_camera_switch_description),
                iconSize = 24.dp,
                strokeWidth = 2.dp,
                onClick = {
                    session.cameraView?.let { CameraController.stopCamera(it) }
                    viewModel.onCameraSelectorChanged(
                        if (cameraLensFacing == CameraCharacteristics.LENS_FACING_FRONT)
                            CameraCharacteristics.LENS_FACING_BACK
                        else
                            CameraCharacteristics.LENS_FACING_FRONT
                    )
                },
            )

            Spacer(modifier = Modifier.width(12.dp))

            OutlinedIconCircleButton(
                enabled = true,
                imageVector = Icons.Filled.Circle,
                contentDescription = stringResource(R.string.button_image_capture_description),
                iconSize = 80.dp,
                strokeWidth = 2.dp,
                onClick = {
                    val mat = session.latestFrame
                    if (mat == null) {
                        scope.launch { snackbarHostState.showSnackbar("No frame available yet.") }
                        return@OutlinedIconCircleButton
                    }

                    // Keep the same save behavior as the Fragment.
                    val matToSave: Mat = if (cameraLensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                        val tmp = Mat()
                        Core.flip(mat, tmp, 1)
                        tmp
                    } else {
                        mat
                    }

                    val bitmap = createBitmap(matToSave.cols(), matToSave.rows())
                    Utils.matToBitmap(matToSave, bitmap)

                    PhotoCaptureManager.saveJpeg(
                        contentResolver = context.contentResolver,
                        bitmap = bitmap,
                        onSaved = { uri, filename ->
                            viewModel.onImageUriChanged(uri)
                            scope.launch { snackbarHostState.showSnackbar("Photo saved: $filename") }
                        },
                        onError = { message, _ ->
                            scope.launch { snackbarHostState.showSnackbar("Photo save failed: $message") }
                        },
                    )

                    if (matToSave !== mat) matToSave.release()
                },
            )

            Spacer(modifier = Modifier.width(12.dp))

            OutlinedIconCircleButton(
                enabled = true,
                imageVector = Icons.Filled.Photo,
                contentDescription = stringResource(R.string.button_image_viewer_description),
                iconSize = 24.dp,
                strokeWidth = 2.dp,
                onClick = {
                    val uri = lastImageUri
                    if (uri != null) {
                        onOpenLastPhoto(uri.toString())
                    } else {
                        scope.launch { snackbarHostState.showSnackbar("No image captured yet.") }
                    }
                },
            )
        }
    }
}
