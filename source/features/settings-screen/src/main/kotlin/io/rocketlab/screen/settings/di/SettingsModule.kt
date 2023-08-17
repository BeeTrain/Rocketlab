package io.rocketlab.screen.settings.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.screen.settings.data.repository.SettingsRepository
import io.rocketlab.screen.settings.domain.interactor.SettingsInteractor
import io.rocketlab.screen.settings.presentation.viewmodel.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@KoinModule
val settingsModule = module {

    viewModel { SettingsViewModel(get(), get()) }

    single { SettingsRepository(get()) }

    factory { SettingsInteractor(get()) }
}