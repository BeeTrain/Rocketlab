package io.rocketlab.ui.swipe.modifier

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.swipeableCard(
    state: SwipeableCardState,
    onSwiped: (Direction) -> Unit = {},
    onSwipeCancel: () -> Unit = {},
    blockedDirections: List<Direction> = listOf(Direction.Up, Direction.Down),
) = pointerInput(Unit) {
    coroutineScope {
        detectDragGestures(
            onDragCancel = {
                launch {
                    state.reset()
                    onSwipeCancel()
                }
            },
            onDrag = { change, dragAmount ->
                launch {
                    if (change.positionChange() != Offset.Zero) change.consume()

                    val original = state.offset.targetValue
                    val summed = original + dragAmount
                    state.drag(
                        x = state.calculateNewX(blockedDirections, original.x, summed.x),
                        y = state.calculateNewY(blockedDirections, original.y, summed.y)
                    )
                }
            },
            onDragEnd = {
                launch {
                    val coercedOffset = state.offset.targetValue
                        .coerceIn(
                            blockedDirections,
                            maxHeight = state.maxHeight,
                            maxWidth = state.maxWidth
                        )
                    if (hasNotTravelledEnough(state, coercedOffset)) {
                        state.reset()
                        onSwipeCancel()
                    } else {
                        val horizontalTravel = abs(state.offset.targetValue.x)
                        val verticalTravel = abs(state.offset.targetValue.y)

                        if (horizontalTravel > verticalTravel) {
                            if (state.offset.targetValue.x > 0) {
                                state.swipe(Direction.Right)
                                onSwiped(Direction.Right)
                            } else {
                                state.swipe(Direction.Left)
                                onSwiped(Direction.Left)
                            }
                        } else {
                            if (state.offset.targetValue.y < 0) {
                                state.swipe(Direction.Up)
                                onSwiped(Direction.Up)
                            } else {
                                state.swipe(Direction.Down)
                                onSwiped(Direction.Down)
                            }
                        }
                    }
                }
            }
        )
    }
}.graphicsLayer {
    translationX = state.offset.value.x
    translationY = state.offset.value.y
    rotationZ = (state.offset.value.x / 60).coerceIn(-40f, 40f)
}

private fun SwipeableCardState.calculateNewX(
    blockedDirections: List<Direction>,
    originalX: Float,
    summedX: Float
): Float {
    return when {
        blockedDirections.contains(Direction.Left) && blockedDirections.contains(Direction.Right) -> originalX
        blockedDirections.contains(Direction.Left) -> summedX.coerceIn(originalX, maxWidth)
        blockedDirections.contains(Direction.Right) -> summedX.coerceIn(-maxWidth, originalX)
        else -> summedX.coerceIn(-maxWidth, maxWidth)
    }
}

private fun SwipeableCardState.calculateNewY(
    blockedDirections: List<Direction>,
    originalY: Float,
    summedY: Float
): Float {
    return when {
        blockedDirections.contains(Direction.Up) && blockedDirections.contains(Direction.Down) -> originalY
        blockedDirections.contains(Direction.Up) -> summedY.coerceIn(-maxHeight, originalY)
        blockedDirections.contains(Direction.Down) -> summedY.coerceIn(originalY, maxHeight)
        else -> summedY.coerceIn(-maxHeight, maxHeight)
    }
}

private fun Offset.coerceIn(
    blockedDirections: List<Direction>,
    maxHeight: Float,
    maxWidth: Float,
): Offset {
    return copy(
        x = x.coerceInForX(blockedDirections, maxWidth),
        y = y.coerceInForY(blockedDirections, maxHeight)
    )
}

private fun Float.coerceInForX(
    blockedDirections: List<Direction>,
    maxWidth: Float
): Float {
    return coerceIn(
        minimumValue = (-maxWidth).takeIf { blockedDirections.contains(Direction.Left).not() } ?: 0f,
        maximumValue = maxWidth.takeIf { blockedDirections.contains(Direction.Right).not() } ?: 0f
    )
}

private fun Float.coerceInForY(
    blockedDirections: List<Direction>,
    maxHeight: Float
): Float {
    return coerceIn(
        minimumValue = (-maxHeight).takeIf { blockedDirections.contains(Direction.Up).not() } ?: 0f,
        maximumValue = maxHeight.takeIf { blockedDirections.contains(Direction.Down).not() } ?: 0f
    )
}

private fun hasNotTravelledEnough(
    state: SwipeableCardState,
    offset: Offset,
): Boolean {
    return abs(offset.x) < state.maxWidth / 4 &&
        abs(offset.y) < state.maxHeight / 4
}