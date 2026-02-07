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

import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.database
import dk.itu.moapd.firebasestorage.core.FirebaseConfig.DATABASE_URL
import dk.itu.moapd.firebasestorage.domain.model.Image

/**
 * Repository that centralizes Realtime Database operations related to images.
 *
 * Exposes Task-based APIs so callers can attach listeners as needed.
 *
 * @property auth The Firebase Authentication SDK instance.
 * @property root The root reference of the Realtime Database.
 * @property storageRepository The repository for handling Firebase Storage operations.
 */
class ImageRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val root: DatabaseReference = Firebase.database(DATABASE_URL).reference,
    private val storageRepository: StorageRepository = StorageRepository(),
) {
    /**
     * A set of private constants used in this class.
     */
    companion object {
        /**
         * The path to the "images" node in the Realtime Database.
         */
        private const val PATH_IMAGES = "images"

        /**
         * The child key for the creation timestamp in the Realtime Database.
         */
        private const val CHILD_CREATED_AT = "createdAt"
    }

    /**
     * Returns the current user's UID.
     *
     * @return The UID of the current user, or null if the user is not authenticated.
     */
    fun currentUserId(): String? = auth.currentUser?.uid

    /**
     * Returns a Query that retrieves all images associated with the current user.
     *
     * @param userId The UID of the user for whom to retrieve images.
     *
     * @return A Query that retrieves all images associated with the specified user.
     */
    fun imagesQuery(userId: String): Query = root
        .child(PATH_IMAGES)
        .child(userId)
        .orderByChild(CHILD_CREATED_AT)

    /**
     * Saves an image entry to the database.
     *
     * @param url The URL of the image.
     * @param path The path of the image in Firebase Storage.
     *
     * @return A Task that completes successfully when the image entry is saved to the database.
     */
    fun saveImage(url: String, path: String): Task<Void>? {
        val userId = currentUserId() ?: return null
        val timestamp = System.currentTimeMillis()
        val image = Image(url = url, path = path, createdAt = timestamp)

        val key = root.child(PATH_IMAGES)
            .child(userId)
            .push()
            .key ?: return null

        return root.child(PATH_IMAGES)
            .child(userId)
            .child(key)
            .setValue(image)
    }

    /**
     * Deletes an image from storage and, on success, deletes the database entry.
     * Returns a Task that represents the whole operation: it completes successfully only after the
     * database deletion completes. If the storage deletion fails, the returned Task fails and the
     * database deletion will not be attempted, allowing the operation to be retried.
     *
     * @param key The unique key of the image entry.
     * @param path The path of the image file in Firebase Storage.
     *
     * @return A Task that represents the whole operation: it completes successfully only after the
     *      database deletion completes.
     */
    fun deleteImage(key: String, path: String): Task<Void>? {
        val userId = currentUserId() ?: return null

        // First, delete the file from storage.
        val storageTask = storageRepository.delete(path)

        // Chain the storage deletion to the database deletion: on storage success, delete from DB.
        return storageTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                throw (task.exception ?: Exception("Storage deletion failed"))
            }
            // Proceed to delete the database entry and return that Task so callers can observe it.
            root.child(PATH_IMAGES)
                .child(userId)
                .child(key)
                .removeValue()
        }
    }
}
