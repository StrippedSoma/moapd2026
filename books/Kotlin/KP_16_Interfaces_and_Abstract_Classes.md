

## 16
Interfaces	and	Abstract	Classes
In	this	chapter	you	will	see	how	to	define	and	use	interfaces	and	abstract	classes
in	Kotlin.
An	interface	allows	you	to	specify	common	properties	and	behavior	that	are
supported	by	a	subset	of	classes	in	your	program	–	without	being	required	to
specify	how	they	will	be	implemented.	This	capability	–	the	what	without	the
how	–	is	useful	when	inheritance	is	not	the	right	relationship	for	classes	in	a
program.	Using	an	interface,	a	group	of	classes	can	have	properties	or	functions
in	common	without	sharing	a	superclass	or	subclassing	one	another.
You	will	also	work	with	a	type	of	class	called	an	abstract	class,	a	hybrid	between
the	features	of	interfaces	and	classes.	Abstract	classes	are	similar	to	interfaces	in
that	they	can	specify	the	what	without	the	how,	but	they	are	different	in	that	they
can	also	define	constructors	and	act	as	a	superclass.
These	new	concepts	will	allow	you	to	add	an	exciting	feature	to	NyetHack:	Now
that	your	hero	can	walk	around,	you	will	add	a	combat	system	to	deal	with	the
evildoers	your	hero	encounters.

Defining	an	Interface
To	define	how	combat	is	performed,	you	will	first	create	an	interface	that
specifies	the	functions	and	properties	used	for	entities	in	the	game	when
performing	combat.	Your	player	will	face	goblins,	but	you	will	define	a	combat
system	that	can	be	applied	to	any	type	of	creature	–	not	just	goblins.
Create	a	new	file	called	Creature.kt	in	the	com.bignerdranch.nyethack
package	(remember	that	this	pattern	is	to	avoid	naming	collisions),	and	define	a
Fightable	interface,	using	the	keyword	interface:
Listing	16.1		Defining	an	interface	(Creature.kt)
interface	Fightable	{
var	healthPoints:	Int
val	diceCount:	Int
val	diceSides:	Int
val	damageRoll:	Int
fun	attack(opponent:	Fightable):	Int
## }
Your	interface	declaration	defines	things	that	are	common	to	any	entity	that	can
fight	in	NyetHack.	Fightable	creatures	use	the	number	of	dice,	the	number	of
sides	on	each	die,	and	the	damage	roll	–	the	sum	of	the	numbers	rolled	on	the
dice	–	to	determine	the	amount	of	damage	dealt	to	an	enemy.	Fightable	creatures
must	also	have	healthPoints	and	an	implementation	for	a	function	called
attack.
The	four	properties	in	Fightable	have	no	initializers,	and	the	attack	function
has	no	function	body.	An	interface	is	not	concerned	with	providing	initializers	or
function	bodies.	Remember	–	interfaces	only	specify	the	what,	not	the	how.
Note	that	the	Fightable	interface	is	also	the	type	of	the	opponent	parameter
that	the	attack	function	accepts.	An	interface	can	be	used	as	a	type	for	a
parameter,	just	as	a	class	can	be	used	as	a	parameter	type.
When	a	function	specifies	a	parameter	type,	that	function	cares	about	what	the
argument	can	do,	not	how	the	behavior	is	implemented.	This	is	one	of	the
strengths	of	an	interface	–	you	can	create	a	set	of	requirements	that	is	shared
between	classes	that	otherwise	have	nothing	in	common.

Implementing	an	Interface
To	use	an	interface,	we	say	that	you	“implement”	it	on	a	class.	There	are	two
parts	to	this:	First,	you	declare	that	the	class	implements	the	interface.	Then,	you
must	ensure	that	the	class	provides	implementations	for	all	of	the	properties	and
functions	specified	in	the	interface.
Use	the	:	operator	to	implement	the	Fightable	interface	on	Player.
Listing	16.2		Implementing	an	interface	(Player.kt)
class	Player(_name:	String,
override	var	healthPoints:	Int	=	100,
var	isBlessed:	Boolean	=	false,
private	var	isImmortal:	Boolean)	:	Fightable	{
## ...
## }
When	you	add	the	Fightable	interface	to	Player,	IntelliJ	indicates	that
functions	and	properties	are	missing.	Warning	you	that	properties	and	functions
have	yet	to	be	implemented	on	Player	helps	you	adhere	to	Fightable’s	rules,
and	IntelliJ	will	also	help	you	implement	everything	that	is	required	by	the
interface.
Right-click	on	Player	and	select	Generate...	→	Implement	Methods...,	then	select
diceCount,	diceSides,	and	attack	in	the	Implement	Members	dialog
(Figure	16.1).	(You	will	deal	with	damageRoll	in	the	next	section.)

Figure	16.1		Implementing	Fightable	members
You	should	see	the	following	code	added	to	the	Player	class:
class	Player(_name:	String,
override	var	healthPoints:	Int	=	100,
var	isBlessed:	Boolean	=	false,
private	var	isImmortal:	Boolean)	:	Fightable	{
override	val	diceCount:	Int
get()	=	TODO("not	implemented")
//To	change	initializer	of	created	properties	use
//File	|	Settings	|	File	Templates.
override	val	diceSides:	Int
get()	=	TODO("not	implemented")
//To	change	initializer	of	created	properties	use
//File	|	Settings	|	File	Templates.

override	fun	attack(opponent:	Fightable):	Int	{
TODO("not	implemented")
//To	change	body	of	created	functions	use
//File	|	Settings	|	File	Templates.
## }
## ...
## }
The	function	implementations	added	to	Player	are	just	stubs.	You	will	provide
more	realistic	implementations	for	them	next.	(By	the	way,	you	might	recall	the
TODO	function	from	the	discussion	of	the	Nothing	type	in	Chapter	4.	Here	it	is
in	action	–	or,	perhaps,	in	anticipation.)	Once	you	implement	these	properties
and	functions,	Player	will	satisfy	the	Fightable	interface	and	can	be	used	in
combat.
Notice	that	the	property	and	function	implementations	all	use	the	override
keyword.	This	may	surprise	you	–	after	all,	you	are	not	replacing	an
implementation	for	these	properties	in	Fightable.	However,	all	implementations
of	interface	properties	and	functions	must	be	marked	with	override.
On	the	other	hand,	the	open	keyword	is	not	required	on	function	declarations	in
an	interface.	This	is	because	all	properties	and	functions	you	add	to	an	interface
must	be	open	implicitly,	since	they	would	serve	no	purpose	otherwise.	After	all,
an	interface	outlines	the	what,	and	the	how	must	be	provided	in	the	classes	that
implement	it.
Replace	the	TODO	calls	in	diceCount,	diceSides,	and	attack	with
appropriate	values	and	functionality.
Listing	16.3		Stubbing	out	an	interface	implementation	(Player.kt)
class	Player(_name:	String,
override	var	healthPoints:	Int	=	100,
var	isBlessed:	Boolean	=	false,
private	var	isImmortal:	Boolean)	:	Fightable	{
override	val	diceCount:	Int	=	3
get()	=	TODO("not	implemented")
//To	change	initializer	of	created	properties	use
//File	|	Settings	|	File	Templates.
override	val	diceSides:	Int	=	6
get()	=	TODO("not	implemented")
//To	change	initializer	of	created	properties	use
//File	|	Settings	|	File	Templates.
override	fun	attack(opponent:	Fightable):	Int	{
TODO("not	implemented")
//To	change	body	of	created	functions	use
//File	|	Settings	|	File	Templates.
val	damageDealt	=	if	(isBlessed)	{
damageRoll	*	2
}	else	{
damageRoll
## }
opponent.healthPoints	-=	damageDealt
return	damageDealt
## }
## ...
## }

diceCount	and	diceSides	are	implemented	with	integers.	Player’s
attack	function	takes	the	result	from	damageRoll	(which	is	not	yet	fleshed
out)	and	doubles	it	if	the	player	is	blessed.	It	then	takes	that	value	and	subtracts
it	from	opponent’s	healthPoints	property	–	which	opponent	is
guaranteed	to	have,	no	matter	what	its	class	is,	because	it	implements
Fightable.	That	is	the	beauty	of	an	interface.

## Default	Implementations
We	have	said	several	times	now	that	interfaces	focus	on	the	what	and	not	the
how.	You	can,	however,	provide	a	default	implementation	for	property	getters
and	functions	in	an	interface.	Classes	that	implement	the	interface	then	have	the
option	of	using	the	default	or	defining	their	own	implementation.
Provide	a	default	getter	for	damageRoll	in	Fightable.	This	getter	should
return	the	sum	of	all	the	dice	rolls	to	determine	how	much	damage	is	dealt	in	a
round	of	combat.
Listing	16.4		Defining	a	default	getter	implementation
(Creature.kt)
interface	Fightable	{
var	healthPoints:	Int
val	diceCount:	Int
val	diceSides:	Int
val	damageRoll:	Int
get()	=	(0	until	diceCount).map	{
Random().nextInt(diceSides	+	1)
## }.sum()
fun	attack(opponent:	Fightable):	Int
## }
Now	that	damageRoll	has	a	default	getter,	any	class	that	implements	the
Fightable	interface	can	opt	out	of	providing	a	value	for	the	damageRoll
property	–	in	which	case	the	property’s	value	will	be	assigned	based	on	the
default	implementation.
Not	every	property	or	function	needs	a	unique	implementation	in	every	class,	so
providing	a	default	implementation	is	a	good	way	to	reduce	duplication	in	your
code.

## Abstract	Classes
Abstract	classes	provide	another	way	to	enforce	structure	in	your	classes.	An
abstract	class	is	never	instantiated.	Its	purpose	is	to	provide	function
implementations	through	inheritance	to	subclasses	that	are	instantiated.
An	abstract	class	is	defined	by	prepending	the	abstract	keyword	to	a	class
definition.	In	addition	to	function	implementations,	abstract	classes	can	include
abstract	functions	–	function	declarations	without	implementations.
It	is	time	to	give	the	player	something	to	fight	in	NyetHack.	Add	an	abstract
class	called	Monster	to	Creature.kt.	Monster	implements	the
Fightable	interface,	and	therefore	needs	a	healthPoints	property	and	an
attack	function.	(What	about	the	other	Fightable	properties?	We	will	return
to	those	in	a	moment.)
Listing	16.5		Defining	an	abstract	class	(Creature.kt)
interface	Fightable	{
var	healthPoints:	Int
val	diceCount:	Int
val	diceSides:	Int
val	damageRoll:	Int
get()	=	(0	until	diceCount).map	{
Random().nextInt(diceSides	+	1)
## }.sum()
fun	attack(opponent:	Fightable):	Int
## }
abstract	class	Monster(val	name:	String,
val	description:	String,
override	var	healthPoints:	Int)	:	Fightable	{
override	fun	attack(opponent:	Fightable):	Int	{
val	damageDealt	=	damageRoll
opponent.healthPoints	-=	damageDealt
return	damageDealt
## }
## }
You	define	Monster	as	an	abstract	class	because	it	is	meant	as	a	foundation	for
more	specific	creatures	in	the	game.	You	will	never	create	an	instance	of
Monster	–	and	could	not	if	you	tried.	Instead,	you	will	create	instances	of
Monster	subclasses:	more	specific	monsters,	like	goblins,	wraiths,	and
dragons,	that	are	concrete	versions	of	an	abstract	monster.
Defining	Monster	as	an	abstract	class	provides	a	template	for	what	it	means	to
be	a	monster	in	NyetHack:	A	monster	must	have	a	name	and	a	description,	and	it
must	satisfy	the	criteria	of	the	Fightable	interface.
Now,	create	the	first	concrete	version	of	the	Monster	abstract	class	–	the

Goblin	subclass	–	in	Creature.kt.
Listing	16.6		Subclassing	an	abstract	class	(Creature.kt)
interface	Fightable	{
## ...
## }
abstract	class	Monster(val	name:	String,
val	description:	String,
override	var	healthPoints:	Int)	:	Fightable	{
override	fun	attack(opponent:	Fightable):	Int	{
val	damageDealt	=	damageRoll
opponent.healthPoints	-=	damageDealt
return	damageDealt
## }
## }
class	Goblin(name:	String	=	"Goblin",
description:	String	=	"A	nasty-looking	goblin",
healthPoints:	Int	=	30)	:	Monster(name,	description,	healthPoints)	{
## }
Because	Goblin	is	a	subclass	of	Monster,	it	has	all	of	the	properties	and
functions	that	Monster	does.
If	you	attempted	to	compile	your	code	at	this	point,	compilation	would	fail.	This
is	because	both	diceCount	and	diceSides	are	specified	as	requirements	of
the	Fightable	interface,	but	they	are	not	implemented	in	Monster	(and	have
no	default	implementation).
Monster	does	not	have	to	include	all	the	requirements	of	the	Fightable
interface,	even	though	it	implements	it,	because	it	is	an	abstract	class	and	will
never	be	instantiated.	Its	subclasses,	however,	must	implement	all	requirements
of	Fightable,	either	through	inheritance	from	Monster	or	on	their	own.
Satisfy	the	requirements	defined	on	the	Fightable	interface	by	adding	them	to
## Goblin:
Listing	16.7		Implementing	properties	in	the	subclass	of	an	abstract
class	(Creature.kt)
interface	Fightable	{
## ...
## }
abstract	class	Monster(val	name:	String,
val	description:	String,
override	var	healthPoints:	Int)	:	Fightable	{
## ...
## }
class	Goblin(name:	String	=	"Goblin",
description:	String	=	"A	nasty-looking	goblin",
healthPoints:	Int	=	30)	:	Monster(name,	description,	healthPoints)	{
override	val	diceCount	=	2
override	val	diceSides	=	8
## }
A	subclass	shares	all	functionality	with	its	superclass,	by	default.	This	is	true	no

matter	what	kind	of	class	the	superclass	is.	If	a	class	implements	an	interface,
then	its	subclass	must	also	satisfy	the	requirements	of	the	interface.
You	may	have	noticed	the	similarity	between	abstract	classes	and	interfaces:
Both	can	define	functions	and	properties	that	do	not	require	an	implementation.
What,	then,	is	the	difference	between	the	two?
For	one	thing,	an	interface	cannot	specify	a	constructor.	For	another,	a	class	can
extend	(or	subclass)	only	one	abstract	class,	but	it	can	implement	many
interfaces.	A	good	rule	of	thumb	is	this:	When	you	need	a	category	of	behavior
or	properties	that	objects	have	in	common	that	does	not	fit	using	inheritance,	use
an	interface.	If,	on	the	other	hand,	inheritance	makes	sense	–	but	you	do	not	want
a	concrete	parent	class	–	then	an	abstract	class	may	make	sense.	(And	if	you
want	to	be	able	to	construct	your	parent	class,	then	a	regular	class	is	still	your
best	bet.)

Combat	in	NyetHack
Adding	combat	to	NyetHack	will	put	to	use	all	that	you	have	learned	about
object-oriented	programming.
Each	room	in	NyetHack	will	contain	a	monster	for	your	hero	to	vanquish	in	the
most	graphic	way	that	you	know	how:	by	nullifying	it.
Add	a	monster	property	of	nullable	type	Monster?	to	the	Room	class,	and
initialize	it	by	assigning	it	a	Goblin.	Update	Room’s	description	to	let	the
player	know	whether	the	room	has	a	monster	to	fight.
Listing	16.8		Adding	a	monster	to	each	room	(Room.kt)
open	class	Room(val	name:	String)	{
protected	open	val	dangerLevel	=	5
var	monster:	Monster?	=	Goblin()
fun	description()	=	"Room:	$name\n"	+
"Danger	level:	$dangerLevel\n"	+
"Creature:	${monster?.description	?:	"none."}"
open	fun	load()	=	"Nothing	much	to	see	here..."
## }
If	a	Room’s	monster	is	null,	then	it	has	been	bested.	Otherwise,	your	hero	still
has	a	foe	to	defeat.
You	initialized	monster,	a	property	of	type	Monster?,	with	an	object	of	type
Goblin.	A	room	can	host	any	subclass	of	Monster,	and	Goblin	is	a	subclass
of	Monster	–	this	is	polymorphism	at	work.	If	you	were	to	create	another	class
that	subclasses	Monster,	then	it	could	also	be	used	in	a	room	in	NyetHack.
Now,	it	is	time	to	add	a	“fight”	command	to	use	Room’s	new	monster
property.	Add	a	private	function	called	fight	to	Game.
Listing	16.9		Defining	the	fight	function	(Game.kt)
## ...
object	Game	{
## ...
private	fun	move(directionInput:	String)	=	...
private	fun	fight()	=	currentRoom.monster?.let	{
while	(player.healthPoints	>	0	&&	it.healthPoints	>	0)	{
## Thread.sleep(1000)
## }
"Combat	complete."
}	?:	"There's	nothing	here	to	fight."
private	class	GameInput(arg:	String?)	{
## ...
## }
## }

fight	first	checks	to	see	whether	the	current	room’s	monster	is	null.	If	it	is,
then	there	is	nothing	to	fight,	and	a	corresponding	message	is	returned.	If	there	is
a	monster	to	fight,	then	–	as	long	as	the	player	and	the	monster	still	have	at	least
1	health	point	–	a	round	of	combat	is	performed.
That	round	of	combat	is	represented	by	the	next	private	function	you	will	add,
called	slay.	slay	calls	the	attack	function	on	the	monster	and	on	the	player.
The	same	attack	function	can	be	called	on	both	Monster	and	Player,
because	they	both	implement	the	Fightable	interface.
Listing	16.10		Defining	the	slay	function	(Game.kt)
## ...
object	Game	{
## ...
private	fun	fight()	=	...
private	fun	slay(monster:	Monster)	{
println("${monster.name}	did	${monster.attack(player)}	damage!")
println("${player.name}	did	${player.attack(monster)}	damage!")
if	(player.healthPoints	<=	0)	{
println(">>>>	You	have	been	defeated!	Thanks	for	playing.	<<<<")
exitProcess(0)
## }
if	(monster.healthPoints	<=	0)	{
println(">>>>	${monster.name}	has	been	defeated!	<<<<")
currentRoom.monster	=	null
## }
## }
private	class	GameInput(arg:	String?)	{
## ...
## }
## }
As	specified	by	the	condition	of	the	while	loop	in	fight,	combat	rounds	repeat
until	either	the	player	or	the	monster	runs	out	of	health	points.
If	the	player’s	healthPoints	value	reaches	0,	then	the	game	ends,	which	you
achieve	with	a	call	to	exitProcess.	exitProcess	is	a	Kotlin	standard
library	function	that	terminates	the	running	instance	of	the	JVM.	To	access	this
function,	you	will	have	to	import	kotlin.system.exitProcess.
If	the	monster’s	healthPoints	value	reaches	0,	then	the	monster	is	nullified
in	dramatic	fashion.
Call	slay	from	fight.
Listing	16.11		Calling	the	slay	function	(Game.kt)
## ...
object	Game	{
## ...
private	fun	move(directionInput:	String)	=	...
private	fun	fight()	=	currentRoom.monster?.let	{
while	(player.healthPoints	>	0	&&	it.healthPoints	>	0)	{
slay(it)

## Thread.sleep(1000)
## }
"Combat	complete."
}	?:	"There's	nothing	here	to	fight."
private	fun	slay(monster:	Monster)	{
## ...
## }
private	class	GameInput(arg:	String?)	{
## ...
## }
## }
After	a	round	of	combat,	Thread.sleep	is	called	for	1	second.
Thread.sleep	is	a	heavy-handed	function	that	pauses	execution	for	a	given
length	of	time,	in	this	case	1,000	milliseconds	(or	1	second).	We	do	not
recommend	using	Thread.sleep	throughout	a	production	codebase,	but	in
this	case,	it	is	a	handy	way	to	create	time	between	combat	rounds	in	NyetHack.
Once	the	condition	of	the	while	loop	is	no	longer	satisfied,	"Combat
complete."	is	returned	to	be	printed	to	the	console.
Test	your	new	combat	system	by	adding	a	“fight”	command	to	GameInput	that
calls	the	fight	function.
Listing	16.12		Adding	the	fight	command	(Game.kt)
## ...
object	Game	{
## ...
private	class	GameInput(arg:	String?)	{
private	val	input	=	arg	?:	""
val	command	=	input.split("	")[0]
val	argument	=	input.split("	").getOrElse(1,	{	""	})
fun	processCommand()	=	when	(command.toLowerCase())	{
"fight"	->	fight()
"move"	->	move(argument)
else	->	commandNotFound()
## }
private	fun	commandNotFound()	=	"I'm	not	quite	sure	what	you're	trying	to	do!"
## }
## }
Run	Game.kt.	Try	moving	from	screen	to	screen	and	using	the	“fight”
command	in	different	rooms.	The	randomness	that	you	introduced	in	the
damageRoll	property	on	the	Fightable	interface	means	that	you	will	have	a
different	experience	each	time	that	you	walk	into	a	new	room	and	pick	a	fight.
Welcome,	adventurer.
A	glass	of	Fireball	springs	into	existence.	Delicious!	(x2)
## Room:	Town	Square
Danger	level:	2
Creature:	A	nasty-looking	goblin
(Aura:	GREEN)	(Blessed:	YES)
Madrigal	of	Tampa	is	in	excellent	condition!
>	Enter	your	command:	fight
Goblin	did	11	damage!
Madrigal	of	Tampa	did	14	damage!
Goblin	did	8	damage!
Madrigal	of	Tampa	did	14	damage!
Goblin	did	7	damage!

Madrigal	of	Tampa	did	10	damage!
>>>>	Goblin	has	been	defeated!	<<<<
Combat	complete.
## Room:	Town	Square
Danger	level:	2
Creature:	none.
(Aura:	GREEN)	(Blessed:	YES)
Madrigal	of	Tampa	looks	pretty	hurt.
>	Enter	your	command:
In	this	chapter,	you	leveraged	interfaces	to	define	what	a	creature	needs	to
engage	in	combat,	and	you	used	abstract	classes	to	create	a	base	class	for	all
monsters	in	the	world	of	NyetHack.	These	tools	will	help	you	create
relationships	that	focus	on	what	a	class	can	do	rather	than	how	it	does	it.
Many	of	the	object-oriented	concepts	that	you	have	learned	about	in	the	past
several	chapters	return	to	this	common	goal:	Leverage	the	tools	of	the	Kotlin
framework	to	create	scalable	codebases	that	only	expose	what	they	need	to	and
encapsulate	the	rest.
In	the	next	chapter,	you	will	learn	about	generics,	a	feature	that	allows	you	to
specify	classes	that	work	with	many	types.