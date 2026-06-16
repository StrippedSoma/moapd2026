

## 429
## 24
Loopers, Handlers, and
HandlerThread
Now that you have downloaded and parsed JSON from Flickr, your next task is to download and
display images. In this chapter, you will learn how to use Looper, Handler, and HandlerThread to
dynamically download and display photos in PhotoGallery.
Preparing RecyclerView to Display Images
The current PhotoHolder in PhotoGalleryFragment simply provides TextViews for the
RecyclerView’s GridLayoutManager to display. Each TextView displays the caption of a GalleryItem.
To display photos, update PhotoHolder to provide ImageViews instead. Eventually, each ImageView
will display a photo downloaded from the mUrl of a GalleryItem.
Start by creating a new layout file for your gallery items called
gallery_item.xml. This layout will
consist of a single ImageView (Figure 24.1).
Figure 24.1  Gallery item layout (res/layout/gallery_item.xml)
These ImageViews will be managed by RecyclerView’s GridLayoutManager, which means that
their width will vary. Their height, on the other hand, will remain fixed. To make the most of the
ImageView’s space, you have set its scaleType to centerCrop. This setting centers the image and then
scales it up so that the smaller dimension is equal to the view and the larger one is cropped on both
sides.
Next, update
PhotoHolder to hold an ImageView instead of a TextView. Replace bindGalleryItem()
with a method to set the ImageView’s Drawable.

Chapter 24  Loopers, Handlers, and HandlerThread
## 430
## Listing 24.1  Updating
PhotoHolder (PhotoGalleryFragment.java)
## ...
private class PhotoHolder extends RecyclerView.ViewHolder {

private TextView mTitleTextView ImageView mItemImageView;
public PhotoHolder(View itemView) {
super(itemView);

mTitleTextView = (TextView) itemView;
mItemImageView = (ImageView) itemView
.findViewById(R.id.fragment_photo_gallery_image_view);
## }
public void bindGalleryItem(GalleryItem item) {
mTitleTextView.setText(item.toString());
## }


public void bindDrawable(Drawable drawable) {
mItemImageView.setImageDrawable(drawable);

## }
## }
## ...
Previously the PhotoHolder constructor assumed it would be passed a TextView directly. The
new version instead expects a view hierarchy that contains an ImageView with the resource ID
## R.id.fragment_photo_gallery_image_view.
Update PhotoAdapter’s onCreateViewHolder() to inflate the gallery_item file you created and pass
it to PhotoHolder’s constructor.
Listing 24.2  Updating PhotoAdapter’s onCreateViewHolder()
(PhotoGalleryFragment.java)
public class PhotoGalleryFragment extends Fragment {

## ...


private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
## ...
@Override

public PhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
TextView textView = new TextView(getActivity());
return new PhotoHolder(textView);
LayoutInflater inflater = LayoutInflater.from(getActivity());
View view = inflater.inflate(R.layout.gallery_item, viewGroup, false);

return new PhotoHolder(view);
## }

## ...

## }


## ...
## }

Preparing RecyclerView to Display Images
## 431
Next, you will need a placeholder image for each ImageView to display until you download an image
to replace it. Find bill_up_close.jpg in the solutions file and put it in res/drawable. (See the section
called “Adding an Icon” in Chapter 2 for more on the solutions.)
Update PhotoAdapter’s onBindViewHolder() to set the placeholder image as the ImageView’s
## Drawable.
Listing 24.3  Binding default image (PhotoGalleryFragment.java)
public class PhotoGalleryFragment extends Fragment {

## ...

private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
## ...


@Override

public void onBindViewHolder(PhotoHolder photoHolder, int position) {
GalleryItem galleryItem = mGalleryItems.get(position);
photoHolder.bindGalleryItem(galleryItem);
Drawable placeholder = getResources().getDrawable(R.drawable.bill_up_close);
photoHolder.bindDrawable(placeholder);

## }

## ...

## }


## ...
## }
Run PhotoGallery, and you should see an array of close-up Bills, as in Figure 24.2.
## Figure 24.2  A Billsplosion

Chapter 24  Loopers, Handlers, and HandlerThread
## 432
Downloading Lots of Small Things
Currently, PhotoGallery’s networking works like this: PhotoGalleryFragment executes an AsyncTask
that retrieves the JSON from Flickr on a background thread and parses the JSON into an array of
GalleryItems. Each GalleryItem now has a URL where a thumbnail-size photo lives.
The next step is to go and get those thumbnails. You might think that this additional networking code
could simply be added to FetchItemsTask’s doInBackground() method. Your GalleryItem array has
100 URLs to download from. You would download the images one after another until you had all 100.
When onPostExecute(...) executed, they would be displayed en masse in the RecyclerView.
However, downloading the thumbnails all at once causes two problems. The first is that it could take
a while, and the UI would not be updated until the downloading was complete. On a slow connection,
users would be staring at a wall of Bills for a long time.
The second problem is the cost of having to store the entire set of images. One hundred thumbnails will
fit into memory easily. But what if it were 1000? What if you wanted to implement infinite scrolling?
Eventually, you would run out of space.
Given these problems, real-world apps often download images only when they need to be displayed
on screen. Downloading on demand puts the responsibility on the
RecyclerView and its adapter. The
adapter triggers the image downloading as part of its onBindViewHolder(...) implementation.
AsyncTask is the easiest way to get a background thread, but it is ill-suited for repetitive and long-
running work. (You can read why in the For the More Curious section at the end of this chapter.)
Instead of using an AsyncTask, you are going to create a dedicated background thread. This is the most
common way to implement downloading on an as-needed basis.
Communicating with the Main Thread
Your dedicated thread will download photos, but how will it work with the RecyclerView’s adapter to
display them when it cannot directly access the main thread?
Think back to the shoe store with two Flashes. Background Flash has wrapped up his phone call to
the distributor. He needs to tell Main Flash that the shoes are back in stock. If Main Flash is busy,
Background Flash cannot do this right away. He would have to wait by the register to catch Main Flash
at a spare moment. This would work, but it is not very efficient.
The better solution is to give each Flash an inbox. Background Flash writes a message about the shoes
being in stock and puts it on top of Main Flash’s inbox. Main Flash does the same thing when he wants
to tell Background Flash that the stock of shoes has run out.
The inbox idea turns out to be really handy. The Flash may have something that needs to be done soon,
but not right at the moment. In that case, he can put a message in his own inbox and then handle it
when he has time.
In Android, the inbox that threads use is called a
message queue. A thread that works by using a
message queue is called a message loop; it loops again and again looking for new messages on its
queue (Figure 24.3).

Assembling a Background Thread
## 433
Figure 24.3  Flash dance
A message loop consists of a thread and a looper. The Looper is the object that manages a thread’s
message queue.
The main thread is a message loop and has a looper. Everything your main thread does is performed by
its looper, which grabs messages off of its message queue and performs the task they specify.
You are going to create a background thread that is also a message loop. You will use a class called
HandlerThread
that prepares a Looper for you.
Assembling a Background Thread
Create a new class called
ThumbnailDownloader that extends HandlerThread. Then give it a
constructor and a stub implementation of a method called
queueThumbnail() (Listing 24.4).
Listing 24.4  Initial thread code (
ThumbnailDownloader.java)
public class ThumbnailDownloader<T> extends HandlerThread {
private static final String TAG = "ThumbnailDownloader";


public ThumbnailDownloader() {
super(TAG);
## }

public void queueThumbnail(T target, String url) {

Log.i(TAG, "Got a URL: " + url);

## }
## }
Notice you gave the class a single generic argument, <T>. Your ThumbnailDownloader’s user,
PhotoGalleryFragment in this case, will need to use some object to identify each download and to

Chapter 24  Loopers, Handlers, and HandlerThread
## 434
determine which UI element to update with the image once it is downloaded. Rather than locking the
user into a specific type of object as the identifier, using a generic makes the implementation more
flexible.
The queueThumbnail() method expects an object of type T to use as the identifier for the download
and a String containing the URL to download. This is the method you will have GalleryItemAdapter
call in its onBindViewHolder(...) implementation.
Open PhotoGalleryFragment.java. Give PhotoGalleryFragment a ThumbnailDownloader member
variable. In onCreate(...), create the thread and start it. Override onDestroy() to quit the thread.
Listing 24.5  Creating ThumbnailDownloader (PhotoGalleryFragment.java)
public class PhotoGalleryFragment extends Fragment {

private static final String TAG = "PhotoGalleryFragment";
private RecyclerView mPhotoRecyclerView;
private List<GalleryItem> mItems = new ArrayList<>();
private ThumbnailDownloader<PhotoHolder> mThumbnailDownloader;


## ...


@Override
public void onCreate(Bundle savedInstanceState) {

super.onCreate(savedInstanceState);
setRetainInstance(true);

new FetchItemsTask().execute();

mThumbnailDownloader = new ThumbnailDownloader<>();

mThumbnailDownloader.start();
mThumbnailDownloader.getLooper();

Log.i(TAG, "Background thread started");
## }


@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,

Bundle savedInstanceState) {
## ...
## }

@Override

public void onDestroy() {
super.onDestroy();
mThumbnailDownloader.quit();
Log.i(TAG, "Background thread destroyed");
## }

## ...
## }
You can specify any type for ThumbnailDownloader’s generic argument. However, recall that this
argument specifies the type of the object that will be used as the identifier for your download. In this
case, the PhotoHolder makes for a convenient identifier as it is also the target where the downloaded
images will eventually go.
A couple of safety notes. One: notice that you call getLooper() after calling start() on your
ThumbnailDownloader (you will learn more about the Looper in a moment). This is a way to ensure

Messages and Message Handlers
## 435
that the thread’s guts are ready before proceeding, to obviate a potential (though rarely occurring) race
condition. Until you call getLooper(), there is no guarantee that onLooperPrepared() has been called,
so there is a possibility that calls to queueThumbnail(...) will fail due to a null Handler.
Safety note number two: you call quit() to terminate the thread inside onDestroy()
. This is critical. If
you do not quit your HandlerThreads, they will never die. Like zombies. Or rock and roll.
Finally, within PhotoAdapter.onBindViewHolder(...), call the thread’s queueThumbnail() method and
pass in the target PhotoHolder where the image will ultimately be placed and the GalleryItem’s URL
to download from.
Listing 24.6  Hooking up ThumbnailDownloader
## (
PhotoGalleryFragment.java)
public class PhotoGalleryFragment extends Fragment {

## ...


private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {


## ...
@Override
public void onBindViewHolder(PhotoHolder photoHolder, int position) {
GalleryItem galleryItem = mGalleryItems.get(position);

Drawable placeholder = getResources().getDrawable(R.drawable.bill_up_close);
photoHolder.bindDrawable(placeholder);

mThumbnailDownloader.queueThumbnail(photoHolder, galleryItem.getUrl());
## }
## ...
## }


## ...
## }
Run PhotoGallery and check out LogCat. When you scroll around the RecyclerView, you should see
lines in LogCat signaling that ThumbnailDownloader is getting each one of your download requests.
Now that you have a HandlerThread up and running, the next step is to create a message with the
information passed in to queueThumbnail() and put that message on the ThumbnailDownloader’s
message queue.
Messages and Message Handlers
Before you create a message, you need to understand what a Message is and the relationship it has with
its
Handler (often called its message handler).
Message anatomy
Let’s start by looking closely at messages. The messages that a Flash might put in an inbox (its own
inbox or that of another Flash) are not supportive notes, like “You run very fast, Flash.” They are tasks
that need to be handled.

Chapter 24  Loopers, Handlers, and HandlerThread
## 436
A message is an instance of Message and contains several fields. Three are relevant to your
implementation:
whata user-defined int that describes the message
obja user-specified object to be sent with the message
targetthe Handler that will handle the message
The target of a Message is an instance of Handler. You can think of the name Handler as being short
for “message handler.” When you create a Message, it will automatically be attached to a Handler. And
when your Message is ready to be processed,
Handler will be the object in charge of making it happen.
Handler anatomy
To do any real work with messages, you will need an instance of Handler first. A Handler is not just
a target for processing your Messages. A Handler is your interface for creating and posting Messages,
too. Take a look at Figure 24.4.
## Figure 24.4
Looper, Handler, HandlerThread, and Messages
Messages must be posted and consumed on a Looper, because Looper owns the inbox of Message
objects. So Handler always has a reference to its coworker, the Looper.
A Handler is attached to exactly one Looper, and a Message is attached to exactly one target Handler,
called its target. A Looper has a whole queue of Messages. Multiple Messages can reference the same
target Handler (Figure 24.5).

Using handlers
## 437
## Figure 24.5  Multiple
Handlers, one Looper
Multiple Handlers can be attached to one Looper. This means that your Handler’s Messages may be
living side by side with another Handler’s messages.
Using handlers
Usually, you do not set a message’s target
Handler by hand. It is better to build the message by calling
Handler.obtainMessage(...). You pass the other message fields into this method, and it automatically
sets the target to the Handler object the method was called on for you.
Handler.obtainMessage(...) pulls from a common recycling pool to avoid creating new Message
objects, so it is also more efficient than creating new instances.
Once you have obtained a Message, you call sendToTarget() to send the Message to its Handler. The
Handler will then put the Message on the end of Looper’s message queue.
In this case, you are going to obtain a message and send it to its target within the implementation
of queueThumbnail(). The message’s what will be a constant defined as MESSAGE_DOWNLOAD. The
message’s obj will be an object of type T, which will be used to identify the download. In this case,
obj will be the PhotoHolder that the adapter passed in to queueThumbnail().
When the looper pulls a Message from the queue, it gives the message to the message’s
target Handler to handle. Typically, the message is handled in the target’s implementation of
Handler.handleMessage(...).
Figure 24.6 shows the object relationships involved.

Chapter 24  Loopers, Handlers, and HandlerThread
## 438
Figure 24.6  Creating a
Message and sending it
In this case, your implementation of handleMessage(...) will use FlickrFetchr to download bytes
from the URL and then turn these bytes into a bitmap.
First, add the constant and member variables as shown in Listing 24.7.
Listing 24.7  Adding constant and member variables
(ThumbnailDownloader.java)
public class ThumbnailDownloader<T> extends HandlerThread {

private static final String TAG = "ThumbnailDownloader";
private static final int MESSAGE_DOWNLOAD = 0;
private Handler mRequestHandler;
private ConcurrentMap<T,String> mRequestMap = new ConcurrentHashMap<>();
## ...
## }
MESSAGE_DOWNLOAD will be used to identify messages as download requests. (ThumbnailDownloader
will set this as the what on any new download messages it creates.)
The newly added mRequestHandler will store a reference to the Handler responsible for queueing
download requests as messages onto the ThumbnailDownloader background thread. This handler will
also be in charge of processing download request messages when they are pulled off the queue.
The mRequestMap variable is a ConcurrentHashMap. A ConcurrentHashMap is a thread-safe version
of HashMap. Here, using a download request’s identifying object of type T as a key, you can store
and retrieve the URL associated with a particular request. (In this case, the identifying object is
a PhotoHolder, so the request response can be easily routed back to the UI element where the
downloaded image should be placed.)
Next, add code to queueThumbnail(...) to update mRequestMap and to post a new message to the
background thread’s message queue.

Using handlers
## 439
Listing 24.8  Obtaining and sending a message (ThumbnailDownloader.java
## )
public class ThumbnailDownloader<T> extends HandlerThread {
private static final String TAG = "ThumbnailDownloader";
private static final int MESSAGE_DOWNLOAD = 0;
private Handler mRequestHandler;
private ConcurrentMap<T,String> mRequestMap = new ConcurrentHashMap<>();

public ThumbnailDownloader() {
super(TAG);
## }

public void queueThumbnail(T target, String url) {
Log.i(TAG, "Got a URL: " + url);

if (url == null) {

mRequestMap.remove(target);
} else {

mRequestMap.put(target, url);
mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, target)
.sendToTarget();

## }
## }
## }
You obtain a message directly from mRequestHandler, which automatically sets the new Message
object’s target field to mRequestHandler. This means mRequestHandler will be in charge of
processing the message when it is pulled off the message queue. The message’s what field is set to
MESSAGE_DOWNLOAD. Its obj field is set to the T target value (a PhotoHolder in this case) that is
passed to queueThumbnail(...).
The new message represents a download request for the specified T target (a PhotoHolder from the
RecyclerView). Recall that PhotoGalleryFragment’s RecyclerView’s adapter implementation calls
queueThumbnail(...) from onBindViewHolder(...), passing along the PhotoHolder the image is being
downloaded for and the URL location of the image to download.
Notice that the message itself does not include the URL. Instead you update mRequestMap with
a mapping between the request identifier (PhotoHolder) and the URL for the request. Later you
will pull the URL from mRequestMap to ensure that you are always downloading the most recently
requested URL for a given PhotoHolder instance. (This is important because ViewHolder objects in
RecyclerViews are recycled and reused.)
Finally, initialize mRequestHandler and define what that Handler will do when downloaded messages
are pulled off the queue and passed to it.

Chapter 24  Loopers, Handlers, and HandlerThread
## 440
Listing 24.9  Handling a message (
ThumbnailDownloader.java)
public class ThumbnailDownloader<T> extends HandlerThread {

private static final String TAG = "ThumbnailDownloader";

private static final int MESSAGE_DOWNLOAD = 0;
private Handler mRequestHandler;
private ConcurrentMap<T,String> mRequestMap = new ConcurrentHashMap<>();

public ThumbnailDownloader(Handler responseHandler) {
super(TAG);
## }
@Override

protected void onLooperPrepared() {

mRequestHandler = new Handler() {
@Override
public void handleMessage(Message msg) {

if (msg.what == MESSAGE_DOWNLOAD) {
T target = (T) msg.obj;

Log.i(TAG, "Got a request for URL: " + mRequestMap.get(target));
handleRequest(target);
## }

## }
## };

## }

public void queueThumbnail(T target, String url) {

## ...
## }
private void handleRequest(final T target) {
try {

final String url = mRequestMap.get(target);
if (url == null) {
return;
## }
byte[] bitmapBytes = new FlickrFetchr().getUrlBytes(url);
final Bitmap bitmap = BitmapFactory
.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
Log.i(TAG, "Bitmap created");

} catch (IOException ioe) {
Log.e(TAG, "Error downloading image", ioe);
## }
## }
## }
You implemented
Handler.handleMessage(...) in your Handler subclass within onLooperPrepared().
HandlerThread.onLooperPrepared() is called before the Looper checks the queue for the first time.
This makes it a good place to create your Handler implementation.
## Within
Handler.handleMessage(...), you check the message type, retrieve the obj value (which will
be of type T and serves as the identifier for the request), and then pass it to handleRequest(...). (Recall
that Handler.handleMessage(...) will get called when a download message is pulled off the queue and
ready to be processed.)

Passing handlers
## 441
The handleRequest() method is a helper method where the downloading happens. Here you check for
the existence of a URL. Then you pass the URL to a new instance of your old friend FlickrFetchr. In
particular, you use the FlickrFetchr.getUrlBytes(...) method that you created with such foresight in
the last chapter.
Finally, you use BitmapFactory to construct a bitmap with the array of bytes returned from
getUrlBytes(...).
Run PhotoGallery and check LogCat for your confirming log statements.
Of course, the request will not be completely handled until you set the bitmap on the PhotoHolder that
originally came from PhotoAdapter. However, this is UI work, so it must be done on the main thread.
Everything you have seen so far uses handlers and messages on a single thread –
ThumbnailDownloader
putting messages in ThumbnailDownloader’s own inbox. In the next section,
you will see how ThumbnailDownloader can use a Handler to post requests to a separate thread
(namely, the main thread).
Passing handlers
So far you are able to schedule work on the background thread from the main thread using
ThumbnailDownloader’s mRequestHandler. This flow is shown in Figure 24.7.
Figure 24.7  Scheduling work on
ThumbnailDownloader from the main thread
You can also schedule work on the main thread from the background thread using a Handler attached
to the main thread. This flow looks like Figure 24.8.

Chapter 24  Loopers, Handlers, and HandlerThread
## 442
Figure 24.8  Scheduling work on the main thread from ThumbnailDownloader
## ’s
thread
The main thread is a message loop with handlers and a Looper. When you create a Handler in the
main thread, it will be associated with the main thread’s Looper. You can then pass that Handler to
another thread. The passed Handler maintains its loyalty to the Looper of the thread that created it.
Any messages the Handler is responsible for will be handled on the main thread’s queue.
In ThumbnailDownloader.java, add the mResponseHandler variable seen above to hold a Handler
passed from the main thread. Then replace the constructor with one that accepts a Handler and sets
the variable, and add a listener interface that will be used to communicate the responses (downloaded
images) with the requester (the main thread).

Passing handlers
## 443
Listing 24.10  Handling a message (
ThumbnailDownloader.java)
public class ThumbnailDownloader<T> extends HandlerThread {
private static final String TAG = "ThumbnailDownloader";
private static final int MESSAGE_DOWNLOAD = 0;
private Handler mRequestHandler;
private ConcurrentMap<T,String> mRequestMap = new ConcurrentHashMap<>();

private Handler mResponseHandler;
private ThumbnailDownloadListener<T> mThumbnailDownloadListener;

public interface ThumbnailDownloadListener<T> {
void onThumbnailDownloaded(T target, Bitmap thumbnail);

## }

public void setThumbnailDownloadListener(ThumbnailDownloadListener<T> listener) {
mThumbnailDownloadListener = listener;

## }


public ThumbnailDownloader(Handler responseHandler) {
super(TAG);
mResponseHandler = responseHandler;

## }
## ...
## }
The onThumbnailDownloaded(...) method defined in your new ThumbnailDownloadListener interface
will eventually be called when an image has been fully downloaded and is ready to be added to
the UI. Using this listener delegates the responsibility of what to do with the downloaded image
to a class other than ThumbnailDownloader (in this case, to PhotoGalleryFragment). Doing so
separates the downloading task from the UI updating task (putting the images into ImageViews), so that
ThumbnailDownloader could be used for downloading into other kinds of View objects as needed.
Next, modify PhotoGalleryFragment to pass a Handler attached to the main thread to
ThumbnailDownloader. Also, set a ThumbnailDownloadListener to handle the downloaded image
once it is complete.

Chapter 24  Loopers, Handlers, and HandlerThread
## 444
Listing 24.11  Hooking up to response Handler (
PhotoGalleryFragment.java)
## ...
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);

setRetainInstance(true);
new FetchItemsTask().execute();
Handler responseHandler = new Handler();
mThumbnailDownloader = new ThumbnailDownloader<>(responseHandler);

mThumbnailDownloader.setThumbnailDownloadListener(
new ThumbnailDownloader.ThumbnailDownloadListener<PhotoHolder>() {
@Override
public void onThumbnailDownloaded(PhotoHolder photoHolder, Bitmap bitmap) {

Drawable drawable = new BitmapDrawable(getResources(), bitmap);
photoHolder.bindDrawable(drawable);

## }
## }
## );

mThumbnailDownloader.start();
mThumbnailDownloader.getLooper();

Log.i(TAG, "Background thread started");
## }
## ...
Remember that by default, the
Handler will attach itself to the Looper for the current thread. Because
this Handler is created in onCreate(...), it will be attached to the main thread’s Looper.
## Now
ThumbnailDownloader has access via mResponseHandler to a Handler that is tied to the main
thread’s Looper. It also has your ThumbnailDownloadListener to do the UI work with the returning
Bitmaps. Specifically, the onThumbnailDownloaded implementation sets the Drawable of the originally
requested PhotoHolder to the newly downloaded Bitmap.
You could send a custom Message back to the main thread requesting to add the image to the UI,
similar to how you queued a request on the background thread to download the image. However, this
would require another subclass of Handler, with an override of handleMessage(...).
Instead, let’s use another handy Handler method – post(Runnable).
Handler.post(Runnable) is a convenience method for posting Messages that look like this:
Runnable myRunnable = new Runnable() {
public void run() {
/* Your code here */
## }
## };
Message m = mHandler.obtainMessage();
m.callback = myRunnable;
When a Message has its callback field set, it is not routed to its
target Handler when pulled off the
message queue. Instead, the run() method of the Runnable stored in callback is executed directly.
In ThumbnailDownloader.handleRequest(), add the following code.

Passing handlers
## 445
Listing 24.12  Downloading and displaying images
## (
ThumbnailDownloader.java)
public class ThumbnailDownloader<T> extends HandlerThread {
## ...
private Handler mResponseHandler;
private ThumbnailDownloadListener<T> mThumbnailDownloadListener;


## ...
private void handleRequest(final T target) {
try {

final String url = mRequestMap.get(target);
if (url == null) {
return;

## }

byte[] bitmapBytes = new FlickrFetchr().getUrlBytes(url);

final Bitmap bitmap = BitmapFactory
.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);

Log.i(TAG, "Bitmap created");

mResponseHandler.post(new Runnable() {

public void run() {
if (mRequestMap.get(target) != url) {

return;
## }
mRequestMap.remove(target);
mThumbnailDownloadListener.onThumbnailDownloaded(target, bitmap);

## }
## });
} catch (IOException ioe) {
Log.e(TAG, "Error downloading image", ioe);

## }
## }
## }
## Because
mResponseHandler is associated with the main thread’s Looper, all of the code inside of
run() will be executed on the main thread.
So what does this code do? First, you double-check the requestMap. This is necessary because the
RecyclerView recycles its views. By the time ThumbnailDownloader finishes downloading the Bitmap,
RecyclerView may have recycled the PhotoHolder and requested a different URL for it. This check
ensures that each PhotoHolder gets the correct image, even if another request has been made in the
meantime.
Finally, you remove the PhotoHolder-URL mapping from the requestMap and set the bitmap on the
target PhotoHolder.
Before running PhotoGallery and seeing your hard-won images, there is one last danger you need
to account for. If the user rotates the screen,
ThumbnailDownloader may be hanging on to invalid
PhotoHolders. Bad things will happen if the corresponding ImageViews get pressed.
Write the following method to clean all the requests out of your queue.

Chapter 24  Loopers, Handlers, and HandlerThread
## 446
Listing 24.13  Adding cleanup method (
ThumbnailDownloader.java)
public class ThumbnailDownloader<T> extends HandlerThread {
## ...

public void queueThumbnail(T target, String url) {
## ...
## }

public void clearQueue() {
mRequestHandler.removeMessages(MESSAGE_DOWNLOAD);
## }


private void handleRequest(final T target) {

## ...
## }
## }
Then clean out your downloader in PhotoGalleryFragment when your view is destroyed.
Listing 24.14  Calling cleanup method (PhotoGalleryFragment.java)
public class PhotoGalleryFragment extends Fragment {


## ...


@Override

public View onCreateView(LayoutInflater inflater, ViewGroup container,
Bundle savedInstanceState) {
## ...

## }


@Override
public void onDestroyView() {
super.onDestroyView();

mThumbnailDownloader.clearQueue();
## }


@Override
public void onDestroy() {
## ...

## }

## ...
## }
With that, your work for this chapter is complete. Run PhotoGallery. Scroll around to see images
dynamically loading.
PhotoGallery has achieved its basic goal of displaying images from Flickr. In the next few chapters,
you will add more functionality, like searching for photos and opening each photo’s Flickr page in a
web view.

For the More Curious: AsyncTask vs. Threads
## 447
For the More Curious: AsyncTask vs. Threads
Now that you understand Handler and Looper, AsyncTask may not seem quite so magical. It is still
less work than what you have done here. So why not use AsyncTask instead of a HandlerThread?
There are a few reasons. The most fundamental one is that AsyncTask is not designed for it. It is
intended for work that is short lived and not repeated too often. Your code in the previous chapter is
a place where AsyncTask shines. But if you are creating a lot of AsyncTasks or having them run for a
long time, you are probably using the wrong class.
A more compelling technical reason is that in Android 3.2 AsyncTask changed its implementation in
a significant way. Starting with Android 3.2, AsyncTask does not create a thread for each instance of
AsyncTask
. Instead, it uses something called an Executor to run background work for all AsyncTasks
on a single background thread. That means that each AsyncTask will run one after the other. A long-
running AsyncTask will hog the thread, preventing other AsyncTasks from getting any CPU time.
It is possible to safely run AsyncTasks in parallel by using a thread pool executor instead, but we do not
recommend doing so. If you are considering doing this, it is usually better to do your own threading,
using Handlers to communicate back to the main thread when necessary.
Challenge: Preloading and Caching
Users accept that not everything can be instantaneous. (Well, most users.) Even so, programmers strive
toward perfection.
To approach instantaneity, most real-world apps augment the code you have here in two ways:
- adding a caching layer
- preloading images
A cache is a place to stash a certain number of Bitmap objects so that they stick around even when you
are done using them. A cache can only hold so many items, so you need a strategy to decide what to
keep when your cache runs out of room. Many caches use a strategy called LRU, or “least recently
used.”
When you are out of room, the cache gets rid of the least recently used item.
The Android support library has a class called LruCache that implements an LRU strategy. For the first
challenge, use LruCache to add a simple cache to ThumbnailDownloader. Whenever you download the
Bitmap for a URL, you will stick it in the cache. Then, when you are about to download a new image,
you will check the cache first to see if you already have it around.
Once you have built a cache, you can preload things into it. Preloading is loading items in the cache
before you actually need them. That way, there is no delay for Bitmaps to download before displaying
them.
Preloading is tricky to implement well, but it makes a huge difference for the user. For a second, harder
challenge, for every
GalleryItem you display, preload Bitmaps for the previous 10 and the next 10
GalleryItems.

Chapter 24  Loopers, Handlers, and HandlerThread
## 448
For the More Curious: Solving the Image Downloading
## Problem
This book is here to teach you about the tools in the standard Android library. If you are open to using
third-party libraries, though, there are a few libraries that can save you a whole lot of time in various
scenarios, including the image downloading work you implemented in PhotoGallery.
Admittedly, the solution you implemented in this chapter is far from perfect. When you start to need
caching, transformations, and better performance, it is natural to ask if someone else has solved this
problem before you. The answer is, of course: someone has. There are several libraries available that
solve the image-loading problem. We currently use Picasso (http://square.github.io/picasso/)
for image loading in our production applications.
Picasso lets you do everything from this chapter in one line:
private class PhotoHolder extends RecyclerView.ViewHolder {

## ...
public void bindGalleryItem(GalleryItem galleryItem) {
Picasso.with(getActivity())

.load(galleryItem.getUrl())
.placeholder(R.drawable.bill_up_close)
.into(mItemImageView);

## }
## ...
## }
The fluent interface requires you specify a context using with(Context). You can specify the URL
of the image to download using load(String) and the ImageView object to load the result into using
into(ImageView). There are many other configurable options, such as specifying an image to display
until the requested image is fully downloaded (using placeholder(int) or placeholder(drawable)).
In PhotoAdapter.onBindViewHolder(...), you would replace the existing code with a call through to
the new bindGalleryItem(...) method.
Picasso does all of the work of ThumbnailDownloader (along with the
ThumbnailDownloader.ThumbnailDownloadListener<T>
callback) and the image-related work of
FlickrFetchr. This means you can remove ThumbnailDownloader if you use Picasso (you will still
need FlickrFetchr for downloading the JSON data). In addition to simplifying your code, Picasso
supports more advanced features such as image transformations and disk caching with minimal effort
on your part.
You can add Picasso to your project as a library dependency using the Project Structure window, just as
you did for other dependencies (like RecyclerView).