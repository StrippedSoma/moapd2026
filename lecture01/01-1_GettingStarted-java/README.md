# Getting Started (Java)

A simple greeting application written in **Java** that introduces the fundamentals of Android development. The user types their name into an `EditText` field and taps a button to see a personalized greeting message displayed in a `TextView`. This is the starting point for understanding how Android activities work and how the UI is wired to code.

> **See also:** [Getting Started (Kotlin)](../01-2_GettingStarted-kotlin) — the same app rewritten in Kotlin with modern Android APIs.

## Learning Outcomes

After studying this app, students will be able to:

- Create a new Android project in Android Studio and understand its file structure
- Explain the role of `AppCompatActivity` and the `onCreate()` lifecycle callback
- Use `setContentView()` to inflate an XML layout
- Reference UI components from code using `findViewById()`
- Set up click listeners on `Button` views
- Manage the soft keyboard programmatically with `InputMethodManager`
- Understand the Java-based Android development workflow

## Architecture

**Pattern:** Basic MVC (Model-View-Controller)

The app follows a minimal architecture where the `Activity` acts as both the controller and the view host. There is no separate model layer — state is held directly in the view components.

| Class | Role |
|-------|------|
| `MainActivity` | Hosts the UI, handles button clicks, reads input, and updates the greeting text |

## Technologies

| Technology | Purpose |
|------------|---------|
| Java | Programming language |
| AppCompatActivity | Base activity class with backward-compatible features |
| XML Layouts | UI definition with `EditText`, `TextView`, and `Button` |
| InputMethodManager | Soft keyboard management |
| WindowCompat | Edge-to-edge display support |

## How to Run

1. Open the `01-1_GettingStarted-java` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Type a name in the text field and tap the button to see the greeting.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/gettingstarted/
│   └── presentation/main/
│       └── MainActivity.java       # Main activity with UI logic
└── res/
    └── layout/
        └── activity_main.xml       # UI layout with EditText, TextView, Button
```

## Dependencies

- AndroidX AppCompat
- AndroidX ConstraintLayout
- Material Components for Android
