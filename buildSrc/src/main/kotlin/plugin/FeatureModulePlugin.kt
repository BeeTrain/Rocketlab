package plugin

import AndroidDependencies.core
import AndroidDependencies.lifecycle
import ComposeDependencies.compose
import Plugins
import extension.setupCompose
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class FeatureModulePlugin : AndroidModulePlugin() {

    override fun apply(project: Project) {
        super.apply(project)
        project.run {
            applyPlugins()
            setupCompose()
            applyDependencies()
        }
    }

    private fun Project.applyPlugins() {
        plugins.run {
            apply(plugin = Plugins.kotlinKapt)
        }
    }

    private fun Project.applyDependencies() {
        dependencies.apply {
            core()
            lifecycle()
            compose()
        }
    }
}