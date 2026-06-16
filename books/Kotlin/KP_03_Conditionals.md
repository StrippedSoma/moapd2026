

## 3
## Conditionals
In	this	chapter,	you	will	learn	how	to	define	rules	for	when	code	should	be
executed.	This	language	feature	is	called	control	flow,	and	it	allows	you	to
describe	the	conditions	for	when	specific	portions	of	your	program	should	run.
You	will	see	the	if/else	statement	and	expression	and	the	when	expression,	and
you	will	learn	how	to	write	true/false	tests	using	the	comparison	and	logical
operators.	Along	the	way,	you	will	also	take	a	look	at	Kotlin’s	string	templating
feature.
To	see	these	concepts	in	action,	you	will	begin	building	a	project	called
NyetHack,	which	you	will	work	on	through	most	of	this	book.
Why	“NyetHack”?	We	are	glad	you	asked.	Perhaps	you	remember	NetHack,	a
game	released	in	1987	by	The	NetHack	DevTeam.	NetHack	was	a	single-player
text-based	fantasy	game	with	ASCII	graphics;	check	it	out	at	nethack.org.
You	will	be	building	elements	of	a	similar	text-based	game	(no	awesome	ASCII
graphics,	though	–	sorry).	JetBrains,	the	creator	of	the	Kotlin	language,	has
offices	in	Russia;	when	you	put	together	a	text-based	game	like	NetHack	and
Kotlin’s	Russian	origins,	you	get	NyetHack.

if/else	Statements
Let’s	get	started.	Open	IntelliJ	and	create	a	new	project.	(If	you	already	have
IntelliJ	open,	you	can	select	File	→	New	→	Project...)	Select	the	Kotlin/JVM	target
and	name	your	project	NyetHack.
Click	on	the	NyetHack	disclosure	arrow	in	the	project	tool	window	and	right-click
the	src	directory	to	create	a	new	Kotlin	File/Class.	Name	your	file	Game.	Add	a
main	entry	point	function	to	Game.kt	by	typing	“main”	and	pressing	the	Tab
key.	Your	function	should	look	like	this:
fun	main(args:	Array<String>)	{
## }
In	NyetHack,	a	player’s	condition	is	based	on	remaining	health	points,	ranging
from	0	to	100.	On	their	quest,	they	may	sustain	injuries	during	combat.	On	the
other	hand,	they	may	be	in	excellent	condition.	You	want	to	define	rules	for	how
to	describe	the	player’s	visible	health	condition:	If	the	player’s	health	is	100,
you	want	to	show	that	they	are	in	excellent	health,	else	you	will	let	them	know
how	hurt	they	are.	One	tool	you	can	use	to	define	rules	like	that	is	the	if/else
statement.
Within	the	main	function,	write	your	first	if/else	statement,	as	shown	below.
There	is	a	lot	going	on	in	this	code;	we	will	break	it	down	after	you	enter	it.
Listing	3.1		Printing	the	player’s	health	condition	(Game.kt)
fun	main(args:	Array<String>)	{
val	name	=	"Madrigal"
var	healthPoints	=	100
if	(healthPoints	==	100)	{
println(name	+	"	is	in	excellent	condition!")
}	else	{
println(name	+	"	is	in	awful	condition!")
## }
## }
Let’s	go	through	this	new	code	line	by	line.	First,	you	define	a	val	called	name
and	assign	it	a	string	value	representing	your	intrepid	player’s	name.	Next,	you
define	a	var	called	healthPoints	and	assign	it	an	initial	value	of	100,	a
perfect	score.	Then,	you	add	an	if/else	statement.
In	your	if/else	statement,	you	begin	by	posing	the	following	true/false
question:	“Does	the	player	have	a	healthPoints	score	of	100?”	You	express
this	with	the	==	structural	equality	operator.	It	can	be	read	as	“is	equal	to,”	so
this	statement	reads	“if	healthPoints	is	equal	to	100.”

Your	if	statement	is	followed	by	a	statement	in	curly	braces	({}).	The	code
within	the	curly	braces	is	what	you	want	the	program	to	do	if	the	if	condition
evaluates	as	the	Boolean	value	true	–	in	this	case,	if	healthPoints	has	a
value	of	exactly	100.
if	(healthPoints	==	100)	{
println(name	+	"	is	in	excellent	condition!")
## }
Included	in	this	statement	is	the	familiar	println	function	used	to	print
something	to	the	console.	What	to	print,	in	the	parentheses,	consists	of	the	value
of	name	and	the	string	"	is	in	excellent	condition!"	(Note	the	leading
space,	so	you	do	not	get	a	result	of	Madrigalis	in	excellent	condition!)	In
short,	your	if/else	statement	so	far	says	that	if	Madrigal	has	100	health	points,
the	program	should	print	that	the	hero	is	in	excellent	condition.
(While	your	if	statement’s	curly	braces	enclose	only	one	statement,	more	than
one	can	be	included	if	you	want	multiple	actions	to	be	taken	when	the	if
evaluates	as	true.)
Using	the	addition	operator	(+)	to	append	a	value	to	a	string	is	called	string
concatenation.	It	is	an	easy	way	to	customize	what	is	printed	to	the	console
based	on	the	value	of	a	variable.	Later	in	this	chapter,	you	will	see	another,
preferred	way	to	inject	values	into	your	strings.
What	if	healthPoints	has	a	value	other	than	100?	In	that	case,	the	if
evaluates	as	false,	and	the	compiler	will	skip	the	expression	in	curly	braces	that
follows	if	and	move	on	to	the	else.	Think	of	else	as	meaning	“otherwise”:	If
some	condition	is	true,	do	this;	otherwise	do	that.	Like	if,	else	is	followed	by
one	or	more	expressions	in	curly	braces	that	tell	the	compiler	what	to	do.	But
unlike	if,	else	does	not	need	to	define	a	condition.	It	applies	whenever	the	if
does	not,	so	the	curly	braces	immediately	follow	the	keyword.
else	{
println(name	+	"	is	in	awful	condition!")
## }
The	only	difference	in	this	call	to	the	println	function	is	in	the	string	that
follows	the	hero’s	name.	Instead	of	reporting	that	the	hero	“is	in	excellent
condition!”,	this	one	reports	that	the	injured	hero	“is	in	awful	condition!”	(Thus
far,	most	of	the	function	calls	that	you	have	seen	serve	only	to	print	strings	out	to
the	console.	You	will	learn	more	about	functions,	including	how	to	define	your
own,	in	Chapter	4.)
Putting	this	all	together	in	plain	English,	your	code	says	to	the	compiler,	“If	the
hero	has	exactly	100	health	points,	print	Madrigal	is	in	excellent
condition!	to	the	console.	If	Madrigal	does	not	have	100	health	points,	print

Madrigal	is	in	awful	condition!	to	the	console.”
The	structural	equality	operator,	==,	is	one	of	Kotlin’s	comparison	operators.
Table	3.1	lists	Kotlin’s	comparison	operators.	You	do	not	need	to	know	all	of	the
operators	listed	now,	as	you	will	learn	more	about	them	later.	Return	to	this	table
when	you	are	considering	possible	operators	to	express	a	condition.
Table	3.1		Comparison	operators
OperatorDescription
## <
Evaluates	whether	the	value	on	the	left	is	less	than	the	value	on	the
right.
## <=
Evaluates	whether	the	value	on	the	left	is	less	than	or	equal	to	the
value	on	the	right.
## >
Evaluates	whether	the	value	on	the	left	is	greater	than	the	value	on
the	right.
## >=
Evaluates	whether	the	value	on	the	left	is	greater	than	or	equal	to
the	value	on	the	right.
## ==
Evaluates	whether	the	value	on	the	left	is	equal	to	the	value	on	the
right.
## !=
Evaluates	whether	the	value	on	the	left	is	not	equal	to	the	value	on
the	right.
## ===
Evaluates	whether	the	two	instances	point	to	the	same	reference.
## !==
Evaluates	whether	the	two	instances	do	not	point	to	the	same
reference.
Back	to	business.	Run	Game.kt	by	clicking	the	run	button	to	the	left	of	the
main	function.	You	should	see	the	following	output:
Madrigal	is	in	excellent	condition!
Since	the	condition	you	defined,	healthPoints	==	100,	is	true,	the	if	branch	in
the	if/else	statement	was	triggered.	(We	use	the	word	branch	because	the	flow
of	your	code	execution	will	branch	depending	on	whether	your	specified
condition	is	met.)	Now,	try	changing	the	healthPoints	value	to	89:
Listing	3.2		Modifying	healthPoints	(Game.kt)
fun	main(args:	Array<String>)	{
val	name	=	"Madrigal"
var	healthPoints	=	100
var	healthPoints	=	89
if	(healthPoints	==	100)	{
println(name	+	"	is	in	excellent	condition!")

}	else	{
println(name	+	"	is	in	awful	condition!")
## }
## }
Run	the	program	again,	and	you	will	see:
Madrigal	is	in	awful	condition!
Now,	the	condition	you	defined	is	false	(89	is	not	equal	to	100),	so	the	else
branch	is	triggered.
Adding	more	conditions
The	health	status	code	gives	a	crude	idea	of	the	player’s	condition,	but	it	is	...
well,	crude.	If	the	player’s	healthPoints	is	89,	you	report	that	they	are	in
“awful	condition,”	which	hardly	makes	sense.	It	might	be	just	a	flesh	wound,
after	all.
To	make	your	if/else	statement	more	nuanced,	you	can	add	more	conditions	to
check	for	and	more	branches	to	include	as	possible	results.	You	do	this	with	else
if	branches,	whose	syntax	is	just	like	an	if’s,	between	the	if	and	the	else.
Update	your	if/else	statement	to	include	three	else	if	branches	checking	for
intermediate	values	of	healthPoints:
Listing	3.3		Checking	for	more	player	conditions	(Game.kt)
fun	main(args:	Array<String>)	{
val	name	=	"Madrigal"
var	healthPoints	=	89
if	(healthPoints	==	100)	{
println(name	+	"	is	in	excellent	condition!")
}	else	if	(healthPoints	>=	90)	{
println(name	+	"	has	a	few	scratches.")
}	else	if	(healthPoints	>=	75)	{
println(name	+	"	has	some	minor	wounds.")
}	else	if	(healthPoints	>=	15)	{
println(name	+	"	looks	pretty	hurt.")
}	else	{
println(name	+	"	is	in	awful	condition!")
## }
## }
Your	new	logic	reads	like	this:
If	Madrigal	has	this	many	health	points...	print	this	message
100Madrigal	is	in	excellent	condition!
90-99Madrigal	has	a	few	scratches.
75-89Madrigal	has	some	minor	wounds.
15-74Madrigal	looks	pretty	hurt.
0-14Madrigal	is	in	awful	condition!

Run	the	program	again.	Because	the	value	of	Madrigal’s	healthPoints	is	89,
neither	the	if	nor	the	first	else	if	will	evaluate	as	true.	But	else	if
(healthPoints	>=	75)	is	true,	so	you	will	see	Madrigal	has	some	minor
wounds.	in	the	console.
Note	that	the	compiler	evaluates	the	conditions	of	an	if/else	from	top	to
bottom	and	stops	checking	conditions	as	soon	as	one	evaluates	as	true.	If	none	of
the	conditions	you	provide	are	true,	the	else	branch	will	be	executed.
This	means	that	the	order	of	the	conditions	matters:	If	you	had	arranged	the	if
and	else	ifs	from	the	lowest	checked	value	to	the	highest,	none	of	the	else
ifs	would	ever	be	executed.	Any	healthPoints	value	of	15	or	higher	would
trigger	the	first	condition,	and	any	value	lower	than	15	would	make	the	else	ifs
evaluate	as	false	–	so	the	else	would	apply.	(Do	not	make	this	change	to	your
code.	It	is	only	for	illustration.)
fun	main(args:	Array<String>)	{
val	name	=	"Madrigal"
var	healthPoints	=	89
if	(healthPoints	>=	15)	{		//	Triggered	for	any	value	of	15	or	higher
println(name	+	"	looks	pretty	hurt.")
}	else	if	(healthPoints	>=	75)	{
println(name	+	"	has	some	minor	wounds.")
}	else	if	(healthPoints	>=	90)	{
println(name	+	"	has	a	few	scratches.")
}	else	if	(healthPoints	==	100)	{
println(name	+	"	is	in	excellent	condition!")
}	else	{																						//	Triggered	for	values	0-14
println(name	+	"	is	in	awful	condition!")
## }
## }
You	have	added	more	subtlety	in	how	the	player’s	health	is	reported	by
including	else	if	statements	with	more	conditions	to	check	when	the	initial	if
condition	evaluates	as	false.	Try	varying	healthPoints’s	value	to	trigger	the
result	in	each	branch	you	defined.	When	you	are	done,	return	healthPoints
to	a	value	of	89.
Nested	if/else	statements
In	NyetHack,	a	player	can	be	“blessed,”	which	means	that	if	they	are	in	good
health	they	will	heal	from	minor	injuries	quickly.	Your	next	step	is	to	add	a
variable	to	track	whether	a	player	is	blessed	(what	type	do	you	think	it	will	be?)
and,	if	so,	to	change	the	health	status	message	to	reflect	that.
Do	this	by	nesting	an	if/else	statement	within	one	of	your	existing	branches	so
that	when	the	player’s	health	is	greater	than	or	equal	to	75	you	use	an	additional
if/else	to	check	whether	the	player	is	blessed.	(As	you	enter	the	changes

below,	do	not	miss	the	added	}	before	the	last	else	if.)
Listing	3.4		Checking	for	blessedness	(Game.kt)
fun	main(args:	Array<String>)	{
val	name	=	"Madrigal"
var	healthPoints	=	89
val	isBlessed	=	true
if	(healthPoints	==	100)	{
println(name	+	"is	in	excellent	condition!")
}	else	if	(healthPoints	>=	90)	{
println(name	+	"	has	a	few	scratches.")
}	else	if	(healthPoints	>=	75)	{
if	(isBlessed)	{
println(name	+	"	has	some	minor	wounds	but	is	healing	quite	quickly!")
}	else	{
println(name	+	"	has	some	minor	wounds.")
## }
}	else	if	(healthPoints	>=	15)	{
println(name	+	"	looks	pretty	hurt.")
}	else	{
println(name	+	"	is	in	awful	condition!")
## }
## }
You	added	a	Boolean	val	representing	whether	the	player	is	blessed	and	inserted
an	if/else	statement	to	create	a	new	output	when	a	player	is	blessed	and	has
between	75	and	89	health	points.	Recall	that	healthPoints	has	a	value	of	89,
so	you	should	expect	to	see	the	new	message	when	you	run	the	program.	Run	it
and	see.	Your	output	should	be:
Madrigal	has	some	minor	wounds	but	is	healing	quite	quickly!
If	you	see	any	other	output,	check	that	your	code	matches	Listing	3.4	exactly	–
in	particular	that	healthPoints	is	assigned	a	value	of	89.
Nesting	conditionals	allows	you	to	create	logical	branches	within	branches	so
that	the	conditions	that	you	check	for	can	be	precise	and	complex.
More	elegant	conditionals
If	you	do	not	keep	a	sharp	eye	on	them,	conditionals	will	explode	all	over	the
place	like	tribbles.	Thankfully,	Kotlin	allows	you	to	take	advantage	of
conditionals’	usefulness	while	keeping	them	concise	and	readable.	Let’s	look	at
some	examples.
Logical	operators
In	NyetHack,	more	complex	conditions	can	arise	that	you	need	to	check	for.	For
example,	if	a	player	is	blessed	and	their	health	is	above	50,	or	if	they	are
immortal,	they	have	an	aura	that	is	visible.	Otherwise,	the	player’s	aura	cannot

be	seen	by	the	naked	eye.
You	could	use	a	series	of	if/else	statements	to	determine	whether	a	player	has
a	visible	aura,	but	you	would	end	up	with	a	lot	of	duplicate	code	and	the	logic	of
the	conditions	would	be	masked.	There	is	a	more	elegant	and	reader-friendly
way:	using	logical	operators	in	a	conditional.
Add	a	new	variable	and	an	if/else	statement	to	print	aura	information	to	the
console:
Listing	3.5		Using	logical	operators	in	a	conditional	(Game.kt)
fun	main(args:	Array<String>)	{
val	name	=	"Madrigal"
var	healthPoints	=	89
val	isBlessed	=	true
val	isImmortal	=	false
## //	Aura
if	(isBlessed	&&	healthPoints	>	50	||	isImmortal)	{
println("GREEN")
}	else	{
println("NONE")
## }
if	(healthPoints	==	100)	{
## ...
## }
## }
You	added	a	val	called	isImmortal	to	track	the	player’s	immortality	(read-
only	because	a	player’s	immortality	does	not	change).	That	part	is	familiar,	but
there	are	a	couple	of	new	things	going	on,	too.	First,	you	included	a	code
comment,	indicated	by	//.
Anything	to	the	right	of	//	is	included	in	the	comment	and	is	ignored	by	the
compiler,	so	you	can	use	any	syntax	you	want	there.	Comments	are	useful	for
organizing	and	adding	information	about	your	code,	making	it	more	readable	for
others	(or	for	your	future	self,	who	may	not	remember	all	the	details).
Next,	you	used	two	logical	operators	in	your	if.	Logical	operators	allow	you	to
combine	comparison	operators	into	a	larger	statement.
&&	is	the	logical	‘and’	operator,	and	it	requires	that	both	the	condition	on	its	left
and	the	condition	on	its	right	be	true	for	the	expression	as	a	whole	to	be	true.	||
is	the	logical	‘or’	operator,	and	it	allows	the	expression	as	a	whole	to	be	true	if
either	the	condition	on	its	left	or	the	condition	on	its	right	(or	both)	is	true.
Table	3.2	shows	Kotlin’s	logical	operators.
Table	3.2		Logical	operators
OperatorDescription
## &&

Logical	‘and’:	true	if	and	only	if	both	are	true	(false	otherwise).
## ||
Logical	‘or’:	true	if	either	is	true	(false	only	if	both	are	false).
## !
Logical	‘not’:	true	becomes	false,	false	becomes	true.
One	note:	When	operators	are	combined,	there	is	an	order	of	precedence	that
determines	what	order	they	are	evaluated	in.	Operators	with	the	same	precedence
are	applied	from	left	to	right.	You	can	also	group	operations	by	surrounding	the
operators	that	should	be	evaluated	as	a	group	in	parentheses.	Here	is	the	order	of
operator	precedence,	from	highest	to	lowest:
## !	(logical	‘not’)
<	(less	than),	<=	(less	than	or	equal	to),	>	(greater	than),	>=	(greater	than	or
equal	to)
==	(structural	equality),	!=	(non-equality)
## &&	(logical	‘and’)
## ||	(logical	‘or’)
Getting	back	to	NyetHack,	let’s	take	a	look	at	your	new	condition:
if	(isBlessed	&&	healthPoints	>	50	||	isImmortal)	{
println("GREEN")
## }
Put	another	way,	if	the	player	is	blessed	and	has	more	than	50	health	points,	or	if
the	player	is	immortal,	a	green	aura	is	visible.	Madrigal	is	not	immortal,	but	is
blessed	and	has	89	health	points.	Thus,	the	first	option	is	met,	and	Madrigal’s
aura	should	be	visible.	Run	your	program	to	see	whether	this	is	so.	You	should
see:
## GREEN
Madrigal	has	some	minor	wounds	but	is	healing	quite	quickly!
Think	about	the	nested	conditional	statements	that	would	be	required	to	express
this	logic	without	logical	operators.	These	operators	give	you	the	tools	to	express
complex	logic	clearly.
Your	aura	code	is	more	clear	than	a	set	of	if/else	statements,	but	it	could	be
even	more	readable.	Logical	operators	are	not	only	for	conditionals.	They	can	be
used	in	many	expressions,	including	in	the	declaration	of	a	variable.	Add	a	new
Boolean	variable	that	encapsulates	the	conditions	for	a	visible	aura	and	refactor
(that	is,	rewrite)	your	conditional	to	use	the	new	variable.
Listing	3.6		Using	logical	operators	in	the	declaration	of	a	variable
(Game.kt)
fun	main(args:	Array<String>)	{
## ...

## //	Aura
if	(isBlessed	&&	healthPoints	>	50	||	isImmortal)	{
val	auraVisible	=	isBlessed	&&	healthPoints	>	50	||	isImmortal
if	(auraVisible)	{
println("GREEN")
}	else	{
println("NONE")
## }
## ...
## }
You	have	moved	the	condition	check	to	a	new	val	called	auraVisible	and
changed	your	if/else	statement	to	check	its	value.	This	is	functionally
equivalent	to	what	you	had	written	before,	but	now	you	express	the	rules	as	a
value	assignment	instead.	The	name	of	the	value	clearly	signifies	what	the	rule
you	defined	expresses	in	“human-readable”	terms:	aura	visibility.	This	is	an
especially	useful	technique	for	when	your	program’s	rules	become	complex,	and
it	helps	to	communicate	what	your	rules	mean	for	future	readers	of	your	code.
Run	your	program	again	to	make	sure	it	functions	as	before.	The	output	should
be	the	same.
Conditional	expressions
Now	the	if/else	statement	displays	the	player’s	health	status	correctly	–	and
with	some	subtlety.
On	the	other	hand,	it	would	be	somewhat	unwieldy	to	make	changes	to	it,
because	each	branch	repeats	a	similar	println	statement.	What	if	you	wanted
to	make	a	change	to	the	overall	formatting	of	the	player	status	display?	The
program	in	its	current	state	would	require	you	to	hunt	through	each	branch	in	the
if/else	statement	and	change	each	println	function	to	the	new	format.
You	can	solve	this	by	changing	the	if/else	statement	you	wrote	to	a	conditional
expression	instead.	A	conditional	expression	is	like	a	conditional	statement,
except	that	you	assign	the	if/else	to	a	value	that	you	can	use	later.	Update	the
health	status	display	code	to	see	what	this	looks	like.
Listing	3.7		Using	a	conditional	expression	(Game.kt)
fun	main(args:	Array<String>)	{
## ...
if	(healthPoints	==	100)	{
val	healthStatus	=	if	(healthPoints	==	100)	{
println(name	+	"is	in	excellent	condition!")
"is	in	excellent	condition!"
}	else	if	(healthPoints	>=	90)	{
println(name	+	"	has	a	few	scratches.")
"has	a	few	scratches."
}	else	if	(healthPoints	>=	75)	{
if	(isBlessed)	{
println(name	+	"	has	some	minor	wounds	but	is	healing	quite	quickly!")
"has	some	minor	wounds	but	is	healing	quite	quickly!"
}	else	{

println(name	+	"	has	some	minor	wounds.")
"has	some	minor	wounds."
## }
}	else	if	(healthPoints	>=	15)	{
println(name	+	"	looks	pretty	hurt.")
"looks	pretty	hurt."
}	else	{
println(name	+	"	is	in	awful	condition!")
"is	in	awful	condition!"
## }
//	Player	status
println(name	+	"	"	+	healthStatus)
## }
(Incidentally,	if	you	are	tired	of	keeping	your	code	nicely	indented	as	you	make
changes,	IntelliJ	is	here	to	help.	Select	Code	→	Auto-Indent	Lines	and	enjoy	the
simple	pleasure	of	clean	indents.)
Through	the	if/else	expression,	the	new	variable	healthStatus	is	assigned
a	string	value	of	"is	in	excellent	condition!",	etc.,	depending	on	the	value
of	healthPoints.	That	is	the	beauty	of	a	conditional	expression.	Because
you	can	now	print	the	player’s	status	using	the	new	healthStatus	variable,
you	are	able	to	remove	six	virtually	identical	print	statements.
When	you	need	to	assign	a	variable	based	on	a	condition,	you	can	likely	use	a
conditional	expression.	Keep	in	mind,	however,	that	conditional	expressions	are
often	most	intuitive	when	the	value	being	assigned	from	each	branch	is	of	the
same	type	(like	the	healthStatus	strings).
Your	aura	code	can	also	be	streamlined	using	a	conditional	expression.	Do	so
now.
Listing	3.8		Improving	aura	code	with	a	conditional	expression
(Game.kt)
## ...
## //	Aura
val	auraVisible	=	isBlessed	&&	healthPoints	>	50	||	isImmortal
if	(auraVisible)	{
println("GREEN")
}	else	{
println("NONE")
## }
val	auraColor	=	if	(auraVisible)	"GREEN"	else	"NONE"
println(auraColor)
## ...
Run	your	code	one	more	time	to	make	sure	everything	works	as	expected.	You
should	see	the	same	output,	but	your	code	is	now	more	elegant	and	reader-
friendly.
You	may	have	noticed	that	you	dropped	the	curly	braces	in	the	aura	color
conditional	expression.	Let’s	discuss	why.
Removing	braces	from	if/else	expressions

In	cases	where	you	have	a	single	response	for	the	matching	condition,	it	is	valid
(at	least,	syntactically	–	more	on	that	shortly)	to	omit	the	curly	braces	wrapping
the	expression.	You	can	only	omit	the	{}s	when	a	branch	contains	only	one
expression	–	omitting	them	from	a	branch	with	more	than	one	expression	will
affect	how	the	code	is	evaluated.
Take	a	look	at	a	version	of	healthStatus	without	braces:
val	healthStatus	=	if	(healthPoints	==	100)	"is	in	excellent	condition!"
else	if	(healthPoints	>=	90)	"has	a	few	scratches."
else	if	(healthPoints	>=	75)
if	(isBlessed)	"has	some	minor	wounds	but	is	healing	quite	quickly!"
else	"has	some	minor	wounds."
else	if	(healthPoints	>=	15)	"looks	pretty	hurt."
else	"is	in	awful	condition!"
This	version	of	the	healthStatus	conditional	expression	does	the	same	thing
as	the	version	you	have	in	your	code.	It	even	expresses	the	same	logic	in	less
code.	But	which	version	do	you	find	easier	to	read	and	understand	at	a	glance?	If
you	chose	the	version	with	the	braces	–	the	version	in	your	code	–	you	have
chosen	the	style	that	the	Kotlin	community	prefers.
We	recommend	that	you	do	not	omit	braces	for	conditional	statements	or
expressions	that	span	more	than	one	line.	For	one	thing,	without	braces	it
becomes	increasingly	difficult	to	understand	where	a	branch	starts	and	ends	with
every	condition	that	is	added.	For	another,	omitting	the	braces	for	the	conditional
increases	the	risk	of	a	new	contributor	mistakenly	updating	the	wrong	branch	or
misunderstanding	what	the	implementation	does.	It	is	just	not	worth	it	to	save	a
few	keystrokes.
Also,	while	the	code	above	expresses	the	same	thing	with	or	without	braces,	this
is	not	the	case	for	every	example.	If	you	have	multiple	expressions	on	a	branch
and	you	drop	the	braces	around	the	conditional,	only	the	first	expression	is
executed	in	that	branch.	Take	this	example:
var	arrowsInQuiver	=	2
if	(arrowsInQuiver	>=	5)	{
println("Plenty	of	arrows")
println("Cannot	hold	any	more	arrows")
## }
If	the	hero	has	five	or	more	arrows,	they	have	plenty	and	cannot	hold	any	more.
The	hero	has	only	two	arrows,	so	nothing	prints	to	the	console.	However,
without	the	braces	the	logic	changes:
var	arrowsInQuiver	=	2
if	(arrowsInQuiver	>=	5)
println("Plenty	of	arrows")
println("Cannot	hold	any	more	arrows")
Without	the	braces,	the	second	println	statement	is	no	longer	part	of	the	if
branch.	While	"Plenty	of	arrows"	only	prints	when	arrowsInQuiver	is	at

least	5,	"Cannot	hold	any	more	arrows"	always	prints	–	no	matter	how	many
arrows	the	hero	is	carrying.
For	a	one-line	expression,	this	overall	principle	should	inform	your	choice:
“Which	way	of	writing	the	expression	is	most	clear	for	new	readers	to
understand?”	Often,	for	one-line	expressions,	removing	the	curly	braces	is	more
readable.	For	example,	removing	the	curly	braces	helps	to	clarify	a	simple	one-
line	conditional	expression	like	your	aura	code,	or	this	example:
val	healthSummary	=	if	(healthPoints	!=	100)	"Need	healing!"	else	"Looking	good."
By	the	way,	if	you	are	thinking,	“OK,	but	I	still	don’t	love	the	if/else	syntax,
even	with	the	curly	braces.	It	is	just	ugly!”	...	fear	not.	You	are	going	to	rewrite
the	health	status	expression	one	last	time	in	a	less	verbose	–	and	more	legible	–
syntax	soon.

## Ranges
All	the	conditions	that	you	wrote	in	the	if/else	expression	for	healthStatus
branch	off	the	value	of	an	integer,	healthPoints.	Some	branches	use	the
structural	equality	operator	to	check	whether	healthPoints	is	equal	to	a
value,	and	others	use	multiple	comparison	operators	to	check	whether
healthPoints	is	within	a	range	of	two	numbers.	There	is	a	better	alternative
for	the	second	group:	Kotlin	provides	ranges	to	represent	a	linear	series	of
values.
The	..	operator,	as	in	in	1..5,	signals	a	range.	A	range	includes	all	values	from
the	value	on	the	left	of	the	..	operator	to	the	value	on	the	right,	so	1..5	includes
1,	2,	3,	4,	and	5.	Ranges	can	also	be	a	sequence	of	characters.
You	use	the	in	keyword	to	check	whether	a	value	is	within	a	range.	Refactor
your	healthStatus	conditional	expression	to	use	ranges	rather	than
comparison	operators.
Listing	3.9		Refactoring	healthStatus	with	ranges	(Game.kt)
fun	main(args:	Array<String>)	{
## ...
val	healthStatus	=	if	(healthPoints	==	100)	{
"is	in	excellent	condition!"
}	else	if	(healthPoints	>=	90)	{
}	else	if	(healthPoints	in	90..99)	{
"has	a	few	scratches."
}	else	if	(healthPoints	>=	75)	{
}	else	if	(healthPoints	in	75..89)	{
if	(isBlessed)	{
"has	some	minor	wounds	but	is	healing	quite	quickly!"
}	else	{
"has	some	minor	wounds."
## }
}	else	if	(healthPoints	>=	15)	{
}	else	if	(healthPoints	in	15..74)	{
"looks	pretty	hurt."
}	else	{
"is	in	awful	condition!"
## }
## }
Bonus:	Using	ranges	in	a	conditional	like	this	solves	the	else	if	ordering	issue
you	saw	earlier	in	this	chapter.	With	ranges,	your	branches	can	be	in	any	order
and	the	code	will	evaluate	the	same.
In	addition	to	the	..	operator,	several	functions	exist	for	creating	ranges.	The
downTo	function	creates	a	range	that	descends	rather	than	ascends,	for	example.
And	the	until	function	creates	a	range	that	excludes	the	upper	bound	of	the
range	specified.	You	will	see	some	of	these	functions	in	a	challenge	near	the	end
of	this	chapter,	and	you	will	learn	more	about	ranges	in	Chapter	10.

when	Expressions
The	when	expression	is	another	control	flow	mechanism	available	in	Kotlin.	Like
if/else,	the	when	expression	allows	you	to	write	conditions	to	check	for	and
will	execute	corresponding	code	when	the	condition	evaluates	as	true.	when
provides	a	more	concise	syntax	and	is	an	especially	good	fit	for	conditionals
with	three	or	more	branches.
Suppose	that	in	NyetHack,	players	can	be	members	of	several	different	fantasy
races,	like	orc	or	gnome,	and	those	fantasy	races	ally	with	each	other	in	factions.
This	when	expression	takes	in	a	fantasy	race	and	returns	the	name	of	the	faction
to	which	it	belongs:
val	race	=	"gnome"
val	faction	=	when	(race)	{
"dwarf"	->	"Keepers	of	the	Mines"
"gnome"	->	"Keepers	of	the	Mines"
"orc"	->	"Free	People	of	the	Rolling	Hills"
"human"	->	"Free	People	of	the	Rolling	Hills"
## }
First,	a	val	is	declared,	race.	Next,	a	second	val	is	declared:	faction,	whose
value	is	determined	with	a	when	expression.	The	expression	checks	the	value	of
race	against	each	of	the	values	on	the	lefthand	side	of	the	->	operator	(called
the	arrow),	and	when	it	finds	a	match	it	assigns	faction	the	value	on	the
righthand	side.	(->	is	used	differently	in	other	languages	–	and,	in	fact,	it	has
other	uses	in	Kotlin,	as	you	will	see	later	in	this	book.)
By	default,	a	when	expression	behaves	as	though	there	were	a	==	equality
operator	between	the	argument	you	provide	in	parentheses	and	the	conditions
you	specify	in	the	curly	braces.	(An	argument	is	data	that	is	given	to	a	piece	of
code	as	input.	You	will	learn	more	about	them	in	Chapter	4.)
In	the	example	when	expression,	race	is	provided	as	the	argument,	so	the
compiler	compares	the	value	of	race,	which	is	"gnome",	against	the	first
condition	to	check	whether	they	are	equal.	They	are	not,	so	the	result	of	the
comparison	is	false,	and	the	compiler	moves	along	to	the	next	condition.	The
next	comparison	is	true,	so	the	value	in	the	corresponding	branch,	"Keepers	of
the	Mines",	is	assigned	to	faction.
Now	that	you	have	seen	how	to	leverage	when	expressions,	you	can	refine	how
the	healthStatus	logic	is	implemented.	You	previously	used	an	if/else
expression,	but,	in	this	case,	a	when	expression	will	make	your	code	more
readable	and	concise.	A	practical	rule	of	thumb	is	that	a	when	expression	should

replace	an	if/else	expression	if	your	code	includes	an	else	if	branch.
Update	the	healthStatus	logic	to	use	when:
Listing	3.10		Refactoring	healthStatus	with	when	(Game.kt)
fun	main(args:	Array<String>)	{
## ...
val	healthStatus	=	if	(healthPoints	==	100)	{
"is	in	excellent	condition!"
}	else	if	(healthPoints	in	90..99)	{
"has	a	few	scratches."
}	else	if	(healthPoints	in	75..89)	{
if	(isBlessed)	{
"has	some	minor	wounds	but	is	healing	quite	quickly!"
}	else	{
"has	some	minor	wounds."
## }
}	else	if	(healthPoints	in	15..74)	{
"looks	pretty	hurt."
}	else	{
"is	in	awful	condition!"
## }
val	healthStatus	=	when	(healthPoints)	{
100	->	"is	in	excellent	condition!"
in	90..99	->	"has	a	few	scratches."
in	75..89	->	if	(isBlessed)	{
"has	some	minor	wounds	but	is	healing	quite	quickly!"
}	else	{
"has	some	minor	wounds."
## }
in	15..74	->	"looks	pretty	hurt."
else	->	"is	in	awful	condition!"
## }
## }
A	when	expression	works	similarly	to	an	if/else	expression	in	that	you	define
conditions	and	branches	that	are	executed	if	a	condition	is	true.	when	is	different
in	that	it	scopes	the	lefthand	side	of	the	condition	automatically	to	whatever	you
provide	as	an	argument	to	when.	We	will	talk	more	about	scoping	in	more	depth
in	Chapter	4	and	Chapter	12.	For	a	quick	introduction,	consider	the	in	90..99
branch	condition.
You	have	seen	how	to	use	the	in	keyword	to	check	whether	a	value	is	within	a
range,	and	that	is	what	you	are	doing	here	–	you	are	checking	the	value	of
healthPoints,	even	though	you	do	not	mention	it	by	name.	Because	the
range,	on	the	left	of	the	->,	is	scoped	to	the	healthPoints	variable,	the
compiler	evaluates	when	expressions	as	though	healthPoints	were	included
in	each	branch	condition.
Often,	when	better	expresses	the	logic	behind	code.	In	this	case,	achieving	the
same	result	with	an	if/else	expression	required	three	else	if	branches.	Your
when	expression	is	much	cleaner.
when	expressions	also	support	greater	flexibility	than	if/else	statements	in	how
they	match	branches	against	the	conditions	you	define.	Most	of	the	conditions	on
the	lefthand	side	of	the	branches	evaluate	to	either	true	or	false,	and	others	fall
back	to	a	default	equality	check,	as	is	the	case	with	the	100	branch	condition.	A

when	expression	can	express	either	one	interchangeably,	as	demonstrated	above.
By	the	way,	were	you	wondering	about	the	nested	if/else	in	one	branch	of	your
when	expression?	This	pattern	is	not	very	common,	but	Kotlin’s	when	expression
gives	you	all	of	the	flexibility	that	you	need	to	implement	it.
Run	NyetHack	to	confirm	that	your	refactoring	of	healthStatus	to	use	a
when	expression	did	not	change	any	logic.

## String	Templates
You	have	seen	that	a	string	can	be	built	up	with	the	values	of	variables	and	even
the	results	of	conditional	expressions.	Kotlin	features	string	templates	to	aid	in
this	common	need	and,	again,	make	your	code	more	readable.	Templates	allow
you	to	include	the	value	of	a	variable	inside	a	string’s	quotation	marks.	Update
the	player	status	display	code	to	use	string	templates,	as	shown	below:
Listing	3.11		Using	a	string	template	(Game.kt)
fun	main(args:	Array<String>)	{
## ...
//	Player	status
println(name	+	"	"	+	healthStatus)
println("$name	$healthStatus")
## }
You	added	the	values	of	name	and	healthStatus	to	the	player	status	display
string	by	prefixing	each	with	$.	This	special	symbol	indicates	to	Kotlin	that	you
would	like	to	template	a	val	or	var	within	a	string	you	define,	and	it	is	provided
as	a	convenience.	Note	that	these	templated	values	appear	inside	the	quotation
marks	that	define	the	string.
Run	the	program.	You	should	see	the	same	output	you	have	been	seeing:
## GREEN
Madrigal	has	some	minor	wounds	but	is	healing	quite	quickly!
Kotlin	also	allows	you	to	evaluate	an	expression	within	a	string	and	interpolate
the	result	–	that	is,	to	insert	the	result	into	the	string.	Any	expression	that	you
add	within	the	curly	braces	after	a	dollar-sign	character	(${})	will	be	evaluated
as	a	part	of	the	string.	Add	a	report	of	the	player’s	blessedness	and	aura	color	to
the	player	status	display	to	see	how	this	works.	Be	sure	to	remove	the	existing
print	statement	for	auraColor.
Listing	3.12		Formatting	the	isBlessed	status	with	a	string
expression	(Game.kt)
fun	main(args:	Array<String>)	{
## ...
## //	Aura
val	auraVisible	=	isBlessed	&&	healthPoints	>	50	||	isImmortal
val	auraColor	=	if	(auraVisible)	"GREEN"	else	"NONE"
print(auraColor)
## ...
//	Player	status
println("(Aura:	$auraColor)	"	+
"(Blessed:	${if	(isBlessed)	"YES"	else	"NO"})")
println("$name	$healthStatus")
## }
This	new	line	tells	the	compiler	to	print	the	literal	string	(Blessed:	and	the

result	of	the	expression	if	(isBlessed)	"YES"	else	"NO".	Note	that	this	one-
line	conditional	expression	takes	advantage	of	the	option	to	skip	braces	for
simplicity.	It	is	the	same	as:
if	(isBlessed)	{
## "YES"
}	else	{
## "NO"
## }
The	extra	syntax	adds	nothing	here,	so	doing	away	with	it	makes	sense.	Either
way,	the	string	template	will	place	the	result	of	the	conditional	in	the	string.
Before	you	run	the	program	to	check	your	addition,	what	do	you	think	the	result
will	be?	Run	the	program	to	confirm.
Much	of	the	work	programs	do	is	in	response	to	some	status	or	action.	In	this
chapter,	you	saw	how	to	add	rules	for	when	your	code	will	execute	by	using
if/else	and	when	expressions.	You	also	saw	the	assignable	version	of	if/else,
the	if/else	conditional	expression.	You	saw	how	to	represent	series	of	numbers
or	characters	using	ranges.	Finally,	you	saw	how	a	string	expression	can	be	used
to	conveniently	interpolate	variables	and	values	into	a	string.
Be	sure	to	save	NyetHack,	because	you	will	be	using	it	again	in	the	next	chapter
–	where	you	will	learn	more	about	functions,	a	way	to	group	and	reuse
expressions	in	your	program.

## Challenge:	Trying	Out	Some	Ranges
Ranges	are	a	powerful	tool	in	Kotlin,	and	with	some	practice	you	will	find	the
syntax	intuitive.	For	this	simple	challenge,	open	the	Kotlin	REPL	(Tools	→	Kotlin
→	REPL)	and	explore	some	range	syntax,	including	the	toList(),	downTo,
and	until	functions.	Enter	the	following	ranges,	one	by	one.	Before	pressing
Command-Return	(Ctrl-Return)	to	execute	the	line	and	see	the	result,	think	about
what	you	expect	the	result	to	be.
Listing	3.13		Exploring	ranges	(REPL)
1	in	1..3
(1..3).toList()
1	in	3	downTo	1
1	in	1	until	3
3	in	1	until	3
2	in	1..3
## 2	!in	1..3
'x'	in	'a'..'z'

Challenge:	Enhancing	the	Aura
Before	you	start	this	challenge	or	the	next	one,	close	NyetHack	and	create	a
copy	of	it	using	your	file	explorer.	You	will	be	making	changes	to	your	program
that	you	will	not	want	to	keep	for	future	chapters.	Name	your	copy
NyetHack_ConditionalsChallenges	or	whatever	you	would	like.	You
will	want	to	do	this	before	starting	the	challenges	in	future	chapters	as	well.
Currently,	if	an	aura	is	displayed,	it	is	always	green.	For	this	challenge,	have	the
color	of	the	player’s	aura	reflect	their	current	karma.
Karma	has	a	numeric	value	from	0	to	20.	To	determine	the	player’s	karma,	use
the	following	formula:
val	karma	=	(Math.pow(Math.random(),	(110	-	healthPoints)	/	100.0)	*	20	).toInt()
Have	the	displayed	aura	follow	these	rules:
Karma	valueAura	color
## 0-5red
## 6-10orange
## 11-15purple
## 16-20green
Determine	the	karma	value	with	the	formula	above	and	check	the	player’s	aura
color	using	a	conditional	expression.	Finally,	modify	the	player	status	display	to
report	the	new	color	if	the	aura	is	visible.

## Challenge:	Configurable	Status	Format
Currently,	the	player’s	status	display	is	created	by	two	calls	to	println.	There
is	no	variable	that	holds	the	value	of	the	full	display	string.
The	code	looks	like	this:
//	Player	status
println("(Aura:	$auraColor)	"	+
"(Blessed:	${if	(isBlessed)	"YES"	else	"NO"	})")
println("$name	$healthStatus")
And	it	produces	output	like	this:
(Aura:	GREEN)	(Blessed:	YES)
Madrigal	has	some	minor	wounds	but	is	healing	quite	quickly!
For	this	more	difficult	challenge,	make	the	status	line	configurable	with	a	status
format	string.	Use	the	character	B	for	blessed,	A	for	aura	color,	H	for
healthStatus,	and	HP	for	healthPoints.	For	example,	a	status	format
string	of:
val	statusFormatString	=	"(HP)(A)	->	H"
should	generate	a	player	status	display	like:
(HP:	100)(Aura:	Green)	->	Madrigal	is	in	excellent	condition!