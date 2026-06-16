

## 10
Lists	and	Sets
Working	with	a	group	of	related	values	is	an	essential	part	of	many	programs.
For	example,	your	program	might	manage	lists	of	books,	travel	destinations,
menu	items,	or	tavern	patron	check	balances.	Collections	allow	you	to
conveniently	work	with	those	groups	of	values	and	pass	them	as	arguments	to
functions.
You	will	see	the	most	commonly	used	collection	types	in	the	next	two	chapters:
List,	Set,	and	Map.	Like	the	other	variable	types	you	learned	about	in
Chapter	2,	lists,	sets,	and	maps	come	in	two	distinct	varieties:	mutable	and	read-
only.	In	this	chapter,	we	will	focus	on	lists	and	sets.
You	are	going	to	use	collections	to	upgrade	NyetHack’s	tavern.	When	your	work
is	finished,	the	tavern	will	sport	a	full	menu	of	items	for	purchase	–	along	with	a
bustling	scene	of	patrons	eager	to	spend	their	gold.

## Lists
You	worked	indirectly	with	a	list	in	Chapter	7,	when	you	used	the	split
function	to	extract	three	elements	from	the	menu	data.	Lists	hold	an	ordered
collection	of	values	and	allow	duplicate	values.
Begin	your	tavern	simulation	in	Tavern.kt	by	adding	a	list	of	patrons,	using
the	listOf	function.	listOf	returns	a	read-only	list	(more	on	that	shortly)
populated	with	the	elements	you	provide	for	the	argument.	Create	your	list	with
three	patron	names:
Listing	10.1		Creating	a	list	of	patrons	(Tavern.kt)
import	kotlin.math.roundToInt
const	val	TAVERN_NAME	=	"Taernyl's	Folly"
var	playerGold	=	10
var	playerSilver	=	10
val	patronList:	List<String>	=	listOf("Eli",	"Mordoc",	"Sophie")
fun	main(args:	Array<String>)	{
placeOrder("shandy,Dragon's	Breath,5.91")
println(patronList)
## }
## ...
Up	to	now,	you	have	been	creating	variables	of	various	types	by	simply
declaring	them.	But	collections	require	two	steps:	creating	the	collection	(here,
the	list	to	hold	the	patrons)	and	adding	contents	to	it	(the	patron	names).	Kotlin
provides	functions,	like	listOf,	that	do	both	at	once.
Now	that	you	have	a	list,	let’s	take	a	closer	look	at	the	List	type.
Though	type	inference	does	work	with	lists,	you	included	the	type	information	–
val	patronList:	List<String>	–	to	make	it	visible	for	discussion.	Notice	the
diamond	braces	in	List<String>.	<String>	is	known	as	a	parameterized	type,
and	it	tells	the	compiler	about	the	type	that	the	contents	of	the	list	will	be	–	in
this	case,	Strings.	Changing	the	type	parameter	changes	what	the	compiler
allows	the	list	to	hold.
If	you	tried	to	put	an	integer	in	the	patronList,	the	compiler	would	not	allow
it.	Try	adding	a	number	to	the	list	you	defined:
Listing	10.2		Adding	an	integer	to	a	list	of	strings	(Tavern.kt)
## ...
var	patronList:	List<String>	=	listOf("Eli",	"Mordoc",	"Sophie",	1)
## ...

IntelliJ	warns	you	that	the	integer	does	not	conform	to	the	expected	type,
String.	Type	parameters	are	used	with	List	because	List	is	a	generic	type.
This	means	that	a	list	can	hold	any	type	of	data,	including	textual	data	like
strings	(as	in	the	case	of	patronList)	or	characters,	numeric	data	like	integers
or	doubles,	or	even	a	new	type	that	you	define.	(You	will	learn	more	about
generics	in	Chapter	17.)
Undo	your	last	change,	either	with	IntelliJ’s	undo	command	(Command-z	[Ctrl-
z])	or	by	deleting	the	integer:
Listing	10.3		Correcting	the	list	contents	(Tavern.kt)
## ...
var	patronList:	List<String>	=	listOf("Eli",	"Mordoc",	"Sophie",	1)
## ...
Accessing	a	list’s	elements
Recall	from	your	work	with	the	split	function	in	Chapter	7	that	you	can
access	any	element	of	a	list	using	the	element’s	index	and	the	[]	operator.	Lists
are	zero-indexed,	so	"Eli"	is	at	index	0,	and	"Sophie"	is	at	index	2.
Change	Tavern.kt	to	print	only	the	first	patron.	Also,	remove	the	explicit
type	information	from	patronList.	Now	that	you	have	seen	the
parameterized	type	that	this	List	uses,	you	can	return	to	using	type	inference
for	cleaner	code.
Listing	10.4		Accessing	the	first	patron	(Tavern.kt)
import	kotlin.math.roundToInt
const	val	TAVERN_NAME	=	"Taernyl's	Folly"
var	playerGold	=	10
var	playerSilver	=	10
val	patronList:	List<String>	=	listOf("Eli",	"Mordoc",	"Sophie")
fun	main(args:	Array<String>)	{
placeOrder("shandy,Dragon's	Breath,5.91")
println(patronList[0])
## }
## ...
Run	Tavern.kt.	You	will	see	the	first	patron,	Eli,	printed.
List	also	provides	other	convenience	index	access	functions,	like	accessing	the
first	or	last	element:
patronList.first()	//	Eli
patronList.last()	//	Sophie
Index	boundaries	and	safe	index	access

Accessing	an	element	by	index	requires	care,	because	attempting	to	access	an
element	at	an	index	that	does	not	exist	–	say,	the	fourth	item	from	a	list	that
contains	only	three	–	causes	an	ArrayIndexOutOfBoundsException
exception.
Try	this	in	the	Kotlin	REPL.	(You	can	copy	the	first	line	from	Tavern.kt.)
Listing	10.5		Accessing	a	nonexistent	index	(REPL)
val	patronList	=	listOf("Eli",	"Mordoc",	"Sophie")
patronList[4]
The	result	is	java.lang.ArrayIndexOutOfBoundsException:	4.
Because	accessing	an	element	by	an	index	can	throw	an	exception,	Kotlin
provides	safe	index	access	functions	that	allow	you	to	deal	with	the	problem
differently.	Instead	of	throwing	an	exception	if	the	index	is	out	of	bounds,	some
other	result	will	occur.
For	example,	one	of	these	safe	index	access	functions,	getOrElse,	takes	two
arguments:	The	first	is	the	requested	index	(in	parentheses,	not	square	brackets).
The	second	is	a	lambda	that	generates	a	default	value,	instead	of	an	exception,	if
the	requested	index	does	not	exist.
Try	it	out	in	the	REPL:
Listing	10.6		Testing	getOrElse	(REPL)
val	patronList	=	listOf("Eli",	"Mordoc",	"Sophie")
patronList.getOrElse(4)	{	"Unknown	Patron"	}
This	time,	the	result	is	Unknown	Patron.	The	anonymous	function	was	used	to
provide	a	default	value,	since	the	requested	index	does	not	exist.
Another	safe	index	access	function,	getOrNull,	returns	null	instead	of
throwing	an	exception.	When	you	use	getOrNull,	you	must	decide	what	to	do
with	the	null	value,	as	you	saw	in	Chapter	6.	One	option	is	to	coalesce	the	null
value	to	a	default.	Try	using	getOrNull	with	the	null	coalescing	operator	in
the	REPL.
Listing	10.7		Testing	getOrNull	(REPL)
val	fifthPatron	=	patronList.getOrNull(4)	?:	"Unknown	Patron"
fifthPatron
Again,	the	result	is	Unknown	Patron.
Checking	the	contents	of	a	list

The	tavern	has	dark	corners	and	secret	back	rooms.	Fortunately,	the	keen-eyed
tavern	master	keeps	diligent	records	of	which	patrons	have	left	or	entered	in	the
patron	list.	If	you	ask	whether	a	particular	patron	is	present,	the	tavern	master
can	tell	you	by	looking	at	the	list.
Update	Tavern.kt	to	use	the	contains	function	to	check	whether	a
particular	patron	is	present:
Listing	10.8		Checking	for	a	patron	(Tavern.kt)
## ...
fun	main(args:	Array<String>)	{
if	(patronList.contains("Eli"))	{
println("The	tavern	master	says:	Eli's	in	the	back	playing	cards.")
}	else	{
println("The	tavern	master	says:	Eli	isn't	here.")
## }
placeOrder("shandy,Dragon's	Breath,5.91")
println(patronList[0])
## }
## ...
Run	Tavern.kt.	Because	patronList	does	contain	"Eli",	you	will	see	The
tavern	master	says:	Eli's	in	the	back	playing	cards.	in	the	console
above	the	output	from	your	placeOrder	call.
Note	that	the	contains	function	performs	a	structural	comparison	for	the
elements	in	the	list,	like	the	structural	equality	operator.
You	can	also	use	the	containsAll	function	to	check	whether	several	patrons
are	present	at	once.	Update	the	code	to	ask	the	tavern	master	whether	both
Sophie	and	Mordoc	are	present:
Listing	10.9		Checking	for	multiple	patrons	(Tavern.kt)
## ...
fun	main(args:	Array<String>)	{
if	(patronList.contains("Eli"))	{
println("The	tavern	master	says:	Eli's	in	the	back	playing	cards.	")
}	else	{
println("The	tavern	master	says:	Eli	isn't	here.")
## }
if	(patronList.containsAll(listOf("Sophie",	"Mordoc")))	{
println("The	tavern	master	says:	Yea,	they're	seated	by	the	stew	kettle.")
}	else	{
println("The	tavern	master	says:	Nay,	they	departed	hours	ago.")
## }
placeOrder("shandy,Dragon's	Breath,5.91")
## }
## ...
Run	Tavern.kt.	You	will	see	the	following	printed:
The	tavern	master	says:	Eli's	in	the	back	playing	cards.
The	tavern	master	says:	Yea,	they're	seated	by	the	stew	kettle.
## ...

Changing	a	list’s	contents
If	a	patron	shows	up	or	leaves	halfway	through	the	night,	the	watchful	tavern
master	needs	to	add	or	remove	the	patron’s	name	from	the	patronList
variable.	Currently,	that	is	not	possible.
listOf	returns	a	read-only	list	that	does	not	allow	changes	to	its	contents:	You
cannot	add,	remove,	update,	or	replace	entries.	Read-only	lists	are	a	good	idea,
because	they	prevent	unfortunate	mistakes	–	like	kicking	a	patron	out	into	the
cold	by	accidentally	removing	them	from	the	list.
The	read-only	nature	of	the	list	has	nothing	to	do	with	the	val	or	var	keyword
you	used	to	define	the	list	variable.	Changing	the	variable	declaration	for
patronList	from	val	(as	it	is	defined	now)	to	var	would	not	change	the	list
from	read-only	to	writable.	Instead,	it	would	allow	you	to	reassign	the
patronList	variable	to	hold	a	new,	different	list.
List	mutability	is	defined	by	the	type	of	the	list	and	refers	to	whether	you	can
modify	the	elements	in	the	list.	Since	patrons	come	and	go	from	the	tavern
freely,	the	type	of	patronList	needs	to	be	changed	to	allow	updates.	In
Kotlin,	a	modifiable	list	is	known	as	a	mutable	list,	and	you	use	the
mutableListOf	function	to	create	one.
Update	Tavern.kt	to	use	mutableListOf	instead	of	listOf.	Mutable
lists	come	with	a	variety	of	functions	for	adding,	removing,	and	updating	items.
Simulate	several	patrons	coming	and	going	by	using	the	add	and	remove
functions:
Listing	10.10		Making	the	patron	list	mutable	(Tavern.kt)
## ...
val	patronList	=	listOf("Eli",	"Mordoc",	"Sophie")
val	patronList	=	mutableListOf("Eli",	"Mordoc",	"Sophie")
fun	main(args:	Array<String>)	{
## ...
placeOrder("shandy,Dragon's	Breath,5.91")
println(patronList)
patronList.remove("Eli")
patronList.add("Alex")
println(patronList)
## }
## ...
Run	Tavern.kt.	You	will	see	the	following	printed	to	the	console:
## ...
Madrigal	exclaims	Ah,	d3l1c10|_|s	Dr4g0n's	Br34th!
[Eli,	Mordoc,	Sophie]
[Mordoc,	Sophie,	Alex]

The	read-only	nature	of	the	list	has	nothing	to	do	with	the	val	or	var	keyword
you	used	to	define	the	list	variable.	Changing	the	variable	declaration	for
patronList	from	val	(as	it	is	defined	now)	to	var	would	not	change	the	list
from	read-only	to	writable.	Instead,	you	would	be	able	to	reassign	the
patronList	variable	to	hold	a	new,	different	list.
List	mutability	is	defined	by	the	type	of	the	list	and	refers	to	whether	you	can
modify	the	elements	in	the	list.	When	you	need	to	be	able	to	modify	the	elements
in	a	list,	use	a	MutableList.	Otherwise,	it	is	a	good	idea	to	restrict	mutability
by	using	List.
Note	that	the	new	element	was	added	at	the	end	of	the	list.	You	can	also	add	a
patron	at	a	particular	position	in	the	list.	For	example,	if	a	VIP	comes	into	the
tavern,	the	tavern	master	can	prioritize	their	place	in	line.
Add	a	VIP	patron	–	coincidentally	also	with	the	name	Alex	–	to	the	beginning	of
the	patron	list.	(This	Alex	is	well-known	around	town	and	enjoys	perks	like
getting	a	pint	of	Dragon’s	Breath	before	everyone	else,	much	to	the	chagrin	of
the	other	Alex.)	List	supports	multiple	elements	with	the	same	value,	such	as
two	patrons	with	the	same	name,	so	adding	another	Alex	is	no	problem	for	the
list.
Listing	10.11		Adding	another	Alex	(Tavern.kt)
## ...
val	patronList	=	mutableListOf("Eli",	"Mordoc",	"Sophie")
fun	main(args:	Array<String>)	{
## ...
placeOrder("shandy,Dragon's	Breath,5.91")
println(patronList)
patronList.remove("Eli")
patronList.add("Alex")
patronList.add(0,	"Alex")
println(patronList)
## }
## ...
Run	Tavern.kt	again.	You	will	see	the	following	printed:
## ...
[Eli,	Mordoc,	Sophie]
[Alex,	Mordoc,	Sophie,	Alex]
To	change	patronList	from	a	read-only	list	to	a	mutable	list,	you	changed
your	code	to	use	mutableListOf	instead	of	listOf.	List	also	provides
functions	for	moving	between	read-only	and	mutable	versions	on	the	fly:
toList	and	toMutableList.	For	example,	you	could	create	a	read-only
version	of	the	mutable	patronList	using	toList:
val	patronList	=	mutableListOf("Eli",	"Mordoc",	"Sophie")
val	readOnlyPatronList	=	patronList.toList()

Say	that	the	famous	Alex	would	prefer	to	go	by	Alexis.	Respect	this	wish	by
modifying	patronList	using	the	set	operator	([]=)	to	reassign	the	string	at
the	first	index	in	the	list.
Listing	10.12		Modifying	a	mutable	list	using	the	set	operator
(Tavern.kt)
## ...
val	patronList	=	mutableListOf("Eli",	"Mordoc",	"Sophie")
fun	main(args:	Array<String>)	{
## ...
placeOrder("shandy,Dragon's	Breath,5.91")
println(patronList)
patronList.remove("Eli")
patronList.add("Alex")
patronList.add(0,	"Alex")
patronList[0]	=	"Alexis"
println(patronList)
## }
## ...
Run	Tavern.kt.	You	will	see	that	patronList	has	been	updated	with
Alexis’	preferred	name.
## ...
[Eli,	Mordoc,	Sophie]
[Alexis,	Mordoc,	Sophie,	Alex]
Functions	that	change	the	contents	of	a	mutable	list	are	called	mutator	functions.
Table	10.1	lists	the	most	commonly	used	mutator	functions	for	lists.
Table	10.1		Mutable	list	mutator	functions
FunctionDescriptionExample(s)
## []=
## (set
operator)
Sets	the	value	at	the	index;	throws	an
exception	if	the	index	does	not	exist.
val	patronList	=
mutableListOf("Eli",

"Mordoc",

"Sophie")
patronList[4]	=	"Reggie"
IndexOutOfBoundsException
add
Adds	an	element	to	the	end	of	the	list,
resizing	it	by	one	element.
val	patronList	=
mutableListOf("Eli",

"Mordoc",

"Sophie")
patronList.add("Reggie")
[Eli,	Mordoc,	Sophie,	Reggie]
patronList.size
## 4
add
(at	index)
Adds	an	element	to	the	list	at	a
particular	index,	resizing	the	list	by	one
element.	Throws	an	exception	if	the
index	does	not	exist.
val	patronList	=
mutableListOf("Eli",

"Mordoc",

"Sophie")
patronList.add(0,	"Reggie")
[Reggie,	Eli,	Mordoc,	Sophie]

patronList.add(5,	"Sophie")
IndexOutOfBoundsException
addAll
Adds	all	of	another	collection	with
contents	of	the	same	type	to	the	list.
val	patronList	=
mutableListOf("Eli",

"Mordoc",

"Sophie")
patronList.addAll(listOf("Reginald",
"Alex"))
[Eli,	Mordoc,	Sophie,	Reginald,
## Alex]
## +=
## (plus
assign
operator)
Adds	an	element	or	collection	of
elements	to	the	list.
mutableListOf("Eli",
"Mordoc",
"Sophie")	+=
"Reginald"
[Eli,	Mordoc,	Sophie,	Reginald]
mutableListOf("Eli",
"Mordoc",
"Sophie")	+=
listOf("Alex",	"Shruti")
[Eli,	Mordoc,	Sophie,	Alex,	Shruti]
## -=
## (minus
assign
operator)
Removes	an	element	or	collection	of
elements	from	the	list.
mutableListOf("Eli",
"Mordoc",
"Sophie")	-=	"Eli"
[Mordoc,	Sophie]
val	patronList	=
mutableListOf("Eli",

"Mordoc",

"Sophie")
patronList	-=	listOf("Eli",	Mordoc")
[Sophie]
clear
Removes	all	the	elements	from	the	list.
mutableListOf("Eli",	"Mordoc",
## Sophie").clear()
## []
removeIf
Removes	elements	from	the	list	based
on	a	predicate	lambda.
val	patronList	=
mutableListOf("Eli",

"Mordoc",

"Sophie")
patronList.removeIf	{
it.contains("o")	}
[Eli]

## Iteration
The	tavern	master	makes	a	point	of	greeting	each	patron,	as	it	is	just	good
business	to	do	so.	Lists	include	built-in	support	for	a	variety	of	functions	that
allow	you	to	perform	an	action	for	each	element	of	their	contents.	This	concept
is	called	iteration.
One	way	to	iterate	through	a	list	is	a	for	loop.	Its	logic	is,	“for	each	element	in
the	list,	do	something.”	You	give	the	element	a	name,	and	the	Kotlin	compiler
will	automatically	detect	its	type	for	you.
Update	Tavern.kt	to	print	a	greeting	for	each	patron.	(Also,	remove	the	code
from	earlier	that	modifies	and	prints	patronList	to	tidy	up	your	console
output.)
Listing	10.13		Iterating	over	the	patronList	with	for	(Tavern.kt)
## ...
fun	main(args:	Array<String>)	{
## ...
placeOrder("shandy,Dragon's	Breath,5.91")
println(patronList)
patronList.remove("Eli")
patronList.add("Alex")
patronList.add(0,	"Alex")
patronList[0]	=	"Alexis"
println(patronList)
for	(patron	in	patronList)	{
println("Good	evening,	$patron")
## }
## }
## ...
Run	Tavern.kt,	and	the	tavern	master	will	greet	each	patron	by	name:
## ...
Good	evening,	Eli
Good	evening,	Mordoc
Good	evening,	Sophie
In	this	case,	because	patronList	is	of	type	MutableList<String>,
patron	will	be	of	type	String.	Within	the	block	of	the	for	loop,	any	code
that	you	apply	to	patron	will	be	applied	to	all	elements	in	patronList.
In	some	languages,	Java	included,	the	default	for	loop	syntax	requires	you	to
work	with	indices	of	the	array	or	collection	you	are	iterating	through.	This	is
often	cumbersome,	but	it	can	be	useful.	The	syntax	is	verbose	and	not	very
readable,	but	you	do	get	a	great	amount	of	control	over	how	you	iterate.
In	Kotlin,	all	for	loops	rely	on	iteration	to	do	their	work.	If	you	are	familiar	with
Java	or	C#,	this	is	equivalent	to	the	foreach	loops	found	in	those	languages.

For	those	familiar	with	Java,	it	can	be	surprising	to	find	that	the	common	Java
expression	for(int	i	=	0;	i	<	10;	i++)	{	...	}	is	not	possible	in	Kotlin.
Instead,	a	for	loop	is	written	for(i	in	1..10)	{	...	}.	However,	at	the
bytecode	level,	the	compiler	will	optimize	a	Kotlin	for	loop	to	use	the	Java
version,	when	possible,	to	improve	performance.
Note	the	in	keyword:
for	(patron	in	patronList)	{	...	}
in	specifies	the	object	being	iterated	over	in	a	for	loop.
The	for	loop	is	simple	and	readable,	but	if	you	prefer	a	more	functional	style	to
your	code,	then	a	loop	using	the	forEach	function	is	also	an	option.
The	forEach	function	traverses	each	element	in	the	list	–	one	by	one,	from	left
to	right	–	and	passes	each	element	to	the	anonymous	function	you	provide	as	an
argument.
Replace	your	for	loop	with	the	forEach	function.
Listing	10.14		Iterating	over	the	patronList	with	forEach
(Tavern.kt)
## ...
fun	main(args:	Array<String>)	{
## ...
placeOrder("shandy,Dragon's	Breath,5.91")
for	(patron	in	patronList)	{
println("Good	evening,	$patron")
## }
patronList.forEach	{	patron	->
println("Good	evening,	$patron")
## }
## }
## ...
Run	Tavern.kt,	and	you	will	see	the	same	output	as	before.	The	for	loop	and
the	forEach	function	are	functionally	equivalent.
Kotlin’s	for	loop	and	forEach	function	handle	indexing	behind	the	scenes.	If
you	also	want	access	to	the	index	of	each	element	in	a	list	as	you	iterate,	use
forEachIndexed.	Update	Tavern.kt	to	use	forEachIndexed	to
display	each	patron’s	position	in	line:
Listing	10.15		Displaying	line	position	with	forEachIndexed
(Tavern.kt)
## ...
fun	main(args:	Array<String>)	{
## ...
placeOrder("shandy,Dragon's	Breath,5.91")
patronList.forEachIndexed	{	index,	patron	->
println("Good	evening,	$patron	-	you're	#${index	+	1}	in	line.")
## }

## }
## ...
Run	Tavern.kt	again	to	see	the	patrons	and	their	positions:
## ...
Good	evening,	Eli	-	you're	#1	in	line.
Good	evening,	Mordoc	-	you're	#2	in	line.
Good	evening,	Sophie	-	you're	#3	in	line.
The	forEach	and	forEachIndexed	functions	are	also	available	on	certain
other	types	in	Kotlin.	This	category	of	types	is	called	Iterable,	and	List,
Set,	Map,	IntRange	(ranges	like	0..9,	which	you	saw	in	Chapter	3),	and
other	collection	types	belong	to	the	Iterable	category.	An	iterable	supports
iteration	–	in	other	words,	it	allows	traversing	the	elements	it	holds,	performing
some	action	for	each	element.
Time	to	get	the	tavern	simulation	going.	Have	each	patron	place	an	order	for	a
Dragon’s	Breath.	To	do	so,	move	the	call	to	placeOrder	within	the	lambda
that	you	passed	to	the	forEachIndexed	function	so	that	it	will	be	called	for
each	patron	in	the	list.	Now	that	patrons	other	than	Madrigal	will	be	ordering,
update	placeOrder	to	accept	the	name	of	the	patron	placing	the	order.
Also,	comment	out	the	call	to	performPurchase	in	placeOrder.	(You
will	add	it	back	in	the	next	chapter.)
Listing	10.16		Simulating	several	orders	(Tavern.kt)
## ...
fun	main(args:	Array<String>)	{
## ...
placeOrder("shandy,Dragon's	Breath,5.91")
patronList.forEachIndexed	{	index,	patron	->
println("Good	evening,	$patron	-	you're	#${index	+	1}	in	line.")
placeOrder(patron,	"shandy,Dragon's	Breath,5.91")
## }
## }
## ...
private	fun	placeOrder(patronName:	String,	menuData:	String)	{
val	indexOfApostrophe	=	TAVERN_NAME.indexOf('\'')
val	tavernMaster	=	TAVERN_NAME.substring(0	until	indexOfApostrophe)
println("Madrigal	speaks	with	$tavernMaster	about	their	order.")
println("$patronName	speaks	with	$tavernMaster	about	their	order.")
val	(type,	name,	price)	=	menuData.split(',')
val	message	=	"Madrigal	buys	a	$name	($type)	for	$price."
val	message	=	"$patronName	buys	a	$name	($type)	for	$price."
println(message)
//		performPurchase(price.toDouble())
performPurchase(price.toDouble())
val	phrase	=	if	(name	==	"Dragon's	Breath")	{
"Madrigal	exclaims:	${toDragonSpeak("Ah,	delicious	$name!")}"
"$patronName	exclaims:	${toDragonSpeak("Ah,	delicious	$name!")}"
}	else	{
"Madrigal	says:	Thanks	for	the	$name."
"$patronName	says:	Thanks	for	the	$name."
## }
println(phrase)
## }

Run	Tavern.kt	and	watch	the	tavern	spring	to	life	as	the	three	patrons
excitedly	place	their	orders	for	Dragon’s	Breath:
The	tavern	master	says:	Eli's	in	the	back	playing	cards.
The	tavern	master	says:	Yea,	they're	seated	by	the	stew	kettle.
Good	evening,	Eli	-	you're	#1	in	line.
Eli	speaks	with	Taernyl	about	their	order.
Eli	buys	a	Dragon's	Breath	(shandy)	for	5.91.
Eli	exclaims:	Ah,	d3l1c10|_|s	Dr4g0n's	Br34th!
Good	evening,	Mordoc	-	you're	#2	in	line.
Mordoc	speaks	with	Taernyl	about	their	order.
Mordoc	buys	a	Dragon's	Breath	(shandy)	for	5.91.
Mordoc	exclaims:	Ah,	d3l1c10|_|s	Dr4g0n's	Br34th!
Good	evening,	Sophie	-	you're	#3	in	line.
Sophie	speaks	with	Taernyl	about	their	order.
Sophie	buys	a	Dragon's	Breath	(shandy)	for	5.91.
Sophie	exclaims:	Ah,	d3l1c10|_|s	Dr4g0n's	Br34th!
Iterable	collections	support	a	variety	of	functions	that	let	you	define	an
action	to	perform	for	each	item	in	the	collection.	You	will	learn	more	about
Iterables	and	the	other	iteration	functions	in	Chapter	19.

Reading	a	File	into	a	List
Variety	is	the	spice	of	life	–	and	the	tavern	master	knows	that	patrons	expect	a
variety	of	items	on	the	menu.	Currently,	Dragon’s	Breath	is	the	only	item	for
sale.	Time	to	fix	that	by	loading	up	some	menu	items	for	patrons	to	choose	from.
To	save	you	some	typing,	we	have	provided	you	with	predefined	menu	data	in	a
text	file	you	can	load	into	NyetHack.	The	file	contains	several	menu	items	in	the
same	format	as	your	current	Dragon’s	Breath	menu	data.
Start	by	creating	a	new	folder	for	data:	Right-click	the	NyetHack	project	in	the
project	tool	window	and	choose	New	→	Directory	(Figure	10.1).	Name	the
directory	data.
Figure	10.1		Creating	a	new	directory
Next,	download	the	menu	data	from	bignerdranch.com/solutions/
tavern-menu-data.txt	and	save	it	to	the	data	folder	you	created	in	a	file
called	tavern-menu-items.txt.
Now	you	can	update	Tavern.kt	to	read	the	text	from	that	file	into	a	string	and
call	split	on	the	resulting	string.	Make	sure	to	include	the	java.io.File
statement	at	the	very	top	of	Tavern.kt.

Listing	10.17		Reading	menu	data	from	a	file	(Tavern.kt)
import	java.io.File
## ...
val	patronList	=	mutableListOf("Eli",	"Mordoc",	"Sophie")
val	menuList	=	File("data/tavern-menu-items.txt")
.readText()
## .split("\n")
## ...
You	used	the	java.io.File	type	to	work	with	a	particular	file	by	providing	a
file	path.
The	readText	function	on	File	returns	the	contents	of	the	file	as	a	String.
Then	you	use	the	split	function	(as	you	did	in	Chapter	7)	to	return	a	list,
splitting	on	the	newline	character	(represented	by	the	escape	sequence	'\n').
Now,	call	forEachIndexed	on	menuList	to	print	out	each	entry	in	the
List	along	with	its	index.
Listing	10.18		Printing	the	diversified	menu	(Tavern.kt)
## ...
fun	main(args:	Array<String>)	{
## ...
patronList.forEachIndexed	{	index,	patron	->
println("Good	evening,	$patron	-	you're	#${index	+	1}	in	line.")
placeOrder(patron,	"shandy,Dragon's	Breath,5.91")
## }
menuList.forEachIndexed	{	index,	data	->
println("$index	:	$data")
## }
## }
## ...
Run	Tavern.kt.	You	will	see	the	menu	data	that	was	loaded	into	the	List:
## ...
0	:	shandy,Dragon's	Breath,5.91
1	:	elixir,Shirley's	Temple,4.12
2	:	meal,goblet	of	LaCroix,1.22
3	:	desert	dessert,pickled	camel	hump,7.33
4	:	elixir,iced	boilermaker,11.22
Now	that	the	menuList	is	loaded,	have	each	patron	choose	randomly	from	the
menu	when	placing	their	order:
Listing	10.19		Placing	random	orders	(Tavern.kt)
## ...
fun	main(args:	Array<String>)	{
## ...
patronList.forEachIndexed	{	index,	patron	->
println("Good	evening,	$patron	-	you're	#${index	+	1}	in	line.")
placeOrder(patron,	"shandy,Dragon's	Breath,5.91")
placeOrder(patron,	menuList.shuffled().first())
## }
menuList.forEachIndexed	{	index,	data	->
println("$index	:	$data")
## }
## }
## ...
Run	Tavern.kt.	You	will	see	each	patron	place	an	order	for	a	random	item	on

the	menu.

## Destructuring
A	list	also	offers	the	ability	to	destructure	up	to	the	first	five	elements	it	contains.
Destructuring,	as	you	saw	in	Chapter	7,	allows	you	to	declare	and	assign
multiple	variables	in	a	single	expression.	You	are	using	this	destructuring
declaration	to	separate	the	elements	of	the	menu	data:
val	(type,	name,	price)	=	menuData.split(',')
This	declaration	assigns	the	first	three	elements	in	the	list	returned	by	the
split	function	to	string	values	named	type,	name,	and	price.
By	the	way,	you	can	also	selectively	destructure	elements	from	a	list	by	using	the
symbol	_	to	skip	unwanted	elements.	Say,	for	example,	that	the	tavern	master
would	like	to	hand	out	medals	to	the	best	sword	jugglers	in	the	realm	but	has
misplaced	the	silver	medal.	If	you	wanted	to	destructure	only	the	first	and	third
value	in	the	result	from	splitting	the	patron	list,	you	could	do	so	with:
val	(goldMedal,	_,	bronzeMedal)	=	patronList

## Sets
Lists,	as	you	have	seen,	allow	duplicate	elements	(and	are	ordered,	so	duplicates
–	and	other	elements	–	can	be	identified	by	their	position).	But	sometimes	you
want	a	collection	that	guarantees	that	its	items	are	unique.	For	that,	you	use	a
## Set.
Sets	are	like	Lists	in	many	ways.	They	use	the	same	iteration	functions,	and
Set	also	comes	in	read-only	and	mutable	flavors.
But	there	are	two	major	differences	between	lists	and	sets:	The	elements	of	a	set
are	unique,	and	a	set	does	not	provide	index-based	mutators,	because	the	items
in	a	set	are	not	guaranteed	to	be	in	any	particular	order.	(That	said,	you	can	still
read	an	element	at	a	particular	index,	which	we	will	discuss	shortly.)
Creating	a	set
Just	as	you	can	create	a	list	using	the	listOf	function,	you	can	create	a	Set
using	the	setOf	function.	Try	creating	a	set	in	the	REPL:
Listing	10.20		Creating	a	set	(REPL)
val	planets	=	setOf("Mercury",	"Venus",	"Earth")
planets
["Mercury",	"Venus",	"Earth"]
If	you	try	to	create	the	planets	set	with	a	duplicate,	only	one	of	the	duplicate
items	will	remain	in	the	set:
Listing	10.21		Trying	to	create	a	set	with	a	duplicate	(REPL)
val	planets	=	setOf("Mercury",	"Venus",	"Earth",	"Earth")
planets
["Mercury",	"Venus",	"Earth"]
The	duplicate	element	"Earth"	was	dropped	from	the	set.
As	with	a	List,	you	can	check	whether	a	set	contains	a	particular	element	using
contains	and	containsAll.	Try	the	contains	function	in	the	REPL:
Listing	10.22		Checking	planets	(REPL)
planets.contains("Earth")
true
planets.contains("Pluto")
false

Set	does	not	index	its	contents	–	meaning	it	provides	no	built-in	[]	operator	to
access	elements	using	an	index.	However,	you	can	still	request	an	element	at	a
particular	index,	using	functions	that	use	iteration	to	accomplish	the	task.	Enter
the	following	into	the	REPL	to	read	the	third	planet	in	the	set	with	the
elementAt	function:
Listing	10.23		Finding	the	third	planet	(REPL)
val	planets	=	setOf("Mercury",	"Venus",	"Earth")
planets.elementAt(2)
## Earth
While	this	works,	using	index-based	access	with	a	set	is	an	order	of	magnitude
slower	than	index-based	access	with	a	list,	because	of	the	way	elementAt
works	under	the	hood.	When	you	call	the	elementAt	function	on	the	set,	the
set	iterates	to	the	index	you	provide,	one	element	at	a	time.	This	means	that	for	a
large	set,	requesting	an	element	at	a	high	index	would	be	slower	than	accessing
an	element	by	index	in	a	list.	For	this	reason,	if	you	want	index-based	access,
you	probably	want	a	List,	not	a	Set.
Also,	while	Set	does	have	a	mutable	version	(which	you	will	soon	see),	no
mutator	functions	are	available	that	rely	on	indices	(like	List’s	add(index,
element)	function).
Having	said	that,	Set	does	provide	the	very	useful	feature	of	eliminating
duplicate	elements.	So	what	is	a	programmer	who	wants	unique	elements	and
high-performance,	index-based	access	to	do?	Use	both:	Create	a	Set	to
eliminate	duplicates	and	convert	it	a	to	a	List	when	index-based	access	or
mutator	functions	are	needed.
This	is	exactly	what	you	will	do	to	develop	a	more	elaborate	patron	name	list	for
your	tavern	simulation.
Adding	elements	to	a	set
To	add	some	diversity	to	the	tavern,	you	will	randomly	generate	patron	names,
using	lists	of	first	and	last	names.	Update	Tavern.kt	with	a	list	of	last	names
and	use	forEach	to	generate	10	random	combinations	of	first	names	(from
patronList)	and	last	names.	(Recall	that	ranges	are	iterable.)
Remove	the	two	calls	to	forEachIndexed	that	created	patron	greetings	and
menu	orders.	You	will	be	iterating	over	a	list	of	unique	patrons	soon	instead.
Listing	10.24		Generating	10	random	patrons	(Tavern.kt)

## ...
val	patronList	=	mutableListOf("Eli",	"Mordoc",	"Sophie")
val	lastName	=	listOf("Ironfoot",	"Fernsworth",	"Baggins")
val	menuList	=	File("data/tavern-menu-items.txt")
.readText()
## .split("\n")
fun	main(args:	Array<String>)	{
## ...
patronList.forEachIndexed	{	index,	patron	->
println("Good	evening,	$patron	-	you're	#${index	+	1}	in	line.")
placeOrder(patron,	menuList.shuffled().first())
## }
menuList.forEachIndexed	{	index,	data	->
println("$index	:	$data")
## }
(0..9).forEach	{
val	first	=	patronList.shuffled().first()
val	last	=	lastName.shuffled().first()
val	name	=	"$first	$last"
println(name)
## }
## }
## ...
Run	Tavern.kt.	You	will	see	10	random	patron	names	in	the	output.	They
will	not	necessarily	match	the	ones	below,	but	they	will	be	similar	–	and	you
should	see	some	duplicate	first	and	last	name	combinations:
## ...
## Eli	Baggins
## Eli	Baggins
## Eli	Baggins
## Eli	Ironfoot
## Sophie	Baggins
## Sophie	Fernsworth
## Sophie	Baggins
## Eli	Ironfoot
## Eli	Ironfoot
## Sophie	Fernsworth
Your	tavern	simulation	requires	unique	patron	names,	because	soon	you	will
associate	gold	balances	with	each	patron’s	unique	name	in	the	tavern	ledger.	A
duplicate	patron	name	could	lead	to	a	case	of	mistaken	identity.
To	remove	the	duplicate	names	from	your	list,	you	will	add	each	name	to	a	set.
Any	duplicate	elements	will	be	dropped,	and	you	will	be	left	with	only	the
unique	elements.
Define	an	empty	mutable	set	and	add	the	randomly	generated	patron	names	to	it:
Listing	10.25		Ensuring	uniqueness	using	a	set	(Tavern.kt)
## ...
val	lastName	=	listOf("Ironfoot",	"Fernsworth",	"Baggins")
val	uniquePatrons	=	mutableSetOf<String>()
val	menuList	=	File("data/tavern-menu-items.txt")
.readText()
## .split("\n")
fun	main(args:	Array<String>)	{
## ...
(0..9).forEach	{
val	first	=	patronList.shuffled().first()
val	last	=	lastName.shuffled().first()
val	name	=	"$first	$last"
println(name)
uniquePatrons	+=	name
## }

println(uniquePatrons)
## }
## ...
Note	that	you	cannot	rely	on	type	inference	for	uniquePatrons,	because	you
declare	it	as	an	empty	set.	You	must	specify	the	type	of	elements	it	can	hold:
mutableSetOf<String>.	Then,	you	use	the	+=	operator	to	add	name	to
uniquePatrons,	iterating	10	times.
Run	Tavern.kt	again.	You	will	see	that	only	unique	values	are	held	in	the	set,
and	consequently	you	will	have	fewer	than	10	patron	names.
## ...
[Eli	Fernsworth,	Eli	Ironfoot,	Sophie	Baggins,	Mordoc	Baggins,	Sophie	Fernsworth]
While	MutableSet	supports	adding	and	removing	elements,	like
MutableList,	it	does	not	provide	index-based	mutator	functions.	Table	10.2
shows	some	of	the	most	commonly	used	MutableSet	mutator	functions.
Table	10.2		Mutable	set	mutator	functions
FunctionDescriptionExample(s)
add
Adds	the	value
to	the	set.
mutableSetOf(1,2).add(3)
## [1,2,3]
addAll
Adds	all	elements
from	another	collection
to	the	set.
mutableSetOf(1,2).addAll(listOf(1,5,6))
## [1,2,5,6]
## +=
(plus	assign	operator)
Adds	the	value(s)
to	the	set.
mutableSetOf(1,2)	+=	3
## [1,2,3]
## -=
(minus	assign	operator)
Removes	the	value(s)
from	the	set.
mutableSetOf(1,2,3)	-=	3
## [1,2]
mutableSetOf(1,2,3)	-=	listOf(2,3)
## [1]
remove
Removes	the	element
from	the	set.
mutableSetOf(1,2,3).remove(1)
## [2,3]
removeAll
Removes	all	elements
in	another	collection
from	the	set.
mutableSetOf(1,2).removeAll(listOf(1,5,6))
## [2]
clear
Removes	all	elements
from	the	set.
mutableSetOf(1,2).clear()
## []

while	Loops
Now	that	you	have	a	unique	list	of	patrons,	you	will	have	them	randomly	place
their	orders	from	the	menu.	In	this	section,	however,	you	will	use	a	different
control	flow	mechanism	for	looping	through	a	collection:	a	while	loop.
for	loops	are	a	useful	form	of	control	flow	when	you	want	to	run	some	code	for
each	element	in	series.	But	they	are	not	as	good	at	representing	state	that	cannot
be	iterated	through.	That	is	where	while	loops	are	useful.
A	while	loop’s	logic	is,	“While	some	condition	is	true,	execute	the	code	in	this
block.”	You	are	going	to	generate	exactly	10	orders	by	using	a	var	to	keep	track
of	how	many	orders	have	been	generated	and	a	while	loop	to	continue
generating	orders	until	10	have	been	placed.
Update	Tavern.kt	to	iterate	through	the	set	and	have	a	total	of	10	orders
placed	using	a	while	loop:
Listing	10.26		Unique	patrons	placing	random	orders	(Tavern.kt)
## ...
fun	main(args:	Array<String>)	{
## ...
println(uniquePatrons)
var	orderCount	=	0
while	(orderCount	<=	9)	{
placeOrder(uniquePatrons.shuffled().first(),
menuList.shuffled().first())
orderCount++
## }
## }
## ...
The	increment	operator	(++)	adds	1	to	the	value	of	orderCount	during	each
iteration.
Run	Tavern.kt.	This	time,	you	will	see	10	random	orders	placed	by	the
patrons	you	generated,	along	the	lines	of:
Sophie	Ironfoot	speaks	with	Taernyl	about	their	order.
Sophie	Ironfoot	buys	a	Dragon's	Breath	(shandy)	for	5.91.
Sophie	Ironfoot	exclaims:	Ah,	d3l1c10|_|s	Dr4g0n's	Br34th!
Mordoc	Fernsworth	speaks	with	Taernyl	about	their	order.
Mordoc	Fernsworth	buys	a	Dragon's	Breath	(shandy)	for	5.91.
Mordoc	Fernsworth	exclaims:	Ah,	d3l1c10|_|s	Dr4g0n's	Br34th!
Eli	Baggins	speaks	with	Taernyl	about	their	order.
Eli	Baggins	buys	a	pickled	camel	hump	(desert	dessert)	for	7.33.
Eli	Baggins	says:	Thanks	for	the	pickled	camel	hump.
## ...
A	while	loop	requires	you	to	maintain	your	own	counter	to	manage	its	state.	You
start	with	an	orderCount	value	of	0	and	increment	each	time	that	you	loop.
while	loops	are	more	flexible	than	for	loops	in	that	they	can	represent	state	that

is	not	purely	based	on	iteration.	Here,	you	are	doing	so	by	incrementing	the
orderCount	counter.
You	can	represent	more	complex	state	by	combining	while	loops	with	other
forms	of	control	flow,	like	the	conditionals	you	saw	in	Chapter	3.	Consider	this
Boolean	example:
var	isTavernOpen	=	true
val	isClosingTime	=	false
while	(isTavernOpen	==	true)	{
if	(isClosingTime)	{
isTavernOpen	=	false
## }
println("Having	a	grand	old	time!")
## }
In	this	example,	the	while	loop	continues	to	loop	as	long	as	isTavernOpen	is
true,	keeping	track	of	state	represented	by	a	Boolean.	This	is	very	powerful	–
but	can	also	be	dangerous.	Consider	what	would	happen	if	isTavernOpen	was
never	false.	This	while	loop	would	loop	forever,	and	the	program	would	“hang,”
or	continue	to	execute	indefinitely.	Take	care	when	using	while	loops	for	this
reason.

The	break	Expression
One	way	to	exit	a	while	loop	is	by	changing	the	state	it	depends	on.	Another
way	to	break	out	of	a	loop	is	the	break	expression.	Consider	the	above	example
in	which	a	while	loop	runs	while	isTavernOpen	is	true.	Instead	of	changing
isTavernOpen’s	value	to	false	to	end	the	loop,	a	break	expression	would	halt
the	loop	immediately:
var	isTavernOpen	=	true
val	isClosingTime	=	false
while	(isTavernOpen	==	true)	{
if	(isClosingTime)	{
break
## }
println("Having	a	grand	old	time!")
## }
Without	break,	"Having	a	grand	old	time!"	would	print	one	more	time	after
the	value	of	isClosingTime	changes.	With	break,	the	grand	old	times	are
interrupted	as	execution	breaks	out	of	the	loop	immediately.
Note	that	break	does	not	stop	execution	of	your	program	entirely.	Rather,	it
simply	breaks	out	of	the	loop	from	which	it	is	called,	and	program	execution
continues.	break	can	be	used	to	jump	out	of	any	loop	or	conditional,	which	can
be	quite	useful.

## Collection	Conversion
In	NyetHack,	you	create	a	mutable	set	of	unique	patron	names	by	feeding	the
elements	from	a	list	into	it,	one	by	one.	You	can	also	convert	a	list	to	a	set,	or
vice	versa,	using	the	toSet	and	toList	functions	(or	their	mutable	cousins:
toMutableSet	and	toMutableList).	A	common	trick	is	to	call	toSet	to
drop	the	non-unique	elements	in	a	list.	(Try	these	experiments	in	the	REPL.)
Listing	10.27		Converting	a	list	to	a	set	(REPL)
listOf("Eli	Baggins",	"Eli	Baggins",	"Eli	Ironfoot").toSet()
[Eli	Baggins,	Eli	Ironfoot]
If	you	want	quick	index-based	access	after	converting	a	list	to	a	set	to	remove
duplicates,	you	can	convert	the	set	back	to	a	list:
Listing	10.28		Converting	a	set	back	to	a	list	(REPL)
val	patrons	=	listOf("Eli	Baggins",	"Eli	Baggins",	"Eli	Ironfoot")
.toSet()
.toList()
[Eli	Baggins,	Eli	Ironfoot]
patrons[0]
## Eli	Baggins
The	need	to	remove	duplicates	and	resume	index-based	access	is	so	common
that	Kotlin	provides	a	function	on	List	called	distinct	that	calls	toSet
and	toList	internally:
Listing	10.29		Calling	distinct	(REPL)
val	patrons	=	listOf("Eli	Baggins",	"Eli	Baggins",	"Eli	Ironfoot").distinct()
[Eli	Baggins,	Eli	Ironfoot]
patrons[0]
## Eli	Baggins
Sets	are	useful	for	representing	series	of	data	where	each	element	is	unique.	In
the	next	chapter,	you	will	complete	your	tour	of	the	Kotlin	collection	types	by
learning	about	maps	as	you	finish	the	tavern	simulation.

For	the	More	Curious:	Array	Types
If	you	have	worked	with	Java,	you	know	that	it	supports	primitive	definitions	of
arrays	–	different	from	the	reference	types	like	List	and	Set	that	you	worked
with	in	this	chapter.	Kotlin	also	includes	a	number	of	reference	types,	called
Arrays,	that	compile	down	to	Java	primitive	arrays.	Arrays	are	included
primarily	to	support	interoperability	between	Kotlin	and	Java.
Suppose	you	had	a	Java	method	that	you	wanted	to	call	from	Kotlin	that	looked
like	this:
static	void	displayPlayerAges(int[]	playerAges)	{
for(int	i	=	0;	i	<	ages.length;	i++)	{
System.out.println("age:	"	+	ages[i]);
## }
## }
Notice	that	the	parameter	expected	by	displayPlayerAges	is	int[]
playerAges,	a	Java	primitive	array	of	int	primitives.	To	call	the	Java
displayPlayerAges	method	from	Kotlin,	you	would	write	the	following:
val	playerAges:	IntArray	=	intArrayOf(34,	27,	14,	52,	101)
displayPlayerAges(playerAges)
Notice	the	IntArray	type	and	the	intArrayOf	function	that	was	called.
Like	a	List,	an	IntArray	represents	a	series	of	elements	–	specifically
integers.	Unlike	a	List,	an	IntArray	is	backed	by	a	primitive	type	when
compiled	to	bytecode.	When	the	Kotlin	code	is	compiled,	the	bytecode	that	is
generated	will	exactly	match	the	expected	primitive	int	array	required	for	the
Java	displayPlayerAges	method	to	be	invoked.
It	is	also	possible	to	convert	a	Kotlin	collection	to	the	required	Java	primitive
array	type	using	built-in	conversion	functions.	For	example,	you	could	convert	a
list	of	integers	to	an	IntArray	using	the	toIntArray	function	provided	by
List.	This	would	allow	you	to	convert	a	collection	to	an	int	array	only	at	the
point	that	you	need	to	provide	a	primitive	array	to	a	Java	function:
val	playerAges:	List<Int>	=	listOf(34,	27,	14,	52,	101)
displayPlayerAges(playerAges.toIntArray())
Table	10.3	shows	the	array	types	and	the	functions	that	create	them.
Table	10.3		Array	types
Array	typeCreation	function
IntArrayintArrayOf

DoubleArraydoubleArrayOf
LongArraylongArrayOf
ShortArrayshortArrayOf
ByteArraybyteArrayOf
FloatArrayfloatArrayOf
BooleanArraybooleanArrayOf
## Array

a
arrayOf
a
Array	compiles	to	a	primitive	array	that	holds	any	reference	type.
As	a	general	rule,	stick	with	the	collection	types	like	List	unless	you	have	a
compelling	reason	to	do	otherwise	–	like	the	need	to	interoperate	with	Java	code.
A	Kotlin	collection	is	a	better	choice	in	most	cases	because	collections	provide
the	concept	of	“read-only-ness”	versus	“mutability”	and	support	a	more	robust
set	of	features.

For	the	More	Curious:	Read-Only	vs	Immutable
Throughout	this	book,	we	have	favored	the	terms	"read-only"	over	"immutable,"
with	few	exceptions	–	but	we	have	not	explained	why.	Now	is	the	time.
“Immutable”	means	“unchangeable,”	and	we	think	it	is	a	misleading	label	for
Kotlin	collections	(and	certain	other	types)	because	they	can,	indeed,	change.
Let’s	look	at	some	examples	using	lists.
Here	are	declarations	of	two	Lists.	They	are	read-only	–	declared	with	val.
The	element	each	one	happens	to	contain	is	a	mutable	list.
val	x	=	listOf(mutableListOf(1,2,3))
val	y	=	listOf(mutableListOf(1,2,3))
x	==	y
true
So	far,	so	good.	It	appears	that	x	and	y	were	assigned	with	the	same	value,	and
the	List	API	does	not	expose	any	functions	for	adding,	removing,	or
reassigning	a	particular	element.
However,	the	lists	contain	mutable	lists,	and	their	contents	can	be	modified:
val	x	=	listOf(mutableListOf(1,2,3))
val	y	=	listOf(mutableListOf(1,2,3))
x[0].add(4)
x	==	y
false
The	structural	comparison	between	x	and	y	now	evaluates	as	false,	because	the
contents	of	x	mutated.	Should	an	immutable	(“unchangeable”)	list	behave	this
way?	In	our	opinion,	it	should	not.
Here	is	another	example:
var	myList:	List<Int>	=	listOf(1,2,3)
(myList	as	MutableList)[2]	=	1000
myList
## [1,	2,	1000]
In	this	example,	myList	was	cast	to	the	MutableList	type	–	meaning	that
the	compiler	was	instructed	to	treat	myList	as	a	mutable	list,	despite	the	fact
that	it	was	created	with	listOf.	(You	will	read	about	casting	in	depth	in
Chapter	14	and	Chapter	16.)	This	cast	has	the	effect	of	allowing	a	change	to	the
value	of	the	third	item	in	myList.	Again,	not	the	behavior	we	expect	of
something	labeled	“unchangeable.”
A	List	in	Kotlin	does	not	enforce	immutability	–	it	is	up	to	you	to	use	it	in	an
immutable	fashion.	A	Kotlin	List’s	“immutability”	is	only	skin	deep	–	and

whatever	you	wind	up	calling	it,	remember	that.

## Challenge:	Formatted	Tavern	Menu
First	impressions	go	a	long	way,	and	one	of	the	first	things	a	patron	will	see	is
the	tavern	menu.	For	this	challenge,	generate	a	more	elegant	version	of	the	menu
to	kick	it	up	a	notch.	Show	the	item	names	capitalized	and	uniformly	aligned.
Include	the	prices,	aligned	by	their	decimal	points.	Format	the	whole	menu	in	a
pleasing	block.
The	output	should	resemble	the	following:
***	Welcome	to	Taernyl's	Folly	***
## Dragon's	Breath...............5.91
## Shirley's	Temple..............4.12
Goblet	of	LaCroix.............1.22
## Pickled	Camel	Hump............7.33
## Iced	Boilermaker.............11.22
Hint:	You	will	need	to	calculate	the	amount	of	padding	for	each	line	by	using	the
longest	string	from	the	list	of	menu	items.

## Challenge:	Advanced	Formatted	Tavern	Menu
Building	on	the	previous	menu	formatting	code,	generate	a	menu	that
additionally	groups	the	elements	to	be	listed	by	their	type.	The	output	should
resemble	the	following:
***	Welcome	to	Taernyl's	Folly	***
## ~[shandy]~
## Dragon's	Breath...............5.91
## ~[elixir]~
## Iced	Boilermaker.............11.22
## Shirley's	Temple..............4.12
## ~[meal]~
Goblet	of	LaCroix.............1.22
~[desert	dessert]~
## Pickled	Camel	Hump............7.33