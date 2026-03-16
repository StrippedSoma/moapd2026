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
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.util.Log
import dk.itu.moapd.chatbluetooth.util.Constants
import java.io.IOException

/**
 * Background thread that acts as a Bluetooth server.
 *
 * ## How It Works
 * 1. Opens a listening RFCOMM server socket using the app's UUID.
 * 2. Blocks on [BluetoothServerSocket.accept] until a client connects.
 * 3. When a client connects, invokes the [onConnected] callback with the resulting
 *      [BluetoothSocket], then closes the server socket (we only allow one connection).
 *
 * ## Important
 * - `accept()` is a blocking call, so it must run on a background thread.
 * - The server socket should be closed when no longer needed to free system resources.
 *
 * @param adapter The system [BluetoothAdapter].
 * @param handler Handler for the main thread (used by the callback if needed).
 * @param onConnected Callback invoked (on this background thread) when a client connects.
 */
class BluetoothServerThread(
    adapter: BluetoothAdapter,
    private val handler: Handler,
    private val onConnected: (BluetoothSocket) -> Unit,
) : Thread("BT-ServerThread") {
    /**
     * A set of static attributes used in this class.
     */
    companion object {
        /**
         * Tag for logging.
         */
        private val TAG = BluetoothServerThread::class.qualifiedName
    }

    /**
     * The listening server socket. Created once in the constructor.
     *
     * `listenUsingRfcommWithServiceRecord` opens a server socket that remote devices can find using
     * the same UUID. The first argument is a human-readable service name that appears in the
     * device's SDP (Service Discovery Protocol) records.
     */
    @SuppressLint("MissingPermission")
    private val serverSocket: BluetoothServerSocket? =
        try {
            adapter.listenUsingRfcommWithServiceRecord(
                Constants.SERVICE_NAME,
                Constants.APP_UUID,
            )
        } catch (e: IOException) {
            Log.e(TAG, "Server socket creation failed", e)
            null
        }

    /**
     * Continuously listens for incoming connections.
     */
    override fun run() {
        Log.d(TAG, "Server: waiting for connection…")

        var socket: BluetoothSocket?
        try {
            // Block until a connection is accepted or the socket is closed.
            socket = serverSocket?.accept()

            // If we got a connection, notify the controller (we only support a single connected
            // device at a time).
            socket?.let {
                Log.d(TAG, "Server: connection accepted!")
                handler.post { onConnected(it) }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Server: accept() failed", e)
        } finally {
            // Always close the server socket — we don't need to accept more connections, and this
            // ensures the listening socket is not leaked if accept() fails.
            try {
                serverSocket?.close()
            } catch (e: IOException) {
                Log.e(TAG, "Server: could not close server socket", e)
            }
        }
    }

    /**
     * Cancels the server by closing the server socket.
     *
     * This will cause `accept()` to throw an [IOException], ending the blocking call in [run] and
     * allowing the thread to finish.
     */
    fun cancel() {
        try {
            serverSocket?.close()
        } catch (e: IOException) {
            Log.e(TAG, "Server: could not close server socket", e)
        }
    }
}
