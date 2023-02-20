import DIDependencies.koin
import DatabaseDependencies.room

plugins {
    id(Plugins.androidModule)
    id(Plugins.kotlinKapt)
}

android {
    namespace = "io.rocketlab.storage"
}

dependencies {
    koin()
    room()
}