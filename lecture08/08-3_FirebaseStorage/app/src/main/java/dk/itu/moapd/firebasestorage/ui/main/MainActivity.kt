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
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import dk.itu.moapd.firebasestorage.R
import dk.itu.moapd.firebasestorage.data.repository.ImageRepository
import dk.itu.moapd.firebasestorage.data.repository.StorageRepository
import dk.itu.moapd.firebasestorage.ui.auth.LoginActivity
import dk.itu.moapd.firebasestorage.ui.main.components.NotificationPermissionRationaleDialog
import dk.itu.moapd.firebasestorage.ui.theme.FirebaseStorageTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * An activity class with several methods to manage the main activity of Realtime Database
 * application.
 */
class MainActivity : ComponentActivity() {

    /**
     * The entry point of the Firebase Authentication SDK.
     */
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    /**
     * The repository for handling Firebase Storage operations.
     */
    private val storageRepo by lazy { StorageRepository() }

    /**
     * The repository for handling Realtime Database operations.
     */
    private val imageRepo by lazy { ImageRepository(storageRepository = storageRepo) }

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

        setContent {
            FirebaseStorageTheme(darkTheme = isSystemInDarkTheme()) {
                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                var isUploading by remember { mutableStateOf(false) }
                var showNotificationsRationale by rememberSaveable { mutableStateOf(false) }

                val showSnackbar: (Int) -> Unit = { resId ->
                    scope.launch { snackbarHostState.showSnackbar(message = getString(resId)) }
                }

                val notificationPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                ) { granted ->
                    if (!granted) showSnackbar(R.string.permission_notifications_denied_message)
                }

                val galleryLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent(),
                ) { uri: Uri? ->
                    uri ?: return@rememberLauncherForActivityResult
                    uploadAndSaveImage(
                        uri = uri,
                        isUploadingSetter = { isUploading = it },
                        scope = scope,
                        snackbarHostState = snackbarHostState,
                        onNotLoggedIn = ::startLoginActivity,
                    )
                }

                LaunchedEffect(Unit) {
                    maybeRequestNotificationPermission(
                        showRationaleSetter = { showNotificationsRationale = it },
                        launchPermission = { notificationPermissionLauncher.launch(it) }
                    )
                }

                LaunchedEffect(Unit) {
                    if (auth.currentUser == null) startLoginActivity()
                }

                if (auth.currentUser == null) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = getString(R.string.app_name))
                    }
                } else {
                    MainScaffold(
                        isUploading = isUploading,
                        onPickImage = { galleryLauncher.launch("image/*") },
                        onLogout = {
                            auth.signOut()
                            startLoginActivity()
                        },
                        snackbarHostState = snackbarHostState,
                    )
                }

                if (showNotificationsRationale) {
                    NotificationPermissionRationaleDialog(
                        onAllow = {
                            showNotificationsRationale = false
                            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        },
                        onDismiss = { showNotificationsRationale = false },
                    )
                }
            }
        }
    }

    /**
     * Uploads an image to Firebase Storage and saves its metadata to Realtime Database.
     *
     * @param uri The URI of the image to be uploaded.
     * @param isUploadingSetter A function to set the uploading state of the image.
     * @param scope The coroutine scope to launch the coroutine.
     * @param snackbarHostState The state of the snackbar host.
     * @param onNotLoggedIn A function to be called when the user is not logged in.
     */
    private fun uploadAndSaveImage(
        uri: Uri,
        isUploadingSetter: (Boolean) -> Unit,
        scope: CoroutineScope,
        snackbarHostState: SnackbarHostState,
        onNotLoggedIn: () -> Unit,
    ) {
        val uid = auth.currentUser?.uid ?: run {
            onNotLoggedIn()
            return
        }

        val filename = UUID.randomUUID().toString()
        val remotePath = "images/$uid/$filename"

        isUploadingSetter(true)

        storageRepo.uploadFile(uri, remotePath)
            .addOnSuccessListener { downloadUri ->
                imageRepo.saveImage(downloadUri.toString(), remotePath)
                    ?.addOnCompleteListener { isUploadingSetter(false) }
                    ?.addOnFailureListener {
                        isUploadingSetter(false)
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = getString(R.string.error_save_image_database)
                            )
                        }
                    }
            }
            .addOnFailureListener {
                isUploadingSetter(false)
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = getString(R.string.error_upload_image)
                    )
                }
            }
    }

    /**
     * Checks if the notification permission is granted and if not, requests it.
     *
     * @param showRationaleSetter A function to set the show rationale state.
     * @param launchPermission A function to launch the permission.
     */
    private fun maybeRequestNotificationPermission(
        showRationaleSetter: (Boolean) -> Unit,
        launchPermission: (String) -> Unit,
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        val permission = Manifest.permission.POST_NOTIFICATIONS
        val hasPermission = checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        if (hasPermission) return

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            showRationaleSetter(true)
        } else {
            launchPermission(permission)
        }
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
}
