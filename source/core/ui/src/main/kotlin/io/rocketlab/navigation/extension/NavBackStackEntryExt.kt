package io.rocketlab.navigation.extension

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry

val NavBackStackEntry.isLifecycleResumed: Boolean
    get() = lifecycle.currentState == Lifecycle.State.RESUMED