# LifeCycle (Compose)

A Jetpack Compose app that demonstrates **lifecycle management and state preservation** using `ViewModel` within the Compose framework. This is the Compose equivalent of the lifecycle apps in [Lecture 02](../../lecture02), showing how ViewModel integration works naturally with Compose's reactive state model.

> **See also:** [LifeCycle — ViewModel](../../lecture02/02-3_LifeCycle-vm) — the XML-based ViewModel approach for comparison.

## Learning Outcomes

After studying this app, students will be able to:

- Integrate `ViewModel` with Jetpack Compose using `viewModel()` composable
- Collect state from ViewModel using `collectAsState()` or `observeAsState()`
- Understand how Compose's lifecycle differs from the traditional Activity/Fragment lifecycle
- Manage UI state reactively with `StateFlow` or `LiveData` in a ViewModel
- Handle configuration changes transparently through ViewModel + Compose
- Define UI state classes for clean state management

## Architecture

**Pattern:** MVVM with Jetpack Compose

The `ViewModel` exposes state that Compose observes reactively. State changes trigger recomposition automatically.

| Class | Role |
|-------|------|
| `MainActivity` | Entry point with `setContent {}` |
| `MainScreen` | Composable function that observes ViewModel state |
| `MainViewModel` | Holds and exposes UI state; survives configuration changes |
| `MainUiState` | Data class representing the current UI state |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Jetpack Compose | Declarative UI framework |
| ViewModel | Lifecycle-aware state holder |
| StateFlow / LiveData | Observable reactive state |
| viewModel() | Composable function for ViewModel access |
| Material Design 3 | UI components and theming |

## How to Run

1. Open the `05-2_LifeCycle-compose` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Interact with the UI, then rotate the device — state is preserved via ViewModel.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/lifecycle/
│   └── ui/main/
│       ├── MainActivity.kt              # Compose entry point
│       ├── MainScreen.kt               # Composable observing ViewModel state
│       ├── MainViewModel.kt            # ViewModel with reactive state
│       └── MainUiState.kt              # UI state data class
└── AndroidManifest.xml
```

## Dependencies

- AndroidX Activity Compose
- AndroidX Lifecycle ViewModel Compose
- AndroidX Lifecycle Runtime Compose
- AndroidX Compose Material 3
- AndroidX Compose BOM
