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
package dk.itu.moapd.palcomp3.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dk.itu.moapd.palcomp3.R
import dk.itu.moapd.palcomp3.ui.main.MainActivity

/**
 * A service class with methods to play an audio in background.
 */
class AudioPlaybackService: Service() {

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "media_playback_channel"
    }

    /**
     * MediaPlayer instance used to control playback of audio/video files and streams.
     */
    private var mediaPlayer: MediaPlayer? = null

    /**
     * Called by the system when the service is first created. This is where we create the
     * notification channel for media playback notifications.
     */
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    /**
     * Called by the system every time a client explicitly starts the service by calling
     * `startService()`, providing the arguments it supplied and a unique integer token representing
     * the start request. Do not call this method directly.
     *
     * For backwards compatibility, the default implementation calls `onStart()` and returns either
     * `START_STICKY` or `START_STICKY_COMPATIBILITY`.
     *
     * Note that the system calls this on your service's main thread. A service's main thread is the
     * same thread where UI operations take place for Activities running in the same process. You
     * should always avoid stalling the main thread's event loop. When doing long-running
     * operations, network calls, or heavy disk I/O, you should kick off a new thread, or use
     * Kotlin Coroutines.
     *
     * @param intent The Intent supplied to `startService()`, as given. This may be `null` if the
     *      service is being restarted after its process has gone away, and it had previously
     *      returned anything except `START_STICKY_COMPATIBILITY`.
     * @param flags Additional data about this start request.
     * @param startId A unique integer representing this specific request to start. Use with
     *      `stopSelfResult(int)`.
     *
     * @return The return value indicates what semantics the system should use for the service's
     *      current started state. It may be one of the constants associated with the
     *      `START_CONTINUATION_MASK` bits.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Start the service as a foreground service with a notification
        startForeground(NOTIFICATION_ID, createNotification())
        
        // Get the url from the intent and play the audio.
        intent?.getStringExtra("url")?.let {
            playAudio(it)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * Plays audio from the specified URL using a MediaPlayer instance.
     *
     * @param url The URL of the audio file to be played.
     */
    private fun playAudio(url: String) {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener { start() }
        }
    }

    /**
     * Creates a notification channel for media playback notifications on Android O and above.
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_name)
            val descriptionText = getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Creates an ongoing notification for the foreground service.
     *
     * @return The notification to be displayed.
     */
    private fun createNotification(): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_text))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    /**
     * Return the communication channel to the service. May return `null` if clients can not bind to
     * the service. The returned `IBinder` is usually for a complex interface that has been
     * described using aidl.
     *
     * Note that unlike other application components, calls on to the `IBinder` interface returned
     * here may not happen on the main thread of the process. More information about the main thread
     * can be found in the official Android documentation (`Processes and Threads`).
     *
     * @param intent The `Intent` that was used to bind to this service, as given to
     *      `bindService()`. Note that any extras that were included with the `Intent` at that point
     *      will not be seen here.
     *
     * @return Return an `IBinder` through which clients can call on to the service.
     */
    override fun onBind(intent: Intent): IBinder? = null

    /**
     * Called by the system to notify a `Service` that it is no longer used and is being removed.
     * The service should clean up any resources it holds (threads, registered receivers, etc) at
     * this point. Upon return, there will be no more calls in to this `Service` object and it is
     * effectively dead. Do not call this method directly.
     */
    override fun onDestroy() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }

}
