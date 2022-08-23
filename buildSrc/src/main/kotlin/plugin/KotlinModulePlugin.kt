package plugin

import AppConfig
import KotlinDependencies.coroutines
import KotlinDependencies.stdLib
import Plugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import kotlinVersion

class KotlinModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.run {
            applyPlugins()
            applyTasks()
            applyDependencies()
        }
    }

    private fun Project.applyPlugins() {
        plugins.run {
            apply(Plugins.kotlinAndroid)
        }
    }

    private fun Project.applyTasks() {
        tasks.withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = AppConfig.javaVersion.toString()
                apiVersion = kotlinVersion.removePatchVersion()
            }
        }
    }

    private fun Project.applyDependencies() {
        dependencies {
            stdLib()
            coroutines()
        }
    }

    private fun String.removePatchVersion(): String {
        val separator = "."
        val versionSplit = split(separator)

        return when {
            versionSplit.size <= 2 -> this
            else -> versionSplit.subList(0, 2).joinToString(separator = separator)
        }
    }
}