package io.rocketlab.screen.settings.presentation.view.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.rocketlab.screen.settings.R
import io.rocketlab.screen.settings.domain.model.AppTheme
import io.rocketlab.screen.settings.presentation.model.SettingsScreenState
import io.rocketlab.screen.settings.presentation.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectThemeBottomSheet(
    uiState: SettingsScreenState.Content,
    viewModel: SettingsViewModel
) {
    if (uiState.shouldShowThemeSelector.not()) return

    val selectedTheme = uiState.selectedTheme
    ModalBottomSheet(
        modifier = Modifier
            .wrapContentHeight(),
        onDismissRequest = { viewModel.onThemeSelectorStateChanged.accept(false) }
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium,
            text = stringResource(R.string.settings_screen_app_theme_title)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            SettingsThemeCard(
                painter = painterResource(R.drawable.img_color_mode_light),
                isSelected = selectedTheme == AppTheme.LIGHT,
                onClick = { viewModel.onAppThemeSelectedAction.accept(AppTheme.LIGHT) }
            )
            SettingsThemeCard(
                painter = painterResource(R.drawable.img_color_mode_dark),
                isSelected = selectedTheme == AppTheme.DARK,
                onClick = { viewModel.onAppThemeSelectedAction.accept(AppTheme.DARK) }
            )
            SettingsThemeCard(
                painter = painterResource(R.drawable.img_color_mode_system),
                isSelected = selectedTheme == AppTheme.AS_IN_SYSTEM,
                onClick = { viewModel.onAppThemeSelectedAction.accept(AppTheme.AS_IN_SYSTEM) }
            )
        }
        Spacer(modifier = Modifier.height(36.dp))
    }
}