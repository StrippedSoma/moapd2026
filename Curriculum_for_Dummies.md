# MOAPD 2026 — The Whole Curriculum, Explained from Scratch

> This document explains every major topic in the course in plain language, ordered from the most basic ideas to the most advanced. No prior knowledge of Kotlin, Android, or programming paradigms is assumed beyond basic object-oriented thinking.

---

## Part 1 — The Big Picture: What Even Is an Android App?

### What is Android?

Android is an **operating system** — the same category of software as Windows or macOS — but designed specifically for phones and tablets. It is built by Google, runs on the Linux kernel under the hood, and is installed on billions of devices.

When you write an Android app, you are writing a program that runs *inside* Android. Android decides when your app starts, when it pauses, when it is killed to free up memory, and when it is restarted. You do not control this; the OS does. Understanding and respecting that is the single most important mental shift in Android development.

### What is Kotlin?

Kotlin is the programming language used to write Android apps in this course. It runs on the **Java Virtual Machine (JVM)** — the same runtime that Java runs on — so Kotlin code can call Java code and vice versa. Google officially recommends Kotlin as the primary language for Android development since 2019.

Compared to Java, Kotlin is more concise, has **built-in null safety** (it prevents a whole category of crashes called NullPointerExceptions at compile time), and supports modern programming patterns like coroutines for concurrency.

### How does an Android app actually work?

Think of an Android app as a collection of **components**. There are four types:

1. **Activities** — screens the user can see and interact with
2. **Services** — background workers with no UI (e.g., playing music while the phone is locked)
3. **Broadcast Receivers** — listeners for system-wide events (e.g., "the phone just booted", "network connectivity changed")
4. **Content Providers** — a standardised way to share data between apps (e.g., the contacts app exposing your contacts)

All four types are declared in a file called **`AndroidManifest.xml`**, which is the app's identity card. Android reads this file to know what your app contains, what permissions it needs, and which component to launch when the user taps the app icon.

---

## Part 2 — Your First Screen: Activities

### What is an Activity?

An Activity is one screen in your app. When you open an app and see a home screen, that is an Activity. When you tap a button and a new screen slides in, that is usually a new Activity (or sometimes a Fragment — more on that later).

An Activity is a Kotlin class that extends `AppCompatActivity`. The minimum you need is an `onCreate()` function, which Android calls when your screen is first created:

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
```

`setContentView` tells Android which XML layout file to use as the visual content for this screen.

### What is an XML layout?

In the traditional (imperative) Android approach, the visual design of a screen is defined separately from the code, in an XML file. XML is a text format that describes structure using nested tags, similar to HTML but for Android views.

A simple layout might look like this:

```xml
<LinearLayout>
    <TextView android:text="Hello!" />
    <Button android:text="Click me" />
</LinearLayout>
```

The code then *inflates* this XML (turns it from a text description into real view objects in memory) and uses `findViewById()` to get a reference to each widget so it can read from or write to it.

**View Binding** is a modern improvement: Android auto-generates a Kotlin class from the XML, so instead of `findViewById()` everywhere you just access `binding.myButton`.

### The Activity Lifecycle — the most important concept in Android

Android is a mobile OS. The user can receive a phone call, switch apps, rotate the screen, or the OS might kill your app to free up RAM. All of these events have consequences for your Activity. Android communicates these events by calling specific functions — the **lifecycle callbacks**:

| Callback | What just happened | What you should do |
|---|---|---|
| `onCreate()` | Activity created for the first time | Set up the UI, initialise data |
| `onStart()` | Activity is now visible | Start visual updates |
| `onResume()` | Activity is in the foreground, user can interact | Acquire camera, register sensors |
| `onPause()` | Another activity is partially covering yours | Release camera, pause animations |
| `onStop()` | Activity is no longer visible | Save data, stop heavy work |
| `onDestroy()` | Activity is being completely destroyed | Release all remaining resources |

Think of it as a heartbeat. Every Activity goes up through these states when it appears and back down when it disappears.

**The rotation problem**: When the user rotates the phone, Android *destroys* and *recreates* the Activity from scratch. Any data you stored in a plain variable is lost. This is intentional — different screen orientations can have completely different layouts. The solution is covered in the next section.

### Saving state across rotation: Bundle and ViewModel

There are two solutions to the rotation problem:

**1. `onSaveInstanceState(Bundle)`** — for small, transient UI state (like the text currently typed in a field):

```kotlin
override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putString("USER_INPUT", currentText)
}

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val saved = savedInstanceState?.getString("USER_INPUT") ?: ""
}
```

A `Bundle` is just a key-value bag. Android saves it before destroying the Activity and hands it back when recreating it.

**2. `ViewModel`** — the modern, recommended approach for anything more substantial. A `ViewModel` is a special object that Android keeps alive across configuration changes. It is not an Activity; it lives longer.

```kotlin
class CounterViewModel : ViewModel() {
    var count = 0
}

// In your Activity:
val viewModel: CounterViewModel by viewModels()
```

`viewModels()` is a Kotlin delegate — it creates the ViewModel the first time, and returns the *same* one on rotation, instead of a new one.

---

## Part 3 — Moving Between Screens: Intents

### What is an Intent?

An Intent is a message that tells Android "I want to do something." The most common use is starting another Activity:

```kotlin
val intent = Intent(this, ProfileActivity::class.java)
startActivity(intent)
```

You can attach data to an Intent using **extras**:

```kotlin
intent.putExtra("USER_ID", 42)

// In ProfileActivity:
val userId = intent.getIntExtra("USER_ID", 0)
```

**Explicit Intents** name the exact class to start (as above). **Implicit Intents** describe an *action* and let Android find something to handle it — for example, asking Android to open a URL in any browser, or share text with any messaging app.

### The Back Stack

When you start a new Activity, the old one goes onto a **back stack** — a pile. When the user presses the back button, the top Activity is popped off the stack and the previous one becomes visible again. Android manages this automatically.

---

## Part 4 — Reusable Screen Pieces: Fragments

### Why do we need Fragments?

Imagine a news app. On a phone, you see a list of headlines on one screen, and tapping one opens a detail screen. On a tablet, there is enough space to show both side-by-side at the same time. If you built these as two Activities, you would need completely separate code for the two layouts even though the logic is the same.

**Fragments** solve this. A Fragment is a modular, reusable piece of UI — think of it as a "sub-screen" that can be placed inside an Activity. The same Fragment can be used both alone (on a phone) and side-by-side with another Fragment (on a tablet).

### Fragment lifecycle

Fragments have their own lifecycle, tied to the host Activity. Key callbacks:

- `onCreateView()` — inflate the fragment's layout, return the view
- `onViewCreated()` — set up listeners and observe data (view is ready)
- `onDestroyView()` — clear view references (important to avoid memory leaks)

The Fragment is added to an Activity via the **FragmentManager**, which tracks all fragments in the back stack and manages transactions (add, replace, remove).

### Navigation Component

The Navigation Component is a Jetpack library that manages fragment navigation declaratively. You define a **nav graph** — a visual map of screens and the connections between them — in an XML file. To navigate, you call:

```kotlin
findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
```

The Navigation Component handles the back stack, the Up button, and can pass data between destinations using **Safe Args** (type-safe, auto-generated code that prevents typos in key names).

### Fragment communication

Fragments should not talk to each other directly — that creates tight coupling. The recommended approach is a **shared ViewModel**: both fragments observe the same ViewModel instance scoped to the parent Activity. One fragment writes, the other observes and updates automatically.

---

## Part 5 — Displaying Lists: RecyclerView

### The problem with long lists

Imagine displaying 10,000 contacts. If you created a view object for every contact at once, you would run out of memory. The solution is **recycling**: only create enough view objects to fill the screen, and as the user scrolls, *reuse* the view objects that scroll off the top to display new items coming in from the bottom.

### RecyclerView architecture

`RecyclerView` is Android's modern list component. It has three parts:

**ViewHolder** — holds references to the views inside one list item (so `findViewById` is called once per item, not every time it scrolls):

```kotlin
class ItemViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
```

**Adapter** — bridges the data and the RecyclerView. It creates ViewHolders and binds data to them:

```kotlin
class ItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder { ... }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.titleText.text = items[position].title
    }
    override fun getItemCount() = items.size
}
```

**LayoutManager** — decides *how* items are arranged: `LinearLayoutManager` (vertical or horizontal list), `GridLayoutManager` (grid), `StaggeredGridLayoutManager` (Pinterest-style).

### ViewPager2

`ViewPager2` uses `RecyclerView` under the hood and displays items as swipeable pages. Combined with `TabLayout` and `TabLayoutMediator`, it creates the classic tabbed interface pattern (like Spotify's Home / Search / Library tabs).

---

## Part 6 — The Modern UI Toolkit: Jetpack Compose

### Imperative vs Declarative UI

The XML approach is **imperative**: you describe *how* to change the UI step by step. When data changes, you manually call `textView.text = newValue`, `button.isEnabled = false`, etc.

**Jetpack Compose** is **declarative**: you describe *what* the UI should look like for a given state. When the state changes, Compose automatically re-runs your UI function with the new state and updates only what changed. You never manually update individual views.

This is the same philosophy as React (web) or SwiftUI (iOS).

### Composable functions

A composable function is any function annotated with `@Composable`. It describes a piece of UI:

```kotlin
@Composable
fun Greeting(name: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Hello, $name!", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = { /* do something */ }) {
            Text("Say hello")
        }
    }
}
```

Composables can call other composables — you build a tree of composable functions, just like you nest XML tags.

### State in Compose

State is data that, when it changes, should cause the UI to update. In Compose, you declare state with `remember` and `mutableStateOf`:

```kotlin
var count by remember { mutableStateOf(0) }
Button(onClick = { count++ }) {
    Text("Clicked $count times")
}
```

When `count` changes, Compose **recomposes** (re-runs) only the parts of the UI that depend on it.

**State hoisting** means moving state *up* to the parent. A composable that receives its value and a callback lambda (instead of managing its own state) is more reusable and testable.

**`rememberSaveable`** does the same as `remember` but also survives screen rotation (equivalent to `onSaveInstanceState`).

### Layouts and Scaffold

- `Column` — stacks children vertically
- `Row` — stacks children horizontally
- `Box` — overlaps children (like a stack of layers)
- `Scaffold` — the Material Design page structure: provides slots for TopAppBar, BottomBar, FloatingActionButton, and a content area

### LazyColumn (lists in Compose)

`LazyColumn` is the Compose equivalent of `RecyclerView`. It only composes visible items:

```kotlin
LazyColumn {
    items(myList, key = { it.id }) { item ->
        ItemCard(item)
    }
}
```

The `key` parameter helps Compose track which item is which when the list changes, enabling smooth animations.

### Theming in Compose

A Material Design 3 theme wraps your app:

```kotlin
MaterialTheme(
    colorScheme = lightColorScheme(primary = Purple80),
    typography = Typography
) {
    MyApp()
}
```

Inside any composable, you access `MaterialTheme.colorScheme.primary`, `MaterialTheme.typography.bodyLarge` etc. without passing them around manually.

---

## Part 7 — Styles, Themes, and Material Design (XML approach)

### Styles

A **Style** is a named set of view attributes you can reuse. Instead of writing `android:textSize="18sp" android:textColor="#333"` on every `TextView`, you define:

```xml
<style name="MyHeadline">
    <item name="android:textSize">18sp</item>
    <item name="android:textColor">#333333</item>
</style>
```

And apply it: `style="@style/MyHeadline"`.

### Themes

A **Theme** is like a style, but applied to the entire app or Activity, not just one view. Themes set default colours, typography, and widget appearances app-wide. In `AndroidManifest.xml`:

```xml
<application android:theme="@style/Theme.MyApp">
```

### Material Design

Material Design is Google's design system — a set of guidelines, components, and behaviours that make Android apps look and feel consistent. Material Design 3 (Material You) includes:

- **`TopAppBar`** — the bar at the top of the screen with a title and action icons
- **`BottomNavigationView`** — tabs at the bottom for switching between major sections
- **`FloatingActionButton` (FAB)** — the prominent circular button for the primary action
- **`Snackbar`** — a brief message that appears at the bottom of the screen (better than Toast for actionable feedback)
- **`MaterialCardView`** — a card with elevation and rounded corners
- **Elevation** — the concept that UI elements float at different heights above the screen surface, with shadows reflecting their Z position

---

## Part 8 — Concurrency: Doing Work Without Freezing the UI

### The main thread rule

Android's UI runs on a single thread called the **main thread** (also called the UI thread). This thread processes everything: touch events, drawing frames, and your code that responds to button clicks.

If you do something slow on the main thread — like downloading a file from the internet, or reading thousands of rows from a database — the thread is blocked. Android cannot process touch events. The screen appears frozen. After 5 seconds, Android shows an **ANR** (Application Not Responding) dialog and offers to kill your app.

**The rule is absolute: never do slow work on the main thread.**

### Threads

The most basic solution is to create a new `Thread` for background work:

```kotlin
Thread {
    val data = downloadData()   // runs on the new thread
    runOnUiThread {
        textView.text = data    // switches back to main thread
    }
}.start()
```

`runOnUiThread` posts a task to the main thread's queue.

### Handlers and Loopers

The main thread has a **Looper** — an infinite loop that processes a queue of **Messages**. A **Handler** lets you post Runnables or Messages to any thread's Looper from another thread.

A `HandlerThread` is a convenience class: a Thread that comes pre-equipped with a Looper, ready to receive work through a Handler.

```kotlin
val workerThread = HandlerThread("Worker")
workerThread.start()
val workerHandler = Handler(workerThread.looper)

workerHandler.post {
    // runs on workerThread
    val result = doWork()
    Handler(Looper.getMainLooper()).post {
        // back on main thread
        updateUI(result)
    }
}
```

### Kotlin Coroutines — the modern approach

Coroutines are the recommended modern solution. They are not threads — they are **lightweight tasks** that can be paused and resumed. Many coroutines can run on the same thread.

The key concept is the **suspend function** — a function marked `suspend` can be paused (without blocking its thread) and resumed later:

```kotlin
suspend fun fetchUser(): User {
    return withContext(Dispatchers.IO) {
        api.getUser()   // actually blocks the IO thread, but not the calling thread
    }
}
```

**Dispatchers** control which thread pool a coroutine runs on:
- `Dispatchers.Main` — the main/UI thread
- `Dispatchers.IO` — a pool of threads for I/O (network, disk)
- `Dispatchers.Default` — a pool for CPU-intensive work

**Scopes** tie coroutines to a lifecycle so they are automatically cancelled when the scope ends:
- `viewModelScope.launch { ... }` — cancelled when the ViewModel is destroyed
- `lifecycleScope.launch { ... }` — cancelled when the Activity/Fragment is destroyed

```kotlin
// In a ViewModel
viewModelScope.launch {
    val result = fetchUser()        // runs on IO, doesn't block
    _uiState.value = result         // back on Main automatically
}
```

Coroutines are written in a straightforward, sequential style — no callbacks, no nesting.

---

## Part 9 — Services: Background Work With No UI

### What is a Service?

A Service is an Android component that runs in the background without a user interface. Use cases: playing music while the user browses other apps, tracking location continuously, downloading a large file.

Services run on the **main thread** by default — they do not create a background thread automatically. You still need coroutines or threads inside a service.

### Types of Services

**Started Service**: you call `startService()` or `startForegroundService()`. The service runs until it calls `stopSelf()` or you call `stopService()`.

**Foreground Service**: a started service that is *required* to display a persistent notification (the notification tells the user the app is actively doing something). Required for long-running work like music playback or navigation. Android will not kill foreground services as aggressively.

```kotlin
startForeground(NOTIFICATION_ID, notification)
```

**Bound Service**: another component (Activity, Fragment) binds to it with `bindService()` and gets an interface to call methods directly. The service lives as long as something is bound.

### Broadcast Receivers

A BroadcastReceiver listens for system-wide or app-wide events. Examples of system broadcasts: boot completed, battery low, network connectivity changed.

You can register receivers:
- **Statically** in `AndroidManifest.xml` — receives broadcasts even when the app is not running
- **Dynamically** in code with `registerReceiver()` — only while the Activity/Fragment is alive

```kotlin
val receiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // React to the event
    }
}
registerReceiver(receiver, IntentFilter(Intent.ACTION_BATTERY_LOW))
```

---

## Part 10 — Local Storage: Keeping Data Between App Launches

### The problem

When the user closes your app, all in-memory data is gone. To persist data — user settings, a shopping cart, a to-do list — you must save it to the device's storage.

### SharedPreferences

The simplest option: a key-value store, like a persistent dictionary. Best for simple settings and flags.

```kotlin
val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)

// Write
prefs.edit { putBoolean("darkMode", true) }

// Read
val isDark = prefs.getBoolean("darkMode", false)
```

Not suitable for large amounts of data or structured/relational data.

### SQLite and Room

**SQLite** is a small, file-based relational database built into Android. You can write raw SQL queries against it. Raw SQLite is verbose and error-prone (string-typed column names, manual cursor handling).

**Room** is a Jetpack library that wraps SQLite with a type-safe, annotation-driven API:

```kotlin
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String
)

@Dao
interface NoteDao {
    @Query("SELECT * FROM note ORDER BY id DESC")
    fun getAll(): Flow<List<Note>>

    @Insert suspend fun insert(note: Note)
    @Delete suspend fun delete(note: Note)
}

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
```

- `@Entity` — marks a data class as a database table
- `@Dao` — Data Access Object; defines operations on a table
- `@Database` — the database itself; you get DAO instances from it
- `Flow<List<Note>>` — the query returns a reactive stream: every time the database changes, the UI receives the updated list automatically

### The Repository Pattern

To keep ViewModels clean and testable, a **Repository** class acts as the single source of truth for data. The ViewModel calls the Repository; the Repository talks to Room and/or a network API.

```
UI → ViewModel → Repository → Room (local) / Firebase (remote)
```

---

## Part 11 — Cloud Storage: Firebase

### What is Firebase?

Firebase is Google's **Backend-as-a-Service (BaaS)** platform. It provides pre-built backend infrastructure so you do not need to build and host your own server. The services most used in this course:

- **Firebase Authentication** — user accounts and sign-in
- **Firebase Realtime Database** — a cloud-hosted JSON database, synced in real time across all connected devices
- **Firebase Cloud Storage** — binary file storage (images, audio, video)

### Setup

1. Create a project in the **Firebase Console** (`console.firebase.google.com`).
2. Register your Android app (package name + SHA-1 fingerprint for Google Sign-In).
3. Download `google-services.json` and place it in the `app/` folder.
4. Add the `google-services` Gradle plugin and the Firebase SDK to your build files.

### Firebase Authentication

Firebase Auth manages user identities. You never store passwords yourself — Firebase does.

```kotlin
Firebase.auth.signInWithEmailAndPassword(email, password)
    .addOnSuccessListener { result ->
        val user = result.user   // FirebaseUser object
        // navigate to main screen
    }
    .addOnFailureListener { exception ->
        // show error
    }
```

**FirebaseUI** provides pre-built, branded sign-in screens with minimal code — it handles email, Google, Facebook, etc. for you.

After sign-in, `Firebase.auth.currentUser` is non-null anywhere in your app. On `signOut()`, it becomes null.

The user's `uid` is unique and stable — use it to organise their data in the database.

### Firebase Realtime Database

A NoSQL (non-relational) database that stores data as a JSON tree. Everything is a path:

```
/users
    /abc123uid
        /name: "Alice"
        /email: "alice@example.com"
/notes
    /abc123uid
        /noteId1
            /title: "Shopping list"
```

Read/write:

```kotlin
val db = Firebase.database.reference

// Write
db.child("users").child(uid).child("name").setValue("Alice")

// Read once
db.child("users").child(uid).get().addOnSuccessListener { snapshot ->
    val name = snapshot.getValue(String::class.java)
}

// Listen for real-time changes
db.child("notes").child(uid).addValueEventListener(object : ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {
        val notes = snapshot.children.mapNotNull { it.getValue(Note::class.java) }
        updateUI(notes)
    }
    override fun onCancelled(error: DatabaseError) {}
})
```

**Offline persistence**: `Firebase.database.setPersistenceEnabled(true)` caches data locally. The app works offline and syncs when reconnected.

### Firebase Security Rules

By default, your database is locked. Security Rules define who can read and write what:

```json
{
  "rules": {
    "notes": {
      "$uid": {
        ".read": "auth.uid === $uid",
        ".write": "auth.uid === $uid"
      }
    }
  }
}
```

This rule says: a user can only read and write their own notes. Rules are enforced server-side — even if someone bypasses your app, the server rejects invalid requests.

### Firebase Cloud Storage

For files (photos, audio clips), you do not store binary data in the Realtime Database. Cloud Storage is designed for this:

```kotlin
val storageRef = Firebase.storage.reference.child("images/$uid.jpg")

// Upload
storageRef.putFile(imageUri).addOnSuccessListener {
    storageRef.downloadUrl.addOnSuccessListener { uri ->
        // Save this URL to the Realtime Database
    }
}
```

---

## Part 12 — Location and Maps

### Permissions

Location is sensitive. On Android, there are two tiers:

- `ACCESS_COARSE_LOCATION` — approximate location (city-level, uses Wi-Fi/cell towers)
- `ACCESS_FINE_LOCATION` — precise GPS location

These are **runtime permissions** — even if declared in the manifest, you must ask the user at runtime while the app is running. If they say no, you cannot use location.

```kotlin
val locationPermissionRequest = registerForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
) { permissions ->
    when {
        permissions[ACCESS_FINE_LOCATION] == true -> startLocationUpdates()
        else -> showPermissionDeniedMessage()
    }
}
locationPermissionRequest.launch(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION))
```

For background location (app receives location even when not visible), you need `ACCESS_BACKGROUND_LOCATION` — and Google Play requires extra justification.

### Fused Location Provider

The old `LocationManager` API is verbose and requires choosing between GPS, Wi-Fi, and cell-based location manually. The modern replacement is the **Fused Location Provider** from Google Play Services — it intelligently combines all available sources for the best accuracy and battery tradeoff.

```kotlin
val fusedClient = LocationServices.getFusedLocationProviderClient(this)

// Last known position (instant, but may be null or stale)
fusedClient.lastLocation.addOnSuccessListener { location ->
    if (location != null) showOnMap(location.latitude, location.longitude)
}

// Continuous updates
val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000L).build()
fusedClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())
```

For background continuous tracking, put the Fused Location Provider inside a **Foreground Service**.

### Geocoding

A `Location` gives you `latitude` and `longitude` — numbers. Geocoding converts between coordinates and human-readable addresses:

```kotlin
// Reverse geocoding: coordinates → address
val addresses = Geocoder(context).getFromLocation(latitude, longitude, 1)
val street = addresses?.firstOrNull()?.getAddressLine(0)
```

Run this on a background thread (it makes a network call).

### Google Maps SDK

Display an interactive map:

1. Enable the Maps SDK for Android in the Google Cloud Console.
2. Create an **API key** — a credential that identifies your app to Google's servers. Restrict it to your app's SHA-1 and package name.
3. Add the key to `AndroidManifest.xml`.

```kotlin
// In the map callback
googleMap.addMarker(
    MarkerOptions()
        .position(LatLng(55.676, 12.568))
        .title("ITU Copenhagen")
)
googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(55.676, 12.568), 15f))
```

The **Google Maps Compose** library provides a `GoogleMap` composable for use in Compose-based apps.

---

## Part 13 — Multimedia: Camera, Audio, and Computer Vision

### CameraX

CameraX is the Jetpack camera library — lifecycle-aware and consistent across devices. It has three **use cases** you can run simultaneously:

- **Preview** — streams the camera to a `PreviewView` on screen
- **ImageCapture** — takes a still photo on demand
- **ImageAnalysis** — delivers every camera frame to a function you provide (for real-time processing)

```kotlin
val cameraProvider = ProcessCameraProvider.getInstance(context).get()
val preview = Preview.Builder().build().also {
    it.setSurfaceProvider(previewView.surfaceProvider)
}
val capture = ImageCapture.Builder().build()

cameraProvider.bindToLifecycle(lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, preview, capture)

// Take a photo
capture.takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
    override fun onImageSaved(output: ImageCapture.OutputFileResults) { ... }
    override fun onError(exception: ImageCaptureException) { ... }
})
```

Requires the `android.permission.CAMERA` runtime permission.

### MediaPlayer

`MediaPlayer` plays audio and video from files, resources, or streaming URLs:

```kotlin
val player = MediaPlayer.create(context, R.raw.my_song)
player.start()
player.pause()
player.release()   // always release when done!
```

`MediaPlayer` has an internal **state machine** (Idle → Initialized → Prepared → Started/Paused/Stopped → End). Calling the wrong method in the wrong state throws an exception. For playing music while the app is in the background, wrap the MediaPlayer in a **Foreground Service**.

### OpenCV

OpenCV (Open Source Computer Vision Library) provides hundreds of classical image processing algorithms: colour space conversion, edge detection, contour finding, feature matching, histogram equalisation, and more.

In Android, you get camera frames from CameraX as `ImageProxy` objects, convert them to OpenCV's `Mat` (matrix) format, apply operations, and render the result. OpenCV does not use machine learning for most of its features — it uses mathematical transformations. This makes it fast and predictable.

### Firebase ML Kit

ML Kit provides pre-trained machine learning models you can run directly on the device:

- **Object detection and tracking** — where are objects in the frame, what are they?
- **Image labelling** — what is generally depicted in this image?
- **Text recognition (OCR)** — extract text from photos
- **Face detection, barcode scanning, pose detection**

You feed it an `InputImage` (from a file, a camera frame, or a bitmap) and it returns structured results:

```kotlin
val detector = ObjectDetection.getClient(options)
detector.process(inputImage)
    .addOnSuccessListener { objects ->
        for (obj in objects) {
            val box = obj.boundingBox      // where on screen
            val label = obj.labels.firstOrNull()?.text   // what it is
        }
    }
```

On-device models (no network after initial download) vs cloud models (more accurate, requires internet, costs money per call).

---

## Part 14 — Machine Learning on the Device: Edge AI

### Why run ML on the device?

The alternative to on-device (edge) inference is **cloud inference**: you send an image to a server, the server runs the model, and you get a result back. The problem with this:

- **Latency**: even fast servers add 100–500 ms of round-trip time. For real-time augmented reality or live video analysis, this is too slow.
- **Privacy**: you are sending potentially sensitive images/data to a server.
- **Connectivity**: no internet = no inference.
- **Cost**: you pay for every inference.

Edge AI solves all four problems by running the model on the phone's hardware directly.

### The challenge: models are big

A neural network trained for high accuracy might be hundreds of megabytes of 32-bit floating-point numbers (FP32). A phone has limited RAM and a relatively weak processor compared to a server GPU. You cannot just copy a cloud model onto a phone.

### Model optimisation: Quantization

**Quantization** reduces the number of bits used to represent each weight in the model:

- **FP32** (full precision): 4 bytes per weight — accurate, large, slow on mobile
- **FP16** (half precision): 2 bytes per weight — 2× smaller, GPU-friendly
- **INT8** (8-bit integer): 1 byte per weight — 4× smaller, can run on dedicated hardware (DSP/NPU), tiny accuracy loss

**Post-Training Quantization (PTQ)**: take a trained FP32 model, run a conversion tool, get a smaller INT8 model — no retraining needed.

**Quantization-Aware Training (QAT)**: simulate the effects of quantization during training, so the model learns to compensate — better accuracy than PTQ but requires access to the training process.

### TensorFlow Lite

Google's cross-platform mobile inference framework. You convert a TensorFlow/Keras model to a `.tflite` file and include it in your app:

```kotlin
val interpreter = Interpreter(loadModelFile("model.tflite"))
interpreter.run(inputBuffer, outputBuffer)
```

**Delegates** offload computation to hardware accelerators:
- `GpuDelegate` — run on the phone's GPU
- `NnApiDelegate` — use Android's Neural Networks API (routes to vendor hardware)

### Qualcomm SNPE

Qualcomm makes the Snapdragon chips found in many Android phones. Snapdragon chips include dedicated AI hardware: a **DSP (Digital Signal Processor)** and/or **NPU (Neural Processing Unit)** that can run neural network layers much faster and more efficiently than the CPU.

SNPE (Snapdragon Neural Processing Engine) is Qualcomm's SDK for this hardware. Models are converted to Qualcomm's `.dlc` format and run via a JNI (Java Native Interface) bridge — your Kotlin code calls C++ code that calls the SNPE native library.

**Qualcomm AI Hub** is a cloud service that takes your model, compiles and optimises it for a specific Snapdragon device, and lets you download the result. It removes the manual SNPE conversion steps.

| | TFLite | SNPE |
|---|---|---|
| Works on | Any Android phone | Snapdragon phones only |
| Best for | General cross-platform deployment | Maximum performance on Qualcomm hardware |
| Integration | Simple Kotlin API | JNI / C++ bridge |
| Model format | `.tflite` | `.dlc` |

---

## Part 15 — Sensors

### The Sensor Framework

Android provides a unified API to read data from any hardware sensor, regardless of which manufacturer made it. The key classes:

- **`SensorManager`** — the entry point; get it via `getSystemService(SENSOR_SERVICE)`
- **`Sensor`** — represents a specific physical sensor (`Sensor.TYPE_ACCELEROMETER`, etc.)
- **`SensorEventListener`** — the interface you implement to receive data
- **`SensorEvent`** — the data delivered to your listener: `values[]` array + timestamp

```kotlin
val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
val accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI)
// ALWAYS unregister in onPause() to save battery:
// sensorManager.unregisterListener(this)

override fun onSensorChanged(event: SensorEvent) {
    val x = event.values[0]   // acceleration along X axis in m/s²
    val y = event.values[1]
    val z = event.values[2]
}
```

### Motion sensors

- **Accelerometer** (`TYPE_ACCELEROMETER`): measures acceleration including gravity. When the phone is flat on a table, `z ≈ 9.8 m/s²` (gravity pulling down). Tilt the phone and the values shift between axes. Used for: shake detection, screen rotation, step counting.
- **Gyroscope** (`TYPE_GYROSCOPE`): measures the *rate* of rotation around each axis in radians per second. Used for: game controllers, image stabilisation, VR.
- **Linear Acceleration** (`TYPE_LINEAR_ACCELERATION`): accelerometer minus gravity — gives you just the motion, not gravity.
- **Step Counter / Step Detector**: hardware-assisted step counting, much more battery-efficient than doing it in software.

### Position sensors

- **Magnetometer** (`TYPE_MAGNETIC_FIELD`): measures the ambient magnetic field. Combined with the accelerometer, you can compute a **compass heading** using `SensorManager.getRotationMatrix()` and `getOrientation()`.
- **Rotation Vector** (`TYPE_ROTATION_VECTOR`): the device's orientation as a **quaternion** (a mathematical representation of 3D rotation). The most reliable way to know which way the device is pointing.
- **Proximity** (`TYPE_PROXIMITY`): distance to the nearest object in centimetres. Used to turn the screen off when you hold the phone to your ear during a call.

### Environmental sensors

- **Light** (`TYPE_LIGHT`): ambient illuminance in lux. Used to auto-adjust screen brightness.
- **Pressure** (`TYPE_PRESSURE`): atmospheric pressure in hPa. Can be used to estimate altitude.
- **Temperature** (`TYPE_AMBIENT_TEMPERATURE`): air temperature in °C.
- **Relative Humidity** (`TYPE_RELATIVE_HUMIDITY`): relative humidity percentage.

### Bluetooth

Bluetooth is a wireless communication protocol for short-range (≈10 m) device-to-device communication. **Bluetooth Classic** (BR/EDR) is designed for continuous data streams: audio, serial data.

The flow to connect two Android devices:
1. Get the `BluetoothAdapter` (the phone's Bluetooth hardware)
2. Discover nearby devices (scan) or use a previously paired device
3. Create a `BluetoothSocket` (like a TCP socket but over Bluetooth)
4. Connect the socket
5. Read/write via `socket.inputStream` and `socket.outputStream`

Because Bluetooth I/O blocks the thread, you need **three threads**: a server thread (waiting for connections), a client thread (initiating connections), and a connected thread (doing the actual I/O once connected). Use a `Handler` to post results back to the main thread for UI updates.

Android 12+ requires runtime permissions: `BLUETOOTH_SCAN`, `BLUETOOTH_CONNECT`, `BLUETOOTH_ADVERTISE`.

**Bluetooth Low Energy (BLE)** is a different variant designed for small, infrequent data transmissions at extremely low power (e.g., heart rate monitors, smart locks, beacons). **Beacons** are small BLE devices that continuously broadcast a fixed identifier (UUID + major + minor numbers). Your app scans for beacons, reads their signal strength (RSSI), and estimates proximity.

---

## Part 16 — Google Firebase (Authentication + Testing)

*(Firebase storage was covered in Part 11. This section covers authentication and testing.)*

### Firebase Authentication in depth

Firebase Auth is stateful and persistent. After the user signs in, Firebase stores a refresh token on the device. The next time the app opens, `Firebase.auth.currentUser` is already non-null — the user is still logged in, no re-entry of credentials needed.

The `currentUser.uid` is a unique, immutable string assigned to every user. You use it as the key to organise that user's data everywhere:

```
database/notes/UID/noteId → note data
storage/images/UID/photoId → photo file
```

**FirebaseUI** is a library of pre-built sign-in screens. You configure it with the providers you want (email, Google, GitHub, etc.), launch it with an Intent, and handle the result. It deals with all edge cases: "forgot password", email verification, account linking (same email, two providers).

### Testing Android apps

**Unit tests** test a single function or class in complete isolation, without the Android framework. They run on the JVM (your development machine), not on a device, so they are very fast:

```kotlin
@Test
fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
}
```

**Instrumentation tests** run on a real device or emulator. They can interact with the Android framework. **Espresso** is the standard library for these:

```kotlin
onView(withId(R.id.loginButton)).perform(click())
onView(withId(R.id.welcomeText)).check(matches(isDisplayed()))
```

**Compose tests** use a different API since there are no view IDs in Compose — you find nodes by test tags or content description:

```kotlin
composeTestRule.onNodeWithTag("login_button").performClick()
composeTestRule.onNodeWithText("Welcome").assertIsDisplayed()
```

**Hamcrest matchers** provide expressive, readable assertions: `assertThat(result, containsString("error"))`.

---

## Part 17 — Security and Publishing

### Why security matters before publishing

Once your app is in the Play Store, anyone can download it. APK files can be opened with freely available tools (`jadx`, `apktool`) and the code decompiled. Without protection, your API keys, business logic, and data access patterns are visible to anyone curious enough to look.

### Code shrinking and obfuscation: R8 / ProGuard

**R8** (Google's replacement for ProGuard) does three things to your release build:

1. **Shrinking (tree-shaking)**: removes all classes and methods that are never called. A typical app includes many library classes it never uses; shrinking deletes them, making the APK smaller.

2. **Obfuscation**: renames classes, methods, and fields to meaningless single-letter names. `LoginActivity` becomes `a`. `validatePassword()` becomes `b()`. This does not make decompilation impossible, but makes the output nearly unreadable.

3. **Optimization**: inlines small functions, removes unreachable code.

Enable it in `build.gradle`:
```kotlin
buildTypes {
    release {
        isMinifyEnabled = true
        isShrinkResources = true
        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
}
```

You need **keep rules** to prevent R8 from stripping things that are accessed through reflection (like data classes serialized by Gson, Firebase model classes, or annotated code):

```
-keep class com.example.model.** { *; }
```

When a crash occurs in a production obfuscated build, the stack trace will show `a.b()` instead of real names. The `mapping.txt` file generated alongside the release build maps obfuscated names back to originals. Upload it to the Play Console to get de-obfuscated crash reports.

### Protecting sensitive information

**Never hardcode secrets in source code.** API keys, database URLs, and passwords committed to Git are a serious security risk. The correct approach:

- Store secrets in `local.properties` (which should be in `.gitignore`).
- Read them in `build.gradle` and inject them as `BuildConfig` fields — generated at build time, not present as plain strings in source.
- For secrets needed at runtime on the device, use `EncryptedSharedPreferences` (encrypts the values at rest using Android's keystore).

### Signing the app

Every Android app must be **digitally signed** before installation. Signing proves the app came from you and has not been tampered with.

You generate a **keystore** file (a secure container holding your private key) and configure your release build to sign with it. Keep this file safe — if you lose it, you cannot publish updates to your app on the Play Store.

Google Play offers **App Signing by Google Play**: you upload with an "upload key", and Google re-signs the actual distributed APK with a key they manage. This protects against losing your key.

### Android App Bundle (.aab)

The modern distribution format. Instead of building one APK that contains resources for all screen sizes, languages, and device architectures, you build one `.aab` that contains everything. Google Play then generates optimized APKs per device. Users download a smaller, tailored APK. Required by Google Play since August 2021.

### Google Play publishing steps

1. Create a **Google Play Developer** account ($25 one-time registration fee).
2. Create an app in the **Play Console** — set package name, default language.
3. Build and upload a signed `.aab` to a **track** (Internal Testing → Closed Testing → Open Testing → Production). Start with Internal for your own testing.
4. Fill in the **store listing**: title (max 30 characters), short description (80 chars), full description (4000 chars), screenshots for phone/tablet, feature graphic.
5. Complete the **app content** section: content rating questionnaire, data safety form (what data you collect and why), privacy policy URL.
6. Submit for **review**. First submissions can take several days; updates are usually reviewed in hours.
7. Once approved, use **staged rollout** for production releases (10% of users first, monitor crash rates, then increase).

---

## Quick Reference: The Course in One Page

| Topic | Core concept | Key class / tool |
|---|---|---|
| Android app structure | Components declared in manifest | `AndroidManifest.xml` |
| Screens | Activity = one screen | `AppCompatActivity`, `onCreate()` |
| Activity state | Lifecycle callbacks | `onPause()`, `onStop()`, `ViewModel` |
| Rotation survival | ViewModel survives config changes | `ViewModel`, `by viewModels()` |
| Navigation | Intents pass messages between Activities | `Intent`, `putExtra()` |
| Reusable UI | Fragments are modular screen pieces | `Fragment`, `NavController` |
| Long lists | Recycle views to save memory | `RecyclerView`, `ViewHolder`, `Adapter` |
| Modern UI | Declarative, state-driven | `@Composable`, `remember`, `LazyColumn` |
| Design system | Consistent visual language | Material Design 3, themes, styles |
| Background work | Never block the main thread | Coroutines, `Dispatchers.IO`, `viewModelScope` |
| Long background work | Service with notification | Foreground `Service` |
| System events | Listen for broadcasts | `BroadcastReceiver` |
| Simple storage | Key-value pairs | `SharedPreferences` |
| Structured storage | SQL database, type-safe | Room (`@Entity`, `@Dao`, `@Database`) |
| User accounts | Firebase manages passwords | `FirebaseAuth`, `FirebaseUI` |
| Cloud database | Real-time JSON sync | Firebase Realtime Database |
| Cloud files | Images, audio, video | Firebase Cloud Storage |
| Security rules | Server-side access control | Firebase Security Rules |
| Location | GPS + Wi-Fi + cell fused | `FusedLocationProviderClient` |
| Maps | Rendered map with markers | Google Maps SDK, API key |
| Camera | Lifecycle-aware capture + analysis | CameraX |
| Audio | Play files and streams | `MediaPlayer` |
| Computer vision | Classical image processing | OpenCV |
| On-device ML | Pre-trained models, no network | Firebase ML Kit |
| Edge inference | Model on phone hardware | TensorFlow Lite, SNPE |
| Model compression | Smaller + faster model | Post-training quantization |
| Motion sensing | Accelerometer, gyroscope | `SensorManager`, `SensorEvent` |
| Wireless comms | Device-to-device data | Bluetooth Classic, BLE |
| Testing | Unit + UI automated tests | JUnit, Espresso, Compose test API |
| Release hardening | Obfuscate, shrink, sign | R8/ProGuard, keystore |
| Distribution | Optimized per-device APKs | Android App Bundle, Play Console |
