import ComposeDependencies.compose
import extension.setupCompose

plugins {
    id(Plugins.androidModule)
}

setupCompose()

dependencies {
    implementation(projects.utils)

    compose()
}