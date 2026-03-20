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
│       │   ├── MainActivity.kt            # Navigation host activity with OpenCV init
│       │   ├── MainFragment.kt            # Camera preview fragment with OpenCV processing
│       │   └── MainViewModel.kt           # ViewModel for camera state
│       ├── utils/
│       │   └── FragmentViewBindingDelegate.kt # View binding delegate for fragments
│       └── viewer/
│           └── ImageFragment.kt           # Processed image viewer fragment
├── res/
│   ├── drawable/
│   │   ├── baseline_arrow_back_24.xml     # Back arrow icon
│   │   ├── baseline_circle_24.xml         # Capture button icon
│   │   ├── baseline_flip_camera_android_24.xml # Camera flip icon
│   │   ├── baseline_photo_24.xml          # Photo gallery icon
│   │   ├── ic_launcher_background.xml     # Launcher icon background
│   │   └── ic_launcher_foreground.xml     # Launcher icon foreground
│   ├── layout/
│   │   ├── activity_main.xml              # Main activity layout
│   │   ├── content_main.xml               # Content area with NavHostFragment
│   │   ├── fragment_image.xml             # Processed image layout
│   │   └── fragment_main.xml              # Camera preview layout
│   ├── navigation/
│   │   └── nav_graph.xml                  # Navigation graph
│   ├── values-night/
│   │   └── themes.xml                     # Dark theme
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
- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- Material Components for Android
