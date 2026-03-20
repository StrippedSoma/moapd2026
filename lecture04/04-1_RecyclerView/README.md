# RecyclerView

An Android app that demonstrates the **`RecyclerView`** component — the modern replacement for `ListView`. The app displays a scrollable list of `DummyModel` items using the `ViewHolder` pattern for efficient view recycling. This project mirrors [03-4_ListView](../../lecture03/03-4_ListView) but uses `RecyclerView` to show the architectural improvements.

> **See also:** [ListView](../../lecture03/03-4_ListView) — the older `ListView` approach for comparison.

## Learning Outcomes

After studying this app, students will be able to:

- Implement a `RecyclerView.Adapter` with the `ViewHolder` pattern
- Understand how `RecyclerView` enforces view recycling for better performance
- Configure a `LayoutManager` (e.g., `LinearLayoutManager`) for list layout
- Compare `RecyclerView` with `ListView` in terms of API design and efficiency
- Bind data model objects to `ViewHolder` instances
- Use Fragment View Binding for safe view management

## Architecture

**Pattern:** Single Activity with Fragment + RecyclerView Adapter

The `MainActivity` hosts a `MainFragment` that sets up the `RecyclerView` with a `CustomAdapter`.

| Class | Role |
|-------|------|
| `MainActivity` | Container activity |
| `MainFragment` | Sets up RecyclerView with adapter and layout manager |
| `CustomAdapter` | RecyclerView.Adapter with ViewHolder for efficient rendering |
| `DummyModel` | Data model class for list items |
| `RecyclerViewApplication` | Custom Application subclass |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| RecyclerView | Efficient scrollable list with view recycling |
| RecyclerView.Adapter | Binds data to ViewHolder instances |
| ViewHolder | Caches view references for recycled items |
| LayoutManager | Controls list layout (linear, grid, staggered) |
| View Binding | Type-safe view references |

## How to Run

1. Open the `04-1_RecyclerView` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Scroll through the list and observe efficient view recycling.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/recyclerview/
│   ├── app/
│   │   └── RecyclerViewApplication.kt           # Application class with Dynamic Colors
│   ├── domain/model/
│   │   └── DummyModel.kt                        # Data class representing a city with metadata
│   └── ui/
│       ├── common/
│       │   └── SnackbarExtensions.kt             # View extension for Snackbar messages
│       ├── list/
│       │   └── CustomAdapter.kt                  # RecyclerView.Adapter with ViewHolder
│       ├── main/
│       │   ├── MainActivity.kt                   # Navigation host activity
│       │   └── MainFragment.kt                   # Fragment setting up RecyclerView
│       └── utils/
│           └── FragmentViewBindingDelegate.kt     # Lifecycle-safe Fragment ViewBinding delegate
├── res/
│   ├── drawable/
│   │   ├── baseline_favorite_24.xml              # Favorite icon
│   │   ├── baseline_share_24.xml                 # Share icon
│   │   ├── baseline_thumb_up_24.xml              # Thumb up icon
│   │   ├── ic_launcher_background.xml            # Launcher icon background
│   │   └── ic_launcher_foreground.xml            # Launcher icon foreground
│   ├── layout/
│   │   ├── activity_main.xml                     # Main activity layout
│   │   ├── content_main.xml                      # NavHostFragment container
│   │   ├── fragment_main.xml                     # Fragment layout with RecyclerView
│   │   └── row_item.xml                          # Custom row layout for each item
│   ├── navigation/
│   │   └── nav_graph.xml                         # Navigation graph
│   ├── values/
│   │   ├── colors.xml                            # Color definitions
│   │   ├── dimens.xml                            # Dimension resources
│   │   ├── strings.xml                           # String resources
│   │   └── themes.xml                            # App theme
│   ├── values-land/
│   │   └── dimens.xml                            # Landscape dimension overrides
│   ├── values-night/
│   │   └── themes.xml                            # Dark mode theme
│   └── xml/
│       ├── backup_rules.xml                      # Backup rules for Android 12+
│       └── data_extraction_rules.xml             # Data extraction rules
└── AndroidManifest.xml                           # App manifest
```

## Dependencies

- AndroidX RecyclerView
- AndroidX AppCompat
- Material Components for Android
- AndroidX ConstraintLayout
