package io.rocketlab.screen.settings.presentation.viewmodel

import io.rocketlab.arch.extension.action
import io.rocketlab.arch.extension.state
import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.navigation.api.Navigator
import io.rocketlab.screen.settings.domain.interactor.SettingsInteractor
import io.rocketlab.screen.settings.domain.model.AppTheme
import io.rocketlab.screen.settings.presentation.model.SettingsScreenState
import kotlinx.coroutines.flow.update

class SettingsViewModel(
    private val settingsInteractor: SettingsInteractor,
    private val navigator: Navigator
) : BaseViewModel() {

    val uiState = state<SettingsScreenState>(SettingsScreenState.Loading)

    val onBackPressedAction = action<Unit> { navigator.navigateUp() }
    val onThemeSelectorStateChanged = action<Boolean> { changeThemeSelectorState(it) }
    val onAppThemeSelectedAction = action<AppTheme> { selectAppTheme(it) }

    init {
        loadScreenState()
    }

    private fun loadScreenState() {
        uiState.update {
            SettingsScreenState.Content(
                selectedTheme = settingsInteractor.loadAppTheme()
            )
        }
    }

    private fun changeThemeSelectorState(shouldShow: Boolean) {
        val state = uiState.value.asContentOrNull() ?: return

        uiState.update {
            state.copy(
                shouldShowThemeSelector = shouldShow
            )
        }
    }

    private fun selectAppTheme(appTheme: AppTheme) {
        settingsInteractor.saveAppTheme(appTheme)
        loadScreenState()
    }
}