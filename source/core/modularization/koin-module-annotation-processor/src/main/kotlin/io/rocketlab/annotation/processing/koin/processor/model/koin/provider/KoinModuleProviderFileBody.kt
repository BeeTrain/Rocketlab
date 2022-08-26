package io.rocketlab.annotation.processing.koin.processor.model.koin.provider

import io.rocketlab.annotation.processing.koin.processor.extension.KOIN_PROCESSOR_PACKAGE
import io.rocketlab.annotation.processing.koin.processor.extension.KOIN_PROVIDER_SUFFIX
import io.rocketlab.annotation.processing.koin.processor.model.koin.file.KoinModuleFile
import io.rocketlab.annotation.processing.koin.processor.model.koin.file.KoinModuleImport
import io.rocketlab.annotation.processing.koin.processor.model.line.Empty
import io.rocketlab.annotation.processing.koin.processor.model.line.NewLine
import io.rocketlab.annotation.processing.koin.processor.model.line.Package
import io.rocketlab.annotation.processing.koin.processor.model.line.ValueDeclaration

internal class KoinModuleProviderFileBody(private val koinModuleFile: KoinModuleFile) {

    override fun toString(): String {
        return StringBuilder()
            .append(Package(KOIN_PROCESSOR_PACKAGE))
            .append(NewLine(Empty()))
            .append(KoinModuleImport(koinModuleFile))
            .append(NewLine(Empty()))
            .append(NewLine(ValueDeclaration("${koinModuleFile.moduleName}$KOIN_PROVIDER_SUFFIX")))
            .append(koinModuleFile.moduleName)
            .toString()
    }
}