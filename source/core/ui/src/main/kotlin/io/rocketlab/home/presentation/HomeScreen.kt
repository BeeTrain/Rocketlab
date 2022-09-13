package io.rocketlab.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    onNotesClicked: () -> Unit
) {
    Scaffold(
        topBar = { Spacer(modifier = Modifier.statusBarsPadding()) },
        modifier = Modifier
            .navigationBarsPadding(),
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Text(
                    text = "Home",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.navigationBarsPadding(),
                onClick = { onNotesClicked.invoke() },
                content = {}
            )
        }
    )
}