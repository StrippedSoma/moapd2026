# Lecture 08 — Cloud Storage on Android

This lecture introduces cloud-based data persistence using Google Firebase. Students learn to store and synchronize structured data with Firebase Realtime Database and to upload/download files with Firebase Cloud Storage. Both services are integrated with Firebase Authentication for secure, user-scoped data access. Each topic is demonstrated in Jetpack Compose and XML (MDC) variants.

## Apps

| App | Description | UI Framework |
|-----|-------------|--------------|
| [08-1_RealtimeDatabase](./08-1_RealtimeDatabase) | Real-time data synchronization with Firebase Realtime Database | Jetpack Compose |
| [08-2_RealtimeDatabase-MDC](./08-2_RealtimeDatabase-MDC) | Firebase Realtime Database with Fragment-based UI and Navigation | XML Layouts + View Binding |
| [08-3_FirebaseStorage](./08-3_FirebaseStorage) | File upload and download with Firebase Cloud Storage | Jetpack Compose |
| [08-4_FirebaseStorage-MDC](./08-4_FirebaseStorage-MDC) | Firebase Cloud Storage with Fragment-based UI and Navigation | XML Layouts + View Binding |

## Key Concepts Covered

- Firebase Realtime Database: reading, writing, and observing data in real time
- Firebase Cloud Storage: uploading and downloading files
- Firebase Authentication integration for user-scoped data
- FirebaseUI database adapters for automatic UI synchronization
- Secure data access with Firebase security rules
- Environment configuration with `dotenv-kotlin`
- JSON data structure design for NoSQL databases
- `StateFlow` and `LiveData` for reactive UI updates
- Gson for JSON serialization/deserialization

## Further Reading

- [Firebase Realtime Database](https://firebase.google.com/docs/database/android/start)
- [Firebase Cloud Storage](https://firebase.google.com/docs/storage/android/start)
- [Firebase Security Rules](https://firebase.google.com/docs/rules)
- [Structure your database](https://firebase.google.com/docs/database/android/structure-data)
