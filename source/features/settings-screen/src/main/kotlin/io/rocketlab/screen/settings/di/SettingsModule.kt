package io.rocketlab.screen.settings.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.screen.settings.presentation.viewmodel.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@KoinModule
val settingsModule = module {

    viewModel { SettingsViewModel(get()) }
}