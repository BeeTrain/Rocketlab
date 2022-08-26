package io.rocketlab.annotation.processing.koin.processor.model.koin.file

internal class KoinModuleFile(val packageName: String, val moduleName: String) {

    override fun toString() = "$packageName.$moduleName"
}