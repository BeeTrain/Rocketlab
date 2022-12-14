import ComposeDependencies.compose
import extension.setupCompose

plugins {
    id(Plugins.androidModule)
    id(Plugins.kotlinKapt)
}

setupCompose()

dependencies {
    implementation(projects.utils)

    compose()
}