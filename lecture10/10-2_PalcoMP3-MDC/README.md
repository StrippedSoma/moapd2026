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
│   ├── service/
│   │   └── AudioPlaybackService.kt      # Foreground audio service
│   └── ui/
│       ├── main/
│       │   ├── MainActivity.kt          # Navigation host
│       │   └── MainFragment.kt         # RecyclerView with artists/songs
│       └── list/
│           └── CustomAdapter.kt         # RecyclerView adapter
├── res/
│   └── layout/
│       ├── activity_main.xml
│       ├── content_main.xml
│       ├── fragment_main.xml
│       ├── row_artist_item.xml           # Artist row layout
│       └── row_song_item.xml            # Song row layout
└── AndroidManifest.xml                   # INTERNET, FOREGROUND_SERVICE permissions
```

## Dependencies

- Volley
- Gson
- Picasso
- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- Material Components for Android
