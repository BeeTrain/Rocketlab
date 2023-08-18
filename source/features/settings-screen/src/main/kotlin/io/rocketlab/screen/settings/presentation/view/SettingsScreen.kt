package io.rocketlab.screen.settings.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.settings.R
import io.rocketlab.screen.settings.presentation.model.SettingsScreenState
import io.rocketlab.screen.settings.presentation.view.theme.SelectThemeBottomSheet
import io.rocketlab.screen.settings.presentation.viewmodel.SettingsViewModel
import io.rocketlab.ui.appbar.AppBar
import io.rocketlab.ui.progress.CircularProgress
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = getViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.settings_screen_title),
                onBackPressed = { viewModel.onBackPressedAction.accept() }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                when (uiState) {
                    is SettingsScreenState.Content -> ContentState(
                        uiState = uiState.asContent(),
                        viewModel = viewModel
                    )
                    is SettingsScreenState.Loading -> LoadingState()
                }
            }
        }
    )
}

@Composable
private fun BoxScope.LoadingState() {
    CircularProgress(
        modifier = Modifier
            .size(56.dp)
            .align(Alignment.Center)
    )
}
@Composable
fun ContentState(
    uiState: SettingsScreenState.Content,
    viewModel: SettingsViewModel
) {
    SelectThemeBottomSheet(
        uiState = uiState,
        viewModel = viewModel
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SettingsScreenRow(
            icon = Icons.Outlined.DarkMode,
            text = stringResource(R.string.settings_screen_app_theme_title),
            onClick = { viewModel.onThemeSelectorStateChanged.accept(true) }
        )
    }
}

@Composable
private fun SettingsScreenRow(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Icon(
            modifier = Modifier.padding(16.dp),
            imageVector = icon,
            contentDescription = null
        )
        Text(
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
            text = text
        )
    }
}