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
package dk.itu.moapd.mylocation.ui.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Location
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import dk.itu.moapd.mylocation.R
import dk.itu.moapd.mylocation.core.preferences.LocationTrackingPreferences
import dk.itu.moapd.mylocation.service.LocationService
import dk.itu.moapd.mylocation.ui.theme.MyLocationTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Main activity of MyLocation application. XML + Fragment UI was migrated to Jetpack Compose.
 */
class MainActivity : ComponentActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    /**
     * The SharedPreferences instance that can be used to save and retrieve data.
     */
    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    }

    /**
     * Provides location updates for while-in-use feature.
     */
    private var locationService: LocationService? = null

    /**
     * A flag to indicate whether a bound to the service.
     */
    private var locationServiceBound: Boolean = false

    /**
     * A job for collecting location updates.
     */
    private var collectJob: Job? = null

    /**
     * Holds the latest onLocation callback provided by the composable. This allows starting
     * collection after the activity is recreated (e.g. on rotation) once the service bind
     * completes.
     */
    private var onLocationCallback: ((Location) -> Unit)? = null

    /**
     * When the user toggles tracking, we may need to start the service even if it's not yet bound.
     * This flag indicates a pending request to subscribe to location updates.
     */
    private var pendingStartTracking: Boolean = false

    /**
     * Defines callbacks for service binding, passed to `bindService()`.
     */
    private val serviceConnection = createLocationServiceConnection(
        onConnected = { service ->
            locationService = service
            locationServiceBound = true

            if (pendingStartTracking) {
                service.subscribeToLocationUpdates()
                pendingStartTracking = false
            }

            startCollectingIfReady()
        },
        onDisconnected = {
            locationService = null
            locationServiceBound = false
            collectJob?.cancel()
            collectJob = null
        },
    )

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
            MyLocationTheme {
                MyLocationScaffold(
                    sharedPreferences = sharedPreferences,
                    onStartTracking = {
                        pendingStartTracking = true
                        val serviceIntent = Intent(this, LocationService::class.java)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            ContextCompat.startForegroundService(this, serviceIntent)
                        } else {
                            startService(serviceIntent)
                        }
                        if (locationServiceBound) {
                            locationService?.subscribeToLocationUpdates()
                            pendingStartTracking = false
                        }
                    },
                    onStopTracking = {
                        pendingStartTracking = false
                        locationService?.unsubscribeToLocationUpdates()
                        stopService(Intent(this, LocationService::class.java))
                    },
                     onCollectLocations = { onLocation ->
                         onLocationCallback = onLocation
                         collectJob?.cancel()
                         startCollectingIfReady()
                     },
                 )
             }
         }

        val alreadyEnabledOnCreate = LocationTrackingPreferences.isTrackingEnabled(this)
        if (alreadyEnabledOnCreate) {
            pendingStartTracking = true
            val serviceIntent = Intent(this, LocationService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ContextCompat.startForegroundService(this, serviceIntent)
            } else {
                startService(serviceIntent)
            }
        }

     }

    /**
     * Called after `onCreate()` or after `onRestart()` when the activity had been stopped, but is
     * now again being displayed to the user. It will usually be followed by `onResume()`. This is a
     * good place to begin drawing visual elements, running animations, etc.
     *
     * You can call `finish()` from within this function, in which case `onStop()` will be
     * immediately called after `onStart()` without the lifecycle transitions in-between
     * (`onResume()`, `onPause()`, etc) executing.
     *
     * Derived classes must call through to the super class's implementation of this method. If they
     * do not, an exception will be thrown.
     */
    override fun onStart() {
        super.onStart()
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        bindService(
            Intent(
                this,
                LocationService::class.java
            ),
            serviceConnection,
            Context.BIND_AUTO_CREATE
        )

        val alreadyEnabled = LocationTrackingPreferences.isTrackingEnabled(this)
        if (alreadyEnabled) {
            pendingStartTracking = true
            val serviceIntent = Intent(this, LocationService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ContextCompat.startForegroundService(this, serviceIntent)
            } else {
                startService(serviceIntent)
            }
            if (locationServiceBound) {
                locationService?.subscribeToLocationUpdates()
                pendingStartTracking = false
            }
        }
    }

    /**
     * Called when you are no longer visible to the user. You will next receive either
     * `onRestart()`, `onDestroy()`, or nothing, depending on later user activity. This is a good
     * place to stop refreshing UI, running animations and other visual things.
     *
     * Derived classes must call through to the super class's implementation of this method. If they
     * do not, an exception will be thrown.
     */
    override fun onStop() {
        if (locationServiceBound) {
            unbindService(serviceConnection)
            locationServiceBound = false
        }

        collectJob?.cancel()
        collectJob = null

        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onStop()
    }

    /**
     * Called when a shared preference is changed, added, or removed. This may be called even if a
     * preference is set to its existing value. This callback will be run on your main thread.
     *
     * @param sharedPreferences The `SharedPreferences` that received the change.
     * @param key The key of the preference that was changed, added, or removed. Apps targeting
     *      android.os.Build.VERSION_CODES#R on devices running OS versions
     *      android.os.Build.VERSION_CODES#R Android R} or later, will receive a `null` value when
     *      preferences are cleared.
     */
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key == LocationTrackingPreferences.KEY_TRACKING_ENABLED) {
            val enabled = LocationTrackingPreferences.isTrackingEnabled(this)
            if (!enabled) {
                collectJob?.cancel()
                collectJob = null
            } else {
                startCollectingIfReady()
            }
        }
    }

    /**
     * Starts the collector if we have both the service bound and the composable's onLocation
     * callback available and tracking is enabled.
     */
    private fun startCollectingIfReady() {
        val isReady = onLocationCallback != null &&
                locationService != null &&
                LocationTrackingPreferences.isTrackingEnabled(this)

        if (!isReady) return

        collectJob?.cancel()
        collectJob = lifecycleScope.launch {
            locationService?.locationUpdates?.collect { location ->
                onLocationCallback?.invoke(location)
            }
        }
    }
}
