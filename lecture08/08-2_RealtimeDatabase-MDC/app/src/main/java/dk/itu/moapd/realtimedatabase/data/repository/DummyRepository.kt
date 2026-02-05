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
package dk.itu.moapd.realtimedatabase.data.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.database
import dk.itu.moapd.realtimedatabase.core.DATABASE_URL
import dk.itu.moapd.realtimedatabase.domain.model.Dummy

class DummyRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val root: DatabaseReference = Firebase.database(DATABASE_URL).reference,
) {
    /**
     * A set of private constants used in this class.
     */
    companion object {
        /**
         * The path to the "dummies" node in the database.
         */
        private const val PATH_DUMMIES = "dummies"

        /**
         * The child key for the "createdAt" field in the database.
         */
        private const val CHILD_CREATED_AT = "createdAt"
    }

    /**
     * Returns the current user's ID.
     *
     * @return The current user's ID, or null if the user is not authenticated.
     */
    fun currentUserId(): String? = auth.currentUser?.uid

    /**
     * Returns a query to retrieve all dummies for the current user.
     *
     * @param userId The ID of the user for whom to retrieve the dummies.
     *
     * @return A query to retrieve all dummies for the current user.
     */
    fun dummiesQuery(userId: String): Query = root
        .child(PATH_DUMMIES)
        .child(userId)
        .orderByChild(CHILD_CREATED_AT)

    /**
     * Adds a new dummy to the database.
     *
     * @param userId The ID of the user who added the dummy.
     * @param name The name of the dummy.
     * @param now The current time in milliseconds.
     */
    fun addDummy(userId: String, name: String, now: Long = System.currentTimeMillis()) {
        val key = root
            .child(PATH_DUMMIES)
            .child(userId)
            .push()
            .key ?: return
        val dummy = Dummy(name = name, createdAt = now, updatedAt = now)
        root
            .child(PATH_DUMMIES)
            .child(userId)
            .child(key)
            .setValue(dummy)
    }

    /**
     * Updates an existing dummy in the database.
     *
     * @param userId The ID of the user who updated the dummy.
     * @param key The key of the dummy to update.
     * @param name The new name of the dummy.
     * @param createdAt The original creation time of the dummy.
     * @param now The current time in milliseconds.
     */
    fun updateDummy(userId: String, key: String, name: String, createdAt: Long?, now: Long = System.currentTimeMillis()) {
        val dummy = Dummy(name = name, createdAt = createdAt, updatedAt = now)
        root
            .child(PATH_DUMMIES)
            .child(userId)
            .child(key)
            .setValue(dummy)
    }

    /**
     * Deletes a dummy from the database.
     *
     * @param userId The ID of the user who deleted the dummy.
     * @param key The key of the dummy to delete.
     */
    fun deleteDummy(userId: String, key: String) {
        root
            .child(PATH_DUMMIES)
            .child(userId)
            .child(key)
            .removeValue()
    }
}
