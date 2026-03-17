# LifeCycle — SharedPreferences (Compose)

A Jetpack Compose app that demonstrates **lightweight data persistence** using Android's `SharedPreferences` API. The app saves UI state (e.g., user input) to SharedPreferences when the app goes to the background and restores it when the app returns, ensuring data survives both configuration changes and process death.

> **See also:** [LifeCycle — SharedPreferences (MDC)](../07-2_LifeCycle-SharedPreferences-MDC) — the same functionality with XML layouts.

## Learning Outcomes

After studying this app, students will be able to:

- Use `SharedPreferences` to persist key-value data across app restarts
- Read and write preference values with `getSharedPreferences()`, `getString()`, `putString()`
- Integrate SharedPreferences access within lifecycle callbacks
- Combine `ViewModel` with SharedPreferences for state management in Compose
- Differentiate between `SharedPreferences` (survives process death) and `ViewModel` (survives configuration changes only)
- Define a UI state class for clean Compose state management

## Architecture

**Pattern:** MVVM with SharedPreferences Persistence

The `ViewModel` manages UI state and coordinates with `SharedPreferences` for persistence. The Compose UI observes the ViewModel state reactively.

| Class | Role |
|-------|------|
| `MainActivity` | Entry point with `setContent {}` |
| `MainScreen` | Composable UI observing ViewModel state |
| `MainViewModel` | Manages state, reads/writes SharedPreferences |
| `MainUiState` | Data class representing the UI state |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Jetpack Compose | Declarative UI framework |
| SharedPreferences | Lightweight key-value persistence |
| ViewModel | Lifecycle-aware state holder |
| StateFlow / LiveData | Observable reactive state |
| Material Design 3 | UI components and theming |

## How to Run

1. Open the `07-1_LifeCycle-SharedPreferences` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Enter data, close the app, and reopen it — the data is restored from SharedPreferences.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/lifecycle/
│   └── ui/main/
│       ├── MainActivity.kt              # Compose entry point
│       ├── MainScreen.kt               # Composable UI
│       ├── MainViewModel.kt            # ViewModel with SharedPreferences access
│       └── MainUiState.kt              # UI state data class
└── AndroidManifest.xml
```

## Dependencies

- AndroidX Activity Compose
- AndroidX Lifecycle ViewModel Compose
- AndroidX Lifecycle Runtime Compose
- AndroidX Compose Material 3
- AndroidX Compose BOM
