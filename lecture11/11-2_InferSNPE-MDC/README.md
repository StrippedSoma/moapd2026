# InferSNPE (MDC)

An Android app that demonstrates **Qualcomm SNPE neural network inference** with a **Fragment-based UI**. The app captures real-time camera frames with CameraX and processes them through a SNPE model running on the device's CPU, GPU, or DSP. Hilt manages the dependency injection of inference components.

> **See also:** [InferSNPE (Compose)](../11-1_InferSNPE) — the same functionality with Jetpack Compose.

## Learning Outcomes

After studying this app, students will be able to:

- Integrate SNPE inference with Fragment-based architecture
- Configure Hilt dependency injection in a Fragment lifecycle
- Display inference results in XML layouts with View Binding
- Process camera frames in a Fragment using CameraX `ImageAnalysis`
- Understand ARM64-specific build configuration for native models
- Compare SNPE integration in Fragment-based vs. Compose architectures

## Architecture

**Pattern:** Single Activity + Fragment Navigation + Hilt + SNPE

The `MainActivity` hosts fragments via Navigation component. Hilt provides inference dependencies.

| Class | Role |
|-------|------|
| `MainActivity` | Navigation host, Hilt injection |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Qualcomm SNPE | Neural network inference engine |
| CameraX | Camera frame capture |
| Hilt | Dependency injection |
| AndroidX Navigation | Fragment navigation |
| View Binding | Type-safe view references |
| Material Components | UI styling |

## How to Run

### Prerequisites

1. The SNPE SDK AAR and DLC model file must be available (see [InferSNPE (Compose)](../11-1_InferSNPE) for details).
2. Requires an **ARM64 physical device**.
3. Uses `targetSdk = 30` for SNPE compatibility.

### Build and Run

1. Open the `11-2_InferSNPE-MDC` project in **Android Studio**.
2. Ensure SNPE dependencies are in place.
3. Sync Gradle and let dependencies download.
4. Run the app on an **ARM64 physical device**.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/infersnpe/
│   └── ui/main/
│       └── MainActivity.kt              # Navigation host with Hilt
├── assets/                               # DLC model file
├── libs/
│   └── snpe-release.aar                 # Qualcomm SNPE library
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   ├── content_main.xml
│   │   └── fragment_main.xml            # Camera + inference results
│   └── navigation/                       # Navigation graph
└── AndroidManifest.xml                   # CAMERA permission
```

## Dependencies

- Qualcomm SNPE (local AAR)
- Hilt Android
- Hilt Compiler (KSP)
- AndroidX Camera Camera2
- AndroidX Camera Lifecycle
- AndroidX Camera View
- AndroidX Navigation Fragment KTX
- Material Components for Android
