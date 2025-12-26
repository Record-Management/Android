plugins {
    id("youth.android.library")
}

android {
    namespace = "see.day.analytics"
}

dependencies {

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
}
