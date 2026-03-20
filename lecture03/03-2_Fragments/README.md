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
│   │   └── FragmentsApplication.kt               # Application class with Dynamic Colors
│   └── ui/
│       ├── albums/
│       │   └── AlbumsFragment.kt                 # Fragment displaying artist photo list
│       ├── articles/
│       │   └── ArticlesFragment.kt               # Fragment displaying article example
│       ├── common/
│       │   └── Dimens.kt                         # Extension function converting dp to px
│       ├── contacts/
│       │   └── ContactsFragment.kt               # Fragment displaying fake contacts list
│       ├── main/
│       │   └── MainActivity.kt                   # Navigation host with BottomNavigationView
│       └── utils/
│           └── FragmentViewBindingDelegate.kt     # Lifecycle-safe Fragment ViewBinding delegate
├── res/
│   ├── drawable/
│   │   ├── baseline_article_24.xml               # Article icon
│   │   ├── baseline_circle_24.xml                # Circle icon
│   │   ├── baseline_dashboard_24.xml             # Dashboard icon
│   │   ├── baseline_people_24.xml                # People icon
│   │   ├── ic_launcher_background.xml            # Launcher icon background
│   │   └── ic_launcher_foreground.xml            # Launcher icon foreground
│   ├── drawable-nodpi/
│   │   ├── album_art_01.jpg ... album_art_20.jpg # Album artwork images
│   │   ├── chuck_norris.jpg                      # Article image
│   │   └── meme.jpg                              # Article image
│   ├── layout/
│   │   ├── activity_main.xml                     # Main activity layout
│   │   ├── contact_row_item.xml                  # Contact list row layout
│   │   ├── content_main.xml                      # NavHostFragment container
│   │   ├── fragment_albums.xml                   # Albums fragment layout
│   │   ├── fragment_articles.xml                 # Articles fragment layout
│   │   └── fragment_contacts.xml                 # Contacts fragment layout
│   ├── layout-land/
│   │   ├── activity_main.xml                     # Landscape main layout
│   │   ├── content_main.xml                      # Landscape content layout
│   │   └── fragment_albums.xml                   # Landscape albums layout
│   ├── menu/
│   │   └── bottom_navigation_menu.xml            # Bottom navigation menu items
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

- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- Material Components for Android
- AndroidX AppCompat
- AndroidX ConstraintLayout
