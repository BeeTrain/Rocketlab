import AndroidDependencies.navigationLibraries

plugins {
    id(Plugins.apiModule)
    id(Plugins.kotlinKapt)
}

android {
    namespace = "io.rocketlab.navigation.api"
}

dependencies {
    navigationLibraries()
}