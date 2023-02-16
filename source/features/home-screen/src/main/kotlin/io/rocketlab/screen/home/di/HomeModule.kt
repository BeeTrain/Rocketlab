package io.rocketlab.screen.home.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.screen.home.presentation.viewmodel.HomeScreenViewModel
import io.rocketlab.screen.home.presentation.viewmodel.mapper.ScreenStateMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@KoinModule
val homeModule = module {

    viewModel { HomeScreenViewModel(get(), get(), get()) }

    factory { ScreenStateMapper(get()) }
}