# Lecture 04 — Flexible Data View and Threads

This lecture builds on the UI foundations from previous weeks and introduces flexible data presentation components (`RecyclerView`, `ViewPager2`), dialog-based navigation, background threading strategies, and Android services. Students learn how to replace `ListView` with `RecyclerView`, implement swipeable tab navigation, compare three concurrency approaches, and run foreground services for persistent background tasks.

## Apps

| App | Description | UI Framework |
|-----|-------------|--------------|
| [04-1_RecyclerView](./04-1_RecyclerView) | Replaces `ListView` with `RecyclerView` using the `ViewHolder` pattern | XML Layouts + View Binding |
| [04-2_ViewPager](./04-2_ViewPager) | Swipeable tab navigation with `ViewPager2` and `TabLayout` | XML Layouts + View Binding |
| [04-3_MaterialDialogs](./04-3_MaterialDialogs) | Material Design dialogs integrated with the Navigation framework | XML Layouts + View Binding |
| [04-4_AndroidThreads](./04-4_AndroidThreads) | Compares three threading approaches: `Thread`, `Handler`/`Looper`, and Kotlin Coroutines | XML Layouts + View Binding + Data Binding |
| [04-5_AndroidService](./04-5_AndroidService) | Background audio player using a foreground `Service` with notifications | XML Layouts + View Binding |

## Key Concepts Covered

- `RecyclerView` architecture: `Adapter`, `ViewHolder`, `LayoutManager`
- `ViewPager2` with `FragmentStateAdapter` and `TabLayoutMediator`
- Material Design dialogs via the Navigation framework
- Android threading: `Thread`, `Handler`/`Looper`, Kotlin Coroutines with `lifecycleScope`
- Foreground services and the `startForegroundService()` API
- `NotificationCompat` for persistent notifications
- `BroadcastReceiver` and `IntentFilter` for inter-component communication
- `MediaPlayer` for audio playback
- Shared `ViewModel` across multiple fragments
- Data Binding for two-way UI data synchronization

## Further Reading

- [RecyclerView](https://developer.android.com/develop/ui/views/layout/recyclerview)
- [ViewPager2](https://developer.android.com/develop/ui/views/animations/screen-slide-2)
- [Dialogs](https://developer.android.com/develop/ui/views/components/dialogs)
- [Background threads](https://developer.android.com/guide/background/threading)
- [Kotlin Coroutines on Android](https://developer.android.com/kotlin/coroutines)
- [Services overview](https://developer.android.com/guide/components/services)
- [Foreground services](https://developer.android.com/develop/background-work/services/foreground-services)
