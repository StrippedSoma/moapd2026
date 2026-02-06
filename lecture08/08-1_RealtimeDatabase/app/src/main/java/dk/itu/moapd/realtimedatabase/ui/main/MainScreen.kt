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
package dk.itu.moapd.realtimedatabase.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dk.itu.moapd.realtimedatabase.R
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.collectAsState
import dk.itu.moapd.realtimedatabase.ui.main.components.DummyEditDialog
import dk.itu.moapd.realtimedatabase.ui.main.components.DummyRow
import kotlinx.coroutines.launch

/**
 * The main screen composable.
 *
 * @property uiState The current state of the main screen.
 * @property onInsert The action to perform when the user inserts a dummy.
 * @property onUpdate The action to perform when the user updates a dummy.
 * @property onDelete The action to perform when the user deletes a dummy.
 * @property onLogout The action to perform when the user logs out.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    uiState: StateFlow<MainUiState>,
    onInsert: (String) -> Unit,
    onUpdate: (key: String, name: String, createdAt: Long?) -> Unit,
    onDelete: (key: String) -> Unit,
    onLogout: () -> Unit,
) {
    val state by uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var showAddDialog by remember { mutableStateOf(false) }
    var editTarget by remember { mutableStateOf<DummyUi?>(null) }

    var menuExpanded by remember { mutableStateOf(false) }

    val notAuthenticated = stringResource(id = R.string.not_authenticated)
    val deletedMessage = stringResource(id = R.string.item_deleted)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = null
                        )
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.action_logout)) },
                            onClick = {
                                menuExpanded = false
                                onLogout()
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_box_24),
                    contentDescription = stringResource(id = R.string.content_description_add)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { padding ->
        if (state.userId == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = notAuthenticated)
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(vertical = 4.dp),
        ) {
            items(items = state.dummies, key = { it.key }) { item ->
                val dismissState = rememberSwipeToDismissBoxState()

                LaunchedEffect(dismissState.currentValue) {
                    if (dismissState.currentValue != SwipeToDismissBoxValue.Settled) {
                        onDelete(item.key)
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
                    backgroundContent = {},
                    content = {
                        DummyRow(
                            dummy = item,
                            onLongPress = { editTarget = item }
                        )
                    }
                )
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
                if (value.isNotBlank()) {
                    onInsert(value)
                }
                showAddDialog = false
            }
        )
    }

    val target = editTarget
    if (target != null) {
        DummyEditDialog(
            title = stringResource(id = R.string.dialog_update_title),
            message = stringResource(id = R.string.dialog_update_message),
            confirmText = stringResource(id = R.string.button_update),
            initialValue = target.name,
            onDismiss = { editTarget = null },
            onConfirm = { value ->
                if (value.isNotBlank()) {
                    onUpdate(target.key, value, target.createdAt)
                }
                editTarget = null
            }
        )
    }
}
