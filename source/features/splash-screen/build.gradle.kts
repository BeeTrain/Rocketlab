plugins {
    id(Plugins.featureModule)
    id(Plugins.kotlinKapt)
}

android {
    namespace = "io.rocketlab.screen.splash"
}

dependencies {
    implementation(projects.auth)
}