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

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dk.itu.moapd.androidsensors.R
import dk.itu.moapd.androidsensors.sensors.ObserveSensor
import dk.itu.moapd.androidsensors.sensorspec.ThreeAxisSensorSpec

/**
 * Represents the current state of a three-axis sensor page.
 *
 * @property valueText The current sensor value formatted as a string.
 * @property progress The current progress indicator percentage.
 */
private data class AxisUiState(
    val valueText: String,
    val progress: Int,
)

/**
 * Displays a three-axis sensor page.
 */
@Composable
fun ThreeAxisSensorPage(spec: ThreeAxisSensorSpec) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val unavailableText = stringResource(R.string.unavailable)

    val initialAxis =
        remember(spec, unavailableText) {
            AxisUiState(
                valueText = unavailableText,
                progress = 0,
            )
        }
    var xState by remember(spec, initialAxis) { mutableStateOf(initialAxis) }
    var yState by remember(spec, initialAxis) { mutableStateOf(initialAxis) }
    var zState by remember(spec, initialAxis) { mutableStateOf(initialAxis) }
    var available by remember(spec) { mutableStateOf(true) }

    ObserveSensor(
        sensorType = spec.sensorType,
        onUnavailable = {
            available = false
            xState = initialAxis
            yState = initialAxis
            zState = initialAxis
        },
        onChanged = { values ->
            available = true
            xState = createAxisUiState(spec, context, values, 0)
            yState = createAxisUiState(spec, context, values, 1)
            zState = createAxisUiState(spec, context, values, 2)
        },
    )

    val containerModifier =
        if (isLandscape) {
            Modifier.fillMaxSize().padding(horizontal = 16.dp)
        } else {
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 8.dp, bottom = 24.dp)
        }

    if (isLandscape) {
        Row(
            modifier = containerModifier,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AxisColumn(label = stringResource(spec.axisXLabelRes), state = xState, unavailable = !available)
            AxisColumn(label = stringResource(spec.axisYLabelRes), state = yState, unavailable = !available)
            AxisColumn(label = stringResource(spec.axisZLabelRes), state = zState, unavailable = !available)
        }
    } else {
        Column(
            modifier = containerModifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            AxisColumn(label = stringResource(spec.axisXLabelRes), state = xState, unavailable = !available)
            Spacer(modifier = Modifier.height(16.dp))
            AxisColumn(label = stringResource(spec.axisYLabelRes), state = yState, unavailable = !available)
            Spacer(modifier = Modifier.height(16.dp))
            AxisColumn(label = stringResource(spec.axisZLabelRes), state = zState, unavailable = !available)
        }
    }
}

/**
 * A reusable column that reproduces one axis item from the XML layout.
 */
@Composable
private fun AxisColumn(
    label: String,
    state: AxisUiState,
    unavailable: Boolean,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator(
            progress = { if (unavailable) 0f else state.progress / 100f },
            modifier = Modifier.size(72.dp),
            strokeWidth = 12.dp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = state.valueText,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
        )
    }
}

/**
 * Builds the UI state for one axis reading.
 */
private fun createAxisUiState(
    spec: ThreeAxisSensorSpec,
    context: Context,
    values: List<Float>,
    index: Int,
): AxisUiState {
    val value = values.getOrElse(index) { 0f }
    return AxisUiState(
        valueText = spec.valueFormatter(context, value),
        progress = spec.progressNormalizer(value).coerceIn(0, 100),
    )
}
