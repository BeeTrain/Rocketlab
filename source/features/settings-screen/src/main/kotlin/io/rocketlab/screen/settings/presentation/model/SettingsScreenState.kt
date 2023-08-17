package io.rocketlab.screen.settings.presentation.model

import androidx.compose.runtime.Immutable
import io.rocketlab.screen.settings.domain.model.AppTheme

sealed interface SettingsScreenState {

    @Immutable
    data object Loading : SettingsScreenState

    @Immutable
    data class Content(
        val selectedTheme: AppTheme,
        val shouldShowThemeSelector: Boolean = false
    ) : SettingsScreenState

    fun asContent() = this as Content

    fun asContentOrNull() = this as? Content
}