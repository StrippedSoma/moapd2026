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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import dk.itu.moapd.testingapps.R
import dk.itu.moapd.testingapps.domain.validation.InputValidator
import dk.itu.moapd.testingapps.ui.components.ValidatedTextField
import kotlinx.coroutines.launch

/**
 * The main screen of the application.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarScope = rememberCoroutineScope()
    val keyboard = LocalSoftwareKeyboardController.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var showErrors by remember { mutableStateOf(false) }

    val isNameValid = InputValidator.InputType.NAME.isValid(name)
    val isEmailValid = InputValidator.InputType.EMAIL.isValid(email)
    val isPasswordValid = InputValidator.InputType.PASSWORD.isValid(password)

    val successMessage = stringResource(id = R.string.data_sent_successfully)

    fun resetForm() {
        name = ""
        email = ""
        password = ""
        showErrors = false
        keyboard?.hide()
    }

    fun submitForm() {
        keyboard?.hide()
        showErrors = true

        val isFormValid = isNameValid && isEmailValid && isPasswordValid
        if (!isFormValid) return

        snackbarScope.launch {
            snackbarHostState.showSnackbar(
                message = successMessage
            )
        }
        resetForm()
    }

    val contentPadding = dimensionResource(id = R.dimen.margin_medium)
    val spaceMedium = dimensionResource(id = R.dimen.margin_medium)

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(id = R.string.app_name)) }) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(contentPadding)
        ) {
            ValidatedTextField(
                value = name,
                onValueChange = { name = it },
                label = stringResource(id = R.string.name_hint),
                showError = showErrors && !isNameValid,
                errorText = stringResource(id = R.string.error),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(MainTestTags.NameField)
            )

            ValidatedTextField(
                value = email,
                onValueChange = { email = it },
                label = stringResource(id = R.string.email_hint),
                showError = showErrors && !isEmailValid,
                errorText = stringResource(id = R.string.error),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(MainTestTags.EmailField)
            )

            ValidatedTextField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(id = R.string.password_hint),
                showError = showErrors && !isPasswordValid,
                errorText = stringResource(id = R.string.error),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(MainTestTags.PasswordField),
                isPassword = true
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = ::resetForm,
                    modifier = Modifier.testTag(MainTestTags.RevertButton)
                ) {
                    Text(stringResource(id = R.string.button_revert))
                }
                Spacer(modifier = Modifier.width(spaceMedium))
                Button(
                    onClick = ::submitForm,
                    modifier = Modifier.testTag(MainTestTags.SendButton)
                ) {
                    Text(stringResource(id = R.string.button_send))
                }
            }
        }
    }
}