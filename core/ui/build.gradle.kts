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

    implementation(libs.coil3.coil.network.okttp)
    implementation(libs.coil3.coil.compose)

    implementation("dev.chrisbanes.snapper:snapper:0.3.0")
}
