# LifeCycle — ViewModel

This app presents the **modern solution** to the lifecycle state problem using `ViewModel` and `LiveData` from Android Architecture Components. The `ViewModel` survives configuration changes automatically, and `LiveData` provides lifecycle-aware observable data that the Activity observes for UI updates. This is the recommended approach for managing UI-related state in Android.

> **See also:**
> - [LifeCycle — Bug](../02-1_LifeCycle-bug) — the buggy version without state saving
> - [LifeCycle — Bundle](../02-2_LifeCycle-bundle) — the `Bundle`-based fix

## Learning Outcomes

After studying this app, students will be able to:

- Explain why `ViewModel` is the preferred solution for surviving configuration changes
- Create a `ViewModel` subclass and expose state via `LiveData`
- Use the `by viewModels()` Kotlin property delegate to obtain a `ViewModel` instance
- Observe `LiveData` from an `Activity` using `observe()` with a lifecycle owner
- Understand `MutableLiveData` (internal) vs. `LiveData` (exposed) encapsulation
- Describe the MVVM (Model-View-ViewModel) architectural pattern
- Compare `ViewModel` with `onSaveInstanceState()` for state management

## Architecture

**Pattern:** MVVM (Model-View-ViewModel)

The `ViewModel` holds and exposes UI state via `LiveData`, decoupling business logic from the Activity. The Activity observes state changes and updates the UI accordingly.

| Class | Role |
|-------|------|
| `MainActivity` | Observes `LiveData` from the ViewModel and updates the UI |
| `MainViewModel` | Holds `MutableLiveData<String>` state; survives configuration changes |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| ViewModel | Lifecycle-aware state holder that survives configuration changes |
| LiveData | Observable data holder that respects the Activity lifecycle |
| by viewModels() | Kotlin property delegate for lazy ViewModel instantiation |
| View Binding | Type-safe view references via `ActivityMainBinding` |
| AppCompatActivity | Base activity class |

## How to Run

1. Open the `02-3_LifeCycle-vm` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Interact with the UI, then **rotate the device** — state is preserved via the ViewModel.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/lifecycle/
│   └── presentation/main/
│       ├── MainActivity.kt               # Activity observing LiveData from ViewModel
│       └── MainViewModel.kt              # ViewModel with MutableLiveData<String>
├── res/
│   ├── drawable/
│   │   ├── ic_launcher_background.xml    # Launcher icon background
│   │   └── ic_launcher_foreground.xml    # Launcher icon foreground
│   ├── layout/
│   │   └── activity_main.xml             # UI layout
│   ├── values/
│   │   ├── colors.xml                    # Color definitions
│   │   ├── strings.xml                   # String resources
│   │   └── themes.xml                    # App theme
│   ├── values-night/
│   │   └── themes.xml                    # Dark mode theme
│   └── xml/
│       ├── backup_rules.xml              # Backup rules for Android 12+
│       └── data_extraction_rules.xml     # Data extraction rules
└── AndroidManifest.xml                   # App manifest
```

## Dependencies

- AndroidX AppCompat
- AndroidX Lifecycle ViewModel KTX
- AndroidX Lifecycle LiveData KTX
- AndroidX Activity KTX (for `by viewModels()`)
- Material Components for Android
