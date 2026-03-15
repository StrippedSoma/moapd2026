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
package dk.itu.moapd.chatbluetooth.data.model

/**
 * Represents the current state of the Bluetooth connection.
 *
 * The UI observes this state (via LiveData) to update status indicators, enable/disable buttons,
 * and navigate between fragments as needed.
 *
 * State transitions typically follow this pattern:
 * ```
 * IDLE -> LISTENING (server) or CONNECTING (client)
 *      -> CONNECTED -> DISCONNECTED
 *      -> CONNECTION_FAILED
 * ```
 */
enum class ConnectionState {
    /**
     * No active connection or connection attempt.
     */
    IDLE,

    /**
     * The device is currently scanning for nearby Bluetooth devices.
     */
    DISCOVERING,

    /**
     * The server socket is listening for incoming connections.
     */
    LISTENING,

    /**
     * A connection attempt (client-side) is in progress.
     */
    CONNECTING,

    /**
     * A Bluetooth connection has been established and the chat is active.
     */
    CONNECTED,

    /**
     * A connection attempt failed (timeout, refusal, I/O error).
     */
    CONNECTION_FAILED,

    /**
     * A previously active connection was closed.
     */
    DISCONNECTED,
}
