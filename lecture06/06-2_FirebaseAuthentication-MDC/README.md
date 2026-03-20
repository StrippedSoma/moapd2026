# Firebase Authentication (MDC)

An Android app that implements **user authentication using Firebase Authentication** with an **XML layout** and **Fragment-based** UI. This is the Material Design Components (MDC) variant, using Navigation component for screen transitions and View Binding for type-safe view references.

> **See also:** [Firebase Authentication (Compose)](../06-1_FirebaseAuthentication) — the same functionality built with Jetpack Compose.

## Learning Outcomes

After studying this app, students will be able to:

- Set up Firebase Authentication with a Fragment-based architecture
- Implement FirebaseUI sign-in flows within the Navigation component
- Use `FirebaseAuth.currentUser` for session management
- Navigate between authentication and content screens using `NavController`
- Display user profile information with Picasso image loading
- Handle authentication state changes in a fragment lifecycle
- Compare the Firebase integration in XML/Fragment vs. Compose approaches

## Architecture

**Pattern:** Single Activity with Fragment Navigation + Firebase

The `MainActivity` hosts a `NavHostFragment`. Authentication is handled by a `LoginActivity` using FirebaseUI. Content fragments display user data through the Navigation framework.

| Class | Role |
|-------|------|
| `MainActivity` | Hosts NavHostFragment, manages navigation and app bar |
| `LoginActivity` | FirebaseUI authentication flow |
| `FirebaseAuthenticationApplication` | Custom Application subclass |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Firebase Auth | User authentication backend |
| FirebaseUI Auth | Pre-built sign-in flows |
| AndroidX Navigation | Fragment-based screen navigation |
| View Binding | Type-safe view references |
| Picasso | Image loading for user profiles |
| CircleImageView | Circular profile image display |
| JavaFaker | Fake data generation |

## How to Run

### Prerequisites

This app requires a Firebase project. Follow these steps:

1. Go to the [Firebase Console](https://console.firebase.google.com/) and create a new project (or use an existing one).
2. Add an Android app with package name `dk.itu.moapd.firebaseauthentication`.
3. Download the `google-services.json` file and place it in the `app/` directory.
4. In the Firebase Console, enable **Email/Password** authentication under Authentication > Sign-in method.

### Build and Run

1. Open the `06-2_FirebaseAuthentication-MDC` project in **Android Studio**.
2. Ensure `google-services.json` is in the `app/` directory.
3. Sync Gradle and let dependencies download.
4. Run the app on an emulator or physical device (min SDK 28).
5. Sign in with email/password to access the main content.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/firebaseauthentication/
│   ├── app/
│   │   └── FirebaseAuthenticationApplication.kt   # Custom Application subclass for global state
│   └── ui/
│       ├── auth/
│       │   └── LoginActivity.kt                   # FirebaseUI sign-in flow
│       ├── common/
│       │   ├── Dimens.kt                           # dp-to-px conversion utility
│       │   └── ViewExtensions.kt                  # Snackbar and dimension extension functions
│       ├── dialogs/
│       │   └── UserInfoDialogFragment.kt           # Dialog showing user profile info
│       ├── main/
│       │   └── MainActivity.kt                    # Navigation host with app bar
│       ├── tabs/
│       │   ├── albums/
│       │   │   └── AlbumsFragment.kt               # Grid fragment displaying album artwork
│       │   ├── articles/
│       │   │   └── ArticlesFragment.kt             # Fragment displaying article content
│       │   └── contacts/
│       │       └── ContactsFragment.kt             # Fragment with contacts list
│       └── utils/
│           └── FragmentViewBindingDelegate.kt      # Lifecycle-aware view binding delegate
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
│   ├── layout/
│   │   ├── activity_main.xml                      # Main activity layout with toolbar
│   │   ├── contact_row_item.xml                   # Contact list row item layout
│   │   ├── content_main.xml                       # NavHostFragment container
│   │   ├── dialog_user_info.xml                   # User info dialog layout
│   │   ├── fragment_albums.xml                    # Albums grid layout
│   │   ├── fragment_articles.xml                  # Articles content layout
│   │   └── fragment_contacts.xml                  # Contacts list layout
│   ├── layout-land/
│   │   ├── activity_main.xml                      # Landscape main activity layout
│   │   ├── content_main.xml                       # Landscape NavHostFragment container
│   │   ├── dialog_user_info.xml                   # Landscape user info dialog layout
│   │   └── fragment_albums.xml                    # Landscape albums grid layout
│   ├── menu/
│   │   ├── bottom_navigation_menu.xml             # Bottom navigation bar items
│   │   └── top_app_bar.xml                        # Top app bar menu items
│   ├── navigation/
│   │   └── nav_graph.xml                          # Navigation graph with fragment destinations
│   ├── values/
│   │   ├── colors.xml                             # Color definitions
│   │   ├── dimens.xml                             # Dimension values
│   │   ├── strings.xml                            # String resources
│   │   └── themes.xml                             # App theme
│   ├── values-night/
│   │   └── themes.xml                             # Dark mode theme overrides
│   └── xml/
│       ├── backup_rules.xml                       # Backup rules for Android 12+
│       └── data_extraction_rules.xml              # Data extraction rules
└── AndroidManifest.xml                            # App manifest with INTERNET permission
```

## Dependencies

- Firebase Auth KTX
- FirebaseUI Auth
- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- Picasso
- CircleImageView
- JavaFaker
- Material Components for Android
