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
package dk.itu.moapd.firebaseauthentication.ui.screens.albums

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dk.itu.moapd.firebaseauthentication.R

/**
 * The albums screen.
 *
 * @see AlbumsScreen
 */
@Composable
fun AlbumsScreen() {
    val albums = remember {
        listOf(
            R.drawable.album_art_01 to R.string.content_description_album_1,
            R.drawable.album_art_02 to R.string.content_description_album_2,
            R.drawable.album_art_03 to R.string.content_description_album_3,
            R.drawable.album_art_04 to R.string.content_description_album_4,
            R.drawable.album_art_05 to R.string.content_description_album_5,
            R.drawable.album_art_06 to R.string.content_description_album_6,
            R.drawable.album_art_07 to R.string.content_description_album_7,
            R.drawable.album_art_08 to R.string.content_description_album_8,
            R.drawable.album_art_09 to R.string.content_description_album_9,
            R.drawable.album_art_10 to R.string.content_description_album_10,
            R.drawable.album_art_11 to R.string.content_description_album_11,
            R.drawable.album_art_12 to R.string.content_description_album_12,
            R.drawable.album_art_13 to R.string.content_description_album_13,
            R.drawable.album_art_14 to R.string.content_description_album_14,
            R.drawable.album_art_15 to R.string.content_description_album_15,
            R.drawable.album_art_16 to R.string.content_description_album_16,
            R.drawable.album_art_17 to R.string.content_description_album_17,
            R.drawable.album_art_18 to R.string.content_description_album_18,
            R.drawable.album_art_19 to R.string.content_description_album_19,
            R.drawable.album_art_20 to R.string.content_description_album_20,
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        items(albums) { (drawableRes, descRes) ->
            Image(
                painter = painterResource(drawableRes),
                contentDescription = stringResource(descRes),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
        }
    }
}
