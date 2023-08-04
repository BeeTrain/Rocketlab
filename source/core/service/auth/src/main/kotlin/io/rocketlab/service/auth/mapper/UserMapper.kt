package io.rocketlab.service.auth.mapper

import com.google.firebase.auth.FirebaseUser
import io.rocketlab.service.auth.model.User
import io.rocketlab.utils.EMPTY

class UserMapper {

    fun map(firebaseUser: FirebaseUser?): User {
        if (firebaseUser == null) return User.anonymous()

        return User(
            name = resolveUserName(firebaseUser),
            eMail = firebaseUser.email.orEmpty(),
            photoUrl = firebaseUser.photoUrl?.toString()
        )
    }

    private fun resolveUserName(firebaseUser: FirebaseUser): String {
        val name = firebaseUser.displayName.orEmpty()
        val eMail = firebaseUser.email.orEmpty()

        return when {
            name.isNotEmpty() -> name
            eMail.isNotEmpty() -> eMail.split("@").firstOrNull().orEmpty()
            else -> EMPTY
        }
    }
}