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
package dk.itu.moapd.firebasestorage.ui.main

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dk.itu.moapd.firebasestorage.R
import dk.itu.moapd.firebasestorage.ui.screens.ImageDetailScreen
import dk.itu.moapd.firebasestorage.ui.screens.ImagesGridScreen

/**
 * The route for the grid screen.
 */
private const val ROUTE_GRID = "grid"

/**
 * The route for the detail screen.
 */
private const val ROUTE_DETAIL = "detail"

/**
 * The main scaffold for the application.
 *
 * @param isUploading A boolean value indicating whether an image is being uploaded.
 * @param onPickImage A function to be called when the user selects an image to upload.
 * @param onLogout A function to be called when the user logs out.
 * @param snackbarHostState The state of the snackbar host.
 */
@Composable
fun MainScaffold(
    isUploading: Boolean,
    onPickImage: () -> Unit,
    onLogout: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            MainTopAppBar(
                isUploading = isUploading,
                onLogout = onLogout,
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onPickImage) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.content_description_add),
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = ROUTE_GRID,
        ) {
            composable(ROUTE_GRID) {
                ImagesGridScreen(
                    contentPadding = padding,
                    onOpenImage = { path ->
                        navController.navigate("$ROUTE_DETAIL/${Uri.encode(path)}")
                    },
                    snackbarHostState = snackbarHostState,
                )
            }

            composable(
                route = "$ROUTE_DETAIL/{path}",
                arguments = listOf(navArgument("path") { type = NavType.StringType }),
            ) { backStackEntry ->
                val path = backStackEntry.arguments?.getString("path").orEmpty()
                ImageDetailScreen(
                    contentPadding = padding,
                    path = Uri.decode(path),
                    onBack = { navController.popBackStack() },
                )
            }
        }
    }
}

/**
 * The top app bar for the main screen.
 *
 * @param isUploading A boolean value indicating whether an image is being uploaded.
 * @param onLogout A function to be called when the user logs out
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainTopAppBar(
    isUploading: Boolean,
    onLogout: () -> Unit,
) {
    var showMenu by remember { mutableStateOf(false) }

    Column {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
            actions = {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = null,
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                ) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = R.string.action_logout)) },
                        onClick = {
                            showMenu = false
                            onLogout()
                        },
                    )
                }
            },
        )

        if (isUploading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
