package io.rocketlab.screen.settings.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.settings.R
import io.rocketlab.screen.settings.presentation.viewmodel.SettingsViewModel
import io.rocketlab.ui.appbar.AppBar
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = getViewModel()
) {
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(id = R.string.settings_screen_title),
                onBackPressed = { viewModel.onBackPressedAction.accept() }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            )
        }
    )
}