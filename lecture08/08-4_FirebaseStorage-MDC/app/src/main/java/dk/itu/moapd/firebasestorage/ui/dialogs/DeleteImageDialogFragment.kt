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
package dk.itu.moapd.firebasestorage.ui.dialogs

import dk.itu.moapd.firebasestorage.R
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dk.itu.moapd.firebasestorage.data.repository.ImageRepository

/**
 * DialogFragment for deleting an image entry from Realtime Database and the corresponding file
 * from Firebase Storage.
 */
class DeleteImageDialogFragment : DialogFragment() {
    /**
     * A set of private constants used in this class.
     */
    companion object {
        /**
         * The argument key for the image key.
         */
        private const val ARG_IMAGE_KEY = "arg_image_key"

        /**
         * The argument key for the image path.
         */
        private const val ARG_IMAGE_PATH = "arg_image_path"

        /**
         * Creates an instance of the [DeleteImageDialogFragment] with the provided image key and
         * path.
         *
         * @param key The unique key of the image entry.
         * @param path The path of the image file in Firebase Storage.
         *
         * @return A new instance of the [DeleteImageDialogFragment].
         */
        fun createInstance(key: String, path: String): DeleteImageDialogFragment {
            return DeleteImageDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_IMAGE_KEY, key)
                    putString(ARG_IMAGE_PATH, path)
                }
            }
        }
    }

    /**
     * Override to build your own custom Dialog container. This is typically used to show an
     * AlertDialog instead of a generic Dialog; when doing so, `onCreateView()` does not need to be
     * implemented since the AlertDialog takes care of its own content.
     *
     * This method will be called after `onCreate(Bundle)` and immediately before `onCreateView()`.
     * The default implementation simply instantiates and returns a `Dialog` class.
     *
     * @param savedInstanceState The last saved instance state of the `Fragment`, or null if this
     *      is a freshly created `Fragment`.
     *
     * @return Return a new `Dialog` instance to be displayed by the `Fragment`.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val key = requireArguments().getString(ARG_IMAGE_KEY).orEmpty()
        val path = requireArguments().getString(ARG_IMAGE_PATH).orEmpty()

        return MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle(getString(R.string.dialog_delete_title))
            setMessage(getString(R.string.dialog_delete_message))
            setPositiveButton(getString(R.string.button_delete)) { dialog, _ ->
                deleteImage(key = key, path = path)
                dialog.dismiss()
            }
            setNegativeButton(getString(R.string.button_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
        }.create()
    }

    /**
     * Deletes an image entry from Realtime Database and the corresponding file from Firebase
     * Storage.
     *
     * @param key The unique key of the image entry.
     * @param path The path of the image file in Firebase Storage.
     */
    private fun deleteImage(key: String, path: String) {
        if (key.isBlank() || path.isBlank()) return
        val repo = ImageRepository()
        repo.deleteImage(key, path)
            ?.addOnSuccessListener {
                // Show confirmation
                view?.let { v -> Snackbar.make(v, R.string.message_image_deleted, Snackbar.LENGTH_SHORT).show() }
            }
            ?.addOnFailureListener { _ ->
                view?.let { v -> Snackbar.make(v, R.string.error_delete_image, Snackbar.LENGTH_LONG).show() }
            }
    }
}
