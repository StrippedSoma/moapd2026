

## 11
## Maps
The	third	commonly	used	type	of	collection	in	Kotlin	is	Map.	The	Map	type	has
a	lot	in	common	with	the	List	and	Set	types:	All	three	group	a	series	of
elements,	are	read-only	by	default,	use	parameterized	types	to	tell	the	compiler
the	type	of	their	contents,	and	support	iteration.
Where	Map	is	different	from	List	and	Set	is	that	its	elements	consist	of	key-
value	pairs	that	you	define,	and	instead	of	index-based	access	using	an	integer,	a
map	provides	key-based	access	using	a	type	that	you	specify.	Keys	are	unique
and	identify	the	values	in	the	map;	the	values,	on	the	other	hand,	do	not	need	to
be	unique.	In	this	way,	Map	shares	another	feature	with	Set:	The	keys	of	a	map
are	guaranteed	to	be	unique,	just	like	the	elements	of	a	set.

Creating	a	Map
Like	lists	and	sets,	maps	are	created	using	functions:	mapOf	and
mutableMapOf.	In	Tavern.kt,	create	a	map	representing	the	amount	of
gold	each	patron’s	purse	contains.	(We	will	explain	the	argument	syntax	shortly.)
Listing	11.1		Creating	a	read-only	map	(Tavern.kt)
## ...
var	uniquePatrons	=	mutableSetOf<String>()
val	menuList	=	File("data/tavern-menu-items.txt")
.readText()
## .split("\n")
val	patronGold	=	mapOf("Eli"	to	10.5,	"Mordoc"	to	8.0,	"Sophie"	to	5.5)
fun	main(args:	Array<String>)	{
## ...
println(uniquePatrons)
var	orderCount	=	0
while	(orderCount	<=	9)	{
placeOrder(uniquePatrons.shuffled().first(),
menuList.shuffled().first())
orderCount++
## }
println(patronGold)
## }
## ...
While	the	keys	in	a	map	must	all	be	of	the	same	type,	and	the	values	must	be	of
the	same	type,	the	keys	and	values	can	be	of	different	types.	Here	you	have	a
map	with	string	keys	and	double	values.	You	are	using	type	inference,	but	if	you
had	wanted	to	include	explicit	type	information,	it	would	look	like	this:	val
patronGold:	Map<String,	Double>.
Run	Tavern.kt	to	see	the	map	printed.	Notice	that	when	a	map	is	printed,	it	is
shown	in	curly	braces,	while	lists	and	sets	are	both	shown	in	square	brackets.
The	tavern	master	says:	Eli's	in	the	back	playing	cards.
The	tavern	master	says:	Yea,	they're	seated	back	by	the	stew	kettle.
## ...
{Eli=10.5,	Mordoc=8.0,	Sophie=5.5}
You	used	to	to	define	each	entry	(key	and	value)	in	the	map:
## ...
mapOf("Eli"	to	10.75,	"Mordoc"	to	8.25,	"Sophie"	to	5.50)
to	may	look	like	a	keyword,	but	in	fact	it	is	a	special	type	of	function	that
allows	you	to	drop	the	dot	and	the	parentheses	around	its	arguments.	You	will
learn	more	about	this	in	Chapter	18.	The	to	function	converts	the	values	on	its
lefthand	and	righthand	sides	into	a	Pair	–	a	type	for	representing	a	group	of
two	elements.
Maps	are	built	using	key-value	Pairs.	In	fact,	another	way	you	could	have

defined	the	entries	for	the	map	is	as	follows.	(Try	it	in	the	REPL.)
Listing	11.2		Defining	a	map	using	the	Pair	type	(REPL)
val	patronGold	=	mapOf(Pair("Eli",	10.75),
Pair("Mordoc",	8.00),
Pair("Sophie",	5.50))
However,	building	a	map	using	the	to	function	is	cleaner	than	this	syntax.
We	have	said	that	the	keys	in	a	map	must	be	unique.	What	if	you	tried	adding	a
duplicate	entry	to	the	map?	In	the	REPL,	add	another	pair	with	"Sophie"	for	the
key:
Listing	11.3		Adding	a	duplicate	key	(REPL)
val	patronGold	=	mutableMapOf("Eli"	to	5.0,	"Sophie"	to	1.0)
patronGold	+=	"Sophie"	to	6.0
println(patronGold)
{Eli=5.0,	Sophie=6.0}
You	used	Map’s	plus	assign	operator	(+=)	to	add	a	pair	with	a	duplicate	key	into
the	map.	Since	the	key	"Sophie"	was	already	in	the	map,	the	existing	pair	was
replaced	with	the	new	one.	You	see	the	same	behavior	if	you	try	to	include
duplicate	keys	when	initializing	a	map:
println(mapOf("Eli"	to	10.75,
"Mordoc"	to	8.25,
"Sophie"	to	5.50,
"Sophie"	to	6.25))
{Eli=10.5,	Mordoc=8.0,	Sophie=6.25}

## Accessing	Map	Values
You	access	a	value	in	a	map	using	its	key.	For	the	patronGold	map,	you	will
use	the	string	key	to	access	the	patron’s	gold	balance	value.
Listing	11.4		Accessing	individual	gold	balances	(Tavern.kt)
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
println(patronGold)
println(patronGold["Eli"])
println(patronGold["Mordoc"])
println(patronGold["Sophie"])
## }
Run	Tavern.kt	to	print	the	balances	for	the	three	patrons	you	added	to	the
map:
## ...
## 10.5
## 8.0
## 5.5
Note	that	the	output	includes	only	the	values,	not	the	keys.
As	with	other	collections,	Kotlin	provides	functions	for	accessing	the	values
stored	in	a	map.	Table	11.1	shows	some	of	the	common	map	accessor	functions
and	their	behaviors.
Table	11.1		Map	accessor	functions
FunctionDescriptionExample
## []	(get/index
operator)
Gets	the	value	for	a	key;	returns
null	if	the	key	does	not	exist.
patronGold["Reginald"]
null
getValue
Gets	the	value	for	a	key;	throws	an
exception	if	the	key	provided	is
not	in	the	map.
patronGold.getValue("Reggie")
NoSuchElementException
getOrElse
Gets	the	value	for	the	key	or
returns	a	default	using	an
anonymous	function.
patronGold.getOrElse("Reggie")	{"No
such	patron"}
No	such	patron
patronGold.getOrDefault("Reginald",

getOrDefault
Gets	the	value	for	the	key	or
returns	a	default	using	a	value	you
provide.
## 0.0)
## 0.0

Adding	Entries	to	a	Map
Your	map	of	patron	gold	values	represents	the	purses	of	Eli,	Mordoc,	and
Sophie,	but	it	does	not	include	the	purse	values	for	the	patrons	you	dynamically
generated.	Time	to	fix	that	by	replacing	patronGold	with	a	MutableMap.
Make	the	patronGold	map	mutable.	Then	iterate	through	the
uniquePatrons	set,	adding	an	entry	to	the	map	for	each	patron	with	a	value
of	6.0	gold.	Also,	remove	the	map	entry	look-ups	that	you	performed,	since	the
keys	are	no	longer	just	first	names.
Listing	11.5		Populating	the	mutable	map	(Tavern.kt)
import	java.io.File
import	kotlin.math.roundToInt
const	val	TAVERN_NAME:	String	=	"Taernyl's	Folly"
var	playerGold	=	10
var	playerSilver	=	10
val	patronList	=	mutableListOf("Eli",	"Mordoc",	"Sophie")
val	lastName	=	listOf("Ironfoot",	"Fernsworth",	"Baggins")
val	uniquePatrons	=	mutableSetOf<String>()
val	menuList	=	File("data/tavern-menu-items.txt")
.readText()
## .split("\n")
val	patronGold	=	mapOf("Eli"	to	10.5,	"Mordoc"	to	8.0,	"Sophie"	to	5.5)
val	patronGold	=	mutableMapOf<String,	Double>()
fun	main(args:	Array<String>)	{
## ...
println(uniquePatrons)
uniquePatrons.forEach	{
patronGold[it]	=	6.0
## }
var	orderCount	=	0
while	(orderCount	<=	9)	{
placeOrder(uniquePatrons.shuffled().first(),
menuList.shuffled().first())
orderCount++
## }
println(patronGold)
println(patronGold["Eli"])
println(patronGold["Mordoc"])
println(patronGold["Sophie"])
## }
## ...
You	have	added	an	entry	for	each	unique	patron	to	the	map,	with	a	value	of	6.0
gold	for	each,	by	iterating	over	uniquePatrons.	(Remember	the	it
keyword?	Here,	it	refers	to	an	element	in	uniquePatrons.)
Table	11.2	shows	some	of	the	commonly	used	functions	for	modifying	the
contents	of	a	mutable	map.
Table	11.2		Mutable	map	mutator	functions
FunctionDescriptionExample

## =
## (assignment
operator)
Adds	or	updates	the	value	in	the	map	for
the	key	specified.
val	patronGold	=
mutableMapOf("Mordoc"	to	6.0)
patronGold["Mordoc"]	=	5.0
{Mordoc=5.0}
## +=
(plus	assign
operator)
Adds	or	updates	an	entry	or	entries	in
the	map	based	on	the	entry	or	map
specified.
val	patronGold	=
mutableMapOf("Mordoc"	to	6.0)
patronGold	+=	"Eli"	to	5.0
{Mordoc=6.0,	Eli=5.0}
val	patronGold	=
mutableMapOf("Mordoc"	to	6.0)
patronGold	+=	mapOf("Eli"	to	7.0,
"Mordoc"	to
## 1.0,
"Jebediah"	to
## 4.5)
{Mordoc=1.0,	Eli=7.0,	Jebediah=4.5}
put
Adds	or	updates	the	value	in	the	map	for
the	key	specified.
val	patronGold	=
mutableMapOf("Mordoc"	to	6.0)
patronGold.put("Mordoc",	5.0)
{Mordoc=5.0}
putAll
Adds	all	of	the	key-value	pairs	provided
to	the	map.
val	patronGold	=
mutableMapOf("Mordoc"	to	6.0)
patronGold.putAll(listOf("Jebediah"
to	5.0,
"Sahara"
to	6.0))
patronGold["Jebediah"]
## 5.0
patronGold["Sahara"]
## 6.0
getOrPut
Adds	an	entry	for	the	key	if	it	does	not
exist	already	and	returns	the	result;
otherwise	returns	the	existing	entry.
val	patronGold	=
mutableMapOf<String,	Double>()
patronGold.getOrPut("Randy"){5.0}
## 5.0
patronGold.getOrPut("Randy"){10.0}
## 5.0
remove
Removes	an	entry	from	the	map	and
returns	the	value.
val	patronGold	=
mutableMapOf("Mordoc"	to	5.0)
val	mordocBalance	=
patronGold.remove("Mordoc")
## {}
print(mordocBalance)
## 5.0
## -
## (minus
operator)
Returns	a	new	map,	excluding	the
entries	specified.
val	newPatrons	=
mutableMapOf("Mordoc"	to	6.0,
"Jebediah"	to	1.0)
- "Mordoc"
{Jebediah=1.0}
## -=
## (minus
assign
operator)
Removes	entry	or	map	of	entries	from
the	map.
mutableMapOf("Mordoc"	to	6.0,
"Jebediah"	to	1.0)	-=
"Mordoc"
{Jebediah=1.0}
clear
Removes	all	entries	from	the	map.
mutableMapOf("Mordoc"	to	6.0,
"Jebediah"	to
## 1.0).clear()
## {}



## Modifying	Map	Values
To	complete	the	transaction,	the	price	of	the	item	should	be	deducted	from	the
patron’s	purse.	The	patronGold	map	associates	gold	balance	values	with	a
given	patron’s	name	as	a	key.	You	will	modify	the	gold	balance	value	for	a
patron	to	record	the	patron’s	new	balance	once	the	purchase	is	completed.
Your	performPurchase	and	displayBalance	functions	are	tied	to
Madrigal’s	purse	and	get	into	details	of	gold	and	silver	pieces	that	are	not	needed
here.	Delete	them	and	the	playerGold	and	playerSilver	variables,	which
are	only	used	in	those	functions.	Then	define	a	new	performPurchase
function	to	handle	a	patron	purchase.	(You	will	define	a	new	function	to	display
patron	balances	soon.)
To	update	the	value	after	the	purchase	is	made,	the	function	will	get	it	from	the
patronGold	map	using	the	patron’s	name.	Call	the	new
performPurchase	function	after	the	patron	speaks	to	Taernyl,	the	tavern
master,	about	their	order	(do	not	forget	to	uncomment	the	call).
Listing	11.6		Updating	the	values	in	patronGold	(Tavern.kt)
import	java.io.File
import	kotlin.math.roundToInt
const	val	TAVERN_NAME:	String	=	"Taernyl's	Folly"
var	playerGold	=	10
var	playerSilver	=	10
val	patronList	=	mutableListOf("Eli",	"Mordoc",	"Sophie")
## ...
fun	performPurchase(price:	Double)	{
displayBalance()
val	totalPurse	=	playerGold	+	(playerSilver	/	100.0)
println("Total	purse:	$totalPurse")
println("Purchasing	item	for	$price")
val	remainingBalance	=	totalPurse	-	price
println("Remaining	balance:	${"%.2f".format(remainingBalance)}")
val	remainingGold	=	remainingBalance.toInt()
val	remainingSilver	=	(remainingBalance	%	1	*	100).roundToInt()
playerGold	=	remainingGold
playerSilver	=	remainingSilver
displayBalance()
## }
private	fun	displayBalance()	{
println("Player's	purse	balance:	Gold:	$playerGold	,	Silver:	$playerSilver")
## }
fun	performPurchase(price:	Double,	patronName:	String)	{
val	totalPurse	=	patronGold.getValue(patronName)
patronGold[patronName]	=	totalPurse	-	price
## }
private	fun	toDragonSpeak(phrase:	String)	=
## ...
## }
private	fun	placeOrder(patronName:	String,	menuData:	String)	{

## ...
println(message)
//		performPurchase(price.toDouble(),	patronName)
val	phrase	=	if	(name	==	"Dragon's	Breath")	{
## ...
## }
## ...
Run	Tavern.kt.	You	will	continue	to	see	ten	random	orders	along	the	lines	of:
The	tavern	master	says:	Eli's	in	the	back	playing	cards.
The	tavern	master	says:	Yea,	they're	seated	by	the	stew	kettle.
Mordoc	Fernsworth	speaks	with	Taernyl	about	their	order.
Mordoc	Fernsworth	buys	a	goblet	of	LaCroix	(meal)	for	1.22.
Mordoc	Fernsworth	says:	Thanks	for	the	goblet	of	LaCroix.
## ...
You	have	updated	the	patron’s	gold	balance,	and	only	one	task	remains	–
reporting	the	patrons’	gold	balances	after	they	make	their	purchases.	You	will	do
this	by	iterating	through	your	map	using	forEach.
Add	a	new	function	to	Tavern.kt	called	displayPatronBalances	that
iterates	through	the	map,	printing	the	final	gold	balance	(formatted	to	the	second
decimal	place,	as	you	did	in	Chapter	8)	for	each	patron.	Call	it	after	the
simulation	completes	in	the	main	function.
Listing	11.7		Displaying	patron	balances	(Tavern.kt)
## ...
fun	main(args:	Array<String>)	{
## ...
var	orderCount	=	0
while	(orderCount	<=	9)	{
placeOrder(uniquePatrons.shuffled().first(),
menuList.shuffled().first())
orderCount++
## }
displayPatronBalances()
## }
private	fun	displayPatronBalances()	{
patronGold.forEach	{	patron,	balance	->
println("$patron,	balance:	${"%.2f".format(balance)}")
## }
## }
## ...
Run	Tavern.kt,	sit	back,	and	watch	as	the	patrons	of	Taernyl’s	Folly	chat
with	the	tavern	master,	order	off	the	menu,	and	pay	for	their	items:
The	tavern	master	says:	Eli's	in	the	back	playing	cards.
The	tavern	master	says:	Yea,	they're	seated	by	the	stew	kettle.
Mordoc	Ironfoot	speaks	with	Taernyl	about	their	order.
Mordoc	Ironfoot	buys	a	iced	boilermaker	(elixir)	for	11.22.
Mordoc	Ironfoot	says:	Thanks	for	the	iced	boilermaker.
Sophie	Baggins	speaks	with	Taernyl	about	their	order.
Sophie	Baggins	buys	a	Dragon's	Breath	(shandy)	for	5.91.
Sophie	Baggins	exclaims:	Ah,	d3l1c10|_|s	Dr4g0n's	Br34th!
Sophie	Ironfoot	speaks	with	Taernyl	about	their	order.
Sophie	Ironfoot	buys	a	pickled	camel	hump	(desert	dessert)	for	7.33.
Sophie	Ironfoot	says:	Thanks	for	the	pickled	camel	hump.
Eli	Fernsworth	speaks	with	Taernyl	about	their	order.
Eli	Fernsworth	buys	a	Dragon's	Breath	(shandy)	for	5.91.
Eli	Fernsworth	exclaims:	Ah,	d3l1c10|_|s	Dr4g0n's	Br34th!
Sophie	Fernsworth	speaks	with	Taernyl	about	their	order.
Sophie	Fernsworth	buys	a	iced	boilermaker	(elixir)	for	11.22.
Sophie	Fernsworth	says:	Thanks	for	the	iced	boilermaker.
Sophie	Fernsworth	speaks	with	Taernyl	about	their	order.

Sophie	Fernsworth	buys	a	Dragon's	Breath	(shandy)	for	5.91.
Sophie	Fernsworth	exclaims:	Ah,	d3l1c10|_|s	Dr4g0n's	Br34th!
Sophie	Fernsworth	speaks	with	Taernyl	about	their	order.
Sophie	Fernsworth	buys	a	pickled	camel	hump	(desert	dessert)	for	7.33.
Sophie	Fernsworth	says:	Thanks	for	the	pickled	camel	hump.
Mordoc	Fernsworth	speaks	with	Taernyl	about	their	order.
Mordoc	Fernsworth	buys	a	Shirley's	Temple	(elixir)	for	4.12.
Mordoc	Fernsworth	says:	Thanks	for	the	Shirley's	Temple.
Sophie	Baggins	speaks	with	Taernyl	about	their	order.
Sophie	Baggins	buys	a	goblet	of	LaCroix	(meal)	for	1.22.
Sophie	Baggins	says:	Thanks	for	the	goblet	of	LaCroix.
Mordoc	Fernsworth	speaks	with	Taernyl	about	their	order.
Mordoc	Fernsworth	buys	a	iced	boilermaker	(elixir)	for	11.22.
Mordoc	Fernsworth	says:	Thanks	for	the	iced	boilermaker.
Mordoc	Ironfoot,	balance:	-5.22
Sophie	Baggins,	balance:	-1.13
Eli	Fernsworth,	balance:	0.09
Sophie	Fernsworth,	balance:	-18.46
Sophie	Ironfoot,	balance:	-1.33
Mordoc	Fernsworth,	balance:	-9.34
In	the	last	two	chapters,	you	learned	how	to	work	with	Kotlin’s	List,	Set,	and
Map	collection	types.	Table	11.3	compares	their	features.
Table	11.3		Kotlin	collections	summary
## Collection
type
Ordered?Unique?Stores
## Supports
destructuring?
## List
YesNoElementsYes
## Set
NoYesElementsNo
## Map
NoKeysKey-value
pairs
## No
Since	collections	are	read-only	by	default,	you	must	explicitly	create	a	mutable
collection	(or	convert	a	read-only	collection	to	be	mutable)	to	modify	its
contents	–	preventing	you	from	accidentally	adding	or	removing	elements.
In	the	next	chapter,	you	will	learn	how	to	apply	object-oriented	programming
principles	as	you	define	your	own	classes	within	NyetHack.

## Challenge:	Tavern	Bouncer
A	patron	without	any	gold	should	not	be	allowed	to	place	an	order.	In	fact,	they
should	not	be	allowed	to	loiter	in	the	tavern	–	the	tavern	bouncer	should	see	to
that.	If	a	patron	lacks	sufficient	funds,	boot	them	out	onto	the	mean	streets	of
NyetHack	by	removing	them	from	uniquePatrons	and	the	patronGold
map.