package io.rocketlab.screen.herosquad.presentation.view.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.herosquad.R
import io.rocketlab.screen.herosquad.presentation.viewmodel.HeroSquadViewModel
import io.rocketlab.ui.appbar.AppBar

@Composable
fun HeroSquadGameMenu(
    viewModel: HeroSquadViewModel
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.isSystemBarsVisible = true

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
                Button(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = { viewModel.onStartGameClickedAction.accept() },
                    content = {
                        Text("Start game")
                    }
                )
            }
        }
    )
}