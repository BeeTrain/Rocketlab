package io.rocketlab.annotation.processing.koin.processor.model.koin.file

internal class ListOfModule(private val listItems: String) {

    constructor(koinModulesList: KoinModulesList) : this(koinModulesList.toString())

    override fun toString() = "listOf<org.koin.core.module.Module>( $listItems \n)"
}