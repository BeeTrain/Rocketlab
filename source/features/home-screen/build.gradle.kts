import MiscDependencies.coil
import MiscDependencies.lottie

plugins {
    id(Plugins.featureModule)
    id(Plugins.kotlinKapt)
}

dependencies {
    implementation(projects.auth)

    coil()
    lottie()
}