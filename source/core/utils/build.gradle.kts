import DIDependencies.koin

plugins {
    id(Plugins.androidModule)
    id(Plugins.kotlinKapt)
}

android {
    namespace = "io.rocketlab.utils"
}

dependencies {
    koin()
}