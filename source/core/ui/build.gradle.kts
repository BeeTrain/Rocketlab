import AndroidDependencies.navigation
import ComposeDependencies.compose
import DIDependencies.koin
import DatabaseDependencies.room
import FirebaseDependencies.firebase
import MiscDependencies.coil
import MiscDependencies.lottie
import extension.setupCompose

plugins {
    id(Plugins.androidModule)
    id(Plugins.kotlinKapt)
}

setupCompose()

dependencies {
    koin()
    firebase()
    compose()
    navigation()

    coil()
    room()
    lottie()
}