

xxi
## The Necessary Tools
To get started with this book, you will need Android Studio. Android Studio is an integrated
development environment used for Android development that is based off of the popular IntelliJ IDEA.
An install of Android Studio includes:
Android SDK
the latest version of the Android SDK
Android SDK tools and platform-tools
tools for debugging and testing your apps
A system image for the Android emulator
lets you create and test your apps on different virtual devices
As of this writing, Android Studio is under active development and is frequently updated. Be aware
that you may find differences between your version of Android Studio and what you see in this book.
## Visit
http://forums.bignerdranch.com for help with these differences.
Downloading and Installing Android Studio
Android Studio is available from Android’s developer site at https://developer.android.com/sdk/.
If you do not already have it installed, you will need to install the Java Development Kit (JDK7), which
you can download from http://www.oracle.com.
If you are still having problems, return to https://developer.android.com/sdk/ for more
information.
Downloading Earlier SDK Versions
Android Studio provides the SDK and the emulator system image from the latest platform. However,
you may want to test your apps on earlier versions of Android.
You can get components for each platform using the Android SDK Manager. In Android Studio, select
Tools → Android → SDK Manager. (You will only see the Tools menu if you have a project open. If
you have not created a project yet, you can instead access the SDK Manager from the Android Setup
Wizard screen. Under the Quick Start section, select Configure → SDK Manager, as shown in Figure 1.)

## The Necessary Tools
xxii
Figure 1  Android SDK Manager
Select and install each version of Android that you want to test with. Note that downloading these
components may take a while.
The Android SDK Manager is also how to get Android’s latest releases, like a new platform or an
update of the tools.
## An Alternative Emulator
The speed of the Android emulator has improved significantly over time and it is a reasonable way to
run the code that you write in this book.
As an alternative, Genymotion is a popular, third-party Android emulator. You will occasionally see
references to the Genymotion emulator in this book. For more information on Genymotion, visit
http://genymotion.com/.
## A Hardware Device
The emulator and Genymotion are useful for testing apps. However, they are no substitute for an actual
Android device when measuring performance. If you have a hardware device, we recommend using
that device at times when working through this book.