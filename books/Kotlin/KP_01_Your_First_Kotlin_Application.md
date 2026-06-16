

## 1
## Your	First	Kotlin	Application
In	this	chapter	you	will	write	your	first	Kotlin	program,	using	IntelliJ	IDEA.
While	completing	this	programming	rite	of	passage,	you	will	familiarize
yourself	with	your	development	environment,	create	a	new	Kotlin	project,	write
and	run	Kotlin	code,	and	inspect	the	resulting	output.	The	project	you	create	in
this	chapter	will	serve	as	a	sandbox	to	easily	try	out	new	concepts	you	will
encounter	throughout	this	book.

Installing	IntelliJ	IDEA
IntelliJ	IDEA	is	an	integrated	development	environment	(IDE)	for	Kotlin	created
by	JetBrains	(which	also	created	the	Kotlin	language).	To	get	started,	download
the	IntelliJ	IDEA	Community	Edition	from	the	JetBrains	website	at
jetbrains.com/idea/download	(Figure	1.1).
Figure	1.1		Downloading	IntelliJ	IDEA	Community	Edition
Once	it	has	downloaded,	follow	the	installation	instructions	for	your	platform	as
described	on	the	JetBrains	installation	and	setup	page	at	jetbrains.com/
help/idea/install-and-set-up-product.html.
IntelliJ	IDEA,	called	IntelliJ	for	short,	helps	you	write	well-formed	Kotlin	code.
It	also	streamlines	the	development	process	with	built-in	tools	for	running,
debugging,	inspecting,	and	refactoring	your	code.	You	can	read	more	about	why
we	recommend	IntelliJ	for	writing	Kotlin	code	in	the	section	called	For	the	More
Curious:	Why	Use	IntelliJ?	near	the	end	of	this	chapter.

## Your	First	Kotlin	Project
Congratulations,	you	now	have	the	Kotlin	programming	language	and	a
powerful	development	environment	to	write	it	with.	Now	there	is	only	one	thing
left	to	do:	Learn	to	speak	Kotlin	fluently.	First	order	of	business	–	create	a	Kotlin
project.
Open	IntelliJ.	You	will	be	presented	with	the	Welcome	to	IntelliJ	IDEA	dialog
(Figure	1.2).
Figure	1.2		Welcome	dialog
(If	this	is	not	the	first	time	you	have	opened	IntelliJ	since	installing	it,	you	may
be	brought	directly	to	the	last	project	you	had	open.	To	get	back	to	the	welcome
dialog,	close	the	project	using	File	→	Close	Project.)
Click	Create	New	Project.	IntelliJ	will	display	the	New	Project	dialog,	as	shown	in
## Figure	1.3.

Figure	1.3		New	Project	dialog
In	the	New	Project	dialog,	select	Kotlin	on	the	left	and	Kotlin/JVM	on	the	right,	as
shown	in	Figure	1.4.

Figure	1.4		Creating	a	Kotlin/JVM	project
You	can	use	IntelliJ	to	write	code	in	languages	other	than	Kotlin,	including	Java,
Python,	Scala,	and	Groovy.	Selecting	Kotlin/JVM	tells	IntelliJ	you	intend	to	use
Kotlin.	More	specifically,	Kotlin/JVM	tells	IntelliJ	you	intend	to	write	Kotlin	code
that	targets,	or	runs	on,	the	Java	Virtual	Machine.	One	of	the	benefits	of	Kotlin	is
that	it	features	a	toolchain	that	allows	you	to	write	Kotlin	code	that	can	run	on
different	operating	systems	and	platforms.
(From	here	on,	we	will	refer	to	the	Java	Virtual	Machine	as	just	“JVM,”	as	it	is
commonly	called	in	the	Java	developer	community.	You	can	learn	more	about
targeting	the	JVM	in	the	section	called	For	the	More	Curious:	Targeting	the
JVM	near	the	end	of	this	chapter.)
Click	Next	in	the	New	Project	dialog.	IntelliJ	will	display	a	dialog	where	you	can
choose	settings	for	your	new	project	(Figure	1.5).	For	the	Project	name,	enter
“Sandbox.”	The	Project	location	field	will	auto-populate.	You	can	leave	the
location	as	is	or	select	a	new	location	by	pressing	the	...	button	to	the	right	of	the
field.	Select	a	Java	1.8	version	from	the	Project	SDK	dropdown	to	link	your	project
to	Java	Development	Kit	(JDK)	version	8.

Figure	1.5		Naming	the	project
Why	do	you	need	the	JDK	to	write	a	Kotlin	program?	The	JDK	gives	IntelliJ
access	to	the	JVM	and	to	Java	tools	that	are	necessary	for	converting	your	Kotlin
code	to	bytecode	(more	on	that	in	a	moment).	Technically,	any	version	6	or
greater	will	work.	But	our	experience,	as	of	this	writing,	is	that	JDK	8	works
most	seamlessly.
If	you	do	not	see	some	version	of	Java	1.8	listed	in	the	Project	SDK	dropdown,	this
means	you	have	not	yet	installed	JDK	8.	Do	so	now	before	proceeding:
Download	JDK	8	for	your	specific	platform	from	oracle.com/
technetwork/java/javase/downloads/jdk8-downloads-
2133151.html.	Install	the	JDK,	then	restart	IntelliJ.	Work	back	through	the
steps	outlined	to	this	point	to	create	a	new	project.
When	your	settings	dialog	looks	like	Figure	1.5,	click	Finish.
IntelliJ	will	generate	a	project	named	Sandbox	and	display	the	new	project	in	a
default	two-pane	view	(Figure	1.6).	On	disk,	IntelliJ	creates	a	folder	and	a	set	of
subfolders	and	project	files	in	the	location	specified	in	the	Project	location	field.

Figure	1.6		Default	two-pane	view
The	pane	on	the	left	shows	the	project	tool	window.	The	pane	on	the	right	is
currently	empty.	This	is	where	you	will	view	and	edit	the	contents	of	your	Kotlin
files	in	the	editor.	Turn	your	attention	to	the	project	tool	window	on	the	left.
Click	the	disclosure	arrow	to	the	left	of	the	project	name,	Sandbox.	It	will	expand
to	display	the	files	contained	in	the	project,	as	shown	in	Figure	1.7.
Figure	1.7		Project	view
A	project	includes	all	of	the	source	code	for	your	program,	along	with

information	about	dependencies	and	configurations.	A	project	can	be	broken
down	into	one	or	more	modules,	which	are	like	subprojects.	By	default,	a	new
project	has	one	module,	which	is	all	you	need	for	your	simple	first	project.
The	Sandbox.iml	file	contains	configuration	information	specific	to	your
single	module.	The	.idea	folder	contains	settings	files	for	the	entire	project	as
well	as	those	specific	to	your	interaction	with	the	project	in	the	IDE	(for
example,	which	files	you	have	open	in	the	editor).	Leave	these	auto-generated
files	as	they	are.
The	External	Libraries	entry	contains	information	about	libraries	the	project
depends	on.	If	you	expand	this	entry	you	will	see	that	IntelliJ	automatically
added	Java	1.8	and	KotlinJavaRuntime	as	dependencies	for	your	project.
(You	can	learn	more	about	IntelliJ	project	structure	on	the	JetBrains
documentation	website	at	jetbrains.org/intellij/sdk/docs/
basics/project_structure.html.)
The	src	folder	is	where	you	will	place	all	the	Kotlin	files	you	create	for	your
Sandbox	project.	And	with	that,	it	is	time	to	create	and	edit	your	first	Kotlin	file.
Creating	your	first	Kotlin	file
Right-click	on	the	src	folder	in	the	project	tool	window.	Select	New	and	then
Kotlin	File/Class	from	the	menu	that	appears	(Figure	1.8).

Figure	1.8		Creating	a	new	Kotlin	file
In	the	New	Kotlin	File/Class	dialog,	type	“Hello”	in	the	Name	field	and	leave	the	Kind
field	set	to	File	(Figure	1.9).
Figure	1.9		Naming	the	file
Click	OK.	IntelliJ	will	create	a	new	file	in	your	project,	src/Hello.kt,	and
display	the	contents	of	the	file	in	the	editor	on	the	righthand	side	of	the	IntelliJ
window	(Figure	1.10).	The	.kt	extension	indicates	that	the	file	contains	Kotlin,
just	like	the	.java	extension	is	used	for	Java	files	and	.py	for	Python	files.

Figure	1.10		Empty	Hello.kt	file	displays	in	editor
At	last,	you	are	ready	to	write	Kotlin	code.	Give	your	fingers	a	little	stretch	and
go	for	it.	Type	the	following	code	into	the	Hello.kt	editor.	(Remember	that
throughout	this	book,	code	you	are	to	enter	is	shown	in	bold.)
Listing	1.1		“Hello,	world!”	in	Kotlin	(Hello.kt)
fun	main(args:	Array<String>)	{
println("Hello,	world!")
## }
The	code	you	just	wrote	might	look	unfamiliar.	Do	not	fear	–	by	the	end	of	this
book,	reading	and	writing	Kotlin	will	feel	like	second	nature.	For	now,	it	is
enough	to	understand	the	code	at	a	high	level.
The	code	in	Listing	1.1	defines	a	new	function.	A	function	is	a	group	of
instructions	that	can	be	run	later.	You	will	learn	in	great	detail	how	to	define	and
work	with	functions	in	Chapter	4.
This	particular	function	–	the	main	function	–	has	a	special	meaning	in	Kotlin.
The	main	function	indicates	the	starting	place	for	your	program.	This	is	called
the	application	entry	point,	and	one	such	entry	point	must	be	defined	for
Sandbox	(or	any	program)	to	be	runnable.	Every	project	you	write	in	this	book
will	start	with	a	main	function.
Your	main	function	contains	one	instruction	(also	known	as	a	statement):
println("Hello,	world!").	println()	is	also	a	function	that	is	built	into	the

Kotlin	standard	library.	When	the	program	runs	and	println("Hello,
world!")	is	executed,	IntelliJ	will	print	the	contents	of	the	parentheses	(without
the	quotation	marks,	so	in	this	case	Hello,	world!)	to	the	screen.
Running	your	Kotlin	file
Shortly	after	you	finish	typing	the	code	in	Listing	1.1,	IntelliJ	will	display	a
green	,	known	as	the	“run	button,”	to	the	left	of	the	first	line	(Figure	1.11).	(If
the	icon	does	not	appear,	or	if	you	see	a	red	line	underneath	the	filename	in	the
tab	or	under	any	of	the	code	you	entered,	this	means	you	have	an	error	in	your
code.	Double-check	that	you	typed	the	code	exactly	as	shown	in	Listing	1.1.	On
the	other	hand,	if	you	see	a	red	and	blue	Kotlin	K,	this	flag	is	the	same	as	the	run
button.)
Figure	1.11		Run	button
It	is	time	for	your	program	to	come	to	life	and	greet	the	world.	Click	the	run
button.	Select	Run	'HelloKt'	from	the	menu	that	appears	(Figure	1.12).	This	tells
IntelliJ	you	want	to	see	your	program	in	action.
## Figure	1.12		Running	Hello.kt
When	you	run	your	program,	IntelliJ	executes	the	code	inside	of	the	curly	braces
({}),	one	line	at	a	time,	and	then	terminates	execution.	It	also	displays	two	new

tool	windows	at	the	bottom	of	the	IntelliJ	window	(Figure	1.13).
Figure	1.13		Run	and	event	log	tool	windows
On	the	left	is	the	run	tool	window,	also	known	as	the	console	(which	is	what	we
will	call	it	from	now	on).	It	displays	information	about	what	happened	as	IntelliJ
executed	your	program,	as	well	as	any	output	your	program	prints.	You	should
see	Hello,	world!	printed	in	your	console.	You	should	also	see	Process
finished	with	exit	code	0,	indicating	successful	completion.	This	line
appears	at	the	end	of	all	console	output	when	there	is	no	error;	we	will	not	show
it	in	console	results	from	now	on.
(macOS	users,	you	may	see	red	error	text	stating	that	there	is	an	issue	with
JavaLauncherHelper,	as	shown	in	Figure	1.13.	Do	not	worry	about	this.	It	is	an
unfortunate	side	effect	of	how	the	Java	Runtime	Environment	is	installed	on
macOS.	To	remove	it	would	require	a	lot	of	effort,	but	the	issue	does	no	harm	–
so	you	may	ignore	it	and	carry	on.)
On	the	right	is	the	event	log	tool	window,	which	displays	information	about	work
IntelliJ	did	to	get	your	program	ready	to	run.	We	will	not	mention	the	event	log
again,	because	you	get	much	more	interesting	output	in	the	console.	(For	the
same	reason,	do	not	be	concerned	if	the	event	log	never	opened	to	begin	with.)
You	can	close	it	with	the	hide	button	at	its	top	right,	which	looks	like	this:	.
Compilation	and	execution	of	Kotlin/JVM	code
A	lot	goes	on	in	the	short	time	between	when	you	select	the	run	button’s	Run
'HelloKt'	option	and	when	you	see	Hello,	World!	print	to	the	console.
First,	IntelliJ	compiles	the	Kotlin	code	using	the	kotlinc-jvm	compiler.	This
means	IntelliJ	translates	the	Kotlin	code	you	wrote	into	bytecode,	the	language
the	JVM	“speaks.”	If	kotlinc-jvm	has	any	problems	translating	your	Kotlin
code,	it	will	display	an	error	message	(or	messages)	giving	you	a	hint	about	how
to	fix	the	issues.	Otherwise,	if	the	compilation	process	goes	smoothly,	IntelliJ
moves	on	to	the	execution	phase.

In	the	execution	phase,	the	bytecode	that	was	generated	by	kotlinc-jvm	is
executed	on	the	JVM.	The	console	displays	any	output	from	your	program,	such
as	printing	the	text	you	specified	in	your	call	to	the	println()	function,	as	the
JVM	executes	the	instructions.
When	there	are	no	more	bytecode	instructions	to	execute,	the	JVM	terminates.
IntelliJ	shows	the	termination	status	in	the	console,	letting	you	know	whether
execution	finished	successfully	or	with	an	error	code.
You	will	not	need	a	comprehensive	understanding	of	the	Kotlin	compilation
process	to	work	through	this	book.	We	will,	however,	discuss	bytecode	in	more
detail	in	Chapter	2.

The	Kotlin	REPL
Sometimes	you	might	want	to	test	out	a	small	bit	of	Kotlin	code	to	see	what
happens	when	you	run	it,	similar	to	how	you	might	use	a	piece	of	scratch	paper
to	jot	down	steps	for	a	small	calculation.	This	is	especially	helpful	as	you	are
learning	the	Kotlin	language.	Luckily	for	you,	IntelliJ	provides	a	tool	for	quickly
testing	code	without	having	to	create	a	file.	This	tool	is	called	the	Kotlin	REPL.
We	will	explain	the	name	in	a	moment	–	for	now,	open	it	up	and	see	what	it	can
do.
In	IntelliJ,	open	the	Kotlin	REPL	tool	window	by	selecting	Tools	→	Kotlin	→	Kotlin
REPL	(Figure	1.14).
Figure	1.14		Opening	the	Kotlin	REPL	tool	window
IntelliJ	will	display	the	REPL	at	the	bottom	of	the	window	(Figure	1.15).

Figure	1.15		The	Kotlin	REPL	tool	window
You	can	type	code	into	the	REPL,	just	like	in	the	editor.	The	difference	is	that
you	can	have	it	evaluated	quickly,	without	compiling	an	entire	project.
Enter	the	following	code	in	the	REPL:
Listing	1.2		“Hello,	Kotlin!”	(REPL)
println("Hello,	Kotlin!")
Once	you	have	entered	the	text,	press	Command-Return	(Ctrl-Return)	to
evaluate	the	code	in	the	REPL.	After	a	moment,	you	will	see	the	resulting	output
underneath,	which	should	read	Hello,	Kotlin!	(Figure	1.16).
Figure	1.16		Evaluating	the	code
REPL	is	short	for	“read,	evaluate,	print,	loop.”	You	type	in	a	piece	of	code	at	the
prompt	and	submit	it	by	clicking	the	green	run	button	on	the	REPL’s	left	side	or
by	pressing	Command-Return	(Ctrl-Return).	The	REPL	then	reads	the	code,
evaluates	(runs)	the	code,	and	prints	out	the	resulting	value	or	side	effect.
Once	the	REPL	finishes	executing,	it	returns	control	back	to	you	and	the	process
loop	starts	all	over.
Your	Kotlin	journey	has	begun!	You	accomplished	a	great	deal	in	this	chapter,
laying	the	foundation	for	your	growing	knowledge	of	Kotlin	programming.	In
the	next	chapter,	you	will	begin	to	dig	into	the	language’s	details	by	learning

about	how	you	can	use	variables,	constants,	and	types	to	represent	data.

For	the	More	Curious:	Why	Use	IntelliJ?
Kotlin	can	be	written	using	any	plain	text	editor.	However,	we	recommend	using
IntelliJ,	especially	as	you	are	learning.	Just	as	text	editing	software	that	offers
spell	check	and	grammar	check	makes	writing	a	well-formed	prose	essay	easier,
IntelliJ	makes	writing	well-formed	Kotlin	easier.	IntelliJ	helps	you:
write	syntactically	and	semantically	correct	code	with	features	like
syntax	highlighting,	context-sensitive	suggestions,	and	automatic	code
completion
run	and	debug	your	code	with	features	like	debug	breakpoints	and	real-
time	code	stepping	when	your	application	is	running
restructure	existing	code	with	refactoring	shortcuts	(like	rename	and
extract	constant)	and	code	formatting	to	clean	up	indentation	and
spacing
Also,	since	Kotlin	was	created	by	JetBrains,	the	integration	between	IntelliJ	and
Kotlin	is	carefully	designed	–	often	leading	to	a	delightful	editing	experience.	As
an	added	bonus,	IntelliJ	is	the	basis	of	Android	Studio,	so	shortcuts	and	tools
you	learn	here	will	translate	to	using	Android	Studio,	if	that	is	your	thing.

For	the	More	Curious:	Targeting	the	JVM
The	JVM	is	a	piece	of	software	that	knows	how	to	execute	a	set	of	instructions,
called	bytecode.	“Targeting	the	JVM”	means	compiling,	or	translating,	your
Kotlin	source	code	into	Java	bytecode,	with	the	intention	of	running	that
bytecode	on	the	JVM	(Figure	1.17).
Figure	1.17		Compilation	and	execution	flow
Each	platform,	such	as	Windows	or	macOS,	has	its	own	instruction	set.	The
JVM	acts	as	a	bridge	between	the	bytecode	and	the	different	hardware	and
software	environments	the	JVM	runs	on,	reading	a	piece	of	bytecode	and	calling
the	corresponding	platform-specific	instruction(s)	that	map	to	that	bytecode.
Therefore,	there	are	different	versions	of	the	JVM	for	different	platforms.	This	is
what	allows	Kotlin	developers	to	write	platform-independent	code	that	can	be
written	one	time	and	then	compiled	into	bytecode	and	executed	on	different
devices	regardless	of	their	operating	systems.
Since	Kotlin	can	be	converted	to	bytecode	that	the	JVM	can	execute,	it	is
considered	a	JVM	language.	Java	is	perhaps	the	most	well-known	JVM
language,	because	it	was	the	first.	However,	other	JVM	languages,	such	as	Scala
and	Kotlin,	have	emerged	to	address	some	shortcomings	of	Java	from	the
developer	perspective.
Kotlin	is	not	limited	to	the	JVM,	however.	At	the	time	of	this	writing,	Kotlin	can
also	be	compiled	into	JavaScript	or	even	into	native	binaries	that	run	directly	on
a	given	platform	–	such	as	Windows,	Linux,	and	macOS	–	negating	the	need	for

a	virtual	machine	layer.

Challenge:	REPL	Arithmetic
Many	of	the	chapters	in	this	book	end	with	one	or	more	challenges.	The
challenges	are	for	you	to	work	through	on	your	own	to	deepen	your
understanding	of	Kotlin	and	get	a	little	extra	experience.
Use	the	REPL	to	explore	how	arithmetic	operators	in	Kotlin	work:	+,	-,	*,	/,	and
%.	For	example,	type	(9+12)*2	into	the	REPL.	Does	the	output	match	what	you
expected?
If	you	wish	to	dive	deeper,	look	over	the	mathematical	functions	available	in	the
Kotlin	standard	library	at	kotlinlang.org/api/latest/jvm/
stdlib/kotlin.math/index.html	and	try	them	out	in	the	REPL.	For
example,	try	min(94,	-99),	which	will	tell	you	the	minimum	of	the	two
numbers	provided	in	parentheses.