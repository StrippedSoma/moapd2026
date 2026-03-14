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
package dk.itu.moapd.androidsensors.sensors

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Nature
import androidx.compose.material.icons.filled.Rotate90DegreesCcw
import androidx.compose.ui.graphics.vector.ImageVector
import dk.itu.moapd.androidsensors.R
import dk.itu.moapd.androidsensors.sensorspec.SensorCatalog
import dk.itu.moapd.androidsensors.sensorspec.SensorPageSpec

/**
 * Describes the high-level categories shown in the app navigation. Each category maps to an icon,
 * a title, and a fixed set of sensor pages.
 *
 * @property titleRes The tab title resource displayed in the category pager.
 * @property imageVector The icon shown in the category pager.
 * @property pages The fixed set of sensor pages shown in the category pager.
 */
enum class SensorCategory(
    val titleRes: Int,
    val imageVector: ImageVector,
    val pages: List<SensorPageSpec>,
) {
    Motion(
        titleRes = R.string.fragment_motion,
        imageVector = Icons.Filled.Rotate90DegreesCcw,
        pages = SensorCatalog.motionPages,
    ),
    Environmental(
        titleRes = R.string.fragment_environmental,
        imageVector = Icons.Filled.Nature,
        pages = SensorCatalog.environmentalPages,
    ),
    Position(
        titleRes = R.string.fragment_position,
        imageVector = Icons.Filled.MyLocation,
        pages = SensorCatalog.positionPages,
    ),
}
