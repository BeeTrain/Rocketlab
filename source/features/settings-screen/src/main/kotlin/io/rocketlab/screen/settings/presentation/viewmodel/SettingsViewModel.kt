package io.rocketlab.screen.settings.presentation.viewmodel

import io.rocketlab.arch.extension.action
import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.navigation.api.Navigator

class SettingsViewModel(
    private val navigator: Navigator
) : BaseViewModel() {

    val onBackPressedAction = action<Unit> { navigator.navigateUp() }

}