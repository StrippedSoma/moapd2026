

## 15
## Objects
In	the	last	three	chapters,	you	learned	how	to	leverage	object-oriented
programming	principles	to	build	meaningful	relationships	between	objects.
Despite	the	variety	in	how	they	can	be	initialized,	all	of	the	classes	that	you	have
worked	with	thus	far	have	been	declared	with	the	same	class	keyword.	This
chapter	introduces	object	declarations	as	well	as	other	types	of	classes:
nested	classes,	data	classes,	and	enum	classes.	As	you	will	see,	each	has
its	own	declaration	syntax	and	unique	characteristics.
By	the	end	of	this	chapter,	your	hero	will	be	able	to	walk	from	room	to	room
around	the	world	of	NyetHack,	thanks	to	all	the	work	you	have	put	into	the
game.	And	your	program	will	be	well	organized	to	support	further	enhancements
coming	in	later	chapters.

The	object	Keyword
In	Chapter	13,	you	learned	about	constructing	classes.	A	class	constructor
returns	an	instance	of	a	class,	and	you	can	call	the	constructor	any	number	of
times	to	create	any	number	of	instances.
For	example,	NyetHack	can	have	any	number	of	players,	because	Player’s
constructor	can	be	called	as	many	times	as	you	would	like.	For	Player,	this	is
desirable,	because	the	world	of	NyetHack	is	big	enough	for	multiple	players.
But	suppose	you	wanted	a	Game	class	to	keep	track	of	the	state	of	the	game.
Having	multiple	instances	of	Game	would	be	a	problem,	because	they	could
each	hold	their	own	states,	which	could	potentially	get	out	of	sync	with	each
other.
If	you	need	to	hold	on	to	a	single	instance	with	state	that	is	consistent	throughout
the	time	that	your	program	is	running,	consider	defining	a	singleton.	With	the
object	keyword,	you	specify	that	a	class	will	be	limited	to	a	single	instance	–	a
singleton.	The	first	time	you	access	an	object,	it	is	instantiated	for	you.	That
same	instance	will	persist	as	long	as	your	program	is	running,	and	each
subsequent	access	will	then	return	the	original	instance.
There	are	three	ways	to	use	the	object	keyword:	object	declarations,	object
expressions,	and	companion	objects.	We	will	outline	the	uses	for	each	in	the	next
three	sections.
Object	declarations
Object	declarations	are	useful	for	organization	and	state	management,	especially
when	you	need	to	maintain	some	state	consistently	throughout	the	lifespan	of
your	program.	You	are	going	to	define	a	Game	object	to	do	just	that.
Defining	a	Game	class	using	an	object	declaration	will	also	give	you	a
convenient	place	to	define	a	game	loop	and	will	allow	you	to	clean	up	the	main
function	in	Game.kt.	And	breaking	code	out	into	classes	and	object
declarations	furthers	your	quest	for	a	codebase	that	remains	organized	at	scale.
Define	a	Game	object	in	Game.kt	using	an	object	declaration.
Listing	15.1		Declaring	the	Game	object	(Game.kt)

fun	main(args:	Array<String>)	{
## ...
## }
private	fun	printPlayerStatus(player:	Player)	{
println("(Aura:	${player.auraColor()})	"	+
"(Blessed:	${if	(player.isBlessed)	"YES"	else	"NO"})")
println("${player.name}	${player.formatHealthStatus()}")
## }
object	Game	{
## }
The	main	function	in	Game.kt	should	now	serve	exclusively	to	kick	off
gameplay.	All	game	logic	will	be	encapsulated	in	the	Game	object,	of	which
there	will	be	only	one	instance.
Because	an	object	declaration	is	instantiated	for	you,	you	do	not	add	a	custom
constructor	with	code	to	be	called	at	initialization.	Instead,	you	need	an
initializer	block	for	any	code	that	you	want	to	be	called	when	your	object	is
initialized.	Add	one	to	the	Game	object	with	a	greeting	to	be	printed	to	the
console	when	the	object	is	instantiated.
Listing	15.2		Adding	an	init	block	to	Game	(Game.kt)
fun	main(args:	Array<String>)	{
## ...
## }
private	fun	printPlayerStatus(player:	Player)	{
println("(Aura:	${player.auraColor()})	"	+
"(Blessed:	${if	(player.isBlessed)	"YES"	else	"NO"})")
println("${player.name}	${player.formatHealthStatus()}")
## }
object	Game	{
init	{
println("Welcome,	adventurer.")
## }
## }
Run	Game.kt.	Your	welcome	message	does	not	print,	because	Game	has	not
been	initialized.	And	Game	has	not	been	initialized	because	it	has	not	been
referenced	yet.
An	object	declaration	is	referenced	by	one	of	its	properties	or	functions.	To
trigger	Game’s	initialization,	you	will	define	and	call	a	function	called	play.
play	will	serve	as	the	home	of	the	game	loop	for	NyetHack.
Add	play	to	Game	and	call	it	from	main.	When	you	call	a	function	defined	in
an	object	declaration,	you	call	it	using	the	name	of	the	object	in	which	it	is
defined	–	not	on	an	instance	of	a	class,	as	you	do	for	other	class	functions.
Listing	15.3		Calling	a	function	defined	on	an	object	declaration
(Game.kt)
fun	main(args:	Array<String>)	{
## ...

//	Player	status
printPlayerStatus(player)
## Game.play()
## }
private	fun	printPlayerStatus(player:	Player)	{
println("(Aura:	${player.auraColor()})	"	+
"(Blessed:	${if	(player.isBlessed)	"YES"	else	"NO"})")
println("${player.name}	${player.formatHealthStatus()}")
## }
object	Game	{
init	{
println("Welcome,	adventurer.")
## }
fun	play()	{
while	(true)	{
//	Play	NyetHack
## }
## }
## }
The	Game	object	will	do	more	than	encapsulate	the	game	state;	it	will	also	hold
the	game	loop	in	order	to	take	commands	from	the	player.	Your	game	loop	takes
the	form	of	a	while	loop	that	will	make	NyetHack	more	interactive.	The	while
loop	has	a	simple	condition:	true.	This	will	keep	the	game	loop	running	as	long
as	your	application	is	running.
For	now,	play	does	not	do	anything.	Eventually,	it	will	define	NyetHack’s
gameplay	in	terms	of	rounds:	For	each	round,	the	player’s	status	and	other
information	describing	the	world	will	be	printed	to	the	console.	Then,	user	input
will	be	accepted	via	the	readLine	function.
Take	a	look	at	the	game	logic	that	is	in	main	and	think	about	where	it	should	go
in	Game.	For	example,	you	will	not	want	to	create	a	new	Player	instance	or	a
new	currentRoom	at	the	beginning	of	each	round,	so	these	aspects	of	game
logic	belong	in	Game,	but	not	in	play.	Declare	player	and	currentRoom
as	private	properties	of	Game.
Next,	move	the	call	to	castFireball	to	Game’s	initializer	block	for	a	fun
start	to	each	game	of	NyetHack,	and	move	the	definition	of
printPlayerStatus	to	Game	as	well.	Make	printPlayerStatus
private,	like	player	and	currentRoom,	to	encapsulate	it	within	Game.
Listing	15.4		Encapsulating	properties	and	functions	within	the
object	declaration	(Game.kt)
fun	main(args:	Array<String>)	{
val	player	=	Player("Madrigal")
player.castFireball()
var	currentRoom:	Room	=	TownSquare()
println(currentRoom.description())
println(currentRoom.load())
//	Player	status
printPlayerStatus(player)

## Game.play()
## }
private	fun	printPlayerStatus(player:	Player)	{
println("(Aura:	${player.auraColor()})	"	+
"(Blessed:	${if	(player.isBlessed)	"YES"	else	"NO"})")
println("${player.name}	${player.formatHealthStatus()}")
## }
object	Game	{
private	val	player	=	Player("Madrigal")
private	var	currentRoom:	Room	=	TownSquare()
init	{
println("Welcome,	adventurer.")
player.castFireball()
## }
fun	play()	{
while	(true)	{
//	Play	NyetHack
## }
## }
private	fun	printPlayerStatus(player:	Player)	{
println("(Aura:	${player.auraColor()})	"	+
"(Blessed:	${if	(player.isBlessed)	"YES"	else	"NO"})")
println("${player.name}	${player.formatHealthStatus()}")
## }
## }
Moving	code	from	the	main	function	in	Game.kt	to	the	play	function	in
Game	keeps	the	code	that	is	essential	for	setting	up	the	game	loop	encapsulated
within	Game.
What	is	left	in	main?	Three	sets	of	information	are	printed:	currentRoom’s
description	and	load	statement	and	the	player’s	status.	These	things	should	be
printed	at	the	beginning	of	each	round	of	gameplay,	so	move	them	to	the	game
loop.	Leave	the	call	to	Game.play	in	main.
Listing	15.5		Printing	status	in	the	game	loop	(Game.kt)
fun	main(args:	Array<String>)	{
println(currentRoom.description())
println(currentRoom.load())
//	Player	status
printPlayerStatus(player)
## Game.play()
## }
object	Game	{
private	val	player	=	Player("Madrigal")
private	var	currentRoom:	Room	=	TownSquare()
init	{
println("Welcome,	adventurer.")
player.castFireball()
## }
fun	play()	{
while	(true)	{
//	Play	NyetHack
println(currentRoom.description())
println(currentRoom.load())
//	Player	status
printPlayerStatus(player)
## }
## }

private	fun	printPlayerStatus(player:	Player)	{
println("(Aura:	${player.auraColor()})	"	+
"(Blessed:	${if	(player.isBlessed)	"YES"	else	"NO"})")
println("${player.name}	${player.formatHealthStatus()}")
## }
## }
If	you	were	to	run	Game.kt	right	now,	it	would	loop	indefinitely,	as	there	is
nothing	to	stop	the	loop.	The	last	step	for	the	game	loop,	at	least	for	now,	is	to
accept	user	input	from	the	console	using	the	readLine	function.	You	may
remember	readLine	from	Chapter	6:	It	pauses	execution	while	it	waits	for
user	input	in	the	console.	Once	the	return	character	is	received,	it	resumes
execution,	returning	the	input	that	was	collected.
Add	a	call	to	readLine	in	your	game	loop	to	accept	user	input.
Listing	15.6		Accepting	user	input	(Game.kt)
## ...
object	Game	{
## ...
fun	play()	{
while	(true)	{
println(currentRoom.description())
println(currentRoom.load())
//	Player	status
printPlayerStatus(player)
print(">	Enter	your	command:	")
println("Last	command:	${readLine()}")
## }
## }
## ...
## }
Try	running	Game.kt	now	and	entering	a	command:
Welcome,	adventurer.
A	glass	of	Fireball	springs	into	existence.	Delicious!	(x2)
## Room:	Town	Square
Danger	level:	2
The	villagers	rally	and	cheer	as	you	enter!
The	bell	tower	announces	your	arrival.	GWONG
(Aura:	GREEN)	(Blessed:	YES)
Madrigal	of	Tampa	is	in	excellent	condition!
>	Enter	your	command:	fight
Last	command:	fight
## Room:	Town	Square
Danger	level:	2
The	villagers	rally	and	cheer	as	you	enter!
The	bell	tower	announces	your	arrival.	GWONG
(Aura:	GREEN)	(Blessed:	YES)
Madrigal	of	Tampa	is	in	excellent	condition!
>	Enter	your	command:
Did	you	notice	that	the	entered	text	is	displayed	back	to	you?	Excellent	–	new
input	is	being	scanned	into	the	game.
Object	expressions
Defining	a	class	using	the	class	keyword	is	useful	in	that	it	establishes	a	new

concept	in	your	codebase.	By	writing	a	class	called	Room,	you	communicate	that
rooms	exist	in	NyetHack.	And	by	writing	a	subclass	of	Room	called
TownSquare,	you	establish	that	there	can	be	specific	types	of	rooms	called
town	squares.
But	defining	a	new,	named	class	is	not	always	necessary.	Perhaps	you	need	a
class	instance	that	is	a	variation	of	an	existing	class	and	will	be	used	for	a	one-
off	purpose.	In	fact,	it	will	be	so	temporary	that	it	does	not	even	require	a	name.
This	is	another	use	for	the	object	keyword:	an	object	expression.	Look	at	the
following	example:
val	abandonedTownSquare	=	object	:	TownSquare()	{
override	fun	load()	=	"You	anticipate	applause,	but	no	one	is	here..."
## }
This	object	expression	is	a	subclass	of	TownSquare	where	no	one	cheers	your
entrance.	In	the	body	of	this	declaration,	the	properties	and	functions	defined	in
TownSquare	can	be	overridden	–	and	new	properties	and	functions	can	be
added	–	to	define	the	data	and	behavior	of	the	anonymous	class.
This	class	still	adheres	to	the	rules	of	the	object	keyword	in	that	there	will	only
ever	be	one	instance	of	it	alive	at	a	time,	but	it	is	significantly	smaller	in	scope
than	a	named	singleton.	As	a	side	effect,	an	object	expression	takes	on	some	of
the	attributes	of	where	it	is	declared.	If	declared	at	the	file	level,	an	object
expression	is	initialized	immediately.	If	declared	within	another	class,	it	is
initialized	when	its	enclosing	class	is	initialized.
Companion	objects
If	you	would	like	to	tie	the	initialization	of	an	object	to	a	class	instance,	there	is
another	option	for	you:	a	companion	object.	A	companion	object	is	declared
within	another	class	declaration	using	the	companion	modifier.	A	class	can	have
no	more	than	one	companion	object.
There	are	two	cases	in	which	a	companion	object	will	be	initialized.	First,	a
companion	object	is	initialized	when	its	enclosing	class	is	initialized.	This	makes
it	a	good	place	for	singleton	data	that	has	a	contextual	connection	to	a	class
definition.	Second,	a	companion	object	is	initialized	when	one	of	its	properties
or	functions	is	accessed	directly.
Because	a	companion	object	is	still	an	object	declaration,	you	do	not	need	an
instance	of	a	class	to	use	any	of	the	functions	or	properties	defined	in	it.	Take	a
look	at	the	following	example	of	a	companion	object	defined	within	a	class

called	PremadeWorldMap:
class	PremadeWorldMap	{
## ...
companion	object	{
private	const	val	MAPS_FILEPATH	=	"nyethack.maps"
fun	load()	=	File(MAPS_FILEPATH).readBytes()
## }
## }
PremadeWorldMap	has	a	companion	object	with	one	function	called	load.	If
you	were	to	call	load	from	elsewhere	in	your	codebase,	you	would	do	so
without	needing	an	instance	of	PremadeWorldMap,	like	so:
PremadeWorldMap.load()
The	contents	of	this	companion	object	will	not	be	loaded	until	either
PremadeWorldMap	is	initialized	or	load	is	called.	And	no	matter	how	many
times	PremadeWorldMap	is	instantiated,	there	will	only	ever	be	one	instance
of	its	companion	object.
Understanding	the	differences	in	how	and	when	object	declarations,	object
expressions,	and	companion	objects	are	instantiated	is	key	in	understanding
when	to	use	each	of	them	effectively.	And	using	them	effectively	helps	you	write
well-organized	code	that	scales	well.

## Nested	Classes
Not	all	classes	defined	within	other	classes	are	declared	without	a	name.	You	can
also	use	the	class	keyword	to	define	a	named	class	nested	inside	of	another
class.	In	this	section,	you	will	define	a	new	GameInput	class	nested	within	the
Game	object.
Now	that	you	have	defined	a	game	loop,	you	will	want	to	apply	some	control
over	the	user	input	passed	to	your	game.	NyetHack	is	a	text	adventure,	driven	by
the	user	entering	commands	to	the	readLine	function.	There	are	two	things
you	need	to	ensure	about	the	user’s	commands:	First,	that	they	are	valid
commands.	Second,	that	commands	with	multiple	parts,	like	“move	east,”	are
handled	correctly.	You	want	“move”	to	trigger	a	move	function	and	“east”	to
provide	the	move	function	a	direction	to	move	in.
You	are	going	to	address	these	two	requirements	next,	starting	with	separating
multipart	commands.	The	GameInput	class	will	provide	a	place	for	the	logic
that	delineates	command	and	argument.
Create	a	private	class	within	the	Game	object	to	provide	this	abstraction:
Listing	15.7		Defining	a	nested	class	(Game.kt)
## ...
object	Game	{
## ...
private	class	GameInput(arg:	String?)	{
private	val	input	=	arg	?:	""
val	command	=	input.split("	")[0]
val	argument	=	input.split("	").getOrElse(1,	{	""	})
## }
## }
Why	nest	GameInput	privately	within	Game?	The	GameInput	class	is	only
relevant	to	Game;	it	does	not	need	to	be	accessed	from	anywhere	else	in
NyetHack.	Making	GameInput	a	private,	nested	class	means	that	GameInput
can	be	used	within	Game	but	does	not	clutter	the	rest	of	your	API.
You	define	two	properties	on	the	GameInput	class:	one	for	the	command,	and
the	other	for	the	argument.	To	do	this,	you	call	split	to	break	the	input	apart	at
the	space	character,	then	getOrElse	to	attempt	to	fetch	the	second	item	in
split’s	resulting	list.	If	the	index	you	provide	to	getOrElse	does	not	exist,
getOrElse	returns	an	empty	string	as	a	default.
Now	you	can	separate	multipart	commands.	It	is	time	to	make	sure	the	user	has
entered	a	valid	command.

To	filter	user	input,	you	will	use	a	when	expression	to	build	a	whitelist	of	valid
commands	in	Game.	Any	good	whitelist	starts	by	locking	out	invalid	input.	Add
a	commandNotFound	function	to	GameInput	that	returns	a	String	to	be
printed	when	invalid	input	is	entered.
Listing	15.8		Defining	a	function	in	a	nested	class	(Game.kt)
## ...
object	Game	{
## ...
private	class	GameInput(arg:	String?)	{
private	val	input	=	arg	?:	""
val	command	=	input.split("	")[0]
val	argument	=	input.split("	").getOrElse(1,	{	""	})
private	fun	commandNotFound()	=	"I'm	not	quite	sure	what	you're	trying	to	do!"
## }
## }
Next,	add	another	function	to	GameInput	called	processCommand.
processCommand	should	return	the	result	of	a	when	expression	that	branches
off	of	the	command	entered	by	the	user.	Be	sure	to	sanitize	the	user’s	input	by
calling	toLowerCase	on	the	entered	command.
Listing	15.9		Defining	the	processCommand	function	(Game.kt)
## ...
object	Game	{
## ...
private	class	GameInput(arg:	String?)	{
private	val	input	=	arg	?:	""
val	command	=	input.split("	")[0]
val	argument	=	input.split("	").getOrElse(1,	{	""	})
fun	processCommand()	=	when	(command.toLowerCase())	{
else	->	commandNotFound()
## }
private	fun	commandNotFound()	=	"I'm	not	quite	sure	what	you're	trying	to	do!"
## }
## }
Now,	it	is	time	to	put	GameInput	to	work.	Replace	your	readLine	call	in
Game.play	with	a	version	that	uses	your	GameInput	class.
Listing	15.10		Using	GameInput	(Game.kt)
## ...
object	Game	{
## ...
fun	play()	{
while	(true)	{
println(currentRoom.description())
println(currentRoom.load())
//	Player	status
printPlayerStatus(player)
print(">	Enter	your	command:	")
println("Last	command:	${readLine()}")
println(GameInput(readLine()).processCommand())
## }
## }
## ...
## }

Run	Game.kt.	Now,	any	input	that	you	enter	triggers	the
commandNotFound	response:
Welcome,	adventurer.
A	glass	of	Fireball	springs	into	existence.	Delicious!	(x2)
## Room:	Town	Square
Danger	level:	2
The	villagers	rally	and	cheer	as	you	enter!
The	bell	tower	announces	your	arrival.	GWONG
(Aura:	GREEN)	(Blessed:	YES)
Madrigal	of	Tampa	is	in	excellent	condition!
>	Enter	your	command:	fight
I'm	not	quite	sure	what	you're	trying	to	do!
## Room:	Town	Square
Danger	level:	2
The	villagers	rally	and	cheer	as	you	enter!
The	bell	tower	announces	your	arrival.	GWONG
(Aura:	GREEN)	(Blessed:	YES)
Madrigal	of	Tampa	is	in	excellent	condition!
>	Enter	your	command:
This	is	progress:	You	have	restricted	input	to	only	the	commands	specified	on	a
small	(empty,	for	now)	whitelist.	Later	in	this	chapter,	you	will	add	the	“move”
command,	and	GameInput	will	become	a	bit	more	useful.
But	before	they	can	move	around	the	world	of	NyetHack,	your	hero	needs	a
world	that	consists	of	more	than	one	town	square.

## Data	Classes
Step	one	in	building	a	world	for	your	hero	is	to	establish	a	coordinate	system	to
move	around	on.	The	coordinate	system	will	use	cardinal	directions	to
communicate	direction	as	well	as	a	class	to	represent	change	in	direction,	called
## Coordinate.
Coordinate	is	a	simple	type	and	a	good	candidate	to	be	defined	as	a	data
class.	As	the	name	suggests,	data	classes	are	classes	designed	specifically	for
holding	data,	and	they	come	with	some	powerful	data	manipulation	benefits,	as
you	will	see	shortly.
Create	a	new	file	called	Navigation.kt	and	add	Coordinate	as	a	data
class,	using	the	data	keyword.	Coordinate	should	have	three	properties:
x,	an	Int	val	defined	in	the	primary	constructor	for	the	x	coordinate
y,	an	Int	val	defined	in	the	primary	constructor	for	the	y	coordinate
isInBounds,	a	Boolean	val	representing	whether	both	of	the
coordinate	values	are	positive
Listing	15.11		Defining	a	data	class	(Navigation.kt)
data	class	Coordinate(val	x:	Int,	val	y:	Int)	{
val	isInBounds	=	x	>=	0	&&	y	>=	0
## }
A	coordinate	should	never	have	an	x	or	y	position	less	than	0,	so	you	add	a
property	to	the	coordinate	class	that	will	return	whether	the	current	position	is
out	of	bounds.	You	will	later	check	the	isInBounds	property	of	the
Coordinate	when	attempting	to	update	the	currentRoom	to	determine
whether	the	Coordinate	is	a	valid	direction	to	move.	For	example,	if	a	player
at	the	top	of	the	game	map	tries	to	move	north,	your	isInBounds	check	will
block	this.
To	keep	track	of	where	the	player	is	on	the	world	map,	add	a	property	called
currentPosition	to	the	Player	class.
Listing	15.12		Tracking	player	position	(Player.kt)
class	Player(_name:	String,
var	healthPoints:	Int	=	100,
val	isBlessed:	Boolean,
private	val	isImmortal:	Boolean)	{
var	name	=	_name

get()	=	"${field.capitalize()}	of	$hometown"
private	set(value)	{
field	=	value.trim()
## }
val	hometown	by	lazy	{	selectHometown()	}
var	currentPosition	=	Coordinate(0,	0)
## ...
## }
Recall	from	Chapter	14	that	all	classes	in	Kotlin	inherit	from	the	same	class,
Any.	Defined	on	Any	are	a	series	of	functions	that	you	can	call	on	any	instance.
These	functions	include	toString,	equals,	and	hashCode,	which
improves	the	speed	a	value	can	be	retrieved	with	a	key	when	using	a	Map.
Any	provides	default	implementations	for	all	of	these	functions,	but,	as	you	have
seen	before,	they	are	often	not	very	reader	friendly.	Data	classes	provide
implementations	for	these	functions	that	may	work	better	for	your	project.	In	this
section,	we	will	walk	through	two	of	those	functions	and	some	of	the	other
benefits	of	using	data	classes	to	represent	data	in	your	codebase.
toString
The	default	toString	implementation	for	a	class	is	not	very	human	readable.
Take	Coordinate,	for	example.	If	Coordinate	were	defined	as	a	normal
class,	calling	toString	on	a	Coordinate	would	return	something	like	this:
## Coordinate@3527c201
You	are	looking	at	a	reference	to	where	this	Coordinate	was	allocated	space
in	memory.	If	you	are	wondering	why	you	would	care	about	the	details	of
Coordinate’s	memory	allocation,	that	is	a	sensible	question.	Most	often,	you
do	not	care.
You	can	override	toString	in	your	class	to	provide	your	own	implementation,
just	like	any	other	open	function.	But	data	classes	save	you	that	work	by
providing	their	own	default	implementation.	For	Coordinate,	that
implementation	looks	like	this:
Coordinate(x=1,	y=0)
Because	x	and	y	are	properties	declared	in	Coordinate’s	primary	constructor,
they	are	used	to	represent	Coordinate	in	textual	form.	(isInBounds	is	not
included	because	it	was	not	defined	in	Coordinate’s	primary	constructor.)	A
data	class’s	toString	implementation	is	considerably	more	useful	than	the
default	implementation	on	Any.

equals
The	next	function	that	data	classes	provide	an	implementation	for	is	equals.	If
Coordinate	were	defined	as	a	normal	class,	what	would	be	the	result	of	the
following	expression?
## Coordinate(1,	0)	==	Coordinate(1,	0)
You	may	be	surprised,	but	the	answer	is	false.	Why?
By	default,	objects	are	compared	by	their	references,	because	that	is	the	default
implementation	of	the	equals	function	in	Any.	Because	these	two	coordinates
are	separate	instances,	they	will	have	different	references	and	will	not	be	equal.
Perhaps	you	would	want	to	consider	two	players	to	be	equal	if	they	have	the
same	name.	You	can	provide	your	own	equality	check	by	overriding	equals	in
your	class	to	determine	equality	based	on	a	comparison	of	properties,	not
memory	references.	You	have	seen	that	classes	like	String	do	this	to	compare
equality	based	on	value.
Again,	data	classes	take	care	of	this	for	you	by	providing	an	implementation	of
equals	that	bases	equality	on	all	of	the	properties	declared	in	the	primary
constructor.	With	Coordinate	defined	as	a	data	class,	Coordinate(1,	0)	==
Coordinate(1,	0)	yields	a	result	of	true,	because	the	values	of	the	two
instances’	x	properties	are	equal,	as	are	the	values	of	their	y	properties.
copy
In	addition	to	giving	you	more	usable	default	implementations	of	functions	on
Any,	data	classes	also	provide	a	function	that	makes	it	easy	to	create	a	new	copy
of	an	object.
Say	that	you	want	to	create	a	new	instance	of	Player	that	has	all	of	the	same
property	values	as	another	player	except	for	isImmortal.	If	Player	were	a
data	class,	then	copying	a	Player	instance	would	be	as	simple	as	calling	copy
and	passing	arguments	for	any	properties	that	you	would	like	to	change.
val	mortalPlayer	=	player.copy(isImmortal	=	false)
Data	classes	save	you	the	work	of	implementing	this	copy	function	on	your
own.
Destructuring	declarations

Another	benefit	of	data	classes	is	that	they	automatically	enable	your	class’s	data
to	be	destructured.
The	examples	of	destructuring	you	have	seen	up	to	this	point	have	involved
things	like	the	list	output	from	split.	Under	the	hood,	destructuring
declarations	depend	on	the	declaration	of	functions	with	names	like
component1,	component2,	etc.,	each	declared	for	some	piece	of	data	that
you	would	like	to	return.	Data	classes	automatically	add	these	functions	for	you
for	each	property	defined	in	their	primary	constructor.
There	is	nothing	magic	about	a	class	supporting	destructuring;	a	data	class
simply	does	the	extra	work	required	to	make	the	class	“destructurable”	for	you.
You	can	make	any	class	support	destructuring	by	adding	component	operator
functions	to	it,	like	so:
class	PlayerScore(val	experience:	Int,	val	level:Int	){
operator	fun	component1()	=	experience
operator	fun	component2()	=	level
## }
val	(experience,	level)	=	PlayerScore(1250,	5)
By	declaring	Coordinate	to	be	a	data	class,	you	are	able	to	retrieve	the
properties	defined	in	Coordinate’s	primary	constructor	like	so:
val	(x,	y)	=	Coordinate(1,	0)
In	this	example,	x	has	a	value	of	1,	because	component1	returns	the	value	of
the	first	property	declared	in	Coordinate’s	primary	constructor.	y	has	a	value
of	0,	because	component2	returns	the	value	of	the	second	property	declared	in
Coordinate’s	primary	constructor.
These	features	all	weigh	in	favor	of	using	data	classes	to	represent	simple
objects	that	hold	data,	like	Coordinate.	Classes	that	are	often	compared	or
copied	or	have	their	contents	printed	out	are	especially	ripe	for	being	made	data
classes.
However,	there	are	also	some	limitations	and	requirements	on	data	classes.	Data
classes:
must	have	a	primary	constructor	with	at	least	one	parameter
require	their	primary	constructor	parameters	to	be	marked	either	val	or
var
cannot	be	abstract,	open,	sealed,	or	inner
If	your	class	does	not	require	the	toString,	copy,	equals,	or	hashCode
functions,	a	data	class	offers	no	benefits.	And	if	you	require	a	customized

equals	function	–	one	that	uses	only	certain	properties	rather	than	all
properties	for	the	comparison,	for	example	–	a	data	class	is	not	the	right	tool,
because	it	includes	all	properties	in	the	equals	function	it	automatically
generates.
You	will	learn	about	overriding	equals	and	other	functions	in	your	own	types
later	in	this	chapter,	in	the	section	called	Operator	Overloading,	and	about	a
shortcut	IntelliJ	provides	for	overriding	equals	in	the	section	called	For	the
## More	Curious:	Defining	Structural	Comparison.

## Enumerated	Classes
Enumerated	classes,	or	“enums,”	are	a	special	type	of	class	useful	for	defining	a
collection	of	constants,	known	as	enumerated	types.
In	NyetHack,	you	will	use	an	enum	to	represent	the	set	of	four	possible
directions	a	player	can	move	in	–	the	four	cardinal	directions.	Add	an	enum
called	Direction	to	Navigation.kt.
Listing	15.13		Defining	an	enum	(Navigation.kt)
enum	class	Direction	{
## NORTH,
## EAST,
## SOUTH,
## WEST
## }
data	class	Coordinate(val	x:	Int,	val	y:	Int)	{
val	isInBounds	=	x	>=	0	&&	y	>=	0
## }
Enums	are	more	descriptive	than	other	types	of	constants,	like	strings.	You	can
reference	enumerated	types	using	the	name	of	the	enum	class,	a	dot,	and	the
name	of	the	type,	like	so:
Direction.EAST
And	enums	can	represent	more	than	simple	naming	constants.	To	use
Direction	to	represent	character	movement	in	NyetHack,	you	will	tie	each
Direction	type	to	the	Coordinate	change	when	the	player	moves	in	that
direction.
Moving	in	the	game	world	should	modify	the	player’s	x	and	y	position	according
to	the	direction	moved.	For	example,	if	a	player	moves	to	the	east,	the	x	position
should	change	by	1	and	the	y	by	0.	If	the	player	moves	to	the	south,	the	x
position	should	change	by	0	and	the	y	by	1.
Add	a	primary	constructor	to	the	Direction	enum	that	defines	a
coordinate	property.	Because	you	add	a	parameter	to	the	constructor	of	the
enum,	you	will	have	to	call	that	constructor	when	defining	each	enumerated	type
in	Direction,	providing	a	Coordinate	for	each	one.
Listing	15.14		Defining	an	enum	constructor	(Navigation.kt)
enum	class	Direction(private	val	coordinate:	Coordinate)	{
NORTH(Coordinate(0,	-1)),
EAST(Coordinate(1,	0)),
SOUTH(Coordinate(0,	1)),
WEST(Coordinate(-1,	0))
## }

data	class	Coordinate(val	x:	Int,	val	y:	Int)	{
val	isInBounds	=	x	>=	0	&&	y	>=	0
## }
Enums,	like	other	classes,	can	also	hold	function	declarations.
Add	a	function	called	updateCoordinate	to	Direction	to	change	the
player’s	location	based	on	their	movement.	(Note	that	you	need	to	add	a
semicolon	to	separate	your	enumerated	type	declarations	from	your	function
declarations.)
Listing	15.15		Defining	a	function	in	an	enum	(Navigation.kt)
enum	class	Direction(private	val	coordinate:	Coordinate)	{
NORTH(Coordinate(0,	-1)),
EAST(Coordinate(1,	0)),
SOUTH(Coordinate(0,	1)),
WEST(Coordinate(-1,	0));
fun	updateCoordinate(playerCoordinate:	Coordinate)	=
Coordinate(playerCoordinate.x	+	coordinate.x,
playerCoordinate.y	+	coordinate.y)
## }
data	class	Coordinate(val	x:	Int,	val	y:	Int)	{
val	isInBounds	=	x	>=	0	&&	y	>=	0
## }
You	call	functions	on	enumerated	types,	not	on	the	enum	class	itself,	so	calling
updateCoordinate	will	look	something	like	this:
Direction.EAST.updateCoordinate(Coordinate(1,	0))

## Operator	Overloading
You	have	seen	that	Kotlin’s	built-in	types	come	with	a	range	of	available
operations	and	that	some	types	tailor	those	operations	based	on	the	data	they
represent.	Take	the	equals	function	and	its	associated	==	operator:	You	can	use
them	to	check	whether	two	instances	of	a	numeric	type	have	the	same	value,
whether	two	strings	hold	the	same	sequence	of	characters,	and	whether	instances
of	a	data	class	have	the	same	values	for	properties	in	the	primary	constructor.
Similarly,	the	plus	function	and	+	operator	add	two	numeric	values	together,
append	one	string	to	the	end	of	another,	and	add	the	elements	of	one	list	to
another.
When	you	create	your	own	types,	the	Kotlin	compiler	does	not	automatically
know	how	to	apply	the	built-in	operators	to	them.	What	does	it	mean	to	ask
whether	one	Player	is	equal	to	another,	for	example?	When	you	want	to	use
built-in	operators	with	your	custom	types,	you	have	to	override	the	operators’
functions	to	tell	the	compiler	how	to	implement	them	for	your	type.	This	is
known	as	operator	overloading.
You	saw	operator	overloading	used	extensively	in	Chapter	10	and	Chapter	11.
Rather	than	having	to	directly	call	a	function	called	get	to	retrieve	an	element
from	a	list,	you	were	able	to	use	the	index	access	operator,	[],	to	index	into	a
collection.	Kotlin’s	concise	syntax	is	built	on	small	improvements	like	this
(spellList[3]	instead	of	spellList.get(3)).
Coordinate	is	a	prime	candidate	for	improvement	via	operator	overloading.
You	move	your	hero	through	the	world	by	adding	the	properties	of	two
Coordinate	instances	together.	Instead	of	having	to	define	that	work	in
Direction,	you	can	overload	the	plus	operator	on	Coordinate.
Make	it	so	in	Navigation.kt,	prepending	the	function	declaration	with	the
operator	modifier.
Listing	15.16		Overloading	the	plus	operator	(Navigation.kt)
enum	class	Direction(private	val	coordinate:	Coordinate)	{
NORTH(Coordinate(0,	-1)),
EAST(Coordinate(1,	0)),
SOUTH(Coordinate(0,	1)),
WEST(Coordinate(-1,	0));
fun	updateCoordinate(playerCoordinate:	Coordinate)	=
Coordinate(playerCoordinate.x	+	coordinate.x,
playerCoordinate.y	+	coordinate.y)
## }

data	class	Coordinate(val	x:	Int,	val	y:	Int)	{
val	isInBounds	=	x	>=	0	&&	y	>=	0
operator	fun	plus(other:	Coordinate)	=	Coordinate(x	+	other.x,	y	+	other.y)
## }
Now,	you	can	simply	use	the	addition	operator	(+)	to	add	two	Coordinate
instances	together.	Do	so	now	in	Direction.
Listing	15.17		Using	an	overloaded	operator	(Navigation.kt)
enum	class	Direction(private	val	coordinate:	Coordinate)	{
NORTH(Coordinate(0,	-1)),
EAST(Coordinate(1,	0)),
SOUTH(Coordinate(0,	1)),
WEST(Coordinate(-1,	0));
fun	updateCoordinate(playerCoordinate:	Coordinate)	=
Coordinate(playerCoordinate.x	+	coordinate.x,
playerCoordinate.y	+	coordinate.y)
coordinate	+	playerCoordinate
## }
data	class	Coordinate(val	x:	Int,	val	y:	Int)	{
val	isInBounds	=	x	>=	0	&&	y	>=	0
operator	fun	plus(other:	Coordinate)	=	Coordinate(x	+	other.x,	y	+	other.y)
## }
Table	15.1	shows	some	commonly	used	operators	you	can	override:
Table	15.1		Common	operators
## Operator
## Function
name
## Purpose
## +
plus
Adds	an	object	to	another.
## +=
plusAssign
Adds	an	object	to	another	and	assigns	the	result	to
the	first.
## ==
equals
Returns	true	if	two	objects	are	equal,	false
otherwise.
## >
compareTo
Returns	true	if	the	object	on	the	lefthand	side	is
greater	than	the	object	on	the	righthand	side,	false
otherwise.
## []
get
Returns	the	element	in	a	collection	at	a	given	index.
## ..
rangeTo
Creates	a	range	object.
in
contains
Returns	true	if	an	object	exists	within	a	collection.
These	operators	can	be	overloaded	on	any	class,	but	make	sure	to	do	so	only
when	it	makes	sense.	While	you	can	assign	logic	to	the	addition	operator	on	the
Player	class,	what	does	“Player	plus	Player”	mean?	Ask	yourself	this	question
before	overloading	an	operator.

By	the	way,	if	you	override	equals	yourself,	you	should	also	override	a
function	called	hashCode.	An	example	of	overriding	both	of	these	functions
using	an	IntelliJ	command	as	a	shortcut	is	shown	in	the	section	called	For	the
More	Curious:	Defining	Structural	Comparison	near	the	end	of	this	chapter.
More	detailed	discussion	of	why	and	how	hashCode	should	be	overridden	is
outside	the	scope	of	this	book;	if	you	are	interested,	see	kotlinlang.org/
api/latest/jvm/stdlib/kotlin/-any/hash-code.html.

Exploring	the	World	of	NyetHack
Now	that	you	have	built	a	game	loop	and	established	a	cardinal	direction	system
on	a	coordinate	plane,	it	is	time	to	put	your	knowledge	to	the	test	and	add	more
rooms	to	explore	in	NyetHack.
To	set	up	a	map	of	the	world,	you	need	a	list	that	will	hold	all	of	the	rooms.	In
fact,	since	players	can	move	in	two	dimensions,	you	need	a	list	containing	two
lists	of	rooms.	The	first	list	of	rooms	will	hold	the	Town	Square	(where	the
player	begins),	Tavern,	and	Back	Room,	from	west	to	east.	The	second	list	of
rooms	will	hold	the	Long	Corridor	and	the	Generic	Room.	These	lists	will	be
held	in	a	third	list	representing	the	y	coordinate,	called	worldMap.
Add	a	worldMap	property	to	Game	with	a	series	of	rooms	for	your	hero	to
explore.
Listing	15.18		Defining	a	world	map	in	NyetHack	(Game.kt)
## ...
object	Game	{
private	val	player	=	Player("Madrigal")
private	var	currentRoom:	Room	=	TownSquare()
private	var	worldMap	=	listOf(
listOf(currentRoom,	Room("Tavern"),	Room("Back	Room")),
listOf(Room("Long	Corridor"),	Room("Generic	Room")))
## ...
## }
Figure	15.1	shows	the	grid	of	rooms	that	can	be	explored	in	NyetHack.
Figure	15.1		NyetHack	world	map
With	the	rooms	in	place,	it	is	time	to	add	the	“move”	command	and	give	the
player	the	ability	to	step	out	into	the	mysterious	land	of	NyetHack.	Add	a

function	called	move	that	takes	in	a	direction	input	as	a	String.	There	is	a	lot
going	in	move;	we	will	explain	it	after	you	enter	it.
Listing	15.19		Defining	the	move	function	(Game.kt)
## ...
object	Game	{
private	var	currentRoom:	Room	=	TownSquare()
private	val	player	=	Player("Madrigal")
private	var	worldMap	=	listOf(
listOf(currentRoom,	Room("Tavern"),	Room("Back	Room")),
listOf(Room("Long	Corridor"),	Room("Generic	Room")))
## ...
private	fun	move(directionInput:	String)	=
try	{
val	direction	=	Direction.valueOf(directionInput.toUpperCase())
val	newPosition	=	direction.updateCoordinate(player.currentPosition)
if	(!newPosition.isInBounds)	{
throw	IllegalStateException("$direction	is	out	of	bounds.")
## }
val	newRoom	=	worldMap[newPosition.y][newPosition.x]
player.currentPosition	=	newPosition
currentRoom	=	newRoom
"OK,	you	move	$direction	to	the	${newRoom.name}.\n${newRoom.load()}"
}	catch	(e:	Exception)	{
"Invalid	direction:	$directionInput."
## }
## }
move	returns	a	String	based	on	the	result	of	a	try/catch	expression.	In	the
try	block,	you	use	the	valueOf	function	to	match	the	user’s	input.	valueOf
is	a	function	available	on	all	enum	classes	that	returns	an	enumerated	type	with	a
name	that	matches	the	String	value	that	you	pass	to	it.	If	you	call
Direction.valueOf("EAST"),	then	Direction.EAST	will	be	returned.	If	you
pass	a	value	that	does	not	match	one	of	the	enumerated	types,	then	an
IllegalArgumentException	is	thrown.
That	exception	will	be	caught	by	the	catch	block.	(In	fact,	it	will	catch	any	type
of	exception	thrown	in	the	try	block.)
If	execution	continues	past	the	valueOf	call,	then	a	check	to	make	sure	that	the
player	is	still	in	bounds	is	made.	If	not,	then	an	IllegalStateException
is	thrown,	which	is	also	caught	by	the	catch	block.
If	the	player	moves	in	a	valid	direction,	then	your	next	step	is	to	query
worldMap	for	a	room	at	the	new	position.	You	saw	how	to	index	into	a
collection	in	Chapter	10,	and	here,	you	are	doing	so	twice.	The	first	indexing,
worldMap[newPosition.y],	returns	a	list	from	the	list	of	lists	called	worldMap.
The	second	indexing,	[newPosition.x],	returns	a	Room	inside	the	list	returned
in	the	first	indexing.	If	a	room	does	not	exist	for	the	coordinate	queried,	then	an
ArrayIndexOutOfBoundsException	is	thrown	and,	yes,	caught	by	the
catch	block.

If	all	that	code	executes	without	throwing	an	exception,	then	the	player’s
currentPosition	property	is	updated	and	you	return	some	text	to	print	out
as	a	part	of	NyetHack’s	text	interface.
The	move	function	should	be	called	when	the	player	enters	the	“move”
command,	which	you	will	now	implement	using	the	GameInput	class	you
wrote	earlier	in	this	chapter:
Listing	15.20		Defining	the	processCommand	function	(Game.kt)
## ...
object	Game	{
## ...
private	class	GameInput(arg:	String?)	{
private	val	input	=	arg	?:	""
val	command	=	input.split("	")[0]
val	argument	=	input.split("	").getOrElse(1,	{	""	})
fun	processCommand()	=	when	(command.toLowerCase())	{
"move"	->	move(argument)
else	->	commandNotFound()
## }
private	fun	commandNotFound()	=	"I'm	not	quite	sure	what	you're	trying	to	do!"
## }
## }
Try	running	Game.kt	and	moving	around	the	world.	You	should	see	some
output	like	the	following:
Welcome,	adventurer.
A	glass	of	Fireball	springs	into	existence.	Delicious!	(x2)
## Room:	Town	Square
Danger	level:	2
The	villagers	rally	and	cheer	as	you	enter!
The	bell	tower	announces	your	arrival.	GWONG
(Aura:	GREEN)	(Blessed:	YES)
Madrigal	of	Tampa	is	in	excellent	condition!
>	Enter	your	command:	move	east
OK,	you	move	EAST	to	the	Tavern.
Nothing	much	to	see	here...
## Room:	Tavern
Danger	level:	5
Nothing	much	to	see	here...
(Aura:	GREEN)	(Blessed:	YES)
Madrigal	of	Tampa	is	in	excellent	condition!
>	Enter	your	command:
And	that	is	it	–	you	are	now	able	to	walk	around	the	world	of	NyetHack.	In	this
chapter,	you	learned	how	to	use	several	variants	of	classes.	Beyond	the	class
keyword,	you	can	use	object	declarations,	data	classes,	and	enum	classes	to
represent	data.	Using	the	right	tool	for	the	job	will	make	the	relationships	among
objects	in	your	code	more	straightforward.
In	the	next	chapter,	you	will	learn	about	interfaces	and	abstract	classes	–
mechanisms	for	defining	protocols	that	your	classes	must	adhere	to	–	as	you	add
the	thrill	of	combat	to	NyetHack.

For	the	More	Curious:	Defining	Structural
## Comparison
Imagine	a	Weapon	class	that	has	name	and	type	properties:
open	class	Weapon(val	name:	String,	val	type:	String)
Suppose	you	would	like	two	individual	weapon	instances	to	be	considered
structurally	equal,	using	the	structural	equality	operator	(==),	if	the	values	of
their	names	and	types	are	structurally	equal.	By	default,	as	we	said	earlier	in	this
chapter,	==	checks	referential	equality	for	objects,	so	this	expression	would
evaluate	as	false:
open	class	Weapon(val	name:	String,	val	type:	String)
println(Weapon("ebony	kris",	"dagger")	==	Weapon("ebony	kris",	"dagger"))	//	False
You	saw	in	this	chapter	that	data	classes	provide	a	solution	to	this	problem	–	an
implementation	of	equals	that	bases	equality	on	all	of	the	properties	declared
in	the	primary	constructor.	But	Weapon	is	not	(and	cannot	be)	a	data	class,
because	it	is	intended	to	be	the	base	class	for	other	weapon	variations	(hence	the
open	keyword).	Data	classes	are	not	permitted	to	be	superclasses.
However,	as	we	discussed	in	the	section	called	Operator	Overloading,	you	can
provide	your	own	implementation	of	equals	and	hashCode	to	specify	how
instances	of	your	class	should	be	compared	structurally.
This	need	is	so	common	that	IntelliJ	has	a	Generate	task	for	adding	the	function
overrides	via	its	Code	→	Generate	command,	which	brings	up	the	Generate	dialog
(Figure	15.2):
Figure	15.2		The	Generate	dialog
When	generating	equals	and	hashCode	overrides,	you	can	select	the

properties	that	should	be	used	when	you	compare	two	instances	of	your	object
structurally	(Figure	15.3):
Figure	15.3		Generating	equals	and	hashCode	overrides
IntelliJ	adds	the	equals	and	hashCode	functions	to	the	class	based	on	the
choices	made:
open	class	Weapon(val	name:String,	val	type:	String)	{
override	fun	equals(other:	Any?):	Boolean	{
if	(this	===	other)	return	true
if	(javaClass	!=	other?.javaClass)	return	false
other	as	Weapon
if	(name	!=	other.name)	return	false
if	(type	!=	other.type)	return	false
return	true
## }
override	fun	hashCode():	Int	{
var	result	=	name.hashCode()
result	=	31	*	result	+	type.hashCode()
return	result
## }
## }
With	these	overrides	in	place,	comparing	two	weapons	would	result	in	true	as
long	as	their	names	and	types	are	the	same:
println(Weapon("ebony	kris",	"dagger")	==	Weapon("ebony	kris",	"dagger"))	//	True
Notice	that	the	overridden	equals	function	that	was	generated	sets	up	a
structural	comparison	between	the	properties	selected	in	the	Generate	command:
## ...
if	(name	!=	other.name)	return	false
if	(type	!=	other.type)	return	false
return	true
## ...
If	any	of	the	properties	are	not	structurally	equal,	then	the	comparison	results	in

false.	Otherwise,	true	is	returned.
As	we	mentioned	earlier,	whenever	you	define	structural	comparison,	you	also
provide	a	hashCode	definition.	hashCode	improves	performance	–	how
quickly	a	value	can	be	retrieved	with	a	key	when	using	a	Map	type,	for	example
–	and	is	tied	to	the	uniqueness	of	a	class	instance.

For	the	More	Curious:	Algebraic	Data	Types
Algebraic	data	types	(or	ADTs,	for	short)	allow	you	to	represent	a	closed	set	of
possible	subtypes	that	can	be	associated	with	a	given	type.	Enum	classes	are	a
simple	form	of	ADT.
Imagine	a	Student	class	that	has	three	possible	associated	states,	depending	on
the	student’s	enrollment	status:	NOT_ENROLLED,	ACTIVE,	or	GRADUATED.
Using	the	enum	class	that	you	learned	about	in	this	chapter,	you	could	model	the
three	states	for	the	Student	class	as	follows:
enum	class	StudentStatus	{
## NOT_ENROLLED,
## ACTIVE,
## GRADUATED
## }
class	Student(var	status:	StudentStatus)
fun	main(args:	Array<String>)	{
val	student	=	Student(StudentStatus.NOT_ENROLLED)
## }
And	you	could	write	a	function	that	generates	a	student	message	using	the
student’s	status:
fun	studentMessage(status:	StudentStatus):	String	{
return	when	(status)	{
StudentStatus.NOT_ENROLLED	->	"Please	choose	a	course."
## }
## }
One	of	the	benefits	of	enums	and	other	ADTs	is	that	the	compiler	can	check	to
ensure	that	you	handled	all	possibilities,	because	an	ADT	is	a	closed	set	of
possible	types.	The	implementation	for	studentMessage	does	not	handle	the
ACTIVE	or	GRADUATED	types,	so	the	compiler	would	give	an	error
(Figure	15.4):
Figure	15.4		Add	necessary	branches
The	compiler	is	satisfied	when	all	types	are	addressed	either	explicitly	or
through	an	else	branch:
fun	studentMessage(status:	StudentStatus):	String	{
return	when	(studentStatus)	{
StudentStatus.NOT_ENROLLED	->	"Please	choose	a	course."
StudentStatus.ACTIVE	->	"Welcome,	student!"

StudentStatus.GRADUATED	->	"Congratulations!"
## }
## }
For	more	complex	ADTs,	you	can	use	Kotlin’s	sealed	classes	to	implement	more
sophisticated	definitions.	Sealed	classes	let	you	specify	an	ADT	similar	to	an
enum,	but	with	more	control	over	the	specific	subtypes	than	an	enum	provides.
For	example,	imagine	that	when	a	student	is	active,	the	student	is	also	assigned	a
course	ID.	You	could	add	a	course	ID	property	to	the	enum	definition,	but	it
would	be	used	only	in	the	ACTIVE	case	–	leading	to	two	unneeded	null	states
for	the	property:
enum	class	StudentStatus	{
## NOT_ENROLLED,
## ACTIVE,
## GRADUATED;
var	courseId:	String?	=	null	//	Used	for	ACTIVE	only
## }
A	better	solution	would	be	to	use	a	sealed	class	to	model	the	student	statuses:
sealed	class	StudentStatus	{
object	NotEnrolled	:	StudentStatus()
class	Active(val	courseId:	String)	:	StudentStatus()
object	Graduated	:	StudentStatus()
## }
The	StudentStatus	sealed	class	has	a	limited	number	of	subclasses	that
must	be	defined	within	the	same	file	where	StudentStatus	is	defined	–
otherwise	it	is	ineligible	for	subclassing.	Defining	a	sealed	class	instead	of	an
enum	to	represent	the	possible	states	allows	you	to	specify	a	limited	set	of
StudentStatuses	that	the	compiler	can	check	in	a	when	(as	in	the	case	of	the
enum),	but	with	more	control	over	the	declaration	of	the	subclasses.
The	object	keyword	is	used	for	the	statuses	that	require	no	course	ID,	since
there	will	never	be	any	variation	on	their	instances,	and	the	class	keyword	is
used	for	the	ACTIVE	class	because	it	will	have	different	instances,	since	the
course	ID	will	change	depending	on	the	student.
Using	the	new	sealed	class	in	the	when	would	allow	you	to	now	read	the
courseId	from	the	ACTIVE	class,	accessible	through	smart	casting:
fun	main(args:	Array<String>)	{
val	student	=	Student(StudentStatus.Active("Kotlin101"))
studentMessage(student.status)
## }
fun	studentMessage(status:	StudentStatus):	String	{
return	when	(status)	{
is	StudentStatus.NotEnrolled	->	"Please	choose	a	course!"
is	StudentStatus.Active	->	"You	are	enrolled	in:	${status.courseId}"
is	StudentStatus.Graduated	->	"Congratulations!"
## }
## }

Challenge:	“Quit”	Command
Players	will	most	likely	want	to	quit	NyetHack	at	some	point,	and	currently
NyetHack	offers	no	way	to	do	that.	Your	challenge	is	to	fix	this.	When	a	user
enters	“quit”	or	“exit,”	NyetHack	should	display	a	farewell	message	to	the
adventurer	and	terminate.	Hint:	Remember	that,	currently,	your	while	loop
executes	forever	–	a	significant	part	of	solving	this	puzzle	is	to	end	that	loop
conditionally.

Challenge:	Implementing	a	World	Map
Remember	when	we	said	NyetHack	would	not	feature	awesome	ASCII
graphics?	Once	you	successfully	complete	this	challenge,	it	will!
Players	sometimes	get	lost	in	the	expansive	world	of	NyetHack,	and	fortunately
you	have	the	power	to	give	them	a	magic	map	of	the	realm.	Implement	a	“map”
command	that	displays	the	player’s	current	position	in	the	game	world.	For	a
player	currently	at	the	tavern,	the	game	interaction	should	resemble	the
following:
>	Enter	your	command:	map
## O	X	O
## O	O
The	X	represents	the	room	the	player	is	currently	in.

Challenge:	Ring	the	Bell
Add	a	“ring”	command	to	NyetHack	so	that	you	can	ring	the	bell	as	many	times
as	you	would	like	from	within	the	town	square.
Hint:	You	will	have	to	make	the	ringBell	function	public.