# ML Kit (MDC)

An Android app that demonstrates **Firebase ML Kit** for on-device machine learning with a **Fragment-based UI**. The app captures camera frames and analyzes them using ML Kit vision models, displaying results in XML layouts.

> **See also:** [ML Kit (Compose)](../10-7_MLKit) — the same functionality with Jetpack Compose.

## Learning Outcomes

After studying this app, students will be able to:

- Integrate Firebase ML Kit with Fragment-based architecture
- Use CameraX `ImageAnalysis` use case for real-time frame processing
- Display ML inference results in XML layouts with View Binding
- Configure ML Kit models for on-device processing
- Navigate between camera and results screens with Navigation component
- Compare ML Kit integration in Fragment-based vs. Compose architectures

## Architecture

**Pattern:** Single Activity + Fragment Navigation + ML Kit

The `MainActivity` hosts fragments. Camera frames are analyzed by ML Kit models within the camera fragment.

| Class | Role |
|-------|------|
| `MainActivity` | Navigation host |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Firebase ML Kit | On-device ML inference |
| CameraX | Camera frame capture and analysis |
| AndroidX Navigation | Fragment navigation |
| View Binding | Type-safe view references |
| Material Components | UI styling |

## How to Run

### Prerequisites

1. Go to the [Firebase Console](https://console.firebase.google.com/) and create a project.
2. Add an Android app with package name `dk.itu.moapd.mlkit`.
3. Download `google-services.json` and place it in the `app/` directory.

### Build and Run

1. Open the `10-8_MLKit-MDC` project in **Android Studio**.
2. Ensure `google-services.json` is in the `app/` directory.
3. Sync Gradle and let dependencies download.
4. Run the app on a **physical device** or emulator with camera support.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/mlkit/
│   └── ui/main/
│       └── MainActivity.kt              # Navigation host
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   ├── content_main.xml
│   │   └── fragment_main.xml            # Camera + ML results layout
│   └── navigation/                       # Navigation graph
├── AndroidManifest.xml                   # CAMERA permission
└── google-services.json                  # Firebase config (not committed)
```

## Dependencies

- Firebase ML Kit
- AndroidX Camera Camera2
- AndroidX Camera Lifecycle
- AndroidX Camera View
- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- Material Components for Android
