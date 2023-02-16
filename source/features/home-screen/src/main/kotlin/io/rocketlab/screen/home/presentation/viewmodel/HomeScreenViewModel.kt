package io.rocketlab.screen.home.presentation.viewmodel

import io.rocketlab.arch.extension.action
import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.navigation.api.Destination
import io.rocketlab.navigation.api.Navigator
import io.rocketlab.service.auth.AuthService

class HomeScreenViewModel(
    private val authService: AuthService,
    private val navigator: Navigator
) : BaseViewModel() {

    val onProfileHeaderClickAction = action<Unit> { openAuth() }
    val onNotesClickedAction = action<Unit> { openNotes() }

    private fun openNotes() {
        navigator.navigate(Destination.NotesList)
    }

    private fun openAuth() {
        if (authService.isLogged) return

        navigator.navigate(Destination.SignIn)
    }
}