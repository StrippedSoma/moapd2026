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
package dk.itu.moapd.chatbluetooth.ui.chat

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dk.itu.moapd.chatbluetooth.R
import dk.itu.moapd.chatbluetooth.data.model.ConnectionState
import dk.itu.moapd.chatbluetooth.databinding.FragmentChatBinding
import dk.itu.moapd.chatbluetooth.ui.adapters.ChatMessageAdapter
import dk.itu.moapd.chatbluetooth.ui.main.BluetoothViewModel
import dk.itu.moapd.chatbluetooth.ui.utils.viewBinding

/**
 * The chat screen where messages are exchanged between two Bluetooth devices.
 */
class ChatFragment : Fragment(R.layout.fragment_chat) {
    /**
     * View binding is a feature that allows you to more easily write code that interacts with
     * views. Once view binding is enabled in a module, it generates a binding class for each XML
     * layout file present in that module. An instance of a binding class contains direct references
     * to all views that have an ID in the corresponding layout.
     */
    private val binding by viewBinding(FragmentChatBinding::bind)

    /**
     * The [BluetoothViewModel] shared by all fragments.
     */
    private val viewModel: BluetoothViewModel by activityViewModels()

    /**
     * The adapter for the chat message list.
     */
    private lateinit var adapter: ChatMessageAdapter

    /**
     * Whether this fragment was opened in server mode.
     */
    private var isServer = false

    /**
     * Human-readable name of the remote device.
     */
    private var deviceName = "Remote Device"

    /**
     * Called to do initial creation of a fragment. This is called after `onAttach(Context)` and
     * before `onCreateView(LayoutInflater, ViewGroup, Bundle)`.
     *
     * Note that this can be called while the fragment's activity is still in the process of being
     * created. As such, you can not rely on things like the activity's content view hierarchy being
     * initialized at this point. If you want to do work once the activity itself is created, add a
     * `androidx.lifecycle.LifecycleObserver` on the activity's Lifecycle, removing it when it
     * receives the `Lifecycle.State#CREATED` callback.
     *
     * Any restored child fragments will be created before the base `Fragment.onCreate()` method
     * returns.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous saved state,
     * this is the state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Read navigation arguments.
        arguments?.let {
            isServer =
                it.getBoolean(
                    "is_server",
                    false,
                )
            deviceName = it.getString(
                "device_name",
                "Remote Device",
            ) ?: "Remote Device"
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

        setupKeyboardInsets()
        setupToolbar()
        setupRecyclerView()
        setupMessageInput()
        observeViewModel()
    }

    /**
     * Handles the soft keyboard (IME) insets for edge-to-edge mode.
     *
     * When `enableEdgeToEdge()` is used in the Activity, `adjustResize` alone does not move the
     * content above the keyboard. We must listen for [WindowInsetsCompat.Type.ime] insets and apply
     * bottom padding to the root layout so the input field and send button are pushed above the
     * keyboard.
     */
    private fun setupKeyboardInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Use the larger of the IME bottom or system bar bottom.
            val bottomPadding = maxOf(imeInsets.bottom, systemBarInsets.bottom)
            view.updatePadding(bottom = bottomPadding)

            insets
        }
    }

    /**
     * Sets up the toolbar and navigation button.
     */
    private fun setupToolbar() {
        binding.toolbar.title = if (isServer) getString(R.string.state_listening) else deviceName
        binding.toolbar.setNavigationOnClickListener {
            viewModel.disconnect()
            findNavController().navigateUp()
        }
    }

    /**
     * Sets up the chat message list.
     */
    private fun setupRecyclerView() {
        adapter = ChatMessageAdapter()
        binding.recyclerMessages.adapter = adapter

        // Scroll to bottom whenever a new message arrives.
        adapter.registerAdapterDataObserver(
            object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(
                    positionStart: Int,
                    itemCount: Int,
                ) {
                    binding.recyclerMessages.scrollToPosition(adapter.itemCount - 1)
                }
            },
        )
    }

    /**
     * Sets up the message input field and send button.
     */
    private fun setupMessageInput() {
        binding.buttonSend.setOnClickListener { sendCurrentMessage() }

        binding.editMessage.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendCurrentMessage()
                true
            } else {
                false
            }
        }
    }

    /**
     * Sends the text currently in the input field and clears it.
     */
    private fun sendCurrentMessage() {
        val text =
            binding.editMessage.text
                ?.toString()
                ?.trim() ?: return
        if (text.isEmpty()) return

        viewModel.sendMessage(text)
        binding.editMessage.text?.clear()
    }

    /**
     * Observes the ViewModel's connection state and messages.
     */
    private fun observeViewModel() {
        // Connection state.
        viewModel.connectionState.observe(viewLifecycleOwner) { state ->
            updateConnectionStatus(state)
        }

        // Chat messages.
        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            adapter.submitList(messages.toList())
        }

        // Remote device name (may change when server accepts a connection).
        viewModel.remoteDeviceName.observe(viewLifecycleOwner) { name ->
            if (name.isNotBlank()) {
                binding.toolbar.title = name
            }
        }
    }

    /**
     * Updates the status bar and enables/disables the input field based on state.
     *
     * @param state The new connection state.
     */
    private fun updateConnectionStatus(state: ConnectionState) {
        val statusText =
            when (state) {
                ConnectionState.IDLE -> getString(R.string.state_idle)
                ConnectionState.DISCOVERING -> getString(R.string.state_discovering)
                ConnectionState.LISTENING -> getString(R.string.state_listening)
                ConnectionState.CONNECTING -> getString(R.string.state_connecting)
                ConnectionState.CONNECTED -> getString(R.string.state_connected)
                ConnectionState.CONNECTION_FAILED -> getString(R.string.state_connection_failed)
                ConnectionState.DISCONNECTED -> getString(R.string.state_disconnected)
            }
        binding.textConnectionStatus.text = statusText
        binding.toolbar.subtitle = statusText

        // Enable input only when connected.
        val isConnected = state == ConnectionState.CONNECTED
        binding.editMessage.isEnabled = isConnected
        binding.buttonSend.isEnabled = isConnected
        binding.inputLayout.isEnabled = isConnected

        // Show toast for important state changes.
        when (state) {
            ConnectionState.CONNECTED -> {
                Toast
                    .makeText(
                        requireContext(),
                        R.string.state_connected,
                        Toast.LENGTH_SHORT,
                    ).show()
            }
            ConnectionState.CONNECTION_FAILED -> {
                Toast
                    .makeText(
                        requireContext(),
                        R.string.error_connection_failed,
                        Toast.LENGTH_LONG,
                    ).show()
            }
            ConnectionState.DISCONNECTED -> {
                Toast
                    .makeText(
                        requireContext(),
                        R.string.error_connection_lost,
                        Toast.LENGTH_SHORT,
                    ).show()
            }
            else -> { /* no toast */ }
        }
    }
}
