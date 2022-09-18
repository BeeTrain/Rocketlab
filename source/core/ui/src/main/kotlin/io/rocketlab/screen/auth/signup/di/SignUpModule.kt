package io.rocketlab.screen.auth.signup.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.screen.auth.signup.presentation.viewmodel.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@KoinModule
val signUpModule = module {

    viewModel { SignUpViewModel(get()) }
}