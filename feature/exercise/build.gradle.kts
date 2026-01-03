plugins {
    id("youth.android.feature")
}

android {
    namespace = "see.day.exercise"
}

dependencies {
    implementation(project(":core:analytics"))
}
