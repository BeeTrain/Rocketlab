package io.rocketlab.arch.extension

import io.rocketlab.arch.presentation.viewmodel.action.Action
import io.rocketlab.arch.presentation.viewmodel.action.ViewModelAction
import kotlinx.coroutines.flow.MutableStateFlow

fun <T> state(value: T) = MutableStateFlow(value)

fun <T> action(onBind: (T) -> Unit): Action<T> {
    return ViewModelAction<T>().apply {
        bind { onBind.invoke(it) }
    }
}

fun Action<Unit>.accept() {
    accept(Unit)
}