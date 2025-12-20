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
    implementation(project(":feature:habit"))
    implementation(project(":feature:exercise"))
    implementation(project(":feature:setting"))
    implementation(project(":feature:notification"))
    implementation(project(":feature:goal"))

    implementation(libs.androidx.core.splashscreen)
}
