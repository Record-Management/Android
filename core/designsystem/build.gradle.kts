plugins {
    id("youth.android.library")
    id("youth.android.compose")
    kotlin("plugin.serialization")
}

android {
    namespace = "see.day.designsystem"
}

dependencies {

    implementation(project(":core:model"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.kotlinx.serialization.json)
}
