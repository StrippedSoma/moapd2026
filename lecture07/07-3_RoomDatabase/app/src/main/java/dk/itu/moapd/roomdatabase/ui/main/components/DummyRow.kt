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
package dk.itu.moapd.roomdatabase.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dk.itu.moapd.roomdatabase.R

/**
 * The dummy row composable.
 *
 * @property name The name of the dummy.
 * @property marginMedium The medium margin.
 * @property marginStandard The standard margin.
 * @property onLongPress The action to perform when the user long presses on the row.
 *
 */
@Composable
fun DummyRow(
    name: String,
    marginMedium: Dp,
    marginStandard: Dp,
    onLongPress: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {},
                onLongClick = onLongPress,
            )
            .padding(horizontal = marginMedium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_account_box_24),
            contentDescription = stringResource(id = R.string.content_description_image),
            modifier = Modifier
                .padding(vertical = marginMedium)
                .size(40.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
        )

        Spacer(modifier = Modifier.width(marginMedium))

        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = marginMedium),
        )

        Image(
            painter = painterResource(id = R.drawable.baseline_swap_horiz_24),
            contentDescription = stringResource(id = R.string.content_description_image),
            modifier = Modifier
                .size(40.dp)
                .padding(marginStandard),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
        )
    }
}
