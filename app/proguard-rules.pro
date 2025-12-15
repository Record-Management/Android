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

############################
# Retrofit + Kotlinx Serialization
############################

# Retrofit 인터페이스는 원래부터 리플렉션 기반이 아니어서 보통 keep 필요 없음.
# 다만 에러 메시지나 디버깅을 위해 이름이 유지되어야 하면 아래와 같이 패키지 기준으로 keep 가능.
# 실제 패키지명에 맞춰 수정해서 사용하세요.

# 예: 네트워크 API 인터페이스
 -keep interface see.day.network.** { *; }
 -keep class see.day.network.dto.** { *; }

############################
# Hilt / Dagger
############################

# Hilt가 생성하는 클래스들, Dagger 관련 메타데이터는 보존
-dontwarn dagger.**
-keep class dagger.** { *; }
-keep interface dagger.** { *; }

# javax/inject 어노테이션
-keep class javax.inject.** { *; }
-keep interface javax.inject.** { *; }
-dontwarn javax.inject.**

# Hilt 내부 클래스
-keep class dagger.hilt.** { *; }
-dontwarn dagger.hilt.**

# @AndroidEntryPoint 가 붙은 Activity/Fragment 가 패키지에 따라 obfuscation 되어도
# 생성/주입에 문제 없도록 패턴으로 보호 (패키지에 맞게 조정)
-keep class see.day.**.*Activity { *; }
-keep class see.day.**.*Fragment { *; }

############################
# Firebase
############################

# Firebase는 대부분 룰이 내장되어 있지만, 경고를 줄이기 위한 방어적 설정

-dontwarn com.google.firebase.**

############################
# Kakao SDK
############################

# Kakao SDK 관련 클래스 보호 (필요시 범위를 더 좁혀도 됨)
-keep class com.kakao.** { *; }

############################
# 기타 (DataStore 등)
############################

# AndroidX DataStore는 보통 추가 룰이 필요 없지만, 경고 방지용
-dontwarn androidx.datastore.**

-keep class see.day.model.** { *; }
-keep class see.day.navigation.** { *; }
