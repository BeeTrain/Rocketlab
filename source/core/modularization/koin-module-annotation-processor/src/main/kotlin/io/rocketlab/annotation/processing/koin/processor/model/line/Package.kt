package io.rocketlab.annotation.processing.koin.processor.model.line

import io.rocketlab.annotation.processing.koin.processor.model.Line

internal class Package(private val packageName: String) : Line {

    override fun toString() = "package $packageName"
}