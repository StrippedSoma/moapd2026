# ViewPager

An Android app that demonstrates **swipeable tab navigation** using `ViewPager2` with `TabLayout`. The app displays multiple content pages that users can navigate by swiping horizontally or tapping tabs. A `TabLayoutMediator` synchronizes the tab indicators with the current page position.

## Learning Outcomes

After studying this app, students will be able to:

- Implement `ViewPager2` for swipeable page navigation
- Create a `FragmentStateAdapter` to supply fragment pages to ViewPager2
- Use `TabLayout` for tab-based navigation indicators
- Synchronize tabs with pages using `TabLayoutMediator`
- Understand the relationship between ViewPager2, FragmentStateAdapter, and TabLayout
- Configure tab titles and behavior programmatically

## Architecture

**Pattern:** Single Activity with ViewPager2 + Tabs

The `MainActivity` sets up a `ViewPager2` backed by a `MainTabsAdapter` and synchronized with a `TabLayout`.

| Class | Role |
|-------|------|
| `MainActivity` | Sets up ViewPager2, TabLayout, and TabLayoutMediator |
| `MainTabsAdapter` | FragmentStateAdapter supplying fragment pages |
| `ViewPagerApplication` | Custom Application subclass |

## Technologies

| Technology | Purpose |
|------------|---------|
| Kotlin | Programming language |
| ViewPager2 | Swipeable page container |
| FragmentStateAdapter | Supplies fragments to ViewPager2 |
| TabLayout | Tab navigation indicators |
| TabLayoutMediator | Synchronizes TabLayout with ViewPager2 |
| View Binding | Type-safe view references |

## How to Run

1. Open the `04-2_ViewPager` project in **Android Studio**.
2. Sync Gradle and let dependencies download.
3. Run the app on an emulator or physical device (min SDK 28).
4. Swipe between pages or tap tabs to navigate.

## Project Structure

```
app/src/main/
├── java/dk/itu/moapd/viewpager/
│   ├── app/
│   │   └── ViewPagerApplication.kt          # Application subclass
│   └── ui/
│       ├── main/
│       │   └── MainActivity.kt              # ViewPager2 + TabLayout setup
│       └── pager/
│           └── MainTabsAdapter.kt           # FragmentStateAdapter for tab pages
└── res/
    └── layout/
        └── activity_main.xml                # Layout with ViewPager2 and TabLayout
```

## Dependencies

- AndroidX ViewPager2
- Material Components for Android (TabLayout)
- AndroidX AppCompat
- AndroidX ConstraintLayout
