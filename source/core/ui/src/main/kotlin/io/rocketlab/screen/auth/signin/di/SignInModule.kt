package io.rocketlab.screen.auth.signin.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.screen.auth.signin.presentation.viewmodel.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@KoinModule
val signInModule = module {
    viewModel { SignInViewModel(get()) }
}