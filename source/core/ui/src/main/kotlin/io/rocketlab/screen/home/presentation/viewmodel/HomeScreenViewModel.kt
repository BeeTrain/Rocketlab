package io.rocketlab.screen.home.presentation.viewmodel

import io.rocketlab.arch.extension.action
import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.navigation.Destination
import io.rocketlab.navigation.Navigator

class HomeScreenViewModel(
    private val navigator: Navigator
) : BaseViewModel() {

    val onNotesClickedAction = action<Unit> { openNotes() }

    private fun openNotes() {
        navigator.navigate(Destination.NotesList)
    }
}