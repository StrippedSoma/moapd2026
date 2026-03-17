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
│   ├── bluetooth/
│   │   ├── BluetoothController.kt        # Bluetooth operations facade
│   │   ├── BluetoothServerThread.kt      # Server thread
│   │   ├── BluetoothClientThread.kt      # Client thread
│   │   └── BluetoothConnectedThread.kt   # Connected I/O thread
│   └── ui/
│       ├── main/
│       │   ├── MainActivity.kt           # Navigation host + permissions
│       │   └── BluetoothViewModel.kt    # Shared Bluetooth state
│       ├── home/
│       │   └── HomeFragment.kt          # Main menu
│       ├── discovery/
│       │   └── DiscoveryFragment.kt     # Discover nearby devices
│       ├── paired/
│       │   └── PairedDevicesFragment.kt # Paired device list
│       └── chat/
│           └── ChatFragment.kt          # Chat message interface
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   ├── content_main.xml
│   │   ├── fragment_home.xml
│   │   ├── fragment_discovery.xml
│   │   ├── fragment_paired_devices.xml
│   │   ├── fragment_chat.xml
│   │   ├── item_device.xml              # Device list item
│   │   └── item_chat_message.xml        # Chat message item
│   └── navigation/                       # Navigation graph
└── AndroidManifest.xml                    # Bluetooth + location permissions
```

## Dependencies

- AndroidX RecyclerView
- AndroidX Fragment KTX
- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- AndroidX Lifecycle ViewModel KTX
- Material Components for Android
