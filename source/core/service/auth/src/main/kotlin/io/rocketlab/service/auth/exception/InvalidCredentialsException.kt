package io.rocketlab.service.auth.exception

class InvalidCredentialsException(reason: Throwable) : Exception(reason.localizedMessage, reason)