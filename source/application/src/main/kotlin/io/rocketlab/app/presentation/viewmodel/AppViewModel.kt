package io.rocketlab.app.presentation.viewmodel

import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.screen.settings.domain.interactor.SettingsInteractor

class AppViewModel(
    settingsInteractor: SettingsInteractor
) : BaseViewModel() {

    val appThemeState = settingsInteractor.observeAppTheme()
}