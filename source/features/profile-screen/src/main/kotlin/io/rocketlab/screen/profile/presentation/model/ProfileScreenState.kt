package io.rocketlab.screen.profile.presentation.model

import androidx.compose.runtime.Immutable

sealed interface ProfileScreenState {

    @Immutable
    data object Loading : ProfileScreenState

    @Immutable
    data class Content(
        val isLogged: Boolean,
        val userName: String,
        val eMail: String,
        val photoUrl: String,
    ) : ProfileScreenState

    fun asContent() = this as Content
}