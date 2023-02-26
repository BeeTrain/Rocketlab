package io.rocketlab.ui.swipe.cardstack

enum class StackGravity {
    START,
    END,
    TOP,
    BOTTOM
}

enum class SwipeSide {
    LEFT,
    TOP,
    RIGHT,
    BOTTOM
}

data class Swipe<T>(val item: T, val side: SwipeSide)