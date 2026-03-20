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
app/src/main/
├── java/dk/itu/moapd/opencv/
│   ├── camera/
│   │   └── CameraController.kt            # Camera initialization and lifecycle
│   ├── media/capture/
│   │   └── PhotoCaptureManager.kt         # Photo capture and storage logic
│   ├── permissions/
│   │   └── CameraPermissionHelper.kt      # Camera permission handling
│   └── ui/
│       ├── main/
│       │   ├── MainActivity.kt            # Entry point with Compose content
│       │   ├── MainScreen.kt              # Main camera Compose screen
│       │   └── MainViewModel.kt           # ViewModel for camera state
│       ├── session/
│       │   └── OpenCvSession.kt           # OpenCV camera view listener
│       ├── theme/
│       │   ├── Color.kt                   # Color definitions
│       │   ├── Theme.kt                   # Material theme setup
│       │   └── Type.kt                    # Typography definitions
│       ├── viewer/
│       │   └── ImageScreen.kt             # Composable processed image viewer
│       └── widgets/
│           └── OutlinedIconCircleButton.kt # Custom circular icon button
├── res/
│   ├── drawable/
│   │   ├── ic_launcher_background.xml     # Launcher icon background
│   │   └── ic_launcher_foreground.xml     # Launcher icon foreground
│   ├── values/
│   │   ├── colors.xml                     # Color resources
│   │   ├── dimens.xml                     # Dimension resources
│   │   ├── strings.xml                    # String resources
│   │   └── themes.xml                     # App theme
│   └── xml/
│       ├── backup_rules.xml               # Backup rules for Android 12+
│       └── data_extraction_rules.xml      # Data extraction rules
└── AndroidManifest.xml                    # App manifest with CAMERA permission
```

## Dependencies

- OpenCV 4.12.0 (local module)
- AndroidX Compose Navigation
- AndroidX Compose Material 3
- AndroidX Compose BOM
