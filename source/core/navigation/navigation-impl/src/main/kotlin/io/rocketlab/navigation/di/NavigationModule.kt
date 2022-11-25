package io.rocketlab.navigation.di

import androidx.lifecycle.SavedStateHandle
import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.navigation.NavigatorImpl
import io.rocketlab.navigation.api.Navigator
import org.koin.dsl.module

@KoinModule
val navigationModule = module {

    single<Navigator> { NavigatorImpl(get()) }

    single { SavedStateHandle() }
}