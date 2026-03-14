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
package dk.itu.moapd.androidsensors.ui.common

import android.hardware.SensorManager
import java.lang.Float.max
import java.lang.Float.min

/**
 * Converts this value from radians to degrees.
 *
 * @return this value in degrees.
 */
fun Float.toDegrees(): Int = (this * 180 / Math.PI).toInt()

/**
 * Normalizes a gravity-related sensor value to an integer percentage in the range [0, 100].
 *
 * The input value is first clamped to the interval [-STANDARD_GRAVITY, +STANDARD_GRAVITY],
 * then linearly mapped so that -STANDARD_GRAVITY → 0, 0 → 50, and +STANDARD_GRAVITY → 100.
 *
 * @return A normalized percentage representation of this gravity value.
 */
fun Float.normalizeGravity(): Int =
    (
            (
                    min(max(this, -SensorManager.STANDARD_GRAVITY), SensorManager.STANDARD_GRAVITY) +
                            SensorManager.STANDARD_GRAVITY
                    ) /
                    (2f * SensorManager.STANDARD_GRAVITY) * 100
            ).toInt()

/**
 * Normalizes this value to an integer percentage in the range [0, 100].
 *
 * The value is linearly mapped from the interval [-1, 1] to [0, 100], so that -1 → 0,
 * 0 → 50, and 1 → 100.
 *
 * @return A normalized percentage representation of this value.
 */
fun Float.normalizeValue(): Int =
    (((this + 1) / 2f) * 100)
        .coerceIn(0f, 100f)
        .toInt()
