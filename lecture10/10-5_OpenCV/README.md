# OpenCV (Compose)

A Jetpack Compose app that demonstrates **computer vision and image processing** using **OpenCV 4.12** on Android. The app processes camera frames or images using OpenCV's native C++ library, applying filters, edge detection, or other image transformations. OpenCV is included as a local Android library module.

> **See also:** [OpenCV (MDC)](../10-6_OpenCV-MDC) — the same functionality with XML layouts and Fragments.

## Learning Outcomes

After studying this app, students will be able to:

- Integrate OpenCV as a local library module in an Android project
- Initialize OpenCV with `OpenCVLoader.initLocal()` or `OpenCVLoader.initDebug()`
- Apply image processing operations using OpenCV `Mat` objects
- Convert between Android `Bitmap` and OpenCV `Mat` formats
- Process camera frames for real-time computer vision
- Navigate between camera and processed image screens with Compose Navigation
- Understand native library loading and JNI integration on Android

## Architecture

**Pattern:** Single Activity with Compose Navigation + OpenCV Module

The app loads the OpenCV native library and uses it for image processing operations within Compose screens.

| Class | Role |
|-------|------|
| `MainActivity` | Entry point, OpenCV initialization, Compose content |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| OpenCV 4.12 | Computer vision and image processing library |
| Mat | OpenCV's core image data structure |
| CameraX | Camera frame capture |
| Compose Navigation | Screen navigation |
| Jetpack Compose | Declarative UI framework |
| Material Design 3 | UI components |

## How to Run

### Prerequisites

This project includes OpenCV 4.12 as a local library module. The module should be present at the project level.

### Build and Run

1. Open the `10-5_OpenCV` project in **Android Studio**.
2. Ensure the `opencv-4.12.0` module is available in the project settings.
3. Sync Gradle and let dependencies download.
4. Run the app on a **physical device** or emulator with camera support.
5. Grant the camera permission when prompted.

## Project Structure

```
10-5_OpenCV/
├── app/src/main/
│   ├── java/dk/itu/moapd/opencv/
│   │   └── ui/main/
│   │       └── MainActivity.kt          # OpenCV init + Compose UI
│   └── AndroidManifest.xml              # CAMERA permission
├── opencv-4.12.0/                        # Local OpenCV library module
├── build.gradle.kts
└── settings.gradle.kts                   # Includes opencv module
```

## Dependencies

- OpenCV 4.12.0 (local module)
- AndroidX Compose Navigation
- AndroidX Compose Material 3
- AndroidX Compose BOM
