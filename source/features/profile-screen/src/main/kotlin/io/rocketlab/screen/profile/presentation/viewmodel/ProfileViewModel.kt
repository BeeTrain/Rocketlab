package io.rocketlab.screen.profile.presentation.viewmodel

import io.rocketlab.arch.extension.action
import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.navigation.api.Navigator

class ProfileViewModel(
    private val navigator: Navigator
) : BaseViewModel() {

    val onBackPressedAction = action<Unit> { navigator.navigateUp() }
}