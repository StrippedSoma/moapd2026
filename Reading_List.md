# Reading List — MOAPD 2026

> Mapped to the **Intended Learning Outcomes (ILOs)** from the course README.
> Two books are used:
> - **AP** = *Android Programming: The Big Nerd Ranch Guide, 5th Ed.* (Sills, Gardner, Marsicano, Stewart)
> - **KP** = *Kotlin Programming: The Big Nerd Ranch Guide, 2nd Ed.* (Bailey, Greenhalgh, Skeen)
>
> The Kotlin book does **not** include page numbers in its TOC. KP chapter references are by chapter number only.
> All Android book (AP) references include precise page numbers from the printed TOC.

---

## ILO 1 — Design and implement native Android applications using Kotlin

| Subject | Book | Chapter / Pages |
|---|---|---|
| Kotlin language basics: variables, types, null safety | KP | Ch. 2 (Variables, Constants, Types), Ch. 6 (Null Safety & Exceptions) |
| Functions, lambdas, anonymous functions | KP | Ch. 4 (Functions), Ch. 5 (Anonymous Functions & Function Type) |
| Classes, objects, inheritance, interfaces | KP | Ch. 12–16 (Defining Classes → Interfaces & Abstract Classes) |
| Generics, extensions, functional programming | KP | Ch. 17 (Generics), Ch. 18 (Extensions), Ch. 19 (Functional Programming Basics) |
| Java interoperability | KP | Ch. 20 (Java Interoperability) |
| First Android app with Kotlin | KP | Ch. 21 (Building Your First Android App with Kotlin) |
| First Android app with Java/Kotlin (Android side) | AP | Ch. 1 pp. 1–55 |
| MVC pattern in Android | AP | Ch. 2 pp. 33–55 |

---

## ILO 2 — Identify Android components, their lifecycle, and their role in the mobile application pipeline

| Subject | Book | Chapter / Pages |
|---|---|---|
| Activity lifecycle (onCreate → onDestroy, rotation, savedInstanceState) | AP | Ch. 3 pp. 57–73 |
| Second Activity, Intents, intent extras, back stack | AP | Ch. 5 pp. 87–109 |
| UI Fragments: lifecycle, FragmentManager, transactions | AP | Ch. 7 pp. 121–148 |
| Fragment arguments, results | AP | Ch. 10 pp. 193–204 |
| Services: types, lifecycle, foreground services | AP | Ch. 26 pp. 467–490 |
| Broadcast receivers: registration, system broadcasts, dynamic receivers | AP | Ch. 27 pp. 491–509 |
| Android SDK versions and compatibility | AP | Ch. 6 pp. 111–119 |

---

## ILO 3 — Describe the Android OS architecture and how to implement, run, and debug a mobile app

| Subject | Book | Chapter / Pages |
|---|---|---|
| Android Studio setup, project structure, build process | AP | Intro pp. xxi–xxii; Ch. 1 pp. 1–55 |
| Android build tools (Gradle, APK assembly) | AP | Ch. 1 p. 29 (For the More Curious: Android Build Process) |
| Debugging: stack traces, LogCat, breakpoints, Android Lint | AP | Ch. 4 pp. 75–85 |
| SDK versions, minimum/target/compile SDK | AP | Ch. 6 pp. 111–119 |
| Running on emulator and device | AP | Ch. 1 pp. 26–28; Ch. 2 pp. 46–47 |
| Kotlin REPL and IntelliJ | KP | Ch. 1 (Your First Kotlin Application) |

---

## ILO 4 — Design mobile applications based on resources available in mobile devices and considering their limitations

| Subject | Book | Chapter / Pages |
|---|---|---|
| ViewModel — survives configuration changes | AP | Ch. 3 pp. 57–73 (state saving context); Lecture 02 notes |
| Handling rotation and device configuration changes | AP | Ch. 3 pp. 63–70 |
| Audio playback with SoundPool (resource management) | AP | Ch. 19 pp. 339–351 |
| Assets vs resources | AP | Ch. 18 pp. 325–337 |
| Screen density, dp/sp, responsive layouts | AP | Ch. 8 pp. 154–157 |
| Two-pane master-detail (tablets vs phones) | AP | Ch. 17 pp. 307–323 |
| Sensor power consumption & sampling rates | AP | Lecture 12 notes (Sensor Framework) |
| Edge AI: on-device inference motivation | AP | Lecture 11 notes |
| Model optimization and quantization | — | Lecture 11 notes (TFLite, SNPE) |

---

## ILO 5 — Design and implement mobile applications considering concurrency, communication, multimedia, local/remote persistence, location, and sensors

### 5a. Concurrency
| Subject | Book | Chapter / Pages |
|---|---|---|
| Main thread, background threads, `Thread` | AP | Ch. 23 pp. 413–414 (You and Your Main Thread) |
| Loopers, Handlers, HandlerThread | AP | Ch. 24 pp. 429–448 |
| Background services | AP | Ch. 26 pp. 467–490 |
| Kotlin Coroutines: async/await, launch, suspend functions | KP | Ch. 22 (Introduction to Coroutines) |
| Coroutines on Android: `lifecycleScope`, `viewModelScope`, `Dispatchers` | KP | Ch. 22; Lecture 04 notes |
| HTTP and background networking | AP | Ch. 23 pp. 405–428 |

### 5b. Multimedia
| Subject | Book | Chapter / Pages |
|---|---|---|
| Audio playback with SoundPool | AP | Ch. 19 pp. 339–351 |
| CameraX (preview, capture, image analysis) | — | Lecture 10 notes |
| MediaPlayer (audio/video) | — | Lecture 10 notes |
| OpenCV image processing | — | Lecture 10 notes |
| Firebase ML Kit (object detection, labeling) | — | Lecture 10 notes |

### 5c. Local persistence
| Subject | Book | Chapter / Pages |
|---|---|---|
| SharedPreferences (key-value storage) | AP | Ch. 25 pp. 460–465 (Simple Persistence with Shared Preferences) |
| SQLite databases (raw) | AP | Ch. 14 pp. 257–272 |
| Room (entities, DAOs, database, migrations) | — | Lecture 07 notes |
| Internal and external file storage | AP | Ch. 16 pp. 291–305 (External Storage) |
| Assets | AP | Ch. 18 pp. 325–337 |

### 5d. Remote / cloud persistence
| Subject | Book | Chapter / Pages |
|---|---|---|
| Firebase Realtime Database (read/write/observe) | — | Lecture 08 notes |
| Firebase Cloud Storage (upload/download) | — | Lecture 08 notes |
| Firebase Security Rules | — | Lecture 08 notes |
| Offline persistence in Firebase | — | Lecture 08 notes |
| FirebaseRecyclerAdapter | — | Lecture 08 notes |
| HTTP networking basics | AP | Ch. 23 pp. 409–411 |

### 5e. Location
| Subject | Book | Chapter / Pages |
|---|---|---|
| Location permissions, runtime permission flow | AP | Ch. 31 pp. 559–561 |
| Google Play Services setup | AP | Ch. 31 pp. 552–564 |
| Fused Location Provider, location updates | AP | Ch. 31 pp. 561–569 |
| Latitude/longitude, geocoding | AP | Ch. 31 pp. 563–569 |
| Google Maps SDK, API key, markers, camera | AP | Ch. 32 pp. 571–585 |
| Broadcast receiver for location changes | AP | Ch. 27 pp. 491–509 |

### 5f. Sensors
| Subject | Book | Chapter / Pages |
|---|---|---|
| Sensor Framework (SensorManager, SensorEvent, listener) | — | Lecture 12 notes |
| Motion sensors (accelerometer, gyroscope, step counter) | — | Lecture 12 notes |
| Position sensors (magnetometer, rotation vector, proximity) | — | Lecture 12 notes |
| Environmental sensors (light, pressure, temperature, humidity) | — | Lecture 12 notes |
| Bluetooth Classic (adapter, socket, threads) | — | Lecture 12 notes |
| Bluetooth Low Energy / Beacons | — | Lecture 12 notes |

---

## ILO 6 — Design user interfaces using imperative (XML / Framework UI Toolkit) and declarative (Jetpack Compose) programming

### 6a. Imperative — Android Framework UI Toolkit
| Subject | Book | Chapter / Pages |
|---|---|---|
| XML layouts, view hierarchy, widget attributes | AP | Ch. 1 pp. 9–25 |
| Layouts and widgets, dp/sp, margins/padding | AP | Ch. 8 pp. 149–166 |
| Styles and themes | AP | Ch. 20 pp. 353–368 |
| XML Drawables (shapes, state lists, layer lists) | AP | Ch. 21 pp. 369–381 |
| Material Design (surfaces, elevation, FAB, Snackbar, Cards) | AP | Ch. 33 pp. 587–601 |
| Toolbar, menus, AppCompat | AP | Ch. 13 pp. 235–255 |
| Dialogs, DialogFragment | AP | Ch. 12 pp. 215–233 |
| ViewPager / ViewPager2 | AP | Ch. 11 pp. 205–214 |
| RecyclerView, ViewHolder, Adapter | AP | Ch. 9 pp. 167–203 |
| Custom Views and touch events | AP | Ch. 29 pp. 527–538 |
| Property animation | AP | Ch. 30 pp. 539–550 |
| Browsing the web / WebView | AP | Ch. 28 pp. 511–525 |

### 6b. Declarative — Jetpack Compose
| Subject | Book | Chapter / Pages |
|---|---|---|
| Composable functions, `setContent`, recomposition | — | Lecture 05 notes |
| Modifiers, layouts (Column, Row, Box, Scaffold) | — | Lecture 05 notes |
| State: `remember`, `mutableStateOf`, state hoisting | — | Lecture 05 notes |
| Material Design 3 in Compose (theming, color scheme, typography) | — | Lecture 05 notes |
| `LazyColumn` / `LazyRow` for lists | — | Lecture 05 notes |
| ViewModel integration (`collectAsState`, `observeAsState`) | — | Lecture 05 notes |
| Navigation in Compose (`NavHost`) | — | Lecture 05 notes |

---

## ILO 7 — Plan and execute the deployment of an Android application using Android Studio

| Subject | Book | Chapter / Pages |
|---|---|---|
| Android App Bundle (.aab) vs APK | — | Lecture 13 notes |
| Keystore creation and app signing | — | Lecture 13 notes |
| ProGuard / R8: shrinking, obfuscation, optimization | — | Lecture 13 notes |
| Protecting secrets (BuildConfig, local.properties) | — | Lecture 13 notes |
| Reverse engineering risks | — | Lecture 13 notes |
| Publishing on Google Play Console | — | Lecture 13 notes |
| Firebase integration setup (google-services.json, Gradle) | — | Lecture 06 notes |
| Firebase Authentication (FirebaseAuth, FirebaseUI) | — | Lecture 06 notes |
| Testing: JUnit, Espresso, Compose test APIs | — | Lecture 06 notes |

---

## Condensed "must-read" page list — Android Programming book

If time is short, prioritize these chapters (ordered by exam weight):

| Priority | Chapter | Pages | Exam questions |
|---|---|---|---|
| ★★★ | Ch. 3 — Activity Lifecycle | 57–73 | Q1 |
| ★★★ | Ch. 5 — Second Activity / Intents | 87–109 | Q1 |
| ★★★ | Ch. 7 — Fragments & FragmentManager | 121–148 | Q2 |
| ★★★ | Ch. 9 — RecyclerView | 167–203 | Q3 |
| ★★★ | Ch. 26 — Background Services | 467–490 | Q3 |
| ★★★ | Ch. 14 — SQLite Databases | 257–272 | Q6 |
| ★★★ | Ch. 31 — Location & Play Services | 551–569 | Q7 |
| ★★★ | Ch. 32 — Maps | 571–585 | Q7 |
| ★★ | Ch. 24 — Loopers, Handlers, HandlerThread | 429–448 | Q3 |
| ★★ | Ch. 27 — Broadcast Intents | 491–509 | Q7 |
| ★★ | Ch. 20 — Styles & Themes | 353–368 | Q2, Q4 |
| ★★ | Ch. 33 — Material Design | 587–601 | Q2 |
| ★★ | Ch. 13 — Toolbar | 235–255 | Q2 |
| ★★ | Ch. 12 — Dialogs | 215–233 | Q2, Q3 |
| ★★ | Ch. 19 — Audio / SoundPool | 339–351 | Q8 |
| ★ | Ch. 23 — HTTP & Background Tasks | 405–428 | Q3 |
| ★ | Ch. 11 — ViewPager | 205–214 | Q3 |
| ★ | Ch. 8 — Layouts & Widgets | 149–166 | Q1, Q2 |
| ★ | Ch. 29 — Custom Views | 527–538 | Q4 |

## Condensed "must-read" — Kotlin Programming book

| Priority | Chapter | Exam relevance |
|---|---|---|
| ★★★ | Ch. 22 — Introduction to Coroutines | Q3 (concurrency) |
| ★★★ | Ch. 21 — Building Android App with Kotlin | Q1 (Kotlin + Android) |
| ★★ | Ch. 6 — Null Safety & Exceptions | General Kotlin |
| ★★ | Ch. 12–14 — Classes, Initialization, Inheritance | General Kotlin |
| ★ | Ch. 19 — Functional Programming Basics | General Kotlin |
| ★ | Ch. 5 — Anonymous Functions / Lambdas | Coroutines, callbacks |
