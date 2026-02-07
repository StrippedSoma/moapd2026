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

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import dk.itu.moapd.mylocation.R
import dk.itu.moapd.mylocation.core.preferences.LocationTrackingPreferences
import dk.itu.moapd.mylocation.core.time.toSimpleDateTimeString
import dk.itu.moapd.mylocation.databinding.FragmentMainBinding
import dk.itu.moapd.mylocation.service.LocationService
import dk.itu.moapd.mylocation.ui.utils.viewBinding
import kotlinx.coroutines.launch
import java.util.Locale

class MainFragment : Fragment(
    R.layout.fragment_main
), SharedPreferences.OnSharedPreferenceChangeListener {
    /**
     * A set of private constants used in this class.
     */
    companion object {
        /**
         * The request code for location permission request.
         */
        private const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34
    }

    /**
     * View binding is a feature that allows you to more easily write code that interacts with
     * views. Once view binding is enabled in a module, it generates a binding class for each XML
     * layout file present in that module. An instance of a binding class contains direct references
     * to all views that have an ID in the corresponding layout.
     */
    private val binding by viewBinding(FragmentMainBinding::bind)

    /**
     * The SharedPreferences instance that can be used to save and retrieve data.
     */
    private val sharedPreferences: SharedPreferences by lazy {
        requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE,
        )
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
     * When a start-tracking request happens but the service is not yet bound, this flag marks a
     * pending request. Once the service bind completes we will subscribe to updates.
     */
    private var pendingStartTracking: Boolean = false

    /**
     * Defines callbacks for service binding, passed to `bindService()`.
     */
    private val serviceConnection = object : ServiceConnection {

        /**
         * Called when a connection to the Service has been established, with the
         * `android.os.IBinder` of the communication channel to the Service.
         *
         * If the system has started to bind your client app to a service, it's possible that your
         * app will never receive this callback. Your app won't receive a callback if there's an
         * issue with the service, such as the service crashing while being created.
         *
         * @param name The concrete component name of the service that has been connected.
         * @param service The IBinder of the Service's communication channel, which you can now make
         *      calls on.
         */
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as LocationService.LocalBinder
            locationService = binder.service
            locationServiceBound = true

            if (pendingStartTracking) {
                locationService?.subscribeToLocationUpdates()
                pendingStartTracking = false
            }

            locationService?.let { svc ->
                viewLifecycleOwner.lifecycleScope.launch {
                    svc.locationUpdates.collect(::updateLocationDetails)
                }
            }
        }

        /**
         * Called when a connection to the Service has been lost. This typically happens when the
         * process hosting the service has crashed or been killed. This does not remove the
         * ServiceConnection itself -- this binding to the service will remain active, and you will
         * receive a call to `onServiceConnected()` when the Service is next running.
         *
         * @param name The concrete component name of the service whose connection has been lost.
         */
        override fun onServiceDisconnected(name: ComponentName) {
            locationService = null
            locationServiceBound = false
        }
    }

    /**
     * Called immediately after `onCreateView(LayoutInflater, ViewGroup, Bundle)` has returned, but
     * before any saved state has been restored in to the view. This gives subclasses a chance to
     * initialize themselves once they know their view hierarchy has been completely created. The
     * fragment's view hierarchy is not however attached to its parent at this point.
     *
     * @param view The View returned by `onCreateView(LayoutInflater, ViewGroup, Bundle)`.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *      saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonState.setOnClickListener {
            if (LocationTrackingPreferences.isTrackingEnabled(requireContext())) {
                resetLocationDetails()
                locationService?.unsubscribeToLocationUpdates()
                pendingStartTracking = false
                requireActivity().stopService(
                    Intent(
                        requireContext(), LocationService::class.java
                    )
                )
            } else {
                if (hasLocationPermission()) {
                    pendingStartTracking = true
                    requireActivity().startService(
                        Intent(
                            requireContext(), LocationService::class.java
                        )
                    )
                    if (locationServiceBound) {
                        locationService?.subscribeToLocationUpdates()
                        pendingStartTracking = false
                    }
                } else {
                    requestLocationPermission()
                }
            }
        }
    }

    /**
     * Called when the Fragment is visible to the user. This is generally tied to
     * `Activity.onStart()` of the containing Activity's lifecycle.
     */
    override fun onStart() {
        super.onStart()

        updateButtonState(
            LocationTrackingPreferences.isTrackingEnabled(requireContext())
        )
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        val serviceIntent = Intent(requireContext(), LocationService::class.java)
        requireActivity().bindService(
            serviceIntent,
            serviceConnection,
            Context.BIND_AUTO_CREATE
        )

        val alreadyEnabled = LocationTrackingPreferences.isTrackingEnabled(requireContext())
        if (alreadyEnabled) {
            pendingStartTracking = true
            requireActivity().startService(
                Intent(
                    requireContext(), LocationService::class.java
                )
            )
            if (locationServiceBound) {
                locationService?.subscribeToLocationUpdates()
                pendingStartTracking = false
            }
        }
    }

    /**
     * Called when the Fragment is no longer started. This is generally tied to `Activity.onStop()`
     * of the containing Activity's lifecycle.
     */
    override fun onStop() {
        if (locationServiceBound) {
            requireActivity().unbindService(serviceConnection)
            locationServiceBound = false
        }

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
            updateButtonState(
                LocationTrackingPreferences.isTrackingEnabled(requireContext())
            )
        }
    }

    /**
     * Checks if the app has granted permission to access the device location.
     *
     * @return `true` if granted, `false` otherwise.
     */
    private fun hasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Requests the app to grant permission to access the device location.
     */
    private fun requestLocationPermission() {
        if (!hasLocationPermission()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE,
            )
        }
    }

    /**
     * Updates the button state based on the current tracking status.
     *
     * @param trackingLocation Whether tracking is enabled.
     */
    private fun updateButtonState(trackingLocation: Boolean) {
        binding.buttonState.text = getString(
            if (trackingLocation) R.string.button_stop else R.string.button_start,
        )
    }

    /**
     * Updates the location details displayed in the UI.
     *
     * @param location The new location to display.
     */
    private fun updateLocationDetails(location: Location) {
        with(binding) {
            editTextLatitude.setText(
                String.format(Locale.getDefault(), "%.6f", location.latitude)
            )
            editTextLongitude.setText(
                String.format(Locale.getDefault(), "%.6f", location.longitude)
            )
            editTextAltitude.setText(
                String.format(Locale.getDefault(), "%.6f", location.altitude)
            )
            editTextSpeed.setText(
                getString(R.string.text_speed_km, location.speed.toInt())
            )
            editTextTime.setText(
                location.time.toSimpleDateTimeString()
            )
        }
    }

    /**
     * Resets the location details displayed in the UI to default values.
     */
    private fun resetLocationDetails() {
        with(binding) {
            editTextLatitude.setText(getString(R.string.text_not_available))
            editTextLongitude.setText(getString(R.string.text_not_available))
            editTextAltitude.setText(getString(R.string.text_not_available))
            editTextSpeed.setText(getString(R.string.text_not_available))
            editTextTime.setText(getString(R.string.text_not_available))
        }
    }
}
