package io.rocketlab.screen.home.presentation.viewmodel

import io.rocketlab.arch.extension.action
import io.rocketlab.arch.extension.state
import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.screen.home.domain.interactor.FeaturesInteractor
import io.rocketlab.screen.home.domain.model.Feature
import io.rocketlab.screen.home.presentation.model.HomeScreenState
import io.rocketlab.screen.home.presentation.viewmodel.mapper.ScreenStateMapper
import io.rocketlab.screen.home.presentation.viewmodel.navigator.HomeScreenNavigator
import io.rocketlab.service.auth.AuthService

class HomeScreenViewModel(
    private val featuresInteractor: FeaturesInteractor,
    private val authService: AuthService,
    private val stateMapper: ScreenStateMapper,
    private val navigator: HomeScreenNavigator
) : BaseViewModel() {

    val onProfileHeaderClickAction = action<Unit> { onHeaderClick() }
    val onFeatureClickedAction = action<Feature> { navigator.openFeature(it) }

    val screenState = state(prepareState())

    private fun onHeaderClick() {
        if (authService.isLogged) {
            navigator.openProfile()
        } else {
            navigator.openAuth()
        }
    }

    private fun prepareState(): HomeScreenState {
        return stateMapper.map(
            user = authService.user,
            features = featuresInteractor.loadFeatures()
        )
    }
}