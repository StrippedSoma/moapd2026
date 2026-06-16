

## 235
## 13
## The Toolbar
A key component of any well-designed Android app is the toolbar. The toolbar includes actions that
the user can take, a new mechanism for navigation, and also provides design consistency and branding.
In this chapter, you will create a menu for CriminalIntent that will be displayed in the toolbar. This
menu will have an
action item that lets users add a new crime. You will also enable the Up button in the
toolbar (Figure 13.1).
Figure 13.1  CriminalIntent’s toolbar
AppCompat
The toolbar component is a new addition to Android as of Android 5.0 (Lollipop). Prior to Lollipop,
the action bar was the recommended component for navigation and actions within an app.
The action bar and toolbar are very similar components. The toolbar builds on top of the action bar. It
has a tweaked user interface and is more flexible in the ways that you can use it.

## Chapter 13  The Toolbar
## 236
CriminalIntent supports API 16+, which means that you cannot use the native toolbar on all supported
versions of Android. Luckily, the toolbar has been back-ported to the AppCompat library. The
AppCompat library allows you to provide a Lollipop’d toolbar on any version of Android back to API
7 (Android 2.1).
Using the AppCompat library
In Chapter 12 you added the AppCompat dependency to Criminal Intent. There are a few additional
steps to fully integrate with the AppCompat library. Some of these steps may already be complete
depending on how your project was created.
The following adjustments are required to use the AppCompat library:
- add the AppCompat dependency
- use one of the AppCompat themes
- ensure that all activities are a subclass of AppCompatActivity
Updating the theme
Since you already have the AppCompat dependency, the next step is to ensure that you are using one of
AppCompat’s themes. The AppCompat library comes with three themes:
•Theme.AppCompat
– a dark theme
•Theme.AppCompat.Light – a light theme
•Theme.AppCompat.Light.DarkActionBar – a light theme with a dark toolbar
The theme for your application is specified at the application level and optionally per activity in your
AndroidManifest.xml. Open AndroidManifest.xml and look at the application tag. Notice the
android:theme attribute. You should see something similar to Listing 13.1.
Listing 13.1  The stock manifest (AndroidManifest.xml)

## ...
## <application

android:allowBackup="true"
android:icon="@mipmap/ic_launcher"
android:label="@string/app_name"
android:theme="@style/AppTheme" >

## ...

## The
AppTheme is defined in res/values/styles.xml. Depending on how your initial project was
created, you may have multiple versions of AppTheme in multiple styles.xml files. These files are
resource-qualified for different versions of Android. When using the AppCompat library, there is

Using the AppCompat library
## 237
no need to switch themes based on the version of Android, because you will provide a consistent
experience on all platforms.
If you have multiple versions of the styles.xml file, delete the extra files. You should have a single
styles.xml file that is located at res/values/styles.xml (Figure 13.2).
Figure 13.2  An extra
styles.xml
After cleaning up any extra files, open res/values/styles.xml and ensure that the parent theme of
your AppTheme matches the shaded portion below.
Listing 13.2  Using an AppCompat theme (res/values/styles.xml)
## <resources>

<style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">

## </style>
## </resources>
You will learn much more about styles and themes in Chapter 20.
Using AppCompatActivity
The final step in your AppCompat conversion is to change all of the activities in CriminalIntent to be
subclasses of AppCompatActivity
. Up until this point, all of your activities have been a subclass of
FragmentActivity, which allows you to use the support library’s fragment implementation.
AppCompatActivity itself is a subclass of FragmentActivity. This means that you can still use
support fragments in AppCompatActivity, which makes this a simple change in CriminalIntent.
## Update
SingleFragmentActivity and CrimePagerActivity to be subclasses of AppCompatActivity.
(Why not
CrimeListActivity? Because it is a subclass of SingleFragmentActivity.)
Listing 13.3  Converting to AppCompatActivity
## (
SingleFragmentActivity.java)
public abstract class SingleFragmentActivity extends FragmentActivity {
public abstract class SingleFragmentActivity extends AppCompatActivity {
## ...
## }
Listing 13.4  Converting to AppCompatActivity (CrimePagerActivity.java)
public class CrimePagerActivity extends FragmentActivity AppCompatActivity {
## ...
## }

## Chapter 13  The Toolbar
## 238
Run CriminalIntent and ensure that the app does not crash. You should see something similar to
## Figure 13.3.
Figure 13.3  The new Toolbar
Now that CriminalIntent uses the AppCompat toolbar, you can add actions to the toolbar.
## Menus
The top-right area of the toolbar is reserved for the toolbar’s menu. The menu consists of action items
(sometimes also referred to as menu items), which can perform an action on the current screen or to the
app as a whole. You will add an action item to allow the user to create a new crime.
Your menu will require a few string resources. Add them to strings.xml (Listing 13.5) now. These
strings may seem mysterious at this point, but it is good to get them taken care of. When you need
them later, they will already be in place, and you will not have to stop what you are doing to add them.
Listing 13.5  Adding strings for menus (res/values/strings.xml)
## <resources>
## ...
<string name="date_picker_title">Date of crime:</string>
<string name="new_crime">New Crime</string>

<string name="show_subtitle">Show Subtitle</string>
<string name="hide_subtitle">Hide Subtitle</string>

<string name="subtitle_format">%1$s crimes</string>
## </resources>

Defining a menu in XML
## 239
Defining a menu in XML
Menus are a type of resource similar to layouts. You create an XML description of a menu and place
the file in the res/menu directory of your project. Android generates a resource ID for the menu file
that you then use to inflate the menu in code.
In the project tool window, right-click on the res directory and select New → Android resource file.
Change the Resource type to Menu, name the menu resource fragment_crime_list, and click OK.
Android Studio will generate res/menu/fragment_crime_list.xml (Figure 13.4).
Figure 13.4  Creating a menu file
In the new fragment_crime_list.xml file, add an item element as shown in Listing 13.6.
Listing 13.6  Creating a menu resource for CrimeListFragment
## (
fragment_crime_list.xml)
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto">
## <item
android:id="@+id/menu_item_new_crime"
android:icon="@android:drawable/ic_menu_add"

android:title="@string/new_crime"
app:showAsAction="ifRoom|withText"/>
## </menu>
The showAsAction attribute refers to whether the item will appear in the toolbar itself or in the
overflow menu. You have piped together two values, ifRoom and withText, so the item’s icon and text

## Chapter 13  The Toolbar
## 240
will appear in the toolbar if there is room. If there is room for the icon but not the text, then only the
icon will be visible. If there is no room for either, then the item will be relegated to the overflow menu.
The overflow menu is accessed by the three dots on the far-right side of the toolbar, as shown in
## Figure 13.5
## .
Figure 13.5  Overflow menu in the toolbar
Other options for showAsAction include always and never. Using always is not recommended; it is
better to use ifRoom and let the OS decide. Using
never is a good choice for less-common actions.
In general, you should only put action items that users will use frequently in the toolbar to avoid
cluttering the screen.
The app namespace
Notice that fragment_crime_list.xml uses the xmlns tag to define a new namespace, app, which is
separate from the usual android namespace declaration. This app namespace is then used to specify
the showAsAction attribute.
This unusual namespace declaration exists for legacy reasons with the AppCompat library. The
action bar APIs were first added in Android 3.0. Originally, the AppCompat library was created to
bundle a compatibility version of the action bar into apps supporting earlier versions of Android, so
that the action bar would exist on any device, even those that did not support the native action bar.
On devices running Android 2.3 or older, menus and their corresponding XML did exist, but the
android:showAsAction
attribute was only added with the release of the action bar.

Defining a menu in XML
## 241
The AppCompat library defines its own custom showAsAction attribute and does not look for the
native showAsAction attribute.
## Using Android Asset Studio
In the
android:icon attribute, the value @android:drawable/ic_menu_add references a system icon. A
system icon is one that is found on the device rather than in your project’s resources.
In a prototype, referencing a system icon works fine. However, in an app that will be released, it is
better to be sure of what your user will see instead of leaving it up to each device. System icons can
change drastically across devices and OS versions, and some devices might have system icons that do
not fit with the rest of your app’s design.
One alternative is to create your own icons from scratch. You will need to prepare versions for each
screen density and possibly for other device configurations. For more information, visit Android’s Icon
Design Guidelines at http://developer.android.com/design/style/iconography.html.
A second alternative is to find system icons that meet your app’s needs and copy them directly into
your project’s drawable resources.
System icons can be found in your Android SDK directory. On a Mac, this is typically /Users/user/
Library/Android/sdk. On Windows, the default location is \Users\user\sdk. You can also verify
your SDK location by opening the Project Structure window and selecting the SDK Location option.
In your SDK directory, you will find Android’s resources, including ic_menu_add. These resources are
found in /platforms/android-21/data/res where 21 represents the API level of the Android version.
The third and easiest alternative is to use the Android Asset Studio, which is included in Android
Studio. The Asset Studio allows you to create and customize an image to use in the Toolbar.
Right-click on your drawable directory in the Project Tool window and select
New → Image Asset to
bring up the Asset Studio (
## Figure 13.6).

## Chapter 13  The Toolbar
## 242
## Figure 13.6  Asset Studio
Here, you can generate a few types of icons. In the Asset Type: field, choose Action Bar and Tab Icons.
Next, change the Foreground option to Clipart and select Choose to pick your clipart.
In the clipart window, choose the image that looks like a plus sign (Figure 13.7).
Figure 13.7  Clipart options – Where is that plus sign?

Defining a menu in XML
## 243
Finally, name your asset: ic_menu_add and select next (Figure 13.8).
Figure 13.8  Asset Studio’s generated files
Next, the Asset Studio will ask you which module and directory to add the image to. Stick to the
defaults to add this image to your app module. This window also provides a preview of the work that
Asset Studio will do. Notice that an mdpi, hdpi, xhdpi, and xxhdpi icon will be created for you. Jim-
dandy.
Select Finish to generate the images. Then, in your layout file, modify your icon attribute to reference
the new resource in your own project.
Listing 13.7  Referencing a local resource (menu/fragment_crime_list.xml)
## <item

android:id="@+id/menu_item_new_crime"
android:icon="@android:drawable/ic_menu_add"
android:icon="@drawable/ic_menu_add"

android:title="@string/new_crime"
app:showAsAction="ifRoom|withText"/>

## Chapter 13  The Toolbar
## 244
Creating the menu
In code, menus are managed by callbacks from the Activity class. When the menu is needed, Android
calls the Activity method onCreateOptionsMenu(Menu).
However, your design calls for code to be implemented in a fragment, not an activity. Fragment comes
with its own set of menu callbacks, which you will implement in CrimeListFragment. The methods for
creating the menu and responding to the selection of an action item are:
public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
public boolean onOptionsItemSelected(MenuItem item)
In CrimeListFragment.java, override onCreateOptionsMenu(Menu, MenuInflater) to inflate the
menu defined in fragment_crime_list.xml.
Listing 13.8  Inflating a menu resource (CrimeListFragment.java)
@Override
public void onResume() {

super.onResume();
updateUI();
## }
@Override
public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

super.onCreateOptionsMenu(menu, inflater);

inflater.inflate(R.menu.fragment_crime_list, menu);
## }
Within this method, you call MenuInflater.inflate(int, Menu) and pass in the resource ID of your
menu file. This populates the Menu instance with the items defined in your file.
Notice that you call through to the superclass implementation of onCreateOptionsMenu(...). This
is not required, but we recommend calling through as a matter of convention. That way, any menu
functionality defined by the superclass will still work. However, it is only a convention – the base
## Fragment
implementation of this method does nothing.
The FragmentManager is responsible for calling Fragment.onCreateOptionsMenu(Menu,
MenuInflater) when the activity receives its onCreateOptionsMenu(...) callback from the
OS. You must explicitly tell the FragmentManager that your fragment should receive a call to
onCreateOptionsMenu(...). You do this by calling the following method:
public void setHasOptionsMenu(boolean hasMenu)
Define CrimeListFragment.onCreate(...) and let the FragmentManager know that
CrimeListFragment needs to receive menu callbacks.

Creating the menu
## 245
Listing 13.9  Receiving menu callbacks (
CrimeListFragment.java)
## ...
private RecyclerView mCrimeRecyclerView;
private CrimeAdapter mAdapter;
@Override
public void onCreate(Bundle savedInstanceState) {

super.onCreate(savedInstanceState);
setHasOptionsMenu(true);
## }
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
## ...
You can run CriminalIntent now to see your menu (
## Figure 13.9).
Figure 13.9  Icon for the add crime action item directly in the toolbar
Where is the action item’s text? Most phones have enough room only for the icon in portrait
orientation. You can long-press an icon in the toolbar to reveal its title (Figure 13.10).

## Chapter 13  The Toolbar
## 246
Figure 13.10  Long-pressing an icon in the toolbar shows the title
In landscape orientation, there is room in the toolbar for the icon and the text (Figure 13.11).
Figure 13.11  Icon and text in the toolbar
Responding to menu selections
To respond to the user pressing the New Crime action item, you need a way to add a new Crime to your
list of crimes. In CrimeLab.java, add the following method that adds a Crime to the list.

Responding to menu selections
## 247
Listing 13.10  Adding a new crime (
CrimeLab.java)

## ...

public void addCrime(Crime c) {
mCrimes.add(c);
## }

public List<Crime> getCrimes() {
return mCrimes;
## }

## ...
In this brave new world where you will be able to add crimes yourself, the 100 programmatically
generated crimes are no longer necessary. Remove the code that generates these crimes from
CrimeLab.java.
Listing 13.11  Goodbye, random crimes! (
CrimeLab.java)
private CrimeLab(Context context) {
mCrimes = new ArrayList<>();
for (int i = 0; i < 100; i++) {
Crime crime = new Crime();
crime.setTitle("Crime #" + i);
crime.setSolved(i % 2 == 0);
mCrimes.add(crime);
## }
## }
When the user presses an action item, your fragment receives a callback to the method
onOptionsItemSelected(MenuItem). This method receives an instance of MenuItem
that describes the
user’s selection.
Although your menu only contains one action item, menus often have more than one. You can
determine which action item has been selected by checking the ID of the MenuItem and then respond
appropriately. This ID corresponds to the ID you assigned to the MenuItem in your menu file.
In CrimeListFragment.java, implement onOptionsItemSelected(MenuItem) to respond to selection
of the MenuItem. You will create a new Crime, add it to CrimeLab, and then start an instance of
CrimePagerActivity to edit the new Crime.

## Chapter 13  The Toolbar
## 248
Listing 13.12  Responding to menu selection (
CrimeListFragment.java)
@Override
public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
super.onCreateOptionsMenu(menu, inflater);
inflater.inflate(R.menu.fragment_crime_list, menu);
## }
@Override
public boolean onOptionsItemSelected(MenuItem item) {

switch (item.getItemId()) {
case R.id.menu_item_new_crime:

Crime crime = new Crime();
CrimeLab.get(getActivity()).addCrime(crime);
Intent intent = CrimePagerActivity
.newIntent(getActivity(), crime.getId());

startActivity(intent);
return true;

default:
return super.onOptionsItemSelected(item);
## }
## }
Notice that this method returns a boolean value. Once you have handled the MenuItem, you should
return
true to indicate that no further processing is necessary. The default case calls the superclass
implementation if the item ID is not in your implementation.
Run CriminalIntent and try out your new menu. Add a few crimes and edit them afterward. (The empty
list that you see before you add any crimes can be disconcerting. At the end of this chapter there is a
challenge to present a helpful clue when the list is empty.)
## Enabling Hierarchical Navigation
So far, CriminalIntent relies heavily on the Back button to navigate around the app. Using the Back
button is
temporal navigation. It takes you to where you were last. Hierarchical navigation, on the
other hand, takes you up the app hierarchy. (It is sometimes called ancestral navigation.)
In hierarchical navigation, the user navigates up by pressing the Up button on the left side of the
toolbar. Prior to Jelly Bean (API level 16), developers had to manually show the Up button and
manually handle presses on the Up button. As of Jelly Bean, there is a much easier way to add this
functionality.
Enable hierarchical navigation in CriminalIntent by adding a parentActivityName attribute in the
AndroidManifest.xml file.
Listing 13.13  Turn on the Up button (AndroidManifest.xml)
## ...
## <activity

android:name=".CrimePagerActivity"

android:label="@string/app_name"
android:parentActivityName=".CrimeListActivity">
## </activity>
## ...

How hierarchical navigation works
## 249
Run the app and create a new crime. Notice the Up button, as shown in Figure 13.12. Pressing the Up
button will take you up one level in CriminalIntent’s hierarchy to CrimeListActivity.
Figure 13.12  CrimePagerActivity’s Up button
How hierarchical navigation works
In CriminalIntent, navigating with the Back button and navigating with the Up button perform the
same task. Pressing either of those from within the CrimePagerActivity will take the user back to the
CrimeListActivity. Even though they accomplish the same result, behind the scenes they are doing
very different things. This is important because, depending on the application, navigating up may pop
the user back multiple activities in the back stack.
When the user navigates up from CrimeActivity, an intent like the following is created:

Intent intent = new Intent(this, CrimeListActivity.class);

intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
startActivity(intent);
finish();
## FLAG_ACTIVITY_CLEAR_TOP
tells Android to look for an existing instance of the activity in the stack,
and if there is one, pop every other activity off the stack so that the activity being started will be top-
most (Figure 13.13).
Figure 13.13  FLAG_ACTIVITY_CLEAR_TOP at work
## An Alternative Action Item
In this section, you will use what you have learned about menu resources to add an action item that lets
users show and hide the subtitle of
CrimeListActivity’s toolbar.
## In
res/menu/fragment_crime_list.xml, add an action item that will read Show Subtitle and will
appear in the toolbar if there is room.

## Chapter 13  The Toolbar
## 250
Listing 13.14  Adding Show Subtitle action item (
res/menu/
fragment_crime_list.xml)
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto">
## <item

android:id="@+id/menu_item_new_crime"
android:icon="@android:drawable/ic_menu_add"
android:title="@string/new_crime"
app:showAsAction="ifRoom|withText"/>

## <item
android:id="@+id/menu_item_show_subtitle"
android:title="@string/show_subtitle"
app:showAsAction="ifRoom"/>
## </menu>
The subtitle will display the number of crimes in CriminalIntent. Create a new method,
updateSubtitle(), that will set the subtitle of the toolbar.
Listing 13.15  Setting the toolbar’s subtitle (
CrimeListFragment.java)
@Override
public boolean onOptionsItemSelected(MenuItem item) {
## ...
## }
private void updateSubtitle() {

CrimeLab crimeLab = CrimeLab.get(getActivity());
int crimeCount = crimeLab.getCrimes().size();

String subtitle = getString(R.string.subtitle_format, crimeCount);

AppCompatActivity activity = (AppCompatActivity) getActivity();

activity.getSupportActionBar().setSubtitle(subtitle);
## }
updateSubtitle first generates the subtitle string using the getString(int resId, Object...
formatArgs) method, which accepts replacement values for the placeholders in the string resource.
Next, the activity that is hosting the CrimeListFragment is cast to an AppCompatActivity.
CriminalIntent uses the AppCompat library, so all activities will be a subclass of AppCompatActivity,
which allows you to access the toolbar. For legacy reasons, the toolbar is still referred to as “action
bar” in many places within the AppCompat library.
Now that updateSubtitle is defined, call the method when the user presses on the new action item.

Toggling the action item title
## 251
Listing 13.16  Responding to Show Subtitle action item
## (
CrimeListFragment.java)
@Override
public boolean onOptionsItemSelected(MenuItem item) {
switch (item.getItemId()) {
case R.id.menu_item_new_crime:

## ...
case R.id.menu_item_show_subtitle:
updateSubtitle();
return true;
default:

return super.onOptionsItemSelected(item);
## }
## }
Run CriminalIntent, press the Show Subtitle item, and confirm that you can see the number of crimes
in the subtitle.
Toggling the action item title
Now the subtitle is visible, but the action item still reads Show Subtitle. It would be better if the action
item toggled its title and function to show or hide the subtitle.
When onOptionsItemSelected(MenuItem) is called, you are given the MenuItem that the user pressed
as a parameter. You could update the text of the Show Subtitle item in this method, but the subtitle
change would be lost as you rotate the device and the toolbar is re-created.
A better solution is to update the Show Subtitle MenuItem in onCreateOptionsMenu(...) and trigger a
re-creation of the toolbar when the user presses on the subtitle item. This allows you to share the code
for updating the action item in the case that the user selects an action item or the toolbar is re-created.
First, add a member variable to keep track of the subtitle visibility.
Listing 13.17  Keeping subtitle visibility state (CrimeListFragment.java)
public class CrimeListFragment extends Fragment {
private RecyclerView mCrimeRecyclerView;

private CrimeAdapter mAdapter;
private boolean mSubtitleVisible;
## ...
Next, modify the subtitle in onCreateOptionsMenu(...) and trigger a re-creation of the action items
when the user presses on the Show Subtitle action item.

## Chapter 13  The Toolbar
## 252
Listing 13.18  Updating a
MenuItem (CrimeListFragment.java)
@Override
public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
super.onCreateOptionsMenu(menu, inflater);
inflater.inflate(R.menu.fragment_crime_list, menu);
MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);

if (mSubtitleVisible) {
subtitleItem.setTitle(R.string.hide_subtitle);
} else {
subtitleItem.setTitle(R.string.show_subtitle);
## }
## }
@Override
public boolean onOptionsItemSelected(MenuItem item) {

switch (item.getItemId()) {
case R.id.menu_item_new_crime:

## ...
case R.id.menu_item_show_subtitle:

mSubtitleVisible = !mSubtitleVisible;
getActivity().invalidateOptionsMenu();
updateSubtitle();

return true;
default:

return super.onOptionsItemSelected(item);
## }
## }
Finally, respect the
mSubtitleVisible member variable when showing or hiding the subtitle in the
toolbar.
Listing 13.19  Showing or hiding the subtitle (CrimeListFragment.java)
private void updateSubtitle() {
CrimeLab crimeLab = CrimeLab.get(getActivity());
int crimeCount = crimeLab.getCrimes().size();

String subtitle = getString(R.string.subtitle_format, crimeCount);

if (!mSubtitleVisible) {
subtitle = null;
## }

AppCompatActivity activity = (AppCompatActivity) getActivity();
activity.getSupportActionBar().setSubtitle(subtitle);
## }
Run CriminalIntent and modify the subtitle visibility in the toolbar. Notice that the action item text
reflects the existence of the subtitle.
“Just one more thing...”
Programming in Android is often like being questioned by the TV detective Columbo. You think you
have the angles covered and are home free. But Android always turns at the door and says,
“Just one
more thing...”

“Just one more thing...”
## 253
Here, there are actually two more things. First, when creating a new crime and then returning to
CrimeListActivity with the Back button, the number of crimes in the subtitle will not update to
reflect the new number of crimes. Second, the visibility of the subtitle is lost across rotation.
Tackle the update issue first. The solution to this problem is to update the subtitle text when returning
to CrimeListActivity. Trigger a call to updateSubtitle in onResume. Your updateUI method is
already called in onResume and onCreate. Add a call to updateSubtitle to the updateUI method.
Listing 13.20  Showing the most recent state (CrimeListFragment.java)
private void updateUI() {

CrimeLab crimeLab = CrimeLab.get(getActivity());

List<Crime> crimes = crimeLab.getCrimes();

if (mAdapter == null) {

mAdapter = new CrimeAdapter(crimes);
mCrimeRecyclerView.setAdapter(mAdapter);

} else {
mAdapter.notifyDataSetChanged();
## }
updateSubtitle();
## }
Run CriminalIntent, show the subtitle, create a new crime, and press the Back button on the device to
return to CrimeListActivity. The number of crimes in the toolbar will be correct.
Now repeat these steps, but instead of using the Back button use the Up button. The visibility of the
subtitle will be reset. Why does this happen?
An unfortunate side effect of the way hierarchical navigation is implemented in Android is that the
activity that you navigate up to will be completely re-created from scratch. This means that any
instance variables will be lost and it also means that any saved instance state will be lost as well. This
parent activity is seen as a completely new activity.
There is not an easy way to ensure that the subtitle stays visible when navigating up. One option
is to override the mechanism that navigates up. In CriminalIntent, you could call finish on the
CrimePagerActivity to pop back to the previous activity. This would work perfectly well in
CriminalIntent but would not work in apps with a more realistic hierarchy, as this would only pop back
one activity.
Another option is to pass information about the subtitle visibility as an extra to CrimePagerActivity
when it is started. Then, override the getParentActivityIntent() method in CrimePagerActivity
to add an extra to the intent that is used to re-create the CrimeListActivity. This solution requires
CrimePagerActivity to know the details of how its parent works.
Both of these solutions are less than ideal, and there is not a great alternative.
Now that the subtitle always displays the correct number of crimes, solve the rotation issue. To fix this
problem, save the mSubtitleVisible instance variable across rotation with the saved instance state
mechanism.

## Chapter 13  The Toolbar
## 254
Listing 13.21  Saving subtitle visibility (
CrimeListFragment.java)
public class CrimeListFragment extends Fragment {
private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
## ...

@Override

public View onCreateView(LayoutInflater inflater, ViewGroup container,
Bundle savedInstanceState) {
## ...
if (savedInstanceState != null) {

mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);

## }

updateUI();


return view;

## }

@Override

public void onResume() {
## ...

## }

@Override

public void onSaveInstanceState(Bundle outState) {
super.onSaveInstanceState(outState);

outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
## }
## }
Run CriminalIntent. Show the subtitle and then rotate. The subtitle should appear as expected in the re-
created view.
For the More Curious: Toolbar vs Action Bar
What is the difference between the toolbar and the action bar?
The most obvious difference between the two is the updated visual design of the toolbar. The toolbar
no longer includes an icon on the left side and decreases some of the spacing between the action items
on the right side. Another major visual change is the Up button. In the action bar, this button was much
more subtle and was just an accessory next to the icon in the action bar.
Aside from the visual differences, the main goal of the toolbar is to be more flexible than the action
bar. The action bar has many constraints. It will always appear at the top of the screen. There can only
be one action bar. The size of the action bar is fixed and should not be changed. The toolbar does not
have these constraints.
In this chapter, you used a toolbar that was provided by one of the AppCompat themes. Alternatively,
you can manually include a toolbar as a normal view in your activity or fragment’s layout file. You
can place this toolbar anywhere you like and you can even include multiple toolbars on the screen at
the same time. This flexibility allows for interesting designs; for example, imagine if each fragment
that you use maintains its own toolbar. When you host multiple fragments on the screen at the same

## Challenge: Deleting Crimes
## 255
time, each of them can bring along their own toolbar instead of sharing a single toolbar at the top of the
screen.
Another interesting addition with the toolbar is the ability to place Views inside of the toolbar and to
also adjust the height of the toolbar. This allows for much more flexibility in the way that your app
works.
## Challenge: Deleting Crimes
Once a crime has been created in CriminalIntent, there is no way to erase that crime from the official
record. For this challenge, add a new action item to the CrimeFragment that allows the user to delete
the current crime. Once the user presses the new delete action item, be sure to pop the user back to the
previous activity with a call to the finish
method on the CrimeFragment’s hosting activity.
## Challenge: Plural String Resources
The subtitle is not grammatically correct when there is a single crime. “1 crimes” just does not show
the right amount of attention to detail for your taste. For this challenge, correct this subtitle text.
You could have two different strings and determine which one to use in code, but this will quickly
fall apart when you localize your app for different languages. A better option is to use plural string
resources (sometimes also called quantity strings).
First, define a plural string in your strings.xml file.
<plurals name="subtitle_plural">
<item quantity="one">%1$s crime</item>
<item quantity="other">%1$s crimes</item>
## </plurals>
Then, use the getQuantityString method to correctly pluralize the string.
int crimeSize = crimeLab.getCrimes().size();
String subtitle = getResources()

.getQuantityString(R.plurals.subtitle_plural, crimeSize, crimeSize);
Challenge: An Empty View for the RecyclerView
Currently, when CriminalIntent launches it displays an empty RecyclerView – a big white void. You
should give users something to interact with when there are no items in the list.
For this challenge, display a message like,
“There are no crimes” and add a button to the view that will
trigger the creation of a new crime.
Use the setVisibility method that exists on any View class to show and hide this new placeholder
view when appropriate.