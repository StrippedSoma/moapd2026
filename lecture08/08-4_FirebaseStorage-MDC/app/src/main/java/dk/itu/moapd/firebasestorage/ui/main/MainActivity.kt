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
package dk.itu.moapd.firebasestorage.ui.main

import android.Manifest
import dk.itu.moapd.firebasestorage.R
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dk.itu.moapd.firebasestorage.databinding.ActivityMainBinding
import java.util.UUID
import dk.itu.moapd.firebasestorage.ui.auth.LoginActivity
import dk.itu.moapd.firebasestorage.data.repository.StorageRepository
import dk.itu.moapd.firebasestorage.data.repository.ImageRepository

/**
 * An activity class with several methods to manage the main activity of Firebase Storage
 * application.
 */
class MainActivity : AppCompatActivity() {

    /**
     * View binding is a feature that allows you to more easily write code that interacts with
     * views. Once view binding is enabled in a module, it generates a binding class for each XML
     * layout file present in that module. An instance of a binding class contains direct references
     * to all views that have an ID in the corresponding layout.
     */
    private lateinit var binding: ActivityMainBinding

    /**
     * The entry point of the Firebase Authentication SDK.
     */
    private lateinit var auth: FirebaseAuth

    /**
     * The repository for handling Firebase Storage operations.
     */
    private val storageRepo by lazy { StorageRepository() }

    /**
     * The repository for handling Realtime Database operations.
     */
    private val imageRepo by lazy { ImageRepository(storageRepository = storageRepo) }

    /**
     * This object launches a new activity and receives back some result data.
     */
    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        galleryResult(result)
    }

    /**
     * Launcher to request POST_NOTIFICATIONS permission (Android 13+).
     */
    private val requestNotificationPermissionLauncher = registerForActivityResult(
        RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted — you can post notifications.
            // No immediate action required here for this app.
        } else {
            // Permission denied — inform the user that notifications won't be shown.
            val sb = Snackbar.make(
                binding.root,
                getString(R.string.permission_notifications_denied_message),
                Snackbar.LENGTH_LONG
            )
            sb.show()
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

        // Initialize Firebase Auth (DB/Storage are handled by repositories).
        auth = FirebaseAuth.getInstance()

        // Migrate from Kotlin synthetics to Jetpack view binding.
        // https://developer.android.com/topic/libraries/view-binding/migration
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the toolbar as the app bar for the activity.
        setSupportActionBar(binding.toolbar)

        // Request notification permission on Android 13+ (only if needed).
        requestNotificationPermissionIfNeeded()

        // Define the add button behavior.
        binding.floatingActionButton.setOnClickListener {
            launchGalleryIntent()
        }
    }

    /**
     * Checks and requests the POST_NOTIFICATIONS runtime permission on Android 13+.
     * We only request it when target SDK requires it; the manifest already declares it.
     */
    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = android.Manifest.permission.POST_NOTIFICATIONS
            val hasPermission = ContextCompat.checkSelfPermission(this, permission) == PERMISSION_GRANTED
            if (!hasPermission) {
                // Show a rationale if appropriate, otherwise request permission directly.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    showNotificationPermissionRationale()
                } else {
                    requestNotificationPermissionLauncher.launch(permission)
                }
            }
        }
    }

    /**
     * Shows a rationale dialog for the POST_NOTIFICATIONS permission.
     */
    private fun showNotificationPermissionRationale() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.permission_notifications_rationale_title))
            .setMessage(getString(R.string.permission_notifications_rationale_message))
            .setPositiveButton(android.R.string.ok) { _, _ ->
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    /**
     * Called after `onCreate()` method; or after `onRestart()` method when the activity had been
     * stopped, but is now again being displayed to the user. It will usually be followed by
     * `onResume()`. This is a good place to begin drawing visual elements, running animations, etc.
     *
     * You can call `finish()` from within this function, in which case `onStop()` will be
     * immediately called after `onStart()` without the lifecycle transitions in-between
     * (`onResume()`, `onPause()`, etc) executing.
     *
     * <em>Derived classes must call through to the super class's implementation of this method. If
     * they do not, an exception will be thrown.</em>
     */
    override fun onStart() {
        super.onStart()

        // Redirect the user to the LoginActivity if they are not logged in.
        auth.currentUser ?: startLoginActivity()
    }

    /**
     * This method starts the login activity which allows the user log in or sign up to the Firebase
     * Authentication application.
     *
     * Before accessing the main activity, the user must log in the application through a Firebase
     * Auth backend service. The method starts a new activity using explicit intent and used the
     * method `finish()` to disable back button.
     */
    private fun startLoginActivity() {
        Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }.let(::startActivity)
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You should place your menu
     * items in to menu.  This is only called once, the first time the options menu is displayed.
     * To update the menu every time it is displayed, see `onPrepareOptionsMenu(Menu)`.  The default
     * implementation populates the menu with standard system menu items.  These are placed in the
     * `Menu#CATEGORY_SYSTEM` group so that they will be correctly ordered with application-defined
     * menu items.  Deriving classes should always call through to the base implementation.  You can
     * safely hold on to menu (and any items created from it), making modifications to it as
     * desired, until the next time `onCreateOptionsMenu()` is called.  When you add items to the
     * menu, you can implement the Activity's `onOptionsItemSelected(MenuItem)` method to handle
     * them there.
     *
     * @param menu The options menu in which you place your items.
     *
     * @return You must return `true` for the menu to be displayed; if you return `false` it will
     *      not be shown.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    /**
     * This hook is called whenever an item in your options menu is selected.  The default
     * implementation simply returns `false` to have the normal processing happen (calling the
     * item's `Runnable` or sending a message to its `Handler` as appropriate).  You can use this
     * method for any items for which you would like to do processing without those other
     * facilities.  Derived classes should call through to the base class for it to perform the
     * default menu handling.
     *
     * @param item The menu item that was selected.  This value cannot be `null`.
     *
     * @return Return `false` to allow normal menu processing to proceed, `true` to consume it here.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_logout -> {
            auth.signOut()
            startLoginActivity()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    /**
     * Launches an intent to select an image from the device's gallery or other document storage.
     *
     * This method creates an Intent to perform an action for retrieving content, specifically
     * images, allowing the user to select an image from their device storage. The intent is
     * configured to filter for image types only, ensuring that the selection process is restricted
     * to images. After configuring the intent, it is launched using a pre-defined
     * `ActivityResultLauncher`, `galleryLauncher`, which handles the result of the image selection.
     */
    private fun launchGalleryIntent() {
        Intent(Intent.ACTION_GET_CONTENT).also { galleryIntent ->
            galleryIntent.type = "image/*"
            galleryLauncher.launch(galleryIntent)
        }
    }

    /**
     * When the second activity finishes (i.e., the photo gallery intent), it returns a result to
     * this activity. If the user selects an image correctly, we can get a reference of the selected
     * image and send it to the Firebase Storage.
     *
     * @param result A container for an activity result as obtained form `onActivityResult()`.
     */
    private fun galleryResult(result: ActivityResult) {
        // The action to upload the selected image to the Firebase Storage.
        val uploadAction: (Uri) -> Unit = { uri ->
            auth.currentUser?.uid?.let { uid ->
                val filename = UUID.randomUUID().toString()
                val remotePath = "images/$uid/$filename"
                uploadImageToBucket(uri, remotePath)
            }
        }

        // Get the URI of the selected image and upload it to the Firebase Storage.
        result.takeIf { it.resultCode == RESULT_OK }
            ?.data?.data
            ?.let(uploadAction)
    }

    /**
     * This method uploads the original and the thumbnail images to the Firebase Storage, and
     * creates a reference of uploaded images in the database.
     *
     * @param uri The URI of original image.
     * @param remotePath The storage path where the image will be uploaded.
     */
    private fun uploadImageToBucket(uri: Uri, remotePath: String) {
        with(binding.contentMain.progressBar) {
            visibility = View.VISIBLE
            storageRepo.uploadFile(uri, remotePath)
                .addOnSuccessListener { downloadUri ->
                    imageRepo.saveImage(downloadUri.toString(), remotePath)
                        ?.addOnCompleteListener { visibility = View.GONE }
                        ?.addOnFailureListener { _ ->
                            visibility = View.GONE
                            val sb = Snackbar.make(binding.root, getString(R.string.error_save_image_database), Snackbar.LENGTH_LONG)
                            sb.show()
                        }
                }
                .addOnFailureListener { _ ->
                    visibility = View.GONE
                    val sb = Snackbar.make(binding.root, getString(R.string.error_upload_image), Snackbar.LENGTH_LONG)
                    sb.show()
                }
        }
    }

}
