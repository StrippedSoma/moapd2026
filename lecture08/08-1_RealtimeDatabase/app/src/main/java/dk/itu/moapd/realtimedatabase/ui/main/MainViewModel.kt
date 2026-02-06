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
package dk.itu.moapd.realtimedatabase.ui.main

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dk.itu.moapd.realtimedatabase.data.repository.DummyRepository
import dk.itu.moapd.realtimedatabase.domain.model.Dummy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * Data class representing a dummy UI element.
 *
 * @property key The unique identifier of the dummy.
 * @property name The name of the dummy.
 * @property createdAt The creation time of the dummy.
 */
data class DummyUi(
    val key: String,
    val name: String,
    val createdAt: Long?,
)

/**
 * A view model sensitive to changes in the `MainActivity` UI components.
 */
class MainViewModel(
    private val repository: DummyRepository = DummyRepository(),
) : ViewModel() {

    /**
     * The current state of all UI components shown in the main activity.
     */
    private val _uiState = MutableStateFlow(MainUiState(userId = repository.currentUserId()))

    /**
     * A `StateFlow` which publicly exposes any update in the UI components.
     */
    val uiState: StateFlow<MainUiState> = _uiState

    /**
     * The listener for Firebase Realtime Database.
     */
    private var listener: ValueEventListener? = null

    /**
     * Initialize the view model.
     */
    init {
        // Start observing as soon as we have a user.
        observeDummies()
    }

    /**
     * Observe the changes in the Firebase Realtime Database.
     */
    private fun observeDummies() {

        // Get the current user ID.
        val userId = repository.currentUserId() ?: return
        _uiState.update { it.copy(userId = userId) }

        // Create a query to retrieve all dummies for the current user.
        val query = repository.dummiesQuery(userId)

        // Create a listener to receive events from the database.
        val valueListener = object : ValueEventListener {

            /**
             * This method will be called with a snapshot of the data at this location. It will also
             * be called each time that data changes.
             *
             * @param snapshot The current data at the location
             */
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.mapNotNull { child ->
                    val key = child.key ?: return@mapNotNull null
                    val dummy = child.getValue(Dummy::class.java) ?: return@mapNotNull null
                    DummyUi(
                        key = key,
                        name = dummy.name,
                        createdAt = dummy.createdAt,
                    )
                }.sortedBy { it.createdAt ?: Long.MIN_VALUE }
                _uiState.update { it.copy(dummies = items) }
            }

            /**
             * This method will be triggered in the event that this listener either failed at the
             * server, or is removed as a result of the security and Firebase Database rules. For
             * more information on securing your data, see: <a
             * href="https://firebase.google.com/docs/database/security/quickstart"
             * target="_blank"> Security Quickstart</a>
             *
             * @param error A description of the error that occurred
             */
            override fun onCancelled(error: DatabaseError) {
                // Keep previous state; errors will be handled by Firebase SDK logs.
            }
        }

        // Update the listener and add it to the query.
        listener = valueListener
        query.addValueEventListener(valueListener)
    }

    /**
     * Called when the ViewModel is no longer used and will be destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        val userId = repository.currentUserId()
        val l = listener
        if (userId != null && l != null) {
            repository.dummiesQuery(userId).removeEventListener(l)
        }
    }

    /**
     * Inserts a dummy into the database.
     *
     * @param name The name of the dummy.
     */
    fun insertDummy(name: String) {
        val userId = repository.currentUserId() ?: return
        repository.insertDummy(userId = userId, name = name)
    }

    /**
     * Updates an existing dummy in the database.
     *
     * @param key The key of the dummy to update.
     */
    fun updateDummy(key: String, name: String, createdAt: Long?) {
        val userId = repository.currentUserId() ?: return
        repository.updateDummy(userId = userId, key = key, name = name, createdAt = createdAt)
    }

    /**
     * Deletes a dummy from the database.
     *
     * @param key The key of the dummy to delete.
     */
    fun deleteDummy(key: String) {
        val userId = repository.currentUserId() ?: return
        repository.deleteDummy(userId = userId, key = key)
    }
}
