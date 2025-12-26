plugins {
    id("youth.android.feature")
}

android {
    namespace = "see.day.setting"
}

dependencies {
    implementation(project(":core:analytics"))
}
