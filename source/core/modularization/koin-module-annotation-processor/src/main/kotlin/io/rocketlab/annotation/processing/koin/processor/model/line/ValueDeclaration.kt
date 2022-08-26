package io.rocketlab.annotation.processing.koin.processor.model.line

import io.rocketlab.annotation.processing.koin.processor.model.Line

internal class ValueDeclaration(private val name: String) : Line {

    override fun toString() = "val $name = "
}