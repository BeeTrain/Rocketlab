package io.rocketlab.app.presentation.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.app.presentation.viewmodel.AppViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@KoinModule
val applicationModule = module {

    viewModel { AppViewModel(get()) }
}