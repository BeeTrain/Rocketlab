package io.rocketlab.app.di.provider

import android.content.Context
import io.rocketlab.app.R
import io.rocketlab.service.auth.model.WebClientId

object WebClientIdProvider {

    fun provide(context: Context): WebClientId {
        return WebClientId(id = context.getString(R.string.default_web_client_id))
    }
}