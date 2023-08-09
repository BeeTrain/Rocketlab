package io.rocketlab.service.auth.mapper

import com.google.firebase.auth.FirebaseUser
import io.rocketlab.service.auth.R
import io.rocketlab.service.auth.model.User
import io.rocketlab.utils.provider.resources.ResourcesProvider

class UserMapper(
    private val resourcesProvider: ResourcesProvider
) {

    fun map(firebaseUser: FirebaseUser?): User {
        return User(
            name = resolveUserName(firebaseUser),
            eMail = firebaseUser?.email.orEmpty(),
            photoUrl = firebaseUser?.photoUrl?.toString()
        )
    }

    private fun resolveUserName(firebaseUser: FirebaseUser?): String {
        val name = firebaseUser?.displayName.orEmpty()
        val eMail = firebaseUser?.email.orEmpty()

        return when {
            name.isNotEmpty() -> name
            eMail.isNotEmpty() -> eMail.split("@").firstOrNull().orEmpty()
            else -> resourcesProvider.getString(R.string.auth_anonymous_user_name)
        }
    }
}