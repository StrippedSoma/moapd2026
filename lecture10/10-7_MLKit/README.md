# ML Kit (Compose)

A Jetpack Compose app that demonstrates **on-device machine learning** using **Firebase ML Kit**. The app uses the device camera to capture images and applies ML Kit models for tasks such as image labeling, object detection, or text recognition — all running locally on the device without requiring a network connection for inference.

> **See also:** [ML Kit (MDC)](../10-8_MLKit-MDC) — the same functionality with XML layouts and Fragments.

## Learning Outcomes

After studying this app, students will be able to:

- Set up Firebase ML Kit for on-device inference
- Process camera frames with ML Kit vision models
- Display inference results (labels, bounding boxes, text) in Compose UI
- Integrate CameraX with ML Kit for real-time image analysis
- Understand the difference between on-device and cloud-based ML inference
- Handle the ML model download and initialization lifecycle

## Architecture

**Pattern:** Single Activity with Compose + ML Kit Pipeline

The app captures camera frames, passes them through ML Kit models, and displays results in Compose UI.

| Class | Role |
|-------|------|
| `MainActivity` | Entry point, ML Kit and camera integration |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Firebase ML Kit | On-device machine learning models |
| CameraX | Camera frame capture |
| InputImage | ML Kit image wrapper for analysis |
| Jetpack Compose | Declarative UI framework |
| Material Design 3 | UI components |

## How to Run

### Prerequisites

1. Go to the [Firebase Console](https://console.firebase.google.com/) and create a project.
2. Add an Android app with package name `dk.itu.moapd.mlkit`.
3. Download `google-services.json` and place it in the `app/` directory.

### Build and Run

1. Open the `10-7_MLKit` project in **Android Studio**.
2. Ensure `google-services.json` is in the `app/` directory.
3. Sync Gradle and let dependencies download.
4. Run the app on a **physical device** or emulator with camera support.
5. Grant the camera permission and point the camera at objects for ML analysis.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/mlkit/
│   └── ui/main/
│       └── MainActivity.kt              # ML Kit + CameraX + Compose UI
├── AndroidManifest.xml                   # CAMERA permission
└── google-services.json                  # Firebase config (not committed)
```

## Dependencies

- Firebase ML Kit
- AndroidX Camera Camera2
- AndroidX Camera Lifecycle
- AndroidX Camera View
- AndroidX Compose Material 3
- AndroidX Compose BOM
