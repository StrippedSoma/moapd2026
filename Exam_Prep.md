# Exam Preparation Guide — MOAPD 2026

> **Exam format:** Draw one question, write outline on whiteboard, max 8 min presentation, then follow-up questions.
> You may bring one A4 sheet (bullet outline + keywords + 1–2 figures) — glance at it for 30 seconds after drawing, then set it aside.
> Start every answer with a **brief topic overview**, then dive into the substantial parts. Use correct Android/Kotlin terminology throughout.

---

<!-- ============================================================ -->
<!-- QUESTION 1 -->
<!-- ============================================================ -->

# Q1 — Android Framework UI Toolkit

> *An Android app has two screens: (1) a login screen, and (2) a main screen. Explain how you would implement this using Activities, how the Activity lifecycle affects the app, and how data can be shared between the two screens using Intents or ViewModel.*

## Opening overview (30 sec)
- Android apps are built around **Activities** — each screen is typically one Activity.
- The OS manages Activity lifetimes through a well-defined **lifecycle**.
- Screens communicate via **Intents** (explicit, passing extras) or share state through a **ViewModel**.

## 1. Activities & the two-screen structure
- Each screen = one `Activity` subclass declared in `AndroidManifest.xml`.
- `LoginActivity` → on success → `startActivity(Intent(this, MainActivity::class.java))`.
- Use **View Binding** to safely reference UI widgets (`ActivityLoginBinding`).
- MVC/MVVM: layout is the **View**, Activity is the **Controller**, ViewModel holds the **Model**.

## 2. Activity Lifecycle (key callbacks)
| Callback | When it runs | What to do |
|---|---|---|
| `onCreate()` | First creation | Inflate layout, init ViewModel, set up listeners |
| `onStart()` | Becomes visible | Register receivers if needed |
| `onResume()` | Foreground & interactive | Start animations, acquire camera |
| `onPause()` | Partially obscured | Release camera, pause animations |
| `onStop()` | No longer visible | Persist lightweight data |
| `onDestroy()` | About to be killed | Clean up resources |

- **Configuration changes** (rotation) destroy and recreate the Activity — data in plain fields is lost.
- Survive rotation with `onSaveInstanceState(Bundle)` for small transient state, or **ViewModel** for larger data.
- `ViewModel` survives rotation because it is scoped to the Activity's *ViewModelStore*, not the Activity itself.

## 3. Passing data with Intents
```kotlin
// Sending from LoginActivity
val intent = Intent(this, MainActivity::class.java).apply {
    putExtra("USER_EMAIL", email)
}
startActivity(intent)

// Receiving in MainActivity
val email = intent.getStringExtra("USER_EMAIL")
```
- For getting a **result back**: use `registerForActivityResult(StartActivityForResult())`.

## 4. Sharing data with ViewModel + LiveData
```kotlin
class AuthViewModel : ViewModel() {
    val userEmail = MutableLiveData<String>()
}
// Both activities can share the same ViewModel if scoped to the same owner
```
- **ViewModel** is preferred when data must survive rotation or be observed reactively.
- **LiveData** lets the UI react to changes automatically (observer pattern).

## Key diagrams to draw on whiteboard
1. Activity lifecycle state diagram (Created → Started → Resumed → Paused → Stopped → Destroyed).
2. Flow: LoginActivity → Intent → MainActivity.

## Likely follow-up topics
- `onSaveInstanceState` vs ViewModel — when to use each.
- What happens to the back stack when you `finish()` vs just navigate?
- Difference between `startActivity` and `startActivityForResult` (deprecated) vs `ActivityResultLauncher`.

---

<!-- ============================================================ -->
<!-- QUESTION 2 -->
<!-- ============================================================ -->

# Q2 — Advanced User Interface Components

> *You are designing an Android app with multiple sections, reusable screens, and a consistent visual identity. Explain how Material Design, themes, fragments, fragment navigation, and communication between fragments can be used to structure this app.*

## Opening overview (30 sec)
- **Material Design** provides a visual language (components, motion, typography).
- **Themes** apply that language consistently across the entire app.
- **Fragments** are reusable, modular UI pieces that can be swapped inside an Activity.
- The **Navigation component** manages the fragment back stack and transitions declaratively.
- Fragments communicate via a **shared ViewModel** (recommended) or callback interfaces.

## 1. Material Design & Themes
- Material Design 3 (`com.google.android.material` library) provides: `TopAppBar`, `BottomNavigationView`, `FloatingActionButton`, `Snackbar`, `MaterialCardView`.
- **Theme** (`res/values/themes.xml`): inherit from `Theme.Material3.*`; set `colorPrimary`, `colorSecondary`, `colorSurface`.
- Applying a theme in the manifest: `android:theme="@style/Theme.MyApp"`.
- **Styles** are reusable attribute bundles for individual views; **themes** are app-wide attribute sets.

## 2. Fragments
- `Fragment` is a modular UI component with its own lifecycle tied to its host Activity.
- Fragment lifecycle: `onAttach → onCreate → onCreateView → onViewCreated → onStart → onResume → … → onDestroyView → onDetach`.
- Use **View Binding** inside fragments (inflate in `onCreateView`, clear in `onDestroyView`).
- Add to a container programmatically via `FragmentManager` and `FragmentTransaction`, or declaratively via Navigation.

## 3. Navigation Component
- Define a **nav graph** (`res/navigation/nav_graph.xml`) with destinations and actions.
- Host via `<androidx.navigation.fragment.NavHostFragment>` in the Activity layout.
- Navigate: `findNavController().navigate(R.id.action_loginFragment_to_mainFragment)`.
- `AppBarConfiguration` + `NavigationUI.setupActionBarWithNavController()` auto-manages the Up button.
- `BottomNavigationView` + `NavigationUI.setupWithNavController()` handles tab switching.
- Pass data between destinations with **Safe Args** (type-safe, generated `Directions` classes).

## 4. Communication between fragments
| Method | When to use |
|---|---|
| Shared `ViewModel` | Same Activity scope — preferred; survives config changes |
| Fragment result API | `setFragmentResult` / `setFragmentResultListener` — for one-shot results |
| Callback interface | Legacy pattern; fragment defines interface, Activity implements it |

```kotlin
// Shared ViewModel (both fragments use the same instance)
private val viewModel: SharedViewModel by activityViewModels()
```

## Key diagrams to draw on whiteboard
1. Fragment lifecycle callbacks.
2. Nav graph: destinations + actions.
3. Activity → NavHostFragment → Fragment A/B/C.

## Likely follow-up topics
- Difference between `addToBackStack()` and not.
- `FragmentManager` vs `ChildFragmentManager`.
- Safe Args vs `Bundle` for passing data.
- `DialogFragment` as a destination.

---

<!-- ============================================================ -->
<!-- QUESTION 3 -->
<!-- ============================================================ -->

# Q3 — Flexible Data View and Threads

> *An Android app must display a long list of items downloaded from a slow operation without freezing the interface. Explain how RecyclerView, ViewHolder, adapters, threads, handlers, coroutines, and services can help solve this problem.*

## Opening overview (30 sec)
- The **main thread** handles UI; blocking it freezes the app.
- **RecyclerView** efficiently displays long lists by recycling views.
- Heavy work (network, DB) must move to **background threads** via coroutines, Handlers, or Services.

## 1. RecyclerView architecture
```
RecyclerView
  └── LayoutManager    (LinearLayoutManager, GridLayoutManager)
  └── Adapter          (binds data to ViewHolders)
        └── ViewHolder (holds references to item views)
```
- `RecyclerView.Adapter<VH>`: implement `onCreateViewHolder()`, `onBindViewHolder()`, `getItemCount()`.
- `ViewHolder` caches `findViewById()` calls — avoids repeated lookups on scroll.
- `DiffUtil` / `ListAdapter` for efficient incremental updates (only changed items re-bind).

## 2. Threads and the main thread rule
- **Main thread = UI thread**: never run network/DB calls here (throws `NetworkOnMainThreadException`).
- Options for background work:
  - `Thread` + `Handler` / `Looper`: low-level; `Handler(Looper.getMainLooper()).post { /* update UI */ }`.
  - **Kotlin Coroutines** (preferred modern approach).
  - `AsyncTask` (deprecated).

## 3. Kotlin Coroutines
```kotlin
// In a ViewModel
viewModelScope.launch {
    val items = withContext(Dispatchers.IO) { repository.fetchItems() }
    _items.value = items   // back on Main dispatcher automatically
}
```
- `Dispatchers.IO` — for network/disk.
- `Dispatchers.Main` — for UI updates.
- `lifecycleScope` / `viewModelScope` — auto-cancel on lifecycle destruction.
- `suspend` functions — can be paused without blocking the thread.

## 4. Handlers and Loopers (classic approach)
- Each thread can have a `Looper` — a message queue processor.
- `HandlerThread`: a pre-built `Thread` with a `Looper`.
- `Handler` posts `Message` objects to a thread's queue.
- Use to send results from a background `HandlerThread` back to the main thread's `Handler`.

## 5. Services for long-running background work
- **Started Service** (`Service`): runs independently of UI; must call `stopSelf()`.
- **Foreground Service**: shows a persistent notification; suitable for ongoing downloads or playback.
- **Bound Service**: client-server pattern; Activity binds to get a reference.
- Services run on the **main thread** by default — still need threads/coroutines inside.

## Key diagrams to draw on whiteboard
1. RecyclerView → Adapter → ViewHolder → item layout.
2. Main thread ↔ background thread ↔ Handler ↔ UI update.
3. Coroutine dispatcher switch: `Dispatchers.IO` → `Dispatchers.Main`.

## Likely follow-up topics
- `ListAdapter` vs plain `RecyclerView.Adapter`.
- `WorkManager` vs foreground service — when to use each.
- `IntentService` vs `JobIntentService` vs coroutine in ViewModel.
- How `ViewPager2` uses `RecyclerView` under the hood.

---

<!-- ============================================================ -->
<!-- QUESTION 4 -->
<!-- ============================================================ -->

# Q4 — Jetpack Compose

> *You are redesigning a traditional Android XML-based interface using Jetpack Compose. Explain the role of composable functions, modifiers, state, theming, layouts, and LazyList in building a modern Android UI.*

## Opening overview (30 sec)
- **Jetpack Compose** is Android's modern **declarative** UI toolkit — describe *what* to show, not *how* to update it.
- Replaces XML layouts with Kotlin functions annotated `@Composable`.
- UI **recomposes** automatically when state changes.

## 1. Composable functions
```kotlin
@Composable
fun Greeting(name: String) {
    Text(text = "Hello, $name!")
}
```
- Pure functions: same input → same UI output.
- Can call other composables; build a tree of composables.
- Entry point: `setContent { MyAppTheme { /* composables */ } }`.

## 2. Modifiers
- Chain visual/behavioral attributes: `Modifier.padding(16.dp).fillMaxWidth().background(Color.Blue)`.
- Order matters — `padding` before `background` vs after produces different results.
- Pass `modifier: Modifier = Modifier` as a parameter for composable reusability.

## 3. State management
```kotlin
var count by remember { mutableStateOf(0) }
Button(onClick = { count++ }) { Text("Count: $count") }
```
- `remember` — survives recompositions within the same composition.
- `rememberSaveable` — also survives configuration changes.
- **State hoisting**: move state up to the caller; pass value + lambda down → makes composables stateless and testable.
- **ViewModel + StateFlow/LiveData**: `val items by viewModel.items.collectAsState()`.

## 4. Theming (Material 3)
```kotlin
MaterialTheme(
    colorScheme = lightColorScheme(primary = Purple40),
    typography = Typography,
) { /* content */ }
```
- `MaterialTheme.colorScheme.primary`, `.surface`, `.onBackground`, etc.
- `MaterialTheme.typography.headlineMedium`, `.bodyLarge`.
- Dark theme: check `isSystemInDarkTheme()`, provide `darkColorScheme`.

## 5. Layouts
| Composable | Purpose |
|---|---|
| `Column` | Vertical stack |
| `Row` | Horizontal stack |
| `Box` | Z-axis stacking (overlay) |
| `Scaffold` | App structure: TopAppBar, FAB, BottomBar, content slot |

## 6. LazyList (efficient long lists)
```kotlin
LazyColumn {
    items(itemList) { item ->
        ItemCard(item)
    }
}
```
- `LazyColumn` / `LazyRow` — compose only visible items (like RecyclerView).
- `items()`, `itemsIndexed()`, `item {}` — flexible DSL.
- Keys: `items(list, key = { it.id })` — helps Compose track identity on recomposition.

## Key diagrams to draw on whiteboard
1. Declarative vs imperative: XML `setText()` vs Compose recomposition.
2. State hoisting diagram: parent holds state, child is stateless.
3. `Scaffold` slot layout.

## Likely follow-up topics
- `SideEffect`, `LaunchedEffect`, `DisposableEffect` — when to use each.
- Navigation in Compose (`NavHost`, `composable()` destinations).
- Interop: `AndroidView` in Compose, `ComposeView` in XML.
- Performance: `key`, `derivedStateOf`, `remember` with lambdas.

---

<!-- ============================================================ -->
<!-- QUESTION 5 -->
<!-- ============================================================ -->

# Q5 — Google Firebase

> *You are building an Android app that requires user authentication and cloud-based backend services. Explain how Google Firebase can be integrated into an Android app, including the role of the Firebase Console, Google Developer Account, hash fingerprint, Firebase services, FirebaseUI, and Firebase Authentication.*

## Opening overview (30 sec)
- **Firebase** is Google's Backend-as-a-Service (BaaS) platform.
- It removes the need to build/maintain your own backend for common features (auth, database, storage, analytics).
- Integration steps: create a Firebase project → register your app → add the SDK → implement features.

## 1. Firebase Console & setup
- **Firebase Console** (`console.firebase.google.com`): create project, enable services, download config.
- Download `google-services.json` → place in `app/` directory.
- Add `google-services` Gradle plugin + Firebase BOM in `build.gradle`.
- Firebase links to a **Google Developer Account** (same Google account, Google Cloud project underneath).

## 2. Hash fingerprint (SHA-1 / SHA-256)
- Required for **Google Sign-In** and some Firebase services.
- Generate with: `./gradlew signingReport` or `keytool -list -v -keystore ~/.android/debug.keystore`.
- Register the fingerprint in Firebase Console → Project Settings → Your App → SHA certificate fingerprints.

## 3. Firebase services overview
| Service | Purpose |
|---|---|
| **Authentication** | User identity (email, Google, etc.) |
| **Realtime Database** | NoSQL JSON cloud database, real-time sync |
| **Cloud Firestore** | Scalable NoSQL document database |
| **Cloud Storage** | Binary file storage (images, video) |
| **ML Kit** | On-device and cloud ML (text recognition, object detection) |

## 4. Firebase Authentication flow
```
User enters credentials
    → FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
    → Task<AuthResult> (async)
        → on success: FirebaseAuth.currentUser != null
        → on failure: show error
```
- `FirebaseUser` object: `uid`, `email`, `displayName`, `photoUrl`.
- Persist across sessions automatically (token stored in SharedPreferences).
- **Sign out**: `FirebaseAuth.getInstance().signOut()`.

## 5. FirebaseUI
- Pre-built, customizable sign-in/sign-up screens.
- Supports multiple providers with minimal code:
```kotlin
val signInIntent = AuthUI.getInstance()
    .createSignInIntentBuilder()
    .setAvailableProviders(listOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
    ))
    .build()
signInLauncher.launch(signInIntent)
```
- Handles edge cases: email verification, password reset, account linking.

## Key diagrams to draw on whiteboard
1. Firebase setup flow: Console → `google-services.json` → Gradle → SDK calls.
2. Auth state machine: `signedOut → signIn → signedIn → signOut`.

## Likely follow-up topics
- Firebase Security Rules — role-based access control.
- How Firebase Auth UID is used to scope database/storage access.
- Anonymous authentication use case.
- Testing Firebase apps (emulator suite).

---

<!-- ============================================================ -->
<!-- QUESTION 6 -->
<!-- ============================================================ -->

# Q6 — Storage on Android

> *You are developing an Android app that must store data both locally on the device and remotely in the cloud. Explain how you would choose between local storage options such as SharedPreferences, internal/external storage, text files, SQLite, and Room, and cloud-based options such as Firebase Realtime Database, Firebase Security Rules, offline persistence, Firebase Cloud Storage, and Firebase Recycler Adapter. Discuss how local and cloud storage can work together to support data persistence, synchronization, and user experience.*

## Opening overview (30 sec)
- Android offers a spectrum of storage from ephemeral in-memory to persistent cloud-backed.
- Choosing the right option depends on: data size, structure, sharing needs, and offline requirements.

## 1. Local storage options

| Option | Best for | Survives app uninstall? |
|---|---|---|
| `SharedPreferences` | Key-value primitives (settings, flags) | No |
| Internal file storage | App-private files (no permission needed) | No |
| External file storage | Media/documents shared with other apps | Yes (user-visible) |
| SQLite (raw) | Structured relational data, full SQL control | No |
| **Room** | Structured data with type safety + LiveData/Flow | No |

### SharedPreferences
```kotlin
val prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
prefs.edit { putString("theme", "dark") }
val theme = prefs.getString("theme", "light")
```

### Room (recommended for structured data)
```kotlin
@Entity data class User(@PrimaryKey val uid: String, val name: String)

@Dao interface UserDao {
    @Query("SELECT * FROM user") fun getAll(): Flow<List<User>>
    @Insert suspend fun insert(user: User)
}

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() { abstract fun userDao(): UserDao }
```
- Repository pattern: DAO → Repository → ViewModel → UI.
- `Flow<List<T>>` for reactive UI updates without polling.

## 2. Cloud storage options

### Firebase Realtime Database
- NoSQL JSON tree, real-time push updates to all connected clients.
- Read/write: `database.getReference("users/$uid").setValue(user)`.
- Attach listeners: `addValueEventListener` / `addChildEventListener`.

### Firebase Cloud Storage
- For binary blobs: images, audio, video.
- Upload: `storageRef.child("images/$uid.jpg").putFile(uri)`.
- Download URL for use in `ImageView`/`Coil`.

### Firebase Security Rules
```json
{
  "rules": {
    "users": {
      "$uid": {
        ".read": "auth.uid === $uid",
        ".write": "auth.uid === $uid"
      }
    }
  }
}
```
- Enforced server-side — not a substitute for client-side validation.

### Offline persistence
- Realtime Database: `database.setPersistenceEnabled(true)` — caches data locally (SQLite under the hood), syncs when back online.
- Firestore: offline persistence enabled by default.

### FirebaseRecyclerAdapter
- `FirebaseRecyclerOptions` binds a database query directly to a `RecyclerView`:
```kotlin
val options = FirebaseRecyclerOptions.Builder<Item>()
    .setQuery(query, Item::class.java).build()
```
- Must call `adapter.startListening()` in `onStart()` and `stopListening()` in `onStop()`.

## 3. Combining local + cloud (sync strategy)
- **Local = cache**: Room stores downloaded data; app reads from Room, Room syncs with Firebase.
- **Optimistic UI**: update Room immediately, push to Firebase in background, roll back on failure.
- **Offline-first**: Room is source of truth; Firebase Realtime Database offline persistence fills the gap.

## Key diagrams to draw on whiteboard
1. Storage decision tree: small/keyed → SharedPreferences; structured → Room; binary → File/Cloud Storage; real-time sync → Realtime DB.
2. Layered architecture: UI → ViewModel → Repository → (Room ↔ Firebase).

## Likely follow-up topics
- Migrations in Room (`@Migration`).
- When to prefer Firestore over Realtime Database.
- `DataStore` (Jetpack) as modern replacement for `SharedPreferences`.
- Content providers — when are they needed?

---

<!-- ============================================================ -->
<!-- QUESTION 7 -->
<!-- ============================================================ -->

# Q7 — Android Location-Aware

> *An app needs to show the user's current position on a map and react to location changes. Explain how Android permissions, GPS, Location Manager, latitude/longitude, geocoding, broadcast receivers, Google Play Services, and Maps API keys are involved.*

## Opening overview (30 sec)
- Location is a sensitive resource — requires **runtime permissions**.
- Android provides two location APIs: the legacy `LocationManager` and the modern `FusedLocationProviderClient` (via Google Play Services).
- **Google Maps SDK** renders maps; **Geocoder** converts coordinates ↔ addresses.

## 1. Permissions
```xml
<!-- AndroidManifest.xml -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
<!-- Background location (requires extra justification on Play Store) -->
<uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION"/>
```
- **Runtime request**: `requestPermissions()` or `ActivityResultLauncher<Array<String>>`.
- Show **rationale** if `shouldShowRequestPermissionRationale()` returns true.
- Check permission before every location call: `ContextCompat.checkSelfPermission()`.

## 2. GPS and LocationManager (legacy)
- `getSystemService(LOCATION_SERVICE) as LocationManager`.
- Providers: `GPS_PROVIDER` (outdoors, accurate), `NETWORK_PROVIDER` (fast, less accurate), `PASSIVE_PROVIDER`.
- `requestLocationUpdates(provider, minTime, minDistance, listener)`.
- `LocationListener.onLocationChanged(location)` callback.

## 3. FusedLocationProviderClient (recommended)
```kotlin
val fusedClient = LocationServices.getFusedLocationProviderClient(this)

// One-shot last known location
fusedClient.lastLocation.addOnSuccessListener { location ->
    location?.let { updateUI(it.latitude, it.longitude) }
}

// Continuous updates
val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000).build()
fusedClient.requestLocationUpdates(request, callback, Looper.getMainLooper())
```
- Fused = intelligently combines GPS, Wi-Fi, cell towers for best accuracy/battery tradeoff.
- Requires **Google Play Services**.

## 4. Latitude / Longitude / Geocoding
- `Location.latitude` (–90 to +90), `Location.longitude` (–180 to +180).
- **Geocoding** (address → coordinates): `Geocoder(context).getFromLocationName(name, 1)`.
- **Reverse geocoding** (coordinates → address): `Geocoder(context).getFromLocation(lat, lng, 1)`.
- `Geocoder` makes a network call — run on background thread.

## 5. Broadcast Receivers for location
- System broadcasts connectivity changes, location provider status changes.
- Register dynamically in Activity/Service for `LocationManager.PROVIDERS_CHANGED_ACTION`.
- For continuous background location updates: use a **Foreground Service** (required for Android 10+).

## 6. Google Play Services & Maps API
- Add dependency: `com.google.android.gms:play-services-maps`.
- **Maps API key**: create in Google Cloud Console → Maps SDK for Android → restrict to app's SHA-1 + package name → add to `AndroidManifest.xml` as `<meta-data>`.
- `SupportMapFragment` or `MapView` in layout.
- `GoogleMap.addMarker(MarkerOptions().position(latLng).title("Here"))`.
- `googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))`.

## Key diagrams to draw on whiteboard
1. Permission flow: request → user grants/denies → handle result.
2. Location update pipeline: FusedClient → callback → ViewModel → Map.

## Likely follow-up topics
- Geofencing — trigger events on entering/leaving an area.
- Why foreground service is mandatory for background location on Android 10+.
- Maps Compose library (`GoogleMap` composable).
- Power implications of HIGH_ACCURACY vs BALANCED_POWER_ACCURACY.

---

<!-- ============================================================ -->
<!-- QUESTION 8 -->
<!-- ============================================================ -->

# Q8 — Android Multimedia and Machine Learning

> *You are building an Android app that uses the camera to analyze images and detect objects. Explain how CameraX, Media Player, OpenCV, image analysis, object detection, and Firebase ML Kit can be combined in an Android application.*

## Opening overview (30 sec)
- Modern Android multimedia uses **CameraX** (Jetpack camera library) for camera access.
- **MediaPlayer** handles audio/video playback.
- **OpenCV** provides traditional computer vision algorithms.
- **Firebase ML Kit** provides on-device ML: object detection, image labeling, text recognition.

## 1. CameraX
- Jetpack library: lifecycle-aware, consistent API across devices.
- Three use cases:
  - `Preview` — display camera feed in `PreviewView`.
  - `ImageCapture` — take still photos.
  - `ImageAnalysis` — process frames in real time (for ML, CV).
```kotlin
val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
cameraProviderFuture.addListener({
    val cameraProvider = cameraProviderFuture.get()
    val preview = Preview.Builder().build().also { it.setSurfaceProvider(previewView.surfaceProvider) }
    val imageAnalysis = ImageAnalysis.Builder().build().also {
        it.setAnalyzer(cameraExecutor) { imageProxy -> analyzeImage(imageProxy) }
    }
    cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageAnalysis)
}, ContextCompat.getMainExecutor(this))
```
- **Permissions**: `android.permission.CAMERA` (runtime).

## 2. MediaPlayer
- Plays audio/video from resources, files, or URIs.
```kotlin
val mediaPlayer = MediaPlayer.create(context, R.raw.myAudio)
mediaPlayer.start()
// Release when done
mediaPlayer.release()
```
- State machine: Idle → Initialized → Prepared → Started/Paused/Stopped → End.
- Run in a **Foreground Service** for music playback to survive app going to background.

## 3. OpenCV
- Traditional computer vision: color filtering, edge detection, contour finding, feature matching.
- Integration: add `opencv-android-sdk` AAR or Maven dependency.
- Frames from CameraX `ImageAnalysis` → convert `ImageProxy` to `Mat` → apply OpenCV operations.
- Useful for: preprocessing before ML inference, barcode scanning without ML, motion detection.

## 4. Firebase ML Kit — object detection & image labeling
```kotlin
// Object detection
val detector = ObjectDetection.getClient(
    ObjectDetectorOptions.Builder()
        .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
        .enableMultipleObjects()
        .enableClassification()
        .build()
)
val image = InputImage.fromMediaImage(mediaImage, rotationDegrees)
detector.process(image)
    .addOnSuccessListener { detectedObjects ->
        for (obj in detectedObjects) { /* obj.boundingBox, obj.labels */ }
    }
```
- **On-device** (no network required after model download) vs **cloud** (higher accuracy, needs network).
- Other ML Kit features: text recognition, face detection, barcode scanning, pose detection.

## 5. Combining them in one app
```
CameraX (ImageAnalysis)
    → ImageProxy (YUV frame)
        → [Optional] OpenCV preprocessing (Mat operations)
        → InputImage for ML Kit
            → ML Kit detector
                → Results → overlay on PreviewView
```

## Key diagrams to draw on whiteboard
1. CameraX use cases: Preview + ImageCapture + ImageAnalysis bound to lifecycle.
2. Frame processing pipeline: camera → OpenCV → ML Kit → UI overlay.

## Likely follow-up topics
- `ImageProxy` → `Bitmap` → `Mat` conversion steps.
- On-device vs cloud ML tradeoffs (latency, privacy, cost).
- `ExoPlayer` vs `MediaPlayer` for more complex playback.
- Permissions for camera + audio recording.

---

<!-- ============================================================ -->
<!-- QUESTION 9 -->
<!-- ============================================================ -->

# Q9 — Model Deployment on Edge Devices

> *You need to deploy a machine learning model directly on an Android device. Explain the motivation for Edge AI, the role of model optimization and post-training quantization, and compare deployment options such as TensorFlow Lite, Qualcomm SNPE, and Qualcomm AI Hub.*

## Opening overview (30 sec)
- **Edge AI** = running ML inference on the device, not in the cloud.
- Motivated by latency, privacy, offline availability, and cost.
- Models trained in full precision must be **optimized** for mobile constraints.
- Multiple deployment runtimes available: TFLite (cross-platform), SNPE (Qualcomm hardware).

## 1. Motivation for Edge AI
| Factor | Edge (on-device) | Cloud |
|---|---|---|
| Latency | ~ms (no network) | ~100 ms+ (network round-trip) |
| Privacy | Data stays on device | Data sent to server |
| Offline | Works without connectivity | Requires internet |
| Cost | One-time compute | Per-inference cost |
| Model size | Constrained by RAM | Unlimited |

## 2. Model optimization & post-training quantization
- **Full precision**: FP32 weights — large, slow on mobile.
- **Quantization**: reduce weight precision:
  - **Post-training quantization (PTQ)**: quantize after training, no retraining needed; minimal accuracy loss.
    - Dynamic range (default TFLite): weights → INT8, activations → FP32 at runtime.
    - Full integer quantization: weights + activations → INT8; requires calibration dataset.
    - Float16 quantization: 2× size reduction, GPU-friendly.
  - **Quantization-aware training (QAT)**: simulate quantization during training; better accuracy than PTQ.
- **Pruning**: remove unimportant weights.
- **Knowledge distillation**: train a smaller "student" model to mimic a larger "teacher".

## 3. TensorFlow Lite
- Google's mobile/edge inference framework.
- Convert: `TFLiteConverter.from_saved_model(...)` → `.tflite` file.
- Android integration:
```kotlin
val interpreter = Interpreter(loadModelFile("model.tflite"))
interpreter.run(inputBuffer, outputBuffer)
```
- **Delegates** for hardware acceleration:
  - `GpuDelegate` — GPU inference.
  - `NnApiDelegate` — Android Neural Networks API (vendor-specific hardware).
  - `HexagonDelegate` — Qualcomm DSP (via SNPE interop).
- Supports quantized models; lower latency and memory footprint.

## 4. Qualcomm SNPE (Snapdragon Neural Processing Engine)
- Qualcomm's proprietary inference SDK for Snapdragon-equipped devices.
- Supports execution on: CPU, GPU, DSP (Hexagon), NPU.
- Workflow: convert model (`snpe-tensorflow-to-dlc` / `snpe-onnx-to-dlc`) → deploy `.dlc` file.
- Integrates as a JNI native library (`jniLibs/arm64-v8a/libSNPE.so`).
- Maximum performance on Qualcomm hardware; limited to Snapdragon devices.

## 5. Qualcomm AI Hub
- Cloud-based model optimization and profiling service.
- Upload a model → select target device → AI Hub compiles and optimizes → download optimized model.
- Provides real-device profiling benchmarks.
- Abstracts away manual SNPE conversion steps.

## 6. Comparison table
| | TFLite | SNPE | Qualcomm AI Hub |
|---|---|---|---|
| Hardware | Cross-platform | Qualcomm only | Qualcomm only |
| Acceleration | GPU, NNAPI, DSP | CPU, GPU, DSP, NPU | Same as SNPE (via SNPE) |
| Model format | `.tflite` | `.dlc` | Optimized `.dlc` / `.tflite` |
| Integration | Kotlin/Java API | JNI (native) | JNI (native) |
| Ease of use | High | Medium | High (cloud tooling) |

## Key diagrams to draw on whiteboard
1. Edge AI pipeline: train (cloud) → optimize/quantize → deploy `.tflite`/`.dlc` → on-device inference.
2. Quantization: FP32 weights → INT8; size/speed vs accuracy tradeoff curve.

## Likely follow-up topics
- When to choose cloud inference vs edge inference (real-time requirement, model size, privacy).
- Latency benchmarking on Snapdragon devices.
- Model versioning and updates (OTA model delivery).
- Hilt dependency injection for managing the interpreter lifecycle.

---

<!-- ============================================================ -->
<!-- QUESTION 10 -->
<!-- ============================================================ -->

# Q10 — Android Sensors

> *You are designing an Android app that reacts to the user's movement and physical environment. Explain how the Android Sensor Framework works, including sensor properties, motion sensors, position sensors, environmental sensors, Bluetooth, and beacons.*

## Opening overview (30 sec)
- Android provides a unified **Sensor Framework** to access hardware sensors.
- Sensors are categorized by what they measure: motion, position, environment.
- **Bluetooth** and **beacons** extend the app's awareness of the physical environment beyond onboard sensors.

## 1. Sensor Framework
- Key classes: `SensorManager`, `Sensor`, `SensorEvent`, `SensorEventListener`.
```kotlin
val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

// Register (use SENSOR_DELAY_NORMAL / UI / GAME / FASTEST)
sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)

// Callback
override fun onSensorChanged(event: SensorEvent) {
    val x = event.values[0]; val y = event.values[1]; val z = event.values[2]
}
override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
```
- **Always unregister** in `onPause()` / `onStop()` to save battery.
- `SensorEvent.values[]` — interpretation depends on sensor type.
- `Sensor.getResolution()`, `getMaximumRange()`, `getPower()` — sensor properties.

## 2. Motion sensors
| Sensor | `Sensor.TYPE_*` | Measures |
|---|---|---|
| Accelerometer | `ACCELEROMETER` | Linear acceleration incl. gravity (m/s²) |
| Gyroscope | `GYROSCOPE` | Rate of rotation around axes (rad/s) |
| Linear Acceleration | `LINEAR_ACCELERATION` | Acceleration without gravity |
| Gravity | `GRAVITY` | Gravity vector |
| Step Counter | `STEP_COUNTER` | Cumulative steps since reboot |
| Step Detector | `STEP_DETECTOR` | One event per step |

## 3. Position sensors
| Sensor | `Sensor.TYPE_*` | Measures |
|---|---|---|
| Magnetometer | `MAGNETIC_FIELD` | Ambient magnetic field (µT) |
| Rotation Vector | `ROTATION_VECTOR` | Device orientation (quaternion) |
| Game Rotation Vector | `GAME_ROTATION_VECTOR` | Orientation without magnetometer |
| Proximity | `PROXIMITY` | Distance to object (cm) — used to turn off screen during calls |

- Derive compass heading: combine accelerometer + magnetometer → `SensorManager.getRotationMatrix()` → `getOrientation()`.

## 4. Environmental sensors
| Sensor | `Sensor.TYPE_*` | Measures |
|---|---|---|
| Ambient Light | `LIGHT` | Illuminance (lux) |
| Pressure | `PRESSURE` | Atmospheric pressure (hPa) |
| Temperature | `AMBIENT_TEMPERATURE` | Air temperature (°C) |
| Humidity | `RELATIVE_HUMIDITY` | Relative humidity (%) |

## 5. Bluetooth
- **Bluetooth Classic** (BR/EDR): for streaming audio, serial data (e.g., HC-05 modules).
```kotlin
val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
// Discover devices, pair, create socket
val socket = device.createRfcommSocketToServiceRecord(MY_UUID)
socket.connect()
// Read/write via socket.inputStream / socket.outputStream
```
- Permissions (Android 12+): `BLUETOOTH_SCAN`, `BLUETOOTH_CONNECT`, `BLUETOOTH_ADVERTISE`.
- Multi-threaded: `AcceptThread` (server), `ConnectThread` (client), `ConnectedThread` (I/O).
- Use `Handler` to send data back to UI thread from I/O threads.

## 6. Bluetooth Low Energy (BLE) & Beacons
- **BLE**: low-power protocol for sensors, wearables, beacons.
- **Beacons** (iBeacon / Eddystone): BLE devices that broadcast a UUID + major/minor.
- Android scans for beacons using `BluetoothLeScanner`.
- Use cases: indoor positioning, proximity marketing, asset tracking.
- `ScanCallback.onScanResult()` → parse `ScanRecord` → extract beacon UUID + RSSI → estimate distance.

## Key diagrams to draw on whiteboard
1. Sensor Framework: app → `SensorManager` → `Sensor` → `SensorEvent` → `onSensorChanged()`.
2. Bluetooth Classic threading: AcceptThread / ConnectThread → ConnectedThread → Handler → UI.

## Likely follow-up topics
- Sensor fusion: combining multiple sensors for better accuracy (e.g., Kalman filter on accelerometer + gyroscope).
- Activity recognition API (high-level, no raw sensor math needed).
- Power consumption implications of different sensor delays.
- BLE GATT profile for custom services.

---

<!-- ============================================================ -->
<!-- QUESTION 11 -->
<!-- ============================================================ -->

# Q11 — Shrinking, Obfuscating, and Publishing an Android App

> *Before publishing an Android app, you must prepare it for release. Explain the process of creating an Android App Bundle, signing the app, protecting sensitive information, applying ProGuard, considering reverse engineering risks, and publishing on Google Play.*

## Opening overview (30 sec)
- Publishing requires: building a release artifact, **signing** it cryptographically, **shrinking/obfuscating** code, and submitting to the **Google Play Console**.
- These steps improve security, reduce APK size, and protect intellectual property.

## 1. Android App Bundle (AAB)
- `.aab` is the modern distribution format (required by Google Play since Aug 2021).
- Contains **all** resources and code; Google Play generates optimized APKs per device configuration.
- **Benefits**: smaller download size (~15-20% smaller than universal APK), no need to manage multiple APKs.
- Build in Android Studio: `Build → Generate Signed Bundle/APK → Android App Bundle`.
- `gradlew bundleRelease` from command line.

## 2. Signing the app
- Every release APK/AAB must be signed with a **keystore** (private key).
- Generate keystore: `keytool -genkey -v -keystore mykey.jks -alias myalias -keyalg RSA -keysize 2048 -validity 10000`.
- Configure in `build.gradle`:
```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file("mykey.jks")
            storePassword = "..."
            keyAlias = "myalias"
            keyPassword = "..."
        }
    }
    buildTypes {
        release { signingConfig = signingConfigs.getByName("release") }
    }
}
```
- **Google Play App Signing**: Google manages the upload key; use an upload key to submit, Play re-signs with the app signing key.

## 3. Protecting sensitive information
- **Never** commit API keys, keystore passwords, or secrets to source control.
- Use `local.properties` (gitignored) or environment variables, accessed via `BuildConfig` fields:
```kotlin
// build.gradle
buildConfigField("String", "API_KEY", "\"${project.property("API_KEY")}\"")
```
- Use `dotenv-kotlin` or Android `EncryptedSharedPreferences` for runtime secrets.
- **ProGuard/R8** can strip log statements: `com.example.** { ... }`.

## 4. Code shrinking and obfuscation with R8 / ProGuard
- Enable in `build.gradle`:
```kotlin
buildTypes {
    release {
        isMinifyEnabled = true      // shrink + obfuscate
        isShrinkResources = true    // remove unused resources
        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
}
```
- **Shrinking (tree-shaking)**: removes unused classes, methods, fields.
- **Obfuscation**: renames classes/methods to short meaningless names (`a`, `b`, `c`).
- **Optimization**: inlines, removes dead code.
- **Keep rules** (`proguard-rules.pro`) prevent stripping of:
  - Reflection-used classes.
  - Data classes used with Gson/Kotlin serialization.
  - Firebase model classes.
  - `@Keep` annotation as alternative.

## 5. Reverse engineering risks
- APKs are ZIP files; Java bytecode can be decompiled with tools like `jadx`, `apktool`.
- Obfuscation raises the bar but does not make reversal impossible.
- Additional protections:
  - **Certificate pinning**: prevent MITM attacks.
  - **Root detection**: detect rooted devices.
  - **Integrity checks**: Play Integrity API (replaces SafetyNet).
  - **Code splitting**: put sensitive logic on the server.

## 6. Publishing on Google Play
1. **Google Play Console** (`play.google.com/console`): create developer account ($25 one-time fee).
2. Create app → set target audience, content rating.
3. Upload `.aab` to a **track** (Internal → Closed Testing → Open Testing → Production).
4. Fill in **store listing**: title, description, screenshots, feature graphic, privacy policy URL.
5. Complete **app content** declarations (ads, data safety form, etc.).
6. Submit for **review** (typically hours to days for first submission).
7. **Release management**: staged rollout (e.g., 10% → 50% → 100%).

## Key diagrams to draw on whiteboard
1. Release pipeline: source code → R8 (shrink/obfuscate) → sign → `.aab` → Google Play → device APK.
2. ProGuard transformation: readable class names → obfuscated names.

## Likely follow-up topics
- Difference between debug and release build types.
- How to read a crash stack trace after obfuscation (using the `mapping.txt` file).
- Play App Signing vs self-managed signing.
- `BuildConfig.DEBUG` flag for conditional logging.

---

*End of exam preparation guide. Good luck!*
