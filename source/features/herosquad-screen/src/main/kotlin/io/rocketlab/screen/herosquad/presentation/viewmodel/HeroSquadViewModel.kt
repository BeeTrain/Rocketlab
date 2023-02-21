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
    val gameMenuDialogState = state(false)

    val onBackPressedAction = action<Unit> { onBackPressed() }
    val onStartGameClickedAction = action<Unit> { startGame() }
    val openGameMenuAction = action<Unit> { openGameMenuDialog() }

    val dismissMenuDialogAction = action<Unit> { closeGameMenuDialog() }
    val openMainMenuDialogAction = action<Unit> { openMainMenu() }

    private fun onBackPressed() {
        navigator.navigateUp()
    }

    private fun startGame() {
        uiState.update { HeroSquadScreenState.Game }
    }

    private fun openGameMenuDialog() {
        gameMenuDialogState.update { true }
    }

    private fun closeGameMenuDialog() {
        gameMenuDialogState.update { false }
    }

    private fun openMainMenu() {
        closeGameMenuDialog()
        uiState.update { HeroSquadScreenState.Menu }
    }
}