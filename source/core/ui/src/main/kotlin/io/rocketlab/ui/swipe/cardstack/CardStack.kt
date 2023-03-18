package io.rocketlab.ui.swipe.cardstack

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
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
    isInfinite: Boolean = true,
    content: @Composable (item: T) -> Unit
) {
    val itemsState = remember { mutableStateOf(items) }
    val currentIndex = remember { mutableStateOf(itemsState.value.size - 1) }
    val cardStackController = rememberCardStackController()

    cardStackController.onSwipe = { swipeType ->
        val swiped = itemsState.value[currentIndex.value]
        itemsState.update {
            if (isInfinite) {
                it.topToBottom()
            } else {
                it.removeTop()
            }
        }
        currentIndex.update { itemsState.value.size - 1 }
        when (swipeType) {
            SwipeSide.TOP -> onSwipe(Swipe(swiped, SwipeSide.TOP))
            SwipeSide.LEFT -> onSwipe(Swipe(swiped, SwipeSide.LEFT))
            SwipeSide.RIGHT -> onSwipe(Swipe(swiped, SwipeSide.RIGHT))
            SwipeSide.BOTTOM -> onSwipe(Swipe(swiped, SwipeSide.BOTTOM))
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
                    if (itemsState.value.size > 2) {
                        content(itemsState.value[abs(currentIndex.value - 2)])
                    }
                }
            )
            Box(
                modifier = Modifier
                    .shadowPadding(stackSide, betweenCardsMargin, cardStackController, 2f)
                    .shadowHorizontalPadding(16.dp, cardStackController, 0f)
                    .align(Alignment.Center)
                    .shadow(0.dp, RoundedCornerShape(8.dp)),
                content = {
                    if (itemsState.value.size > 1) {
                        content(itemsState.value[abs(currentIndex.value - 1)])
                    }
                }
            )
            Box(
                modifier = Modifier
                    .shadowPaddingLayerThree(stackSide, betweenCardsMargin)
                    .align(Alignment.Center)
                    .draggableStack(cardStackController, orientation)
                    .moveTo(cardStackController, orientation)
                    .graphicsLayer(rotationZ = cardStackController.rotation.value)
                    .shadow(0.dp, RoundedCornerShape(8.dp)),
                content = { content(itemsState.value[currentIndex.value]) }
            )
        }
    }
}