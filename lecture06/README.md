# Lecture 06 — Google Firebase and Testing Android Apps

This lecture covers two essential topics: integrating Firebase Authentication for user management and writing automated tests for Android applications. Students learn to implement sign-in flows using FirebaseUI, manage authenticated sessions, and write unit tests (JUnit), UI tests (Espresso), and Compose UI tests. Each topic is demonstrated in both Jetpack Compose and XML (MDC) variants.

## Apps

| App | Description | UI Framework |
|-----|-------------|--------------|
| [06-1_FirebaseAuthentication](./06-1_FirebaseAuthentication) | User authentication with Firebase Auth and FirebaseUI | Jetpack Compose |
| [06-2_FirebaseAuthentication-MDC](./06-2_FirebaseAuthentication-MDC) | User authentication with Firebase Auth using Fragments and Navigation | XML Layouts + View Binding |
| [06-3_TestingApps](./06-3_TestingApps) | Demonstrates unit testing with JUnit and UI testing with Compose test APIs | Jetpack Compose |
| [06-4_TestingApps-MDC](./06-4_TestingApps-MDC) | Demonstrates unit testing with JUnit and UI testing with Espresso | XML Layouts + View Binding |

## Key Concepts Covered

- Firebase project setup and `google-services.json` configuration
- Firebase Authentication with email/password and third-party providers
- FirebaseUI for pre-built authentication flows
- User session management (`FirebaseAuth.currentUser`)
- JUnit for unit testing
- Hamcrest matchers for expressive assertions
- Espresso for Android UI testing
- Compose UI test APIs (`createComposeRule`, `onNodeWithTag`, `performClick`)
- Test tags for Compose testability
- Fragment and Navigation testing

## Further Reading

- [Firebase Authentication](https://firebase.google.com/docs/auth/android/start)
- [FirebaseUI for Android](https://firebase.google.com/docs/auth/android/firebaseui)
- [Test apps on Android](https://developer.android.com/training/testing)
- [Test your Compose layout](https://developer.android.com/develop/ui/compose/testing)
- [Espresso](https://developer.android.com/training/testing/espresso)
