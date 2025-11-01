plugins {
    id("youth.android.library")
}

android {
    namespace = "see.day.messaging"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:model"))

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
}
