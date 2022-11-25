package io.rocketlab.arch.lifecycle

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow

class SingleSharedFlow<T> : MutableSharedFlow<T> {

    private val mutableSharedFlow = MutableSharedFlow<T>(replay = 1)

    override val replayCache: List<T>
        get() = mutableSharedFlow.replayCache

    override val subscriptionCount: StateFlow<Int>
        get() = mutableSharedFlow.subscriptionCount

    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        mutableSharedFlow.collect {
            try {
                collector.emit(it)
            } catch (e: Exception) {
                throw e
            } finally {
                mutableSharedFlow.resetReplayCache()
            }
        }
    }

    override suspend fun emit(value: T) = mutableSharedFlow.emit(value)

    @ExperimentalCoroutinesApi
    override fun resetReplayCache() = mutableSharedFlow.resetReplayCache()

    override fun tryEmit(value: T) = mutableSharedFlow.tryEmit(value)
}