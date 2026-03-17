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
│   │   └── RealtimeDatabaseApplication.kt   # Application subclass
│   └── ui/
│       ├── main/
│       │   ├── MainActivity.kt              # Navigation host
│       │   └── MainFragment.kt             # Real-time data list
│       └── auth/
│           └── LoginActivity.kt             # FirebaseUI sign-in
├── res/
│   ├── layout/                               # Activity, fragment, dialog layouts
│   └── navigation/                           # Navigation graph
└── google-services.json                      # Firebase config (not committed)
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
