package io.rocketlab.annotation.processing.koin.processor.model.line

import io.rocketlab.annotation.processing.koin.processor.model.Line

internal class NewLine(private val line: Line) : Line {

    override fun toString() = "\n$line"
}