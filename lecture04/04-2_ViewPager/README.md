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
│   │   └── ViewPagerApplication.kt              # Application class with Dynamic Colors
│   └── ui/
│       ├── common/
│       │   └── Dimens.kt                         # Extension function converting dp to px
│       ├── main/
│       │   └── MainActivity.kt                   # ViewPager2 + TabLayout setup
│       ├── pager/
│       │   └── MainTabsAdapter.kt                # FragmentStateAdapter for tab pages
│       ├── pages/
│       │   ├── albums/
│       │   │   └── AlbumsFragment.kt             # Fragment displaying artist photo list
│       │   ├── articles/
│       │   │   └── ArticlesFragment.kt           # Fragment displaying article example
│       │   └── contacts/
│       │       └── ContactsFragment.kt           # Fragment displaying fake contacts list
│       └── utils/
│           └── FragmentViewBindingDelegate.kt     # Lifecycle-safe Fragment ViewBinding delegate
├── res/
│   ├── drawable/
│   │   ├── baseline_article_24.xml               # Article icon
│   │   ├── baseline_circle_24.xml                # Circle icon
│   │   ├── baseline_dashboard_24.xml             # Dashboard icon
│   │   ├── baseline_people_24.xml                # People icon
│   │   ├── ic_launcher_background.xml            # Launcher icon background
│   │   └── ic_launcher_foreground.xml            # Launcher icon foreground
│   ├── drawable-nodpi/
│   │   ├── album_art_01.jpg ... album_art_20.jpg # Album artwork images
│   │   ├── chuck_norris.jpg                      # Article image
│   │   └── meme.jpg                              # Article image
│   ├── layout/
│   │   ├── activity_main.xml                     # Main activity layout
│   │   ├── contact_row_item.xml                  # Contact list row layout
│   │   ├── content_main.xml                      # Content container
│   │   ├── fragment_albums.xml                   # Albums fragment layout
│   │   ├── fragment_articles.xml                 # Articles fragment layout
│   │   └── fragment_contacts.xml                 # Contacts fragment layout
│   ├── values/
│   │   ├── colors.xml                            # Color definitions
│   │   ├── dimens.xml                            # Dimension resources
│   │   ├── strings.xml                           # String resources
│   │   └── themes.xml                            # App theme
│   ├── values-night/
│   │   └── themes.xml                            # Dark mode theme
│   └── xml/
│       ├── backup_rules.xml                      # Backup rules for Android 12+
│       └── data_extraction_rules.xml             # Data extraction rules
└── AndroidManifest.xml                           # App manifest
```

## Dependencies

- AndroidX ViewPager2
- Material Components for Android (TabLayout)
- AndroidX AppCompat
- AndroidX ConstraintLayout
