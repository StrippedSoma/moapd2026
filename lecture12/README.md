# Lecture 12 — Android Sensors

This lecture covers hardware sensor access and Bluetooth communication on Android. Students learn to read data from device sensors (accelerometer, gyroscope, and other motion/environment sensors) using the `SensorManager` API and to build a real-time peer-to-peer chat application over Bluetooth Classic. Each topic is demonstrated in Jetpack Compose and XML (MDC) variants.

## Apps

| App | Description | UI Framework |
|-----|-------------|--------------|
| [12-1_AndroidSensors](./12-1_AndroidSensors) | Reads and displays data from device sensors in real time | Jetpack Compose |
| [12-2_AndroidSensors-MDC](./12-2_AndroidSensors-MDC) | Sensor data display with Fragment-based UI | XML Layouts + View Binding |
| [12-3_ChatBluetooth](./12-3_ChatBluetooth) | Peer-to-peer chat over Bluetooth Classic with device discovery | Jetpack Compose |
| [12-4_ChatBluetooth-MDC](./12-4_ChatBluetooth-MDC) | Bluetooth chat with Fragment-based UI and RecyclerView | XML Layouts + View Binding |

## Key Concepts Covered

- `SensorManager` API: registering listeners, reading sensor events
- Motion sensors: accelerometer, gyroscope
- Environment sensors: light, pressure, temperature
- Activity recognition permission and high-sampling-rate sensors
- Bluetooth Classic: `BluetoothAdapter`, `BluetoothDevice`, `BluetoothSocket`
- Bluetooth permissions: `BLUETOOTH_SCAN`, `BLUETOOTH_CONNECT`, `BLUETOOTH_ADVERTISE`
- Device discovery and pairing
- Multi-threaded Bluetooth communication: server, client, and connected threads
- `BroadcastReceiver` for Bluetooth state and discovery events
- `Handler` for thread-safe UI updates from Bluetooth threads

## Further Reading

- [Sensors overview](https://developer.android.com/develop/sensors-and-location/sensors/sensors_overview)
- [Motion sensors](https://developer.android.com/develop/sensors-and-location/sensors/sensors_motion)
- [Position sensors](https://developer.android.com/develop/sensors-and-location/sensors/sensors_position)
- [Environment sensors](https://developer.android.com/develop/sensors-and-location/sensors/sensors_environment)
- [Bluetooth overview](https://developer.android.com/develop/connectivity/bluetooth)
- [Bluetooth permissions](https://developer.android.com/develop/connectivity/bluetooth/bt-permissions)
