

## 4
## Functions
A	function	is	a	reusable	portion	of	code	that	accomplishes	a	specific	task.
Functions	are	a	very	important	part	of	programming.	In	fact,	programs	are
fundamentally	a	series	of	functions	combined	to	accomplish	more	complex
tasks.
You	have	worked	with	some	functions	already,	like	the	println	function,
which	is	provided	by	the	Kotlin	standard	library	for	printing	data	to	the	console.
You	can	also	define	your	own	functions	in	code	that	you	write.	Some	functions
take	in	data	required	to	perform	a	specific	task.	Some	functions	also	return	data,
generating	output	that	can	be	used	elsewhere	after	the	function	has	performed	its
task.
To	get	your	function	feet	wet,	you	will	start	by	using	functions	to	organize
NyetHack’s	existing	code.	Then,	you	will	define	your	own	function	to	add	an
exciting	new	feature	to	NyetHack:	a	fireball	spell.

Extracting	Code	to	Functions
The	logic	you	coded	into	NyetHack	in	Chapter	3	was	sound,	but	it	would	be	a
better	practice	to	organize	it	using	functions.	Your	first	task	is	to	reorganize	your
project	to	encapsulate	much	of	the	logic	you	have	already	written	in	functions.
This	will	set	the	stage	for	adding	new	features	to	NyetHack.
Does	this	mean	you	are	going	to	delete	all	your	code	and	type	the	same	logic	in	a
different	way?	Perish	the	thought.	IntelliJ	will	help	you	group	your	logic	into
functions	easily.
Begin	by	opening	your	NyetHack	project.	Make	sure	the	file	Game.kt	is	open
in	the	editor.
Next,	select	the	conditional	code	that	you	defined	for	generating	the	player’s
healthStatus	message.	Click	and	drag	the	cursor,	beginning	with	the	line
that	defines	healthStatus	and	ending	with	the	closing	curly	brace	for	the
when	expression,	like	so:
## ...
val	healthStatus	=	when	(healthPoints)	{
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
## ...
Control-click	(right-click)	on	the	code	you	selected	and	choose	Refactor	→	Extract
→	Function...	(Figure	4.1).

Figure	4.1		Extracting	logic	to	a	function
The	Extract	Function	dialog	will	pop	up,	as	in	Figure	4.2:

Figure	4.2		The	Extract	Function	dialog
We	will	come	back	to	the	elements	of	this	dialog	shortly.	For	now,	enter
“formatHealthStatus”	for	the	name,	as	shown,	and	leave	everything	else	as	is.
Then,	click	the	OK	button.	IntelliJ	will	add	a	function	definition	to	the	bottom	of
Game.kt,	like	this:
private	fun	formatHealthStatus(healthPoints:	Int,	isBlessed:	Boolean):	String	{
val	healthStatus	=	when	(healthPoints)	{
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
return	healthStatus
## }
Your	formatHealthStatus	function	is	surrounded	by	some	new	code.	We
will	break	this	down	piece	by	piece	next.

Anatomy	of	a	Function
Figure	4.3	shows	the	two	primary	parts	of	a	function,	the	header	and	body,	using
formatHealthStatus	as	a	model:
Figure	4.3		A	function	consists	of	a	function	header	and	a	function
body
Function	header
The	first	part	of	a	function	is	the	function	header.	The	function	header	is	made
up	of	five	parts:	the	visibility	modifier,	function	declaration	keyword,	function
name,	function	parameters,	and	return	type	(Figure	4.4).
Figure	4.4		Anatomy	of	a	function	header
Let’s	look	at	each	of	those	elements	in	some	detail.
Visibility	modifier
Not	all	functions	should	be	visible,	or	accessible,	to	all	other	functions.	Some
might	deal	with	data	that	should	be	kept	private	to	a	particular	file,	for	example.
A	function	can	optionally	begin	with	a	visibility	modifier	(Figure	4.5).	The

visibility	modifier	determines	which	other	functions	can	“see”	–	and	therefore
use	–	the	function.
Figure	4.5		Function	visibility	modifier
By	default,	a	function’s	visibility	is	public	–	meaning	that	all	other	functions
(including	functions	defined	in	other	files)	can	use	the	function.	In	other	words,
if	you	do	not	specify	a	modifier	for	the	function,	the	function	is	considered
public.
In	this	case,	IntelliJ	has	determined	that	this	function	can	have	private	visibility,
since	the	formatHealthStatus	function	is	used	only	within	the	current	file,
Game.kt.	You	will	learn	more	about	the	available	visibility	modifiers	and	how
to	use	them	to	control	which	functions	can	see	the	function	you	define	in
## Chapter	12.
Function	name	declaration
After	the	visibility	modifier	(if	there	is	one)	comes	the	fun	keyword,	followed	by
a	name	for	the	function	(Figure	4.6):
Figure	4.6		Function	keyword	and	name	declaration
You	specified	formatHealthStatus	for	the	function	name	in	the	Extract
Function	dialog,	so	IntelliJ	added	fun	formatHealthStatus	for	the	function’s
name	declaration.
Notice	that	the	name	you	chose	for	the	function,	formatHealthStatus,
starts	with	a	lowercase	letter	and	uses	“camel	case”	naming	with	no	underscores.
All	of	your	function	names	should	conform	to	this	official	standard	naming
convention.

Function	parameters
Next	come	the	function	parameters	(Figure	4.7):
Figure	4.7		Function	parameters
Function	parameters	specify	the	name	and	type	of	each	input	required	for	the
function	to	perform	its	task.	Functions	can	require	zero	to	several	or	more
parameters,	depending	on	the	task	they	are	designed	to	perform.
For	the	formatHealthStatus	function	to	determine	the	health	status
message	it	should	display,	the	healthPoints	and	isBlessed	variables	are
needed,	because	the	when	expression	requires	them	to	check	its	conditions.
Therefore,	formatHealthStatus’s	function	definition	specifies	that	those
two	variables	are	required	as	parameters:
private	fun	formatHealthStatus(healthPoints:	Int,	isBlessed:	Boolean):	String	{
val	healthStatus	=	when	(healthPoints)	{
## ...
in	75..89	->	if	(isBlessed)	{
## ...
}	else	{
## ...
## }
## ...
## }
return	healthStatus
## }
For	each	parameter,	the	definition	also	specifies	the	type	of	data	it	requires.
healthPoints	must	be	an	Int,	and	isBlessed	must	be	a	Boolean.
Note	that	function	parameters	are	always	read-only	–	they	do	not	support
reassignment	within	the	function	body.	In	other	words,	within	the	body	of	a
function,	a	function	parameter	is	a	val,	instead	of	a	var.
Function	return	type
Many	functions	generate	some	type	of	output;	that	is	their	job,	to	send	a	value	of
some	type	back	to	where	they	are	called.	The	final	element	of	the	function
header	is	the	return	type,	which	defines	the	type	of	output	that	the	function	will
return	once	it	has	completed	its	work.

The	return	type	in	formatHealthStatus	specifies	that	the	function	sends
back	a	String	(Figure	4.8):
Figure	4.8		Function	return	type
Function	body
After	the	function	header,	the	function	body	is	defined	within	curly	braces.	The
body	is	where	the	action	the	function	performs	takes	place.	It	may	also	include	a
return	statement	that	indicates	what	data	to	send	back.
In	this	case,	the	extract	function	command	moved	the	definition	of	the
healthStatus	val	(the	code	you	selected	when	you	ran	the	command)	into
the	body	of	the	formatHealthStatus	function.
After	that	is	the	new	line	return	healthStatus.	The	return	keyword	indicates
to	the	compiler	that	the	function	has	finished	its	work	and	is	ready	to	return	its
output	data.	Here,	the	output	data	is	healthStatus,	meaning	that	the	function
will	return	the	value	of	the	healthStatus	variable	–	the	string	selected	based
on	the	logic	in	healthStatus’s	definition.
Function	scope
Notice	that	the	declaration	and	assignment	for	the	healthStatus	variable
occur	within	the	function	body	and	that	its	value	is	returned	at	the	end	of	the
function	body:
private	fun	formatHealthStatus(healthPoints:	Int,	isBlessed:	Boolean):	String	{
val	healthStatus	=	when	(healthPoints)	{
## ...
## }
return	healthStatus
## }
The	healthStatus	variable	is	referred	to	as	a	local	variable	because	it	exists
only	in	the	formatHealthStatus	function’s	body.	Another	way	to	put	this
is	that	the	healthStatus	variable	exists	only	within	the
formatHealthStatus	function’s	scope.	You	can	think	of	scope	as	the
lifespan	for	a	variable.

Because	it	exists	only	within	the	function’s	scope,	healthStatus	will	cease
to	exist	once	formatHealthStatus	completes.	The	function	returns
healthStatus’s	value	to	its	caller,	but	the	variable	that	held	the	value	is	gone
once	the	function	completes.
The	same	is	true	of	the	function	parameters:	The	variables	healthPoints	and
isBlessed	exist	within	the	scope	of	the	function	body	and	cease	to	exist	once
the	function	completes.
In	Chapter	2,	you	saw	an	example	of	a	variable	that	was	not	local	to	a	function
or	class	–	a	file-level	variable:
const	val	MAX_EXPERIENCE:	Int	=	5000
fun	main(args:	Array<String>)	{
## ...
## }
This	file-level	variable	can	be	accessed	from	anywhere	in	the	project	(though	a
visibility	modifier	can	be	added	to	the	declaration	to	change	its	visibility	level).
File-level	variables	remain	initialized	until	program	execution	stops.
Because	of	the	differences	between	local	and	file-level	variables,	the	compiler
enforces	different	requirements	on	when	they	must	be	assigned	an	initial	value,
or	initialized.
File-level	variables	must	always	be	assigned	when	they	are	defined,	or	the	code
will	not	compile.	(You	will	see	certain	exceptions	to	this	in	Chapter	15.)	This
requirement	protects	you	from	unexpected	–	and	unwanted	–	behavior,	like	a
variable	not	having	a	value	when	you	try	to	use	it.
Since	a	local	variable	is	more	limited	in	where	it	can	be	used	–	within	the	scope
of	the	function	in	which	it	is	defined	–	the	compiler	is	more	lenient	about	when	it
must	be	initialized.	A	local	variable	only	has	to	be	initialized	before	it	is	used.
This	means	that	the	following	statement	is	valid:
fun	main(args:	Array<String>)	{
val	name:	String
name	=	"Madrigal"
var	healthPoints:	Int
healthPoints	=	89
healthPoints	+=	3
## ...
## }
So	long	as	you	have	assigned	a	value	before	referencing	the	variable,	the
compiler	permits	the	expression.

Calling	a	Function
IntelliJ	not	only	generated	the	formatHealthStatus	function,	but	it	also
added	a	line	in	place	of	the	code	it	extracted:
fun	main(args:	Array<String>)	{
val	name	=	"Madrigal"
var	healthPoints	=	89
var	isBlessed	=	true
## ...
val	healthStatus	=	formatHealthStatus(healthPoints,	isBlessed)
## ...
## }
This	line	is	a	function	call,	which	triggers	the	function	to	perform	whatever
actions	are	defined	in	its	body.	You	call	a	function	with	its	name,	along	with	data
to	satisfy	any	parameters	required	by	the	function	header.
Compare	the	function	header	for	formatHealthStatus	with	its
corresponding	function	call:
formatHealthStatus(healthPoints:	Int,	isBlessed:	Boolean):	String	//	Header
formatHealthStatus(healthPoints,	isBlessed)																							//	Call
The	definition	of	formatHealthStatus	shows	that	it	requires	two
parameters,	as	discussed	above.	When	you	call	formatHealthStatus,	you
include	in	parentheses	the	inputs	to	those	parameters.	The	inputs	are	called
arguments,	and	providing	them	to	the	function	is	called	passing	in	arguments.
(A	note	about	the	terminology:	While	technically	a	parameter	is	what	a	function
requires	and	an	argument	is	what	the	caller	passes	in	to	fulfill	the	requirement,
you	will	hear	the	two	terms	used	interchangeably.)
Here,	as	the	function	definition	specifies,	you	pass	in	the	value	of
healthPoints	(which,	as	required,	is	an	Int)	and	the	Boolean	value	of
isBlessed.
Run	NyetHack	by	clicking	the	run	button,	and	shazam!	You	will	see	the	same
output	you	have	seen	before:
(Aura:	GREEN)	(Blessed:	YES)
Madrigal	has	some	minor	wounds,	but	is	healing	quite	quickly!
While	the	output	has	not	changed,	NyetHack’s	code	is	now	more	organized	and
maintainable.

Refactoring	to	Functions
Continue	extracting	the	logic	previously	defined	in	the	main	function	into
separate	functions	by	using	the	extract	to	function	feature.	Start	by	refactoring
the	logic	for	the	aura	color.	Select	the	code	from	the	line	where	aura	visibility	is
defined	to	the	end	of	the	if/else	condition	that	checks	the	Boolean	to
determine	what	value	to	print:
## ...
## //	Aura
val	auraVisible	=	isBlessed	&&	healthPoints	>	50	||	isImmortal
val	auraColor	=	if	(auraVisible)	"GREEN"	else	"NONE"
## ...
Next,	select	the	Extract	Function	command.	You	can	Control-click	(right-click)	the
selected	code	and	choose	Refactor	→	Extract	→	Function...,	as	you	did	before.	You
can	also	use	the	menus	to	select	Refactor	→	Extract	→	Function...	Or	you	can	use	the
keyboard	shortcut	Command-Option-M	(Ctrl-Alt-M).	Whichever	way	you
choose,	the	Extract	Function	dialog	you	saw	in	Figure	4.2	appears.
Give	the	new	function	the	name	auraColor.
(If	you	want	to	check	the	resulting	code,	hang	tight:	We	will	show	you	the	full
file	after	you	extract	a	few	more	functions.)
Next,	extract	the	logic	that	prints	the	player’s	status	to	a	new	function.	Select	the
two	calls	to	println	in	main:
## ...
//	Player	status
println("(Aura:	$auraColor)	"	+
"(Blessed:	${if	(isBlessed)	"YES"	else	"NO"})")
println("$name	$healthStatus")
## ...
Extract	them	to	a	function	called	printPlayerStatus.
The	Game.kt	file	now	looks	like	this:
fun	main(args:	Array<String>)	{
val	name	=	"Madrigal"
var	healthPoints	=	89
var	isBlessed	=	true
val	isImmortal	=	false
## //	Aura
val	auraColor	=	auraColor(isBlessed,	healthPoints,	isImmortal)
val	healthStatus	=	formatHealthStatus(healthPoints,	isBlessed)
//	Player	status
printPlayerStatus(auraColor,	isBlessed,	name,	healthStatus)
## }
private	fun	formatHealthStatus(healthPoints:	Int,	isBlessed:	Boolean):	String	{
val	healthStatus	=	when	(healthPoints)	{
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
return	healthStatus
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
(We	have	broken	the	headers	for	printPlayerStatus	and	auraColor
onto	multiple	lines	for	readability	and	to	fit	on	the	page.)
Run	NyetHack.	You	should	see	Madrigal’s	familiar	stats	and	aura	color	printed:
(Aura:	GREEN)	(Blessed:	YES)
Madrigal	has	some	minor	wounds,	but	is	healing	quite	quickly!

## Writing	Your	Own	Functions
Now	that	you	have	organized	NyetHack’s	logic	in	functions,	you	can	proceed	as
planned	to	implement	the	new	fireball	spell.	At	the	bottom	of	Game.kt,	define
a	function	called	castFireball	that	takes	no	parameters.	Make	its	visibility
private.	castFireball	should	have	no	return	statement,	but	it	should	print
the	results	of	casting	the	spell.
Listing	4.1		Adding	a	castFireball	function	(Game.kt)
## ...
private	fun	auraColor(isBlessed:	Boolean,
healthPoints:	Int,
isImmortal:	Boolean):	String	{
val	auraVisible	=	isBlessed	&&	healthPoints	>	50	||	isImmortal
val	auraColor	=	if	(auraVisible)	"GREEN"	else	"NONE"
return	auraColor
## }
private	fun	castFireball()	{
println("A	glass	of	Fireball	springs	into	existence.")
## }
Now,	call	castFireball	at	the	bottom	of	the	main	function.
(castFireball	was	defined	without	parameters,	so	you	do	not	pass	in	any
arguments	to	call	it	–	hence	the	empty	parentheses.)
Listing	4.2		Calling	castFireball	(Game.kt)
fun	main(args:	Array<String>)	{
## ...
//	Player	status
printPlayerStatus(auraColor,	isBlessed,	name,	healthStatus)
castFireball()
## }
## ...
Run	NyetHack	and	admire	your	new	output:
(Aura:	GREEN)	(Blessed:	YES)
Madrigal	has	some	minor	wounds,	but	is	healing	quite	quickly!
A	glass	of	Fireball	springs	into	existence.
Excellent	–	it	appears	the	spell	works	as	intended.	Feel	free	to	have	a	glass	of
Fireball	as	a	celebratory	measure.	(On	second	thought,	better	wait	until	the	end
of	this	chapter.)
One	fireball	is	fine,	but	two	or	more	is	a	party.	Your	player	needs	to	be	able	to
cast	more	than	one	at	a	time.
Update	the	castFireball	function	to	accept	an	Int	parameter	called
numFireballs.	In	the	call	to	castFireball,	pass	in	5	as	the	argument.
Finally,	display	the	number	of	fireballs	in	the	message	that	is	printed.

Listing	4.3		Adding	a	numFireballs	parameter	(Game.kt)
fun	main(args:	Array<String>)	{
## ...
//	Player	status
printPlayerStatus(auraColor,	isBlessed,	name,	healthStatus)
castFireball()
castFireball(5)
## }
## ...
private	fun	castFireball()	{
private	fun	castFireball(numFireballs:	Int)	{
println("A	glass	of	Fireball	springs	into	existence.")
println("A	glass	of	Fireball	springs	into	existence.	(x$numFireballs)")
## }
Run	NyetHack	again.	You	should	see	the	following	output:
(Aura:	GREEN)	(Blessed:	YES)
Madrigal	has	some	minor	wounds,	but	is	healing	quite	quickly!
A	glass	of	Fireball	springs	into	existence.	(x5)
Functions	with	parameters	provide	a	way	for	the	caller	to	supply	the	function
with	input	as	an	argument.	You	can	use	that	input	in	your	function’s	logic	or
simply	print	it	out	in	a	string	template,	as	you	did	here	with	the	value	5.

## Default	Arguments
Sometimes	an	argument	for	a	function	has	a	“usual”	value.	For	example,	with
the	castFireball	function,	five	glasses	of	Fireball	is	excessive.	Typically,
only	two	glasses	of	Fireball	should	appear	when	the	spell	is	cast.	To	make
calling	castFireball	more	efficient,	you	can	use	a	default	argument	to
specify	this.
You	saw	in	Chapter	2	that	a	var	can	be	assigned	an	initial	value	and	later
reassigned.	Similarly,	you	can	assign	a	default	value	for	a	parameter	that	will	be
assigned	if	no	argument	is	specified.	Update	the	castFireball	function	with
a	default	value	for	numFireballs:
Listing	4.4		Giving	the	numFireballs	parameter	a	default	value
(Game.kt)
fun	main(args:	Array<String>)	{
## ...
//	Player	status
printPlayerStatus(auraColor,	isBlessed,	name,	healthStatus)
castFireball(5)
## }
## ...
private	fun	castFireball(numFireballs:	Int)	{
private	fun	castFireball(numFireballs:	Int	=	2)	{
println("A	glass	of	Fireball	springs	into	existence.	(x$numFireballs)")
## }
Now,	by	default,	numFireballs’s	Int	value	will	be	2	if	no	other	argument	is
provided	when	calling	castFireball.	Update	the	main	function,	removing
the	Int	argument	in	the	call	to	castFireball:
Listing	4.5		Using	castFireball’s	default	argument	value
(Game.kt)
fun	main(args:	Array<String>)	{
## ...
//	Player	status
printPlayerStatus(auraColor,	isBlessed,	name,	healthStatus)
castFireball(5)
castFireball()
## }
## ...
Run	NyetHack	again.	With	no	argument	specified	for	castFireball,	you
will	see	the	following	output:
(Aura:	GREEN)	(Blessed:	YES)
Madrigal	has	some	minor	wounds,	but	is	healing	quite	quickly!
A	glass	of	Fireball	springs	into	existence.	(x2)
Because	you	do	not	pass	an	argument	for	the	numFireballs	parameter,	the

default	value	you	defined,	2,	is	used	for	the	function	argument.

Single-Expression	Functions
Kotlin	allows	you	to	reduce	the	amount	of	code	required	to	define	a	function	like
castFireball	or	formatHealthStatus	that	has	only	one	expression	–
that	is,	one	statement	to	be	evaluated.	For	single-expression	functions,	you	can
omit	the	return	type,	curly	braces,	and	return	statement.	Make	those	changes	to
your	castFireball	and	formatHealthStatus	functions,	as	shown
below:
Listing	4.6		Using	optional	single-expression	function	syntax
(Game.kt)
## ...
private	fun	formatHealthStatus(healthPoints:	Int,	isBlessed:	Boolean):	String	{
val	healthStatus	=	when	(healthPoints)	{
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
return	healthStatus
## }
## ...
private	fun	castFireball(numFireballs:	Int	=	2)		{
private	fun	castFireball(numFireballs:	Int	=	2)	=
println("A	glass	of	Fireball	springs	into	existence.	(x$numFireballs)")
## }
Notice	that	instead	of	using	the	function	body	to	specify	the	work	the	function
will	perform,	with	single-expression	function	syntax	you	use	the	assignment
operator	(=),	followed	by	the	expression.
This	optional	syntax	allows	you	to	tighten	up	the	definition	for	functions	with
only	one	expression	that	is	evaluated	to	perform	their	task.	When	you	need	the
results	of	more	than	one	expression,	use	the	function	definition	syntax	you	have
already	seen.
From	this	point	forward,	we	will	favor	using	single-expression	function	syntax
when	possible	to	make	the	code	more	concise.

## Unit	Functions
Not	all	functions	return	a	value.	Some	use	side	effects	instead	to	do	their	work,
like	modifying	the	state	of	a	variable	or	calling	other	functions	that	yield	system
output.	Think	about	your	player	status	and	aura	display	code,	or	the
castFireball	function,	for	example.	They	define	no	return	type	and	have	no
return	statement.	They	use	println	to	do	their	work.
private	fun	castFireball(numFireballs:	Int	=	2)	=
println("A	glass	of	Fireball	springs	into	existence.	(x$numFireballs)")
In	Kotlin,	such	functions	are	known	as	Unit	functions,	meaning	their	return
type	is	Unit.	Click	on	the	castFireball	function’s	name	and	press	Control-
Shift-P	(Ctrl-P).	IntelliJ	will	display	its	return	type	information	(Figure	4.9).
Figure	4.9		castFireball	is	a	Unit	function
What	kind	of	type	is	Unit?	Kotlin	uses	the	Unit	return	type	to	signify	exactly
this:	a	function	that	returns	no	value.	If	the	return	keyword	is	not	used,	it	is
implicit	that	the	return	type	for	that	function	is	Unit.
Prior	to	Kotlin,	many	languages	faced	the	problem	of	describing	a	function	that
does	not	return	anything.	Some	languages	opted	for	a	keyword	void,	which	said,
“There	is	no	return	type;	skip	it,	because	it	does	not	apply.”	This	seems	sound	on
the	surface:	If	the	function	does	not	return	anything,	skip	the	type,	since	there	is
nothing	being	returned.
Unfortunately,	this	solution	fails	to	account	for	an	important	feature	found	in
modern	languages:	generics.	Generics	are	a	feature	of	modern	compiled
language	that	enable	a	great	deal	of	flexibility.	You	will	investigate	generics	in
Kotlin,	which	allow	you	to	specify	functions	that	work	with	many	types,	in
## Chapter	17.
What	do	generics	have	to	do	with	Unit	and	void?	Languages	that	use	the	void
keyword	have	no	good	way	to	deal	with	a	generic	function	that	returns	nothing.
void	is	not	a	type	–	in	fact,	it	says,	“Type	information	is	not	relevant;	skip	it.”
And	there	is	no	way	to	describe	this	“generically,”	so	these	languages	miss	out

on	being	able	to	describe	generic	functions	that	return	nothing.
Kotlin	solves	this	problem	by	specifying	Unit	for	the	return	type	instead.	Unit
indicates	a	function	that	does	not	return	anything,	but	at	the	same	time	it	is
compatible	with	generic	functions	that	must	have	some	type	to	work	with.	This
is	why	Kotlin	uses	Unit:	You	get	the	best	of	both	worlds.

## Named	Function	Arguments
Take	a	look	at	how	you	call	the	printPlayerStatus	function,	passing
arguments	as	parameters:
printPlayerStatus("NONE",	true,	"Madrigal",	status)
Another	way	you	could	call	the	same	function	is:
printPlayerStatus(auraColor	=	"NONE",
isBlessed	=	true,
name	=	"Madrigal",
healthStatus	=	status)
This	optional	syntax	uses	named	function	arguments	and	is	an	alternative	way	to
provide	arguments	to	a	function.	In	certain	cases,	it	grants	several	advantages.
For	example,	using	named	arguments	frees	you	to	pass	the	arguments	to	the
function	in	whatever	order	you	would	like.	For	example,	you	could	also	call
printPlayerStatus	like	this:
printPlayerStatus(healthStatus	=	status,
auraColor	=	"NONE",
name	=	"Madrigal",
isBlessed	=	true)
When	you	do	not	use	named	function	arguments,	you	must	pass	arguments	in	the
order	they	are	defined	on	the	function	header.	With	named	function	arguments,
you	can	pass	arguments	independent	of	the	function	header’s	parameter	order.
Another	benefit	of	named	function	arguments	is	that	they	can	bring	clarity	to
your	code.	When	a	function	requires	a	large	number	of	arguments,	it	can	become
confusing	to	keep	track	of	which	argument	provides	the	value	for	which	function
parameter.	This	is	especially	true	if	the	names	of	the	variables	passed	in	do	not
match	the	names	of	the	defined	function	parameters.	Named	function	arguments
are	always	named	the	same	as	the	parameters	they	provide	values	for.
In	this	chapter,	you	saw	how	to	define	functions	to	encapsulate	your	code’s
logic.	Your	code	has	become	much	cleaner	and	better	organized.	You	also
learned	a	number	of	the	conveniences	built	into	Kotlin’s	function	syntax	to
enable	you	to	write	less	code	that	is	just	as	descriptive:	single-expression
function	syntax,	named	function	arguments,	and	default	arguments.	In	the	next
chapter,	you	will	learn	about	a	different	kind	of	function	available	in	Kotlin	–
anonymous	functions.
Do	not	forget	to	save	NyetHack	and	create	a	copy	before	working	through	the
challenges	below.

For	the	More	Curious:	The	Nothing	Type
In	this	chapter	you	learned	about	the	Unit	type	and	that	a	function	of	the	Unit
type	returns	no	value.
Another	type	that	is	related	to	Unit	is	the	Nothing	type.	Like	Unit,
Nothing	indicates	that	a	function	returns	no	value	–	but	there	the	similarity
ends.	Nothing	lets	the	compiler	know	that	a	function	is	guaranteed	to	never
successfully	complete;	the	function	will	either	throw	an	exception	or	for	some
other	reason	never	return	to	where	it	was	called.
What	is	the	use	of	the	Nothing	type?	One	example	of	Nothing’s	use	is	the
TODO	function,	included	with	the	Kotlin	standard	library.
Take	a	look	at	TODO	by	pressing	the	Shift	key	twice	to	open	the	Search	Everywhere
dialog	and	entering	its	name.
## /**
- Always	throws	[NotImplementedError]	stating	that	operation	is	not	implemented.
## */
public	inline	fun	TODO():	Nothing	=	throw	NotImplementedError()
TODO	throws	an	exception	–	in	other	words,	it	is	guaranteed	to	never	complete
successfully	–	and	returns	the	Nothing	type.
When	would	you	use	TODO?	The	answer	is	in	the	name:	It	tells	you	what	you
still	have	“to	do.”	Consider	the	following	function	that	has	yet	to	be
implemented,	and	instead	calls	TODO:
fun	shouldReturnAString():	String	{
TODO("implement	the	string	building	functionality	here	to	return	a	string")
## }
The	developer	knows	that	the	shouldReturnAString	function	should
return	a	String,	but	they	have	not	yet	completed	the	other	features	needed	to
implement	it.	Notice	that	the	return	type	for	shouldReturnAString	is	a
String,	but	the	function	never	actually	returns	anything	at	all.	Because	of
TODO’s	return	value,	that	is	perfectly	fine.
TODO’s	Nothing	return	type	indicates	to	the	compiler	that	the	function	is
guaranteed	to	cause	an	error,	so	checking	the	return	type	in	the	function	body	is
not	required	past	TODO	because	shouldReturnAString	will	never	return.
The	compiler	is	happy,	and	the	developer	is	able	to	continue	feature	development
without	completing	the	implementation	for	shouldReturnAString	until	all
the	details	are	ready.

Another	feature	of	Nothing	that	is	useful	in	development	is	that	if	you	add
code	below	the	TODO	function,	the	compiler	will	show	a	warning	indicating	that
the	code	is	unreachable	(Figure	4.10):
Figure	4.10		Unreachable	code
Because	of	the	Nothing	type,	the	compiler	can	make	this	assertion:	It	is	aware
that	TODO	will	not	successfully	complete;	therefore,	all	code	after	TODO	is
unreachable.

For	the	More	Curious:	File-Level	Functions	in
## Java
All	of	the	functions	that	you	have	written	so	far	have	been	defined	at	the	file
level	in	Game.kt.	If	you	are	a	Java	developer,	then	this	may	seem	surprising	to
you.	In	Java,	functions	and	variables	can	only	be	defined	within	classes,	a	rule
that	Kotlin	does	not	adhere	to.
How	is	this	possible	if	Kotlin	code	compiles	to	Java	bytecode	to	run	on	the
JVM?	Does	Kotlin	not	have	to	play	by	the	same	rules?	A	look	at	the	decompiled
Java	bytecode	for	Game.kt	should	prove	illuminating:
public	final	class	GameKt	{
public	static	final	void	main(...)	{
## ...
## }
private	static	final	String	formatHealthStatus(...)	{
## ...
## }
private	static	final	void	printPlayerStatus(...)	{
## ...
## }
private	static	final	String	auraColor(...)	{
## ...
## }
private	static	final	void	castFireball(...)	{
## ...
## }
//	$FF:	synthetic	method
//	$FF:	bridge	method
static	void	castFireball$default(...)	{
## ...
## }
## }
File-level	functions	are	represented	in	Java	as	static	methods	on	a	class	with	a
name	based	on	the	file	in	which	they	are	declared	in	Kotlin.	(Method	is	Java	for
“function.”)	In	this	case,	functions	and	variables	defined	in	Game.kt	are
defined	in	Java	in	a	class	called	GameKt.
You	will	see	how	to	declare	functions	in	classes	in	Chapter	12,	but	being	able	to
declare	functions	and	variables	outside	of	them	gives	you	more	flexibility	to
define	a	function	that	is	not	tied	to	a	particular	class	definition.	(And	if	you	are
wondering	what	the	castFireball$default	method	in	GameKt	is	all
about,	this	is	how	default	arguments	are	implemented.	You	will	see	this	in	more
detail	in	Chapter	20.)

For	the	More	Curious:	Function	Overloading
The	castFireball	function	you	defined,	with	its	default	argument	for	the
numFireballs	parameter,	can	be	called	two	ways:
castFireball()
castFireball(numFireballs)
When	a	function	has	multiple	implementations,	like	castFireball,	it	is	said
to	be	overloaded.	Overloading	is	not	always	the	result	of	a	default	argument.
You	can	define	multiple	implementations	with	the	same	function	name.	To	see
what	this	looks	like,	open	the	Kotlin	REPL	(Tools	→	Kotlin	→	Kotlin	REPL)	and
enter	these	function	definitions:
Listing	4.7		Defining	an	overloaded	function	(REPL)
fun	performCombat()	{
println("You	see	nothing	to	fight!")
## }
fun	performCombat(enemyName:	String)	{
println("You	begin	fighting	$enemyName.")
## }
fun	performCombat(enemyName:	String,	isBlessed:	Boolean)	{
if	(isBlessed)	{
println("You	begin	fighting	$enemyName.	You	are	blessed	with	2X	damage!")
}	else	{
println("You	begin	fighting	$enemyName.")
## }
## }
You	have	defined	three	implementations	of	performCombat.	All	are	Unit
functions,	with	no	return	value.	One	takes	no	arguments.	One	takes	a	single
argument,	the	name	of	an	enemy.	And	the	last	takes	two	arguments:	the	enemy’s
name	and	a	Boolean	indicating	whether	the	player	is	blessed.	Each	function
generates	a	different	message	(or	messages)	through	calls	to	println.
When	you	call	performCombat,	how	will	the	REPL	know	which	one	you
want?	It	will	evaluate	the	arguments	you	pass	in	and	find	the	implementation
that	matches	the	number	and	type	of	the	arguments.	In	the	REPL,	call	each	of	the
implementations	of	performCombat,	as	shown:
Listing	4.8		Calling	the	overloaded	functions	(REPL)
performCombat()
performCombat("Ulrich")
performCombat("Hildr",	true)
Your	output	will	read:
You	see	nothing	to	fight!
You	begin	fighting	Ulrich.
You	begin	fighting	Hildr.	You	are	blessed	with	2X	damage!

Notice	that	the	implementation	of	the	overloaded	function	was	selected	based	on
how	many	arguments	you	provided.

For	the	More	Curious:	Function	Names	in
## Backticks
Kotlin	includes	a	feature	that	might,	at	first	glance,	seem	slightly	peculiar:	the
ability	to	define	or	invoke	a	function	named	using	spaces	and	other	unusual
characters,	so	long	as	they	are	surrounded	using	the	backtick	symbol,	`.	For
example,	you	can	define	a	function	like	this:
fun	`**~prolly	not	a	good	idea!~**`()	{
## ...
## }
And	you	could	then	invoke	`**~prolly	not	a	good	idea!~**`	like
this:
`**~prolly	not	a	good	idea!~**`()
Why	is	this	feature	included?	You	should	never	name	a	function	anything	like
`**~prolly	not	a	good	idea!~**`.	(Nor	with	an	emoji.	Please
backtick	responsibly.)	There	are	several	valid	reasons	the	function	name
backticks	exist.
The	first	is	to	support	Java	interoperability.	Kotlin	includes	great	support	for
invoking	methods	from	existing	Java	code	within	a	Kotlin	file.	(You	will	tour	a
number	of	Java	interoperability	features	in	Chapter	20.)	Because	Kotlin	and	Java
have	different	reserved	keywords,	words	that	are	forbidden	for	use	as	function
names,	the	function	name	backticks	allow	you	to	dodge	any	potential	conflict
when	interoperability	is	important.
For	example,	imagine	a	Java	method	name	from	a	legacy	Java	project,	is:
public	static	void	is()	{
## ...
## }
In	Kotlin,	is	is	a	reserved	keyword	(the	Kotlin	standard	library	includes	an	is
operator;	it	allows	you	to	check	the	type	of	an	instance,	as	you	will	see	in
Chapter	14).	In	Java,	however,	is	is	a	valid	method	name,	since	no	is	keyword
exists	in	the	language.	Because	of	the	backtick	feature,	you	are	able	to	invoke	a
Java	is	method	from	Kotlin,	like	so:
fun	doStuff()	{
`is`()	//	Invokes	the	Java	`is`	method	from	Kotlin
## }
In	this	case	the,	backtick	feature	supports	interoperating	with	a	Java	method	that
would	otherwise	be	inaccessible	due	to	its	name.

The	second	reason	for	the	feature	is	to	support	more	expressive	names	of
functions	that	are	used	in	a	testing	file.	For	example,	a	function	name	like	this:
fun	`users	should	be	signed	out	when	they	click	logout`()	{
//	Do	test
## }
Is	more	expressive	and	readable	than	this:
fun	usersShouldBeSignedOutWhenTheyClickLogout()	{
//	Do	test
## }
Using	backticks	to	provide	an	expressive	name	for	a	test	function	is	the
exception	to	the	“lowercase	first	letter,	followed	by	camel	case”	naming	standard
for	functions.

Challenge:	Single-Expression	Functions
Earlier,	you	saw	the	single-expression	function	syntax	as	a	way	to	make
functions	that	execute	one	statement	more	concise.	Can	you	convert
auraColor	to	use	the	single-expression	function	syntax?

## Challenge:	Fireball	Inebriation	Level
Casting	fireballs	does	not	just	print	a	message	to	the	console.	While	NyetHack
fireballs	are	more	delicious	than	strong,	they	do	have	an	intoxicating	effect	on
the	caster.	Make	the	castFireball	function	return	a	resulting	inebriation
value	that	depends	on	the	number	of	fireballs	cast.	The	inebriation	value	should
be	between	1	and	50,	with	50	being	the	maximum	level	of	intoxication	in	the
game.

## Challenge:	Inebriation	Status
Building	on	your	last	challenge,	display	the	player’s	inebriation	status	based	on
the	inebriation	value	returned	from	castFireball.	Have	the	displayed
inebriation	status	follow	these	rules:
Inebriation	levelInebriation	status
## 1-10tipsy
## 11-20sloshed
## 21-30soused
## 31-40stewed
41-50..t0aSt3d

## BIG	NERD	RANCH
## CODING	BOOTCAMPS
Looking	 for	 additional	 support?	 Look	 into	 one	 of	 our	coding	 bootcamps.
Students	learn	from	authors	and	full-time	consultants	who	work	on	projects
every	day.	Don’t	take	our	word	for	it;	hear	from	our	alumni:
LIFE	CHANGING.	The	Big	Nerd	Ranch	changed	my	life.	I	was	working
as	a	lawyer	and	writing	software	on	the	side.	I	wanted	to	transition	to
writing	software	full-time,	but	I	didn't	have	the	confidence	to	make	the
switch.	I	heard	about	the	Big	Nerd	Ranch	from	a	friend	and	I	decided	to
attend	a	seven-day	bootcamp	in	Atlanta.	I	was	very	nervous	because	I
wasn't	a	professional	software	developer	and	I	didn't	have	a	computer
science	degree.	The	first	morning,	my	instructor	made	me	feel	at	ease.
As	we	worked	through	the	materials	and	the	examples,	I	noticed	that	I
knew	as	much	or	more	than	my	peers.	I	took	advantage	of	the	lunch
and	 dinner	 time	 to	 speak	 with	 my	 instructors	 and	 peers	 and	my
confidence	continued	to	grow.	I	got	home	and,	with	my	Big	Nerd	Ranch
certification	in	hand,	I	applied	to	several	software	 development	 jobs.
After	several	offers,	I	closed	up	my	law	firm	and	started	my	new	career
as	a	software	developer.	I	still	work	as	a	software	developer.	I	even
write	software	for	some	of	my	lawyer	friends.	All	thanks	to	The	Big	Nerd
## Ranch.
—Larry	Staton,	Jr.,	Alumnus
We	 offer	 classes	 in	 Android,	 Kotlin,	 Front	 End,	 iOS,	 Swift,	 design,	 and
more.	Take	$100	off	your	bootcamp	tuition	by	using	code	BNRGUIDE100
when	you	register.
Alumni	 gain	 access	 to	 an	 exclusive	 developer	 community	to	 network	 and
nurture	their	career	growth.
www.bignerdranch.com