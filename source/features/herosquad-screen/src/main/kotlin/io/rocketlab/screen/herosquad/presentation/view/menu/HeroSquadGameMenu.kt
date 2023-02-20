package io.rocketlab.screen.herosquad.presentation.view.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.rocketlab.screen.herosquad.presentation.viewmodel.HeroSquadViewModel
import io.rocketlab.arch.extension.accept

@Composable
fun HeroSquadGameMenu(
    modifier: Modifier = Modifier,
    viewModel: HeroSquadViewModel
) {
    Box(
        modifier = modifier
            .fillMaxSize()
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