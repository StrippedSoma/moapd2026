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
package dk.itu.moapd.roomdatabase.ui.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dk.itu.moapd.roomdatabase.R
import dk.itu.moapd.roomdatabase.app.RoomStorageApplication
import dk.itu.moapd.roomdatabase.databinding.DialogDummyDataBinding
import dk.itu.moapd.roomdatabase.domain.model.Dummy
import dk.itu.moapd.roomdatabase.ui.main.DummyViewModel
import dk.itu.moapd.roomdatabase.ui.main.DummyViewModelFactory

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
         * The argument key for the dummy identifier.
         */
        private const val ARG_DUMMY_ID = "dummyIdentifier"

        /**
         * The argument key for the dummy name.
         */
        private const val ARG_DUMMY_NAME = "dummyNameValue"

        /**
         * Creates an instance of this dialog fragment with the provided dummy data.
         *
         * @param dummyData The dummy data to be displayed in the dialog.
         *
         * @return A new instance of this dialog fragment.
         */
        fun createInstance(dummyData: Dummy) =
            UpdateDataDialogFragment().apply {
                arguments =
                    Bundle().apply {
                        putInt(ARG_DUMMY_ID, dummyData.id)
                        putString(ARG_DUMMY_NAME, dummyData.name)
                    }
            }
    }

    /**
     * Retrieves the dummy identifier from the arguments.
     *
     * @return The dummy identifier.
     */
    private val dummyId: Int
        get() = requireArguments().getInt(ARG_DUMMY_ID)

    /**
     * Retrieves the dummy name from the arguments.
     *
     * @return The dummy name.
     */
    private val dummyName: String
        get() =
            requireArguments().getString(ARG_DUMMY_NAME)
                ?: error("Dummy name argument is required but was null")

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
     * Using lazy initialization to create the view model instance when the user access the object
     * for the first time.
     */
    private val dummyViewModel: DummyViewModel by viewModels {
        DummyViewModelFactory((requireActivity().application as RoomStorageApplication).repository)
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
        super.onCreateDialog(savedInstanceState)

        // Inflate the view using view binding.
        _binding = DialogDummyDataBinding.inflate(layoutInflater)
        binding.editTextName.setText(dummyName)

        // Create a lambda for positive button click handling.
        val onPositiveButtonClick: (DialogInterface, Int) -> Unit = { dialog, _ ->
            val name = binding.editTextName.text.toString()
            if (name.isNotBlank()) {
                val updatedDummy = Dummy(id = dummyId, name = name)
                dummyViewModel.update(updatedDummy)
            }
            dialog.dismiss()
        }

        // Create and return a new instance of MaterialAlertDialogBuilder.
        return MaterialAlertDialogBuilder(requireContext())
            .apply {
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
