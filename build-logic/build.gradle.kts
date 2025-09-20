plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
    implementation("org.jetbrains.kotlin:kotlin-serialization:1.8.0")
}

gradlePlugin {
    plugins {
        register("Hilt") {
            id = "record.hilt"
            implementationClass = "com.youth.app.HiltPlugin"
        }
    }
}

tasks.register(":clean", Delete::class) {
    delete = setOf(rootProject.layout.buildDirectory)
}
