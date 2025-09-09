plugins {
    id("youth.android.feature")
}

android {
    namespace = "see.day.onboarding"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation("com.github.commandiron:WheelPickerCompose:1.1.11")
}
