package io.rocketlab.annotation.processing.koin.processor.model.koin.file

import io.rocketlab.annotation.processing.koin.processor.model.koin.provider.KoinModuleProvider

internal class KoinModulesList(private val koinModules: List<KoinModuleProvider>) {

    override fun toString(): String {
        return StringBuilder()
            .appendKoinModulesList()
            .toString()
    }

    private fun StringBuilder.appendKoinModulesList(): StringBuilder {
        koinModules
            .map { KoinModuleLine(it.name) }
            .forEach { append(it) }
        return this
    }
}