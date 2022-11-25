plugins {
    id(Plugins.featureModule)
    id(Plugins.kotlinKapt)
}

dependencies {
    implementation(projects.auth)
}