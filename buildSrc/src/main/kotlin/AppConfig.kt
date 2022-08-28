import org.gradle.api.JavaVersion

@Suppress("unused")
object AppConfig {
    const val applicationId = "io.rocketlab"

    const val compileSdkVersion = 33
    const val minSdkVersion = 26
    const val targetSdkVersion = 33

    val javaVersion = JavaVersion.VERSION_11
}

enum class BuildTypes(
    val title: String,
    val isMinifyEnabled: Boolean,
) {
    DEBUG(title = "debug", isMinifyEnabled = false),
    RELEASE(title = "release", isMinifyEnabled = true)
}