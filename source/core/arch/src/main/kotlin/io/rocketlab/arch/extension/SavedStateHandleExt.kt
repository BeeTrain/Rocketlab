package io.rocketlab.arch.extension

import androidx.lifecycle.SavedStateHandle

fun SavedStateHandle.clear() {
    keys().forEach { key ->
        remove<Any?>(key)
    }
}

fun SavedStateHandle.addAll(values: Map<String, Any?>) {
    values.forEach { (key, value) -> this[key] = value }
}