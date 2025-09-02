plugins {
    id("youth.android.library")
    id("kotlinx-serialization")
    kotlin("plugin.serialization")
}

android {
    namespace = "see.day.data"
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp3.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.okhttp3.mockwebserver)
    testImplementation(libs.mockito.kotlin)
}
