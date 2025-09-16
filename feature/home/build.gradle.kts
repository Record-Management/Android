plugins {
    id("youth.android.feature")
}

android {
    namespace = "see.day.home"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.balloon)
    implementation(libs.balloon.compose)
}
