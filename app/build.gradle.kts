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
            signingConfig = signingConfigs.getByName("release")
        }
    }
    android {
        lint {
            disable += "NullSafeMutableLiveData"
        }
    }
}

dependencies {

    implementation(project(":feature:main"))

    implementation(project(":core:data"))
    implementation(project(":core:domain"))

    implementation(libs.kakao.v2.user)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    // Firebase Cloud Messaging
    implementation(libs.firebase.messaging)
}
