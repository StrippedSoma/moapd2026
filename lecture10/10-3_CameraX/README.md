# CameraX (Compose)

A Jetpack Compose app that demonstrates **photo capture** using the **CameraX** library. The app provides a live camera preview and allows users to take photos, which are saved to the device's storage. CameraX abstracts away device-specific camera differences and integrates cleanly with Compose via the `PreviewView` embedded in a Compose layout.

> **See also:** [CameraX (MDC)](../10-4_CameraX-MDC) — the same functionality with XML layouts and Fragments.

## Learning Outcomes

After studying this app, students will be able to:

- Set up CameraX with `ProcessCameraProvider` and use cases (Preview, ImageCapture)
- Display a live camera preview in Compose using `AndroidView` with `PreviewView`
- Capture photos with `ImageCapture.takePicture()` and handle the result
- Request and handle the `CAMERA` runtime permission
- Bind camera use cases to the Activity lifecycle with `cameraProvider.bindToLifecycle()`
- Navigate between camera and image preview screens with Compose Navigation
- Load captured images with Coil Compose

## Architecture

**Pattern:** Single Activity with Compose Navigation and CameraX

The app uses Compose Navigation for switching between the camera preview screen and the captured image screen.

| Class | Role |
|-------|------|
| `MainActivity` | Entry point, CameraX lifecycle binding |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| CameraX | Abstracted camera API for preview and capture |
| Camera2 | Underlying camera implementation |
| ImageCapture | CameraX use case for photo capture |
| Preview | CameraX use case for live viewfinder |
| Coil Compose | Displaying captured images |
| Compose Navigation | Screen navigation |
| Jetpack Compose | Declarative UI framework |
| Material Design 3 | UI components |

## How to Run

1. Open the `10-3_CameraX` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on a **physical device** (camera features require real hardware) or emulator with camera support.
4. Grant the camera permission when prompted.
5. Tap the capture button to take a photo and view it.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/camerax/
│   ├── camerax/
│   │   └── CameraXController.kt           # CameraX initialization and lifecycle
│   ├── media/capture/
│   │   └── PhotoCaptureManager.kt         # Photo capture and storage logic
│   ├── permissions/
│   │   └── CameraPermissionHelper.kt      # Camera permission handling
│   └── ui/
│       ├── main/
│       │   ├── MainActivity.kt            # Entry point with Compose content
│       │   ├── MainScreen.kt              # Main camera Compose screen
│       │   ├── MainViewModel.kt           # ViewModel for camera state
│       │   └── components/
│       │       └── CameraXApp.kt          # Composable camera preview component
│       ├── theme/
│       │   ├── Color.kt                   # Color definitions
│       │   ├── Theme.kt                   # Material theme setup
│       │   └── Type.kt                    # Typography definitions
│       ├── viewer/
│       │   └── ImageScreen.kt             # Composable captured image viewer
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

- AndroidX Camera Camera2
- AndroidX Camera Lifecycle
- AndroidX Camera View
- Coil Compose
- AndroidX Compose Navigation
- AndroidX Compose Material 3
