# LazyList

A Jetpack Compose app that displays a **scrollable list of items** using `LazyColumn` — Compose's equivalent of `RecyclerView`. The app generates 50 fake data items using the JavaFaker library and loads images from the network with Coil, all rendered within a Material Design 3 `Scaffold` with a `TopAppBar`.

> **See also:** [RecyclerView](../../lecture04/04-1_RecyclerView) — the XML-based equivalent using `RecyclerView`.

## Learning Outcomes

After studying this app, students will be able to:

- Build efficient, lazy-loaded lists with `LazyColumn` in Jetpack Compose
- Use the `items()` builder function for iterating list data in Compose
- Load and display network images with the Coil Compose library (`AsyncImage`)
- Generate fake data for prototyping with the JavaFaker library
- Build Material Design 3 layouts with `Scaffold`, `TopAppBar`, and `BottomAppBar`
- Use `remember` for recomposition-safe state management
- Compare `LazyColumn` (Compose) with `RecyclerView` (XML) approaches

## Architecture

**Pattern:** Single Activity with Composable Screen

The UI is defined entirely with composable functions. `LazyListScreen` is the main screen composable, and `RowItem` renders individual list items.

| Class | Role |
|-------|------|
| `MainActivity` | Entry point with `setContent {}` |
| `LazyListScreen` | Main composable with Scaffold + LazyColumn |
| `RowItem` | Composable for rendering individual list items with images |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Jetpack Compose | Declarative UI framework |
| LazyColumn | Efficient lazy-loaded vertical list |
| Coil Compose | Asynchronous image loading and caching |
| JavaFaker | Fake data generation for prototyping |
| Material Design 3 | Scaffold, TopAppBar, styling |
| Material Icons Extended | Extended icon set |
| INTERNET permission | Loading network images |

## How to Run

1. Open the `05-3_LazyList` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Scroll through the lazy-loaded list of items with network images.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/lazylist/
│   ├── domain/
│   │   ├── model/
│   │   │   └── DummyModel.kt                 # Data class for list items
│   │   └── sampledata/
│   │       └── DummyDataFactory.kt           # Factory generating fake data with JavaFaker
│   └── ui/
│       ├── components/
│       │   └── RowItem.kt                    # Composable row item for the list
│       ├── main/
│       │   ├── LazyListScreen.kt             # Composable screen with LazyColumn
│       │   └── MainActivity.kt               # Compose entry point activity
│       └── theme/
│           ├── Color.kt                      # Color palette definitions
│           ├── Theme.kt                      # Material 3 theme configuration
│           └── Type.kt                       # Typography definitions
├── res/
│   ├── drawable/
│   │   ├── ic_launcher_background.xml        # Launcher icon background
│   │   └── ic_launcher_foreground.xml        # Launcher icon foreground
│   ├── values/
│   │   ├── colors.xml                        # Color resources
│   │   ├── strings.xml                       # String resources
│   │   └── themes.xml                        # App theme
│   └── xml/
│       ├── backup_rules.xml                  # Backup rules for Android 12+
│       └── data_extraction_rules.xml         # Data extraction rules
└── AndroidManifest.xml                       # App manifest
```

## Dependencies

- AndroidX Activity Compose
- AndroidX Compose Material 3
- AndroidX Compose Material Icons Extended
- AndroidX Compose BOM
- Coil Compose (image loading)
- JavaFaker (fake data generation)
