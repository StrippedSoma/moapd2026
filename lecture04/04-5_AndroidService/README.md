# Android Service

An Android app that demonstrates a **foreground Service** for background audio playback. The app plays music using `MediaPlayer` within a persistent foreground service that displays a notification, allowing audio to continue playing even when the user navigates away from the app. The service communicates its state back to the UI via `BroadcastReceiver`.

## Learning Outcomes

After studying this app, students will be able to:

- Create a foreground `Service` with a persistent notification
- Use `startForegroundService()` to launch a service on Android 8+ (API 26+)
- Build notifications with `NotificationCompat` and notification channels
- Play audio with `MediaPlayer` inside a service
- Communicate between a service and the UI using `BroadcastReceiver` and `IntentFilter`
- Manage the `POST_NOTIFICATIONS` runtime permission (Android 13+)
- Understand the lifecycle differences between Activities and Services

## Architecture

**Pattern:** Single Activity + Fragment + Foreground Service + ViewModel

The `MainFragment` provides Start/Stop controls. The `AudioPlaybackService` runs as a foreground service with a notification. The `MainViewModel` observes broadcast events from the service.

| Class | Role |
|-------|------|
| `MainActivity` | Container activity |
| `MainFragment` | UI with Start/Stop buttons for music playback |
| `MainViewModel` | Observes service state via BroadcastReceiver |
| `AudioPlaybackService` | Foreground service managing MediaPlayer audio playback |
| `ServiceDemoApplication` | Custom Application subclass |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Service | Background component for long-running tasks |
| startForegroundService() | Launches foreground service (API 26+) |
| NotificationCompat | Persistent notification for foreground service |
| MediaPlayer | Audio playback engine |
| BroadcastReceiver | Inter-component state communication |
| IntentFilter | Filters broadcast intents for specific actions |
| ViewModel | UI state management |
| View Binding | Type-safe view references |

## How to Run

1. Open the `04-5_AndroidService` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Grant the notification permission if prompted (Android 13+).
5. Tap **Start** to begin audio playback — a notification appears.
6. Tap **Stop** to end playback and dismiss the notification.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/androidservice/
│   ├── app/
│   │   └── ServiceDemoApplication.kt        # Application subclass
│   ├── service/
│   │   └── AudioPlaybackService.kt          # Foreground service with MediaPlayer
│   └── ui/main/
│       ├── MainActivity.kt                  # Container activity
│       ├── MainFragment.kt                  # Start/Stop UI controls
│       └── MainViewModel.kt                 # Observes service broadcasts
└── res/
    ├── layout/
    │   └── fragment_main.xml                # UI with playback controls
    └── raw/                                  # Audio resource files
```

## Dependencies

- AndroidX Lifecycle ViewModel KTX
- AndroidX AppCompat
- Material Components for Android
- AndroidX ConstraintLayout
