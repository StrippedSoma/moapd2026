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
package dk.itu.moapd.opencv.media.capture

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Utility responsible for saving a photo (Bitmap) into MediaStore (DCIM).
 *
 * This version does NOT depend on CameraX. It simply stores the provided [Bitmap].
 */
object PhotoCaptureManager {

    /**
     * Photo filename format.
     */
    private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"

    /**
     * MIME type for JPEG images.
     */
    private const val MIME_TYPE_JPEG = "image/jpeg"

    /**
     * Saves [bitmap] into MediaStore (DCIM).
     *
     * @param contentResolver ContentResolver used to write to MediaStore.
     * @param bitmap The image to be saved.
     * @param onSaved Called when image is saved, providing the [Uri] (may be null on some devices)
     *      and the created filename.
     * @param onError Called when save fails.
     */
    fun saveJpeg(
        contentResolver: ContentResolver,
        bitmap: Bitmap,
        onSaved: (savedUri: Uri?, filename: String) -> Unit,
        onError: (message: String, throwable: Throwable?) -> Unit,
    ) {
        val timestamp = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())
        val filename = "IMG_$timestamp.jpg"

        val contentValues = buildContentValues(filename)

        val uri = try {
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        } catch (t: Throwable) {
            onError("Failed to create MediaStore entry: ${t.message ?: t}", t)
            return
        }

        if (uri == null) {
            onError("Failed to create MediaStore entry (null Uri).", null)
            return
        }

        var out: OutputStream? = null
        try {
            out = contentResolver.openOutputStream(uri)
            if (out == null) {
                onError("Failed to open output stream.", null)
                return
            }
            val ok = bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
            if (!ok) {
                onError("Bitmap compression failed.", null)
                return
            }
            onSaved(uri, filename)
        } catch (t: Throwable) {
            onError("Failed to save image: ${t.message ?: t}", t)
        } finally {
            try { out?.close() } catch (_: Throwable) {}
        }
    }

    /**
     * Build content values used to store photos using [MediaStore].
     *
     * @param filename The output filename.
     */
    private fun buildContentValues(filename: String): ContentValues {
        return ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, MIME_TYPE_JPEG)
            put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
        }
    }
}
