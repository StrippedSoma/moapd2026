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
│   └── ui/main/
│       └── MainActivity.kt              # Navigation host
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   ├── content_main.xml
│   │   ├── fragment_main.xml            # Camera preview layout
│   │   └── fragment_image.xml           # Captured image layout
│   └── navigation/                       # Navigation graph
└── AndroidManifest.xml                   # CAMERA permission
```

## Dependencies

- AndroidX Camera Camera2
- AndroidX Camera Lifecycle
- AndroidX Camera View
- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- Material Components for Android
