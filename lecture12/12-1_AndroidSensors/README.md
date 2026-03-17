# Android Sensors (Compose)

A Jetpack Compose app that demonstrates how to **read data from device hardware sensors** using the Android `SensorManager` API. The app accesses motion sensors (accelerometer, gyroscope), environment sensors (light, pressure), and other available sensors, displaying their real-time readings in the Compose UI. It also demonstrates the `ACTIVITY_RECOGNITION` permission for step counting and activity detection.

> **See also:** [Android Sensors (MDC)](../12-2_AndroidSensors-MDC) — the same functionality with XML layouts and Fragments.

## Learning Outcomes

After studying this app, students will be able to:

- Access the `SensorManager` system service and list available sensors
- Register and unregister `SensorEventListener` callbacks
- Read and interpret sensor data from `SensorEvent` objects
- Understand sensor types: `TYPE_ACCELEROMETER`, `TYPE_GYROSCOPE`, `TYPE_LIGHT`, etc.
- Request the `ACTIVITY_RECOGNITION` runtime permission
- Use `HIGH_SAMPLING_RATE_SENSORS` for high-frequency sensor data
- Display real-time sensor values in Compose UI
- Handle sensor lifecycle registration/unregistration correctly

## Architecture

**Pattern:** Single Activity with Compose + SensorManager

The `MainActivity` registers sensor listeners and exposes sensor data to the Compose UI.

| Class | Role |
|-------|------|
| `MainActivity` | Sensor registration, Compose UI with real-time readings |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| SensorManager | System service for hardware sensor access |
| SensorEventListener | Callback for sensor data updates |
| ACTIVITY_RECOGNITION | Permission for activity/step detection |
| HIGH_SAMPLING_RATE_SENSORS | Permission for high-frequency sensor access |
| Jetpack Compose | Declarative UI framework |
| Material Design 3 | UI components |

## How to Run

1. Open the `12-1_AndroidSensors` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on a **physical device** (most sensors are not available on emulators).
4. Grant permissions when prompted.
5. Observe real-time sensor data on screen. Move the device to see accelerometer/gyroscope changes.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/androidsensors/
│   └── ui/main/
│       └── MainActivity.kt              # Sensor registration + Compose UI
└── AndroidManifest.xml                   # ACTIVITY_RECOGNITION, HIGH_SAMPLING_RATE_SENSORS
```

## Dependencies

- AndroidX Activity Compose
- AndroidX Compose Material 3
- AndroidX Compose BOM
