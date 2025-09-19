plugins {
    id("youth.android.library")
    id("youth.android.compose")
}

android {
    namespace = "see.day.designsystem"
}

dependencies {

    implementation(project(":core:model"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}
