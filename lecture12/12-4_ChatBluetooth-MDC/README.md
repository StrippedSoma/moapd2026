# Chat Bluetooth (MDC)

An Android app that implements a **Bluetooth Classic peer-to-peer chat** with a **Fragment-based UI**, **RecyclerView** for message history and device lists, and the **Navigation component** for screen transitions. The app provides device discovery, connection management, and real-time message exchange over Bluetooth RFCOMM sockets.

> **See also:** [Chat Bluetooth (Compose)](../12-3_ChatBluetooth) — the same functionality with Jetpack Compose.

## Learning Outcomes

After studying this app, students will be able to:

- Integrate Bluetooth Classic with a Fragment-based architecture
- Display discovered devices and paired devices in RecyclerView
- Render chat messages in a RecyclerView with custom item layouts
- Navigate between home, discovery, paired devices, and chat screens
- Handle Bluetooth permissions across multiple Android API levels
- Register BroadcastReceiver for device discovery within Fragment lifecycle
- Compare Bluetooth integration in Fragment-based vs. Compose architectures

## Architecture

**Pattern:** Single Activity + Fragment Navigation + Bluetooth Controller

The `MainActivity` hosts fragments via Navigation component. Each screen (home, discovery, paired devices, chat) is a separate fragment. The Bluetooth logic is shared through a ViewModel.

| Class | Role |
|-------|------|
| `MainActivity` | Navigation host, permission handling |
| `HomeFragment` | Main menu with connection options |
| `DiscoveryFragment` | RecyclerView of discovered Bluetooth devices |
| `PairedDevicesFragment` | RecyclerView of paired devices |
| `ChatFragment` | RecyclerView chat message display and input |
| `BluetoothViewModel` | Shared ViewModel managing connection and messages |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| BluetoothAdapter | Bluetooth hardware access |
| BluetoothSocket | RFCOMM socket communication |
| BroadcastReceiver | Device discovery events |
| Handler | Thread-safe UI updates |
| RecyclerView | Device lists and chat messages |
| ViewModel + LiveData | Shared state management |
| AndroidX Navigation | Fragment navigation |
| View Binding | Type-safe view references |
| Material Components | UI styling |

## How to Run

1. Open the `12-4_ChatBluetooth-MDC` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on **two physical devices** with Bluetooth support.
4. Grant all Bluetooth and location permissions when prompted.
5. On one device, start listening. On the other, discover and connect.
6. Exchange text messages in real time.

> **Note:** Bluetooth Classic requires physical devices. Emulators do not support Bluetooth.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/chatbluetooth/
│   ├── app/
│   │   └── ChatBluetoothApplication.kt       # Application class
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
│   │   ├── adapters/
│   │   │   ├── ChatMessageAdapter.kt         # RecyclerView adapter for chat messages
│   │   │   └── DeviceListAdapter.kt          # RecyclerView adapter for device lists
│   │   ├── chat/
│   │   │   └── ChatFragment.kt               # Chat message interface fragment
│   │   ├── discovery/
│   │   │   └── DiscoveryFragment.kt          # Device discovery fragment
│   │   ├── home/
│   │   │   └── HomeFragment.kt               # Home menu fragment
│   │   ├── main/
│   │   │   ├── BluetoothViewModel.kt         # Shared Bluetooth ViewModel
│   │   │   └── MainActivity.kt               # Navigation host + permissions
│   │   ├── paired/
│   │   │   └── PairedDevicesFragment.kt      # Paired devices fragment
│   │   └── utils/
│   │       └── FragmentViewBindingDelegate.kt # View binding delegate for fragments
│   └── util/
│       └── Constants.kt                      # Bluetooth constants (UUIDs, message types)
├── res/
│   ├── drawable/
│   │   ├── ic_launcher_background.xml        # Launcher icon background
│   │   └── ic_launcher_foreground.xml        # Launcher icon foreground
│   ├── layout/
│   │   ├── activity_main.xml                 # Main activity layout
│   │   ├── content_main.xml                  # Content layout with navigation host
│   │   ├── fragment_chat.xml                 # Chat screen fragment layout
│   │   ├── fragment_discovery.xml            # Device discovery fragment layout
│   │   ├── fragment_home.xml                 # Home menu fragment layout
│   │   ├── fragment_paired_devices.xml       # Paired devices fragment layout
│   │   ├── item_chat_message.xml             # Chat message list item layout
│   │   └── item_device.xml                   # Device list item layout
│   ├── navigation/
│   │   └── nav_graph.xml                     # Navigation graph
│   ├── values/
│   │   ├── colors.xml                        # Color definitions
│   │   ├── strings.xml                       # String resources
│   │   └── themes.xml                        # App theme
│   ├── values-night/
│   │   └── themes.xml                        # Dark theme
│   └── xml/
│       ├── backup_rules.xml                  # Backup rules for Android 12+
│       └── data_extraction_rules.xml         # Data extraction rules
└── AndroidManifest.xml                       # Bluetooth + location permissions
```

## Dependencies

- AndroidX RecyclerView
- AndroidX Fragment KTX
- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- AndroidX Lifecycle ViewModel KTX
- Material Components for Android
