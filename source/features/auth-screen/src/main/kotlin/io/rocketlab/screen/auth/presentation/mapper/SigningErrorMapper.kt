package io.rocketlab.screen.auth.presentation.mapper

import io.rocketlab.screen.auth.R
import io.rocketlab.service.auth.exception.AuthServerTimeoutException
import io.rocketlab.service.auth.exception.UserNotFoundException
import io.rocketlab.utils.provider.resources.ResourcesProvider

class SigningErrorMapper(
    private val resourcesProvider: ResourcesProvider
) {

    fun map(exception: Throwable): String {
        return when (exception) {
            is UserNotFoundException -> resourcesProvider.getString(R.string.auth_error_user_not_found_message)
            is AuthServerTimeoutException -> resourcesProvider.getString(R.string.auth_error_time_out_message)
            else -> resourcesProvider.getString(R.string.auth_error_unknown_message)
        }
    }
}