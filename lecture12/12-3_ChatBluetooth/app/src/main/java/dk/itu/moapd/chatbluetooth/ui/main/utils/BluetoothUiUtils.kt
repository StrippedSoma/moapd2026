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
package dk.itu.moapd.chatbluetooth.ui.main.utils

import android.Manifest
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import dk.itu.moapd.chatbluetooth.R
import dk.itu.moapd.chatbluetooth.data.model.ConnectionState
import dk.itu.moapd.chatbluetooth.ui.main.BluetoothViewModel
import java.net.URLEncoder

/**
 * Returns the string representation of the [ConnectionState].
 *
 * @param state The connection state.
 * @param context The application context.
 *
 * @return The string representation of the connection state.
 */
fun connectionLabel(state: ConnectionState, context: Context): String = when (state) {
    ConnectionState.IDLE -> context.getString(R.string.state_idle)
    ConnectionState.DISCOVERING -> context.getString(R.string.state_discovering)
    ConnectionState.LISTENING -> context.getString(R.string.state_listening)
    ConnectionState.CONNECTING -> context.getString(R.string.state_connecting)
    ConnectionState.CONNECTED -> context.getString(R.string.state_connected)
    ConnectionState.CONNECTION_FAILED -> context.getString(R.string.state_connection_failed)
    ConnectionState.DISCONNECTED -> context.getString(R.string.state_disconnected)
}

/**
 * Ensures that Bluetooth is ready for use.
 *
 * @param viewModel The [BluetoothViewModel] that owns this screen.
 * @param hasPermissions `true` if the required permissions have been granted.
 * @param context The application context.
 * @param permissionLauncher The permission launcher.
 * @param permissions The list of required permissions.
 *
 * @return `true` if Bluetooth is ready to use, `false` otherwise.
 */
fun ensureBluetoothReady(
    viewModel: BluetoothViewModel,
    hasPermissions: Boolean,
    context: Context,
    permissionLauncher: ActivityResultLauncher<Array<String>>,
    permissions: List<String>,
): Boolean {
    return when {
        !hasPermissions -> {
            permissionLauncher.launch(permissions.toTypedArray())
            false
        }
        !viewModel.isBluetoothEnabled -> {
            Toast.makeText(
                context,
                R.string.error_bluetooth_required,
                Toast.LENGTH_LONG,
            ).show()
            false
        }
        else -> true
    }
}

/**
 * Returns the list of required permissions.
 *
 * @return The list of required permissions.
 */
fun getRequiredPermissions(): List<String> =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        listOf(
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_ADVERTISE,
        )
    } else {
        listOf(Manifest.permission.ACCESS_FINE_LOCATION)
    }

/**
 * Encodes a string to be used in a URL.
 *
 * @param value The string to encode.
 */
fun encodeRoute(value: String): String =
    URLEncoder.encode(value, Charsets.UTF_8.name())
