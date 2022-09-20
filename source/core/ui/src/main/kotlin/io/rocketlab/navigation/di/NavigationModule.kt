package io.rocketlab.navigation.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.navigation.Navigator
import org.koin.dsl.module

@KoinModule
val navigationModule = module {

    single { Navigator() }
}