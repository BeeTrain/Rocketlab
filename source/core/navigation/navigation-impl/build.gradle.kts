import AndroidDependencies.navigationLibraries
import ComposeDependencies.compose
import DIDependencies.koin
import extension.applyFeatureModules
import extension.setupCompose

plugins {
    id(Plugins.androidModule)
    id(Plugins.kotlinKapt)
}

android {
    namespace = "io.rocketlab.navigation"
}

setupCompose()

dependencies {
    implementation(projects.arch)
    implementation(projects.navigationApi)

    applyFeatureModules()

    koin()
    compose()
    navigationLibraries()
}