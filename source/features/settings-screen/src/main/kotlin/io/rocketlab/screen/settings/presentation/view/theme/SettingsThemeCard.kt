package io.rocketlab.screen.settings.presentation.view.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsThemeCard(
    painter: Painter,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        onClick = onClick
    ) {
        Image(
            modifier = Modifier
                .padding(4.dp),
            painter = painter,
            contentDescription = null
        )
        RadioButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            enabled = false,
            colors = RadioButtonDefaults.colors(
                disabledSelectedColor = MaterialTheme.colorScheme.onBackground,
                disabledUnselectedColor = MaterialTheme.colorScheme.onBackground
            ),
            selected = isSelected,
            onClick = { }
        )
    }
}