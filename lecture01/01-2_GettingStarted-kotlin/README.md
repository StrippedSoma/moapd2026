# Getting Started (Kotlin)

A simple greeting application written in **Kotlin** that demonstrates modern Android development practices. The user enters their name and receives a personalized greeting — the same functionality as the Java version, but rewritten with Kotlin idioms, View Binding, and modern Android APIs like edge-to-edge display and window insets handling.

> **See also:** [Getting Started (Java)](../01-1_GettingStarted-java) — the same app written in Java for comparison.

## Learning Outcomes

After studying this app, students will be able to:

- Compare Java and Kotlin approaches to Android development
- Use Kotlin-specific features: `companion object`, null safety, extension functions, and lambdas
- Enable and use edge-to-edge UI with `enableEdgeToEdge()`
- Handle window insets with `ViewCompat.setOnApplyWindowInsetsListener()`
- Configure code quality tools (ktlint, detekt) in a Gradle build
- Understand the Kotlin JVM target configuration for Android

## Architecture

**Pattern:** Basic MVC (Model-View-Controller)

Same minimal architecture as the Java variant. The `Activity` acts as both the controller and view host with no separate model layer.

| Class | Role |
|-------|------|
| `MainActivity` | Hosts the UI, handles user interaction, updates the greeting text |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| AppCompatActivity | Base activity with backward-compatible features |
| XML Layouts | UI definition |
| enableEdgeToEdge() | Modern edge-to-edge display setup |
| ViewCompat | Window insets handling for system bars |
| ktlint | Kotlin code style enforcement |
| detekt | Static code analysis for Kotlin |

## How to Run

1. Open the `01-2_GettingStarted-kotlin` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Type a name in the text field and tap the button to see the greeting.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/gettingstarted/
│   └── presentation/main/
│       └── MainActivity.kt               # Main activity with Kotlin idioms
├── res/
│   ├── drawable/
│   │   ├── ic_launcher_background.xml    # Launcher icon background
│   │   └── ic_launcher_foreground.xml    # Launcher icon foreground
│   ├── layout/
│   │   └── activity_main.xml             # UI layout with EditText, TextView, Button
│   ├── values/
│   │   ├── colors.xml                    # Color definitions
│   │   ├── strings.xml                   # String resources
│   │   └── themes.xml                    # App theme
│   ├── values-night/
│   │   └── themes.xml                    # Dark mode theme
│   └── xml/
│       ├── backup_rules.xml              # Backup rules for Android 12+
│       └── data_extraction_rules.xml     # Data extraction rules
└── AndroidManifest.xml                   # App manifest
```

## Dependencies

- AndroidX AppCompat
- AndroidX ConstraintLayout
- AndroidX Activity KTX
- Material Components for Android
