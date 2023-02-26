package io.rocketlab.ui.swipe.cardstack

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import io.rocketlab.ui.extension.update
import kotlin.math.abs

private val betweenCardsMargin = 16.dp

@Composable
fun <T> SwipeCardStack(
    modifier: Modifier = Modifier,
    items: List<T>,
    onSwipe: (Swipe<T>) -> Unit = {},
    orientation: Orientation = Orientation.Horizontal,
    stackSide: StackGravity = StackGravity.BOTTOM,
    content: @Composable (item: T) -> Unit
) {
    val itemsState = remember { mutableStateOf(items) }
    val currentIndex by remember { mutableStateOf(itemsState.value.size - 1) }
    val cardStackController = rememberCardStackController()

    cardStackController.onSwipe = { swipeType ->
        itemsState.update { it.fistToLast() }
        when (swipeType) {
            SwipeSide.TOP -> onSwipe(Swipe(itemsState.value[currentIndex], SwipeSide.TOP))
            SwipeSide.LEFT -> onSwipe(Swipe(itemsState.value[currentIndex], SwipeSide.LEFT))
            SwipeSide.RIGHT -> onSwipe(Swipe(itemsState.value[currentIndex], SwipeSide.RIGHT))
            SwipeSide.BOTTOM -> onSwipe(Swipe(itemsState.value[currentIndex], SwipeSide.BOTTOM))
        }
    }

    if (itemsState.value.isNotEmpty()) {
        Box(
            modifier = modifier.wrapContentSize()
        ) {
            Box(
                modifier = Modifier
                    .shadowPadding(stackSide, betweenCardsMargin, cardStackController, 1f)
                    .shadowHorizontalPadding(16.dp, cardStackController, 1f)
                    .align(Alignment.Center)
                    .shadow(0.dp, RoundedCornerShape(8.dp)),
                content = {
                    content(
                        if (currentIndex < 2) {
                            itemsState.value[items.size + currentIndex - 2]
                        } else {
                            itemsState.value[abs(currentIndex - 2)]
                        }
                    )
                }
            )
            Box(
                modifier = Modifier
                    .shadowPadding(stackSide, betweenCardsMargin, cardStackController, 2f)
                    .shadowHorizontalPadding(16.dp, cardStackController, 0f)
                    .align(Alignment.Center)
                    .shadow(0.dp, RoundedCornerShape(8.dp)),
                content = { content(itemsState.value[abs(currentIndex - 1)]) }
            )
            Box(
                modifier = Modifier
                    .shadowPaddingLayerThree(stackSide, betweenCardsMargin)
                    .align(Alignment.Center)
                    .draggableStack(cardStackController, orientation)
                    .moveTo(cardStackController, orientation)
                    .graphicsLayer(rotationZ = cardStackController.rotation.value)
                    .shadow(0.dp, RoundedCornerShape(8.dp)),
                content = { content(itemsState.value[currentIndex]) }
            )
        }
    }
}