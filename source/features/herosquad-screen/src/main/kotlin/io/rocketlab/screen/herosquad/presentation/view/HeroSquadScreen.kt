package io.rocketlab.screen.herosquad.presentation.view

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.herosquad.presentation.model.HeroSquadScreenState
import io.rocketlab.screen.herosquad.presentation.view.game.HeroSquadGame
import io.rocketlab.screen.herosquad.presentation.view.menu.HeroSquadGameMenu
import io.rocketlab.screen.herosquad.presentation.viewmodel.HeroSquadViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun HeroSquadScreen(
    viewModel: HeroSquadViewModel = getViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    BackHandler { viewModel.onBackPressedAction.accept() }
    when (uiState) {
        HeroSquadScreenState.Menu -> HeroSquadGameMenu(viewModel)
        HeroSquadScreenState.Game -> HeroSquadGame(viewModel)
    }
}