package io.rocketlab.screen.home.presentation.viewmodel

import io.rocketlab.arch.extension.action
import io.rocketlab.arch.extension.state
import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.navigation.api.Destination
import io.rocketlab.navigation.api.Navigator
import io.rocketlab.screen.home.domain.interactor.FeaturesInteractor
import io.rocketlab.screen.home.domain.model.Feature
import io.rocketlab.screen.home.presentation.model.HomeScreenState
import io.rocketlab.screen.home.presentation.viewmodel.mapper.ScreenStateMapper
import io.rocketlab.service.auth.AuthService

class HomeScreenViewModel(
    private val featuresInteractor: FeaturesInteractor,
    private val authService: AuthService,
    private val stateMapper: ScreenStateMapper,
    private val navigator: Navigator
) : BaseViewModel() {

    val onProfileHeaderClickAction = action<Unit> { openAuth() }
    val onFeatureClickedAction = action<Feature> { openFeature(it) }

    val screenState = state(prepareState())

    private fun openFeature(feature: Feature) {
        when (feature) {
            Feature.NOTES -> openNotes()
        }
    }

    private fun openNotes() {
        navigator.navigate(Destination.NotesList)
    }

    private fun openAuth() {
        if (authService.isLogged) return

        navigator.navigate(Destination.SignIn)
    }

    private fun prepareState(): HomeScreenState {
        return stateMapper.map(
            user = authService.user,
            features = featuresInteractor.loadFeatures()
        )
    }
}