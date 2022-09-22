package io.rocketlab.app.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.app.di.provider.EnvironmentProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@KoinModule
val appModule = module {

    single { EnvironmentProvider.provide(androidContext()) }
}