# Google Maps (MDC)

An Android app that integrates the **Google Maps SDK** with a **Fragment-based UI**. The app uses `SupportMapFragment` to display an interactive map within the Navigation framework, supporting markers, camera controls, and location awareness.

> **See also:** [Google Maps (Compose)](../09-3_GoogleMaps) — the same functionality with Jetpack Compose and Maps Compose library.

## Learning Outcomes

After studying this app, students will be able to:

- Integrate Google Maps SDK with `SupportMapFragment` in a Fragment-based app
- Implement `OnMapReadyCallback` to configure the map after initialization
- Handle the asynchronous map loading lifecycle
- Place markers and control camera position programmatically
- Set up Google Maps API key in `AndroidManifest.xml`
- Combine map functionality with Navigation component
- Compare `SupportMapFragment` (XML) with `GoogleMap` composable (Compose)

## Architecture

**Pattern:** Single Activity + Fragment Navigation + Map Fragment

The `MainActivity` hosts a `NavHostFragment`. A map fragment integrates `SupportMapFragment` for map display.

| Class | Role |
|-------|------|
| `MainActivity` | Navigation host with app bar |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Google Maps SDK | Map rendering and interaction |
| SupportMapFragment | Fragment hosting the map view |
| OnMapReadyCallback | Asynchronous map initialization |
| Google Maps API Key | Maps service authentication |
| AndroidX Navigation | Fragment navigation |
| View Binding | Type-safe view references |
| Material Components | UI styling |

## How to Run

### Prerequisites

1. Go to the [Google Cloud Console](https://console.cloud.google.com/) and enable the **Maps SDK for Android**.
2. Create an API key restricted to the Maps SDK.
3. Add the API key to `AndroidManifest.xml` or a local properties file.

### Build and Run

1. Open the `09-4_GoogleMaps-MDC` project in **Android Studio**.
2. Configure your Google Maps API key.
3. Sync Gradle and let dependencies download.
4. Run the app on an emulator or physical device (min SDK 28).

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/googlemaps/
│   └── ui/main/
│       └── MainActivity.kt              # Navigation host
├── res/
│   ├── layout/
│   │   ├── activity_main.xml            # Main layout
│   │   ├── content_main.xml             # Content area
│   │   └── fragment_main.xml            # Map fragment layout
│   └── navigation/                       # Navigation graph
└── AndroidManifest.xml                   # Maps API key, location permissions
```

## Dependencies

- Google Play Services Maps
- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- Material Components for Android
