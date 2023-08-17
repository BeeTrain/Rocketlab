package io.rocketlab.screen.settings.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.settings.R
import io.rocketlab.screen.settings.domain.model.AppTheme
import io.rocketlab.screen.settings.presentation.model.SettingsScreenState
import io.rocketlab.screen.settings.presentation.viewmodel.SettingsViewModel
import io.rocketlab.ui.appbar.AppBar
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
            SelectThemeBottomSheet(
                uiState = uiState,
                viewModel = viewModel
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                SettingsScreenRow(
                    icon = Icons.Outlined.DarkMode,
                    text = stringResource(R.string.settings_screen_app_theme_title),
                    onClick = { viewModel.onThemeSelectorStateChanged.accept(true) }
                )
            }
        }
    )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectThemeBottomSheet(
    uiState: SettingsScreenState,
    viewModel: SettingsViewModel
) {
    if (uiState.asContentOrNull()?.shouldShowThemeSelector == false) return

    val selectedTheme = uiState.asContent().selectedTheme

    ModalBottomSheet(
        modifier = Modifier
            .fillMaxHeight(),
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsThemeCard(
    painter: Painter,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        onClick = onClick
    ) {
        Image(
            modifier = Modifier
                .padding(4.dp),
            painter = painter,
            contentDescription = null
        )
        RadioButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            enabled = false,
            colors = RadioButtonDefaults.colors(
                disabledSelectedColor = MaterialTheme.colorScheme.onBackground,
                disabledUnselectedColor = MaterialTheme.colorScheme.onBackground
            ),
            selected = isSelected,
            onClick = { }
        )
    }
}