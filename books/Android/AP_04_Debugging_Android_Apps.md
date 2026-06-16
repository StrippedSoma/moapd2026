

## 75
## 4
## Debugging Android Apps
In this chapter, you will find out what to do when apps get buggy. You will learn how to use LogCat,
Android Lint, and the debugger that comes with Android Studio.
To practice debugging, the first step is to break something. In
QuizActivity.java, comment out the
code in onCreate(Bundle) where you pull out mQuestionTextView.
Listing 4.1  Comment out a crucial line (
QuizActivity.java)
@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);

Log.d(TAG, "onCreate() called");
setContentView(R.layout.activity_quiz);
mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
// mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
mTrueButton = (Button)findViewById(R.id.true_button);
mTrueButton.setOnClickListener(new View.OnClickListener() {

## ...
## });


## ...
## }
Run GeoQuiz and see what happens.
Figure 4.1 shows the message that appears when your app crashes
and burns. Different versions of Android will have slightly different messages, but they all mean the
same thing.

## Chapter 4  Debugging Android Apps
## 76
Figure 4.1  GeoQuiz is about to E.X.P.L.O.D.E.
Of course, you know what is wrong with your app, but if you did not, it might help to look at your app
from a new perspective.
Exceptions and Stack Traces
Expand the Android DDMS tool window so that you can see what has happened. If you scroll up
and down in LogCat, you should eventually find an expanse of red, as shown in
Figure 4.2. This is a
standard AndroidRuntime exception report. If you are unable to see this exception in LogCat, you may
need to tweak LogCat’s filters. Select the No Filters option in the filter dropdown. You can also adjust
the Log Level to Error, which will show only the most severe log messages.

Diagnosing misbehaviors
## 77
Figure 4.2  Exception and stack trace in LogCat
The report tells you the top-level exception and its stack trace, then the exception that caused that
exception and its stack trace, and so on and so forth until it finds an exception with no cause.
In most of the code you will write, that last exception with no cause is the interesting one. Here the
exception without a cause is a java.lang.NullPointerException. The line just below this exception
is the first line in its stack trace. This line tells you the class and method where the exception occurred
as well as what file and line number the exception occurred on. Click the blue link, and Android Studio
will take you to that line in your source code.
The line to which you are taken is the first use of the mQuestionTextView variable, inside
updateQuestion(). The name NullPointerException gives you a hint to the problem: this variable
was not initialized.
Uncomment the line initializing mQuestionTextView to fix the bug.
When you encounter runtime exceptions, remember to look for the last exception in LogCat and the
first line in its stack trace that refers to code that you have written. That is where the problem occurs,
and it is the best place to start looking for answers.
If a crash occurs while a device is not plugged in, all is not lost. The device will store the latest lines
written to the log. The length and expiration of the stored log depends on the device, but you can
usually count on retrieving log results within 10 minutes. Just plug in the device and select your device
in the Devices view. LogCat will fill itself with the stored log.
Diagnosing misbehaviors
Problems with your apps will not always be crashes. In some cases, they will be misbehaviors. For
example, suppose that every time you pressed the Next button, nothing happened. That would be a
noncrashing, misbehaving bug.

## Chapter 4  Debugging Android Apps
## 78
In QuizActivity.java, make a change to the mNextButton listener to comment out the code that
increments mCurrentIndex.
Listing 4.2  Forget a critical line of code (QuizActivity.java)
@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);


## ...
mNextButton = (Button)findViewById(R.id.next_button);

mNextButton.setOnClickListener(new View.OnClickListener() {
@Override

public void onClick(View v) {
mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
// mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;

updateQuestion();
## }

## });

## ...
## }
Run GeoQuiz and press the
Next button. You should see no effect.
This bug is trickier than the last bug. It is not throwing an exception, so fixing the bug is not a simple
matter of making the exception go away. On top of that, this misbehavior could be caused in two
different ways: the index might not be changed, or updateQuestion() might not be called.
If you had no idea what was causing the problem, you would need to track down the culprit. In the
next few sections, you will see two ways to do this: diagnostic logging of a stack trace and using the
debugger to set a breakpoint.
Logging stack traces
## In
QuizActivity, add a log statement to updateQuestion().
## Listing 4.3
Exception for fun and profit (QuizActivity.java)
public class QuizActivity extends AppCompatActivity {
## ...


private void updateQuestion() {
Log.d(TAG, "Updating question text for question #" + mCurrentIndex,
new Exception());
int question = mQuestionBank[mCurrentIndex].getTextResId();

mQuestionTextView.setText(question);
## }
## The
Log.d(String, String, Throwable) version of Log.d logs the entire stack trace just like
with the AndroidRuntime exception you saw earlier. The stack trace will tell you where the call to
updateQuestion() was made.

Setting breakpoints
## 79
The exception that you pass to Log.d(...) does not have to be a thrown exception that you caught. You
can create a brand new Exception and pass it to the method without ever throwing it, and you will get
a report of where the exception was created.
Run GeoQuiz, press the Next button, and then check the output in LogCat (Figure 4.3).
Figure 4.3  The results
The top line in the stack trace is the line where you logged out the Exception. Two lines after that you
can see where updateQuestion() was called from within your onClick(...) implementation. Click
the link on this line, and you will be taken to where you commented out the line to increment your
question index. But do not get rid of the bug; you are going to use the debugger to find it again in a
moment.
Logging out stack traces is a powerful tool, but it is also a verbose one. Leave a bunch of these hanging
around, and soon LogCat will be an unmanageable mess. Also, a competitor might steal your ideas by
reading your stack traces to understand what your code is doing.
On the other hand, sometimes a stack trace showing what your code does is exactly what you need. If
you are seeking help with a problem at
http://stackoverflow.com or forums.bignerdranch.com, it
often helps to include a stack trace. You can copy and paste lines directly from LogCat.
Before continuing, delete the log statement in QuizActivity.java.
Listing 4.4  Farewell, old friend (
QuizActivity.java)
public class QuizActivity extends AppCompatActivity {

## ...

private void updateQuestion() {
Log.d(TAG, "Updating question text for question #" + mCurrentIndex,
new Exception());
int question = mQuestionBank[mCurrentIndex].getTextResId();
mQuestionTextView.setText(question);
## }
Setting breakpoints
Now you will use the debugger that comes with Android Studio to track down the same bug. You will
set a breakpoint on updateQuestion() to see whether it was called. A breakpoint pauses execution
before the line executes and allows you to examine line by line what happens next.

## Chapter 4  Debugging Android Apps
## 80
In QuizActivity.java, return to the updateQuestion() method. In the first line of this method, click
the gray bar in the lefthand margin. You should now see a red circle in the gray bar like the one shown
in Figure 4.4. This is a breakpoint.
Figure 4.4  A breakpoint
To engage the debugger and trigger your breakpoint, you need to debug your app instead of running
it. To debug your app, click the debug button (represented by a green bug), next to the run button.
Alternatively, you can navigate to Run → Debug 'app' in the menu bar. Your device will report that it is
waiting for the debugger to attach, and then it will proceed normally.
Once your app is up and running with the debugger attached, it will pause. Firing up GeoQuiz called
QuizActivity.onCreate(Bundle), which called updateQuestion(), which hit your breakpoint.
## In
Figure 4.5, you can see that this editor has opened QuizActivity.java and highlighted the line with
the breakpoint where execution has paused.
Figure 4.5  Stop right there!
The Debug tool window at the bottom of the screen is now visible and contains the Frames and
Variables views (Figure 4.6).

Setting breakpoints
## 81
Figure 4.6  The Debug tool window
You can use the arrow buttons at the top of the view to step through your program. You can see from
the stack trace that updateQuestion() has been called from inside onCreate(Bundle). But you are
interested in investigating the Next button’s behavior, so click the Resume Program button to continue
execution. Then press the Next button in GeoQuiz to see if your breakpoint is hit and execution is
stopped (it should be).
Now that you are stopped at an interesting point of execution, you can take a look around. The
Variables view allows you to examine the values of the objects in your program. You should see
the variables that you have created in QuizActivity as well as an additional value: this (the
QuizActivity instance itself).
You could expand the this variable to see all the variables declared in QuizActivity’s superclass,
Activity, in Activity’s superclass, in its super-superclass, and so on. But for now, focus on the
variables that you created.
You are only interested in one value:
mCurrentIndex. Scroll down in the variables view until you see
mCurrentIndex. Sure enough, it still has a value of 0.
This code looks perfectly fine. To continue your investigation, you need to step out of this method.
Click the Step Out button.
Check the editor view. It has now jumped you over to your mNextButton’s OnClickListener, right
after updateQuestion() was called. Pretty nifty.
You will want to fix this implementation, but before you make any changes to code, you should stop
debugging your app. You can do this in two ways: you can either stop the program, or you can simply
disconnect the debugger. To stop the program, click the Stop button shown in Figure 4.6. Usually it is
easier to simply disconnect the debugger. To do that, click the Close button also labeled in Figure 4.6.

## Chapter 4  Debugging Android Apps
## 82
Now return your OnClickListener to its former glory.
Listing 4.5  Returning to normalcy (QuizActivity.java)
@Override

protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
## ...

mNextButton = (Button)findViewById(R.id.next_button);
mNextButton.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {

// mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
updateQuestion();

## }
## });
## ...
## }
You have tried out two ways of tracking down a misbehaving line of code: stack trace logging and
setting a breakpoint in the debugger. Which is better? Each has its uses, and one or the other will
probably end up being your favorite.
Logging out stack traces has the advantage that you can see stack traces from multiple places in
one log. The downside is that to learn something new you have to add new log statements, rebuild,
deploy, and navigate through your app to see what happened. The debugger is more convenient. If you
run your app with the debugger attached, then you can set a breakpoint while the application is still
running and poke around to get information about multiple issues.
Using exception breakpoints
As if that were not enough choices, you can also use the debugger to catch exceptions. Return to
QuizActivity’s onCreate method and comment out a line of code that will cause the app to crash.
Listing 4.6  Making GeoQuiz crash again (QuizActivity.java)
@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);

## ...
mNextButton = (Button) findViewById(R.id.next_button);
// mNextButton = (Button) findViewById(R.id.next_button);

mNextButton.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;

updateQuestion();
## }

## });
## ...
## }

Using exception breakpoints
## 83
Now select Run → View Breakpoints... to pull up the breakpoints dialog, as shown in Figure 4.7.
Figure 4.7  Setting an exception breakpoint
This dialog shows all of your currently set breakpoints. Remove the breakpoint you added earlier by
highlighting it and clicking the Remove button (shaped like a minus sign).
The breakpoints dialog also allows you to set a breakpoint that is triggered when an exception is
thrown, wherever it might happen. You can limit it to only uncaught exceptions, or apply it to both
caught and uncaught exceptions.
Click the Add button (shaped like a plus sign) to add a new breakpoint. Choose Java Exception
Breakpoints in the drop-down list. You can now select the type of exception that you want to
catch. Type in
RuntimeException and choose RuntimeException (java.lang) from the suggestions.
RuntimeException is the superclass of NullPointerException, ClassCastException, and other
runtime problems, so it makes a nice catch-all.
Click Done and launch GeoQuiz with the debugger attached. This time, your debugger will jump right
to the line where the exception was thrown as soon as it happens. Exquisite.
Now, this is a fairly big hammer. If you leave this breakpoint on while debugging, you can expect
it to stop on some framework code or in other places you do not expect. So you may want to turn it
off when you are not using it. Go ahead and remove the breakpoint now by returning to Run
## → View
## Breakpoints....
Undo the change from Listing 4.6 to get GeoQuiz back to a good state.

## Chapter 4  Debugging Android Apps
## 84
Android-Specific Debugging
Most Android debugging is just like Java debugging. However, you will run into issues with Android-
specific parts, such as resources, that the Java compiler knows nothing about.
## Using Android Lint
This is where Android Lint comes in. Android Lint is a static analyzer for Android code. A static
analyzer is a program that examines your code to find defects without running it. Android Lint uses
its knowledge of the Android frameworks to look deeper into your code and find problems that the
compiler cannot. In most cases, Android Lint’s advice is worth taking.
## In
Chapter 6, you will see Android Lint warn you about compatibility problems. Android Lint can also
perform type-checking for objects that are defined in XML. Make the following casting mistake in
QuizActivity:
Listing 4.7  A simple mix-up (QuizActivity.java
## )
@Override

protected void onCreate(Bundle savedInstanceState) {

super.onCreate(savedInstanceState);
Log.d(TAG, "onCreate() called");

setContentView(R.layout.activity_quiz);
mQuestionTextView = (TextView)findViewById(R.id.question_text_view);

mTrueButton = (Button)findViewById(R.id.true_button);
mTrueButton = (Button)findViewById(R.id.question_text_view);


## ...
## }
Because you used the wrong resource ID, this code will attempt to cast a TextView as a Button at
runtime. This will cause an improper cast exception. The Java compiler sees no problem with this
code, but Android Lint will catch this error. You should see Lint immediately highlight this line of code
indicating that there is a problem.
You can manually run Lint to see all of the potential issues in your project, including those that are not
as serious as the one above. Select
Analyze → Inspect Code... from the menu bar. You will be asked
which parts of your project you would like to inspect. Choose
Whole project. Android Studio will now
run Lint as well as a few other static analyzers on your code.
Once the scan is complete, you will see a few categories of potential issues. Expand the Android Lint
category to see Lint’s information about your project (Figure 4.8).

Issues with the R class
## 85
Figure 4.8  Lint warnings
You can select an issue in this list to see more detailed information and its location in your project.
The Mismatched view type warning is the one that you created above. Go ahead and correct the cast in
onCreate(Bundle).
Listing 4.8  Fixing that simple mix-up (
QuizActivity.java)
@Override
protected void onCreate(Bundle savedInstanceState) {

super.onCreate(savedInstanceState);
Log.d(TAG, "onCreate() called");
setContentView(R.layout.activity_quiz);
mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
mTrueButton = (Button)findViewById(R.id.question_text_view);
mTrueButton = (Button)findViewById(R.id.true_button);


## ...
## }
Run GeoQuiz once more and confirm that the app is back to normal.
Issues with the R class
You are familiar with build errors that occur when you reference resources before adding them or
delete resources that other files refer to. Usually, resaving the files once the resource is added or the
references are removed will cause Android Studio to rebuild without any fuss.
Sometimes, however, these build errors will persist or appear seemingly out of nowhere. If this happens
to you, here are some things you can try:

## Chapter 4  Debugging Android Apps
## 86
Recheck the validity of the XML in your resource files
If your R.java file was not generated for the last build, you will see errors in your project
wherever you reference a resource. Often, this is caused by an XML typo in one of your XML
files. Layout XML is not always validated, so typos in these files may not be pointedly brought
to your attention. Finding the typo and resaving the file should cause R.java to regenerate.
Clean your project
Select Build → Clean Project. Android Studio will rebuild the project from scratch, which often
results in an error-free build. We can all use a deep clean every now and then.
Sync your project with Gradle
If you make changes to your
build.gradle file, you will need to sync those changes to update
your project’s build settings. Select Tools → Android → Sync Project with Gradle Files. Android
Studio will rebuild the project from scratch with the correct project settings, which can help to
resolve issues after changing your Gradle configuration.
## Run Android Lint
Pay close attention to the warnings from Android Lint. You will often discover unexpected
issues with this tool.
If you are still having problems with resources (or having different problems), give the error messages
and your layout files a fresh look. It is easy to miss mistakes in the heat of the moment. Check out any
Android Lint errors and warnings as well. A cool-headed reconsideration of the error messages may
turn up a bug or typo.
Finally, if you are stuck or having other issues with Android Studio, check the archives at http://
stackoverflow.com or visit the forum for this book at http://forums.bignerdranch.com.