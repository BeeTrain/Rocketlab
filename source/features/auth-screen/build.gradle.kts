import FirebaseDependencies.firebase

plugins {
    id(Plugins.featureModule)
    id(Plugins.kotlinKapt)
}

android {
    namespace = "io.rocketlab.screen.auth"
}

dependencies {
    implementation(projects.auth)

    firebase()
}