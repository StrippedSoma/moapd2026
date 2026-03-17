# Room Database (MDC)

An Android app that demonstrates **Room database** integration with a **Fragment-based UI** and the **Navigation component**. The app provides CRUD operations on a local SQLite database with Room, using dialogs for data input and a list display for stored items.

> **See also:** [Room Database (Compose)](../07-3_RoomDatabase) — the same functionality with Jetpack Compose.

## Learning Outcomes

After studying this app, students will be able to:

- Set up Room with Fragment-based architecture and Navigation component
- Create dialog fragments for data input in a navigation graph
- Observe Room database changes with `LiveData` in Fragment lifecycle
- Implement CRUD operations through a Repository pattern
- Use KSP for Room annotation processing
- Navigate between list, detail, and dialog screens with NavController
- Compare Room integration in Fragment-based vs. Compose architectures

## Architecture

**Pattern:** MVVM with Repository + Room + Fragment Navigation

The architecture uses Navigation-managed fragments for each screen, with a shared ViewModel backed by Room database through a Repository.

| Class | Role |
|-------|------|
| `MainActivity` | Hosts NavHostFragment, sets up navigation |
| `MainFragment` | Displays list of database items |
| `DummyViewModel` | Exposes database data; delegates to Repository |
| `DummyRepository` | Abstracts DAO operations |
| `DummyDao` | Room DAO with SQL-annotated methods |
| `DummyRoomDatabase` | Room database singleton |
| `Dummy` | Room `@Entity` data class |
| `RoomStorageApplication` | Initializes database instance |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Room | Type-safe SQLite database abstraction |
| LiveData | Reactive database observation |
| ViewModel | Lifecycle-safe state management |
| Repository Pattern | Data source abstraction |
| KSP | Annotation processing for Room |
| AndroidX Navigation | Fragment and dialog navigation |
| View Binding | Type-safe view references |
| Material Components | UI styling and dialogs |

## How to Run

1. Open the `07-4_RoomDatabase-MDC` project in **Android Studio**.
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
│   │   ├── DummyDao.kt                  # @Dao interface
│   │   ├── DummyRoomDatabase.kt         # @Database singleton
│   │   └── DummyRepository.kt           # Repository
│   └── ui/main/
│       ├── MainActivity.kt              # Navigation host
│       └── MainFragment.kt             # List display fragment
├── res/
│   ├── layout/                           # Activity, fragment, and dialog layouts
│   └── navigation/                       # Navigation graph with dialog destinations
└── AndroidManifest.xml
```

## Dependencies

- AndroidX Room KTX
- AndroidX Room Compiler (KSP)
- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- AndroidX Lifecycle ViewModel KTX
- AndroidX Lifecycle LiveData KTX
- Material Components for Android
