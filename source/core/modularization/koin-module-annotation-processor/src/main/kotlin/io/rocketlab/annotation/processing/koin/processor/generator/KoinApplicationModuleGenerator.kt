package io.rocketlab.annotation.processing.koin.processor.generator

import io.rocketlab.annotation.processing.annotation.KoinInitializer
import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.annotation.processing.koin.processor.extension.KAPT_OPTION
import io.rocketlab.annotation.processing.koin.processor.extension.KOIN_MODULES_FILE_NAME
import io.rocketlab.annotation.processing.koin.processor.extension.KOIN_PROCESSOR_PACKAGE
import io.rocketlab.annotation.processing.koin.processor.extension.KOIN_PROVIDER_SUFFIX
import io.rocketlab.annotation.processing.koin.processor.extension.KOTLIN_FILE_EXT
import io.rocketlab.annotation.processing.koin.processor.extension.formatName
import io.rocketlab.annotation.processing.koin.processor.extension.toKoinModuleProvider
import io.rocketlab.annotation.processing.koin.processor.extension.toProviderName
import io.rocketlab.annotation.processing.koin.processor.model.koin.module.KoinModulesFile
import io.rocketlab.annotation.processing.koin.processor.model.koin.provider.KoinModuleProvider
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment

internal class KoinApplicationModuleGenerator(
    private val roundEnvironment: RoundEnvironment,
    private val processingEnvironment: ProcessingEnvironment
) {

    fun generate() {
        val koinApplicationElement = roundEnvironment.getElementsAnnotatedWith(KoinInitializer::class.java)
        val applicationModules = roundEnvironment.getApplicationModulesProviders()
        val modulesProviders = getModulesProviders()
        val koinProviders = applicationModules + modulesProviders

        println("koinApplicationElement: $koinApplicationElement")
        println("applicationModules: $applicationModules")
        println("modulesProviders: $modulesProviders")
        println("koinProviders: $koinProviders")

        if (koinApplicationElement.isNotEmpty() && koinProviders.isNotEmpty()) {
            generateKoinModulesFile(koinProviders)
        }
    }

    private fun generateKoinModulesFile(modulesProviders: List<KoinModuleProvider>) {
        val koinModulesFile = KoinModulesFile(
            processingEnvironment.options[KAPT_OPTION],
            KOIN_MODULES_FILE_NAME,
            modulesProviders
        )
        koinModulesFile.generate()
    }

    private fun RoundEnvironment.getApplicationModulesProviders(): List<KoinModuleProvider> {
        return getElementsAnnotatedWith(KoinModule::class.java)
            ?.map { it.formatName() }
            ?.map { it.toProviderName() }
            ?.map { it.removeSuffix(KOTLIN_FILE_EXT) }
            ?.map { KoinModuleProvider(it) }
            .orEmpty()
    }

    private fun getModulesProviders(): List<KoinModuleProvider> {
        return processingEnvironment.elementUtils
            .getPackageElement(KOIN_PROCESSOR_PACKAGE)
            ?.enclosedElements
            ?.filter { it.simpleName.endsWith("${KOIN_PROVIDER_SUFFIX}Kt") }
            ?.map { it.toKoinModuleProvider() }
            ?: emptyList()
    }
}