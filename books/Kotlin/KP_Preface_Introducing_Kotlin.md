

## Introducing	Kotlin
In	2011,	JetBrains	announced	the	development	of	the	Kotlin	programming
language,	an	alternative	to	writing	code	in	languages	like	Java	or	Scala	to	run	on
the	Java	Virtual	Machine.	Six	years	later,	Google	announced	that	Kotlin	would
be	an	officially	supported	development	path	for	the	Android	operating	system.
Kotlin’s	scope	quickly	grew	from	a	language	with	a	bright	future	into	the
language	powering	applications	on	the	world’s	foremost	mobile	operating
system.	Today,	large	companies	like	Google,	Uber,	Netflix,	Capital	One,
Amazon,	and	more	have	embraced	Kotlin	for	its	many	advantages,	including	its
concise	syntax,	modern	features,	and	seamless	interoperability	with	legacy	Java
code.

## Why	Kotlin?
To	understand	the	appeal	of	Kotlin,	you	first	need	to	understand	the	role	of	Java
in	the	modern	software	development	landscape.	The	two	languages	are	closely
tied,	because	Kotlin	code	is	most	often	written	for	the	Java	Virtual	Machine.
Java	is	a	robust	and	time-tested	language	and	has	been	one	of	the	most
commonly	written	languages	in	production	codebases	for	years.	However,	since
Java	was	released	in	1995,	much	has	been	learned	about	what	makes	for	a	good
programming	language.	Java	is	missing	the	many	advancements	that	developers
working	with	more	modern	languages	enjoy.
Kotlin	benefits	from	the	learning	gained	as	some	design	decisions	made	in	Java
(and	other	languages,	like	Scala)	have	aged	poorly.	It	has	evolved	beyond	what
was	possible	with	older	languages	and	has	corrected	what	was	painful	about
them.	You	will	learn	more	in	the	coming	chapters	about	how	Kotlin	improves	on
Java	and	offers	a	more	reliable	development	experience.
And	Kotlin	is	not	just	a	better	language	to	write	code	to	run	on	the	Java	Virtual
Machine.	It	is	a	multiplatform	language	that	aims	to	be	general	purpose:	Kotlin
can	be	used	to	write	native	macOS	and	Windows	applications,	JavaScript
applications,	and,	of	course,	Android	applications.	Platform	independence	means
that	Kotlin	has	a	wide	variety	of	uses.

## Who	Is	This	Book	For?
We	have	written	this	book	for	developers	of	all	kinds:	experienced	Android
developers	who	want	modern	features	beyond	what	Java	offers,	server-side
developers	interested	in	learning	about	Kotlin’s	features,	and	newer	developers
looking	to	venture	into	a	high-performance	compiled	language.
Android	support	might	be	why	you	are	reading	this	book,	but	the	book	is	not
limited	to	Kotlin	programming	for	Android.	In	fact,	except	in	one	advanced
chapter,	Chapter	21,	all	the	Kotlin	code	in	this	book	is	agnostic	to	the	Android
framework.	That	said,	if	you	are	interested	in	using	Kotlin	for	Android
application	development,	this	book	shows	off	some	common	patterns	that	make
writing	Android	apps	a	breeze	in	Kotlin.
Although	Kotlin	has	been	influenced	by	a	number	of	other	languages,	you	do	not
need	to	know	the	ins	and	outs	of	any	other	language	to	learn	Kotlin.	From	time
to	time,	we	will	discuss	the	Java	code	equivalent	for	Kotlin	code	you	have
written.	If	you	have	Java	experience,	this	will	help	you	understand	the
relationship	between	the	two	languages.	If	you	do	not	know	Java,	seeing	how
another	language	tackles	the	same	problems	can	help	you	grasp	the	principles
that	have	shaped	Kotlin’s	development.

How	to	Use	This	Book
This	book	is	not	a	reference	guide.	Our	goal	is	to	guide	you	through	the	most
important	parts	of	the	Kotlin	programming	language.	You	will	be	working
through	example	projects,	building	knowledge	as	you	progress.	To	get	the	most
out	of	this	book,	we	recommend	that	you	type	out	the	examples	in	the	book	as
you	read	along.	Working	through	the	projects	will	help	build	muscle	memory
and	will	give	you	something	to	carry	on	from	one	chapter	to	the	next.
Also,	each	chapter	builds	on	the	topics	presented	in	the	last,	so	we	recommend
that	you	do	not	jump	around.	Even	if	you	feel	that	you	are	familiar	with	a	topic
in	other	languages,	we	suggest	that	you	read	straight	through	–	Kotlin	handles
many	problems	in	unique	ways.	You	will	begin	with	introductory	topics	like
variables	and	lists,	work	your	way	through	object-oriented	and	functional
programming	techniques,	and	understand	along	the	way	what	makes	Kotlin	such
a	powerful	language.	By	the	end	of	the	book,	you	will	have	built	your
knowledge	of	Kotlin	from	that	of	a	beginner	to	a	more	advanced	developer.
Having	said	that,	do	take	your	time:	Branch	out,	use	the	Kotlin	reference	at
kotlinlang.org/docs/reference	to	follow	up	on	anything	that	piqued
your	curiosity,	and	experiment.
For	the	More	Curious
Most	of	the	chapters	in	this	book	have	a	section	or	two	titled	“For	the	More
Curious.”	Many	of	these	sections	illuminate	the	underlying	mechanisms	of	the
Kotlin	language.	The	examples	in	the	chapters	do	not	depend	on	the	information
in	these	sections,	but	they	provide	additional	information	that	you	may	find
interesting	or	helpful.
## Challenges
Most	chapters	end	with	one	or	more	challenges.	These	are	additional	problems
to	solve	that	are	designed	to	further	your	understanding	of	Kotlin.	We	encourage
you	to	give	them	a	try	to	enhance	your	Kotlin	mastery.
Typographical	conventions

As	you	build	the	projects	in	this	book,	we	will	guide	you	by	introducing	a	topic
and	then	showing	how	to	apply	your	new-found	knowledge.	For	clarity,	we	stick
to	the	following	typographical	conventions.
Variables,	values,	and	types	are	shown	with	fixed-width	font.	Class,	function,
and	interface	names	are	given	bold	font.
All	code	listings	are	shown	in	fixed-width	font.	If	you	are	to	type	some	code	in	a
code	listing,	that	code	is	denoted	in	bold.	If	you	are	to	delete	some	code	in	a
code	listing,	that	code	is	struck	through.	In	the	following	example,	you	are	being
instructed	to	delete	the	line	defining	variable	y	and	to	add	a	variable	called	z:
var	x	=	"Python"
var	y	=	"Java"
var	z	=	"Kotlin"
Kotlin	is	a	relatively	young	language,	so	many	coding	conventions	are	still	being
figured	out.	Over	time,	you	will	likely	develop	your	own	style,	but	we	tend	to
adhere	to	JetBrains’	and	Google’s	Kotlin	style	guides:
JetBrains’	coding	conventions:	kotlinlang.org/docs/
reference/coding-conventions.html
Google’s	style	guide,	including	conventions	for	Android	code	and
interoperability:	android.github.io/kotlin-guides/
style.html
Using	an	eBook
If	you	are	reading	this	book	on	an	eReader,	we	want	to	point	out	that	reading	the
code	may	be	tricky	at	times.	Longer	lines	of	code	may	wrap	to	a	second	line,
depending	on	your	selected	font	size.
The	longest	lines	of	code	in	this	book	are	86	monospace	characters,	like	this	one.
println(playerCreateMessage(nameIsLong("Polarcubis,	the	Supreme	Master	of	NyetHack")))
You	can	play	with	your	eReader’s	settings	to	find	the	best	for	viewing	long	code
lines.
If	you	are	reading	on	an	iPad	with	iBooks,	we	recommend	you	go	to	the	Settings
app,	select	iBooks,	and	set	Full	Justification	OFF	and	Auto-hyphenation	OFF.
When	you	get	to	the	point	where	you	are	actually	typing	in	code,	we	suggest
opening	the	book	on	your	PC	or	Mac	in	Adobe	Digital	Editions.	(Adobe	Digital
Editions	is	a	free	eReader	application	you	can	download	from
www.adobe.com/products/digitaleditions.)	Make	the	application

window	large	enough	so	that	you	can	see	the	code	with	no	wrapping	lines.	You
will	also	be	able	to	see	the	figures	in	full	detail.

## Looking	Forward
Take	your	time	with	the	examples	in	this	book.	Once	you	get	the	hang	of
Kotlin’s	syntax,	we	think	that	you	will	find	the	development	process	to	be	clear,
pragmatic,	and	fluid.	Until	then,	keep	at	it;	learning	a	new	language	can	be	quite
rewarding.