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
package dk.itu.moapd.chatbluetooth.ui.main.screens

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import dk.itu.moapd.chatbluetooth.R
import dk.itu.moapd.chatbluetooth.bluetooth.BluetoothDiscoveryReceiver
import dk.itu.moapd.chatbluetooth.data.model.BluetoothDeviceItem
import dk.itu.moapd.chatbluetooth.data.model.ConnectionState
import dk.itu.moapd.chatbluetooth.ui.main.BluetoothViewModel
import dk.itu.moapd.chatbluetooth.ui.main.utils.getRequiredPermissions
import dk.itu.moapd.chatbluetooth.ui.main.widgets.DeviceList

/**
 * The screen that displays a list of discovered devices.
 *
 * @param viewModel The [BluetoothViewModel] that owns this screen.
 * @param snackbarHostState The [SnackbarHostState] used to show notifications.
 * @param onBack Callback invoked when the back button is pressed
 * @param onDeviceSelected Callback invoked when a device is selected.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoveryScreen(
    viewModel: BluetoothViewModel,
    snackbarHostState: SnackbarHostState,
    onBack: () -> Unit,
    onDeviceSelected: (BluetoothDeviceItem) -> Unit,
) {
    val context = LocalContext.current
    val devices by viewModel.discoveredDevices.observeAsState(emptyList())
    val state by viewModel.connectionState.observeAsState(ConnectionState.IDLE)
    val permissions = remember { getRequiredPermissions() }

    val discoveryReceiver =
        remember(viewModel) {
            BluetoothDiscoveryReceiver(
                onDeviceFound = viewModel.controller.onDeviceDiscovered ?: {},
            )
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
        ) {}

    DisposableEffect(context, discoveryReceiver) {
        ContextCompat.registerReceiver(
            context,
            discoveryReceiver,
            BluetoothDiscoveryReceiver.getIntentFilter(),
            ContextCompat.RECEIVER_EXPORTED,
        )

        onDispose {
            runCatching { context.unregisterReceiver(discoveryReceiver) }
            viewModel.stopDiscovery()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.discover_devices)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
            ) {
                Button(
                    onClick = {
                        val hasPermissions =
                            permissions.all {
                                ContextCompat.checkSelfPermission(
                                    context,
                                    it,
                                ) == PackageManager.PERMISSION_GRANTED
                            }

                        when {
                            !hasPermissions -> {
                                permissionLauncher.launch(permissions.toTypedArray())
                            }
                            !viewModel.isBluetoothEnabled -> {
                                Toast
                                    .makeText(
                                        context,
                                        R.string.error_bluetooth_required,
                                        Toast.LENGTH_LONG,
                                    ).show()
                            }
                            else -> {
                                viewModel.startDiscovery()
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Text(stringResource(R.string.start_scan))
                }

                Spacer(Modifier.padding(8.dp))

                OutlinedButton(
                    onClick = {
                        viewModel.stopDiscovery()
                        Toast
                            .makeText(
                                context,
                                R.string.info_discovery_stopped,
                                Toast.LENGTH_SHORT,
                            ).show()
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Text(stringResource(R.string.stop_scan))
                }
            }

            if (state == ConnectionState.DISCOVERING) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            if (devices.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_devices_found),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                )
            } else {
                DeviceList(
                    devices = devices,
                    modifier = Modifier.weight(1f),
                    onClick = onDeviceSelected,
                )
            }
        }
    }
}
