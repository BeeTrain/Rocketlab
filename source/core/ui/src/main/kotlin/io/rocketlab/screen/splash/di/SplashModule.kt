package io.rocketlab.screen.splash.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.screen.splash.presentation.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@KoinModule
val splashModule = module {

    viewModel { SplashViewModel(get()) }
}