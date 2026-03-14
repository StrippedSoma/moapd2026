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
 * Converts this value from degrees to radians.
 *
 * @return this value in radians.
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
 * Converts this value from radians to degrees.
 *
 * @return this value in degrees.
 */
fun Float.normalizeValue(): Int =
    (((this + 1) / 2f) * 100)
        .coerceIn(0f, 100f)
        .toInt()
