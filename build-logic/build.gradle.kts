plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
}

gradlePlugin {

}

tasks.register(":clean", Delete::class) {
    delete = setOf(rootProject.layout.buildDirectory)
}

gradlePlugin {
    plugins {
        register("Hilt") {
            id = "record.hilt"
            implementationClass = "com.youth.app.HiltSetting"
        }
    }
}
