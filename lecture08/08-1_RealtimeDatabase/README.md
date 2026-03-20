# Realtime Database (Compose)

A Jetpack Compose app that demonstrates **real-time data synchronization** using **Firebase Realtime Database**. The app integrates Firebase Authentication for user-scoped data access, allowing authenticated users to create, read, update, and delete data that synchronizes instantly across all connected clients.

> **See also:** [Realtime Database (MDC)](../08-2_RealtimeDatabase-MDC) — the same functionality with XML layouts and Fragments.

## Learning Outcomes

After studying this app, students will be able to:

- Set up Firebase Realtime Database in an Android project
- Read and write data with `DatabaseReference.setValue()` and `addValueEventListener()`
- Structure data in a NoSQL JSON tree for efficient querying
- Integrate Firebase Authentication for user-scoped database access
- Use FirebaseUI database adapters for automatic UI synchronization
- Manage environment configuration securely with `dotenv-kotlin`
- Observe real-time data changes and reflect them in Compose UI
- Understand Firebase security rules for data access control

## Architecture

**Pattern:** MVVM with Firebase Backend

The `ViewModel` manages Firebase database operations and exposes state to the Compose UI. Firebase Authentication gates access to user-scoped data.

| Class | Role |
|-------|------|
| `MainActivity` | Main content screen for authenticated users |
| `LoginActivity` | FirebaseUI authentication flow |
| `MainScreen` | Composable UI displaying real-time data |
| `MainViewModel` | Manages Firebase database operations and state |
| `MainUiState` | Data class representing the UI state |
| `RealtimeDatabaseApplication` | Custom Application subclass |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Firebase Realtime Database | Cloud-hosted NoSQL database with real-time sync |
| Firebase Auth | User authentication |
| FirebaseUI Database | Auto-syncing UI adapters for Realtime Database |
| FirebaseUI Auth | Pre-built sign-in flows |
| dotenv-kotlin | Environment-based configuration management |
| Jetpack Compose | Declarative UI framework |
| ViewModel | State management |
| Material Design 3 | UI components |

## How to Run

### Prerequisites

1. Go to the [Firebase Console](https://console.firebase.google.com/) and create a project.
2. Add an Android app with package name `dk.itu.moapd.realtimedatabase`.
3. Download `google-services.json` and place it in the `app/` directory.
4. Enable **Email/Password** authentication and **Realtime Database** in the Firebase Console.
5. Configure database security rules as needed.

### Build and Run

1. Open the `08-1_RealtimeDatabase` project in **Android Studio**.
2. Ensure `google-services.json` is in the `app/` directory.
3. Sync Gradle and let dependencies download.
4. Run the app on an emulator or physical device (min SDK 28).
5. Sign in and interact with real-time synchronized data.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/realtimedatabase/
│   ├── app/
│   │   └── RealtimeDatabaseApplication.kt   # Application subclass with Firebase DB persistence
│   ├── core/
│   │   ├── FirebaseConfig.kt                # Firebase database URL from dotenv config
│   │   └── LoggingExtensions.kt             # Logging tag extension function
│   ├── data/
│   │   └── repository/
│   │       └── DummyRepository.kt           # Firebase CRUD operations for Dummy data
│   ├── domain/
│   │   └── model/
│   │       └── Dummy.kt                     # Data model for Firebase Realtime Database
│   └── ui/
│       ├── auth/
│       │   └── LoginActivity.kt             # FirebaseUI authentication flow
│       ├── common/
│       │   └── ViewExtensions.kt            # Snackbar view extension helpers
│       ├── main/
│       │   ├── components/
│       │   │   ├── DummyEditDialog.kt       # Composable dialog for editing dummy data
│       │   │   └── DummyRow.kt              # Composable row item for dummy list
│       │   ├── MainActivity.kt              # Main Compose activity with auth gating
│       │   ├── MainScreen.kt                # Composable screen with real-time data list
│       │   ├── MainUiState.kt               # UI state data class
│       │   └── MainViewModel.kt             # ViewModel managing Firebase database state
│       └── theme/
│           ├── Color.kt                     # Material 3 color definitions
│           ├── Theme.kt                     # Material 3 theme configuration
│           └── Type.kt                      # Material 3 typography definitions
├── res/
│   ├── drawable/
│   │   ├── baseline_account_box_24.xml      # Account icon
│   │   ├── baseline_add_box_24.xml          # Add icon
│   │   ├── baseline_firebase_24.xml         # Firebase icon
│   │   ├── baseline_swap_horiz_24.xml       # Swap icon
│   │   ├── ic_launcher_background.xml       # Launcher icon background
│   │   └── ic_launcher_foreground.xml       # Launcher icon foreground
│   ├── values-v31/
│   │   └── themes.xml                       # Theme overrides for Android 12+
│   ├── values/
│   │   ├── colors.xml                       # Color definitions
│   │   ├── strings.xml                      # String resources
│   │   └── themes.xml                       # App theme
│   └── xml/
│       ├── backup_rules.xml                 # Backup rules for Android 12+
│       └── data_extraction_rules.xml        # Data extraction rules
├── assets/
│   └── env                                  # Environment configuration file
└── AndroidManifest.xml                      # App manifest
```

## Dependencies

- Firebase Database KTX
- Firebase Auth KTX
- FirebaseUI Database
- FirebaseUI Auth
- dotenv-kotlin
- AndroidX Lifecycle ViewModel Compose
- AndroidX Lifecycle Runtime Compose
- AndroidX Compose Material 3
- Material Icons Extended
