package io.rocketlab.annotation.processing.koin.processor.extension

internal const val KOTLIN_FILE_EXT = ".kt"
internal const val KOIN_PROCESSOR_PACKAGE = "koin.annotation.processing.module"
internal const val KOIN_PROVIDER_SUFFIX = "Provider"
internal const val KAPT_OPTION = "kapt.kotlin.generated"
internal const val KOIN_MODULES_FILE_NAME = "KoinModules${KOTLIN_FILE_EXT}"

internal fun String.toProviderName(): String {
    return StringBuilder()
        .append(this)
        .append(KOIN_PROVIDER_SUFFIX)
        .append(KOTLIN_FILE_EXT)
        .toString()
}