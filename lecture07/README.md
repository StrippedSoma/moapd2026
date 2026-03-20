# Lecture 07 — Local Storage on Android

This lecture focuses on persisting data locally on Android devices. Students learn two key persistence mechanisms: `SharedPreferences` for simple key-value storage and Room for structured `SQLite database` access. Each mechanism is demonstrated in both Jetpack Compose and XML (MDC) variants, showing how local storage integrates with different UI paradigms.

## Apps

| App | Description | UI Framework |
|-----|-------------|--------------|
| [07-1_LifeCycle-SharedPreferences](./07-1_LifeCycle-SharedPreferences) | Persists UI state across app restarts using `SharedPreferences` | Jetpack Compose |
| [07-2_LifeCycle-SharedPreferences-MDC](./07-2_LifeCycle-SharedPreferences-MDC) | Persists UI state using `SharedPreferences` with View Binding | XML Layouts + View Binding |
| [07-3_RoomDatabase](./07-3_RoomDatabase) | Full CRUD operations with Room database, Repository pattern, and `ViewModel` | Jetpack Compose |
| [07-4_RoomDatabase-MDC](./07-4_RoomDatabase-MDC) | Room database with Fragment-based UI and Navigation component | XML Layouts + View Binding |

## Key Concepts Covered

- `SharedPreferences` API for lightweight key-value persistence
- Reading and writing preferences in lifecycle callbacks
- Room database architecture: `@Entity`, `@Dao`, `@Database`
- Room query annotations: `@Query`, `@Insert`, `@Update`, `@Delete`
- Reactive data access with `Flow` and `LiveData`
- The Repository pattern for abstracting data sources
- KSP (Kotlin Symbol Processing) for Room annotation processing
- MVVM architecture with database-backed `ViewModel`
- Swipe-to-dismiss gestures for delete operations

## Further Reading

- [SharedPreferences](https://developer.android.com/training/data-storage/shared-preferences)
- [Room persistence library](https://developer.android.com/training/data-storage/room)
- [Data and file storage overview](https://developer.android.com/training/data-storage)
- [Write asynchronous DAO queries](https://developer.android.com/training/data-storage/room/async-queries)
