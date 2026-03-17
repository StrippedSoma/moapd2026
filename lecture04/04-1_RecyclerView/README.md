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
│   │   └── RecyclerViewApplication.kt       # Application subclass
│   ├── domain/model/
│   │   └── DummyModel.kt                    # Data model for list items
│   └── ui/
│       ├── main/
│       │   ├── MainActivity.kt              # Container activity
│       │   └── MainFragment.kt              # Fragment setting up RecyclerView
│       └── list/
│           └── CustomAdapter.kt             # RecyclerView.Adapter with ViewHolder
└── res/
    └── layout/
        ├── fragment_main.xml                 # Fragment layout with RecyclerView
        └── row_item.xml                      # Row layout for each item
```

## Dependencies

- AndroidX RecyclerView
- AndroidX AppCompat
- Material Components for Android
- AndroidX ConstraintLayout
