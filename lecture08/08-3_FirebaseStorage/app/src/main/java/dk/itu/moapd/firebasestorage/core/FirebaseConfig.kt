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
package dk.itu.moapd.firebasestorage.core

import io.github.cdimascio.dotenv.dotenv

/**
 * Centralized access to Firebase configuration values loaded from `/app/src/main/assets/env`.
 */
object FirebaseConfig {

    private val env = dotenv {
        directory = "/assets"
        filename = "env"
    }

    /**
     * Firebase Realtime Database URL.
     *
     * IMPORTANT: This is sensitive information and should not be hardcoded.
     * Create an `env` file in `/app/src/main/assets` with:
     * DATABASE_URL=https://xxxxxxxxxx-default-rtdb.europe-west1.firebasedatabase.app
     */
    val DATABASE_URL: String = env["DATABASE_URL"]

    /**
     * Firebase Cloud Storage bucket URL.
     *
     * IMPORTANT: This is sensitive information and should not be hardcoded.
     * Create an `env` file in `/app/src/main/assets` with:
     * BUCKET_URL=gs://xxxxxxxxxx.firebasestorage.app
     */
    val BUCKET_URL: String = env["BUCKET_URL"]
}
