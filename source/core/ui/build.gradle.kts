import AndroidDependencies.navigation
import ComposeDependencies.compose
import extension.setupCompose

plugins {
    id(Plugins.androidModule)
}

setupCompose()

dependencies {
    compose()
    navigation()
}