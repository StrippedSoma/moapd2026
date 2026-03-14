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
package dk.itu.moapd.androidsensors.ui.sensors.core

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dk.itu.moapd.androidsensors.R
import dk.itu.moapd.androidsensors.databinding.FragmentSingleValueBinding
import dk.itu.moapd.androidsensors.databinding.FragmentThreeAxesBinding
import dk.itu.moapd.androidsensors.ui.utils.viewBinding

/**
 * A generic fragment that renders either a single-value sensor page or a three-axis sensor page.
 *
 * The concrete behavior of the page is driven by the [SensorPageSpec] fetched from
 * [SensorCatalog]. This keeps all sensor-specific differences in configuration objects instead of
 * spreading them across many nearly identical fragment classes.
 */
class SensorPageFragment : Fragment() {
    /**
     * A set of private constants used in this class.
     */
    companion object {
        /**
         * The bundle key used to persist the requested page specification.
         */
        private const val ARG_PAGE_KEY = "page_key"

        /**
         * Creates a new fragment instance bound to the provided page specification.
         *
         * @param pageKey The stable sensor page key defined in [SensorCatalog].
         *
         * @return A new [SensorPageFragment] instance.
         */
        fun newInstance(pageKey: String): SensorPageFragment =
            SensorPageFragment().apply {
                arguments =
                    Bundle().apply {
                        putString(ARG_PAGE_KEY, pageKey)
                    }
            }
    }

    /**
     * A view binding for the single-value layout, initialized only when needed.
     */
    private val singleValueBinding by viewBinding(FragmentSingleValueBinding::bind)

    /**
     * A view binding for the three-axis layout, initialized only when needed.
     */
    private val threeAxesBinding by viewBinding(FragmentThreeAxesBinding::bind)

    /**
     * Provides access to the Android sensor framework.
     */
    private lateinit var sensorManager: SensorManager

    /**
     * The sensor page definition used by the current fragment instance.
     */
    private lateinit var spec: SensorPageSpec

    /**
     * Listens for updates from the current Android sensor.
     */
    private val sensorEventListener =
        object : SensorEventListener {
            /**
             * Called when there is a new sensor event. Note that "on changed" is somewhat of a
             * misnomer, as this will also be called if we have a new reading from a sensor with the
             * exact same sensor values (but a newer timestamp).
             *
             * The application doesn't own the `android.hardware.SensorEvent` object passed as a
             * parameter and therefore cannot hold on to it. The object may be part of an internal pool
             * and may be reused by the framework.
             *
             * @param event The SensorEvent instance.
             */
            override fun onSensorChanged(event: SensorEvent) {
                when (val currentSpec = spec) {
                    is SingleValueSensorSpec -> renderSingleValue(currentSpec, event.values[0])
                    is ThreeAxisSensorSpec -> renderThreeAxes(currentSpec, event.values)
                }
            }

            /**
             * Called when the accuracy of the registered sensor has changed. Unlike
             * `onSensorChanged()`, this is only called when this accuracy value changes.
             *
             * Accuracy changes are not used by this screen, so the callback intentionally does
             * nothing.
             *
             * @param sensor An instance of the `Sensor` class.
             * @param accuracy The new accuracy of this sensor, one of `SensorManager.SENSOR_STATUS_`
             */
            override fun onAccuracyChanged(
                sensor: Sensor,
                accuracy: Int,
            ) = Unit
        }

    /**
     * Called to do initial creation of a fragment. This is called after `onAttach(Context)` and
     * before `onCreateView(LayoutInflater, ViewGroup, Bundle)`.
     *
     * Note that this can be called while the fragment's activity is still in the process of being
     * created. As such, you can not rely on things like the activity's content view hierarchy being
     * initialized at this point. If you want to do work once the activity itself is created, add a
     * `androidx.lifecycle.LifecycleObserver` on the activity's Lifecycle, removing it when it
     * receives the `Lifecycle.State#CREATED` callback.
     *
     * Any restored child fragments will be created before the base `Fragment.onCreate()` method
     * returns.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous saved state,
     * this is the state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pageKey =
            requireArguments().getString(ARG_PAGE_KEY)
                ?: throw IllegalArgumentException("A sensor page key must be provided.")
        spec = SensorCatalog.requirePage(pageKey)
    }

    /**
     * Called to have the fragment instantiate its user interface view. This is optional, and
     * non-graphical fragments can return null. This will be called between `onCreate(Bundle)` and
     * `onViewCreated(View, Bundle)`. A default `View` can be returned by calling `Fragment(int)` in
     * your constructor. Otherwise, this method returns null.
     *
     * It is recommended to <strong>only</strong> inflate the layout in this method and move logic
     * that operates on the returned View to `onViewCreated(View, Bundle)`.
     *
     * If you return a `View` from here, you will later be called in `onDestroyView()` when the view
     * is being released.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the
     *      fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be
     *      attached to. The fragment should not add the view itself, but this can be used to
     *      generate the `LayoutParams` of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *      saved state as given here.
     *
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val layoutRes =
            when (spec) {
                is SingleValueSensorSpec -> R.layout.fragment_single_value
                is ThreeAxisSensorSpec -> R.layout.fragment_three_axes
            }
        return inflater.inflate(layoutRes, container, false)
    }

    /**
     * Called immediately after `onCreateView(LayoutInflater, ViewGroup, Bundle)` has returned, but
     * before any saved state has been restored in to the view. This gives subclasses a chance to
     * initialize themselves once they know their view hierarchy has been completely created. The
     * fragment's view hierarchy is not however attached to its parent at this point.
     *
     * @param view The View returned by `onCreateView(LayoutInflater, ViewGroup, Bundle)`.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *      saved state as given here.
     */
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        when (val currentSpec = spec) {
            is SingleValueSensorSpec -> configureSingleValuePage(currentSpec)
            is ThreeAxisSensorSpec -> configureThreeAxisPage(currentSpec)
        }
    }

    /**
     * Called when the fragment is visible to the user and actively running. This is generally tied
     * to `Activity.onResume()` of the containing Activity's lifecycle.
     */
    override fun onResume() {
        super.onResume()
        val sensor = sensorManager.getDefaultSensor(spec.sensorType)
        if (sensor == null) {
            renderUnavailableState()
            return
        }

        if (spec is SingleValueSensorSpec) {
            (spec as SingleValueSensorSpec).onSensorAvailable?.invoke(sensor)
        }

        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    /**
     * Called when the Fragment is no longer resumed. This is generally tied to `Activity.onPause()`
     * of the containing Activity's lifecycle.
     */
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorEventListener)
    }

    /**
     * Initializes the widgets used by a single-value sensor page.
     *
     * @param currentSpec The specification that describes the current page.
     */
    private fun configureSingleValuePage(currentSpec: SingleValueSensorSpec) {
        singleValueBinding.imageView.setImageResource(currentSpec.initialIconRes)
        singleValueBinding.imageView.contentDescription = getString(currentSpec.titleRes)
        if (currentSpec.key == "step_counter") {
            singleValueBinding.singleValue.text = resources.getQuantityString(R.plurals.steps_text, 0, 0)
        } else {
            singleValueBinding.singleValue.text = getString(R.string.unavailable)
        }
    }

    /**
     * Initializes the labels used by a three-axis sensor page.
     *
     * @param currentSpec The specification that describes the current page.
     */
    private fun configureThreeAxisPage(currentSpec: ThreeAxisSensorSpec) {
        with(threeAxesBinding) {
            axisX.text = getString(currentSpec.axisXLabelRes)
            axisY.text = getString(currentSpec.axisYLabelRes)
            axisZ.text = getString(currentSpec.axisZLabelRes)
        }
    }

    /**
     * Updates the single-value layout with the latest sensor reading.
     *
     * @param currentSpec The specification that formats the reading.
     * @param value The raw sensor value.
     */
    private fun renderSingleValue(
        currentSpec: SingleValueSensorSpec,
        value: Float,
    ) {
        with(singleValueBinding) {
            singleValue.text = currentSpec.formatter(requireContext(), value)
            imageView.setImageResource(currentSpec.iconProvider(requireContext(), value))
        }
    }

    /**
     * Updates the three-axis layout with the latest sensor readings.
     *
     * @param currentSpec The specification that formats and normalizes the axis values.
     * @param values The raw sensor values emitted by Android.
     */
    private fun renderThreeAxes(
        currentSpec: ThreeAxisSensorSpec,
        values: FloatArray,
    ) {
        val x = values.getOrElse(0) { 0f }
        val y = values.getOrElse(1) { 0f }
        val z = values.getOrElse(2) { 0f }

        with(threeAxesBinding) {
            circularProgressIndicatorX.progress = currentSpec.progressNormalizer(x)
            circularProgressIndicatorY.progress = currentSpec.progressNormalizer(y)
            circularProgressIndicatorZ.progress = currentSpec.progressNormalizer(z)

            axisXValue.text = currentSpec.valueFormatter(requireContext(), x)
            axisYValue.text = currentSpec.valueFormatter(requireContext(), y)
            axisZValue.text = currentSpec.valueFormatter(requireContext(), z)
        }
    }

    /**
     * Renders a consistent fallback state when the requested sensor is not available.
     */
    private fun renderUnavailableState() {
        when (spec) {
            is SingleValueSensorSpec -> {
                singleValueBinding.singleValue.text = getString(R.string.unavailable)
                singleValueBinding.imageView.setImageResource((spec as SingleValueSensorSpec).initialIconRes)
            }
            is ThreeAxisSensorSpec ->
                with(threeAxesBinding) {
                    axisXValue.text = getString(R.string.unavailable)
                    axisYValue.text = getString(R.string.unavailable)
                    axisZValue.text = getString(R.string.unavailable)
                }
        }
    }
}
