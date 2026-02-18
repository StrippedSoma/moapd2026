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
package dk.itu.moapd.opencv.ui.session

import android.hardware.camera2.CameraCharacteristics
import dk.itu.moapd.opencv.camera.OpenCvImageProcessor
import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat

/**
 * This class manages the OpenCV pipeline in the Android application.
 */
class OpenCvSession : CameraBridgeViewBase.CvCameraViewListener2 {

    /**
     * The current selected camera lens facing (front/back).
     */
    var lensFacing: Int = CameraCharacteristics.LENS_FACING_BACK

    /**
     * The current selected OpenCV processing method id.
     */
    var methodId: Int = 0

    /**
     * The latest frame captured by the camera.
     */
    var latestFrame: Mat? = null
        private set

    /**
     * The camera view.
     */
    var cameraView: CameraBridgeViewBase? = null

    /**
     * The camera frame capture buffer.
     */
    private var captureMat: Mat? = null

    /**
     * This method is invoked when camera preview has started. After this method is invoked the
     * frames will start to be delivered to client via the `onCameraFrame()` callback.
     *
     * @param width The width of the frames that will be delivered.
     * @param height The height of the frames that will be delivered
     */
    override fun onCameraViewStarted(width: Int, height: Int) {
        captureMat = Mat(height, width, CvType.CV_8UC4)
    }

    /**
     * This method is invoked when camera preview has been stopped for some reason. No frames will
     * be delivered via `onCameraFrame()` callback after this method is called.
     */
    override fun onCameraViewStopped() {
        captureMat?.release()
        captureMat = null
        latestFrame?.release()
        latestFrame = null
    }

    /**
     * This method is invoked when delivery of the frame needs to be done. The returned values - is
     * a modified frame which needs to be displayed on the screen.
     *
     * @param inputFrame The current frame grabbed from the video camera device stream.
     */
    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        val image = inputFrame?.rgba() ?: return Mat()

        // Match original fragment behavior.
        if (lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
            Core.flip(image, image, 1)
        }

        // Keep a copy of the latest frame for capture.
        if (captureMat != null) {
            image.copyTo(captureMat)
            latestFrame?.release()
            latestFrame = captureMat?.clone()
        }

        return when (methodId) {
            1 -> OpenCvImageProcessor.toGrayscale(image)
            2 -> OpenCvImageProcessor.toBgra(image)
            3 -> OpenCvImageProcessor.toCanny(image)
            else -> image
        }
    }
}
