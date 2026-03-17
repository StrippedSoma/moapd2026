# Room Database (Compose)

A Jetpack Compose app that demonstrates **structured data persistence** using the **Room** persistence library. The app provides full CRUD (Create, Read, Update, Delete) operations on a local SQLite database through Room's type-safe abstractions. It follows the MVVM architecture with a Repository pattern, reactive data streams via `Flow`, and swipe-to-dismiss delete gestures.

> **See also:** [Room Database (MDC)](../07-4_RoomDatabase-MDC) — the same functionality with XML layouts and Fragments.

## Learning Outcomes

After studying this app, students will be able to:

- Define Room database components: `@Entity`, `@Dao`, `@Database`
- Write SQL queries with Room annotations: `@Query`, `@Insert`, `@Update`, `@Delete`
- Use `Flow<List<T>>` for reactive, auto-updating data streams from the database
- Convert `Flow` to `LiveData` with `asLiveData()` for Compose observation
- Implement the Repository pattern to abstract data source access
- Set up KSP (Kotlin Symbol Processing) for Room annotation processing
- Create a Room database singleton with `Room.databaseBuilder()`
- Implement swipe-to-dismiss gestures (`SwipeToDismissBox`) for item deletion

## Architecture

**Pattern:** MVVM with Repository + Room Database

The architecture separates concerns into four layers: UI (Compose), ViewModel, Repository, and Database (Room).

| Class | Role |
|-------|------|
| `MainActivity` | Entry point with `setContent {}` |
| `MainScreen` | Composable UI with LazyColumn and SwipeToDismiss |
| `DummyViewModel` | Exposes database data via LiveData; delegates operations to Repository |
| `DummyRepository` | Abstracts DAO operations for the ViewModel |
| `DummyDao` | Room DAO with SQL-annotated query methods |
| `DummyRoomDatabase` | Room database singleton with entity configuration |
| `Dummy` | Room `@Entity` data class representing a database row |
| `RoomStorageApplication` | Initializes database instance |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Room | Type-safe SQLite database abstraction |
| Flow | Reactive data stream from database queries |
| LiveData | Lifecycle-aware observable for Compose |
| ViewModel | Lifecycle-safe state and operation management |
| Repository Pattern | Abstracts data source from ViewModel |
| KSP | Annotation processing for Room |
| Jetpack Compose | Declarative UI framework |
| SwipeToDismissBox | Gesture-driven delete interaction |
| Material Design 3 | UI components and theming |

## How to Run

1. Open the `07-3_RoomDatabase` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Add, update, and delete items. Data persists across app restarts.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/roomdatabase/
│   ├── app/
│   │   └── RoomStorageApplication.kt    # Initializes Room database
│   ├── data/
│   │   ├── Dummy.kt                     # @Entity data class
│   │   ├── DummyDao.kt                  # @Dao interface with SQL queries
│   │   ├── DummyRoomDatabase.kt         # @Database singleton
│   │   └── DummyRepository.kt           # Repository abstracting DAO access
│   └── ui/main/
│       ├── MainActivity.kt              # Compose entry point
│       ├── MainScreen.kt               # LazyColumn with SwipeToDismiss
│       └── DummyViewModel.kt           # ViewModel with Repository access
└── AndroidManifest.xml
```

## Dependencies

- AndroidX Room KTX
- AndroidX Room Compiler (KSP)
- AndroidX Lifecycle ViewModel Compose
- AndroidX Lifecycle LiveData KTX
- AndroidX Compose Runtime LiveData
- AndroidX Compose Material 3
- AndroidX Compose BOM
