/*
 * MIT License
 *
 * Copyright (c) 2026 Elizabete Munzlinger and Fabricio Batista Narcizo
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
package dk.itu.moapd.infersnpe.ui.main

import android.content.Context
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dk.itu.moapd.infersnpe.R
import dk.itu.moapd.infersnpe.feature.detector.infra.camera.model.DetectorCameraStartRequest
import dk.itu.moapd.infersnpe.feature.detector.overlay.paints.OverlayPaintFactory
import dk.itu.moapd.infersnpe.ui.main.viewmodel.DetectorViewModel
import androidx.compose.ui.res.stringResource

/**
 * A composable function that represents the main screen of the application.
 *
 * @param detectorViewModel The view model that manages the state of the detector.
 * @param hasCameraPermission A boolean value indicating whether the camera permission is granted.
 */
@Composable
fun MainScreen(
    detectorViewModel: DetectorViewModel,
    hasCameraPermission: Boolean,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by detectorViewModel.uiState.collectAsStateWithLifecycle()

    var confidenceThreshold by remember { mutableFloatStateOf(0.5f) }
    var cameraProvider by remember { mutableStateOf<ProcessCameraProvider?>(null) }
    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    val previewView = remember(context) { createPreviewView(context) }
    val resultsOverlayView = remember(context) { ResultsOverlayView(context) }

    ConfigureOverlayStyle(resultsOverlayView)

    LaunchedEffect(context) {
        val future = ProcessCameraProvider.getInstance(context)
        future.addListener(
            { cameraProvider = future.get() },
            ContextCompat.getMainExecutor(context),
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            detectorViewModel.stopCamera()
        }
    }

    LaunchedEffect(confidenceThreshold) {
        detectorViewModel.updateObjectThreshold(confidenceThreshold)
    }

    LaunchedEffect(
        hasCameraPermission,
        cameraProvider,
        uiState.isModelLoaded,
        uiState.errorMessage,
        lifecycleOwner,
    ) {
        detectorViewModel.stopCamera()

        val provider = cameraProvider ?: return@LaunchedEffect
        if (!hasCameraPermission || !uiState.isModelLoaded || uiState.errorMessage != null) return@LaunchedEffect

        detectorViewModel.startCamera(
            DetectorCameraStartRequest(
                lifecycleOwner = lifecycleOwner,
                cameraProvider = provider,
                previewView = previewView,
                cameraSelector = cameraSelector,
                onResults = { results -> resultsOverlayView.update(results) },
            ),
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize(),
        )

        AndroidView(
            factory = { resultsOverlayView },
            modifier = Modifier.fillMaxSize(),
        )

        Slider(
            value = confidenceThreshold,
            onValueChange = { confidenceThreshold = it },
            valueRange = 0f..1f,
            steps = 99,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(top = 64.dp, start = 16.dp, end = 16.dp),
        )

        Surface(
            color = MaterialTheme.colorScheme.surfaceContainerHighest.copy(alpha = 0.95f),
            shape = MaterialTheme.shapes.small,
            tonalElevation = 2.dp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 120.dp),
        ) {
            Text(
                text = stringResource(R.string.fps_text, uiState.fps ?: 0.0),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            )
        }
    }
}

/**
 * Creates a new [PreviewView] for displaying the camera preview.
 *
 * @param context The context used to create the [PreviewView].
 *
 * @return A new [PreviewView] instance.
 */
private fun createPreviewView(context: Context): PreviewView =
    PreviewView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
        )
        scaleType = PreviewView.ScaleType.FILL_START
    }

/**
 * Configures the style of the ResultsOverlayView based on the current Material theme.
 *
 * @param resultsOverlayView The ResultsOverlayView to configure.
 */
@Composable
private fun ConfigureOverlayStyle(resultsOverlayView: ResultsOverlayView) {
    val primaryColor = MaterialTheme.colorScheme.primary.toArgb()
    val primaryTextColor = MaterialTheme.colorScheme.onPrimary.toArgb()
    val density = LocalDensity.current
    val primaryTextSizePx =
        with(density) {
            MaterialTheme.typography.titleMedium.fontSize
                .toPx()
        }

    val overlayPaints =
        remember(primaryColor, primaryTextColor, primaryTextSizePx) {
            OverlayPaintFactory(
                primaryColor = primaryColor,
                labelTextColor = primaryTextColor,
                labelTextSizePx = primaryTextSizePx,
            ).create()
        }

    SideEffect {
        resultsOverlayView.setOverlayPaints(overlayPaints)
    }
}
