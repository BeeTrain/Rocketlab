package io.rocketlab.screen.auth.presentation.view.branding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.rocketlab.ui.R

@Composable
fun Branding(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(vertical = 24.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.img_rocketlab),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}