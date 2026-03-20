# Lecture 11 — Model Deployment on Edge Devices

This lecture focuses on deploying trained neural network models for on-device inference on Android. Students learn to run models using two different inference engines: Qualcomm's Snapdragon Neural Processing Engine (SNPE) for Snapdragon-powered devices and TensorFlow Lite for cross-platform mobile inference. Both approaches use Hilt for dependency injection and CameraX for real-time camera input. Each engine is demonstrated in Jetpack Compose and XML (MDC) variants.

## Apps

| App | Description | UI Framework |
|-----|-------------|--------------|
| [11-1_InferSNPE](./11-1_InferSNPE) | Real-time neural network inference using Qualcomm SNPE | Jetpack Compose |
| [11-2_InferSNPE-MDC](./11-2_InferSNPE-MDC) | SNPE inference with Fragment-based UI | XML Layouts + View Binding |
| [11-3_InferTFLite](./11-3_InferTFLite) | Real-time inference using TensorFlow Lite with GPU acceleration | Jetpack Compose |
| [11-4_InferTFLite-MDC](./11-4_InferTFLite-MDC) | TensorFlow Lite inference with Fragment-based UI | XML Layouts + View Binding |

## Key Concepts Covered

- On-device (edge) machine learning inference
- Qualcomm SNPE: model loading, DSP/GPU delegates, and native library integration
- TensorFlow Lite: interpreter setup, GPU delegate, and model optimization
- Hilt dependency injection for Android
- CameraX integration for real-time camera frame processing
- Native library packaging with `jniLibs`
- ARM64 architecture targeting
- Model quantization and performance considerations

## Further Reading

- [TensorFlow Lite for Android](https://www.tensorflow.org/lite/android)
- [Qualcomm SNPE documentation](https://developer.qualcomm.com/software/qualcomm-neural-processing-sdk)
- [Hilt dependency injection](https://developer.android.com/training/dependency-injection/hilt-android)
- [CameraX overview](https://developer.android.com/media/camera/camerax)
