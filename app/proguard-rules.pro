## Add project specific ProGuard rules here.
## You can control the set of applied configuration files using the
## proguardFiles setting in build.gradle.
##
## For more details, see
##   http://developer.android.com/guide/developing/tools/proguard.html
#
## If your project uses WebView with JS, uncomment the following
## and specify the fully qualified class name to the JavaScript interface
## class:
##-keepclassmembers class fqcn.of.javascript.interface.for.webview {
##   public *;
##}
#
## Uncomment this to preserve the line number information for
## debugging stack traces.
##-keepattributes SourceFile,LineNumberTable
#
## If you keep the line number information, uncomment this to
## hide the original source file name.
##-renamesourcefileattribute SourceFile
#
## Keep CartItem and related classes
#-keep class com.beauty.parler.model.CartItem { *; }
#-keep class com.beauty.parler.shared.** { *; }
#-keep class com.beauty.parler.model.** { *; }
#
## Keep GSON serialization classes
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
#
## Keep ViewModel classes
#-keep class * extends androidx.lifecycle.ViewModel { *; }
#
## Keep DataStore classes
#-keep class androidx.datastore.** { *; }
#
## Keep coroutines
#-keep class kotlinx.coroutines.** { *; }
#
## Keep Firebase classes
#-keep class com.google.firebase.** { *; }
#-keep class com.google.android.gms.** { *; }



# Firebase
-keep class com.google.firebase.** { *; }
-keep class com.firebase.** { *; }

# Google Play Services
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

# Google Sign-In
-keep class com.google.android.gms.auth.** { *; }

# Firebase Auth
-keep class com.google.firebase.auth.** { *; }