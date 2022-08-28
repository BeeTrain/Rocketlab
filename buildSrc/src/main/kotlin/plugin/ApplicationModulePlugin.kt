package plugin

import AndroidDependencies.core
import AndroidDependencies.lifecycle
import AppConfig
import BuildTypes
import ComposeDependencies
import ComposeDependencies.compose
import DIDependencies.koin
import FirebaseDependencies.firebase
import Plugins
import internal.applicationExtension
import internal.applicationProguardFiles
import internal.configureProjectModules
import internal.versionCode
import internal.versionName
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import java.io.File

class ApplicationModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.run {
            applyPlugins()
            applyApplicationConfig()
            applyDependencies()
        }
    }

    private fun Project.applyPlugins() {
        plugins.run {
            apply(plugin = Plugins.androidApplication)
            apply(plugin = Plugins.kotlinModule)
            apply(plugin = Plugins.kotlinKapt)
            apply(plugin = Plugins.firebaseCrashlytics)
        }
    }

    private fun Project.applyApplicationConfig() {
        applicationExtension.apply {
            compileSdk = AppConfig.compileSdkVersion

            defaultConfig {
                applicationId = AppConfig.applicationId
                minSdk = AppConfig.minSdkVersion
                targetSdk = AppConfig.targetSdkVersion
                versionCode = versionCode()
                versionName = versionName()
                proguardFiles += applicationProguardFiles
            }

            compileOptions {
                sourceCompatibility = AppConfig.javaVersion
                targetCompatibility = AppConfig.javaVersion
            }

            signingConfigs {
                BuildTypes.values().forEach { buildType ->
                    maybeCreate(buildType.title)
                    named(buildType.title) {
                        storeFile = File("${rootDir.path}/${buildType.signing.storeFilePath}")
                        storePassword = buildType.signing.storePassword
                        keyAlias = buildType.signing.keyAlias
                        keyPassword = buildType.signing.keyPassword

                        enableV1Signing = true
                        enableV2Signing = true
                        enableV3Signing = true
                        enableV4Signing = true
                    }
                }
            }

            buildTypes {
                BuildTypes.values().forEach { buildType ->
                    getByName(buildType.title) {
                        isMinifyEnabled = buildType.isMinifyEnabled
                        signingConfig = signingConfigs.getByName(buildType.title)
                    }
                }
            }

            buildFeatures {
                compose = true
            }

            composeOptions {
                kotlinCompilerExtensionVersion = ComposeDependencies.Versions.compiler
            }

            packagingOptions {
                resources {
                    excludes += setOf(
                        "/META-INF/{AL2.0,LGPL2.1}"
                    )
                    merges += setOf(
                        "META-INF/gradle/incremental.annotation.processors"
                    )
                }
            }
        }
    }

    private fun Project.applyDependencies() {
        dependencies.apply {
            configureProjectModules()

            core()
            koin()
            compose()
            firebase()
            lifecycle()
        }
    }
}