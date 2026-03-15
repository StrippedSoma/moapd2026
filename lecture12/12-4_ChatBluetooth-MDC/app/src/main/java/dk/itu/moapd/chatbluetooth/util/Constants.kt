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
package dk.itu.moapd.chatbluetooth.util

import java.util.UUID

/**
 * Application-wide constants for the Bluetooth Chat app.
 *
 * ## About the UUID
 * Bluetooth RFCOMM connections require both devices to agree on a UUID that identifies the service.
 * The UUID below is the standard Serial Port Profile (SPP) UUID. Both the server and client must
 * use the same UUID to establish a connection.
 *
 * For a production app, you would generate your own unique UUID. For this educational project, the
 * SPP UUID works well for simple serial-style communication.
 */
object Constants {
    /**
     * Human-readable service name used when creating the Bluetooth server socket.
     */
    const val SERVICE_NAME = "ChatBluetoothService"

    /**
     * UUID for the RFCOMM Bluetooth socket.
     *
     * This is a custom UUID for our app. Both devices (server and client) must use the same UUID to
     * connect. We use a randomly generated UUID rather than the standard SPP UUID so that our app
     * only connects to other instances of itself.
     */
    val APP_UUID: UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66")

    /**
     * Buffer size for reading bytes from the Bluetooth socket input stream.
     */
    const val BUFFER_SIZE = 1024
}
