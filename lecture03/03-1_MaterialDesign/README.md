# Material Design

An Android app that demonstrates **Material Design** components and theming using the Material Components for Android library. The app features a `TopAppBar` with menu actions and a `BottomAppBar` with a floating action button, showcasing how to build polished, standards-compliant Android interfaces.

## Learning Outcomes

After studying this app, students will be able to:

- Implement `TopAppBar` and `BottomAppBar` using Material Components
- Create and inflate options menus with `onCreateOptionsMenu()` and `onOptionsItemSelected()`
- Apply Material Design theming (colors, typography, shapes) to an Android app
- Use an `Application` subclass for app-wide initialization
- Structure click listener setup methods for clean, maintainable code
- Understand the Material Design guidelines and component library

## Architecture

**Pattern:** Activity-based with Material Components

A single Activity manages the Material Design component setup, menu handling, and click interactions.

| Class | Role |
|-------|------|
| `MainActivity` | Sets up TopAppBar, BottomAppBar, menus, and click listeners |
| `MaterialDesignApplication` | Custom `Application` subclass for app-wide initialization |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Material Components | TopAppBar, BottomAppBar, FloatingActionButton, theming |
| View Binding | Type-safe view references |
| Options Menu | App bar action items |
| AppCompatActivity | Base activity class |

## How to Run

1. Open the `03-1_MaterialDesign` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Explore the top and bottom app bars, menu items, and FAB interaction.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/materialdesign/
│   ├── app/
│   │   └── MaterialDesignApplication.kt      # Application class with Dynamic Colors
│   └── ui/
│       ├── common/
│       │   └── SnackbarExtensions.kt          # View extension for Snackbar messages
│       └── main/
│           └── MainActivity.kt               # Material Design TopAppBar/BottomAppBar setup
├── res/
│   ├── drawable/
│   │   ├── baseline_add_24.xml               # Add icon
│   │   ├── baseline_attach_file_24.xml       # Attach file icon
│   │   ├── baseline_calendar_today_24.xml    # Calendar icon
│   │   ├── baseline_delete_24.xml            # Delete icon
│   │   ├── baseline_menu_24.xml              # Menu icon
│   │   ├── baseline_search_24.xml            # Search icon
│   │   ├── baseline_shortcut_24.xml          # Shortcut icon
│   │   ├── baseline_system_update_alt_24.xml # System update icon
│   │   ├── ic_launcher_background.xml        # Launcher icon background
│   │   └── ic_launcher_foreground.xml        # Launcher icon foreground
│   ├── layout/
│   │   └── activity_main.xml                 # Layout with TopAppBar and BottomAppBar
│   ├── menu/
│   │   ├── bottom_app_bar.xml                # Bottom app bar menu items
│   │   └── top_app_bar.xml                   # Top app bar menu items
│   ├── values/
│   │   ├── colors.xml                        # Color definitions
│   │   ├── strings.xml                       # String resources
│   │   └── themes.xml                        # App theme
│   ├── values-night/
│   │   └── themes.xml                        # Dark mode theme
│   └── xml/
│       ├── backup_rules.xml                  # Backup rules for Android 12+
│       └── data_extraction_rules.xml         # Data extraction rules
└── AndroidManifest.xml                       # App manifest
```

## Dependencies

- AndroidX AppCompat
- Material Components for Android
- AndroidX ConstraintLayout
