import com.youth.app.configureHilt
import com.youth.app.libs

plugins {
    id("youth.android.library")
    id("youth.android.compose")
}

android {
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

configureHilt()

dependencies {

    implementation(project(":core:designsystem"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))

    implementation(libs.findLibrary("hilt.navigation.compose").get())
    implementation(libs.findLibrary("androidx.navigation.compose").get())
    implementation(libs.findLibrary("timber").get())

    implementation(libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
    implementation(libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
}
