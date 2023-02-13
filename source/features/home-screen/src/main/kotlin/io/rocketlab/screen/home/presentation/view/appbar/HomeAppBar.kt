package io.rocketlab.screen.home.presentation.view.appbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.rocketlab.screen.home.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            Row {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    modifier = Modifier
                        .size(36.dp),
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null
                )
            }
        },
        title = {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(text = stringResource(id = R.string.home_screen_profile_title))
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = null
                )
            }
        },
    )
}