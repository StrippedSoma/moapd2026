# Chat Bluetooth (Compose)

A Jetpack Compose app that implements a **real-time peer-to-peer chat** over **Bluetooth Classic**. The app discovers nearby Bluetooth devices, establishes connections through RFCOMM sockets, and exchanges text messages in real time. A multi-threaded architecture handles server listening, client connecting, and bidirectional message I/O on separate threads, with a `Handler` posting results to the main thread for UI updates.

> **See also:** [Chat Bluetooth (MDC)](../12-4_ChatBluetooth-MDC) — the same functionality with XML layouts and Fragments.

## Learning Outcomes

After studying this app, students will be able to:

- Use `BluetoothAdapter` to check availability and enable Bluetooth
- Discover nearby devices with `BluetoothAdapter.startDiscovery()`
- List paired devices with `BluetoothAdapter.bondedDevices`
- Establish Bluetooth connections with `BluetoothSocket` (RFCOMM)
- Implement a multi-threaded Bluetooth architecture: server thread (accept), client thread (connect), connected thread (read/write)
- Register `BroadcastReceiver` for `ACTION_FOUND` device discovery events
- Handle Bluetooth permissions for both legacy (API < 31) and modern (API 31+) APIs
- Use `Handler` for thread-safe UI updates from background Bluetooth threads
- Build a chat interface with Compose and `LazyColumn` for message history
- Manage connection state with `ViewModel` and `LiveData`

## Architecture

**Pattern:** MVVM with Bluetooth Controller + Multi-threaded Communication

A `BluetoothController` facade encapsulates all low-level Bluetooth operations. Three thread types handle concurrent connection and I/O. The `BluetoothViewModel` exposes state to Compose UI.

| Class | Role |
|-------|------|
| `MainActivity` | Entry point, permission handling |
| `MainScreen` | Multi-screen Compose UI (Home, Discovery, Chat) |
| `BluetoothViewModel` | Manages connection state, messages, discovered devices |
| `BluetoothController` | Facade for all Bluetooth operations (server, client, connected) |
| `BluetoothServerThread` | Listens for incoming connections (`BluetoothServerSocket.accept()`) |
| `BluetoothClientThread` | Initiates connection to remote device |
| `BluetoothConnectedThread` | Manages socket I/O for message exchange |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| BluetoothAdapter | Bluetooth hardware access |
| BluetoothSocket | RFCOMM socket for data transfer |
| BluetoothServerSocket | Server-side connection listener |
| BroadcastReceiver | Device discovery event handling |
| Handler | Thread-safe main thread communication |
| ViewModel + LiveData | Reactive UI state management |
| Jetpack Compose | Declarative UI framework |
| Compose Navigation | Screen transitions |
| Material Design 3 | UI components |

## How to Run

1. Open the `12-3_ChatBluetooth` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on **two physical devices** with Bluetooth support.
4. Grant all Bluetooth and location permissions when prompted.
5. On one device, start listening as a server. On the other, discover and connect.
6. Exchange text messages in real time.

> **Note:** Bluetooth Classic requires physical devices. Emulators do not support Bluetooth.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/chatbluetooth/
│   ├── bluetooth/
│   │   ├── BluetoothClientThread.kt          # Initiates outgoing connections
│   │   ├── BluetoothConnectedThread.kt       # Socket I/O for message exchange
│   │   ├── BluetoothController.kt            # Bluetooth operations facade
│   │   ├── BluetoothDiscoveryReceiver.kt     # BroadcastReceiver for device discovery
│   │   └── BluetoothServerThread.kt          # Accepts incoming connections
│   ├── data/model/
│   │   ├── BluetoothDeviceItem.kt            # Discovered device data class
│   │   ├── ChatMessage.kt                    # Chat message data class
│   │   └── ConnectionState.kt                # Connection state enum
│   ├── ui/
│   │   ├── main/
│   │   │   ├── BluetoothViewModel.kt         # ViewModel for Bluetooth state management
│   │   │   ├── MainActivity.kt               # Entry point + permission handling
│   │   │   ├── MainScreen.kt                 # Compose navigation (Home, Discovery, Chat)
│   │   │   ├── screens/
│   │   │   │   ├── ChatScreen.kt             # Chat message screen
│   │   │   │   ├── DiscoveryScreen.kt        # Device discovery screen
│   │   │   │   ├── HomeScreen.kt             # Home/main menu screen
│   │   │   │   └── PairedDevicesScreen.kt    # Paired devices screen
│   │   │   ├── utils/
│   │   │   │   └── BluetoothUiUtils.kt       # UI utility functions for Bluetooth
│   │   │   └── widgets/
│   │   │       ├── ChatBubble.kt             # Chat bubble composable
│   │   │       └── DeviceList.kt             # Device list composable
│   │   └── theme/
│   │       ├── Color.kt                      # Color definitions
│   │       ├── Theme.kt                      # App theme composable
│   │       └── Type.kt                       # Typography definitions
│   └── util/
│       └── Constants.kt                      # Bluetooth constants (UUIDs, message types)
├── res/
│   ├── drawable/
│   │   ├── ic_launcher_background.xml        # Launcher icon background
│   │   └── ic_launcher_foreground.xml        # Launcher icon foreground
│   ├── values/
│   │   ├── colors.xml                        # Color definitions
│   │   ├── strings.xml                       # String resources
│   │   └── themes.xml                        # App theme
│   └── xml/
│       ├── backup_rules.xml                  # Backup rules for Android 12+
│       └── data_extraction_rules.xml         # Data extraction rules
└── AndroidManifest.xml                       # Bluetooth + location permissions
```

## Dependencies

- AndroidX Activity Compose
- AndroidX Compose Navigation
- AndroidX Compose Runtime LiveData
- AndroidX Compose Material 3
- AndroidX Lifecycle ViewModel Compose
