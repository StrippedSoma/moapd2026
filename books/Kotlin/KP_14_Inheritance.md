

## 14
## Inheritance
Inheritance	is	an	object-oriented	principle	you	can	use	to	define	hierarchical
relationships	between	types.	In	this	chapter	you	will	use	inheritance	to	share	data
and	behavior	between	related	classes.
To	get	a	handle	on	inheritance,	consider	an	example	outside	of	programming.
Cars	and	trucks	have	much	in	common:	They	each	have	wheels,	an	engine,	etc.
They	also	have	some	different	features.	Using	inheritance,	you	could	define	the
things	that	they	have	in	common	in	a	shared	class,	Vehicle,	so	that	you	do	not
have	to	implement	Wheel	and	Engine	and	so	on	in	both	Car	and	Truck.
Car	and	Truck	would	inherit	those	shared	features,	and	each	would	then	define
its	unique	features	as	well.
In	NyetHack,	you	have	defined	what	it	means	to	be	a	Player	in	the	game.	In
this	chapter,	you	will	put	inheritance	to	work	by	adding	a	series	of	rooms	to
NyetHack	so	that	your	player	has	places	to	go.

Defining	the	Room	Class
Begin	by	creating	a	new	file	in	NyetHack	called	Room.kt.	Room.kt	will
contain	a	new	class	called	Room	that	will	represent	one	square	in	NyetHack’s
coordinate	plane.	Later,	you	will	define	a	particular	kind	of	room	in	a	class	that
inherits	qualities	from	Room.
To	begin,	Room	will	have	one	property	–	name	–	and	two	functions,
description	and	load.	description	returns	a	String	describing	the
room.	load	returns	a	String	that	will	be	printed	to	the	console	when	you
enter	the	room.	These	are	features	you	want	for	every	room	in	NyetHack.
Add	the	Room	class	definition	to	Room.kt,	as	shown:
Listing	14.1		Declaring	the	Room	class	(Room.kt)
class	Room(val	name:	String)	{
fun	description()	=	"Room:	$name"
fun	load()	=	"Nothing	much	to	see	here..."
## }
To	test	your	new	Room	class,	create	a	Room	instance	when	the	game	starts	in
main	and	print	the	result	of	its	description	function.
Listing	14.2		Printing	the	room	description	(Game.kt)
fun	main(args:	Array<String>)	{
val	player	=	Player("Madrigal")
player.castFireball()
var	currentRoom	=	Room("Foyer")
println(currentRoom.description())
println(currentRoom.load())
//	Player	status
printPlayerStatus(player)
## }
## ...
Run	Game.kt.	You	should	see	the	following	output	to	the	console.
A	glass	of	Fireball	springs	into	existence.	Delicious!	(x2)
## Room:	Foyer
Nothing	much	to	see	here...
(Aura:	GREEN)	(Blessed:	YES)
Madrigal	of	Tampa	is	in	excellent	condition!
So	far,	so	good	...	but	kind	of	boring.	Who	wants	to	hang	out	in	a	foyer?	It	is
time	for	Madrigal	of	Tampa	to	go	places.

Creating	a	Subclass
A	subclass	shares	all	properties	with	the	class	it	inherits	from,	commonly	known
as	the	parent	class	or	superclass.
For	example,	citizens	of	NyetHack	will	need	a	town	square.	A	town	square	is	a
type	of	Room	with	special	features	only	town	squares	will	have	–	like	a
customized	loading	message	when	players	enter.	To	create	the	TownSquare
class,	you	will	subclass	Room,	since	they	have	common	features,	and	then
describe	how	TownSquare	differs	from	Room.
But	before	defining	a	TownSquare	class,	you	first	need	to	make	a	change	to
the	Room	class	so	that	it	can	be	subclassed.
Not	every	class	you	write	is	intended	to	be	part	of	a	hierarchy,	and,	in	fact,
classes	are	closed	–	meaning	they	prohibit	subclassing	–	by	default.	For	a	class
to	be	subclassed,	it	must	be	marked	with	the	open	keyword.
Add	the	open	keyword	to	Room	so	that	it	can	be	subclassed.
Listing	14.3		Making	the	Room	class	open	for	subclassing	(Room.kt)
open	class	Room(val	name:	String)	{
fun	description()	=	"Room:	$name"
fun	load()	=	"Nothing	much	to	see	here..."
## }
Now	that	Room	is	marked	open,	create	a	TownSquare	class	in	Room.kt	by
subclassing	the	Room	class	using	the	:	operator,	like	so:
Listing	14.4		Declaring	the	TownSquare	class	(Room.kt)
open	class	Room(val	name:	String)	{
fun	description()	=	"Room:	$name"
fun	load()	=	"Nothing	much	to	see	here..."
## }
class	TownSquare	:	Room("Town	Square")
The	TownSquare	class	declaration	includes	the	class	name	to	the	left	of	the	:
operator	and	a	constructor	invocation	to	the	right.	The	constructor	invocation
indicates	which	constructor	to	call	for	TownSquare’s	parent	and	what
arguments	to	pass	to	it.	In	this	case,	a	TownSquare	is	a	version	of	Room	with
the	specific	name	"Town	Square".
But	you	want	more	from	your	town	square	than	just	a	name.	Another	way	for
you	to	differentiate	a	subclass	from	its	parent	is	through	overriding.	Recall	from

Chapter	12	that	a	class	uses	properties	to	represent	data	and	functions	to
represent	behavior.	Subclasses	can	override,	or	provide	custom	implementations
for,	both.
Room	has	two	functions,	description	and	load.	TownSquare	should
provide	its	own	implementation	of	load	to	express	the	joy	that	comes	with	your
hero	entering	the	town	square.
Override	load	in	TownSquare	using	the	override	keyword:
Listing	14.5		Declaring	the	TownSquare	class	(Room.kt)
open	class	Room(val	name:	String)	{
fun	description()	=	"Room:	$name"
fun	load()	=	"Nothing	much	to	see	here..."
## }
class	TownSquare	:	Room("Town	Square")	{
override	fun	load()	=	"The	villagers	rally	and	cheer	as	you	enter!"
## }
When	you	override	load,	IntelliJ	complains	about	your	override	keyword
(Figure	14.1):
Figure	14.1		load	cannot	be	overridden
IntelliJ	is	right,	as	always:	There	is	a	problem.	In	addition	to	Room	being	marked
open,	load	must	also	be	marked	open	for	you	to	override	it.
Mark	the	load	function	in	the	Room	class	as	a	function	that	can	be	overridden.
Listing	14.6		Declaring	an	open	function	(Room.kt)
open	class	Room(val	name:	String)	{
fun	description()	=	"Room:	$name"
open	fun	load()	=	"Nothing	much	to	see	here..."
## }
class	TownSquare	:	Room("Town	Square")	{
override	fun	load()	=	"The	villagers	rally	and	cheer	as	you	enter!"
## }

Now,	instead	of	printing	a	default	statement	(Nothing	much	to	see	here...),
an	instance	of	TownSquare	will	display	the	cheering	villagers	when	the	hero
enters	and	load	is	called.
In	Chapter	12,	you	saw	how	to	control	the	visibility	of	properties	and	functions
using	visibility	modifiers.	Properties	and	functions	are	public	by	default.	You
can	also	make	them	visible	only	within	the	class	where	they	are	defined	by
setting	visibility	to	private.
Protected	visibility	is	a	third	option	that	restricts	visibility	to	the	class	in	which	a
property	or	function	is	defined	or	to	any	subclasses	of	that	class.
Add	a	new	protected	property	called	dangerLevel	to	Room.
Listing	14.7		Declaring	a	protected	property	(Room.kt)
open	class	Room(val	name:	String)	{
protected	open	val	dangerLevel	=	5
fun	description()	=	"Room:	$name\n"	+
"Danger	level:	$dangerLevel"
open	fun	load()	=	"Nothing	much	to	see	here..."
## }
class	TownSquare	:	Room("Town	Square")	{
override	fun	load()	=	"The	villagers	rally	and	cheer	as	you	enter!"
## }
dangerLevel	holds	a	rating	of	how	dangerous	a	room	is	on	a	scale	of	1	to	10.
It	is	printed	to	the	console	so	that	the	player	knows	what	level	of	suspense	to
expect	in	each	room.	The	average	danger	level	is	5,	so	that	is	the	default	value
assigned	to	the	Room	class.
Subclasses	of	Room	can	modify	dangerLevel	to	reflect	how	dangerous	(or
not)	a	particular	room	is,	but	dangerLevel	should	otherwise	be	encapsulated
to	Room	and	its	subclasses.	This	scenario	is	perfect	for	the	protected	keyword:
You	want	to	expose	a	property	only	to	the	class	where	the	property	is	defined
and	its	subclasses.
To	override	the	dangerLevel	property	in	TownSquare,	you	use	the
override	keyword,	just	as	you	did	with	the	load	function.
The	danger	level	of	a	NyetHack	town	square	is	three	points	below	average.	To
express	this	logic,	you	need	to	be	able	to	reference	the	average	danger	level	of	a
Room.	You	can	reference	a	class’s	superclass	using	the	super	keyword.	From
there,	you	have	access	to	any	public	or	protected	properties	or	functions,
including,	in	this	case,	dangerLevel.
Override	dangerLevel	in	TownSquare	to	indicate	that	the	danger	level	of	a

town	square	is	three	points	below	the	average	room.
Listing	14.8		Overriding	dangerLevel	(Room.kt)
open	class	Room(val	name:	String)	{
protected	open	val	dangerLevel	=	5
fun	description()	=	"Room:	$name\n"	+
"Danger	level:	$dangerLevel"
open	fun	load()	=	"Nothing	much	to	see	here..."
## }
class	TownSquare	:	Room("Town	Square")	{
override	val	dangerLevel	=	super.dangerLevel	-	3
override	fun	load()	=	"The	villagers	rally	and	cheer	as	you	enter!"
## }
Subclasses	are	not	limited	to	overriding	the	properties	and	functions	of	their
superclass.	They	can	also	define	their	own.
NyetHack	town	squares,	for	example,	are	unique	among	rooms	in	that	they	have
bells	that	chime	to	announce	important	happenings.	Add	a	private	function
called	ringBell	and	a	private	variable	called	bellSound	to	TownSquare.
bellSound	holds	a	string	representing	the	sound	that	the	bell	makes,	and
ringBell,	called	in	the	load	function,	returns	a	string	to	announce	your	entry
to	the	town	square.
Listing	14.9		Adding	a	new	property	and	function	to	a	subclass
(Room.kt)
open	class	Room(val	name:	String)	{
protected	open	val	dangerLevel	=	5
fun	description()	=	"Room:	$name\n"	+
"Danger	level:	$dangerLevel"
open	fun	load()	=	"Nothing	much	to	see	here..."
## }
class	TownSquare	:	Room("Town	Square")	{
override	val	dangerLevel	=	super.dangerLevel	-	3
private	var	bellSound	=	"GWONG"
override	fun	load()	=	"The	villagers	rally	and	cheer	as	you	enter!\n${ringBell()}"
private	fun	ringBell()	=	"The	bell	tower	announces	your	arrival.	$bellSound"
## }
TownSquare	includes	properties	and	functions	defined	both	in	TownSquare
and	in	Room.	Room,	however,	does	not	include	all	properties	and	functions
declared	in	TownSquare,	so	it	does	not	have	access	to	ringBell.
Test	the	load	function	by	updating	the	currentRoom	variable	in	Game.kt
to	create	an	instance	of	TownSquare.
Listing	14.10		Calling	subclass	function	implementation	(Game.kt)
fun	main(args:	Array<String>)	{
val	player	=	Player("Madrigal")

player.castFireball()
var	currentRoom:	Room	=	Room("Foyer")TownSquare()
println(currentRoom.description())
println(currentRoom.load())
//	Player	status
printPlayerStatus(player)
## }
## ...
Run	Game.kt	again.	You	should	see	the	following	output	to	the	console:
A	glass	of	Fireball	springs	into	existence.	Delicious!	(x2)
## Room:	Town	Square
Danger	level:	2
The	villagers	rally	and	cheer	as	you	enter!
The	bell	tower	announces	your	arrival.	GWONG
(Aura:	GREEN)	(Blessed:	YES)
Madrigal	of	Tampa	is	in	excellent	condition!
Notice	that	the	currentRoom	variable’s	type	in	Game.kt	is	still	Room,
despite	the	fact	that	the	instance	itself	is	a	TownSquare,	and	its	load	function
has	been	changed	substantially	from	Room’s	implementation.	You	explicitly
declared	the	type	of	currentRoom	to	be	Room	so	that	it	can	hold	any	type	of
Room,	even	though	you	assigned	currentRoom	with	a	TownSquare
constructor.
Since	TownSquare	subclasses	Room,	this	is	completely	valid	syntax.
You	can	also	subclass	a	subclass,	creating	a	deeper	hierarchy.	If	you	created	a
subclass	of	TownSquare	called	Piazza,	then	Piazza	would	also	be	of	type
TownSquare	and	of	type	Room.	The	only	limit	to	the	number	of	levels	that
you	can	subclass	is	what	makes	sense	for	the	organization	of	your	codebase.
(And,	of	course,	your	imagination.)
The	different	versions	of	load,	based	on	the	class	they	are	called	on,	are	an
example	of	a	concept	in	object-oriented	programming	called	polymorphism.
Polymorphism	is	a	strategy	for	simplifying	the	structure	of	your	program.	It
allows	you	to	reuse	functions	for	common	sets	of	features	across	groups	of
classes	(like	what	happens	when	a	player	enters	a	room)	and	also	to	customize
the	behavior	for	the	unique	needs	of	a	class	(like	the	cheering	crowd	in
TownSquare).	When	you	subclassed	Room	to	define	TownSquare,	you
defined	a	new	load	implementation	that	overrides	Room’s	version.	Now,	when
currentRoom’s	load	function	is	called,	TownSquare’s	version	of	load
will	be	used	–	and	no	changes	to	Game.kt	were	required.
Consider	the	following	function	header.
fun	drawBlueprint(room:	Room)
drawBlueprint	accepts	a	Room	as	its	parameter.	It	can	also	accept	any
subclass	of	Room,	because	any	subclass	will	have	at	least	the	capabilities	that

Room	does.	Polymorphism	allows	you	to	write	functions	that	care	only	about
what	a	class	can	do,	not	how	it	is	implemented.
Opening	up	functions	to	be	overridden	is	useful	–	but	it	does	come	with	a	side
effect.	When	you	override	a	function	in	Kotlin,	the	overriding	function	in	the
subclass	is,	by	default,	open	to	being	overridden	(as	long	as	the	subclass	is
marked	open).
What	if	you	do	not	want	this	to	be	the	case?	In	the	TownSquare	example,	say
that	you	wanted	any	subclass	of	TownSquare	to	be	able	to	customize	its
description	but	not	how	it	loads.
The	final	keyword	allows	you	to	specify	that	a	function	cannot	be	overridden.
Open	TownSquare	and	make	its	load	function	final	so	that	no	one	can
override	the	fact	that	villagers	cheer	when	you	enter	a	town	square.
Listing	14.11		Declaring	a	function	to	be	final	(Room.kt)
open	class	Room(val	name:	String)	{
protected	open	val	dangerLevel	=	5
fun	description()	=	"Room:	$name\n"	+
"Danger	level:	$dangerLevel"
open	fun	load()	=	"Nothing	much	to	see	here..."
## }
open	class	TownSquare	:	Room("Town	Square")	{
override	val	dangerLevel	=	super.dangerLevel	-	3
private	var	bellSound	=	"GWONG"
final	override	fun	load()	=
"The	villagers	rally	and	cheer	as	you	enter!\n${ringBell()}"
private	fun	ringBell()	=	"The	bell	tower	announces	your	arrival.	$bellSound"
## }
Now,	any	subclass	of	TownSquare	could	provide	an	overriding	function	for
description	but	not	load,	thanks	to	the	final	keyword.
As	you	saw	when	you	first	tried	to	override	load,	functions	are	final	by	default
unless	they	are	inherited	from	an	open	class.	Adding	the	final	keyword	to	an
inherited	function	will	ensure	that	it	cannot	be	overridden,	even	if	the	class	in
which	it	is	defined	is	open.
You	have	now	seen	how	to	use	inheritance	to	share	data	and	behavior	between
classes.	You	have	also	seen	how	to	use	open,	final,	and	override	to	customize
what	can	and	cannot	be	shared.	By	requiring	the	explicit	use	of	the	open	and
override	keywords,	Kotlin	requires	you	to	opt	in	to	inheritance.	This	reduces
the	chances	of	exposing	classes	that	were	not	meant	to	be	subclassed	and
prevents	you	–	or	others	–	from	overriding	functions	that	were	never	meant	to	be
overridden.

## Type	Checking
NyetHack	is	not	a	terribly	complex	program.	But	a	production	codebase	can
include	many	classes	and	subclasses.	Despite	your	best	efforts	at	clear	naming,
you	may	find	yourself	from	time	to	time	unsure	of	the	type	of	a	variable	at
runtime.	The	is	operator	is	a	useful	tool	that	lets	you	query	whether	an	object	is
of	a	certain	type.
Try	this	out	in	the	Kotlin	REPL.	Instantiate	a	Room.	(You	may	need	to	import
Room	into	the	REPL.)
Listing	14.12		Instantiating	a	Room	(REPL)
var	room	=	Room("Foyer")
Next,	query	whether	room	is	an	instance	of	the	Room	class	using	the	is
operator.
Listing	14.13		Checking	room	is	Room	(REPL)
room	is	Room
true
The	type	of	the	object	on	the	lefthand	side	of	the	is	operator	is	checked	against
the	type	on	the	righthand	side.	The	expression	returns	a	Boolean:	true	if	the
types	match,	false	otherwise.
Try	another	query:	Check	whether	room	is	an	instance	of	the	TownSquare
class.
Listing	14.14		Checking	room	is	TownSquare	(REPL)
room	is	TownSquare
false
room	is	of	type	Room,	which	is	a	parent	class	to	TownSquare.	But	room	is
not	itself	a	TownSquare.
Try	another	variable	–	this	time,	a	TownSquare.
Listing	14.15		Checking	townSquare	is	TownSquare	(REPL)
var	townSquare	=	TownSquare()
townSquare	is	TownSquare
true
Listing	14.16		Checking	townSquare	is	Room	(REPL)
townSquare	is	Room
true

townSquare	is	of	type	TownSquare	and	also	of	type	Room.	This,
remember,	is	the	idea	that	makes	polymorphism	possible.
If	you	need	to	know	the	type	of	a	variable,	type	checking	is	a	straightforward
way	to	find	out.	You	can	build	branching	logic	using	type	checking	and
conditionals	–	but	be	sure	to	bear	in	mind	how	polymorphism	will	affect	that
logic.
For	example,	create	a	when	expression	in	the	Kotlin	REPL	that	returns	Room	or
TownSquare	depending	on	the	type	of	a	variable.
Listing	14.17		Type	checking	as	a	branching	condition	(REPL)
var	townSquare	=	TownSquare()
var	className	=	when(townSquare)	{
is	TownSquare	->	"TownSquare"
is	Room	->	"Room"
else	->	throw	IllegalArgumentException()
## }
print(className)
The	first	branch	in	this	when	expression	evaluates	as	true,	because
townSquare	is	of	type	TownSquare.	The	second	branch	is	also	true,
because	townSquare	is	also	of	type	Room	–	but	that	does	not	matter,	because
the	first	branch	was	already	satisfied.
Run	this	code,	and	TownSquare	is	printed	to	the	console.
Now	reverse	the	order	of	the	branches:
Listing	14.18		Type	checking	with	reversed	conditions	(REPL)
var	townSquare	=	TownSquare()
var	className	=	when(townSquare)	{
is	TownSquare	->	"TownSquare"
is	Room	->	"Room"
is	TownSquare	->	"TownSquare"
else	->	throw	IllegalArgumentException()
## }
print(className)
Run	this	code,	and	this	time	Room	is	printed	to	the	console,	because	the	first
branch	evaluates	to	true.
When	branching	conditionally	on	object	type,	order	matters.

## The	Kotlin	Type	Hierarchy
Every	class	in	Kotlin	descends	from	a	common	superclass,	known	as	Any,
without	you	having	to	explicitly	subclass	it	in	your	code	(Figure	14.2).
Figure	14.2		TownSquare	type	hierarchy
For	example,	a	Room	and	a	Player	are	both	implicitly	children	of	Any,	which
is	why	you	can	define	functions	that	will	accept	either	of	them	as	parameters.	If
you	have	worked	with	Java,	this	is	similar	to	how	classes	in	Java	subclass	the
java.lang.Object	class	implicitly.
Consider	the	following	example	of	a	function	called
printIsSourceOfBlessings.	printIsSourceOfBlessings	takes
in	an	argument	of	type	Any	and	uses	type	checking	to	branch	conditionally	on
the	type	of	the	argument	passed	to	it.	It	finishes	by	printing	a	statement	based	on
the	result.	There	are	some	new	concepts	in	this	code	that	we	will	discuss	over	the
next	couple	of	sections.
fun	printIsSourceOfBlessings(any:	Any)	{
val	isSourceOfBlessings	=	if	(any	is	Player)	{
any.isBlessed
}	else	{
(any	as	Room).name	==	"Fount	of	Blessings"
## }

println("$any	is	a	source	of	blessings:	$isSourceOfBlessings")
## }
In	NyetHack,	only	two	things	are	a	source	of	blessings:	a	blessed	player	or	the
room	called	Fount	of	Blessings.
Because	every	object	is	a	subclass	of	Any,	you	can	pass	arguments	of	whatever
type	you	want	to	printIsSourceOfBlessings.	This	flexibility	is	useful,
but	it	comes	at	the	cost	of	not	being	able	to	immediately	work	with	the
argument.	This	example	employs	type	casting	to	get	a	handle	on	the	slippery
Any	argument.
Type	casting
A	type	check	may	not	always	return	a	useful	answer.	For	example,	the	any
parameter	in	the	printIsSourceOfBlessings	function	tells	you	that	the
argument	passed	will	be	of	type	Any,	but	the	Any	type	is	unspecific	about	what
you	can	do	with	that	argument.
Type	casting	allows	you	to	treat	an	object	as	if	it	were	an	instance	of	a	different
type.	This	gives	you	the	power	to	do	anything	with	an	object	that	you	would	do
with	an	object	of	the	type	you	specify	(such	as	call	functions	on	it).
In	the	printIsSourceOfBlessings	function,	the	conditional	expression
uses	a	type	check	to	see	whether	any	is	of	type	Player.	If	it	is	not,	then	the
code	on	the	else	branch	will	be	executed.
The	else	branch	references	a	name	variable:
fun	printIsSourceOfBlessings(any:	Any)	{
val	isSourceOfBlessings	=	if	(any	is	Player)	{
any.isBlessed
}	else	{
(any	as	Room).name	==	"Fount	of	Blessings"
## }
println("$any	is	a	source	of	blessings:	$isSourceOfBlessings")
## }
The	as	operator	denotes	a	type	cast.	This	cast	says,	“Treat	any	as	if	it	were	of
type	Room	for	the	purposes	of	this	expression.”	The	expression	in	this	case	is	a
reference	to	Room’s	name	property,	so	that	it	can	be	compared	against	the	string
"Fount	of	Blessings".
Casting	is	powerful	and	comes	with	great	responsibility;	you	have	to	use	it
safely.	An	example	of	a	safe	cast	would	be	casting	from	an	Int	to	a	more
precise	number	type	like	Long.
The	cast	in	printIsSourceOfBlessings	works	–	but	it	is	not	safe.	Why

not?	Room,	Player,	and	TownSquare	are	the	only	three	classes	in	NyetHack,
so	isn’t	it	valid	to	assume	that	if	any	is	not	of	type	Player,	then	it	must	be	of
type	Room?
It	is	–	at	the	moment.	But	what	happens	when	a	new	class	is	added	to	NyetHack?
Your	cast	will	fail	if	the	type	being	cast	to	is	incompatible	with	the	type	being
cast	from.	A	String	has	nothing	to	do	with	an	Int,	for	example,	so	a	cast
from	String	to	Int	would	cause	a	ClassCastException	that	may	crash
your	program.	(Bear	in	mind	that	a	cast	is	different	from	a	conversion.	Some
strings	can	be	converted	to	integers;	no	String	can	be	cast	to	an	Int.)
Casts	allow	you	to	attempt	to	cast	any	variable	to	any	type,	but	it	is	up	to	you	to
make	sure	that	you	are	confident	in	the	type	of	what	you	are	casting	from	and
casting	to.	If	you	must	make	an	unsafe	cast,	then	surrounding	it	with	a
try/catch	block	is	a	good	idea.	It	is	best,	however,	to	avoid	type	casting	unless
you	are	sure	that	the	cast	will	succeed.
Smart	casting
One	way	to	be	sure	that	a	cast	will	succeed	is	by	first	checking	the	type	of	the
variable	being	cast.	Return	to	the	first	branch	of	the	conditional	expression	in
printIsSourceOfBlessings.
fun	printIsSourceOfBlessings(any:	Any)	{
val	isSourceOfBlessings	=	if	(any	is	Player)	{
any.isBlessed
}	else	{
(any	as	Room).name	==	"Fount	of	Blessings"
## }
println("$any	is	a	source	of	blessings:	$isSourceOfBlessings")
## }
The	condition	for	entering	this	branch	is	for	any	to	be	of	type	Player.	Inside
the	branch,	a	reference	to	the	isBlessed	property	is	made	on	any.
isBlessed	is	a	property	defined	on	Player,	not	Any,	so	how	is	this	possible
without	a	cast?
There	is,	in	fact,	a	cast	happening	here	–	a	smart	cast.	You	previously	saw	smart
casts	in	action	in	Chapter	6.
The	Kotlin	compiler	is	smart	enough	to	recognize	that	if	the	any	is	Player	type
check	is	successful	for	a	branch,	then	any	can	be	treated	as	a	Player	within
that	branch.	Because	it	knows	that	casting	any	to	Player	will	always	succeed
in	this	branch,	the	compiler	lets	you	drop	the	cast	syntax	and	just	reference
isBlessed,	a	Player	property,	on	any.

Smart	casting	is	an	example	of	how	the	intelligence	of	the	Kotlin	compiler
results	in	a	more	concise	syntax.
In	this	chapter	you	have	seen	how	to	use	subclassing	to	share	behavior	between
classes.	In	the	next	chapter,	you	will	work	with	more	types	of	classes,	including
data	classes,	enums,	and	object	–	Kotlin’s	single-instance	class	–	as	you	add	a
game	loop	to	NyetHack.

For	the	More	Curious:	Any
When	you	print	the	value	of	a	variable	to	the	console,	a	function	called
toString	is	called	to	determine	what	that	value	looks	like	in	the	console.	For
some	types,	this	is	easy	–	for	example,	the	value	of	a	string	makes	sense	to
represent	a	String	value.	For	other	types,	this	is	a	bit	less	clear.
Any	provides	abstract	definitions	for	common	functions	like	toString,	which
are	backed	by	an	implementation	found	on	the	platform	that	your	project	targets.
A	peek	at	the	source	for	the	Any	class	yields	the	following:
## /**
- The	root	of	the	Kotlin	class	hierarchy.
- Every	Kotlin	class	has	[Any]	as	a	superclass.
## */
public	open	class	Any	{
public	open	operator	fun	equals(other:	Any?):	Boolean
public	open	fun	hashCode():	Int
public	open	fun	toString():	String
## }
Notice	that	no	definition	of	the	toString	function	is	contained	in	the	class
definition.	So	where	is	it	defined,	and	what	is	returned	when	the	toString
function	for,	say,	a	Player	is	called?
Recall	that	the	last	line	of	printIsSourceOfBlessings	prints	to	the
console:
fun	printIsSourceOfBlessings(any:	Any)	{
val	isSourceOfBlessings	=	if	(any	is	Player)	{
any.isBlessed
}	else	{
(any	as	Room).name	==	"Fount	of	Blessings"
## }
println("$any	is	a	source	of	blessings:	$isSourceOfBlessings")
## }
The	result	of	calling	printIsSourceOfBlessings	and	passing	it	a	blessed
player	looks	something	like	this:
Player@71efa55d	is	a	source	of	blessings:	true
Player@71efa55d	is	the	result	of	the	default	implementation	of	toString	on
the	Any	class.	Kotlin	uses	the	JVM’s	java.lang.Object.toString
implementation	because	you	targeted	the	JVM	for	compilation.	You	can	override
toString	in	your	Player	class	to	return	something	more	human-readable.
The	Any	type	is	one	of	the	ways	that	Kotlin	allows	for	platform	independence	–
it	provides	an	abstraction	above	the	class	that	represents	a	common	superclass	on
each	specific	platform,	like	the	JVM.	So	while	Any’s	toString

implementation	is	java.lang.Object.toString	when	targeting	the
JVM,	it	could	be	something	entirely	different	when	compiling	down	to
JavaScript.	This	abstraction	means	that	you	do	not	need	to	know	the	details	of
each	platform	that	your	code	could	be	run	on.	Instead,	you	can	simply	rely	on
the	Any	type.