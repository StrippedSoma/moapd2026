

## 339
## 19
Audio Playback with SoundPool
Now that you are ready to go with your assets, it is time to actually play all these .wav files. Android’s
audio APIs are fairly low level for the most part, but there is a tool practically tailor-made for the app
you are writing:
SoundPool.
SoundPool
can load a large set of sounds into memory and control the maximum number of sounds
that are playing back at any one time. So if your app’s user gets a bit too excited and mashes all the
buttons at the same time, it will not break your app or overtax your phone.
Ready? Time to get started.
Creating a SoundPool
The first step is to create a SoundPool object.
Listing 19.1  Creating a
SoundPool (BeatBox.java)
public class BeatBox {
private static final String TAG = "BeatBox";
private static final String SOUNDS_FOLDER = "sample_sounds";

private static final int MAX_SOUNDS = 5;

private AssetManager mAssets;
private List<Sound> mSounds;

private SoundPool mSoundPool;
public BeatBox(Context context) {
mAssets = context.getAssets();
// This old constructor is deprecated, but we need it for

// compatibility.
mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
loadSounds();
## }

## ...
## }
Lollipop introduced a new way of creating a
SoundPool using a SoundPool.Builder. However, since
SoundPool.Builder is not available on your minimum-supported API 16, you are using the older
SoundPool(int, int, int) constructor instead.

Chapter 19  Audio Playback with SoundPool
## 340
The first parameter specifies how many sounds can play at any given time. Here, you pass in 5. If five
sounds are playing and you try to play a sixth one, the SoundPool will stop playing the oldest one.
The second parameter determines the kind of audio stream your SoundPool will play on. Android has
a variety of different audio streams, each of which has its own independent volume settings. This is
why turning down the music does not also turn down your alarms. Check out the documentation for the
AUDIO_* constants in AudioManager to see the other options. STREAM_MUSIC will put you on the same
volume setting as music and games on the device.
And the last parameter? It specifies the quality for the sample rate converter. The documentation says it
is ignored, so you just pass in 0.
## Loading Sounds
The next thing to do with your
SoundPool is to load it up with sounds. The main benefit of using a
SoundPool over some other methods of playing audio is that SoundPool is responsive: when you tell it
to play a sound, it will play the sound immediately, with no lag.
The trade-off for that is that you have to load sounds into your SoundPool before you play them. Each
sound you load will get its own integer ID. So go ahead and add a mSoundId field to Sound and a
generated getter and setter to keep track of this.
Listing 19.2  Adding sound ID field (Sound.java)
public class Sound {
private String mAssetPath;

private String mName;
private Integer mSoundId;
## ...
public String getName() {
return mName;
## }
public Integer getSoundId() {
return mSoundId;
## }
public void setSoundId(Integer soundId) {

mSoundId = soundId;
## }
## }
By making mSoundId an Integer instead of an int, you make it possible to represent the state where a
Sound has no value for mSoundId by assigning it a null value.
Now to load your sounds in. Add a load(Sound) method to BeatBox to load a Sound into your
SoundPool.

## Playing Sounds
## 341
Listing 19.3  Loading sounds into
SoundPool (BeatBox.java)

private void loadSounds() {

## ...
## }
private void load(Sound sound) throws IOException {
AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());

int soundId = mSoundPool.load(afd, 1);
sound.setSoundId(soundId);
## }
## }
Calling mSoundPool.load(AssetFileDescriptor, int) loads a file into your SoundPool for later
playback. To keep track of the sound and play it back again (or unload it), mSoundPool.load(...)
returns an int ID, which you stash in the mSoundId field you just defined. And since calling
openFd(String) throws IOException, load(Sound) throws IOException, too.
Now load up all your sounds by calling load(Sound) inside BeatBox.loadSounds().
Listing 19.4  Loading up all your sounds (BeatBox.java)
private void loadSounds() {
## ...
mSounds = new ArrayList<Sound>();

for (String filename : soundNames) {
try {
String assetPath = SOUNDS_FOLDER + "/" + filename;

Sound sound = new Sound(assetPath);
load(sound);

mSounds.add(sound);
} catch (IOException ioe) {
Log.e(TAG, "Could not load sound " + filename, ioe);

## }
## }
## }
Run BeatBox to make sure that all the sounds loaded correctly. If they did not, you will see red
exception logs in LogCat.
## Playing Sounds
One last step: playing the sounds back. Add the play(Sound) method to BeatBox.

Chapter 19  Audio Playback with SoundPool
## 342
Listing 19.5  Playing sounds back (
BeatBox.java)

mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);

loadSounds();
## }
public void play(Sound sound) {
Integer soundId = sound.getSoundId();

if (soundId == null) {
return;
## }
mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
## }
public List<Sound> getSounds() {

return mSounds;
## }
Before playing your soundId, you check to make sure it is not null. This might happen if the Sound
failed to load.
Once you are sure you have a non-null value, play the sound by calling SoundPool.play(int,
float, float, int, int, float). Those parameters are, respectively: the sound ID, volume on the
left, volume on the right, priority (which is ignored), whether the audio should loop, and playback rate.
For full volume and normal playback rate, you pass in 1.0. Passing in 0 for the looping value says “do
not loop.” (You can pass in -1 if you want it to loop forever. We speculate that this would be incredibly
annoying.)
With that method written, you can play the sound each time one of the buttons is pressed.
Listing 19.6  Playing sound on button press (BeatBoxFragment.java)
private class SoundHolder extends RecyclerView.ViewHolder

implements View.OnClickListener {
private Button mButton;

private Sound mSound;

public SoundHolder(LayoutInflater inflater, ViewGroup container) {

super(inflater.inflate(R.layout.list_item_sound, parent, false));

mButton = (Button)itemView.findViewById(R.id.button);
mButton.setOnClickListener(this);
## }
public void bindSound(Sound sound) {

mSound = sound;
mButton.setText(mSound.getName());
## }
@Override

public void onClick(View v) {
mBeatBox.play(mSound);
## }
## }
Press a button, as shown in
Figure 19.1, and you should hear a sound played.

## Unloading Sounds
## 343
Figure 19.1  A functioning sound bank
## Unloading Sounds
The app works, but you still have some cleanup to do. To be a good citizen, you should clean up
your SoundPool by calling SoundPool.release() when you are done with it. Add a matching
BeatBox.release() method.
Listing 19.7  Releasing your SoundPool
(BeatBox.java)
public class BeatBox {
## ...
public void play(Sound sound) {
## ...

## }
public void release() {
mSoundPool.release();
## }

## ...
## }
Then, clean it up when you are done with it in BeatBoxFragment.

Chapter 19  Audio Playback with SoundPool
## 344
Listing 19.8  Releasing your BeatBox
(BeatBoxFragment.java)
public class BeatBoxFragment extends Fragment {
## ...
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
Bundle savedInstanceState) {

## ...
## }
@Override
public void onDestroy() {

super.onDestroy();

mBeatBox.release();
## }


## ...
## }
Go ahead and run your app to make sure it works correctly with your new release() method.
Rotation and Object Continuity
Now you are a good citizen, which is nice. Unfortunately, your app no longer handles rotation
correctly. Try playing the 69_ohm-loko sound and rotating the screen: the sound will stop abruptly. (If
it does not, make sure you have built and run the app with your recent onDestroy() implementation.)
Here is the problem: on rotation, the BeatBoxActivity
is destroyed. As this happens,
the FragmentManager destroys your BeatBoxFragment, too. In doing that, it calls
BeatBoxFragment’s waning lifecycle methods: onPause(), onStop(), and onDestroy(). In
BeatBoxFragment.onDestroy(), you call BeatBox.release(), which releases the SoundPool and
stops sound playback.
You have seen how Activity and Fragment instances die on rotation before, and you have solved these
issues using onSaveInstanceState(Bundle). However, that solution will not work this time, because it
relies on saving data out and restoring it using Parcelable data inside a Bundle.
Parcelable, like Serializable, is an API for saving an object out to a stream of bytes. Objects may
elect to implement the Parcelable interface if they are what we will call “stashable” here. Objects
are stashed in Java by putting them in a Bundle, or by marking them Serializable so that they can
be serialized, or by implementing the Parcelable interface. Whichever way you do it, the same idea
applies: you should not be using any of these tools unless your object is stashable.
To illustrate what we mean by “stashable,” imagine watching a television program with a friend. You
could write down the channel you are watching, the volume level, and even the TV you are watching
the program on. Once you do that, even if a fire alarm goes off and the power goes out, you can look at
what you wrote down and get back to watching TV just like you were before.
So the configuration of your TV watching time is stashable. The time you spend watching TV is not,
though: once the fire alarm goes off and the power goes out, that session is gone. You can return and
create a new session just like it, but you will experience an interruption no matter what you do. So the
session is not
stashable.

Retaining a fragment
## 345
Some parts of BeatBox are stashable: everything contained in Sound is stashable, for example.
SoundPool is more like your TV watching session, though. Yes, you can create a new SoundPool that
has all the same sounds as an older one. You can even start playing again right where you left off. You
will always experience a brief interruption, though, no matter what you do. That means that SoundPool
is not stashable.
Non-stashability tends to be contagious. If a non-stashable object is critical to another object’s mission,
that other object is probably not stashable, either. Here, BeatBox has the same mission as SoundPool: to
play back sounds. Therefore, ipso facto, Q.E.D.: BeatBox is not stashable. (Sorry.)
The regular savedInstanceState mechanism preserves stashable data for you, but BeatBox is not
stashable. You need your BeatBox instance to be continuously available as your Activity is created
and destroyed.
What to do?
Retaining a fragment
Fortunately, fragments have a feature that you can use to keep the BeatBox
instance alive across a
configuration change: retainInstance. Override BeatBoxFragment.onCreate(...) and set a property
of the fragment.
Listing 19.9  Calling setRetainInstance(true) (BeatBoxFragment.java)
## ...
public static BeatBoxFragment newInstance() {

return new BeatBoxFragment();
## }
@Override
public void onCreate(Bundle savedInstanceState) {

super.onCreate(savedInstanceState);
setRetainInstance(true);
mBeatBox = new BeatBox(getActivity());

## }
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
Bundle savedInstanceState) {

## ...
By default, the retainInstance property of a fragment is false. This means it is not retained,
and it is destroyed and re-created on rotation along with the activity that hosts it. Calling
setRetainInstance(true) retains the fragment. When a fragment is retained, the fragment is not
destroyed with the activity. Instead, it is preserved and passed along intact to the new activity.
When you retain a fragment, you can count on all of its instance variables (like mBeatBox) to keep the
same values. When you reach for them, they are simply there.
Run BeatBox again. Play the 69_ohm-loko sound, rotate the device, and confirm that playback
continues unimpeded.

Chapter 19  Audio Playback with SoundPool
## 346
Rotation and retained fragments
Let’s take a closer look at how retained fragments work. Retained fragments take advantage of the fact
that a fragment’s view can be destroyed and re-created without having to destroy the fragment itself.
During a configuration change, the FragmentManager first destroys the views of the fragments in
its list. Fragment views always get destroyed and re-created on a configuration change for the same
reasons that activity views are destroyed and re-created: If you have a new configuration, then you
might need new resources. Just in case better matching resources are now available, you rebuild the
view from scratch.
Next, the FragmentManager checks the retainInstance property of each fragment. If it is false,
which it is by default, then the
FragmentManager destroys the fragment instance. The fragment
and its view will be re-created by the new FragmentManager of the new activity “on the other side”
(Figure 19.2).
Figure 19.2  Default rotation with a UI fragment
On the other hand, if retainInstance is true, then the fragment’s view is destroyed but the fragment
itself is not. When the new activity is created, the new FragmentManager finds the retained fragment
and re-creates its view (Figure 19.3).

Rotation and retained fragments
## 347
Figure 19.3  Rotation with a retained UI fragment
A retained fragment is not destroyed, but it is detached from the dying activity. This puts the fragment
in a retained state. The fragment still exists, but it is not hosted by any activity (Figure 19.4).

Chapter 19  Audio Playback with SoundPool
## 348
Figure 19.4  Fragment lifecycle
The retained state is only entered into when two conditions are met:
•setRetainInstance(true) has been called on the fragment.
- The hosting activity is being destroyed for a configuration change (typically rotation).
A fragment is only in the retained state for an extremely brief interval – the time between being
detached from the old activity and being reattached to the new activity that is immediately created.
For the More Curious: Whether to Retain
Retained fragments: pretty nifty, right? Yes! They are indeed nifty. They appear to solve all the
problems that pop up from activities and fragments being destroyed on rotation. When the device
configuration changes, you get the most appropriate resources by creating a brand-new view, and you
have an easy way to retain data and objects.
You may wonder why you would not retain every fragment or why fragments are not retained by
default. In general, we do not recommend using this mechanism unless you absolutely need to, for a
couple of reasons.

For the More Curious: More on Rotation Handling
## 349
The first reason is simply that retained fragments are more complicated than unretained fragments.
When something goes wrong with them, it takes longer to get to the bottom of what went wrong.
Programs are always more complicated than you want them to be, so if you can get by without this
complication, you are better off.
The other reason is that fragments that handle rotation using saved instance state handle all lifecycle
situations, but retained fragments only handle the case when an activity is destroyed for a configuration
change. If your activity is destroyed because the OS needs to reclaim memory, then all your retained
fragments are destroyed, too, which may mean that you lose some data.
For the More Curious: More on Rotation Handling
## The
onSaveInstanceState(Bundle) is another tool you have used to handle rotation. In fact,
if your app does not have any problems with rotation, it is because the default behavior of
onSaveInstanceState(...) is working.
Your CriminalIntent app is a good example. CrimeFragment is not retained, but if you make changes
to the crime’s title or toggle the checkbox, the new states of these View objects are automatically saved
out and restored after rotation. This is what onSaveInstanceState(...) was designed to do – save out
and restore the UI state of your app.
The major difference between overriding Fragment.onSaveInstanceState(...) and retaining
the fragment is how long the preserved data lasts. If it only needs to last long enough to survive
configuration changes, then retaining the fragment is much less work. This is especially true when
preserving an object; you do not have to worry about whether the object implements Serializable.
However, if you need the data to last longer, retaining the fragment is no help. If an activity is
destroyed to reclaim memory after the user has been away for a while, then any retained fragments are
destroyed just like their unretained brethren.
To make this difference clearer, think back to your GeoQuiz app. The rotation problem you faced was
that the question index was being reset to zero on rotation. No matter what question the user was on,
rotating the device sent them back to the first question. You saved out the index data and then read it
back in to ensure the user would see the right question.
GeoQuiz did not use fragments, but imagine a redesigned GeoQuiz with a
QuizFragment hosted by
QuizActivity. Should you override Fragment.onSaveInstanceState(...) to save out the index or
retain QuizFragment and keep the variable alive?
Figure 19.5 shows the three different lifetimes you have to work with: the life of the activity object
(and its unretained fragments), the life of a retained fragment, and the life of the activity record.

Chapter 19  Audio Playback with SoundPool
## 350
Figure 19.5  Three lifetimes
The lifetime of the activity object is too short. That is the source of the rotation problem. The index
definitely needs to outlive the activity object.
If you retain
QuizFragment, then the index will exist for the lifetime of this retained fragment. When
GeoQuiz has only five questions, retaining QuizFragment is the easier choice and requires less code.
You would initialize the index as a member variable and then call setRetainInstance(true) in
QuizFragment.onCreate(...).
Listing 19.10  Retaining of hypothetical
QuizFragment
public class QuizFragment extends Fragment {
## ...

private int mCurrentIndex = 0;
## ...

@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setRetainInstance(true);

## }


## ...
## }

For the More Curious: More on Rotation Handling
## 351
By tying the index to the lifetime of the retained fragment, it survives the destruction of the
activity object and solves the problem of resetting the index on rotation. However, as you can see
in Figure 19.5, retaining QuizFragment does not preserve the value of the index across a process
shutdown, which may happen if the user leaves the app for a while and the activity and the retained
fragment are destroyed to reclaim memory.
For only five questions, having users start over may be an acceptable choice. But what if GeoQuiz had
100 questions? Users would rightly be irritated at returning to the app and having to start again at the
first question. You need the state of the index to survive for the lifetime of the activity record. To make
this happen, you would save out the index in onSaveInstanceState(...). Then, if users left the app for
a while, they would always be able to pick up where they left off.
Therefore, if you have something in your activity or fragment that should last a long time, then you
should tie it to the activity record’s lifetime by overriding onSaveInstanceState(Bundle) to save its
state so that you can restore it later.