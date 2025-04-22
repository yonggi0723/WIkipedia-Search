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

# customhttp 라이브러리 전체 보호
-keep class com.yonggi.customhttp.** { *; }

# 만약 리플렉션이나 Gson 등 직렬화 가능성이 있다면, 이름도 유지
-keepnames class com.yonggi.customhttp.** { *; }

# 클래스 멤버 중 어노테이션 기반 처리나 리플렉션을 쓰는 경우 멤버 유지
-keepclassmembers class com.yonggi.customhttp.** {
    *;
}

# 기본 생성자 보호 (혹시라도 리플렉션 생성 시 필요)
-keepclassmembers class com.yonggi.customhttp.** {
    public <init>(...);
}