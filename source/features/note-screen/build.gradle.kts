import MiscDependencies.lottie

plugins {
    id(Plugins.featureModule)
    id(Plugins.kotlinKapt)
}

dependencies {
    implementation(projects.storage)

    lottie()
}