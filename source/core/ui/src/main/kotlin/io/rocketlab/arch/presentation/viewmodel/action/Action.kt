package io.rocketlab.arch.presentation.viewmodel.action

interface Action<T> {

    fun bind(onNext: (T) -> Unit)

    fun accept(value: T)
}