# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-optimizationpasses 5
-keep public class * extends android.app.Application
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn retrofit2.**
-dontwarn rx.**
-keep class okhttp3.**{ *; }
-keep class okio.** {*;}
-keep class retrofit2.** { *; }
-keep class rx.** { *; }

# rxjava
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# json相关
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keepclassmembers class * { public <init>(org.json.JSONObject); }

# 极光
-dontwarn cn.jpush.**
-dontwarn cn.jiguang.**
-keep class cn.jpush.**{*;}
-keep class cn.jiguang.** { *; }

# 百度（地图？）
-keep class com.baidu.** {*;}
-keep class vi.com.gdi.bgl.** {*;}

# Glide
-dontwarn com.bumptech.glide.**
-keep class com.bumptech.glide.**{ *; }

# ijkplayer
-keep class tv.danmaku.ijk.media.**{*;}

# zxing
-keep class com.google.zxing.**{*;}

# 支付宝
-keep class com.alipay.**{*;}

# 微信
-keep class com.tencent.**{*;}