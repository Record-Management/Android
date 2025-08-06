plugins {
    id("youth.android.application")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "record.daily.android"
    compileSdk = 35

    defaultConfig {
        applicationId = "record.daily.android"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(project(":feature:main"))

    implementation(libs.kakao.v2.user)
}
