

## 20
## Java	Interoperability
Throughout	this	book,	you	have	learned	the	fundamentals	of	the	Kotlin
programming	language,	and	we	hope	you	are	inspired	to	use	Kotlin	to	improve
existing	Java	projects	you	may	have.	Where	do	you	start?
You	have	seen	before	that	Kotlin	compiles	down	to	Java	bytecode.	This	means
that	Kotlin	is	interoperable	with	Java	–	that	is,	it	functions	alongside	and	works
with	Java	code.
This	is	likely	the	most	important	feature	of	the	Kotlin	programming	language.
Full	interoperability	with	Java	means	that	Kotlin	files	and	Java	files	can	exist	in
the	same	project,	side	by	side.	You	can	invoke	Java	methods	from	Kotlin	and
Kotlin	functions	from	Java.	This	means	you	can	use	existing	Java	libraries	from
Kotlin,	including	the	Android	framework.
Full	interoperability	with	Java	also	means	that	you	can	slowly	transition	your
codebase	from	Java	to	Kotlin.	Maybe	you	do	not	have	the	opportunity	to	rebuild
your	project	entirely	in	Kotlin	–	consider	moving	new	feature	development	to
Kotlin.	Perhaps	you	would	like	to	convert	the	Java	files	in	your	application	that
will	see	the	most	benefit	from	a	move	to	Kotlin	–	consider	converting	your
model	objects	or	your	unit	tests.
This	chapter	will	show	you	how	Java	and	Kotlin	files	interoperate	and	discuss
the	things	you	should	consider	when	writing	code	that	will	interoperate.

Interoperating	with	a	Java	Class
For	this	chapter,	create	a	new	project	in	IntelliJ	called	Interop.	Interop	will
contain	two	files:	Hero.kt,	a	Kotlin	file	that	represents	the	hero	from
NyetHack,	and	Jhava.java,	a	Java	class	that	represents	a	monster	from
another	realm.	Create	these	two	files	as	well.
In	this	chapter,	you	will	write	both	Kotlin	code	and	Java	code.	If	you	do	not	have
experience	writing	Java	code,	fear	not,	as	the	Java	code	in	these	examples	should
be	intuitive	given	your	Kotlin	experience.
Start	by	declaring	the	Jhava	class	and	giving	it	a	method	called
utterGreeting	that	returns	a	String:
Listing	20.1		Declaring	a	class	and	method	in	Java	(Jhava.java)
public	class	Jhava	{
public	String	utterGreeting()	{
return	"BLARGH";
## }
## }
Now,	in	Hero.kt,	create	a	main	function.	In	it,	declare	an	adversary	val,
an	instance	of	Jhava:
Listing	20.2		Declaring	a	main	function	and	Jhava	adversary	in
Kotlin	(Hero.kt)
fun	main(args:	Array<String>)	{
val	adversary	=	Jhava()
## }
That	is	it!	With	that,	you	have	written	a	line	of	Kotlin	code	that	instantiates	a
Java	object	and	bridged	the	gap	between	the	two	languages.	Java	interoperability
in	Kotlin	really	is	that	easy.
But	we	do	have	more	to	show	you,	so	let’s	press	on.	As	a	test,	print	out	the
greeting	that	the	Jhava	adversary	utters.
Listing	20.3		Invoking	a	Java	method	in	Kotlin	(Hero.kt)
fun	main(args:	Array<String>)	{
val	adversary	=	Jhava()
println(adversary.utterGreeting())
## }
You	have	now	instantiated	a	Java	object	and	invoked	a	Java	method	on	it,	all
from	Kotlin.	Run	Hero.kt.	You	should	see	the	monster’s	greeting	(BLARGH)
printed	out	to	the	console.

Kotlin	was	created	to	interoperate	seamlessly	with	Java.	It	was	also	created	with
a	number	of	improvements	over	Java.	Do	you	have	to	give	up	the	improvements
when	you	want	to	interoperate?	Not	at	all.	With	some	awareness	of	the
differences	in	the	two	languages	and	the	help	of	annotations	available	on	each
side,	you	can	enjoy	the	best	of	what	Kotlin	has	to	offer.

Interoperability	and	Nullity
Add	another	method	to	Jhava	called	determineFriendshipLevel.
determineFriendshipLevel	should	return	a	value	of	type	String	and,
because	the	monster	does	not	understand	friendship,	a	value	of	null.
Listing	20.4		Returning	null	from	a	Java	method	(Jhava.java)
public	class	Jhava	{
public	String	utterGreeting()	{
return	"BLARGH";
## }
public	String	determineFriendshipLevel()	{
return	null;
## }
## }
Call	this	new	method	from	Hero.kt,	storing	the	monster’s	friendship	level	in	a
val.	You	are	going	to	print	this	value	out	to	the	console,	but,	remembering	that
the	monster	yelled	its	greeting	at	you	in	all	caps,	go	ahead	and	lowercase	the
friendship	level	before	printing	it	out.
Listing	20.5		Printing	the	friendship	level	(Hero.kt)
fun	main(args:	Array<String>)	{
val	adversary	=	Jhava()
println(adversary.utterGreeting())
val	friendshipLevel	=	adversary.determineFriendshipLevel()
println(friendshipLevel.toLowerCase())
## }
Run	Hero.kt.	Although	the	compiler	did	not	alert	you	to	any	problems,	the
program	crashes	at	runtime:
Exception	in	thread	"main"
java.lang.IllegalStateException:	friendshipLevel	must	not	be	null
In	Chapter	6,	we	told	you	that	in	Java	all	objects	can	be	null.	When	you	call	a
Java	method	like	determineFriendshipLevel,	the	API	seems	to
advertise	that	the	method	will	return	a	String,	but	that	does	not	mean	that	you
can	assume	that	the	return	value	will	play	by	Kotlin’s	rules	about	nullity.
Because	all	objects	in	Java	can	be	null,	it	is	safer	to	assume	that	values	are
nullable	unless	otherwise	specified.	However,	while	this	assumption	is	safer,	it
can	lead	to	considerably	more	verbose	code,	as	you	will	have	to	handle	the
nullability	of	each	and	every	Java	variable	you	reference.
In	Hero.kt,	hold	down	the	Command	(Ctrl)	key	and	mouse	over
determineFriendshipLevel.	IntelliJ	reports	that	the	method	returns	a

value	of	type	String!.	The	exclamation	mark	means	that	the	return	value
could	either	be	String	or	String?.	The	Kotlin	compiler	does	not	know
whether	the	value	of	the	string	being	returned	from	Java	is	null.
These	ambiguous	return	value	types	are	called	platform	types.	Platform	types	are
not	syntactically	meaningful;	they	are	only	displayed	in	the	IDE	and	in	other
documentation.
Fortunately,	authors	of	Java	code	can	write	Kotlin-friendly	code	that	advertises
nullity	more	explicitly	using	nullability	annotations.	Explicitly	declare	that
determineFriendshipLevel	can	return	a	value	of	null	by	adding	a
@Nullable	annotation	to	its	method	header.
Listing	20.6		Specifying	that	a	return	value	will	possibly	be	null
(Jhava.java)
public	class	Jhava	{
public	String	utterGreeting()	{
return	"BLARGH";
## }
@Nullable
public	String	determineFriendshipLevel()	{
return	null;
## }
## }
(You	will	need	to	import	org.jetbrains.annotations.Nullable,	which	IntelliJ
will	offer	to	do	for	you.)
@Nullable	warns	the	consumer	of	this	API	that	the	method	can	return	null	(not
that	it	must	return	null).	The	Kotlin	compiler	recognizes	this	annotation.	Return
to	Hero.kt	and	note	that	IntelliJ	is	now	warning	you	about	invoking
toLowerCase	directly	on	a	String?.
Replace	this	direct	invocation	with	a	safe	call.
Listing	20.7		Handling	nullability	with	the	safe	call	operator
(Hero.kt)
fun	main(args:	Array<String>)	{
val	adversary	=	Jhava()
println(adversary.utterGreeting())
val	friendshipLevel	=	adversary.determineFriendshipLevel()
println(friendshipLevel?.toLowerCase())
## }
Run	Hero.kt.	Now,	null	should	be	printed	to	the	console.
Because	friendshipLevel	is	null,	you	may	want	to	provide	a	default
friendship	level.	Use	the	null	coalescing	operator	to	provide	a	default	to	be	used
when	friendshipLevel	is	null.

Listing	20.8		Providing	a	default	value	with	the	Elvis	operator
(Hero.kt)
fun	main(args:	Array<String>)	{
val	adversary	=	Jhava()
println(adversary.utterGreeting())
val	friendshipLevel	=	adversary.determineFriendshipLevel()
println(friendshipLevel?.toLowerCase()	?:	"It's	complicated.")
## }
Run	Hero.kt,	and	you	should	see	It's	complicated.
You	used	@Nullable	to	signify	that	a	method	could	return	null.	You	can	specify
that	a	value	will	definitely	not	be	null	using	the	@NotNull	annotation.	This
annotation	is	nice,	because	it	means	that	the	consumer	of	this	API	does	not	need
to	worry	that	the	value	returned	could	be	null.	The	Jhava	monster’s	greeting
should	not	be	null,	so	add	a	@NotNull	annotation	to	the	utterGreeting
method	header.
Listing	20.9		Specifying	that	a	return	value	will	not	be	null
(Jhava.java)
public	class	Jhava	{
@NotNull
public	String	utterGreeting()	{
return	"BLARGH";
## }
@Nullable
public	String	determineFriendshipLevel()	{
return	null;
## }
## }
(Again,	you	will	need	to	import	the	annotation.)
Nullability	annotations	can	be	used	to	add	context	to	return	values,	parameters,
and	even	fields.
Kotlin	provides	a	variety	of	tools	for	dealing	with	nullability,	including
prohibiting	normal	types	from	being	null.	If	you	write	Kotlin	code,	then	the	most
common	source	of	issues	with	null	is	interoperation,	so	take	care	when	calling
Java	code	from	Kotlin.

## Type	Mapping
Kotlin’s	types	often	correspond	one	to	one	with	Java	types.	A	String	in	Kotlin
is	a	String	when	compiled	down	to	Java.	This	means	that	a	String	returned
from	Java	methods	can	be	used	in	the	same	way	in	Kotlin	as	a	String
explicitly	declared	in	Kotlin.
There	are,	however,	some	type	mappings	that	are	not	one	to	one	between	Kotlin
and	Java.	For	an	example,	consider	basic	data	types.	As	we	discussed	in	the
section	called	For	the	More	Curious:	Java	Primitive	Types	in	Kotlin	in
Chapter	2,	Java	represents	basic	data	types	using	what	it	calls	primitive	types.
Primitive	types	are	not	objects	in	Java,	but	all	types	are	objects	in	Kotlin	–
including	basic	data	types.	However,	the	Kotlin	compiler	maps	Java	primitives
onto	the	most	similar	Kotlin	type.
To	see	type	mapping	in	action,	add	an	integer	called	hitPoints	to	Jhava.	An
integer	is	represented	by	the	object	type	Int	in	Kotlin	and	by	the	primitive	type
int	in	Java.
Listing	20.10		Declaring	an	int	in	Java	(Jhava.java)
public	class	Jhava	{
public	int	hitPoints	=	52489112;
@NotNull
public	String	utterGreeting()	{
return	"BLARGH";
## }
@Nullable
public	String	determineFriendshipLevel()	{
return	null;
## }
## }
Now,	obtain	a	reference	to	hitPoints	in	Hero.kt.
Listing	20.11		Referencing	a	Java	field	from	Kotlin	(Hero.kt)
fun	main(args:	Array<String>)	{
val	adversary	=	Jhava()
println(adversary.utterGreeting())
val	friendshipLevel	=	adversary.determineFriendshipLevel()\
println(friendshipLevel?.toLowerCase()	?:	"It's	complicated.")
val	adversaryHitPoints:	Int	=	adversary.hitPoints
## }
Although	hitPoints	is	defined	in	the	Jhava	class	as	an	int,	you	refer	to	it
here	as	an	Int	with	no	problem.	(You	are	not	using	type	inference	here	only	to
illustrate	the	type	mapping.	Explicit	type	declarations	are	not	required	for

interoperability:	val	adversaryHitPoints	=	adversary.hitPoints	would
work	just	as	well.)
Now	that	you	have	a	reference	to	this	integer,	you	can	invoke	functions	on	it.
Call	a	function	on	adversaryHitPoints	and	print	out	the	result.
Listing	20.12		Referencing	a	Java	field	from	Kotlin	(Hero.kt)
fun	main(args:	Array<String>)	{
## ...
val	adversaryHitPoints:	Int	=	adversary.hitPoints
println(adversaryHitPoints.dec())
## }
Run	Hero.kt	to	print	out	the	adversary’s	hit	points,	decremented	by	1.
In	Java,	methods	cannot	be	invoked	on	primitive	types.	In	Kotlin,	the	integer
adversaryHitPoints	is	an	object	of	type	Int,	and	functions	can	be	called
on	that	Int.
As	another	illustration	of	type	mapping,	print	the	name	of	the	Java	class	backing
adversaryHitPoints.
Listing	20.13		Java	backing	class	name	(Hero.kt)
fun	main(args:	Array<String>)	{
## ...
val	adversaryHitPoints:	Int	=	adversary.hitPoints
println(adversaryHitPoints.dec())
println(adversaryHitPoints.javaClass)
## }
When	you	run	Hero.kt,	you	will	see	int	printed	to	the	console.	Although	you
can	invoke	Int	functions	on	adversaryHitPoints,	the	variable	is	a
primitive	int	at	runtime.	As	you	may	recall	from	the	bytecode	you	looked	at	in
Chapter	2,	all	mapped	types	are	mapped	back	to	their	Java	counterparts	at
runtime.	Kotlin	gives	you	the	power	of	objects	when	you	want	them,	but	the
performance	of	primitive	types	when	you	need	them.

Getters,	Setters,	and	Interoperability
Kotlin	and	Java	handle	class-level	variables	quite	differently.	Java	uses	fields
and	typically	gates	access	via	accessor	and	mutator	methods.	Kotlin,	as	you	have
seen,	features	properties,	which	restrict	access	to	backing	fields	and	may
automatically	expose	accessors	and	mutators.
In	the	last	section,	you	added	a	public	hitPoints	field	to	Jhava.	This
worked	to	illustrate	type	mapping,	but	it	violates	the	principle	of	encapsulation	–
so	is	not	a	good	solution.	In	Java,	fields	should	be	accessed	or	mutated	using
methods	called	getters	and	setters.	Getters	can	be	used	to	access	data,	and	setters
can	be	used	to	mutate	data.
Make	hitPoints	private	and	create	a	getter	method	so	that	hitPoints	can
be	accessed	but	not	mutated.
Listing	20.14		Declaring	a	field	in	Java	(Jhava.java)
public	class	Jhava	{
publicprivate	int	hitPoints	=	52489112;
@NotNull
public	String	utterGreeting()	{
return	"BLARGH";
## }
@Nullable
public	String	determineFriendshipLevel()	{
return	null;
## }
public	int	getHitPoints()	{
return	hitPoints;
## }
## }
Now,	return	to	Hero.kt.	Note	that	your	code	still	compiles.	Recall	from
Chapter	12	that	Kotlin	can	bypass	the	need	for	using	getter/setter	syntax,
meaning	that	you	can	use	syntax	that	looks	like	you	are	accessing	fields	or
properties	directly	while	still	maintaining	encapsulation.	Because
getHitPoints	is	prefixed	with	get,	you	can	drop	the	prefix	in	Kotlin	and
refer	to	it	simply	as	hitPoints.	This	Kotlin	feature	transcends	the	barrier
between	Kotlin	and	Java.
The	same	goes	for	setters.	By	now	your	hero	and	the	Jhava	monster	are	well
acquainted	and	wish	to	communicate	further.	The	hero	would	like	to	expand	the
monster’s	vocabulary	beyond	a	single	utterance.	Pull	the	monster’s	greeting	out
into	a	field	and	add	a	getter	and	a	setter	so	that	the	hero	can	modify	the	greeting

in	an	attempt	to	teach	the	monster	language.
Listing	20.15		Exposing	a	greeting	in	Java	(Jhava.java)
public	class	Jhava	{
private	int	hitPoints	=	52489112;
private	String	greeting	=	"BLARGH";
## ...
@NotNull
public	String	utterGreeting()	{
return	"BLARGH"greeting;
## }
## ...
public	String	getGreeting()	{
return	greeting;
## }
public	void	setGreeting(String	greeting)	{
this.greeting	=	greeting;
## }
## }
In	Hero.kt,	modify	adversary.greeting.
Listing	20.16		Setting	a	Java	field	from	Kotlin	(Hero.kt)
fun	main(args:	Array<String>)	{
## ...
val	adversaryHitPoints:	Int	=	adversary.hitPoints
println(adversaryHitPoints.dec())
println(adversaryHitPoints.javaClass)
adversary.greeting	=	"Hello,	Hero."
println(adversary.utterGreeting())
## }
You	can	use	assignment	syntax	to	mutate	a	Java	field,	rather	than	calling	its
associated	setter.	You	have	the	syntax	benefits	provided	in	Kotlin,	even	while
working	with	Java	APIs.	Run	Hero.kt	to	see	that	the	hero	has	taught	the
Jhava	monster	some	language.

## Beyond	Classes
Kotlin	affords	developers	greater	flexibility	with	respect	to	the	format	of	the
code	that	they	write.	A	Kotlin	file	can	include	classes,	functions,	and	variables	–
all	at	the	top	level	of	the	file.	In	Java,	a	file	represents	exactly	one	class.	How,
then,	are	top-level	functions	declared	in	Kotlin	represented	in	Java?
Expand	the	interspecies	communication	with	a	proclamation	from	the	hero.
Declare	a	function	called	makeProclamation	in	Hero.kt,	outside	of	the
main	function	that	you	worked	in	before.
Listing	20.17		Declaring	a	top-level	function	in	Kotlin	(Hero.kt)
fun	main(args:	Array<String>)	{
## ...
## }
fun	makeProclamation()	=	"Greetings,	beast!"
You	will	need	a	way	to	invoke	this	function	from	Java,	so	add	a	main	method	to
## Jhava.
Listing	20.18		Defining	a	main	method	in	Java	(Jhava.java)
public	class	Jhava	{
private	int	hitPoints	=	52489112;
private	String	greeting	=	"BLARGH";
public	static	void	main(String[]	args)	{
## }
## ...
## }
In	that	main	method,	print	out	the	value	returned	by	makeProclamation,
referencing	the	function	as	a	static	method	in	the	class	HeroKt:
Listing	20.19		Referencing	a	top-level	Kotlin	function	from	Java
(Jhava.java)
public	class	Jhava	{
## ...
public	static	void	main(String[]	args)	{
System.out.println(HeroKt.makeProclamation());
## }
## ...
## }
Top-level	functions	defined	in	Kotlin	are	represented	as	static	methods	in	Java
and	are	called	as	such.	makeProclamation	is	defined	in	Hero.kt,	so	the
Kotlin	compiler	creates	a	class	called	HeroKt	for	the	static	method	to	be
associated	with.

If	you	would	like	Hero.kt	and	Jhava.java	to	interoperate	a	bit	more
fluidly,	you	can	change	the	name	of	the	generated	class	with	the	@JvmName
annotation.	Do	this	at	the	top	of	Hero.kt:
Listing	20.20		Specifying	compiled	class	name	using	JvmName
(Hero.kt)
@file:JvmName("Hero")
fun	main(args:	Array<String>)	{
## ...
## }
fun	makeProclamation()	=	"Greetings,	beast!"
Now,	in	Jhava,	you	can	reference	the	makeProclamation	function	more
cleanly.
Listing	20.21		Referencing	a	renamed	top-level	Kotlin	function	from
Java	(Jhava.java)
public	class	Jhava	{
## ...
public	static	void	main(String[]	args)	{
System.out.println(HeroKt.makeProclamation());
## }
## ...
## }
Run	Jhava.java	to	read	your	hero’s	proclamation.	JVM	annotations	like
@JvmName	give	you	direct	control	over	what	Java	code	is	generated	when	you
write	Kotlin	code.
Another	important	JVM	annotation	is	@JvmOverloads.	Kotlin’s	default
parameters	empower	you	to	replace	verbose,	repetitive	method	overloading	with
a	streamlined	approach	to	providing	options	in	your	API.	What	does	this	mean	in
practice?	The	following	example	should	clarify	things.
Add	a	new	function	called	handOverFood	to	Hero.kt.
Listing	20.22		Adding	a	function	with	default	parameters	(Hero.kt)
## ...
fun	makeProclamation()	=	"Greetings,	beast!"
fun	handOverFood(leftHand:	String	=	"berries",	rightHand:	String	=	"beef")	{
println("Mmmm...	you	hand	over	some	delicious	$leftHand	and	$rightHand.")
## }
The	hero	offers	some	food	in	the	handOverFood	function,	and	the	function’s
caller	has	options	for	invoking	it	due	to	its	default	parameters.	The	caller	can
specify	what	is	in	the	hero’s	left	hand	and/or	right	hand	–	or	accept	the	default
options	of	berries	and	beef.	Kotlin	gives	the	caller	options	without	adding	much
complexity	to	the	code.

Java,	on	the	other	hand,	which	lacks	default	parameters,	would	accomplish	this
with	method	overloading:
public	static	void	handOverFood(String	leftHand,	String	rightHand)	{
System.out.println("Mmmm...	you	hand	over	some	delicious	"	+
leftHand	+	"	and	"	+	rightHand	+	".");
## }
public	static	void	handOverFood(String	leftHand)	{
handOverFood(leftHand,	"beef");
## }
public	static	void	handOverFood()	{
handOverFood("berries",	"beef");
## }
Method	overloading	in	Java	requires	much	more	code	than	default	parameters	in
Kotlin.	Also,	one	option	for	calling	the	Kotlin	function	cannot	be	replicated	in
Java	–	the	option	of	using	the	default	value	for	the	first	parameter,	leftHand,
while	passing	a	value	for	the	second	parameter,	rightHand.	Kotlin’s	named
function	arguments	make	this	option	possible:	handOverFood(rightHand	=
"cookies")	will	result	in	Mmmm...	you	hand	over	some	delicious	berries
and	cookies.	But	Java	does	not	support	named	method	parameters,	so	it	has	no
way	to	distinguish	between	methods	called	with	the	same	number	of	parameters
(unless	the	parameters	are	of	different	types).
As	you	will	see	in	a	moment,	the	@JvmOverloads	annotation	triggers	the
generation	of	the	three	corresponding	Java	methods	so	that	Java	consumers	are,
for	the	most	part,	not	left	out.
The	Jhava	monster	abhors	fruit.	It	would	like	to	be	offered	pizza	or	beef
instead	of	berries.	Add	a	method	called	offerFood	to	Jhava.java	that
exposes	a	way	for	a	Hero	to	offer	food	to	a	Jhava	monster.
Listing	20.23		Only	one	method	signature	(Jhava.java)
public	class	Jhava	{
## ...
public	void	setGreeting(String	greeting)	{
this.greeting	=	greeting;
## }
public	void	offerFood()	{
Hero.handOverFood("pizza");
## }
## }
This	call	to	handOverFood	causes	a	compiler	error,	because	Java	has	no
concept	of	default	method	parameters.	As	such,	a	version	of	handOverFood
with	only	one	parameter	does	not	exist	in	Java.	To	verify,	take	a	look	at	the
decompiled	Java	bytecode	for	handOverFood:
public	static	final	void	handOverFood(@NotNull	String	leftHand,
@NotNull	String	rightHand)	{
Intrinsics.checkParameterIsNotNull(leftHand,	"leftHand");
Intrinsics.checkParameterIsNotNull(rightHand,	"rightHand");
String	var2	=	"Mmmm...	you	hand	over	some	delicious	"	+

leftHand	+	"	and	"	+	rightHand	+	'.';
## System.out.println(var2);
## }
While	you	have	the	option	to	avoid	method	overloading	in	Kotlin,	your	Java
counterparts	are	not	afforded	the	same	luxury.	The	@JvmOverloads	annotation
will	help	your	API	consumers	in	Java	by	providing	overloaded	versions	of	your
Kotlin	function.	Add	the	annotation	to	handOverFood	in	Hero.kt.
Listing	20.24		Adding	@JvmOverloads	(Hero.kt)
## ...
fun	makeProclamation()	=	"Greetings,	beast!"
@JvmOverloads
fun	handOverFood(leftHand:	String	=	"berries",	rightHand:	String	=	"beef")	{
println("Mmmm...	you	hand	over	some	delicious	$leftHand	and	$rightHand.")
## }
Your	invocation	of	handOverFood	in	Jhava.offerFood	no	longer	causes
an	error,	because	it	is	now	calling	a	version	of	handOverFood	that	exists	in
Java.	You	can	again	confirm	this	by	looking	at	the	new	decompiled	Java
bytecode:
@JvmOverloads
public	static	final	void	handOverFood(@NotNull	String	leftHand,
@NotNull	String	rightHand)	{
Intrinsics.checkParameterIsNotNull(leftHand,	"leftHand");
Intrinsics.checkParameterIsNotNull(rightHand,	"rightHand");
String	var2	=	"Mmmm...	you	hand	over	some	delicious	"	+
leftHand	+	"	and	"	+	rightHand	+	'.';
## System.out.println(var2);
## }
@JvmOverloads
public	static	final	void	handOverFood(@NotNull	String	leftHand)	{
handOverFood$default(leftHand,	(String)null,	2,	(Object)null);
## }
@JvmOverloads
public	static	final	void	handOverFood()	{
handOverFood$default((String)null,	(String)null,	3,	(Object)null);
## }
Note	that	the	single-parameter	method	specifies	the	first	parameter	from	the
Kotlin	function:	leftHand.	When	this	method	is	called,	the	default	value	for
the	second	parameter	will	be	used.
To	test	offering	food	to	the	monster,	call	offerFood	in	Hero.kt:
Listing	20.25		Testing	out	offerFood	(Hero.kt)
@file:JvmName("Hero")
fun	main(args:	Array<String>)	{
## ...
adversary.greeting	=	"Hello,	Hero."
println(adversary.utterGreeting())
adversary.offerFood()
## }
fun	makeProclamation()	=	"Greetings,	beast!"
## ...

Run	Hero.kt	to	confirm	that	the	hero	hands	over	pizza	and	beef.
If	you	are	designing	an	API	that	may	be	exposed	to	Java	consumers,	consider
using	@JvmOverloads	for	an	API	that	is	nearly	as	robust	for	Java	developers	as	it
is	for	Kotlin	developers.
There	are	two	more	JVM	annotations	that	you	should	consider	when	writing
Kotlin	code	that	will	interoperate	with	Java	code,	and	they	both	have	to	do	with
classes.	Hero.kt	does	not	yet	have	a	class	implementation,	so	add	a	new	class
called	Spellbook.	Give	Spellbook	one	property,	spells	–	a	list	of	string
spell	names.
Listing	20.26		Declaring	the	Spellbook	class	(Hero.kt)
## ...
@JvmOverloads
fun	handOverFood(leftHand:	String	=	"berries",	rightHand:	String	=	"beef")	{
println("Mmmm...	you	hand	over	some	delicious	$leftHand	and	$rightHand.")
## }
class	Spellbook	{
val	spells	=	listOf("Magic	Ms.	L",	"Lay	on	Hans")
## }
Recall	that	Kotlin	and	Java	handle	class-level	variables	quite	differently:	Java
uses	fields	with	getter	and	setter	methods,	while	Kotlin	has	properties	with
backing	fields.	As	a	result,	while	in	Java	you	can	access	a	field	directly,	in	Kotlin
you	will	be	routed	through	an	accessor	–	even	though	the	access	syntax	may	be
identical.
So,	referencing	spells,	a	property	of	Spellbook,	in	Kotlin	would	look	like
this:
val	spellbook	=	Spellbook()
val	spells	=	spellbook.spells
And	in	Java,	accessing	spells	would	look	like	this:
Spellbook	spellbook	=	new	Spellbook();
List<String>	spells	=	spellbook.getSpells();
In	Java,	calling	getSpells	would	be	necessary	because	you	cannot	directly
access	the	spells	field.	However,	you	can	apply	the	@JvmField	annotation	to	a
Kotlin	property	to	expose	its	backing	field	to	Java	consumers	and	avoid	the	need
for	a	getter	method.	Apply	JvmField	to	spells	to	expose	it	directly	to	Jhava:
Listing	20.27		Applying	the	@JvmField	annotation	(Hero.kt)
## ...
@JvmOverloads
fun	handOverFood(leftHand:	String	=	"berries",	rightHand:	String	=	"beef")	{
println("Mmmm...	you	hand	over	some	delicious	$leftHand	and	$rightHand.")
## }
class	Spellbook	{
@JvmField
val	spells	=	listOf("Magic	Ms.	L",	"Lay	on	Hans")

## }
Now,	in	Jhava.java’s	main	method,	you	can	access	spells	directly	to
print	out	each	spell:
Listing	20.28		Accessing	a	Kotlin	field	directly	in	Java	(Jhava.java)
## ...
public	static	void	main(String[]	args)	{
System.out.println(Hero.makeProclamation());
System.out.println("Spells:");
Spellbook	spellbook	=	new	Spellbook();
for	(String	spell	:	spellbook.spells)	{
## System.out.println(spell);
## }
## }
@NotNull
public	String	utterGreeting()	{
return	greeting;
## }
## ...
Run	Jhava.java	to	confirm	that	the	spells	in	the	spellbook	are	printed	out	to
the	console.
You	can	also	use	@JvmField	to	statically	represent	values	in	a	companion	object.
Recall	from	Chapter	15	that	companion	objects	are	declared	within	another	class
declaration	and	initialized	either	when	their	enclosing	class	is	initialized	or	when
any	of	their	properties	or	functions	are	accessed.	Add	a	companion	object
containing	one	value,	MAX_SPELL_COUNT,	to	Spellbook.
Listing	20.29		Adding	a	companion	object	to	Spellbook	(Hero.kt)
## ...
class	Spellbook	{
@JvmField
val	spells	=	listOf("Magic	Ms.	L",	"Lay	on	Hans")
companion	object	{
val	MAX_SPELL_COUNT	=	10
## }
## }
Now	attempt	to	access	MAX_SPELL_COUNT	from	Jhava’s	main	method
using	Java’s	static	access	syntax.
Listing	20.30		Accessing	a	static	value	in	Java	(Jhava.java)
public	static	void	main(String[]	args)	{
System.out.println(Hero.makeProclamation());
System.out.println("Spells:");
Spellbook	spellbook	=	new	Spellbook();
for	(String	spell	:	spellbook.spells)	{
## System.out.println(spell);
## }
System.out.println("Max	spell	count:	"	+	Spellbook.MAX_SPELL_COUNT);
## }
## ...
The	code	does	not	compile.	Why	not?	When	referencing	members	of	a

companion	object	from	Java,	you	must	access	them	first	by	referencing	the
companion	object	and	using	its	accessor:
System.out.println("Max	spell	count:	"	+
Spellbook.Companion.getMAX_SPELL_COUNT());
@JvmField	takes	care	of	all	this	for	you.	Add	a	@JvmField	annotation	to
MAX_SPELL_COUNT	in	Spellbook’s	companion	object.
Listing	20.31		Adding	the	@JvmField	annotation	to	the	member	of	a
companion	object	(Hero.kt)
## ...
class	Spellbook	{
@JvmField
val	spells	=	listOf("Magic	Ms.	L",	"Lay	on	Hans")
companion	object	{
@JvmField
val	MAX_SPELL_COUNT	=	10
## }
## }
Once	that	annotation	is	in	place,	your	code	in	Jhava.java	will	compile,
because	you	can	access	MAX_SPELL_COUNT	just	like	any	other	Java	static.
Run	Jhava.kt	to	confirm	that	the	maximum	spell	count	is	printed	to	the
console.
Although	Kotlin	and	Java	handle	field	access	in	different	ways	by	default,
@JvmField	is	a	useful	way	to	expose	fields	and	ensure	equivalent	access	to	your
Java	counterparts.
Functions	defined	on	companion	objects	run	into	similar	issues	when	accessed
from	Java	–	they	have	to	be	accessed	via	a	reference	to	the	companion	object.
The	@JvmStatic	annotation	works	like	@JvmField	to	allow	direct	access	to
functions	defined	on	companion	objects.	Define	a	function	on	Spellbook’s
companion	object	called	getSpellbookGreeting.
getSpellbookGreeting	returns	a	function	to	be	invoked	when
getSpellbookGreeting	is	called.
Listing	20.32		Using	@JvmStatic	on	a	function	(Hero.kt)
## ...
class	Spellbook	{
@JvmField
val	spells	=	listOf("Magic	Ms.	L",	"Lay	on	Hans")
companion	object	{
@JvmField
val	MAX_SPELL_COUNT	=	10
@JvmStatic
fun	getSpellbookGreeting()	=	println("I	am	the	Great	Grimoire!")
## }
## }
Now,	invoke	getSpellbookGreeting	in	Jhava.java.

Listing	20.33		Invoking	a	static	method	in	Java	(Jhava.java)
## ...
public	static	void	main(String[]	args)	{
System.out.println(Hero.makeProclamation());
System.out.println("Spells:");
Spellbook	spellbook	=	new	Spellbook();
for	(String	spell	:	spellbook.spells)	{
## System.out.println(spell);
## }
System.out.println("Max	spell	count:	"	+	Spellbook.MAX_SPELL_COUNT);
Spellbook.getSpellbookGreeting();
## }
## ...
Run	Jhava.java	to	confirm	that	the	spellbook’s	greeting	is	printed	to	the
console.
Although	statics	do	not	exist	in	Kotlin,	many	commonly	used	patterns	compile
down	to	static	variables	and	methods.	Employing	the	@JvmStatic	annotation
gives	you	greater	control	over	how	Java	developers	interface	with	your	code.

Exceptions	and	Interoperability
The	hero	has	taught	the	Jhava	monster	language,	and	the	monster	will	now
extend	its	hand	in	friendship	...	or	maybe	not.	Add	a	method,
extendHandInFriendship,	to	Jhava.java.
Listing	20.34		Throwing	an	exception	in	Java	(Jhava.java)
public	class	Jhava	{
## ...
public	void	offerFood()	{
Hero.handOverFood("pizza");
## }
public	void	extendHandInFriendship()	throws	Exception	{
throw	new	Exception();
## }
## }
Invoke	this	method	in	Hero.kt:
Listing	20.35		Invoking	a	method	that	throws	an	exception
(Hero.kt)
@file:JvmName("Hero")
fun	main(args:	Array<String>)	{
## ...
adversary.offerFood()
adversary.extendHandInFriendship()
## }
fun	makeProclamation()	=	"Greetings,	beast!"
## ...
Run	this	code,	and	you	will	see	that	a	runtime	exception	is	thrown.	It	is	not	wise
to	trust	a	monster.
Recall	that	exceptions	are	unchecked	in	Kotlin.	In	calling
extendHandInFriendship,	you	called	a	method	that	throws	an	exception.
In	this	instance,	you	knew	that	when	you	called	the	method.	Another	time,	you
may	not	be	so	lucky.	You	should	take	extra	care	to	understand	the	Java	APIs	that
you	are	interfacing	with	from	Kotlin.
Wrap	your	invocation	of	the	extendHandInFriendship	method	in	a
try/catch	block	to	thwart	the	monster’s	treachery.
Listing	20.36		Handling	exceptions	using	try/catch	(Hero.kt)
@file:JvmName("Hero")
fun	main(args:	Array<String>)	{
## ...
adversary.offerFood()

try	{
adversary.extendHandInFriendship()
}	catch	(e:	Exception)	{
println("Begone,	foul	beast!")
## }
## }
fun	makeProclamation()	=	"Greetings,	beast!"
## ...
Run	Hero.kt	to	see	that	the	hero	deftly	avoids	the	monster’s	duplicitous
attack.
Calling	Kotlin	functions	from	Java	requires	some	additional	understanding	when
it	comes	to	handling	exceptions.	All	exceptions	in	Kotlin,	as	we	have	said,	are
unchecked.	But	this	is	not	the	case	in	Java	–	exceptions	can	be	checked,	and	they
must	be	handled	at	the	risk	of	a	crash.	How	does	this	affect	calling	a	Kotlin
function	from	Java?
To	see,	add	a	function	to	Hero.kt	called	acceptApology.	It	is	time	to	exact
revenge	on	the	monster.
Listing	20.37		Throwing	an	unchecked	exception	(Hero.kt)
## ...
@JvmOverloads
fun	handOverFood(leftHand:	String	=	"berries",	rightHand:	String	=	"beef")	{
println("Mmmm...	you	hand	over	some	delicious	$leftHand	and	$rightHand.")
## }
fun	acceptApology()	{
throw	IOException()
## }
class	Spellbook	{
## ...
## }
(You	will	need	to	import	java.io.IOException.)
Now,	call	acceptApology	from	Jhava.java.
Listing	20.38		Throwing	an	exception	in	Java	(Jhava.java)
public	class	Jhava	{
## ...
public	void	apologize()	{
try	{
Hero.acceptApology();
}	catch	(IOException	e)	{
System.out.println("Caught!");
## }
## }
## }
The	Jhava	monster	is	clever	enough	to	suspect	the	hero	of	trickery	and	wraps
its	invocation	of	acceptApology	in	a	try/catch	block.	But	the	Java
compiler	warns	you	that	an	IOException	is	never	thrown	in	the	contents	of
the	try	block	–	that	is,	in	acceptApology.	How	can	this	be?
acceptApology	clearly	throws	an	IOException.

Understanding	this	scenario	requires	a	peek	into	the	decompiled	Java	bytecode:
public	static	final	void	acceptApology()	{
throw	(Throwable)(new	IOException());
## }
You	can	see	that	an	IOException	is	thrown	in	this	function,	but	nothing	about
the	function’s	signature	notifies	the	caller	that	an	IOException	should	be
checked	for.	When	the	Java	compiler	complains	that	acceptApology	does
not	throw	an	IOException	when	invoked	from	Java,	this	is	why.	It	has	no
idea.
Fortunately,	there	is	an	annotation	to	solve	this	problem,	too:	@Throws.	When
you	use	@Throws,	you	include	information	about	the	exception	that	the	function
throws.	Add	a	@Throws	annotation	to	acceptApology	to	augment	its	Java
bytecode.
Listing	20.39		Using	the	@Throws	annotation	(Hero.kt)
## ...
@Throws(IOException::class)
fun	acceptApology()	{
throw	IOException()
## }
class	Spellbook	{
## ...
## }
Now,	look	at	the	resulting	decompiled	Java	bytecode:
public	static	final	void	acceptApology()	throws	IOException	{
throw	(Throwable)(new	IOException());
## }
The	@Throws	annotation	adds	a	throws	keyword	to	the	Java	version	of
acceptApology.	Looking	back	at	Jhava.java,	you	should	see	that	you
have	now	satisfied	the	Java	compiler,	as	it	can	now	recognize	that
acceptApology	throws	an	IOException	that	requires	checking.
The	@Throws	annotation	smooths	over	some	of	the	ideological	differences
between	Java	and	Kotlin	with	respect	to	exception	checking.	If	you	are	writing	a
Kotlin	API	that	may	be	exposed	to	a	Java	consumer,	consider	using	this
annotation	so	that	your	consumer	can	properly	handle	any	exception	thrown.

Function	Types	in	Java
Function	types	and	anonymous	functions	are	novel	inclusions	in	the	Kotlin
programming	language	that	provide	a	streamlined	syntax	for	communicating
between	components.	Their	concise	syntax	is	made	possible	via	the	->	operator,
but	lambdas	are	not	supported	in	versions	of	Java	prior	to	Java	8.
So	what	do	these	function	types	look	like	when	called	from	Java?	The	answer
may	seem	deceptively	simple:	In	Java,	your	function	type	is	represented	by	an
interface	with	a	name	like	FunctionN,	where	N	is	the	number	of	arguments	taken
as	parameters.
To	see	this	in	practice,	add	a	function	type	called	translator	to	Hero.kt.
translator	should	take	a	String,	lowercase	it,	capitalize	the	first	letter,
and	print	out	the	result.
Listing	20.40		Defining	the	translator	function	type	(Hero.kt)
fun	main(args:	Array<String>)	{
## ...
## }
val	translator	=	{	utterance:	String	->
println(utterance.toLowerCase().capitalize())
## }
fun	makeProclamation()	=	"Greetings,	beast!"
translator	is	defined	like	many	of	the	function	types	that	you	saw	in
Chapter	5.	It	is	of	type	(String)	->	Unit.	What	will	this	function	type
look	like	in	Java?	Store	the	translator	instance	in	a	variable	in	Jhava.
Listing	20.41		Storing	a	function	type	in	a	variable	in	Java
(Jhava.java)
public	class	Jhava	{
## ...
public	static	void	main(String[]	args)	{
## ...
Spellbook.getSpellbookGreeting();
Function1<String,	Unit>	translator	=	Hero.getTranslator();
## }
## }
(You	will	need	to	import	kotlin.Unit;	be	sure	to	choose	the	option	from	the
Kotlin	standard	library.	You	will	also	need	to	import
kotlin.jvm.functions.Function1.)
This	function	type	is	of	type	Function1<String,	Unit>.	Function1	is
the	base	type	because	translator	has	exactly	one	parameter.	String	and

Unit	are	used	as	type	parameters	because	the	type	of	translator’s
parameter	is	String	and	it	returns	the	Kotlin	type	Unit.
There	are	23	of	these	Function	interfaces,	ranging	from	Function0	to
Function22.	Each	of	them	includes	one	function,	invoke.	invoke	is	used
to	call	a	function	type,	so	any	time	that	you	need	to	call	a	function	type,	you	call
invoke	on	it.	Call	translator	in	Jhava:
Listing	20.42		Calling	a	function	type	in	Java	(Jhava.java)
public	class	Jhava	{
## ...
public	static	void	main(String[]	args)	{
## ...
Function1<String,	Unit>	translator	=	Hero.getTranslator();
translator.invoke("TRUCE");
## }
## }
Run	Jhava.kt	to	confirm	that	Truce	is	printed	to	the	console.
While	function	types	are	useful	in	Kotlin,	be	mindful	of	how	they	are
represented	in	Java.	The	concise,	fluid	syntax	that	you	have	grown	fond	of	in
Kotlin	is	quite	different	when	called	from	Java.	If	your	code	is	visible	to	Java
classes	(e.g.,	as	a	part	of	an	API),	then	the	more	considerate	route	may	be	to
avoid	function	types.	But	if	you	are	comfortable	with	the	more	verbose	syntax,
then	Kotlin’s	function	types	are	indeed	available	to	you	in	Java.
Interoperability	between	Kotlin	and	Java	is	the	foundation	of	Kotlin’s	growth
trajectory.	It	provides	Kotlin	with	the	ability	to	leverage	existing	frameworks,
such	as	Android,	and	to	interface	with	legacy	codebases,	giving	you	a	path	to
gradually	introduce	Kotlin	in	your	projects.	Fortunately,	interoperation	between
Kotlin	and	Java	is	straightforward,	with	a	few	small	exceptions.	Writing	Java-
friendly	Kotlin	code	and	Kotlin-friendly	Java	code	is	useful	skill	that	will	pay
dividends	as	you	continue	your	Kotlin	journey.
In	the	next	chapter,	you	will	build	your	first	Android	app	with	Kotlin,	which	will
generate	the	starting	attributes	for	new	players	in	NyetHack.