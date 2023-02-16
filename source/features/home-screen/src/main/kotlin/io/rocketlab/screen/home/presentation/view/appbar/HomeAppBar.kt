package io.rocketlab.screen.home.presentation.view.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    modifier: Modifier = Modifier,
    userName: String,
    userPhotoUrl: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            onClick = onClick
        ) {
            Row {
                UserImage(
                    modifier = Modifier.size(36.dp),
                    userPhotoUrl = userPhotoUrl
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = userName,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.CenterVertically),
                    imageVector = Icons.Filled.ChevronRight,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun UserImage(
    modifier: Modifier = Modifier,
    userPhotoUrl: String?
) {
    if (userPhotoUrl != null) {
        AsyncImage(
            modifier = modifier.clip(CircleShape),
            model = userPhotoUrl,
            contentDescription = null
        )
    } else {
        Icon(
            modifier = modifier,
            imageVector = Icons.Filled.AccountCircle,
            tint = MaterialTheme.colorScheme.onBackground,
            contentDescription = null
        )
    }
}