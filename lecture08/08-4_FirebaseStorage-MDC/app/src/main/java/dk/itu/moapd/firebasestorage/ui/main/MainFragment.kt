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

import dk.itu.moapd.firebasestorage.R
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.gson.Gson
import dk.itu.moapd.firebasestorage.core.tag
import dk.itu.moapd.firebasestorage.databinding.FragmentMainBinding
import dk.itu.moapd.firebasestorage.domain.model.Image
import dk.itu.moapd.firebasestorage.ui.list.ImagesAdapter
import dk.itu.moapd.firebasestorage.ui.list.ImageItemListener
import dk.itu.moapd.firebasestorage.ui.dialogs.DeleteImageDialogFragment
import dk.itu.moapd.firebasestorage.ui.utils.viewBinding
import dk.itu.moapd.firebasestorage.data.repository.ImageRepository
import com.google.android.material.snackbar.Snackbar

/**
 * Fragment that lists the user's uploaded images.
 */
class MainFragment : Fragment(R.layout.fragment_main), ImageItemListener {
    /**
     * A set of private constants used in this class.
     */
    companion object {
        /**
         * The argument key for the image.
         */
        private const val ARG_IMAGE = "ARG_IMAGE"
    }

    /**
     * View binding is a feature that allows you to more easily write code that interacts with
     * views. Once view binding is enabled in a module, it generates a binding class for each XML
     * layout file present in that module. An instance of a binding class contains direct references
     * to all views that have an ID in the corresponding layout.
     */
    private val binding by viewBinding(FragmentMainBinding::bind)

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

        // Set up the FragmentResultListener to handle delete results
        parentFragmentManager.setFragmentResultListener(
            DeleteImageDialogFragment.REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val success = bundle.getBoolean(DeleteImageDialogFragment.RESULT_SUCCESS, false)
            if (success) {
                Snackbar.make(binding.root, R.string.message_image_deleted, Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(binding.root, R.string.error_delete_image, Snackbar.LENGTH_LONG).show()
            }
        }

        // Initialize repository and build a query for the current user.
        val repo = ImageRepository()
        repo.currentUserId()?.let { userId ->
            val query = repo.imagesQuery(userId)

            val options = FirebaseRecyclerOptions.Builder<Image>()
                .setQuery(query, Image::class.java)
                .setLifecycleOwner(this)
                .build()

            val adapter = ImagesAdapter(this@MainFragment, options)
            setupRecyclerView(adapter)
        }
    }

    /**
     * Configures the RecyclerView with a custom adapter and sets up its layout manager, item
     * animator, and item decoration. The layout manager is determined to be a GridLayoutManager
     * with a column count based on the current orientation of the device (2 columns in portrait
     * mode and 4 columns in landscape mode). The item animator is explicitly set to null to disable
     * RecyclerView animations for item changes. Additionally, an item decoration is added to
     * provide uniform padding between items in the grid.
     *
     * @param adapter The CustomAdapter instance that will be used to populate items within the
     *      RecyclerView. This adapter is responsible for creating ViewHolder instances and binding
     *      data to them when the RecyclerView requests it.
     */
    private fun setupRecyclerView(adapter: ImagesAdapter) {
        binding.recyclerView.apply {

            val paddingPx = (2f * resources.displayMetrics.density).toInt()
            val columns = when (resources.configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> 2
                else -> 4
            }

            layoutManager = GridLayoutManager(requireContext(), columns)
            itemAnimator = null
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.set(paddingPx, paddingPx, paddingPx, paddingPx)
                }
            })

            this.adapter = adapter
        }
    }

    /**
     * This method will be executed when the user press an item in the `RecyclerView` for a short
     * time.
     *
     * @param image An instance of `Image` class.
     */
    override fun onItemClickListener(image: Image) {
        val json = Gson().toJson(image)
        val bundle = bundleOf(ARG_IMAGE to json)

        requireActivity().findNavController(R.id.fragment_container_view).navigate(
            R.id.action_main_to_image,
            bundle
        )
    }

    /**
     * This method will be executed when the user press an item in the `RecyclerView` for a long
     * time.
     *
     * @param image An instance of `Image` class.
     * @param position The position of selected view holder in the `RecyclerView`.
     */
    override fun onItemLongClickListener(image: Image, position: Int) {
        val adapter = (binding.recyclerView.adapter as? ImagesAdapter) ?: return
        val key = adapter.getRef(position).key ?: return
        val path = image.path ?: return

        DeleteImageDialogFragment.createInstance(key = key, path = path).apply {
            isCancelable = false
        }.show(parentFragmentManager, tag())
    }
}
