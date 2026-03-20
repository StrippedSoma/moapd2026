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
│   ├── app/
│   │   └── AndroidSensorsApplication.kt      # Application class
│   └── ui/
│       ├── common/
│       │   └── SensorValueExtensions.kt      # Extension functions for sensor value formatting
│       ├── main/
│       │   └── MainActivity.kt               # Navigation host activity
│       ├── sensors/
│       │   ├── core/
│       │   │   ├── SensorCategoryFragment.kt  # Abstract base fragment for sensor categories
│       │   │   ├── SensorPageFragment.kt      # Fragment for individual sensor pages
│       │   │   ├── SensorSpecifications.kt    # Sealed interfaces/classes for sensor specs
│       │   │   └── SensorsPagerAdapter.kt     # ViewPager adapter for sensor pages
│       │   ├── environmental/
│       │   │   └── EnvironmentalFragment.kt   # Fragment for environmental sensors
│       │   ├── motion/
│       │   │   └── MotionFragment.kt          # Fragment for motion sensors
│       │   └── position/
│       │       └── PositionFragment.kt        # Fragment for position sensors
│       └── utils/
│           └── FragmentViewBindingDelegate.kt # View binding delegate for fragments
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
│   ├── layout/
│   │   ├── activity_main.xml                  # Main activity layout
│   │   ├── content_main.xml                   # Content layout with navigation host
│   │   ├── fragment_main.xml                  # Main fragment with ViewPager
│   │   ├── fragment_single_value.xml          # Single-value sensor fragment layout
│   │   └── fragment_three_axes.xml            # Three-axis sensor fragment layout
│   ├── layout-land/
│   │   ├── fragment_single_value.xml          # Landscape single-value sensor layout
│   │   └── fragment_three_axes.xml            # Landscape three-axis sensor layout
│   ├── menu/
│   │   └── bottom_navigation_menu.xml         # Bottom navigation menu items
│   ├── navigation/
│   │   └── nav_graph.xml                      # Navigation graph
│   ├── values/
│   │   ├── colors.xml                         # Color definitions
│   │   ├── dimens.xml                         # Dimension resources
│   │   ├── strings.xml                        # String resources
│   │   └── themes.xml                         # App theme
│   ├── values-night/
│   │   └── themes.xml                         # Dark theme
│   └── xml/
│       ├── backup_rules.xml                   # Backup rules for Android 12+
│       └── data_extraction_rules.xml          # Data extraction rules
└── AndroidManifest.xml                        # ACTIVITY_RECOGNITION, HIGH_SAMPLING_RATE_SENSORS
```

## Dependencies

- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- Material Components for Android
- AndroidX AppCompat
