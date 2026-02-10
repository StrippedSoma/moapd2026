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
package dk.itu.moapd.palcomp3.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dk.itu.moapd.palcomp3.R
import dk.itu.moapd.palcomp3.domain.model.SongModel

/**
 * The song row of the application.
 *
 * @param song The song to be displayed.
 * @param isPlaying Whether the song is currently playing.
 * @param onTogglePlayback The function to be called when the song is clicked.
 */
@Composable
fun SongRow(
    song: SongModel,
    isPlaying: Boolean,
    onTogglePlayback: (SongModel) -> Unit,
) {
    val marginSmall = dimensionResource(id = R.dimen.margin_small)
    val marginStandard = dimensionResource(id = R.dimen.margin_standard)
    val marginMedium = dimensionResource(id = R.dimen.margin_medium)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceDim)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(marginSmall)
                .background(MaterialTheme.colorScheme.onSurfaceVariant)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = song.photo,
                contentDescription = stringResource(id = R.string.content_description_album),
                modifier = Modifier
                    .padding(marginStandard)
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.onSurfaceVariant),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = marginMedium, end = marginMedium),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = song.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = song.album,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            val imageVector = if (isPlaying) {
                Icons.Filled.StopCircle
            } else {
                Icons.Filled.PlayCircle
            }

            Image(
                imageVector = imageVector,
                contentDescription = stringResource(id = R.string.content_description_play),
                modifier = Modifier
                    .padding(marginStandard)
                    .size(64.dp)
                    .clickable { onTogglePlayback(song) },
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            )
        }
    }
}