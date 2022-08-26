plugins {
    id(Plugins.javaLibrary)
    id(Plugins.kotlinJVM)
    id(Plugins.kotlinKapt)
}

java.sourceCompatibility = AppConfig.javaVersion
java.targetCompatibility = AppConfig.javaVersion