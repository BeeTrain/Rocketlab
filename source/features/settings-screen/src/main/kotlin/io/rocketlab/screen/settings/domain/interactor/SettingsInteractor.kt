package io.rocketlab.screen.settings.domain.interactor

import io.rocketlab.screen.settings.data.repository.SettingsRepository
import io.rocketlab.screen.settings.domain.model.AppTheme
import kotlinx.coroutines.flow.StateFlow

class SettingsInteractor(
    private val settingsRepository: SettingsRepository
) {

    fun observeAppTheme(): StateFlow<AppTheme> {
        return settingsRepository.currentThemeState
    }

    fun loadAppTheme(): AppTheme {
        return settingsRepository.getAppTheme()
    }

    fun saveAppTheme(appTheme: AppTheme) {
        settingsRepository.setAppTheme(appTheme)
    }
}