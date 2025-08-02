plugins {
    id("youth.android.feature")
}

android {
    namespace = "record.daily.login"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}
