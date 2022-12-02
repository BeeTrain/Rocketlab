package internal

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File
import java.util.Locale

private const val MAX_DEPTH = 10
private const val ANDROID_EXTENSION_NAME = "android"
private const val QUALITY_REPORT_DIR = "build/quality"

internal val Project.applicationExtension: ApplicationExtension
    get() = extensions.findByName(ANDROID_EXTENSION_NAME) as ApplicationExtension

internal val Project.libraryExtension: LibraryExtension
    get() = extensions.findByName(ANDROID_EXTENSION_NAME) as LibraryExtension

internal val Project.androidExtension: CommonExtension<*, *, *, *>
    get() = extensions.findByName(ANDROID_EXTENSION_NAME) as CommonExtension<*, *, *, *>

internal fun Project.configureProjectModules() {
    val projectName = name
    dependencies.apply {
        rootDir.walk()
            .maxDepth(MAX_DEPTH)
            .filter { it.isGradleModule && it.isBuildModule.not() && it.name != projectName && it.name != "buildSrc" }
            .forEach { module ->
                val moduleName = ":${module.name}"
                implementation(project(moduleName))
                println("module \"$moduleName\" attached to $projectName")
            }
    }
}

private fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase(Locale.getDefault()).contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

internal val Project.applicationProguardFiles: List<File>
    get() = arrayOf(
        arrayOf(androidExtension.getDefaultProguardFile("proguard-android-optimize.txt")),
        File("${rootDir.path}/config/signing/proguard").listFiles()
    ).flatten()

internal fun Project.setupComposableMetrics() {
    rootProject.subprojects {
        tasks.withType<KotlinCompile>().configureEach {
            if (project.findProperty("composeCompilerMetricsEnabled") == "true") {
                println("composeCompilerMetricsEnabled=true, need to build report")
                kotlinOptions {
                    freeCompilerArgs = freeCompilerArgs + setOf(
                        "-P",
                        "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                            project.buildDir.absolutePath + "/compose_metrics"
                    )
                    freeCompilerArgs = freeCompilerArgs + setOf(
                        "-P",
                        "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                            project.buildDir.absolutePath + "/compose_metrics"
                    )
                }
            }
        }
    }
}