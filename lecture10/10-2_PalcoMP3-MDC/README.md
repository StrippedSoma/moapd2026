# PalcoMP3 (MDC)

An Android **music player app** built with **XML layouts, RecyclerView, and Fragments**. The app consumes a REST API to fetch artist and song data, displays them in expandable lists, and plays audio with a foreground service. This is the Material Design Components variant.

> **See also:** [PalcoMP3 (Compose)](../10-1_PalcoMP3) — the same functionality with Jetpack Compose.

## Learning Outcomes

After studying this app, students will be able to:

- Consume REST APIs with Volley in a Fragment-based architecture
- Display hierarchical data in a RecyclerView with expandable/collapsible rows
- Implement a RecyclerView.Adapter for artist and song list items
- Parse JSON responses into data models with Gson
- Build a foreground service for background audio playback
- Load images with Picasso into RecyclerView item views
- Compare RecyclerView-based expandable lists with Compose expandable lists

## Architecture

**Pattern:** Single Activity + Fragment Navigation + Foreground Service

The `MainActivity` hosts fragments via Navigation component. The main fragment displays an expandable RecyclerView list of artists and songs.

| Class | Role |
|-------|------|
| `MainActivity` | Navigation host with app bar |
| `MainFragment` | RecyclerView with expandable artist/song list |
| `AudioPlaybackService` | Foreground service for audio playback |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Volley | REST API consumption |
| Gson | JSON parsing |
| RecyclerView | Scrollable list with expanded/collapsed rows |
| Picasso | Image loading and caching |
| MediaPlayer | Audio playback |
| Foreground Service | Background audio |
| AndroidX Navigation | Fragment navigation |
| View Binding | Type-safe view references |

## How to Run

1. Open the `10-2_PalcoMP3-MDC` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Browse artists, expand to see songs, and tap to play audio.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/palcomp3/
│   ├── app/
│   │   └── PalcoMP3Application.kt         # Application class setup
│   ├── domain/model/
│   │   ├── ArtistModel.kt                 # Data class for artist information
│   │   ├── ExpandableModel.kt             # Expandable list data model
│   │   └── SongModel.kt                   # Data class for song information
│   ├── service/
│   │   └── AudioPlaybackService.kt        # Foreground service with MediaPlayer
│   └── ui/
│       ├── list/
│       │   ├── ExpandableAdapter.kt       # RecyclerView expandable adapter
│       │   └── ItemClickListener.kt       # Click listener interface
│       ├── main/
│       │   ├── MainActivity.kt            # Navigation host activity
│       │   ├── MainFragment.kt            # RecyclerView with artists/songs
│       │   └── MainViewModel.kt           # ViewModel for data management
│       └── utils/
│           └── FragmentViewBindingDelegate.kt # View binding delegate for fragments
├── res/
│   ├── drawable/
│   │   ├── baseline_keyboard_arrow_down_24.xml # Arrow down icon
│   │   ├── baseline_keyboard_arrow_up_24.xml   # Arrow up icon
│   │   ├── baseline_play_circle_outline_64.xml # Play button icon
│   │   ├── baseline_stop_circle_64.xml    # Stop button icon
│   │   ├── ic_launcher_background.xml     # Launcher icon background
│   │   └── ic_launcher_foreground.xml     # Launcher icon foreground
│   ├── layout/
│   │   ├── activity_main.xml              # Main activity layout
│   │   ├── content_main.xml               # Content area with NavHostFragment
│   │   ├── fragment_main.xml              # Main fragment with RecyclerView
│   │   ├── row_artist_item.xml            # Artist row layout
│   │   └── row_song_item.xml              # Song row layout
│   ├── navigation/
│   │   └── nav_graph.xml                  # Navigation graph
│   ├── values-night/
│   │   └── themes.xml                     # Dark theme
│   ├── values/
│   │   ├── colors.xml                     # Color resources
│   │   ├── dimens.xml                     # Dimension resources
│   │   ├── strings.xml                    # String resources
│   │   └── themes.xml                     # App theme
│   └── xml/
│       ├── backup_rules.xml               # Backup rules for Android 12+
│       └── data_extraction_rules.xml      # Data extraction rules
└── AndroidManifest.xml                    # App manifest with INTERNET and FOREGROUND_SERVICE permissions
```

## Dependencies

- Volley
- Gson
- Picasso
- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- Material Components for Android
