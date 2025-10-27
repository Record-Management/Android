plugins {
    id("youth.android.feature")
}

android {
    namespace = "see.day.notification"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}
