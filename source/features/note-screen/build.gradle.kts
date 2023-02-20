import MiscDependencies.lottie

plugins {
    id(Plugins.featureModule)
    id(Plugins.kotlinKapt)
}

android {
    namespace = "io.rocketlab.screen.note"
}

dependencies {
    implementation(projects.storage)

    lottie()
}