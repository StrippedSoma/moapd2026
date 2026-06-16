

## 449
## 25
## Search
Your next task with PhotoGallery is to search photos on Flickr. You will learn how to integrate search
into your app the Android way. Or, as it turns out, one of the Android ways. Search has been integrated
into Android from the very beginning, but it has changed a lot over time.
In this chapter you will implement search using SearchView
## .
The user will be able to submit a query using the SearchView, which will search Flickr using the query
string and populate the RecyclerView with the search results (Figure 25.1). The query string submitted
will be persisted to the filesystem. This means the user’s last query will be accessible across restarts of
the app and even the device.
Figure 25.1  App preview
## Searching Flickr
Let’s begin with the Flickr side of things. To search Flickr, you call the flickr.photos.search
method. Here is what a GET request to search for the text
“cat” looks like:
https://api.flickr.com/services/rest/?method=flickr.photos.search
## &api_key=xxx&format=json&nojsoncallback=1&text=cat

## Chapter 25  Search
## 450
The method is set to flickr.photos.search. A new parameter, text, is added and set to whatever
string you are searching for (“cat,” in this case).
While the search request URL differs from the one you used to request recent photos, the format of
the JSON returned remains the same. This is good news, because it means you can use the same JSON
parsing code you already wrote, regardless of whether you are searching or getting recent photos.
First, refactor some of your old FlickrFetchr code to reuse the parsing code across both scenarios.
Start by adding constants for the reusable pieces of the URL, as shown in Listing 25.1. Cut the URI-
building code from fetchItems and paste it as the value for ENDPOINT. However, make sure to only
include the shaded parts. The constant ENDPOINT should not contain the method query parameter, and
the build statement should not be converted to a string using toString().
Listing 25.1  Adding URL constants (FlickrFetchr.java)
public class FlickrFetchr {

private static final String TAG = "FlickrFetchr";
private static final String API_KEY = "yourApiKeyHere";
private static final String FETCH_RECENTS_METHOD = "flickr.photos.getRecent";
private static final String SEARCH_METHOD = "flickr.photos.search";

private static final Uri ENDPOINT = Uri
## .parse("https://api.flickr.com/services/rest/")

.buildUpon()
.appendQueryParameter("api_key", API_KEY)
.appendQueryParameter("format", "json")

.appendQueryParameter("nojsoncallback", "1")
.appendQueryParameter("extras", "url_s")

## .build();

## ...


public List<GalleryItem> fetchItems() {
List<GalleryItem> items = new ArrayList<>();
try {
String url = Uri.parse("https://api.flickr.com/services/rest/")
.buildUpon()
.appendQueryParameter("method", "flickr.photos.getRecent")
.appendQueryParameter("api_key", API_KEY)
.appendQueryParameter("format", "json")
.appendQueryParameter("nojsoncallback", "1")
.appendQueryParameter("extras", "url_s")
.build().toString();
String jsonString = getUrlString(url);
## ...

} catch (IOException ioe) {
Log.e(TAG, "Failed to fetch items", ioe);
} catch (JSONException je) {
Log.e(TAG, "Failed to parse JSON", je);
## }

return items;
## }


## ...
## }

## Searching Flickr
## 451
(The change you just made will result in an error in fetchItems(). You can ignore this error for now,
as you are about to delete fetchItems() anyway.)
Rename fetchItems() to downloadGalleryItems(String url) to reflect its new, more general
purpose. It no longer needs to be public, either, so change its visibility to private, too.
Listing 25.2  Refactoring Flickr code (
FlickrFetchr.java)
public class FlickrFetchr {
## ...
public List<GalleryItem> fetchItems() {
private List<GalleryItem> downloadGalleryItems(String url) {

List<GalleryItem> items = new ArrayList<>();


try {
String jsonString = getUrlString(url);
Log.i(TAG, "Received JSON: " + jsonString);

JSONObject jsonBody = new JSONObject(jsonString);
parseItems(items, jsonBody);

} catch (IOException ioe) {
Log.e(TAG, "Failed to fetch items", ioe);
} catch (JSONException je) {

Log.e(TAG, "Failed to parse JSON", je);
## }
return items;
## }


## ...
## }
The new downloadGalleryItems(String) method takes a URL as input, so there is no need to build
the URL inside. Instead, add a new method to build the URL based on method and query values.

## Chapter 25  Search
## 452
Listing 25.3  Adding helper method to build URL (FlickrFetchr.java
## )
public class FlickrFetchr {
## ...

private List<GalleryItem> downloadGalleryItems(String url) {
## ...

## }

private String buildUrl(String method, String query) {
Uri.Builder uriBuilder = ENDPOINT.buildUpon()
.appendQueryParameter("method", method);

if (method.equals(SEARCH_METHOD)) {
uriBuilder.appendQueryParameter("text", query);
## }
return uriBuilder.build().toString();

## }

private void parseItems(List<GalleryItem> items, JSONObject jsonBody)

throws IOException, JSONException {
## ...

## }
## }
The buildUrl(...) method appends the necessary parameters, just as the removed fetchItems() used
to. But it dynamically fills in the method parameter value. Additionally, it appends a value for the text
parameter only if the value specified for the method parameter is search.
Now add methods to kick off the download by building a URL and calling
downloadGalleryItems(String).

## Searching Flickr
## 453
Listing 25.4  Adding methods to get recents and search (FlickrFetchr.java)
public class FlickrFetchr {

## ...

public String getUrlString(String urlSpec) throws IOException {
return new String(getUrlBytes(urlSpec));

## }
public List<GalleryItem> fetchRecentPhotos() {
String url = buildUrl(FETCH_RECENTS_METHOD, null);
return downloadGalleryItems(url);

## }

public List<GalleryItem> searchPhotos(String query) {
String url = buildUrl(SEARCH_METHOD, query);

return downloadGalleryItems(url);
## }
private List<GalleryItem> downloadGalleryItems(String url) {
List<GalleryItem> items = new ArrayList<>();

## ...
return items;

## }

## ...
## }
FlickrFetchr is now equipped to handle both searching and getting recent photos. The
fetchRecentPhotos() and searchPhotos(String) methods serve as the public interface for getting a
list of GalleryItems from the Flickr web service.
You need to update your fragment code to reflect the refactoring you just completed in FlickrFetchr.
Open PhotoGalleryFragment and update FetchItemsTask.

## Chapter 25  Search
## 454
Listing 25.5  Hardwired search query code (
PhotoGalleryFragment.java)
public class PhotoGalleryFragment extends Fragment {
## ...
private class FetchItemsTask extends AsyncTask<Void,Void,List<GalleryItem>> {

@Override
protected List<GalleryItem> doInBackground(Void... params) {
return new FlickrFetchr().fetchItems();
String query = "robot"; // Just for testing

if (query == null) {
return new FlickrFetchr().fetchRecentPhotos();
} else {
return new FlickrFetchr().searchPhotos(query);

## }
## }
@Override
protected void onPostExecute(List<GalleryItem> items) {

mItems = items;
setupAdapter();

## }
## }

## }
If the query string is not null (which for now is always the case), then FetchItemsTask will execute a
Flickr search. Otherwise FetchItemsTask will default to fetching recent photos, just as it did before.
Hardcoding the query allows you to test out your new search code even though you have not yet
provided a way to enter a query through the user interface.
Run PhotoGallery and see what you get. Hopefully, you will see a cool robot or two (Figure 25.2).

Using SearchView
## 455
Figure 25.2  Hardcoded search results
Using SearchView
Now that
FlickrFetchr supports searching, it is time to add a way for the user to enter a query and
initiate a search. Do this by adding a SearchView.
SearchView is an action view – a view that may be included within the toolbar. SearchView allows
your entire search interface to live within your application’s toolbar.
First, confirm that a toolbar (containing your app title) appears at the top of your app. If not, follow the
steps outlined in
Chapter 13 to add a toolbar to your app.
Next, create a new menu XML file for PhotoGalleryFragment in res/menu/
fragment_photo_gallery.xml. This file will specify the items that should appear in the toolbar.

## Chapter 25  Search
## 456
Listing 25.6  Adding menu XML file (
res/menu/
fragment_photo_gallery.xml)
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto">

<item android:id="@+id/menu_item_search"
android:title="@string/search"
app:actionViewClass="android.support.v7.widget.SearchView"
app:showAsAction="ifRoom" />
<item android:id="@+id/menu_item_clear"

android:title="@string/clear_search"
app:showAsAction="never" />
## </menu>
You will see a couple errors in the new XML, complaining that you have not yet defined the strings
you are referencing for the
android:title attributes. Ignore those for now. You will fix them in a bit.
The first item entry in Listing 25.6 tells the toolbar to display a SearchView by specifying the value
android.support.v7.widget.SearchView for the app:actionViewClass attribute. (Notice the usage
of the app namespace for the showAsAction and actionViewClass attributes. Please refer back to
Chapter 13 if you are unsure of why this is used.)
SearchView (android.widget.SearchView) was originally introduced in API 11 (Honeycomb
3.0). However, SearchView was more recently included as part of the support library
(android.support.v7.widget.SearchView). So which version of SearchView should you use? You
have seen our answer in the code you just entered: the support library version. This may seem strange,
as your app’s minimum SDK is 16.
We recommend using the support library for the same reasons outlined in Chapter 7. As new features
get added with each new release of Android, the features are often back-ported to the support library.
A prime example is theming. With the release of API 21 (Lollipop 5.0), the native framework
SearchView supports many options for customizing the SearchView’s appearance. The only way to get
these fancy features on earlier versions of Android (down to API 7) is to use the support library version
of SearchView.
The second item in Listing 25.6 will add a Clear Search option. This option will always display in the
overflow menu because you set app:showAsAction to never. Later on you will configure this item so
that, when pressed, the user’s stored query will be erased from the disk. For now, you can ignore this
item.
Now it is time to address the errors in your menu XML. Open strings.xml and add the missing
strings:
Listing 25.7  Adding search strings (
res/values/strings.xml)
## <resources>

## ...
<string name="search">Search</string>
<string name="clear_search">Clear Search</string>
## </resources>

Using SearchView
## 457
Finally, open PhotoGalleryFragment. Add a call to setHasOptionsMenu(true) in onCreate(...) to
register the fragment to receive menu callbacks. Override onCreateOptionsMenu(...) and inflate the
menu XML file you created. This will add the items listed in your menu XML to the toolbar.
## Listing 25.8  Overriding
onCreateOptionsMenu(...)
(PhotoGalleryFragment.java)
public class PhotoGalleryFragment extends Fragment {
## ...


@Override

public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setRetainInstance(true);

setHasOptionsMenu(true);
new FetchItemsTask().execute();


## ...
## }


## ...


@Override
public void onDestroy() {

## ...
## }
@Override
public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

super.onCreateOptionsMenu(menu, menuInflater);
menuInflater.inflate(R.menu.fragment_photo_gallery, menu);

## }

private void setupAdapter() {

## ...
## }
## ...
## }
Fire up PhotoGallery and see what the SearchView looks like. Pressing the Search icon expands the
view to display a text box where the user can enter a query (Figure 25.3).

## Chapter 25  Search
## 458
Figure 25.3  SearchView collapsed and expanded
When the SearchView is expanded, a x icon appears on the right. Pressing the x icon one time clears
out what you typed. Pressing the x again collapses the SearchView back to a single search icon.
If you try submitting a query, it will not do anything yet. Not to worry. You will make your SearchView
more useful in just a moment.
Responding to SearchView user interactions
When the user submits a query, your app should execute a search against the Flickr web
service and refresh the images the user sees with the search results. Fortunately, the
SearchView.OnQueryTextListener interface provides a way to receive a callback when a query is
submitted.
## Update
onCreateOptionsMenu(...) to add a SearchView.OnQueryTextListener to your SearchView.

Responding to SearchView user interactions
## 459
## Listing 25.9  Logging
SearchView.OnQueryTextListener events
(PhotoGalleryFragment.java)
public class PhotoGalleryFragment extends Fragment {

## ...
@Override
public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

super.onCreateOptionsMenu(menu, menuInflater);
menuInflater.inflate(R.menu.fragment_photo_gallery, menu);

MenuItem searchItem = menu.findItem(R.id.menu_item_search);

final SearchView searchView = (SearchView) searchItem.getActionView();
searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
@Override
public boolean onQueryTextSubmit(String s) {

Log.d(TAG, "QueryTextSubmit: " + s);
updateItems();

return true;
## }
@Override
public boolean onQueryTextChange(String s) {

Log.d(TAG, "QueryTextChange: " + s);
return false;
## }

## });
## }
private void updateItems() {
new FetchItemsTask().execute();

## }


## ...
## }
In onCreateOptionsMenu(...), you pull the MenuItem representing the search box from the menu
and store it in searchItem. Then you pull the SearchView object from searchItem using the
getActionView() method.
(Note: MenuItem.getActionView() was added in API 11. This is fine here as the minimum SDK for
your app is API 16. However, if you need to make an app that goes back as far as the support library
allows, you will need to take a different approach for getting access to the SearchView object.)
Once you have a reference to the SearchView
you are able to set a SearchView.OnQueryTextListener
using the setOnQueryTextListener(...) method. You must override two methods in the
SearchView.OnQueryTextListener implementation: onQueryTextSubmit(String) and
onQueryTextChange(String).
## The
onQueryTextChange(String) callback is executed any time text in the SearchView text box
changes. This means that it is called every time a single character changes. You will not do anything
inside this callback for this app except log the input string.
## The
onQueryTextSubmit(String) callback is executed when the user submits a query. The query
the user submitted is passed as input. Returning true signifies to the system that the search request

## Chapter 25  Search
## 460
has been handled. This callback is where you will launch a FetchItemsTask to query for new results.
(Right now FetchItemsTask still has a hardcoded query. You will refactor FetchItemsTask in a bit so
that it uses a submitted query if there is one.)
The updateItems() does not seem terribly useful just yet. Later on you will have several places where
you need to execute FetchItemsTask. The updateItems() method is a wrapper for doing just that.
As a last bit of cleanup, replace the line that creates and executes a FetchItemsTask with a call to
updateItems() in the onCreate(...) method.
Listing 25.10  Cleaning up onCreate(...) (PhotoGalleryFragment.java)
public class PhotoGalleryFragment extends Fragment {
## ...
@Override
public void onCreate(Bundle savedInstanceState) {

super.onCreate(savedInstanceState);
setRetainInstance(true);
setHasOptionsMenu(true);

new FetchItemsTask().execute();
updateItems();
## ...
Log.i(TAG, "Background thread started");

## }


## ...
## }
Run your app and submit a query. The search results will still be based on the hardcoded query in
Listing 25.5, but you should see the images reload. You should also see log statements reflecting the
fact that your SearchView.OnQueryTextListener callback methods have been executed.
Note: If you use the hardware keyboard (e.g., from your laptop) to submit your search query on an
emulator, you will see the search executed two times, one after the other. It will look like the images
start to load, then load all over again. This is because there is a small bug in SearchView
. You can
ignore this behavior because it is simply a side effect of using the emulator and will not affect your app
when it runs on a real Android device.
Simple Persistence with Shared Preferences
The last piece of functionality you need to add is to actually use the query entered in the SearchView
when the search request is submitted.
In your app, there will only be one active query at a time. That query should be persisted (remembered
by the app) between restarts of the app (even after the user turns off the device). You will achieve this
by writing the query string to shared preferences
. Any time the user submits a query, you will first
write the query to shared preferences, overwriting whatever query was there before. When a search is
executed against Flickr, you will pull the query string from shared preferences and use it as the value
for the text parameter.

Simple Persistence with Shared Preferences
## 461
Shared preferences are files on your filesystem that you read and edit using the SharedPreferences
class. An instance of SharedPreferences acts like a key-value store, much like Bundle, except that it
is backed by persistent storage. The keys are strings, and the values are atomic data types. If you look
at them you will see that the files are simple XML, but SharedPreferences makes it easy to ignore
that implementation detail. Shared preferences files are stored in your application’s sandbox, so you
should not store sensitive information (like passwords) there.
To get a specific instance of SharedPreferences, you can use the
Context.getSharedPreferences(String, int) method. However, in practice, you will often not care
too much about the specific instance, just that it is shared across the entire app. In that case, it is better
to use the PreferenceManager.getDefaultSharedPreferences(Context) method, which returns an
instance with a default name and private permissions (so that the preferences are only available from
within your application).
Add a new class named QueryPreferences, which will serve as a convenient interface for reading and
writing the query to and from shared preferences.
Listing 25.11  Adding class to manage stored query (QueryPreferences.java)

public class QueryPreferences {
private static final String PREF_SEARCH_QUERY = "searchQuery";
public static String getStoredQuery(Context context) {

return PreferenceManager.getDefaultSharedPreferences(context)
.getString(PREF_SEARCH_QUERY, null);
## }
public static void setStoredQuery(Context context, String query) {

PreferenceManager.getDefaultSharedPreferences(context)
## .edit()
.putString(PREF_SEARCH_QUERY, query)

## .apply();
## }

## }
## PREF_SEARCH_QUERY
is used as the key for the query preference. You will use this key any time you
read or write the query value.
## The
getStoredQuery(Context) method returns the query value stored in shared preferences. It does so
by first acquiring the default SharedPreferences for the given context. (Because QueryPreferences
does not have a Context of its own, the calling component will have to pass its context as input.)
Getting a value you previously stored is as simple as calling SharedPreferences.getString(...),
getInt(...), or whichever method is appropriate for your data type. The second input to
SharedPreferences.getString(PREF_SEARCH_QUERY, null) specifies the default return value that
should be used if there is no entry for the PREF_SEARCH_QUERY key.
The setStoredQuery(Context) method writes the input query to the default shared preferences
for the given context. In your code above, you call SharedPreferences.edit() to get an
instance of SharedPreferences.Editor. This is the class you use to stash values in your
SharedPreferences. It allows you to group sets of changes together in transactions, much like you do
with FragmentTransaction. If you have a lot of changes, this will allow you to group them together
into a single storage write operation.

## Chapter 25  Search
## 462
Once you are done making all of your changes, you call apply() on your editor to make them visible
to other users of that SharedPreferences file. The apply() method makes the change in memory
immediately and then does the actual file writing on a background thread.
QueryPreferences is your entire persistence engine for PhotoGallery. Now that you have a way to
easily store and access the user’s most recent query, update PhotoGalleryFragment to read and write
the query as necessary.
First, update the stored query whenever the user submits a new query.
Listing 25.12  Storing submitted query in shared preferences
## (
PhotoGalleryFragment.java)
public class PhotoGalleryFragment extends Fragment {

## ...
@Override

public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
## ...
searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
@Override

public boolean onQueryTextSubmit(String s) {
Log.d(TAG, "QueryTextSubmit: " + s);
QueryPreferences.setStoredQuery(getActivity(), s);

updateItems();
return true;

## }

@Override

public boolean onQueryTextChange(String s) {
Log.d(TAG, "QueryTextChange: " + s);

return false;
## }
## });

## }

## ...
## }
Next, clear the stored query (set it to null) whenever the user selects the Clear Search item from the
overflow menu.

Simple Persistence with Shared Preferences
## 463
Listing 25.13  Clearing stored query (PhotoGalleryFragment.java
## )

public class PhotoGalleryFragment extends Fragment {

## ...
@Override
public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

## ...
## }
@Override
public boolean onOptionsItemSelected(MenuItem item) {

switch (item.getItemId()) {
case R.id.menu_item_clear:
QueryPreferences.setStoredQuery(getActivity(), null);
updateItems();

return true;
default:

return super.onOptionsItemSelected(item);
## }
## }
## ...

## }
Note that you call
updateItems() after you update the stored query, just as you did in Listing 25.12.
This ensures that the images displayed in the RecyclerView reflect the most recent search query.
Last, but not least, update FetchItemsTask to use the stored query rather than a hardcoded string. Add
a custom constructor to FetchItemsTask that accepts a query string as input and stashes it in a member
variable. Update updateItems() to pull the stored query from shared preferences and use it to create a
new instance of FetchItemsTask. All of these changes are shown in Listing 25.14.

## Chapter 25  Search
## 464
Listing 25.14  Using stored query in FetchItemsTask
## (
PhotoGalleryFragment.java)
public class PhotoGalleryFragment extends Fragment {
## ...

private void updateItems() {

String query = QueryPreferences.getStoredQuery(getActivity());
new FetchItemsTask(query).execute();
## }

## ...

private class FetchItemsTask extends AsyncTask<Void,Void,List<GalleryItem>> {
private String mQuery;
public FetchItemsTask(String query) {
mQuery = query;

## }

@Override

protected List<GalleryItem> doInBackground(Void... params) {
String query = "robot"; // Just for testing
if (querymQuery == null) {
return new FlickrFetchr().fetchRecentPhotos();

} else {
return new FlickrFetchr().searchPhotos(querymQuery);

## }
## }
@Override
protected void onPostExecute(List<GalleryItem> items) {

mItems = items;
setupAdapter();
## }

## }
## }
Search should now work like a charm. Run PhotoGallery, try searching for something, and see what
you get.
## Polishing Your App
For one last bit of polish, pre-populate the search text box with the saved query when the user presses
on the search icon to expand the
SearchView. SearchView’s View.OnClickListener.onClick()
method is called when the user presses the search icon. Hook into this callback and set the
SearchView’s query text when the view is expanded.

## Challenge: Polishing Your App Some More
## 465
## Listing 25.15  Pre-populating
SearchView (PhotoGalleryFragment.java)
public class PhotoGalleryFragment extends Fragment {
## ...
@Override
public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
## ...

searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
## ...
## });


searchView.setOnSearchClickListener(new View.OnClickListener() {

@Override
public void onClick(View v) {
String query = QueryPreferences.getStoredQuery(getActivity());

searchView.setQuery(query, false);
## }

## });
## }
## ...
## }
Run your app and play around with submitting a few searches. Revel at the polish your last bit of code
added. Of course, there is always more polish you could add....
## Challenge: Polishing Your App Some More
You may notice that, when you submit a query, there is a bit of a lag before the RecyclerView starts to
refresh. For this challenge, make the response to the user’s query submission feel more immediate. As
soon as a query is submitted, hide the soft keyboard and collapse the SearchView.
As an extra challenge, clear the contents of the RecyclerView and display a loading indicator
(indeterminate progress bar) as soon as a query is submitted. Get rid of the loading indicator once the
JSON data has been fully downloaded. In other words, the loading indicator should not show once your
code moves on to downloading individual images.