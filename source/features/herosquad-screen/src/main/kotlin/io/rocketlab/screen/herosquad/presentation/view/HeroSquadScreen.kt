package io.rocketlab.screen.herosquad.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.herosquad.R
import io.rocketlab.screen.herosquad.presentation.model.HeroSquadScreenState
import io.rocketlab.screen.herosquad.presentation.view.game.HeroSquadGame
import io.rocketlab.screen.herosquad.presentation.view.menu.HeroSquadGameMenu
import io.rocketlab.screen.herosquad.presentation.viewmodel.HeroSquadViewModel
import io.rocketlab.ui.appbar.AppBar
import org.koin.androidx.compose.getViewModel

@Composable
fun HeroSquadScreen(
    viewModel: HeroSquadViewModel = getViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(id = R.string.hero_squad_screen_title),
                onBackPressed = { viewModel.onBackPressedAction.accept() }
            )
        },
        modifier = Modifier
            .navigationBarsPadding(),
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                when(uiState) {
                    HeroSquadScreenState.Menu -> HeroSquadGameMenu(viewModel = viewModel)
                    HeroSquadScreenState.Game -> HeroSquadGame()
                }
            }
        }
    )
}