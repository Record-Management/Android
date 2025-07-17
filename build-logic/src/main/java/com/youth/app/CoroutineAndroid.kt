package com.youth.app

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureCoroutineAndroid() {
    configureCoroutineKotlin()
    dependencies {
        add("implementation", libs.findLibrary("coroutines.android").get())
    }

}

internal fun Project.configureCoroutineKotlin() {
    dependencies {
        add("implementation", libs.findLibrary("coroutines.core").get())
        add("testImplementation", libs.findLibrary("coroutines.test").get())
    }
}
