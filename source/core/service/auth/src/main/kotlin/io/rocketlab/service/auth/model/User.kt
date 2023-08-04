package io.rocketlab.service.auth.model

import io.rocketlab.utils.EMPTY

data class User(
    val name: String,
    val eMail: String,
    val photoUrl: String?
) {

    companion object {

        fun anonymous(): User {
            return User(
                name = EMPTY,
                eMail = EMPTY,
                photoUrl = null
            )
        }
    }
}