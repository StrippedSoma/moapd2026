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
│   │   └── FirebaseStorageApplication.kt    # Application subclass with Dynamic Colors
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
│       ├── common/
│       │   └── ViewExtensions.kt            # Snackbar view extension helpers
│       ├── detail/
│       │   └── ImageFragment.kt             # Fragment for full-screen image display
│       ├── dialogs/
│       │   └── DeleteImageDialogFragment.kt # Confirmation dialog for image deletion
│       ├── list/
│       │   ├── ImageItemListener.kt         # Click/long-press listener interface
│       │   └── ImagesAdapter.kt             # FirebaseRecyclerAdapter for image grid
│       ├── main/
│       │   ├── MainActivity.kt              # Navigation host with permissions handling
│       │   └── MainFragment.kt              # Fragment displaying image grid
│       └── utils/
│           ├── FragmentViewBindingDelegate.kt # View binding lifecycle delegate
│           └── NavigationArgs.kt            # Navigation argument key constants
├── res/
│   ├── drawable/
│   │   ├── baseline_add_box_24.xml          # Add icon
│   │   ├── baseline_arrow_back_24.xml       # Back arrow icon
│   │   ├── baseline_firebase_24.xml         # Firebase icon
│   │   ├── ic_launcher_background.xml       # Launcher icon background
│   │   └── ic_launcher_foreground.xml       # Launcher icon foreground
│   ├── layout/
│   │   ├── activity_main.xml                # Main activity layout with NavHost
│   │   ├── content_main.xml                 # Content area layout
│   │   ├── fragment_image.xml               # Image detail fragment layout
│   │   ├── fragment_main.xml                # Main fragment layout with grid
│   │   └── row_item.xml                     # Grid item layout for images
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
