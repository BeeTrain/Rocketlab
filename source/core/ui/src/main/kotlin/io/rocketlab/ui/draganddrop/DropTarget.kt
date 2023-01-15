package io.rocketlab.ui.draganddrop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned

@Suppress("UNCHECKED_CAST")
@Composable
fun <T> DropTarget(
    modifier: Modifier = Modifier,
    content: @Composable (BoxScope.(isInBound: Boolean, droppedData: T?) -> Unit)
) {
    val dragInfo = LocalDragTargetInfo.current
    val dragPosition = dragInfo.dragPosition
    val dragOffset = dragInfo.dragOffset
    var isCurrentDropTarget by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                coordinates.boundsInWindow().let { rect ->
                    isCurrentDropTarget = rect.contains(dragPosition + dragOffset) && dragInfo.isDragging
                }
            },
        content = {
            val droppedData = (dragInfo.dataToDrop as T?).takeIf { isCurrentDropTarget && dragInfo.isDragging.not() }
            content(isCurrentDropTarget, droppedData)
        }
    )
}