package io.rocketlab.screen.settings.data.repository

import io.rocketlab.screen.settings.domain.model.AppTheme
import io.rocketlab.storage.plain.SimpleDataStorage
import kotlinx.coroutines.flow.MutableStateFlow

private const val KEY_APP_THEME_ID = "APP_THEME_ID"

class SettingsRepository(
    private val dataStorage: SimpleDataStorage
) {

    val currentThemeState = MutableStateFlow(getAppTheme())

    fun getAppTheme(): AppTheme {
        val themeId = dataStorage.getNullableInt(KEY_APP_THEME_ID)

        return AppTheme.getOrDefault(themeId)
    }

    fun setAppTheme(appTheme: AppTheme) {
        dataStorage.putInt(KEY_APP_THEME_ID, appTheme.id)
        currentThemeState.value = appTheme
    }
}