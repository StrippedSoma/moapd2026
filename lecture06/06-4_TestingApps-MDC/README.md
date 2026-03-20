# Testing Apps (MDC)

An Android app built with **XML layouts and Fragments** that demonstrates automated testing using **Espresso** for UI tests and **JUnit** for unit tests. This is the Material Design Components (MDC) variant, showing how to test Fragment-based navigation and view interactions.

> **See also:** [Testing Apps (Compose)](../06-3_TestingApps) — the same testing concepts applied to a Jetpack Compose app.

## Learning Outcomes

After studying this app, students will be able to:

- Write UI tests with Espresso for XML-based layouts
- Use Espresso matchers: `onView()`, `withId()`, `withText()`
- Perform Espresso actions: `perform(click())`, `perform(typeText())`
- Assert Espresso results: `check(matches(isDisplayed()))`, `check(matches(withText()))`
- Test Fragment navigation with the Navigation component
- Write unit tests with JUnit and Hamcrest matchers
- Compare Espresso testing (XML) with Compose UI testing

## Architecture

**Pattern:** Single Activity with Fragment Navigation

The app uses Navigation component with fragments, providing clear test targets for Espresso.

| Class | Role |
|-------|------|
| `MainActivity` | Hosts NavHostFragment with navigation-aware app bar |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Espresso | Android UI testing framework for XML views |
| JUnit 4 | Unit test framework |
| Hamcrest | Matcher-based test assertions |
| AndroidX Navigation | Fragment-based navigation |
| View Binding | Type-safe view references |
| Material Components | UI components |

## How to Run

### Run the App

1. Open the `06-4_TestingApps-MDC` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).

### Run Tests

- **Unit tests:** Right-click on `test/` → Run Tests, or run `./gradlew test`.
- **Instrumented UI tests:** Right-click on `androidTest/` → Run Tests, or run `./gradlew connectedAndroidTest`.

## Project Structure

```
app/src/
├── main/
│   ├── java/dk/itu/moapd/testingapps/
│   │   ├── app/
│   │   │   └── TestingApplication.kt               # Custom Application subclass for global state
│   │   ├── domain/validation/
│   │   │   └── InputValidator.kt                   # Reusable input validation logic (name, email, password)
│   │   └── ui/
│   │       ├── common/
│   │       │   ├── KeyboardExtensions.kt            # Extension function to hide soft keyboard
│   │       │   └── SnackbarExtensions.kt            # Extension function to show Snackbar messages
│   │       ├── main/
│   │       │   ├── MainActivity.kt                  # Navigation host activity with app bar
│   │       │   └── MainFragment.kt                  # Form fragment with input validation
│   │       └── utils/
│   │           └── FragmentViewBindingDelegate.kt    # Lifecycle-aware view binding delegate
│   ├── res/
│   │   ├── drawable/
│   │   │   ├── ic_launcher_background.xml           # Launcher icon background
│   │   │   └── ic_launcher_foreground.xml           # Launcher icon foreground
│   │   ├── layout/
│   │   │   ├── activity_main.xml                    # Main activity layout with toolbar
│   │   │   ├── content_main.xml                     # NavHostFragment container
│   │   │   └── fragment_main.xml                    # Main form fragment layout
│   │   ├── navigation/
│   │   │   └── nav_graph.xml                        # Navigation graph with fragment destinations
│   │   ├── values/
│   │   │   ├── colors.xml                           # Color definitions
│   │   │   ├── dimens.xml                           # Dimension values
│   │   │   ├── strings.xml                          # String resources
│   │   │   └── themes.xml                           # App theme
│   │   ├── values-night/
│   │   │   └── themes.xml                           # Dark mode theme overrides
│   │   └── xml/
│   │       ├── backup_rules.xml                     # Backup rules for Android 12+
│   │       └── data_extraction_rules.xml            # Data extraction rules
│   └── AndroidManifest.xml                          # App manifest
├── test/java/dk/itu/moapd/testingapps/
│   └── InputValidatorTest.kt                        # Unit tests for input validation (JUnit + Hamcrest)
└── androidTest/java/dk/itu/moapd/testingapps/
    └── MainActivityTest.kt                          # Instrumented Espresso UI tests
```

## Dependencies

- AndroidX Navigation Fragment KTX
- AndroidX Navigation UI KTX
- JUnit 4
- Hamcrest
- AndroidX Test JUnit
- AndroidX Test Espresso Core
- Material Components for Android
