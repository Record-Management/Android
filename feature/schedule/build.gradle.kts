plugins {
    id("youth.android.feature")
}

android {
    namespace = "see.day.schedule"
}

dependencies {

    implementation(project(":core:analytics"))

    implementation(libs.wheelpickercompose)
}
