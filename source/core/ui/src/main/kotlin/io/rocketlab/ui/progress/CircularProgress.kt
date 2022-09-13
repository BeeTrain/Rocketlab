package io.rocketlab.ui.progress

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun CircularProgress(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.secondary,
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color,
        strokeWidth = strokeWidth
    )
}