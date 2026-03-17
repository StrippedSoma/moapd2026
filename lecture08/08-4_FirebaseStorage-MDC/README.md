# Firebase Storage (MDC)

An Android app that demonstrates **Firebase Cloud Storage** with a **Fragment-based UI** and the **Navigation component**. The app allows authenticated users to upload and download files, storing metadata in Firebase Realtime Database and displaying files with Picasso image loading.

> **See also:** [Firebase Storage (Compose)](../08-3_FirebaseStorage) — the same functionality with Jetpack Compose.

## Learning Outcomes

After studying this app, students will be able to:

- Integrate Firebase Cloud Storage with Fragment-based architecture
- Upload files to Firebase Storage and monitor transfer progress
- Store and retrieve file metadata from Firebase Realtime Database
- Display images from Firebase Storage URLs using Picasso
- Navigate between file list and upload screens with Navigation component
- Handle notification permissions for transfer progress
- Compare Firebase Storage integration in Fragment vs. Compose architectures

## Architecture

**Pattern:** Single Activity + Fragment Navigation + Firebase Backend

The `MainActivity` hosts a `NavHostFragment`. Fragments handle file listing, uploading, and downloading through Firebase services.

| Class | Role |
|-------|------|
| `MainActivity` | Navigation host with app bar |
| `MainFragment` | File list display and actions |
| `LoginActivity` | FirebaseUI authentication flow |
| `FirebaseStorageApplication` | Custom Application subclass |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Firebase Cloud Storage | Cloud file storage |
| Firebase Realtime Database | File metadata storage |
| Firebase Auth | User authentication |
| FirebaseUI Auth | Pre-built sign-in flows |
| FirebaseUI Database | Auto-syncing RecyclerView adapters |
| Gson | JSON serialization |
| Picasso | Image loading from storage URLs |
| dotenv-kotlin | Environment configuration |
| AndroidX Navigation | Fragment navigation |
| View Binding | Type-safe view references |

## How to Run

### Prerequisites

1. Go to the [Firebase Console](https://console.firebase.google.com/) and create a project.
2. Add an Android app with package name `dk.itu.moapd.firebasestorage`.
3. Download `google-services.json` and place it in the `app/` directory.
4. Enable **Email/Password** authentication, **Realtime Database**, and **Cloud Storage** in the Firebase Console.
5. Configure storage and database security rules.

### Build and Run

1. Open the `08-4_FirebaseStorage-MDC` project in **Android Studio**.
2. Ensure `google-services.json` is in the `app/` directory.
3. Sync Gradle and let dependencies download.
4. Run the app on an emulator or physical device (min SDK 28).

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/firebasestorage/
│   ├── app/
│   │   └── FirebaseStorageApplication.kt    # Application subclass
│   └── ui/
│       ├── main/
│       │   ├── MainActivity.kt              # Navigation host
│       │   └── MainFragment.kt             # File list and operations
│       └── auth/
│           └── LoginActivity.kt             # FirebaseUI sign-in
├── res/
│   ├── layout/                               # Activity and fragment layouts
│   └── navigation/                           # Navigation graph
└── google-services.json                      # Firebase config (not committed)
```

## Dependencies

- Firebase Storage KTX
- Firebase Database KTX
- Firebase Auth KTX
- FirebaseUI Auth
- FirebaseUI Database
- Gson
- Picasso
- dotenv-kotlin
- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- Material Components for Android
