plugins {
    id("youth.android.application")
}

android {
    namespace = "see.day.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "see.day.app"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

    }
}

dependencies {

    implementation(project(":feature:main"))

    implementation(libs.kakao.v2.user)
}
