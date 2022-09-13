package io.rocketlab.splash.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.splash.presentation.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@KoinModule
val splashModule = module {

    viewModel { SplashViewModel(get()) }
}