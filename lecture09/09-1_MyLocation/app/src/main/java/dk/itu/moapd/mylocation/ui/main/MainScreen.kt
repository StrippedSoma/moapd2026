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
package dk.itu.moapd.mylocation.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import dk.itu.moapd.mylocation.R
import dk.itu.moapd.mylocation.domain.model.CurrentLocation
import dk.itu.moapd.mylocation.ui.main.components.ReadOnlyFilledField

/**
 * A composable function that displays the main screen of the application.
 *
 * @param paddingValues The padding values to apply to the content.
 * @param location The current location.
 * @param trackingEnabled Whether tracking is enabled.
 * @param onToggleTracking The callback to be invoked when the tracking button is clicked.
 */
@Composable
fun MainScreen(
    paddingValues: PaddingValues,
    location: CurrentLocation,
    trackingEnabled: Boolean,
    onToggleTracking: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = dimensionResource(id = R.dimen.margin_medium))
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_medium)))

            ReadOnlyFilledField(label = stringResource(R.string.text_latitude), value = location.latitude)
            Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_medium)))

            ReadOnlyFilledField(label = stringResource(R.string.text_longitude), value = location.longitude)
            Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_medium)))

            ReadOnlyFilledField(label = stringResource(R.string.text_altitude), value = location.altitude)
            Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_medium)))

            ReadOnlyFilledField(label = stringResource(R.string.text_speed), value = location.speed)
            Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_medium)))

            ReadOnlyFilledField(label = stringResource(R.string.text_time), value = location.time)
            Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_medium)))

            Button(onClick = onToggleTracking) {
                Text(text = stringResource(id = if (trackingEnabled) R.string.button_stop else R.string.button_start))
            }
            Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_medium)))
        }
    }
}
