plugins {
    id("youth.android.library")
    kotlin("plugin.serialization")
}

android {
    namespace = "see.day.navigation"
}

dependencies {

    implementation(project(":core:model"))
    implementation(libs.kotlinx.serialization.json)
}
