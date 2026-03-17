# InferTFLite (Compose)

A Jetpack Compose app that demonstrates **real-time neural network inference** using **TensorFlow Lite** with GPU acceleration. The app captures live camera frames with CameraX and processes them through a TFLite model for tasks such as image classification or object detection. The GPU delegate is used to accelerate inference on supported devices. Hilt manages the dependency injection.

> **See also:** [InferTFLite (MDC)](../11-4_InferTFLite-MDC) — the same functionality with XML layouts and Fragments.

## Learning Outcomes

After studying this app, students will be able to:

- Set up TensorFlow Lite interpreter with a pre-trained `.tflite` model
- Enable GPU acceleration using the TFLite GPU delegate
- Process CameraX frames as input tensors for TFLite models
- Display inference results (classifications, detections) in Compose UI
- Use Hilt for dependency injection of the model and interpreter
- Understand the TFLite model lifecycle: loading, inference, and resource cleanup
- Configure TFLite support libraries for input/output tensor processing

## Architecture

**Pattern:** MVVM with Hilt Dependency Injection + TFLite Inference

Hilt manages the TFLite interpreter and GPU delegate. CameraX provides real-time frames processed through the model via the ViewModel.

| Class | Role |
|-------|------|
| `MainActivity` | Entry point, Hilt injection host |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| TensorFlow Lite | On-device ML inference runtime |
| TFLite GPU Delegate | GPU-accelerated inference |
| TFLite Support Library | Tensor processing utilities |
| CameraX | Real-time camera frame capture |
| Hilt | Dependency injection |
| Jetpack Compose | Declarative UI framework |
| Material Design 3 | UI components |

## How to Run

### Prerequisites

1. A pre-trained `.tflite` model file must be placed in the `assets/` directory.
2. For GPU acceleration, ensure the device supports OpenGL ES 3.1 or OpenCL.

### Build and Run

1. Open the `11-3_InferTFLite` project in **Android Studio**.
2. Ensure the TFLite model file is in the `assets/` directory.
3. Sync Gradle and let dependencies download.
4. Run the app on a physical device or emulator (min SDK 28).
5. Grant the camera permission and view real-time inference results.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/infertflite/
│   └── ui/main/
│       └── MainActivity.kt              # Hilt + TFLite + CameraX + Compose UI
├── assets/                               # TFLite model file (.tflite)
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
- AndroidX Compose Material 3
