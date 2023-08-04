package io.rocketlab.screen.profile.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.screen.profile.presentation.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@KoinModule
val profileModule = module {

    viewModel { ProfileViewModel(get(), get()) }
}