

## 9
## Standard	Functions
Standard	functions	are	general	utility	functions	in	the	Kotlin	standard	library	that
accept	lambdas	to	specify	their	work.	In	this	chapter	you	will	meet	the	six	most
commonly	used	standard	functions	–	apply,	let,	run,	with,	also,	and
takeIf	–	and	see	examples	of	what	they	can	do.
This	is	not	a	hands-on	chapter,	and	you	will	not	be	adding	to	NyetHack	or
Sandbox.	As	always,	however,	we	encourage	you	to	experiment	with	the	code
examples	in	the	REPL.
In	this	chapter	we	will	refer	to	an	instance	of	a	type	using	the	term	receiver.	This
is	because	Kotlin’s	standard	functions	are	extension	functions	under	the	hood,
and	receiver	is	the	term	for	the	subject	of	an	extension	function.	You	will	learn
about	extensions,	which	are	a	flexible	way	to	define	functions	for	use	with
different	types,	in	Chapter	18.

apply
First	on	our	tour	of	the	standard	functions	is	apply.	apply	can	be	thought	of
as	a	configuration	function:	It	allows	you	to	call	a	series	of	functions	on	a
receiver	to	configure	it	for	use.	After	the	lambda	provided	to	apply	executes,
apply	returns	the	configured	receiver.
apply	can	be	used	to	reduce	the	amount	of	repetition	when	configuring	an
object	for	use.	Here	is	an	example	of	configuring	a	file	instance	without	apply:
val	menuFile	=	File("menu-file.txt")
menuFile.setReadable(true)
menuFile.setWritable(true)
menuFile.setExecutable(false)
Using	apply,	the	same	configuration	can	be	achieved	with	less	repetition:
val	menuFile	=	File("menu-file.txt").apply	{
setReadable(true)
setWritable(true)
setExecutable(false)
## }
apply	allows	you	to	drop	the	variable	name	from	every	function	call	performed
to	configure	the	receiver.	This	is	because	apply	scopes	each	function	call
within	the	lambda	to	the	receiver	it	is	called	on.
This	behavior	is	sometimes	referred	to	as	relative	scoping,	because	all	the
function	calls	within	the	lambda	are	now	called	relative	to	the	receiver.	Another
way	to	say	this	is	that	they	are	implicitly	called	on	the	receiver:
val	menuFile	=	File("menu-file.txt").apply	{
setReadable(true)		//	Implicitly,	menuFile.setReadable(true)
setWritable(true)		//	Implicitly,	menuFile.setWritable(true)
setExecutable(false)		//	Implicitly,	menuFile.setExecutable(false)
## }

let
Another	commonly	used	standard	function	is	let,	which	you	encountered	in
Chapter	6.	let	scopes	a	variable	to	the	lambda	provided	and	makes	the
keyword	it,	which	you	learned	about	in	Chapter	5,	available	to	refer	to	it.	Here
is	an	example	of	let,	which	squares	the	first	number	in	a	list:
val	firstItemSquared	=	listOf(1,2,3).first().let	{
it	*	it
## }
Without	let,	you	would	need	to	assign	the	first	element	to	a	variable	to	do	the
multiplication:
val	firstElement	=	listOf(1,2,3).first()
val	firstItemSquared	=	firstElement	*	firstElement
When	combined	with	other	Kotlin	syntax,	let	provides	additional	benefits.	You
saw	in	Chapter	6	that	the	null	coalescing	operator	and	let	can	be	combined	to
work	on	a	nullable	type.	Consider	the	following	example	that	customizes	a
greeting	message	depending	on	whether	a	player	is	recognized	by	the	tavern
master:
fun	formatGreeting(vipGuest:	String?):	String	{
return	vipGuest?.let	{
"Welcome,	$it.	Please,	go	straight	back	-	your	table	is	ready."
}	?:	"Welcome	to	the	tavern.	You'll	be	seated	soon."
## }
Since	the	vipGuest	string	is	nullable,	it	is	important	to	deal	with	the
possibility	of	null	before	calling	functions	on	it.	Using	the	safe	call	operator
means	that	let	executes	if	and	only	if	the	string	is	non-null	–	and,	if	let	is
executed,	that	means	that	the	it	argument	is	non-null.
Compare	formatGreeting	using	let	with	a	version	that	does	not	use	let:
fun	formatGreeting(vipGuest:	String?):	String	{
return	if	(vipGuest	!=	null)	{
"Welcome,	$vipGuest.	Please,	go	straight	back	-	your	table	is	ready."
}	else	{
"Welcome	to	the	tavern.	You'll	be	seated	shortly."
## }
## }
This	version	of	formatGreeting	is	functionally	equivalent,	but	slightly	more
verbose.	The	if/else	structure	uses	the	full	vipGuest	variable	name	twice:
once	in	the	condition	and	once	to	create	the	resulting	string.	let,	on	the	other
hand,	allows	a	fluent	or	chainable	style	that	only	requires	the	variable	name	to
be	used	one	time.
let	can	be	called	on	any	kind	of	receiver	and	returns	the	result	of	evaluating	the

lambda	you	provide.	Here,	let	is	called	on	a	nullable	string,	vipGuest.	The
lambda	passed	to	let	accepts	the	receiver	it	is	called	on	as	its	only	argument.
You	can	therefore	access	the	argument	using	the	it	keyword.
Several	differences	between	let	and	apply	are	worth	mentioning:	As	you	saw,
let	passes	the	receiver	to	the	lambda	you	provide,	but	apply	passes	nothing.
Also,	apply	returns	the	current	receiver	once	the	anonymous	function
completes.	let,	on	the	other	hand,	returns	the	last	line	of	the	lambda	(the
lambda	result).
Standard	functions	like	let	can	also	be	used	to	reduce	the	risk	of	accidentally
changing	a	variable,	because	the	argument	let	passes	to	the	lambda	is	a	read-
only	function	parameter.	You	will	see	an	example	of	this	application	of	standard
functions	in	Chapter	12.

run
Next	up	on	our	tour	of	the	standard	functions	is	run.	run	is	similar	to	apply
in	that	it	provides	the	same	relative	scoping	behavior.	However,	unlike	apply,
run	does	not	return	the	receiver.
Say	you	wanted	to	check	whether	a	file	contains	a	particular	string:
val	menuFile	=	File("menu-file.txt")
val	servesDragonsBreath	=	menuFile.run	{
readText().contains("Dragon's	Breath")
## }
The	readText	function	is	implicitly	performed	on	the	receiver	–	the	File
instance.	This	is	just	like	the	setReadable,	setWriteable,	and
setExecutable	functions	you	saw	with	apply.	However,	unlike	apply,
run	returns	the	lambda	result	–	here,	a	true	or	false	value.
run	can	also	be	used	to	execute	a	function	reference	on	a	receiver.	You	used
function	references	in	Chapter	5;	here	is	an	example	that	shows	their	use	with
run:
fun	nameIsLong(name:	String)	=	name.length	>=	20
"Madrigal".run(::nameIsLong)		//	False
"Polarcubis,	Supreme	Master	of	NyetHack".run(::nameIsLong)	//	True
While	code	like	this	is	equivalent	to	nameIsLong(“Madrigal”),	the	benefits	of
using	run	become	clear	when	there	are	multiple	function	calls:	Chained	calls
using	run	are	easier	to	read	and	follow	than	nested	function	calls.	For	example,
consider	the	following	code	that	checks	whether	a	player’s	name	is	10	characters
or	longer,	formats	a	message	depending	on	the	result,	and	prints	the	result.
fun	nameIsLong(name:	String)	=	name.length	>=	20
fun	playerCreateMessage(nameTooLong:	Boolean):	String	{
return	if	(nameTooLong)	{
"Name	is	too	long.	Please	choose	another	name."
}	else	{
"Welcome,	adventurer"
## }
## }
"Polarcubis,	Supreme	Master	of	NyetHack"
.run(::nameIsLong)
.run(::playerCreateMessage)
## .run(::println)
Compare	the	calls	chained	with	run	to	calling	the	three	functions	using	nested
syntax:
println(playerCreateMessage(nameIsLong("Polarcubis,	Supreme	Master	of	NyetHack")))
The	nested	function	calls	are	more	difficult	to	understand	because	they	require
the	reader	to	work	from	the	inside	out,	rather	than	the	more	familiar	top	to

bottom.
Note	that	there	is	a	second	flavor	of	run	that	is	not	called	on	a	receiver.	This
form	is	far	less	commonly	seen,	but	we	include	it	here	for	completeness:
val	status	=	run	{
if	(healthPoints	==	100)	"perfect	health"	else	"has	injuries"
## }

with
with	is	a	variant	of	run.	It	behaves	identically,	but	it	uses	a	different	calling
convention.	Unlike	the	standard	functions	you	have	seen	so	far,	with	requires
its	argument	to	be	accepted	as	the	first	parameter	rather	than	calling	the	standard
function	on	a	receiver	type:
val	nameTooLong	=	with("Polarcubis,	Supreme	Master	of	NyetHack")	{
length	>=	20
## }
Instead	of	calling	with	on	the	string,	as	in	"Polarcubis,	Supreme	Master	of
NyetHack".run,	the	string	is	passed	as	the	first	(in	this	case,	only)	argument	to
with.
This	is	inconsistent	with	the	way	you	work	with	the	rest	of	the	standard
functions,	making	it	a	less	favorable	choice	than	run.	In	fact,	we	recommend
avoiding	with	and	using	run	instead.	We	are	including	with	here	so	that	if
you	encounter	it	in	the	wild	you	will	know	what	it	means	(and	possibly	consider
replacing	it	with	run).

also
The	also	function	works	very	similarly	to	the	let	function.	Just	like	let,
also	passes	the	receiver	you	call	it	on	as	an	argument	to	a	lambda	you	provide.
But	there	is	one	major	difference	between	let	and	also:	also	returns	the
receiver,	rather	than	the	result	of	the	lambda.
This	makes	also	especially	useful	for	adding	multiple	side	effects	from	a
common	source.	In	the	example	below,	also	is	called	twice	to	organize	two
different	operations:	One	prints	the	filename,	and	the	other	assigns	a	variable,
fileContents,	with	the	contents	of	the	file.
var	fileContents:	List<String>
## File("file.txt")
## .also	{
print(it.name)
## }.also	{
fileContents	=	it.readLines()
## }
## }
Since	also	returns	the	receiver	instead	of	the	result	of	the	lambda,	you	can
continue	to	chain	additional	function	calls	on	to	the	original	receiver.

takeIf
The	last	stop	on	our	tour	of	the	standard	functions	is	takeIf.	takeIf	works	a
bit	differently	than	the	other	standard	functions:	It	evaluates	a	condition	provided
in	a	lambda,	called	a	predicate,	that	returns	either	true	or	false	depending	on	the
conditions	defined.	If	the	condition	evaluates	as	true,	the	receiver	is	returned
from	takeIf.	If	the	condition	is	false,	null	is	returned	instead.
Consider	the	following	example,	which	reads	a	file	if	and	only	if	it	is	readable
and	writable.
val	fileContents	=	File("myfile.txt")
.takeIf	{	it.canRead()	&&	it.canWrite()	}
?.readText()
Without	takeIf,	this	would	be	more	verbose:
val	file	=	File("myfile.txt")
val	fileContents	=	if	(file.canRead()	&&	file.canWrite())	{
file.readText()
}	else	{
null
## }
The	takeIf	version	does	not	require	the	temporary	variable	file,	nor	does	it
need	to	specify	the	possibility	of	a	null	return.	takeIf	is	useful	for	checking
that	some	condition	required	for	assigning	a	variable	or	proceeding	with	work	is
true	before	continuing.	Conceptually,	takeIf	is	similar	to	an	if	statement,	but
with	the	advantage	of	being	directly	callable	on	an	instance,	often	allowing	you
to	remove	a	temporary	variable	assignment.
takeUnless
We	said	that	the	tour	was	over,	but	there	is	a	complementary	function	to
takeIf	that	we	should	mention,	if	only	to	warn	you	away	from	it:
takeUnless.	The	takeUnless	function	is	exactly	like	takeIf	except	that
it	returns	the	original	value	if	the	condition	you	define	is	false.	This	example
reads	the	file	if	it	is	not	hidden	(and	returns	null	otherwise):
val	fileContents	=	File("myfile.txt").takeUnless	{	it.isHidden	}?.readText()
We	recommend	that	you	limit	the	use	of	takeUnless,	especially	for	more
complicated	condition-checking,	because	it	takes	longer	for	human	readers	of
your	program	to	interpret.	Compare	the	“understandability”	of	these	two
phrases:

“Return	the	value	if	the	condition	is	true”	–	takeIf
“Return	the	value	unless	the	condition	is	true”	–	takeUnless
If	you	found	yourself	having	to	pause	slightly	for	the	second	phrase,	you	are	like
us:	takeUnless	seems	to	be	a	less	natural	way	of	describing	the	logic	you
want	to	express.
For	simple	conditions	(as	in	the	example	above),	takeUnless	is	not
problematic.	But	with	more	complicated	examples,	we	find	takeUnless	is
harder	to	parse	(for	human	brains,	anyway).

## Using	Standard	Library	Functions
Table	9.1	summarizes	the	Kotlin	standard	library	functions	discussed	in	this
chapter:
Table	9.1		Standard	functions
## Function
Passes	receiver	to
lambda	as	argument?
## Provides
relative
scoping?
## Returns
let
YesNoLambda	result
apply
NoYesReceiver
run

a
NoYesLambda	result
with

b
NoYesLambda	result
also
YesNoReceiver
takeIf
YesNoNullable	version
of	receiver
takeUnless
YesNoNullable	version
of	receiver
a
The	non-receiver	version	of	run	(less	commonly	used)	passes	no	receiver,
performs	no	relative	scoping,	and	returns	the	lambda	result.
b
with	is	not	called	on	the	receiver	like	this:	"hello.with	{..}".	Instead,	it
treats	the	first	argument	as	the	receiver,	the	second	being	the	lambda,	like	this:
with("hello"){..}.	It	is	the	only	standard	function	that	works	this	way,
which	is	why	we	recommend	avoiding	it.
In	this	chapter,	you	saw	how	to	simplify	your	code	using	standard	functions.
They	give	you	the	ability	to	write	code	that	is	not	only	concise	but	also	has	the
unique	feel	of	Kotlin.	We	will	use	standard	functions	in	the	rest	of	this	book
where	applicable.
In	Chapter	2,	you	saw	how	to	represent	data	using	variables.	In	the	next	chapter,
you	will	learn	how	to	represent	series	of	data	with	variables	of	Kotlin’s	List
and	Set	collection	types.