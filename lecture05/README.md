# Lecture 05 — Jetpack Compose

This lecture introduces Jetpack Compose, Android's modern declarative UI framework. Students transition from the imperative XML-based approach to building UIs with composable functions, learning how Compose handles state, recomposition, and lifecycle integration. The lecture covers the basics of Compose, lifecycle management with `ViewModel`, and building efficient scrollable lists with `LazyColumn`.

## Apps

| App | Description | UI Framework |
|-----|-------------|--------------|
| [05-1_GettingStarted-compose](./05-1_GettingStarted-compose) | A greeting app rebuilt with Jetpack Compose and Material Design 3 | Jetpack Compose |
| [05-2_LifeCycle-compose](./05-2_LifeCycle-compose) | Lifecycle and state management using `ViewModel` within Compose | Jetpack Compose |
| [05-3_LazyList](./05-3_LazyList) | Scrollable list with `LazyColumn`, image loading with Coil, and fake data generation | Jetpack Compose |

## Key Concepts Covered

- Jetpack Compose fundamentals: `@Composable` functions, `setContent {}`, and recomposition
- Material Design 3 with Compose: `Scaffold`, `TopAppBar`, `BottomAppBar`
- State management in Compose: `remember`, `mutableStateOf`, state hoisting
- `ViewModel` integration with `viewModel()` composable function
- Lifecycle-aware state collection with `collectAsState()` and `observeAsState()`
- `LazyColumn` for efficient, lazy-loaded lists
- Image loading with the Coil Compose library
- Fake data generation with JavaFaker
- Edge-to-edge UI with `enableEdgeToEdge()`

## Further Reading

- [Jetpack Compose basics](https://developer.android.com/develop/ui/compose)
- [State and Jetpack Compose](https://developer.android.com/develop/ui/compose/state)
- [Lists and grids in Compose](https://developer.android.com/develop/ui/compose/lists)
- [Material Design 3 in Compose](https://developer.android.com/develop/ui/compose/designsystems/material3)
