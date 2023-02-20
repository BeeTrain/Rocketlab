package io.rocketlab.screen.home.presentation.viewmodel.navigator

import io.rocketlab.navigation.api.Destination
import io.rocketlab.navigation.api.Navigator
import io.rocketlab.screen.home.domain.model.Feature

class HomeScreenNavigator(
    private val navigator: Navigator
) {

    fun openAuth() {
        navigator.navigate(Destination.SignIn)
    }

    fun openFeature(feature: Feature) {
        val destination = when (feature) {
            Feature.NOTES -> Destination.NotesList
            Feature.HERO_SQUAD -> Destination.HeroSquad
        }
        navigator.navigate(destination)
    }
}