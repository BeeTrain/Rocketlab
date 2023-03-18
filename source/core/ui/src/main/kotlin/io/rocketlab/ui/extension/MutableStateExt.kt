package io.rocketlab.ui.extension

import androidx.compose.runtime.MutableState

inline fun <T> MutableState<T>.update(block: ((T) -> T)) {
    val newValue = block.invoke(value)
    if (value != newValue) value = newValue
}