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
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.util.Log
import dk.itu.moapd.chatbluetooth.util.Constants
import java.io.IOException

/**
 * Background thread that acts as a Bluetooth client.
 *
 * ## How It Works
 * 1. Creates an RFCOMM [BluetoothSocket] to the remote [device] using the app's UUID.
 * 2. Calls [BluetoothSocket.connect], which blocks until the connection is established or fails.
 * 3. Invokes the [onResult] callback with the connected socket (or `null` on failure).
 *
 * ## Important
 * - Always cancel device discovery before calling `connect()`. Discovery significantly slows down
 *      the connection process.
 * - `connect()` is a blocking call that can take up to ~12 seconds before timing out.
 *
 * @param device The remote Bluetooth device to connect to.
 * @param handler Handler for the main thread.
 * @param onResult Callback with the connected socket, or `null` if the connection failed.
 */
class BluetoothClientThread(
    private val device: BluetoothDevice,
    private val handler: Handler,
    private val onResult: (BluetoothSocket?) -> Unit,
) : Thread("BT-ClientThread") {
    /**
     * A set of static attributes used in this class.
     */
    companion object {
        /**
         * Tag for logging.
         */
        private val TAG = BluetoothClientThread::class.qualifiedName
    }

    /**
     * The client socket. Created from the remote device's address and our app UUID.
     *
     * `createRfcommSocketToServiceRecord` creates a socket that will connect to the remote device's
     * server socket listening on the same UUID.
     */
    @SuppressLint("MissingPermission")
    private val socket: BluetoothSocket? =
        try {
            device.createRfcommSocketToServiceRecord(Constants.APP_UUID)
        } catch (e: IOException) {
            Log.e(TAG, "Client: socket creation failed", e)
            null
        }

    /**
     * Connects to the remote device and invokes the [onResult] callback.
     */
    @SuppressLint("MissingPermission")
    override fun run() {
        Log.d(TAG, "Client: connecting to ${device.address}.")

        socket?.let { sock ->
            try {
                // This call blocks until it connects or throws an exception.
                sock.connect()
                Log.d(TAG, "Client: connected!")
                handler.post { onResult(sock) }
            } catch (e: IOException) {
                Log.e(TAG, "Client: connection failed", e)
                // Close the socket on failure to free resources.
                try {
                    sock.close()
                } catch (closeException: IOException) {
                    Log.e(TAG, "Client: could not close socket", closeException)
                }
                handler.post { onResult(null) }
            }
        } ?: handler.post { onResult(null) }
    }

    /**
     * Cancels the connection attempt by closing the socket.
     */
    fun cancel() {
        try {
            socket?.close()
        } catch (e: IOException) {
            Log.e(TAG, "Client: could not close socket", e)
        }
    }
}
