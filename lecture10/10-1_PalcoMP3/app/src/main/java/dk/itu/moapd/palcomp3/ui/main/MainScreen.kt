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

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dk.itu.moapd.palcomp3.R
import dk.itu.moapd.palcomp3.domain.model.ExpandableModel
import dk.itu.moapd.palcomp3.domain.model.PlaybackAction
import dk.itu.moapd.palcomp3.service.AudioPlaybackService
import dk.itu.moapd.palcomp3.ui.main.components.ArtistRow
import dk.itu.moapd.palcomp3.ui.main.components.SongRow

/**
 * The main screen of the application.
 *
 * @param viewModel The view model for the main screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val context = LocalContext.current

    val rows by viewModel.rows.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val playingUrl by viewModel.playingUrl.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(error) {
        if (!error.isNullOrBlank()) {
            snackbarHostState.showSnackbar(error!!)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->

        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.margin_large)),
                    contentAlignment = Alignment.TopCenter
                ) {
                    CircularProgressIndicator()
                }
            }

            rows.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.margin_large)),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(text = error ?: "")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(padding),
                    contentPadding = PaddingValues(bottom = 48.dp)
                ) {
                    itemsIndexed(rows) { index, row ->
                        when (row.type) {
                            ExpandableModel.PARENT -> ArtistRow(
                                row = row,
                                onToggle = { viewModel.toggleArtistAt(index) }
                            )

                            ExpandableModel.CHILD -> SongRow(
                                song = row.songChild,
                                isPlaying = playingUrl == row.songChild.file,
                                onTogglePlayback = { clickedSong ->
                                    val action = viewModel.onPlaybackClicked(clickedSong)
                                    when (action) {
                                        is PlaybackAction.Start -> {
                                            stopAudio(
                                                context = context
                                            )
                                            startAudio(
                                                context = context,
                                                url = action.url
                                            )
                                        }

                                        PlaybackAction.Stop -> {
                                            stopAudio(
                                                context = context
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Explicit intent to start the audio playback service.
 *
 * @param context The context of the application.
 * @param url The URL of the audio file to be played.
 */
private fun startAudio(context: Context, url: String) {
    Intent(context, AudioPlaybackService::class.java).apply {
        putExtra("url", url)
    }.also { context.startService(it) }
}

/**
 * Explicit intent to stop the audio playback service.
 *
 * @param context The context of the application.
 */
private fun stopAudio(context: Context) {
    Intent(context, AudioPlaybackService::class.java).also {
        context.stopService(it)
    }
}
