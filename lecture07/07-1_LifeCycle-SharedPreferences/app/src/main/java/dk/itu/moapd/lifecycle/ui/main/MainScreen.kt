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
package dk.itu.moapd.lifecycle.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dk.itu.moapd.lifecycle.R

/**
 * Composable function for displaying the main screen of the application.
 *
 * @param modifier Optional modifier for configuring the layout and behavior of the main screen.
 */
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    initialTextId: Int,
    initialChecked: Boolean,
    onPersist: (textId: Int, checked: Boolean) -> Unit,
) {
    var textId by rememberSaveable { mutableIntStateOf(initialTextId) }
    var checked by rememberSaveable { mutableStateOf(initialChecked) }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = stringResource(textId),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        )

        MainButtonsRow(
            onTrueClick = {
                textId = R.string.true_text
                onPersist(textId, checked)
            },
            onFalseClick = {
                textId = R.string.false_text
                onPersist(textId, checked)
            }
        )

        CheckBoxWithLabel(
            checked = checked,
            onCheckedChange = { newChecked ->
                checked = newChecked
                textId = if (newChecked) R.string.checked_text else R.string.unchecked_text
                onPersist(textId, checked)
            }
        )
    }
}

/**
 * Composable function for displaying a row of two buttons.
 *
 * @param onTrueClick Callback function invoked when the "True" button is clicked.
 * @param onFalseClick Callback function invoked when the "False" button is clicked.
 *
 * @see Button
 */
@Composable
private fun MainButtonsRow(
    onTrueClick: () -> Unit,
    onFalseClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = onTrueClick,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text(stringResource(R.string.button_true), textAlign = TextAlign.Center)
        }

        Button(
            onClick = onFalseClick,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(stringResource(R.string.button_false), textAlign = TextAlign.Center)
        }
    }
}

/**
 * Composable function for displaying a checkbox with accompanying text.
 *
 * @param checked The current state of the checkbox (true if checked, false otherwise).
 * @param onCheckedChange Callback function invoked when the checkbox state changes.
 *
 * @see Checkbox
 */
@Composable
private fun CheckBoxWithLabel(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .height(56.dp)
            .toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                role = Role.Checkbox
            )
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null
        )
        Text(
            text = stringResource(R.string.check_box_select),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}