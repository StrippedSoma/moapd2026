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

import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.mlkit.vision.common.InputImage
import dk.itu.moapd.mlkit.R
import dk.itu.moapd.mlkit.databinding.FragmentMainBinding
import dk.itu.moapd.mlkit.domain.vision.ObjectDetectionProcessor
import dk.itu.moapd.mlkit.ui.common.tag
import dk.itu.moapd.mlkit.ui.utils.viewBinding
import kotlin.math.min

/**
 * Main screen: capture a photo and run ML Kit Object Detection.
 */
class MainFragment : Fragment(R.layout.fragment_main) {

    /**
     * View binding is a feature that allows you to more easily write code that interacts with
     * views. Once view binding is enabled in a module, it generates a binding class for each XML
     * layout file present in that module. An instance of a binding class contains direct references
     * to all views that have an ID in the corresponding layout.
     */
    private val binding by viewBinding(FragmentMainBinding::bind)

    /**
     * The URI of latest taken photo using the camera intent.
     */
    private var capturedImageUri: Uri? = null

    /**
     * ML Kit Object Detection model.
     */
    private val processor = ObjectDetectionProcessor()

    /**
     * This object launches a new activity and receives back some result data.
     */
    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ::handleCameraResult
    )

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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Define the UI behavior using lambda expressions.
        binding.buttonCapture.setOnClickListener { launchCameraIntent() }
    }

    /**
     * Launches the camera intent to capture a photo.
     */
    private fun launchCameraIntent() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        // Only launch if there is a camera app available.
        val canHandle = cameraIntent.resolveActivity(requireActivity().packageManager) != null
        if (!canHandle) return

        val values = ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, "ML Kit")
        }

        val uri = requireActivity().contentResolver
            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            ?: return

        capturedImageUri = uri
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        cameraLauncher.launch(cameraIntent)
    }

    /**
     * Handles the result of the camera intent.
     *
     * @param result The result of the camera intent.
     */
    private fun handleCameraResult(result: ActivityResult) {
        if (result.resultCode != RESULT_OK) return

        val bitmap = getCapturedImageBitmap() ?: return

        // Display captured image.
        binding.imageView.setImageBitmap(bitmap)

        // Run Object Detection and render the visualized result.
        processor.process(
            bitmap = bitmap,
            onSuccess = { visualized -> binding.imageView.setImageBitmap(visualized) },
            onFailure = { e -> Log.e(tag(), e.message.orEmpty(), e) }
        )
    }

    /**
     * Gets the captured image bitmap.
     *
     * @return The captured image bitmap.
     */
    private fun getCapturedImageBitmap(): Bitmap? {
        val uri = capturedImageUri ?: return null

        val bitmap = InputImage.fromFilePath(requireContext(), uri).bitmapInternal ?: return null

        // Crop the bitmap to match the ImageView aspect (same behavior as the original).
        val viewWidth = binding.imageView.width.toFloat()
        val viewHeight = binding.imageView.height.toFloat()
        val imageWidth = bitmap.width.toFloat()
        val imageHeight = bitmap.height.toFloat()

        val scaleFactor = min(imageWidth / viewWidth, imageHeight / viewHeight)
        val scaledWidth = viewWidth * scaleFactor
        val scaledHeight = viewHeight * scaleFactor

        val left = (imageWidth - scaledWidth) / 2
        val top = (imageHeight - scaledHeight) / 2

        return Bitmap.createBitmap(
            bitmap,
            left.toInt(),
            top.toInt(),
            scaledWidth.toInt(),
            scaledHeight.toInt(),
        )
    }
}
