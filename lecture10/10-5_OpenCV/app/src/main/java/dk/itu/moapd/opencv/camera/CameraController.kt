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
package dk.itu.moapd.opencv.camera

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import android.util.Log

/**
 * Controller responsible for configuring and starting the OpenCV camera preview.
 *
 * This project intentionally does NOT use CameraX, but keeps the same file name/package structure
 * as the reference CameraX project to make both projects "drop-in" comparable in teaching material.
 */
object CameraController {

    /**
     * Starts the OpenCV camera view.
     *
     * @param context Used to query available cameras.
     * @param cameraView The OpenCV camera view (e.g., JavaCameraView).
     * @param lensFacing Desired lens facing. Uses [CameraCharacteristics.LENS_FACING_BACK] or
     *      [CameraCharacteristics.LENS_FACING_FRONT].
     * @param listener Frame listener that will receive OpenCV callbacks.
     * @param onCanSwitchCamera Callback informing whether front+back cameras are available.
     * @param onError Callback for error messages.
     */
    fun startCamera(
        context: Context,
        cameraView: CameraBridgeViewBase,
        lensFacing: Int,
        listener: CameraBridgeViewBase.CvCameraViewListener2,
        onCanSwitchCamera: (Boolean) -> Unit,
        onError: (String) -> Unit,
    ) {
        try {
            cameraView.visibility = CameraBridgeViewBase.VISIBLE
            cameraView.setCvCameraViewListener(listener)
            cameraView.setCameraIndex(lensFacing)
            cameraView.setCameraPermissionGranted()
            cameraView.enableView()
            onCanSwitchCamera(canSwitchCamera(context))
        } catch (t: Throwable) {
            onError("Failed to start OpenCV camera: ${t.message ?: t}")
            onCanSwitchCamera(false)
        }
    }

    /**
     * Stops the OpenCV camera view.
     */
    fun stopCamera(cameraView: CameraBridgeViewBase) {
        try {
            cameraView.disableView()
        } catch (_: Throwable) {
            // Ignore
        }
    }

    /**
     * Checks if the Android device has both front and back cameras.
     */
    private fun canSwitchCamera(context: Context): Boolean {
        val manager = context.getSystemService(Context.CAMERA_SERVICE) as? CameraManager ?: return false
        var hasFront = false
        var hasBack = false
        return try {
            for (id in manager.cameraIdList) {
                val facing = manager.getCameraCharacteristics(id).get(CameraCharacteristics.LENS_FACING)
                when (facing) {
                    CameraCharacteristics.LENS_FACING_FRONT -> hasFront = true
                    CameraCharacteristics.LENS_FACING_BACK -> hasBack = true
                }
            }
            hasFront && hasBack
        } catch (_: Throwable) {
            false
        }
    }
}

/**
 * A small collection of OpenCV image-processing helpers used by the demo app.
 *
 * Notes for students:
 * - All functions receive a non-null [Mat] and return a new [Mat] instance.
 * - The caller is responsible for releasing any [Mat] instances when they are no longer needed.
 */
object OpenCvImageProcessor {

    /**
     * Converts an RGBA image to grayscale.
     *
     * @param rgba The image in RGBA color space format.
     *
     * @return The converted grayscale image.
     */
    fun toGrayscale(rgba: Mat): Mat {
        val grayscale = Mat()
        Imgproc.cvtColor(rgba, grayscale, Imgproc.COLOR_RGBA2GRAY)
        return grayscale
    }

    /**
     * Converts an RGBA image to BGRA.
     *
     * @param rgba The image in RGBA color space format.
     *
     * @return The converted BGRA image.
     */
    fun toBgra(rgba: Mat): Mat {
        val bgra = Mat()
        Imgproc.cvtColor(rgba, bgra, Imgproc.COLOR_RGBA2BGRA)
        return bgra
    }

    /**
     * Applies an Otsu threshold + Canny edge detection pipeline.
     *
     * @param rgba The image in RGBA color space format.
     *
     * @return The binary image with the detected edges on it.
     */
    fun toCanny(rgba: Mat): Mat {
        val grayscale = toGrayscale(rgba)

        val thresh = Mat()
        val otsuThresh = Imgproc.threshold(
            grayscale,
            thresh,
            0.0,
            255.0,
            Imgproc.THRESH_BINARY or Imgproc.THRESH_OTSU,
        )

        val canny = Mat()
        Imgproc.Canny(
            grayscale,
            canny,
            otsuThresh * 0.5,
            otsuThresh
        )

        grayscale.release()
        thresh.release()

        return canny
    }
}
