package io.rocketlab.annotation.processing.koin.processor.model.koin.module

import io.rocketlab.annotation.processing.koin.processor.extension.KOIN_PROCESSOR_PACKAGE
import io.rocketlab.annotation.processing.koin.processor.model.koin.provider.KoinModuleProvider
import io.rocketlab.annotation.processing.koin.processor.model.koin.file.KoinModulesList
import io.rocketlab.annotation.processing.koin.processor.model.koin.file.ListOfModule
import io.rocketlab.annotation.processing.koin.processor.model.line.Empty
import io.rocketlab.annotation.processing.koin.processor.model.line.NewLine
import io.rocketlab.annotation.processing.koin.processor.model.line.Package
import io.rocketlab.annotation.processing.koin.processor.model.line.ValueDeclaration

internal class KoinModulesFileBody(
    private val koinModuleFiles: List<KoinModuleProvider>
) {

    override fun toString(): String {
        return StringBuilder()
            .append(Package(KOIN_PROCESSOR_PACKAGE))
            .append(NewLine(Empty()))
            .append(NewLine(ValueDeclaration("koinModules")))
            .append(ListOfModule(KoinModulesList(koinModuleFiles)))
            .toString()
    }
}