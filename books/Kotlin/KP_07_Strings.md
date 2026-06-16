

## 7
## Strings
In	programming,	textual	data	is	represented	by	strings	–	ordered	sequences	of
characters.	You	have	already	used	Kotlin’s	strings,	like	the	string	you	formatted
and	displayed	in	SimVillage:
"Welcome	to	SimVillage,	Mayor!	(copyright	2018)"
In	this	chapter	you	will	see	more	of	what	strings	can	do,	using	a	variety	of
functions	for	the	String	type	from	the	Kotlin	standard	library.	In	the	process,
you	will	upgrade	NyetHack’s	tavern	to	allow	customers	to	order	from	the	menu,
an	essential	feature	of	any	tavern	worth	its	salt.

## Extracting	Substrings
To	allow	tavern	customers	to	place	an	order,	you	will	look	at	two	ways	to	extract
one	string	from	another:	the	functions	substring	and	split.
substring
Your	first	task	is	to	write	a	function	that	allows	a	player	to	place	an	order	with
the	tavern	master.	Open	Tavern.kt	in	the	NyetHack	project,	add	a	variable	to
hold	the	name	of	the	tavern,	and	add	a	new	function	called	placeOrder.
Within	the	new	placeOrder	function,	use	String’s	indexOf	and
substring	functions	to	extract	the	tavern	master’s	name	from	the
TAVERN_NAME	string	and	display	it.	(We	will	walk	through	each	line	of
placeOrder	after	you	add	it.)	Also,	remove	the	old	beverage	code	from	the
previous	exercise.	The	tavern	will	feature	more	than	beverage	items,	and
Buttered	Ale	is	no	longer	lawful	to	serve	in	the	realm	anyway.
Listing	7.1		Extracting	the	tavern	master’s	name	(Tavern.kt)
const	val	TAVERN_NAME	=	"Taernyl's	Folly"
fun	main(args:	Array<String>)	{
var	beverage	=	readLine()
//	beverage	=	null
if	(beverage	!=	null)	{
beverage	=	beverage.capitalize()
}	else	{
println("I	can't	do	that	without	crashing	-	beverage	was	null!")
## }
val	beverageServed:	String	=	beverage	?:	"Buttered	Ale"
println(beverageServed)
placeOrder()
## }
private	fun	placeOrder()	{
val	indexOfApostrophe	=	TAVERN_NAME.indexOf('\'')
val	tavernMaster	=	TAVERN_NAME.substring(0	until	indexOfApostrophe)
println("Madrigal	speaks	with	$tavernMaster	about	their	order.")
## }
Run	Tavern.kt.	You	will	see	the	output	Madrigal	speaks	with	Taernyl
about	their	order.
Let’s	go	line	by	line	to	see	how	placeOrder	extracted	the	tavern	master’s
name	from	the	name	of	the	tavern.
First,	you	use	String’s	indexOf	function	to	get	the	index	of	the	first
apostrophe	in	the	String:

val	indexOfFirstApostrophe	=	TAVERN_NAME.indexOf('\'')
An	index	is	an	integer	that	corresponds	to	the	position	of	a	character	in	the
string.	The	index	starts	at	0	for	the	first	character.	The	next	character	has	the
index	1,	the	next	2,	and	so	forth.
The	Char	type,	defined	within	single	quotes,	is	used	to	represent	the	individual
characters	in	a	string.	Passing	a	Char	to	indexOf	tells	the	function	to	locate
the	first	instance	of	the	Char	and	return	its	index.	The	argument	you	provide
indexOf	is	'\'',	so	the	indexOf	function	scans	through	the	string	until	it
finds	a	match	and	returns	the	index	for	the	apostrophe	character.
What	is	the	\	doing	in	that	argument?	The	apostrophe	character	is	also	the	single
quotation	mark	that	signals	a	character	literal.	If	you	entered	your	argument	as
''',	the	compiler	would	read	the	apostrophe	in	the	middle	as	a	single	quotation
mark	enclosing	an	empty	character	literal.	You	need	to	let	the	compiler	know
that	you	are	specifying	the	apostrophe	character	instead,	and	you	do	this	with	the
\	escape	character,	which	distinguishes	between	certain	characters	and	special
meanings	they	have	to	the	compiler.
Table	7.1	lists	the	escape	sequences	(consisting	of	\	and	the	character	being
escaped)	and	their	meanings	to	the	compiler:
Table	7.1		Escape	sequences
Escape	sequenceMeaning
## \t
Tab	character
## \b
Backspace	character
## \n
Newline	character
## \r
Carriage	return
## \"
Double	quotation	mark
## \'
Single	quotation	mark/apostrophe
## \\
## Backslash
## \$
Dollar	sign
## \u
Unicode	character

Once	you	have	the	index	of	the	first	apostrophe	in	the	string,	you	use	string’s
substring	function,	which	returns	a	new	string	from	an	existing	string	using
parameters	you	provide:
val	tavernMaster	=	TAVERN_NAME.substring(0	until	indexOfFirstApostrophe)
substring	accepts	an	IntRange	(a	type	that	represents	a	range	of	integers)
that	determines	the	indices	of	the	characters	to	extract.	In	this	case,	the	range
starts	with	the	first	character	and	ends	with	the	character	before	the	first
apostrophe	(recall	that	until	creates	a	range	that	excludes	the	specified	upper
bound).
This	sets	the	value	of	the	variable	tavernMaster	to	the	string	consisting	of
the	characters	from	the	beginning	of	the	TAVERN_NAME	string	to	just	before	the
first	apostrophe	–	in	other	words,	"Taernyl".
Finally,	you	used	string	templating	(as	you	saw	in	Chapter	3)	to	interpolate	the
variable	tavernMaster	in	the	output	by	prefixing	the	variable	with	$:
println("Madrigal	speaks	with	$tavernMaster	about	their	order.")
split
The	tavern’s	menu	data	will	be	represented	as	a	string	and	stored	in	the
following	format,	separated	by	commas:	type	of	drink,	drink	name,	and	price	(in
gold).	For	example:
shandy,Dragon's	Breath,5.91
Your	next	task	is	to	allow	the	placeOrder	function	to	accept	tavern	menu	data
and	display	the	name,	type,	and	price	of	the	item	the	customer	has	ordered.
Update	the	placeOrder	function	to	accept	tavern	menu	data,	passing	some
menu	data	where	placeOrder	is	called.
(Note	that	from	here	forward	we	will	show	additions	to	a	line	of	code	in	the
existing	line,	rather	than	showing	the	line	deleted	and	re-entered	with	the
change.)
Listing	7.2		Passing	tavern	data	to	placeOrder	(Tavern.kt)
const	val	TAVERN_NAME	=	"Taernyl's	Folly"
fun	main(args:	Array<String>)	{
placeOrder("shandy,Dragon's	Breath,5.91")
## }
private	fun	placeOrder(menuData:	String)	{
val	indexOfApostrophe	=	TAVERN_NAME.indexOf('\'')
val	tavernMaster	=	TAVERN_NAME.substring(0	until	indexOfApostrophe)
println("Madrigal	speaks	with	$tavernMaster	about	their	order.")
## }

Next,	to	extract	the	individual	parts	of	the	menu	data	for	display,	you	are	going
to	use	String’s	split	function,	which	creates	a	series	of	substrings	using	a
delimiter	you	provide.	Add	the	split	function	to	placeOrder:
Listing	7.3		Splitting	the	menu	data	(Tavern.kt)
## ...
private	fun	placeOrder(menuData:	String)	{
val	indexOfApostrophe	=	TAVERN_NAME.indexOf('\'')
val	tavernMaster	=	TAVERN_NAME.substring(0	until	indexOfApostrophe)
println("Madrigal	speaks	with	$tavernMaster	about	their	order.")
val	data	=	menuData.split(',')
val	type	=	data[0]
val	name	=	data[1]
val	price	=	data[2]
val	message	=	"Madrigal	buys	a	$name	($type)	for	$price."
println(message)
## }
split	accepts	a	delimiter	character	to	look	for	and	returns	a	list	of	the	resulting
substrings	with	the	delimiter	omitted.	(Lists,	which	you	will	learn	about	in
Chapter	10,	hold	a	series	of	elements.)	In	this	case,	split	returns	a	list	of
strings	in	the	order	it	found	them.	You	use	indices	in	square	brackets,	officially
known	as	the	indexed	access	operator,	to	extract	the	first,	second,	and	third
strings	from	the	list	and	assign	them	as	the	values	of	the	variables	type,	name,
and	price.
Finally,	as	before,	you	include	the	strings	in	a	message	using	string	interpolation.
Run	Tavern.kt.	This	time,	you	will	see	the	drink	order	printed,	including	the
item	type	and	price.
Madrigal	speaks	with	Taernyl	about	their	order.
Madrigal	buys	a	Dragon's	Breath	(shandy)	for	5.91.
Because	split	returns	a	list,	it	also	supports	simplified	syntax	called
destructuring	–	a	feature	that	allows	you	to	declare	and	assign	multiple	variables
in	a	single	expression.	Update	placeOrder	to	use	destructuring	syntax	instead
of	individual	assignments.
Listing	7.4		Destructuring	the	menu	data	(Tavern.kt)
## ...
private	fun	placeOrder(menuData:	String)	{
val	indexOfApostrophe	=	TAVERN_NAME.indexOf('\'')
val	tavernMaster	=	TAVERN_NAME.substring(0	until	indexOfApostrophe)
println("Madrigal	speaks	with	$tavernMaster	about	their	order.")
val	data	=	menuData.split(',')
val	type	=	data[0]
val	name	=	data[1]
val	price	=	data[2]
val	(type,	name,	price)	=	menuData.split(',')
val	message	=	"Madrigal	buys	a	$name	($type)	for	$price."
println(message)
## }

Destructuring	can	often	be	used	to	simplify	the	assignment	of	variables.	Any
time	the	result	is	a	list,	a	destructuring	assignment	is	allowed.	In	addition	to
List,	other	types	that	support	destructuring	include	Maps	and	Pairs	(both	of
which	you	will	learn	about	in	Chapter	11),	as	well	as	data	classes.
Run	Tavern.kt	again.	The	results	should	be	the	same.

## String	Manipulation
Whoever	drinks	a	Dragon’s	Breath	enjoys	not	only	a	delightful	sensory
experience	but	also	gains	elite	programming	abilities	as	well	as	DragonSpeak,	an
ancient	tongue	similar	to	1337Sp34k.
For	example,	the	following	utterance:
A	word	of	advice:	Don't	drink	the	Dragon's	Breath
Translates	to	this	in	DragonSpeak:
A	w0rd	0f	4dv1c3:	D0n't	dr1nk	th3	Dr4g0n's	Br34th
The	String	type	includes	functions	for	manipulating	the	values	of	a	string.	To
add	a	DragonSpeak	translator	to	NyetHack’s	tavern,	you	are	going	to	use
String’s	replace	function,	which,	as	the	name	suggests,	replaces	characters
based	on	rules	you	specify.	replace	accepts	a	regular	expression	(more	on	that
in	a	moment)	to	determine	what	characters	it	should	act	on	and	calls	an
anonymous	function	that	you	define	to	determine	what	to	replace	the	matched
characters	with.
Add	a	new	function	called	toDragonSpeak	that	accepts	a	phrase	and	returns
a	DragonSpeak	translation.	Also,	add	a	phrase	to	printOrder	and	call
toDragonSpeak	on	it.
Listing	7.5		Defining	the	toDragonSpeak	function	(Tavern.kt)
const	val	TAVERN_NAME	=	"Taernyl's	Folly"
fun	main(args:	Array<String>)	{
placeOrder("shandy,Dragon's	Breath,5.91")
## }
private	fun	toDragonSpeak(phrase:	String)	=
phrase.replace(Regex("[aeiou]"))	{
when	(it.value)	{
## "a"	->	"4"
## "e"	->	"3"
## "i"	->	"1"
## "o"	->	"0"
## "u"	->	"|_|"
else	->	it.value
## }
## }
private	fun	placeOrder(menuData:	String)	{
## ...
println(message)
val	phrase	=	"Ah,	delicious	$name!"
println("Madrigal	exclaims:	${toDragonSpeak(phrase)}")
## }
Run	Tavern.kt.	This	time,	you	will	notice	Madrigal’s	speech	has	taken	on	the
very	distinctive	drawl	of	DragonSpeak:

Madrigal	speaks	with	Taernyl	about	their	order.
Madrigal	buys	a	Dragon's	breath	(shandy)	for	5.91
Madrigal	exclaims:	Ah,	d3l1c10|_|s	Dr4g0n's	Br34th!
Here	you	used	a	combination	of	features	available	on	String	to	generate	the
DragonSpeak	version	of	the	phrase.
The	version	of	the	replace	function	you	used	accepts	two	arguments.	The	first
argument	is	a	regular	expression	that	determines	which	characters	you	want	to
replace.	A	regular	expression,	or	regex,	defines	a	search	pattern	for	characters
you	want	to	look	for.	The	second	argument	is	an	anonymous	function	that
defines	what	you	want	to	replace	each	matching	character	with.
Take	a	look	at	the	first	argument	that	you	provided	to	replace,	the	regular
expression	that	determines	which	characters	to	select	for	replacement:
phrase.replace(Regex("[aeiou]"))	{
## ...
## }
Regex	accepts	a	pattern	argument,	"[aeiou]",	that	defines	the	characters	you
want	to	match	and	replace.	Kotlin	uses	the	same	regular	expression	patterns	as
Java.	You	can	read	the	documentation	for	the	supported	regular	expression
pattern	syntax	at	docs.oracle.com/javase/8/docs/api/java/
util/regex/Pattern.html.
After	defining	the	characters	you	want	replace	to	match,	you	define	what	you
want	to	replace	those	characters	with,	using	an	anonymous	function.
phrase.replace(Regex("[aeiou]"))	{
when	(it.value)	{
## "a"	->	"4"
## "e"	->	"3"
## "i"	->	"1"
## "o"	->	"0"
## "u"	->	"|_|"
else	->	it.value
## }
## }
The	argument	received	by	the	anonymous	function	gives	the	value	of	the	each
match	found	by	the	regular	expression	you	defined	and	returns	the	new	value	for
the	match.
Strings	are	immutable
A	clarification	regarding	the	“replacing”	of	the	characters	performed	by
toDragonSpeak:	If	you	were	to	print	the	phrase	variable	from	Listing	7.5
before	and	after	calling	replace	on	it,	you	would	find	that	the	variable’s	value
does	not	actually	change.
In	reality,	the	replace	function	does	not	“replace”	any	part	of	the	phrase

variable.	Instead,	replace	creates	a	new	string.	It	uses	the	old	string’s	value	as
an	input	and	chooses	characters	for	the	new	string	using	the	expression	you
provide.
Whether	they	are	defined	with	var	or	val,	all	strings	in	Kotlin	are	actually
immutable	(as	they	are	in	Java).	Though	the	variables	that	hold	the	value	for	the
String	can	be	reassigned	if	the	string	is	a	var,	the	string	instance	itself	can
never	be	changed.	Any	function	that	appears	to	change	the	value	of	a	string	(like
replace)	actually	creates	a	new	string	with	the	changes	applied	to	it.

## String	Comparison
What	if	a	player	were	to	order	something	other	than	Dragon’s	Breath?
toDragonSpeak	would	still	be	called.	This	is	not	what	you	want.
Add	a	conditional	to	Tavern.kt’s	placeOrder	function	to	skip	calling
toDragonSpeak	if	the	player	did	not	order	Dragon’s	Breath:
Listing	7.6		Comparing	strings	in	placeOrder	(Tavern.kt)
## ...
private	fun	placeOrder(menuData:	String)	{
## ...
val	phrase	=	"Ah,	delicious	$name!"
println("Madrigal	exclaims:	${toDragonSpeak(phrase)}")
val	phrase	=	if	(name	==	"Dragon's	Breath")	{
"Madrigal	exclaims	${toDragonSpeak("Ah,	delicious	$name!")}"
}	else	{
"Madrigal	says:	Thanks	for	the	$name."
## }
println(phrase)
## }
Comment	out	your	Dragon’s	Breath	order	in	the	main	function	–	we	will	return
to	it	soon	–	and	add	a	new	call	to	placeOrder	with	different	menu	data.
Listing	7.7		Changing	the	menu	data	(Tavern.kt)
const	val	TAVERN_NAME	=	"Taernyl's	Folly"
fun	main(args:	Array<String>)	{
placeOrder("shandy,Dragon's	Breath,5.91")
//		placeOrder("shandy,Dragon's	Breath,5.91")
placeOrder("elixir,Shirley's	Temple,4.12")
## }
## ...
Run	Tavern.kt.	You	will	see	the	following	output:
Madrigal	speaks	with	Taernyl	about	their	order.
Madrigal	buys	a	Shirley's	Temple	(elixir)	for	4.12
Madrigal	says:	Thanks	for	the	Shirley's	Temple.
You	checked	the	structural	equality	of	name	and	"Dragon's	Breath"	using	the
structural	equality	operator,	==.	You	have	seen	this	operator	before,	used	with
numeric	values.	When	used	with	strings,	it	checks	that	the	characters	in	each
string	match	one	another	and	are	in	the	same	order.
There	is	another	way	to	check	the	equality	of	two	variables:	comparing
referential	equality,	which	means	checking	that	two	variables	share	the	same
reference	to	a	type	instance	–	in	other	words,	that	two	variables	point	to	the	same
object	on	the	heap.	Referential	equality	is	checked	using	===.
Referential	comparison	is	not	usually	what	you	want.	You	generally	do	not	care

whether	strings	are	different	instances,	only	that	they	have	the	same	characters	in
the	same	sequence	(i.e.,	that	the	structures	of	two	separate	type	instances	are
identical).
If	you	are	familiar	with	Java,	the	string	comparison	behavior	using	==	is	different
than	what	you	may	have	expected,	because	Java	uses	the	==	symbol	for
referential	comparison.	To	compare	strings	structurally	in	Java,	you	use	the
function	equals.
In	this	chapter,	you	have	learned	more	about	how	to	work	with	strings	in	Kotlin.
You	saw	how	to	use	the	indexOf	function	to	find	the	specific	index	of	a
character	and	regular	expressions	to	search	through	strings	for	patterns	that	you
define.	You	learned	about	destructuring	syntax,	which	allows	you	to	declare
multiple	variables	and	assign	their	values	in	a	single	expression,	and	you	also
learned	that	Kotlin	uses	structural	comparison	when	using	the	==	operator.
In	the	next	chapter,	you	will	learn	about	working	with	numbers	in	Kotlin	by
building	out	the	strongbox	for	the	tavern	so	that	gold	and	silver	can	change
hands.

For	the	More	Curious:	Unicode
As	you	have	learned,	a	string	consists	of	an	ordered	sequence	of	characters,	and
a	character	is	an	instance	of	the	Char	type.	Specifically,	a	Char	is	a	Unicode
character.	The	Unicode	character	encoding	system	is	designed	to	support	“the
interchange,	processing,	and	display	of	the	written	texts	of	the	diverse	languages
and	technical	disciplines	of	the	modern	world”	(unicode.org).
This	means	the	individual	characters	in	a	string	can	be	any	of	a	diverse	palette	of
characters	and	symbols	–	136,690	of	them	(and	growing)	–	including	characters
from	the	alphabet	of	any	language	in	the	world,	icons,	glyphs,	emoji,	and	more.
To	declare	a	character,	you	have	two	options.	Both	are	wrapped	in	single	quotes.
For	characters	on	your	keyboard,	the	simplest	option	is	the	character	itself	in	the
single	quotes:
val	capitalA:	Char	=	'A'
But	not	all	136,690	characters	are	included	on	your	keyboard.	The	other	way	to
represent	a	character	is	with	its	Unicode	character	code,	preceded	by	the
Unicode	character	escape	sequence	\u:
val	unicodeCapitalA:	Char	=	'\u0041'
There	is	a	key	for	the	letter	“A”	on	your	keyboard,	but	there	is	not	one	for	the
symbol.	To	represent	it	in	your	program,	your	only	choice	is	to	use	its	character
code	in	single	quotes.	If	you	want	to	try	it	out,	create	a	new	Kotlin	file	in	your
project.	Enter	the	code	below	into	the	file	and	run	it.	(Delete	the	file	when	you
are	done	by	right-clicking	on	it	in	the	project	tool	window	and	selecting	Delete.)
Listing	7.8		Om...	(scratch	file)
fun	main(args:	Array<String>)	{
val	omSymbol	=	'\u0950'
print(omSymbol)
## }
You	will	see	the		symbol	printed	in	the	console.

For	the	More	Curious:	Traversing	a	String’s
## Characters
The	String	type	includes	other	functions	that	move	through	the	sequence	of
characters	one	at	a	time,	as	indexOf	and	split	do.	For	example,	you	can
print	each	character	of	the	tavern	data,	one	character	at	a	time,	using	String’s
forEach	function.	This	call:
"Dragon's	Breath".forEach	{
println("$it\n")
## }
Would	generate	the	following	output:
## D
r
a
g
o
n
## '
s
## B
r
e
a
t
h
Many	of	these	functions	are	also	available	on	the	List	type,	just	as	the	majority
of	the	functions	for	traversing	lists	that	you	will	learn	about	in	Chapter	10	are
also	available	for	strings.	In	many	ways,	a	Kotlin	String	behaves	like	a	list	of
characters.

Challenge:	Improving	DragonSpeak
Currently,	toDragonSpeak	only	works	on	lowercase	letters.	For	example,	the
following	exclamation	would	not	be	rendered	correctly	as	DragonSpeak	output:
## DRAGON'S	BREATH:	IT'S	GOT	WHAT	ADVENTURERS	CRAVE!
Improve	the	toDragonSpeak	function	to	work	with	capital	letters.