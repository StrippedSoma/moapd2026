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
package dk.itu.moapd.mylocation.domain.model

import android.content.Context
import dk.itu.moapd.mylocation.R

/**
 * A data class representing a location.
 *
 * @property latitude The latitude of the location.
 * @property longitude The longitude of the location.
 * @property altitude The altitude of the location.
 * @property speed The speed of the location.
 * @property time The time of the location.
 */
data class CurrentLocation(
    val latitude: String,
    val longitude: String,
    val altitude: String,
    val speed: String,
    val time: String,
) {
    /**
     * A set of private constants used in this class.
     */
    companion object {

        /**
         * Creates a `Location` with not available values.
         *
         * @param context The context to use for getting the string resources.
         *
         * @return A `Location` with not available values.
         */
        fun notAvailable(context: Context): CurrentLocation {
            val na = context.getString(R.string.text_not_available)
            return CurrentLocation(
                latitude = na,
                longitude = na,
                altitude = na,
                speed = na,
                time = na,
            )
        }
    }
}
