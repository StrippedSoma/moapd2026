# OpenCV (MDC)

An Android app that demonstrates **OpenCV 4.12 image processing** with a **Fragment-based UI**. The app processes camera frames or images using OpenCV's native library, with navigation between camera and image views managed by the Navigation component.

> **See also:** [OpenCV (Compose)](../10-5_OpenCV) — the same functionality with Jetpack Compose.

## Learning Outcomes

After studying this app, students will be able to:

- Set up OpenCV with a Fragment-based Android architecture
- Process images displayed in `ImageView` using OpenCV operations
- Navigate between camera and processed image fragments
- Initialize and verify OpenCV library loading in the Activity lifecycle
- Compare OpenCV integration in Fragment-based vs. Compose architectures

## Architecture

**Pattern:** Single Activity + Fragment Navigation + OpenCV Module

The `MainActivity` hosts fragments for camera preview and processed image display.

| Class | Role |
|-------|------|
| `MainActivity` | Navigation host, OpenCV initialization |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| OpenCV 4.12 | Computer vision library |
| CameraX | Camera frame capture |
| AndroidX Navigation | Fragment navigation |
| View Binding | Type-safe view references |
| Material Components | UI styling |

## How to Run

### Prerequisites

This project includes OpenCV 4.12 as a local library module.

### Build and Run

1. Open the `10-6_OpenCV-MDC` project in **Android Studio**.
2. Ensure the `opencv-4.12.0` module is available in the project settings.
3. Sync Gradle and let dependencies download.
4. Run the app on a **physical device** or emulator with camera support.

## Project Structure

```
10-6_OpenCV-MDC/
├── app/src/main/
│   ├── java/dk/itu/moapd/opencv/
│   │   └── ui/main/
│   │       └── MainActivity.kt          # Navigation host, OpenCV init
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml
│   │   │   ├── content_main.xml
│   │   │   ├── fragment_main.xml        # Camera preview
│   │   │   └── fragment_image.xml       # Processed image display
│   │   └── navigation/                   # Navigation graph
│   └── AndroidManifest.xml              # CAMERA permission
├── opencv-4.12.0/                        # Local OpenCV library module
└── settings.gradle.kts                   # Includes opencv module
```

## Dependencies

- OpenCV 4.12.0 (local module)
- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- Material Components for Android
