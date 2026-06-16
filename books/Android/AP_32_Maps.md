

## 571
## 32
## Maps
In this chapter, you will go one step further with
LocatrFragment. In addition to searching for a nearby
image, you will find its latitude and longitude and plot it on a map.
## Importing Play Services Maps
Before you get started, you need to import the mapping library. This is another Play Services
library. Open your project structure and add the following dependency to your app module:
com.google.android.gms:play-services-maps:7.0.0. As in the previous chapter, note that the
actual version number will change over time. Use whatever the latest version number is for the plain
play-services dependency.
Mapping on Android
As enjoyable as it is to have data that tells you where your phone is, that data begs to be visualized.
Mapping was probably the first truly killer app for smartphones, which is why Android has had
mapping since day one.
Mapping is big, complicated, and involves an entire support system of servers to provide base map
data. Most of Android can stand alone as part of the Android Open Source Project. Maps, however,
cannot.
So while Android has always
had maps, maps have also always been separate from the rest of
Android’s APIs. The current version of the Maps API, version 2, lives in Google Play Services along
with the Fused Location Provider. So in order to use it, the same requirements apply as you saw in the
section called “Google Play Services” in Chapter 31: you have to either have a device with the Play
Store installed or an emulator with the Google APIs.
If you are making something with maps and happen to flip to this chapter, make sure that you have
followed the steps from the previous chapter before you start:
- Ensure your device supports Play Services.
- Import the appropriate Play Services library.
- Use GooglePlayServicesUtil at an appropriate entry point to ensure that an up-to-date Play
Store app is installed.

## Chapter 32  Maps
## 572
Maps API Setup
Let’s move forward. In addition to the permissions configuration you did in the previous chapter, the
Maps API requires adding more items to your manifest.
The first part of that is simply to add a few additional permissions. The Maps API needs to be able to
do the following:
- download map data from the Internet (android.permission.INTERNET)
- query the state of the network (
android.permission.ACCESS_NETWORK_STATE)
- write temporary map data to external storage (android.permission.WRITE_EXTERNAL_STORAGE)
The INTERNET permission was added in the previous chapter, so that is already taken care of. Add the
other two permissions to your manifest.
Listing 32.1  Adding more permissions (
AndroidManifest.xml)
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
package="com.bignerdranch.android.locatr" >
## <uses-permission
android:name="android.permission.ACCESS_FINE_LOCATION" />

## <uses-permission
android:name="android.permission.ACCESS_COARSE_LOCATION" />
## <uses-permission

android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
## ...
Getting a Maps API Key
Using the Maps API also requires you to declare an API key in your manifest. To do that, you have to
go get your own API key. This API key is used to ensure that your app is authorized to use Google’s
mapping services.
To get an API key, you need to obtain a hash of your signing key and then use it to register for the
Google Maps v2 API on the Google Developer Console. In the next section, we will show you how to
use the Android tools to see what your signing key is. The Google Developer Console is beyond the
scope of this book, however, so we will be pointing you to some documentation on the Web after that.
Getting an API key requires you to identify yourself by your signing key. A signing key is a
mathematically inscrutable chunk of numbers that is yours and yours alone. Every app that is installed
to an Android device is signed with a unique key so that Android knows who made that app.
You have not needed to worry about this so far, because it has been taken care of for you. Behind the
scenes, Android Studio automatically created a default signing key for you, called a debug key. Every
time it builds your app it signs your APK with that debug key before deploying it.
Your signing key
Gradle makes finding this signing key straightforward, but it does require you to do a little bit of work
on the command line.

Getting a Maps API Key
## 573
Open up a command line terminal in your OS and change your directory to your project directory by
typing in a cd command. On OS X, your author would type in a command like this:
Listing 32.2  Changing directory to solution folder (terminal)
## $
cd /Users/bphillips/src/android/Locatr
Then you use one of the gradle command line tools to get a signing report. For Linux or OS X, run the
following command:
Listing 32.3  Signing report on Linux/OS X (terminal)
$ cd /Users/bphillips/src/android/Locatr
## $
./gradlew signingReport
If you are on Windows, on the other hand, use the Windows directory structure and run gradlew.bat
instead:
Listing 32.4  Signing report on Windows (terminal)
## >
cd c:\users\bphillips\Documents\android\Locatr
> gradlew.bat signingReport
When you type in that command, you will get a printout of a report of what signing keys are used for
different kinds of builds. It should look something like this:
$ ./gradlew signingReport
:app:signingReport
Variant: debug
Config: debug
Store: /Users/bphillips/.android/debug.keystore
Alias: AndroidDebugKey
## MD5: XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX
## SHA1:
## XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX
Valid until: Friday, May 16, 2042
## ----------
Variant: release
Config: none
## ----------
Variant: debugTest
Config: debug
Store: /Users/bphillips/.android/debug.keystore
Alias: AndroidDebugKey
## MD5: XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX
## SHA1: XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX
Valid until: Friday, May 16, 2042
## ----------
## BUILD SUCCESSFUL
Total time: 4.354 secs

## Chapter 32  Maps
## 574
In your report, you will see hexadecimal numbers instead of XX for the MD5 and SHA1 values reported
above. The debug SHA1 value shaded above will be the key you want to provide in a moment to get
your API key.
Getting an API key
Once you have the SHA1 of your debug key, you are ready to get an API key. For instructions on how
to finish that process, visit Google’s documentation:
https://developers.google.com/maps/documentation/android/start
When you finish those instructions, you will be provided with an API key for your project that
corresponds to your debug signing key. Add it to your manifest.
Listing 32.5  Adding API key to manifest (AndroidManifest.xml)
## <application

android:allowBackup="true"
android:icon="@mipmap/ic_launcher"

android:label="@string/app_name"
android:theme="@style/AppTheme" >
## <meta-data

android:name="com.google.android.maps.v2.API_KEY"
android:value="XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"/>

## ...
## </application>
With that, you are all ready to go.
## Setting Up Your Map
Now that you have the Maps API set up, you need to create a map. Maps are displayed, appropriately
enough, in a MapView. MapView is like other views, mostly, except in one way: for it to work correctly,
you have to forward all of your lifecycle events, like this:
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);

mMapView.onCreate(savedInstanceState);
## }
This is a huge pain in the neck. It is far easier to let the SDK do that work for you instead by using a
MapFragment or, if you are using support library fragments, SupportMapFragment. The MapFragment
will create and host a
MapView for you, including the proper lifecycle callback hookups.
Your first step is to wipe out your old user interface entirely and replace it with a SupportMapFragment.
This is not as painful as it might sound. All you need to do is switch to using a SupportMapFragment,
delete your onCreateView(...) method, and delete everything that uses your ImageView.

## Setting Up Your Map
## 575
Listing 32.6  Switching to
SupportMapFragment (LocatrFragment.java)
public class LocatrFragment extends
SupportMapFragment Fragment{

private static final String TAG = "LocatrFragment";
private ImageView mImageView;
private GoogleApiClient mClient;

## ...

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
Bundle savedInstanceState) {
View v = inflater.inflate(R.layout.fragment_locatr, container, false);
mImageView = (ImageView) v.findViewById(R.id.image);
return v;
## }
## ...
private class SearchTask extends AsyncTask<Location,Void,Void> {
## ...
@Override
protected void onPostExecute(Void result) {

mImageView.setImageBitmap(mBitmap);
## }

## }
## }
SupportMapFragment has its own override of onCreateView(...), so you should be all set. Run Locatr
to see a map displayed (Figure 32.1).

## Chapter 32  Maps
## 576
Figure 32.1  A plain old map
## Getting More Location Data
To actually plot your image on this map, you need to know where it is. Add an additional “extra”
parameter to your Flickr API query to fetch a lat-lon pair back for your GalleryItem.
Listing 32.7  Adding lat-lon to query (FlickrFetchr.java)
private static final String API_KEY = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
private static final String FETCH_RECENTS_METHOD = "flickr.photos.getRecent";
private static final String SEARCH_METHOD = "flickr.photos.search";
private static final Uri ENDPOINT = Uri.parse("https://api.flickr.com/services/rest/")

.buildUpon()
.appendQueryParameter("api_key", API_KEY)
.appendQueryParameter("format", "json")
.appendQueryParameter("nojsoncallback", "1")
.appendQueryParameter("extras", "url_s,geo")

## .build();
Now add latitude and longitude to GalleryItem.

## Getting More Location Data
## 577
Listing 32.8  Adding lat-lon properties (
GalleryItem.java)
public class GalleryItem {
private String mCaption;
private String mId;
private String mUrl;
private double mLat;
private double mLon;

## ...
public void setId(String id) {
mId = id;

## }

public double getLat() {
return mLat;

## }
public void setLat(double lat) {
mLat = lat;
## }
public double getLon() {

return mLon;
## }
public void setLon(double lon) {
mLon = lon;

## }

@Override

public String toString() {
return mCaption;

## }
## }
And then pull that data out of your Flickr JSON response.

## Chapter 32  Maps
## 578
Listing 32.9  Pulling data from Flickr JSON response (
FlickrFetchr.java)
private void parseItems(List<GalleryItem> items, JSONObject jsonBody)
throws IOException, JSONException {
JSONObject photosJsonObject = jsonBody.getJSONObject("photos");
JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");

for (int i = 0; i < photoJsonArray.length(); i++) {
JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);
GalleryItem item = new GalleryItem();
item.setId(photoJsonObject.getString("id"));

item.setCaption(photoJsonObject.getString("title"));

if (!photoJsonObject.has("url_s")) {
continue;

## }


item.setUrl(photoJsonObject.getString("url_s"));
item.setLat(photoJsonObject.getDouble("latitude"));
item.setLon(photoJsonObject.getDouble("longitude"));
items.add(item);

## }
## }
Now that you are getting your location data, add some fields to your main fragment to store the current
state of your search. Add one field to stash the Bitmap you will display, one for the GalleryItem it is
associated with, and one for your current Location.
Listing 32.10  Adding map data (LocatrFragment.java)
public class LocatrFragment extends SupportMapFragment {
private static final String TAG = "LocatrFragment";
private GoogleApiClient mClient;

private Bitmap mMapImage;
private GalleryItem mMapItem;
private Location mCurrentLocation;

## ...
Next, save those bits of information out from within SearchTask.

Working with Your Map
## 579
Listing 32.11  Saving out query results (
LocatrFragment.java)
private class SearchTask extends AsyncTask<Location,Void,Void> {
private Bitmap mBitmap;
private GalleryItem mGalleryItem;

private Location mLocation;
@Override
protected Void doInBackground(Location... params) {
mLocation = params[0];

FlickrFetchr fetchr = new FlickrFetchr();
## ...
## }
@Override
protected void onPostExecute(Void result) {

mMapImage = mBitmap;
mMapItem = mGalleryItem;
mCurrentLocation = mLocation;

## }
## }
With that, you have the data you need. Next up: making your map show it.
Working with Your Map
Your SupportMapFragment creates a MapView, which is, in turn, a host for the object that does the real
work: GoogleMap. So your first step is to acquire a reference to this master object. Do this by calling
getMapAsync(OnMapReadyCallback).
Listing 32.12  Getting a GoogleMap (LocatrFragment.java)
public class LocatrFragment extends SupportMapFragment {

private static final String TAG = "LocatrFragment";

private GoogleApiClient mClient;

private GoogleMap mMap;
private Bitmap mMapImage;

private GalleryItem mMapItem;
private Location mCurrentLocation;

@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setHasOptionsMenu(true);

mClient = new GoogleApiClient.Builder(getActivity())
## ...
## .build();
getMapAsync(new OnMapReadyCallback() {

@Override
public void onMapReady(GoogleMap googleMap) {
mMap = googleMap;
## }

## });
## }


## ...

## Chapter 32  Maps
## 580
SupportMapFragment.getMapAsync(...) does what it says on the tin: it gets a map object
asynchronously. If you call this from within your onCreate(Bundle), you will get a reference to a
GoogleMap once it is created and initialized.
Now that you have a GoogleMap, you can update the look of that map according to the current state of
LocatrFragment. The first thing you will want to do is zoom in on an area of interest. You will want a
margin around that area of interest. Add a dimension value for that margin.
Listing 32.13  Adding margin (res/values/dimens.xml)
## <resources>

<!-- Default screen margins, per the Android Design guidelines. -->
<dimen name="activity_horizontal_margin">16dp</dimen>

<dimen name="activity_vertical_margin">16dp</dimen>
<dimen name="map_inset_margin">100dp</dimen>
## </resources>
Then add an
updateUI() implementation to perform the zoom.
Listing 32.14  Zooming in (LocatrFragment.java)
private void findImage() {
## ...
## }
private void updateUI() {

if (mMap == null || mMapImage == null) {
return;

## }

LatLng itemPoint = new LatLng(mMapItem.getLat(), mMapItem.getLon());

LatLng myPoint = new LatLng(
mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
LatLngBounds bounds = new LatLngBounds.Builder()
.include(itemPoint)

.include(myPoint)
## .build();

int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, margin);
mMap.animateCamera(update);
## }
private class SearchTask extends AsyncTask<Location,Void,Void> {

## ...
Here is what you just did. To move your GoogleMap around, you built a CameraUpdate.
CameraUpdateFactory has a variety of static methods to build different kinds of CameraUpdate objects
that adjust the position, zoom level, and other properties around what your map is displaying.
Here, you created an update that points the camera at a specific LatLngBounds. You can think of a
LatLngBounds as a rectangle around a set of points. You can make one explicitly by saying what the
southwest and northeast corners of it should be.

Working with Your Map
## 581
More often, it is easier to provide a list of points that you would like this rectangle to encompass.
LatLngBounds.Builder makes it easy to do this: simply create a LatLngBounds.Builder and call
.include(LatLng) for each point your LatLngBounds should encompass (represented by LatLng
objects). When you are done, call build(), and you get an appropriately configured LatLngBounds.
With that done, you can update your map in two ways: with moveCamera(CameraUpdate) or
animateCamera(CameraUpdate). Animating is more fun, so naturally that is what you used above.
Next, hook up your updateUI() method in two places: when the map is first received, and when your
search is finished.
Listing 32.15  Hooking up updateUI()
(LocatrFragment.java)
@Override
public void onCreate(Bundle savedInstanceState) {

## ...

getMapAsync(new OnMapReadyCallback() {

@Override
public void onMapReady(GoogleMap googleMap) {

mMap = googleMap;
updateUI();
## }

## });
## }
## ...
private class SearchTask extends AsyncTask<Location,Void,Void> {
## ...
@Override
protected void onPostExecute(Void result) {

mMapImage = mBitmap;
mMapItem = mGalleryItem;

mCurrentLocation = mLocation;

updateUI();
## }
## }
Run Locatr and press the search button. You should see your map zoom in on an area of interest that
includes your current location (
Figure 32.2). (Emulator users will need to have MockWalker running to
get a location fix.)

## Chapter 32  Maps
## 582
Figure 32.2  Zoomed map
Drawing on the map
Your map is nice, but a little vague. You know that you are in there somewhere, and you know that the
Flickr photo is in there somewhere. But where? Let’s add specificity with some markers.
Drawing on a map is not the same as drawing on a regular view. It is a little easier, in fact. Instead of
drawing pixels to the screen, you draw features to a geographic area. And by “drawing,” we mean,
“build little objects and add them to your GoogleMap so that it can draw them for you.”
Actually, that is not quite right, either. It is, in fact, the GoogleMap object that makes these objects, not
you. Instead, you create objects that describe what you want the GoogleMap to create, called options
objects.
Add two markers to your map by creating MarkerOptions objects and then calling
mMap.addMarker(MarkerOptions).

Drawing on the map
## 583
Listing 32.16  Adding markers (LocatrFragment.java)
private void updateUI() {

## ...
LatLng itemPoint = new LatLng(mMapItem.getLat(), mMapItem.getLon());
LatLng myPoint = new LatLng(
mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());

BitmapDescriptor itemBitmap = BitmapDescriptorFactory.fromBitmap(mMapImage);
MarkerOptions itemMarker = new MarkerOptions()
.position(itemPoint)
.icon(itemBitmap);

MarkerOptions myMarker = new MarkerOptions()
.position(myPoint);

mMap.clear();

mMap.addMarker(itemMarker);
mMap.addMarker(myMarker);
LatLngBounds bounds = new LatLngBounds.Builder()
## ...
## }
When you call addMarker(MarkerOptions), the GoogleMap builds a Marker instance and adds it to the
map. If you need to remove or modify the marker in the future, you can hold on to this instance. In this
case, you will be clearing the map every time you update it. As a result, you do not need to hold on to
the Markers.
Run Locatr, press the search button, and you should see your two markers show up (Figure 32.3).

## Chapter 32  Maps
## 584
Figure 32.3  Geographic looming
And with that, your little geographic image finder is complete. You figured out how to use two Play
Services APIs, you tracked your phone’s location, you registered for one of Google’s many web
services APIs, and you plotted everything on a map. Perhaps a nap is in order now that your app’s map
is in order.
For the More Curious: Teams and API Keys
When you have more than one person working an app with an API key, debug builds start to be a pain.
Your signing credentials are stored in a keystore file, which is unique to you. On a team, everyone will
have their
own keystore file, and their own credentials. In order for anyone new to work on the app,
you have to ask them for their SHA1, and then go and update your API key’s credentials.
Or, at least, that is one option for how to manage the API key: manage all of the signing hashes in your
project. If you want a lot of explicit control over who is doing what, that may be the right solution.
But there is another option: create a debug keystore specifically for the project. Start by creating a
brand new debug keystore with Java’s
keytool program.
Listing 32.17  Creating a new keystore (terminal)
$ keytool -genkey -v -keystore debug.keystore -alias androiddebugkey \
-storepass android -keypass android -keyalg RSA -validity 14600
You will be asked a series of questions by keytool. Answer them honestly, as if no one were watching.
(Since this is a debug key, it is OK to leave the default value on everything but the name if you like.)

For the More Curious: Teams and API Keys
## 585
$ keytool -genkey -v -keystore debug.keystore -alias androiddebugkey \
-storepass android -keypass android -keyalg RSA -validity 14600
What is your first and last name?
[Unknown]:  Bill Phillips
## ...
Once you have that debug.keystore file, move it into your app module’s folder. Then open up your
project structure, select your app module, and navigate to the Signing tab. Click the + button to add
a new signing config. Type in debug in the Name field and debug.keystore for your newly created
keystore (Figure 32.4).
Figure 32.4  Configuring debug signing key
If you configure your API key to use this new keystore, then anyone else can use the same API key by
using the same keystore. Much easier.
Note that if you do this, you need to exercise some caution about how you distribute this new
debug.keystore. If you only share it in a private code repo, you should be fine. But do not publish this
keystore in a public repo where anybody can get to it, because it will allow them to use your API key.