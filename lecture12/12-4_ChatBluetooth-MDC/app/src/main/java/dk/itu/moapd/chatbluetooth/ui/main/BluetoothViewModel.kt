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

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dk.itu.moapd.chatbluetooth.bluetooth.BluetoothController
import dk.itu.moapd.chatbluetooth.data.model.BluetoothDeviceItem
import dk.itu.moapd.chatbluetooth.data.model.ChatMessage
import dk.itu.moapd.chatbluetooth.data.model.ConnectionState

/**
 * Shared ViewModel that all fragments use to interact with Bluetooth.
 *
 * ## Why a Shared ViewModel?
 * The Bluetooth connection state and messages must survive fragment transactions. By scoping this
 * ViewModel to the Activity, all fragments observe the same live data and operate on the same
 * [BluetoothController] instance.
 *
 * ## Architecture
 * ```
 * Fragments ←→ BluetoothViewModel ←→ BluetoothController ←→ Threads/Sockets
 * ```
 * Fragments never touch Bluetooth APIs directly. They call methods on this ViewModel and observe
 * [LiveData] for state changes.
 *
 * @param application The application context, needed to initialize [BluetoothController].
 */
class BluetoothViewModel(
    application: Application,
) : AndroidViewModel(application) {
    /**
     * The controller that manages all Bluetooth operations.
     */
    val controller = BluetoothController(application.applicationContext)

    /**
     * The current connection state, displayed in the HomeFragment.
     */
    private val _connectionState = MutableLiveData(ConnectionState.IDLE)

    /**
     * The current connection state (e.g., connecting, connected, etc.), displayed in the
     * HomeFragment.
     */
    val connectionState: LiveData<ConnectionState> = _connectionState

    /**
     * The list of chat messages, displayed in the ChatFragment.
     */
    private val _messages = MutableLiveData<List<ChatMessage>>(emptyList())

    /**
     * The list of chat messages (sent + received), displayed in the ChatFragment.
     */
    val messages: LiveData<List<ChatMessage>> = _messages

    /**
     * The list of discovered devices, displayed in the DiscoveryFragment.
     */
    private val _discoveredDevices = MutableLiveData<List<BluetoothDeviceItem>>(emptyList())

    /**
     * The list of discovered devices (new + existing), displayed in the DiscoveryFragment.
     */
    val discoveredDevices: LiveData<List<BluetoothDeviceItem>> = _discoveredDevices

    /**
     * The name of the remote device, displayed in the ChatFragment.
     */
    private val _remoteDeviceName = MutableLiveData<String>()

    /**
     * The name of the remote device (e.g., "Remote Device"), displayed in the ChatFragment.
     */
    val remoteDeviceName: LiveData<String> = _remoteDeviceName

    /**
     * Initializes the controller callbacks.
     */
    init {
        controller.onStateChanged = { state ->
            _connectionState.value = state
        }

        controller.onMessageReceived = { message ->
            val current = _messages.value.orEmpty().toMutableList()
            current.add(message)
            _messages.value = current
        }

        controller.onDeviceDiscovered = { device ->
            val current = _discoveredDevices.value.orEmpty().toMutableList()
            // Avoid duplicates (devices can be found multiple times during a scan).
            if (current.none { it.address == device.address }) {
                current.add(device)
                _discoveredDevices.value = current
            }
        }
    }

    /**
     * Whether the device has Bluetooth hardware.
     */
    val isBluetoothSupported: Boolean get() = controller.isBluetoothSupported

    /**
     * Whether Bluetooth is currently enabled.
     */
    val isBluetoothEnabled: Boolean get() = controller.isBluetoothEnabled

    /**
     * Returns the list of already-paired devices.
     *
     * @return The list of paired devices.
     */
    fun getPairedDevices(): List<BluetoothDeviceItem> = controller.getPairedDevices()

    /**
     * Starts Bluetooth device discovery.
     */
    fun startDiscovery() {
        _discoveredDevices.value = emptyList()
        controller.startDiscovery()
    }

    /**
     * Stops Bluetooth device discovery.
     */
    fun stopDiscovery() = controller.stopDiscovery()

    /**
     * Starts the Bluetooth server (listening for incoming connections).
     *
     * Starts the Bluetooth server (listening for incoming connections).
     *
     * @return `true` if the server was successfully started, `false` otherwise.
     */
    fun startServer() = controller.startServer()

    /**
     * Connects to a remote device as a client.
     *
     * @param address The MAC address of the device.
     * @param deviceName The human-readable name of the device.
     */
    fun connectToDevice(
        address: String,
        deviceName: String,
    ) {
        _remoteDeviceName.value = deviceName
        controller.connectToDevice(address)
    }

    /**
     * Sends a text message to the connected device. Also adds the message to the local message
     * list.
     *
     * @param text The message text.
     */
    fun sendMessage(text: String) {
        if (text.isBlank()) return
        controller.sendMessage(text)

        val message = ChatMessage(text = text, isSentByMe = true)
        val current = _messages.value.orEmpty().toMutableList()
        current.add(message)
        _messages.value = current
    }

    /**
     * Disconnects the active connection.
     *
     * @return `true` if a connection was active, `false` otherwise.
     */
    fun disconnect() = controller.disconnect()

    /**
     * Clears the message list (e.g., when starting a new chat session).
     */
    fun clearMessages() {
        _messages.value = emptyList()
    }

    /**
     * Sets the remote device name (e.g., when server accepts a connection).
     *
     * @param name The device name.
     */
    fun setRemoteDeviceName(name: String) {
        _remoteDeviceName.value = name
    }

    /**
     * Called when the ViewModel is no longer used and will be destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        controller.release()
    }
}
