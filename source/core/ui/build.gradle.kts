import AndroidDependencies.navigation
import ComposeDependencies.compose
import DIDependencies.koin
import FirebaseDependencies.firebase
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
}