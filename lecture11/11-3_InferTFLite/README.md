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
│   └── yolo_nas_s_int8.tflite                                # YOLO-NAS INT8 model (TFLite format)
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
