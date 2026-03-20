# Realtime Database (MDC)

An Android app that demonstrates **Firebase Realtime Database** with a **Fragment-based UI** and the **Navigation component**. The app integrates Firebase Authentication for user-scoped access and uses FirebaseUI adapters to automatically synchronize the list display with database changes.

> **See also:** [Realtime Database (Compose)](../08-1_RealtimeDatabase) — the same functionality with Jetpack Compose.

## Learning Outcomes

After studying this app, students will be able to:

- Integrate Firebase Realtime Database with Fragment-based architecture
- Use FirebaseUI `FirebaseRecyclerAdapter` for automatic list synchronization
- Navigate between list, detail, and dialog screens with Navigation component
- Structure Firebase data for efficient queries and real-time updates
- Manage authentication state across fragments
- Use `dotenv-kotlin` for secure environment configuration
- Compare Firebase integration in Fragment-based vs. Compose architectures

## Architecture

**Pattern:** Single Activity + Fragment Navigation + Firebase Backend

The `MainActivity` hosts a `NavHostFragment`. Fragments observe Firebase database changes through FirebaseUI adapters.

| Class | Role |
|-------|------|
| `MainActivity` | Navigation host with app bar |
| `MainFragment` | Displays real-time data list |
| `LoginActivity` | FirebaseUI authentication flow |
| `RealtimeDatabaseApplication` | Custom Application subclass |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Firebase Realtime Database | Cloud NoSQL database with real-time sync |
| Firebase Auth | User authentication |
| FirebaseUI Database | RecyclerView adapters for real-time data |
| FirebaseUI Auth | Pre-built sign-in flows |
| dotenv-kotlin | Environment configuration |
| AndroidX Navigation | Fragment navigation management |
| View Binding | Type-safe view references |
| Material Components | UI styling |

## How to Run

### Prerequisites

1. Go to the [Firebase Console](https://console.firebase.google.com/) and create a project.
2. Add an Android app with package name `dk.itu.moapd.realtimedatabase`.
3. Download `google-services.json` and place it in the `app/` directory.
4. Enable **Email/Password** authentication and **Realtime Database** in the Firebase Console.
5. Configure database security rules as needed.

### Build and Run

1. Open the `08-2_RealtimeDatabase-MDC` project in **Android Studio**.
2. Ensure `google-services.json` is in the `app/` directory.
3. Sync Gradle and let dependencies download.
4. Run the app on an emulator or physical device (min SDK 28).

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/realtimedatabase/
│   ├── app/
│   │   └── RealtimeDatabaseApplication.kt   # Application subclass with Dynamic Colors
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
│       ├── dialogs/
│       │   ├── AddDataDialogFragment.kt     # Dialog for adding new data
│       │   └── UpdateDataDialogFragment.kt  # Dialog for updating existing data
│       ├── list/
│       │   ├── DummiesAdapter.kt            # FirebaseRecyclerAdapter for dummy list
│       │   ├── DummyItemLongClickListener.kt # Long-press listener interface
│       │   └── SwipeToDeleteCallback.kt     # Swipe-to-delete gesture handler
│       ├── main/
│       │   ├── MainActivity.kt              # Navigation host with app bar
│       │   └── MainFragment.kt              # Fragment displaying real-time data list
│       └── utils/
│           └── FragmentViewBindingDelegate.kt # View binding lifecycle delegate
├── res/
│   ├── drawable/
│   │   ├── baseline_account_box_24.xml      # Account icon
│   │   ├── baseline_add_box_24.xml          # Add icon
│   │   ├── baseline_firebase_24.xml         # Firebase icon
│   │   ├── baseline_swap_horiz_24.xml       # Swap icon
│   │   ├── ic_launcher_background.xml       # Launcher icon background
│   │   └── ic_launcher_foreground.xml       # Launcher icon foreground
│   ├── layout/
│   │   ├── activity_main.xml                # Main activity layout with NavHost
│   │   ├── content_main.xml                 # Content area layout
│   │   ├── dialog_dummy_data.xml            # Dialog layout for data input
│   │   ├── fragment_main.xml                # Main fragment layout with RecyclerView
│   │   └── row_item.xml                     # List item layout for dummy data
│   ├── menu/
│   │   └── top_app_bar.xml                  # Top app bar menu items
│   ├── navigation/
│   │   └── nav_graph.xml                    # Navigation graph
│   ├── values-night/
│   │   └── themes.xml                       # Dark theme overrides
│   ├── values/
│   │   ├── colors.xml                       # Color definitions
│   │   ├── dimens.xml                       # Dimension values
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
- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- Material Components for Android
