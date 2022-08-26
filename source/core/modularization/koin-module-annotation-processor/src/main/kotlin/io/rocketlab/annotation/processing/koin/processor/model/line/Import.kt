package io.rocketlab.annotation.processing.koin.processor.model.line

import io.rocketlab.annotation.processing.koin.processor.model.Line

internal class Import(private val importName: String) : Line {

    override fun toString() = "import $importName"
}