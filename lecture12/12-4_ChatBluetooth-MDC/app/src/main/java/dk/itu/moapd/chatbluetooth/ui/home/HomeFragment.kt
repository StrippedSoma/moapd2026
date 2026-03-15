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
package dk.itu.moapd.chatbluetooth.ui.home

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dk.itu.moapd.chatbluetooth.R
import dk.itu.moapd.chatbluetooth.data.model.ConnectionState
import dk.itu.moapd.chatbluetooth.databinding.FragmentHomeBinding
import dk.itu.moapd.chatbluetooth.ui.main.BluetoothViewModel
import dk.itu.moapd.chatbluetooth.ui.utils.viewBinding

/**
 * The home screen of the Bluetooth Chat app.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {
    /**
     * View binding is a feature that allows you to more easily write code that interacts with
     * views. Once view binding is enabled in a module, it generates a binding class for each XML
     * layout file present in that module. An instance of a binding class contains direct references
     * to all views that have an ID in the corresponding layout.
     */
    private val binding by viewBinding(FragmentHomeBinding::bind)

    /**
     * The [BluetoothViewModel] shared by all fragments.
     */
    private val viewModel: BluetoothViewModel by activityViewModels()

    /**
     * Launcher to request Bluetooth enable.
     *
     * `ACTION_REQUEST_ENABLE` shows a system dialog asking the user to turn on Bluetooth. On
     * Android 12+, you need BLUETOOTH_CONNECT permission before launching this intent.
     */
    private val enableBluetoothLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                updateBluetoothStatus()
                Toast
                    .makeText(
                        requireContext(),
                        R.string.bt_enabled,
                        Toast.LENGTH_SHORT,
                    ).show()
            }
        }

    /**
     * Launcher for runtime permission requests.
     *
     * After the user responds to the permission dialog, we update the UI accordingly.
     */
    private val permissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
        ) { permissions ->
            val allGranted = permissions.values.all { it }
            if (allGranted) {
                updateBluetoothStatus()
            } else {
                Toast
                    .makeText(
                        requireContext(),
                        R.string.error_permissions_required,
                        Toast.LENGTH_LONG,
                    ).show()
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
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        // Request permissions as soon as the fragment is displayed.
        requestBluetoothPermissions()

        setupButtons()
        observeConnectionState()
        updateBluetoothStatus()
    }

    /**
     * Called when the fragment is visible to the user and actively running. This is generally tied
     * to `Activity.onResume()` of the containing Activity's lifecycle.
     */
    override fun onResume() {
        super.onResume()
        // Refresh status in case Bluetooth was toggled while away.
        updateBluetoothStatus()
    }

    /**
     * Wires click listeners to the four main action buttons.
     */
    private fun setupButtons() {
        // Enable Bluetooth.
        binding.buttonEnableBluetooth.setOnClickListener {
            if (!hasBluetoothPermissions()) {
                requestBluetoothPermissions()
                return@setOnClickListener
            }
            if (!viewModel.isBluetoothEnabled) {
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                enableBluetoothLauncher.launch(intent)
            } else {
                Toast
                    .makeText(
                        requireContext(),
                        R.string.bt_enabled,
                        Toast.LENGTH_SHORT,
                    ).show()
            }
        }

        // Navigate to Paired Devices
        binding.buttonPairedDevices.setOnClickListener {
            if (ensureBluetoothReady()) {
                findNavController().navigate(R.id.action_home_to_paired)
            }
        }

        // Navigate to Discovery
        binding.buttonDiscoverDevices.setOnClickListener {
            if (ensureBluetoothReady()) {
                findNavController().navigate(R.id.action_home_to_discovery)
            }
        }

        // Start Server mode and navigate to Chat
        binding.buttonStartServer.setOnClickListener {
            if (ensureBluetoothReady()) {
                viewModel.clearMessages()
                viewModel.setRemoteDeviceName("Remote Device")
                viewModel.startServer()
                val bundle =
                    Bundle().apply {
                        putString("device_address", "")
                        putBoolean("is_server", true)
                        putString("device_name", "Remote Device")
                    }
                findNavController().navigate(R.id.action_home_to_chat, bundle)
            }
        }
    }

    /**
     * Observes the connection state LiveData and updates the status text.
     */
    private fun observeConnectionState() {
        viewModel.connectionState.observe(viewLifecycleOwner) { state ->
            binding.textConnectionStatus.text = getConnectionStateText(state)
        }
    }

    /**
     * Updates the Bluetooth enabled/disabled status shown on screen.
     */
    private fun updateBluetoothStatus() {
        if (!viewModel.isBluetoothSupported) {
            binding.textBluetoothStatus.text = getString(R.string.bt_not_supported)
            binding.buttonEnableBluetooth.isEnabled = false
            binding.buttonPairedDevices.isEnabled = false
            binding.buttonDiscoverDevices.isEnabled = false
            binding.buttonStartServer.isEnabled = false
            return
        }

        if (viewModel.isBluetoothEnabled) {
            binding.textBluetoothStatus.text = getString(R.string.bt_enabled)
        } else {
            binding.textBluetoothStatus.text = getString(R.string.bt_disabled)
        }
    }

    /**
     * Checks whether all required Bluetooth permissions are granted.
     *
     * On Android 12+ (API 31), we need `BLUETOOTH_CONNECT`, `BLUETOOTH_SCAN`,
     * and `BLUETOOTH_ADVERTISE`. On older versions, we need `ACCESS_FINE_LOCATION`.
     *
     * @return `true` if all permissions are granted, `false` otherwise.
     */
    private fun hasBluetoothPermissions(): Boolean {
        val permissions = getRequiredPermissions()
        return permissions.all {
            ContextCompat.checkSelfPermission(requireContext(), it) ==
                PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * Requests the required Bluetooth permissions using the [permissionLauncher].
     */
    private fun requestBluetoothPermissions() {
        val permissions = getRequiredPermissions()
        val notGranted =
            permissions.filter {
                ContextCompat.checkSelfPermission(requireContext(), it) !=
                    PackageManager.PERMISSION_GRANTED
            }
        if (notGranted.isNotEmpty()) {
            permissionLauncher.launch(notGranted.toTypedArray())
        }
    }

    /**
     * Returns the list of permissions needed, depending on the Android version.
     *
     * @return The list of permissions.
     */
    private fun getRequiredPermissions(): List<String> =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Android 12+: granular Bluetooth permissions.
            listOf(
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_ADVERTISE,
            )
        } else {
            // Android 11 and below: location is needed for scanning.
            listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        }

    /**
     * Checks permissions and Bluetooth enabled state.
     *
     * @return `true` if both are met, `false` otherwise.
     */
    private fun ensureBluetoothReady(): Boolean {
        if (!hasBluetoothPermissions()) {
            requestBluetoothPermissions()
            return false
        }
        if (!viewModel.isBluetoothEnabled) {
            Toast
                .makeText(
                    requireContext(),
                    R.string.error_bluetooth_required,
                    Toast.LENGTH_SHORT,
                ).show()
            return false
        }
        return true
    }

    /**
     * Maps a [ConnectionState] to a user-friendly string.
     *
     * @param state The connection state.
     *
     * @return The corresponding string.
     */
    private fun getConnectionStateText(state: ConnectionState) =
        when (state) {
            ConnectionState.IDLE -> getString(R.string.state_idle)
            ConnectionState.DISCOVERING -> getString(R.string.state_discovering)
            ConnectionState.LISTENING -> getString(R.string.state_listening)
            ConnectionState.CONNECTING -> getString(R.string.state_connecting)
            ConnectionState.CONNECTED -> getString(R.string.state_connected)
            ConnectionState.CONNECTION_FAILED -> getString(R.string.state_connection_failed)
            ConnectionState.DISCONNECTED -> getString(R.string.state_disconnected)
        }
}
