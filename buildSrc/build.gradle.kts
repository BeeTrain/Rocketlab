import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val javaVersion = JavaVersion.VERSION_11
val kotlinVersion = "1.7.20"
val buildGradlePluginVersion = "7.3.1"
val googleServicesVersion = "4.3.14"
val appDistributionVersion = "3.1.1"
val crashlyticsPlugin = "2.9.2"

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

project.tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = javaVersion.toString()
        apiVersion = kotlinVersion
    }
}

dependencies {
    compileOnly(gradleApi())

    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    implementation("com.android.tools.build:gradle:$buildGradlePluginVersion")

    implementation("com.google.gms:google-services:$googleServicesVersion")
    implementation("com.google.firebase:firebase-appdistribution-gradle:$appDistributionVersion")
    implementation("com.google.firebase:firebase-crashlytics-gradle:$crashlyticsPlugin")
}

gradlePlugin {
    plugins.register("application-module-plugin") {
        id = "application-module-plugin"
        implementationClass = "plugin.ApplicationModulePlugin"
    }
    plugins.register("api-module-plugin") {
        id = "api-module-plugin"
        implementationClass = "plugin.ApiModulePlugin"
    }
    plugins.register("feature-module-plugin") {
        id = "feature-module-plugin"
        implementationClass = "plugin.FeatureModulePlugin"
    }
    plugins.register("android-module-plugin") {
        id = "android-module-plugin"
        implementationClass = "plugin.AndroidModulePlugin"
    }
    plugins.register("kotlin-module-plugin") {
        id = "kotlin-module-plugin"
        implementationClass = "plugin.KotlinModulePlugin"
    }
}