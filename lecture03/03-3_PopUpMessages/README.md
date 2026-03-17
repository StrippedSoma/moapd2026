# Pop-Up Messages

An Android app that demonstrates two common user feedback mechanisms: **Toast** and **Snackbar** messages. The app provides buttons to trigger each message type and showcases how to create reusable Kotlin extension functions for displaying messages throughout an application.

## Learning Outcomes

After studying this app, students will be able to:

- Display `Toast` messages for brief, non-interactive notifications
- Display `Snackbar` messages for contextual feedback with optional actions
- Create Kotlin extension functions to encapsulate reusable UI patterns
- Compare Toast (system-level, non-interactive) vs. Snackbar (view-anchored, actionable)
- Use Fragment View Binding delegation to manage view references safely
- Structure an app with Activity + Fragment architecture

## Architecture

**Pattern:** Single Activity with Fragment

The `MainActivity` hosts a `MainFragment` that handles user interactions and triggers pop-up messages.

| Class | Role |
|-------|------|
| `MainActivity` | Container activity |
| `MainFragment` | UI fragment with buttons that trigger Toast and Snackbar |
| `PopupMessagesApplication` | Custom Application subclass |
| `ToastExtensions.kt` | Kotlin extension functions for Toast messages |
| `SnackbarExtensions.kt` | Kotlin extension functions for Snackbar messages |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Toast | System-level pop-up notifications |
| Snackbar | View-anchored feedback messages with actions |
| Extension Functions | Reusable Kotlin utilities for message display |
| View Binding | Type-safe view references |
| Fragment | Modular UI component |

## How to Run

1. Open the `03-3_PopUpMessages` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Tap the buttons to see Toast and Snackbar messages in action.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/popupmessages/
│   ├── app/
│   │   └── PopupMessagesApplication.kt      # Application subclass
│   └── ui/
│       ├── main/
│       │   ├── MainActivity.kt              # Container activity
│       │   └── MainFragment.kt              # Fragment with message triggers
│       └── extensions/
│           ├── ToastExtensions.kt           # Reusable Toast helpers
│           └── SnackbarExtensions.kt        # Reusable Snackbar helpers
└── res/
    └── layout/
        └── fragment_main.xml                # UI with trigger buttons
```

## Dependencies

- AndroidX AppCompat
- Material Components for Android
- AndroidX ConstraintLayout
