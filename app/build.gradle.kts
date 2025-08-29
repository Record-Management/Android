plugins {
    id("youth.android.application")
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
}

dependencies {

    implementation(project(":feature:main"))

    implementation(libs.kakao.v2.user)
}
