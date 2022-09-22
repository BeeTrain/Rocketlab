package io.rocketlab.service.auth.impl

import io.rocketlab.service.auth.AuthService

class DebugAuthService(
    private val prodAuthService: ProdAuthService
) : AuthService by prodAuthService {

    override val isLogged = true
}