package io.rocketlab.annotation.processing.koin.processor.model.koin.provider

import io.rocketlab.annotation.processing.koin.processor.model.koin.file.KoinModuleFile
import java.io.File

internal class KoinModuleProviderFIle(
    private val path: String?,
    private val name: String,
    koinModuleFile: KoinModuleFile
) {

    private val fileBody = KoinModuleProviderFileBody(koinModuleFile)

    fun generate() {
        val bodyString = fileBody.toString()
        val file = File(path, name)
        file.writeText(bodyString)
    }
}