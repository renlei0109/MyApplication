# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/renlei/soft/code/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-useuniqueclassmembernames
-keep public class com.example.renlei.myapplication.StartActivity
-keep public class butterknife.**
-keep public class com.squareup.picasso.**
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-dontwarn com.squareup.picasso.**