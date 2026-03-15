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
package dk.itu.moapd.chatbluetooth.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import dk.itu.moapd.chatbluetooth.data.model.BluetoothDeviceItem

/**
 * A [BroadcastReceiver] that listens for Bluetooth discovery events.
 *
 * ## How Bluetooth Discovery Works
 * When [BluetoothAdapter.startDiscovery] is called, the system starts scanning for nearby Bluetooth
 * devices. For each device found, the system broadcasts an [BluetoothDevice.ACTION_FOUND] intent
 * containing the [BluetoothDevice] object.
 *
 * This receiver also listens for [BluetoothAdapter.ACTION_DISCOVERY_STARTED] and
 * [BluetoothAdapter.ACTION_DISCOVERY_FINISHED] to track the scanning lifecycle.
 *
 * ## Usage
 * Register this receiver in the Discovery fragment's `onStart()` and unregister it in `onStop()` to
 * avoid leaking the receiver.
 *
 * @param onDeviceFound Callback invoked when a new device is discovered.
 * @param onDiscoveryStarted Callback when scanning begins.
 * @param onDiscoveryFinished Callback when scanning ends.
 */
class BluetoothDiscoveryReceiver(
    private val onDeviceFound: (BluetoothDeviceItem) -> Unit,
    private val onDiscoveryStarted: () -> Unit = {},
    private val onDiscoveryFinished: () -> Unit = {},
) : BroadcastReceiver() {
    /**
     * A set of static attributes used in this class.
     */
    companion object {
        /**
         * Tag for logging.
         */
        private val TAG = BluetoothDiscoveryReceiver::class.qualifiedName

        /**
         * Creates the [IntentFilter] that this receiver should be registered with.
         *
         * We listen for three actions:
         * - `ACTION_FOUND` – a new device was discovered.
         * - `ACTION_DISCOVERY_STARTED` – scanning has begun.
         * - `ACTION_DISCOVERY_FINISHED` – scanning has ended (after ~12 seconds).
         */
        fun getIntentFilter(): IntentFilter =
            IntentFilter().apply {
                addAction(BluetoothDevice.ACTION_FOUND)
                addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
                addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
            }
    }

    /**
     * Called by the system when a broadcast matching our [IntentFilter] is received.
     *
     * @param context The application context.
     * @param intent The broadcast intent.
     */
    @SuppressLint("MissingPermission")
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        when (intent.action) {
            BluetoothDevice.ACTION_FOUND -> {
                // Extract the BluetoothDevice from the intent extras.
                // Use the type-safe API on Android 13+ (API 33), fall back on older versions.
                val device: BluetoothDevice? =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra(
                            BluetoothDevice.EXTRA_DEVICE,
                            BluetoothDevice::class.java,
                        )
                    } else {
                        @Suppress("DEPRECATION")
                        intent.getParcelableExtra(
                            BluetoothDevice.EXTRA_DEVICE,
                        )
                    }

                device?.let {
                    val item =
                        BluetoothDeviceItem(
                            name = it.name ?: "Unknown",
                            address = it.address,
                        )
                    Log.d(TAG, "Discovered: ${item.name} [${item.address}]")
                    onDeviceFound(item)
                }
            }

            BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                Log.d(TAG, "Discovery started")
                onDiscoveryStarted()
            }

            BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                Log.d(TAG, "Discovery finished")
                onDiscoveryFinished()
            }
        }
    }
}
