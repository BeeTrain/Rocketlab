package io.rocketlab.ui.swipe.cardstack

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

fun Modifier.moveTo(
    cardStackController: CardStackController,
    orientation: Orientation
): Modifier {
    val x = cardStackController.offsetX.value.takeIf { orientation == Orientation.Horizontal } ?: 0f
    val y = cardStackController.offsetY.value.takeIf { orientation == Orientation.Vertical } ?: 0f
    return layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(x.roundToInt(), y.roundToInt())
        }
    }
}

fun Modifier.shadowPadding(
    shadowSide: StackGravity,
    betweenMargin: Dp,
    controller: CardStackController,
    layer: Float
): Modifier {
    return padding(
        bottom = getBottomShadowPadding(shadowSide, layer, controller, betweenMargin),
        start = getStartShadowPadding(shadowSide, layer, controller, betweenMargin),
        top = getTopShadowPadding(shadowSide, layer, controller, betweenMargin),
        end = getEndShadowPadding(shadowSide, layer, controller, betweenMargin)
    )
}

private fun getEndShadowPadding(
    shadowSide: StackGravity,
    layer: Float,
    controller: CardStackController,
    betweenMargin: Dp
): Dp {
    return when (shadowSide) {
        StackGravity.END -> ((layer - (10 - controller.scale.value * 10)) * betweenMargin.value).dp
        else -> 0.dp
    }
}

private fun getTopShadowPadding(
    shadowSide: StackGravity,
    layer: Float,
    controller: CardStackController,
    betweenMargin: Dp
): Dp {
    return when (shadowSide) {
        StackGravity.TOP -> ((layer - (10 - controller.scale.value * 10)) * betweenMargin.value).dp
        else -> 0.dp
    }
}

private fun getStartShadowPadding(
    shadowSide: StackGravity,
    layer: Float,
    controller: CardStackController,
    betweenMargin: Dp
): Dp {
    return when (shadowSide) {
        StackGravity.START -> ((layer - (10 - controller.scale.value * 10)) * betweenMargin.value).dp
        else -> 0.dp
    }
}

private fun getBottomShadowPadding(
    shadowSide: StackGravity,
    layer: Float,
    controller: CardStackController,
    betweenMargin: Dp
): Dp {
    return when (shadowSide) {
        StackGravity.BOTTOM -> ((layer - (10 - controller.scale.value * 10)) * betweenMargin.value).dp
        else -> 0.dp
    }
}

fun Modifier.shadowPaddingLayerThree(
    stackGravity: StackGravity,
    betweenMargin: Dp
): Modifier {
    return padding(
        bottom = if (stackGravity == StackGravity.BOTTOM) betweenMargin * 2 else 0.dp,
        end = if (stackGravity == StackGravity.END) betweenMargin * 2 else 0.dp,
        start = if (stackGravity == StackGravity.START) betweenMargin * 2 else 0.dp,
        top = if (stackGravity == StackGravity.TOP) betweenMargin * 2 else 0.dp,
    )
}

fun Modifier.shadowHorizontalPadding(
    margin: Dp,
    cardStackController: CardStackController,
    layer: Float
): Modifier {
    return padding(
        horizontal = ((layer + (10 - cardStackController.scale.value * 10)) * margin.value).dp
    )
}

fun <E> List<E>.topToBottom(): List<E> {
    return toMutableList().apply {
        val item = last()
        removeLast()
        add(0, item)
    }
}

fun <E> List<E>.removeTop(): List<E> {
    return toMutableList().apply {
        removeLast()
    }
}