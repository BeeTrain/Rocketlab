package io.rocketlab.service.auth.di

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import io.rocketlab.annotation.processing.annotation.KoinModule
import io.rocketlab.service.auth.AuthService
import io.rocketlab.service.auth.R
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@KoinModule
val authServiceModule = module {

    single { AuthService.Provider.provide(get(), get(), get()) }

    single { FirebaseAuth.getInstance() }

    single {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(androidContext().getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    single { GoogleSignIn.getClient(androidContext(), get()) }
}