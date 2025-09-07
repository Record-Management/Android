plugins {
    id("youth.android.feature")
}

android {
    namespace = "see.day.main"
}

dependencies {

    implementation(project(":feature:login"))
    implementation(project(":feature:onboarding"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}
