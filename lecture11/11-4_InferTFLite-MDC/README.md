# InferTFLite (MDC)

An Android app that demonstrates **TensorFlow Lite inference with GPU acceleration** using a **Fragment-based UI**. The app captures real-time camera frames with CameraX and processes them through a TFLite model, displaying results in XML layouts. Hilt manages the injection of inference components.

> **See also:** [InferTFLite (Compose)](../11-3_InferTFLite) — the same functionality with Jetpack Compose.

## Learning Outcomes

After studying this app, students will be able to:

- Integrate TensorFlow Lite with Fragment-based architecture
- Configure the TFLite GPU delegate within Fragment lifecycle
- Display inference results in XML layouts with View Binding
- Use CameraX `ImageAnalysis` for frame-by-frame model input in Fragments
- Manage Hilt-injected dependencies in Fragment scope
- Compare TFLite integration in Fragment-based vs. Compose architectures

## Architecture

**Pattern:** Single Activity + Fragment Navigation + Hilt + TFLite

The `MainActivity` hosts fragments via Navigation component. Hilt provides the TFLite interpreter and GPU delegate instances.

| Class | Role |
|-------|------|
| `MainActivity` | Navigation host, Hilt injection |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| TensorFlow Lite | ML inference runtime |
| TFLite GPU Delegate | GPU-accelerated inference |
| TFLite Support | Tensor processing |
| CameraX | Camera frame capture |
| Hilt | Dependency injection |
| AndroidX Navigation | Fragment navigation |
| View Binding | Type-safe view references |
| Material Components | UI styling |

## How to Run

### Prerequisites

1. A `.tflite` model file must be in the `assets/` directory.
2. For GPU acceleration, ensure the device supports OpenGL ES 3.1 or OpenCL.

### Build and Run

1. Open the `11-4_InferTFLite-MDC` project in **Android Studio**.
2. Ensure the TFLite model file is in the `assets/` directory.
3. Sync Gradle and let dependencies download.
4. Run the app on a physical device or emulator (min SDK 28).

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/infertflite/
│   └── ui/main/
│       └── MainActivity.kt              # Navigation host with Hilt
├── assets/                               # TFLite model file
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   ├── content_main.xml
│   │   └── fragment_main.xml            # Camera + inference results
│   └── navigation/                       # Navigation graph
└── AndroidManifest.xml                   # CAMERA permission
```

## Dependencies

- TensorFlow Lite
- TensorFlow Lite GPU
- TensorFlow Lite GPU API
- TensorFlow Lite GPU Delegate Plugin
- TensorFlow Lite Support
- Hilt Android
- Hilt Compiler (KSP)
- AndroidX Camera Camera2
- AndroidX Camera Lifecycle
- AndroidX Camera View
- AndroidX Navigation Fragment KTX
- Material Components for Android
