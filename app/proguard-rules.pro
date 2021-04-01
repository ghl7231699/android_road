# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\AsSdk/tools/proguard/proguard-android.txt
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

# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
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
#-libraryjars libs/alipaySdk-20161009.jar
#-libraryjars libs/gson-2.2.1.jar
#

-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**

##-------通用---------------------------------------------------------------------------------
#-----行号和文件名----
-keepattributes SourceFile,LineNumberTable
-keep public class * implements java.io.Serializable {*;}
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.preference.Preference
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.multidex.**
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.widget.**
#----------Parcelable & Serializable---------------
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keep public class * implements java.io.Serializable {
        public *;
}

#----------JSONObject---------------
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}


#----------native method------------
-keepclasseswithmembernames class * {
    native <methods>;
}
#----------enum---------------------
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#---------------renderscript-------------------------------------------------------
-keep class android.support.v8.renderscript.** { *; }

##------------我的世界--------------------------------------------------------------------------
-keep class com.ghl.support.mycontrol.** {*;}
-keep class com.ghl.support.model.** {*;}
-keep class com.ghl.support.viewmodel.** {*;}

-keep class com.ghl.personal.model.** {*;}
-keep class com.ghl.personal.viewmodel.** {*;}

-keep class com.ghl.comment.model.** {*;}
-keep class com.ghl.comment.viewmodel.** {*;}

-keep class com.ghl.im.model.** {*;}
-keep class com.ghl.im.viewmodel.** {*;}

-keep class com.ghl.imV2.model.** {*;}

-keep class com.ghl.order.model.** {*;}
-keep class com.ghl.order.viewmodel.** {*;}

-keep class com.ghl.orderV2.model.** {*;}

-keep class com.ghl.cleaning.model.** {*;}
-keep class com.ghl.cleaning.viewmodel.** {*;}

-keep class com.ghl.publish.bean.** {*;}
-keep class com.ghl.publish.model.** {*;}
-keep class com.ghl.publish.viewmodel.** {*;}
-keep class com.ghl.publish.viewmodel2.** {*;}
-keep class com.ghl.publish.fragment.** {*;}

-keep class com.ghl.realshot.model.** {*;}

-keep class com.ghl.settlement.model.** {*;}
-keep class com.ghl.settlement.viewmodel.** {*;}

-keep class com.ghl.find.model.** {*;}
-keep class com.ghl.find.viewmodel.** {*;}

-keep class com.ghl.invoice.model.** {*;}

-keep class com.ghl.lock.model.** {*;}

-keep class com.ghl.splash.model.** {*;}

-keep class com.ghl.pay.model.** {*;}

-keep class com.ghl.voucher.model.** {*;}
-keep class com.ghl.voucher.viewmodel.** {*;}

# 物流
-keep class com.ghl.logistics.model.** {*;}
-keep class com.ghl.logistics.viewmodel.** {*;}

# 实拍
-keep class com.ghl.realshot.model.** {*;}
-keep class com.ghl.realshot.viewmodel.** {*;}

#相册
-keep class com.ghl.support.photo.photoselector.model.** {*;}

#收款设置
-keep class com.ghl.income.model.** {*;}

#资质认证
-keep class com.ghl.qualification.bean.** {*;}

-keep class com.ghl.support.tools.TextWithColorsTool$* {*;}
-keep class com.ghl.activity.XZWebViewActivity$* {*;}

-keep class com.ghl.servicecenter.model.** {*;}
-keep class com.ghl.servicecenter.viewmodel.** {*;}

-keep class com.ghl.**.model.** {*;}# 通用数据bean
-keep class com.ghl.**.viewmodel.** {*;}# 通用数据bean

##---------------mi push start ----------------------------------------------------------------
#这里com.xiaomi.mipushdemo.DemoMessageRreceiver改成app中定义的完整类名
-keep class com.xiaomi.mipush.sdk.DemoMessageReceiver {*;}
#可以防止一个误报的 warning 导致无法成功编译，如果编译使用的 Android 版本是 23。
-dontwarn com.xiaomi.push.**
-keep class com.ghl.push.PushInfo {*;}
-keep class com.ghl.push.PushInfo$* {*;}
-keep class com.ghl.push.RefreshPushInfo {*;}

##---------------mi push end ----------------------------------------------------------------

##---------------hua wei push start--------------------------------------------------------------
-keep class com.huawei.android.pushagent.**{*;}
-keep class com.huawei.android.pushselfshow.**{*;}
-keep class com.huawei.android.microkernel.**{*;}
##---------------hua wei push end--------------------------------------------------------------



##---------------sharesdk-------------------------------------------------------------------
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class m.framework.**{*;}
-keep class android.net.http.SslError
-keep class android.webkit.**{*;}
-keep class com.mob.tools.utils
-keep class com.mob.**{*;}
-dontwarn com.mob.**
-dontwarn cn.sharesdk.**
-dontwarn **.R$*


#阿里系SDK
-dontwarn com.alipay.**
-dontwarn com.alibaba.**
-keep class com.alipay.** {*;}
-keep class com.alibaba.** { *;}
-keep class com.taobao.**{*;}
-keep class com.ta.** { *;}
-keep class com.ut.** { *; }
## 蚂蚁金服 - 智能门锁
-keep class com.clj.** { *; }
-keep class org.simalliance.** { *; }
-keep class org.json.alipay.** { *; }

-dontwarn android.net.**
-keep class android.net.SSLCertificateSocketFactory{*;}
##-------微信支付---------------------------------------------------------------------------
-keep class com.tencent.mm.opensdk.** {
   *;
}
-keep class com.tencent.wxop.** {
   *;
}
-keep class com.tencent.mm.sdk.** {
   *;
}

#################屏蔽报错信息########################
-dontwarn com.ghl.support.tools.hotfix.AntilazyLoad
-dontwarn cn.sharesdk.sina.weibo.**
-dontwarn com.google.android.gms.**
-dontwarn com.unionpay.mobile.android.**
-dontwarn com.baidu.navisdk.**
-dontwarn com.baidu.pano.platform.http.p
-dontwarn com.tencent.mm.**
-dontwarn com.xiaozhu.hack.AntilazyLoad


-dontwarn com.alibaba.fastjson.support.**
-dontwarn javax.servlet.http.**
-dontwarn com.baseproject.utils.**
-dontwarn java.time.**
-dontwarn java.awt.**

-dontwarn com.ghl.support.tools.DownloadApkXutis.**

# universal-image-loader
-dontwarn com.nostra13.universalimageloader.**
-keep class com.nostra13.universalimageloader.** { *; }

# fastjson
-dontwarn com.ali.fastjson.**
-keep class com.ali.fastjson.** { *; }
-keepattributes Exceptions, Signature, InnerClasses

#-keepattributes *Annotation*


#------------广点通start---------------------
-dontwarn com.qq.gdt.action.**
-keep class com.qq.gdt.action.** {*;}
#------------广点通end---------------------


-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-ignorewarnings

##-------百度sdk--------
-dontwarn com.baidu.**
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-keep class mapsdkvi.com.** {*;}
##-------云迈sdk（身份证扫描）---------------------------------------------------------------------------
-keep class com.ym.idcard.reg.**{*;}
-keep class com.yunmai.**{*;}

##-------银联sdk--------
-keep class com.unionpay.**{*;}

##------------GIO sdk------------------

-keep class com.growingio.** {
    *;
}
-dontwarn com.growingio.**
-keepnames class * extends android.view.View
-keepnames class * extends android.app.Fragment
-keepnames class * extends android.support.v4.app.Fragment
-keepnames class * extends androidx.fragment.app.Fragment
-keep class android.support.v4.view.ViewPager{
    *;
}
-keep class android.support.v4.view.ViewPager$**{
	*;
}
-keep class androidx.viewpager.widget.ViewPager{
    *;
}
-keep class androidx.viewpager.widget.ViewPager$**{
	*;
}

##------------GIO sdk------------------


##--------------榉树SDK------------------
-keep class cn.zelkova.** {*;}
#-keep class com.ghl.lock.sdk.XZ4BluetoothLockSDKManager {*;}
##--------------榉树SDK------------------


##---------------听云------------------
-keep class com.networkbench.** { *; }
-dontwarn com.networkbench.**
-keepattributes Exceptions, Signature, InnerClasses
##---------------听云end------------------

##--------------apk升级------------------
-keep class com.ghl.bizBase.update.SoftVersion {*;}
##--------------apk升级-----------------

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
#-keep class com.google.gson.examples.android.model.** { *; }

##---------------End: proguard configuration for Gson  ----------

##--------------七陌 相关---------------
-keep class com.moor.imkf.** { *; }
-keep class com.moor.videosdk.** { *; }
-keep class org.webrtc.** { *; }
##--------------七陌 相关end---------------

##---------------友盟------------------
-keep class com.umeng.** {*;}
-keep class com.umeng.commonsdk.** {*;}

-keep public class com.ghl.R$*{
    public static final int *;
}

##--------------提醒V2强弹层-------------
-keep class com.ghl.support.shade.** {*;}

##---------------QuestMobile------------------
-dontwarn com.sijla.**
-keep class com.sijla.**{*; }
-dontwarn com.q.**
-keep class com.q.Qt{*; }

##---------------AWS------------------
-dontwarn com.amazonaws.**
-keep class com.amazonaws.**{*; }


## 此混淆配置文件已经过时!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
# 基础混淆配置见:proguard-base.pro
# 业务混淆配置见:proguard-app.pro

#  还有疑问  联系小泽

