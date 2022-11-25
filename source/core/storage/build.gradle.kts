import DIDependencies.koin
import DatabaseDependencies.room

plugins {
    id(Plugins.androidModule)
    id(Plugins.kotlinKapt)
}

dependencies {
    koin()
    room()
}