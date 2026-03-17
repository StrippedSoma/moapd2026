# ListView

An Android app that demonstrates how to display a scrollable collection of items using a **`ListView`** with a custom `ArrayAdapter`. The app populates a list with `DummyModel` data objects and renders each item using a custom row layout. This serves as a baseline to compare with the more modern `RecyclerView` approach in [04-1_RecyclerView](../../lecture04/04-1_RecyclerView).

> **See also:** [RecyclerView](../../lecture04/04-1_RecyclerView) — the modern replacement for `ListView` using the `ViewHolder` pattern.

## Learning Outcomes

After studying this app, students will be able to:

- Create a `ListView` and populate it with data using a custom `ArrayAdapter`
- Implement a custom adapter with `getView()` for custom row layouts
- Define a data model class (`DummyModel`) for list items
- Understand the limitations of `ListView` (no view recycling enforcement, no `ViewHolder` requirement)
- Use Fragment View Binding delegation for safe view management
- Compare `ListView` with `RecyclerView` in terms of performance and API design

## Architecture

**Pattern:** Single Activity with Fragment + Adapter

The `MainActivity` hosts a `MainFragment` that sets up the `ListView` with a `CustomAdapter`.

| Class | Role |
|-------|------|
| `MainActivity` | Container activity |
| `MainFragment` | Sets up the ListView and CustomAdapter |
| `CustomAdapter` | Custom `ArrayAdapter` for rendering list items |
| `DummyModel` | Data model class for list items |
| `ListViewApplication` | Custom Application subclass |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| ListView | Scrollable list widget |
| ArrayAdapter | Adapter bridging data to ListView rows |
| View Binding | Type-safe view references |
| Fragment | Modular UI component |
| INTERNET permission | Network data fetching |

## How to Run

1. Open the `03-4_ListView` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Scroll through the list of items displayed by the custom adapter.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/listview/
│   ├── app/
│   │   └── ListViewApplication.kt           # Application subclass
│   ├── domain/model/
│   │   └── DummyModel.kt                    # Data model for list items
│   └── ui/
│       ├── main/
│       │   ├── MainActivity.kt              # Container activity
│       │   └── MainFragment.kt              # Fragment setting up ListView
│       └── list/
│           └── CustomAdapter.kt             # Custom ArrayAdapter implementation
└── res/
    └── layout/
        ├── fragment_main.xml                 # Fragment layout with ListView
        └── row_item.xml                      # Custom row layout for each item
```

## Dependencies

- AndroidX AppCompat
- Material Components for Android
- AndroidX ConstraintLayout
