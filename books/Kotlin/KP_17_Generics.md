

## 17
## Generics
You	learned	in	Chapter	10	that	a	list	can	hold	any	type:	integers,	strings,	or	even
new	types	that	you	have	defined:
val	listOfInts:	List<Int>	=	listOf(1,2,3)
val	listOfStrings:	List<String>	=	listOf("string	one",	"string	two")
val	listOfRooms:	List<Room>	=	listOf(Room(),	TownSquare())
Lists	can	hold	any	type	because	of	generics,	a	type	system	feature	that	allows
both	functions	and	types	to	work	with	types	that	are	not	yet	known	to	you	or	the
compiler.	Generics	greatly	expand	the	reusability	of	your	class	definitions,
because	they	allow	your	definitions	to	work	with	many	types.
In	this	chapter,	you	will	learn	how	to	create	your	own	generic	classes	and
functions	that	work	with	generic	type	parameters.	You	will	be	working	in	your
Sandbox	project	to	model	a	generic	LootBox	class	that	can	hold	virtual
rewards	of	any	kind	you	can	imagine.

## Defining	Generic	Types
A	generic	type	is	a	class	that	accepts	an	input	of	any	type	in	its	constructor.	You
are	going	to	begin	by	defining	your	own	generic	type.
Open	the	Sandbox	project	and	add	a	new	file	called	Generics.kt.	Within
Generics.kt,	define	a	LootBox	class	that	specifies	a	generic	type
parameter	for	use	with	its	contents	and	contains	a	private	property	called	loot
that	is	assigned	the	item.
Listing	17.1		Creating	a	generic	type	(Generics.kt)
class	LootBox<T>(item:	T)	{
private	var	loot:	T	=	item
## }
You	define	the	LootBox	class	and	make	it	generic	by	specifying	a	generic	type
parameter	for	use	with	the	class,	written	as	T	and	specified	within	diamond
braces	(<	>)	like	other	type	parameters.	The	generic	type	parameter,	T,	is	a
placeholder	for	the	item’s	type.
The	LootBox	class	accepts	an	item	of	any	type	as	a	primary	constructor	value
(item:	T)	and	assigns	the	value	to	the	private	property	loot,	also	of	type	T.
Note	that	the	generic	type	parameter	is	usually	represented	with	a	single	letter	T,
short	for	“type,”	though	any	letter	or	word	can	be	used.	We	suggest	you
generally	stick	with	T,	since	it	is	what	is	commonly	used	in	other	languages	that
support	generics	and	is	therefore	the	most	readable	choice.
Time	to	put	the	new	LootBox	class	to	the	test.	Add	a	main	function,	define	a
couple	kinds	of	loot,	and	place	an	instance	of	each	new	item	in	its	very	own	loot
box.
Listing	17.2		Defining	loot	boxes	(Generics.kt)
class	LootBox<T>(item:	T)	{
private	var	loot:	T	=	item
## }
class	Fedora(val	name:	String,	val	value:	Int)
class	Coin(val	value:	Int)
fun	main(args:	Array<String>)	{
val	lootBoxOne:	LootBox<Fedora>	=	LootBox(Fedora("a	generic-looking	fedora",	15))
val	lootBoxTwo:	LootBox<Coin>	=	LootBox(Coin(15))
## }
You	have	created	two	different	kinds	of	loot	(fedoras	and	coins,	both	highly
desirable	virtual	rewards)	and	two	different	kinds	of	loot	boxes	to	hold	them.

Since	you	made	the	LootBox	class	generic,	you	are	able	to	use	just	one	class
definition	to	support	different	kinds	of	loot	boxes:	ones	that	hold	fedoras,	ones
that	hold	coins,	and	so	on.
Notice	the	type	signature	for	each	LootBox	variable:
val	lootBoxOne:	LootBox<Fedora>	=	LootBox(Fedora("a	generic-looking	fedora",	15))
val	lootBoxTwo:	LootBox<Coin>	=	LootBox(Coin(15))
The	diamond	braces	on	the	type	signature	for	the	variable	show	what	type	of	loot
a	particular	LootBox	instance	is	capable	of	holding.
Generic	types,	like	other	types	in	Kotlin,	support	type	inference.	We	have
included	the	type	explicitly	for	illustration,	but	it	could	have	been	omitted	since
each	variable	is	initialized	with	a	value.	In	your	own	code,	you	typically	will
omit	the	type	information	when	it	is	not	needed;	feel	free	to	delete	it	here,	if	you
prefer.

## Generic	Functions
Generic	type	parameters	also	work	with	functions.	That	is	good	news,	since
there	is	currently	no	way	for	a	player	to	retrieve	loot	from	a	loot	box.
Time	to	fix	that.	Add	a	function	that	lets	a	player	fetch	the	item	if	and	only	if	the
box	is	open.	Track	whether	the	box	is	open	by	adding	an	open	property.
Listing	17.3		Adding	a	fetch	function	(Generics.kt)
class	LootBox<T>(item:	T)	{
var	open	=	false
private	var	loot:	T	=	item
fun	fetch():	T?	{
return	loot.takeIf	{	open	}
## }
## }
Here	you	define	a	generic	function,	fetch,	that	returns	T	–	the	generic	type
parameter	specified	on	the	LootBox	class,	which	is	a	placeholder	for	the	type
of	item.
Note	that	if	fetch	were	defined	outside	of	LootBox,	type	T	would	not	be
available,	since	T	is	tied	to	the	LootBox	class	definition.	However,	a	function
does	not	require	a	class	to	use	a	generic	type	parameter,	as	you	will	see	in	the
next	section.
Try	fetching	the	contents	of	lootBoxOne	in	the	main	function	using	the	new
fetch	function,	first	with	the	box	closed:
Listing	17.4		Testing	the	generic	fetch	function	(Generics.kt)
## ...
fun	main(args:	Array<String>)	{
val	lootBoxOne:	LootBox<Fedora>	=	LootBox(Fedora("a	generic-looking	fedora",	15))
val	lootBoxTwo:	LootBox<Coin>	=	LootBox(Coin(15))
lootBoxOne.fetch()?.run	{
println("You	retrieve	$name	from	the	box!")
## }
## }
You	use	the	standard	function	run	(which	you	learned	about	in	Chapter	9)	to
print	the	name	of	lootBoxOne’s	contents,	if	it	is	non-null.
Recall	that	run	scopes	everything	within	the	lambda	it	accepts	to	the	receiver
instance	it	is	called	on	–	so	$name	accesses	Fedora’s	name	property.
Run	Generics.kt.	There	will	be	no	output.	You	could	not	take	the	loot,
because	the	loot	box	was	closed.	Now,	open	the	loot	box	and	run

Generics.kt	again.
Listing	17.5		Opening	the	box	(Generics.kt)
## ...
fun	main(args:	Array<String>)	{
val	lootBoxOne:	LootBox<Fedora>	=	LootBox(Fedora("a	generic-looking	fedora",	15))
val	lootBoxTwo:	LootBox<Coin>	=	LootBox(Coin(15))
lootBoxOne.open	=	true
lootBoxOne.fetch()?.run	{
println("You	retrieve	a	$name	from	the	box!")
## }
## }
This	time,	when	you	run	Generics.kt	you	will	see	the	name	of	the	loot
found:
You	retrieve	a	generic-looking	fedora	from	the	box!

## Multiple	Generic	Type	Parameters
A	generic	function	or	type	can	also	support	multiple	generic	type	parameters.
Suppose	you	want	a	second	fetch	function	that	accepts	a	loot-modification
function,	allowing	you	to	convert	the	loot	to	some	other	new	type,	perhaps	a
coin,	when	you	fetch	it.	The	value	of	the	coin	returned	depends	on	the	value	of
the	original	loot	–	and	a	lootModFunction	higher-order	function	that	is
passed	to	fetch	will	determine	that.
Add	a	new	fetch	function	to	LootBox	that	accepts	a	loot-modification
function.
Listing	17.6		Using	multiple	generic	type	parameters	(Generics.kt)
class	LootBox<T>(item:	T)	{
var	open	=	false
private	var	loot:	T	=	item
fun	fetch():	T?	{
return	loot.takeIf	{	open	}
## }
fun	<R>	fetch(lootModFunction:	(T)	->	R):	R?	{
return	lootModFunction(loot).takeIf	{	open	}
## }
## }
## ...
Here,	you	add	a	new	generic	type	parameter	to	the	function,	R,	short	for
“return,”	since	the	generic	type	parameter	will	be	used	for	fetch’s	return	type.
You	place	the	generic	type	parameter	in	diamond	braces	directly	before	the
function	name:	fun	<R>	fetch.	fetch	returns	a	value	of	type	R?,	a	nullable
version	of	R.
You	also	specify	that	the	lootModFunction	(via	its	function	type
declaration,	(T)	->	R)	accepts	an	argument	of	type	T	and	returns	a	result	of
type	R.	Try	out	the	new	fetch	function	that	you	defined	–	this	time,	passing	a
loot-modification	function	as	an	argument.
Listing	17.7		Passing	the	loot-modification	function	as	an	argument
(Generics.kt)
## ...
fun	main(args:	Array<String>)	{
val	lootBoxOne:	LootBox<Fedora>	=	LootBox(Fedora("a	generic-looking	fedora",	15))
val	lootBoxTwo:	LootBox<Coin>	=	LootBox(Coin(15))
lootBoxOne.open	=	true
lootBoxOne.fetch()?.run	{
println("You	retrieve	$name	from	the	box!")
## }

val	coin	=	lootBoxOne.fetch()	{
## Coin(it.value	*	3)
## }
coin?.let	{	println(it.value)	}
## }
The	new	version	of	the	fetch	function	you	defined	returns	the	type	of	the
lambda	you	provide	it,	R.	You	return	a	Coin?	from	the	lambda,	so	the	type	of	R
in	this	case	is	Coin?.	But	the	new	version	of	fetch	is	more	flexible	than
returning	a	coin	every	time	–	whatever	you	return	from	the	lambda,	the	fetch
function	will	return	that	same	type,	since	the	type	of	R	depends	on	what	is
returned	from	the	anonymous	function.
lootBoxOne	holds	an	item	of	type	Fedora.	However,	your	new	fetch
function	returns	a	Coin?,	instead	of	a	Fedora?.	The	new	generic	type
parameter	that	you	added,	R,	makes	this	possible.
The	lootModFunction	you	pass	to	fetch	calculates	a	value	for	the	coin	by
looking	at	the	value	of	the	loot	in	the	box	and	multiplying	it	by	3.
Run	Generics.kt.	This	time	you	will	see	the	name	of	the	loot	found	along
with	the	value	of	the	coin	returned	from	the	loot	box:	the	value	of	the	original
item	(a	fedora)	multiplied	by	3:
You	retrieve	a	generic-looking	fedora	from	the	box!
## 45

## Generic	Constraints
What	if	you	wanted	to	ensure	that	the	loot	box	was	only	used	to	hold	loot,	and
not	something	else?	You	can	specify	a	generic	type	constraint	to	enforce	exactly
that.
Start	by	changing	the	Coin	and	Fedora	classes	to	be	subclasses	of	a	new	top-
level	Loot	class:
Listing	17.8		Adding	a	superclass	(Generics.kt)
class	LootBox<T>(item:	T)	{
var	open	=	false
private	var	loot:	T	=	item
fun	fetch():	T?	{
return	loot.takeIf	{	open	}
## }
fun	<R>	fetch(lootModFunction:	(T)	->	R):	R?	{
return	lootModFunction(loot).takeIf	{	open	}
## }
## }
open	class	Loot(val	value:	Int)
class	Fedora(val	name:	String,	val	value:	Int)	:	Loot(value)
class	Coin(val	value:	Int)	:	Loot(value)
## ...
Now,	add	a	generic	type	constraint	to	LootBox’s	generic	type	parameter	to
allow	only	descendants	of	the	Loot	class	to	be	used	with	LootBox:
Listing	17.9		Constraining	the	generic	parameter	to	Loot	only
(Generics.kt)
class	LootBox<T	:	Loot>(item:	T)	{
## ...
## }
## ...
Here,	you	add	a	constraint	to	the	generic	type	T,	specified	as	:	Loot.	Now,	only
items	that	are	descendants	of	the	Loot	class	can	be	added	to	the	loot	box.
You	might	be	wondering,	“Why	is	T	still	needed	here?	Why	not	just	use	the	type
Loot?”	By	using	T,	LootBox	allows	you	to	access	the	specific	kind	of	Loot
while	allowing	the	contents	to	be	any	kind	of	Loot.	So,	rather	than	the
LootBox	containing	Loot,	the	LootBox	can	contain	a	Fedora	–	and	the
Fedora’s	specific	type	is	tracked	with	T.
If	you	used	Loot	for	the	type,	that	would	constrain	LootBox	to	accept
descendants	of	Loot,	but	it	would	also	discard	the	information	that	a	Fedora

was	in	the	box.	Using	Loot	for	the	type,	for	example,	the	following	would	not
compile:
val	lootBox:	LootBox<Loot>	=	LootBox(Fedora("a	dazzling	fuschia	fedora",	15))
val	fedora:	Fedora	=	lootBox.item	//	Type	mismatch	-	Required:	Fedora,	Found:	Loot
It	would	no	longer	be	possible	to	see	that	the	LootBox	contained	anything
other	than	Loot.	By	using	a	type	constraint,	it	is	possible	to	constrain	the
contents	to	Loot	and	also	preserve	the	specific	subtype	of	the	loot	in	the	box.

vararg	and	get
Your	LootBox	can	now	hold	any	kind	of	Loot,	but	it	cannot	hold	more	than
one	item	at	a	time.	What	if	you	want	to	hold	multiple	items	of	Loot	in	your
LootBox?
To	do	so,	modify	LootBox’s	primary	constructor	with	the	vararg	keyword,
which	allows	a	variable	number	of	arguments	to	be	passed	to	the	constructor.
Listing	17.10		Adding	vararg	(Generics.kt)
class	LootBox<T	:	Loot>(vararg	item:	T)	{
## ...
## }
## ...
Now	that	you	have	added	the	vararg	keyword	to	LootBox,	its	item	variable
will	be	treated	as	an	Array	of	elements	instead	of	a	single	element	when	it	is
initialized,	and	LootBox	can	accept	multiple	items	passed	into	the	constructor.
(Recall	from	Chapter	10	that	Array	is	a	collection	type.)
Update	the	loot	variable	and	the	fetch	function	to	account	for	this	change	by
indexing	into	the	loot	array:
Listing	17.11		Indexing	into	the	loot	array	(Generics.kt)
class	LootBox<T	:	Loot>(vararg	item:	T)	{
var	open	=	false
private	var	loot:	TArray<out	T>	=	item
fun	fetch(item:	Int):	T?	{
return	loot[item].takeIf	{	open	}
## }
fun	<R>	fetch(item:	Int,	lootModFunction:	(T)	->	R):	R?	{
return	lootModFunction(loot[item]).takeIf	{	open	}
## }
## }
## ...
Notice	the	out	keyword	that	you	added	for	the	new	loot	variable’s	type
signature.	The	out	keyword	is	required	here	because	it	is	part	of	the	return	type
for	any	variable	marked	as	a	vararg.	You	will	learn	more	about	this	keyword,
and	its	partner	in,	shortly.
Try	out	the	new	and	improved	LootBox	in	main.	Pass	another	fedora	into	the
loot	box	(get	creative	with	the	second	fedora’s	name,	if	you	like).	Then	fetch	the
two	items	from	lootBoxOne,	one	in	each	call	to	fetch:
Listing	17.12		Testing	the	new	LootBox	(Generics.kt)
## ...

fun	main(args:	Array<String>)	{
val	lootBoxOne:	LootBox<Fedora>	=	LootBox(Fedora("a	generic-looking	fedora",	15),
Fedora("a	dazzling	magenta	fedora",	25))
val	lootBoxTwo:	LootBox<Coin>	=	LootBox(Coin(15))
lootBoxOne.open	=	true
lootBoxOne.fetch(1)?.run	{
println("You	retrieve	$name	from	the	box!")
## }
val	coin	=	lootBoxOne.fetch(0)	{
## Coin(it.value	*	3)
## }
coin?.let	{	println(it.value)	}
## }
Run	Generics.kt	again.	You	will	see	the	name	of	the	second	item	in
lootBoxOne	and	the	value	of	the	first	item	(multiplied	by	3):
You	retrieve	a	dazzling	magenta	fedora	from	the	box!
## 45
Another	way	to	provide	index	access	to	the	loot	array	is	to	have	LootBox
implement	an	operator	function:	the	get	function,	which	enables	the	[]
operator.	(You	saw	operator	overloading	in	Chapter	15.)
Update	LootBox	to	include	a	get	operator	implementation:
Listing	17.13		Adding	a	get	operator	to	LootBox	(Generics.kt)
class	LootBox<T	:	Loot>(vararg	item:	T)	{
var	open	=	false
private	var	loot:	Array<out	T>	=	item
operator	fun	get(index:	Int):	T?	=	loot[index].takeIf	{	open	}
fun	fetch(item:	Int):	T?	{
return	loot[item].takeIf	{	open	}
## }
fun	<R>	fetch(item:	Int,	lootModFunction:	(T)	->	R):	R?	{
return	lootModFunction(loot[item]).takeIf	{	open	}
## }
## }
## ...
Now,	use	the	new	get	operator	in	your	main	function:
Listing	17.14		Using	get	(Generics.kt)
## ...
fun	main(args:	Array<String>)	{
## ...
coin?.let	{	println(it.value)	}
val	fedora	=	lootBoxOne[1]
fedora?.let	{	println(it.name)	}
## }
get	gives	you	a	shorthand	for	fetching	loot	at	a	particular	index.	Run
Generics.kt	again	–	you	will	see	the	name	of	the	second	fedora	in
lootBoxOne	printed	after	the	output	from	before.
You	retrieve	a	dazzling	magenta	fedora	from	the	box!
## 45
a	dazzling	magenta	fedora

in	and	out
To	further	customize	your	generic	type	parameters,	Kotlin	provides	the
keywords	in	and	out.	To	see	how	they	work,	create	a	simple	generic	Barrel
class	in	a	new	file	called	Variance.kt:
Listing	17.15		Defining	Barrel	(Variance.kt)
class	Barrel<T>(var	item:	T)
To	experiment	with	Barrel,	add	a	main	function.	In	main,	define	a	Barrel
to	hold	a	Fedora	and	another	Barrel	to	hold	Loot:
Listing	17.16		Defining	Barrels	in	main	(Variance.kt)
class	Barrel<T>(var	item:	T)
fun	main(args:	Array<String>)	{
var	fedoraBarrel:	Barrel<Fedora>	=	Barrel(Fedora("a	generic-looking	fedora",	15))
var	lootBarrel:	Barrel<Loot>	=	Barrel(Coin(15))
## }
While	a	Barrel<Loot>	can	hold	any	kind	of	loot,	the	particular	instance
defined	here	happens	to	hold	a	Coin	(which,	remember,	is	a	subclass	of	Loot).
Now,	assign	fedoraBarrel	to	lootBarrel:
Listing	17.17		Attempting	to	reassign	lootBarrel	(Variance.kt)
class	Barrel<T>(var	item:	T)
fun	main(args:	Array<String>)	{
var	fedoraBarrel:	Barrel<Fedora>	=	Barrel(Fedora("a	generic-looking	fedora",	15))
var	lootBarrel:	Barrel<Loot>	=	Barrel(Coin(15))
lootBarrel	=	fedoraBarrel
## }
You	may	be	surprised	to	find	that	the	assignment	was	not	allowed	by	the
compiler	(Figure	17.1):
Figure	17.1		Type	mismatch

It	might	seem	like	the	assignment	should	have	been	possible.	Fedora	is,	after
all,	a	descendant	of	Loot,	and	assigning	a	variable	of	the	Loot	type	an	instance
of	Fedora	is	possible:
var	loot:	Loot	=	Fedora("a	generic-looking	fedora",	15)	//	No	errors
To	understand	why	the	assignment	fails,	let’s	walk	through	what	could	happen	if
it	succeeded.
If	the	compiler	allowed	you	to	assign	the	fedoraBarrel	instance	to	the
lootBarrel	variable,	lootBarrel	would	then	point	to	fedoraBarrel,
and	it	would	be	possible	to	interface	with	fedoraBarrel’s	item	as	Loot,
instead	of	Fedora	(because	of	lootBarrel’s	type,	Barrel<Loot>).
For	example,	a	coin	is	valid	Loot,	so	it	would	be	possible	to	assign	a	coin	to
lootBarrel.item	(which	points	to	fedoraBarrel).	Do	so	in
## Variance.kt:
Listing	17.18		Assigning	a	coin	to	lootBarrel.item
(Variance.kt)
class	Barrel<T>(var	item:	T)
fun	main(args:	Array<String>)	{
var	fedoraBarrel:	Barrel<Fedora>	=	Barrel(Fedora("a	generic-looking	fedora",	15))
var	lootBarrel:	Barrel<Loot>	=	Barrel(Coin(15))
lootBarrel	=	fedoraBarrel
lootBarrel.item	=	Coin(15)
## }
Now,	suppose	you	tried	to	access	fedoraBarrel.item,	expecting	a	fedora:
Listing	17.19		Accessing	fedoraBarrel.item	(Variance.kt)
class	Barrel<T>(var	item:	T)
fun	main(args:	Array<String>)	{
var	fedoraBarrel:	Barrel<Fedora>	=	Barrel(Fedora("a	generic-looking	fedora",	15))
var	lootBarrel:	Barrel<Loot>	=	Barrel(Coin(15))
lootBarrel	=	fedoraBarrel
lootBarrel.item	=	Coin(15)
val	myFedora:	Fedora	=	fedoraBarrel.item
## }
The	compiler	would	then	be	faced	with	a	type	mismatch	–
fedoraBarrel.item	is	not	a	Fedora,	it	is	a	Coin	–	and	you	would	be
faced	with	a	ClassCastException.	This	is	the	problem	that	arises,	and	the
reason	the	assignment	is	not	allowed	by	the	compiler.
It	is	also	why	the	in	and	out	keywords	exist.
In	the	Barrel	class’s	definition,	add	the	out	keyword	and	change	item	from	a
var	to	a	val:

Listing	17.20		Adding	out	(Variance.kt)
class	Barrel<out	T>(varval	item:	T)
## ...
Next,	delete	the	line	that	assigned	Coin	to	item	(which	is	no	longer	allowed,
since	item	is	a	val)	and	change	the	assignment	of	myFedora	to
lootBarrel.item	instead	of	fedoraBarrel.item.
Listing	17.21		Changing	the	assignment	(Variance.kt)
class	Barrel<out	T>(val	item:	T)
fun	main(args:	Array<String>)	{
var	fedoraBarrel:	Barrel<Fedora>	=	Barrel(Fedora("a	generic-looking	fedora",	15))
var	lootBarrel:	Barrel<Loot>	=	Barrel(Coin(15))
lootBarrel	=	fedoraBarrel
lootBarrel.item	=	Coin(15)
val	myFedora:	Fedora	=	fedoraBarrel.itemlootBarrel.item
## }
All	errors	are	resolved.	What	has	changed?
There	are	two	roles	a	generic	parameter	can	be	assigned:	producer	or	consumer.
The	role	of	producer	means	that	a	generic	parameter	will	be	readable	(but	not
writable),	and	consumer	means	the	generic	parameter	will	be	writable	(but	not
readable).
When	you	added	the	out	keyword	to	Barrel<out	T>,	you	specified	that	the
generic	would	act	as	a	producer	–	that	it	would	be	readable,	but	not	writable.
That	meant	that	defining	item	with	the	var	keyword	was	no	longer	permitted	–
otherwise,	it	would	not	simply	be	a	producer	of	Fedoras,	but	would	also	be
writable	and	support	consuming	one.
By	making	the	generic	a	producer,	you	assure	the	compiler	that	the	dilemma
pointed	out	earlier	is	no	longer	a	possibility:	Since	the	generic	parameter	is	a
producer,	not	a	consumer,	the	item	variable	will	never	change.	Kotlin	now
permits	the	assignment	of	fedoraBarrel	to	lootBarrel,	because	it	is	safe
to	do	so:	lootBarrel’s	item	now	has	type	Fedora,	not	Loot,	and	cannot
be	changed.
Take	a	closer	look	at	the	assignment	of	the	myFedora	variable	in	IntelliJ.	The
green	shading	around	lootBarrel	indicates	that	a	smart	cast	took	place,	and
that	is	confirmed	when	you	mouse	over	it	(Figure	17.2):

Figure	17.2		Smart	cast	to	Barrel<Fedora>
The	compiler	can	smart	cast	Barrel<Loot>	to	Barrel<Fedora>	because
item	can	never	change	–	it	is	a	producer	only.
By	the	way,	Lists	are	also	producers.	In	Kotlin’s	definition	for	List,	the
generic	type	parameter	is	marked	with	the	out	keyword:
public	interface	List<out	E>	:	Collection<E>
Marking	the	generic	type	parameter	for	Barrel	with	the	in	keyword	would
have	the	opposite	effect	on	reassigning	the	Barrels:	Instead	of	being	allowed
to	assign	fedoraBarrel	to	lootBarrel,	you	would	be	allowed	to	assign
lootBarrel	to	fedoraBarrel	–	but	not	vice	versa.
Update	Barrel	to	use	the	in	keyword	instead	of	out.	You	will	notice	that
Barrel	will	now	require	dropping	the	val	keyword	for	item,	since	it	could
otherwise	produce	an	item	(a	violation	of	the	consumer	role).
Listing	17.22		Marking	Barrel	with	in	(Variance.kt)
class	Barrel<inout	T>(val	item:	T)
## ...
Now,	lootBarrel	=	fedoraBarrel	in	main	has	an	error	warning	you	of	a	type
mismatch.	Reverse	the	assignment:
Listing	17.23		Reversing	the	assignment	(Variance.kt)
## ...
fun	main(args:	Array<String>)	{
var	fedoraBarrel:	Barrel<Fedora>	=	Barrel(Fedora("a	generic-looking	fedora",	15))
var	lootBarrel:	Barrel<Loot>	=	Barrel(Coin(15))
lootBarrel	=	fedoraBarrel
fedoraBarrel	=	lootBarrel
val	myFedora:	Fedora	=	lootBarrel.item
## }
The	opposite	assignment	is	possible	because	the	compiler	can	be	certain	you
would	never	be	able	to	produce	Loot	from	a	Barrel	containing	Fedoras	–
leading	to	the	possibility	of	class	cast	exceptions.
You	removed	the	val	keyword	from	Barrel	because	Barrel	is	now	a
consumer	–	it	accepts	a	value,	but	it	does	not	produce	one.	Therefore,	you	also
drop	the	item	lookup.	This	is	how	the	compiler	is	able	to	reason	that	the
assignment	you	have	made	is	a	safe	one.

By	the	way,	you	may	have	heard	the	terms	covariance	and	contravariance	used
to	describe	what	out	and	in	do.	In	our	opinion,	these	terms	lack	the
commonsense	clarity	of	in	and	out,	so	we	avoid	them.	We	mention	them	here
because	you	may	encounter	them	elsewhere,	so	now	you	know:	If	you	hear
“covariance,”	think	“out,”	and	if	you	hear	“contravariance,”	think	“in.”
In	this	chapter	you	have	learned	how	to	use	generics	to	expand	the	capabilities	of
Kotlin’s	classes.	You	have	also	seen	type	constraints	and	how	the	in	and	out
keywords	can	be	used	to	define	the	producer	or	consumer	role	for	the	generic
parameter.
In	the	next	chapter,	you	will	learn	about	extensions,	which	allow	you	to	share
functions	and	properties	without	using	inheritance.	You	will	use	them	to	improve
NyetHack’s	codebase.

For	the	More	Curious:	The	reified	Keyword
There	are	cases	where	it	is	useful	to	know	the	specific	type	that	is	used	for	a
generic	parameter.	The	reified	keyword	allows	you	to	check	a	generic
parameter’s	type.
Imagine	that	you	wanted	to	fetch	loot	from	a	list	of	different	kinds	of	loot
(Coins	and	Fedoras,	for	example),	and	–	depending	on	the	type	of	loot	that
was	randomly	selected	–	you	either	wanted	to	provide	a	backup	loot	item	of	a
desired	type	or	return	the	one	that	was	selected.	Here	is	a
randomOrBackupLoot	function	that	attempts	to	capture	that	logic:
fun	<T>	randomOrBackupLoot(backupLoot:	()	->	T):	T	{
val	items	=	listOf(Coin(14),	Fedora("a	fedora	of	the	ages",	150))
val	randomLoot:	Loot	=	items.shuffled().first()
return	if	(randomLoot	is	T)
randomLoot
}	else	{
backupLoot()
## }
## }
fun	main(args:	Array<String>)	{
randomOrBackupLoot	{
Fedora("a	backup	fedora",	15)
## }.run	{
//	Prints	either	the	backup	fedora	or	the	fedora	of	the	ages
println(name)
## }
## }
If	you	typed	this	in,	you	would	find	that	it	does	not	work.	IntelliJ	would	flag	the
type	parameter	T	with	an	error	(Figure	17.3):
Figure	17.3		Cannot	check	for	instance	of	erased	type
Kotlin	normally	disallows	the	type	check	you	performed	against	T	because
generic	types	are	subject	to	type	erasure	–	meaning	the	type	information	for	T	is
not	available	at	runtime.	Java	has	the	same	rule.
If	you	were	to	look	at	the	bytecode	for	the	randomOrBackupLoot	function,
you	would	see	the	effect	of	type	erasure	on	the	expression	randomLoot	is	T:
return	(randomLoot	!=	null	?	randomLoot	instanceof	Object	:	true)
?	randomLoot	:	backupLoot.invoke();

Where	you	used	T,	Object	is	used	instead,	because	the	compiler	no	longer
knows	the	type	of	T	at	runtime.	This	is	why	type	checking	a	generic	defined	in
the	usual	way	is	not	possible.
However,	unlike	Java,	Kotlin	provides	the	reified	keyword,	which	allows	you
to	preserve	the	type	information	at	runtime.
reified	is	used	on	an	inlined	function:
inline	fun	<reified	T>	randomOrBackupLoot(backupLoot:	()	->	T):	T	{
val	items	=	listOf(Coin(14),	Fedora("a	fedora	of	the	ages",	150))
val	first:	Loot	=	items.shuffled().first()
return	if	(first	is	T)	{
first
}	else	{
backupLoot()
## }
## }
Now	the	type	check	first	is	T	is	possible,	because	the	type	information	is
reified.	The	generic	type	information	that	is	normally	erased	is	instead	preserved
so	that	the	compiler	can	check	the	type	of	the	generic	parameter.
The	bytecode	for	the	updated	randomOrBackupLoot	shows	that	the	actual
type	information	for	T	is	maintained,	instead	of	Object:
randomLoot$iv	instanceof	Fedora
?	randomLoot$iv	:	new	Fedora("a	backup	fedora",	15);
Using	the	reified	keyword	allows	you	to	inspect	the	type	of	a	generic
parameter	without	requiring	reflection	(learning	a	name	or	a	type	of	a	property	or
function	at	runtime	–	generally	a	costly	operation).