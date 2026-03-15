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

import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.util.Log
import dk.itu.moapd.chatbluetooth.util.Constants
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/**
 * Background thread that manages a connected Bluetooth socket.
 *
 * ## How It Works
 * Once a connection is established (by either the server or client thread), this thread takes
 * ownership of the [BluetoothSocket] and continuously reads incoming data from the [InputStream].
 * Outgoing data is written via the [write] method.
 *
 * ## Threading
 * - Reading happens in the `run()` loop on this background thread.
 * - Writing also happens on this background thread via [write] (the write is fast enough that
 *      blocking the caller briefly is acceptable for this educational app).
 * - Results are posted to the main thread via [handler].
 *
 * @param socket The connected Bluetooth socket.
 * @param handler Handler for the main thread.
 * @param onRead Callback invoked (on the main thread) when data is received.
 * @param onDisconnected Callback invoked when the connection drops.
 */
class BluetoothConnectedThread(
    private val socket: BluetoothSocket,
    private val handler: Handler,
    private val onRead: (ByteArray) -> Unit,
    private val onDisconnected: () -> Unit,
) : Thread("BT-ConnectedThread") {
    /**
     * A set of static attributes used in this class.
     */
    companion object {
        /**
         * Tag for logging.
         */
        private val TAG = BluetoothConnectedThread::class.qualifiedName
    }

    /**
     * The input and output streams for the connected socket.
     */
    private val inputStream: InputStream? = socket.inputStream

    /**
     * The output stream for the connected socket.
     */
    private val outputStream: OutputStream? = socket.outputStream

    /**
     * Reads data from the remote device and invokes the [onRead] callback.
     */
    override fun run() {
        val buffer = ByteArray(Constants.BUFFER_SIZE)

        Log.d(TAG, "Connected thread: reading…")

        // Continuously read from the InputStream until an exception occurs.
        while (true) {
            try {
                // read() blocks until data is available or the stream is closed.
                val bytesRead = inputStream?.read(buffer) ?: break

                if (bytesRead > 0) {
                    // Copy only the bytes that were actually read.
                    val received = buffer.copyOf(bytesRead)
                    handler.post { onRead(received) }
                }
            } catch (e: IOException) {
                // The connection was lost (remote device disconnected or socket closed).
                Log.d(TAG, "Connected thread: connection lost", e)
                handler.post { onDisconnected() }
                break
            }
        }
    }

    /**
     * Writes data to the remote device.
     *
     * This method can be called from any thread. The [OutputStream.write] call is synchronous but
     * typically fast for small payloads like chat messages.
     *
     * @param bytes The data to send.
     */
    fun write(bytes: ByteArray) {
        try {
            outputStream?.write(bytes)
            outputStream?.flush()
        } catch (e: IOException) {
            Log.e(TAG, "Connected thread: write failed", e)
            handler.post { onDisconnected() }
        }
    }

    /**
     * Closes the connected socket, which terminates the read loop in [run].
     */
    fun cancel() {
        try {
            socket.close()
        } catch (e: IOException) {
            Log.e(TAG, "Connected thread: could not close socket", e)
        }
    }
}
