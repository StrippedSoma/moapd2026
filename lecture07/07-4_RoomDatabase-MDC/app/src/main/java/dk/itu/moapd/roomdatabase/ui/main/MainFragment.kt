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
package dk.itu.moapd.roomdatabase.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dk.itu.moapd.roomdatabase.R
import dk.itu.moapd.roomdatabase.app.RoomStorageApplication
import dk.itu.moapd.roomdatabase.databinding.FragmentMainBinding
import dk.itu.moapd.roomdatabase.domain.model.Dummy
import dk.itu.moapd.roomdatabase.ui.common.tag
import dk.itu.moapd.roomdatabase.ui.dialogs.UpdateDataDialogFragment
import dk.itu.moapd.roomdatabase.ui.list.DummyItemLongClickListener
import dk.itu.moapd.roomdatabase.ui.list.DummyListAdapter
import dk.itu.moapd.roomdatabase.ui.list.SwipeToDeleteCallback
import dk.itu.moapd.roomdatabase.ui.utils.viewBinding

class MainFragment :
    Fragment(R.layout.fragment_main),
    DummyItemLongClickListener {
    /**
     * View binding is a feature that allows you to more easily write code that interacts with
     * views. Once view binding is enabled in a module, it generates a binding class for each XML
     * layout file present in that module. An instance of a binding class contains direct references
     * to all views that have an ID in the corresponding layout.
     */
    private val binding by viewBinding(FragmentMainBinding::bind)

    /**
     * Using lazy initialization to create the view model instance when the user access the object
     * for the first time.
     */
    private val dummyViewModel: DummyViewModel by viewModels {
        DummyViewModelFactory((requireActivity().application as RoomStorageApplication).repository)
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

        // Create the custom adapter to bind a list of strings.
        val adapter = DummyListAdapter(this)

        // Setup the recycler view.
        binding.recyclerView.apply {
            // Define the recycler view layout manager and adapter.
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL,
                ),
            )
            this.adapter = adapter

            // Adding the swipe option.
            val swipeHandler =
                object : SwipeToDeleteCallback() {
                    override fun onSwiped(
                        viewHolder: RecyclerView.ViewHolder,
                        direction: Int,
                    ) {
                        super.onSwiped(viewHolder, direction)
                        val position = viewHolder.bindingAdapterPosition
                        if (position != RecyclerView.NO_POSITION) {
                            adapter.currentList.getOrNull(position)?.let(dummyViewModel::delete)
                        }
                    }
                }
            ItemTouchHelper(swipeHandler).attachToRecyclerView(this)
        }

        // Collecting data from the dataset.
        dummyViewModel.dummies.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }

    /**
     * This method will be executed when the user press an item in the `RecyclerView` for a long
     * time.
     *
     * @param dummy An instance of `Dummy` class.
     */
    override fun onItemLongClick(dummy: Dummy) {
        UpdateDataDialogFragment(dummy)
            .apply { isCancelable = false }
            .show(parentFragmentManager, tag())
    }
}
