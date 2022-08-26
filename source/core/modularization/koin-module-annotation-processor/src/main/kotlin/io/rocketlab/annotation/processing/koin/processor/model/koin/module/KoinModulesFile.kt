package io.rocketlab.annotation.processing.koin.processor.model.koin.module

import io.rocketlab.annotation.processing.koin.processor.model.koin.provider.KoinModuleProvider
import java.io.File

internal class KoinModulesFile(
    private val path: String?,
    private val name: String,
    koinModuleProviders: List<KoinModuleProvider>
) {

    private val fileBody = KoinModulesFileBody(koinModuleProviders)

    fun generate() {
        val bodyString = fileBody.toString()
        val file = File(path, name)
        file.writeText(bodyString)
    }
}