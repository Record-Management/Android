plugins {
    id("youth.android.feature")
}

android {
    namespace = "see.day.home"
}

dependencies {
    implementation(project(":core:analytics"))
    implementation(libs.balloon)
    implementation(libs.balloon.compose)

    implementation(libs.inapp.review)
    implementation(libs.inapp.review.ktx)
}
