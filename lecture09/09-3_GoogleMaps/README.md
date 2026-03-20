# Google Maps (Compose)

A Jetpack Compose app that integrates the **Google Maps SDK** using the **Maps Compose** library. The app displays an interactive map with markers, camera controls, and location awareness. Users can navigate the map, place markers, and view their current location on the map.

> **See also:** [Google Maps (MDC)](../09-4_GoogleMaps-MDC) — the same functionality with XML layouts and Fragments.

## Learning Outcomes

After studying this app, students will be able to:

- Set up a Google Maps API key and configure it in `AndroidManifest.xml`
- Use the Maps Compose library (`GoogleMap` composable) for map integration
- Place markers on the map with `Marker` and `MarkerState`
- Control camera position with `CameraPositionState` and `rememberCameraPositionState()`
- Request and handle location permissions for map-based apps
- Customize map settings (zoom controls, compass, map type)
- Compare Maps Compose with the traditional `SupportMapFragment` approach

## Architecture

**Pattern:** Single Activity with Compose Map Integration

The `MainActivity` hosts Compose content with a `GoogleMap` composable for map display.

| Class | Role |
|-------|------|
| `MainActivity` | Entry point with Google Maps Compose UI |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Google Maps Compose | Compose wrapper for Google Maps SDK |
| Google Maps SDK | Map rendering and interaction |
| Google Maps API Key | Authentication for map services |
| Jetpack Compose | Declarative UI framework |
| Location Permissions | ACCESS_FINE_LOCATION for user location |
| Material Design 3 | UI components |

## How to Run

### Prerequisites

1. Go to the [Google Cloud Console](https://console.cloud.google.com/) and enable the **Maps SDK for Android**.
2. Create an API key restricted to the Maps SDK.
3. Add the API key to the project. The key is typically configured in `AndroidManifest.xml` via a `<meta-data>` element or through a local properties file.

### Build and Run

1. Open the `09-3_GoogleMaps` project in **Android Studio**.
2. Configure your Google Maps API key.
3. Sync Gradle and let dependencies download.
4. Run the app on an emulator or physical device (min SDK 28).
5. Grant location permissions to see your position on the map.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/googlemaps/
│   ├── app/
│   │   └── GoogleMapsApplication.kt              # Application class with Maps SDK init
│   ├── core/
│   │   └── LoggingExtensions.kt                  # Extension function for log tags
│   └── ui/
│       ├── main/
│       │   ├── MainActivity.kt                   # Entry point with Compose content
│       │   └── MainScreen.kt                     # Main screen composable with GoogleMap
│       └── theme/
│           ├── Color.kt                          # Color definitions
│           ├── Theme.kt                          # Material 3 theme configuration
│           └── Type.kt                           # Typography definitions
├── res/
│   ├── drawable/
│   │   ├── ic_launcher_background.xml            # Launcher icon background
│   │   └── ic_launcher_foreground.xml            # Launcher icon foreground
│   ├── raw/
│   │   └── maps_style_json                       # Map style for light theme
│   ├── raw-night/
│   │   └── maps_style_json                       # Map style for dark theme
│   ├── values/
│   │   ├── colors.xml                            # Color resources
│   │   ├── strings.xml                           # String resources
│   │   └── themes.xml                            # App theme
│   └── xml/
│       ├── backup_rules.xml                      # Backup rules for Android 12+
│       └── data_extraction_rules.xml             # Data extraction rules
└── AndroidManifest.xml                           # Maps API key, location permissions
```

## Dependencies

- Google Maps Compose
- Google Maps SDK for Android
- AndroidX Activity Compose
- AndroidX Compose Material 3
- AndroidX Compose BOM
