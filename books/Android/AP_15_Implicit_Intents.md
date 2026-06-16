

## 273
## 15
## Implicit Intents
In Android, you can start an activity in another application on the device using an implicit intent. In an
explicit intent, you specify the class of the activity to start, and the OS will start it. In an implicit intent,
you describe the job that you need done, and the OS will start an activity in an appropriate application
for you.
In CriminalIntent, you will use implicit intents to enable picking a suspect for a Crime
from the
user’s list of contacts and sending a text-based report of a crime. The user will choose a suspect from
whatever contacts app is installed on the device and will be offered a choice of apps to send the crime
report (Figure 15.1).
Figure 15.1  Opening contacts app and a text-sending app
Using implicit intents to harness other applications is far easier than writing your own implementations
for common tasks. Users also appreciate being able to use apps they already know and like in
conjunction with your app.

## Chapter 15  Implicit Intents
## 274
Before you can create these implicit intents, there is some setup to do in CriminalIntent:
- add Choose Suspect and Send Crime Report buttons to CrimeFragment’s layouts
- add an mSuspect field to the Crime class that will hold the name of a suspect
- create a crime report using a set of format resource strings
## Adding Buttons
You are going to start by updating CrimeFragment’s layouts to include new buttons for accusation
and tattling: namely, a suspect button and a report button. First, add the strings that these buttons will
display.
Listing 15.1  Adding button strings (
strings.xml)
## ...

<string name="subtitle_format">%1$s crimes</string>
<string name="crime_suspect_text">Choose Suspect</string>

<string name="crime_report_text">Send Crime Report</string>
## </resources>
In layout/fragment_crime.xml, add two button widgets, as shown in Figure 15.2. Notice that in this
diagram we are not showing the first LinearLayout and all of its children so that you can focus on the
new and interesting parts of the diagram on the right.
Figure 15.2  Adding suspect and crime report buttons (layout/
fragment_crime.xml)

## Adding Buttons
## 275
In the landscape layout, you are going to make these new buttons children of a new horizontal
LinearLayout below the one that contains the date button and the checkbox. Figure 15.3 shows the
new layout.
Figure 15.3  New landscape layout
In layout-land/fragment_crime.xml, add the LinearLayout and two button widgets, as shown in
## Figure 15.4.
Figure 15.4  Adding suspect and crime report buttons (layout-land/
fragment_crime.xml)

## Chapter 15  Implicit Intents
## 276
At this point, you can preview the layouts or run CriminalIntent to confirm that your new buttons are in
place.
Adding a Suspect to the Model Layer
Next, open
Crime.java and add a new member variable to give Crime a field that will hold the name of
a suspect.
Listing 15.2  Adding suspect field (Crime.java)
public class Crime {
## ...
private boolean mSolved;

private String mSuspect;

public Crime() {

this(UUID.randomUUID());
## }
## ...
public void setSolved(boolean solved) {
mSolved = solved;

## }

public String getSuspect() {

return mSuspect;
## }
public void setSuspect(String suspect) {
mSuspect = suspect;

## }
## }
Now you need to add an additional field to your crime database. First, add a suspect column to
CrimeDbSchema.
Listing 15.3  Adding suspect column (CrimeDbSchema.java)
public class CrimeDbSchema {
public static final class CrimeTable {

public static final String NAME = "crimes";
public static final class Cols {
public static final String UUID = "uuid";
public static final String TITLE = "title";

public static final String DATE = "date";
public static final String SOLVED = "solved";
public static final String SUSPECT = "suspect";
## }

## }
## }
Add the column in CrimeBaseHelper, also. (Notice that the new code begins with a comma after
CrimeTable.Cols.SOLVED.)

Adding a Suspect to the Model Layer
## 277
Listing 15.4  Adding suspect column again (CrimeBaseHelper.java)
@Override
public void onCreate(SQLiteDatabase db) {

db.execSQL("create table " + CrimeTable.NAME + "(" +

" _id integer primary key autoincrement, " +
CrimeTable.Cols.UUID + ", " +
CrimeTable.Cols.TITLE + ", " +
CrimeTable.Cols.DATE + ", " +
CrimeTable.Cols.SOLVED + ", " +

CrimeTable.Cols.SUSPECT +
## ")"
## );
## }
Next, write to the new column in
CrimeLab.getContentValues(Crime).
Listing 15.5  Writing to suspect column (CrimeLab.java)
## ...
private static ContentValues getContentValues(Crime crime) {

ContentValues values = new ContentValues();
values.put(CrimeTable.Cols.UUID, crime.getId().toString());

values.put(CrimeTable.Cols.TITLE, crime.getTitle());
values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());

values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
values.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());
return values;
## }
## ...
Now read from it in
CrimeCursorWrapper.
Listing 15.6  Reading from suspect column (CrimeCursorWrapper.java)

## ...
public Crime getCrime() {
String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));

String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
String suspect = getString(getColumnIndex(CrimeTable.Cols.SUSPECT));

Crime crime = new Crime(UUID.fromString(uuidString));
crime.setTitle(title);
crime.setDate(new Date(date));
crime.setSolved(isSolved != 0);

crime.setSuspect(suspect);

return crime;

## }
## }

## Chapter 15  Implicit Intents
## 278
If CriminalIntent is already installed on your device, your existing database will not have the suspect
column, and your new onCreate(SQLiteDatabase) will not be run to add the new column, either.
The easiest solution is to wipe out your old database in favor of a new one. (This happens a lot in app
development.)
First, uninstall the CriminalIntent app by opening the app launcher screen and dragging the
CriminalIntent icon to the top of the screen. All your sandbox storage will get blown away, along
with the out-of-date database schema, as part of the uninstall process. Next, run CriminalIntent from
Android Studio. A new database will be created with the new column as part of the app installation
process.
Using a Format String
The last preliminary is to create a template crime report that can be configured with the specific
crime’s details. Because you will not know a crime’s details until runtime, you must use a format string
with placeholders that can be replaced at runtime. Here is the format string you will use:
<string name="crime_report">%1$s! The crime was discovered on %2$s. %3$s, and %4$s
## The
%1$s, %2$s, etc. are placeholders that expect string arguments. In code, you will call getString(...)
and pass in the format string and four other strings in the order in which they should replace the
placeholders.
First, in strings.xml, add the strings shown in Listing 15.7.
Listing 15.7  Adding string resources (strings.xml)
<string name="crime_suspect_text">Choose Suspect</string>
<string name="crime_report_text">Send Crime Report</string>

<string name="crime_report">%1$s!
The crime was discovered on %2$s. %3$s, and %4$s

## </string>
<string name="crime_report_solved">The case is solved</string>
<string name="crime_report_unsolved">The case is not solved</string>

<string name="crime_report_no_suspect">there is no suspect.</string>

<string name="crime_report_suspect">the suspect is %s.</string>
<string name="crime_report_subject">CriminalIntent Crime Report</string>
<string name="send_report">Send crime report via</string>
## </resources>
In CrimeFragment.java, add a method that creates four strings and then pieces them together and
returns a complete report.

## Using Implicit Intents
## 279
## Listing 15.8  Adding
getCrimeReport() method (CrimeFragment.java)
## ...
private void updateDate() {
mDateButton.setText(mCrime.getDate().toString());
## }
private String getCrimeReport() {
String solvedString = null;

if (mCrime.isSolved()) {
solvedString = getString(R.string.crime_report_solved);
} else {
solvedString = getString(R.string.crime_report_unsolved);
## }

String dateFormat = "EEE, MMM dd";
String dateString = DateFormat.format(dateFormat, mCrime.getDate()).toString();
String suspect = mCrime.getSuspect();
if (suspect == null) {

suspect = getString(R.string.crime_report_no_suspect);
} else {
suspect = getString(R.string.crime_report_suspect, suspect);

## }
String report = getString(R.string.crime_report,
mCrime.getTitle(), dateString, solvedString, suspect);
return report;
## }
(Note that there are two DateFormat classes: android.text.format.DateFormat, and
java.text.DateFormat. Use android.text.format.DateFormat.)
Now the preliminaries are complete, and you can turn to implicit intents.
## Using Implicit Intents
An Intent is an object that describes to the OS something that you want it to do. With the explicit
intents that you have created thus far, you explicitly name the activity that you want the OS to start.
Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
intent.putExtra(EXTRA_CRIME_ID, crimeId);
startActivity(intent);
With an
implicit intent, you describe to the OS the job that you want done. The OS then starts the
activity that has advertised itself as capable of doing that job. If the OS finds more than one capable
activity, then the user is offered a choice.

## Chapter 15  Implicit Intents
## 280
Parts of an implicit intent
Here are the critical parts of an intent that you can use to define the job you want done:
the action that you are trying to perform
These are typically constants from the Intent class. If you want to view a URL, you can use
Intent.ACTION_VIEW for your action. To send something, you use Intent.ACTION_SEND.
the location of any data
This can be something outside the device, like the URL of a web page, but it can also be a URI
to a file or a
content URI pointing to a record in a ContentProvider.
the type of data that the action is for
This is a MIME type, like text/html
or audio/mpeg3. If an intent includes a location for data,
then the type can usually be inferred from that data.
optional categories
If the action is used to describe what to do, the category usually describes
where
, when, or how you are trying to use an activity. Android uses the category
android.intent.category.LAUNCHER to indicate that an activity should be displayed in the top-
level app launcher. The android.intent.category.INFO category, on the other hand, indicates
an activity that shows information about a package to the user but should not show up in the
launcher.
A simple implicit intent for viewing a website would include an action of Intent.ACTION_VIEW and a
data Uri that is the URL of a website.
Based on this information, the OS will launch the appropriate activity of an appropriate application. (If
it finds more than one candidate, the user gets a choice.)
An activity would advertise itself as an appropriate activity for ACTION_VIEW via an intent filter in the
manifest. If you wanted to write a browser app, for instance, you would include the following intent
filter in the declaration of the activity that should respond to ACTION_VIEW:
## <activity

android:name=".BrowserActivity"
android:label="@string/app_name" >
## <intent-filter>
<action android:name="android.intent.action.VIEW" />

<category android:name="android.intent.category.DEFAULT" />
<data android:scheme="http" android:host="www.bignerdranch.com" />
## </intent-filter>
## </activity>
To respond to implicit intents, a DEFAULT category must be set explicitly in an intent filter. The action
element in the intent filter tells the OS that the activity is capable of performing the job, and the
DEFAULT category tells the OS that this activity should be considered for the job when the OS is asking

Sending a crime report
## 281
for volunteers. This DEFAULT category is implicitly added to every implicit intent. (In Chapter 22, you
will see that this is not the case when Android is not asking for a volunteer.)
Implicit intents can also include extras just like explicit intents. Any extras on an implicit intent,
however, are not used by the OS to find an appropriate activity.
Note that the action and data parts of an intent can also be used in conjunction with an explicit intent.
That would be the equivalent of telling a particular activity to do something specific.
Sending a crime report
Let’s see how this works by creating an implicit intent to send a crime report in CriminalIntent. The
job you want done is sending plain text; the crime report is a string. So the implicit intent’s action will
be ACTION_SEND. It will not point to any data or have any categories, but it will specify a type of text/
plain.
In CrimeFragment.onCreateView(...), get a reference to the Send Crime Report button and set
a listener on it. Within the listener’s implementation, create an implicit intent and pass it into
startActivity(Intent).
Listing 15.9  Sending a crime report (CrimeFragment.java)
private Crime mCrime;
private EditText mTitleField;
private Button mDateButton;
private CheckBox mSolvedCheckbox;
private Button mReportButton;
## ...
public View onCreateView(LayoutInflater inflater, ViewGroup container,

Bundle savedInstanceState) {
## ...
mReportButton = (Button) v.findViewById(R.id.crime_report);

mReportButton.setOnClickListener(new View.OnClickListener() {

public void onClick(View v) {
Intent i = new Intent(Intent.ACTION_SEND);
i.setType("text/plain");
i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
i.putExtra(Intent.EXTRA_SUBJECT,

getString(R.string.crime_report_subject));
startActivity(i);
## }
## });

return v;
## }
Here you use the Intent constructor that accepts a string that is a constant defining the action. There
are other constructors that you can use depending on what kind of implicit intent you need to create.
You can find them all on the Intent reference page in the documentation. There is no constructor that
accepts a type, so you set it explicitly.

## Chapter 15  Implicit Intents
## 282
You include the text of the report and the string for the subject of the report as extras. Note that these
extras use constants defined in the Intent class. Any activity responding to this intent will know these
constants and what to do with the associated values.
Run CriminalIntent and press the Send Crime Report button. Because this intent will likely match
many activities on the device, you will probably see a list of activities presented in a chooser
(Figure 15.5).
Figure 15.5  Activities volunteering to send your crime report
If you are offered a choice, make a selection. You will see your crime report loaded into the app that
you chose. All you have to do is address and send it.
If, on the other hand, you do not see a chooser, that means one of two things. Either you have already
set a default app for an identical implicit intent, or your device has only a single activity that can
respond to this intent.
Often, it is best to go with the user’s default app for an action. In CriminalIntent, however, you always
want the user to have a choice for
ACTION_SEND. Today a user might want to be discreet and email the
crime report, but tomorrow he or she may prefer public shaming via Twitter.
You can create a chooser to be shown every time an implicit intent is used to start an activity. After
you create your implicit intent as before, you call the following Intent method and pass in the implicit
intent and a string for the chooser’s title:

public static Intent createChooser(Intent target, String title)
Then you pass the intent returned from
createChooser(...) into startActivity(...).
## In
CrimeFragment.java, create a chooser to display the activities that respond to your implicit intent.

Asking Android for a contact
## 283
Listing 15.10  Using a chooser (CrimeFragment.java)
public void onClick(View v) {

Intent i = new Intent(Intent.ACTION_SEND);
i.setType("text/plain");
i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
i.putExtra(Intent.EXTRA_SUBJECT,
getString(R.string.crime_report_subject));

i = Intent.createChooser(i, getString(R.string.send_report));
startActivity(i);
## }
Run CriminalIntent and press the Send Crime Report button. As long as you have more than one
activity that can handle your intent, you will be offered a list to choose from (Figure 15.6).
Figure 15.6  Sending text with a chooser
Asking Android for a contact
Now you are going to create another implicit intent that enables users to choose a suspect
from their contacts. This implicit intent will have an action and a location where the relevant
data can be found. The action will be Intent.ACTION_PICK. The data for contacts is at
ContactsContract.Contacts.CONTENT_URI. In short, you are asking Android to help pick an item in
the contacts database.
You expect a result back from the started activity, so you will pass the intent via
startActivityForResult(...) along with a request code. In CrimeFragment.java, add a constant for
the request code and a member variable for the button.

## Chapter 15  Implicit Intents
## 284
Listing 15.11  Adding field for suspect button (
CrimeFragment.java)
## ...
private static final int REQUEST_DATE = 0;
private static final int REQUEST_CONTACT = 1;
## ...
private CheckBox mSolvedCheckbox;
private Button mSuspectButton;
## ...
At the end of
onCreateView(...), get a reference to the button and set a listener on it. Within the
listener’s implementation, create the implicit intent and pass it into startActivityForResult(...).
Also, once a suspect is assigned show the name on the suspect button.
Listing 15.12  Sending an implicit intent (CrimeFragment.java)
public View onCreateView(LayoutInflater inflater, ViewGroup container,
Bundle savedInstanceState) {

## ...
final Intent pickContact = new Intent(Intent.ACTION_PICK,
ContactsContract.Contacts.CONTENT_URI);
mSuspectButton = (Button) v.findViewById(R.id.crime_suspect);

mSuspectButton.setOnClickListener(new View.OnClickListener() {
public void onClick(View v) {

startActivityForResult(pickContact, REQUEST_CONTACT);
## }
## });
if (mCrime.getSuspect() != null) {

mSuspectButton.setText(mCrime.getSuspect());
## }
return v;
## }
You will be using
pickContact one more time in a bit, which is why you put it outside
mSuspectButton’s OnClickListener.
Run CriminalIntent and press the Choose Suspect button. You should see a list of contacts
(Figure 15.7).

Asking Android for a contact
## 285
Figure 15.7  A list of possible suspects
If you have a different contacts app installed, your screen will look different. Again, this is one of the
benefits of implicit intents. You do not have to know the name of the contacts application to use it from
your app. Users can install whatever app they like best, and the OS will find and launch it.
Getting the data from the contact list
Now you need to get a result back from the contacts application. Contacts information is shared
by many applications, so Android provides an in-depth API for working with contacts information
through a
ContentProvider. Instances of this class wrap databases and make it available to other
applications. You can access a ContentProvider through a ContentResolver.
Because you started the activity for a result with ACTION_PICK, you will receive an intent via
onActivityResult(...). This intent includes a data URI. The URI is a locator that points at the single
contact the user picked.
In CrimeFragment.java, add the following code to your onActivityResult(...) implementation in
CrimeFragment.

## Chapter 15  Implicit Intents
## 286
Listing 15.13  Pulling contact name out (
CrimeFragment.java)
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
if (resultCode != Activity.RESULT_OK) {
return;
## }

if (requestCode == REQUEST_DATE) {
## ...
updateDate();
} else if (requestCode == REQUEST_CONTACT && data != null) {

Uri contactUri = data.getData();
// Specify which fields you want your query to return
// values for.
String[] queryFields = new String[] {

ContactsContract.Contacts.DISPLAY_NAME
## };

// Perform your query - the contactUri is like a "where"
// clause here
Cursor c = getActivity().getContentResolver()

.query(contactUri, queryFields, null, null, null);
try {
// Double-check that you actually got results
if (c.getCount() == 0) {

return;
## }
// Pull out the first column of the first row of data -
// that is your suspect's name.

c.moveToFirst();
String suspect = c.getString(0);

mCrime.setSuspect(suspect);
mSuspectButton.setText(suspect);
} finally {

c.close();
## }

## }
## }
## In
Listing 15.13, you create a query that asks for all the display names of the contacts in the returned
data. Then you query the contacts database and get a Cursor object to work with. Because you know
that the cursor only contains one item, you move to the first item and get it as a string. This string will
be the name of the suspect, and you use it to set the Crime’s suspect and the text of the Choose Suspect
button.
(The contacts database is a large topic in itself. We will not cover it here. If you would like to know
more, read the Contacts Provider API guide: http://developer.android.com/guide/topics/
providers/contacts-provider.html.)
Go ahead and run your app. Some devices may not have a contacts app for you to use. If that is the
case, use an emulator to test this code.

Checking for responding activities
## 287
Contacts permissions
How are you getting permission to read from the contacts database? The contacts app is extending
its permissions to you. The contacts app has full permissions to the contacts database. When
the contacts app returns a data URI in an Intent
to the parent activity, it also adds the flag
Intent.FLAG_GRANT_READ_URI_PERMISSION. This flag signals to Android that the parent activity in
CriminalIntent should be allowed to use this data one time. This works well because you do not really
need access to the entire contacts database. You only need access to one contact inside that database.
Checking for responding activities
The first implicit intent you created in this chapter will always be responded to in some way – there
may be no way to send a report, but the chooser will still display properly. However, that is not the case
for the second example: some devices or users may not have a contacts app, and if the OS cannot find a
matching activity, then the app will crash.
The fix is to check with part of the OS called the
PackageManager first. Do this in onCreateView(...).
Listing 15.14  Guarding against no contacts app (CrimeFragment.java)
public View onCreateView(LayoutInflater inflater, ViewGroup container,
Bundle savedInstanceState) {

## ...


if (mCrime.getSuspect() != null) {
mSuspectButton.setText(mCrime.getSuspect());
## }
PackageManager packageManager = getActivity().getPackageManager();

if (packageManager.resolveActivity(pickContact,
PackageManager.MATCH_DEFAULT_ONLY) == null) {
mSuspectButton.setEnabled(false);

## }
return v;
## }
PackageManager
knows about all the components installed on your Android device, including
all of its activities. (You will run into the other components later on in this book.) By calling
resolveActivity(Intent, int), you ask it to find an activity that matches the Intent you gave it.
The MATCH_DEFAULT_ONLY flag restricts this search to activities with the CATEGORY_DEFAULT flag, just
like startActivity(Intent) does.
If this search is successful, it will return an instance of ResolveInfo telling you all about which
activity it found. On the other hand, if the search returns null, the game is up – no contacts app. So
you disable the useless suspect button.
If you would like to verify that your filter works, but do not have a device without a contacts
application, temporarily add an additional category to your intent. This category does nothing, but it
will prevent any contacts applications from matching your intent.

## Chapter 15  Implicit Intents
## 288
Listing 15.15  Dummy code to verify filter (
CrimeFragment.java)

## ...


final Intent pickContact = new Intent(Intent.ACTION_PICK,
ContactsContract.Contacts.CONTENT_URI);
pickContact.addCategory(Intent.CATEGORY_HOME);
mSuspectButton = (Button)v.findViewById(R.id.crime_suspect);

mSuspectButton.setOnClickListener(new View.OnClickListener() {
public void onClick(View v) {
startActivityForResult(pickContact, REQUEST_CONTACT);
## }
## });


## ...
Now you should see the suspect button disabled (
## Figure 15.8).
Figure 15.8  Disabled suspect button
Delete the dummy code once you are done verifying this behavior.

Challenge: ShareCompat
## 289
Listing 15.16  Deleting dummy code (
CrimeFragment.java)

## ...


final Intent pickContact = new Intent(Intent.ACTION_PICK,
ContactsContract.Contacts.CONTENT_URI);
pickContact.addCategory(Intent.CATEGORY_HOME);
mSuspectButton = (Button)v.findViewById(R.id.crime_suspect);
mSuspectButton.setOnClickListener(new View.OnClickListener() {

public void onClick(View v) {
startActivityForResult(pickContact, REQUEST_CONTACT);
## }
## });


## ...
Challenge: ShareCompat
Your first challenge is an easy one. Android’s support library provides a class called ShareCompat, with
an inner class called IntentBuilder. ShareCompat.IntentBuilder makes it a bit easier to build the
exact kind of Intent you used for your report button.
So your first challenge is this: in mReportButton’s OnClickListener, use
ShareCompat.IntentBuilder to build your Intent instead of doing it by hand.
## Challenge: Another Implicit Intent
Instead of sending a crime report, an angry user may prefer a phone confrontation with the suspect.
Add a new button that calls the named suspect.
You will need the phone number out of the contacts database. This will require you to query another
table in the ContactsContract database called CommonDataKinds.Phone. Check out the documentation
for ContactsContract and ContactsContract.CommonDataKinds.Phone for more information on how
to query for this information.
A couple of tips: to query for additional data, you can use the android.permission.READ_CONTACTS
permission. With that permission in hand, you can read the
ContactsContract.Contacts._ID to get
a contact ID on your original query. You can then use that ID to query the CommonDataKinds.Phone
table.
Once you have the phone number, you can create an implicit intent with a telephone URI:
Uri number = Uri.parse("tel:5551234");
The action can be Intent.ACTION_DIAL or Intent.ACTION_CALL. ACTION_CALL pulls up the phone app
and immediately calls the number sent in the intent; ACTION_DIAL just dials the number and waits for
the user to initiate the call.
We recommend using ACTION_DIAL. ACTION_CALL may be restricted and will definitely require a
permission. Your user may also appreciate the chance to cool down before pressing the Call button.