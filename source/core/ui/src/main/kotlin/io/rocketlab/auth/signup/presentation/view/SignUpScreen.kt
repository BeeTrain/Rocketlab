package io.rocketlab.auth.signup.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.rocketlab.auth.signup.presentation.viewmodel.SignUpViewModel
import org.koin.androidx.compose.inject

@Composable
fun SignUpScreen() {
    val viewModel by inject<SignUpViewModel>()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                label = { Text("E-mail") },
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                label = { Text("Password") },
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                label = { Text("Confirm password") },
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
        Button(
            onClick = { },
            content = { Text("Register") },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}