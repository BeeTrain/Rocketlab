import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val javaVersion = JavaVersion.VERSION_17
val kotlinVersion = "1.9.0"
val buildGradlePluginVersion = "8.1.0"
val googleServicesVersion = "4.3.15"
val appDistributionVersion = "4.0.0"
val crashlyticsPlugin = "2.9.7"

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