plugins {
    id("youth.android.feature")
}

android {
    namespace = "see.day.goal"
}

dependencies {
    implementation(project(":core:analytics"))
}
