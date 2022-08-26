package extension

import ComposeDependencies
import internal.androidExtension
import org.gradle.api.Project

fun Project.setupCompose() {

    androidExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = ComposeDependencies.Versions.compiler
        }
    }
}