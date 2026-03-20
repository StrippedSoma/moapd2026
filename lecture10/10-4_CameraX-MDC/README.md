# CameraX (MDC)

An Android app that demonstrates **CameraX photo capture** with a **Fragment-based UI**. The app provides a live camera preview in a `PreviewView` and lets users capture photos, navigating between the camera and image preview screens using the Navigation component.

> **See also:** [CameraX (Compose)](../10-3_CameraX) — the same functionality with Jetpack Compose.

## Learning Outcomes

After studying this app, students will be able to:

- Set up CameraX with Fragments and bind to `viewLifecycleOwner`
- Use `PreviewView` directly in an XML layout for camera preview
- Navigate between camera and image fragments with Navigation component
- Handle the CAMERA permission in a Fragment lifecycle
- Compare CameraX integration in Fragment-based vs. Compose architectures

## Architecture

**Pattern:** Single Activity + Fragment Navigation + CameraX

The `MainActivity` hosts fragments. Camera functionality is in a dedicated fragment, and a separate fragment displays captured images.

| Class | Role |
|-------|------|
| `MainActivity` | Navigation host with app bar |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| CameraX | Camera preview and capture |
| Camera2 | Camera implementation backend |
| PreviewView | Camera preview widget for XML layouts |
| AndroidX Navigation | Fragment navigation |
| View Binding | Type-safe view references |
| Material Components | UI styling |

## How to Run

1. Open the `10-4_CameraX-MDC` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on a **physical device** or emulator with camera support.
4. Grant the camera permission when prompted.

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
│       │   ├── MainActivity.kt            # Navigation host activity
│       │   ├── MainFragment.kt            # Camera preview fragment
│       │   └── MainViewModel.kt           # ViewModel for camera state
│       ├── utils/
│       │   └── FragmentViewBindingDelegate.kt # View binding delegate for fragments
│       └── viewer/
│           └── ImageFragment.kt           # Captured image viewer fragment
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
│   │   ├── fragment_image.xml             # Captured image layout
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

- AndroidX Camera Camera2
- AndroidX Camera Lifecycle
- AndroidX Camera View
- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- Material Components for Android
