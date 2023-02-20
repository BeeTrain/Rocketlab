package io.rocketlab.screen.herosquad.presentation.viewmodel

import io.rocketlab.arch.extension.action
import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.navigation.api.Navigator

class HeroSquadViewModel(
    private val navigator: Navigator
) : BaseViewModel() {

    val onBackPressedAction = action<Unit> { onBackPressed() }

    private fun onBackPressed() {
        navigator.navigateUp()
    }
}