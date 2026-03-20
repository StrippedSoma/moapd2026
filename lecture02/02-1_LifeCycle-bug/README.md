# LifeCycle — Bug

An Android app that **intentionally demonstrates a common lifecycle bug**: UI state is lost when the device is rotated (configuration change). The app displays True/False buttons and a checkbox that update a text view, but all state disappears on rotation because no state-saving mechanism is implemented. This app is designed to be studied alongside its fixed versions.

> **See also:**
> - [LifeCycle — Bundle](../02-2_LifeCycle-bundle) — fixes the bug using `onSaveInstanceState()`
> - [LifeCycle — ViewModel](../02-3_LifeCycle-vm) — the modern fix using `ViewModel` + `LiveData`

## Learning Outcomes

After studying this app, students will be able to:

- Identify the complete Activity lifecycle: `onCreate()` → `onStart()` → `onResume()` → `onPause()` → `onStop()` → `onDestroy()`
- Explain what happens during a configuration change (e.g., device rotation)
- Use `Log.d()` to trace lifecycle callback execution
- Recognize that the Activity is destroyed and recreated on rotation
- Understand why UI state is lost without explicit state-saving code
- Use View Binding to reference views in a type-safe way

## Architecture

**Pattern:** Activity-based (no state management)

This app deliberately omits state preservation to illustrate the problem. The `Activity` holds references to views via View Binding but does not persist any state.

| Class | Role |
|-------|------|
| `MainActivity` | Hosts UI with buttons and text view; logs all lifecycle callbacks; **does not save state** |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| View Binding | Type-safe view references via `ActivityMainBinding` |
| Log.d() | Lifecycle callback tracing |
| AppCompatActivity | Base activity class |

## How to Run

1. Open the `02-1_LifeCycle-bug` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Tap the True/False buttons to update the text view.
5. **Rotate the device** (Ctrl+Left/Right on emulator) and observe that the text resets — this is the bug.
6. Check Logcat for lifecycle callback logs tagged with `MainActivity`.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/lifecycle/
│   └── activities/presentation/main/
│       └── MainActivity.kt               # Activity with lifecycle logging, no state saving
├── res/
│   ├── drawable/
│   │   ├── ic_launcher_background.xml    # Launcher icon background
│   │   └── ic_launcher_foreground.xml    # Launcher icon foreground
│   ├── layout/
│   │   └── activity_main.xml             # UI with True/False buttons, checkbox, and text view
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
- AndroidX ConstraintLayout
- Material Components for Android
