plugins {
    id("youth.android.feature")
}

android {
    namespace = "see.day.main"
}

dependencies {

    implementation(project(":feature:login"))
    implementation(project(":feature:onboarding"))
    implementation(project(":feature:home"))
    implementation(project(":feature:daily"))

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}
