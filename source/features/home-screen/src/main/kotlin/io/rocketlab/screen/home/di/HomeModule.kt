package io.rocketlab.screen.home.di

import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.screen.home.data.repository.FeaturesRepository
import io.rocketlab.screen.home.domain.interactor.FeaturesInteractor
import io.rocketlab.screen.home.presentation.viewmodel.HomeScreenViewModel
import io.rocketlab.screen.home.presentation.viewmodel.mapper.FeaturesMapper
import io.rocketlab.screen.home.presentation.viewmodel.mapper.ScreenStateMapper
import io.rocketlab.screen.home.presentation.viewmodel.navigator.HomeScreenNavigator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@KoinModule
val homeModule = module {

    viewModel { HomeScreenViewModel(get(), get(), get(), get()) }

    factory { HomeScreenNavigator(get()) }

    factory { ScreenStateMapper(get(), get()) }

    factory { FeaturesInteractor(get()) }

    factory { FeaturesRepository() }

    factory { FeaturesMapper(get()) }
}