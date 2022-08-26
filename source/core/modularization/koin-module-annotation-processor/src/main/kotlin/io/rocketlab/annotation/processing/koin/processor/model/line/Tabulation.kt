package io.rocketlab.annotation.processing.koin.processor.model.line

import io.rocketlab.annotation.processing.koin.processor.model.Line

internal class Tabulation(private val line: String) : Line {

    override fun toString() = "\t$line"
}