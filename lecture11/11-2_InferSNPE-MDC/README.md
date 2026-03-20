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
│       │   ├── MainActivity.kt                               # Navigation host activity with Hilt
│       │   └── MainFragment.kt                               # Camera preview and inference fragment
│       └── utils/
│           └── FragmentViewBindingDelegate.kt                 # View Binding delegate for Fragments
├── assets/
│   ├── README.txt                                            # Instructions for model files
│   └── yolo_nas_s_int8.dlc                                   # YOLO-NAS INT8 model (DLC format)
├── libs/
│   ├── README.txt                                            # SNPE AAR setup instructions
│   └── snpe-release.aar                                      # Qualcomm SNPE runtime library
├── res/
│   ├── drawable/
│   │   ├── baseline_bg_chip.xml                              # Rounded chip background shape
│   │   ├── ic_launcher_background.xml                        # Launcher icon background
│   │   └── ic_launcher_foreground.xml                        # Launcher icon foreground
│   ├── layout/
│   │   ├── activity_main.xml                                 # Main activity with NavHostFragment
│   │   ├── content_main.xml                                  # Content area with navigation container
│   │   └── fragment_main.xml                                 # Camera preview and overlay layout
│   ├── navigation/
│   │   └── nav_graph.xml                                     # Navigation graph with main fragment
│   ├── values/
│   │   ├── colors.xml                                        # Color definitions
│   │   ├── dimens.xml                                        # Dimension values
│   │   ├── strings.xml                                       # String resources
│   │   └── themes.xml                                        # App theme
│   ├── values-land/
│   │   └── dimens.xml                                        # Landscape dimension overrides
│   ├── values-night/
│   │   └── themes.xml                                        # Dark mode theme
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
- AndroidX Navigation Fragment KTX
- Material Components for Android
