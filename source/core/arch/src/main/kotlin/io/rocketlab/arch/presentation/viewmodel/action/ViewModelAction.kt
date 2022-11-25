package io.rocketlab.arch.presentation.viewmodel.action

import io.rocketlab.utils.extension.TAG

class ViewModelAction<T> : Action<T> {

    private var action: ((T) -> Unit)? = null

    override fun bind(onNext: (T) -> Unit) {
        action = onNext
    }

    override fun accept(value: T) {
        action?.invoke(value) ?: throw Exception("$TAG is not bind")
    }
}