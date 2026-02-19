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
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * A view model sensitive to changes in the `MainActivity` UI components.
 *
 * This mirrors the same responsibilities as the reference CameraX project:
 * - camera selection (front/back)
 * - last captured image Uri
 * - selected OpenCV processing method (kept for the OpenCV demo)
 */
class MainViewModel : ViewModel() {

    /**
     * The current selected camera (front/back).
     */
    private val _selector = MutableLiveData<Int>(CameraCharacteristics.LENS_FACING_BACK)

    /**
     * A `LiveData` which publicly exposes any update in the camera selector.
     */
    val selector: LiveData<Int>
        get() = _selector

    /**
     * The last captured image Uri.
     */
    private val _imageUri = MutableLiveData<Uri?>()

    /**
     * A `LiveData` which publicly exposes any update in the last captured image Uri.
     */
    val imageUri: LiveData<Uri?>
        get() = _imageUri

    /**
     * The selected OpenCV processing method id.
     */
    private val _methodId = MutableLiveData(0)

    val methodId: LiveData<Int>
        get() = _methodId

    /**
     * Called when the user changes which camera should be used.
     */
    fun onCameraSelectorChanged(lensFacing: Int) {
        _selector.value = lensFacing
    }

    /**
     * Called when a new image Uri is produced after saving a frame.
     */
    fun onImageUriChanged(uri: Uri?) {
        _imageUri.value = uri
    }

    /**
     * Called when the user changes the OpenCV processing method.
     */
    fun onMethodChanged(methodId: Int) {
        _methodId.value = methodId
    }
}
