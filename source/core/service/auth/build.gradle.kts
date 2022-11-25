import DIDependencies.koin
import FirebaseDependencies.firebase
import extension.setupCompose

plugins {
    id(Plugins.androidModule)
    id(Plugins.kotlinKapt)
}

setupCompose()

dependencies {
    implementation(projects.utils)

    koin()
    firebase()
}