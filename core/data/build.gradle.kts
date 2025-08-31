plugins {
    id("youth.android.library")
}

android {
    namespace = "record.daily.data"
}

dependencies {
    implementation(project(":core:domain"))
}
