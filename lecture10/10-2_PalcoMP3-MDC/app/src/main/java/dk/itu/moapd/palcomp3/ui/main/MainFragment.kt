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
package dk.itu.moapd.palcomp3.ui.main

import dk.itu.moapd.palcomp3.domain.model.ExpandableModel
import dk.itu.moapd.palcomp3.domain.model.SongModel
import dk.itu.moapd.palcomp3.service.AudioPlaybackService
import dk.itu.moapd.palcomp3.ui.list.ExpandableAdapter
import dk.itu.moapd.palcomp3.ui.list.ItemClickListener
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dk.itu.moapd.palcomp3.R
import dk.itu.moapd.palcomp3.databinding.FragmentMainBinding
import dk.itu.moapd.palcomp3.ui.utils.viewBinding

/**
 * A fragment to display the main screen of the app.
 */
class MainFragment : Fragment(R.layout.fragment_main), ItemClickListener {
    /**
     * View binding is a feature that allows you to more easily write code that interacts with
     * views. Once view binding is enabled in a module, it generates a binding class for each XML
     * layout file present in that module. An instance of a binding class contains direct references
     * to all views that have an ID in the corresponding layout.
     */
    private val binding by viewBinding(FragmentMainBinding::bind)

    /**
     * A view model to manage the data access to the database. Using lazy initialization to create
     * the view model instance when the user access the object for the first time.
     */
    private val mainViewModel: MainViewModel by activityViewModels()

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

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(requireContext())
        val url = "https://api.npoint.io/e8aba8d92d4ab5605a9b"

        // Create a space in the bottom of the RecyclerView.
        ViewCompat.setOnApplyWindowInsetsListener(binding.recyclerView) { view, insets ->
            val navBarHeight = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = navBarHeight
            }
            insets
        }

        // Request a JSON response from the provided URL.
        val jsonRequest = StringRequest( Request.Method.GET, url, { response ->

            // Convert the downloaded file into a music data structure.
            val json = response.toString()
            val itemType = object : TypeToken<ArrayList<ExpandableModel>>() {}.type
            val data = Gson().fromJson<ArrayList<ExpandableModel>>(json, itemType)

            // Create the custom adapter to bind a list of cards.
            val adapter = ExpandableAdapter(this@MainFragment, data, mainViewModel)
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = adapter
            
            // Observe current song changes to update UI
            mainViewModel.currentSong.observe(viewLifecycleOwner) { 
                adapter.updatePlaybackIcons()
            }

        }, { })

        // Add the request to the RequestQueue.
        queue.add(jsonRequest)
    }

    /**
     * This method will be executed when the user press an item in the `RecyclerView` for a long
     * time.
     *
     * @param song An instance of `SongModel` class.
     * @param imageView The button pressed by the user.
     */
    override fun onItemClickListener(song: SongModel, imageView: ImageView) {

        // Change the song.
        mainViewModel.onSongChanged(song)

        // Try to stop playing a current song.
        Intent(requireContext(), AudioPlaybackService::class.java).also {
            requireActivity().stopService(it)
        }

        // Start playing a new song.
        if (song.isPlaying)
            Intent(requireContext(), AudioPlaybackService::class.java).apply {
                putExtra("url", song.file)
            }.also {
                requireActivity().startService(it)
            }
    }

}
