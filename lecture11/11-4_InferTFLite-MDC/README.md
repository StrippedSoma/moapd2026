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
│   ├── app/
│   │   └── InferTfLiteApplication.kt                         # Hilt application class
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
│   │   │   └── TensorOutputs.kt                              # Type alias for TFLite tensor outputs
│   │   ├── config/
│   │   │   ├── ModelConfig.kt                                # Model configuration data class
│   │   │   └── ModelRegistry.kt                              # Registry of available models
│   │   ├── detectors/
│   │   │   └── ObjectDetector.kt                             # Object detector implementation
│   │   ├── engine/
│   │   │   └── TFLiteModel.kt                                # TensorFlow Lite model wrapper
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
│   └── yolo_nas_s_int8.tflite                                # YOLO-NAS INT8 model (TFLite format)
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
