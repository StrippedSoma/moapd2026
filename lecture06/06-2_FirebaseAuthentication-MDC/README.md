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
│   │   └── FirebaseAuthenticationApplication.kt  # Application subclass
│   └── ui/
│       ├── main/
│       │   └── MainActivity.kt                   # Navigation host with app bar
│       └── auth/
│           └── LoginActivity.kt                  # FirebaseUI sign-in
├── res/
│   ├── layout/                                    # Activity and fragment layouts
│   └── navigation/                                # Navigation graph
└── google-services.json                           # Firebase configuration (not committed)
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
