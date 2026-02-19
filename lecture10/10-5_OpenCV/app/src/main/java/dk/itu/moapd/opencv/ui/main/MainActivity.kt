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
package dk.itu.moapd.opencv.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dk.itu.moapd.opencv.ui.theme.OpenCVTheme
import dk.itu.moapd.opencv.ui.viewer.ImageScreen
import org.opencv.android.OpenCVLoader
import java.net.URLEncoder

/**
 * An activity class with several methods to manage the main activity of OpenCV application.
 */
class MainActivity : ComponentActivity() {
    /**
     * A set of static attributes used in this class.
     */
    companion object {
        /**
         * Tag for logging.
         */
        private val TAG = MainActivity::class.qualifiedName
    }

    /**
     * Called when the activity is starting. This is where most initialization should go: calling
     * `setContentView(int)` to inflate the activity's UI, using `findViewById()` to
     * programmatically interact with widgets in the UI, calling
     * `managedQuery(android.net.Uri, String[], String, String[], String)` to retrieve cursors for
     * data being displayed, etc.
     *
     * You can call `finish()` from within this function, in which case `onDestroy()` will be
     * immediately called after `onCreate()` without any of the rest of the activity lifecycle
     * (`onStart()`, `onResume()`, onPause()`, etc) executing.
     *
     * <em>Derived classes must call through to the super class's implementation of this method. If
     * they do not, an exception will be thrown.</em>
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut
     * down then this Bundle contains the data it most recently supplied in `onSaveInstanceState()`.
     * <b><i>Note: Otherwise it is null.</i></b>
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            OpenCVTheme {
                val navController = rememberNavController()
                val vm: MainViewModel = viewModel()

                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(
                            viewModel = vm,
                            onOpenLastPhoto = { uriString ->
                                navController.navigate("image/${URLEncoder.encode(uriString, "utf-8")}")
                            },
                            onFinish = { finish() },
                        )
                    }
                    composable("image/{uri}") { backStackEntry ->
                        val encoded = backStackEntry.arguments?.getString("uri").orEmpty()
                        val decoded = java.net.URLDecoder.decode(encoded, "utf-8")
                        ImageScreen(
                            uriString = decoded,
                            onBack = { navController.popBackStack() },
                        )
                    }
                }
            }
        }
    }

    /**
     * Called after `onStart()`, `onRestart()`, or `onPause()`, for your activity to start
     * interacting with the user. This is an indicator that the activity became active and ready to
     * receive input. It is on top of an activity stack and visible to user.
     *
     * On platform versions prior to `android.os.Build.VERSION_CODES#Q` this is also a good place to
     * try to open exclusive-access devices or to get access to singleton resources. Starting  with
     * `android.os.Build.VERSION_CODES#Q` there can be multiple resumed activities in the system
     * simultaneously, so `onTopResumedActivityChanged(boolean)` should be used for that purpose
     * instead.
     *
     * <em>Derived classes must call through to the super class's implementation of this method. If
     * they do not, an exception will be thrown.</em>
     */
    override fun onResume() {
        super.onResume()

        // Ensure OpenCV is available when resuming. If not, log and inform the user.
        if (OpenCVLoader.initLocal()) {
            Log.i(TAG, "OpenCV loaded successfully (onResume)")
        } else {
            Log.w(TAG, "OpenCV not loaded onResume")
        }
    }
}
