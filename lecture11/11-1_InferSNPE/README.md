# InferSNPE (Compose)

A Jetpack Compose app that demonstrates **real-time neural network inference** using the **Qualcomm Snapdragon Neural Processing Engine (SNPE)**. The app uses CameraX to capture live camera frames and processes them through a pre-trained deep learning model running on the device's CPU, GPU, or DSP via the SNPE runtime. Hilt is used for dependency injection to manage model initialization and inference pipeline components.

> **See also:** [InferSNPE (MDC)](../11-2_InferSNPE-MDC) — the same functionality with XML layouts and Fragments.

## Learning Outcomes

After studying this app, students will be able to:

- Set up the Qualcomm SNPE SDK as a local AAR dependency
- Load and initialize a DLC (Deep Learning Container) model file
- Run inference on the device's CPU, GPU, or DSP runtime
- Process CameraX frames for real-time model input
- Use Hilt for dependency injection in an Android ML pipeline
- Configure native library packaging with `jniLibs.useLegacyPackaging`
- Target ARM64 architecture for on-device inference
- Display inference results (labels, confidence scores) in Compose UI

## Architecture

**Pattern:** MVVM with Hilt Dependency Injection + SNPE Inference

Hilt manages the creation and lifecycle of the SNPE model wrapper and inference pipeline. CameraX provides real-time frames that are fed into the model through the ViewModel.

| Class | Role |
|-------|------|
| `MainActivity` | Entry point, Hilt injection host |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Qualcomm SNPE | On-device neural network inference engine |
| DLC Model Format | SNPE's compiled model format |
| CameraX | Real-time camera frame capture |
| Hilt | Dependency injection framework |
| JNI / Native Libs | SNPE native library loading |
| Jetpack Compose | Declarative UI framework |
| Material Design 3 | UI components |

## How to Run

### Prerequisites

1. The SNPE SDK AAR (`snpe-release.aar`) must be available in the project's local library directory.
2. A pre-trained DLC model file must be placed in the assets directory.
3. This app targets **ARM64 devices** — it will not run on x86 emulators.
4. The app uses `targetSdk = 30` for SNPE compatibility.

### Build and Run

1. Open the `11-1_InferSNPE` project in **Android Studio**.
2. Ensure the SNPE AAR and model file are in place.
3. Sync Gradle and let dependencies download.
4. Run the app on an **ARM64 physical device** (e.g., a Snapdragon-powered phone).
5. Grant the camera permission and view real-time inference results.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/infersnpe/
│   └── ui/main/
│       └── MainActivity.kt              # Hilt injection + SNPE inference + Compose UI
├── assets/                               # DLC model file
├── libs/
│   └── snpe-release.aar                 # Qualcomm SNPE library
├── jniLibs/arm64-v8a/                    # SNPE native libraries for ARM64
└── AndroidManifest.xml                   # CAMERA permission
```

## Dependencies

- Qualcomm SNPE (local AAR)
- Hilt Android
- Hilt Compiler (KSP)
- AndroidX Camera Camera2
- AndroidX Camera Lifecycle
- AndroidX Camera View
- AndroidX Compose Material 3
