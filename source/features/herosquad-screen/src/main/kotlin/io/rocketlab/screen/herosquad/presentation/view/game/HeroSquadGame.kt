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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.herosquad.presentation.view.game.dialog.HeroSquadMenuDialog
import io.rocketlab.screen.herosquad.presentation.viewmodel.HeroSquadViewModel
import io.rocketlab.ui.swipe.cardstack.SwipeCardStack

@Composable
fun HeroSquadGame(
    viewModel: HeroSquadViewModel
) {
    val menuDialogState by viewModel.gameMenuDialogState.collectAsState()
    val list = mutableListOf(
        Pair("1", Color.Cyan),
        Pair("2", Color.Green),
        Pair("3", Color.Red),
        Pair("4", Color.Yellow),
    )
    Box(
        modifier = Modifier.fillMaxSize(),
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
            SwipeCardStack(
                modifier = Modifier.align(Alignment.Center),
                items = list
            ) { item ->
                Card(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(horizontal = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = item.second
                    ),
                    content = {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = item.first
                            )
                        }
                    }
                )
            }
        }
    )
    HeroSquadMenuDialog(
        shouldShowDialog = menuDialogState,
        viewModel = viewModel
    )
}