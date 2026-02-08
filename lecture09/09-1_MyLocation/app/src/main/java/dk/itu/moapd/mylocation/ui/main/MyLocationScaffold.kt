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
package dk.itu.moapd.mylocation.ui.main

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import dk.itu.moapd.mylocation.R
import dk.itu.moapd.mylocation.domain.model.CurrentLocation
import dk.itu.moapd.mylocation.mapper.fieldsFromLocation
import dk.itu.moapd.mylocation.ui.state.rememberTrackingEnabledState

/**
 * A composable function that displays the main screen of the application.
 *
 * @param sharedPreferences The shared preferences of the application.
 * @param onStartTracking The callback to be invoked when tracking is started.
 * @param onStopTracking The callback to be invoked when tracking is stopped.
 * @param onCollectLocations The callback to be invoked when locations are collected.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Suppress("UNUSED_VALUE")
@Composable
fun MyLocationScaffold(
    sharedPreferences: SharedPreferences,
    onStartTracking: () -> Unit,
    onStopTracking: () -> Unit,
    onCollectLocations: (onLocation: (Location) -> Unit) -> Unit,
) {
    val context = LocalContext.current
    val trackingEnabled = rememberTrackingEnabledState(sharedPreferences, context)

    var fields by remember { mutableStateOf(CurrentLocation.notAvailable(context)) }

    LaunchedEffect(trackingEnabled.value) {
        if (trackingEnabled.value) {
            onCollectLocations { location ->
                fields = fieldsFromLocation(context, location)
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { granted ->
        if (granted) onStartTracking()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }) },
    ) { padding ->
        MainScreen(
            paddingValues = padding,
            location = fields,
            trackingEnabled = trackingEnabled.value,
            onToggleTracking = {
                if (trackingEnabled.value) {
                    // Stop tracking and immediately reset displayed fields to 'not available'
                    onStopTracking()
                    fields = CurrentLocation.notAvailable(context)
                } else {
                    requestOrStartTracking(
                        context = context,
                        onHasPermission = onStartTracking,
                        onRequestPermission = {
                            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        },
                    )
                }
            },
        )
    }
}

/**
 * Requests or starts tracking.
 *
 * @param context The context of the application.
 * @param onHasPermission The callback to be invoked when permission is granted.
 * @param onRequestPermission The callback to be invoked when permission is requested.
 */
private fun requestOrStartTracking(
    context: Context,
    onHasPermission: () -> Unit,
    onRequestPermission: () -> Unit,
) {
    val hasPermission = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION,
    ) == PackageManager.PERMISSION_GRANTED

    if (hasPermission) onHasPermission() else onRequestPermission()
}
