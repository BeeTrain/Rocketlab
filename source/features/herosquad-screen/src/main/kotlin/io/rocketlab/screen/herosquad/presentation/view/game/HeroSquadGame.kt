package io.rocketlab.screen.herosquad.presentation.view.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.herosquad.presentation.view.game.dialog.HeroSquadMenuDialog
import io.rocketlab.screen.herosquad.presentation.viewmodel.HeroSquadViewModel

@Composable
fun HeroSquadGame(
    viewModel: HeroSquadViewModel
) {
    val menuDialogState by viewModel.gameMenuDialogState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        content = {
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable { viewModel.openGameMenuAction.accept() },
                imageVector = Icons.Filled.Menu,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = "Game"
            )
        }
    )
    HeroSquadMenuDialog(
        shouldShowDialog = menuDialogState,
        viewModel = viewModel
    )
}