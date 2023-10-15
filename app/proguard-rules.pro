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

# 微信
-keep class com.tencent.mm.opensdk.** {*;}
-keep class com.tencent.wxop.** {*;}
-keep class com.tencent.mm.sdk.** {*;}

# Google
-keep class com.google.android.gms.** { *; }

# 企业微信
-keep class com.tencent.wework.api.** { *; }

# 钉钉
-keep class com.android.dingtalk.openauth.**{*;}

# 华为
-ignorewarnings
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keep class com.huawei.hianalytics.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}

# OPPO
-keepattributes Annotation
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keepclassmembers enum * {
    public static [] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator ;
}
-keep class * implements java.io.Serializable{;}
-keep class com.bun.miitmdid. {;}
-keeppackagenames com.heytap.msp**
-keep class com.heytap.openid.sdk.HeytapIDSDK{;}
-keep class com.heytap.usercenter.{;}
-keep class com.platform.usercenter.annotation.Keep
-keep @com.platform.usercenter.annotation.Keep class * {;}
-keep interface com.heytap.msp.{;}
-keep class com.heytap.msp.sdk.base.**{;}

-keep class com.heytap.msp.sdk.SdkAgent{;}
-keep class com.heytap.msp.sdk.common.**{;}
-keep class com.heytap.msp.sdk.agent.AccountSdkAgent{;}
-keep class com.heytap.msp.sdk.AccountSdk{;}
-keep class com.heytap.msp.sdk.agent.OAuthSdkAgent{;}
-keep class com.heytap.msp.sdk.OAuthSdk{;}
-keep class com.platform.oms.**{*;}

