

## 2
Variables,	Constants,	and	Types
This	chapter	will	introduce	you	to	variables,	constants,	and	Kotlin’s	basic	data
types	–	fundamental	elements	of	any	program.	You	use	variables	and	constants
to	store	values	and	pass	data	around	in	your	application.	Types	describe	the
particular	kind	of	data	that	is	held	by	a	constant	or	variable.
There	are	important	differences	between	each	of	the	data	types	and	between
variables	and	constants	that	shape	how	they	are	used.

## Types
Variables	and	constants	have	a	data	type	that	you	specify.	The	type	describes	the
data	that	is	held	by	a	variable	or	constant	and	tells	the	compiler	how	type
checking	will	be	handled,	a	feature	in	Kotlin	that	prevents	the	assignment	of	the
wrong	kind	of	data	to	a	variable	or	constant.
To	see	this	idea	in	action,	you	are	going	to	add	a	file	to	the	Sandbox	project	you
created	in	Chapter	1.	Open	IntelliJ.	The	Sandbox	project	will	likely	open
automatically,	because	IntelliJ	reopens	your	most	recent	project.	If	it	does	not,
you	can	open	Sandbox	from	the	list	of	recent	files	on	the	lefthand	side	of	the
welcome	dialog	or	by	selecting	File	→	Open	Recent	→	Sandbox.
Begin	by	adding	a	new	file	to	the	project	by	right-clicking	src	in	the	project
tool	window.	(You	may	need	to	click	the	Sandbox	disclosure	arrow	to	see	src.)
Select	New	→	Kotlin	File/Class,	and	name	the	file	TypeIntro.	The	new	file	will
open	in	the	editor.
The	main	function,	as	you	saw	in	Chapter	1,	defines	the	entry	point	for	your
program.	IntelliJ	offers	a	shortcut	for	writing	this	function:	Type	the	word
“main”	in	TypeIntro.kt	and	press	the	Tab	key.	IntelliJ	will	automatically
add	the	basic	elements	of	the	function	for	you,	as	shown	in	Listing	2.1.
Listing	2.1		Adding	a	main	function	(TypeIntro.kt)
fun	main(args:	Array<String>)	{
## }

Declaring	a	Variable
Imagine	you	are	writing	an	adventure	game	that	allows	a	player	to	explore	an
interactive	world.	You	may	want	a	variable	for	keeping	track	of	the	player’s
score.
In	TypeIntro.kt,	create	your	first	variable,	called	experiencePoints,
and	assign	it	a	value:
Listing	2.2		Declaring	an	experiencePoints	variable
(TypeIntro.kt)
fun	main(args:	Array<String>)	{
var	experiencePoints:	Int	=	5
println(experiencePoints)
## }
Here,	you	have	assigned	an	instance	of	the	type	Int	to	a	variable	called
experiencePoints.	Let’s	walk	through	each	part	of	what	happened.
You	defined	a	variable	using	the	keyword	var,	which	indicates	that	you	want	to
declare	a	new	variable,	followed	by	the	new	variable’s	name.
Next,	you	specified	the	type	definition	for	the	variable,	:	Int,	which	indicates
that	experiencePoints	will	hold	an	integer	(whole	number)	value.
Last,	you	used	the	assignment	operator	(=)	to	assign	what	is	on	the	righthand
side	(an	instance	of	the	Int	type,	specifically	5)	to	what	is	on	the	lefthand	side
(experiencePoints).
Figure	2.1	shows	the	experiencePoints	variable’s	definition	in	diagram
form.

Figure	2.1		Anatomy	of	a	variable	definition
After	defining	the	variable,	you	print	its	value	to	the	console	using	the
println	function.
Run	the	program	by	clicking	the	run	button	next	to	the	main	function	and
selecting	Run	'TypeIntroKt'.	The	result	printed	to	the	console	is	5,	the	value	you
assigned	to	experiencePoints.
Now,	try	assigning	experiencePoints	the	value	"thirty-two"	instead.
(The	strike-through	indicates	code	you	are	to	delete.)
Listing	2.3		Assigning	"thirty-two"	to	experiencePoints
(TypeIntro.kt)
fun	main(args:	Array<String>)	{
var	experiencePoints:	Int	=	5
var	experiencePoints:	Int	=	"thirty-two"
println(experiencePoints)
## }
Run	main	again	by	clicking	the	run	button.	This	time,	the	Kotlin	compiler
displays	an	error:
Error:(2,	33)	Kotlin:	Type	mismatch:	inferred	type	is	String	but	Int	was	expected
When	you	typed	this	code,	you	may	have	noticed	the	red	underline	beneath
"thirty-two".	This	is	IntelliJ’s	signal	that	the	program	has	an	error.	Hover
over	"thirty-two"	to	read	the	details	of	the	detected	problem	(Figure	2.2).

Figure	2.2		Type	mismatch	disclosure
Kotlin	uses	a	static	type	system	–	meaning	the	compiler	labels	the	source	code
you	define	with	types	so	that	it	can	ensure	the	code	you	wrote	is	valid.	IntelliJ
also	checks	code	as	you	type	it	and	notices	when	an	instance	of	a	particular	type
is	incorrectly	assigned	to	a	variable	of	a	different	type.	This	feature	is	called
static	type	checking,	and	it	tells	you	about	programming	mistakes	before	you
even	compile	the	program.
To	fix	the	error,	change	the	value	assigned	to	experiencePoints	to	an	Int
that	matches	its	declared	type	by	changing	"thirty-two"	back	to	5:
Listing	2.4		Fixing	the	type	error	(TypeIntro.kt)
fun	main(args:	Array<String>)	{
var	experiencePoints:	Int	=	"thirty-two"
var	experiencePoints:	Int	=	5
println(experiencePoints)
## }
A	variable	can	be	reassigned	in	the	course	of	your	program.	If	the	player	gains
more	experience,	for	example,	you	can	assign	a	new	value	to	the
experiencePoints	variable.	Add	5	to	the	experiencePoints	variable,
as	shown:
Listing	2.5		Adding	5	to	experiencePoints	(TypeIntro.kt)
fun	main(args:	Array<String>)	{
var	experiencePoints:	Int	=	5
experiencePoints	+=	5
println(experiencePoints)
## }
After	assigning	the	experiencePoints	variable	a	value	of	5,	you	use	the
addition	and	assignment	operator	(+=)	to	add	5	to	the	original	value.	Run	the
program	again.	You	will	see	the	number	10	printed	to	the	console.

Kotlin’s	Built-In	Types
You	have	seen	variables	that	are	of	the	String	type	and	variables	of	the	Int
type.	Kotlin	also	has	types	to	handle	values	like	true/false,	lists	of	elements,	and
key-value	pairs	for	defining	mappings	of	elements.	Table	2.1	shows	many	of	the
commonly	used	built-in	types	available	in	Kotlin:
Table	2.1		Commonly	used	built-in	types
TypeDescriptionExamples
## String
## Textual
data
"Estragon"
"happy	meal"
## Char
## Single
character
## 'X'
Unicode	character	U+0041
## Boolean
## True/false
values
true
false
## Int
## Whole
numbers
"Estragon".length
## 5
## Double
## Decimal
numbers
## 3.14
## 2.718
## List
## Collections
of
elements
## 3,	1,	2,	4,	3
"root	beer",	"club	soda",	"coke"
## Set
## Collections
of
unique
elements
"Larry",	"Moe",	"Curly"
"Mercury",	"Venus",	"Earth",	"Mars",	"Jupiter",
"Saturn",	"Uranus",	"Neptune"
## Map
## Collections
of
key-value
pairs
"small"	to	5.99,	"medium"	to	7.99,	"large"	to
## 10.99
If	you	have	not	seen	all	of	these	types,	do	not	be	concerned	–	you	will	learn

about	all	of	them	throughout	the	course	of	this	book.	In	particular,	you	will	learn
more	about	strings	in	Chapter	7	and	numbers	in	Chapter	8,	and	you	will	learn
about	lists,	sets,	and	maps,	together	called	collection	types,	in	Chapter	10	and
## Chapter	11.

Read-Only	Variables
So	far,	you	have	seen	variables	whose	values	can	be	reassigned.	But	often,	you
will	want	to	use	variables	whose	values	should	not	change	in	your	program.	For
example,	in	the	text	adventure	game,	the	player’s	name	will	not	change	after	it
has	been	initially	assigned.
Kotlin	provides	a	different	syntax	for	declaring	read-only	variables	–	variables
that	cannot	be	modified	once	they	are	assigned.
You	declare	a	variable	that	can	be	modified	using	the	var	keyword.	To	declare	a
read-only	variable,	you	use	the	val	keyword.
Colloquially,	variables	whose	values	can	change	are	referred	to	as	vars	and	read-
only	variables	are	referred	to	as	vals.	We	will	follow	this	convention	from	now
on,	since	“variable”	and	“read-only	variable”	are	less	distinct.	vars	and	vals	are
both	considered	“variables,”	so	we	will	continue	to	use	that	term	to	refer	to	them
as	a	group.
Add	a	val	definition	for	the	player’s	name	and	print	it	after	the	experience
points:
Listing	2.6		Adding	a	playerName	val	(TypeIntro.kt)
fun	main(args:	Array<String>)	{
val	playerName:	String	=	"Estragon"
var	experiencePoints:	Int	=	5
experiencePoints	+=	5
println(experiencePoints)
println(playerName)
## }
Run	the	program	by	clicking	the	run	button	next	to	the	main	function	and
selecting	Run	'TypeIntroKt'.	You	will	see	the	values	of	experiencePoints	and
playerName	printed	in	the	console:
## 10
## Estragon
Next,	try	reassigning	playerName	to	a	different	String	value,	using	the	=
assignment	operator,	and	run	the	program	again.
Listing	2.7		Trying	to	change	playerName’s	value	(TypeIntro.kt)
fun	main(args:	Array<String>)	{
val	playerName:	String	=	"Estragon"
playerName	=	"Madrigal"
var	experiencePoints:	Int	=	5
experiencePoints	+=	5
println(experiencePoints)
println(playerName)
## }

You	will	see	the	following	compilation	error:
Error:(3,	5)	Kotlin:	Val	cannot	be	reassigned
The	compiler	complains	because	you	tried	to	modify	a	val.	Once	a	val	has	been
assigned,	it	can	never	be	reassigned.
Delete	the	second	assignment	to	fix	the	reassignment	error:
Listing	2.8		Fixing	the	val	reassignment	error	(TypeIntro.kt)
fun	main(args:	Array<String>)	{
val	playerName:	String	=	"Estragon"
playerName	=	"Madrigal"
var	experiencePoints:	Int	=	5
experiencePoints	+=	5
println(experiencePoints)
println(playerName)
## }
vals	are	useful	for	guarding	against	accidentally	changing	variables	that	should
be	read-only.	For	this	reason,	we	recommend	that	you	use	a	val	any	time	you	do
not	need	a	var.
IntelliJ	can	detect	when	a	var	can	be	made	a	val	instead	by	analyzing	your	code
statically.	If	a	var	is	never	changed,	IntelliJ	will	suggest	that	you	convert	it	to	a
val.	We	suggest	you	follow	IntelliJ’s	suggestion	–	unless	you	are	about	to	write
the	code	that	reassigns	the	var.	To	see	what	IntelliJ’s	suggestion	looks	like,
change	playerName	to	a	var:
Listing	2.9		Changing	playerName	to	be	reassignable
(TypeIntro.kt)
fun	main(args:	Array<String>)	{
val	playerName:	String	=	"Estragon"
var	playerName:	String	=	"Estragon"
var	experiencePoints:	Int	=	5
experiencePoints	+=	5
println(experiencePoints)
println(playerName)
## }
Because	the	value	of	playerName	is	never	reassigned,	it	does	not	need	to	be
(and	should	not	be)	a	var.	Notice	that	IntelliJ	has	added	a	mustard-colored
highlight	to	the	line	with	the	var	keyword.	If	you	mouse	over	the	var	keyword,
IntelliJ	explains	the	suggested	improvement	(Figure	2.3).

Figure	2.3		Variable	never	modified
As	expected,	IntelliJ	suggests	converting	playerName	to	a	val.	To	apply	the
suggestion,	click	on	the	var	keyword	next	to	playerName	and	press	Option-
Return	(Alt-Enter).	In	the	pop-up,	select	Make	variable	immutable	(Figure	2.4).
Figure	2.4		Making	a	variable	immutable
IntelliJ	automatically	converts	the	var	to	a	val	(Figure	2.5).
Figure	2.5		Immutable	playerName
As	we	said	earlier,	we	recommend	that	you	use	a	val	any	time	you	can,	so	that
Kotlin	can	warn	you	about	accidental	reassignments.	We	also	recommend	that

you	pay	attention	to	IntelliJ’s	suggestions	for	code	improvement.	You	may	not
always	use	them,	but	they	are	always	worth	taking	a	look	at.

## Type	Inference
Notice	that	the	type	definitions	you	specified	for	the	playerName	and
experiencePoints	variables	are	grayed	out	in	IntelliJ.	Grayed-out	text
indicates	an	element	that	is	not	required.	Mouse	over	the	String	type
definition,	and	IntelliJ	will	provide	an	explanation	about	why	the	element	is	not
required	(Figure	2.6).
Figure	2.6		Redundant	type	information
As	you	can	see,	Kotlin	indicates	that	your	type	declaration	is	“redundant.”	What
does	this	mean?
Kotlin	includes	a	feature	called	type	inference	that	allows	you	to	omit	the	type
definition	for	variables	that	are	assigned	a	value	when	they	are	declared.
Because	you	assign	data	of	the	String	type	to	playerName	and	of	the	Int
type	to	experiencePoints	when	you	declare	them,	the	Kotlin	compiler
infers	the	appropriate	type	information	for	both	variables.
Just	as	IntelliJ	can	help	you	change	a	var	to	a	val,	it	can	also	help	you	remove
unneeded	type	specifications.	Click	on	the	String	type	definition	(:	String)
next	to	playerName	and	press	Option-Return	(Alt-Enter).	Then	click	Remove
explicit	type	specification	in	the	pop-up	(Figure	2.7).

Figure	2.7		Removing	explicit	type	specification
:	String	will	disappear.	Repeat	the	process	for	the	experiencePoints	var
to	remove	:	Int.
Whether	you	take	advantage	of	type	inference	or	specify	the	type	when	you
declare	the	variable,	the	compiler	will	keep	track	of	the	type.	In	this	book,	we
use	type	inference	where	it	is	unambiguous	to	do	so.	Type	inference	helps	keep
code	clean,	concise,	and	easier	to	modify	as	your	program	changes.
Note	that	IntelliJ	will	display	the	type	of	any	variable	on	request,	including	those
that	use	type	inference.	If	you	ever	have	a	question	about	the	type	of	a	variable,
click	on	its	name	and	press	Control-Shift-P.	IntelliJ	will	display	its	type
(Figure	2.8).
Figure	2.8		Displaying	type	info

Compile-Time	Constants
Earlier	we	told	you	that	vars	can	have	their	values	changed	and	vals	cannot.
That	...	was	a	white	lie.	In	fact,	there	are	special	cases	where	a	val	can	return
different	values,	which	we	will	discuss	in	Chapter	12.	If	you	have	a	piece	of	data
that	you	want	to	be	absolutely,	positively	immutable	–	to	never	change	–
consider	a	compile-time	constant.
A	compile-time	constant	must	be	defined	outside	of	any	function,	including
main,	because	its	value	must	be	assigned	at	compile	time	(that	is,	when	the
program	compiles)	–	hence	the	name.	main	and	your	other	functions	are	called
during	runtime	(when	the	program	is	executed),	and	the	variables	within	them
are	assigned	their	values	then.	A	compile-time	constant	exists	before	any	of
these	assignments	take	place.
Compile-time	constants	also	must	be	of	one	of	the	following	basic	types,
because	use	of	more	complex	types	for	a	constant	could	jeopardize	the	compile-
time	guarantee.	You	will	learn	more	about	how	types	are	constructed	in
Chapter	13.	Here	are	the	supported	basic	types	for	a	compile-time	constant:
## String
## Int
## Double
## Float
## Long
## Short
## Byte
## Char
## Boolean
Add	a	compile-time	constant	to	TypeIntro.kt,	above	the	declaration	of	the
main	function,	using	the	const	modifier:
Listing	2.10		Declaring	a	compile-time	constant	(TypeIntro.kt)

const	val	MAX_EXPERIENCE:	Int	=	5000
fun	main(args:	Array<String>)	{
## ...
## }
Prepending	a	val	with	the	const	modifier	tells	the	compiler	that	it	can	be	sure
that	this	val	will	never	change.	In	this	case,	MAX_EXPERIENCE	is	guaranteed
to	have	the	integer	value	5000,	no	matter	what.	This	gives	the	compiler	the
flexibility	to	perform	optimization	behind	the	scenes.
Wondering	about	the	format	of	the	const	val’s	name,	MAX_EXPERIENCE?
While	this	format	is	not	required	by	the	compiler,	our	preferred	style	is	to
distinguish	const	vals	by	fully	capitalizing	them	and	replacing	spaces	with
underscores.	As	you	may	have	noticed,	we	use	camel	casing	and	an	initial
lowercase	for	both	vars	and	vals.	Style	norms	like	these	help	keep	your	code
readable	and	clear.

## Inspecting	Kotlin	Bytecode
You	learned	in	Chapter	1	that	Kotlin	is	an	alternative	to	Java	for	writing
programs	that	run	on	the	JVM,	where	Java	bytecode	is	executed.	It	is	often
useful	to	inspect	the	Java	bytecode	that	the	Kotlin	compiler	generates	to	run	on
the	JVM.	You	will	look	at	the	bytecode	in	several	places	in	this	book	as	a	way	to
analyze	how	a	particular	language	feature	works	on	the	JVM.
Knowing	how	to	inspect	the	Java	equivalent	of	the	Kotlin	code	you	write	is	a
great	technique	for	understanding	how	Kotlin	works,	especially	if	you	have	Java
experience.	If	you	do	not	have	Java	experience	specifically,	the	Java	code	will
likely	share	familiar	traits	with	a	language	that	you	have	worked	with	–	so	think
of	it	as	a	pseudocode	to	aid	your	understanding.	And,	if	you	are	brand	new	to
programming	–	congratulations!	In	choosing	Kotlin,	you	have	chosen	a	language
that,	as	you	will	see	in	these	sections,	allows	you	to	express	the	same	logic	that
Java	does,	typically	in	much	less	code.
For	example,	you	may	have	wondered	how	using	type	inference	when	defining
variables	in	Kotlin	affects	the	resulting	bytecode	that	is	generated	to	run	on	the
JVM.	To	learn	how,	you	can	use	the	Kotlin	bytecode	tool	window.
In	TypeIntro.kt,	press	the	Shift	key	twice	to	open	the	Search	Everywhere
dialog.	Begin	entering	“show	kotlin	bytecode”	in	the	search	box,	and	select	Show
Kotlin	Bytecode	from	the	list	of	available	actions	when	it	appears	(Figure	2.9).
Figure	2.9		Showing	Kotlin	bytecode
The	Kotlin	bytecode	tool	window	will	open	(Figure	2.10).	(You	can	also	open
the	tool	window	with	Tools	→	Kotlin	→	Show	Kotlin	Bytecode.)

Figure	2.10		Kotlin	bytecode	tool	window
If	bytecode	is	not	your	native	tongue,	fear	not!	You	can	translate	the	bytecode
back	to	Java	to	see	it	represented	in	terms	you	may	be	more	familiar	with.	In	the
bytecode	tool	window,	click	the	Decompile	button	at	the	top	left.
A	new	tab	will	open	showing	TypeIntro.decompiled.java
(Figure	2.11),	a	Java	version	of	the	bytecode	the	Kotlin	compiler	generated	for
the	JVM.

Figure	2.11		Decompiled	bytecode
(The	red	underlines	in	this	case	are	due	to	a	quirk	in	the	interaction	between
Kotlin	and	Java,	rather	than	a	problem.)
Focus	on	the	variable	declarations	for	experiencePoints	and
playerName:
String	playerName	=	"Estragon";
int	experiencePoints	=	5;
Although	you	omitted	type	declarations	from	the	definitions	of	both	variables	in
the	Kotlin	source,	the	bytecode	that	was	generated	includes	explicit	type
definitions.	This	is	how	the	variables	would	be	declared	in	Java,	and	the
bytecode	gives	a	behind-the-scenes	look	at	Kotlin’s	type	inference	support.
You	will	dig	deeper	into	the	decompiled	Java	bytecode	in	later	chapters.	For
now,	close	TypeIntro.decompiled.java	(using	the	X	in	its	tab)	and	the
bytecode	tool	window	(using	the		icon	at	the	top	right).
In	this	chapter,	you	have	learned	how	to	store	basic	data	in	vars	and	vals	and
seen	when	to	use	each,	depending	on	whether	you	need	to	be	able	to	change	their
values.	You	have	seen	how	to	declare	immutable	values	using	compile-time
constants.	Last,	you	learned	how	Kotlin	leverages	the	power	of	type	inference	to

save	you	keystrokes	every	time	you	declare	a	variable.	You	will	be	using	all
these	basic	tools	over	and	over	as	you	proceed	through	this	book.
In	the	next	chapter,	you	will	learn	how	to	represent	more	complex	states	using
conditionals.

For	the	More	Curious:	Java	Primitive	Types	in
## Kotlin
In	Java,	there	are	two	kinds	of	types:	reference	types	and	primitive	types.
Reference	types	are	defined	in	source	code:	A	matching	source	code	definition
corresponds	to	the	type.	Java	also	offers	primitive	types	(often	called	just
“primitives”),	which	have	no	source	file	definition	and	are	represented	by	special
keywords	instead.
A	reference	type	in	Java	always	begins	with	a	capital	letter,	indicating	that	it	is
backed	by	a	source	definition	for	its	type.	Here	is	experiencePoints	defined
using	a	Java	reference	type:
Integer	experiencePoints	=	5;
A	Java	primitive	type	starts	with	a	lowercase	letter:
int	experiencePoints	=	5;
All	primitives	in	Java	have	a	corresponding	reference	type.	(But	not	all	reference
types	have	a	corresponding	primitive	type.)	Why	use	one	versus	the	other?
One	reason	for	choosing	a	reference	type	is	that	there	are	certain	features	of	the
Java	language	that	are	only	available	when	using	reference	types.	Generics,	for
example,	which	you	will	learn	about	in	Chapter	17,	do	not	work	with	primitives.
Reference	types	can	also	work	with	the	object-oriented	features	of	Java	more
readily	than	Java	primitives.	(You	will	learn	about	object-oriented	programming
and	the	object-oriented	features	of	Kotlin	in	Chapter	12.)
On	the	other	hand,	primitives	offer	better	performance	and	some	other	perks.
Unlike	Java,	Kotlin	provides	only	one	kind	of	type:	reference	types.
var	experiencePoints:	Int	=	5
Kotlin	made	this	design	decision	for	several	reasons.	First,	if	there	is	no	choice
between	kinds	of	types,	you	cannot	code	yourself	into	a	corner	as	easily	as	you
can	with	multiple	kinds	to	choose	from.	For	example,	what	if	you	define	an
instance	of	a	primitive	type,	then	realize	later	that	you	need	to	use	the	generic
feature,	which	requires	a	reference	type?	Having	only	reference	types	in	Kotlin
means	that	you	will	never	encounter	this	problem.
If	you	are	familiar	with	Java,	you	may	be	thinking,	“But	primitives	offer	better
performance	than	reference	types!”	This	is	true.	But	take	another	look	at	the
experiencePoints	variable	in	the	decompiled	bytecode	you	saw	earlier:

int	experiencePoints	=	5;
As	you	can	see,	a	primitive	type	was	used	in	place	of	the	reference	type.	Why	is
that,	if	Kotlin	only	has	reference	types?	The	Kotlin	compiler	will,	where
possible,	use	primitives	in	the	Java	bytecode,	because	they	do	indeed	offer	better
performance.
Kotlin	gives	you	the	ease	of	reference	types	with	the	performance	of	primitives
under	the	hood.	In	Kotlin	you	will	find	a	corresponding	reference	type	for	the
eight	primitive	types	you	may	be	familiar	with	in	Java.

Challenge:	hasSteed
Here	is	your	first	challenge:	In	the	text	adventure	game,	players	may	acquire	a
dragon	or	minotaur	they	can	ride.	Define	a	variable	called	hasSteed	to	track
whether	the	player	has	acquired	one.	Give	the	variable	an	initial	state	indicating
that	the	player	does	not	have	one	yet.

## Challenge:	The	Unicorn’s	Horn
Imagine	this	scene	from	the	adventure	game:
The	hero	Estragon	arrives	at	a	pub	known	as	the	Unicorn’s	Horn.	The	publican
asks,	“Do	you	need	to	stable	a	steed?”
“No,”	Estragon	replies,	“I	have	no	steed.	But	I	do	have	50	gold	pieces,	and	I
would	like	a	drink.”
“Excellent!”	says	the	publican.	“I	have	mead,	wine,	and	LaCroix.	What	will	you
have?”
For	this	challenge,	add	below	your	hasSteed	variable	the	variables	required
for	the	scene	at	the	Unicorn’s	Horn,	using	type	inference	and	assigned	values
where	possible.	Add	variables	to	hold	values	for	the	name	of	the	pub,	the	name
of	the	current	publican	on	duty,	and	how	much	gold	the	player	has	acquired	so
far.
Notice	that	the	Unicorn’s	Horn	has	a	menu	of	drinks	the	hero	can	select	from.
What	type	might	you	use	to	represent	the	menu?	If	you	need	to,	consult
## Table	2.1.

## Challenge:	Magic	Mirror
Refreshed,	Estragon	is	ready	for	a	challenging	quest.	Are	you?
The	hero	discovers	a	magic	mirror	that	shows	a	player	the	reflection	of	their
playerName.	Using	the	String	type’s	magic,	transform	the	playerName
string	"Estragon"	into	"nogartsE",	a	reflection	of	its	value.
To	solve	this	challenge,	consult	the	documentation	for	String	at
kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/
index.html.	You	will	find	that,	fortunately,	the	actions	that	a	particular	type
can	perform	are	usually	very	intuitively	named	(hint).