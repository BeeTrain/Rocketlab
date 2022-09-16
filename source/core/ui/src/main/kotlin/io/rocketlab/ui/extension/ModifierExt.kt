package io.rocketlab.ui.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged

fun Modifier.hideKeyboardOnClick(
    interactionSource: MutableInteractionSource,
    focusManager: FocusManager
): Modifier {
    return this.clickable(
        interactionSource = interactionSource,
        indication = null,
        onClick = { focusManager.clearFocus() }
    )
}

fun Modifier.afterFocusChanged(
    isFocused: MutableState<Boolean>,
    onFocusChanged: (FocusState) -> Unit
): Modifier {
    return onFocusChanged {
        if (it.isFocused.not() && isFocused.value) {
            onFocusChanged.invoke(it)
        }
        isFocused.value = it.isFocused
    }
}