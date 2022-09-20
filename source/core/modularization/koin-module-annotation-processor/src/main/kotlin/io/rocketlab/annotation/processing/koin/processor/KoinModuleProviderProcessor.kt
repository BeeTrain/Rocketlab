package io.rocketlab.annotation.processing.koin.processor

import com.google.auto.service.AutoService
import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.annotation.processing.koin.processor.extension.KAPT_OPTION
import io.rocketlab.annotation.processing.koin.processor.generator.KoinModuleProviderGenerator
import net.ltgt.gradle.incap.IncrementalAnnotationProcessor
import net.ltgt.gradle.incap.IncrementalAnnotationProcessorType
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedOptions
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
@SupportedOptions(KAPT_OPTION)
@IncrementalAnnotationProcessor(IncrementalAnnotationProcessorType.AGGREGATING)
class KoinModuleProviderProcessor : AbstractProcessor() {

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(KoinModule::class.java.name)
    }

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        environment: RoundEnvironment?
    ): Boolean {
        if (environment?.processingOver() == true) return true

        environment?.generateFile()
        return true
    }

    private fun RoundEnvironment.generateFile() {
        KoinModuleProviderGenerator(this, processingEnv).generate()
    }
}