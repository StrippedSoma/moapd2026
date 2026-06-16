

## 13
## Initialization
In	the	last	chapter,	you	saw	how	to	define	classes	that	represent	real-world
objects.	In	NyetHack,	a	player	is	defined	in	part	by	its	properties	and	by	its
behavior.	For	all	the	complexity	that	can	be	represented	using	class	properties
and	functions,	you	have	seen	very	little	so	far	of	how	instances	of	classes	come
to	be.
Think	back	to	how	Player	was	defined	in	the	last	chapter.
class	Player	{
## ...
## }
Player’s	class	header	is	quite	simple,	and,	as	such,	instantiating	Player	was
also	simple.
fun	main(args:	Array<String>)	{
val	player	=	Player()
## ...
## }
Recall	that	when	you	call	a	class’s	constructor,	an	instance	of	that	class	is	created
–	a	process	known	as	instantiation.	This	chapter	covers	the	ways	classes	and
their	properties	can	be	initialized.	When	you	initialize	a	variable,	property,	or
class	instance,	you	assign	it	an	initial	value	to	make	it	ready	for	use.	You	will	see
more	constructors,	learn	about	property	initialization,	and	even	learn	how	to
bend	the	rules	with	late	and	lazy	initialization.
A	note	about	terminology:	Technically,	an	object	is	instantiated	when	memory
is	allocated	for	it,	and	it	is	initialized	when	it	is	assigned	a	value.	However,	in
practice	the	terms	are	often	used	slightly	differently.	Often,	initialization	is
used	to	mean	“everything	required	to	make	a	variable,	property,	or	class	instance
ready	to	use,”	while	instantiation	tends	to	be	limited	to	“creating	an	instance
of	a	class.”	In	this	book,	we	follow	this	more	typical	usage.

## Constructors
Player	now	contains	behavior	and	data	you	defined.	For	example,	you
specified	an	isImmortal	property:
val	isImmortal	=	false
You	used	a	val	because	once	the	player	is	created,	their	immortality	should
never	be	reassigned.	But	this	property	declaration	means	that,	at	the	moment,	no
player	can	be	immortal:	There	is	currently	no	way	to	initialize	isImmortal	to
any	value	other	than	false.
This	is	where	a	primary	constructor	comes	into	play.	A	constructor	allows	its
caller	to	specify	the	initial	values	that	an	instance	of	a	class	will	require	in	order
to	be	constructed.	Those	values	are	then	available	for	assignment	to	the
properties	defined	within	the	class.
Primary	constructors
Like	a	function,	a	constructor	defines	expected	parameters	that	must	be	provided
as	arguments.	To	specify	what	is	needed	for	a	Player	instance	to	work
correctly,	you	are	going	to	define	the	primary	constructor	in	Player’s	header.
Update	Player.kt	to	provide	initial	values	for	each	of	Player’s	properties
using	temporary	variables.
Listing	13.1		Defining	a	primary	constructor	(Player.kt)
class	Player(_name:	String,
_healthPoints:	Int,
_isBlessed:	Boolean,
_isImmortal:	Boolean)	{
var	name	=	"Madrigal"_name
get()	=	field.capitalize()
private	set(value)	{
field	=	value.trim()
## }
var	healthPoints	=	89_healthPoints
val	isBlessed	=	true_isBlessed
private	val	isImmortal	=	false_isImmortal
## ...
## }
(Why	prepend	these	variable	names	with	underscores?	Temporary	variables,
including	parameters	that	you	do	not	need	to	reference	more	than	once,	are	often
given	a	name	starting	with	an	underscore	to	signify	that	they	are	single-use.)
Now,	to	create	an	instance	of	Player,	you	provide	arguments	that	match	the

parameters	you	added	to	the	constructor.	Instead	of	hardcoding	the	value	for	the
player’s	name	property,	for	example,	you	pass	an	argument	to	Player’s
primary	constructor.	Change	the	call	to	Player’s	constructor	in	main	to	reflect
this.
Listing	13.2		Calling	the	primary	constructor	(Game.kt)
fun	main(args:	Array<String>)	{
val	player	=	Player("Madrigal",	89,	true,	false)
## ...
## }
Consider	how	much	functionality	the	primary	constructor	has	added	to	Player:
Before,	a	player	of	NyetHack	was	always	named	Madrigal,	was	never	immortal,
and	so	on.	Now,	a	player	can	be	named	anything,	and	the	door	to	immortality	is
open	–	none	of	Player’s	data	is	hardcoded.
Run	Game.kt	to	confirm	that	the	output	has	not	changed.
Defining	properties	in	a	primary	constructor
Notice	the	one-to-one	relationship	between	the	constructor	parameters	in
Player	and	the	class	properties:	You	have	a	parameter	and	a	class	property	for
each	property	to	be	specified	when	the	player	is	constructed.
For	properties	that	use	the	default	getter	and	setter,	Kotlin	allows	you	to	specify
both	in	one	definition,	rather	than	having	to	assign	them	using	temporary
variables.	name	uses	a	custom	getter	and	setter,	so	it	cannot	take	advantage	of
this	feature,	but	Player’s	other	properties	can.
Update	the	Player	class	to	define	healthPoints,	isBlessed,	and
isImmortal	as	properties	in	Player’s	primary	constructor.	(Do	not	neglect
to	delete	the	underscores	before	the	names	of	the	variables.)
Listing	13.3		Defining	properties	in	the	primary	constructor
(Player.kt)
class	Player(_name:	String,
var	_healthPoints:	Int,
val	_isBlessed:	Boolean,
private	val	_isImmortal:	Boolean)	{
var	name	=	_name
get()	=	field.capitalize()
private	set(value)	{
field	=	value.trim()
## }
var	healthPoints	=	_healthPoints
val	isBlessed	=	_isBlessed
private	val	isImmortal	=	_isImmortal
## ...
## }

For	each	constructor	parameter,	you	specify	whether	it	is	writable	or	read-only.
By	specifying	the	parameters	with	val	or	var	keywords	in	the	constructor,	you
define	properties	for	the	class,	whether	they	are	val	or	var	properties,	and	the
parameters	the	constructor	will	expect	arguments	for.	You	also	implicitly	assign
each	property	to	the	value	passed	to	it	as	an	argument.
Duplication	of	code	makes	it	harder	to	make	changes.	Generally,	we	prefer	this
way	of	defining	class	properties	because	it	leads	to	less	duplication.	You	were
not	able	to	use	this	syntax	for	name,	because	of	its	custom	getter	and	setter,	but
in	other	cases,	defining	a	property	in	a	primary	constructor	is	often	the	most
straightforward	choice.
Secondary	constructors
Constructors	come	in	two	flavors:	primary	and	secondary.	When	you	specify	a
primary	constructor,	you	say,	“These	parameters	are	required	for	any	instance	of
this	class.”	When	you	specify	a	secondary	constructor,	you	provide	alternative
ways	to	construct	the	class	(while	still	meeting	the	requirements	of	the	primary
constructor).
A	secondary	constructor	must	either	call	the	primary	constructor,	providing	it	all
of	the	arguments	it	requires,	or	call	through	to	another	secondary	constructor	–
which	follows	the	same	rule.	For	example,	say	you	know	that	in	most	cases	a
player	will	begin	with	100	health	points,	will	be	blessed,	and	will	be	mortal.	You
can	define	a	secondary	constructor	to	provide	that	configuration.	Add	a
secondary	constructor	to	Player:
Listing	13.4		Defining	a	secondary	constructor	(Player.kt)
class	Player(_name:	String,
var	healthPoints:	Int
val	isBlessed:	Boolean
private	val	isImmortal:	Boolean)	{
var	name	=	_name
get()	=	field.capitalize()
private	set(value)	{
field	=	value.trim()
## }
constructor(name:	String)	:	this(name,
healthPoints	=	100,
isBlessed	=	true,
isImmortal	=	false)
## ...
## }
You	can	define	multiple	secondary	constructors	for	different	combinations	of
parameters.	This	secondary	constructor	calls	through	to	the	primary	constructor
with	a	certain	set	of	parameters.	The	this	keyword	in	this	case	refers	to	the

instance	of	the	class	for	which	this	constructor	is	defined.	Specifically,	this	is
calling	into	another	constructor	defined	in	the	class	–	the	primary	constructor.
Because	this	secondary	constructor	provides	default	values	for
healthPoints,	isBlessed,	and	isImmortal,	you	do	not	need	to	pass
arguments	for	those	parameters	when	calling	it.	Call	Player’s	secondary
constructor	from	Game.kt	instead	of	its	primary	constructor.
Listing	13.5		Calling	a	secondary	constructor	(Game.kt)
fun	main(args:	Array<String>)	{
val	player	=	Player("Madrigal",	89,	true,	false)
## ...
## }
You	can	also	use	a	secondary	constructor	to	define	initialization	logic	–	code	that
will	run	when	your	class	is	instantiated.	For	example,	add	an	expression	that
reduces	the	player’s	health	points	value	to	40	if	their	name	is	Kar.
Listing	13.6		Adding	logic	to	a	secondary	constructor	(Player.kt)
class	Player(_name:	String,
var	healthPoints:	Int
val	isBlessed:	Boolean
private	val	isImmortal:	Boolean)	{
var	name	=	_name
get()	=	field.capitalize()
private	set(value)	{
field	=	value.trim()
## }
constructor(name:	String)	:	this(name,
healthPoints	=	100,
isBlessed	=	true,
isImmortal	=	false)	{
if	(name.toLowerCase()	==	"kar")	healthPoints	=	40
## }
## ...
## }
Although	they	are	useful	for	defining	alternative	logic	to	be	run	on	instantiation,
secondary	constructors	cannot	be	used	to	define	properties	like	primary
constructors	can.	Class	properties	must	be	defined	in	the	primary	constructor	or
at	the	class	level.
Run	Game.kt	to	see	that	Madrigal	is	still	blessed	and	has	health	points,
showing	that	Player’s	secondary	constructor	was	called	from	Game.kt.
Default	arguments
When	defining	a	constructor,	you	can	also	specify	default	values	that	should	be
assigned	if	an	argument	is	not	provided	for	a	specific	parameter.	You	have	seen
these	default	arguments	in	the	context	of	functions,	and	they	work	the	same	way
with	both	primary	and	secondary	constructors.	For	example,	set	the	default	value

for	healthPoints	with	a	default	parameter	value	of	100	in	the	primary
constructor,	as	follows:
Listing	13.7		Defining	a	default	argument	in	a	constructor
(Player.kt)
class	Player(_name:	String,
var	healthPoints:	Int	=	100
val	isBlessed:	Boolean
private	val	isImmortal:	Boolean)	{
var	name	=	_name
get()	=	field.capitalize()
private	set(value)	{
field	=	value.trim()
## }
constructor(name:	String)	:	this(name,
healthPoints	=	100,
isBlessed	=	true,
isImmortal	=	false)	{
if	(name.toLowerCase()	==	"kar")	healthPoints	=	40
## }
## ...
## }
Because	you	added	a	default	argument	value	to	the	healthPoints	parameter
in	the	primary	constructor,	you	removed	the	healthPoints	argument	passed
from	Player’s	secondary	constructor	to	its	primary	constructor.	This	gives	you
another	way	to	instantiate	Player:	with	or	without	an	argument	for
healthPoints.
//	Player	constructed	with	64	health	points	using	the	primary	constructor
Player("Madrigal",	64,	true,	false)
//	Player	constructed	with	100	health	points	using	the	primary	constructor
Player("Madrigal",	true,	false)
//	Player	constructed	with	100	health	points	using	the	secondary	constructor
Player("Madrigal")
Named	arguments
The	more	default	arguments	you	use,	the	more	options	you	have	for	calling	your
constructor.	More	options	can	open	the	door	for	more	ambiguity,	so	Kotlin
provides	named	constructor	arguments,	just	like	the	named	arguments	that	you
have	used	to	call	functions.
Compare	the	following	two	options	for	constructing	an	instance	of	Player:
val	player	=	Player(name	=	"Madrigal",
healthPoints	=	100,
isBlessed	=	true,
isImmortal	=	false)
val	player	=	Player("Madrigal",	100,	true,	false)
Which	option	do	you	find	to	be	more	readable?	If	you	chose	the	first,	we	agree
with	your	judgment.

Named	argument	syntax	lets	you	include	the	parameter	name	for	each	argument
to	improve	readability.	This	is	especially	useful	when	you	have	multiple
parameters	of	the	same	type:	If	you	see	“true”	and	“false”	both	passed	into	the
Player	constructor,	named	arguments	will	help	you	determine	which	value
corresponds	to	which	parameter.	This	reduced	ambiguity	leads	to	another
benefit:	Named	arguments	allow	you	to	specify	the	arguments	to	a	function	or
constructor	in	any	order.	If	parameters	are	unnamed,	then	you	need	to	refer	to	the
constructor	to	know	their	order.
You	may	have	noticed	that	the	secondary	constructor	you	wrote	for	Player
used	named	arguments,	similar	to	the	ones	that	you	saw	in	Chapter	4.
constructor(name:	String)	:	this(name,
healthPoints	=	100,
isBlessed	=	true,
isImmortal	=	false)
When	you	have	more	than	a	few	arguments	to	provide	to	a	constructor	or
function,	we	recommend	using	named	parameters.	They	make	it	easier	for
readers	to	keep	track	of	which	argument	is	being	passed	as	which	parameter.

## Initializer	Blocks
In	addition	to	the	primary	and	secondary	constructors,	you	can	also	specify	an
initializer	block	for	a	class	in	Kotlin.	The	initializer	block	is	a	way	to	set	up
variables	or	values	as	well	as	perform	validation	–	like	checking	to	make	sure
that	the	arguments	to	the	constructor	are	valid	ones.	The	code	it	holds	is
executed	when	the	class	is	constructed.
For	example,	players	have	certain	requirements	as	they	are	constructed:	A	player
must	begin	the	game	with	at	least	one	health	point.	Their	name	must	not	be
blank.
Use	an	initializer	block,	denoted	by	the	init	keyword,	to	enforce	these
requirements	with	preconditions.
Listing	13.8		Defining	an	initializer	block	(Player.kt)
class	Player(_name:	String,
var	healthPoints:	Int	=	100
val	isBlessed:	Boolean
private	val	isImmortal:	Boolean)	{
var	name	=	_name
get()	=	field.capitalize()
private	set(value)	{
field	=	value.trim()
## }
init	{
require(healthPoints	>	0,	{	"healthPoints	must	be	greater	than	zero."	})
require(name.isNotBlank(),	{	"Player	must	have	a	name."	})
## }
constructor(name:	String)	:	this(name,
isBlessed	=	true,
isImmortal	=	false)	{
if	(name.toLowerCase()	==	"kar")	healthPoints	=	40
## }
## ...
## }
If	either	of	these	preconditions	fails,	then	an	IllegalArgumentException
is	thrown.	(You	can	test	this	by	passing	Player	different	parameters	in	the
Kotlin	REPL.)
These	requirements	would	be	difficult	to	encapsulate	in	a	constructor	or	a
property	declaration.	The	code	in	the	initializer	block	will	be	called	when	the
class	is	instantiated	–	no	matter	which	constructor	for	the	class	is	called,	primary
or	secondary.

## Property	Initialization
So	far,	you	have	seen	a	property	initialized	in	two	ways	–	either	assigned	to	a
value	passed	as	an	argument,	or	defined	inline	in	a	primary	constructor.
A	property	can	(and	must)	be	initialized	with	any	value	of	its	type,	including
function	return	values.	Let’s	look	at	an	example.
Your	hero	can	come	from	one	of	any	number	of	exotic	locales	in	the	world	of
NyetHack.	Define	a	new	String	property	called	hometown	to	hold	the	name
of	a	player’s	town	of	origin.
Listing	13.9		Defining	the	hometown	property	(Player.kt)
class	Player(_name:	String,
var	healthPoints:	Int	=	100
val	isBlessed:	Boolean
private	val	isImmortal:	Boolean)	{
var	name	=	_name
get()	=	field.capitalize()
private	set(value)	{
field	=	value.trim()
## }
val	hometown:	String
init	{
require(healthPoints	>	0,	{	"healthPoints	must	be	greater	than	zero."	})
require(name.isNotBlank(),	{	"Player	must	have	a	name"	})
## }
## ...
## }
You	have	defined	hometown,	but	you	have	not	yet	satisfied	the	Kotlin
compiler.	Defining	the	name	and	type	of	a	property	is	not	enough	–	you	must
assign	an	initial	value	when	defining	a	property.	Why?	Kotlin’s	null	safety	rules.
Without	an	initial	value,	a	property	could	be	null,	which	would	be	illegal	if	the
property	is	of	a	non-nullable	type.
One	way	to	put	a	bandage	on	this	problem	would	be	to	initialize	hometown	as
an	empty	string:
val	hometown	=	""
This	compiles,	but	it	is	not	the	ideal	solution	because	""	is	not	a	town	in
NyetHack.	Instead,	add	a	new	function	called	selectHometown	that	returns	a
random	town	from	a	file	containing	towns.	You	will	use	this	function	to	assign
an	initial	value	to	hometown.
Listing	13.10		Defining	the	selectHometown	function	(Player.kt)
import	java.io.File
class	Player(_name:	String,

var	healthPoints:	Int	=	100
val	isBlessed:	Boolean
private	val	isImmortal:	Boolean)	{
var	name	=	_name
get()	=	field.capitalize()
private	set(value)	{
field	=	value.trim()
## }
val	hometown:	String	=	selectHometown()
## ...
private	fun	selectHometown()	=	File("data/towns.txt")
.readText()
## .split("\n")
## .shuffled()
## .first()
## }
(Notice	that	you	need	to	import	java.io.File	into	Player.kt	to	access
the	File	class.)
You	will	need	to	add	a	towns.txt	file	to	your	existing	data	directory	to	hold
this	list	of	towns.	You	can	find	the	file	at	bignerdranch.com/
solutions/towns.txt.
Test	out	your	hometown	property	by	using	it	in	the	name	property’s	getter.	To
differentiate	your	hero	from	all	of	the	other	Madrigals	in	the	world,	your	hero
should	be	addressed	by	a	name	that	includes	their	hometown.
Listing	13.11		Using	the	hometown	property	(Player.kt)
class	Player(_name:	String,
var	healthPoints:	Int	=	100
val	isBlessed:	Boolean
private	val	isImmortal:	Boolean)	{
var	name	=	_name
get()	=	"${field.capitalize()}	of	$hometown"
private	set(value)	{
field	=	value.trim()
## }
val	hometown	=	selectHometown()
## ...
private	fun	selectHometown()	=	File("data/towns.txt")
.readText()
## .split("\n")
## .shuffled()
## .first()
## }
Run	Game.kt.	Whenever	your	hero	is	addressed	by	name,	they	will	be
differentiated	via	their	hometown:
A	glass	of	Fireball	springs	into	existence.	Delicious!	(x2)
(Aura:	GREEN)	(Blessed:	YES)
Madrigal	of	Tampa	is	in	excellent	condition!
If	your	property	requires	complex	initialization	logic	–	multiple	expressions,	for
example	–	consider	pulling	this	initialization	logic	into	a	function	or	an
initializer	block.
The	rule	that	states	that	properties	must	be	assigned	on	declaration	does	not
apply	to	variables	in	a	smaller	scope,	like	a	function.	For	example:
class	JazzPlayer	{

fun	acquireMusicalInstrument()	{
val	instrumentName:	String
instrumentName	=	"Oboe"
## }
## }
Because	instrumentName	is	assigned	a	value	before	it	can	be	referenced,
this	code	compiles.
Properties	have	more	strict	rules	on	initialization	because	they	can	be	accessed
from	other	classes	if	they	are	public.	Variables	local	to	a	function,	on	the	other
hand,	are	scoped	to	the	function	in	which	they	are	defined	and	cannot	be
accessed	from	outside	of	it.

## Initialization	Order
You	have	seen	how	to	initialize	properties	or	add	logic	to	the	initialization	of
properties	in	various	ways	–	inline	in	a	primary	constructor,	initialized	at
declaration,	initialized	in	a	secondary	constructor,	or	initialized	in	an	initializer
block.	It	is	possible	for	the	same	property	to	be	referenced	in	multiple
initializers,	so	the	order	in	which	they	are	executed	is	important.
To	take	a	closer	look,	it	is	helpful	to	examine	the	resulting	field	initialization
order	and	method	invocations	in	the	decompiled	Java	bytecode.	Consider	the
following,	which	defines	a	Player	class	and	constructs	an	instance	of	it:
class	Player(_name:	String,	val	health:	Int)	{
val	race	=	"DWARF"
var	town	=	"Bavaria"
val	name	=	_name
val	alignment:	String
private	var	age	=	0
init	{
println("initializing	player")
alignment	=	"GOOD"
## }
constructor(_name:	String)	:	this(_name,	100)	{
town	=	"The	Shire"
## }
## }
fun	main(args:	Array<String>)	{
Player("Madrigal")
## }
Notice	that	this	Player	class	is	constructed	by	calling	Player("Madrigal"),
the	secondary	constructor.
Figure	13.1	shows	this	Player	class	on	the	left.	The	abbreviated	decompiled
Java	bytecode	on	the	right	shows	the	resulting	initialization	order:

Figure	13.1		Initialization	order	for	the	Player	class	(decompiled
bytecode)
The	resulting	initialization	order	is	as	follows:
- the	primary	constructor’s	inline	properties	(val	health:	Int)
- required	class-level	property	assignments	(val	race	=	"DWARF",	val
town	=	"Bavaria",	val	name	=	_name)
- init	block	property	assignments	and	function	calls	(alignment	=
"GOOD",	println	function)
- secondary	constructor	property	assignments	and	function	calls	(town	=
"The	Shire")
The	initialization	order	of	the	init	block	(item	3)	and	the	class-level	property
assignments	(item	2)	depends	on	the	order	they	are	specified	in.	If	the	init
block	were	defined	before	the	class	property	assignments,	it	would	be	initialized
second,	followed	by	the	class	property	assignments.
Note	that	one	property	is	not	assigned	in	the	constructor	–	age	–	even	though	it	is
assigned	at	the	class	property	level.	Because	its	value	is	0	(Java’s	primitive	int
default	value),	the	assignment	is	not	required	and	the	compiler	optimizes
initialization	by	skipping	it.

## Delaying	Initialization
Wherever	it	is	declared,	a	class	property	must	be	initialized	when	the	class
instance	is	constructed.	This	rule	is	an	important	part	of	Kotlin’s	null	safety
system,	because	it	means	that	all	non-nullable	properties	of	a	class	are	initialized
with	a	non-null	value	when	the	constructor	for	that	class	is	called.	When	you
instantiate	an	object,	you	can	immediately	reference	any	property	on	that	object,
from	within	or	outside	of	the	class.
Despite	its	importance,	you	can	bend	this	rule.	Why	would	you?	You	do	not
always	have	control	over	how	or	when	a	constructor	is	called.	One	such	case	is
in	the	Android	framework.
Late	initialization
On	Android,	a	class	called	Activity	represents	a	screen	in	your	application.
You	do	not	have	control	over	when	the	constructor	of	your	Activity	is	called.
Instead,	the	earliest	point	of	code	execution	you	have	is	in	a	function	called
onCreate.	If	you	cannot	initialize	your	properties	at	instantiation	time,	when
can	you?
This	is	where	late	initialization	becomes	important	–	and	more	than	just	a	simple
bending	of	Kotlin’s	rules	on	initialization.
Any	var	property	declaration	can	be	appended	with	the	lateinit	keyword,	and
the	Kotlin	compiler	will	let	you	put	off	initializing	the	property	until	you	assign
it.
class	Player	{
lateinit	var	alignment:	String
fun	determineFate()	{
alignment	=	"Good"
## }
fun	proclaimFate()	{
if	(::alignment.isInitialized)	println(alignment)
## }
## }
This	is	useful	but	must	be	regarded	with	care.	As	long	as	you	initialize	your	late-
initialized	variable	before	it	is	accessed,	then	there	is	no	problem.	But	if	you
reference	your	late-initialized	property	before	it	has	been	initialized,	then	you
will	be	greeted	with	an	unpleasant
UninitializedPropertyAccessException.

You	could	implement	this	pattern	using	a	nullable	type	instead,	but	you	would
then	be	required	to	handle	your	property’s	nullability	throughout	your	codebase,
which	is	burdensome.	Late-initialized	variables	function	just	like	other	variables
once	assigned.
The	lateinit	keyword	functions	as	a	contract	that	you	make	with	yourself:	“I
take	responsibility	for	initializing	this	variable	before	it	is	accessed.”	Kotlin	does
provide	a	way	to	check	whether	a	late-initialized	variable	has	been	initialized:
the	isInitialized	check	shown	in	the	example	above.	You	can	check
isInitialized	when	there	is	any	uncertainty	about	whether	the	lateinit
variable	is	initialized	to	avoid	an
UninitializedPropertyAccessException.
However,	isInitialized	should	be	used	sparingly	–	it	should	not	be	added
to	every	lateinit,	for	example.	If	you	are	using	isInitialized	a	lot,	it	is
likely	an	indicator	that	you	should	be	using	a	nullable	type	instead.
Lazy	initialization
Late	initialization	is	not	the	only	way	to	delay	initialization.	You	can	also	hold
off	on	initializing	a	variable	until	it	is	accessed	for	the	first	time.	This	concept	is
known	as	lazy	initialization,	and	despite	the	name,	it	can	actually	make	your
code	more	efficient.
Most	of	the	properties	that	you	have	initialized	in	this	chapter	have	been	pretty
lightweight	–	single	objects,	like	a	String.	Many	classes,	however,	are	more
complex.	They	may	require	the	instantiation	of	multiple	objects,	or	they	may
involve	some	more	computationally	intensive	task	when	being	initialized,	like
reading	from	a	file.	If	your	property	triggers	a	large	number	of	these	sorts	of
tasks,	or	if	your	class	does	not	require	access	to	a	property	right	away,	then	lazy
initialization	could	be	a	good	choice.
Lazy	initialization	is	implemented	in	Kotlin	using	a	mechanism	known	as	a
delegate.	Delegates	define	templates	for	how	a	property	is	initialized.
You	use	a	delegate	with	the	by	keyword.	The	Kotlin	standard	library	includes
some	delegates	that	are	already	implemented	for	you,	and	lazy	is	one	of	them.
Lazy	initialization	takes	a	lambda	in	which	you	define	any	code	that	you	wish	to
execute	when	your	property	is	initialized.
Player’s	hometown	property	reads	from	a	file	as	a	part	of	its	initialization.
You	might	not	access	hometown	right	away,	so	it	is	more	efficient	to	wait	to

initialize	until	hometown	is	needed.	Lazily	initialize	hometown	in	Player.
(Some	of	these	changes	are	tricky	to	see.	You	need	to	delete	the	=,	add	by	lazy,
and	add	curly	braces	around	selectHometown().)
Listing	13.12		Lazily	initializing	hometown	(Player.kt)
class	Player(_name:	String,
var	healthPoints:	Int	=	100
val	isBlessed:	Boolean
private	val	isImmortal:	Boolean)	{
var	name	=	_name
get()	=	"${field.capitalize()}	of	$hometown"
private	set(value)	{
field	=	value.trim()
## }
val	hometown	=by	lazy	{	selectHometown()	}
## ...
private	fun	selectHometown()	=	File("towns.txt")
.readText()
## .split("\n")
## .shuffled()
## .first()
## }
In	this	lambda,	the	result	of	selectHometown	is	implicitly	returned	and
assigned	to	hometown.
hometown	remains	uninitialized	until	it	is	referenced	for	the	first	time.	At	that
point,	all	of	the	code	in	lazy’s	lambda	is	executed.	Importantly,	this	code	is
only	executed	once	–	the	first	time	that	the	delegated	property	(hometown,
here)	is	accessed	in	name’s	getter.	Future	access	to	the	lazy	property	will	use	a
cached	result	instead	of	performing	the	expensive	computation	again.
Lazy	initialization	is	useful,	but	it	can	be	a	bit	verbose,	so	stick	to	using	lazy
initialization	for	more	computationally	needy	tasks.
And	with	that,	you	have	seen	what	there	is	to	see	when	it	comes	to	initializing	an
object	in	Kotlin.	Most	often,	your	experience	will	be	quite	straightforward:	You
call	a	constructor,	and	you	get	a	reference	to	an	instance	of	a	class	to	do	with
what	you	will.	That	said,	you	have	other	options	when	initializing	an	object	in
Kotlin,	and	understanding	those	options	can	help	you	write	clean,	efficient	code.
In	the	next	chapter	you	will	learn	about	inheritance,	an	object-oriented	principle
that	allows	you	to	share	data	and	behavior	between	related	classes.

For	the	More	Curious:	Initialization	Gotchas
You	saw	earlier	in	the	chapter	that	order	is	important	when	using	initializer
blocks	–	you	must	ensure	that	all	properties	used	in	the	block	are	initialized
before	the	initializer	block	is	defined.	Take	a	look	at	the	following	code	that
shows	this	initializer	block	ordering	problem:
class	Player()	{
init	{
val	healthBonus	=	health.times(3)
## }
val	health	=	100
## }
fun	main(args:	Array<String>)	{
## Player()
## }
This	code	would	not	compile,	because	the	health	property	is	not	initialized	at
the	point	that	it	is	used	by	the	init	block.	As	we	mentioned	earlier,	when	a
property	is	used	within	an	init	block,	the	property	initialization	must	happen
before	it	is	accessed.	When	health	is	defined	before	the	initializer	block,	the
code	compiles:
class	Player()	{
val	health	=	100
init	{
val	healthBonus	=	health.times(3)
## }
## }
fun	main(args:	Array<String>)	{
## Player()
## }
There	are	a	couple	of	similar,	but	more	subtle,	scenarios	that	trip	up	unwary
programmers.	For	example,	in	the	following	code,	a	name	property	is	declared,
then	a	firstLetter	function	reads	the	first	character	from	the	property:
class	Player()	{
val	name:	String
private	fun	firstLetter()	=	name[0]
init	{
println(firstLetter())
name	=	"Madrigal"
## }
## }
fun	main(args:	Array<String>)	{
## Player()
## }
This	code	will	compile,	because	the	compiler	sees	that	the	name	property	is
initialized	in	the	init	block,	a	legal	place	to	assign	an	initial	value.	But	running
this	code	would	result	in	a	null	pointer	exception,	because	the	firstLetter

function	(which	uses	the	name	property)	is	called	before	the	name	property	is
assigned	an	initial	value	in	the	init	block.
The	compiler	does	not	inspect	the	order	properties	are	initialized	in	compared	to
the	functions	that	use	them	within	the	init	block.	When	defining	an	init	block
that	calls	functions	that	access	properties,	it	is	up	to	you	to	ensure	that	you	have
initialized	those	properties	before	calling	the	functions.	When	name	is	assigned
before	firstLetter	is	called,	the	code	compiles	and	will	run	without	error:
class	Player()	{
val	name:	String
private	fun	firstLetter()	=	name[0]
init	{
name	=	"Madrigal"
println(firstLetter())
## }
## }
fun	main(args:	Array<String>)	{
## Player()
## }
One	more	tricky	scenario	is	shown	in	the	following	code,	in	which	two
properties	are	initialized:
class	Player(_name:	String)	{
val	playerName:	String	=	initPlayerName()
val	name:	String	=	_name
private	fun	initPlayerName()	=	name
## }
fun	main(args:	Array<String>)	{
println(Player("Madrigal").playerName)
## }
Again,	this	code	compiles,	since	the	compiler	sees	that	all	properties	have	been
initialized.	But	running	the	code	would	result	in	the	unsatisfying	output	null.
What	is	the	problem	here?	When	playerName	is	initialized	with	the
initPlayerName	function,	the	compiler	assumes	that	name	is	initialized,	but
when	initPlayerName	is	called,	name	is	actually	not	yet	initialized.
In	this	case,	once	again,	order	matters.	The	initialization	order	of	the	two
properties	must	be	reversed.	With	that	done,	the	Player	class	compiles	and
returns	a	non-null	name	value:
class	Player(_name:	String)	{
val	name:	String	=	_name
val	playerName:	String	=	initPlayerName()
private	fun	initPlayerName()	=	name
## }
fun	main(args:	Array<String>)	{
println(Player("Madrigal").playerName)
## }

Challenge:	The	Riddle	of	Excalibur
As	you	learned	in	Chapter	12,	you	can	specify	your	own	getter	and	setter	for	a
property.	Now	that	you	have	seen	how	properties	and	their	classes	are	initialized,
we	have	a	riddle	for	you.
Every	great	sword	has	a	name.	Define	a	class	called	Sword	in	the	Kotlin	REPL
that	reflects	this	truth.
Listing	13.13		Defining	Sword	(REPL)
class	Sword(_name:	String)	{
var	name	=	_name
get()	=	"The	Legendary	$field"
set(value)	{
field	=	value.toLowerCase().reversed().capitalize()
## }
## }
What	is	printed	when	you	instantiate	a	Sword	and	reference	name?	(Try	to
answer	for	yourself	before	you	check	the	REPL.)
Listing	13.14		Referencing	name	(REPL)
val	sword	=	Sword("Excalibur")
println(sword.name)
What	is	printed	when	you	reassign	name?
Listing	13.15		Reassigning	name	(REPL)
sword.name	=	"Gleipnir"
println(sword.name)
Finally,	add	an	initializer	block	to	Sword	that	assigns	name.
Listing	13.16		Adding	an	initializer	block	(REPL)
class	Sword(_name:	String)	{
var	name	=	_name
get()	=	"The	Legendary	$field"
set(value)	{
field	=	value.toLowerCase().reversed().capitalize()
## }
init	{
name	=	_name
## }
## }
What	is	printed	when	you	instantiate	Sword	and	reference	name	now?
Listing	13.17		Referencing	name	again	(REPL)
val	sword	=	Sword("Excalibur")
println(sword.name)

This	challenge	will	test	your	knowledge	of	both	initializers	and	custom	property
getters	and	setters.