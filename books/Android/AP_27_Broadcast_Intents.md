

## 491
## 27
## Broadcast Intents
In this chapter you will polish PhotoGallery in two big ways. First, you will make the app poll for
new search results and notify the user if new results are found, even if the user has not opened the
application since booting the device. Second, you will ensure notifications about new results are posted
only if the user is not interacting with the app. (It is annoying and redundant to both get a notification
and see the results update in the screen when you are actively viewing an app.)
In making these updates, you will learn how to listen for
broadcast intents from the system and how to
handle such intents using a broadcast receiver. You will also dynamically send and receive broadcast
intents within your app at runtime. Finally, you will use ordered broadcasts to determine if your
application is currently running in the foreground or not.
Regular Intents vs. Broadcast Intents
Things are happening all the time on an Android device. WiFi is going in and out of range, packages
are getting installed, phone calls and text messages are coming and going.
When many components on the system need to know that some event has occurred, Android uses a
broadcast intent to tell everyone about it. Broadcast intents work similarly to the intents you already
know and love, except that they can be received by multiple components, called broadcast receivers, at
the same time (Figure 27.1).
Figure 27.1  Regular intents vs. broadcast intents

## Chapter 27  Broadcast Intents
## 492
Activities and services should respond to implicit intents whenever they are used as part of a public
API. In other circumstances, explicit intents are almost always sufficient. On the other hand, the entire
reason broadcast intents exist is to send information to more than one listener. So while broadcast
receivers can respond to explicit intents, they are rarely, if ever, used this way, because explicit intents
have only have one receiver.
Receiving a System Broadcast: Waking Up on Boot
PhotoGallery’s background alarm works, but it is not perfect. If the user reboots the device, the alarm
will be forgotten.
Apps that perform an ongoing process for the user usually need to wake themselves up after the
device is booted. You can detect when boot is completed by listening for a broadcast intent with the
## BOOT_COMPLETED
action. The system sends out a BOOT_COMPLETED broadcast intent whenever the device
is turned on. You can listen for it by creating and registering a standalone broadcast receiver that filters
for the appropriate action.
Creating and registering a standalone receiver
A standalone receiver is a broadcast receiver that is declared in the manifest. Such a receiver can be
activated even if your app process is dead. (Later you will learn about dynamic receivers, which can
instead be tied to the lifecycle of a visible app component, like a fragment or activity.)
Just like services and activities, broadcast receivers must be registered with the system in order to do
anything useful. If the receiver is not registered with the system, the system will not send any intents its
way and in turn the receiver’s onReceive(...) will not get executed as desired.
But before you can register your broadcast receiver, you have to write it. Create a new Java class called
StartupReceiver that is a subclass of android.content.BroadcastReceiver.
Listing 27.1  Your first broadcast receiver (
StartupReceiver.java)
public class StartupReceiver
extends BroadcastReceiver{
private static final String TAG = "StartupReceiver";
@Override
public void onReceive(Context context, Intent intent) {

Log.i(TAG, "Received broadcast intent: " + intent.getAction());
## }
## }
A broadcast receiver is a component that receives intents, just like a service or an activity. When an
intent is issued to StartupReceiver, its onReceive(...) method will be called.
Next, open AndroidManifest.xml and hook up StartupReceiver as a standalone receiver:

Creating and registering a standalone receiver
## 493
Listing 27.2  Adding your receiver to the manifest (AndroidManifest.xml
## )
## <manifest ...>
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
## <application
## ...>
## <activity

android:name=".PhotoGalleryActivity"
android:label="@string/app_name">
## ...
## </activity>
<service android:name=".PollService"/>
<receiver android:name=".StartupReceiver">
## <intent-filter>

<action android:name="android.intent.action.BOOT_COMPLETED"/>
## </intent-filter>
## </receiver>

## </application>
## </manifest>
Registering a standalone receiver to respond to an implicit intent works just like registering an activity
to do the same. You use the receiver tag with appropriate intent-filters within. StartupReceiver
will be listening for the BOOT_COMPLETED action. This action also requires a permission, so you include
an appropriate uses-permission tag as well.
With your broadcast receiver declared in your manifest, it will wake up any time a matching broadcast
intent is sent – even if your app is not currently running. Upon waking up, the ephemeral broadcast
receiver’s onReceive(Context, Intent) method will be run, and then it will die, as shown in
## Figure 27.2.
Figure 27.2  Receiving BOOT_COMPLETED

## Chapter 27  Broadcast Intents
## 494
Time to verify that StartupReceiver’s onReceive(...) is executed when the device boots up. First, run
PhotoGallery to install the most recent version on your device.
Next, shut down your device. If you are using a physical device, power it all the way off. If you are
using an emulator, the easiest way to shut it down is to quit out of the emulator by closing the emulator
window.
Turn the device back on. If you are using a physical device, use the power button. If you are using
an emulator, either rerun your application or start the device using
AVD Manager. Make sure you are
using the same emulator image you just shut down.
Now, open the Android Device Monitor by selecting Tools → Android → Android Device Monitor.
(You may hear the Android Device Monitor called “Dalvik Debug Monitor Server” or “DDMS.”
Prior to KitKat (4.4), Dalvik was the only runtime system available on Android. Starting with KitKat,
Android Runtime (ART) was included as an alternative; as of Lollipop (5.0), ART is the only runtime
used. Android Device Monitor has been renamed accordingly, but the old name still lingers.)
Click on your device in Android Device Monitor’s Devices tab. (If you do not see the device listed, try
unplugging and replugging in your USB device, or restarting the emulator.)
Search the LogCat results within the Android Device Monitor window for your log statement
## (
## Figure 27.3).
Figure 27.3  Searching LogCat output
You should see a LogCat statement showing that your receiver ran. However, if you check your device
in the Devices tab, you will probably not see a process for PhotoGallery. Your process came to life just
long enough to run your broadcast receiver, and then it died again.

Using receivers
## 495
(Testing that the receiver executed can be unreliable when you are using LogCat output, especially if
you are using an emulator. If you do not see the log statement the first time through the instructions
above, try a few more times. Worst case, continue through the rest of the exercise. Once you get to
the part where you hook up notifications you will have a more reliable way to check on whether the
receiver is working.)
Using receivers
The fact that broadcast receivers live such short lives restricts the things you can do with them. You
cannot use any asynchronous APIs, for example, or register any listeners, because your receiver
will not be alive any longer than the length of onReceive(Context, Intent). Also, because
onReceive(Context, Intent) runs on your main thread, you cannot do any heavy lifting inside it.
That means no networking or heavy work with permanent storage.
This does not make receivers useless, though. They are invaluable for all kinds of little plumbing
code, such as starting an activity or service (so long as you do not expect a result back) or resetting a
recurring alarm when the system finishes rebooting (as you will do in this exercise).
Your receiver will need to know whether the alarm should be on or off. Add a preference constant and
convenience methods to
QueryPreferences to store this information in shared preferences.
Listing 27.3  Adding alarm status preference (QueryPreferences.java
## )
public class QueryPreferences {
private static final String PREF_SEARCH_QUERY = "searchQuery";
private static final String PREF_LAST_RESULT_ID = "lastResultId";
private static final String PREF_IS_ALARM_ON = "isAlarmOn";
## ...
public static void setLastResultId(Context context, String lastResultId) {
## ...

## }
public static boolean isAlarmOn(Context context) {

return PreferenceManager.getDefaultSharedPreferences(context)
.getBoolean(PREF_IS_ALARM_ON, false);
## }

public static void setAlarmOn(Context context, boolean isOn) {
PreferenceManager.getDefaultSharedPreferences(context)
## .edit()
.putBoolean(PREF_IS_ALARM_ON, isOn)

## .apply();
## }
## }
Next, update PollService.setServiceAlarm(...) to write to shared preferences when the alarm is set.

## Chapter 27  Broadcast Intents
## 496
Listing 27.4  Writing alarm status preference when alarm is set
## (
PollService.java)
public class PollService extends IntentService {
## ...

public static void setServiceAlarm(Context context, boolean isOn) {
## ...

if (isOn) {
alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,

SystemClock.elapsedRealtime(), POLL_INTERVAL, pi);
} else {
alarmManager.cancel(pi);
pi.cancel();

## }

QueryPreferences.setAlarmOn(context, isOn);

## }
## ...
## }
Then your StartupReceiver can use it to turn the alarm on at boot.
Listing 27.5  Starting alarm on boot (StartupReceiver.java)
public class StartupReceiver extends BroadcastReceiver{
private static final String TAG = "StartupReceiver";
@Override

public void onReceive(Context context, Intent intent) {
Log.i(TAG, "Received broadcast intent: " + intent.getAction());
boolean isOn = QueryPreferences.isAlarmOn(context);
PollService.setServiceAlarm(context, isOn);

## }
## }
Run PhotoGallery again. (You may want to change PollService.POLL_INTERVAL back to a shorter
interval, such as 60 seconds, for testing purposes.) Turn polling on by clicking Start polling in the
toolbar. Reboot your device. This time, your background polling should be restarted after you reboot
your phone, tablet, or emulator.
## Filtering Foreground Notifications
With that sharp corner filed down a bit, lets turn to another imperfection in PhotoGallery. Your
notifications work great, but they are sent even when the user already has the application open.
You can fix this problem with broadcast intents, too. But they will work in a completely different way.
First, you will send (and receive) your own custom broadcast intent (and ultimately will lock it down
so it can be received by components in your application only). Second, you will register a receiver

Sending broadcast intents
## 497
for your broadcast dynamically in code, rather than in the manifest. Finally, you will send an ordered
broadcast to pass data along a chain of receivers, ensuring a certain receiver is run last. (You do not
know how to do all this yet, but you will by the time you are done.)
Sending broadcast intents
This part is easy: you need to send your own broadcast intent. Specifically, you will send a broadcast
notifying interested components that a new search results notification is ready to post. To send a
broadcast intent, create an intent and pass it in to sendBroadcast(Intent). In this case, you will want
it to broadcast an action you define, so define an action constant as well.
Add these items in PollService.
Listing 27.6  Sending a broadcast intent (PollService.java)
public class PollService extends IntentService {

private static final String TAG = "PollService";

private static final long POLL_INTERVAL = AlarmManager.INTERVAL_FIFTEEN_MINUTES;


public static final String ACTION_SHOW_NOTIFICATION =

"com.bignerdranch.android.photogallery.SHOW_NOTIFICATION";

## ...


@Override

protected void onHandleIntent(Intent intent) {

## ...
String resultId = items.get(0).getId();

if (resultId.equals(lastResultId)) {
Log.i(TAG, "Got an old result: " + resultId);
} else {

## ...


NotificationManagerCompat notificationManager =

NotificationManagerCompat.from(this);
notificationManager.notify(0, notification);
sendBroadcast(new Intent(ACTION_SHOW_NOTIFICATION));

## }
QueryPreferences.setLastResultId(this, resultId);
## }

## ...
## }
Now your app will send out a broadcast every time new search results are available.
Creating and registering a dynamic receiver
Now you need a receiver for your ACTION_SHOW_NOTIFICATION broadcast intent.

## Chapter 27  Broadcast Intents
## 498
You could write a standalone broadcast receiver, like StartupReceiver, and register it in the manifest.
But that would not be ideal in this case. Here, you want PhotoGalleryFragment to receive the intent
only while it is alive. A standalone receiver declared in the manifest would always receive the intent
and would need some other way of knowing that PhotoGalleryFragment is alive (which is not easily
achieved in Android).
The solution is to use a dynamic broadcast receiver. A dynamic receiver is registered in code, not
in the manifest. You register the receiver by calling registerReceiver(BroadcastReceiver,
IntentFilter) and unregister it by calling unregisterReceiver(BroadcastReceiver). The receiver
itself is typically defined as an inner instance, like a button-click listener. However, since you need
the same instance in registerReceiver(...) and unregisterReceiver(...), you will need to assign the
receiver to an instance variable.
Create a new abstract class called VisibleFragment, with Fragment as its superclass. This class will
be a generic fragment that hides foreground notifications. (You will write another fragment like this in
## Chapter 28.)
Listing 27.7  A receiver of VisibleFragment’s own (VisibleFragment.java)
public abstract class VisibleFragment extends Fragment {
private static final String TAG = "VisibleFragment";
@Override

public void onStart() {
super.onStart();
IntentFilter filter = new IntentFilter(PollService.ACTION_SHOW_NOTIFICATION);

getActivity().registerReceiver(mOnShowNotification, filter);
## }
@Override
public void onStop() {

super.onStop();
getActivity().unregisterReceiver(mOnShowNotification);

## }



private BroadcastReceiver mOnShowNotification = new BroadcastReceiver() {

@Override
public void onReceive(Context context, Intent intent) {
Toast.makeText(getActivity(),
"Got a broadcast:" + intent.getAction(),
Toast.LENGTH_LONG)

## .show();
## }
## };
## }
Note that to pass in an IntentFilter, you have to create one in code. Your IntentFilter here is
identical to the filter specified by the following XML:
## <intent-filter>

<action android:name="com.bignerdranch.android.photogallery.SHOW_NOTIFICATION" />
## </intent-filter>

Creating and registering a dynamic receiver
## 499
Any IntentFilter you can express in XML can also be expressed in code this way. Just call
addCategory(String), addAction(String), addDataPath(String), and so on to configure your filter.
When you use dynamically registered broadcast receivers, you must also take care to
clean them up. Typically, if you register a receiver in a startup lifecycle method, you call
Context.unregisterReceiver(BroadcastReceiver) in the corresponding shutdown method.
Here, you register inside onStart() and unregister inside onStop(). If instead you registered inside
onActivityCreated(...), you would unregister inside onActivityDestroyed().
(Be careful with onCreate(...) and onDestroy() in retained fragments, by the way. getActivity()
will return different values in onCreate(...) and onDestroy() if the screen has rotated. If you
want to register/unregister in Fragment.onCreate(Bundle) and Fragment.onDestroy(), use
getActivity().getApplicationContext()
instead.)
Next, modify PhotoGalleryFragment to be a subclass of your new VisibleFragment.
Listing 27.8  Making your fragment visible (PhotoGalleryFragment.java)
public class PhotoGalleryFragment extends FragmentVisibleFragment {
## ...
## }
Run PhotoGallery and toggle background polling a couple of times. You will see a nice toast pop up in
addition to your notification icon up top (
## Figure 27.4).
Figure 27.4  Proof that your broadcast exists

## Chapter 27  Broadcast Intents
## 500
Limiting broadcasts to your app using private permissions
One issue with a broadcast like this is that anyone on the system can listen to it or trigger your
receivers. You are usually not going to want either of those things to happen.
You can preclude these unauthorized intrusions into your personal business in a couple of ways.
One way is to declare in your manifest that the receiver is internal to your app by adding an
android:exported="false" attribute to your receiver tag. This will prevent it from being visible to
other applications on the system.
Another way is to create your own permission by adding a permission tag to your
AndroidManifest.xml. This is the approach you will take for PhotoGallery.
Declare and acquire your own permission in
AndroidManifest.xml.
Listing 27.9  Adding a private permission (
AndroidManifest.xml)

## <manifest ...>

<permission android:name="com.bignerdranch.android.photogallery.PRIVATE"

android:protectionLevel="signature" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />

<uses-permission android:name="com.bignerdranch.android.photogallery.PRIVATE" />
## <application
## ... >
## ...

## </application>
## </manifest>

Notice that you define a custom permission with a protection level of signature. You will learn
more about protection levels in just a moment. The permission itself is a simple string, just like intent
actions, categories, and system permissions you have used. You must always acquire a permission to
use it, even when you defined it yourself. Them’s the rules.
Take note of the shaded constant value above, by the way. This string needs to appear in three more
places and must be identical in each place. You would be wise to copy and paste it rather than typing it
out by hand.
Now, use your permission by defining a corresponding constant in code and then passing it in to your
sendBroadcast(...) call.

Limiting broadcasts to your app using private permissions
## 501
Listing 27.10  Sending with a permission (
PollService.java)
public class PollService extends IntentService {

## ...

public static final String ACTION_SHOW_NOTIFICATION =
"com.bignerdranch.android.photogallery.SHOW_NOTIFICATION";
public static final String PERM_PRIVATE =
"com.bignerdranch.android.photogallery.PRIVATE";

public static Intent newIntent(Context context) {
return new Intent(context, PollService.class);
## }
## ...


@Override
protected void onHandleIntent(Intent intent) {
## ...


String resultId = items.get(0).getId();
if (resultId.equals(lastResultId)) {

Log.i(TAG, "Got an old result: " + resultId);
} else {

## ...
notificationManager.notify(0, notification);
sendBroadcast(new Intent(ACTION_SHOW_NOTIFICATION), PERM_PRIVATE);
## }
QueryPreferences.setLastResultId(this, resultId);
## }
## ...
## }
To use your permission, you pass it as a parameter to sendBroadcast(...). Using the permission here
ensures that any application must use that same permission to receive the intent you are sending.
What about your broadcast receiver? Someone could create their own broadcast intent to trigger it. You
can fix that by passing in your permission in
registerReceiver(...), too.
Listing 27.11  Permissions on a broadcast receiver (VisibleFragment.java)
public abstract class VisibleFragment extends Fragment {
## ...
@Override
public void onStart() {
super.onStart();

IntentFilter filter = new IntentFilter(PollService.ACTION_SHOW_NOTIFICATION);
getActivity().registerReceiver(mOnShowNotification, filter,
PollService.PERM_PRIVATE, null);
## }
## ...
## }
Now, your app is the only app that can trigger that receiver.

## Chapter 27  Broadcast Intents
## 502
More about protection levels
Every custom permission has to specify a value for
android:protectionLevel. Your permission’s
protectionLevel tells Android how it should be used. In your case, you used a protectionLevel of
signature.
The signature protection level means that if another application wants to use your permission, it has
to be signed with the same key as your application. This is usually the right choice for permissions
you use internally in your application. Because other developers do not have your key, they cannot get
access to anything this permission protects. Plus, because you do have your own key, you can use this
permission in any other app you decide to write later.
Table 27.1  Values for protectionLevel
ValueDescription
normalThis is for protecting app functionality that will not do anything dangerous like
accessing secure personal data or sending data to the Internet. The user can see
the permission before choosing to install the app, but is not explicitly asked to
grant it. android.permission.RECEIVE_BOOT_COMPLETED uses this permission
level, and so does the permission that lets your app vibrate the user’s device.
dangerousThis is for everything you would not use normal for – accessing personal data,
sending and receiving things from network interfaces, accessing hardware
that might be used to spy on the user, or anything else that could cause real
problems. The Internet permission, camera permission, and contacts permission
all fall under this category. Android may ask the user for an explicit go-ahead
before approving a dangerous permission.
signatureThe system grants this permission if the app is signed with the same certificate
as the declaring application, and denies it otherwise. If the permission is
granted, the user is not notified. This is for functionality that is internal to an
app – as the developer, because you have the certificate and only apps signed
with the same certificate can use the permission, you have control over who
uses the permission. You used it here to prevent anyone else from seeing your
broadcasts. If you wanted, you could write another app that listens to them, too.
signatureOrSystemThis is like signature, but it also grants permission to all packages in the
Android system image. This is for communicating with apps built into the
system image. If the permission is granted, the user is not notified. Most
developers do not need to use it.
Passing and receiving data with ordered broadcasts
Time to finally bring this baby home. The last piece is to ensure your dynamically registered receiver
always receives the PollService.ACTION_SHOW_NOTIFICATION broadcast before any other receivers
and that it modifies the broadcast to indicate that the notification should not be posted.
Right now you are sending your own personal private broadcast, but so far you only have one-way
communication (Figure 27.5).

Passing and receiving data with ordered broadcasts
## 503
Figure 27.5  Regular broadcast intents
This is because a regular broadcast intent is conceptually received by everyone at the same time. In
reality, because onReceive(...) is called on the main thread, your receivers are not actually executed
concurrently. However, it is not possible to rely on their being executed in any particular order or to
know when they have all completed execution. As a result, it is a hassle for the broadcast receivers to
communicate with each other or for the sender of the intent to receive information from the receivers.
You can implement two-way communication using an ordered broadcast intent (Figure 27.6). Ordered
broadcasts allow a sequence of broadcast receivers to process a broadcast intent in order. They also
allow the sender of a broadcast to receive results from the broadcast’s recipients by passing in a special
broadcast receiver, called the result receiver.
Figure 27.6  Ordered broadcast intents
On the receiving side, this looks mostly the same as a regular broadcast. But you get an additional
tool: a set of methods used to change the return value of your receiver. Here, you want to cancel
the notification. This can be communicated by use of a simple integer result code. You will use the
setResultCode(int) method to set the result code to Activity.RESULT_CANCELED.
Modify VisibleFragment to tell the sender of SHOW_NOTIFICATION whether the notification should be
posted. This information will also be sent to any other broadcast receivers along the chain.

## Chapter 27  Broadcast Intents
## 504
Listing 27.12  Sending a simple result back (
VisibleFragment.java)
public abstract class VisibleFragment extends Fragment {
private static final String TAG = "VisibleFragment";
private BroadcastReceiver mOnShowNotification = new BroadcastReceiver() {
@Override
public void onReceive(Context context, Intent intent) {

Toast.makeText(getActivity(),
"Got a broadcast:" + intent.getAction(),
Toast.LENGTH_LONG)
## .show();
// If we receive this, we're visible, so cancel

// the notification
Log.i(TAG, "canceling notification");
setResultCode(Activity.RESULT_CANCELED);
## }

## };
## ...
## }
Because all you need to do is signal yes or no here, you only need the result code. If you need to return
more complicated data, you can use setResultData(String) or setResultExtras(Bundle). And if
you want to set all three values, you can call setResult(int, String, Bundle). Once your return
values are set here, every subsequent receiver will be able to see or modify them.
For those methods to do anything useful, your broadcast needs to be ordered. Write a new method to
send an ordered broadcast in PollService. This method will package up a Notification invocation
and send it out as a broadcast. Update onHandleIntent(...) to call your new method and, in turn, send
out an ordered broadcast instead of posting the notification directly to the NotificationManager.

Passing and receiving data with ordered broadcasts
## 505
Listing 27.13  Sending an ordered broadcast (PollService.java
## )
## ...
public static final String PERM_PRIVATE =
"com.bignerdranch.android.photogallery.PRIVATE";
public static final String REQUEST_CODE = "REQUEST_CODE";
public static final String NOTIFICATION = "NOTIFICATION";
## ...
@Override
protected void onHandleIntent(Intent intent) {

## ...

String resultId = items.get(0).getId();
if (resultId.equals(lastResultId)) {

Log.i(TAG, "Got an old result: " + resultId);
} else {

Log.i(TAG, "Got a new result: " + resultId);
## ...
Notification notification = ...;
NotificationManagerCompat notificationManager =
NotificationManagerCompat.from(this);
notificationManager.notify(0, notification);
sendBroadcast(new Intent(ACTION_SHOW_NOTIFICATION), PERM_PRIVATE);
showBackgroundNotification(0, notification);
## }
QueryPreferences.setLastResultId(this, resultId);
## }
private void showBackgroundNotification(int requestCode, Notification notification) {
Intent i = new Intent(ACTION_SHOW_NOTIFICATION);

i.putExtra(REQUEST_CODE, requestCode);
i.putExtra(NOTIFICATION, notification);

sendOrderedBroadcast(i, PERM_PRIVATE, null, null,
Activity.RESULT_OK, null, null);
## }
## ...
Context.sendOrderedBroadcast(Intent, String, BroadcastReceiver, Handler, int, String,
## Bundle)
has five additional parameters beyond the ones you used in sendBroadcast(Intent,
String). They are, in order: a result receiver, a Handler to run the result receiver on, and then initial
values for the result code, result data, and result extras for the ordered broadcast.
The result receiver is a special receiver that runs after all the other recipients of your ordered broadcast
intent. In other circumstances, you would be able to use the result receiver to receive the broadcast and
post the notification object. Here, though, that will not work. This broadcast intent will often be sent
right before PollService dies. That means that your broadcast receiver might be dead, too.
Thus, your final broadcast receiver will need to be a standalone receiver, and you will need to enforce
that it runs after the dynamically registered receiver by different means.
First, create a new BroadcastReceiver subclass called NotificationReceiver. Implement it as
follows:

## Chapter 27  Broadcast Intents
## 506
Listing 27.14  Implementing your result receiver
## (
NotificationReceiver.java)
public class NotificationReceiver extends BroadcastReceiver {
private static final String TAG = "NotificationReceiver";
@Override
public void onReceive(Context c, Intent i) {

Log.i(TAG, "received result: " + getResultCode());
if (getResultCode() != Activity.RESULT_OK) {
// A foreground activity cancelled the broadcast
return;
## }
int requestCode = i.getIntExtra(PollService.REQUEST_CODE, 0);

Notification notification = (Notification)
i.getParcelableExtra(PollService.NOTIFICATION);
NotificationManagerCompat notificationManager =
NotificationManagerCompat.from(c);

notificationManager.notify(requestCode, notification);
## }
## }
Next, register your new receiver and assign it a priority. To ensure NotificationReceiver receives
the broadcast after your dynamically registered receiver (so it can check to see whether it should post
the notification to
NotificationManager), you need to set a low priority for NotificationReceiver.
Give it a priority of -999 so that it runs last. This is the lowest user-defined priority possible (-1000 and
below are reserved).
Also, since this receiver is only used by your application, you do not need it to be externally visible.
Set android:exported="false" to keep this receiver to yourself.
Listing 27.15  Registering the notification receiver (AndroidManifest.xml)
## <manifest ...>

## ...

## <application
## ... >

## ...
<receiver android:name=".StartupReceiver">
## <intent-filter>
<action android:name="android.intent.action.BOOT_COMPLETED" />
## </intent-filter>

## </receiver>
<receiver android:name=".NotificationReceiver"
android:exported="false">
## <intent-filter
android:priority="-999">

## <action
android:name="com.bignerdranch.android.photogallery.SHOW_NOTIFICATION" />
## </intent-filter>
## </receiver>

## </application>
## </manifest>

Receivers and Long-Running Tasks
## 507
Run PhotoGallery and toggle background polling a couple of times. You should see that notifications
no longer appear when you have the app in the foreground. (If you have not already done so, change
PollService.POLL_INTERVAL to 60 seconds so that you do not have to wait 15 minutes to verify that
notifications still work in the background.)
Receivers and Long-Running Tasks
So what do you do if you want a broadcast intent to kick off a longer-running task than the restrictions
of the main run loop allow?
You have two options. The first is to put that work into a service instead, and start the service in your
broadcast receiver’s small window of opportunity. This is the method we recommend. A service can
take as long as it needs to service a request. It can queue up multiple requests and service them in order
or otherwise manage requests as it sees fit.
The second is to use the
BroadcastReceiver.goAsync() method. This method returns a
BroadcastReceiver.PendingResult object, which can be used to provide a result at a later time. So
you could give that PendingResult to an AsyncTask to perform some longer running work, and then
respond to the broadcast by calling methods on PendingResult.
There is one downside to using the goAsync method: it is less flexible. You still have to service the
broadcast within 10 seconds or so, and you have fewer architectural options than you do with a service.
Of course, goAsync() has one huge advantage: you can set results for ordered broadcasts with it. If you
really need that, nothing else will do. Just make sure you do not take too long.
For the More Curious: Local Events
Broadcast intents allow you to propagate information across the system in a global fashion. What if
you want to broadcast the occurrence of an event within your app’s process only? Using an event bus is
a great alternative.
An event bus operates on the idea of having a shared bus, or stream of data, that components within
your application can subscribe to. When an event is posted to the bus, subscribed components will be
activated and have their callback code executed.
EventBus by greenrobot is a third-party event bus library we use in our Android applications. Other
alternatives to consider include Square’s Otto, which is another event bus implementation, or using
RxJava
Subjects and Observables to simulate an event bus.
Android does provide a local way to send broadcast intents, called LocalBroadcastManager. But we
find that the third-party libraries mentioned here provide a more flexible and easier-to-use API for
broadcasting local events.
Using EventBus
In order to use EventBus in your application, you must add a library dependency to your project. Once
the dependency is set up, you define a class representing an event (you can add fields to the event if
you need to pass data along):
public class NewFriendAddedEvent { }

## Chapter 27  Broadcast Intents
## 508
You can post to the bus from just about anywhere in your app:
EventBus eventBus = EventBus.getDefault();
eventBus.post(new NewFriendAddedEvent());
Other parts of your app can subscribe to receive events by first registering to listen on the bus. Often
you will register and unregister activities or fragments in corresponding lifecycle methods, such as
onStart(...) and onStop(...):
// In some fragment or activity...
private EventBus mEventBus;
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);

mEventBus = EventBus.getDefault();
## }
@Override
public void onStart() {

super.onStart();
mEventBus.register(this);
## }
@Override
public void onStop() {

super.onStop();
mEventBus.unregister(this);
## }
You specify what the subscriber should do when an event it is looking for is posted by implementing
an
onEvent(...) or onEventMainThread(...) method with the appropriate event type as input. Using
onEvent(...) means the event will be processed on the same thread it was sent from. (You could
implement onEventMainThread(...) to ensure the event is processed on the main thread if it happens to
be issued from a background thread.)
// In some registered component, like a fragment or activity...
public void onEventMainThread(NewFriendAddedEvent event){

Friend newFriend = event.getFriend();
// Update the UI or do something in response to event...
## }
Using RxJava
RxJava can also be used to implement an event broadcasting mechanism. RxJava is a library for
writing “reactive”-style Java code. That “reactive” idea is broad, and beyond the scope of what we can
cover here. The short story is that it allows you to publish and subscribe to sequences of events and
gives you a broad set of generic tools for manipulating these event sequences.
So you could create something called a
Subject, which is an object you can publish events to as well
as subscribe to events on.
Subject<Object, Object> eventBus = new SerializedSubject<>(PublishSubject.create());
You can publish events to it:

For the More Curious: Detecting the Visibility of Your Fragment
## 509
Friend someNewFriend = ...;
NewFriendAddedEvent event = new NewFriendAddedEvent(someNewFriend);
eventBus.onNext(event);
And subscribe to events on it:
eventBus.subscribe(new Action1<Object>() {

@Override
public void call(Object event) {
if (event instanceof NewFriendAddedEvent) {
Friend newFriend = ((NewFriendAddedEvent)event).getFriend();
// Update the UI

## }
## }
## })
The advantage of RxJava’s solution is that your eventBus is now also an Observable, RxJava’s
representation of a stream of events. That means that you get to use all of RxJava’s various event
manipulation tools. If that piques your interest, check out the wiki on RxJava’s project page: https://
github.com/ReactiveX/RxJava/wiki.
For the More Curious: Detecting the Visibility of Your
## Fragment
When you reflect on your PhotoGallery implementation, you may notice that you used the global
broadcast mechanism to broadcast the SHOW_NOTIFICATION intent. However, you locked the receiving
of that broadcast to items local to your app progress by using custom permissions. You may find
yourself asking, “Why am I using a global mechanism if I am just communicating things in my own
app? Why not a local mechanism instead?”
This is because you were specifically trying to solve the problem of knowing whether or not
PhotoGalleryFragment
was visible. The combination of ordered broadcasts, standalone receivers,
and dynamically registered receivers you implemented gets the job done. There is not a more
straightforward way to do this in Android.
More specifically, LocalBroadcastManager would not work for PhotoGallery’s notification broadcast
and visible fragment detection, for two main reasons.
First, LocalBroadcastManager does not support ordered broadcasts (though it does provide a blocking
way to broadcast, namely sendBroadcastSync(Intent intent)). This will not work for PhotoGallery
because you need to force NotificationReceiver to run last in the chain.
## Second,
sendBroadcastSync(Intent intent) does not support sending and receiving a broadcast
on separate threads. In PhotoGallery you need to send the broadcast from a background thread (in
PollService.onHandleIntent(...)) and receive the intent on the main thread (by the dynamic receiver
that is registered by PhotoGalleryFragment on the main thread in onResume(...)).
As of this writing, the semantics of LocalBroadcastManager’s thread delivery are not well documented
and, in our experience, are not intuitive. For example, if you call sendBroadcastSync(...) from a
background thread, all pending broadcasts will get flushed out on that background thread regardless of
whether they were posted from the main thread.
This is not to say LocalBroadcastManager is not useful. It is simply not the right tool for the problems
you solved in this chapter.