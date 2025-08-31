package com.youth.app

import org.gradle.api.Project

internal fun Project.configureVerifyKtLint() {
    with(pluginManager) {
        apply("org.jlleitschuh.gradle.ktlint")
    }
}

