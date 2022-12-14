/** Attach modules **/
attachAllModules()

internal object Constants {
    const val MAX_DEPTH = 10
}

fun attachAllModules() {
    attachBuildModules()
    attachProjectModules()
}

fun attachBuildModules() {
    var buildModulesCount = 0
    println("attach build modules...")
    rootDir.walk()
        .maxDepth(Constants.MAX_DEPTH)
        .filter { !it.startsWith(".") && it.name != "build" }
        .filter { it.isBuildModule }
        .forEach {
            it.attachBuildModule()
            buildModulesCount++
        }
    println("$buildModulesCount build module(s) attached.\n")
}

fun attachProjectModules() {
    var projectModulesCount = 0
    println("attach project modules...")
    rootDir.walk()
        .maxDepth(Constants.MAX_DEPTH)
        .filter { !it.startsWith(".") && it.name != "build" }
        .filter { it.isProjectModule }
        .forEach {
            it.attachModule()
            projectModulesCount++
        }
    println("$projectModulesCount project module(s) attached.\n")
}

fun File.attachModule() {
    val moduleName = ":$name"
    include(moduleName)
    project(moduleName).projectDir = file(path)
    println("project module \"$moduleName\" attached")
}

fun File.attachBuildModule() {
    val moduleName = ":$name"
    includeBuild(absolutePath)
    println("build module \"$moduleName\" attached")
}

val File.isGradleModule: Boolean
    get() {
        return isDirectory &&
            absolutePath != rootDir.path &&
            isBuildSrcModule.not() &&
            isBuildGradleConfigExists
    }

val File.isProjectModule: Boolean
    get() {
        return isGradleModule &&
            !name.contains("build") &&
            isBuildSrcModule.not()
    }

val File.isBuildModule: Boolean
    get() {
        return isGradleModule &&
            name.contains("build") &&
            isBuildSrcModule.not()
    }

val File.isBuildSrcModule: Boolean
    get() {
        return name == "buildSrc" &&
            isDirectory &&
            isBuildGradleConfigExists
    }

val File.isBuildGradleConfigExists: Boolean
    get() {
        return file("$absolutePath/build.gradle.kts").exists() ||
            file("$absolutePath/build.gradle").exists()
    }