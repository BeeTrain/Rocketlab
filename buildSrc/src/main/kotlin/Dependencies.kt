@file:Suppress("MemberVisibilityCanBePrivate")

import CoreModules.koinModules
import internal.compileOnly
import internal.debugImplementation
import internal.implementation
import internal.kapt
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

const val kotlinVersion = "1.7.10"

object Plugins {

    const val applicationModule = "application-module-plugin"
    const val featureModule = "feature-module-plugin"
    const val apiModule = "api-module-plugin"
    const val androidModule = "android-module-plugin"
    const val kotlinModule = "kotlin-module-plugin"

    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "kotlin-android"

    const val kotlin = "kotlin"
    const val kotlinJVM = "org.jetbrains.kotlin.jvm"
    const val kotlinKapt = "kotlin-kapt"
    const val javaLibrary = "java-library"
}

object KotlinDependencies {

    object Versions {

        const val coroutines = "1.6.4"
    }

    const val stdLibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

    fun DependencyHandler.stdLib() = apply {
        implementation(stdLibJdk8)
    }

    fun DependencyHandler.coroutines() = apply {
        implementation(coroutinesCore)
        implementation(coroutinesAndroid)
        implementation(coroutinesTest)
    }
}

object ComposeDependencies {

    object Versions {

        const val compiler = "1.3.0"
        const val functionality = "1.2.1"
        const val material3 = "1.0.0-alpha01"
    }

    const val ui = "androidx.compose.ui:ui:${Versions.functionality}"
    const val material3 = "androidx.compose.material3:material3:${Versions.material3}"
    const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.functionality}"

    const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.functionality}"
    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Versions.functionality}"

    fun DependencyHandler.compose() = apply {
        implementation(ui)
        implementation(material3)
        implementation(uiToolingPreview)
        implementation(AndroidDependencies.activityCompose)

        debugImplementation(uiTooling)
        debugImplementation(uiTestManifest)
    }
}

object AndroidDependencies {

    object Versions {

        const val core = "1.8.0"
        const val lifecycle = "2.4.1"
        const val activityCompose = "1.5.1"
    }

    const val coreKtx = "androidx.core:core-ktx:${Versions.core}"

    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"

    fun DependencyHandler.core() = apply {
        implementation(coreKtx)
    }

    fun DependencyHandler.lifecycle() = apply {
        implementation(lifecycleRuntimeKtx)
    }
}


object DIDependencies {
    object Versions {
        const val koin = "3.2.0"
    }

    const val core = "io.insert-koin:koin-core:${Versions.koin}"
    const val android = "io.insert-koin:koin-android:${Versions.koin}"
    const val compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"

    fun DependencyHandler.koin() = apply {
        implementation(core)
        implementation(android)
        implementation(compose)
        koinModules()
    }
}

object AnnotationProcessingDependencies {

    object Versions {
        const val autoService = "1.0.1"
        const val incap = "0.3"
    }

    val autoService = "com.google.auto.service:auto-service:${Versions.autoService}"
    val fixGuavaPackage = "com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava"

    val incap = "net.ltgt.gradle.incap:incap:${Versions.incap}"
    val incapProcessor = "net.ltgt.gradle.incap:incap-processor:${Versions.incap}"

    fun DependencyHandler.annotationProcessing() = apply {
        kapt(autoService)
        implementation(autoService)
        kapt(fixGuavaPackage)
        implementation(fixGuavaPackage)
        kapt(incapProcessor)
        compileOnly(incap)
    }
}

internal object CoreModules {

    fun DependencyHandler.uiModule() = apply {
        implementation(project(":ui"))
    }

    fun DependencyHandler.koinModules() = apply {
        implementation(project(":koin-module-annotation"))
        kapt(project(":koin-module-annotation-processor"))
    }
}