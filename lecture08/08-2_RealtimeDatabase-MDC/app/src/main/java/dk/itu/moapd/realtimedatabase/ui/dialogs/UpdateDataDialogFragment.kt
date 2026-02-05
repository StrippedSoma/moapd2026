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
package dk.itu.moapd.realtimedatabase.ui.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dk.itu.moapd.realtimedatabase.R
import dk.itu.moapd.realtimedatabase.data.repository.DummyRepository
import dk.itu.moapd.realtimedatabase.databinding.DialogDummyDataBinding

/**
 * A DialogFragment subclass for updating data to a database. This dialog shows a field to type a
 * `String` value.
 */
class UpdateDataDialogFragment : DialogFragment() {
    /**
     * A set of private constants used in this class.
     */
    companion object {

        /**
         * The argument key for the key of the dummy.
         */
        private const val ARG_KEY = "arg_key"

        /**
         * The argument key for the name of the dummy.
         */
        private const val ARG_NAME = "arg_name"

        /**
         * The argument key for the creation time of the dummy.
         */
        private const val ARG_CREATED_AT = "arg_created_at"

        /**
         * Creates a new instance of a [UpdateDataDialogFragment].
         *
         * @param key The key of the dummy.
         * @param currentName The current name of the dummy.
         * @param createdAt The creation time of the dummy.
         *
         * @return A new instance of a [UpdateDataDialogFragment].
         */
        fun createInstance(
            key: String,
            currentName: String,
            createdAt: Long?
        ): UpdateDataDialogFragment {
            return UpdateDataDialogFragment().apply {
                arguments = bundleOf(
                    ARG_KEY to key,
                    ARG_NAME to currentName,
                    ARG_CREATED_AT to (createdAt ?: Long.MIN_VALUE),
                )
            }
        }
    }

    /**
     * View binding is a feature that allows you to more easily write code that interacts with
     * views. Once view binding is enabled in a module, it generates a binding class for each XML
     * layout file present in that module. An instance of a binding class contains direct references
     * to all views that have an ID in the corresponding layout.
     */
    private var _binding: DialogDummyDataBinding? = null

    /**
     * This property is only valid between `onCreateView()` and `onDestroyView()` methods.
     */
    val binding
        get() =
            requireNotNull(_binding) {
                "Cannot access binding because it is null. Is the view visible?"
            }

    /**
     * Creates a new instance of a [DummyRepository].
     *
     * This repository is used to add data to a database.
     */
    private val repository by lazy { DummyRepository() }

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
        super.onCreateDialog(savedInstanceState)

        // Get the arguments from the bundle.
        val key = requireArguments().getString(ARG_KEY)
        val currentName = requireArguments().getString(ARG_NAME).orEmpty()
        val createdAt = requireArguments().getLong(ARG_CREATED_AT, Long.MIN_VALUE)
            .let { if (it == Long.MIN_VALUE) null else it }

        // Inflate the view using view binding.
        _binding = DialogDummyDataBinding.inflate(layoutInflater)
        binding.editTextName.setText(currentName)

        // Create a lambda for positive button click handling.
        val onPositiveButtonClick: (DialogInterface, Int) -> Unit = { dialog, _ ->
            val name = binding.editTextName.text.toString().trim()
            val userId = repository.currentUserId()

            if (name.isNotEmpty() && userId != null && key != null) {
                repository.updateDummy(
                    userId = userId,
                    key = key,
                    name = name,
                    createdAt = createdAt
                )
            }
            dialog.dismiss()
        }

        // Create and return a new instance of MaterialAlertDialogBuilder.
        return MaterialAlertDialogBuilder(requireContext()).apply {
            setView(binding.root)
            setTitle(getString(R.string.dialog_update_title))
            setMessage(getString(R.string.dialog_update_message))
            setPositiveButton(getString(R.string.button_update), onPositiveButtonClick)
            setNegativeButton(getString(R.string.button_cancel)) { dialog, _ -> dialog.dismiss() }
        }.create()
    }

    /**
     * Called when the view previously created by `onCreateView()` has been detached from the
     * fragment. The next time the fragment needs to be displayed, a new view will be created. This
     * is called after `onStop()` and before `onDestroy()`. It is called <em>regardless</em> of
     * whether `onCreateView()` returned a non-null view. Internally it is called after the view's
     * state has been saved but before it has been removed from its parent.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
