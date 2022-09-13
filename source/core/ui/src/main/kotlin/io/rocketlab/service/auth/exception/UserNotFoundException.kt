package io.rocketlab.service.auth.exception

class UserNotFoundException(reason: Throwable) : Exception("User not found", reason)