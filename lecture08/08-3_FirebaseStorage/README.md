# Firebase Storage (Compose)

A Jetpack Compose app that demonstrates **file upload and download** using **Firebase Cloud Storage**. The app integrates Firebase Authentication for user-scoped storage access and Firebase Realtime Database for file metadata management. Users can upload files to the cloud and retrieve them, with the UI displaying upload progress and file listings.

> **See also:** [Firebase Storage (MDC)](../08-4_FirebaseStorage-MDC) — the same functionality with XML layouts and Fragments.

## Learning Outcomes

After studying this app, students will be able to:

- Set up Firebase Cloud Storage in an Android project
- Upload files with `StorageReference.putFile()` and monitor progress
- Download files and generate download URLs with `getDownloadUrl()`
- Store file metadata in Firebase Realtime Database alongside storage references
- Handle the `POST_NOTIFICATIONS` runtime permission for upload/download notifications
- Use Gson for JSON serialization of file metadata
- Manage authenticated cloud storage access with Firebase security rules
- Integrate Firebase Storage with Compose UI and ViewModel

## Architecture

**Pattern:** MVVM with Firebase Storage + Realtime Database Backend

The `ViewModel` coordinates between Firebase Storage (files) and Realtime Database (metadata). Authentication gates access to user-scoped storage.

| Class | Role |
|-------|------|
| `MainActivity` | Main Compose screen for file management |
| `LoginActivity` | FirebaseUI authentication flow |
| `MainScaffold` | Composable scaffold with file list and actions |
| `FirebaseStorageApplication` | Custom Application subclass |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Firebase Cloud Storage | Cloud file storage service |
| Firebase Realtime Database | File metadata storage |
| Firebase Auth | User authentication |
| FirebaseUI Auth | Pre-built sign-in flows |
| FirebaseUI Database | Auto-syncing UI adapters |
| Gson | JSON serialization for metadata |
| dotenv-kotlin | Environment configuration |
| Jetpack Compose | Declarative UI framework |
| Material Design 3 | UI components |

## How to Run

### Prerequisites

1. Go to the [Firebase Console](https://console.firebase.google.com/) and create a project.
2. Add an Android app with package name `dk.itu.moapd.firebasestorage`.
3. Download `google-services.json` and place it in the `app/` directory.
4. Enable **Email/Password** authentication, **Realtime Database**, and **Cloud Storage** in the Firebase Console.
5. Configure storage and database security rules.

### Build and Run

1. Open the `08-3_FirebaseStorage` project in **Android Studio**.
2. Ensure `google-services.json` is in the `app/` directory.
3. Sync Gradle and let dependencies download.
4. Run the app on an emulator or physical device (min SDK 28).
5. Sign in and upload/download files.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/firebasestorage/
│   ├── app/
│   │   └── FirebaseStorageApplication.kt    # Application subclass with Firebase DB persistence
│   ├── core/
│   │   ├── FirebaseConfig.kt                # Firebase configuration from dotenv
│   │   └── LoggingExtensions.kt             # Logging tag extension function
│   ├── data/
│   │   └── repository/
│   │       ├── ImageRepository.kt           # Firebase database operations for image metadata
│   │       └── StorageRepository.kt         # Firebase Storage file upload/download
│   ├── domain/
│   │   └── model/
│   │       └── Image.kt                     # Data model for image metadata
│   └── ui/
│       ├── auth/
│       │   └── LoginActivity.kt             # FirebaseUI authentication flow
│       ├── main/
│       │   ├── components/
│       │   │   └── NotificationPermissionRationaleDialog.kt # Permission rationale dialog
│       │   ├── MainActivity.kt              # Main Compose activity with auth and permissions
│       │   └── MainScaffold.kt              # Scaffold composable with image list and actions
│       ├── screens/
│       │   ├── ImageDetailScreen.kt         # Full-screen image detail view
│       │   └── ImagesGridScreen.kt          # Composable grid of uploaded images
│       └── theme/
│           ├── Color.kt                     # Material 3 color definitions
│           ├── Theme.kt                     # Material 3 theme configuration
│           └── Type.kt                      # Material 3 typography definitions
├── res/
│   ├── drawable/
│   │   ├── baseline_firebase_24.xml         # Firebase icon
│   │   ├── ic_launcher_background.xml       # Launcher icon background
│   │   └── ic_launcher_foreground.xml       # Launcher icon foreground
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

- Firebase Storage KTX
- Firebase Database KTX
- Firebase Auth KTX
- FirebaseUI Auth
- FirebaseUI Database
- Gson
- dotenv-kotlin
- AndroidX Lifecycle ViewModel Compose
- AndroidX Lifecycle Runtime Compose
- AndroidX Compose Material 3
- Material Icons Extended
