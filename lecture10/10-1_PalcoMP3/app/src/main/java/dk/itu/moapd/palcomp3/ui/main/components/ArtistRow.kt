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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import dk.itu.moapd.palcomp3.domain.model.ExpandableModel

/**
 * The artist row of the application.
 *
 * @param row The row to be displayed.
 * @param onToggle The function to be called when the row is clicked.
 */
@Composable
fun ArtistRow(
    row: ExpandableModel,
    onToggle: () -> Unit,
) {
    val marginSmall = dimensionResource(id = R.dimen.margin_small)
    val marginStandard = dimensionResource(id = R.dimen.margin_standard)
    val marginMedium = dimensionResource(id = R.dimen.margin_medium)
    val marginLarge = dimensionResource(id = R.dimen.margin_large)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        // Top divider bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(marginSmall)
                .background(MaterialTheme.colorScheme.onSurfaceVariant)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
        ) {
            // Artist image (50%)
            AsyncImage(
                model = row.artistParent.photo,
                contentDescription = stringResource(id = R.string.content_description_artist),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .height(170.dp)
                    .background(MaterialTheme.colorScheme.onSurfaceVariant),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )

            // Middle separator (1dp)
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(170.dp)
            )

            // Texts + arrow (50%)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = marginLarge, top = marginLarge, end = marginMedium)
                ) {
                    Text(
                        text = row.artistParent.name,
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(marginSmall))

                    Text(
                        text = row.artistParent.style,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                val imageVector = if (row.isExpanded) {
                    Icons.Filled.KeyboardArrowUp
                } else {
                    Icons.Filled.KeyboardArrowDown
                }

                Image(
                    imageVector = imageVector,
                    contentDescription = if (row.isExpanded) {
                        stringResource(id = R.string.content_description_open)
                    } else {
                        stringResource(id = R.string.content_description_close)
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = marginStandard, bottom = marginStandard)
                        .clickable { onToggle() },
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                )
            }
        }
    }
}
