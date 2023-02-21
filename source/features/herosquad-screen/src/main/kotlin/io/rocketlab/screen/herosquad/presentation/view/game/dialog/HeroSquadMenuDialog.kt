package io.rocketlab.screen.herosquad.presentation.view.game.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.herosquad.presentation.viewmodel.HeroSquadViewModel

@Composable
fun HeroSquadMenuDialog(
    shouldShowDialog: Boolean,
    viewModel: HeroSquadViewModel
) {
    if (shouldShowDialog.not()) return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .clickable { viewModel.dismissMenuDialogAction.accept() }
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Menu",
                style = MaterialTheme.typography.titleLarge
            )
            Button(
                modifier = Modifier.padding(16.dp),
                onClick = { viewModel.openMainMenuDialogAction.accept() },
                content = {
                    Text(
                        text = "To Main menu",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            )
        }
    }
}