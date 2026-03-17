# Material Dialogs

An Android app that demonstrates **Material Design dialogs** integrated with the AndroidX Navigation framework. The app uses dialog destinations within a navigation graph, allowing dialogs to participate in the standard navigation back-stack and transition system.

## Learning Outcomes

After studying this app, students will be able to:

- Create and display Material Design dialogs in Android
- Integrate dialog destinations into a Navigation graph
- Use `NavHostFragment` and `NavController` for fragment and dialog navigation
- Configure `AppBarConfiguration` for navigation-aware app bar behavior
- Understand the difference between standalone dialogs and navigation-managed dialogs
- Apply Material Design dialog styles and patterns

## Architecture

**Pattern:** Single Activity with Navigation-managed Fragments and Dialogs

The `MainActivity` hosts a `NavHostFragment`. Navigation destinations include both fragments and dialog fragments defined in the navigation graph.

| Class | Role |
|-------|------|
| `MainActivity` | Hosts NavHostFragment, sets up navigation with app bar |
| `DialogsApplication` | Custom Application subclass |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| AndroidX Navigation | Fragment and dialog navigation management |
| Material Dialogs | Styled dialog components |
| NavHostFragment | Navigation container for fragments and dialogs |
| AppBarConfiguration | Navigation-aware app bar integration |
| View Binding | Type-safe view references |

## How to Run

1. Open the `04-3_MaterialDialogs` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Navigate through the app and trigger dialog interactions.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/materialdialogs/
│   ├── app/
│   │   └── DialogsApplication.kt            # Application subclass
│   └── ui/main/
│       └── MainActivity.kt                  # Navigation setup with dialog destinations
└── res/
    ├── layout/
    │   └── activity_main.xml                # Layout with NavHostFragment
    └── navigation/
        └── nav_graph.xml                    # Navigation graph with dialog destinations
```

## Dependencies

- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- Material Components for Android
- AndroidX AppCompat
- AndroidX ConstraintLayout
