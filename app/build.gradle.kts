import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("youth.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "see.day.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "see.day.app"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
    }

    fun getApiKey(propertyKey: String): String {
        return gradleLocalProperties(rootDir, providers).getProperty(propertyKey)
    }

    signingConfigs {
        create("release") {
            storeFile = file(getApiKey("keystore.file"))
            storePassword = getApiKey("keystore.password")
            keyAlias = getApiKey("key.alias")
            keyPassword = getApiKey("key.password")
        }

    }
    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    android {
        lint {
            disable += "NullSafeMutableLiveData"
        }
    }
    firebaseCrashlytics {
        mappingFileUploadEnabled = true
    }
}

dependencies {

    implementation(project(":feature:main"))

    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))
    implementation(project(":core:model"))

    implementation(libs.kakao.v2.user)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    // Firebase Cloud Messaging
    implementation(libs.firebase.messaging)
}
