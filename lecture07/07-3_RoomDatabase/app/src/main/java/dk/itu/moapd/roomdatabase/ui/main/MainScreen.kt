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
package dk.itu.moapd.roomdatabase.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import dk.itu.moapd.roomdatabase.R
import dk.itu.moapd.roomdatabase.domain.model.Dummy
import dk.itu.moapd.roomdatabase.ui.main.components.DummyEditDialog
import dk.itu.moapd.roomdatabase.ui.main.components.DummyRow
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.LaunchedEffect

/**
 * The main screen composable.
 *
 * @property dummies The list of dummies.
 * @property onAdd The action to perform when the user adds a new dummy.
 * @property onUpdate The action to perform when the user updates a dummy.
 * @property onDelete The action to perform when the user deletes a dummy.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    dummies: List<Dummy>,
    onAdd: (Dummy) -> Unit,
    onUpdate: (Dummy) -> Unit,
    onDelete: (Dummy) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var showAddDialog by remember { mutableStateOf(false) }
    var editingDummy by remember { mutableStateOf<Dummy?>(null) }

    val marginMedium = dimensionResource(id = R.dimen.margin_medium)
    val marginStandard = dimensionResource(id = R.dimen.margin_standard)

    val addContentDesc = stringResource(R.string.content_description_add)
    val deletedMessage = stringResource(id = R.string.item_deleted)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.semantics { contentDescription = addContentDesc },
                onClick = { showAddDialog = true },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_box_24),
                    contentDescription = null,
                )
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            items(
                items = dummies,
                key = { it.id },
            ) { dummy ->
                val dismissState = rememberSwipeToDismissBoxState()

                LaunchedEffect(dismissState.currentValue) {
                    if (dismissState.currentValue != SwipeToDismissBoxValue.Settled) {
                        onDelete(dummy)
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = deletedMessage,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }

                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surface),
                        )
                    },
                    content = {
                        DummyRow(
                            name = dummy.name,
                            marginMedium = marginMedium,
                            marginStandard = marginStandard,
                            onLongPress = { editingDummy = dummy },
                        )
                    },
                )

                HorizontalDivider()
            }
        }
    }

    if (showAddDialog) {
        DummyEditDialog(
            title = stringResource(id = R.string.dialog_add_title),
            message = stringResource(id = R.string.dialog_add_message),
            confirmText = stringResource(id = R.string.button_add),
            initialValue = "",
            onDismiss = { showAddDialog = false },
            onConfirm = { value ->
                if (value.isNotBlank()) onAdd(Dummy(name = value))
                showAddDialog = false
            },
        )
    }

    editingDummy?.let { dummy ->
        DummyEditDialog(
            title = stringResource(id = R.string.dialog_update_title),
            message = stringResource(id = R.string.dialog_update_message),
            confirmText = stringResource(id = R.string.button_update),
            initialValue = dummy.name,
            onDismiss = { editingDummy = null },
            onConfirm = { value ->
                if (value.isNotBlank()) onUpdate(dummy.copy(name = value))
                editingDummy = null
            },
        )
    }
}