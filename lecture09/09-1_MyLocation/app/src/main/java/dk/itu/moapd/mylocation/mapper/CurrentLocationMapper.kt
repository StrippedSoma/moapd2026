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
package dk.itu.moapd.mylocation.mapper

import android.content.Context
import android.location.Location
import dk.itu.moapd.mylocation.R
import dk.itu.moapd.mylocation.core.time.toSimpleDateTimeString
import dk.itu.moapd.mylocation.domain.model.CurrentLocation
import java.util.Locale

/**
 * Maps a `Location` to a `CurrentLocation`.
 *
 * @param context The context to use for getting the string resources.
 * @param location The `Location` to map.
 *
 * @return A `CurrentLocation` mapped from the `Location`.
 */
fun fieldsFromLocation(context: Context, location: Location): CurrentLocation {
    return CurrentLocation(
        latitude = String.format(Locale.getDefault(), "%.6f", location.latitude),
        longitude = String.format(Locale.getDefault(), "%.6f", location.longitude),
        altitude = String.format(Locale.getDefault(), "%.6f", location.altitude),
        speed = context.getString(R.string.text_speed_km, (location.speed * 3.6f).toInt()),
        time = location.time.toSimpleDateTimeString(),
    )
}
