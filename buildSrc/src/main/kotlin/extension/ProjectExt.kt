package extension

import ComposeDependencies
import internal.androidExtension
import internal.implementation
import internal.isGradleModule
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

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

val Project.featureModulesDirectory
    get() = "$rootDir/source/features"

fun Project.applyFeatureModules() {
    dependencies {
        file(featureModulesDirectory).listFiles()?.forEach { featureModule ->
            if (featureModule.isDirectory && featureModule.isGradleModule) {
                implementation(project(":${featureModule.name}"))
            }
        }
    }
}