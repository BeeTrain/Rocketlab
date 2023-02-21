package io.rocketlab.screen.herosquad.presentation.view.game.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.herosquad.presentation.viewmodel.HeroSquadViewModel

@Composable
fun HeroSquadMenuDialog(
    shouldShowDialog: Boolean,
    viewModel: HeroSquadViewModel
) {
    if (shouldShowDialog.not()) return

    val systemUiController = rememberSystemUiController()
    systemUiController.isSystemBarsVisible = false
    AlertDialog(
        onDismissRequest = { viewModel.dismissMenuDialogAction.accept() },
        shape = MaterialTheme.shapes.extraLarge,
        title = {
            Text(
                text = "Menu",
                style = MaterialTheme.typography.titleLarge
            )
        },
        buttons = {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Button(
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
    )
}