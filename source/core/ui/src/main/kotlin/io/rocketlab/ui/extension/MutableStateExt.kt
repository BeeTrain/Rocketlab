package io.rocketlab.ui.extension

import androidx.compose.runtime.MutableState

inline fun <T> MutableState<T>.update(block: ((T) -> T)) {
    value = block.invoke(value)
}