# Testing Apps (Compose)

A Jetpack Compose app designed to demonstrate **automated testing** strategies for Android applications. The app includes unit tests with JUnit, assertion tests with Hamcrest matchers, and Compose-specific UI tests using the Compose testing framework. Test tags are applied to composables to enable reliable UI test selectors.

> **See also:** [Testing Apps (MDC)](../06-4_TestingApps-MDC) — the same testing concepts applied to an XML-based app with Espresso.

## Learning Outcomes

After studying this app, students will be able to:

- Write unit tests with JUnit for business logic validation
- Use Hamcrest matchers for expressive, readable test assertions
- Write Compose UI tests with `createComposeRule()` and `ComposeTestRule`
- Apply test tags to composables for reliable UI element selection
- Use `onNodeWithTag()`, `performClick()`, and `assertTextEquals()` in Compose tests
- Understand the test pyramid: unit tests, integration tests, and UI tests
- Configure test dependencies in Gradle (`testImplementation` vs. `androidTestImplementation`)

## Architecture

**Pattern:** Single Activity with Composable Screen + Test Tags

The app is intentionally simple to focus on testability. Test tags are defined in a separate file and applied to composables for Compose UI testing.

| Class | Role |
|-------|------|
| `MainActivity` | Entry point with `setContent {}` |
| `MainTestTags` | Defines string constants for Compose test tags |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| Jetpack Compose | Declarative UI framework |
| JUnit 4 | Unit test framework |
| Hamcrest | Matcher-based test assertions |
| Compose UI Test | Compose-specific UI testing framework |
| ComposeTestRule | Test rule for Compose UI tests |
| Test Tags | Compose element identifiers for UI tests |
| Material Design 3 | UI components |

## How to Run

### Run the App

1. Open the `06-3_TestingApps` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).

### Run Tests

- **Unit tests:** Right-click on `test/` → Run Tests, or run `./gradlew test`.
- **Instrumented UI tests:** Right-click on `androidTest/` → Run Tests, or run `./gradlew connectedAndroidTest`.

## Project Structure

```
app/src/
├── main/java/dk/itu/moapd/testingapps/
│   └── ui/main/
│       ├── MainActivity.kt              # Compose entry point
│       └── MainTestTags.kt             # Test tag constants for composables
├── test/                                 # Unit tests (JUnit + Hamcrest)
└── androidTest/                          # Instrumented Compose UI tests
```

## Dependencies

- AndroidX Activity Compose
- AndroidX Compose Material 3
- JUnit 4
- Hamcrest
- AndroidX Test JUnit
- AndroidX Test Espresso Core
- AndroidX Compose UI Test JUnit4
- AndroidX Compose UI Test Manifest
