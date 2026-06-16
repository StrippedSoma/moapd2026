

Table	of	Contents
## Introducing	Kotlin
## Why	Kotlin?
## Who	Is	This	Book	For?
How	to	Use	This	Book
For	the	More	Curious
## Challenges
Typographical	conventions
Using	an	eBook
## Looking	Forward
## 1.	Your	First	Kotlin	Application
Installing	IntelliJ	IDEA
## Your	First	Kotlin	Project
Creating	your	first	Kotlin	file
Running	your	Kotlin	file
The	Kotlin	REPL
For	the	More	Curious:	Why	Use	IntelliJ?
For	the	More	Curious:	Targeting	the	JVM
Challenge:	REPL	Arithmetic
- Variables,	Constants,	and	Types
## Types
Declaring	a	Variable
Kotlin’s	Built-In	Types
Read-Only	Variables
## Type	Inference
Compile-Time	Constants
## Inspecting	Kotlin	Bytecode
For	the	More	Curious:	Java	Primitive	Types	in	Kotlin
Challenge:	hasSteed
## Challenge:	The	Unicorn’s	Horn
## Challenge:	Magic	Mirror
## 3.	Conditionals
if/else	Statements
Adding	more	conditions
Nested	if/else	statements

More	elegant	conditionals
## Ranges
when	Expressions
## String	Templates
## Challenge:	Trying	Out	Some	Ranges
Challenge:	Enhancing	the	Aura
## Challenge:	Configurable	Status	Format
## 4.	Functions
Extracting	Code	to	Functions
Anatomy	of	a	Function
Function	header
Function	body
Function	scope
Calling	a	Function
Refactoring	to	Functions
## Writing	Your	Own	Functions
## Default	Arguments
Single-Expression	Functions
## Unit	Functions
## Named	Function	Arguments
For	the	More	Curious:	The	Nothing	Type
For	the	More	Curious:	File-Level	Functions	in	Java
For	the	More	Curious:	Function	Overloading
For	the	More	Curious:	Function	Names	in	Backticks
Challenge:	Single-Expression	Functions
## Challenge:	Fireball	Inebriation	Level
## Challenge:	Inebriation	Status
- Anonymous	Functions	and	the	Function	Type
## Anonymous	Functions
The	function	type
Implicit	returns
Function	arguments
The	it	keyword
Accepting	multiple	arguments
## Type	Inference	Support
Defining	a	Function	That	Accepts	a	Function
Shorthand	syntax
## Function	Inlining
## Function	References

Function	Type	as	Return	Type
For	the	More	Curious:	Kotlin’s	Lambdas	Are	Closures
For	the	More	Curious:	Lambdas	vs	Anonymous	Inner	Classes
- Null	Safety	and	Exceptions
## Nullability
## Kotlin’s	Explicit	Null	Type
Compile	Time	vs	Runtime
## Null	Safety
Option	one:	the	safe	call	operator
Option	two:	the	double-bang	operator
Option	three:	checking	whether	a	value	is	null	with	if
## Exceptions
Throwing	an	exception
Custom	exceptions
Handling	exceptions
## Preconditions
## Null:	What	Is	It	Good	For?
For	the	More	Curious:	Checked	vs	Unchecked	Exceptions
For	the	More	Curious:	How	Is	Nullability	Enforced?
## 7.	Strings
## Extracting	Substrings
substring
split
## String	Manipulation
Strings	are	immutable
## String	Comparison
For	the	More	Curious:	Unicode
For	the	More	Curious:	Traversing	a	String’s	Characters
Challenge:	Improving	DragonSpeak
## 8.	Numbers
## Numeric	Types
## Integers
## Decimal	Numbers
Converting	a	String	to	a	Numeric	Type
Converting	an	Int	to	a	Double
Formatting	a	Double
Converting	a	Double	to	an	Int
For	the	More	Curious:	Bit	Manipulation
## Challenge:	Remaining	Pints

Challenge:	Handling	a	Negative	Balance
## Challenge:	Dragoncoin
## 9.	Standard	Functions
apply
let
run
with
also
takeIf
takeUnless
## Using	Standard	Library	Functions
- Lists	and	Sets
## Lists
Accessing	a	list’s	elements
Changing	a	list’s	contents
## Iteration
Reading	a	File	into	a	List
## Destructuring
## Sets
Creating	a	set
Adding	elements	to	a	set
while	Loops
The	break	Expression
## Collection	Conversion
For	the	More	Curious:	Array	Types
For	the	More	Curious:	Read-Only	vs	Immutable
## Challenge:	Formatted	Tavern	Menu
## Challenge:	Advanced	Formatted	Tavern	Menu
## 11.	Maps
Creating	a	Map
## Accessing	Map	Values
Adding	Entries	to	a	Map
## Modifying	Map	Values
## Challenge:	Tavern	Bouncer
## 12.	Defining	Classes
Defining	a	Class
## Constructing	Instances
## Class	Functions
Visibility	and	Encapsulation

## Class	Properties
Property	getters	and	setters
Property	visibility
Computed	properties
Refactoring	NyetHack
## Using	Packages
For	the	More	Curious:	A	Closer	Look	at	var	and	val	Properties
For	the	More	Curious:	Guarding	Against	Race	Conditions
For	the	More	Curious:	Package	Private
## 13.	Initialization
## Constructors
Primary	constructors
Defining	properties	in	a	primary	constructor
Secondary	constructors
Default	arguments
Named	arguments
## Initializer	Blocks
## Property	Initialization
## Initialization	Order
## Delaying	Initialization
Late	initialization
Lazy	initialization
For	the	More	Curious:	Initialization	Gotchas
Challenge:	The	Riddle	of	Excalibur
## 14.	Inheritance
Defining	the	Room	Class
Creating	a	Subclass
## Type	Checking
## The	Kotlin	Type	Hierarchy
Type	casting
Smart	casting
For	the	More	Curious:	Any
## 15.	Objects
The	object	Keyword
Object	declarations
Object	expressions
Companion	objects
## Nested	Classes
## Data	Classes

toString
equals
copy
Destructuring	declarations
## Enumerated	Classes
## Operator	Overloading
Exploring	the	World	of	NyetHack
For	the	More	Curious:	Defining	Structural	Comparison
For	the	More	Curious:	Algebraic	Data	Types
Challenge:	“Quit”	Command
Challenge:	Implementing	a	World	Map
Challenge:	Ring	the	Bell
- Interfaces	and	Abstract	Classes
Defining	an	Interface
Implementing	an	Interface
## Default	Implementations
## Abstract	Classes
Combat	in	NyetHack
## 17.	Generics
## Defining	Generic	Types
## Generic	Functions
## Multiple	Generic	Type	Parameters
## Generic	Constraints
vararg	and	get
in	and	out
For	the	More	Curious:	The	reified	Keyword
## 18.	Extensions
## Defining	Extension	Functions
Defining	an	extension	on	a	superclass
## Generic	Extension	Functions
## Extension	Properties
Extensions	on	Nullable	Types
Extensions,	Under	the	Hood
Extracting	to	Extensions
Defining	an	Extensions	File
Renaming	an	Extension
Extensions	in	the	Kotlin	Standard	Library
For	the	More	Curious:	Function	Literals	with	Receivers
Challenge:	toDragonSpeak	Extension

## Challenge:	Frame	Extension
## 19.	Functional	Programming	Basics
## Function	Categories
## Transforms
## Filters
## Combines
## Why	Functional	Programming?
## Sequences
For	the	More	Curious:	Profiling
For	the	More	Curious:	Arrow.kt
Challenge:	Reversing	the	Values	in	a	Map
Challenge:	Applying	Functional	Programming	to	Tavern.kt
## Challenge:	Sliding	Window
## 20.	Java	Interoperability
Interoperating	with	a	Java	Class
Interoperability	and	Nullity
## Type	Mapping
Getters,	Setters,	and	Interoperability
## Beyond	Classes
Exceptions	and	Interoperability
Function	Types	in	Java
- Building	Your	First	Android	Application	with	Kotlin
## Android	Studio
Gradle	configuration
Project	organization
Defining	a	UI
Running	the	App	on	an	Emulator
Generating	a	Character
## The	Activity	Class
## Wiring	Up	Views
## Kotlin	Android	Extensions	Synthetic	Properties
Setting	a	Click	Listener
## Saved	Instance	State
Reading	from	the	saved	instance	state
Refactoring	to	an	Extension
For	the	More	Curious:	Android	KTX	and	Anko	Libraries
- Introduction	to	Coroutines
## Parsing	Character	Data
## Fetching	Live	Data

## The	Android	Main	Thread
## Enabling	Coroutines
Specifying	a	Coroutine	with	async
launch	vs	async/await
## Suspending	Functions
## Challenge:	Live	Data
## Challenge:	Minimum	Strength
## 23.	Afterword
Where	to	Go	from	Here
## Shameless	Plugs
## Thank	You
## Appendix:	More	Challenges
Leveling	Up	with	Exercism
## Glossary
## Index