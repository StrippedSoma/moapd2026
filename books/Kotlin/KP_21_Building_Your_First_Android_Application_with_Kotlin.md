

## 21
## Building	Your	First	Android
Application	with	Kotlin
Kotlin	is	a	first-class	language	for	developing	Android	applications,	with	official
support	from	Google.	In	this	chapter,	you	will	write	your	first	Android
application	using	Kotlin.	The	app,	which	rolls	a	new	NyetHack	player
character’s	starting	attributes,	is	called	Samodelkin,	honoring	a	Russian	cartoon
android	from	the	1950s	who	created	himself.

## Android	Studio
To	create	an	Android	project,	you	will	use	the	Android	Studio	IDE	instead	of
IntelliJ.	Android	Studio	is	built	on	IntelliJ,	and	while	they	share	many
similarities,	Android	Studio	includes	extra	features	required	for	developing
Android	applications.
Download	Android	Studio	from	developer.android.com/studio/
index.html.	Once	the	download	has	completed,	follow	the	installation
instructions	for	your	platform	at	developer.android.com/studio/
install.html.
Note	that	this	chapter	is	based	on	Android	Studio	3.1	and	Android	8.1	(API	27).
If	you	have	a	more	recent	version,	some	of	the	details	may	have	changed.
Before	creating	a	new	project,	confirm	that	the	Android	SDK	package	you	will
need	has	been	downloaded	for	your	system	by	selecting	Configure	→	SDK	Manager
from	the	Welcome	to	Android	Studio	dialog	(Figure	21.1):

Figure	21.1		Bringing	up	the	SDK	Manager
In	the	Android	SDK	window,	ensure	that	Android	8.1	(Oreo)	(API	27)	is	checked	and
marked	Installed	in	the	status	column	(Figure	21.2).	If	it	is	not,	check	the	box	next
to	it	and	click	OK,	which	will	download	the	required	API.	If	it	is	installed,	click
Cancel	to	return	to	the	Welcome	to	Android	Studio	dialog.

Figure	21.2		Confirming	API	27	is	installed
Back	at	the	Welcome	to	Android	Studio	dialog,	click	Start	a	new	Android	Studio	project.
In	the	Create	Android	Project	dialog,	enter	“Samodelkin”	for	Application	name	and
“android.bignerdranch.com”	for	Company	domain.	Make	sure	that	Include	Kotlin
support	is	checked	(Figure	21.3).

Figure	21.3		The	Create	Android	Project	dialog
Click	Next	and,	in	the	Target	Android	Devices	dialog,	make	sure	Phone	and	Tablet	is
checked.	Leave	the	default	in	the	API	dropdown	below	as	is	(even	if	it	looks
different	from	ours)	(Figure	21.4).	Click	Next.

Figure	21.4		The	Target	Android	Devices	dialog
In	the	Add	an	Activity	to	Mobile	dialog,	select	Empty	Activity	and	click	Next
(Figure	21.5).

Figure	21.5		Adding	an	empty	activity
Last,	you	will	be	presented	with	the	Configure	Activity	dialog.	Enter
“NewCharacterActivity”	for	the	Activity	Name	and	leave	the	other	defaults	as	they
are.
In	this	step,	you	specified	an	activity	that	will	be	created,
NewCharacterActivity.	You	can	think	of	an	activity	using	your	common-
sense	definition	of	the	word	–	it	is	something	a	user	of	your	application	will	be
able	to	do	when	using	your	app.	For	example,	composing	an	email,	searching	for
a	contact,	or,	in	the	case	of	Samodelkin,	creating	a	new	character	–	these	are	all
activities.

In	Android,	activities	consist	of	two	parts	–	a	user	interface	and	an	Activity
class.	The	user	interface,	or	UI,	defines	the	elements	a	user	will	see	and	interact
with	in	the	app,	and	the	Activity	class	defines	the	logic	required	to	bring	that
UI	to	life.	You	will	work	with	both	of	these	when	building	the	app.
Click	Finish.	A	small	dialog	appears,	showing	that	the	project	is	being	configured
(Figure	21.6):
Figure	21.6		Configuring	project
After	a	minute	or	two,	your	new	project	will	open.
A	new	project	configuration,	directory	structure,	and	default	definitions	for	the
activity’s	class	definition	and	UI	have	been	generated	and	added	to	your	project.
Let’s	take	a	quick	tour.
Gradle	configuration
First,	take	a	look	at	the	directory	structure	for	your	project,	visible	in	the	project
tool	window	on	the	left.	Make	sure	that	Android	is	selected	in	the	dropdown	for
the	project	tool	window	(Figure	21.7):

Figure	21.7		Android	project	tool	window	perspective
Now,	find	the	Gradle	Scripts	section	at	the	bottom	of	the	project	tool	window	and
expand	it	(Figure	21.8):

## Figure	21.8		Gradle	Scripts
Android	uses	a	popular	build	automation	tool	called	Gradle	to	manage	your
application	dependencies	and	compilation.	Gradle	configuration	is	defined	using
a	lightweight	DSL.	An	Android	project’s	Gradle	settings	are	configured	using
two	build.gradle	files,	automatically	added	when	the	Android	project	is
created.
Certain	Gradle	configuration	steps	Android	Studio	took	care	of	for	you	enable
your	Android	project	to	be	developed	using	Kotlin.	Let’s	take	a	look.
The	(Project:	Samodelkin)	Gradle	configuration	file	defines	global
settings	for	the	project.	Double-click	on	build.gradle	(Project:
Samodelkin)	to	open	it	in	the	editor,	the	main	Android	Studio	window	area.
You	will	see	contents	similar	to	the	following:
buildscript	{
ext.kotlin_version	=	'1.2.30'
repositories	{
google()
jcenter()
## }
dependencies	{
classpath	'com.android.tools.build:gradle:3.1.0'
classpath	"org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
## }
## }
allprojects	{
repositories	{
google()
jcenter()
## }
## }
task	clean(type:	Delete)	{
delete	rootProject.buildDir
## }

The	shaded	lines	add	the	classpath	configuration	for	the	Kotlin	Gradle	plug-in,
enabling	Gradle	to	compile	Kotlin	files.
Next,	open	and	look	at	the	build.gradle	(Module:	app)	file:
apply	plugin:	'com.android.application'
apply	plugin:	'kotlin-android'
apply	plugin:	'kotlin-android-extensions'
android	{
compileSdkVersion	27
defaultConfig	{
applicationId	"com.bignerdranch.android.samodelkin"
minSdkVersion	19
targetSdkVersion	27
versionCode	1
versionName	"1.0"
testInstrumentationRunner	"android.support.test.runner.AndroidJUnitRunner"
## }
buildTypes	{
release	{
minifyEnabled	false
proguardFiles	getDefaultProguardFile('proguard-android.txt'),
## 'proguard-rules.pro'
## }
## }
## }
dependencies	{
implementation	fileTree(dir:	'libs',	include:	['*.jar'])
implementation"org.jetbrains.kotlin:kotlin-stdlib-jre7:$kotlin_version"
implementation	'com.android.support:appcompat-v7:27.1.0'
implementation	'com.android.support.constraint:constraint-layout:1.0.2'
testImplementation	'junit:junit:4.12'
androidTestImplementation	'com.android.support.test:runner:1.0.1'
androidTestImplementation
## 'com.android.support.test.espresso:espresso-core:3.0.1'
## }
The	shaded	lines	here	add	two	plug-ins	to	your	project.	The	kotlin-android
plug-in	enables	Kotlin	code	to	be	correctly	compiled	when	used	in	conjunction
with	the	Android	framework.	It	is	required	for	any	Android	project	that	will	be
written	using	Kotlin.
The	kotlin-android-extensions	plug-in	adds	a	number	of	conveniences	for
improving	your	Android	application	when	working	with	Kotlin.	You	will	be
using	a	feature	that	kotlin-android-extensions	provides	soon.
Gradle	also	manages	the	library	dependencies	that	are	required	for	your	Android
project.	Toward	the	end	of	the	app/build.gradle	file,	you	will	see	the	listing	of
the	required	libraries	that	are	downloaded	and	included	automatically	by	the
Gradle	build	management	tool.
Dependencies	for	a	Gradle	Android	project	are	defined	in	the	dependencies
block	of	app/build.gradle.	Note	that	the	Kotlin	standard	library	is	included	in
the	list	of	dependencies:	implementation"org.jetbrains.kotlin:kotlin-
stdlib-jre7:$kotlin_version".
Project	organization

Next,	expand	the	app/src/main/java	directory	in	the	project	tool	window.
You	will	see	a	package	called
com.bignerdranch.android.samodelkin	and	a	file	called
NewCharacterActivity.kt	(which	may	have	opened	in	the	editor	when
your	project	was	created).
All	source	code	for	your	project	will	live	within	the
com.bignerdranch.android.samodelkin	package.	Do	not	be	fooled
by	the	directory	name	–	your	project	will	be	written	in	Kotlin,	not	Java.	The
default	naming	convention	for	the	src	directory	is	a	holdover	from	the	days	of
## Java.
Finally,	expand	the	app/src/main/res	directory	in	the	project	tool	window.
This	is	the	home	of	your	app’s	resources.	UI	XML	files,	images,	localized	string
definitions,	and	color	values	are	all	examples	of	Android	resources.

Defining	a	UI
Your	first	work	in	developing	Samodelkin	will	be	in	the	res	directory.	In
Android,	a	UI	layout	resource	is	an	XML	file	that	describes	the	elements	the	user
will	see	and	interact	with.
Open	the	res/layout	folder.	You	will	see	an	XML	file	called
activity_new_character.xml,	which	was	created	for	you	using	the
name	you	specified	for	your	first	activity	in	the	project	setup	process.
Double-click	on	activity_new_character.xml.	The	file	opens	in	the	UI
graphical	layout	tool	(Figure	21.9):

Figure	21.9		The	UI	graphical	layout	tool
The	UI	for	Samodelkin	will	display	five	attributes	for	the	new	character:	name,
race,	wisdom,	strength,	and	dexterity.	The	character	creation	screen	also	requires
a	button	to	randomly	generate	the	character’s	stats,	allowing	the	user	to	“re-roll”
the	stats	to	get	a	different	character	build.
Click	on	the	Text	tab	at	the	lower	left	of	the	editor.	UIs	for	Android	applications
are	written	in	XML.	The	details	of	the	XML	are	outside	the	scope	of	this	book,
so	–	to	allow	you	to	focus	on	the	Kotlin	aspects	of	project	development	–	we
have	provided	the	XML	for	the	new	character	UI	for	you	at
bignerdranch.com/solutions/activity_new_character.xml.
Overwrite	the	XML	content	in	the	file	with	the	XML	content	in	the	link.	Save

the	file	with	Command-S	(Ctrl-S)	and	click	on	the	Design	tab	at	the	lower	left.
Your	UI	will	now	look	like	Figure	21.10.
Figure	21.10		The	new	character	UI
Switch	back	to	the	Text	tab	to	look	more	closely	at	the	XML.	Press	Command-F
(Ctrl-F)	to	search	for	the	text	“android:id”	in	the	file.	You	will	find	that	there	are
five	android:ids	in	the	XML	–	one	for	each	attribute	(name,	race,	wis,	str,	and
dex),	like	this	one:
<TextView
android:id="@+id/nameTextView"
android:layout_width="wrap_content"
android:layout_height="match_parent"
android:textSize="24sp"
tools:text="Madrigal"	/>
For	each	view	element	that	displays	data	or	allows	the	user	to	interact	with	the

app,	you	specify	an	id	attribute.	An	id	attribute	allows	you	to	programmatically
access	the	view	element	it	is	defined	on	(often	called	a	widget)	in	your	Kotlin
code.	You	will	be	using	these	id	attributes	shortly	to	associate	your	app’s	logic
with	its	UI.

Running	the	App	on	an	Emulator
Next,	you	are	going	to	deploy	and	run	the	application	on	an	Android	emulator	to
test	it.
The	first	step	is	to	configure	an	emulator.	From	the	Android	Studio	menus,	select
Tools	→	AVD	Manager	(Figure	21.11).
Figure	21.11		Showing	the	AVD	Manager
At	the	lower	left	of	the	window,	click	+	Create	Virtual	Device...	(Figure	21.12).
## Figure	21.12		Android	Virtual	Device	Manager
In	the	Select	Hardware	dialog,	select	a	phone	model	(the	default	choice	should	be

fine)	and	click	Next.	In	the	System	Image	dialog,	select	the	Oreo	API	Level	27
release	(and	download	it,	if	necessary).	Click	Next.	When	the	system	image	has
finished	downloading,	click	Next	again.	Finally,	on	the	Android	Virtual	Device	(AVD)
dialog,	click	Finish.	Close	the	Android	Virtual	Device	Manager	window.
Back	at	the	main	Android	Studio	window,	look	at	the	row	of	buttons	in	the	top
right.	To	the	left	of	the	run	button	is	a	dropdown	box.	Make	sure	that	it	says	app,
then	click	the	run	button	(Figure	21.13).	This	opens	the	Select	Deployment	Target
dialog.
## Figure	21.13		Running	Samodelkin
Select	the	virtual	device	you	configured	and	click	OK.	The	emulator	will	launch
and	display	the	new	character	activity	UI,	in	all	of	its	current	(unpopulated)
glory	(Figure	21.14):

Figure	21.14		Samodelkin,	running	in	the	emulator
The	UI	shows	no	values	for	the	character’s	stats	yet.	In	the	next	section,	you	will
fix	that.

Generating	a	Character
Now	that	you	have	defined	the	UI,	it	is	time	to	generate	and	display	a	new
character	sheet.	Since	the	focus	of	this	chapter	is	Android	and	Kotlin,	and	the
details	of	the	implementation	have	been	the	focus	of	the	previous	chapters,	we
will	move	quickly	with	the	implementation	for	CharacterGenerator.	Add
a	new	file	to	the	project	by	right-clicking	on	the
com.bignerdranch.android.samodelkin	package	and	selecting	New
→	Kotlin	File/Class.
Name	the	new	file	CharacterGenerator.kt	and	fill	it	in	like	so:
Listing	21.1		The	CharacterGenerator	object
(CharacterGenerator.kt)
private	fun	<T>	List<T>.rand()	=	shuffled().first()
private	fun	Int.roll()	=	(0	until	this)
.map	{	(1..6).toList().rand()	}
## .sum()
.toString()
private	val	firstName	=	listOf("Eli",	"Alex",	"Sophie")
private	val	lastName	=	listOf("Lightweaver",	"Greatfoot",	"Oakenfeld")
object	CharacterGenerator	{
data	class	CharacterData(val	name:	String,
val	race:	String,
val	dex:	String,
val	wis:	String,
val	str:	String)
private	fun	name()	=	"${firstName.rand()}	${lastName.rand()}"
private	fun	race()	=	listOf("dwarf",	"elf",	"human",	"halfling").rand()
private	fun	dex()	=	4.roll()
private	fun	wis()	=	3.roll()
private	fun	str()	=	5.roll()
fun	generate()	=	CharacterData(name	=	name(),
race	=	race(),
dex	=	dex(),
wis	=	wis(),
str	=	str())
## }
The	CharacterGenerator	object	you	define	exposes	one	public	function,
generate,	which	returns	the	data	representing	a	randomly	generated	character
wrapped	in	a	CharacterData	class.	You	also	define	two	extensions,
List<T>.rand	and	Int.roll,	to	shorten	the	code	for	selecting	an	element
at	random	from	a	collection	and	for	randomly	rolling	a	six-sided	die	a	set
number	of	times.

## The	Activity	Class
NewCharacterActivity.kt	may	already	be	open	in	an	editor	tab.	If	it	is
not,	expand	the
app/src/main/java/com.bignerdranch.android.samodelkin
directory	and	double-click	NewCharacterActivity.kt.
The	initial	class	definition	appears	in	the	editor:
class	NewCharacterActivity	:	AppCompatActivity()	{
override	fun	onCreate(savedInstanceState:	Bundle?)	{
super.onCreate(savedInstanceState)
setContentView(R.layout.activity_new_character)
## }
## }
This	code	was	generated	along	with	your	project.	Notice	that
NewCharacterActivity,	the	activity	you	defined	during	the	setup	process,
subclasses	AppCompatActivity.
AppCompatActivity	is	part	of	the	Android	framework	and	serves	as	a	base
class	for	the	NewCharacterActivity	you	will	define	in	your	app.
Also,	notice	that	the	onCreate	function	has	been	overridden.	onCreate	is	an
Android	lifecycle	function:	a	function	that	the	Android	operating	system
invokes	for	you	when,	in	this	case,	your	activity	is	initially	created.
The	onCreate	function	is	where	you	retrieve	view	elements	from	the	UI	XML
and	where	you	wire	up	associated	interactive	logic	for	a	particular	activity.	Take
a	look	at	its	definition:
class	NewCharacterActivity	:	AppCompatActivity()	{
override	fun	onCreate(savedInstanceState:	Bundle?)	{
super.onCreate(savedInstanceState)
setContentView(R.layout.activity_new_character)
## }
## }
Within	onCreate,	the	setContentView	function	is	called	with	the	name
of	the	XML	file	you	defined,	activity_new_character.
setContentView	takes	a	layout	resource	and	inflates	it	–	converting	the
XML	to	a	UI	view	that	is	displayed	on	the	phone,	tablet,	or	emulator	for	a
particular	activity.

## Wiring	Up	Views
To	display	the	character	data	in	the	UI,	you	will	first	retrieve	each	view	element
that	will	display	text	using	a	function	available	on	NewCharacterActivity
(via	inheritance)	called	findViewById.	findViewById	accepts	a	view
element	id	(the	android:ids	defined	in	the	XML)	and	returns	the	view	element
if	a	match	is	found.
In	NewCharacterActivity.kt,	update	onCreate	to	look	up	each	view
element	that	will	display	data	by	its	id	and	assign	it	to	a	local	variable:
Listing	21.2		Looking	up	view	elements
(NewCharacterActivity.kt)
class	NewCharacterActivity	:	AppCompatActivity()	{
override	fun	onCreate(savedInstanceState:	Bundle?)	{
super.onCreate(savedInstanceState)
setContentView(R.layout.activity_new_character)
val	nameTextView	=	findViewById<TextView>(R.id.nameTextView)
val	raceTextView	=	findViewById<TextView>(R.id.raceTextView)
val	dexterityTextView	=	findViewById<TextView>(R.id.dexterityTextView)
val	wisdomTextView	=	findViewById<TextView>(R.id.wisdomTextView)
val	strengthTextView	=	findViewById<TextView>(R.id.strengthTextView)
val	generateButton	=	findViewById<Button>(R.id.generateButton)
## }
## }
Android	Studio	will	complain	about	all	your	references	to	TextView	and
Button.	You	need	to	import	the	classes	that	define	these	widgets	in	your	file	to
access	their	properties.	Click	on	the	first	red	TextView	and	press	Option-
Return	(Alt-Enter).	The	line	import	android.widget.TextView	appears	at	the
top	of	your	file,	and	the	red	error	underlines	disappear.	Repeat	the	process	for
## Button.
Next,	assign	the	character	data	to	a	property	on	the
NewCharacterActivity	class:
Listing	21.3		Defining	the	characterData	property
(NewCharacterActivity.kt)
class	NewCharacterActivity	:	AppCompatActivity()	{
private	var	characterData	=	CharacterGenerator.generate()
override	fun	onCreate(savedInstanceState:	Bundle?)	{
## ...
## }
## }
And	to	the	views	that	you	looked	up	at	the	end	of	the	onCreate	function:
Listing	21.4		Displaying	the	character	data

(NewCharacterActivity.kt)
class	NewCharacterActivity	:	AppCompatActivity()	{
private	var	characterData	=	CharacterGenerator.generate()
override	fun	onCreate(savedInstanceState:	Bundle?)	{
## ...
characterData.run	{
nameTextView.text	=	name
raceTextView.text	=	race
dexterityTextView.text	=	dex
wisdomTextView.text	=	wis
strengthTextView.text	=	str
## }
## }
## }
There	are	several	details	to	notice	about	the	code	that	assigns	the	character	data
to	the	text	views.	First,	you	use	the	run	standard	function	to	shorten	the	amount
of	code	required	to	configure	the	view	elements	from	the	character	data	–
scoping	each	character	data	property	access	to	be	implicitly	called	on
characterData.
Also,	you	assign	the	text	using	property	assignment	syntax,	like	this:
nameTextView.text	=	name
To	do	this	with	Java,	instead	of	Kotlin,	you	would	write:
nameTextView.setText(name);
Why	is	there	a	difference	here?	Android	is	a	Java	framework,	and	the	standard
Java	convention	for	accessing	a	field	is	to	use	getters	and	setters.	Remember	that
AppCompatActivity,	the	TextView	elements,	and	all	of	the	components
of	the	Android	platform	are	in	fact	written	in	the	Java	language,	and	you
interface	with	them	when	using	Kotlin	to	write	an	Android	app.
If	you	were	to	interface	with	the	nameTextView	from	a	Java	class,	you	would
use	the	standard	Java	getter/setter	syntax	(setText,	getText)	to	set	the	text
for	the	TextView.
Because	you	interfaced	with	the	TextView	Java	class	using	Kotlin,	Kotlin
translates	Java’s	getter/setter	convention	to	the	equivalent	Kotlin	convention:
property	access	syntax.	This	required	no	additional	code	or	extra	changes.	Kotlin
bridges	Java	style	and	Kotlin	style	automatically,	since	Kotlin	was	designed	with
seamless	Java	interoperability	in	mind.
Run	Samodelkin	on	the	emulator	again.	This	time,	you	will	see	character	data
that	was	loaded	from	CharacterGenerator	and	populated	in	the	UI
(Figure	21.15):

Figure	21.15		Samodelkin,	showing	data

## Kotlin	Android	Extensions	Synthetic	Properties
One	problem	–	your	onCreate	function	is	getting	somewhat	lengthy	and
disorganized.	(Also,	the	GENERATE	button	does	nothing	yet.	You	will	fix	that	in	a
moment.)
As	you	pack	more	code	into	onCreate,	it	becomes	harder	to	follow	what	is
going	on.	In	a	more	elaborate	application,	this	lack	of	order	could	be
problematic.	Even	in	a	relatively	simple	app	like	Samodelkin,	it	is	good	practice
to	keep	things	tidy.
You	are	going	to	move	the	assignment	of	the	character	data	to	the	views	to	a
separate	function,	instead	of	cramming	it	all	into	the	onCreate	function.	Using
functions	to	organize	your	activity	can	preserve	your	sanity	as	the	interface	and
functionality	of	the	activity	grow	more	complex.
To	do	this,	you	need	a	way	to	use	the	views	that	you	looked	up	in	onCreate.
One	solution	is	to	make	the	view	elements	you	retrieved	with	findViewById
properties	of	NewCharacterActivity,	allowing	them	to	be	accessed	in
other	functions	beyond	onCreate.
However,	an	even	more	convenient	solution,	available	because	your	project
includes	the	kotlin-android-extensions	plug-in,	is	to	use	synthetic	properties,
which	expose	a	view	via	its	id	attribute.	These	properties	correspond	to	all	the
widget	properties	defined	in	the	named	layout	file,
activity_new_character.xml.
Update	NewCharacterActivity	with	a	displayCharacterData
function	to	see	what	this	means.	(You	can	cut	and	paste
characterData.run	to	save	typing.)
Listing	21.5		Refactoring	to	displayCharacterData
(NewCharacterActivity.kt)
import	kotlinx.android.synthetic.main.activity_new_character.*
class	NewCharacterActivity	:	AppCompatActivity()	{
private	var	characterData	=	CharacterGenerator.generate()
override	fun	onCreate(savedInstanceState:	Bundle?)	{
super.onCreate(savedInstanceState)
setContentView(R.layout.activity_new_character)
val	nameTextView	=	findViewById<TextView>(R.id.nameTextView)
val	raceTextView	=	findViewById<TextView>(R.id.raceTextView)
val	dexterityTextView	=	findViewById<TextView>(R.id.dexterityTextView)
val	wisdomTextView	=	findViewById<TextView>(R.id.wisdomTextView)
val	strengthTextView	=	findViewById<TextView>(R.id.strengthTextView)
val	generateButton	=	findViewById<Button>(R.id.generateButton)

characterData.run	{
nameTextView.text	=	name
raceTextView.text	=	race
dexterityTextView.text	=	dex
wisdomTextView.text	=	wis
strengthTextView.text	=	str
## }
displayCharacterData()
## }
private	fun	displayCharacterData()	{
characterData.run	{
nameTextView.text	=	name
raceTextView.text	=	race
dexterityTextView.text	=	dex
wisdomTextView.text	=	wis
strengthTextView.text	=	str
## }
## }
## }
Kotlin	Android	extensions	are	a	suite	of	extras	included	by	default	with	your
new	project,	via	Gradle.	The	line	import
kotlinx.android.synthetic.main.activity_new_character.*,	enabled	by
the	kotlin-android-extensions	plug-in,	adds	a	series	of	extension	properties
to	your	Activity.	As	you	can	see,	synthetic	properties	greatly	simplify	your
view	lookup	code	–	no	findViewById	needed.	Instead	of	each	view	being	a
local	variable	in	the	onCreate	function,	you	now	have	properties	that
correspond	to	each	view’s	id	defined	in	the	layout	file.
Now	the	view	assignment	behavior	also	has	its	own	function,
displayCharacterData.

Setting	a	Click	Listener
You	have	displayed	a	character’s	stats,	but	the	user	currently	has	no	way	to
generate	another	character.	The	GENERATE	button	needs	to	be	wired	up	with	the
details	of	what	to	do	when	it	is	pressed.	It	should	update	the	character	data
property	and	display	the	results.
Update	onCreate	to	implement	this	behavior	by	defining	a	click	listener.
(Even	though	you	“press”	things	in	Android,	the	listener	is	called	“click.”)
Listing	21.6		Setting	a	click	listener	(NewCharacterActivity.kt)
class	NewCharacterActivity	:	AppCompatActivity()	{
private	var	characterData	=	CharacterGenerator.generate()
override	fun	onCreate(savedInstanceState:	Bundle?)	{
super.onCreate(savedInstanceState)
setContentView(R.layout.activity_new_character)
generateButton.setOnClickListener	{
characterData	=	CharacterGenerator.generate()
displayCharacterData()
## }
displayCharacterData()
## }
## ...
## }
Here,	you	define	a	click	listener	implementation	that	determines	what	happens
when	the	button	is	pressed.	Run	Samodelkin	again	and	press	the	GENERATE
button	several	times.	You	will	see	a	new	character	sheet	loaded	each	time	the
button	is	pressed.
The	setOnClickListener	method	expects	an	argument	that	implements	the
OnClickListener	interface.	(You	do	not	have	to	take	our	word	for	it;	you	can
look	it	up	yourself	at	developer.android.com/reference/
android/view/View.html.)	The	OnClickListener	interface	has	only	one
abstract	method	defined	on	it	–	onClick.	Interface	parameters	like	this	are
called	SAM	types	–	single	abstract	method	types.
In	older	versions	of	Java,	the	implementation	for	the	click	listener	interface
would	be	provided	using	an	anonymous	inner	class:
generateButton.setOnClickListener(new	View.OnClickListener()	{
@Override
public	void	onClick(View	view)	{
//	Do	stuff
## }
## });
Kotlin	includes	a	feature	called	SAM	conversions,	allowing	you	to	use	a
function	literal	as	a	valid	argument	in	place	of	an	anonymous	inner	class.	Any

time	you	interface	with	Java	code	that	requires	an	argument	implementing	an
SAM	interface,	traditionally	accomplished	with	an	anonymous	inner	class,
Kotlin	supports	using	a	function	literal	instead.
Note	that	if	you	were	to	look	at	the	bytecode	for	the	click	listener	code	that	you
have	written,	you	would	see	that	a	full	anonymous	inner	class	was	used	to
provide	the	implementation,	just	like	in	the	traditional	Java	code	above.

## Saved	Instance	State
Your	character	attribute	app	is	really	shaping	up.	You	can	press	GENERATE	and
create	character	stats	to	your	heart’s	content.	But	there	is	still	a	problem.	To	see
it,	run	the	emulator,	then	simulate	rotating	the	phone	by	clicking	on	one	of	the
rotation	icons	in	the	emulator	options	window	(Figure	21.16):

Figure	21.16		Rotating	the	emulator
The	UI	shows	different	character	data	(Figure	21.17):

Figure	21.17		Different	character	data	after	rotating
The	data	shown	in	the	UI	changed	because	of	how	Android’s	activity	lifecycle
works.	When	a	device	is	rotated	(Android	calls	this	a	device	configuration
change),	Android	destroys	and	re-creates	the	activity	–	and,	in	the	process,	re-
creates	the	UI	by	calling	the	onCreate	function	on	a	new	instance	of	the
NewCharacterActivity	class.
One	way	to	fix	this	issue	is	to	carry	the	displayed	character	data	forward	to	the
next	instance	of	the	activity	by	storing	it	in	the	activity’s	saved	instance
state.	The	saved	instance	state	can	be	used	to	store	data	that	you	would	like	to
reuse	when	the	activity	is	re-created	after	a	device	configuration	change.
First,	update	the	NewCharacterActivity	class	to	serialize	the	character
data:
Listing	21.7		Serializing	the	characterData
(NewCharacterActivity.kt)
private	const	val	CHARACTER_DATA_KEY	=	"CHARACTER_DATA_KEY"
class	NewCharacterActivity	:	AppCompatActivity()	{
private	var	characterData	=	CharacterGenerator.generate()
override	fun	onSaveInstanceState(outState:	Bundle)	{
super.onSaveInstanceState(outState)
outState.putSerializable(CHARACTER_DATA_KEY,	characterData)
## }
## ...
## }
Serialization	is	a	process	by	which	objects	are	stored.	When	you	serialize	an
object,	you	break	it	down	into	basic	data	types,	like	String	or	Int.	Only

serializable	objects	can	be	stored	in	a	Bundle.
You	will	have	an	error	on	characterData	because	you	tried	to	pass	non-
serializable	data	to	the	putSerializable	function.	To	fix	it,	you	need	to	add
the	Serializable	interface	to	the	CharacterData	class	so	that
CharacterData	is	serializable:
Listing	21.8		Making	the	CharacterData	class	Serializable
(CharacterGenerator.kt)
object	CharacterGenerator	{
data	class	CharacterData(val	name:	String,
val	race:	String,
val	dex:	String,
val	wis:	String,
val	str:	String)	:	Serializable
## ...
## }
The	onSaveInstanceState	function	is	called	once	before	the	activity	is
destroyed.	It	exposes	the	savedInstanceState	bundle,	which	allows	an
activity’s	instance	state	to	be	persisted.
You	add	the	current	characterData	to	the	saved	instance	state	bundle	using
the	putSerializable	method,	which	expects	a	serializable	class	and	a	key.
The	key	is	a	constant	and	will	be	used	later	to	retrieve	the	serializable	data,	and
the	value	is	the	CharacterData	class,	which	you	updated	to	implement
## Serializable.
Reading	from	the	saved	instance	state
You	have	taken	care	of	the	problem	of	serializing	CharacterData	to	the
saved	instance	state,	and	now	you	need	to	deserialize	it	and	set	the	UI	back	up
using	the	old	data.	You	do	so	in	the	onCreate	function:
Listing	21.9		Fetching	the	serialized	character	data
(NewCharacterActivity.kt)
private	const	val	CHARACTER_DATA_KEY	=	"CHARACTER_DATA_KEY"
class	NewCharacterActivity	:	AppCompatActivity()	{
## ...
override	fun	onCreate(savedInstanceState:	Bundle?)	{
super.onCreate(savedInstanceState)
setContentView(R.layout.activity_new_character)
characterData	=	savedInstanceState?.let	{
it.getSerializable(CHARACTER_DATA_KEY)	as	CharacterGenerator.CharacterData
}	?:	CharacterGenerator.generate()
generateButton.setOnClickListener	{
characterData	=	CharacterGenerator.generate()
displayCharacterData()
## }

displayCharacterData()
## }
## ...
## }
Here	you	read	the	serialized	character	data	from	the	saved	instance	state	bundle,
casting	it	back	to	CharacterData	if	the	saved	instance	state	is	non-null.	On
the	other	hand,	if	the	saved	instance	state	is	null,	you	use	the	null	coalescing
operator	(?:)	to	generate	new	character	data.
Either	way,	you	assign	the	result	of	this	expression	(either	the	deserialized
character	data	or	new	character	data)	to	the	characterData	property.
Try	running	Samodelkin	again	and	rotating	the	emulator.	This	time,	you	will	see
that	the	data	is	retrieved	from	the	bundle	and	displayed	again	after	rotating,
because	you	set	character	data	from	the	saved	instance	state.

Refactoring	to	an	Extension
The	saved	instance	state	serialization	and	deserialization	work	correctly,	but	the
code	can	be	improved.	Notice	that,	currently,	you	are	required	to	manage	the	key
and	type	of	data	(you	must	manually	cast	it	to	CharacterData)	when	you	get
and	put	the	CharacterData	on	the	savedInstanceState	bundle:
private	const	val	CHARACTER_DATA_KEY	=	"CHARACTER_DATA_KEY"
class	NewCharacterActivity	:	AppCompatActivity()	{
private	var	characterData	=	CharacterGenerator.generate()
override	fun	onSaveInstanceState(outState:	Bundle)	{
super.onSaveInstanceState(outState)
outState.putSerializable(CHARACTER_DATA_KEY,	characterData)
## }
override	fun	onCreate(savedInstanceState:	Bundle?)	{
super.onCreate(savedInstanceState)
setContentView(R.layout.activity_new_character)
characterData	=	savedInstanceState?.let	{
it.getSerializable(CHARACTER_DATA_KEY)
as	CharacterGenerator.CharacterData
}	?:	CharacterGenerator.generate()
## ...
## }
## ...
## }
To	improve	on	this,	add	an	extension	property	definition	to
NewCharacterActivity.kt:
Listing	21.10		Defining	a	characterData	extension	property
(NewCharacterActivity.kt)
private	const	val	CHARACTER_DATA_KEY	=	"CHARACTER_DATA_KEY"
private	var	Bundle.characterData
get()	=	getSerializable(CHARACTER_DATA_KEY)	as	CharacterGenerator.CharacterData
set(value)	=	putSerializable(CHARACTER_DATA_KEY,	value)
class	NewCharacterActivity	:	AppCompatActivity()	{
## ...
## }
Now	you	can	access	the	characterData	from	the	saved	instance	state	bundle
as	a	property.	You	no	longer	need	the	key	to	retrieve	the	data,	and	you	no	longer
require	casting	the	Serializable	to	CharacterData	when	you	retrieve	it.
The	extension	property	provides	a	clean	abstraction	over	the	bundle’s	API,
removing	the	need	for	tracking	the	details	of	how	the	character	data	was	stored
and	which	key	was	used	each	time	you	wish	to	read	or	write	the
characterData.
Now,	update	the	onSaveInstanceState	and	onCreate	functions	to	use
the	new	extension	property:

Listing	21.11		Using	the	new	extension	property
(NewCharacterActivity.kt)
private	const	val	CHARACTER_DATA_KEY	=	"CHARACTER_DATA_KEY"
class	NewCharacterActivity	:	AppCompatActivity()	{
private	var	characterData	=	CharacterGenerator.generate()
override	fun	onSaveInstanceState(outState:	Bundle)	{
super.onSaveInstanceState(outState)
outState.putSerializable(CHARACTER_DATA,	characterData)
outState.characterData	=	characterData
## }
override	fun	onCreate(savedInstanceState:	Bundle?)	{
super.onCreate(savedInstanceState)
setContentView(R.layout.activity_new_character)
characterData	=	savedInstanceState?.let	{
it.getSerializable(CHARACTER_DATA_KEY)	as	CharacterGenerator.CharacterData
}	?:	CharacterGenerator.generate()
characterData	=	savedInstanceState?.characterData	?:
CharacterGenerator.generate()
generateButton.setOnClickListener	{
characterData	=	CharacterGenerator.generatw()
displayCharacterData()
## }
displayCharacterData()
## }
## ...
## }
Run	Samodelkin	again,	putting	the	application	through	its	paces	by	rotating	the
emulator	and	pressing	the	GENERATE	button	several	times.	You	will	see	that	the
character	data	is	persisted	correctly	as	before.
Congratulations!	You	have	created	your	first	Android	application	using	Kotlin.
You	have	learned	about	some	of	the	ways	Kotlin	supports	working	with	the	Java
code	that	the	Android	framework	is	written	in,	and	you	have	also	seen	an
example	of	how	kotlin-android-extensions	makes	your	coding	life	easier.
Finally,	you	have	seen	how	features	in	Kotlin,	like	extensions	and	standard
functions,	can	make	your	Android	code	more	clean.
In	the	next	chapter,	you	will	learn	about	Kotlin	coroutines,	an	experimental
feature	that	provides	a	lightweight	and	elegant	alternative	to	other	models	for
specifying	work	in	the	background.

For	the	More	Curious:	Android	KTX	and	Anko
## Libraries
There	are	many	open-source	libraries	designed	to	enhance	the	developer
experience	when	working	with	Kotlin	and	Android.	We	will	highlight	two	here
to	give	an	idea	of	what	is	possible.
The	Android	KTX	project	(github.com/android/android-ktx)
provides	a	number	of	useful	Kotlin	extensions	for	Android	app	development,
often	also	granting	a	more	Kotlinesque	interface	to	the	Android	Java	APIs	than
would	otherwise	be	possible.	For	example,	consider	the	following	code,	which
uses	Android’s	shared	preferences	to	persist	a	small	amount	of	data	for	later	use:
sharedPrefs.edit()
.putBoolean(true,	USER_SIGNED_IN)
.putString("Josh",	USER_CALLSIGN)
## .apply()
With	Android	KTX,	you	can	shorten	the	expression	and	write	it	in	a	more
idiomatic	Kotlin	style:
sharedPrefs.edit	{
putBoolean(true,	USER_SIGNED_IN)
putString("Josh",	USER_CALLSIGN)
## }
Android	KTX	enables	many	nice,	if	small,	improvements	to	your	Kotlin
Android	code,	and	it	allows	you	to	work	with	the	Android	framework	in	a	style
that	is	a	closer	match	to	Kotlin,	rather	than	Java.
Another	popular	Kotlin	project	for	use	with	Android,	Anko	(github.com/
Kotlin/anko),	provides	a	variety	of	enhancements	for	Kotlin	Android
development,	including	a	DSL	for	defining	Android	UIs	and	a	number	of	helpers
for	working	with	Android	intents	and	dialogs,	SQLite,	and	many	other	aspects	of
an	Android	project.	For	example,	the	following	Anko	layout	code
programmatically	defines	a	vertically	oriented	linear	layout	containing	a	button
that	displays	a	toast	(a	pop-up	message)	when	clicked:
verticalLayout	{
val	username	=	editText()
button("Greetings")	{
onClick	{	toast("Hello,	${username.text}!")	}
## }
## }
Compare	this	with	the	large	amount	of	code	to	do	the	same	programmatically	in
classic	Java:
LayoutParams	params	=	new	LinearLayout.LayoutParams(
LayoutParams.FILL_PARENT,
LayoutParams.WRAP_CONTENT);

LinearLayout	layout	=	new	LinearLayout(this);
layout.setOrientation(LinearLayout.VERTICAL);
EditText	name	=	new	EditText(this);
name.setLayoutParams(params);
layout.addView(name);
Button	greetings	=	new	Button(this);
greetings.setText("Greetings");
greetings.setLayoutParams(params);
layout.addView(greetings);
LinearLayout.LayoutParams	layoutParam	=	new	LinearLayout.LayoutParams(
LayoutParams.FILL_PARENT,
LayoutParams.WRAP_CONTENT);
this.addContentView(layout,	layoutParam);
greetings.setOnClickListener(new	OnClickListener()	{
public	void	onClick(View	v)	{
Toast.makeText(this,	"Hello,	"	+	name.getText(),
Toast.LENGTH_SHORT).show();
## }
## }
Kotlin	is	still	a	relatively	young	language,	and	useful	libraries	are	being
developed	every	day.	Keep	your	eye	on	kotlinlang.org	for	up-to-date
news	on	developments	in	the	language.