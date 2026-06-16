

## 8
## Numbers
Kotlin	has	a	variety	of	types	for	dealing	with	numbers	and	numeric
computations.	Multiple	types	are	available	for	each	of	the	two	main	varieties	of
numbers	that	Kotlin	can	work	with:	whole-number	integers	and	numbers	with
decimals.	In	this	chapter,	you	will	see	how	Kotlin	handles	both	varieties	as	you
update	NyetHack	to	implement	the	player’s	purse	and	allow	money	to	change
hands	at	the	tavern.

## Numeric	Types
All	numeric	types	in	Kotlin,	as	in	Java,	are	signed,	meaning	they	can	represent
both	positive	and	negative	numbers.	In	addition	to	whether	they	support	decimal
values,	the	numeric	types	differ	in	the	number	of	bits	they	are	allocated	in
memory	and,	consequently,	their	minimum	and	maximum	values.
Table	8.1	shows	some	of	the	numeric	types	in	Kotlin,	the	number	of	bits	for	each
type,	and	the	maximum	and	minimum	value	the	type	supports.	(We	will	explain
these	details	in	a	moment.)
Table	8.1		Commonly	used	numeric	types
TypeBitsMax	ValueMin	Value
## Byte
## 8127-128
## Short
## 1632767-32768
## Int
## 322147483647-2147483648
## Long
## 649223372036854775807-9223372036854775808
## Float
## 323.4028235E381.4E-45
## Double
## 641.7976931348623157E3084.9E-324
What	is	the	relationship	between	a	type’s	bit	size	and	its	maximum	and
minimum	values?	Computers	store	integers	in	binary	form	with	a	fixed	number
of	bits	(“bit”	is	short	for	“binary	digit,”	by	the	way).	A	bit	is	represented	by	a
single	0	or	1.
To	represent	a	number,	Kotlin	assigns	a	finite	number	of	bits,	depending	on	the
numeric	type	chosen.	The	leftmost	bit	position	represents	the	sign	(the	positive
or	negative	nature	of	the	number).	The	remaining	bit	positions	each	represent	a
power	of	2,	with	the	rightmost	position	being	2
## 0
.	To	compute	the	value	of	a
binary	number,	add	up	each	of	the	powers	of	2	whose	bit	is	a	1.
Figure	8.1	shows	the	example	of	the	number	42	in	binary	form.

Figure	8.1		42	in	binary
Since	Int	is	32	bit,	the	largest	number	that	can	be	stored	in	an	Int	is
represented,	in	its	binary	form,	with	31	1s.	Adding	up	all	those	powers	of	2
yields	a	total	of	2,147,483,647,	the	largest	value	an	Int	in	Kotlin	can	hold.
Because	the	number	of	bits	determines	the	maximum	and	minimum	value	a
numeric	type	can	represent,	the	difference	between	the	types	is	the	number	of
bits	available	to	represent	the	number.	Since	Long	uses	64	bits	instead	of	32,	a
Long	can	hold	an	exponentially	larger	number	(2
## 63
## ).
A	note	about	the	types	Short	and	Byte.	The	long	and	short	of	it	(sorry)	is	that
neither	Short	or	Byte	is	commonly	used	when	representing	conventional
numbers.	They	are	used	for	specialized	cases	and	to	support	interoperability	with
legacy	Java	programs.	For	example,	you	might	work	with	Byte	when	reading	a
stream	of	data	from	a	file	or	processing	graphics	(a	color	pixel	is	often
represented	as	three	bytes:	one	for	each	color	in	RGB).	You	will	sometimes	see
Short	used	when	interacting	with	native	code	for	CPUs	that	do	not	support	32
bit	instructions.	However,	for	most	purposes,	whole	numbers	are	represented
with	Int	or,	when	a	greater	value	is	needed,	Long.

## Integers
You	learned	in	Chapter	2	that	an	integer	is	a	number	that	does	not	have	a	decimal
point	–	a	whole	number	–	and	is	represented	in	Kotlin	with	the	Int	type.	Int	is
good	for	representing	a	quantity	or	count	of	“things”:	the	remaining	pints	of
mead,	the	number	of	tavern	patrons,	or	the	count	of	gold	and	silver	coins	a
player	possesses.
Time	to	do	some	coding.	Open	Tavern.kt	and	add	Int	variables	to	represent
the	current	gold	and	silver	in	the	player’s	purse.	Uncomment	the	call	to
placeOrder	to	pass	the	menu	data	for	an	order	of	Dragon’s	Breath	and
remove	your	order	of	a	Shirley’s	Temple.
Also,	add	a	placeholder	performPurchase	function	that	will	handle	the	logic
for	making	a	purchase	and	a	function	to	display	the	player’s	current	purse
balance.	Call	the	new	performPurchase	in	placeOrder.
Listing	8.1		Setting	up	the	player’s	purse	(Tavern.kt)
const	val	TAVERN_NAME	=	"Taernyl's	Folly"
var	playerGold	=	10
var	playerSilver	=	10
fun	main(args:	Array<String>)	{
//		placeOrder("shandy,Dragon's	Breath,5.91")
placeOrder("elixir,Shirley's	Temple,4.12")
## }
fun	performPurchase()	{
displayBalance()
## }
private	fun	displayBalance()	{
println("Player's	purse	balance:	Gold:	$playerGold	,	Silver:	$playerSilver")
## }
private	fun	toDragonSpeak(phrase:	String)	=
## ...
## }
private	fun	placeOrder(menuData:	String)	{
val	indexOfApostrophe	=	TAVERN_NAME.indexOf('\'')
val	tavernMaster	=	TAVERN_NAME.substring(0	until	indexOfApostrophe)
println("Madrigal	speaks	with	$tavernMaster	about	their	order.")
val	(type,	name,	price)	=	menuData.split(',')
val	message	=	"Madrigal	buys	a	$name	($type)	for	$price."
println(message)
performPurchase()
val	phrase	=	if	(name	==	"Dragon's	Breath")	{
"Madrigal	exclaims	${toDragonSpeak("Ah,	delicious	$name!")}"
}	else	{
"Madrigal	says:	Thanks	for	the	$name."
## }
println(phrase)
## }

Notice	that	you	used	an	Int	to	represent	the	player’s	gold	and	silver	quantities.
The	max	quantity	of	gold	and	silver	in	the	player’s	purse	(and	in	the	known
NyetHack	universe)	will	be	much	less	than	2,147,483,647,	the	max	value	for	an
## Int.
Go	ahead	and	run	Tavern.kt.	You	have	not	yet	implemented	the	logic	for
showing	that	the	player	has	paid	for	an	item,	so	this	time	Madrigal	gets	their
order	on	the	house:
Madrigal	speaks	with	Taernyl	about	their	order.
Madrigal	buys	a	Dragon's	Breath	(shandy)	for	5.91.
Player's	purse	balance:	Gold:	10	,	Silver:	10
Madrigal	exclaims:	Ah,	d3l1c10|_|s	Dr4g0n's	Br34th!

## Decimal	Numbers
Take	another	look	at	the	menuData	string	for	the	tavern:
"shandy,Dragon's	Breath,5.91"
Madrigal	needs	to	pay	5.91	gold	for	the	Dragon’s	Breath,	so	playerGold
should	decrease	by	5.91	when	the	drink	is	ordered.
Numeric	values	with	decimal	places	are	represented	with	the	Float	or	Double
type.	Update	Tavern.kt	so	that	a	double	with	the	value	for	the	item	is	passed
to	the	performPurchase	function:
Listing	8.2		Passing	the	price	information	(Tavern.kt)
const	val	TAVERN_NAME	=	"Taernyl's	Folly"
## ...
fun	performPurchase(price:	Double)	{
displayBalance()
println("Purchasing	item	for	$price")
## }
## ...
private	fun	placeOrder(menuData:	String)	{
## ...
val	(type,	name,	price)	=	menuData.split(',')
val	message	=	"Madrigal	buys	a	$name	($type)	for	$price."
println(message)
performPurchase(price)
## ...
## }

Converting	a	String	to	a	Numeric	Type
If	you	were	to	run	Tavern.kt	right	now,	you	would	see	a	compilation	error.
This	is	because	the	price	variable	that	you	are	currently	passing	to
performPurchase	is	a	string,	and	the	function	expects	a	double.	To	the
human	eye,	“5.91”	might	look	like	a	number,	but	the	Kotlin	compiler	sees	it
differently,	because	it	was	split	from	the	menuData	string.
The	good	news	is	that	Kotlin	includes	functions	that	convert	strings	to	different
types	–	including	numbers.	Some	of	the	most	commonly	used	of	these
conversion	functions	are:
toFloat
toDouble
toDoubleOrNull
toIntOrNull
toLong
toBigDecimal
Attempting	to	convert	a	string	of	the	wrong	format	will	throw	an	exception.	For
example,	calling	toInt	on	a	string	with	the	value	“5.91”	would	throw	an
exception,	since	the	decimal	portion	of	the	string	value	would	not	fit	into	an
## Int.
Because	of	the	possibility	of	exceptions	when	converting	between	different
numeric	types,	Kotlin	also	provides	the	safe	conversion	functions
toDoubleOrNull	and	toIntOrNull.	When	the	number	does	not	convert
correctly,	a	null	value	is	returned	instead	of	an	exception.	You	could	use	the	null
coalescing	operator	with	toIntOrNull,	for	example,	to	provide	a	default
value:
val	gold:	Int	=		"5.91".toIntOrNull()	?:	0
Update	placeOrder	to	convert	the	string	argument	to	performPurchase
to	a	double.
Listing	8.3		Converting	the	price	argument	to	a	double

(Tavern.kt)
## ...
private	fun	placeOrder(menuData:	String)	{
val	indexOfApostrophe	=	TAVERN_NAME.indexOf('\'')
val	tavernMaster	=	TAVERN_NAME.substring(0	until	indexOfApostrophe)
println("Madrigal	speaks	with	$tavernMaster	about	their	order.")
val	(type,	name,	price)	=	menuData.split(',')
val	message	=	"Madrigal	buys	a	$name	($type)	for	$price."
println(message)
performPurchase(price.toDouble())
## ...
## }

Converting	an	Int	to	a	Double
Now	to	take	the	gold	out	of	the	player’s	purse.	The	purse	contains	whole	gold
and	silver	coins,	but	the	price	of	a	menu	item	is	represented	in	gold	as	a	double.
To	do	the	sale,	you	first	need	to	convert	the	player’s	gold	and	silver	to	a	single
double	so	that	the	item	price	can	be	subtracted.	Add	a	new	variable	to
performPurchase	to	track	the	player’s	total	purse.	One	gold	is	worth	100
silver,	so	divide	the	player’s	silver	by	100	and	add	the	result	to	the	quantity	of
gold	to	get	the	total	value.	The	totalPurse	and	price	variables	are	of	the
same	type,	Double,	so	subtract	the	price	from	the	purse	and	assign	the	result	to
a	new	variable.
Listing	8.4		Subtracting	the	price	from	the	player’s	purse
(Tavern.kt)
## ...
fun	performPurchase(price:	Double)	{
displayBalance()
val	totalPurse	=	playerGold	+	(playerSilver	/	100.0)
println("Total	purse:	$totalPurse")
println("Purchasing	item	for	$price")
val	remainingBalance	=	totalPurse	-	price
## }
## ...
First,	you	do	the	calculation	for	getting	the	totalPurse	and	print	the	result.
Notice	that	the	division	to	convert	playerSilver	for	totalPurse	includes
a	decimal	point	for	the	divisor	–	100.0,	not	100.
If	you	were	to	divide	playerSilver,	an	Int,	by	100,	also	an	Int,	Kotlin
would	not	give	you	0.10,	a	Double.	Instead,	you	would	get	another	Int	–	0,	in
fact	–	that	loses	the	decimal	result	you	are	looking	for.	(Try	it	in	the	REPL.)
Because	both	numbers	in	the	operation	are	integers,	Kotlin	performs	integer
arithmetic,	which	does	not	support	a	result	with	a	decimal.
To	get	a	decimal	result,	you	need	Kotlin	to	perform	floating-point	arithmetic,
which	you	achieve	by	including	in	the	operation	at	least	one	type	that	supports	a
decimal.	Try	the	calculation	in	the	REPL	again,	but	this	time	add	a	decimal	to
either	number	to	indicate	that	floating-point	arithmetic	should	be	used	and	the
result	should	be	a	Double	(0.1).
With	the	player’s	purse	converted	to	totalPurse,	you	next	subtract	the	price
of	the	Dragon’s	Breath	from	the	converted	purse	value:
val	remainingBalance	=	totalPurse	-	price

To	see	the	result	of	this	calculation,	enter	10.1	-	5.91	in	the	REPL.	If	you	have
not	worked	with	numeric	types	in	another	programming	language,	the	result
might	be	surprising.
You	might	have	assumed	a	result	of	4.19,	but	what	you	get	is
4.1899999999999995.	This	result	is	due	to	the	way	computers	represent
fractional	quantities:	by	using	a	floating	point.	A	floating	point,	meaning	a
decimal	that	can	be	positioned	at	an	arbitrary	place	(“float”),	is	an
approximation	of	a	real	number.	A	floating	point	number	approximates	its	value
to	support	both	precision	(the	ability	to	represent	a	wide	range	of	numbers	with
varying	levels	of	decimal	places)	and	performance	(speedy	calculations).
How	precisely	you	represent	a	number	with	a	fractional	portion	depends	on	the
type	of	calculation	required.	For	example,	if	you	were	programming	the
mainframe	for	the	central	bank	of	NyetHack,	processing	a	high	volume	of
financial	transactions	and	involved	fractional	computations,	you	would	represent
those	transactions	using	a	very	high	level	of	precision,	at	the	cost	of	some
processing	time.	Generally	speaking,	for	this	sort	of	financial	calculation	you
would	use	a	type	called	BigDecimal	to	specify	the	precision	and	rounding	of
the	floating	point	calculations.	(This	is	the	same	BigDecimal	type	that	you
may	be	familiar	with	from	Java.)
For	your	tavern	simulation,	however,	you	can	accept	the	very	small	degree	of
imprecision	in	Double.

Formatting	a	Double
Rather	than	working	with	4.1899999999999995	pieces	of	gold,	you	will	round
the	value	up	to	4.19.	String’s	format	function	can	be	used	to	round	a	double
to	a	precision	that	you	define.	Update	the	performPurchase	function	to
format	the	remaining	balance	amount:
Listing	8.5		Formatting	a	double	(Tavern.kt)
## ...
fun	performPurchase(price:	Double)	{
displayBalance()
val	totalPurse	=	playerGold	+	(playerSilver	/	100.0)
println("Total	purse:	$totalPurse")
println("Purchasing	item	for	$price")
val	remainingBalance	=	totalPurse	-	price
println("Remaining	balance:	${"%.2f".format(remainingBalance)}")
## }
## ...
The	gold	remaining	in	the	purse	is	interpolated	into	the	string	using	$,	as	you
have	seen	before.	But	what	follows	the	$	is	not	simply	the	name	of	the	variable	–
it	is	an	expression	in	curly	braces.	Within	the	braces	is	a	call	to	format	with
remainingBalance	passed	in	as	the	argument.
The	call	to	format	also	specifies	a	format	string,	"%.2f".	A	format	string	uses
a	special	sequence	of	characters	to	define	how	you	want	to	format	data.	The
particular	format	string	you	defined	specifies	that	you	want	to	round	the	floating
point	number	up	to	the	second	decimal	place.	Then	you	pass	the	value	or	values
to	format	as	an	argument	to	the	format	function.
Kotlin’s	format	strings	use	the	same	style	as	the	standard	format	strings	in	Java,
C/C++,	Ruby,	and	many	other	languages.	For	details	on	format	string
specification,	take	a	look	at	the	Java	API	documentation	at
docs.oracle.com/javase/8/docs/api/java/util/
## Formatter.html.
Run	Tavern.kt.	You	will	see	that	Madrigal	now	pays	for	the	Dragon's	Breath:
Madrigal	speaks	with	Taernyl	about	their	order.
Madrigal	buys	a	Dragon's	Breath	(shandy)	for	5.91.
Player's	purse	balance:	Gold:	10	,	Silver:	10
Total	purse:	10.1
Purchasing	item	for	5.91
Remaining	balance:	4.19
Madrigal	exclaims	Ah,	d3l1c10|_|s	Dr4g0n's	Br34th!

Converting	a	Double	to	an	Int
Now	that	the	player’s	remaining	balance	has	been	calculated,	all	that	is	left	to	do
is	to	convert	the	remaining	balance	back	to	gold	and	silver	amounts.	Update	the
performPurchase	function	to	convert	the	player’s	total	purse	value	to	gold
and	silver.	(Make	sure	to	add	the	import	kotlin.math.roundToInt	statement	at
the	top	of	the	file.)
Listing	8.6		Converting	to	silver	and	gold	(Tavern.kt)
import	kotlin.math.roundToInt
const	val	TAVERN_NAME	=	"Taernyl's	Folly"
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
## ...
Here,	you	used	two	of	the	conversion	functions	available	on	Double.	Calling
toInt	on	a	Double	results	in	dropping	any	fractional	value	from	the	double.
Another	term	for	this	is	loss	of	precision.	Some	portion	of	the	original	data	is
lost,	because	you	asked	for	an	integer	representation	of	a	double	that	included	a
fractional	quantity,	and	the	integer	representation	is	less	precise.
Note	that	calling	toInt	on	a	double	is	different	than	calling	toInt	on	a	string
like	"5.91",	which	would	result	in	an	exception	being	thrown.	The	difference	is
that	converting	a	string	to	a	double	requires	parsing	the	string	to	turn	it	into	a
numeric	type,	whereas	a	type	that	is	already	numeric,	like	Double	or	Int,	does
not	require	any	parsing.
In	this	case,	remainingBalance	is	4.1899999999999995,	so	calling	toInt
results	in	the	integer	4.	This	is	the	amount	of	gold	the	player	has	remaining.
Next,	you	convert	the	fractional	portion	of	the	total	value	to	silver:
val	remainingSilver	=	(remainingBalance	%	1	*	100).roundToInt()
Here,	you	use	the	modulus	operator	(%,	also	known	as	the	remainder	operator),
which	finds	the	remainder	when	one	number	is	divided	by	another.	%	1	has	the

effect	of	stripping	the	whole-number	portion	of	remainingBalance	(the
portion	that	can	be	evenly	divided	by	1),	leaving	the	decimal	value.	Finally,	you
multiply	the	remainder	by	100	to	convert	to	silver	and	call	roundToInt	on	the
result,	18.99999999999995.	roundToInt	rounds	to	the	nearest	integer,	so	you
are	left	with	19	silver.
Run	Tavern.kt	again	to	see	the	smooth	operation	of	your	Tavern:
Madrigal	speaks	with	Taernyl	about	their	order.
Madrigal	buys	a	Dragon's	Breath	(shandy)	for	5.91.
Player's	purse	balance:	Gold:	10	,	Silver:	10
Total	purse:	10.1
Purchasing	item	for	5.91
Remaining	balance:	4.19
Player's	purse	balance:	Gold:	4	,	Silver:	19
Madrigal	exclaims	Ah,	d3l1c10|_|s	Dr4g0n's	Br34th!
In	this	chapter	you	have	worked	with	Kotlin’s	numeric	types	and	learned	how
Kotlin	handles	the	two	major	categories	of	numbers:	whole	numbers	and
decimal	point	numbers.	You	have	also	learned	how	to	convert	between	the
different	types	and	what	each	type	supports.	In	the	next	chapter,	you	will	learn
about	Kotlin’s	standard	functions	–	a	set	of	utility	functions	available	for	use
with	all	types.

For	the	More	Curious:	Bit	Manipulation
Earlier,	you	saw	that	numbers	have	a	binary	representation.	You	can	get	the
binary	representation	for	a	number	at	any	time.	For	example,	you	could	ask	for
the	binary	representation	of	the	integer	42	with:
Integer.toBinaryString(42)
## 101010
Kotlin	includes	functions	for	performing	operations	on	the	binary	representation
of	a	value,	called	bitwise	operations	–	including	operations	you	may	be	familiar
with	from	other	languages,	such	as	Java.	Table	8.2	shows	commonly	used	binary
operations	available	in	Kotlin.
Table	8.2		Binary	operations
FunctionDescriptionExample
Integer.toBinaryString
Converts	an	integer
to	binary
representation.
Integer.toBinaryString(42)
## //	101010
shl(bitcount)
Shifts	bits	left	by
bitcount.
## 42.shl(2)
## //	10101000
shr(bitcount)
Shifts	bits	right	by
bitcount.
## 42.shr(2)
## //	1010
inv()
Inverts	bits.42.inv()
## //
## 11111111111111111111111111010101
xor(number)
Compares	two	binary
representations	and
performs	a	logical
‘exclusive	or’
operation	on	the
corresponding	bit
positions,	returning	1
for	each	bit	position
that	has	a	1	in	one
input	but	not	the
other.
## 42.xor(33)
## //	001011
and(number)
Compares	two	binary42.and(10)
## //	1010

representations	and
performs	a	logical
‘and’	operation	on	the
corresponding	bit
positions,	returning	1
for	each	bit	position
that	has	a	1	in	both
inputs.

## Challenge:	Remaining	Pints
When	a	Dragon’s	Breath	is	sold,	it	is	drafted	from	the	cask,	which	holds	5
gallons.	Assuming	an	order	is	one	pint	(.125	gallons),	track	the	remaining
quantity	of	Dragon’s	Breath.	Display	the	number	of	pints	left	in	the	cask	after	12
pints	have	been	sold.

Challenge:	Handling	a	Negative	Balance
Currently,	Madrigal	can	place	an	order	no	matter	how	little	gold	and	silver	is	in
their	purse	–	even	if	there	is	none.	This	is	an	unsustainable	business	model	for
Taernyl’s	Folly.	In	this	challenge	you	will	correct	that.
Update	the	code	for	performPurchase	to	determine	whether	the	purchase
can	be	performed.	If	it	cannot,	no	money	should	change	hands,	and	instead	of
the	message	"Madrigal	buys	a	Dragon’s	Breath	(shandy)	for	5.91",	a
message	from	the	bartender	explaining	that	the	customer	is	short	on	gold	should
be	printed.	To	simulate	multiple	orders,	call	performPurchase	several	times
in	the	placeOrder	function.

## Challenge:	Dragoncoin
A	new	currency	is	sweeping	the	land:	dragoncoin	–	instant,	secure,	and
anonymous	to	spend	at	any	tavern.	Assuming	the	current	valuation	is	1.43	gold
per	dragoncoin,	represent	the	player’s	purchase	in	dragoncoin	instead	of	silver
and	gold.	Tavern	prices	remain	defined	in	gold	prices.	Your	player	starts	the
game	with	5	dragoncoin.	For	a	single	purchase	of	Dragon’s	Breath	that	costs
5.91	gold,	make	sure	the	player’s	remaining	dragoncoin	balance	is	.8671	after
the	purchase.