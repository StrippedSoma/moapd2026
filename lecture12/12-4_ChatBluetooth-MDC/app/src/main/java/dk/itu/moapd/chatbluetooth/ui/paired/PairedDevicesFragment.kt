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
package dk.itu.moapd.chatbluetooth.ui.paired

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dk.itu.moapd.chatbluetooth.R
import dk.itu.moapd.chatbluetooth.databinding.FragmentPairedDevicesBinding
import dk.itu.moapd.chatbluetooth.ui.adapters.DeviceListAdapter
import dk.itu.moapd.chatbluetooth.ui.main.BluetoothViewModel
import dk.itu.moapd.chatbluetooth.ui.utils.viewBinding

/**
 * Displays the list of Bluetooth devices that are already paired (bonded) with this Android device.
 */
class PairedDevicesFragment : Fragment(R.layout.fragment_paired_devices) {
    /**
     * View binding is a feature that allows you to more easily write code that interacts with
     * views. Once view binding is enabled in a module, it generates a binding class for each XML
     * layout file present in that module. An instance of a binding class contains direct references
     * to all views that have an ID in the corresponding layout.
     */
    private val binding by viewBinding(FragmentPairedDevicesBinding::bind)

    /**
     * The [BluetoothViewModel] shared by all fragments.
     */
    private val viewModel: BluetoothViewModel by activityViewModels()

    /**
     * Adapter for the paired devices RecyclerView.
     */
    private lateinit var adapter: DeviceListAdapter

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

        setupToolbar()
        setupRecyclerView()
        loadPairedDevices()
    }

    /**
     * Sets up the toolbar and navigation button.
     */
    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    /**
     * Sets up the paired devices RecyclerView.
     */
    private fun setupRecyclerView() {
        adapter =
            DeviceListAdapter { device ->
                viewModel.clearMessages()
                viewModel.connectToDevice(device.address, device.name)

                val bundle =
                    Bundle().apply {
                        putString("device_address", device.address)
                        putBoolean("is_server", false)
                        putString("device_name", device.name)
                    }
                findNavController().navigate(R.id.action_paired_to_chat, bundle)
            }
        binding.recyclerPairedDevices.adapter = adapter
    }

    /**
     * Queries the system for paired devices and submits them to the adapter.
     *
     * This call is fast because it reads from the system's stored bond information
     * rather than performing an active scan.
     */
    private fun loadPairedDevices() {
        val devices = viewModel.getPairedDevices()
        adapter.submitList(devices)

        // Show/hide empty state
        binding.textEmpty.isVisible = devices.isEmpty()
        binding.recyclerPairedDevices.isVisible = devices.isNotEmpty()
    }
}
