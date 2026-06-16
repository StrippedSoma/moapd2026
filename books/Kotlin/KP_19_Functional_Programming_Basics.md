

## 19
## Functional	Programming	Basics
For	the	last	several	chapters,	you	have	been	learning	about	and	working	with	the
object-oriented	programming	paradigm.	Another	prominent	programming
paradigm	is	functional	programming,	developed	in	the	1950s	based	on	the
mathematical	abstraction	lambda	calculus.	While	functional	programming
languages	have	generally	been	more	common	in	academia	than	in	commercial
software,	the	principles	of	the	approach	are	useful	in	any	language.
The	functional	programming	style	relies	on	data	that	is	returned	from	a	small
number	of	higher-order	functions	(functions	that	accept	or	return	another
function)	designed	specifically	to	work	on	collections,	and	it	favors	composing
chains	of	operations	with	those	functions	to	create	more	complex	behavior.	You
have	worked	with	higher-order	functions	(which	accept	functions	as	parameters
and	return	functions	as	their	result)	and	function	types	(which	enable	you	to
define	functions	as	values)	already.
Kotlin	supports	multiple	programming	styles,	so	you	can	mix	object-oriented
and	functional	programming	styles	to	suit	the	problem	at	hand.	In	this	chapter,
you	will	use	the	REPL	to	explore	some	of	the	functional	programming	features
Kotlin	offers	and	learn	about	the	ideas	behind	the	functional	programming
paradigm.

## Function	Categories
There	are	three	broad	categories	of	functions	that	compose	a	functional	program:
transforms,	filters,	and	combines.	Each	category	is	designed	to	work	on
collection	data	structures	to	yield	a	final	result.	Functions	in	functional
programming	are	also	designed	to	be	composable,	meaning	that	simple	functions
can	be	combined	to	build	complex	behavior.
## Transforms
The	first	category	of	function	in	functional	programming	is	transforms.	A
transform	function	works	on	the	contents	of	a	collection	by	walking	through	the
collection	and	transforming	each	item	with	a	transformer	function	provided	as
an	argument.	The	transform	function	then	returns	a	copy	of	the	modified
collection,	and	execution	proceeds	to	the	next	function	in	the	chain.
Two	commonly	used	transforms	are	map	and	flatMap.
The	map	transform	function	iterates	through	the	collection	it	is	called	on	and
applies	its	transformer	function	to	each	element.	The	result	is	a	collection	with
the	same	number	of	elements	as	the	input	collection.	Enter	the	following	into	the
Kotlin	REPL	to	see	an	example:
Listing	19.1		Converting	a	list	of	animals	to	babies	–	with	tails
## (REPL)
val	animals	=	listOf("zebra",	"giraffe",	"elephant",	"rat")
val	babies	=	animals
.map{	animal	->	"A	baby	$animal"	}
.map{	baby	->	"$baby,	with	the	cutest	little	tail	ever!"}
println(babies)
Functional	programming	emphasizes	composable	functions	that	can	be
combined	with	one	another	to	act	on	data	in	series.	Here,	the	first	map	applies	its
transformer	function,	{	animal	->	"A	baby	$animal"	},	to	transform	each
animal	into	a	baby	animal	(or,	at	least,	to	stick	“baby”	in	front	of	its	name)	and
passes	the	resulting	modified	copy	of	the	list	forward	to	the	next	function	in	the
chain.
The	next	function	here	is	also	a	map,	which	runs	through	the	same	series	of
steps	to	add	a	cute	tail	to	each	baby	animal.	Reaching	the	end	of	the	chain	of
functions,	a	final	collection	with	the	result	of	applying	both	map	operations	to

each	element	is	yielded:
A	baby	zebra,	with	the	cutest	little	tail	ever!
A	baby	giraffe,	with	the	cutest	little	tail	ever!
A	baby	elephant,	with	the	cutest	little	tail	ever!
A	baby	rat,	with	the	cutest	little	tail	ever!
We	said	earlier	that	transform	functions	return	a	modified	copy	of	the	collection
they	are	called	on.	They	do	not	directly	modify	the	original	collection.	In	the
REPL,	print	the	value	of	animals,	the	original	list,	to	see	that	it	has	not
changed:
Listing	19.2		Original	collection	not	modified	(REPL)
print(animals)
## "zebra",	"giraffe",	"elephant",	"rat"
The	original	animals	collection	was	not	modified	in	any	way.	map	does	its
work	by	returning	a	new	copy	of	the	collection	with	the	transformer	you	defined
applied	to	each	element.
In	this	way,	variables	that	change	over	time	are	avoided.	In	fact,	the	functional
programming	style	favors	immutable	copies	of	data	that	are	passed	to	the	next
function	in	the	chain.	The	idea	behind	this	is	that	mutable	variables	lead	to
programs	that	are	harder	to	debug	and	reason	about.	They	also	increase	the
amount	of	state	programs	rely	on	to	do	their	work.
We	said	earlier	that	map	returns	a	collection	with	the	same	number	of	elements
as	the	input	collection.	(This	is	not	the	case	for	all	transform	functions,	as	you
will	see	in	the	next	section.)	However,	the	elements	do	not	need	to	be	of	the
same	type.	Try	entering	the	following	in	the	REPL:
Listing	19.3		Before	and	after	mapping:	same	number	of	items,
different	types	(REPL)
val	tenDollarWords	=	listOf("auspicious",	"avuncular",	"obviate")
val	tenDollarWordLengths	=	tenDollarWords.map	{	it.length	}
print(tenDollarWordLengths)
## [10,	9,	7]
tenDollarWords.size
## 3
tenDollarWordLengths.size
## 3
size	is	a	property	available	on	collections	that	holds	the	number	of	elements	in
a	list	or	set	or	the	number	of	key-value	pairs	in	a	map.
In	this	example,	three	items	were	received	on	the	lefthand	side	of	map,	and	three
items	were	returned	on	its	righthand	side.	What	changes	is	the	type	of	data:	The
tenDollarWords	collection	is	a	List<String>,	and	the	list	generated	by	the
map	function	is	a	List<Int>.

Take	a	look	at	the	signature	of	the	map	function:
<T,	R>	Iterable<T>.map(transform:	(T)	->	R):	List<R>
The	functional	programming	style	is	enabled	largely	because	of	Kotlin’s	support
for	higher-order	functions.	map,	as	you	can	see	in	its	signature,	accepts	a
function	type.	It	would	not	be	possible	to	pass	a	transformer	function	to	map
without	the	ability	to	define	a	higher-order	type.	And	map	would	not	be	nearly
as	useful	if	not	for	its	generic	type	parameters.
Another	commonly	used	transform	function	is	flatMap.	The	flatMap
function	works	with	a	collection	of	collections	and	returns	a	single,	“flattened”
collection	containing	all	of	the	elements	of	the	input	collections.
Enter	the	following	into	the	Kotlin	REPL	to	see	an	example:
Listing	19.4		Flattening	two	lists	(REPL)
listOf(listOf(1,	2,	3),	listOf(4,	5,	6)).flatMap	{	it	}
## [1,	2,	3,	4,	5,	6]
The	result	is	a	new	list	with	all	the	elements	from	the	two	original	sublists.	Note
that	the	number	of	elements	in	the	original	collection	(two	–	the	two	sublists)
and	the	number	of	elements	in	the	output	collection	(six)	are	not	the	same.
In	the	next	section,	you	will	combine	flatMap	with	another	category	of
function.
## Filters
The	second	category	of	functions	in	functional	programming	is	filters.	A	filter
function	accepts	a	predicate	function	that	checks	each	element	in	a	collection
against	a	condition	and	returns	either	true	or	false.	If	the	predicate	returns	true,
the	element	is	added	to	the	new	collection	that	the	filter	returns.	If	the	predicate
returns	false,	the	element	is	excluded	from	the	new	collection.
One	filter	function	is	the	aptly	named	filter.	Let’s	start	with	an	example	of
filter	combined	with	flatMap.	Enter	the	following	into	the	REPL:
Listing	19.5		Filtering	and	flattening	(REPL)
val	itemsOfManyColors	=	listOf(listOf("red	apple",	"green	apple",	"blue	apple"),
listOf("red	fish",	"blue	fish"),	listOf("yellow	banana",	"teal	banana"))
val	redItems	=	itemsOfManyColors.flatMap	{	it.filter	{	it.contains("red")	}	}
print(redItems)
[red	apple,	red	fish]
Here,	flatMap	accepts	the	transform	function	filter,	allowing	you	to	do

work	on	each	of	the	sublists	before	they	are	flattened.
filter,	in	turn,	accepts	a	predicate	function	with	a	condition	to	check:	{
it.contains("red")	}.	As	flatMap	iterates	through	all	of	the	elements	in	its
input	lists,	filter	checks	each	against	the	condition	in	its	predicate	and
includes	only	those	elements	for	which	the	predicate	is	true	in	the	new
collections	it	returns.
Finally,	flatMap	combines	the	items	from	the	resulting	transformed	sublists
into	one	new	list.
This	series	of	functions	is	typical	of	functional	programming.	Enter	the
following	into	the	Kotlin	REPL	to	see	another	example:
Listing	19.6		Filtering	non-prime	numbers	(REPL)
val	numbers	=	listOf(7,	4,	8,	4,	3,	22,	18,	11)
val	primes	=	numbers.filter	{	number	->
(2	until	number).map	{	number	%	it	}
.none	{	it	==	0	}
## }
print(primes)
You	have	implemented	a	solution	to	a	fairly	complex	problem	with	only	a
handful	of	simple	functions.	This	is	the	signature	style	of	functional
programming:	bite-sized	operations	that	do	one	thing	and	work	together	to
produce	a	more	complex	result.
The	filter	function’s	predicate	condition	here	is	the	result	of	another
function:	map.	For	each	element	in	numbers,	map	divides	the	number	by	each
value	in	the	range	from	2	until	the	number	in	question	and	returns	the
remainders.	Next,	none	returns	true	if	none	of	the	returned	remainders	equal	0.
If	so,	the	predicate	condition	is	true	and	the	number	checked	is	prime	(because	it
is	not	evenly	divisible	by	any	number	except	1	and	itself).
## Combines
The	third	category	of	functions	used	in	functional	programming	is	combines.
Combining	functions	take	different	collections	and	merge	them	into	a	new	one.
(This	is	different	than	flatMap,	which	is	called	on	one	collection	that	contains
other	collections.)	Enter	the	following	into	the	Kotlin	REPL:
Listing	19.7		Combining	two	collections,	functional	style	(REPL)
val	employees	=	listOf("Denny",	"Claudette",	"Peter")
val	shirtSize	=	listOf("large",	"x-large",	"medium")
val	employeeShirtSizes	=	employees.zip(shirtSize).toMap()
println(employeeShirtSizes["Denny"])

Here,	you	used	the	zip	combining	function	to	combine	two	lists:	employees	and
their	respective	shirt	sizes.	zip	then	returns	a	new	list,	a	collection	of	Pairs.
You	call	toMap	on	the	resulting	list,	as	you	can	whenever	you	have	a	list	of
Pairs,	to	return	a	map	that	can	be	indexed	into	using	a	key	–	here,	an	employee
name.
Another	function	that	is	useful	for	combining	values	is	the	fold	function.	fold
accepts	an	initial	accumulator	value,	which	is	updated	with	the	result	of	an
anonymous	function	that	is	called	for	each	item.	The	accumulator	value	is	then
carried	forward	to	the	next	anonymous	function.	Consider	this	example,	where
the	fold	function	is	used	to	accumulate	a	list	of	numbers,	multiplied	by	3:
val	foldedValue	=	listOf(1,	2,	3,	4).fold(0)	{	accumulator,	number	->
println("Accumulated	value:	$accumulator")
accumulator	+	(number	*	3)
## }
println("Final	value:	$foldedValue")
If	you	were	to	run	this	code,	you	would	see	the	following	result:
Accumulated	value:	0
Accumulated	value:	3
Accumulated	value:	9
Accumulated	value:	18
Final	value:	30
The	initial	value	for	the	accumulator,	0,	is	passed	to	the	anonymous	function,
with	the	result	that	Accumulated	value:	0	is	printed.	That	value,	0,	is	then
carried	forward	into	the	calculation	for	the	first	element	in	the	list,	1,	with	the
result	Accumulated	value:	3	(that	is,	0	+	(1	*	3)).	In	the	next	calculation,	the
accumulated	value	of	3	is	added	to	(2	*	3),	with	the	result	Accumulated	value:
9	–	and	so	forth.	Once	all	the	elements	have	been	visited,	the	final	accumulator
value	holds	the	result.

## Why	Functional	Programming?
Look	back	at	the	example	using	zip	in	Listing	19.7.	Imagine	implementing	the
same	task	in	the	object-oriented	paradigm	or	its	broader	class,	called	imperative
programming.	In	Java,	for	example,	this	task	might	look	something	like	this:
List<String>	employees	=	Arrays.asList("Denny",	"Claudette",	"Peter");
List<String>	shirtSizes	=	Arrays.asList("large",	"x-large",	"medium");
Map<String,	String>	employeeShirtSizes	=	new	HashMap<>();
for	(int	i	=	0;	i	<	employees.size;	i++)	{
employeeShirtSizes.put(employees.get(i),	shirtSizes.get(i));
## }
At	first	glance,	the	imperative	version	here	may	look	like	it	accomplishes	the
task	in	roughly	the	same	number	of	lines	as	the	functional	version	in	Listing
19.7.	But	a	closer	look	shows	that	the	functional	approach	offers	a	number	of
key	benefits:
- “Accumulator”	variables	(employeeShirtSizes,	for	example)	are
defined	implicitly,	reducing	the	number	of	stateful	variables	to	keep
track	of.
- The	results	from	functional	operations	are	added	to	accumulator
variables	automatically,	reducing	the	risk	of	bugs.
- New	operations	are	trivially	easy	to	add	to	the	functional	chain,	since
all	functional	operators	work	with	the	iterable	you	are	performing	work
on.
Considering	the	first	two	of	these	benefits,	new	operations	in	the	imperative
style	usually	also	involve	the	creation	of	more	variables	to	hold	more	state.	For
example,	an	employeeShirtSizes	collection	is	needed	outside	of	the	for
loop	to	hold	the	loop’s	results.
This	pattern	requires	manually	adding	the	results	to	employeeShirtSizes
with	each	loop.	If	you	neglect	to	add	the	values	to	the	employeeShirtSizes
collection	(a	step	that	can	be	easy	to	overlook),	the	rest	of	the	program	will	not
work	correctly.	Each	additional	step	increases	the	chances	that	this	type	of
mistake	will	occur.
On	the	other	hand,	a	functional	implementation	implicitly	accumulates	a	new
collection	after	each	operation	in	the	chain	without	requiring	new	accumulator
variable	definitions:
val	formattedSwagOrders	=	employees.zip(shirtSize).toMap()

There	are	fewer	mistakes	to	make	in	the	functional	style	because	the
accumulation	of	the	values	in	a	new	collection	is	performed	implicitly,	as	part	of
the	functional	chain’s	work.
As	for	the	third	benefit	listed	above,	since	all	of	the	functional	operations	are
designed	to	work	with	iterables,	it	is	trivial	to	add	another	step	to	the	functional
chain.	For	example,	suppose	the	employeeShirtSizes	map	needed	to	be
formatted	to	represent	swag	orders	after	building	the	hash	map.	In	the	imperative
style,	that	would	require	an	addition	like	this:
List<String>	formattedSwagOrders	=	new	ArrayList<>();
for	(Map.Entry<String,	String>	shirtSize	:	employeeShirtSizes.entrySet())	{
formattedSwagOrders.add(String.format("%s,	shirt	size:	%s",
it.getKey(),	it.getValue());
## }
A	new	accumulator	value	and	a	new	for	loop	that	works	to	populate	the
accumulator	with	results:	more	entities,	more	state,	more	to	keep	track	of.
With	the	functional	style,	subsequent	operations	are	easily	added	to	the	chain
without	the	need	for	additional	state.	The	same	program	could	be	implemented
functionally	with	the	simple	addition	of:
.map	{	"${it.key},	shirt	size:	${it.value}"	}

## Sequences
In	Chapter	10	and	Chapter	11,	you	were	introduced	to	the	collection	types	List,
Set,	and	Map.	These	collection	types	are	all	known	as	eager	collections.	When
an	instance	of	any	of	these	types	is	created,	all	the	values	it	contains	are	added	to
the	collection	and	can	be	accessed.
There	is	another	flavor	of	collection:	lazy	collections.	You	learned	about	lazy
initialization,	in	which	a	variable	is	not	initialized	until	it	is	first	accessed,	in
Chapter	13.	Lazy	collection	types,	similar	to	lazy	initialization	of	other	types,
provide	better	performance	–	specifically	when	working	with	very	large
collections	–	because	their	values	are	produced	only	as	needed.
Kotlin	offers	a	built-in	lazy	collection	type	called	Sequence.	Sequences	do
not	index	their	contents,	and	they	do	not	keep	track	of	their	size.	In	fact,	when
working	with	a	sequence,	the	possibility	of	an	infinite	sequence	of	values	exists,
because	there	is	no	limit	to	the	number	of	items	that	can	be	produced.
With	a	sequence,	you	define	a	function	that	is	referred	to	each	time	a	new	value
is	requested,	called	an	iterator	function.	One	way	to	define	a	sequence	and	its
iterator	is	by	using	a	sequence	builder	function	provided	by	Kotlin,
generateSequence.	generateSequence	accepts	an	initial	seed	value,
the	starting	place	for	the	sequence.	When	the	sequence	is	acted	on	by	a	function,
generateSequence	calls	an	iterator	you	specify	that	determines	the	next
value	to	produce.	For	example:
generateSequence(0)	{	it	+	1	}
.onEach	{	println("The	Count	says:	$it,	ah	ah	ah!")	}
If	you	were	to	run	this	snippet,	the	onEach	function	would	execute	forever.
So,	what	is	a	lazy	collection	good	for,	and	why	choose	it	over	a	list?	Let’s	go
back	to	the	example	of	finding	primes	in	Listing	19.6.	Suppose	you	wanted	to
adapt	this	to	generate	the	first	N	number	of	primes	–	say,	1,000.	A	first	shot	at	an
implementation	might	look	like	this:
//	Extension	to	Int	that	determines	whether	a	number	is	prime
fun	Int.isPrime():	Boolean	{
(2	until	this).map	{
if	(this	%	it	==	0)	{
return	false	//	Not	a	prime!
## }
## }
return	true
## }
val	toList	=	(1..5000).toList().filter	{	it.isPrime()	}.take(1000)

The	problem	with	this	implementation	is	that	you	do	not	know	how	many
numbers	you	have	to	check	to	get	1,000	primes.	This	implementation	takes	a
guess	–	5,000	–	but	in	fact	this	is	not	enough.	(It	will	only	get	you	669	primes,	if
you	want	to	know.)
This	is	a	perfect	case	for	using	a	lazy	collection,	instead	of	an	eager	one,	to	back
the	chain	of	functions.	A	lazy	collection	is	ideal,	because	you	do	not	need	to
define	an	upper	bound	for	the	number	of	items	to	check	for	the	sequence:
val	oneThousandPrimes	=	generateSequence(3)	{	value	->
value	+	1
}.filter	{	it.isPrime()	}.take(1000)
In	this	solution,	generateSequence	produces	a	new	value,	one	at	a	time,
starting	from	3	(the	seed	value)	and	incrementing	by	one	each	time.	Then	it
filters	the	values	with	the	extension	isPrime.	It	continues	doing	this	until
1,000	items	have	been	produced.	Because	there	is	no	way	to	know	how	many
candidate	numbers	will	have	to	be	checked,	lazily	producing	new	values	until
the	take	function	is	satisfied	is	ideal.
In	most	cases,	the	collections	you	work	with	will	be	small,	containing	fewer	than
1,000	elements.	In	these	cases,	worrying	about	using	a	sequence	or	a	list	for	a
constrained	number	of	items	will	be	of	little	concern,	because	the	performance
difference	between	the	two	collection	types	will	be	negligible	–	on	the	order	of	a
few	nanoseconds.	But	with	more	sizable	collections,	with	hundreds	of	thousands
of	elements,	the	performance	improvement	to	be	realized	by	switching	the
collection	type	can	be	significant.	In	these	cases,	you	can	convert	a	list	to	a
sequence	quite	simply:
val	listOfNumbers	=	(0	until	10000000).toList()
val	sequenceOfNumbers	=	listOfNumbers.asSequence()
The	functional	programming	paradigm	can	require	frequent	creation	of	new
collections,	and	sequences	provide	a	scalable	mechanism	for	working	with	large
collections.
In	this	chapter,	you	saw	how	to	use	basic	functional	programming	tools	like
map,	flatMap,	and	filter	to	streamline	how	you	work	with	data.	You	also
saw	how	to	use	sequences	to	work	efficiently	as	your	data	set	grows	larger.
In	the	next	chapter,	you	will	learn	how	your	Kotlin	code	interoperates	with
Java	code	as	you	write	Kotlin	code	that	calls	Java	code	and	vice	versa.

For	the	More	Curious:	Profiling
When	the	speed	of	code	is	an	important	consideration,	Kotlin	provides	utility
functions	for	profiling	code	performance:	measureNanoTime	and
measureTimeInMillis.	Both	functions	accept	a	lambda	as	their	argument
and	measure	the	execution	speed	of	the	code	contained	within	the	lambda.
measureNanoTime	returns	a	time	in	nanoseconds,	and
measureTimeInMillis	returns	a	time	in	milliseconds.
Wrap	the	function	to	measure	in	one	of	the	utility	functions	like	so:
val	listInNanos	=	measureNanoTime	{
//	List	functional	chain	here
## }
val	sequenceInNanos	=	measureNanoTime	{
//	Sequence	functional	chain	here
## }
println("List	completed	in	$listInNanos	ns")
println("Sequence	completed	in	$sequenceInNanos	ns")
As	an	experiment,	try	profiling	the	performance	of	the	list	and	sequence	versions
of	the	prime	number	examples.	(Change	the	list	example	to	check	numbers
through	7,919	so	that	it	can	find	1,000	primes.)	How	much	does	the	change	from
a	list	to	a	sequence	affect	the	performance	time?

For	the	More	Curious:	Arrow.kt
In	this	chapter	you	saw	some	of	the	functional	programming-style	tools	that	are
included	in	Kotlin’s	standard	library,	like	map,	flatMap,	and	filter.
Kotlin	is	a	“multiparadigm”	language,	meaning	it	mixes	the	styles	of	object-
oriented,	imperative,	and	functional	programming.	If	you	have	worked	with	a
strictly	functional	programming	language	like	Haskell,	you	know	that	Haskell
offers	useful	functional	programming	ideas	that	go	further	than	the	basics
included	in	Kotlin.
For	example,	Haskell	includes	the	Maybe	type	–	a	type	that	includes	support	for
either	something	or	an	error	–	and	allows	operations	that	might	result	in	an	error
to	be	represented	using	a	type	instead.	Using	a	Maybe	type	allows	you	to
represent	an	exception,	like	incorrectly	parsing	a	number,	without	throwing	an
exception	–	which	allows	you	to	not	need	try/catch	logic	in	your	code.
Representing	an	exception	without	dealing	with	try/catch	logic	is	a	good	thing.
Some	view	try/catch	as	a	form	of	GOTO	statement:	More	often	than	not,	it	leads
to	code	that	is	difficult	to	read	and	maintain.
Many	of	the	functional	programming	features	found	in	Haskell	can	be	brought	to
Kotlin	through	libraries	like	Arrow.kt	(http://arrow-kt.io/).
For	example,	the	Arrow.kt	library	includes	a	flavor	of	the	Maybe	type	found	in
Haskell	called	Either.	Using	Either,	it	is	possible	to	represent	an	operation
that	could	result	in	failure	without	resorting	to	throwing	exceptions	and	try/catch
logic.
Consider,	for	example,	a	function	that	parses	some	user	input	from	a	string	to	an
Int.	If	the	value	is	a	number,	it	should	be	parsed	as	an	Int,	but	if	it	is	invalid,
it	should	instead	be	represented	as	an	error.
Using	Either,	the	logic	would	read	as	follows:
fun	parse(s:	String):	Either<NumberFormatException,	Int>	=
if	(s.matches(Regex("-?[0-9]+")))	{
Either.Right(s.toInt())
}	else	{
Either.Left(NumberFormatException("$s	is	not	a	valid	integer."))
## }
val	x	=	parse("123")
val	value	=	when(x)	{
is	Either.Left	->	when	(x.a)	{
is	NumberFormatException	->	"Not	a	number!"
else	->	"Unknown	error"
## }

is	Either.Right	->	"Number	that	was	parsed:	${x.b}"
## }
No	exceptions,	no	try/catch	blocks	–	just	easy-to-follow	logic.

Challenge:	Reversing	the	Values	in	a	Map
Using	the	functional	techniques	you	learned	in	this	chapter,	write	a	function
called	flipValues	that	allows	you	to	flip-flop	the	keys	and	values	in	a	map.
For	example:
val	gradesByStudent	=	mapOf("Josh"	to	4.0,	"Alex"	to	2.0,	"Jane"	to	3.0)
{Josh=4.0,	Alex=2.0,	Jane=3.0}
flipValues(gradesByStudent)
{4.0=Josh,	2.0=Alex,	3.0=Jane}

Challenge:	Applying	Functional	Programming	to
## Tavern.kt
Tavern.kt	could	be	improved	by	using	some	of	the	functional	programming
features	you	learned	about	in	this	chapter.
Consider	the	forEach	loop	that	you	use	to	generate	the	unique	patron	names:
val	uniquePatrons	=	mutableSetOf<String>()
fun	main(args:	Array<String>)	{
## ...
(0..9).forEach	{
val	first	=	patronList.random()
val	last	=	lastName.random()
val	name	=	"$first	$last"
uniquePatrons	+=	name
## }
## ...
## }
The	loop	mutates	the	state	of	the	uniquePatrons	set	every	iteration.	This
works	–	but	it	is	possible	to	do	better	using	a	functional	programming	approach.
You	might	express	the	uniquePatrons	set	like	this	instead:
val	uniquePatrons:	Set<String>	=	generateSequence	{
val	first	=	patronList.random()
val	last	=	lastName.random()
## "$first	$last"
}.take(10).toSet()
This	is	an	improvement	over	the	old	version,	because	the	mutable	set	is	no
longer	required	and	you	can	make	the	collection	read-only.
Notice	that	the	number	of	uniquePatrons	currently	varies,	depending	on
chance.	For	your	first	challenge,	use	the	generateSequence	function	to
generate	exactly	nine	unique	patron	names.	(Look	back	at	the	example	in	this
chapter	that	generated	exactly	1,000	prime	numbers	for	a	hint.)
For	a	second	challenge,	using	what	you	learned	in	this	section,	upgrade	the	code
in	Tavern.kt	that	populates	the	patron	gold	map	with	initial	values:
fun	main(args:	Array<String>)	{
## ...
uniquePatrons.forEach	{
patronGold[it]	=	6.0
## }
## ...
## }
The	new	version	should	perform	the	setup	for	the	patronGold	set	where	the
variable	is	defined,	rather	than	within	the	main	function.

## Challenge:	Sliding	Window
For	this	advanced	challenge,	begin	with	this	list	of	values:
val	valuesToAdd	=	listOf(1,	18,	73,	3,	44,	6,	1,	33,	2,	22,	5,	7)
Using	a	functional	programming	approach,	perform	the	following	operations	on
the	valuesToAdd	list:
- Exclude	any	number	less	than	5.
- Group	the	numbers	in	pairs.
- Multiply	the	two	numbers	in	each	pair.
- Sum	the	resulting	products	to	produce	a	final	number.
The	correct	result	is	2,339.	Walking	through	each	step,	here	is	what	the	data
should	look	like	along	the	way:
## Step	1:	1,	18,	73,	3,	44,	6,	1,	33,	2,	22,	5,	7
## Step	2:	18,	73,	44,	6,	33,	22,	5,	7
## Step	3:	[18*73],	[44*6],	[33*22],	[5*7]
## Step	4:	1314	+	264	+	726	+	35	=	2339
Notice	that	step	3	groups	the	list	into	sublists	of	two	elements	each	–	this	is
commonly	known	as	a	“sliding	window”	algorithm	(and	is	where	the	challenge
gets	its	name).	Solving	this	tricky	challenge	will	require	consulting	the	Kotlin
reference	documentation	–	particularly	the	collections	functions	at
kotlinlang.org/api/latest/jvm/stdlib/
kotlin.collections/index.html.	Good	luck!