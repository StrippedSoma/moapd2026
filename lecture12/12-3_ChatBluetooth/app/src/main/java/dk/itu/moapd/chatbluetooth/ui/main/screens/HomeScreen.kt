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

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import dk.itu.moapd.chatbluetooth.R
import dk.itu.moapd.chatbluetooth.data.model.ConnectionState
import dk.itu.moapd.chatbluetooth.ui.main.BluetoothViewModel
import dk.itu.moapd.chatbluetooth.ui.main.utils.connectionLabel
import dk.itu.moapd.chatbluetooth.ui.main.utils.ensureBluetoothReady
import dk.itu.moapd.chatbluetooth.ui.main.utils.getRequiredPermissions

/**
 * The main screen component.
 *
 * @param viewModel A view model sensitive to changes in the `MainActivity` UI components.
 * @param snackbarHostState The state of the snackbar component.
 * @param onNavigatePaired A callback to navigate to the paired devices screen.
 * @param onNavigateDiscovery A callback to navigate to the discovery screen.
 * @param onStartServer A callback to start the server.
 */
@Composable
fun HomeScreen(
    viewModel: BluetoothViewModel,
    snackbarHostState: SnackbarHostState,
    onNavigatePaired: () -> Unit,
    onNavigateDiscovery: () -> Unit,
    onStartServer: () -> Unit,
) {
    val context = LocalContext.current
    val connectionState by viewModel.connectionState.observeAsState(ConnectionState.IDLE)
    val permissions = remember { getRequiredPermissions() }

    val enableBluetoothLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Toast
                    .makeText(
                        context,
                        R.string.bt_enabled,
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
        ) { result ->
            if (!result.values.all { it }) {
                Toast
                    .makeText(
                        context,
                        R.string.error_permissions_required,
                        Toast.LENGTH_LONG,
                    ).show()
            }
        }

    fun hasPermissions(): Boolean =
        permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

    LaunchedEffect(Unit) {
        if (!hasPermissions()) {
            permissionLauncher.launch(permissions.toTypedArray())
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.bluetooth_status),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text =
                            when {
                                !viewModel.isBluetoothSupported ->
                                    stringResource(R.string.bt_not_supported)
                                viewModel.isBluetoothEnabled ->
                                    stringResource(R.string.bt_enabled)
                                else ->
                                    stringResource(R.string.bt_disabled)
                            },
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = connectionLabel(connectionState, context),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    when {
                        !hasPermissions() -> {
                            permissionLauncher.launch(permissions.toTypedArray())
                        }
                        !viewModel.isBluetoothEnabled -> {
                            enableBluetoothLauncher.launch(
                                Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),
                            )
                        }
                        else -> {
                            Toast
                                .makeText(
                                    context,
                                    R.string.bt_enabled,
                                    Toast.LENGTH_SHORT
                                ).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.isBluetoothSupported,
            ) {
                Icon(Icons.Default.Bluetooth, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.enable_bluetooth))
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    if (
                        ensureBluetoothReady(
                            viewModel = viewModel,
                            hasPermissions = hasPermissions(),
                            context = context,
                            permissionLauncher = permissionLauncher,
                            permissions = permissions,
                        )
                    ) {
                        onNavigatePaired()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.paired_devices))
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    if (
                        ensureBluetoothReady(
                            viewModel = viewModel,
                            hasPermissions = hasPermissions(),
                            context = context,
                            permissionLauncher = permissionLauncher,
                            permissions = permissions,
                        )
                    ) {
                        onNavigateDiscovery()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.discover_devices))
            }

            Spacer(Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    if (
                        ensureBluetoothReady(
                            viewModel = viewModel,
                            hasPermissions = hasPermissions(),
                            context = context,
                            permissionLauncher = permissionLauncher,
                            permissions = permissions,
                        )
                    ) {
                        onStartServer()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.start_server))
            }
        }
    }
}
