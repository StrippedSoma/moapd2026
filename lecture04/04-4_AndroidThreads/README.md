# Android Threads

An Android app that compares **three different approaches to background threading**: raw Java `Thread`, `Handler`/`Looper` message passing, and Kotlin Coroutines. Each approach is implemented in its own Fragment, accessible via bottom navigation. A shared `ViewModel` holds the counter state that each threading mechanism updates, demonstrating the trade-offs between different concurrency strategies.

## Learning Outcomes

After studying this app, students will be able to:

- Create and manage raw Java `Thread` instances with proper interrupt handling
- Use `Handler` and `Looper` for message-based communication between threads
- Implement Kotlin Coroutines with `lifecycleScope` for lifecycle-aware background work
- Compare the complexity, safety, and ergonomics of the three approaches
- Share state across fragments using a shared `ViewModel`
- Use Data Binding for two-way UI data synchronization
- Handle fragment lifecycle correctly when managing background tasks

## Architecture

**Pattern:** Single Activity with Navigation + Shared ViewModel

Each threading approach is encapsulated in its own Fragment. A shared `DataViewModel` holds the counter state, and the Navigation framework manages fragment transitions.

| Class | Role |
|-------|------|
| `MainActivity` | Hosts NavHostFragment with bottom navigation |
| `ThreadFragment` | Demonstrates raw `Thread` with interrupt handling |
| `HandlerFragment` | Demonstrates `Handler`/`Looper` message passing |
| `CoroutinesFragment` | Demonstrates Kotlin Coroutines with `lifecycleScope` |
| `DataViewModel` | Shared ViewModel holding counter state across fragments |
| `ThreadsApplication` | Custom Application subclass |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Thread | Raw Java threading for background work |
| Handler / Looper | Message-based thread communication |
| Kotlin Coroutines | Lightweight, lifecycle-aware concurrency |
| lifecycleScope | Coroutine scope tied to fragment lifecycle |
| ViewModel | Shared state holder across fragments |
| Data Binding | Two-way UI data synchronization |
| AndroidX Navigation | Fragment transitions with bottom navigation |

## How to Run

1. Open the `04-4_AndroidThreads` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Switch between tabs to see each threading approach increment a counter.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/androidthreads/
│   ├── app/
│   │   └── ThreadsApplication.kt                    # Application class with Dynamic Colors
│   └── ui/
│       ├── main/
│       │   └── MainActivity.kt                      # Navigation host with BottomNavigationView
│       ├── samples/
│       │   ├── coroutines/
│       │   │   └── CoroutinesFragment.kt             # Kotlin Coroutines implementation
│       │   ├── handler/
│       │   │   └── HandlerFragment.kt                # Handler/Looper implementation
│       │   └── thread/
│       │       └── ThreadFragment.kt                 # Raw Thread implementation
│       ├── shared/
│       │   └── DataViewModel.kt                      # Shared counter state ViewModel
│       └── utils/
│           └── FragmentViewBindingDelegate.kt         # Lifecycle-safe Fragment ViewBinding delegate
├── res/
│   ├── drawable/
│   │   ├── baseline_android_24.xml                   # Android icon
│   │   ├── baseline_handshake_24.xml                 # Handshake icon
│   │   ├── baseline_restart_alt_24.xml               # Restart icon
│   │   ├── baseline_sync_24.xml                      # Sync icon
│   │   ├── circular_determinative.xml                # Circular progress drawable
│   │   ├── ic_launcher_background.xml                # Launcher icon background
│   │   └── ic_launcher_foreground.xml                # Launcher icon foreground
│   ├── layout/
│   │   ├── activity_main.xml                         # Main activity layout
│   │   ├── content_main.xml                          # NavHostFragment container
│   │   ├── fragment_coroutines.xml                   # Coroutines fragment layout
│   │   ├── fragment_handler.xml                      # Handler fragment layout
│   │   └── fragment_thread.xml                       # Thread fragment layout
│   ├── layout-land/
│   │   ├── activity_main.xml                         # Landscape main layout
│   │   └── content_main.xml                          # Landscape content layout
│   ├── menu/
│   │   └── bottom_navigation_menu.xml                # Bottom navigation menu items
│   ├── navigation/
│   │   └── nav_graph.xml                             # Navigation graph
│   ├── values/
│   │   ├── colors.xml                                # Color definitions
│   │   ├── dimens.xml                                # Dimension resources
│   │   ├── strings.xml                               # String resources
│   │   └── themes.xml                                # App theme
│   ├── values-night/
│   │   └── themes.xml                                # Dark mode theme
│   └── xml/
│       ├── backup_rules.xml                          # Backup rules for Android 12+
│       └── data_extraction_rules.xml                 # Data extraction rules
└── AndroidManifest.xml                               # App manifest
```

## Dependencies

- AndroidX Lifecycle ViewModel KTX
- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- Kotlin Coroutines Android
- Material Components for Android
- AndroidX AppCompat
