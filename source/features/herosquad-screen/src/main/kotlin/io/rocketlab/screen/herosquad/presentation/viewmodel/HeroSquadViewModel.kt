package io.rocketlab.screen.herosquad.presentation.viewmodel

import io.rocketlab.arch.extension.action
import io.rocketlab.arch.extension.state
import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.navigation.api.Navigator
import io.rocketlab.screen.herosquad.presentation.model.HeroSquadScreenState
import kotlinx.coroutines.flow.update

class HeroSquadViewModel(
    private val navigator: Navigator
) : BaseViewModel() {

    val uiState = state<HeroSquadScreenState>(HeroSquadScreenState.Menu)

    val onBackPressedAction = action<Unit> { onBackPressed() }
    val onStartGameClickedAction = action<Unit> { startGame() }

    private fun onBackPressed() {
        navigator.navigateUp()
    }

    private fun startGame() {
        uiState.update { HeroSquadScreenState.Game }
    }
}