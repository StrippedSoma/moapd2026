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
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import dk.itu.moapd.chatbluetooth.data.model.BluetoothDeviceItem
import dk.itu.moapd.chatbluetooth.data.model.ChatMessage
import dk.itu.moapd.chatbluetooth.data.model.ConnectionState

/**
 * Central controller for all Bluetooth operations in the application.
 *
 * This class acts as a facade that hides the complexity of the Android Bluetooth API behind a
 * simple, fragment-friendly interface. Fragments interact with this controller through the shared
 * [dk.itu.moapd.chatbluetooth.ui.main.BluetoothViewModel] rather than touching Bluetooth APIs
 * directly.
 *
 * ## Responsibilities
 * - Check Bluetooth availability and enabled state.
 * - Query paired devices.
 * - Start/stop device discovery.
 * - Manage server mode (accept incoming connections).
 * - Manage client mode (connect to a remote device).
 * - Read and write messages on the connected socket.
 * - Report [ConnectionState] changes and incoming [ChatMessage]s via callbacks.
 *
 * ## Threading Model
 * All socket I/O runs on plain background [Thread]s. Results are posted to the main thread via a
 * [Handler] so that LiveData/UI updates are safe.
 *
 * @param context Application context (used to obtain [BluetoothManager]).
 */
class BluetoothController(
    context: Context,
) {
    /**
     * A set of static attributes used in this class.
     */
    companion object {
        /**
         * Tag for logging.
         */
        private val TAG = BluetoothController::class.qualifiedName
    }

    // --------------------------------------------------------------------------------------------
    // Fields
    // ---------------------------------------------------------------------------------------------

    /**
     * The system [BluetoothAdapter]. Null only if the device has no Bluetooth hardware.
     *
     * We obtain it from [BluetoothManager], which is the recommended approach on modern Android
     * versions (the deprecated static `BluetoothAdapter.getDefaultAdapter()` still works but is not
     * recommended).
     */
    private val bluetoothAdapter: BluetoothAdapter? =
        (context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager)?.adapter

    /**
     * Handler to post results back to the main (UI) thread.
     */
    private val mainHandler = Handler(Looper.getMainLooper())

    /**
     * Background thread running the server accept-loop.
     */
    private var serverThread: BluetoothServerThread? = null

    /**
     * Background thread running the client connect operation.
     */
    private var clientThread: BluetoothClientThread? = null

    /**
     * Background thread managing the connected socket (read/write).
     */
    private var connectedThread: BluetoothConnectedThread? = null

    // ---------------------------------------------------------------------------------------------
    // Callbacks – set by the ViewModel
    // ---------------------------------------------------------------------------------------------

    /**
     * Called whenever the [ConnectionState] changes.
     */
    var onStateChanged: ((ConnectionState) -> Unit)? = null

    /**
     * Called when a new [ChatMessage] is received from the remote device.
     */
    var onMessageReceived: ((ChatMessage) -> Unit)? = null

    /**
     * Called when a new device is discovered during scanning.
     */
    var onDeviceDiscovered: ((BluetoothDeviceItem) -> Unit)? = null

    // ---------------------------------------------------------------------------------------------
    // Public API – Queries
    // ---------------------------------------------------------------------------------------------

    /**
     * Returns `true` if the device has Bluetooth hardware.
     */
    val isBluetoothSupported: Boolean
        get() = bluetoothAdapter != null

    /**
     * Returns `true` if Bluetooth is currently turned on.
     */
    val isBluetoothEnabled: Boolean
        get() = bluetoothAdapter?.isEnabled == true

    /**
     * Returns the list of devices that have been paired (bonded) with this device.
     *
     * Permission note: On Android 12+ this requires `BLUETOOTH_CONNECT` permission. The calling
     * fragment/activity must ensure the permission is granted before calling.
     */
    @SuppressLint("MissingPermission")
    fun getPairedDevices(): List<BluetoothDeviceItem> =
        bluetoothAdapter?.bondedDevices?.map { device ->
            BluetoothDeviceItem(
                name = device.name ?: "Unknown",
                address = device.address,
            )
        } ?: emptyList()

    // ---------------------------------------------------------------------------------------------
    // Public API – Discovery
    // ---------------------------------------------------------------------------------------------

    /**
     * Starts Bluetooth device discovery.
     *
     * Discovery is an asynchronous process: results arrive through `ACTION_FOUND` broadcast
     * intents. The [BluetoothDiscoveryReceiver] (registered by the fragment) forwards those results
     * to [onDeviceDiscovered].
     *
     * Important: Discovery is a heavyweight operation that significantly reduces the bandwidth of
     * an existing Bluetooth connection. Always call [stopDiscovery] before attempting to connect.
     */
    @SuppressLint("MissingPermission")
    fun startDiscovery() {
        // Cancel any ongoing discovery first.
        bluetoothAdapter?.cancelDiscovery()
        bluetoothAdapter?.startDiscovery()
        updateState(ConnectionState.DISCOVERING)
    }

    /**
     * Stops an ongoing Bluetooth discovery scan.
     */
    @SuppressLint("MissingPermission")
    fun stopDiscovery() {
        bluetoothAdapter?.cancelDiscovery()
        updateState(ConnectionState.IDLE)
    }

    // ---------------------------------------------------------------------------------------------
    // Public API – Server Mode
    // ---------------------------------------------------------------------------------------------

    /**
     * Starts the server: opens a listening RFCOMM Bluetooth server socket and waits for an incoming
     * connection on a background thread.
     *
     * When a client connects, the server thread hands the connected socket to
     * [manageConnectedSocket] which starts the read/write loop.
     */
    @SuppressLint("MissingPermission")
    fun startServer() {
        // Clean up any previous connections.
        stopAllThreads()

        val adapter = bluetoothAdapter ?: return
        updateState(ConnectionState.LISTENING)

        serverThread =
            BluetoothServerThread(adapter, mainHandler) { socket ->
                // A client connected! Transition to the connected state.
                manageConnectedSocket(socket)
            }.also { it.start() }
    }

    // ---------------------------------------------------------------------------------------------
    // Public API – Client Mode
    // ---------------------------------------------------------------------------------------------

    /**
     * Connects to a remote device as a client.
     *
     * The connection is performed on a background thread. On success, the connected socket is
     * handed to [manageConnectedSocket].
     *
     * @param address The MAC address of the remote device to connect to.
     */
    @SuppressLint("MissingPermission")
    fun connectToDevice(address: String) {
        stopAllThreads()

        val adapter = bluetoothAdapter ?: return
        val device: BluetoothDevice = adapter.getRemoteDevice(address)

        // Always cancel discovery before connecting – discovery slows down the connection.
        adapter.cancelDiscovery()

        updateState(ConnectionState.CONNECTING)

        clientThread =
            BluetoothClientThread(device, mainHandler) { socket ->
                if (socket != null) {
                    manageConnectedSocket(socket)
                } else {
                    updateState(ConnectionState.CONNECTION_FAILED)
                }
            }.also { it.start() }
    }

    // ---------------------------------------------------------------------------------------------
    // Public API – Messaging
    // ---------------------------------------------------------------------------------------------

    /**
     * Sends a text message to the connected remote device.
     *
     * The write operation happens on the connected thread's background context.
     *
     * @param text The message text to send.
     */
    fun sendMessage(text: String) {
        // Append a newline as a simple message delimiter so the receiver can read complete messages.
        connectedThread?.write((text + "\n").toByteArray(Charsets.UTF_8))
    }

    // ---------------------------------------------------------------------------------------------
    // Public API – Lifecycle
    // ---------------------------------------------------------------------------------------------

    /**
     * Disconnects any active connection and releases all resources.
     *
     * Call this when the user presses "Disconnect" or when the hosting Activity/Fragment is
     * destroyed.
     */
    fun disconnect() {
        stopAllThreads()
        updateState(ConnectionState.DISCONNECTED)
    }

    /**
     * Releases all resources. Call from the ViewModel's `onCleared()`.
     */
    fun release() {
        stopAllThreads()
        onStateChanged = null
        onMessageReceived = null
        onDeviceDiscovered = null
    }

    // ---------------------------------------------------------------------------------------------
    // Private helpers
    // ---------------------------------------------------------------------------------------------

    /**
     * Manages a successfully connected [BluetoothSocket].
     *
     * Starts a [BluetoothConnectedThread] that continuously reads incoming data and provides a
     * write method for outgoing data.
     */
    private fun manageConnectedSocket(socket: BluetoothSocket) {
        // Stop any existing connected thread.
        connectedThread?.cancel()

        updateState(ConnectionState.CONNECTED)

        // Buffer used to accumulate incoming bytes until full messages (newline-delimited) are available.
        val incomingBuffer = StringBuilder()

        connectedThread =
            BluetoothConnectedThread(
                socket,
                mainHandler,
                onRead = { bytes ->
                    // Decode the raw bytes into text using UTF-8 and append to the buffer.
                    val chunk = String(bytes, Charsets.UTF_8)
                    if (chunk.isEmpty()) {
                        return@onRead
                    }
                    incomingBuffer.append(chunk)

                    // Extract and dispatch complete newline-delimited messages.
                    var newlineIndex = incomingBuffer.indexOf("\n")
                    while (newlineIndex >= 0) {
                        val line = incomingBuffer.substring(0, newlineIndex).trim()
                        if (line.isNotEmpty()) {
                            val message =
                                ChatMessage(
                                    text = line,
                                    isSentByMe = false,
                                )
                            mainHandler.post { onMessageReceived?.invoke(message) }
                        }
                        // Remove the processed message (including the newline) from the buffer.
                        incomingBuffer.delete(0, newlineIndex + 1)
                        newlineIndex = incomingBuffer.indexOf("\n")
                    }
                },
                onDisconnected = {
                    mainHandler.post { updateState(ConnectionState.DISCONNECTED) }
                },
            ).also { it.start() }
    }

    /**
     * Cancels and nullifies all background threads.
     */
    private fun stopAllThreads() {
        serverThread?.cancel()
        serverThread = null
        clientThread?.cancel()
        clientThread = null
        connectedThread?.cancel()
        connectedThread = null
    }

    /**
     * Convenience to post a state change on the main thread.
     *
     * @param state The new connection state.
     */
    private fun updateState(state: ConnectionState) {
        Log.d(TAG, "Connection state -> $state")
        if (Looper.myLooper() == Looper.getMainLooper()) {
            onStateChanged?.invoke(state)
        } else {
            mainHandler.post { onStateChanged?.invoke(state) }
        }
    }
}
