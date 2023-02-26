package io.rocketlab.ui.swipe.cardstack

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.sign

open class CardStackController(
    private val screenWidth: Float,
    private val screenHeight: Float
) {
    val right = Offset(screenWidth, 0f)
    val bottom = Offset(0f, screenHeight)
    val center = Offset(0f, 0f)

    val offsetX = Animatable(0f)
    val offsetY = Animatable(0f)

    val rotation = Animatable(0f)
    val scale = Animatable(0.9f)

    var onSwipe: (swipeSide: SwipeSide) -> Unit = {}

    suspend fun swipeLeft() {
        offsetX.animateTo(-screenWidth, tween(90))

        onSwipe(SwipeSide.LEFT)
        offsetX.snapTo(center.x)
        offsetY.snapTo(0f)
        scale.snapTo(0.9f)
        rotation.snapTo(0f)

        scale.animateTo(1f, tween(90))
    }

    suspend fun swipeRight() {
        offsetX.animateTo(screenWidth, tween(90))

        onSwipe(SwipeSide.RIGHT)
        offsetX.snapTo(center.x)
        offsetY.snapTo(0f)
        scale.snapTo(0.9f)
        rotation.snapTo(0f)

        scale.animateTo(1f, tween(90))
    }

    suspend fun swipeTop() {
        offsetY.animateTo(-screenHeight * 6, tween(90))

        onSwipe(SwipeSide.TOP)
        offsetX.snapTo(center.x)
        offsetY.snapTo(0f)
        scale.snapTo(0.9f)
        rotation.snapTo(0f)

        scale.animateTo(1f, tween(90))
    }

    suspend fun swipeBottom() {
        offsetY.animateTo(screenHeight * 6, tween(90))

        onSwipe(SwipeSide.BOTTOM)
        offsetX.snapTo(center.x)
        offsetY.snapTo(0f)
        scale.snapTo(0.9f)
        rotation.snapTo(0f)

        scale.animateTo(1f, tween(90))
    }

    suspend fun returnCenter() {
        offsetX.animateTo(center.x, tween(90))
        offsetY.animateTo(center.y, tween(90))
        rotation.animateTo(0f, tween(90))
        scale.animateTo(0.9f, tween(90))
    }
}

@Composable
fun rememberCardStackController(): CardStackController {
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }
    val screenHeight = with(LocalDensity.current) {
        LocalConfiguration.current.screenHeightDp.dp.toPx()
    }

    return remember {
        CardStackController(
            screenWidth = screenWidth,
            screenHeight = screenHeight
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.draggableStack(
    controller: CardStackController,
    orientation: Orientation,
): Modifier {
    return composed {
        val scope = rememberCoroutineScope()

        Modifier.pointerInput(Unit) {
            detectDragGestures(
                onDragEnd = {
                    when (orientation) {
                        Orientation.Horizontal -> scope.launch { onHorizontalDragEnd(controller) }
                        Orientation.Vertical -> scope.launch { onVerticalDragEnd(controller) }
                    }
                },
                onDrag = { change, dragAmount ->
                    scope.launch {
                        controller.offsetX.snapTo(controller.offsetX.value + dragAmount.x)
                        controller.offsetY.snapTo(controller.offsetY.value + dragAmount.y)

                        when (orientation) {
                            Orientation.Horizontal -> onHorizontalDrag(controller)
                            Orientation.Vertical -> onVerticalDrag(controller)
                        }
                    }
                    change.consume()
                }
            )
        }
    }
}

private suspend fun onVerticalDrag(controller: CardStackController) {
    controller.scale.snapTo(
        normalizeVertical(
            minValue = controller.center.y,
            maxValue = controller.bottom.y / 3,
            currentValue = abs(controller.offsetY.value),
            startRange = 0.9f
        )
    )
}

private suspend fun onHorizontalDrag(controller: CardStackController) {
    val targetRotation = normalize(
        minValue = controller.center.x,
        maxValue = controller.right.x,
        currentValue = abs(controller.offsetX.value),
        startRange = 0f,
        endRange = 10f
    )
    controller.rotation.snapTo(targetRotation * controller.offsetX.value.sign)
    controller.scale.snapTo(
        normalize(
            minValue = controller.center.x,
            maxValue = controller.right.x / 3,
            currentValue = abs(controller.offsetX.value),
            startRange = 0.9f
        )
    )
}

private suspend fun onVerticalDragEnd(controller: CardStackController) {
    if (controller.offsetY.value <= 0f) {
        if (controller.offsetY.value > -72) controller.returnCenter()
        else controller.swipeTop()
    } else {
        if (controller.offsetY.value < 72) controller.returnCenter()
        else controller.swipeBottom()
    }
}

private suspend fun onHorizontalDragEnd(controller: CardStackController) {
    if (controller.offsetX.value <= 0f) {
        if (controller.offsetX.value > -72) controller.returnCenter()
        else controller.swipeLeft()
    } else {
        if (controller.offsetX.value < 72) controller.returnCenter()
        else controller.swipeRight()
    }
}

fun normalize(
    minValue: Float,
    maxValue: Float,
    currentValue: Float,
    startRange: Float = 0f,
    endRange: Float = 1f
): Float {
    require(startRange < endRange) { "startRange=$startRange is greater than endRange=$endRange" }
    val value = currentValue.coerceIn(minValue, maxValue)

    return (value - minValue) / (maxValue - minValue) * (endRange - startRange) + startRange
}

fun normalizeVertical(
    minValue: Float,
    maxValue: Float,
    currentValue: Float,
    startRange: Float = 0f,
    endRange: Float = 1f
): Float {
    require(startRange < endRange) { "startRange=$startRange is greater than endRange=$endRange" }
    val value = currentValue.coerceIn(minValue, maxValue)

    return (value - minValue) / (maxValue - minValue) * (endRange - startRange) + startRange
}