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

import android.hardware.camera2.CameraCharacteristics
import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import dk.itu.moapd.opencv.R
import dk.itu.moapd.opencv.databinding.FragmentMainBinding
import dk.itu.moapd.opencv.media.capture.PhotoCaptureManager
import dk.itu.moapd.opencv.camera.CameraController
import dk.itu.moapd.opencv.camera.OpenCvImageProcessor
import dk.itu.moapd.opencv.permissions.CameraPermissionHelper
import dk.itu.moapd.opencv.ui.utils.viewBinding
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.CvType.CV_8UC4
import org.opencv.core.Mat
import androidx.core.graphics.createBitmap

/**
 * A fragment to display the main screen of the app.
 */
class MainFragment : Fragment(R.layout.fragment_main), CameraBridgeViewBase.CvCameraViewListener2 {
    /**
     * A set of static attributes used in this class.
     */
    companion object {
        /**
         * Tag for logging.
         */
        private val TAG = MainFragment::class.qualifiedName

        /**
         * Argument key for passing image data between fragments.
         */
        const val ARG_IMAGE: String = "ARG_IMAGE"
    }

    /**
     * View binding is a feature that allows you to more easily write code that interacts with
     * views. Once view binding is enabled in a module, it generates a binding class for each XML
     * layout file present in that module. An instance of a binding class contains direct references
     * to all views that have an ID in the corresponding layout.
     */
    private val binding by viewBinding(FragmentMainBinding::bind)

    /**
     * A view model sensitive to changes in the `MainActivity` UI components.
     */
    private val viewModel: MainViewModel by activityViewModels()

    /**
     * The current selected camera lens facing (front/back).
     */
    private var cameraLensFacing: Int = CameraCharacteristics.LENS_FACING_BACK

    /**
     * The latest frame in OpenCV format (RGBA).
     */
    private lateinit var captureMat: Mat

    /**
     * Small local cache of the last saved image Uri (mirrors ViewModel).
     */
    private var imageUriLocal: Uri? = null

    /**
     * This object launches a new permission dialog and receives back the user's permission.
     */
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) startCamera() else requireActivity().finish()
    }

    /**
     * Called immediately after `onCreateView(LayoutInflater, ViewGroup, Bundle)` has returned, but
     * before any saved state has been restored in to the view.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe imageUri from the ViewModel so the fragment UI reflects the latest saved photo.
        viewModel.imageUri.observe(viewLifecycleOwner) { uri ->
            imageUriLocal = uri
        }

        // Request camera permissions.
        if (CameraPermissionHelper.hasCameraPermission(requireContext())) {
            startCamera()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        // The current selected camera. When the selector LiveData changes we update our
        // local value and restart the OpenCV camera with the new lensFacing. This guarantees
        // we always use the freshly selected value and avoids races caused by updating the
        // LiveData and immediately calling startCamera() before the observer runs.
        viewModel.selector.observe(viewLifecycleOwner) {
            cameraLensFacing = it ?: CameraCharacteristics.LENS_FACING_BACK
        }

        // Define the UI behavior.
        binding.apply {

            // Set up the listener for take photo button.
            buttonImageCapture.setOnClickListener {
                takePhoto()
            }

            // Listener for button used to switch cameras.
            buttonCameraSwitch.apply {

                // Disabled until we know whether switching is possible.
                isEnabled = true

                setOnClickListener {

                    // Stop the current video streaming.
                    CameraController.stopCamera(javaCameraView)

                    viewModel.onCameraSelectorChanged(
                        if (cameraLensFacing == CameraCharacteristics.LENS_FACING_FRONT)
                            CameraCharacteristics.LENS_FACING_BACK
                        else
                            CameraCharacteristics.LENS_FACING_FRONT
                    )

                    // Re-start OpenCV preview to update selected camera.
                    startCamera()
                }
            }

            // Set up the listener for the photo view button.
            buttonImageViewer.setOnClickListener {
                imageUriLocal?.let { uri ->
                    val bundle = bundleOf(ARG_IMAGE to uri.toString())
                    // Navigate to the `ImageFragment` passing the Uri as an argument.
                    requireActivity()
                        .findNavController(R.id.fragment_container_view)
                        .navigate(R.id.action_main_to_image, bundle)
                } ?: showSnackBar("No image captured yet.")
            }

            // Listener for the screen clicks used to change the image analysis method.
            viewModel.methodId.observe(viewLifecycleOwner) { methodId ->
                javaCameraView.setOnClickListener {
                    viewModel.onMethodChanged((methodId + 1) % 4)
                }
            }
        }
    }

    /**
     * Starts the OpenCV camera preview using [CameraController].
     */
    private fun startCamera() {
        CameraController.startCamera(
            context = requireContext(),
            cameraView = binding.javaCameraView,
            lensFacing = cameraLensFacing,
            listener = this,
            onCanSwitchCamera = { canSwitch ->
                binding.buttonCameraSwitch.isEnabled = canSwitch
            },
            onError = { message ->
                showSnackBar(message)
            },
        )
    }

    /**
     * Saves the latest frame into MediaStore (DCIM) using [PhotoCaptureManager].
     */
    private fun takePhoto() {
        if (!::captureMat.isInitialized) {
            showSnackBar("No frame available yet.")
            return
        }

        val matToSave: Mat = if (cameraLensFacing == CameraCharacteristics.LENS_FACING_BACK) {
            val tmp = Mat()
            Core.flip(captureMat, tmp, 1)
            tmp
        } else {
            captureMat
        }

        val bitmap = createBitmap(matToSave.cols(), matToSave.rows())
        Utils.matToBitmap(matToSave, bitmap)

        PhotoCaptureManager.saveJpeg(
            contentResolver = requireContext().contentResolver,
            bitmap = bitmap,
            onSaved = { uri, filename ->
                viewModel.onImageUriChanged(uri)
                showSnackBar("Photo saved: $filename")
            },
            onError = { message, _ ->
                showSnackBar("Photo save failed: $message")
            },
        )

        // Release temporary Mat if we created one
        if (matToSave !== captureMat) {
            matToSave.release()
        }
    }

    /**
     * Displays a SnackBar to show a brief message.
     */
    private fun showSnackBar(message: String) {
        // Use the fragment's view when it's available; otherwise fall back to the
        // activity's content view so we avoid touching the view binding during
        // onDestroyView/onDestroy where getView() may be null.
        val anchor = view ?: activity?.findViewById(android.R.id.content)
        anchor?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    /**
     * Called when the fragment is visible to the user and actively running. This is generally tied
     * to `Activity.onResume()` of the containing Activity's lifecycle.
     */
    override fun onResume() {
        super.onResume()

        // Ensure OpenCV is available when resuming. If not, log and inform the user.
        if (OpenCVLoader.initLocal()) {
            Log.i(TAG, "OpenCV loaded successfully (onResume)")
        } else {
            Log.w(TAG, "OpenCV not loaded onResume")
        }

        // When returning from background the CameraView may have been stopped in onPause().
        // Restart the OpenCV camera if we have camera permission and the fragment's view exists.
        // This ensures the preview resumes correctly when the app returns to foreground.
        if (isAdded && view != null && CameraPermissionHelper.hasCameraPermission(requireContext())) {
            startCamera()
        }
    }

    /**
     * Called when the Fragment is no longer resumed. This is generally tied to `Activity.onPause()`
     * of the containing Activity's lifecycle.
     */
    override fun onPause() {
        super.onPause()

        // Avoid accessing view binding during teardown. Use the fragment view to safely
        // retrieve the camera view instance if it exists.
        val cameraView = view?.findViewById<CameraBridgeViewBase>(R.id.java_camera_view)
        cameraView?.let { CameraController.stopCamera(it) }
    }

    /**
     * This method is invoked when camera preview has started. After this method is invoked the
     * frames will start to be delivered to client via the `onCameraFrame()` callback.
     *
     * @param width The width of the frames that will be delivered.
     * @param height The height of the frames that will be delivered
     */
    override fun onCameraViewStarted(width: Int, height: Int) {
        captureMat = Mat(height, width, CV_8UC4)
    }

    /**
     * This method is invoked when camera preview has been stopped for some reason. No frames will
     * be delivered via `onCameraFrame()` callback after this method is called.
     */
    override fun onCameraViewStopped() {
        if (::captureMat.isInitialized) {
            captureMat.release()
        }
    }

    /**
     * This method is invoked when delivery of the frame needs to be done. The returned values - is
     * a modified frame which needs to be displayed on the screen.
     *
     * @param inputFrame The current frame grabbed from the video camera device stream.
     */
    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        // Get the current frame (RGBA).
        val image = inputFrame?.rgba() ?: return Mat()

        // Flip the image if the rear camera is used.
        if (cameraLensFacing == CameraCharacteristics.LENS_FACING_BACK)
            Core.flip(image, image, 1)

        // Keep a copy of the latest frame for capture.
        if (::captureMat.isInitialized) {
            image.copyTo(captureMat)
        }

        // Apply the selected image processing method.
        val processed = when (viewModel.methodId.value ?: 0) {
            1 -> OpenCvImageProcessor.toGrayscale(image)
            2 -> OpenCvImageProcessor.toBgra(image)
            3 -> OpenCvImageProcessor.toCanny(image)
            else -> image
        }

        return processed
    }
}