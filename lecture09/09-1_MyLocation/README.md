# MyLocation (Compose)

A Jetpack Compose app that demonstrates **real-time location tracking** using the **Fused Location Provider** from Google Play Services. The app requests location permissions at runtime, retrieves the device's current position, and tracks location updates continuously using a foreground service. Location data is displayed in the Compose UI.

> **See also:** [MyLocation (MDC)](../09-2_MyLocation-MDC) — the same functionality with XML layouts and Fragments.

## Learning Outcomes

After studying this app, students will be able to:

- Use `FusedLocationProviderClient` to access the device's location
- Request and handle `ACCESS_FINE_LOCATION` and `ACCESS_COARSE_LOCATION` runtime permissions
- Display permission rationale dialogs to the user
- Implement continuous location updates with a foreground service
- Display real-time location data (latitude, longitude, accuracy) in Compose UI
- Understand the differences between last-known location and continuous updates
- Handle location permission denial gracefully

## Architecture

**Pattern:** MVVM with Compose + Location Service

The `ViewModel` manages location state. A foreground service provides continuous location updates that flow to the UI through reactive state.

| Class | Role |
|-------|------|
| `MainActivity` | Entry point, permission handling, Compose content |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Fused Location Provider | Google Play Services location API |
| Foreground Service | Continuous location tracking in background |
| Runtime Permissions | ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION |
| Jetpack Compose | Declarative UI framework |
| Material Design 3 | UI components |

## How to Run

1. Open the `09-1_MyLocation` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Grant location permissions when prompted.
5. On an emulator, use Extended Controls → Location to simulate GPS coordinates.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/mylocation/
│   └── ui/main/
│       └── MainActivity.kt              # Location tracking with Compose UI
├── AndroidManifest.xml                   # Location and foreground service permissions
└── res/
```

## Dependencies

- Google Play Services Location
- AndroidX Activity Compose
- AndroidX Compose Material 3
- AndroidX Lifecycle Runtime KTX
