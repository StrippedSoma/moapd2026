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
package dk.itu.moapd.lifecycle.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.edit
import dk.itu.moapd.lifecycle.R
import dk.itu.moapd.lifecycle.ui.theme.LifeCycleTheme

/**
 * An activity class with methods to manage the main activity of Life Cycle application.
 */
class MainActivity : ComponentActivity() {
    /**
     * A set of private constants used in this class.
     */
    companion object {
        private const val KEY_LAST_TEXT_ID = "key_last_text_id"
        private const val KEY_LAST_CHECKED = "key_last_checked"
    }

    /**
     * An object to access and modify the shared preference application data.
     */
    private val preferences: SharedPreferences by lazy { getPreferences(Context.MODE_PRIVATE) }

    /**
     * Called when the activity is starting. This is where most initialization should go: calling
     * `setContentView(int)` to inflate the activity's UI, using `findViewById()` to
     * programmatically interact with widgets in the UI, calling
     * `managedQuery(android.net.Uri, String[], String, String[], String)` to retrieve cursors for
     * data being displayed, etc.
     *
     * You can call `finish()` from within this function, in which case `onDestroy()` will be
     * immediately called after `onCreate()` without any of the rest of the activity lifecycle
     * (`onStart()`, `onResume()`, onPause()`, etc) executing.
     *
     * <em>Derived classes must call through to the super class's implementation of this method. If
     * they do not, an exception will be thrown.</em>
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut
     * down then this Bundle contains the data it most recently supplied in `onSaveInstanceState()`.
     * <b><i>Note: Otherwise it is null.</i></b>
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val uiState = retrieveSharedPreferences()

        setContent {
            LifeCycleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        initialTextId = uiState.textId,
                        initialChecked = uiState.checked,
                        onPersist = ::saveSharedPreferences
                    )
                }
            }
        }
    }

    /**
     * Saves the status of UI components to SharedPreferences. It extracts the status of a checkbox
     * and the text from a text view in the `contentMain` layout, and then saves them to
     * SharedPreferences.
     */
    private fun saveSharedPreferences(textId: Int, checked: Boolean) {
        preferences.edit {
            putInt(KEY_LAST_TEXT_ID, textId)
            putBoolean(KEY_LAST_CHECKED, checked)
        }
    }

    /**
     * Retrieves the saved preferences from SharedPreferences and updates the UI components
     * accordingly. The checkbox status and text view content are retrieved from SharedPreferences
     * and applied to the UI.
     *
     * @return A [MainUiState] object containing the retrieved preferences.
     */
    private fun retrieveSharedPreferences(): MainUiState {
        val textId = preferences.getInt(KEY_LAST_TEXT_ID, R.string.initial_text)
        val checked = preferences.getBoolean(KEY_LAST_CHECKED, false)
        return MainUiState(textId = textId, checked = checked)
    }
}