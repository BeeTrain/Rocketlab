import ComposeDependencies.compose
import extension.setupCompose

plugins {
    id(Plugins.androidModule)
}

android {
    namespace = "io.rocketlab.arch"
}

setupCompose()

dependencies {
    implementation(projects.utils)

    compose()
}