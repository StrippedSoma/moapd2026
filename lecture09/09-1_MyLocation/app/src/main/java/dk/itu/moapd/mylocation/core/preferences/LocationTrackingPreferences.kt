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
package dk.itu.moapd.mylocation.core.preferences

import android.content.Context
import androidx.core.content.edit
import dk.itu.moapd.mylocation.R

/**
 * Wrapper around SharedPreferences used to keep this demo's state.
 */
object LocationTrackingPreferences {

    /**
     * The key used to store the location updates state in SharedPreferences.
     */
    const val KEY_TRACKING_ENABLED = "tracking_foreground_location"

    /**
     * Returns true if requesting location updates, otherwise returns false.
     *
     * @param context The [Context].
     *
     * @return Returns true if requesting location updates, otherwise returns false.
     */
    fun isTrackingEnabled(context: Context): Boolean {
        return prefs(context).getBoolean(KEY_TRACKING_ENABLED, false)
    }

    /**
     * Stores the location updates state in SharedPreferences.
     *
     * @param context The [Context].
     * @param enabled Setting this flag to false will stop location updates.
     *
     */
    fun setTrackingEnabled(context: Context, enabled: Boolean) {
        prefs(context).edit { putBoolean(KEY_TRACKING_ENABLED, enabled) }
    }

    /**
     * Retrieves the location updates preferences from SharedPreferences.
     *
     * @param context The [Context].
     *
     * @return A `SharedPreferences` instance.
     */
    private fun prefs(context: Context) = context.getSharedPreferences(
        context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE,
    )
}
