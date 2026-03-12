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
package dk.itu.moapd.infertflite.ui.main

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.color.MaterialColors
import dagger.hilt.android.AndroidEntryPoint
import dk.itu.moapd.infertflite.R
import dk.itu.moapd.infertflite.databinding.FragmentMainBinding
import dk.itu.moapd.infertflite.feature.detector.infra.camera.model.DetectorCameraStartRequest
import dk.itu.moapd.infertflite.feature.detector.overlay.paints.OverlayPaintFactory
import dk.itu.moapd.infertflite.feature.detector.presentation.ui.viewmodel.DetectorViewModel
import dk.itu.moapd.infertflite.ui.utils.viewBinding
import kotlinx.coroutines.launch

/**
 * A fragment to display the main screen of the app.
 */
@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    /**
     * View binding is a feature that allows you to more easily write code that interacts with
     * views. Once view binding is enabled in a module, it generates a binding class for each XML
     * layout file present in that module. An instance of a binding class contains direct references
     * to all views that have an ID in the corresponding layout.
     */
    private val binding by viewBinding(FragmentMainBinding::bind)

    /**
     * ViewModel for managing service state that survives configuration changes.
     */
    private val detectorViewModel: DetectorViewModel by viewModels()

    /**
     * Camera selector to switch between front and back cameras.
     */
    private var cameraProvider: ProcessCameraProvider? = null

    /**
     * The camera selector allows to select a camera or return a filtered set of cameras.
     */
    private var currentCameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    /**
     * Indicates if the camera is currently started.
     */
    private var isCameraStarted: Boolean = false

    /**
     * Called immediately after `onCreateView(LayoutInflater, ViewGroup, Bundle)` has returned, but
     * before any saved state has been restored in to the view. This gives subclasses a chance to
     * initialize themselves once they know their view hierarchy has been completely created. The
     * fragment's view hierarchy is not however attached to its parent at this point.
     *
     * @param view The View returned by `onCreateView(LayoutInflater, ViewGroup, Bundle)`.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *      saved state as given here.
     */
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        configurePreview()
        configureOverlay()
        setupUi()
        observeUiState()
        setupCameraProvider()
    }

    /**
     * Configures the camera preview.
     */
    private fun configurePreview() {
        binding.previewView.scaleType = androidx.camera.view.PreviewView.ScaleType.FILL_START
    }

    /**
     * Configures the Overlay view.
     */
    private fun configureOverlay() {
        val primaryColor =
            MaterialColors.getColor(
                requireContext(),
                com.google.android.material.R.attr.colorPrimaryFixed,
                Color.RED,
            )

        val labelTextColor =
            MaterialColors.getColor(
                requireContext(),
                com.google.android.material.R.attr.colorOnPrimaryFixed,
                Color.YELLOW,
            )

        val typedValue = TypedValue()
        val theme = requireContext().theme

        val success =
            theme.resolveAttribute(
                com.google.android.material.R.attr.textAppearanceTitleMediumEmphasized,
                typedValue,
                true,
            )

        val labelTextSizePx =
            if (success) {
                val attributes = intArrayOf(android.R.attr.textSize)
                val typedArray = requireContext().obtainStyledAttributes(typedValue.data, attributes)
                val fontSize = typedArray.getDimension(0, -1f)
                typedArray.recycle()
                fontSize
            } else {
                45f
            }

        val overlayPaints =
            OverlayPaintFactory(
                primaryColor = primaryColor,
                labelTextColor = labelTextColor,
                labelTextSizePx = labelTextSizePx,
            ).create()

        binding.resultsOverlayView.setOverlayPaints(overlayPaints)
    }

    /**
     * Setups the UI components.
     */
    private fun setupUi() {
        binding.apply {
            fpsText.text = getString(R.string.fps_text, 0.0)
            confidenceSlider.addOnChangeListener { _, value, _ ->
                detectorViewModel.updateObjectThreshold(value)
            }
        }
    }

    /**
     * Observes the UI state.
     */
    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detectorViewModel.uiState.collect { uiState ->
                    binding.fpsText.text = getString(R.string.fps_text, uiState.fps)
                    when {
                        uiState.errorMessage != null -> {
                            stopCameraIfRunning()
                        }

                        uiState.isModelLoading -> {
                            stopCameraIfRunning()
                        }

                        uiState.isModelLoaded -> {
                            startCameraIfReady()
                        }

                        else -> {
                            stopCameraIfRunning()
                        }
                    }
                }
            }
        }
    }

    /**
     * Sets up the camera provider.
     */
    private fun setupCameraProvider() {
        val future = ProcessCameraProvider.getInstance(requireContext())

        future.addListener(
            {
                cameraProvider = future.get()
                startCameraIfReady()
            },
            ContextCompat.getMainExecutor(requireContext()),
        )
    }

    /**
     * Starts the camera if it is ready.
     */
    private fun startCameraIfReady() {
        val provider = cameraProvider ?: return
        val uiState = detectorViewModel.uiState.value

        if (!uiState.isModelLoaded) return
        if (isCameraStarted) return

        detectorViewModel.startCamera(
            DetectorCameraStartRequest(
                lifecycleOwner = viewLifecycleOwner,
                cameraProvider = provider,
                previewView = binding.previewView,
                cameraSelector = currentCameraSelector,
                onResults = { results ->
                    binding.resultsOverlayView.update(results)
                },
            ),
        )

        isCameraStarted = true
    }

    /**
     * Stops the camera if it is running.
     */
    private fun stopCameraIfRunning() {
        if (!isCameraStarted) return
        detectorViewModel.stopCamera()
        isCameraStarted = false
    }

    /**
     * Called when the view previously created by onCreateView() has been detached from the
     * fragment. The next time the fragment needs to be displayed, a new view will be created. This
     * is called after onStop() and before onDestroy(). It is called <em>regardless</em> of whether
     * onCreateView() returned a non-null view. Internally it is called after the view's state has
     * been saved but before it has been removed from its parent.
     */
    override fun onDestroyView() {
        stopCameraIfRunning()
        super.onDestroyView()
    }
}
