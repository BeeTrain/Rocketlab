import ComposeDependencies.compose
import extension.setupCompose

plugins {
    id(Plugins.androidModule)
    id(Plugins.kotlinKapt)
}

android {
    namespace = "io.rocketlab.ui"
}

setupCompose()

dependencies {
    implementation(projects.utils)

    compose()
}