# PalcoMP3 (Compose)

A Jetpack Compose **music player app** that consumes a REST API to fetch artist and song data, displays them in an expandable list, and plays audio tracks using a foreground service. The app demonstrates network operations with Volley, JSON parsing with Gson, reactive state management with `StateFlow`, and background audio playback with `MediaPlayer`.

> **See also:** [PalcoMP3 (MDC)](../10-2_PalcoMP3-MDC) — the same functionality with XML layouts and RecyclerView.

## Learning Outcomes

After studying this app, students will be able to:

- Consume REST APIs with the Volley HTTP library
- Parse JSON responses with Gson into Kotlin data classes
- Display data in expandable/collapsible list structures in Compose
- Manage complex UI state with `StateFlow` in a ViewModel
- Use `viewModelScope` for coroutine-based network operations
- Implement background audio playback with a foreground service and `MediaPlayer`
- Load and cache images with Coil Compose
- Build a media notification for foreground audio playback

## Architecture

**Pattern:** MVVM with Compose + Network Layer + Foreground Service

The `MainViewModel` fetches data from a REST API and manages the expandable list state. Audio playback is delegated to `AudioPlaybackService`.

| Class | Role |
|-------|------|
| `MainActivity` | Entry point, service lifecycle management |
| `MainScreen` | Compose UI with expandable artist/song rows |
| `MainViewModel` | API data fetching, StateFlow state management, expansion logic |
| `AudioPlaybackService` | Foreground service for MediaPlayer audio playback |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Volley | HTTP request library for REST API consumption |
| Gson | JSON serialization/deserialization |
| StateFlow | Reactive state management in ViewModel |
| MediaPlayer | Audio playback engine |
| Foreground Service | Persistent background audio playback |
| Coil Compose | Image loading and caching |
| Jetpack Compose | Declarative UI framework |
| Material Design 3 | UI components |

## How to Run

1. Open the `10-1_PalcoMP3` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Browse artists, expand to see songs, and tap to play audio.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/palcomp3/
│   ├── service/
│   │   └── AudioPlaybackService.kt      # Foreground service with MediaPlayer
│   └── ui/
│       ├── main/
│       │   ├── MainActivity.kt          # Entry point with service management
│       │   ├── MainScreen.kt           # Expandable list Compose UI
│       │   └── MainViewModel.kt        # API fetching and state management
│       └── model/
│           ├── ExpandableModel.kt       # Expandable list data model
│           └── SongModel.kt            # Song data model
├── AndroidManifest.xml                   # INTERNET, FOREGROUND_SERVICE permissions
└── res/
```

## Dependencies

- Volley
- Gson
- Coil Compose
- Picasso
- AndroidX Lifecycle ViewModel Compose
- AndroidX Compose Material 3
- Material Icons Extended
