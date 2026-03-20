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
│   ├── app/
│   │   └── InferSnpeApplication.kt                           # Hilt application class
│   ├── di/
│   │   ├── detector/
│   │   │   ├── DetectorConfigModule.kt                       # Hilt module for detector configuration
│   │   │   ├── DetectorMlBindingsModule.kt                   # Hilt module for ML interface bindings
│   │   │   └── DetectorProcessingModule.kt                   # Hilt module for frame processing deps
│   │   └── ml/
│   │       ├── MlModelsModule.kt                             # Hilt module for ML model providers
│   │       └── MlQualifiers.kt                               # Hilt qualifier annotations for ML
│   ├── feature/detector/
│   │   ├── application/
│   │   │   ├── geometry/
│   │   │   │   └── BoundingBoxExtensions.kt                  # BoundingBox ↔ RectF conversions
│   │   │   ├── DetectorController.kt                         # Orchestrates detector lifecycle
│   │   │   ├── DetectorDetectionsMapper.kt                   # Maps raw detections to domain models
│   │   │   ├── DetectorRuntimeState.kt                       # Runtime state data class
│   │   │   ├── FrameProcessResult.kt                         # Frame processing result data
│   │   │   └── FrameProcessor.kt                             # Processes camera frames through pipeline
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   │   ├── BoundingBox.kt                            # Bounding box domain model
│   │   │   │   └── ObjectDetection.kt                        # Object detection domain model
│   │   │   └── ports/
│   │   │       ├── FrameAnalyzer.kt                          # Frame analyzer port interface
│   │   │       └── FrameSource.kt                            # Frame source port interface
│   │   ├── infra/
│   │   │   ├── camera/
│   │   │   │   ├── bind/
│   │   │   │   │   └── DetectorCameraBinder.kt               # Binds CameraX to detector pipeline
│   │   │   │   ├── controller/
│   │   │   │   │   └── CameraXCameraController.kt            # CameraX lifecycle controller
│   │   │   │   └── model/
│   │   │   │       ├── CameraStartResult.kt                  # Camera start result sealed interface
│   │   │   │       ├── DetectorCameraStartRequest.kt         # Camera start request data
│   │   │   │       ├── DetectorFramePayload.kt               # Camera frame payload data
│   │   │   │       └── FrameInfo.kt                          # Frame metadata info
│   │   │   ├── image/
│   │   │   │   ├── FrameTransformer.kt                       # Transforms frames for model input
│   │   │   │   └── ImageProxyToBitmapConverter.kt            # Converts ImageProxy to Bitmap
│   │   │   └── perf/
│   │   │       └── FPSTracker.kt                             # Tracks frames per second
│   │   ├── overlay/
│   │   │   ├── model/
│   │   │   │   └── OverlayPrimitives.kt                      # Overlay drawing primitives
│   │   │   ├── paints/
│   │   │   │   ├── OverlayPaintFactory.kt                    # Creates Paint objects for overlay
│   │   │   │   └── OverlayPaints.kt                          # Paint configuration data class
│   │   │   ├── renderer/
│   │   │   │   ├── ObjectOverlayRenderer.kt                  # Renders object detection overlays
│   │   │   │   └── OverlayRenderer.kt                        # Overlay renderer interface
│   │   │   └── OverlayCoordinateMapper.kt                    # Maps detection coords to overlay
│   │   └── presentation/
│   │       ├── config/
│   │       │   ├── DetectorCameraConfig.kt                   # Camera configuration data
│   │       │   └── OverlayConfig.kt                          # Overlay style and size constants
│   │       ├── state/
│   │       │   └── DetectorState.kt                          # UI state for detector screen
│   │       └── ui/
│   │           ├── viewmodel/
│   │           │   ├── DetectorStateMapper.kt                # Maps runtime state to UI state
│   │           │   └── DetectorViewModel.kt                  # ViewModel for detector screen
│   │           └── ResultsOverlayView.kt                     # Custom View for drawing results
│   ├── ml/
│   │   ├── api/
│   │   │   ├── Detector.kt                                   # Generic detector interface
│   │   │   ├── InferenceEngine.kt                            # Inference engine interface
│   │   │   ├── Pipeline.kt                                   # ML pipeline interface
│   │   │   └── TensorOutputs.kt                              # Type alias for SNPE tensor outputs
│   │   ├── config/
│   │   │   ├── ModelConfig.kt                                # Model configuration data class
│   │   │   └── ModelRegistry.kt                              # Registry of available models
│   │   ├── detectors/
│   │   │   └── ObjectDetector.kt                             # Object detector implementation
│   │   ├── engine/
│   │   │   └── SnpeModel.kt                                  # SNPE model wrapper
│   │   ├── models/labels/
│   │   │   └── Coco80Labels.kt                               # COCO 80-class label list
│   │   ├── pipeline/
│   │   │   ├── DetectionPipeline.kt                          # Detection pipeline implementation
│   │   │   ├── DetectionPipelineConfig.kt                    # Pipeline configuration data class
│   │   │   └── DetectionPipelineResult.kt                    # Pipeline result data class
│   │   ├── postprocessor/
│   │   │   ├── common/
│   │   │   │   ├── DetectionUtils.kt                         # NMS and detection utility functions
│   │   │   │   ├── NmsConfig.kt                              # Non-maximum suppression config
│   │   │   │   └── PostProcessor.kt                          # Post-processor interface
│   │   │   ├── ObjectPostProcessor.kt                        # Post-processes raw detections
│   │   │   └── ObjectPostProcessorConfig.kt                  # Post-processor configuration
│   │   ├── preprocess/
│   │   │   └── BitmapRgbFloatPreprocessor.kt                 # Preprocesses bitmaps to float arrays
│   │   └── results/
│   │       └── ObjectResult.kt                               # Object detection result data class
│   └── ui/
│       ├── main/
│       │   ├── components/
│       │   │   ├── ConfidenceThresholdSlider.kt              # Confidence threshold slider composable
│       │   │   └── FpsIndicator.kt                           # FPS display composable
│       │   ├── MainActivity.kt                               # Hilt entry point with Compose UI
│       │   ├── MainCameraLifecycleHandler.kt                 # Manages camera lifecycle in Compose
│       │   ├── MainContent.kt                                # Main layout composable
│       │   ├── MainOverlayStyle.kt                           # Configures overlay view style
│       │   ├── MainScreen.kt                                 # Top-level screen composable
│       │   └── MainViewFactory.kt                            # Factory for camera and overlay views
│       └── theme/
│           ├── Color.kt                                      # Color definitions
│           ├── Theme.kt                                      # Material 3 Compose theme
│           └── Type.kt                                       # Typography definitions
├── assets/
│   ├── README.txt                                            # Instructions for model files
│   └── yolo_nas_s_int8.dlc                                   # YOLO-NAS INT8 model (DLC format)
├── libs/
│   ├── README.txt                                            # SNPE AAR setup instructions
│   └── snpe-release.aar                                      # Qualcomm SNPE runtime library
├── res/
│   ├── drawable/
│   │   ├── ic_launcher_background.xml                        # Launcher icon background
│   │   └── ic_launcher_foreground.xml                        # Launcher icon foreground
│   ├── values/
│   │   ├── colors.xml                                        # Color definitions
│   │   ├── strings.xml                                       # String resources
│   │   └── themes.xml                                        # App theme
│   └── xml/
│       ├── backup_rules.xml                                  # Backup rules for Android 12+
│       └── data_extraction_rules.xml                         # Data extraction rules
└── AndroidManifest.xml                                       # App manifest with camera permission
```

## Dependencies

- Qualcomm SNPE (local AAR)
- Hilt Android
- Hilt Compiler (KSP)
- AndroidX Camera Camera2
- AndroidX Camera Lifecycle
- AndroidX Camera View
- AndroidX Compose Material 3
