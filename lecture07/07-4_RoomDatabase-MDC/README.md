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
│   │   └── RoomStorageApplication.kt            # Application class initializing Room database
│   ├── data/
│   │   ├── local/
│   │   │   ├── DummyDao.kt                      # @Dao interface with SQL query methods
│   │   │   └── DummyRoomDatabase.kt             # @Database singleton with entity config
│   │   └── repository/
│   │       └── DummyRepository.kt               # Repository abstracting DAO access
│   ├── domain/model/
│   │   └── Dummy.kt                             # @Entity data class for database rows
│   └── ui/
│       ├── common/
│       │   └── LoggingExtensions.kt             # Extension function for class tag logging
│       ├── dialogs/
│       │   ├── AddDataDialogFragment.kt         # DialogFragment for adding new items
│       │   └── UpdateDataDialogFragment.kt      # DialogFragment for updating existing items
│       ├── list/
│       │   ├── DummyItemLongClickListener.kt    # Interface for item long-click events
│       │   ├── DummyListAdapter.kt              # ListAdapter for RecyclerView
│       │   └── SwipeToDeleteCallback.kt         # ItemTouchHelper callback for swipe delete
│       ├── main/
│       │   ├── DummyViewModel.kt                # ViewModel with Repository access
│       │   ├── MainActivity.kt                  # Navigation host activity
│       │   └── MainFragment.kt                  # Fragment displaying list of items
│       └── utils/
│           └── FragmentViewBindingDelegate.kt    # Delegate for Fragment view binding
├── res/
│   ├── drawable/
│   │   ├── baseline_account_box_24.xml           # Account box icon
│   │   ├── baseline_add_box_24.xml               # Add box icon
│   │   ├── baseline_swap_horiz_24.xml            # Swap horizontal icon
│   │   ├── ic_launcher_background.xml            # Launcher icon background
│   │   └── ic_launcher_foreground.xml            # Launcher icon foreground
│   ├── layout/
│   │   ├── activity_main.xml                     # Main activity layout
│   │   ├── content_main.xml                      # Content area with NavHostFragment
│   │   ├── dialog_dummy_data.xml                 # Dialog layout for data input
│   │   ├── fragment_main.xml                     # Main fragment layout with RecyclerView
│   │   └── row_item.xml                          # RecyclerView item layout
│   ├── navigation/
│   │   └── nav_graph.xml                         # Navigation graph with dialog destinations
│   ├── values/
│   │   ├── colors.xml                            # Color resources
│   │   ├── dimens.xml                            # Dimension resources
│   │   ├── strings.xml                           # String resources
│   │   └── themes.xml                            # Light theme attributes
│   ├── values-night/
│   │   └── themes.xml                            # Dark theme attributes
│   └── xml/
│       ├── backup_rules.xml                      # Backup rules for Android 12+
│       └── data_extraction_rules.xml             # Data extraction rules
└── AndroidManifest.xml                           # App manifest
```

## Dependencies

- AndroidX Room KTX
- AndroidX Room Compiler (KSP)
- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- AndroidX Lifecycle ViewModel KTX
- AndroidX Lifecycle LiveData KTX
- Material Components for Android
