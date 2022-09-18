package io.rocketlab.arch.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import io.rocketlab.arch.lifecycle.SingleSharedFlow
import io.rocketlab.arch.presentation.viewmodel.action.Action
import io.rocketlab.arch.presentation.viewmodel.action.ViewModelAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

fun <T> state(value: T) = MutableStateFlow(value)

fun <T> command() = SingleSharedFlow<T>()

fun <T> action(onBind: (T) -> Unit): Action<T> {
    return ViewModelAction<T>().apply {
        bind { onBind.invoke(it) }
    }
}

fun Action<Unit>.accept() {
    accept(Unit)
}

suspend fun MutableSharedFlow<Unit>.emit() = emit(Unit)

fun MutableSharedFlow<Unit>.tryEmit() = tryEmit(Unit)

@Composable
fun <T : R, R> Flow<T>.collectAsCommand() = collectAsState(initial = null)
