import org.gradle.api.JavaVersion

private const val DEBUG_KEYSTORE_FILE_PATH = "config/signing/key/rocketlab-debug.jks"

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
    val signing: SigningConfig = SigningConfig.Debug
) {
    DEBUG(title = "debug", isMinifyEnabled = false),
    RELEASE(title = "release", isMinifyEnabled = true)
}

sealed class SigningConfig(
    val storeFilePath: String,
    val storePassword: String,
    val keyAlias: String,
    val keyPassword: String
) {

    object Debug : SigningConfig(
        storeFilePath = DEBUG_KEYSTORE_FILE_PATH,
        storePassword = "81sffB1qJkFjULJhpHbF",
        keyAlias = "rocketlab-debug",
        keyPassword = "81sffB1qJkFjULJhpHbF"
    )
}