import MiscDependencies.coil

plugins {
    id(Plugins.featureModule)
    id(Plugins.kotlinKapt)
}

android {
    namespace = "io.rocketlab.screen.settings"
}

dependencies {
    implementation(projects.storage)

    coil()
}