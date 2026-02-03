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
package dk.itu.moapd.testingapps.ui.main

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import dk.itu.moapd.testingapps.R
import dk.itu.moapd.testingapps.databinding.FragmentMainBinding
import dk.itu.moapd.testingapps.domain.validation.InputValidator
import dk.itu.moapd.testingapps.domain.validation.InputValidator.InputType
import dk.itu.moapd.testingapps.ui.common.hideKeyboard
import dk.itu.moapd.testingapps.ui.common.showSnackBar
import dk.itu.moapd.testingapps.ui.utils.viewBinding

/**
 * Displays a simple form and validates user input before "sending" the data.
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
     * A component to validate the form data.
     */
    private lateinit var nameValidator: InputValidator

    /**
     * A component to validate the user's email.
     */
    private lateinit var emailValidator: InputValidator

    /**
     * A component to validate the user's password.
     */
    private lateinit var passwordValidator: InputValidator

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
        setupValidators()
        setupClickListeners()
    }

    /**
     * Sets up the validators for the form fields.
     */
    private fun setupValidators() = with(binding) {
        nameValidator = InputValidator(InputType.NAME)
        editTextName.addTextChangedListener(nameValidator)

        emailValidator = InputValidator(InputType.EMAIL)
        editTextEmail.addTextChangedListener(emailValidator)

        passwordValidator = InputValidator(InputType.PASSWORD)
        editTextPassword.addTextChangedListener(passwordValidator)
    }

    /**
     * Sets up the click listeners for the form buttons.
     */
    private fun setupClickListeners() = with(binding) {
        buttonRevert.setOnClickListener { onRevertClick(it) }
        buttonSend.setOnClickListener { onSendClick(it) }
    }

    /**
     * This method is called when the `Send` button is clicked.
     *
     * @param view The pressed view UI component.
     */
    private fun onSendClick(anchor: View) {
        val errors = validateInputs()
        hideKeyboard(anchor)

        if (errors.isNotEmpty()) {
            errors.forEach { (field, message) -> field.error = message }
            return
        }

        binding.root.showSnackBar("Data was successfully sent")
        onRevertClick(anchor)
    }

    /**
     * This method is called when the `Revert` button is clicked.
     *
     * @param view The pressed view UI component.
     */
    private fun onRevertClick(anchor: View) {
        with(binding) {
            editTextName.text?.clear()
            editTextEmail.text?.clear()
            editTextPassword.text?.clear()
        }
        hideKeyboard(anchor)
    }

    /**
     * Validates the input fields and returns a list of pairs containing the invalid fields and
     * their error messages.
     *
     * @return A list of pairs containing the invalid fields and their error messages.
     */
    private fun validateInputs(): List<Pair<EditText, String>> {
        val errorMessage = getString(R.string.error)
        return buildList {
            with(binding) {
                if (!nameValidator.isValid) add(editTextName to errorMessage)
                if (!emailValidator.isValid) add(editTextEmail to errorMessage)
                if (!passwordValidator.isValid) add(editTextPassword to errorMessage)
            }
        }
    }
}
