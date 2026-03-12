/*
 * MIT License
 *
 * Copyright (c) 2026 Elizabete Munzlinger and Fabricio Batista Narcizo
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
package dk.itu.moapd.infertflite.ui.main

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import dk.itu.moapd.infertflite.R
import dk.itu.moapd.infertflite.databinding.ActivityMainBinding

/**
 * An activity class with several methods to manage the main activity of InferTFLite application.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    /**
     * View binding is a feature that allows you to more easily write code that interacts with
     * views. Once view binding is enabled in a module, it generates a binding class for each XML
     * layout file present in that module. An instance of a binding class contains direct references
     * to all views that have an ID in the corresponding layout.
     */
    private lateinit var binding: ActivityMainBinding

    /**
     * This object launches a new activity and receives back some result data.
     */
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            if (!isGranted) {
                // PERMISSION DENIED: End the Activity.
                finish()
            }
        }

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

        // Migrate from Kotlin synthetics to Jetpack view binding.
        // https://developer.android.com/topic/libraries/view-binding/migration
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!checkPermission()) {
            requestCameraPermission()
        }
    }

    /**
     * Requests the camera permission. If the user has previously denied the request, a rationale
     * dialog is shown explaining why the app needs the permission.
     */
    private fun requestCameraPermission() {
        when {
            // If the permission is already denied.
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_DENIED &&
                shouldShowRequestPermissionRationale(
                    Manifest.permission.CAMERA,
                ) -> {
                // Shows a rationale dialog to explain why the app needs camera permission.
                showPermissionRationaleDialog()
            }
            // First time permission request or permission denied without "Don't ask again"
            else ->
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    /**
     * Displays an AlertDialog explaining why the app needs camera permission, with options to
     * grant or deny the request.
     */
    private fun showPermissionRationaleDialog() {
        // Creates and shows an AlertDialog to explain the need for camera permission.
        AlertDialog
            .Builder(this)
            // Sets the title and rationale message using R.string resources.
            .setTitle(R.string.camera_permission_title)
            .setMessage(R.string.camera_permission_rationale_message)
            // Positive Button (Main Action): Re-launches the permission request.
            .setPositiveButton(R.string.button_ok) { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
            // Negative Button (Secondary Action): Dismisses the dialog and exits the app, as the
            //                                     camera is essential
            .setNegativeButton(R.string.button_cancel) { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            // Makes the dialog non-cancelable by tapping outside.
            .setCancelable(false)
            // Shows the dialog.
            .show()
    }

    /**
     * This method checks if the user allows the application uses the camera to take photos for this
     * application.
     *
     * @return A boolean value with the user permission agreement.
     */
    private fun checkPermission() =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA,
        ) == PackageManager.PERMISSION_GRANTED
}
