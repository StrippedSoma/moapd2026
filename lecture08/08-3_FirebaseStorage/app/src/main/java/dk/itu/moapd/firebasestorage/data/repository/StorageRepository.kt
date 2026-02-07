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
package dk.itu.moapd.firebasestorage.data.repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import dk.itu.moapd.firebasestorage.core.FirebaseConfig.BUCKET_URL

/**
 * Handles Firebase Storage operations.
 *
 * Exposes Task-based APIs so callers can attach listeners as needed.
 *
 * @property storage The Firebase Storage instance.
 */
class StorageRepository(
    bucketUrl: String = BUCKET_URL,
) {

    /**
     * Firebase Storage instance.
     */
    private val storage = Firebase.storage(bucketUrl)

    /**
     * Uploads a local file (identified by [localUri]) to [remotePath] in the storage bucket and
     * returns a Task that resolves with the public download Uri of the uploaded file.
     *
     * @param localUri The Uri of the local file to upload.
     * @param remotePath The path in the storage bucket where the file will be uploaded.
     *
     * @return A Task that resolves with the public download Uri of the uploaded file.
     */
    fun uploadFile(localUri: Uri, remotePath: String): Task<Uri> {
        val ref: StorageReference = storage.reference.child(remotePath)
        // Put the file and then request the downloadUrl.
        return ref.putFile(localUri).continueWithTask { task ->
            if (!task.isSuccessful) {
                throw (task.exception ?: Exception("Upload failed"))
            }
            ref.downloadUrl
        }
    }

    /**
     * Deletes the file at [remotePath] in the storage bucket.
     *
     * @param remotePath The path in the storage bucket where the file to delete is located.
     *
     * @return A Task that completes successfully when the file is deleted.
     */
    fun delete(remotePath: String): Task<Void> {
        return storage.reference.child(remotePath).delete()
    }
}
