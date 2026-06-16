

## 5
Anonymous	Functions	and	the
## Function	Type
In	the	last	chapter,	you	saw	how	to	define	functions	in	Kotlin	by	naming	them
and	how	to	call	them	by	name.	In	this	chapter,	you	will	see	another	way	to	define
functions:	anonymously.	You	will	be	taking	a	short	break	from	NyetHack	to
work	with	anonymous	functions	in	your	Sandbox	project,	but	do	not	worry	–
there	is	more	NyetHack	action	in	the	next	chapter.
Functions	like	the	ones	you	saw	and	wrote	in	Chapter	4	are	called	named
functions.	Functions	defined	without	a	name,	called	anonymous	functions,	are
similar,	with	two	major	differences:	Anonymous	functions	have	no	name	as	part
of	their	definition,	and	they	interact	with	the	rest	of	your	code	a	little	differently
in	that	they	are	commonly	passed	to	or	returned	from	other	functions.	These
interactions	are	made	possible	by	the	function	type	and	function	arguments,
which	you	will	also	learn	about	in	this	chapter.

## Anonymous	Functions
Anonymous	functions	are	an	essential	part	of	Kotlin.	One	way	they	are	used	is	to
allow	you	to	easily	customize	how	built-in	functions	from	the	Kotlin	standard
library	work	to	meet	your	particular	needs.	An	anonymous	function	lets	you
describe	additional	rules	for	a	standard	library	function	so	that	you	can
customize	its	behavior.	Let’s	look	at	an	example.
One	of	the	many	functions	in	the	standard	library	is	count.	When	called	on	a
string,	count	returns	the	total	number	of	letters	in	the	string.	The	following
code	counts	the	letters	in	the	string	"Mississippi":
val	numLetters	=	"Mississippi".count()
print(numLetters)
## //	Prints	11
(Here	you	have	used	dot	syntax	to	invoke	the	count	function.	This	syntax	is
used	any	time	you	invoke	a	function	that	is	included	as	part	of	a	type’s
definition.)
But	what	if	you	wanted	to	count	only	a	specific	character	in	"Mississippi",	say
the	letter	“s”?
For	this	kind	of	problem,	the	Kotlin	standard	library	allows	you	to	provide	rules
to	the	count	function	to	determine	whether	a	letter	should	be	counted.	You
describe	the	rules	for	the	function	by	providing	an	anonymous	function	as	an
argument.	It	looks	like	this:
val	numLetters	=	"Mississippi".count({	letter	->
letter	==	's'
## })
print(numLetters)
## //	Prints	4
Here,	the	Kotlin	String	count	function	uses	an	anonymous	function	to
decide	how	it	should	count	the	characters	in	the	string.	Proceeding	character	by
character,	if	the	anonymous	function	evaluates	as	true,	the	count	is	incremented.
Once	it	has	checked	every	character,	count	returns	the	final	number.
Anonymous	functions	let	the	standard	library	do	what	it	does	best	–	provide	a
foundation	of	functions	and	types	for	building	great	Kotlin	applications	–
without	including	features	that	would	be	too	specific	to	be	considered
“standard.”	They	also	have	other	uses,	which	you	will	see	later	in	this	chapter.
To	understand	how	count	works,	take	a	closer	look	at	Kotlin’s	anonymous
function	syntax	by	defining	your	own.	You	are	going	to	write	a	small	simulation

called	SimVillage,	a	game	that	allows	players	to	serve	as	mayor	of	a	virtual
village.
Your	first	anonymous	function	in	SimVillage	will	display	a	greeting	to	the
player,	acknowledging	them	as	mayor	of	the	village.	(Why	do	this	with	an
anonymous	function?	As	you	will	see	later	in	the	chapter,	this	will	allow	you	to
easily	pass	the	anonymous	function	to	other	functions.)
Open	your	Sandbox	project,	create	a	new	file	called	SimVillage.kt,	and
give	it	a	main	function,	as	you	have	done	before	(type	“main”	and	press	the	Tab
key).
Define	an	anonymous	function	within	the	main	function,	call	it,	and	print	the
result:
Listing	5.1		Defining	an	anonymous	greeting	function
(SimVillage.kt)
fun	main(args:	Array<String>)	{
println({
val	currentYear	=	2018
"Welcome	to	SimVillage,	Mayor!	(copyright	$currentYear)"
## }())
## }
Just	as	you	write	a	string	by	putting	characters	between	opening	and	closing
quotes,	you	write	a	function	by	putting	an	expression	or	statements	between
opening	and	closing	curly	braces.	Here,	you	begin	with	a	call	to	println.
Inside	the	parentheses	that	enclose	println’s	argument,	you	define	an
anonymous	function	inside	a	set	of	curly	braces.	The	anonymous	function
defines	a	variable	and	returns	a	greeting	message	string:
## {
val	currentYear	=	2018
"Welcome	to	SimVillage,	Mayor!	(copyright	$currentYear)"
## }
Outside	the	anonymous	function’s	closing	brace,	you	call	the	function	with	a
pair	of	empty	parentheses.	If	you	were	to	leave	the	parentheses	off	the	end	of	the
anonymous	function,	the	greeting	message	string	would	not	print.	Just	like	a
named	function,	an	anonymous	function	does	its	work	only	when	it	has	been
called,	using	parentheses	along	with	any	arguments	the	function	expects	(zero,	in
this	case):
## {
val	currentYear	=	2018
"Welcome	to	SimVillage,	Mayor!	(copyright	$currentYear)"
## }()
Run	SimVillage.kt’s	main	function.	You	will	see	the	following	output:
Welcome	to	SimVillage,	Mayor!	(copyright	2018)

The	function	type
In	Chapter	2,	you	learned	about	data	types	like	Int	and	String.	Anonymous
functions	also	have	a	type,	called	the	function	type.	Variables	of	the	function
type	can	hold	an	anonymous	function	as	their	value,	and	the	function	can	then	be
passed	around	your	code	like	any	other	variable.
(Do	not	confuse	the	function	type	with	a	type	called	Function.	You	define	the
specifics	of	a	function	using	a	function	type	declaration,	which	varies	depending
on	the	details	of	a	particular	function’s	input,	output,	and	parameters,	as	you	will
soon	see.)
Update	SimVillage.kt	to	define	a	variable	that	holds	a	function,	and	assign
it	the	anonymous	function	that	displays	the	greeting.	There	is	some	unfamiliar
syntax	here,	which	we	will	explain	after	you	enter	it.
Listing	5.2		Assigning	the	anonymous	function	to	a	variable
(SimVillage.kt)
fun	main(args:	Array<String>)	{
println({
val	greetingFunction:	()	->	String	=	{
val	currentYear	=	2018
"Welcome	to	SimVillage,	Mayor!	(copyright	$currentYear)"
## }
## })()
println(greetingFunction())
## }
You	can	declare	a	variable	with	its	name	followed	by	a	colon	and	its	type.	That
is	exactly	what	you	have	done	here	with	greetingFunction:	()	->	String.
And	just	as	:	Int	tells	the	compiler	what	kind	of	data	a	variable	can	hold	(an
integer),	the	function	type	:	()	->	String	tells	the	compiler	what	kind	of
function	a	variable	can	hold.
A	function	type	definition	consists	of	two	parts:	the	function’s	parameters,	in
parentheses,	followed	by	its	return	type,	delimited	by	the	arrow	(->),	as	shown	in
## Figure	5.1.
Figure	5.1		Function	type	syntax

The	type	declaration	you	specified	for	the	variable	greetingFunction,	()	-
>	String,	indicates	to	the	compiler	that	greetingFunction	can	be	assigned
any	function	that	accepts	no	arguments	(indicated	by	the	empty	parentheses)	and
returns	a	String.	As	with	any	other	type	declaration	for	a	variable,	the
compiler	will	ensure	that	the	function	assigned	to	the	variable	or	passed	as	an
argument	is	of	the	correct	type.
Run	main.	The	output	is	the	same:
Welcome	to	SimVillage,	Mayor!	(copyright	2018)
Implicit	returns
You	may	have	noticed	that	there	is	no	return	keyword	within	the	anonymous
function	you	defined:
val	greetingFunction:	()	->	String	=	{
val	currentYear	=	2018
"Welcome	to	SimVillage,	Mayor!	(copyright	$currentYear)"
## }
However,	the	function	type	you	specified	indicates	that	the	function	must	return
a	String,	and	the	compiler	did	not	complain.	And,	based	on	the	output,	a
string	is	indeed	returned:	the	greeting	to	the	mayor.	Why,	then,	is	there	no
return	keyword?
Unlike	a	named	function,	an	anonymous	function	does	not	require	–	or	even
allow,	except	in	rare	cases	–	the	return	keyword	to	output	data.	Anonymous
functions	implicitly,	or	automatically,	return	the	last	line	of	their	function
definition,	allowing	you	to	omit	the	return	keyword.
This	feature	of	anonymous	functions	is	both	a	convenience	and	a	necessity	of	the
anonymous	function	syntax.	The	return	keyword	is	prohibited	in	an	anonymous
function	because	it	could	be	ambiguous	to	the	compiler	which	function	the
return	is	from:	the	function	the	anonymous	function	was	invoked	within,	or	the
anonymous	function	itself.
Function	arguments
Like	a	named	function,	an	anonymous	function	can	accept	zero,	one,	or	multiple
arguments	of	any	type.	The	parameters	an	anonymous	function	accepts	are
indicated	by	type	in	the	function	type	definition	and	then	named	in	the
anonymous	function’s	definition.
Update	the	greetingFunction	variable	declaration	to	accept	the	player’s

name	as	an	argument:
Listing	5.3		Adding	a	playerName	parameter	to	the	anonymous
function	(SimVillage.kt)
fun	main(args:	Array<String>)	{
val	greetingFunction:	()	->	String	=	{
val	greetingFunction:	(String)	->	String	=	{	playerName	->
val	currentYear	=	2018
"Welcome	to	SimVillage,	Mayor!	(copyright	$currentYear)"
"Welcome	to	SimVillage,	$playerName!	(copyright	$currentYear)"
## }
println(greetingFunction())
println(greetingFunction("Guyal"))
## }
Here	you	specify	that	the	anonymous	function	accepts	a	String.	You	name	the
string	parameter	within	the	function,	right	after	the	opening	brace,	and	follow	the
name	with	an	arrow:
val	greetingFunction:	(String)	->	String	=	{	playerName	->
Run	SimVillage.kt	again.	You	will	see	that	the	argument	you	passed	to	the
anonymous	function	was	added	to	the	string:
Welcome	to	SimVillage,	Guyal!	(copyright	2018)
Remember	the	count	function?	It	takes	in	an	anonymous	function	with	an
argument	called	predicate	of	type	(Char)	->	Boolean.	The	predicate
function	type	takes	a	Char	as	an	argument	and	returns	a	Boolean.	You	will
see	anonymous	functions	used	to	implement	much	of	the	Kotlin	standard	library,
so	it	is	best	to	familiarize	yourself	with	their	syntax.
The	it	keyword
When	defining	anonymous	functions	that	accept	exactly	one	argument,	the	it
keyword	is	available	as	a	convenient	alternative	to	specifying	the	parameter
name.	Both	it	and	a	named	parameter	are	valid	when	you	have	an	anonymous
function	that	has	only	one	parameter.
Delete	the	parameter	name	and	arrow	from	the	beginning	of	the	anonymous
function	and	use	the	it	keyword	instead:
Listing	5.4		Using	the	it	keyword	(SimVillage.kt)
fun	main(args:	Array<String>)	{
val	greetingFunction:	(String)	->	String	=	{	playerName	->
val	greetingFunction:	(String)	->	String	=	{
val	currentYear	=	2018
"Welcome	to	SimVillage,	$playerName!	(copyright	$currentYear)"
"Welcome	to	SimVillage,	$it!	(copyright	$currentYear)"
## }
println(greetingFunction("Guyal"))
## }

Run	SimVillage.kt	to	confirm	that	it	works	as	before.
it	is	convenient	in	that	it	requires	no	variable	naming,	but	notice	that	it	is	not
very	descriptive	about	the	data	it	represents.	We	suggest	that	when	you	are
working	with	more	complex	anonymous	function	definitions,	or	with	nested
anonymous	functions	(anonymous	functions	within	anonymous	functions),	you
stick	with	naming	the	parameter	to	preserve	future	readers’	(and	your	own)
sanity.	On	the	other	hand,	it	is	great	for	shorter	expressions,	like	the	count
function	you	saw	earlier,	where	the	logic	is	clear	even	without	an	argument
name:
"Mississippi".count({	it	==	's'	})
Accepting	multiple	arguments
While	the	it	syntax	is	available	for	an	anonymous	function	that	accepts	one
argument,	it	is	not	allowed	when	there	is	more	than	one	argument.	However,
anonymous	functions	can	certainly	accept	multiple	named	arguments.
It	is	time	for	SimVillage	to	do	something	besides	greet	its	mayor.	The	mayor
needs	to	know	whether	the	village	is	growing,	for	example.	Change	your
anonymous	function	to	accept	a	numBuildings	argument,	representing	the
number	of	houses	or	shops	constructed,	in	addition	to	the	player’s	name:
Listing	5.5		Accepting	a	second	argument	(SimVillage.kt)
fun	main(args:	Array<String>)	{
val	greetingFunction:	(String)	->	String	=	{
val	greetingFunction:	(String,	Int)	->	String	=	{	playerName,	numBuildings	->
val	currentYear	=	2018
println("Adding	$numBuildings	houses")
"Welcome	to	SimVillage,	$it!	(copyright	$currentYear)"
"Welcome	to	SimVillage,	$playerName!	(copyright	$currentYear)"
## }
println(greetingFunction("Guyal"))
println(greetingFunction("Guyal",	2))
## }
The	expression	now	declares	two	parameters,	playerName	and
numBuildings,	and	accepts	two	arguments	when	called.	Because	there	is
more	than	one	parameter	defined	for	the	expression,	the	it	keyword	is	no	longer
available.
Run	SimVillage	again.	This	time,	you	will	see	the	number	of	buildings
constructed	as	well	as	the	greeting:
Adding	2	houses
Welcome	to	SimVillage,	Guyal!	(copyright	2018)

## Type	Inference	Support
Kotlin’s	type	inference	rules	behave	exactly	the	same	with	function	types	as	they
do	with	the	types	you	met	earlier	in	this	book:	If	a	variable	is	given	an
anonymous	function	as	its	value	when	it	is	declared,	no	explicit	type	definition	is
needed.
This	means	that	the	anonymous	function	you	wrote	earlier	that	accepted	no
arguments:
val	greetingFunction:	()	->	String	=	{
val	currentYear	=	2018
"Welcome	to	SimVillage,	Mayor!	(copyright	$currentYear)"
## }
Could	also	have	been	written	with	no	specified	type,	like	this:
val	greetingFunction	=	{
val	currentYear	=	2018
"Welcome	to	SimVillage,	Mayor!	(copyright	$currentYear)"
## }
Type	inference	is	also	an	option	when	the	anonymous	function	accepts	one	or
more	arguments,	but	to	help	the	compiler	infer	the	type	of	the	variable,	you	do
need	to	provide	both	the	name	and	the	type	of	each	parameter	in	the	anonymous
function	definition.
Update	the	greetingFunction	variable	to	use	type	inference	by	including
types	for	each	parameter	in	the	anonymous	function.
Listing	5.6		Using	type	inference	for	greetingFunction
(SimVillage.kt)
fun	main()	{
val	greetingFunction:	(String,	Int)	->	String	=	{	playerName,	numBuildings	->
val	greetingFunction	=	{	playerName:	String,	numBuildings:	Int	->
val	currentYear	=	2018
println("Adding	$numBuildings	houses")
"Welcome	to	SimVillage,	$playerName!	(copyright	$currentYear)"
## }
println(greetingFunction("Guyal",	2))
## }
Run	SimVillage.kt	and	confirm	that	it	works	just	as	before.
When	combined	with	an	ambiguous	implicit	return	type,	type	inference	may
make	an	anonymous	function	difficult	to	read.	But	when	your	anonymous
function	is	simple	and	clear,	type	inference	is	an	asset	for	making	your	code
more	concise.

Defining	a	Function	That	Accepts	a	Function
You	have	seen	that	anonymous	functions	can	customize	the	work	of	standard
library	functions.	You	can	also	use	them	in	functions	you	write	yourself.
By	the	way,	from	here	on	out,	we	will	refer	to	anonymous	functions	as	lambdas
and	their	definitions	as	lambda	expressions.	We	will	also	refer	to	what	an
anonymous	function	returns	as	a	lambda	result.	This	is	common	terminology
you	will	encounter	in	the	wild	as	well.	(A	bit	of	trivia:	Why	“lambda”?	The	term,
also	represented	with	the	Greek	character	λ,	is	short	for	“lambda	calculus”	–	a
system	of	logic	for	expressing	computations,	devised	in	the	1930s	by
mathematician	Alonzo	Church.	You	use	lambda	calculus	notation	when	you
define	an	anonymous	function.)
A	function	parameter	can	accept	arguments	of	any	type,	including	arguments	that
are	functions.	A	function	type	parameter	is	defined	like	a	parameter	of	any	other
type:	You	list	it	in	the	parentheses	after	the	function	name	and	include	the	type.
To	see	how	this	works,	you	are	going	to	add	a	new	function	to	SimVillage	that
randomly	decides	how	many	buildings	have	been	constructed,	then	invokes	the
lambda	to	display	the	greeting.
Add	a	function	called	runSimulation	that	accepts	the	playerName	and
greetingFunction	variables.	You	will	use	a	couple	of	standard	library
functions	that	we	have	provided	for	you	to	generate	a	random	number.	Finally,
call	the	new	runSimulation	function.
Listing	5.7		Adding	the	runSimulation	function	(SimVillage.kt)
fun	main(args:	Array<String>)	{
val	greetingFunction	=	{	playerName:	String,	numBuildings:	Int	->
val	currentYear	=	2018
println("Adding	$numBuildings	houses")
"Welcome	to	SimVillage,	$playerName!	(copyright	$currentYear)"
## }
println(greetingFunction("Guyal",	2))
runSimulation("Guyal",	greetingFunction)
## }
fun	runSimulation(playerName:	String,	greetingFunction:	(String,	Int)	->	String)	{
val	numBuildings	=	(1..3).shuffled().last()			//	Randomly	selects	1,	2,	or	3
println(greetingFunction(playerName,	numBuildings))
## }
The	two	parameters	to	runSimulation	are	the	player’s	name	and
greetingFunction,	a	function	that	accepts	a	String	and	Int	and	returns
a	String.	runSimulation	generates	a	random	number	and	calls	the
function	passed	as	greetingFunction	with	the	generated	number	and	the

playerName	(which	runSimulation	received	as	an	argument).
Run	SimVillage	several	times.	You	will	see	that	the	number	of	buildings
constructed	varies	now,	because	runSimulation	provides	a	random	number
to	the	greeting	function.
Shorthand	syntax
When	a	function	accepts	a	function	type	for	its	last	parameter,	you	can	also	omit
the	parentheses	around	the	lambda	argument.	So	this	example	that	we	showed
you	earlier:
"Mississippi".count({	it	==	's'	})
Can	also	be	written	this	way,	without	the	parentheses:
"Mississippi".count	{	it	==	's'	}
This	syntax	is	cleaner	to	read	and	gets	to	the	essential	ingredients	of	your
function	call	just	a	bit	more	quickly.
This	simplification	can	be	made	only	when	a	lambda	is	passed	as	the	last
argument	into	a	function.	When	writing	functions,	declare	function	type
parameters	as	the	final	parameter	so	that	callers	of	your	function	can	take
advantage	of	this	pattern.
In	SimVillage,	you	can	take	advantage	of	this	shorthand	with	the
runSimulation	function	you	defined.	runSimulation	expects	two
arguments:	a	string	and	a	function.	Provide	the	arguments	that	are	not	functions
to	runSimulation	inside	of	parentheses.	Then,	list	the	last	argument,	the
function,	outside	of	the	parentheses:
Listing	5.8		Passing	a	lambda	with	the	shorthand	syntax
(SimVillage.kt)
fun	main(args:	Array<String>)	{
val	greetingFunction	=	{	playerName:	String,	numBuildings:	Int	->
runSimulation("Guyal")	{	playerName,	numBuildings	->
val	currentYear	=	2018
println("Adding	$numBuildings	houses")
"Welcome	to	SimVillage,	$playerName!	(copyright	$currentYear)"
## }
## }
fun	runSimulation(playerName:	String,	greetingFunction:	(String,	Int)	->	String)	{
val	numBuildings	=	(1..3).shuffled().last()			//	Randomly	selects	1,	2,	or	3
println(greetingFunction(playerName,	numBuildings))
## }
Nothing	changed	in	the	implementation	of	runSimulation;	all	changes	were
on	how	it	is	called.	Notice	that	because	you	are	no	longer	assigning	the	lambda
to	a	variable	and	are	instead	directly	passing	it	to	runSimulation,	listing	the

types	for	the	parameters	in	the	lambda	is	no	longer	required.
This	shorthand	syntax	empowers	you	to	write	cleaner	code,	and	we	will	leverage
it	where	applicable	in	this	book.

## Function	Inlining
Lambdas	are	useful	because	they	enable	a	high	degree	of	flexibility	in	how	your
programs	can	be	written.	However,	that	flexibility	comes	at	a	cost.
When	you	define	a	lambda,	it	is	represented	as	an	object	instance	on	the	JVM.
The	JVM	also	performs	memory	allocations	for	all	variables	accessible	to	the
lambda,	and	this	behavior	comes	with	associated	memory	costs.	As	a	result,
lambdas	introduce	memory	overhead	that	can	in	turn	cause	a	performance
impact	–	and	such	performance	impacts	are	to	be	avoided.
Fortunately,	there	is	an	optimization	you	can	enable	that	removes	the	overhead
when	using	lambdas	as	arguments	to	other	functions,	called	inlining.	Inlining
removes	the	need	for	the	JVM	to	use	an	object	instance	and	to	perform	variable
memory	allocations	for	the	lambda.
To	inline	a	lambda,	you	mark	the	function	that	accepts	the	lambda	using	the
inline	keyword.	Add	the	inline	keyword	to	the	runSimulation	function:
Listing	5.9		Using	the	inline	keyword	(SimVillage.kt)
## ...
inline	fun	runSimulation(playerName:	String,
greetingFunction:	(String,	Int)	->	String)	{
val	numBuildings	=	(1..3).shuffled().last()			//	Randomly	selects	1,	2,	or	3
println(greetingFunction(playerName,	numBuildings))
## }
Now	that	you	have	added	the	inline	keyword,	instead	of	invoking
runSimulation	with	a	lambda	object	instance,	the	compiler	“copy	and
pastes”	the	function	body	where	the	call	is	made.	Take	a	look	at	the	decompiled
Kotlin	bytecode	for	SimVillage.kt’s	main	function,	where	the	(now
inlined)	runSimulation	function	is	called:
## ...
public	static	final	void	main(@NotNull	String[]	args)	{
Intrinsics.checkParameterIsNotNull(args,	"args");
String	playerName$iv	=	"Guyal";
byte	var2	=	1;
int	numBuildings$iv	=
((Number)CollectionsKt.last(CollectionsKt.shuffled((Iterable)
(new	IntRange(var2,	3))))).intValue();
int	currentYear	=	2018;
String	var7	=	"Adding	"	+	numBuildings$iv	+	"	houses";
## System.out.println(var7);
String	var10	=	"Welcome	to	SimVillage,	"	+	playerName$iv	+	"!
(copyright	"	+	currentYear	+	')';
## System.out.println(var10);
## }
## ...
Notice	that	instead	of	invoking	the	runSimulation	function,	the	work	that

runSimulation	performed	with	the	lambda	is	now	directly	inlined	into	the
main	function,	avoiding	the	need	to	pass	any	lambda	at	all	(and	so	avoiding	the
need	for	a	new	object	instance).
It	is	generally	a	good	idea	to	mark	functions	that	accept	lambdas	as	arguments
with	the	inline	keyword.	However,	in	a	few	limited	instances,	it	is	not	possible.
One	situation	where	inlining	is	not	permitted,	for	example,	is	a	recursive
function	that	accepts	a	lambda,	since	the	result	of	inlining	such	a	function	would
be	an	infinite	loop	of	copying	and	pasting	function	bodies.	The	compiler	will
warn	you	if	you	try	to	inline	a	function	that	violates	the	rules.

## Function	References
So	far,	you	have	defined	lambdas	to	provide	a	function	as	an	argument	to
another	function.	There	is	another	way	to	do	so:	by	passing	a	function	reference.
A	function	reference	converts	a	named	function	(a	function	defined	using	the	fun
keyword)	to	a	value	that	can	be	passed	as	an	argument.	You	can	use	a	function
reference	anywhere	you	use	a	lambda	expression.
To	see	a	function	reference,	start	by	defining	a	new	function,	called
printConstructionCost:
Listing	5.10		Defining	the	printConstructionCost	function
(SimVillage.kt)
## ...
inline	fun	runSimulation(playerName:	String,
greetingFunction:	(String,	Int)	->	String)	{
val	numBuildings	=	(1..3).shuffled().last()			//	Randomly	selects	1,	2,	or	3
println(greetingFunction(playerName,	numBuildings))
## }
fun	printConstructionCost(numBuildings:	Int)	{
val	cost	=	500
println("construction	cost:	${cost	*	numBuildings}")
## }
Now,	add	a	function	parameter	to	runSimulation	called	costPrinter,
and	use	the	value	within	runSimulation	to	print	the	construction	cost	for	the
buildings.
Listing	5.11		Adding	a	costPrinter	parameter	(SimVillage.kt)
## ...
inline	fun	runSimulation(playerName:	String,
greetingFunction:	(String,	Int)	->	String)	{
inline	fun	runSimulation(playerName:	String,
costPrinter:	(Int)	->	Unit,
greetingFunction:	(String,	Int)	->	String)	{
val	numBuildings	=	(1..3).shuffled().last()			//	Randomly	selects	1,	2,	or	3
costPrinter(numBuildings)
println(greetingFunction(playerName,	numBuildings))
## }
fun	printConstructionCost(numBuildings:	Int)	{
val	cost	=	500
println("construction	cost:	${cost	*	numBuildings}")
## }
To	obtain	a	function	reference,	you	use	the	::	operator	with	the	function	name
you	would	like	a	reference	for.	Obtain	a	function	reference	for	the
printConstructionCost	function	and	pass	the	reference	as	the	argument
for	the	new	costPrinter	parameter	you	defined	on	runSimulation:

Listing	5.12		Passing	a	function	reference	(SimVillage.kt)
fun	main(args:	Array<String>)	{
runSimulation("Guyal")	{	playerName,	numBuildings	->
runSimulation("Guyal",	::printConstructionCost)	{	playerName,	numBuildings	->
val	currentYear	=	2018
println("Adding	$numBuildings	houses")
"Welcome	to	SimVillage,	$playerName!	(copyright	$currentYear)"
## }
## }
## ...
Run	SimVillage.kt.	You	will	see	that	in	addition	to	the	number	of	buildings
constructed,	the	total	cost	of	the	construction	prints	as	well.
Function	references	are	useful	in	a	number	of	situations.	If	you	have	a	named
function	that	fits	the	needs	of	a	parameter	that	requires	a	function	argument,	a
function	reference	allows	you	to	use	it	instead	of	defining	a	lambda.	Or	you	may
want	to	use	a	Kotlin	standard	library	function	as	an	argument	to	a	function.	You
will	see	more	examples	of	both	of	these	uses	of	function	references	in	Chapter	9.

Function	Type	as	Return	Type
Like	any	other	type,	the	function	type	is	also	a	valid	return	type,	meaning	you
can	define	a	function	that	returns	a	function.
In	SimVillage,	define	a	configureGreetingFunction	function	that
configures	the	arguments	for	the	lambda	held	by	the	greetingFunction
variable	and	generates	and	then	returns	the	lambda,	ready	for	use:
Listing	5.13		Adding	the	configureGreetingFunction	function
(SimVillage.kt)
fun	main(args:	Array<String>)	{
runSimulation("Guyal",	::printContructionCost)	{	playerName,	numBuildings	->
val	currentYear	=	2018
println("Adding	$numBuildings	houses")
"Welcome	to	SimVillage,	$playerName!	(copyright	$currentYear)"
## }
runSimulation()
## }
inline	fun	runSimulation(playerName:	String,
costPrinter:	(Int)	->	Unit,
greetingFunction:	(String,	Int)	->	String)	{
val	numBuildings	=	(1..3).shuffled().last()			//	Randomly	selects	1,	2,	or	3
costPrinter(numBuildings)
println(greetingFunction(playerName,	numBuildings))
fun	runSimulation()	{
val	greetingFunction	=	configureGreetingFunction()
println(greetingFunction("Guyal"))
## }
fun	configureGreetingFunction():	(String)	->	String	{
val	structureType	=	"hospitals"
var	numBuildings	=	5
return	{	playerName:	String	->
val	currentYear	=	2018
numBuildings	+=	1
println("Adding	$numBuildings	$structureType")
"Welcome	to	SimVillage,	$playerName!	(copyright	$currentYear)"
## }
## }
You	might	think	of	configureGreetingFunction	as	a	“function	factory”
–	a	function	that	sets	up	another	function.	It	declares	the	necessary	variables	and
assembles	them	in	a	lambda	that	it	then	returns	to	its	caller,	runSimulation.
Run	SimVillage.kt	again.	The	number	of	hospitals	built	is	incremented	and
displayed:
Adding	6	hospitals
Welcome	to	SimVillage,	Guyal!	(copyright	2018)
Both	numBuildings	and	structureType,	local	variables	defined	within
configureGreetingFunction,	were	used	by	the	lambda	that
configureGreetingFunction	returns,	even	though	they	were	defined	in
the	outer	function	the	lambda	was	returned	from.	This	works	because	lambdas	in

Kotlin	are	what	are	called	closures	–	they	“close	over”	the	variables	in	the	outer
scope	that	they	are	defined	within.	To	learn	more	about	closures,	take	a	look	at
the	section	called	For	the	More	Curious:	Kotlin’s	Lambdas	Are	Closures,	below.
A	function	that	accepts	or	returns	another	function	is	sometimes	also	referred	to
as	a	higher-order	function.	The	terminology	is	borrowed	from	the	same	area	of
mathematics	the	term	lambda	is	borrowed	from.	Higher-order	functions	are	used
extensively	in	a	style	of	programming	called	functional	programming,	which
you	will	learn	about	in	Chapter	19.
In	this	chapter,	you	have	learned	how	lambdas	(AKA	anonymous	functions)	are
used	to	customize	Kotlin	standard	library	functions	and	how	to	define	your	own.
You	have	also	learned	how	functions	behave	like	any	other	type	in	Kotlin	and
how	they	can	be	used	as	arguments	or	returned	by	functions	that	you	define.
In	the	next	chapter,	you	will	see	how	Kotlin	helps	prevent	programming
mistakes	by	enforcing	nullability	in	its	type	system.	You	will	also	return	to
NyetHack	and	begin	building	a	tavern	in	the	game.

For	the	More	Curious:	Kotlin’s	Lambdas	Are
## Closures
In	Kotlin,	an	anonymous	function	can	modify	and	reference	variables	defined
outside	of	its	scope.	This	means	that	an	anonymous	function	has	a	reference	to
the	variables	defined	in	the	scope	where	it	is	itself	created	–	as	in	the	case	of	the
configureGreetingFunction	function	you	saw	earlier.
As	a	demonstration	of	this	property	of	anonymous	functions,	update	the
runSimulation	function	to	call	the	function	returned	from
configureGreetingFunction	multiple	times:
Listing	5.14		Calling	println	twice	in	runSimulation
(SimVillage.kt)
## ...
fun	runSimulation()	{
val	greetingFunction	=	configureGreetingFunction()
println(greetingFunction("Guyal"))
println(greetingFunction("Guyal"))
## }
## ...
Run	SimVillage	again.	You	will	see	the	following	output:
building	6	hospitals
Welcome	to	SimVillage,	Guyal!	(copyright	2018)
building	7	hospitals
Welcome	to	SimVillage,	Guyal!	(copyright	2018)
Though	the	numBuildings	variable	is	defined	outside	of	the	anonymous
function,	the	anonymous	function	has	access	to	the	variable	and	can	modify	it.
Therefore,	the	numBuildings	value	increments	from	6	to	7.

For	the	More	Curious:	Lambdas	vs	Anonymous
## Inner	Classes
If	you	have	not	used	function	types	before,	you	may	wonder	why	you	would
want	to	use	them	in	your	program.	Our	answer:	Function	types	offer	increased
flexibility	with	less	boilerplate.	Consider	a	language	that	does	not	offer	function
types,	like	Java	8.
Java	8	includes	support	for	object-oriented	programming	and	lambda
expressions	but	does	not	include	the	ability	to	define	a	function	as	a	parameter	to
a	function	or	variable.	Instead,	Java	provides	anonymous	inner	classes	–
nameless	classes	that	are	defined	within	another	class	to	implement	a	single
method	definition.	You	can	pass	anonymous	inner	classes	as	an	instance,	like	a
lambda.	For	example,	in	Java	8,	to	pass	the	definition	for	a	single	method,	you
would	write:
Greeting	greeting	=	(playerName,	numBuildings)	->	{
int	currentYear	=	2018;
System.out.println("Adding	"	+	numBuildings	+	"	houses");
return	"Welcome	to	SimVillage,	"	+	playerName	+
"!	(copyright	"	+	currentYear	+	")";
## };
public	interface	Greeting	{
String	greet(String	playerName,	int	numBuildings);
## }
greeting.greet("Guyal",	6);
On	the	surface,	this	seems	mostly	equivalent	to	what	Kotlin	provides:	the	ability
to	pass	lambda	expressions.	But	if	you	dig	deeper,	you	will	find	that	Java
requires	the	definition	of	named	interfaces	or	classes	to	represent	the	functions
the	lambda	will	define,	even	though	instances	of	those	types	appear	to	be	written
in	almost	the	same	shorthand	Kotlin	allows.	If	you	would	like	to	simply	pass	a
function	without	defining	an	interface,	you	will	find	Java	does	not	support	this
concise	syntax.
For	example,	take	a	look	at	the	Runnable	interface	in	Java:
public	interface	Runnable	{
public	abstract	void	run();
## }
This	Java	8	lambda	declaration	requires	an	interface	definition.	In	Kotlin,	this
extra	effort	to	describe	a	single	abstract	method	is	not	required.	The	following	is
possible	in	Kotlin	and	is	functionally	equivalent	to	the	Java	code:
fun	runMyRunnable(runnable:	()	->	Unit)	=	{	runnable()	}
runMyRunnable	{	println("hey	now")	}

Combine	this	more	precise	syntax	with	the	other	features	you	learned	about	in
the	chapter	–	implicit	returns,	the	it	keyword,	and	the	closure	behavior	–	and
you	have	a	nice	improvement	on	manually	defining	inner	classes	to	implement	a
single	method.
The	flexibility	Kotlin	provides	by	including	functions	as	first-class	citizens	frees
you	to	spend	your	time	on	more	valuable	pursuits	than	writing	boilerplate	–	like
getting	your	work	done.