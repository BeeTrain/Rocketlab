package io.rocketlab.ui.swipe.modifier

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

enum class Direction {
    Left,
    Right,
    Up,
    Down
}

data class SwipeModel<T>(
    val item: T,
    val swipeState: SwipeableCardState
)

@Composable
fun <T> T.toSwipeModel(
): SwipeModel<T> {
    return SwipeModel(
        item = this,
        swipeState = rememberSwipeableCardState()
    )
}

@Composable
fun rememberSwipeableCardState(): SwipeableCardState {
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }
    val screenHeight = with(LocalDensity.current) {
        LocalConfiguration.current.screenHeightDp.dp.toPx()
    }
    return remember {
        SwipeableCardState(screenWidth, screenHeight)
    }
}

class SwipeableCardState(
    val maxWidth: Float,
    val maxHeight: Float
) {

    val offset = Animatable(offset(0f, 0f), Offset.VectorConverter)

    var swipedDirection: Direction? by mutableStateOf(null)
        private set

    suspend fun reset() {
        offset.animateTo(offset(0f, 0f), tween(400))
    }

    suspend fun drag(x: Float, y: Float) {
        offset.animateTo(offset(x, y))
    }

    suspend fun swipe(direction: Direction, animationSpec: AnimationSpec<Offset> = tween(400)) {
        val endX = maxWidth * 1.5f
        val endY = maxHeight
        when (direction) {
            Direction.Left -> offset.animateTo(offset(x = -endX), animationSpec)
            Direction.Right -> offset.animateTo(offset(x = endX), animationSpec)
            Direction.Up -> offset.animateTo(offset(y = -endY), animationSpec)
            Direction.Down -> offset.animateTo(offset(y = endY), animationSpec)
        }
        this.swipedDirection = direction
    }

    private fun offset(
        x: Float = offset.value.x,
        y: Float = offset.value.y
    ): Offset {
        return Offset(x, y)
    }
}