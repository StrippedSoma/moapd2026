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
package dk.itu.moapd.lifecycle.presentation.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dk.itu.moapd.lifecycle.R
import dk.itu.moapd.lifecycle.databinding.ActivityMainBinding
import androidx.core.content.edit

/**
 * An activity class with methods to manage the main activity of Life Cycle application.
 */
class MainActivity : AppCompatActivity() {
    /**
     * A set of private constants used in this class.
     */
    companion object {
        private const val KEY_LAST_MESSAGE = "key_last_message"
        private const val KEY_LAST_CHECKED = "key_last_checked"
    }

    /**
     * View binding is a feature that allows you to more easily write code that interacts with
     * views. Once view binding is enabled in a module, it generates a binding class for each XML
     * layout file present in that module. An instance of a binding class contains direct references
     * to all views that have an ID in the corresponding layout.
     */
    private lateinit var binding: ActivityMainBinding

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
     * (`onStart()`, `onResume()`, `onPause()`, etc) executing.
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

        // Migrate from Kotlin synthetics to Jetpack view binding.
        // https://developer.android.com/topic/libraries/view-binding/migration
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle window insets to support edge-to-edge content.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up the UI components and retrieve shared preferences data.
        setupUI()
        retrieveSharedPreferences()
    }

    /**
     * Sets up the user interface components by attaching click listeners to the `buttonTrue`,
     * `buttonFalse`, and `checkBoxSelect`. When the `buttonTrue` is clicked, it updates the text in the
     * `ViewModel` with the string resource associated with `R.string.true_text`. When the
     * `buttonFalse` is clicked, it updates the text in the `ViewModel` with the string resource
     * associated with `R.string.false_text`. When the `checkBoxSelect` is clicked, it toggles its checked
     * state in the `ViewModel` and updates the text accordingly, displaying either "checked" or
     * "unchecked" depending on the current state of the `checkBoxSelect`.
     */
    private fun setupUI() =
        with(binding.contentMain) {
            buttonTrue.setOnClickListener {
                textViewMessage.text = getString(R.string.true_text)
                saveSharedPreferences()
            }

            buttonFalse.setOnClickListener {
                textViewMessage.text = getString(R.string.false_text)
                saveSharedPreferences()
            }

            checkBoxSelect.setOnCheckedChangeListener { buttonView, isChecked ->
                if (!buttonView.isPressed) return@setOnCheckedChangeListener
                val textId = if (isChecked) R.string.checked_text else R.string.unchecked_text
                textViewMessage.text = resources.getString(textId)
                saveSharedPreferences()
            }
        }

    /**
     * Saves the status of UI components to SharedPreferences. It extracts the status of a checkbox
     * and the text from a text view in the `contentMain` layout, and then saves them to
     * SharedPreferences.
     */
    private fun saveSharedPreferences() {
        binding.contentMain.run {
            val isChecked = checkBoxSelect.isChecked
            val text = textViewMessage.text.toString()

            preferences.edit {
                putString(KEY_LAST_MESSAGE, text)
                putBoolean(KEY_LAST_CHECKED, isChecked)
            }
        }
    }

    /**
     * Retrieves the saved preferences from SharedPreferences and updates the UI components
     * accordingly. The checkbox status and text view content are retrieved from SharedPreferences
     * and applied to the UI.
     */
    private fun retrieveSharedPreferences() {
        // Set the UI components status.
        binding.contentMain.apply {
            textViewMessage.text = preferences.getString(
                KEY_LAST_MESSAGE,
                getString(R.string.initial_text)
            )
            checkBoxSelect.isChecked = preferences.getBoolean(
                KEY_LAST_CHECKED,
                false
            )
        }
    }
}
