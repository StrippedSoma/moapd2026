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
package dk.itu.moapd.androidsensors.ui.main.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dk.itu.moapd.androidsensors.R
import dk.itu.moapd.androidsensors.sensors.ObserveSensor
import dk.itu.moapd.androidsensors.sensorspec.SingleValueSensorSpec

/**
 * Represents the current state of a single-value sensor page.
 *
 * @property available Whether the sensor is available on the device.
 * @property valueText The current sensor value formatted as a string.
 * @property imageVector The corresponding drawable resource.
 */
private data class SingleValueUiState(
    val available: Boolean = true,
    val valueText: String,
    val imageVector: ImageVector,
)

/**
 * Displays a single-value sensor page.
 */
@Composable
fun SingleValueSensorPage(spec: SingleValueSensorSpec) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val unavailable = stringResource(R.string.unavailable)
    val stepsText = pluralStringResource(R.plurals.steps_text, 0, 0)

    val defaultText =
        remember(spec, unavailable, stepsText) {
            if (spec.key == "step_counter") stepsText else unavailable
        }
    var state by remember(spec, defaultText) {
        mutableStateOf(
            SingleValueUiState(
                available = true,
                valueText = defaultText,
                imageVector = spec.initialImageVector,
            ),
        )
    }

    ObserveSensor(
        sensorType = spec.sensorType,
        onUnavailable = {
            state =
                state.copy(
                    available = false,
                    valueText = unavailable,
                    imageVector = spec.initialImageVector,
                )
        },
        onAvailable = { sensor -> spec.onSensorAvailable?.invoke(sensor) },
        onChanged = { values ->
            val value = values.firstOrNull() ?: return@ObserveSensor
            state =
                state.copy(
                    available = true,
                    valueText = spec.formatter(context, value),
                    imageVector = spec.iconProvider(context, value),
                )
        },
    )

    val indicatorSize = if (isLandscape) 120.dp else 180.dp
    val trackThickness = if (isLandscape) 10.dp else 15.dp
    val iconSize = if (isLandscape) 64.dp else 96.dp
    val textStyle = if (isLandscape) MaterialTheme.typography.titleLarge else MaterialTheme.typography.headlineSmall
    val topBottomSpacing = if (isLandscape) 8.dp else 24.dp

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = { if (state.available) 1f else 0f },
                modifier = Modifier.size(indicatorSize),
                strokeWidth = trackThickness,
            )
            Image(
                imageVector = state.imageVector,
                contentDescription = stringResource(R.string.content_description_steps),
                modifier = Modifier.size(iconSize),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            )
        }
        Spacer(modifier = Modifier.height(topBottomSpacing))
        Text(
            text = state.valueText,
            style = textStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
