package io.rocketlab.screen.home.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.screen.home.presentation.viewmodel.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@KoinModule
val homeModule = module {

    viewModel { HomeScreenViewModel(get()) }
}