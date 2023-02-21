package io.rocketlab.screen.herosquad.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.screen.herosquad.presentation.viewmodel.HeroSquadViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@KoinModule
val heroSquadModule = module {

    viewModel { HeroSquadViewModel(get(), get()) }
}