package io.rocketlab.service.auth.model

data class User(
    val name: String,
    val eMail: String,
    val photoUrl: String?
)