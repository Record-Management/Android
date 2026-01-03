plugins {
    id("youth.android.feature")
}

android {
    namespace = "see.day.daily"
    lint {
        disable += "NullSafeMutableLiveData"
    }
}

dependencies {
    implementation(project(":core:analytics"))
}
