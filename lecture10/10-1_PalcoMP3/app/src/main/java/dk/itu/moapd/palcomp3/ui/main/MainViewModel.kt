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

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dk.itu.moapd.palcomp3.domain.model.ExpandableModel
import dk.itu.moapd.palcomp3.domain.model.PlaybackAction
import dk.itu.moapd.palcomp3.domain.model.SongModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * The view model for the main screen of the application.
 *
 * @param app The application context.
 */
class MainViewModel(app: Application) : AndroidViewModel(app) {

    /**
     * A private mutable state flow for the rows of the main screen.
     */
    private val _rows = MutableStateFlow<List<ExpandableModel>>(emptyList())

    /**
     * A public read-only state flow for the rows of the main screen.
     */
    val rows: StateFlow<List<ExpandableModel>> = _rows.asStateFlow()

    /**
     * A private mutable state flow for the loading state of the main screen.
     */
    private val _isLoading = MutableStateFlow(true)

    /**
     * A public read-only state flow for the loading state of the main screen.
     */
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    /**
     * A private mutable state flow for the error message of the main screen.
     */
    private val _error = MutableStateFlow<String?>(null)

    /**
     * A public read-only state flow for the error message of the main screen.
     */
    val error: StateFlow<String?> = _error.asStateFlow()

    /**
     * A private mutable state flow for the currently playing URL.
     */
    private val _playingUrl = MutableStateFlow<String?>(null)

    /**
     * A public read-only state flow for the currently playing URL.
     */
    val playingUrl: StateFlow<String?> = _playingUrl.asStateFlow()

    /**
     * The URL to load the data from.
     */
    private val url = "https://api.npoint.io/e8aba8d92d4ab5605a9b"

    /**
     * Initializes the view model by loading the data.
     */
    init {
        loadData()
    }

    /**
     * Loads the data from the URL and updates the rows state flow.
     */
    fun loadData() {
        _isLoading.value = true
        _error.value = null

        val queue = Volley.newRequestQueue(getApplication())
        val request = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                viewModelScope.launch {
                    try {
                        val itemType = object : TypeToken<ArrayList<ExpandableModel>>() {}.type
                        val data = Gson().fromJson<ArrayList<ExpandableModel>>(response, itemType)
                        _rows.value = data
                        _isLoading.value = false
                    } catch (t: Throwable) {
                        _error.value = t.message ?: "Unknown parsing error"
                        _isLoading.value = false
                    }
                }
            },
            { volleyError ->
                _error.value = volleyError.message ?: "Network error"
                _isLoading.value = false
            }
        )

        queue.add(request)
    }

    /**
     * Toggles the expansion state of the artist at the given position.
     *
     * @param position The position of the artist to toggle.
     */
    fun toggleArtistAt(position: Int) {
        val current = _rows.value.toMutableList()
        val row = current.getOrNull(position) ?: return
        if (row.type != ExpandableModel.PARENT) return

        if (row.isExpanded) {
            // Collapse.
            row.isExpanded = false
            var next = position + 1
            while (next < current.size && current[next].type != ExpandableModel.PARENT) {
                // If we are collapsing a parent that owns the currently playing song, stop it
                val song = current[next].songChild
                if (_playingUrl.value != null && song.file == _playingUrl.value) {
                    _playingUrl.value = null
                }
                current.removeAt(next)
            }
            _rows.value = current
        } else {
            // Expand.
            row.isExpanded = true
            val childSongs = row.artistParent.songs.map { ExpandableModel(ExpandableModel.CHILD, it) }
            current.addAll(position + 1, childSongs)
            _rows.value = current
        }
    }

    /**
     * Handles the playback action for the given song.
     *
     * @param song The song to play.
     *
     * @return The playback action to perform.
     */
    fun onPlaybackClicked(song: SongModel): PlaybackAction {
        val currently = _playingUrl.value
        return if (currently == song.file) {
            _playingUrl.value = null
            PlaybackAction.Stop
        } else {
            _playingUrl.value = song.file
            PlaybackAction.Start(song.file)
        }
    }

    /**
     * Stops the playback of the currently playing song.
     */
    fun stopPlayback() {
        _playingUrl.value = null
    }
}
