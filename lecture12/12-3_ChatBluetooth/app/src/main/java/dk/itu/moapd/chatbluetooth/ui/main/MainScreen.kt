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
package dk.itu.moapd.chatbluetooth.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dk.itu.moapd.chatbluetooth.ui.main.screens.ChatScreen
import dk.itu.moapd.chatbluetooth.ui.main.screens.DiscoveryScreen
import dk.itu.moapd.chatbluetooth.ui.main.screens.HomeScreen
import dk.itu.moapd.chatbluetooth.ui.main.screens.PairedDevicesScreen
import dk.itu.moapd.chatbluetooth.ui.main.utils.encodeRoute

/**
 * The home route.
 */
private const val HOME_ROUTE = "home"

/**
 * The paired devices route.
 */
private const val PAIRED_ROUTE = "paired"

/**
 * The discovery route.
 */
private const val DISCOVERY_ROUTE = "discovery"

/**
 * The chat route.
 */
private const val CHAT_ROUTE = "chat/{deviceAddress}/{isServer}/{deviceName}"

/**
 * The main screen component.
 *
 * @param viewModel A view model sensitive to changes in the `MainActivity` UI components.
 */
@Composable
fun MainScreen(viewModel: BluetoothViewModel) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    NavHost(
        navController = navController,
        startDestination = HOME_ROUTE,
        modifier = Modifier.fillMaxSize(),
    ) {
        composable(HOME_ROUTE) {
            HomeScreen(
                viewModel = viewModel,
                snackbarHostState = snackbarHostState,
                onNavigatePaired = { navController.navigate(PAIRED_ROUTE) },
                onNavigateDiscovery = { navController.navigate(DISCOVERY_ROUTE) },
                onStartServer = {
                    viewModel.clearMessages()
                    viewModel.setRemoteDeviceName("Remote Device")
                    viewModel.startServer()
                    navController.navigate("chat/none/true/Remote Device")
                },
            )
        }

        composable(PAIRED_ROUTE) {
            PairedDevicesScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onDeviceSelected = { device ->
                    viewModel.clearMessages()
                    viewModel.connectToDevice(device.address, device.name)
                    navController.navigate(
                        "chat/${device.address}/false/${encodeRoute(device.name)}",
                    )
                },
            )
        }

        composable(DISCOVERY_ROUTE) {
            DiscoveryScreen(
                viewModel = viewModel,
                snackbarHostState = snackbarHostState,
                onBack = { navController.popBackStack() },
                onDeviceSelected = { device ->
                    viewModel.stopDiscovery()
                    viewModel.clearMessages()
                    viewModel.connectToDevice(device.address, device.name)
                    navController.navigate(
                        "chat/${device.address}/false/${encodeRoute(device.name)}",
                    )
                },
            )
        }

        composable(
            route = CHAT_ROUTE,
            arguments =
                listOf(
                    navArgument("deviceAddress") { type = NavType.StringType },
                    navArgument("isServer") { type = NavType.BoolType },
                    navArgument("deviceName") { type = NavType.StringType },
                ),
        ) { backStackEntry ->
            val isServer = backStackEntry.arguments?.getBoolean("isServer") ?: false
            val deviceName =
                backStackEntry.arguments
                    ?.getString("deviceName")
                    .orEmpty()
                    .ifBlank { "Remote Device" }

            LaunchedEffect(deviceName) {
                viewModel.setRemoteDeviceName(deviceName)
            }

            ChatScreen(
                viewModel = viewModel,
                isServer = isServer,
                onBack = {
                    viewModel.disconnect()
                    navController.popBackStack(HOME_ROUTE, false)
                },
            )
        }
    }
}
