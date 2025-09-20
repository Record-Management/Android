plugins {
    id("youth.android.feature")
}

android {
    namespace = "see.day.daily"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}
