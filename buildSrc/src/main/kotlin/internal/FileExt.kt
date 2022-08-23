package internal

import java.io.File

internal val File.isGradleProjectDir
    get() = listFiles()?.any { it.name == "build.gradle" || it.name == "build.gradle.kts" } ?: false

internal val File.isBuildModule: Boolean
    get() = name.contains("build")

internal val File.isGradleModule: Boolean
    get() {
        return name != "buildSrc" &&
            isDirectory &&
            isBuildGradleConfigExists
    }

internal val File.isBuildGradleConfigExists: Boolean
    get() {
        return File("$absolutePath/build.gradle.kts").exists() ||
            File("$absolutePath/build.gradle").exists()
    }