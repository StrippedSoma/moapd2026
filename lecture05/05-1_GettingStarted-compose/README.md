# Getting Started (Compose)

A simple greeting application built with **Jetpack Compose** and **Material Design 3**. This is the Compose counterpart to the XML-based apps in [Lecture 01](../../lecture01), demonstrating how the same user interaction — entering a name and displaying a greeting — is implemented using Android's modern declarative UI framework.

> **See also:** [Getting Started (Java)](../../lecture01/01-1_GettingStarted-java) and [Getting Started (Kotlin)](../../lecture01/01-2_GettingStarted-kotlin) — XML-based versions for comparison.

## Learning Outcomes

After studying this app, students will be able to:

- Set up a Jetpack Compose project with `ComponentActivity` and `setContent {}`
- Build UI with `@Composable` functions instead of XML layouts
- Use Material Design 3 components: `Scaffold`, `TextField`, `Button`, `Text`
- Manage local UI state with `remember` and `mutableStateOf`
- Understand the Compose recomposition model
- Compare the declarative Compose approach with imperative XML-based development

## Architecture

**Pattern:** Single Activity with Composable Functions

The `MainActivity` uses `setContent {}` to define the UI tree with composable functions. There is no XML layout — the entire UI is defined in Kotlin code.

| Class | Role |
|-------|------|
| `MainActivity` | Entry point, sets up Compose content with `setContent {}` |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Jetpack Compose | Declarative UI framework |
| Material Design 3 | Modern Material theming and components |
| ComponentActivity | Base activity for Compose apps |
| remember / mutableStateOf | Compose state management primitives |

## How to Run

1. Open the `05-1_GettingStarted-compose` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Enter a name and tap the button to see the greeting.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/gettingstarted/
│   └── ui/
│       ├── main/
│       │   ├── GettingStartedScreen.kt       # Composable screen with greeting logic
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
- AndroidX Compose UI
- AndroidX Compose Material 3
- AndroidX Compose BOM (Bill of Materials)
- AndroidX Lifecycle Runtime KTX
