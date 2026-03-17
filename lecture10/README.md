# Lecture 10 — Android Multimedia and Machine Learning

This lecture covers multimedia capabilities and machine learning integration on Android. Students learn to consume REST APIs for audio playback, capture photos with CameraX, process images with OpenCV, and perform on-device inference with Firebase ML Kit. Each topic is demonstrated in Jetpack Compose and XML (MDC) variants.

## Apps

| App | Description | UI Framework |
|-----|-------------|--------------|
| [10-1_PalcoMP3](./10-1_PalcoMP3) | Music player consuming a REST API with expandable artist/song lists | Jetpack Compose |
| [10-2_PalcoMP3-MDC](./10-2_PalcoMP3-MDC) | Music player with RecyclerView-based UI | XML Layouts + View Binding |
| [10-3_CameraX](./10-3_CameraX) | Photo capture using the CameraX library | Jetpack Compose |
| [10-4_CameraX-MDC](./10-4_CameraX-MDC) | CameraX photo capture with Fragment-based UI | XML Layouts + View Binding |
| [10-5_OpenCV](./10-5_OpenCV) | Computer vision image processing with OpenCV 4.12 | Jetpack Compose |
| [10-6_OpenCV-MDC](./10-6_OpenCV-MDC) | OpenCV image processing with Fragment-based UI | XML Layouts + View Binding |
| [10-7_MLKit](./10-7_MLKit) | On-device image analysis with Firebase ML Kit | Jetpack Compose |
| [10-8_MLKit-MDC](./10-8_MLKit-MDC) | Firebase ML Kit with Fragment-based UI | XML Layouts + View Binding |

## Key Concepts Covered

- REST API consumption with Volley and JSON parsing with Gson
- Audio playback with `MediaPlayer` and foreground services
- CameraX: camera preview, image capture, and lifecycle integration
- OpenCV for Android: image processing and computer vision
- Firebase ML Kit: on-device object detection and image labeling
- `StateFlow` for reactive state management in ViewModels
- Coil and Picasso for image loading and caching
- Expandable list patterns for hierarchical data
- Camera permissions handling

## Further Reading

- [CameraX overview](https://developer.android.com/media/camera/camerax)
- [Volley overview](https://developer.android.com/training/volley)
- [ML Kit for Android](https://developers.google.com/ml-kit)
- [OpenCV for Android](https://docs.opencv.org/4.x/d5/df8/tutorial_dev_with_OCV_on_Android.html)
- [MediaPlayer overview](https://developer.android.com/media/platform/mediaplayer)
