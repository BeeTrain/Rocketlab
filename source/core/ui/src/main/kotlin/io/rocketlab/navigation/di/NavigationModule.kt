package io.rocketlab.navigation.di

import androidx.lifecycle.SavedStateHandle
import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.navigation.Navigator
import org.koin.dsl.module

@KoinModule
val navigationModule = module {

    single { Navigator(get()) }

    single { SavedStateHandle() }
}