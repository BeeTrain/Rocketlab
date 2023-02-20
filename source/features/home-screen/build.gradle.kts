import MiscDependencies.coil
import MiscDependencies.lottie

plugins {
    id(Plugins.featureModule)
    id(Plugins.kotlinKapt)
}

android {
    namespace = "io.rocketlab.screen.home"
}

dependencies {
    implementation(projects.auth)

    coil()
    lottie()
}