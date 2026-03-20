# Firebase Authentication (Compose)

A Jetpack Compose app that implements **user authentication using Firebase Authentication** and **FirebaseUI**. The app provides a complete sign-in/sign-out flow, user profile display, and session management. Users can sign in with email/password or supported third-party providers through FirebaseUI's pre-built authentication screens.

> **See also:** [Firebase Authentication (MDC)](../06-2_FirebaseAuthentication-MDC) — the same functionality built with XML layouts and Fragments.

## Learning Outcomes

After studying this app, students will be able to:

- Set up a Firebase project and add `google-services.json` to an Android app
- Implement Firebase Authentication with email/password sign-in
- Use FirebaseUI for pre-built, customizable authentication flows
- Manage user sessions with `FirebaseAuth.currentUser`
- Navigate between authenticated and unauthenticated screens
- Display user profile information (name, email, photo URL)
- Load user profile images with Coil Compose
- Handle sign-out and session cleanup

## Architecture

**Pattern:** MVVM with Jetpack Compose + Firebase

The app uses a `LoginActivity` for authentication (via FirebaseUI) and a `MainActivity` for the main content. Navigation between authenticated and unauthenticated states is managed through activity-level checks.

| Class | Role |
|-------|------|
| `MainActivity` | Main content screen for authenticated users |
| `LoginActivity` | Hosts FirebaseUI authentication flow |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Jetpack Compose | Declarative UI framework |
| Firebase Auth | User authentication backend |
| FirebaseUI Auth | Pre-built sign-in flows |
| Coil Compose | User profile image loading |
| Compose Navigation | Screen navigation |
| Material Design 3 | UI components and theming |
| Material Icons Extended | Extended icon set |

## How to Run

### Prerequisites

This app requires a Firebase project. Follow these steps:

1. Go to the [Firebase Console](https://console.firebase.google.com/) and create a new project (or use an existing one).
2. Add an Android app with package name `dk.itu.moapd.firebaseauthentication`.
3. Download the `google-services.json` file and place it in the `app/` directory.
4. In the Firebase Console, enable **Email/Password** authentication under Authentication > Sign-in method.

### Build and Run

1. Open the `06-1_FirebaseAuthentication` project in **Android Studio**.
2. Ensure `google-services.json` is in the `app/` directory.
3. Sync Gradle and let dependencies download.
4. Run the app on an emulator or physical device (min SDK 28).
5. Sign in with email/password to access the main content.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/firebaseauthentication/
│   ├── app/
│   │   └── FirebaseAuthenticationApplication.kt  # Custom Application subclass for global state
│   └── ui/
│       ├── auth/
│       │   └── LoginActivity.kt                  # FirebaseUI sign-in flow
│       ├── dialogs/
│       │   └── UserInfoDialog.kt                  # Compose dialog showing user profile info
│       ├── main/
│       │   ├── MainActivity.kt                    # Main screen for authenticated users
│       │   └── MainScaffold.kt                    # Scaffold with top/bottom bars and navigation
│       ├── screens/
│       │   ├── albums/
│       │   │   └── AlbumsScreen.kt                # Grid screen displaying album artwork
│       │   ├── articles/
│       │   │   └── ArticlesScreen.kt              # Scrollable articles screen with fake data
│       │   └── contacts/
│       │       └── ContactsScreen.kt              # Lazy list of contacts with avatars
│       └── theme/
│           ├── Color.kt                           # Material 3 color definitions
│           ├── Theme.kt                           # App-wide theme configuration
│           └── Type.kt                            # Material typography styles
├── res/
│   ├── drawable/
│   │   ├── baseline_article_24.xml                # Article icon
│   │   ├── baseline_circle_24.xml                 # Circle icon
│   │   ├── baseline_dashboard_24.xml              # Dashboard icon
│   │   ├── baseline_firebase_24.xml               # Firebase logo icon
│   │   ├── baseline_people_24.xml                 # People icon
│   │   ├── baseline_person_24.xml                 # Person icon
│   │   ├── ic_launcher_background.xml             # Launcher icon background
│   │   └── ic_launcher_foreground.xml             # Launcher icon foreground
│   ├── drawable-nodpi/
│   │   ├── album_art_01.jpg ... album_art_20.jpg  # Album cover artwork images
│   │   ├── chuck_norris.jpg                       # Sample image for articles
│   │   └── meme.jpg                               # Sample meme image
│   ├── values/
│   │   ├── colors.xml                             # Color definitions
│   │   ├── strings.xml                            # String resources
│   │   └── themes.xml                             # App theme
│   ├── values-v31/
│   │   └── themes.xml                             # Theme overrides for API 31+
│   └── xml/
│       ├── backup_rules.xml                       # Backup rules for Android 12+
│       └── data_extraction_rules.xml              # Data extraction rules
└── AndroidManifest.xml                            # App manifest with INTERNET permission
```

## Dependencies

- Firebase Auth KTX
- FirebaseUI Auth
- AndroidX Activity Compose
- AndroidX Compose Navigation
- AndroidX Compose Material 3
- Coil Compose + Coil OkHttp
- Picasso / CircleImageView
- JavaFaker
