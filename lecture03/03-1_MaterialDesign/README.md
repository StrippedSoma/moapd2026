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
│   │   └── MaterialDesignApplication.kt  # Application subclass
│   └── ui/main/
│       └── MainActivity.kt               # Material Design component setup
└── res/
    ├── layout/
    │   └── activity_main.xml              # Layout with TopAppBar and BottomAppBar
    └── menu/                              # Menu resource files
```

## Dependencies

- AndroidX AppCompat
- Material Components for Android
- AndroidX ConstraintLayout
