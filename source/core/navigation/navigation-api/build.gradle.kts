import AndroidDependencies.navigationLibraries

plugins {
    id(Plugins.apiModule)
    id(Plugins.kotlinKapt)
}

dependencies {
    navigationLibraries()
}