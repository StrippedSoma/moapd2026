

## 12
## Defining	Classes
The	object-oriented	programming	paradigm	has	been	around	since	the	1960s	and
continues	to	be	popular	because	it	provides	a	set	of	useful	tools	for	simplifying
the	structure	of	a	program.	Central	to	the	object-oriented	style	are	classes,
definitions	of	the	unique	categories	of	“things”	your	code	represents.	Classes
define	what	sort	of	data	those	things	will	consist	of	and	what	kind	of	work	they
can	do.
To	make	NyetHack	object-oriented,	you	will	start	by	identifying	the	unique
types	of	things	that	will	exist	in	the	world	and	defining	classes	for	them.	In	this
chapter,	you	will	add	a	custom	Player	class	to	NyetHack,	which	you	will	use
to	represent	a	NyetHack	player’s	particular	characteristics.

Defining	a	Class
A	class	can	be	defined	in	its	own	file	or	alongside	other	elements,	like	functions
or	variables.	Defining	a	class	in	its	own	file	gives	it	room	to	grow	as	the	program
scales	up	over	time,	and	that	is	what	you	will	do	in	NyetHack.	Create	a	new
Player.kt	file	and	declare	your	first	class	with	the	class	keyword:
Listing	12.1		Defining	the	Player	class	(Player.kt)
class	Player
A	class	is	often	declared	in	a	file	matching	its	name,	but	it	does	not	have	to	be.
You	can	define	multiple	classes	in	the	same	file	–	and	you	may	want	to	if	you
have	multiple	classes	used	for	a	similar	purpose.
With	that,	your	class	is	defined.	Now	all	you	have	to	do	is	give	it	some	work	to
do.

## Constructing	Instances
A	class	declaration	is	like	a	blueprint.	Blueprints	contain	the	details	for	how	to
construct	a	building,	but	they	are	not	a	building.	Your	Player	class	declaration
works	similarly:	So	far,	a	player	has	not	been	constructed	–	you	have	only
created	the	(so	far,	quite	sparse)	blueprint.
When	you	start	a	new	game	of	NyetHack,	the	main	function	is	called,	and	one
of	the	first	things	that	you	will	want	to	do	is	create	a	player	character	to	play	the
game.	To	construct	a	player	so	that	it	can	be	used	in	NyetHack,	you	must
instantiate	it	–	create	an	instance	of	it	–	by	calling	its	constructor.	In	Game.kt,
where	variables	are	declared	in	the	main	function,	instantiate	a	Player,	as
shown:
Listing	12.2		Instantiating	a	Player	(Game.kt)
fun	main(args:	Array<String>)	{
val	name	=	"Madrigal"
var	healthPoints	=	89
val	isBlessed	=	true
val	isImmortal	=	false
val	player	=	Player()
## //	Aura
val	auraColor	=	auraColor(isBlessed,	healthPoints,	isImmortal)
//	Player	status
val	healthStatus	=	formatHealthStatus(healthPoints,	isBlessed)
printPlayerStatus(auraColor,	isBlessed,	name,	healthStatus)
castFireball()
## }
## ...
You	called	Player’s	primary	constructor	by	suffixing	the	Player	class	name
with	parentheses.	This	constructs	an	instance	of	the	Player	class.	The	player
variable	is	now	said	to	“contain	an	instance	of	the	Player	class.”
A	constructor	does	what	its	name	says:	It	constructs.	Specifically,	it	constructs	an
instance	and	prepares	it	for	use.	The	syntax	for	calling	a	constructor	is	a	lot	like
calling	a	function:	It	uses	parentheses	to	capture	arguments	for	its	parameters.
You	will	see	other	ways	instances	can	be	constructed	in	Chapter	13.
Now	that	you	have	an	instance	of	Player,	what	can	you	do	with	it?

## Class	Functions
Class	definitions	can	specify	two	types	of	content:	behavior	and	data.	In
NyetHack,	a	player	can	take	various	actions:	perform	combat,	move,	cast	the
fireball	spell,	or	check	their	inventory,	for	example.	You	define	behavior	for	a
class	by	adding	function	definitions	to	its	class	body.	Functions	defined	within	a
class	are	called	class	functions.
You	already	have	some	player	behaviors	that	are	defined	in	Game.kt.	Now,
you	are	going	to	reorganize	your	code	to	bring	class-specific	elements	into	the
class	definition.
Begin	by	adding	the	castFireball	function	to	Player:
Listing	12.3		Defining	a	class	function	(Player.kt)
class	Player	{
fun	castFireball(numFireballs:	Int	=	2)	=
println("A	glass	of	Fireball	springs	into	existence.	(x$numFireballs)")
## }
(You	might	notice	that	this	implementation	of	castFireball	does	not	have	a
private	keyword.	We	will	explain	that	in	a	moment.)
Here,	you	define	a	class	body	for	Player	with	curly	braces.	The	class	body
holds	definitions	for	the	class’s	behavior	and	data,	much	like	the	actions	of	a
function	are	defined	within	the	function	body.
In	Game.kt,	remove	the	definition	of	castFireball	and	add	a	call	to	it	as	a
class	function	in	main:
Listing	12.4		Calling	a	class	function	(Game.kt)
fun	main(args:	Array<String>)	{
var	healthPoints	=	89
val	isBlessed	=	true
val	isImmortal	=	false
val	player	=	Player()
player.castFireball()
## //	Aura
val	auraColor	=	auraColor(isBlessed,	healthPoints,	isImmortal)
//	Player	status
val	healthStatus	=	formatHealthStatus(healthPoints,	isBlessed)
printPlayerStatus(auraColor,	isBlessed,	player.name,	healthStatus)
castFireball()
## }
## ...
private	fun	castFireball(numFireballs:	Int	=	2)	=
println("A	glass	of	Fireball	springs	into	existence.	(x$numFireballs)")
Grouping	the	logic	about	the	“things”	in	your	code	using	classes	keeps	your

code	organized	at	scale.	As	NyetHack	grows,	you	will	add	more	classes,	each
with	its	own	responsibilities.
Run	Game.kt	and	confirm	that	the	player	summons	a	glass	of	Fireball.
Why	move	castFireball	to	Player?	In	NyetHack,	summoning	a	glass	of
Fireball	is	something	that	a	player	does:	It	cannot	happen	without	an	instance	of
Player,	and	it	is	performed	by	the	particular	player	on	which
castFireball	is	called.	Defining	castFireball	as	a	class	function,	so
that	it	is	called	on	an	instance	of	the	class,	reflects	this	logic.	Later	in	this
chapter,	you	will	move	other	functions	associated	with	NyetHack’s	player	into
the	Player	class	as	well.

Visibility	and	Encapsulation
Adding	behavior	to	a	class	with	class	functions	(and	data	with	class	properties,
as	you	will	see	in	a	moment)	builds	a	description	of	what	that	class	can	do	and
be,	and	that	description	is	visible	to	anyone	with	an	instance	of	that	class.
By	default,	any	function	or	property	without	a	visibility	modifier	is	public	–
meaning	it	is	accessible	from	any	file	or	function	in	your	program.	Since	you
now	include	no	visibility	modifier	on	castFireball,	it	can	be	called	from
everywhere.
In	some	cases,	like	with	castFireball,	you	want	other	parts	of	your	code	to
be	able	to	access	your	class	properties	or	call	your	class	functions.	But	you
might	have	other	class	functions	or	properties	you	do	not	want	to	be	called	from
elsewhere	in	your	codebase.
As	the	number	of	classes	in	your	program	grows,	so	does	your	codebase’s
complexity.	Hiding	the	implementation	details	that	do	not	need	to	be	visible
from	other	parts	of	your	codebase	helps	to	ensure	that	the	logic	of	your	code	is
clear	and	concise.	That	is	where	visibility	comes	into	play.
While	a	public	class	function	can	be	invoked	anywhere	in	the	program,	a	private
class	function	cannot	be	invoked	outside	of	the	class	on	which	it	is	defined.	This
idea	of	restricting	visibility	to	certain	class	functions	or	properties	drives	a
concept	in	object-oriented	programming	known	as	encapsulation.	Encapsulation
says	that	a	class	should	selectively	expose	functions	and	properties	to	define	how
other	objects	interact	with	it.	Anything	that	is	not	essential	to	expose,	including
implementation	details	of	exposed	functions	and	properties,	should	be	kept
private.
For	example,	if	castFireball	is	called	from	Game.kt,	Game.kt	does	not
care	about	how	castFireball	is	implemented.	It	only	cares	that	a	glass	of
Fireball	is	summoned.	So	while	the	function	itself	may	be	exposed,	the	details	of
its	implementation	should	not	matter	to	the	caller.
In	fact,	it	could	be	dangerous	if	code	in	Game.kt	could	alter	values	that
castFireball	depends	on	to	do	its	work	–	like	the	number	of	glasses	of
Fireball	to	create,	or	the	Fireball	intensity	level.
In	short:	When	building	classes,	expose	only	what	you	need	to.
Table	12.1	lists	the	available	visibility	modifiers:

Table	12.1		Visibility	modifiers
ModifierDescription
public
## (default)
The	function	or	property	will	be	accessible	by	code	outside	of	the
class.	By	default,	functions	and	properties	without	a	visibility
modifier	are	public.
private
The	function	or	property	will	be	accessible	only	within	the	same
class.
protected
The	function	or	property	will	be	accessible	only	within	the	same
class	or	its	subclass.
internal
The	function	or	property	will	be	accessible	within	the	same
module.
We	will	discuss	the	protected	keyword	in	Chapter	14.
If	you	are	familiar	with	Java,	notice	that	the	package	private	visibility	level	is
not	included	in	Kotlin.	We	will	explain	why	in	the	section	called	For	the	More
Curious:	Package	Private	at	the	end	of	this	chapter.

## Class	Properties
Class	function	definitions	describe	the	behavior	associated	with	a	class.	Data
definitions,	better	known	as	class	properties,	are	the	attributes	required	to
represent	the	specific	state	or	characteristics	of	a	class.	For	example,	Player’s
class	properties	could	represent	a	player’s	name,	current	health	points,	race,
alignment,	gender,	and	other	attributes.
Currently,	you	define	a	name	for	a	player	in	the	main	function,	but	your	new
class	definition	is	a	better	place	for	it.	Update	Player.kt	with	a	name
property.	(The	value	for	name	may	look	sloppy,	but	there	is	a	method	to	our
madness	–	enter	it	as	shown.)
Listing	12.5		Defining	the	name	property	(Player.kt)
class	Player	{
val	name	=	"madrigal"
fun	castFireball(numFireballs:	Int	=	2)	=
println("A	glass	of	Fireball	springs	into	existence.	(x$numFireballs)")
## }
You	add	the	name	property	to	the	Player	class	body,	including	it	as	relevant
data	a	Player	instance	contains.	Notice	that	name	is	defined	as	a	val.	Like
variables,	properties	can	represent	either	read-only	or	mutable	data	using	the	val
and	var	keywords,	respectively.	We	will	talk	more	about	property	mutability
later	in	this	chapter.
Now,	remove	the	name	declaration	from	Game.kt:
Listing	12.6		Removing	name	from	main	(Game.kt)
fun	main(args:	Array<String>)	{
val	name	=	"Madrigal"
var	healthPoints	=	89
## ...
## }
## ...
You	might	notice	that	IntelliJ	is	now	warning	you	about	a	problem	in	Game.kt
(Figure	12.1).

Figure	12.1		Unresolved	reference	error
Now	that	name	is	a	property	of	Player,	you	will	need	to	update
printPlayerStatus	to	access	it	from	the	instance	of	the	Player	class.
Use	dot	syntax	to	pass	the	player	variable’s	name	property	to
printPlayerStatus:
Listing	12.7		Resolving	the	reference	to	Player’s	name	property
(Game.kt)
fun	main(args:	Array<String>)	{
## ...
//	Player	status
printPlayerStatus(auraColor,	isBlessed,	player.name,	healthStatus)
## }
## ...
Run	Game.kt.	The	player	status,	including	the	name,	prints	as	before,	but	now
you	access	the	name	property	from	the	instance	of	the	Player	class	rather	than
from	a	local	variable	in	main.
When	an	instance	of	a	class	is	constructed,	all	of	its	properties	must	have	values.
This	means	that,	unlike	other	variables,	class	properties	must	be	assigned	an
initial	value.	For	example,	the	following	code	is	invalid,	because	name	is	not
assigned	at	declaration:
class	Player	{
var	name:	String
## }
We	will	explore	the	nuances	of	class	and	property	initialization	in	Chapter	13.
Later	in	this	chapter,	you	will	refactor	NyetHack	to	move	the	other	data

belonging	to	the	Player	class	into	the	class	definition.
Property	getters	and	setters
Properties	model	the	characteristics	of	each	instance	of	a	class.	They	also
provide	a	way	for	other	entities	to	interface	with	the	data	that	the	class	keeps
track	of,	represented	in	a	compact	and	concise	syntax.	This	interaction	happens
through	getters	and	setters.
For	each	property	you	define,	Kotlin	will	generate	a	field,	a	getter,	and,	if
needed,	a	setter.	A	field	is	where	the	data	for	a	property	is	stored.	You	cannot
directly	define	a	field	on	a	class.	Kotlin	encapsulates	the	fields	for	you,
protecting	the	data	in	the	field	and	exposing	it	via	getters	and	setters.	A
property’s	getter	specifies	how	the	property	is	read.	Getters	are	generated	for
every	property.	A	setter	defines	how	a	property’s	value	is	assigned,	so	it	is
generated	only	when	a	property	is	writable	–	in	other	words,	when	the	property
is	a	var.
Imagine	that	you	are	in	a	restaurant	where	the	menu	advertises	spaghetti,	among
other	foods.	You	order	spaghetti,	and	the	waiter	serves	you	spaghetti	dressed	up
with	spaghetti	sauce	and	cheese.	You	do	not	have	access	to	the	kitchen,	and	the
waiter	handles	everything	behind	the	scenes	for	you,	even	adding	spaghetti	sauce
and	cheese	to	your	order	of	spaghetti.	You	are	like	the	caller,	and	the	waiter	is
the	getter.
As	a	patron	of	this	restaurant,	you	do	not	want	the	responsibility	of	boiling	water
when	you	order	spaghetti.	Rather,	you	simply	want	to	order	spaghetti	and	have	it
brought	to	you.	And	the	restaurant	does	not	want	you	in	the	kitchen,	nosing
around	in	the	ingredients	and	putting	together	dishes	in	your	own	way.	This	is
encapsulation	at	work.
Although	default	getters	and	setters	are	provided	automatically	by	Kotlin,	you
can	define	your	own	custom	getters	and	setters	when	you	want	to	specify	how
the	data	will	be	read	or	written.	This	is	called	overriding	the	getter	or	setter.
To	see	how	getter	overriding	works,	add	a	getter	to	name	that	ensures	that	its
value	is	capitalized	when	it	is	accessed.
Listing	12.8		Defining	a	custom	getter	(Player.kt)
class	Player	{
val	name	=	"madrigal"
get()	=	field.capitalize()
fun	castFireball(numFireballs:	Int	=	2)	=

println("A	glass	of	Fireball	springs	into	existence.	(x$numFireballs)")
## }
When	you	define	a	custom	getter	for	a	property,	you	change	how	the	property
works	when	it	is	accessed.	Because	name	contains	a	proper	noun,	you	always
want	it	to	be	capitalized	when	you	reference	it.	This	custom	getter	makes	sure	of
that.
Run	Game.kt	and	confirm	that	Madrigal	now	prints	with	capital	“M.”
The	field	keyword	here	points	to	the	backing	field	that	Kotlin	manages	for	your
property	automatically.	The	backing	field	is	the	data	that	the	getters	and	setters
use	to	read	and	write	the	data	that	represents	the	property.	It	is	like	the
ingredients	in	the	restaurant	kitchen	–	the	caller	never	sees	the	backing	field
directly,	only	the	data	as	presented	by	the	getter.	In	fact,	a	field	is	only	accessible
within	a	getter	or	a	setter.
When	the	capitalized	version	of	name	is	returned,	the	backing	field	is	not
modified.	If	the	value	assigned	to	name	is	not	capitalized,	as	in	your	code,	it
remains	lowercase	after	the	getter	does	its	work.
A	setter,	on	the	other	hand,	does	modify	the	backing	field	of	the	property	on
which	it	is	declared.	Add	a	setter	to	name	that	uses	the	trim	function	to
remove	any	leading	and	trailing	spaces	from	the	value	it	is	passed.
Listing	12.9		Defining	a	custom	setter	(Player.kt)
class	Player	{
val	name	=	"madrigal"
get()	=	field.capitalize()
set(value)	{
field	=	value.trim()
## }
fun	castFireball(numFireballs:	Int	=	2)	=
println("A	glass	of	Fireball	springs	into	existence.	(x$numFireballs)")
## }
There	is	a	problem	with	adding	a	setter	to	this	property,	which	IntelliJ	is	warning
you	about	(Figure	12.2):

Figure	12.2		val	properties	are	read-only
Because	you	defined	the	name	property	as	a	val,	it	is	read-only	and	cannot	be
modified,	even	with	a	setter.	This	protects	your	vals	from	being	modified
without	your	consent.
IntelliJ’s	complaint	underscores	an	important	point	about	setters:	They	are
triggered	when	the	value	of	a	property	is	set.	It	is	not	logical	(and,	in	fact,	it	is	an
error)	to	define	a	setter	for	a	val	property,	because	if	the	value	is	read-only,	the
setter	can	never	do	its	job.
You	want	to	be	able	to	change	the	player’s	name,	so	change	the	name	property
from	a	val	to	a	var.	(Note	that	from	this	point	forward,	we	will	show	all	changes
to	the	code	inline	when	possible.)
Listing	12.10		Making	name	mutable	(Player.kt)
class	Player	{
valvar	name	=	"madrigal"
get()	=	field.capitalize()
set(value)	{
field	=	value.trim()
## }
fun	castFireball(numFireballs:	Int	=	2)	=
println("A	glass	of	Fireball	springs	into	existence.	(x$numFireballs)")
## }
Now,	name	can	be	modified	according	to	the	rules	outlined	in	its	custom	setter,
and	IntelliJ’s	warnings	disappear	accordingly.
Property	getters	are	called	using	the	same	access	syntax	as	the	other	variables
that	you	have	seen.	Property	setters	are	called	using	the	assignment	operator	that
you	have	used	to	assign	values	to	variables.	In	the	Kotlin	REPL,	try	changing	a
player’s	name	from	outside	of	the	Player	class.
Listing	12.11		Changing	a	player’s	name	(REPL)
val	player	=	Player()
player.name	=	"estragon	"
print(player.name	+	"TheBrave")
EstragonTheBrave

Here	you	can	see	the	effect	of	both	the	getter	and	the	setter	on	the	new	value	for
name.
Assigning	new	values	to	class	properties	changes	the	state	of	the	class	on	which
they	are	assigned.	If	name	were	still	a	val,	then	the	example	that	you	just	tried
in	the	REPL	would	result	in	the	following	error	message:
error:	val	cannot	be	reassigned
(If	you	try	this,	you	will	need	to	reload	the	REPL	with	the	Build	and	restart	button
to	the	left	so	that	the	change	to	Player	is	recognized.)
Property	visibility
Properties	are	different	from	variables	defined	locally	within	a	function.	When	a
property	is	defined,	it	is	defined	at	the	class	level.	As	such,	it	may	be	accessible
to	other	classes,	if	its	visibility	allows.	Over-permissive	visibility	can	cause
problems:	If	other	classes	have	access	to	a	Player’s	data,	then	any	class	in
your	application	could	make	changes	to	that	instance	of	Player	at	will.
Properties	provide	fine-grained	control	around	reading	and	modifying	data
through	their	getters	and	setters.	All	properties	have	getters	–	and	all	var
properties	have	setters	–	whether	you	define	custom	behavior	for	them	or	not.	By
default,	the	visibility	of	a	property’s	getter	and	setter	match	the	visibility	of	the
property	itself.	So	if	you	have	a	public	property,	both	its	getter	and	setter	are
public.
What	if	you	want	to	expose	access	to	a	property	but	do	not	want	to	expose	its
setter?	You	can	define	the	visibility	of	the	setter	separately.	Make	the	name
property’s	setter	private:
Listing	12.12		Hiding	name’s	setter	(Player.kt)
class	Player	{
var	name	=	"madrigal"
get()	=	field.capitalize()
private	set(value)	{
field	=	value.trim()
## }
fun	castFireball(numFireballs:	Int	=	2)	=
println("A	glass	of	Fireball	springs	into	existence.	(x$numFireballs)")
## }
Now,	name	can	be	accessed	from	anywhere	in	NyetHack,	but	it	can	only	be
modified	from	within	Player.	This	technique	is	quite	useful	if	you	want	to
control	whether	certain	properties	can	be	modified	by	other	parts	of	your
application.

A	getter	or	a	setter’s	visibility	cannot	be	more	permissive	than	the	property	on
which	it	is	defined.	You	can	restrict	access	to	a	property	via	a	getter	or	a	setter,
but	they	are	not	intended	for	making	properties	more	visible.
Remember	that	properties	must	be	assigned	when	declared.	This	rule	is
especially	important	when	your	class	has	a	public	property.	If	an	instance	of	the
Player	class	is	referenced	elsewhere	in	your	codebase,	then	whoever	makes
that	reference	must	be	assured	that	when	they	reference	Player.name,	a	value
for	name	exists.
Computed	properties
Earlier,	we	said	that	when	you	define	a	property,	a	field	is	always	generated	to
store	the	value	the	property	encapsulates.	That	is	true	...	except	in	a	particular
case:	computed	properties.	A	computed	property	is	a	property	that	is	specified
with	an	overridden	get	and/or	set	operator	in	a	way	that	makes	a	field
unnecessary.	In	such	cases,	Kotlin	will	not	generate	a	field.
In	the	REPL,	create	a	Dice	class	with	a	computed	rolledValue	property:
Listing	12.13		Defining	a	computed	property	(REPL)
class	Dice()	{
val	rolledValue
get()	=	(1..6).shuffled().first()
## }
Now,	take	it	for	a	roll:
Listing	12.14		Accessing	the	computed	property	(REPL)
val	myD6	=	Dice()
myD6.rolledValue
## 6
myD6.rolledValue
## 1
myD6.rolledValue
## 4
The	value	is	different	each	time	the	rolledValue	property	is	accessed.	This	is
because	the	value	is	computed	each	time	the	variable	is	accessed.	It	has	no	initial
or	default	value	–	and	no	backing	field	to	hold	a	value.
You	will	look	more	carefully	at	how	val	and	var	properties	are	implemented	and
what	bytecode	is	emitted	by	the	compiler	when	you	specify	them	in	the	section
called	For	the	More	Curious:	A	Closer	Look	at	var	and	val	Properties	near	the
end	of	this	chapter.

Refactoring	NyetHack
You	have	learned	about	class	functions,	properties,	and	encapsulation,	and	you
have	done	some	of	the	work	to	apply	these	concepts	to	NyetHack.	It	is	time	to
finish	the	job	and	thoroughly	clean	up	NyetHack’s	code.
You	will	be	moving	chunks	of	code	from	one	file	to	another.	It	helps	to	see	the
two	files	side	by	side.	Fortunately,	IntelliJ	provides	this	feature.
With	Game.kt	open,	right-click	on	the	Player.kt	tab	at	the	top	of	the	editor	and
select	Split	Vertically	(Figure	12.3).
Figure	12.3		Splitting	the	editor	vertically
You	now	have	another	editor	pane	to	work	in	(Figure	12.4).	(You	can	drag	tabs
between	editor	panes	to	configure	your	editor	experience	to	your	liking.)

Figure	12.4		Two	panes
This	is	a	complex	refactor,	but	by	the	end	of	this	section	Player	will	expose	a
selective	API	and	encapsulate	the	implementation	details	that	other	components
do	not	need	to	know	about.	In	short:	It	is	for	a	good	cause.
First,	locate	the	variables	declared	in	Game.kt’s	main	function	that	make
sense	as	properties	of	Player.	These	include	healthPoints,	isBlessed,
and	isImmortal.	Refactor	them	to	become	properties	of	Player.
Listing	12.15		Removing	variables	from	main	(Game.kt)
fun	main(args:	Array<String>)	{
var	healthPoints	=	89
val	isBlessed	=	true
val	isImmortal	=	false
val	player	=	Player()
player.castFireball()
## ...
## }
## ...
As	you	add	them	to	Player.kt,	be	sure	that	the	variables	are	all	defined
inside	the	Player	class’s	body.
Listing	12.16		Adding	properties	to	Player	(Player.kt)
class	Player	{
var	name	=	"madrigal"
get()	=	field.capitalize()
private	set(value)	{

field	=	value.trim()
## }
var	healthPoints	=	89
val	isBlessed	=	true
val	isImmortal	=	false
fun	castFireball(numFireballs:	Int	=	2)	=
println("A	glass	of	Fireball	springs	into	existence.	(x$numFireballs)")
## }
These	changes	will	result	in	a	number	of	errors	in	Game.kt.	Hang	tight;	by	the
time	you	are	finished,	all	the	errors	will	be	taken	care	of.
healthPoints	and	isBlessed	will	be	accessed	from	Game.kt.	But
isImmortal	is	never	accessed	from	outside	of	Player,	so	it	behooves	you	to
make	isImmortal	private.	Encapsulate	the	property	by	making	it	private	to
ensure	that	other	classes	will	not	have	access	to	it.
Listing	12.17		Encapsulating	isImmortal	within	Player
(Player.kt)
class	Player	{
var	name	=	"madrigal"
get()	=	field.capitalize()
private	set(value)	{
field	=	value.trim()
## }
var	healthPoints	=	89
val	isBlessed	=	true
private	val	isImmortal	=	false
fun	castFireball(numFireballs:	Int	=	2)	=
println("A	glass	of	Fireball	springs	into	existence.	(x$numFireballs)")
## }
Next,	review	the	functions	declared	in	Game.kt.	printPlayerStatus
prints	out	the	textual	interface	for	the	game,	so	it	is	appropriate	for	it	to	be
declared	in	Game.kt.	But	auraColor	and	formatHealthStatus	both
relate	directly	to	the	player,	rather	than	the	gameplay.	Therefore,	those	two
functions	belong	in	the	class	definition	rather	than	in	main.
Move	auraColor	and	formatHealthStatus	into	Player.
Listing	12.18		Removing	functions	from	main	(Game.kt)
fun	main(args:	Array<String>)	{
## ...
## }
private	fun	formatHealthStatus(healthPoints:	Int,	isBlessed:	Boolean)	=
when	(healthPoints)	{
100	->	"is	in	excellent	condition!"
in	90..99	->	"has	a	few	scratches."
in	75..89	->	if	(isBlessed)	{
"has	some	minor	wounds,	but	is	healing	quite	quickly!"
}	else	{
"has	some	minor	wounds."
## }
in	15..74	->	"looks	pretty	hurt."
else	->	"is	in	awful	condition!"
## }

private	fun	printPlayerStatus(auraColor:	String,
isBlessed:	Boolean,
name:	String,
healthStatus:	String)	{
println("(Aura:	$auraColor)	"	+
"(Blessed:	${if	(isBlessed)	"YES"	else	"NO"})")
println("$name	$healthStatus")
## }
private	fun	auraColor(isBlessed:	Boolean,
healthPoints:	Int,
isImmortal:	Boolean):	String	{
val	auraVisible	=	isBlessed	&&	healthPoints	>	50	||	isImmortal
val	auraColor	=	if	(auraVisible)	"GREEN"	else	"NONE"
return	auraColor
## }
Again,	make	sure	the	refactored	functions	are	inside	the	class’s	body.
Listing	12.19		Adding	class	functions	to	Player	(Player.kt)
class	Player	{
var	name	=	"madrigal"
get()	=	field.capitalize()
private	set(value)	{
field	=	value.trim()
## }
var	healthPoints	=	89
val	isBlessed	=	true
private	val	isImmortal	=	false
private	fun	auraColor(isBlessed:	Boolean,
healthPoints:	Int,
isImmortal:	Boolean):	String	{
val	auraVisible	=	isBlessed	&&	healthPoints	>	50	||	isImmortal
val	auraColor	=	if	(auraVisible)	"GREEN"	else	"NONE"
return	auraColor
## }
private	fun	formatHealthStatus(healthPoints:	Int,	isBlessed:	Boolean)	=
when	(healthPoints)	{
100	->	"is	in	excellent	condition!"
in	90..99	->	"has	a	few	scratches."
in	75..89	->	if	(isBlessed)	{
"has	some	minor	wounds,	but	is	healing	quite	quickly!"
}	else	{
"has	some	minor	wounds."
## }
in	15..74	->	"looks	pretty	hurt."
else	->	"is	in	awful	condition!"
## }
fun	castFireball(numFireballs:	Int	=	2)	=
println("A	glass	of	Fireball	springs	into	existence.	(x$numFireballs)")
## }
That	takes	care	of	the	cutting	and	pasting,	but	there	is	work	left	to	do	in	both
Game.kt	and	Player.kt.	For	now,	turn	your	attention	to	Player.
(If	you	split	your	editor	earlier,	you	can	un-split	it	now	by	closing	all	the	files
open	in	a	pane.	Close	files	by	clicking	the	X	in	their	tab	[Figure	12.5]	or	by
pressing	Command-W	[Ctrl-W].)

Figure	12.5		Closing	a	tab	in	IntelliJ
In	Player.kt,	notice	that	the	functions	previously	declared	in	Game.kt	that
were	moved	to	Player	–	auraColor	and	formatHealthStatus	–	take
in	values	that	are	now	properties	of	Player	–	isBlessed,	healthPoints,
and	isImmortal.	When	the	functions	were	defined	in	Game.kt,	they	were
outside	of	Player’s	class	scope.	But	because	they	are	now	class	functions	on
Player,	they	have	access	to	all	of	the	properties	declared	in	Player.
This	means	that	the	class	functions	in	Player	no	longer	need	any	of	their
parameters,	as	they	can	all	be	accessed	from	within	the	Player	class.
Modify	the	function	headers	to	remove	their	parameters.
Listing	12.20		Removing	unnecessary	parameters	from	class
functions	(Player.kt)
class	Player	{
var	name	=	"madrigal"
get()	=	field.capitalize()
private	set(value)	{
field	=	value.trim()
## }
var	healthPoints	=	89
val	isBlessed	=	true
private	val	isImmortal	=	false
private	fun	auraColor(isBlessed:	Boolean,
healthPoints:	Int,
isImmortal:	Boolean):	String	{
val	auraVisible	=	isBlessed	&&	healthPoints	>	50	||	isImmortal
val	auraColor	=	if	(auraVisible)	"GREEN"	else	"NONE"
return	auraColor
## }
private	fun	formatHealthStatus(healthPoints:	Int,	isBlessed:	Boolean)	=
when	(healthPoints)	{
100	->	"is	in	excellent	condition!"
in	90..99	->	"has	a	few	scratches."
in	75..89	->	if	(isBlessed)	{
"has	some	minor	wounds,	but	is	healing	quite	quickly!"
}	else	{
"has	some	minor	wounds."
## }
in	15..74	->	"looks	pretty	hurt."
else	->	"is	in	awful	condition!"
## }
fun	castFireball(numFireballs:	Int	=	2)	=
println("A	glass	of	Fireball	springs	into	existence.	(x$numFireballs)")
## }
Before	this	change,	a	reference	to	healthPoints	within	the

formatHealthStatus	function	would	be	a	reference	to
formatHealthStatus’s	parameter,	because	that	reference	was	scoped	to	the
function.	Without	a	variable	named	healthPoints	within	the	function	scope,
the	next	most	local	scope	is	at	the	class	level,	where	the	healthPoints
property	is	defined.
Next,	notice	that	the	two	class	functions	are	defined	as	private.	This	was	not	a
problem	when	they	were	defined	in	the	same	file	from	which	they	were
accessed.	But	now	that	they	are	private	to	the	Player	class,	they	are	not	visible
to	other	classes.	These	functions	should	not	be	encapsulated,	so	make	them
visible	by	removing	the	private	keyword	from	auraColor	and
formatHealthStatus.
Listing	12.21		Making	class	functions	public	(Player.kt)
class	Player	{
var	name	=	"madrigal"
get()	=	field.capitalize()
private	set(value)	{
field	=	value.trim()
## }
var	healthPoints	=	89
val	isBlessed	=	true
private	val	isImmortal	=	false
private	fun	auraColor():	String	{
## ...
## }
private	fun	formatHealthStatus()	=	when	(healthPoints)	{
## ...
## }
fun	castFireball(numFireballs:	Int	=	2)	=
println("A	glass	of	Fireball	springs	into	existence.	(x$numFireballs)")
## }
At	this	point,	your	properties	and	functions	are	declared	in	the	correct	places,	but
their	invocation	syntax	in	Game.kt	is	no	longer	correct,	for	three	reasons:
- printPlayerStatus	no	longer	has	access	to	the	variables	that	it
needs	to	do	its	job,	because	those	variables	are	now	properties	of
## Player.
- Now	that	functions	like	auraColor	are	class	functions	declared	in
Player,	they	need	to	be	called	on	an	instance	of	Player.
- Player’s	class	functions	need	to	be	called	with	their	new,
parameterless	signatures.
Refactor	printPlayerStatus	to	take	a	Player	as	an	argument	that	can	be
used	to	access	any	properties	necessary	and	to	call	the	new,	parameterless

versions	of	auraColor	and	formatHealthStatus.
Listing	12.22		Calling	class	functions	(Game.kt)
fun	main(args:	Array<String>)	{
val	player	=	Player()
player.castFireball()
## //	Aura
val	auraColor	=	player.auraColor(isBlessed,	healthPoints,	isImmortal)
//	Player	status
val	healthStatus	=	formatHealthStatus(healthPoints,	isBlessed)
printPlayerStatus(playerauraColor,	isBlessed,	player.name,	healthStatus)
## //	Aura
player.auraColor(isBlessed,	healthPoints,	isImmortal)
## }
private	fun	printPlayerStatus(player:	PlayerauraColor:	String,
isBlessed:	Boolean,
name:	String,
healthStatus:	String)	{
println("(Aura:	${player.auraColor()})	"	+
"(Blessed:	${if	(player.isBlessed)	"YES"	else	"NO"})")
println("${player.name}	${player.formatHealthStatus()}")
## }
This	change	to	printPlayerStatus’s	header	keeps	it	clean	from	the
implementation	details	of	Player.	Compare	these	two	signatures:
printPlayerStatus(player:	Player)
printPlayerStatus(auraColor:	String,
isBlessed:	Boolean,
name:	String,
healthStatus:	String)
Which	is	cleaner	to	call?	The	latter	requires	the	caller	to	know	quite	a	lot	about
the	implementation	details	of	Player.	The	former	simply	requires	an	instance
of	Player.	Here	you	see	one	of	the	benefits	of	object-oriented	programming:
Since	the	data	is	now	a	part	of	the	Player	class,	it	can	be	referenced	without
having	to	explicitly	pass	it	to	and	from	each	function.
Take	a	step	back	and	assess	what	you	have	accomplished	in	this	refactor.	The
Player	class	now	owns	all	the	data	and	behaviors	specific	to	a	player	entity	in
the	game.	It	deliberately	exposes	three	properties	and	three	functions	and
encapsulates	all	other	implementation	details	as	private	concerns	that	only	the
Player	class	should	have	access	to.	These	functions	advertise	capabilities	of
the	player:	The	player	can	provide	a	health	status,	the	player	can	tell	you	their
aura	color,	etc.
As	your	applications	grow	in	scale,	keeping	scope	manageable	is	imperative.	By
embracing	object-oriented	programming,	you	subscribe	to	the	idea	that	each
object	should	hold	its	own	responsibilities	and	expose	only	the	properties	and
functions	that	other	functions	and	classes	should	see.	Now,	Player	exposes
what	it	means	to	be	a	player	of	NyetHack,	and	Game.kt	holds	the	game	loop	in

a	much	more	readable	main	function.
Run	Game.kt	to	confirm	that	everything	works	as	it	did	before.	And	pat
yourself	on	the	back	for	completing	that	refactor.	In	the	chapters	to	come,	you
will	build	on	this	solid	foundation	for	NyetHack,	adding	complexity	and	features
that	rely	on	the	object-oriented	programming	paradigm.
In	the	next	chapter,	you	will	add	more	ways	to	instantiate	Player	as	you	learn
about	initialization.	But	before	growing	your	application	further,	it	is	a	good	time
to	learn	about	packages.

## Using	Packages
A	package	is	like	a	folder	for	similar	elements	that	helps	give	a	logical	grouping
to	the	files	in	your	project.	For	example,	the	kotlin.collections	package
contains	classes	to	create	and	manage	lists	and	sets.	Packages	allow	you	to
organize	your	project	as	it	becomes	more	complex,	and	they	also	prevent	naming
collisions.
Create	a	package	by	right-clicking	your	src	directory	and	selecting	New	→
Package.	When	prompted,	name	your	package	com.bignerdranch.nyethack.
(You	can	name	a	package	anything	you	like,	but	we	prefer	this	reverse-DNS
style	that	scales	with	the	number	of	applications	that	you	write.)
The	package	you	created,	com.bignerdranch.nyethack,	is	the	top-level	package
for	NyetHack.	Including	your	files	within	a	top-level	package	will	prevent	any
naming	collisions	with	types	that	you	define	and	types	defined	elsewhere	–	for
instance,	in	external	libraries	or	modules.	As	you	add	more	files,	you	can	create
additional	packages	to	keep	the	files	organized.
Notice	that	the	new	com.bignerdranch.nyethack	package	(which	resembles	a
folder)	is	displayed	in	the	project	tool	window.	Add	your	source	files
(Game.kt,	Player.kt,	SwordJuggler.kt,	and	Tavern.kt)	to	your
new	package	by	dragging	them	into	the	package	(Figure	12.6).

Figure	12.6		The	com.bignerdranch.nyethack	package
Organizing	code	using	classes,	files,	and	packages	will	help	you	to	make	sure
that	your	code	is	clear	as	your	application	grows	in	complexity.

For	the	More	Curious:	A	Closer	Look	at	var	and
val	Properties
In	this	chapter	you	learned	that	the	var	and	val	keywords	are	used	when
specifying	a	class	property	–	var	for	writable,	and	val	for	read-only.
You	may	be	wondering	how	a	Kotlin	class	property	works,	under	the	hood,	when
targeting	the	JVM.
To	understand	how	class	properties	are	implemented,	it	is	helpful	to	look	at	the
decompiled	JVM	bytecode	–	specifically,	to	compare	the	bytecode	generated	for
a	single	property	depending	on	how	it	is	specified.	Create	a	new	file	called
Student.kt.	(You	will	delete	this	file	after	this	exercise.)
First,	define	a	class	with	a	var	property	(which	allows	both	reading	and	writing
the	class	property).
Listing	12.23		Defining	a	Student	class	(Student.kt)
class	Student(var	name:	String)
The	name	property	in	this	example	is	defined	in	Student’s	primary
constructor.	You	will	learn	more	about	constructors	in	Chapter	13,	but	for	now,
just	think	of	the	constructor	as	providing	a	way	to	customize	how	your	class	is
built.	In	this	case,	the	constructor	gives	you	a	way	to	specify	the	name	of	the
student.
Now,	take	a	look	at	the	resulting	decompiled	bytecode	(Tools	→	Kotlin	→	Show
## Kotlin	Bytecode):
public	final	class	Student	{
@NotNull
private	String	name;
@NotNull
public	final	String	getName()	{
return	this.name;
## }
public	final	void	setName(@NotNull	String	var1)	{
Intrinsics.checkParameterIsNotNull(var1,	"<set-?>");
this.name	=	var1;
## }
public	Student(@NotNull	String	name)	{
Intrinsics.checkParameterIsNotNull(name,	"name");
super();
this.name	=	name;
## }
## }
Four	elements	of	the	Student	class	were	generated	in	bytecode	when	you
defined	the	name	var	on	the	class:	a	name	field	(where	name’s	data	will	be

stored),	a	getter	method,	a	setter	method,	and	finally	a	constructor	assignment
for	the	field,	where	the	name	field	is	initialized	with	the	Student’s	name
constructor	argument.
Now	try	changing	the	property	from	a	var	to	a	val:
Listing	12.24		Changing	the	var	to	a	val	(Student.kt)
class	Student(varval	name:	String)
And	observe	the	resulting	decompiled	bytecode.	(The	strike-through	here	is	to
emphasize	what	is	missing.)
public	final	class	Student	{
@NotNull
private	String	name;
@NotNull
public	final	String	getName()	{
return	this.name;
## }
public	final	void	setName(@NotNull	String	var1)	{
Intrinsics.checkParameterIsNotNull(var1,	"<set-?>");
this.name	=	var1;
## }
public	Student(@NotNull	String	name)	{
Intrinsics.checkParameterIsNotNull(name,	"name");
super();
this.name	=	name;
## }
## }
The	difference	between	using	the	var	keyword	and	val	keyword	for	the	property
is	the	absence	of	a	setter.
You	also	learned	in	this	chapter	that	you	can	define	a	custom	getter	or	setter	for	a
property.	What	happens	in	bytecode	when	you	define	a	computed	property,	with
a	custom	getter	and	no	field	for	storing	the	data?	Try	it	with	the	Student	class
you	defined:
Listing	12.25		Making	name	a	computed	property	(Student.kt)
class	Student(val	name:	String)	{
val	name:	String
get()	=	"Madrigal"
## }
Now	take	a	look	at	the	resulting	decompiled	bytecode:
public	final	class	Student	{
@NotNull
private	String	name;
@NotNull
public	final	String	getName()	{
return	this.name;
return	"Madrigal"
## }
public	final	void	setName(@NotNull	String	var1)	{
Intrinsics.checkParameterIsNotNull(var1,	"<set-?>");
this.name	=	var1;
## }

public	Student(@NotNull	String	name)	{
Intrinsics.checkParameterIsNotNull(name,	"name");
super();
this.name	=	name;
## }
## }
Only	one	element	was	generated	in	the	bytecode	this	time	–	a	getter.	The
compiler	was	able	to	determine	that	no	field	was	required,	since	no	data	from	a
field	was	read	or	written.
This	particular	feature	of	properties	–	computing	a	value,	rather	than	reading	a
field’s	state	–	is	another	reason	we	use	the	terms	“writable”	and	“read-only”
rather	than	“mutable”	and	“immutable.”	Look	again	at	the	Dice	class	you
defined	in	the	REPL	earlier:
class	Dice()	{
val	rolledValue
get()	=	(1..6).shuffled().first()
## }
The	result	of	reading	Dice’s	rolledValue	property	is	a	random	value
ranging	from	1	to	6,	determined	each	time	the	property	is	accessed	–	hardly	the
definition	of	“immutable.”
When	you	are	done	exploring	the	bytecode,	close	Student.kt	and	delete	it	by
Control-clicking	(right-clicking)	on	the	filename	in	the	project	tool	window	and
selecting	Delete.

For	the	More	Curious:	Guarding	Against	Race
## Conditions
When	a	class	property	is	both	nullable	and	mutable,	you	must	ensure	that	it	is
non-null	before	referencing	it.	For	example,	consider	the	following	code	that
checks	whether	a	player	is	wielding	a	weapon	(since	the	player	may	have	been
disarmed	or	dropped	their	weapon)	and,	if	so,	prints	its	name:
class	Weapon(val	name:	String)
class	Player	{
var	weapon:	Weapon?	=	Weapon("Ebony	Kris")
fun	printWeaponName()	{
if	(weapon	!=	null)	{
println(weapon.name)
## }
## }
## }
fun	main(args:	Array<String>)	{
Player().printWeaponName()
## }
You	may	be	surprised	to	learn	that	this	code	does	not	compile.	Check	out	the
error	to	see	why	(Figure	12.7):
Figure	12.7		Smart	cast	to	‘Weapon’	is	impossible
The	compiler	prevents	the	code	from	compiling	because	of	the	possibility	of
what	is	known	as	a	race	condition.	A	race	condition	occurs	when	some	other
part	of	your	program	simultaneously	modifies	the	state	of	your	code	in	a	manner
that	leads	to	unpredictable	results.
Here,	the	compiler	sees	that	although	weapon	is	checked	for	a	null	value,	there
is	still	a	possibility	of	the	Player’s	weapon	property	being	replaced	with	a
null	value	between	the	time	that	check	passed	and	the	time	the	name	of	the
weapon	is	printed.
Therefore,	unlike	in	other	cases	where	weapon	could	be	smart	cast	within	the

null	check,	the	compiler	balks	because	it	cannot	safely	say	that	weapon	will
never	be	null.
One	way	to	fix	this	problem	is	to	use	a	standard	function	like	also,	which	you
read	about	in	Chapter	9,	to	guard	against	null:
class	Player	{
var	weapon:	Weapon?	=	Weapon("Ebony	Kris")
fun	printWeaponName()	{
weapon?.also	{
println(it.name)
## }
## }
## }
This	code	compiles,	thanks	to	the	also	standard	function.	Instead	of	referring	to
the	class	property,	it,	the	argument	to	also,	is	a	local	variable	that	exists	only
within	the	scope	of	the	anonymous	function.	Therefore,	the	it	variable	is
guaranteed	to	not	be	changed	by	another	part	of	your	program.	The	smart	cast
issue	is	avoided	entirely,	because	instead	of	dealing	with	the	original	nullable
property,	this	code	uses	a	read-only,	non-nullable	local	variable	(since	also	is
called	after	the	safe	call	operator:	weapon?.also).

For	the	More	Curious:	Package	Private
Recall	from	earlier	in	the	chapter	the	discussion	about	public	and	private
visibility	levels.	As	you	learned,	a	Kotlin	class,	function,	or	property	is	public	by
default	(without	a	visibility	modifier),	which	means	it	is	usable	by	any	other
class,	function,	or	property	in	the	project.
If	you	are	familiar	with	Java,	you	may	have	noticed	that	the	default	access	level
differs	from	that	of	Kotlin:	By	default,	Java	uses	package	private	visibility,
which	means	that	methods,	fields,	and	classes	with	no	visibility	modifier	are
usable	from	classes	belonging	to	the	same	package	only.	Kotlin	opted	out	of
supporting	package	private	visibility	because	it	accomplishes	little.	In	practice,	it
is	easily	circumvented	by	creating	a	matching	package	and	adding	a	class	to	it.
On	the	other	hand,	a	visibility	level	Kotlin	provides	that	Java	does	not	is	the
internal	visibility	level.	Internal	visibility	marks	a	function,	class,	or	property	as
public	to	other	functions,	classes,	and	properties	within	the	same	module.	A
module	is	a	discrete	unit	of	functionality	that	can	be	run,	tested,	and	debugged
independently.
Modules	include	such	things	as	source	code,	build	scripts,	unit	tests,	deployment
descriptors,	and	so	on.	NyetHack	is	one	module	within	your	project,	and	an
IntelliJ	project	can	contain	multiple	modules.	Modules	can	also	depend	on	other
modules	for	source	files	and	resources.
Internal	visibility	is	useful	for	sharing	classes	within	a	module	while	disallowing
access	from	other	modules,	which	makes	it	a	great	choice	for	building	libraries
in	Kotlin.