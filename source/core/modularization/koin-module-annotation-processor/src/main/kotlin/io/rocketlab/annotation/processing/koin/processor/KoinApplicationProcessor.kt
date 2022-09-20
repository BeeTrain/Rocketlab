package io.rocketlab.annotation.processing.koin.processor

import com.google.auto.service.AutoService
import io.rocketlab.annotation.processing.annotation.KoinInitializer
import io.rocketlab.annotation.processing.koin.processor.extension.KAPT_OPTION
import io.rocketlab.annotation.processing.koin.processor.generator.KoinApplicationModuleGenerator
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedOptions
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
@SupportedOptions(KAPT_OPTION)
class KoinApplicationProcessor : AbstractProcessor() {

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(KoinInitializer::class.java.name)
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
        KoinApplicationModuleGenerator(this, processingEnv).generate()
    }
}