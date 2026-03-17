# Lecture 02 — Android Framework UI Toolkit

This lecture explores the Android Activity lifecycle in depth. Through a progressive series of three apps, students first encounter a common lifecycle bug (state loss on configuration changes), then learn to fix it with `Bundle`-based state saving, and finally adopt the modern `ViewModel` + `LiveData` architecture. The lecture demonstrates why understanding the lifecycle is essential for building robust Android applications.

## Apps

| App | Description | UI Framework |
|-----|-------------|--------------|
| [02-1_LifeCycle-bug](./02-1_LifeCycle-bug) | An intentionally buggy app that loses UI state on device rotation | XML Layouts + View Binding |
| [02-2_LifeCycle-bundle](./02-2_LifeCycle-bundle) | Fixes the bug by saving and restoring state with `onSaveInstanceState()` | XML Layouts + View Binding |
| [02-3_LifeCycle-vm](./02-3_LifeCycle-vm) | The modern solution using `ViewModel` and `LiveData` for lifecycle-safe state | XML Layouts + View Binding |

## Key Concepts Covered

- The complete Activity lifecycle: `onCreate()`, `onStart()`, `onResume()`, `onPause()`, `onStop()`, `onDestroy()`
- Configuration changes and their impact on activity instances
- State preservation with `onSaveInstanceState()` and `Bundle`
- View Binding for type-safe view references
- The `ViewModel` class and its lifecycle scope
- `LiveData` as an observable, lifecycle-aware data holder
- The `by viewModels()` Kotlin property delegate
- Logging lifecycle transitions with `Log.d()`
- Evolution from imperative state management to MVVM

## Further Reading

- [The Activity lifecycle](https://developer.android.com/guide/components/activities/activity-lifecycle)
- [Save UI states](https://developer.android.com/topic/libraries/architecture/saving-states)
- [ViewModel overview](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [LiveData overview](https://developer.android.com/topic/libraries/architecture/livedata)
