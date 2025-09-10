plugins {
    id("youth.android.library")
    id("youth.android.compose")
}

android {
    namespace = "see.day.ui"

    packaging {
        resources {
            excludes += "/META-INF/**"
        }
    }
    defaultConfig {
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

dependencies {

    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}
