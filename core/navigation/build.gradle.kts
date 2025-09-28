plugins {
    id("youth.android.library")
    kotlin("plugin.serialization")
}

android {
    namespace = "see.day.navigation"
}

dependencies {

    implementation(project(":core:designsystem"))
    implementation(libs.kotlinx.serialization.json)
}
