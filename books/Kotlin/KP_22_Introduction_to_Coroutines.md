

## 22
Introduction	to	Coroutines
Android	apps	perform	all	kinds	of	functions.	You	may	want	your	app	to
download	data,	query	a	database,	or	make	a	request	to	a	web	API.	These	are	all
useful	operations	–	but	they	can	all	require	a	considerable	amount	of	time	to
complete.	You	do	not	want	your	user	to	be	stuck	waiting	for	them	to	complete
before	they	can	continue	using	your	app.
Coroutines	allow	you	to	specify	work	that	happens	in	the	background	of	your
application,	or	asynchronously.	Instead	of	requiring	the	user	to	wait	while	that
work	completes,	coroutines	allow	the	user	to	continue	interacting	with	your	app
while	the	work	completes	in	the	background.
Coroutines	are	considerably	more	resource	efficient	and	easier	to	work	with	than
the	solutions	offered	by	some	other	programming	languages,	such	as	the	threads
used	by	Java	and	other	languages	(which	you	will	learn	more	about	in	this
chapter).	Complex	code	can	be	required	to	handle	the	delivery	of	results	between
threads,	and	they	are	faced	with	performance	issues	because	it	is	all	too	easy	to
“block”	a	thread.
In	this	chapter,	you	will	add	coroutines	to	your	Samodelkin	Android	app	to	fetch
new	character	data	from	a	web	API.

## Parsing	Character	Data
The	new	character	data	web	API	is	located	at	chargen-
api.herokuapp.com.	(By	the	way,	the	new	character	web	API	is	written	in
Kotlin,	using	the	Ktor	web	framework	(github.com/ktorio/ktor).	If	you
are	interested,	you	can	check	out	the	source	code	for	the	web	API	at
github.com/bignerdranch/character-data-api.)
When	the	web	API	data	is	requested,	a	comma-separated	list	of	new	player
attributes	is	returned	with	values	for	the	race,	name,	dex,	wis,	and	str	attributes.
Try	visiting	chargen-api.herokuapp.com	to	see	a	set	of	attribute	values
like:
halfling,Lars	Kizzy,14,13,8
Reload	your	web	browser	several	times	to	see	different	responses	from	the
service.
Your	first	task	is	to	convert	the	comma-separated	string	of	player	character	data
returned	from	the	web	API	to	a	CharacterData	instance	that	can	be
displayed	in	the	UI.
Let’s	get	started.	Open	CharacterGenerator.kt	in	Android	Studio	and
define	a	fromApiData	conversion	function:
Listing	22.1		Adding	the	fromApiData	function
(CharacterGenerator.kt)
## ...
object	CharacterGenerator	{
data	class	CharacterData(val	name:	String,
val	race:	String,
val	dex:	String,
val	wis:	String,
val	str:	String)	:	Serializable
## ...
fun	fromApiData(apiData:	String):	CharacterData	{
val	(race,	name,	dex,	wis,	str)	=
apiData.split(",")
return	CharacterData(name,	race,	dex,	wis,	str)
## }
## }
## ...
The	fromApiData	function	takes	a	comma-separated	string	from	the	character
data	service,	splits	it	at	the	commas,	and	destructures	the	results	into	a	new
CharacterData	instance.
Test	fromApiData	by	calling	it	when	the	GENERATE	button	is	pressed.	For
now,	pass	some	fake	data:

Listing	22.2		Testing	the	fromApiData	function
(NewCharacterActivity.kt)
## ...
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
fromApiData("halfling,Lars	Kizzy,14,13,8")
displayCharacterData()
## }
## ...
## }
## ...
## }
Run	Samodelkin	on	the	emulator	to	confirm	that	the	application	builds.	Press	the
GENERATE	button.	You	will	see	the	test	data	that	you	passed	to	the	conversion
function	displayed	in	the	UI	(Figure	22.1):

Figure	22.1		Displaying	test	data

## Fetching	Live	Data
Now	that	you	have	tested	the	conversion	function,	it	is	time	to	fetch	some	live
data	from	the	character	data	web	API.
Before	starting	with	the	implementation,	you	will	need	to	add	several
permissions	to	your	Android	manifest	to	enable	network	requests.	Find	and	open
the	manifest	at	src/main/AndroidManifest.xml.	Add	the	permissions
as	shown:
Listing	22.3		Adding	required	permissions	(AndroidManifest.xml)
<?xml	version="1.0"	encoding="utf-8"?>
<manifest	xmlns:android="http://schemas.android.com/apk/res/android"
package="com.bignerdranch.android.samodelkin">
<uses-permission	android:name="android.permission.INTERNET"	/>
<uses-permission	android:name="android.permission.ACCESS_NETWORK_STATE"	/>
## <application
android:allowBackup="true"
android:icon="@mipmap/ic_launcher"
android:label="@string/app_name"
## ...
## </application>
## </manifest>
Now	to	request	the	data	from	the	web	API.	A	simple	way	to	fetch	the	web	API
data	is	to	use	a	java.net.URL	instance.	Kotlin	includes	an	extension	function	to
URL,	readText,	that	provides	simple	support	for	connecting	to	a	basic	web
API	endpoint,	buffering	the	data,	and	converting	that	data	into	a	string	–
everything	you	need	here.
Define	a	new	constant	in	CharacterGenerator	for	the	web	API	endpoint	as
well	as	a	new	function	called	fetchCharacterData	that	reads	data	from	the
web	API	using	URL’s	readText	function.	Make	sure	to	import	the	URL	class
at	the	top	of	the	file,	as	shown:
Listing	22.4		Adding	the	fetchCharacterData	function
(CharacterGenerator.kt)
import	java.io.Serializable
import	java.net.URL
private	const	val	CHARACTER_DATA_API	=	"https://chargen-api.herokuapp.com/"
private	fun	<T>	List<T>.rand()	=	shuffled().first()
object	CharacterGenerator	{
## ...
## }
fun	fetchCharacterData():	CharacterGenerator.CharacterData	{
val	apiData	=	URL(CHARACTER_DATA_API).readText()
return	CharacterGenerator.fromApiData(apiData)

## }
Now,	put	the	new	function	to	use.	Update	the	GENERATE	button’s	click	listener	to
call	fetchCharacterData:
Listing	22.5		Calling	fetchCharacterData
(CharacterGenerator.kt)
## ...
class	NewCharacterActivity	:	AppCompatActivity()	{
## ...
override	fun	onCreate(savedInstanceState:	Bundle?)	{
## ...
generateButton.setOnClickListener	{
characterData	=	CharacterGenerator.
fromApiData("halfling,Lars	Kizzy,14,13,8")
fetchCharacterData()
displayCharacterData()
## }
displayCharacterData()
## }
## ...
## }
Run	Samodelkin	again	and	click	the	GENERATE	button.	Instead	of	new	character
attributes,	you	will	see	the	dialog	in	Figure	22.2.

Figure	22.2		Samodelkin	has	stopped
Samodelkin	crashed.	Why?	To	find	out,	take	a	look	in	the	Logcat	output,	where
the	Android	application	logs	are	displayed.	Click	on	the	Logcat	tab	at	the	bottom
of	Android	Studio	and	scroll	up	until	you	reach	the	red	text	that	starts	with	FATAL
EXCEPTION:	main	(Figure	22.3):

Figure	22.3		Logcat	output
Two	lines	below	FATAL	EXCEPTION,	the	log	shows	the	cause	of	the	error:	an
android.os.NetworkOnMainThreadException.	The	exception
occurred	because	you	attempted	to	make	a	network	request	on	the	main	thread
of	the	application,	an	operation	that	is	not	allowed.

## The	Android	Main	Thread
A	thread	is	a	pipeline	that	handles	a	sequence	of	work	to	be	performed.	The
main	thread	of	an	Android	application	is	reserved	for	processing	the	work
required	for	keeping	the	UI	responsive:	handling	button	presses,	rendering
updates	when	the	user	scrolls,	and	updating	the	text	box	as	characters	are
generated,	for	example.	For	this	reason,	it	is	sometimes	called	the	“UI	thread.”
When	you	requested	data	from	the	web	API,	the	UI	would	have	been
unresponsive	while	that	request	completed.	This	is	called	“blocking	a	thread”,
because	the	thread	cannot	move	forward	to	the	next	work	to	process	until	the
current	–	possibly	long-running	–	work	completes.	Android	explicitly	forbids
networking	on	the	main	thread	because	it	blocks	the	main	thread	for	an	unknown
amount	of	time,	leading	to	an	unresponsive	UI.

## Enabling	Coroutines
To	solve	the	crash,	you	need	a	way	to	move	the	network	request	to	a	background
thread	instead	of	the	main	thread.	Kotlin	1.1	and	all	versions	since	include	a
coroutines	API	that	gives	you	a	way	to	do	so	concisely.
As	of	this	writing,	coroutines	are	considered	experimental	(though	they	are
expected	to	become	a	permanent	feature	of	Kotlin),	so	to	use	them	you	must	opt
in	by	enabling	them.	You	also	need	a	coroutine	library	extension	to	use
coroutines	with	Android.	Click	the	Logcat	tab	again	to	hide	it,	and	open	your
app/build.gradle	file.	Enable	coroutines	and	add	the	new	dependency
there:
Listing	22.6		Enabling	coroutines	(app/build.gradle)
## ...
kotlin	{
experimental	{
coroutines	'enable'
## }
## }
dependencies	{
implementation	fileTree(dir:	'libs',	include:	['*.jar'])
implementation	"org.jetbrains.kotlin:kotlin-stdlib-jre7:$kotlin_version"
implementation	"org.jetbrains.kotlinx:kotlinx-coroutines-android:0.22.5"
## ...
## }
Once	you	add	the	entry	to	your	app/build.gradle	file,	click	the	Sync	Now
button	that	appears	at	the	top	right	of	the	screen	to	sync	the	Gradle	files.

Specifying	a	Coroutine	with	async
One	way	to	create	a	coroutine	is	to	use	the	async	function	provided	with	the
coroutine	library.	The	async	function	requires	one	argument:	a	lambda	that
specifies	the	work	you	want	to	happen	in	the	background.
In	fetchCharacterData,	move	the	blocking	readText	function	call	into
a	lambda	and	pass	it	to	the	async	function.	Also,	update	the	return	type	to	be	a
Deferred<CharacterGenerator.CharacterData>,	the	result	of	the
async	function:
Listing	22.7		Making	fetchCharacterData	async
(CharacterGenerator.kt)
## ...
fun	fetchCharacterData():	Deferred<CharacterGenerator.CharacterData>	{
return	async	{
val	apiData	=	URL(CHARACTER_DATA_API).readText()
return	CharacterGenerator.fromAPIData(apiData)
## }
## }
Now,	instead	of	returning	CharacterData,	the	fetchCharacterData
function	returns	a
Deferred<CharacterGenerator.CharacterData>.	A	Deferred	is
like	a	promise	for	future	results:	No	data	is	returned	until	you	request	it.
Return	to	NewCharacterActivity.kt	and	add	the	following,	which
converts	the	deferred	web	API	results	into	CharacterData	and	displays	the
results.	(We	will	walk	through	this	code	after	you	enter	it.)
Listing	22.8		Awaiting	the	API	results	(NewCharacterActivity.kt)
## ...
class	NewCharacterActivity	:	AppCompatActivity()	{
## ...
override	fun	onCreate(savedInstanceState:	Bundle?)	{
## ...
generateButton.setOnClickListener	{
launch(UI)	{
characterData	=	fetchCharacterData().await()
displayCharacterData()
## }
## }
displayCharacterData()
## }
## ...
## }
Android	Studio	will	prompt	you	to	import	launch	and	UI.	Make	sure	to	import
the	kotlinx.coroutines.experimental	versions.

Run	your	new	and	improved	app	and	click	the	GENERATE	button.	This	time,	the
data	you	see	has	been	fetched	from	the	web	service	and	displayed	in	the	UI.
Let’s	take	a	closer	look	at	how	this	happens.
First,	you	created	a	new	coroutine	by	calling	the	launch	function.	launch
starts	the	work	that	you	specify	in	a	new	coroutine	immediately.
You	included	UI	as	the	first	argument	to	launch.	UI	specifies	the	coroutine
context	–	where	the	work	specified	within	the	lambda	will	be	performed	–	as
Android’s	UI	thread.
Why	the	UI	thread?	The	call	to	displayCharacterData	must	be	performed
on	the	UI	thread	because	it	contains	code	that	updates	the	UI.	That	call	will
happen	only	after	the	character	data	is	downloaded,	so	it	does	not	block	the	main
thread.
As	we	said	above,	networking	is	forbidden	on	the	main	thread.	The	default
argument	for	the	coroutine	context	is	CommonPool,	a	pool	of	background
threads	available	for	executing	coroutines.	This	is	the	argument	that	was	used,
by	default,	for	the	async	function	in	fetchCharacterData,	so	the	request
to	the	web	API	is	executed	using	the	thread	pool	when	you	call	await,	instead
of	the	Android	main	thread.

launch	vs	async/await
The	async	and	launch	functions	that	you	used	to	perform	the	request	and
update	the	UI	are	called	coroutine	builder	functions,	functions	that	set	up	a
coroutine	to	perform	work	in	a	certain	way.	launch	builds	a	coroutine	that
performs	the	work	you	specify	right	away	–	in	this	case,	calling
fetchCharacterData	and	updating	the	UI.
The	async	coroutine	builder	works	differently	than	launch	in	that	it	builds	a
coroutine	that	returns	a	Deferred,	a	type	that	represents	work	that	has	not
been	completed	yet.	Instead	of	starting	the	work	immediately,	a	Deferred	is	a
promise	of	work	to	be	completed	some	time	in	the	future.
The	Deferred	type	provides	a	function	called	await	that	you	call	when	you
would	like	the	work	to	be	performed.	await	also	suspends	execution	of	the
next	work	to	do	(the	UI	update)	until	the	deferred	work	has	completed.	This
means	that	you	call	displayCharacterData	after	the	response	from	the
web	service	has	been	returned.	If	you	are	familiar	with	the	concept	of	a	Java
Future,	a	Deferred	works	in	a	very	similar	manner.
Even	though	the	web	request	was	performed	in	the	background,	you	were	able	to
structure	the	code	imperatively	because	of	Deferred’s	await	function:	You
await	the	result	and	then	call	the	UI	update	function.	Compared	to	a	traditional
approach	(like	a	callbacks	interface),	you	were	able	to	structure	the	code	as	if	the
request	to	the	web	service	was	synchronous.	This	is	because	of	a	coroutine’s
ability	to	suspend	execution	and	resume	at	a	later	time	–	all	without	blocking	the
thread.

## Suspending	Functions
Notice	the		icon	in	Android	Studio	next	to	where	you	called	the	await
function.	The	IDE	indicates	that	you	made	a	suspend	function	call	on	that
line.	What	does	this	mean?
Coroutines	are	said	to	“suspend,”	whereas	a	traditional	thread	is	said	to	“block.”
This	difference	in	terminology	hints	at	why	coroutines	offer	better	performance
than	threads:	When	a	thread	is	blocked,	it	can	no	longer	be	used	to	do	any	work
until	it	is	unblocked.	A	coroutine	is	executed	by	a	thread	–	for	example,	the
Android	UI	thread,	or	a	thread	in	the	common	pool	–	but	does	not	block	the
thread	that	executes	it.	Instead,	a	thread	executing	a	function	that	suspends	can
be	used	to	execute	other	coroutines.	This	is	why	a	coroutine	offers	significantly
better	performance	than	a	standard	thread.
Under	the	hood,	suspend	functions	are	marked	with	the	suspend	keyword.	Here
is	await’s	function	signature:
public	suspend	fun	await():	T
In	this	chapter,	you	completed	the	Samodelkin	app	(Do	svidaniya,
Samodelkin!)	and	saw	that	Android’s	main	thread	is	reserved	for	processing	UI
events.	You	also	learned	the	basics	of	using	coroutines	to	perform	work	in	the
background	without	blocking	Android’s	main	thread.

## Challenge:	Live	Data
Currently,	the	data	that	is	initially	shown	in	the	app	is	static	data	from	the
CharacterGenerator	object,	which	is	replaced	with	live	data	when	the
GENERATE	button	is	pressed.	For	this	challenge,	you	will	fix	that.	Make	the	initial
data	that	is	shown	in	the	application	live	data	from	the	web	service	instead.

## Challenge:	Minimum	Strength
A	character	with	a	strength	value	lower	than	10	will	not	last	more	than	a	few
rounds	of	play	in	NyetHack.	For	this	challenge,	discard	any	response	with	a
strength	value	less	than	10.	Perform	new	requests	until	you	receive	a	response
with	a	value	of	10	or	greater.