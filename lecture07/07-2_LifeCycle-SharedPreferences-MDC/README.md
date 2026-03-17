# LifeCycle — SharedPreferences (MDC)

An Android app that demonstrates **lightweight data persistence** using `SharedPreferences` with **XML layouts** and **View Binding**. The app saves and restores user input across app restarts, showing how SharedPreferences integrates with the traditional Activity lifecycle.

> **See also:** [LifeCycle — SharedPreferences (Compose)](../07-1_LifeCycle-SharedPreferences) — the same functionality with Jetpack Compose.

## Learning Outcomes

After studying this app, students will be able to:

- Use `SharedPreferences` to persist data across Activity restarts and process death
- Access SharedPreferences with `getSharedPreferences()`, `edit()`, and `apply()`
- Save state in `onPause()` or `onStop()` and restore in `onCreate()` or `onResume()`
- Understand the SharedPreferences XML file storage format
- Compare SharedPreferences with `onSaveInstanceState()` for persistence scope
- Use View Binding for type-safe view references in an Activity

## Architecture

**Pattern:** Activity-based with SharedPreferences Persistence

The `MainActivity` directly manages SharedPreferences access through lifecycle callbacks.

| Class | Role |
|-------|------|
| `MainActivity` | Manages UI, reads/writes SharedPreferences in lifecycle callbacks |
| `LifecycleApplication` | Custom Application subclass |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| SharedPreferences | Lightweight key-value persistence |
| View Binding | Type-safe view references via `ActivityMainBinding` |
| AppCompatActivity | Base activity class |
| Material Components | UI styling |

## How to Run

1. Open the `07-2_LifeCycle-SharedPreferences-MDC` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Enter data, close the app, and reopen it — the data is restored.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/lifecycle/
│   ├── app/
│   │   └── LifecycleApplication.kt      # Application subclass
│   └── presentation/main/
│       └── MainActivity.kt              # Activity with SharedPreferences lifecycle
└── res/
    └── layout/
        └── activity_main.xml            # UI layout
```

## Dependencies

- AndroidX AppCompat
- AndroidX Activity KTX
- Material Components for Android
- AndroidX ConstraintLayout
