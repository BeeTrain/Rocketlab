import DIDependencies.koin
import FirebaseDependencies.firebase
import extension.setupCompose

plugins {
    id(Plugins.androidModule)
    id(Plugins.kotlinKapt)
}

android {
    namespace = "io.rocketlab.service.auth"
}

setupCompose()

dependencies {
    implementation(projects.utils)

    koin()
    firebase()
}