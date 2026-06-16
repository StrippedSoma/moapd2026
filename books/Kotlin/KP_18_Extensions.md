

## 18
## Extensions
Extensions	allow	you	to	add	functionality	to	a	type	without	directly	modifying
the	type’s	definition.	You	can	use	extensions	with	your	own	types	and	also	types
you	do	not	control,	like	List,	String,	and	other	types	from	the	Kotlin
standard	library.
Extensions	are	an	alternative	to	the	sharing	behavior	of	inheritance.	They	are	a
good	fit	for	adding	functionality	to	a	type	when	you	do	not	control	the	definition
of	the	class	or	when	a	class	is	not	marked	with	the	open	keyword,	making	it
ineligible	for	subclassing.
The	Kotlin	standard	library	frequently	uses	extensions.	For	example,	the
standard	functions	that	you	learned	about	in	Chapter	9	are	defined	as	extensions,
and	you	will	look	at	several	examples	of	their	declarations	in	this	chapter.
For	this	chapter,	you	will	be	working	first	in	the	Sandbox	project	and	then
applying	what	you	learned	to	streamline	NyetHack’s	codebase.	Begin	by
opening	the	Sandbox	project	and	creating	a	new	file	called	Extensions.kt.

## Defining	Extension	Functions
Your	first	extension	allows	you	to	add	a	specified	amount	of	enthusiasm	to	any
String.	Define	it	in	Extensions.kt:
Listing	18.1		Adding	an	extension	to	String	(Extensions.kt)
fun	String.addEnthusiasm(amount:	Int	=	1)	=	this	+	"!".repeat(amount)
Extension	functions	are	defined	in	the	same	way	as	other	functions,	with	one
major	difference:	When	you	specify	an	extension	function,	you	also	specify	the
type	the	extension	adds	functionality	to,	known	as	the	receiver	type.	(Recall	from
Chapter	9	that	the	subject	of	an	extension	is	called	a	“receiver.”)	For	the
addEnthusiasm	function,	the	receiver	type	you	specified	is	String.
addEnthusiasm’s	function	body	is	a	single-expression	function	that	returns	a
new	string:	the	contents	of	this	plus	1	or	more	exclamation	points,	based	on	the
argument	passed	to	amount	(1,	if	the	default	vaue	is	used).	The	this	keyword
refers	to	the	receiver	instance	the	extension	function	was	called	on	(a	String
instance,	in	this	case).
Now,	you	can	invoke	the	addEnthusiasm	function	on	any	instance	of
String.	Try	out	the	new	extension	function	by	defining	a	string	in	a	new	main
function,	calling	the	addEnthusiasm	extension	function	on	it,	and	printing
the	result:
Listing	18.2		Calling	the	new	extension	on	a	String	receiver
instance	(Extensions.kt)
fun	String.addEnthusiasm(amount:	Int	=	1)	=	this	+	"!".repeat(amount)
fun	main(args:	Array<String>)	{
println("Madrigal	has	left	the	building".addEnthusiasm())
## }
Run	Extensions.kt	to	see	that	your	extension	function	adds	an	exclamation
point	to	the	string,	as	expected.
Could	you	have	subclassed	String	to	add	this	functionality	to	String
instances?	In	IntelliJ,	view	String’s	source	definition	by	pressing	the	Shift	key
twice	to	open	the	Search	Everywhere	dialog	and	then	searching	for	the	“String.kt”
file.	Its	header	looks	like	this:
public	class	String	:	Comparable<String>,	CharSequence	{
## ...
## }

Since	there	is	no	open	keyword	on	the	String	class	definition,	there	is	no	way
to	subclass	String	to	add	functionality	through	inheritance.	As	we	said	earlier,
extensions	are	a	good	option	when	you	want	to	add	functionality	to	a	class	you
do	not	control	or	that	is	ineligible	for	subclassing.
Defining	an	extension	on	a	superclass
Extensions	do	not	rely	on	inheritance,	but	they	can	be	combined	with	inheritance
to	expand	their	scope.	Try	it	in	Extensions.kt:	Define	an	extension	on	the
Any	type	called	easyPrint.	Because	it	is	defined	on	Any,	it	will	be	directly
callable	on	all	types.	In	main,	change	the	call	to	the	println	function	to
instead	call	your	new	easyPrint	extension	function	directly	on	the	String:
Listing	18.3		Extending	Any	(Extensions.kt)
fun	String.addEnthusiasm(amount:	Int	=	1)	=	this	+	"!".repeat(amount)
fun	Any.easyPrint()	=	println(this)
fun	main(args:	Array<String>)	{
println("Madrigal	has	left	the	building".addEnthusiasm()).easyPrint()
## }
Run	Extensions.kt	and	confirm	that	the	output	has	not	changed.
Since	you	added	the	extension	for	the	Any	type,	it	is	also	available	for	use	with
other	subtypes.	Call	the	extension	on	an	Int	after	the	String:
Listing	18.4		easyPrint	is	available	on	all	subtypes
(Extensions.kt)
fun	String.addEnthusiasm(amount:	Int	=	1)	=	this	+	"!".repeat(amount)
fun	Any.easyPrint()	=	println(this)
fun	main(args:	Array<String>)	{
"Madrigal	has	left	the	building".addEnthusiasm().easyPrint()
42.easyPrint()
## }

## Generic	Extension	Functions
What	if	you	wanted	to	print	the	string	"Madrigal	has	left	the	building"
both	before	and	after	calling	addEnthusiasm	on	it?
First,	you	would	need	to	make	the	easyPrint	function	chainable.	You	have
seen	chained	function	calls	before;	functions	can	be	chained	if	they	return	their
receiver	or	another	object	that	subsequent	functions	can	be	called	on.
Update	easyPrint	to	make	it	chainable:
Listing	18.5		Making	easyPrint	chainable	(Extensions.kt)
fun	String.addEnthusiasm(amount:	Int	=	1)	=	this	+	"!".repeat(amount)
fun	Any.easyPrint()=	println(this):	Any	{
println(this)
return	this
## }
## ...
Now,	try	calling	the	easyPrint	function	two	times:	once	before
addEnthusiasm	and	once	afterward:
Listing	18.6		Calling	easyPrint	twice	(Extensions.kt)
fun	String.addEnthusiasm(amount:	Int	=	1)	=	this	+	"!".repeat(amount)
fun	Any.easyPrint():	Any	{
println(this)
return	this
## }
fun	main(args:	Array<String>)	{
"Madrigal	has	left	the	building".easyPrint().addEnthusiasm().easyPrint()
42.easyPrint()
## }
The	code	does	not	compile.	The	first	easyPrint	call	was	allowed,	but
addEnthusiasm	was	not.	Take	a	look	at	the	type	information	to	understand
why:	Click	on	the	first	easyPrint	and	press	Control-Shift-P	(Ctrl-P),	then,
from	the	list	of	expressions	that	pops	up,	select	the	first	("Madrigal	has	left
the	building".easyPrint()")	(Figure	18.1):
Figure	18.1		Chainable,	but	wrong	type	for	adding	enthusiasm
The	easyPrint	function	returns	the	String	it	was	called	on,	but	uses	Any	to
represent	it.	addEnthusiasm	is	only	available	on	String,	so	it	cannot	be

called	on	the	return	from	easyPrint.
To	solve	this,	you	can	make	the	extension	generic.	Update	the	easyPrint
extension	function	to	use	a	generic	type	as	its	receiver	instead	of	Any:
Listing	18.7		Making	easyPrint	generic	(Extensions.kt)
fun	String.addEnthusiasm(amount:	Int	=	1)	=	this	+	"!".repeat(amount)
fun	<T>	AnyT.easyPrint():	AnyT	{
println(this)
return	this
## }
## ...
Now	that	the	extension	uses	the	generic	type	parameter	T	for	the	receiver	and
returns	T	instead	of	Any,	the	particular	type	information	for	the	receiver	is
passed	forward	in	the	chain	of	calls	(Figure	18.2):
Figure	18.2		Chained	function	returning	a	usable	type
Try	running	Extensions.kt	again.	This	time	you	will	see	the	string	printed
twice:
Madrigal	has	left	the	building
Madrigal	has	left	the	building!
## 42
Your	new	generic	extension	function	works	with	any	type,	and	it	also	maintains
the	type	information.	Extensions	used	with	generic	types	allow	you	to	write
functions	that	have	a	wide	reach	across	a	number	of	different	types	in	your
program.
Extensions	on	generic	types	appear	throughout	the	Kotlin	standard	library.	For
example,	take	a	look	at	the	definition	for	the	let	function:
public	inline	fun	<T,	R>	T.let(block:	(T)	->	R):	R	{
return	block(this)
## }
let	is	defined	as	a	generic	extension	function,	allowing	it	to	work	with	all
types.	It	accepts	a	lambda	that	takes	the	receiver	as	its	argument	(T)	and	returns	R
–	some	new	type	that	is	whatever	the	lambda	returns.
Notice	that	the	inline	keyword	you	learned	about	in	Chapter	5	is	also	used	here.
The	same	guidance	from	before	applies:	Inlining	the	extension	function	if	it
accepts	a	lambda	reduces	the	memory	overhead	required.

## Extension	Properties
In	addition	to	adding	functionality	to	a	type	by	specifying	extension	functions,
you	can	also	define	extension	properties.	Add	another	extension	to	String	in
Extensions.kt,	this	time	an	extension	property	that	counts	a	string’s
vowels:
Listing	18.8		Adding	an	extension	property	(Extensions.kt)
val	String.numVowels
get()	=	count	{	"aeiouy".contains(it)	}
fun	String.addEnthusiasm(amount:	Int	=	1)	=	this	+	"!".repeat(amount)
## ...
Try	out	your	new	extension	property	by	printing	the	numVowels	extension	in
main:
Listing	18.9		Using	an	extension	property	(Extensions.kt)
val	String.numVowels
get()	=	count	{	"aeiouy".contains(it)	}
fun	String.addEnthusiasm(amount:	Int	=	1)	=	this	+	"!".repeat(amount)
fun	<T>	T.easyPrint():	T	{
println(this)
return	this
## }
fun	main(args:	Array<String>)	{
"Madrigal	has	left	the	building".easyPrint().addEnthusiasm().easyPrint()
42.easyPrint()
"How	many	vowels?".numVowels.easyPrint()
## }
Run	Extensions.kt.	You	will	see	the	new	numVowels	property	printed:
Madrigal	has	left	the	building
Madrigal	has	left	the	building!
## 42
## 5
Recall	from	Chapter	12	that	class	properties	have	a	backing	field	where	their
data	is	stored	(except	for	computed	properties)	and	that	they	are	automatically
assigned	getters	and,	if	needed,	setters.	Like	computed	properties,	extension
properties	do	not	have	a	backing	field	–	they	must	define	get	and/or	set
operators	that	compute	the	value	that	should	be	returned	by	the	property	to	be
valid.
For	example,	the	following	would	not	be	allowed:
var	String.preferredCharacters	=	10
error:	extension	property	cannot	be	initialized	because	it	has	no	backing	field
Instead,	you	could	define	a	valid	preferredCharacters	extension	property

by	defining	a	getter	for	the	preferredCharacters	val.

Extensions	on	Nullable	Types
An	extension	can	also	be	defined	for	use	with	a	nullable	type.	Defining	an
extension	on	a	nullable	type	allows	you	to	deal	with	the	possibility	of	the	value
being	null	within	the	body	of	the	extension	function,	rather	than	at	the	call	site.
Add	an	extension	for	nullable	Strings	in	Extensions.kt	and	test	it	out	in
the	main	function:
Listing	18.10		Adding	an	extension	on	a	nullable	type
(Extensions.kt)
## ...
infix	fun	String?.printWithDefault(default:	String)	=	print(this	?:	default)
fun	main(args:	Array<String>)	{
"Madrigal	has	left	the	building".easyPrint().addEnthusiasm().easyPrint()
42.easyPrint()
"How	many	vowels?".numVowels.easyPrint()
val	nullableString:	String?	=	null
nullableString	printWithDefault	"Default	string"
## }
The	infix	keyword,	available	for	both	extension	and	class	functions	that	have	a
single	argument,	allows	for	the	cleaner	syntax	you	see	in	the	function	call.	If	a
function	is	defined	with	infix,	you	can	omit	the	dot	between	the	receiver	and
the	function	call	as	well	as	the	parentheses	around	the	argument.
Here	are	versions	of	the	call	to	printWithDefault	with	and	without	infix:
null	printWithDefault	"Default	string"			//	With	infix
null.printWithDefault("Default	string")		//	Without	infix
Making	a	function	an	infix	allows	you	to	clean	up	usage	of	the	function	and	can
be	a	nice	refinement	when	you	have	an	extension	or	class	function	that	expects	a
single	argument.
Run	Extensions.kt.	You	will	see	that	Default	string	is	printed.	Since	the
value	of	nullableString	was	null,	printWithDefault	coalesced	the
value	to	use	the	default	you	provided.

Extensions,	Under	the	Hood
An	extension	function	or	property	is	called	in	the	same	style	as	a	normal
function	or	property,	but	it	is	not	directly	defined	on	the	class	it	extends,	nor	does
it	rely	on	inheritance	for	adding	functionality.	So	how	are	extensions
implemented	on	the	JVM?
To	inspect	how	an	extension	works	on	the	JVM,	you	can	look	at	the	bytecode
that	the	Kotlin	compiler	generates	when	you	define	one	and	translate	it	back	to
## Java.
Open	the	Kotlin	bytecode	tool	window,	either	by	selecting	Tools	→	Kotlin	→	Kotlin
Bytecode	or	by	searching	for	“show	Kotlin	bytecode”	in	the	Search	Everywhere
dialog	(accessed	by	pressing	the	Shift	key	twice).
In	the	Kotlin	bytecode	window,	click	the	Decompile	button	at	the	top	left	to	open	a
new	tab	with	the	Java	representation	of	the	bytecode	that	was	generated	from
Extensions.kt.	Find	the	equivalent	bytecode	for	the	addEnthusiasm
extension	that	you	defined	for	String:
public	static	final	String	addEnthusiasm(@NotNull	String	$receiver,	int	amount)	{
Intrinsics.checkParameterIsNotNull($receiver,	"$receiver");
return	$receiver	+	StringsKt.repeat((CharSequence)"!",	amount);
## }
In	the	Java	version	of	the	bytecode,	the	Kotlin	extension	is	a	static	method	that
accepts	what	it	extends	as	an	argument	when	you	compile	it	for	the	JVM.	The
compiler	substitutes	a	call	of	the	addEnthusiasm	function.

Extracting	to	Extensions
Next,	you	will	apply	what	you	have	learned	to	refine	NyetHack.	Open	the
project	and	the	Tavern.kt	file.
Tavern.kt	contains	duplicate	chains	of	logic	called	on	several	collections:
shuffled().first().
## ...
(0..9).forEach	{
val	first	=	patronList.shuffled().first()
val	last	=	lastName.shuffled().first()
## }
uniquePatrons.forEach	{
patronGold[it]	=	6.0
## }
var	orderCount	=	0
while	(orderCount	<=	9)	{
placeOrder(uniquePatrons.shuffled().first(),
menuList.shuffled().first())
orderCount++
## ...
This	duplication	indicates	an	opportunity	to	extract	the	duplicate	logic	to	a
reusable	extension.
Define	a	new	extension	called	random	at	the	top	of	Tavern.kt:
Listing	18.11		Adding	a	private	random	extension	(Tavern.kt)
## ...
val	patronGold	=	mutableMapOf<String,	Double>()
private	fun	<T>	Iterable<T>.random():	T	=	this.shuffled().first()
fun	main(args:	Array<String>)	{
## ...
## }
## ...
The	combination	of	shuffled	and	first	is	called	on	both	lists	(like
menuList)	and	a	set	–	uniquePatrons.	To	make	your	extension	available
on	both	types,	you	define	their	supertype	as	the	receiver	type:	Iterable.
Now,	replace	the	old	calls	to	shuffled().first()	with	a	call	to	the
extension	function	random.	(You	can	press	Command-R	[Ctrl-R]	to	open	the
search	and	replace	bar	to	make	this	easier.	However,	be	sure	not	to	replace	the
call	to	shuffled().first()	in	your	extension	definition.)
Listing	18.12		Using	the	random	extension	(Tavern.kt)
## ...
private	fun	<T>	Iterable<T>.random():	T	=	this.shuffled().first()
fun	main(args:	Array<String>)	{
## ...

(0..9).forEach	{
val	first	=	patronList.shuffled().first()random()
val	last	=	lastName.shuffled().first()random()
## }
uniquePatrons.forEach	{
patronGold[it]	=	6.0
## }
var	orderCount	=	0
while	(orderCount	<=	9)	{
placeOrder(uniquePatrons.shuffled().first()random(),
menuList.shuffled().first()random())
orderCount++
## }
displayPatronBalances()
## }
## ...

Defining	an	Extensions	File
Your	random	extension	is	marked	with	the	private	visibility	modifier:
private	fun	<T>	Iterable<T>.random():	T	=	this.shuffled().first()
Marking	an	extension	as	private	prohibits	use	of	the	extension	outside	of	the	file
it	is	defined	in.	Right	now,	the	extension	you	defined	is	only	used	in
Tavern.kt,	so	it	makes	sense	to	mark	it	private	to	restrict	access.	The	rule	of
thumb	is	the	same	for	extensions	as	it	is	for	functions:	If	the	extension	will	not
be	used	elsewhere,	mark	it	private.
Having	said	that,	you	also	defined	your	random	extension	so	that	it	would	work
with	any	Iterable.	Are	there	other	places	in	your	code,	outside	of
Tavern.kt,	where	you	could	put	it	to	use?	As	it	turns	out,	there	is	one.
Take	a	look	in	Player.kt	–	you	will	see	the	same	randomization	code	used	to
select	a	Player’s	hometown:
## ...
private	fun	selectHometown()	=	File("data/towns.txt")
.readText()
## .split("\n")
## .shuffled()
## .first()
## ...
It	would	be	nice	to	be	able	to	use	your	random	extension	there,	as	well.
Since	the	random	extension	will	be	used	across	several	files,	making	it	private
is	no	longer	appropriate	–	and	neither	is	leaving	it	in	Tavern.kt.	A	good	place
for	extensions	to	be	used	across	multiple	files	is	within	their	own	file	–	and,	in
fact,	their	own	package.
Control-click	(right-click)	the	com.bignerdranch.nyethack	package	and
choose	New	→	Package.	Name	the	package	extensions	and	add	a	file	to	it
called	IterableExt.kt	(Figure	18.3).	The	naming	convention	for	files	that
contain	only	extensions	is	typically	the	type	the	extension	applies	to	plus	-Ext.

Figure	18.3		Adding	an	extensions	package	and	file
Move	the	random	extension	to	IterableExt.kt,	removing	the	old	listing
in	Tavern.kt.	Delete	the	private	keyword	from	the	extension	when	you
move	it	to	IterableExt.kt.
Listing	18.13		Removing	the	random	extension	from	Tavern.kt
(Tavern.kt)
## ...
private	fun	<T>	Iterable<T>.random():	T	=	this.shuffled().first()
fun	main(args:	Array<String>)	{
## ...
## }
## ...
Listing	18.14		Adding	the	random	extension	to	IterableExt.kt
(IterableExt.kt)
package	com.bignerdranch.nyethack.extensions
fun	<T>	Iterable<T>.random():	T	=	this.shuffled().first()
Now	that	you	have	moved	the	extension	to	its	own	file	and	made	it	public,	you
can	use	it	in	Tavern.kt	and	Player.kt.	But	you	might	notice	that
Tavern.kt	is	reporting	errors.	When	an	extension	is	defined	in	a	separate
package,	you	must	import	the	extension	in	each	file	that	uses	it.	Make	sure	that
the	import	statement	for	the	random	extension	is	present	at	the	top	of	both
Tavern.kt	and	Player.kt:
import	com.bignerdranch.nyethack.extensions.random

Now,	within	Player.kt,	update	the	selectHometown	function	to	use	the
random	extension	function	in	place	of	the	old	randomization	code:
Listing	18.15		Using	random	in	selectHometown	(Player.kt)
## ...
private	fun	selectHometown()	=	File("data/towns.txt")
.readText()
## .split("\n")
## .random()
## .shuffled()
## .first()
## ...

Renaming	an	Extension
Occasionally,	you	may	want	to	use	an	extension	or	an	imported	class	whose
name	is	less	than	ideal	in	some	way.	Perhaps	it	is	a	difficult-to-remember
acronym,	or	maybe	you	already	have	a	class	with	the	same	name	in	your	file.	If
you	want	the	function	of	an	imported	function	or	class	but	not	its	name,	you	can
use	the	as	operator	to	assign	a	different	name	to	be	used	within	the	file.
For	example,	in	Tavern.kt	you	could	change	the	name	of	the	imported
random	function	to	randomizer:
Listing	18.16		The	as	operator	(Tavern.kt)
import	com.bignerdranch.nyethack.extensions.random	as	randomizer
## ...
private	fun	selectHometown()	=	File("data/towns.txt")
.readText()
## .split("\n")
## .random()
## .randomizer()
## ...
And	with	that	done,	it	is	time	to	say	farewell	to	NyetHack.	Congratulations!	You
have	accomplished	quite	a	lot	in	your	journey:	You	laid	a	foundation	of
conditionals	and	functions,	defined	your	own	classes	so	that	you	could	represent
objects	in	the	world,	built	a	game	loop	to	take	input	from	the	player,	and	even
built	out	a	world	to	explore	with	monsters	to	defeat.
And	all	the	while	you	leveraged	Kotlin’s	language	features	to	take	advantage	of
the	object-oriented	programming	paradigm.

Extensions	in	the	Kotlin	Standard	Library
A	large	portion	of	the	Kotlin	standard	library’s	functionality	is	defined	via
extension	functions	and	extension	properties.
For	example,	take	a	look	at	the	source	code	file	Strings.kt	(note:	Strings,
not	String),	by	pressing	the	Shift	key	twice	to	open	the	Search	Everywhere	dialog
and	entering	“Strings.kt”:
public	inline	fun	CharSequence.trim(predicate:	(Char)	->	Boolean):	CharSequence	{
var	startIndex	=	0
var	endIndex	=	length	-	1
var	startFound	=	false
while	(startIndex	<=	endIndex)	{
val	index	=	if	(!startFound)	startIndex	else	endIndex
val	match	=	predicate(this[index])
if	(!startFound)	{
if	(!match)
startFound	=	true
else
startIndex	+=	1
## }
else	{
if	(!match)
break
else
endIndex	-=	1
## }
## }
return	subSequence(startIndex,	endIndex	+	1)
## }
Browse	through	this	standard	library	file,	and	you	will	see	that	it	consists	of
extensions	to	the	String	type.	The	excerpt	above,	for	example,	defines	an
extension	function	trim	that	is	used	to	remove	characters	from	a	string.
Standard	library	files	that	contain	extensions	to	a	type	are	often	named	in	this
way,	with	an	-s	appended	to	the	type	name.	If	you	look	through	the	standard
library	files,	you	will	notice	other	files	matching	this	same	naming	convention:
Sequences.kt,	Ranges.kt,	and	Maps.kt	are	just	some	of	the	files	that
add	functionality	to	the	standard	library	through	extensions	to	their
corresponding	type.
Heavy	use	of	extension	functions	to	define	core	API	functionality	is	one	of	the
ways	that	the	Kotlin	standard	library	keeps	such	a	small	footprint	(~930k)	but
packs	in	so	many	features.	Extensions	use	space	efficiently	because	they	can
provide	a	feature	for	many	types	with	one	definition.
In	this	chapter,	you	have	learned	how	extensions	provide	an	alternative	to
sharing	behavior	with	inheritance.	In	the	next	chapter	you	will	delve	into	the
fascinating	world	of	functional	programming.

For	the	More	Curious:	Function	Literals	with
## Receivers
It	is	possible	to	use	function	literals	with	the	extension	syntax	to	powerful	effect.
To	understand	what	is	meant	by	“function	literals	with	receivers,”	take	a	look	at
the	definition	for	apply,	a	function	you	met	in	Chapter	9:
public	inline	fun	<T>	T.apply(block:	T.()	->	Unit):	T	{
block()
return	this
## }
Remember	what	apply	enables	you	to	do:	set	up	properties	of	a	particular
receiver	instance	within	a	lambda	that	you	pass	as	an	argument.	For	example:
val	menuFile	=	File("menu-file.txt").apply	{
setReadable(true)
setWritable(true)
setExecutable(false)
## }
This	allows	you	to	avoid	explicitly	calling	each	function	on	a	menuFile
variable.	Instead,	you	can	call	them	implicitly	within	a	lambda.	The	bit	of	magic
that	apply	provides	is	accomplished	by	defining	a	function	literal	with	a
receiver.
Looking	again	at	the	definition	for	apply,	check	out	how	the	function
parameter	called	block	is	specified:
public	inline	fun	<T>	T.apply(block:	T.()	->	Unit):	T	{
block()
return	this
## }
Not	only	is	the	block	function	parameter	a	lambda,	it	also	is	specified	as	an
extension	to	generic	type	T:	T.()	->	Unit.	This	is	what	allows	the	lambda	that
you	define	to	also	have	access	to	the	receiver	instance’s	properties	and	functions
implicitly.
Specified	as	an	extension,	the	lambda’s	receiver	is	also	the	instance	that	apply
is	called	on	–	granting	access	to	the	receiver	instance’s	functions	and	properties
within	the	body	lambda.
Using	this	style,	it	is	possible	to	write	what	are	known	as	“domain-specific
languages”	–	an	API	style	that	exposes	functions	and	features	of	a	receiver
context	you	configure	using	lambda	expressions	that	you	define	to	access	them.
For	example,	the	Exposed	framework	from	JetBrains	(github.com/
JetBrains/Exposed)	makes	extensive	use	of	the	DSL	style	for	its	API	to

allow	you	to	define	SQL	queries.
You	might	add	a	function	to	NyetHack	that	uses	this	same	style,	allowing	a	room
to	be	configured	with	a	pit	goblin.	(Feel	free	to	add	this	to	your	NyetHack
project	as	an	experiment.)
fun	Room.configurePitGoblin(block:	Room.(Goblin)	->	Goblin):	Room	{
val	goblin	=	block(Goblin("Pit	Goblin",	description	=	"An	Evil	Pit	Goblin"))
monster	=	goblin
return	this
## }
This	extension	to	Room	accepts	a	lambda	that	has	Room	as	its	receiver.	The
result	is	that	the	properties	of	Room	are	available	within	the	lambda	that	you
define,	so	the	goblin	can	be	configured	using	the	Room	receiver’s	properties:
currentRoom.configurePitGoblin	{	goblin	->
goblin.healthPoints	=	dangerLevel	*	3
goblin
## }
(Note	that	you	would	need	to	change	the	visibility	of	dangerLevel	on	Room
to	actually	allow	access	to	the	dangerLevel	property.)

Challenge:	toDragonSpeak	Extension
For	this	challenge,	revisit	Tavern.kt.	Convert	the	toDragonSpeak
function	that	you	wrote	to	be	a	private	extension	function	within	Tavern.kt.

## Challenge:	Frame	Extension
The	following	is	a	small	program	that	allows	a	string	of	an	arbitrary	size	to	be
displayed	in	a	beautiful	ASCII	frame	that	is	suitable	for	printing	and	hanging	on
any	wall:
fun	frame(name:	String,	padding:	Int,	formatChar:	String	=	"*"):	String	{
val	greeting	=	"$name!"
val	middle	=	formatChar.padEnd(padding)
## .plus(greeting)
.plus(formatChar.padStart(padding))
val	end	=	(0	until	middle.length).joinToString("")	{	formatChar	}
return	"$end\n$middle\n$end"
## }
For	this	challenge,	you	will	apply	what	you	have	learned	about	extensions.	Try
refactoring	the	frame	function	as	an	extension	that	is	available	for	use	with	any
String.	An	example	of	calling	the	new	version	would	look	like	this:
print("Welcome,	Madrigal".frame(5))
## ******************************
## *					Welcome,	Madrigal						*
## ******************************