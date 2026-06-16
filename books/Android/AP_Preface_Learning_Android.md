

xvii
## Learning Android
As a beginning Android programmer, you face a steep learning curve. Learning Android is like moving
to a foreign city. Even if you speak the language, it will not feel like home at first. Everyone around
you seems to understand things that you are missing. Things you already knew turn out to be dead
wrong in this new context.
Android has a culture. That culture speaks Java, but knowing Java is not enough. Getting your head
around Android requires learning many new ideas and techniques. It helps to have a guide through
unfamiliar territory.
That’s where we come in. At Big Nerd Ranch, we believe that to be an Android programmer, you
must:
•write Android applications
## •understand
what you are writing
This guide will help you do both. We have trained hundreds of professional Android programmers
using it. We lead you through writing several Android applications, introducing concepts and
techniques as needed. When there are rough spots, when some things are tricky or obscure, you will
face them head on, and we will do our best to explain why things are the way they are.
This approach allows you to put what you have learned into practice in a working app right away rather
than learning a lot of theory and then having to figure out how to apply it all later. You will come away
with the experience and understanding you need to get going as an Android developer.
## Prerequisites
To use this book, you need to be familiar with Java, including classes and objects, interfaces, listeners,
packages, inner classes, anonymous inner classes, and generic classes.
If these ideas do not ring a bell, you will be in the weeds by page 2. Start instead with an introductory
Java book and return to this book afterward. There are many excellent introductory books available, so
you can choose one based on your programming experience and learning style.
If you are comfortable with object-oriented programming concepts, but your Java is a little rusty, you
will probably be OK. We will provide some brief reminders about Java specifics (like interfaces and
anonymous inner classes). Keep a Java reference handy in case you need more support as you go
through the book.
What's New in the Second Edition?
This second edition shows how to use the Android Studio integrated development environment to write
practical applications for Android 5.1 (Lollipop) that are backwards-compatible through Android 4.1
(Jelly Bean). It includes updated coverage of the fundamentals of Android programming as well as new
Lollipop tools like the toolbar and material design. It also covers new tools from the support libraries,
like
RecyclerView and Google Play Services, plus some key standard library tools, like SoundPool,
animations, and assets.

## Learning Android
xviii
How to Use This Book
This book is not a reference book. Its goal is to get you over the initial hump to where you can get
the most out of the reference and recipe books available. It is based on our five-day class at Big Nerd
Ranch. As such, it is meant to be worked through from the beginning. Chapters build on each other and
skipping around is unproductive.
In our classes, students work through these materials, but they also benefit from the right environment
– a dedicated classroom, good food and comfortable board, a group of motivated peers, and an
instructor to answer questions.
As a reader, you want your environment to be similar. That means getting a good night’s rest and
finding a quiet place to work. These things can help, too:
- Start a reading group with your friends or coworkers.
- Arrange to have blocks of focused time to work on chapters.
- Participate in the forum for this book at
http://forums.bignerdranch.com.
- Find someone who knows Android to help you out.
How This Book is Organized
As you work through this book, you will write eight Android apps. A couple are very simple and take
only a chapter to create. Others are more complex. The longest app spans 11 chapters. All are designed
to teach you important concepts and techniques and give you direct experience using them.
GeoQuizIn your first app, you will explore the fundamentals of Android projects,
activities, layouts, and explicit intents.
CriminalIntentThe largest app in the book, CriminalIntent lets you keep a record of your
colleagues’ lapses around the office. You will learn to use fragments, master-
detail interfaces, list-backed interfaces, menus, the camera, implicit intents,
and more.
BeatBoxIntimidate your foes with this app while you learn more about fragments,
media playback, themes, and drawables.
NerdLauncherBuilding this custom launcher will give you insight into the intent system and
tasks.
PhotoGalleryA Flickr client that downloads and displays photos from Flickr’s public
feed, this app will take you through services, multithreading, accessing web
services, and more.

## Challenges
xix
DragAndDrawIn this simple drawing app, you will learn about handling touch events and
creating custom views.
SunsetIn this toy app, you will create a beautiful representation of a sunset over open
water while learning about animations.
LocatrThis app lets you query Flickr for pictures around your current location and
display them on a map. In it, you will learn how to use location services and
maps.
## Challenges
Most chapters have a section at the end with exercises for you to work through. This is your
opportunity to use what you have learned, explore the documentation, and do some problem solving on
your own.
We strongly recommend that you do the challenges. Going off the beaten path and finding your way
will solidify your learning and give you confidence with your own projects.
If you get lost, you can always visit http://forums.bignerdranch.com for some assistance.
Are you more curious?
There are also sections at the ends of chapters labeled
“For the More Curious.” These sections offer
deeper explanations or additional information about topics presented in the chapter. The information in
these sections is not absolutely essential, but we hope you will find it interesting and useful.
## Code Style
There are two areas where our choices differ from what you might see elsewhere in the Android
community:
We use anonymous inner classes for listeners.
This is mostly a matter of opinion. We find it makes for cleaner code in the applications in this
book because it puts the listener’s method implementations right where you want to see them. In
high-performance contexts or large applications, anonymous inner classes may cause problems,
but for most circumstances they work fine.
After we introduce fragments in
Chapter 7, we use them for all user interfaces.
Fragments are not an absolutely necessary tool but we find that, when used correctly, they are a
valuable tool in any Android developer’s toolkit. Once you get comfortable with fragments, they
are not that difficult to work with. Fragments have clear advantages over activities that make
them worth the effort, including flexibility in building and presenting your user interfaces.

## Learning Android
xx
## Typographical Conventions
To make this book easier to read, certain items appear in certain fonts. Variables, constants, and types
appear in a fixed-width font. Class names, interface names, and method names appear in a bold, fixed-
width font.
All code and XML listings are in a fixed-width font. Code or XML that you need to type in is always
bold. Code or XML that should be deleted is struck through. For example, in the following method
implementation, you are deleting the call to makeText(...) and adding the call to checkAnswer(true).
@Override
public void onClick(View v) {

Toast.makeText(QuizActivity.this, R.string.incorrect_toast,
Toast.LENGTH_SHORT).show();
checkAnswer(true);
## }
## Android Versions
This book teaches Android development for all widely used versions of Android. As of this writing,
that is Android 4.1 (Jelly Bean) - Android 5.1 (Lollipop). While there is a small amount of market-
share on older versions of Android, we find that for most developers the amount of effort required to
support those versions is not worth the reward. For more info on the support of versions of Android
earlier than 4.1 (in particular, Android 2.2 and Android 2.3), see the first edition of this book.
As Android releases new versions, the techniques you learn in this book will continue to work thanks
to Android’s backwards compatibility support (see Chapter 6 for details). We will keep track of
changes at http://forums.bignerdranch.com and offer notes on using this book with the latest
version.