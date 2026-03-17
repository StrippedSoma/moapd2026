# LifeCycle — Bundle

This app fixes the lifecycle bug from [02-1_LifeCycle-bug](../02-1_LifeCycle-bug) by using `onSaveInstanceState()` to persist UI state in a `Bundle` before the Activity is destroyed during configuration changes. When the Activity is recreated, the saved state is restored from the `Bundle` parameter in `onCreate()`.

> **See also:**
> - [LifeCycle — Bug](../02-1_LifeCycle-bug) — the buggy version without state saving
> - [LifeCycle — ViewModel](../02-3_LifeCycle-vm) — the modern fix using `ViewModel` + `LiveData`

## Learning Outcomes

After studying this app, students will be able to:

- Implement `onSaveInstanceState(outState: Bundle)` to save UI state before destruction
- Restore state from the `Bundle` parameter in `onCreate(savedInstanceState: Bundle?)`
- Use `Bundle` constants (keys) to store and retrieve primitive types
- Differentiate between a fresh launch (`savedInstanceState == null`) and a recreation
- Understand the limitations of `Bundle`-based state saving (size limits, primitive types only)
- Compare this approach to the modern `ViewModel` alternative

## Architecture

**Pattern:** Activity-based with Bundle state persistence

The `Activity` manages its own state through lifecycle callbacks. State is serialized to a `Bundle` on destruction and deserialized on recreation.

| Class | Role |
|-------|------|
| `MainActivity` | Hosts UI, saves state in `onSaveInstanceState()`, restores in `onCreate()` |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| View Binding | Type-safe view references via `ActivityMainBinding` |
| Bundle | Lightweight state serialization for configuration changes |
| onSaveInstanceState() | Lifecycle callback for state persistence |
| AppCompatActivity | Base activity class |

## How to Run

1. Open the `02-2_LifeCycle-bundle` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Interact with the UI, then **rotate the device** — state is now preserved.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/lifecycle/
│   └── presentation/main/
│       └── MainActivity.kt           # Activity with onSaveInstanceState() implementation
└── res/
    └── layout/
        └── activity_main.xml         # UI layout
```

## Dependencies

- AndroidX AppCompat
- AndroidX ConstraintLayout
- Material Components for Android
