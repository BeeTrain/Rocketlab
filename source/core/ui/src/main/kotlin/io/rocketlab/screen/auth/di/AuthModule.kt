package io.rocketlab.screen.auth.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.screen.auth.presentation.mapper.SigningErrorMapper
import io.rocketlab.screen.auth.presentation.signin.presentation.viewmodel.SignInViewModel
import io.rocketlab.screen.auth.presentation.signup.presentation.viewmodel.SignUpViewModel
import io.rocketlab.screen.auth.presentation.validation.EmailValidator
import io.rocketlab.screen.auth.presentation.validation.PasswordValidator
import io.rocketlab.screen.auth.presentation.validation.SigningValidator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@KoinModule
val authModule = module {

    viewModel { SignUpViewModel(get(), get(), get(), get()) }
    viewModel { SignInViewModel(get(), get(), get(), get()) }

    factory { SigningErrorMapper(get()) }

    factory { EmailValidator(get()) }
    factory { PasswordValidator(get()) }
    factory { SigningValidator(get(), get()) }
}