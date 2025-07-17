package com.youth.app

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureHilt() {
    with(pluginManager) {
        apply("com.google.devtools.ksp")
    }

    dependencies {
        add("ksp", libs.findLibrary("hilt.compiler").get())
    }

    pluginManager.withPlugin("otg.jetbrains.kotlin.jvm") {
        dependencies {
            add("implementation", libs.findLibrary("hilt.core").get())
        }
    }


    pluginManager.withPlugin("com.android.base") {
        pluginManager.apply("dagger.hilt.android.plugin")
        dependencies {
            add("implementation",libs.findLibrary("hilt.android").get())
            add("ksp", libs.findLibrary("hilt.android.compiler").get())
        }
    }
}

internal class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureHilt()
        }
    }
}
