package io.rocketlab.screen.profile.presentation.viewmodel

import io.rocketlab.arch.extension.action
import io.rocketlab.arch.extension.state
import io.rocketlab.arch.presentation.viewmodel.BaseViewModel
import io.rocketlab.navigation.api.Destination
import io.rocketlab.navigation.api.Navigator
import io.rocketlab.screen.profile.presentation.model.ProfileScreenState
import io.rocketlab.service.auth.AuthService
import kotlinx.coroutines.flow.update

class ProfileViewModel(
    private val authService: AuthService,
    private val navigator: Navigator
) : BaseViewModel() {

    val uiState = state<ProfileScreenState>(ProfileScreenState.Loading)

    val onBackPressedAction = action<Unit> { navigator.navigateUp() }
    val onLogOutPressedAction = action<Unit> { logOut() }
    val onProfileImagePressedAction = action<Unit> { }
    val onUserNamePressedAction = action<Unit> { }
    val onSettingsPressedAction = action<Unit> { openSettings() }

    init {
        launchJob {
            val user = authService.user
            uiState.update {
                ProfileScreenState.Content(
                    isLogged = authService.isLogged,
                    userName = user.name,
                    eMail = user.eMail,
                    photoUrl = user.photoUrl.orEmpty()
                )
            }
        }
    }

    private fun logOut() {
        authService.signOut()
        navigator.navigate(Destination.Splash) {
            popUpTo(Destination.Home.route) {
                inclusive = true
            }
        }
    }

    private fun openSettings() {
        navigator.navigate(Destination.Settings)
    }
}