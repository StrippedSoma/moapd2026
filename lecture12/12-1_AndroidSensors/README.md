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
│   ├── sensors/
│   │   ├── ObserveSensor.kt                 # Composable for observing sensor data
│   │   └── SensorCategory.kt                # Enum class for sensor categories
│   ├── sensorspec/
│   │   └── SensorSpecifications.kt           # Sealed interfaces/classes for sensor page specs
│   └── ui/
│       ├── common/
│       │   └── SensorValueExtensions.kt      # Extension functions for sensor value formatting
│       ├── main/
│       │   ├── MainActivity.kt               # Sensor registration + Compose UI
│       │   ├── MainScreen.kt                 # Main Compose screen composable
│       │   └── components/
│       │       ├── SensorCategoryContent.kt   # Composable for sensor category content
│       │       ├── SensorPage.kt              # Composable for individual sensor page
│       │       ├── SingleValueSensorPage.kt   # Composable for single-value sensor display
│       │       └── ThreeAxisSensorPage.kt     # Composable for three-axis sensor display
│       └── theme/
│           ├── Color.kt                       # Color definitions
│           ├── Theme.kt                       # App theme composable
│           └── Type.kt                        # Typography definitions
├── res/
│   ├── drawable/
│   │   ├── baseline_cloud_128.xml             # Cloud/weather sensor icon
│   │   ├── baseline_directions_walk_128.xml   # Step counter sensor icon
│   │   ├── baseline_lightbulb_128.xml         # Light sensor icon
│   │   ├── baseline_my_location_24.xml        # Location sensor icon
│   │   ├── baseline_nature_24.xml             # Environment sensor category icon
│   │   ├── baseline_nightlight_128.xml        # Proximity sensor icon
│   │   ├── baseline_nights_stay_128.xml       # Night/ambient sensor icon
│   │   ├── baseline_pressure_128.xml          # Barometric pressure sensor icon
│   │   ├── baseline_rotate_90_degrees_ccw_24.xml # Rotation/gyroscope sensor icon
│   │   ├── baseline_sunrise_128.xml           # Sunrise/ambient light icon
│   │   ├── baseline_thermostat_128.xml        # Temperature sensor icon
│   │   ├── baseline_thunderstorm_128.xml      # Magnetic field sensor icon
│   │   ├── baseline_volume_off_128.xml        # Sound off/mute icon
│   │   ├── baseline_volume_up_128.xml         # Sound sensor icon
│   │   ├── baseline_water_drop_128.xml        # Humidity sensor icon
│   │   ├── baseline_wb_shade_128.xml          # Shade/uncalibrated light icon
│   │   ├── baseline_wb_sunny_128.xml          # Sunlight sensor icon
│   │   ├── ic_launcher_background.xml         # Launcher icon background
│   │   └── ic_launcher_foreground.xml         # Launcher icon foreground
│   ├── values/
│   │   ├── colors.xml                         # Color definitions
│   │   ├── strings.xml                        # String resources
│   │   └── themes.xml                         # App theme
│   └── xml/
│       ├── backup_rules.xml                   # Backup rules for Android 12+
│       └── data_extraction_rules.xml          # Data extraction rules
└── AndroidManifest.xml                        # ACTIVITY_RECOGNITION, HIGH_SAMPLING_RATE_SENSORS
```

## Dependencies

- AndroidX Activity Compose
- AndroidX Compose Material 3
- AndroidX Compose BOM
