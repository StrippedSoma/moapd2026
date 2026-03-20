# MyLocation (MDC)

An Android app that demonstrates **real-time location tracking** using the **Fused Location Provider** with a **Fragment-based UI** and the **Navigation component**. The app requests location permissions, retrieves the device's position, and provides continuous updates through a foreground service.

> **See also:** [MyLocation (Compose)](../09-1_MyLocation) — the same functionality with Jetpack Compose.

## Learning Outcomes

After studying this app, students will be able to:

- Integrate Fused Location Provider with Fragment-based architecture
- Handle runtime permission requests from within Fragments
- Display location data in XML layouts with View Binding
- Use Kotlin Coroutines for asynchronous location operations
- Implement a foreground service for background location tracking
- Navigate between screens with the Navigation component
- Compare location API integration in Fragment vs. Compose architectures

## Architecture

**Pattern:** Single Activity + Fragment Navigation + Location Service

The `MainActivity` hosts a `NavHostFragment`. Fragments display location data obtained from the Fused Location Provider.

| Class | Role |
|-------|------|
| `MainActivity` | Navigation host with app bar |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Fused Location Provider | Google Play Services location API |
| Foreground Service | Background location tracking |
| Kotlin Coroutines | Asynchronous location operations |
| AndroidX Navigation | Fragment navigation |
| View Binding | Type-safe view references |
| Material Components | UI styling |

## How to Run

1. Open the `09-2_MyLocation-MDC` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Grant location permissions when prompted.
5. On an emulator, use Extended Controls → Location to simulate GPS coordinates.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/mylocation/
│   ├── app/
│   │   └── MyLocationApplication.kt             # Application class with initialization
│   ├── core/
│   │   ├── preferences/
│   │   │   └── LocationTrackingPreferences.kt    # Shared preferences for tracking settings
│   │   └── time/
│   │       └── TimeFormatExtensions.kt           # Long-to-date/time formatting extensions
│   ├── service/
│   │   └── LocationService.kt                   # Foreground service for location updates
│   └── ui/
│       ├── main/
│       │   ├── MainActivity.kt                   # Navigation host with app bar
│       │   └── MainFragment.kt                   # Fragment displaying location data
│       └── utils/
│           └── FragmentViewBindingDelegate.kt     # Delegate for Fragment view binding
├── res/
│   ├── drawable/
│   │   ├── ic_launcher_background.xml            # Launcher icon background
│   │   └── ic_launcher_foreground.xml            # Launcher icon foreground
│   ├── layout/
│   │   ├── activity_main.xml                     # Main activity layout with NavHostFragment
│   │   ├── content_main.xml                      # Content area within coordinator layout
│   │   └── fragment_main.xml                     # Location display fragment layout
│   ├── navigation/
│   │   └── nav_graph.xml                         # Navigation graph
│   ├── values/
│   │   ├── colors.xml                            # Color resources
│   │   ├── dimens.xml                            # Dimension resources
│   │   ├── strings.xml                           # String resources
│   │   └── themes.xml                            # App theme
│   ├── values-night/
│   │   └── themes.xml                            # Dark theme overrides
│   └── xml/
│       ├── backup_rules.xml                      # Backup rules for Android 12+
│       └── data_extraction_rules.xml             # Data extraction rules
└── AndroidManifest.xml                           # Location and foreground service permissions
```

## Dependencies

- Google Play Services Location
- Kotlin Coroutines Android
- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- Material Components for Android
