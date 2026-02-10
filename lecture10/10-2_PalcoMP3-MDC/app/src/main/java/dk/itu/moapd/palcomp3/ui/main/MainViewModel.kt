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

import dk.itu.moapd.palcomp3.domain.model.SongModel
import dk.itu.moapd.palcomp3.domain.model.ExpandableModel
import java.util.ArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * A view model sensitive to changes in the `MainActivity` UI components.
 */
class MainViewModel : ViewModel() {

    /**
     * The current song playing in the main activity.
     */
    private val _currentSong = MutableLiveData<SongModel?>(null)

    /**
     * Exposes the current playing song as immutable LiveData.
     */
    val currentSong: LiveData<SongModel?> = _currentSong

    /**
     * Cache the loaded expandable items so rotation doesn't reload and reset the models.
     */
    var cachedItems: ArrayList<ExpandableModel>? = null

    /**
     * This method will be executed when the user interacts with any player control button. It sets
     * the new song in the LiveData instance.
     *
     * @param song A instance of `SongModel` class.
     */
    fun onSongChanged(song: SongModel) {
        _currentSong.value?.let { prevSong ->
            if (prevSong != song)
                prevSong.isPlaying = false
        }
        _currentSong.value = song
    }

}
