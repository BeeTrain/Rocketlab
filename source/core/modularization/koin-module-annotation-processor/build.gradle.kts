import AnnotationProcessingDependencies.annotationProcessing

plugins {
    id(Plugins.javaLibrary)
    id(Plugins.kotlinJVM)
    id(Plugins.kotlinKapt)
}

java.sourceCompatibility = AppConfig.javaVersion
java.targetCompatibility = AppConfig.javaVersion

dependencies {
    kapt(projects.koinModuleAnnotation)
    compileOnly(projects.koinModuleAnnotation)
    implementation(projects.koinModuleAnnotation)

    annotationProcessing()
}