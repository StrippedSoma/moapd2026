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
package dk.itu.moapd.androidsensors.sensorspec

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Thunderstorm
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbShade
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector
import dk.itu.moapd.androidsensors.R
import dk.itu.moapd.androidsensors.ui.common.normalizeGravity
import dk.itu.moapd.androidsensors.ui.common.normalizeValue
import dk.itu.moapd.androidsensors.ui.common.toDegrees

/**
 * A contract that describes a sensor page shown inside the application view pager.
 *
 * Implementations define which Android sensor should be observed, the tab title shown to the
 * users, the layout type used by the page, and how sensor values are transformed into UI data.
 */
sealed interface SensorPageSpec {
    /**
     * A stable identifier used to recreate the page from fragment arguments.
     */
    val key: String

    /**
     * The Android sensor type handled by this page.
     */
    val sensorType: Int

    /**
     * The tab title resource displayed in the category pager.
     */
    @get:StringRes
    val titleRes: Int
}

/**
 * A specification for pages that present a single sensor value and a central icon.
 *
 * @property key A stable identifier used to recreate the page from fragment arguments.
 * @property sensorType The Android sensor type handled by this page.
 * @property titleRes The tab title resource displayed in the category pager.
 * @property initialImageVector The default icon shown when the sensor is unavailable.
 * @property onSensorAvailable A callback invoked when the requested sensor exists on the device.
 * @property formatter Formats the current sensor reading into a user-facing string.
 * @property iconProvider Resolves the icon that best represents the current sensor reading.
 */
sealed class SingleValueSensorSpec(
    override val key: String,
    override val sensorType: Int,
    @param:StringRes override val titleRes: Int,
    val initialImageVector: ImageVector,
    val onSensorAvailable: ((Sensor) -> Unit)? = null,
    val formatter: Context.(Float) -> String,
    val iconProvider: Context.(Float) -> ImageVector = { initialImageVector },
) : SensorPageSpec

/**
 * A specification for pages that present values on the X, Y, and Z axes.
 *
 * @property key A stable identifier used to recreate the page from fragment arguments.
 * @property sensorType The Android sensor type handled by this page.
 * @property titleRes The tab title resource displayed in the category pager.
 * @property axisXLabelRes The label shown for the X axis.
 * @property axisYLabelRes The label shown for the Y axis.
 * @property axisZLabelRes The label shown for the Z axis.
 * @property valueFormatter Converts an axis reading into a user-facing string.
 * @property progressNormalizer Maps a raw axis value to a progress indicator percentage.
 */
sealed class ThreeAxisSensorSpec(
    override val key: String,
    override val sensorType: Int,
    @param:StringRes override val titleRes: Int,
    @param:StringRes val axisXLabelRes: Int = R.string.axis_x,
    @param:StringRes val axisYLabelRes: Int = R.string.axis_y,
    @param:StringRes val axisZLabelRes: Int = R.string.axis_z,
    val valueFormatter: Context.(Float) -> String,
    val progressNormalizer: (Float) -> Int,
) : SensorPageSpec

/**
 * Represents the current ambient light.
 *
 * @property icon The corresponding drawable resource.
 * @property labelRes The label shown to the user.
 */
private data class LightUi(
    val icon: ImageVector,
    val labelRes: Int,
)

/**
 * A catalog of all sensor page specifications supported by the application.
 *
 * Centralizing the sensor configuration in one place keeps the project compact and avoids the
 * repeated boilerplate that would otherwise be required for one fragment class per sensor.
 */
object SensorCatalog {
    /**
     * Motion sensor pages.
     */
    val motionPages: List<SensorPageSpec> =
        listOf(
            AccelerometerSpec,
            GravitySpec,
            GyroscopeSpec,
            LinearAccelerationSpec,
            RotationVectorSpec,
            StepCounterSpec,
        )

    /**
     * Environmental sensor pages.
     */
    val environmentalPages: List<SensorPageSpec> =
        listOf(
            AmbientTemperatureSpec,
            LightSpec,
            PressureSpec,
            RelativeHumiditySpec,
        )

    /**
     * Position sensor pages.
     */
    val positionPages: List<SensorPageSpec> =
        listOf(
            GameRotationVectorSpec,
            GeomagneticRotationVectorSpec,
            MagneticFieldSpec,
            ProximitySpec,
        )

    /**
     * All available pages indexed by their stable key.
     */
    private val pagesByKey: Map<String, SensorPageSpec> =
        (motionPages + environmentalPages + positionPages).associateBy { it.key }

    /**
     * Returns the specification that matches the provided key.
     *
     * @param key The stable page key stored inside a fragment argument bundle.
     *
     * @return The matching [SensorPageSpec].
     *
     * @throws IllegalArgumentException If the key is not registered in the catalog.
     */
    fun requirePage(key: String): SensorPageSpec =
        pagesByKey[key] ?: throw IllegalArgumentException(
            "Unknown sensor page key: $key",
        )

    /**
     * Accelerometer sensor page.
     */
    private object AccelerometerSpec : ThreeAxisSensorSpec(
        key = "accelerometer",
        sensorType = Sensor.TYPE_ACCELEROMETER,
        titleRes = R.string.tab_accelerometer,
        valueFormatter = { getString(R.string.gravity_text, it) },
        progressNormalizer = { it.normalizeGravity() },
    )

    /**
     * Gravity sensor page.
     */
    private object GravitySpec : ThreeAxisSensorSpec(
        key = "gravity",
        sensorType = Sensor.TYPE_GRAVITY,
        titleRes = R.string.tab_gravity,
        valueFormatter = { getString(R.string.gravity_text, it) },
        progressNormalizer = { it.normalizeGravity() },
    )

    /**
     * Gyroscope sensor page.
     */
    private object GyroscopeSpec : ThreeAxisSensorSpec(
        key = "gyroscope",
        sensorType = Sensor.TYPE_GYROSCOPE,
        titleRes = R.string.tab_gyroscope,
        axisXLabelRes = R.string.rotation_x,
        axisYLabelRes = R.string.rotation_y,
        axisZLabelRes = R.string.rotation_z,
        valueFormatter = { getString(R.string.degrees_text, it.toDegrees()) },
        progressNormalizer = { it.normalizeGravity() },
    )

    /**
     * Linear acceleration sensor page.
     */
    private object LinearAccelerationSpec : ThreeAxisSensorSpec(
        key = "linear_acceleration",
        sensorType = Sensor.TYPE_LINEAR_ACCELERATION,
        titleRes = R.string.tab_linear_acceleration,
        valueFormatter = { getString(R.string.gravity_text, it) },
        progressNormalizer = { it.normalizeGravity() },
    )

    /**
     * Rotation vector sensor page.
     */
    private object RotationVectorSpec : ThreeAxisSensorSpec(
        key = "rotation_vector",
        sensorType = Sensor.TYPE_ROTATION_VECTOR,
        titleRes = R.string.tab_rotation_vector,
        axisXLabelRes = R.string.rotation_x,
        axisYLabelRes = R.string.rotation_y,
        axisZLabelRes = R.string.rotation_z,
        valueFormatter = { getString(R.string.float_text, it) },
        progressNormalizer = { it.normalizeValue() },
    )

    /**
     * Step counter sensor page.
     */
    private object StepCounterSpec : SingleValueSensorSpec(
        key = "step_counter",
        sensorType = Sensor.TYPE_STEP_COUNTER,
        titleRes = R.string.tab_step_counter,
        initialImageVector = Icons.AutoMirrored.Filled.DirectionsWalk,
        formatter = {
            val count = it.toInt()
            resources.getQuantityString(R.plurals.steps_text, count, count)
        },
    )

    /**
     * Ambient temperature sensor page.
     */
    private object AmbientTemperatureSpec : SingleValueSensorSpec(
        key = "ambient_temperature",
        sensorType = Sensor.TYPE_AMBIENT_TEMPERATURE,
        titleRes = R.string.tab_ambient_temperature,
        initialImageVector = Icons.Filled.Thermostat,
        formatter = { getString(R.string.celsius_text, it.toInt()) },
    )

    /**
     * Light sensor page.
     */
    private object LightSpec : SingleValueSensorSpec(
        key = "light",
        sensorType = Sensor.TYPE_LIGHT,
        titleRes = R.string.tab_light,
        initialImageVector = Icons.Filled.Lightbulb,
        formatter = { value ->
            getString(resolveLightUi(value).labelRes)
        },
        iconProvider = { value ->
            resolveLightUi(value).icon
        },
    )

    /**
     * Pressure sensor page.
     */
    private object PressureSpec : SingleValueSensorSpec(
        key = "pressure",
        sensorType = Sensor.TYPE_PRESSURE,
        titleRes = R.string.tab_pressure,
        initialImageVector = Icons.Filled.Speed,
        formatter = { getString(R.string.pascal_text, it) },
    )

    /**
     * Relative humidity sensor page.
     */
    private object RelativeHumiditySpec : SingleValueSensorSpec(
        key = "relative_humidity",
        sensorType = Sensor.TYPE_RELATIVE_HUMIDITY,
        titleRes = R.string.tab_relative_humidity,
        initialImageVector = Icons.Filled.WaterDrop,
        formatter = { getString(R.string.percent_text, it.toInt()) },
    )

    /**
     * Game rotation vector sensor page.
     */
    private object GameRotationVectorSpec : ThreeAxisSensorSpec(
        key = "game_rotation_vector",
        sensorType = Sensor.TYPE_GAME_ROTATION_VECTOR,
        titleRes = R.string.tab_game_rotation_vector,
        axisXLabelRes = R.string.rotation_x,
        axisYLabelRes = R.string.rotation_y,
        axisZLabelRes = R.string.rotation_z,
        valueFormatter = { getString(R.string.float_text, it) },
        progressNormalizer = { it.normalizeValue() },
    )

    /**
     * Geomagnetic rotation vector sensor page.
     */
    private object GeomagneticRotationVectorSpec : ThreeAxisSensorSpec(
        key = "geomagnetic_rotation_vector",
        sensorType = Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR,
        titleRes = R.string.tab_geomagnetic_rotation_vector,
        axisXLabelRes = R.string.rotation_x,
        axisYLabelRes = R.string.rotation_y,
        axisZLabelRes = R.string.rotation_z,
        valueFormatter = { getString(R.string.float_text, it) },
        progressNormalizer = { it.normalizeValue() },
    )

    /**
     * Magnetic field sensor page.
     */
    private object MagneticFieldSpec : ThreeAxisSensorSpec(
        key = "magnetic_field",
        sensorType = Sensor.TYPE_MAGNETIC_FIELD,
        titleRes = R.string.tab_magnetic_field,
        valueFormatter = { getString(R.string.tesla_text, it) },
        progressNormalizer = { it.normalizeValue() },
    )

    /**
     * Proximity sensor page.
     */
    private object ProximitySpec : SingleValueSensorSpec(
        key = "proximity",
        sensorType = Sensor.TYPE_PROXIMITY,
        titleRes = R.string.tab_proximity,
        initialImageVector = Icons.AutoMirrored.Filled.VolumeOff,
        formatter = { getString(R.string.cm_text, it.toInt()) },
        iconProvider = { value ->
            val defaultSensor =
                (getSystemService(Context.SENSOR_SERVICE) as SensorManager)
                    .getDefaultSensor(Sensor.TYPE_PROXIMITY)
            val maximumRange = defaultSensor?.maximumRange ?: 0f
            if (maximumRange / 2 > value) {
                Icons.AutoMirrored.Filled.VolumeOff
            } else {
                Icons.AutoMirrored.Filled.VolumeUp
            }
        },
    )

    /**
     * Resolves the drawable resource that represents the current ambient light.
     *
     * @param value The current light sensor value.
     *
     * @return The corresponding drawable resource.
     */
    private fun resolveLightUi(value: Float): LightUi {
        val lightLevels =
            listOf(
                SensorManager.LIGHT_NO_MOON to LightUi(Icons.Filled.NightsStay, R.string.light_no_moon),
                SensorManager.LIGHT_FULLMOON to LightUi(Icons.Filled.Nightlight, R.string.light_full_moon),
                SensorManager.LIGHT_CLOUDY to LightUi(Icons.Filled.Cloud, R.string.light_cloudy),
                SensorManager.LIGHT_SUNRISE to LightUi(Icons.Outlined.WbSunny, R.string.light_sunrise),
                SensorManager.LIGHT_OVERCAST to LightUi(Icons.Filled.Thunderstorm, R.string.light_overcast),
                SensorManager.LIGHT_SHADE to LightUi(Icons.Filled.WbShade, R.string.light_shade),
                SensorManager.LIGHT_SUNLIGHT to LightUi(Icons.Filled.WbSunny, R.string.light_sunlight),
                Float.MAX_VALUE to LightUi(Icons.Filled.WbSunny, R.string.light_sunlight),
            )

        return lightLevels.firstOrNull { value <= it.first }?.second
            ?: LightUi(Icons.Filled.WbSunny, R.string.light_sunlight)
    }
}
