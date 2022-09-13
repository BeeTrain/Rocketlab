package io.rocketlab.auth.signin.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.rocketlab.auth.signin.presentation.model.SignInState
import io.rocketlab.auth.signin.presentation.viewmodel.SignInViewModel
import io.rocketlab.ui.progress.CircularProgress
import io.rocketlab.ui.text.OutlinedValidatedTextField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.inject

@Composable
fun SignInScreen(
    onRegisterClicked: (() -> Unit),
    onLogged: (() -> Unit)
) {
    val viewModel by inject<SignInViewModel>()
    val focusManager = LocalFocusManager.current
    val uiState by viewModel.uiState.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { focusManager.clearFocus() }
                )
        ) {
            when (uiState) {
                is SignInState.Content -> {
                    renderContent(
                        uiState = uiState as SignInState.Content,
                        onEmailValueChange = { viewModel.updateEmail(it) },
                        onPasswordValueChange = { viewModel.updatePassword(it) },
                        onRegisterClicked = onRegisterClicked,
                        onLoginClicked = { viewModel.onLoginClicked(onLogged) },
                        onPasswordVisibilityClicked = { viewModel.onPasswordVisibilityClicked() }
                    )
                    (uiState as? SignInState.Content)?.error?.let { error ->
                        coroutineScope.renderError(scaffoldState, error)
                    }
                }
                is SignInState.Loading -> renderLoading()
                is SignInState.Error -> coroutineScope.renderError(scaffoldState, uiState as SignInState.Error)
            }
        }
    }
}

@Composable
private fun BoxScope.renderLoading() {
    CircularProgress(
        modifier = Modifier
            .size(56.dp)
            .align(Alignment.Center)
    )
}

@Composable
private fun BoxScope.renderContent(
    uiState: SignInState.Content,
    onEmailValueChange: (String) -> Unit,
    onPasswordValueChange: (String) -> Unit,
    onRegisterClicked: () -> Unit,
    onLoginClicked: () -> Unit,
    onPasswordVisibilityClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center)
            .padding(8.dp)
    ) {
        OutlinedValidatedTextField(
            label = { Text(uiState.eMail.label) },
            value = uiState.eMail.value,
            error = uiState.eMail.error,
            isError = uiState.eMail.error.isNotEmpty(),
            onValueChange = { onEmailValueChange.invoke(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
        OutlinedValidatedTextField(
            label = { Text(uiState.password.label) },
            value = uiState.password.value,
            error = uiState.password.error,
            isError = uiState.password.error.isNotEmpty(),
            onValueChange = { onPasswordValueChange.invoke(it) },
            visualTransformation = uiState.password.passwordTransformation,
            trailingIcon = {
                IconButton(
                    onClick = { onPasswordVisibilityClicked.invoke() },
                    content = {
                        Icon(
                            imageVector = uiState.password.visibilityIcon,
                            contentDescription = uiState.password.description
                        )
                    }
                )
            },
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier.align(Alignment.End)
        ) {
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = onRegisterClicked,
                content = { Text("Register") }
            )
            Button(
                modifier = Modifier.padding(8.dp),
                enabled = uiState.isLoginButtonEnabled,
                onClick = onLoginClicked,
                content = { Text("Login") }
            )
        }
    }
}

@Composable
private fun CoroutineScope.renderError(
    scaffoldState: ScaffoldState,
    error: SignInState.Error
) {
    launch {
        scaffoldState.snackbarHostState.showSnackbar(
            message = error.message
        )
    }
}