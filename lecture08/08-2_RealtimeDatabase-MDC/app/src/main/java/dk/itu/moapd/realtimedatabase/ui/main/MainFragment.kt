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
package dk.itu.moapd.realtimedatabase.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.Firebase
import com.google.firebase.database.database
import dk.itu.moapd.realtimedatabase.R
import dk.itu.moapd.realtimedatabase.core.DATABASE_URL
import dk.itu.moapd.realtimedatabase.core.tag
import dk.itu.moapd.realtimedatabase.data.repository.DummyRepository
import dk.itu.moapd.realtimedatabase.databinding.FragmentMainBinding
import dk.itu.moapd.realtimedatabase.domain.model.Dummy
import dk.itu.moapd.realtimedatabase.ui.dialogs.UpdateDataDialogFragment
import dk.itu.moapd.realtimedatabase.ui.list.DummiesAdapter
import dk.itu.moapd.realtimedatabase.ui.list.DummyItemLongClickListener
import dk.itu.moapd.realtimedatabase.ui.list.SwipeToDeleteCallback
import dk.itu.moapd.realtimedatabase.ui.utils.viewBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    /**
     * View binding is a feature that allows you to more easily write code that interacts with
     * views. Once view binding is enabled in a module, it generates a binding class for each XML
     * layout file present in that module. An instance of a binding class contains direct references
     * to all views that have an ID in the corresponding layout.
     */
    private val binding by viewBinding(FragmentMainBinding::bind)

    /**
     * Creates a new instance of a [DummyRepository].
     *
     * This repository is used to list data from a database.
     */
    private val repository by lazy { DummyRepository() }

    /**
     * The adapter used to populate the RecyclerView.
     */
    private var adapter: DummiesAdapter? = null

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

        val userId = repository.currentUserId() ?: return

        val query = Firebase.database(DATABASE_URL).reference
            .child("dummies")
            .child(userId)
            .orderByChild("createdAt")

        val options = FirebaseRecyclerOptions.Builder<Dummy>()
            .setQuery(query, Dummy::class.java)
            .setLifecycleOwner(viewLifecycleOwner)
            .build()

        adapter = DummiesAdapter(
            longClickListener = DummyItemLongClickListener { dummy, position ->
                val key = adapter?.getRef(position)?.key ?: return@DummyItemLongClickListener
                UpdateDataDialogFragment
                    .createInstance(key = key, currentName = dummy.name, createdAt = dummy.createdAt)
                    .apply { isCancelable = false }
                    .show(parentFragmentManager, tag())
            },
            options = options,
        )

        setupRecyclerView(requireNotNull(adapter))
    }

    /**
     * Configures the RecyclerView with a custom adapter and sets up its layout manager, item
     * animator, and item decoration. The layout manager is determined to be a LinearLayoutManager
     * with 1 column. The item animator is explicitly set to null to disable RecyclerView animations
     * for item changes. Additionally, setup an ItemTouchHelperCallback to enable swipe-to-delete an
     * item from the database.
     *
     * @param adapter The CustomAdapter instance that will be used to populate items within the
     *      RecyclerView. This adapter is responsible for creating ViewHolder instances and binding
     *      data to them when the RecyclerView requests it.
     */
    private fun setupRecyclerView(adapter: DummiesAdapter) {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            this.adapter = adapter

            val swipeHandler = object : SwipeToDeleteCallback() {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    super.onSwiped(viewHolder, direction)
                    val userId = repository.currentUserId() ?: return
                    val pos = viewHolder.bindingAdapterPosition
                    val key = adapter.getRef(pos).key ?: return
                    repository.deleteDummy(userId = userId, key = key)
                }
            }

            ItemTouchHelper(swipeHandler).attachToRecyclerView(this)
        }
    }

    /**
     * Called when the view previously created by `onCreateView()` has been detached from the
     * fragment. The next time the fragment needs to be displayed, a new view will be created. This
     * is called after `onStop()` and before `onDestroy()`. It is called <em>regardless</em> of
     * whether `onCreateView()` returned a non-null view. Internally it is called after the view's
     * state has been saved but before it has been removed from its parent.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}
