# Fragments

An Android app demonstrating **Fragment-based navigation** using the AndroidX Navigation component. The app features three content sections — Contacts, Articles, and Albums — accessible via a `BottomNavigationView`, with the Navigation framework managing fragment transitions and back-stack behavior. The app also handles portrait and landscape layout configurations.

## Learning Outcomes

After studying this app, students will be able to:

- Create and manage multiple `Fragment` subclasses within a single Activity
- Set up the AndroidX Navigation component with `NavHostFragment` and `NavController`
- Configure `AppBarConfiguration` to integrate the app bar with navigation
- Implement bottom navigation with `BottomNavigationView` and `NavigationUI.setupWithNavController()`
- Define navigation graphs in XML with destinations and actions
- Handle portrait and landscape layouts with resource qualifiers
- Use the Fragment View Binding delegation pattern to avoid memory leaks

## Architecture

**Pattern:** Single Activity with Fragment-based Navigation

The `MainActivity` hosts a `NavHostFragment` as the navigation container. Each screen is a `Fragment` that the Navigation framework manages.

| Class | Role |
|-------|------|
| `MainActivity` | Hosts NavHostFragment, sets up navigation with bottom nav |
| `ContactsFragment` | Displays contacts content |
| `ArticlesFragment` | Displays articles content |
| `AlbumsFragment` | Displays albums content |
| `FragmentsApplication` | Custom Application subclass |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| AndroidX Navigation | Fragment transitions, back-stack management, NavController |
| BottomNavigationView | Tab-based screen switching |
| View Binding | Type-safe view references |
| Fragment | Modular, reusable UI components |
| Resource qualifiers | Portrait/landscape layout adaptation |

## How to Run

1. Open the `03-2_Fragments` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Tap the bottom navigation items to switch between Contacts, Articles, and Albums.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/fragments/
│   ├── app/
│   │   └── FragmentsApplication.kt           # Application subclass
│   └── ui/
│       ├── main/
│       │   └── MainActivity.kt               # Navigation setup with BottomNavigationView
│       ├── contacts/
│       │   └── ContactsFragment.kt           # Contacts screen
│       ├── articles/
│       │   └── ArticlesFragment.kt           # Articles screen
│       └── albums/
│           └── AlbumsFragment.kt             # Albums screen
└── res/
    ├── layout/                                # Portrait layouts
    ├── layout-land/                           # Landscape layouts
    ├── navigation/                            # Navigation graph XML
    └── menu/                                  # Bottom navigation menu
```

## Dependencies

- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- Material Components for Android
- AndroidX AppCompat
- AndroidX ConstraintLayout
