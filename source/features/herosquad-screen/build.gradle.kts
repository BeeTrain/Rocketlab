import MiscDependencies.lottie

plugins {
    id(Plugins.featureModule)
    id(Plugins.kotlinKapt)
}

android {
    namespace = "io.rocketlab.screen.herosquad"
}

dependencies {
    implementation(projects.storage)

    lottie()
}