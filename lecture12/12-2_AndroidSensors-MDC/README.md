# Android Sensors (MDC)

An Android app that demonstrates **hardware sensor data reading** with a **Fragment-based UI**. The app uses the `SensorManager` API to access device sensors and displays their values in dedicated fragments — one for single-value sensors (light, pressure) and another for three-axis sensors (accelerometer, gyroscope).

> **See also:** [Android Sensors (Compose)](../12-1_AndroidSensors) — the same functionality with Jetpack Compose.

## Learning Outcomes

After studying this app, students will be able to:

- Integrate `SensorManager` with Fragment lifecycle for proper registration/unregistration
- Display different sensor types in specialized fragment layouts
- Differentiate between single-value and three-axis sensor data
- Navigate between sensor display fragments with the Navigation component
- Handle activity recognition and high-sampling-rate permissions
- Compare sensor integration in Fragment-based vs. Compose architectures

## Architecture

**Pattern:** Single Activity + Fragment Navigation + SensorManager

The `MainActivity` hosts fragments via Navigation component. Each fragment type is specialized for different sensor data formats.

| Class | Role |
|-------|------|
| `MainActivity` | Navigation host |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| SensorManager | Hardware sensor access |
| SensorEventListener | Sensor data callbacks |
| AndroidX Navigation | Fragment navigation |
| View Binding | Type-safe view references |
| Material Components | UI styling |

## How to Run

1. Open the `12-2_AndroidSensors-MDC` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on a **physical device** (sensors require real hardware).
4. Grant permissions and observe real-time sensor readings.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/androidsensors/
│   └── ui/main/
│       └── MainActivity.kt              # Navigation host
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   ├── content_main.xml
│   │   ├── fragment_main.xml            # Sensor selection
│   │   ├── fragment_single_value.xml    # Single-value sensor display
│   │   └── fragment_three_axes.xml      # Three-axis sensor display
│   └── navigation/                       # Navigation graph
└── AndroidManifest.xml                   # ACTIVITY_RECOGNITION, HIGH_SAMPLING_RATE_SENSORS
```

## Dependencies

- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- Material Components for Android
- AndroidX AppCompat
