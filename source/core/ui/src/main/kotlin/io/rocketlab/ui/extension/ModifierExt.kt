package io.rocketlab.ui.extension

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.hideKeyboardOnClick(
    focusManager: FocusManager,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
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

fun Modifier.supportWideScreen(): Modifier {
    return this
        .fillMaxWidth()
        .wrapContentWidth(align = Alignment.CenterHorizontally)
        .widthIn(max = 840.dp)
}

private enum class ButtonState { PRESSED, IDLE }

fun Modifier.bounceClick(
    onClick: (() -> Unit)? = null
) = composed {
    var buttonState by remember { mutableStateOf(ButtonState.IDLE) }
    val scale by animateFloatAsState(if (buttonState == ButtonState.PRESSED) 0.70f else 1f)

    graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { onClick?.invoke() }
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.PRESSED) {
                    waitForUpOrCancellation()
                    ButtonState.IDLE
                } else {
                    awaitFirstDown(false)
                    ButtonState.PRESSED
                }
            }
        }
}

fun Modifier.pressClickEffect() = composed {
    var buttonState by remember { mutableStateOf(ButtonState.IDLE) }
    val yTranslation by animateFloatAsState(if (buttonState == ButtonState.PRESSED) 0f else -20f)

    graphicsLayer { translationY = yTranslation }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { }
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.PRESSED) {
                    waitForUpOrCancellation()
                    ButtonState.IDLE
                } else {
                    awaitFirstDown(false)
                    ButtonState.PRESSED
                }
            }
        }
}

fun Modifier.shakeClickEffect() = composed {
    var buttonState by remember { mutableStateOf(ButtonState.IDLE) }

    val xTranslation by animateFloatAsState(
        targetValue = if (buttonState == ButtonState.PRESSED) 0f else -50f,
        animationSpec = repeatable(
            iterations = 2,
            animation = tween(durationMillis = 50, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    graphicsLayer { translationX = xTranslation }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { }
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.PRESSED) {
                    waitForUpOrCancellation()
                    ButtonState.IDLE
                } else {
                    awaitFirstDown(false)
                    ButtonState.PRESSED
                }
            }
        }
}

fun Modifier.nonClickable() = clickable(
    interactionSource = MutableInteractionSource(),
    indication = null,
    onClick = {}
)