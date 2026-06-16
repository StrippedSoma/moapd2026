

## 6
Null	Safety	and	Exceptions
Null	is	a	special	value	that	indicates	that	the	value	of	a	var	or	val	does	not	exist.
In	many	programming	languages,	including	Java,	null	is	a	common	cause	of
crashes,	because	a	nonexistent	value	cannot	be	asked	to	do	anything.	Kotlin
requires	a	specific	declaration	if	a	var	or	val	can	accept	null	as	a	value,	which
helps	avoid	this	type	of	crash.
In	this	chapter,	you	will	learn	why	null	causes	a	crash,	how	Kotlin	protects
against	null	by	default	at	compile	time,	and	how	to	safely	work	with	nullable
values	in	Kotlin	when	you	require	them.	You	will	also	see	how	to	work	with
what	are	called	exceptions	in	Kotlin,	indicators	that	something	went	wrong	in
your	program.
To	see	these	issues	in	action,	you	will	be	updating	the	NyetHack	project.	You
will	add	a	tavern	to	the	game	that	accepts	user	input	and	attempts	to	fulfill
custom	drink	requests	for	its	choosy	patrons.	You	will	also	add	a	dangerous
sword	juggling	feature.

## Nullability
Some	elements	in	Kotlin	can	be	assigned	a	value	of	null,	and	some	cannot.	We
say	that	the	former	are	nullable	and	the	latter	are	non-nullable.	For	example,
while	you	might	want	a	variable	in	NyetHack	that	tracks	a	player’s	steed	to	be
nullable,	since	not	every	player	will	ride	a	steed,	you	would	not	want	the	health
points	variable	to	be	null.	Every	player	has	to	have	an	associated	health	points
value;	it	would	be	illogical	for	them	not	to.	Its	value	might	be	0,	but	0	is	not	the
same	as	null	–	null	is	the	absence	of	any	value.
Open	NyetHack	and	create	a	new	file	called	Tavern.kt.	Add	a	main
function,	where	your	code	will	begin	executing.
Before	opening	the	tavern	to	custom	drink	requests	from	users,	first	try	an
experiment.	Start	by	adding	a	familiar-looking	var	assignment	to	main,	and
then	reassigning	the	variable’s	value	to	null:
Listing	6.1		Reassigning	a	var’s	value	to	null	(Tavern.kt)
fun	main(args:	Array<String>)	{
var	signatureDrink	=	"Buttered	Ale"
signatureDrink	=	null
## }
Even	before	you	execute	this	code,	IntelliJ	warns	you	with	a	red	underline	that
something	is	amiss.	Run	it	anyway,	and	you	will	see:
Null	can	not	be	a	value	of	a	non-null	type	String
Kotlin	prevents	the	assignment	of	null	to	the	signatureDrink	variable,
because	it	is	a	non-null	type	(String).	A	non-null	type	is	one	that	does	not
support	the	assignment	of	null.	The	current	definition	of	signatureDrink	is
guaranteed	to	be	a	string,	rather	than	null.
If	you	have	worked	with	Java	before,	this	behavior	is	different	than	what	you
may	be	familiar	with.	In	Java,	the	following	code	is	permitted,	for	example.
String	signatureDrink	=	"Buttered	Ale";
signatureDrink	=	null;
Reassigning	signatureDrink	to	a	value	of	null	is	fine	in	Java.	But	what	do
you	think	would	happen	if	you	asked	Java	to	concatenate	a	string	to	the	null
signatureDrink	variable?
String	signatureDrink	=	"Buttered	Ale";
signatureDrink	=	null;
signatureDrink	=	signatureDrink	+	",	large";
In	fact,	this	code	will	cause	an	exception	that	will	crash	the	program,	called	a

NullPointerException.
The	Java	code	crashes	because	a	nonexistent	String	has	been	asked	to	perform
string	concatenation.	This	is	an	impossible	request.	(If	you	are	confused	about
why	a	value	of	null	is	not	the	same	as	an	empty	string,	this	example	shows	the
difference.	A	value	of	null	means	that	the	variable	does	not	exist.	An	empty
string	means	that	the	variable	exists	and	has	a	value	of	"",	which	could	easily
concatenate	with	",	large".)
Java	and	many	other	programming	languages	support	exactly	this	pseudo-code
statement:	“Hey,	nonexistent	string,	do	string	concatenation.”	In	those	languages,
the	value	of	any	variable	can	be	null	(with	the	exception	of	primitives,	which
Kotlin	does	not	support).	In	languages	that	allow	null	for	any	type,
NullPointerExceptions	are	a	common	source	of	application	crashes.
Kotlin	takes	the	opposite	position	on	the	problem	of	null.	Unless	otherwise
specified,	a	variable	cannot	be	assigned	a	value	of	null.	This	guards	against	the
problem	of	“Hey,	nonexistent	thing,	do	something”	at	compile	time,	rather	than
crashing	at	runtime.

## Kotlin’s	Explicit	Null	Type
NullPointerExceptions	like	the	one	that	you	saw	above	should	be
avoided	at	all	costs.	Kotlin	protected	you	by	preventing	you	from	assigning	a
null	value	to	a	variable	of	a	non-nullable	type.	That	said,	nullness	does	still	exist
in	Kotlin.
Here	is	an	example,	from	the	header	for	the	function	called	readLine.
readLine	accepts	user	input	from	the	console	and	returns	it	so	that	it	can	be
used	later.
public	fun	readLine():	String?
readLine’s	header	looks	like	one	that	you	have	seen	before,	with	one
exception:	the	return	type	String?.	The	question	mark	represents	a	nullable
version	of	a	type.	That	means	readLine	will	either	return	a	value	of	type
String,	or	it	will	return	null.
Remove	your	earlier	signatureDrink	experiment	and	add	a	call	to
readLine.	Store	the	value	that	is	passed	in	to	readLine	and	print	it	out.
Listing	6.2		Defining	a	nullable	variable	(Tavern.kt)
fun	main(args:	Array<String>)	{
var	signatureDrink	=	"Buttered	Ale"
signatureDrink	=	null
var	beverage	=	readLine()
println(beverage)
## }
Run	Tavern.kt.	Nothing	will	happen	initially,	because	it	is	waiting	for	your
input.	Click	in	the	console,	type	the	name	of	your	preferred	beverage,	and	press
Return.	The	name	that	you	entered	will	be	echoed	back	to	you	in	the	console.
(What	if	you	entered	no	beverage	name	and	just	pressed	Return?	Would	that
assign	beverage	a	null	value?	No.	It	would	assign	the	variable	the	value	of	the
empty	string,	which	would	then	be	echoed	back	to	you.)
Recall	that	a	variable	of	type	String?	can	hold	either	a	string	value	or	null.
This	means	that	assigning	beverage	to	a	null	value	will	indeed	compile.	Try	it
out:
Listing	6.3		Reassigning	a	variable	to	null	(Tavern.kt)
fun	main(args:	Array<String>)	{
var	beverage	=	readLine()
beverage	=	null

println(beverage)
## }
Run	Tavern.kt	and,	as	before,	enter	your	beverage	of	choice.	This	time,	no
matter	what	you	enter,	the	value	printed	to	the	console	will	be	null.	No	beverage
for	you	–	but	no	error,	either.
Before	moving	on,	comment	out	the	reassignment	to	null	so	that	your	tavern	will
actually	serve	customers.	IntelliJ	provides	a	shortcut	for	commenting	out	a	line
of	code:	Click	anywhere	in	the	line	and	press	Command-/	(Ctrl-/).	Commenting
out	this	line	of	code	instead	of	deleting	it	will	give	you	the	option	to	toggle	the
nullness	of	beverage	by	uncommenting	the	line	(using	the	same	keybinding).
This	way,	you	can	easily	test	the	strategies	for	handling	nullness	outlined	in	this
chapter.
Listing	6.4		Restoring	service	(Tavern.kt)
fun	main(args:	Array<String>)	{
var	beverage	=	readLine()
beverage	=	null
//			beverage	=	null
println(beverage)
## }

Compile	Time	vs	Runtime
Kotlin	is	a	compiled	language,	meaning	that	your	program	is	translated	into
machine-language	instructions	prior	to	execution	by	a	special	program,	called
the	compiler.	During	this	step,	the	compiler	ensures	that	certain	requirements	are
met	by	your	code	before	the	instructions	are	generated.	For	example,	the
compiler	checks	whether	null	is	assigned	to	a	nullable	type.	As	you	have	seen,	if
you	attempt	to	assign	null	to	a	non-nullable	type,	Kotlin	will	refuse	to	compile
your	program.
Errors	caught	at	compile	time	are	called	compile-time	errors,	and	they	are	one	of
the	advantages	of	working	with	Kotlin.	It	may	sound	odd	to	say	that	errors	are	an
advantage,	but	having	the	compiler	check	your	work	during	development	–
before	you	allow	others	to	run	your	program	and	tell	you	about	your	mistakes	–
makes	it	much	easier	to	track	down	problems.
On	the	other	hand,	a	runtime	error	is	a	mistake	that	happens	after	the	program
has	compiled	and	is	already	running,	because	the	compiler	was	unable	to
discover	it.	For	example,	because	Java	lacks	any	distinction	between	nullable
and	non-nullable	types,	the	Java	compiler	cannot	tell	you	that	there	is	a	problem
if	you	ask	a	variable	with	a	value	of	null	to	perform	work.	Code	like	that
compiles	just	fine	in	Java,	but	it	will	crash	at	runtime.
Generally	speaking,	compile-time	errors	are	preferable	to	runtime	errors.
Finding	out	about	a	problem	while	you	are	writing	code	is	better	than	finding	out
later.	And	finding	out	after	your	program	has	been	released?	That	is	the	worst.

## Null	Safety
Because	Kotlin	distinguishes	between	nullable	and	non-nullable	types,	the
compiler	is	aware	of	the	possibly	dangerous	situation	of	asking	a	variable
defined	as	a	nullable	type	to	do	something	when	the	variable	might	not	exist.	To
shield	against	these	dangers,	Kotlin	will	prevent	you	from	calling	functions	on	a
value	defined	as	nullable	until	you	have	accepted	responsibility	for	this	unsafe
situation.
To	see	what	this	looks	like	in	practice,	try	to	call	a	function	on	beverage.	This
is	a	fancy	tavern,	and,	as	such,	all	drink	names	should	be	capitalized.	Try	to	call
capitalize	on	beverage.	(You	will	see	more	String	functions	in
## Chapter	7.)
Listing	6.5		Using	a	nullable	variable	(Tavern.kt)
fun	main(args:	Array<String>)	{
var	beverage	=	readLine()
var	beverage	=	readLine().capitalize()
//			beverage	=	null
println(beverage)
## }
Run	Tavern.kt.	You	might	expect	that	the	result	of	this	code	will	be	a	fancily
capitalized	version	of	the	drink	that	you	order.	But	when	you	execute	the	code,
you	will	see	a	compile-time	error	instead:
Only	safe	(?.)	or	non-null	asserted	(!!.)	calls
are	allowed	on	a	nullable	receiver	of	type	String?
Kotlin	did	not	allow	you	to	call	the	capitalize	function,	because	you	did	not
deal	with	the	possibility	of	beverage	being	null.	Even	though	you	assign
beverage	to	a	non-null	value	via	the	console	at	its	declaration,	its	type
remains	nullable.	Kotlin	has	prevented	you	at	compile	time	from	potentially
causing	a	runtime	error,	because	the	compiler	was	aware	of	your	mistake	with
the	nullable	type.
By	now	you	are	likely	thinking,	“So	how	do	I	deal	with	the	possibility	of	null?	I
have	important	drink	name	fancying-up	to	do.”	You	have	a	number	of	choices
for	safely	working	with	a	nullable	type	in	Kotlin,	and	in	a	moment	we	will	give
you	three	options,	plus	some	extras.
First,	though,	consider	option	zero:	Use	a	non-nullable	type,	if	at	all	possible.
Non-nullable	types	are	easier	to	reason	about	because	they	guarantee	that	they
contain	a	value	that	can	have	functions	called	on	it.	So	ask	yourself,	“Why	do	I

need	a	nullable	type	here?	Would	a	non-nullable	type	work	just	as	well?”	Often,
you	simply	do	not	need	null	–	and	when	you	do	not	need	it,	avoiding	it	is	the
safest	course.
Option	one:	the	safe	call	operator
Sometimes,	nothing	but	a	nullable	type	will	do.	For	example,	when	you	are
working	with	a	variable	from	code	you	do	not	control,	you	cannot	be	sure	that	it
will	not	return	null.	In	cases	like	that,	your	first	option	is	to	use	the	safe	call
operator	(?.)	in	your	function	call.	Try	it	out	in	Tavern.kt:
Listing	6.6		Using	the	safe	call	operator	(Tavern.kt)
fun	main(args:	Array<String>)	{
var	beverage	=	readLine().capitalize()
var	beverage	=	readLine()?.capitalize()
//			beverage	=	null
println(beverage)
## }
Notice	that	Kotlin	does	not	generate	an	error	this	time.	When	the	compiler
encounters	the	safe	call	operator,	it	knows	to	check	for	a	null	value.	If	it	finds
one,	it	skips	over	the	call	and	does	not	evaluate	it,	instead	returning	null.	Here,	if
beverage	is	non-null,	a	capitalized	version	is	returned.	(Try	it	and	see.)	If
beverage	is	null,	capitalize	is	not	called,	because	it	would	not	be	safe	to
do	so.	(Try	that,	too.)
The	safe	call	operator	ensures	that	a	function	is	called	if	and	only	if	the	variable
it	acts	on	is	not	null,	thus	preventing	a	null	pointer	exception.	We	say,	using	the
example	above,	that	capitalize	is	called	“safely,”	because	the	risk	of	a	null
pointer	exception	no	longer	exists.
Using	safe	calls	with	let
Safe	calls	allow	you	to	call	a	single	function	on	a	nullable	type,	but	what	if	you
want	to	perform	additional	work,	like	creating	a	new	value	or	calling	other
functions	if	the	variable	is	not	null?	One	way	to	achieve	this	is	to	use	the	safe
call	operator	with	the	function	let.	let	can	be	called	on	any	value,	and	its
purpose	is	to	allow	you	to	define	a	variable	or	variables	for	a	given	scope	that
you	provide.	(Recall	that	you	learned	about	function	scope	in	Chapter	4.)
Because	let	provides	its	own	function	scope,	you	can	use	a	safe	call	with	let
to	scope	multiple	expressions	that	each	require	the	variable	that	they	are	called

on	to	be	non-null.	You	will	learn	more	details	about	working	with	let	in
Chapter	9,	but	for	now	adapt	your	beverage	implementation	to	get	a	sneak
preview:
Listing	6.7		Using	let	with	the	safe	call	operator	(Tavern.kt)
fun	main(args:	Array<String>)	{
var	beverage	=	readLine()?.capitalize()
var	beverage	=	readLine()?.let	{
if	(it.isNotBlank())	{
it.capitalize()
}	else	{
"Buttered	Ale"
## }
## }
//			beverage	=	null
println(beverage)
## }
Here,	you	define	beverage	as	a	nullable	variable,	as	before.	But	this	time	you
assign	its	value	to	the	result	of	safely	calling	let	on	it.	When	beverage	is	not
null	and	let	is	invoked,	everything	within	the	anonymous	function	passed	to
let	is	evaluated:	The	input	from	readLine	is	checked	to	see	whether	it	is
blank;	if	it	is	not	blank	it	is	capitalized,	and	if	it	is	blank,	then	a	fallback
beverage	name,	"Buttered	Ale",	is	returned	instead.	Both	isNotBlank	and
capitalize	require	the	beverage	name	to	be	non-null,	which	is	guaranteed	by
let.
let	provides	a	number	of	conveniences,	two	of	which	you	take	advantage	of
here.	As	you	define	beverage,	you	use	the	convenience	value	it,	provided	by
let.	You	saw	it	in	Chapter	5.	Within	let,	it	is	a	reference	to	the	variable	on
which	let	is	called,	and	is	guaranteed	to	be	non-null.	You	call	isNotBlank
and	capitalize	on	it	–	a	non-null	form	of	beverage.
The	second	let	convenience	is	behind	the	scenes:	let	returns	the	results	of
your	expression	implicitly,	so	you	can	(and	do)	assign	that	result	to	a	variable
once	it	has	completed	evaluating	the	expression	you	define.
Run	Tavern.kt	with	the	reassignment	to	null	commented	out,	then
uncommented.	When	beverage	is	not	null,	let	is	invoked,	capitalization
happens,	and	the	result	is	printed.	When	beverage	is	null,	the	contents	of	the
let	function	are	not	evaluated,	and	beverage	remains	null.
Option	two:	the	double-bang	operator
The	double-bang	operator	(!!.)	can	also	be	used	to	call	a	function	on	a	nullable
type.	But	be	forewarned:	This	is	a	much	more	drastic	option	than	the	safe	call

operator	and	should	generally	not	be	used.	Visually,	the	!!.	should	look	very
loud	in	your	code,	because	it	is	a	dangerous	option.	If	you	use	!!.,	you	are
proclaiming	to	the	compiler:	“If	I	ask	a	nonexistent	thing	to	do	something,	I
DEMAND	that	you	throw	a	null	pointer	exception!!”	(By	the	way,	its	official
name	is	the	non-null	assertion	operator,	but	it	is	more	often	called	the	double-
bang	operator.)
While	we	generally	advise	against	the	double-bang	operator,	strap	on	your	safety
goggles	and	try	it	out:
Listing	6.8		Using	the	double-bang	operator	(Tavern.kt)
fun	main(args:	Array<String>)	{
var	beverage	=	readLine()?.let	{
if	(it.isNotBlank())	{
it.capitalize()
}	else	{
"Buttered	Ale"
## }
## }
var	beverage	=	readLine()!!.capitalize()
//			beverage	=	null
println(beverage)
## }
beverage	=	readLine()!!.capitalize()	means,	“I	don’t	care	whether
beverage	is	null;	capitalize	it	anyway!”	If	beverage	is	indeed	null,	a
KotlinNullPointerException	is	thrown.
There	are	situations	where	using	the	double-bang	operator	is	appropriate.
Perhaps	you	do	not	have	control	over	the	type	of	a	variable,	but	you	are	sure	that
it	will	never	be	null.	As	long	as	you	are	confident	that	the	variable	you	are	using
will	not	be	null	when	you	use	it,	then	!!.	may	be	an	option	for	you.	You	will	see
an	example	of	an	appropriate	use	of	!!.	later	in	this	chapter.
Option	three:	checking	whether	a	value	is	null	with	if
A	third	option	for	working	safely	with	null	values	is	to	check	whether	a	value	is
null	as	a	condition	for	executing	an	if	branch.	Recall	Table	3.1	in	Chapter	3,
which	lists	the	comparison	operators	available	in	Kotlin.	The	!=	operator
evaluates	whether	the	value	on	the	left	is	not	equal	to	the	value	on	the	right,	and
you	can	use	it	to	check	that	a	value	is	not	null.	Try	it	out	in	the	tavern:
Listing	6.9		Using	!=	null	for	null	checking	(Tavern.kt)
fun	main(args:	Array<String>)	{
var	beverage	=	readLine()!!.capitalize()
var	beverage	=	readLine()
//			beverage	=	null
if	(beverage	!=	null)	{

beverage	=	beverage.capitalize()
}	else	{
println("I	can't	do	that	without	crashing	-	beverage	was	null!")
## }
println(beverage)
## }
Now,	if	beverage	is	null,	you	will	get	the	following	output,	and	no	error.
I	can't	do	that	without	crashing	-	beverage	was	null!
Using	the	safe	call	operator	should	be	favored	before	using	value	!=	null	as	a
means	to	guard	against	null,	since	it	is	a	more	flexible	tool	to	solve	generally	the
same	problem,	but	in	less	code.	For	example,	the	safe	call	operator	can	be
chained	on	to	subsequent	function	calls	with	ease:
beverage?.capitalize()?.plus(",	large")
Notice	that	you	did	not	have	to	use	the	!!.	operator	when	referencing
beverage	in	beverage	=	beverage.capitalize().	The	Kotlin	compiler
recognizes	that	beverage	must	be	non-null	as	a	condition	for	that	branch,	and
it	can	deduce	that	a	second	null	check	is	unnecessary.	This	feature	–	the	compiler
tracking	conditions	within	an	if	expression	–	is	an	example	of	smart	casting.
When	would	you	use	an	if/else	statement	for	null	checking?	This	option	is	best
for	times	when	you	have	some	complex	logic	that	you	would	only	like	to	be
evaluated	if	a	variable	is	null.	An	if/else	statement	allows	you	to	represent	that
complex	logic	in	a	readable	form.
The	null	coalescing	operator
Another	way	to	check	for	null	values	is	to	use	Kotlin’s	null	coalescing	operator
?:	(also	known	as	the	“Elvis	operator”	due	to	its	semblance	to	Elvis	Presley’s
iconic	hairstyle).	This	operator	says,	“If	the	thing	on	the	lefthand	side	of	me	is
null,	do	the	thing	on	the	righthand	side	instead.”
Add	a	default	beverage	choice	to	your	tavern	using	the	null	coalescing	operator.
If	beverage	is	null,	then	print	out	the	house	specialty,	Buttered	Ale.
Listing	6.10		Using	the	null	coalescing	operator	(Tavern.kt)
fun	main(args:	Array<String>)	{
var	beverage	=	readLine()
//			beverage	=	null
if	(beverage	!=	null)	{
beverage	=	beverage.capitalize()
}	else	{
println("I	can't	do	that	without	crashing	-	beverage	was	null!")
## }
println(beverage)
val	beverageServed:	String	=	beverage	?:	"Buttered	Ale"
println(beverageServed)

## }
Most	often	in	this	book,	we	exclude	the	type	of	a	variable	if	it	can	be	inferred	by
the	Kotlin	compiler.	We	include	it	here	to	illuminate	the	role	of	the	null
coalescing	operator.
If	beverage	is	non-null,	then	it	will	be	assigned	to	beverageServed.	If
beverage	is	null,	then	"Buttered	Ale"	is	assigned.	Either	way,
beverageServed	is	assigned	a	value	of	type	String,	not	String?.	This	is
great	–	the	beverage	served	to	the	user	is	now	guaranteed	to	be	non-null.
Think	of	the	null	coalescing	operator	as	ensuring	that	a	value	is	not	null	by
providing	a	default	(and	not	null)	value	to	be	assigned	if	the	first	option	turns	out
to	be	null.	Null	coalescing	can	be	used	to	clean	up	values	that	might	be	null	so
that	you	can	have	peace	of	mind	as	you	work	with	them.
Run	Tavern.kt.	As	long	as	beverage	is	not	null,	you	will	see	your
capitalized	drink	order.	When	beverage	is	null,	you	will	see	the	following
printed	to	the	console	instead.
I	can't	do	that	without	crashing	-	beverage	was	null!
## Buttered	Ale
The	null	coalescing	operator	can	also	be	used	in	conjunction	with	the	let
function	in	place	of	an	if/else	statement.	Compare	this	code,	which	is	the	result
of	Listing	6.9:
var	beverage	=	readLine()
if	(beverage	!=	null)	{
beverage	=	beverage.capitalize()
}	else	{
println("I	can't	do	that	without	crashing	-	beverage	was	null!")
## }
With	this:
var	beverage	=	readLine()
beverage?.let	{
beverage	=	it.capitalize()
}	?:	println("I	can't	do	that	without	crashing	-	beverage	was	null!")
This	code	is	functionally	equivalent	to	the	code	in	Listing	6.9.	If	beverage	is
null,	then	"I	can't	do	that	without	crashing	-	beverage	was	null!"	is
printed	to	the	console.	Otherwise,	beverage	is	capitalized.
So,	should	you	replace	your	existing	if/else	statements	with	this	style?	That	is
not	a	question	that	we	can	answer	for	you,	because	the	choice	is	a	stylistic	one.
We	tend	to	opt	for	if/else	statements	in	this	type	of	scenario,	and	you	will	see
them	throughout	this	book.	We	prefer	their	readability.	If	you	or	your	team
disagree,	that	is	OK	–	either	syntax	is	valid.

## Exceptions
Like	many	other	languages,	Kotlin	also	includes	exceptions	to	indicate	that
something	went	wrong	in	your	program.	This	is	important,	because	the	world	of
NyetHack	is	a	place	in	which	things	can	indeed	go	wrong.
Let’s	see	some	examples.	Start	by	creating	a	new	file	in	NyetHack	called
SwordJuggler.kt	and	adding	a	main	function.
Against	your	better	judgment,	a	group	of	tavern	attendees	has	convinced	you	to
juggle	some	swords.	You	will	keep	track	of	the	number	of	swords	that	you	are
juggling	with	a	nullable	integer.	Why	a	nullable	integer?	If	swordsJuggling
is	null,	then	you	lack	proficiency	in	sword	juggling	and	your	journey	in
NyetHack	may	be	cut	short.
Begin	by	adding	variables	for	the	number	of	swords	you	are	juggling	as	well	as
your	juggling	proficiency.	You	can	represent	sword	juggling	proficiency	using
the	same	randomness	mechanism	that	you	wrote	in	Chapter	5.	If	you	are	a
proficient	juggler,	print	the	number	of	swords	juggled	to	the	console.
Listing	6.11		Adding	sword	juggling	logic	(SwordJuggler.kt)
fun	main(args:	Array<String>)	{
var	swordsJuggling:	Int?	=	null
val	isJugglingProficient	=	(1..3).shuffled().last()	==	3
if	(isJugglingProficient)	{
swordsJuggling	=	2
## }
println("You	juggle	$swordsJuggling	swords!")
## }
Run	SwordJuggler.	You	have	a	1	in	3	chance	of	being	proficient	with	juggling
swords	–	not	bad	for	a	first-timer.	If	your	proficiency	check	passes,	then	you	will
see	You	juggle	2	swords!	printed	to	the	console.	If	your	check	fails,	then	you
will	see	You	juggle	null	swords!	instead.
Printing	the	value	of	swordsJuggling	is	not	an	inherently	dangerous
operation.	You	can	print	null	to	the	console,	and	your	program	will	continue
running.	It	is	time	to	ratchet	up	the	danger.	Add	another	sword	using	the	plus
function	and	the	!!.	operator.
Listing	6.12		Adding	a	third	sword	(SwordJuggler.kt)
fun	main(args:	Array<String>)	{
var	swordsJuggling:	Int?	=	null
val	isJugglingProficient	=	(1..3).shuffled().last()	==	3
if	(isJugglingProficient)	{
swordsJuggling	=	2
## }

swordsJuggling	=	swordsJuggling!!.plus(1)
println("You	juggle	$swordsJuggling	swords!")
## }
Using	the	!!.	operator	on	a	nullable	variable	is	a	dangerous	operation.	One-
third	of	the	time,	your	sword-juggling	proficiency	enables	you	juggle	a	third
sword.	The	other	two-thirds	of	the	time,	your	program	crashes.
When	an	exception	occurs,	it	must	be	dealt	with,	or	execution	of	the	program
will	be	halted.	An	exception	that	is	not	dealt	with	is	called	an	unhandled
exception.	And	halting	the	execution	of	the	program	is	known	by	the	ugly	name
crash.
Test	your	luck	by	running	SwordJuggler	a	couple	of	times.	If	your	application
crashes,	you	will	see	a	KotlinNullPointerException,	and	the	rest	of	the
code	(the	println	statement)	will	not	execute.
When	there	is	the	possibility	of	a	variable	being	null,	there	is	the	possibility	of	a
KotlinNullPointerException.	This	is	one	of	the	reasons	Kotlin	makes
variables	non-nullable	by	default.
Throwing	an	exception
Similar	to	many	other	languages,	Kotlin	allows	you	to	manually	signal	that	an
exception	has	occurred.	You	do	this	with	the	throw	operator,	and	it	is	called
throwing	an	exception.	There	are	many	more	exceptions	that	can	be	thrown	in
addition	to	the	null	pointer	exception	that	you	just	saw.
Why	would	you	want	to	throw	an	exception?	It	is	all	in	the	name	–	exceptions
are	used	to	represent	exceptional	state.	If	something	in	your	code	has	gone
extraordinarily	wrong,	then	throwing	an	exception	signals	that	the	issue	must	be
handled	before	execution	continues.
One	of	the	more	common	exceptions	that	you	will	see	is	called	an
IllegalStateException.	IllegalStateException	is	a	vague
name,	to	be	sure	–	it	means	that	your	program	has	reached	some	state	that	you
have	deemed	illegal.	This	is	useful,	because	you	can	pass
IllegalStateException	a	string	to	print	out	when	the	exception	is
thrown	to	provide	more	information	about	what	went	wrong.
The	world	of	NyetHack	may	be	expansive	and	mysterious,	but	the	tavern	has	its
share	of	good	people.	One	particular	merrymaker	recognizes	your	lack	of	sword-
juggling	proficiency	and	steps	in	before	you	can	do	anything	dangerous.	Add	a

function	called	proficiencyCheck	to	SwordJuggler,	and	call	it	in	main.	If
swordsJuggling	is	null,	interject	by	throwing	an
IllegalStateException	before	any	dangerous	operations	can	be
performed.
Listing	6.13		Throwing	an	IllegalStateException
(SwordJuggler.kt)
fun	main(args:	Array<String>)	{
var	swordsJuggling:	Int?	=	null
val	isJugglingProficient	=	(1..3).shuffled().last()	==	3
if	(isJugglingProficient)	{
swordsJuggling	=	2
## }
proficiencyCheck(swordsJuggling)
swordsJuggling	=	swordsJuggling!!.plus(1)
println("You	juggle	$swordsJuggling	swords!")
## }
fun	proficiencyCheck(swordsJuggling:	Int?)	{
swordsJuggling	?:	throw	IllegalStateException("Player	cannot	juggle	swords")
## }
Run	this	code	a	couple	of	times	to	see	the	different	results.
Here,	you	signaled	that	the	state	of	the	program	is	an	illegal	one	–
swordsJuggling	should	not	be	null,	lest	you	put	yourself	at	risk.	This	signal
decrees	that	anyone	that	would	like	to	work	with	the	swordsJuggling
variable	must	handle	the	exceptional	state	stemming	from	its	nullability.	It	is
loud,	but	that	is	a	good	thing,	as	it	increases	the	likelihood	that	you	will	notice
the	exceptional	state	during	development	–	before	it	causes	a	crash	for	your	user.
And	because	you	provided	an	error	message	to	the
IllegalStateException,	you	know	exactly	why	your	program	crashed.
When	you	throw	an	exception,	you	are	not	limited	to	Kotlin’s	built-in	types.	You
can	define	your	own	custom	exceptions	to	represent	a	state	that	is	specific	to
your	application.
Custom	exceptions
You	have	now	seen	how	to	use	the	throw	operator	to	signal	that	an	exception	has
occurred.	The	exception	you	just	threw,	IllegalStateException,
indicates	that	an	illegal	state	has	occurred	and	gives	you	the	opportunity	to	add
more	information	by	passing	a	string	to	be	printed	when	the	exception	is	thrown.
To	add	more	detail	to	your	exception,	you	can	create	a	custom	exception	for	the
particular	problem.	To	define	a	custom	exception,	you	define	a	new	class	that
inherits	from	some	other	exception.	Classes	allow	you	to	define	the	“things”	in

your	program	–	monsters,	weapons,	food,	tools,	and	so	on.	You	will	learn	lots
more	about	classes	in	Chapter	12,	so	do	not	worry	about	the	details	of	the	syntax
now.
Define	a	custom	exception	called	UnskilledSwordJugglerException
in	SwordJuggler.kt.
Listing	6.14		Defining	a	custom	exception	(SwordJuggler.kt)
fun	main(args:	Array<String>)	{
## ...
## }
fun	proficiencyCheck(swordsJuggling:	Int?)	{
swordsJuggling	?:	throw	IllegalStateException("Player	cannot	juggle	swords")
## }
class	UnskilledSwordJugglerException()	:
IllegalStateException("Player	cannot	juggle	swords")
UnskilledSwordJugglerException	is	a	custom	exception	that	acts	as
an	IllegalStateException	with	a	specific	message.
You	can	throw	this	new,	custom	exception	in	the	same	way	that	you	threw
IllegalStateException,	using	the	throw	operator.	Throw	your	custom
exception	in	SwordJuggler.kt.
Listing	6.15		Throwing	a	custom	exception	(SwordJuggler.kt)
fun	main(args:	Array<String>)	{
## ...
## }
fun	proficiencyCheck(swordsJuggling:	Int?)	{
swordsJuggling	?:	throw	IllegalStateException("Player	cannot	juggle	swords")
swordsJuggling	?:	throw	UnskilledSwordJugglerException()
## }
class	UnskilledSwordJugglerException()	:
IllegalStateException("Player	cannot	juggle	swords")
UnskilledSwordJugglerException	is	a	custom	error	intended	to	be
thrown	when	swordsJuggling	is	null.	Nothing	about	the	code	used	to	define
this	exception	specifies	when	it	is	thrown	–	that	is	your	responsibility.
Custom	exceptions	are	flexible	and	useful.	Not	only	can	you	use	them	to	print
custom	messages,	but	you	also	can	add	functionality	to	be	executed	when	the
exception	is	thrown.	And	they	reduce	duplication,	as	you	can	reuse	them	across
your	codebase.
Handling	exceptions
Exceptions	are	disruptive,	and	they	should	be	–	they	represent	a	state	that	is
unrecoverable	unless	it	is	handled.	Kotlin	allows	you	to	specify	how	to	handle

exceptions	by	defining	a	try/catch	statement	around	the	code	that	might	cause
one.	The	syntax	of	try/catch	is	similar	to	the	syntax	for	if/else.	To	see	what	it
looks	like,	use	try/catch	in	SwordJuggler.kt	to	protect	against	the
dangerous	operation	that	you	performed,	as	shown:
Listing	6.16		Adding	a	try/catch	statement	(SwordJuggler.kt)
fun	main(args:	Array<String>)	{
var	swordsJuggling:	Int?	=	null
val	isJugglingProficient	=	(1..3).shuffled().last()	==	3
if	(isJugglingProficient)	{
swordsJuggling	=	2
## }
try	{
proficiencyCheck(swordsJuggling)
swordsJuggling	=	swordsJuggling!!.plus(1)
}	catch	(e:	Exception)	{
println(e)
## }
println("You	juggle	$swordsJuggling	swords!")
## }
fun	proficiencyCheck(swordsJuggling:	Int?)	{
swordsJuggling	?:	throw	UnskilledSwordJugglerException()
## }
class	UnskilledSwordJugglerException()	:
IllegalStateException("Player	cannot	juggle	swords")
When	you	define	a	try/catch	statement,	you	declare	what	will	happen	in	the
event	that	some	value	is	not	null	and	what	will	happen	if	it	is	null.	In	the	try
block,	you	“try”	to	use	a	variable.	If	no	exception	occurs,	the	try	statement
executes	and	the	catch	statement	does	not.	This	branching	logic	is	akin	to	a
conditional.	In	this	case,	you	try	to	add	another	sword	to	be	juggled	using	the
!!.	operator.
In	the	catch	block,	you	define	what	will	happen	if	some	expression	in	the	try
block	causes	an	exception.	The	catch	block	takes	a	specific	type	of	exception	to
protect	as	an	argument.	In	this	case,	you	catch	any	exception	of	type
## Exception.
catch	blocks	can	include	all	sorts	of	logic,	but	this	example	keeps	it	simple.
Here,	you	use	the	catch	block	to	simply	print	the	name	of	the	exception.
Within	the	try	block,	each	line	of	code	is	executed	in	the	order	it	is	declared.	In
this	case,	if	swordsJuggling	is	non-null,	the	plus	function	will	add	1	to
swordsJuggling	without	issue,	and	the	following	statement	will	be	printed
to	the	console:
You	juggle	3	swords!
If	you	are	not	fortunate	enough	to	be	proficient	with	sword	juggling,	then
swordsJuggling	will	be	null.	As	such,	proficiencyCheck	will	throw
an	UnskilledSwordJugglerException.	But	because	you	handled	the

exception	with	a	try/catch	statement,	program	execution	will	continue	and	the
catch	block	will	run,	printing	the	following	output	to	the	console:
UnskilledSwordJugglerException:	Player	cannot	juggle	swords
You	juggle	null	swords!
Note	that	both	the	name	of	the	exception	and	You	juggle	null	swords!	was
printed.	This	is	significant,	because	the	latter	string	is	printed	after	the
try/catch	block	executes.	An	unhandled	exception	will	crash	your	program,
halting	execution.	Because	you	handled	the	exception	using	a	try/catch	block,
code	execution	can	continue	as	if	a	dangerous	operation	never	caused	an	issue.
Run	SwordJuggler.kt	several	times	to	see	both	outcomes.

## Preconditions
Unexpected	values	can	cause	your	program	to	behave	in	unintended	ways.	As	a
developer,	you	will	spend	plenty	of	time	validating	input	to	ensure	you	are
working	with	the	values	you	intend.	Some	sources	of	exceptions	are	common,
like	unexpected	null	values.	To	make	it	easier	to	validate	input	and	debug	to
avoid	certain	common	issues,	Kotlin	provides	some	convenience	functions	as
part	of	its	standard	library.	They	allow	you	to	use	a	built-in	function	to	throw	an
exception	with	a	custom	message.
These	functions	are	called	precondition	functions,	because	they	allow	you	to
define	preconditions	–	conditions	that	must	be	true	before	some	piece	of	code	is
executed.
For	example,	you	have	seen	a	number	of	ways	in	this	chapter	to	guard	against
the	KotlinNullPointerException	and	other	exceptions.	One	last	option
is	to	use	a	precondition	function	like	checkNotNull,	which	checks	whether	a
value	is	null	and	returns	the	value,	if	it	is	not	null,	or	throws	an
IllegalStateException	if	it	is	null.	Try	replacing	your	thrown
UnskilledSwordJugglerException	with	a	precondition	function:
Listing	6.17		Using	a	precondition	function	(SwordJuggler.kt)
fun	main(args:	Array<String>)	{
var	swordsJuggling:	Int?	=	null
val	isJugglingProficient	=	(1..3).shuffled().last()	==	3
if	(isJugglingProficient)	{
swordsJuggling	=	2
## }
try	{
proficiencyCheck(swordsJuggling)
swordsJuggling	=	swordsJuggling!!.plus(1)
}	catch	(e:	Exception)	{
println(e)
## }
println("You	juggle	$swordsJuggling	swords!")
## }
fun	proficiencyCheck(swordsJuggling:	Int?)	{
swordsJuggling	?:	throw	UnskilledSwordJugglerException()
checkNotNull(swordsJuggling,	{	"Player	cannot	juggle	swords"	})
## }
class	UnskilledSwordJugglerException()	:
IllegalStateException("Player	cannot	juggle	swords")
checkNotNull	makes	explicit	that	swordsJuggling	must	not	be	null	past
a	certain	point	in	your	code	execution.	If	it	is	null	when	passed	to
checkNotNull,	then	a	thrown	IllegalStateException	makes	it	clear
that	the	current	state	is	unacceptable.	checkNotNull	takes	two	arguments:

The	first	is	a	value	to	check	for	nullness,	and	the	second	is	an	error	message	to
be	printed	to	the	console	in	the	event	that	the	first	argument	is	null.
Precondition	functions	are	a	great	way	to	communicate	requirements	before
some	bit	of	code	is	executed.	They	can	be	cleaner	than	manually	throwing	your
own	exception,	because	the	condition	to	be	satisfied	is	included	in	the	name	of
the	function.	In	this	case,	while	the	outcome	is	the	same	–	you	can	be	assured
that	either	swordsJuggling	will	not	be	null	or	that	a	custom	exception
message	will	print	–	checkNotNull	is	more	clear	than	the	earlier	throw
UnskilledSwordJugglerException.
Kotlin	includes	five	preconditions	in	the	standard	library;	this	variety
differentiates	them	from	other	types	of	null	checks.	The	five	precondition
functions	are	shown	in	Table	6.1:
Table	6.1		Kotlin	precondition	functions
FunctionDescription
checkNotNull
Throws	an	IllegalStateException	if	argument
is	null.	Otherwise	returns	the	non-null	value.
require
Throws	an	IllegalArgumentException	if
argument	is	false.
requireNotNull
Throws	an	IllegalArgumentException	if
argument	is	null.	Otherwise	returns	the	non-null	value.
error
Throws	an	IllegalArgumentException	with	a
provided	message	if	argument	is	null.	Otherwise	returns
the	non-null	value.
assert
Throws	an	AssertionError	if	argument	is	false	and
the	assertion	compiler	flag	is	enabled.
a
a
The	details	of	enabling	assertions	are	outside	the	scope	of	this	book.	If	you
are	interested,	see	kotlinlang.org/api/latest/jvm/stdlib/
kotlin/assert.html	and	docs.oracle.com/cd/E19683-01/
## 806-7930/assert-4/index.html.
require	is	a	particularly	useful	precondition.	Functions	can	leverage

require	to	communicate	bounds	for	the	arguments	passed	to	them.	Take	a
look	at	a	function	using	require	to	make	the	requirements	for	the
swordsJuggling	parameter	explicit:
fun	juggleSwords(swordsJuggling:	Int)	{
require(swordsJuggling	>=	3,	{	"Juggle	at	least	3	swords	to	be	exciting."	})
## //	Juggle
## }
To	put	on	a	good	show,	the	player	must	juggle	at	least	three	swords.	Using
require	at	the	top	of	the	function	declaration	makes	this	clear	to	whoever	calls
juggleSwords.

## Null:	What	Is	It	Good	For?
This	chapter	has	taken	a	largely	anti-null	stance.	We	view	this	stance	to	be	a
noble	one,	but	in	the	wild	world	of	software	engineering,	representing	state	using
null	is	common.
Why?	Null	is	often	used	in	Java	and	languages	of	its	ilk	as	an	initial	value	for
variables.	For	example,	think	of	a	variable	declared	to	hold	a	person’s	name.
There	are	common	first	names	for	human	beings,	but	no	name	can	be	considered
a	default.	Null	is	often	used	as	an	initial	value	for	variables	that	have	no	natural
default	value.	In	fact,	in	many	languages,	you	can	define	a	variable	without
assigning	it	a	value,	and	its	value	will	default	to	null.
This	mentality	of	defaulting	to	null	can	lead	to	null	pointer	exceptions,	which
can	be	common	in	other	languages.	One	way	that	you	can	work	around	nullness
is	to	provide	better	initializers.	Not	every	type	has	a	natural	initial	value,	but	the
String	in	our	name	example	does	–	an	empty	string.	An	empty	string	tells	you
as	much	as	a	null	initializer	would:	This	value	is	not	yet	initialized.	Therefore,
you	can	represent	an	uninitialized	state	without	resorting	to	null	checks	in	your
code.
The	other	way	to	work	with	nullness	is	to	accept	it	and	to	use	the	strategies
outlined	in	this	chapter	to	work	with	nullable	types.	Whether	you	use	the	safe
call	operator	to	protect	yourself	against	null	pointer	exceptions	or	the	null
coalescing	operator	to	provide	your	own	default	value,	working	with	null	is	a
reasonable	expectation	of	you	as	a	Kotlin	developer.
Nullness	–	the	absence	of	a	value	–	is	a	real-world	phenomenon.	Being	able	to
represent	nullness	in	Kotlin	is	important	for	that	reason.	When	you	represent	it	in
your	code	or	call	into	someone	else’s	code	that	relies	on	nullness,	do	so	wisely.
In	this	chapter,	you	have	learned	how	Kotlin	handles	problems	related	to
nullness.	You	have	seen	that	you	must	explicitly	define	when	you	support
nullability,	because	values	are	by	default	non-nullable.	And	you	learned	that	you
should	favor	types	that	do	not	support	null	when	possible,	because	they	let	the
compiler	help	prevent	runtime	errors.
You	have	also	seen	how	to	work	safely	with	nullable	types	when	you	absolutely
must	have	them	–	by	using	the	safe	call	operator	or	null	coalescing	operator,	or
by	explicitly	checking	whether	the	value	is	null.	You	also	saw	the	let	function
and	how	it	can	be	used	in	conjunction	with	the	safe	call	operator	to	evaluate

expressions	safely	on	a	nullable	variable.	Finally,	you	learned	about	exceptions,
how	to	deal	with	them	using	the	try/catch	syntax	that	Kotlin	provides,	and	how
to	define	preconditions	to	catch	exceptional	states	before	they	cause	a	crash.
In	the	next	chapter	you	will	learn	more	about	working	with	strings	in	Kotlin	as
you	continue	to	build	NyetHack’s	tavern.

For	the	More	Curious:	Checked	vs	Unchecked
## Exceptions
In	Kotlin,	all	exceptions	are	unchecked.	This	means	that	the	Kotlin	compiler
does	not	force	you	to	wrap	all	code	that	could	produce	an	exception	in	a
try/catch	statement.
Compare	this	with	Java,	for	example,	which	supports	a	mixture	of	both	checked
and	unchecked	exception	types.	With	a	checked	exception,	the	compiler	checks
that	the	exception	is	guarded	against,	requiring	you	add	a	try/catch	to	your
program.
This	sounds	reasonable.	But	in	practice,	the	idea	of	checked	exceptions	does	not
hold	up	as	well	as	the	inventors	thought	it	would.	Often,	checked	exceptions	are
caught	(because	the	compiler	requires	the	checked	exception	to	be	handled)	and
then	simply	ignored,	just	to	allow	the	program	to	compile.	This	is	called
“swallowing	an	exception,”	and	it	makes	your	program	very	hard	to	debug
because	it	suppresses	the	information	that	anything	went	wrong	in	the	first	place.
In	most	cases,	ignoring	the	problem	at	compile	time	leads	to	more	errors	at
runtime.
Unchecked	exceptions	have	won	out	in	modern	languages	because	experience
has	shown	that	checked	exceptions	lead	to	more	problems	than	they	solve:	code
duplication,	difficult-to-understand	error	recovery	logic,	and	swallowed
exceptions	with	no	record	of	an	error	even	taking	place.

For	the	More	Curious:	How	Is	Nullability
## Enforced?
Kotlin	has	strict	patterns	around	nullness	when	compared	to	languages	like	Java.
This	is	a	boon	when	working	exclusively	in	Kotlin,	but	how	is	this	pattern
implemented?	Do	Kotlin’s	rules	still	protect	you	when	interoperating	with	a	less
strict	language	like	Java?	Think	back	to	the	printPlayerStatus	function
from	Chapter	4.
fun	printPlayerStatus(auraColor:	String,
isBlessed:	Boolean,
name:	String,
healthStatus:	String)	{
## ...
## }
printPlayerStatus	takes	in	parameters	of	Kotlin	types	String	and
## Boolean.
If	you	are	calling	this	function	from	Kotlin,	then	the	function	signature	is	clear	–
auraColor,	name,	and	healthStatus	must	be	of	type	String,	which	is
not	nullable,	and	isBlessed	must	be	of	type	Boolean,	which	is	also	not
nullable.	However,	because	Java	does	not	have	the	same	rules	regarding
nullability,	a	String	in	Java	could	potentially	be	null.
How	does	Kotlin	maintain	a	null-safe	environment?	Answering	that	question
requires	a	dive	into	the	decompiled	Java	bytecode:
public	static	final	void	printPlayerStatus(@NotNull	String	auraColor,
boolean	isBlessed,
@NotNull	String	name,
@NotNull	String	healthStatus)	{
Intrinsics.checkParameterIsNotNull(auraColor,	"auraColor");
Intrinsics.checkParameterIsNotNull(name,	"name");
Intrinsics.checkParameterIsNotNull(healthStatus,	"healthStatus");
## ...
## }
There	are	two	mechanisms	for	ensuring	that	non-null	parameters	do	not	accept
null	arguments.	First,	note	the	@NotNull	annotations	on	each	of	the	non-
primitive	parameters	to	printPlayerStatus.	These	annotations	serve	as	a
signal	to	callers	of	this	Java	method	that	the	annotated	parameters	should	not
take	null	arguments.	isBlessed	does	not	require	a	@NotNull	annotation,
because	booleans	are	represented	as	primitive	types	in	Java	and,	as	such,	cannot
be	null.
@NotNull	annotations	can	be	found	in	many	Java	projects,	but	they	are
particularly	useful	for	those	calling	Java	methods	from	Kotlin,	as	the	Kotlin

compiler	uses	them	to	determine	whether	a	Java	method	parameter	is	nullable.
You	will	learn	more	about	Kotlin’s	interoperability	with	Java	in	Chapter	20.
The	Kotlin	compiler	goes	a	step	further	in	guaranteeing	that	auraColor,
name,	and	healthStatus	will	not	be	null:	using	a	method	called
Intrinsics.checkParameterIsNotNull.	This	method	is	called	on
each	non-nullable	parameter	and	will	throw	an
IllegalArgumentException	if	a	null	value	manages	to	be	passed	as	an
argument.
In	short,	any	function	that	you	declare	in	Kotlin	will	play	by	Kotlin’s	rules	about
nullness,	even	when	represented	as	Java	code	on	the	JVM.
So	there	you	have	it	–	you	are	doubly	protected	from	a	null	pointer	exception
when	writing	functions	that	take	values	of	non-null	types	in	Kotlin,	even	when
interoperating	with	languages	that	are	less	strict	about	nullness.