plugins {
    id("youth.android.feature")
}

android {
    namespace = "see.day.habit"

}

dependencies {
    implementation(project(":core:analytics"))
    
    implementation(libs.wheelpickercompose)
}
