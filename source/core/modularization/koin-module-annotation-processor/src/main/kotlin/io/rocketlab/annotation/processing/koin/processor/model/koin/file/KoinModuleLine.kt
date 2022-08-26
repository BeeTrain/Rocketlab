package io.rocketlab.annotation.processing.koin.processor.model.koin.file

import io.rocketlab.annotation.processing.koin.processor.model.line.NewLine
import io.rocketlab.annotation.processing.koin.processor.model.line.Tabulation

internal class KoinModuleLine(private val koinModule: String) {

    override fun toString(): String {
        return NewLine(
            Tabulation("${koinModule},")
        ).toString()
    }
}