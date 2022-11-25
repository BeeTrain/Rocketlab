import DIDependencies.koin

plugins {
    id(Plugins.androidModule)
    id(Plugins.kotlinKapt)
}

dependencies {
    koin()
}