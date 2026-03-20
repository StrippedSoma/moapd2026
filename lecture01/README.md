# Lecture 01 — Getting Started

This lecture introduces the fundamentals of Android application development. Students set up their development environment with Android Studio and build their first Android app — a simple greeting application — in both Java and Kotlin. The lecture covers the basic structure of an Android project, the role of activities, and how to handle user input through UI components.

## Apps

| App | Description | UI Framework |
|-----|-------------|--------------|
| [01-1_GettingStarted-java](./01-1_GettingStarted-java) | A basic greeting app written in Java using `findViewById()` | XML Layouts |
| [01-2_GettingStarted-kotlin](./01-2_GettingStarted-kotlin) | The same greeting app rewritten in Kotlin with modern Android APIs | XML Layouts |

## Key Concepts Covered

- Setting up an Android Studio project from scratch
- The `Activity` class and the `onCreate()` lifecycle callback
- XML layout inflation and view references with `findViewById()`
- Handling user input with `EditText` and `Button` click listeners
- Managing the soft keyboard with `InputMethodManager`
- Migrating from Java to Kotlin: companion objects, extension functions, and null safety
- Edge-to-edge UI and window insets handling with `ViewCompat`

## Further Reading

- [Build your first Android app](https://developer.android.com/training/basics/firstapp)
- [Kotlin for Android developers](https://developer.android.com/kotlin)
- [Edge-to-edge display](https://developer.android.com/develop/ui/views/layout/edge-to-edge)
