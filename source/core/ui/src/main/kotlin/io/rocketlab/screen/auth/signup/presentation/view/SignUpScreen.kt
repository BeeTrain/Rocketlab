package io.rocketlab.screen.auth.signup.presentation.view

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.auth.signup.presentation.model.SignUpErrorState
import io.rocketlab.screen.auth.signup.presentation.model.SignUpScreenState
import io.rocketlab.screen.auth.signup.presentation.model.asContent
import io.rocketlab.screen.auth.signup.presentation.viewmodel.SignUpViewModel
import io.rocketlab.ui.extension.afterFocusChanged
import io.rocketlab.ui.extension.hideKeyboardOnClick
import io.rocketlab.ui.progress.CircularProgress
import io.rocketlab.ui.text.OutlinedValidatedTextField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.inject

@Composable
fun SignUpScreen(
    onRegistered: () -> Unit
) {
    val viewModel by inject<SignUpViewModel>()
    val focusManager = LocalFocusManager.current
    val uiState by viewModel.uiState.collectAsState()
    val errorState by viewModel.errorState.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .hideKeyboardOnClick(interactionSource, focusManager)
        ) {
            when (uiState) {
                is SignUpScreenState.Loading -> renderLoading()
                is SignUpScreenState.Content -> renderContent(uiState.asContent(), viewModel, onRegistered)
            }
            if (errorState != SignUpErrorState.None) {
                renderError(coroutineScope, scaffoldState, errorState)
                viewModel.onErrorShowedAction.accept()
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
    uiState: SignUpScreenState.Content,
    viewModel: SignUpViewModel,
    onRegistered: () -> Unit,
) {
    val isEmailFocused = remember { mutableStateOf(false) }
    val isPasswordFocused = remember { mutableStateOf(false) }
    val isPasswordConfirmFocused = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center)
            .padding(16.dp)
    ) {
        OutlinedValidatedTextField(
            label = { Text(uiState.eMail.label) },
            value = uiState.eMail.value,
            error = uiState.eMail.error,
            isError = uiState.eMail.error.isNotEmpty(),
            onValueChange = { viewModel.updateEmailAction.accept(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .afterFocusChanged(isEmailFocused) { viewModel.validateEmailAction.accept() }
        )
        OutlinedValidatedTextField(
            label = { Text(uiState.password.label) },
            value = uiState.password.value,
            error = uiState.password.error,
            isError = uiState.password.error.isNotEmpty(),
            onValueChange = { viewModel.updatePasswordAction.accept(it) },
            visualTransformation = uiState.password.passwordTransformation,
            trailingIcon = {
                IconButton(
                    onClick = { viewModel.updatePasswordVisibilityAction.accept() },
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
                .fillMaxWidth()
                .afterFocusChanged(isPasswordFocused) { viewModel.validatePasswordAction.accept() }
        )
        OutlinedValidatedTextField(
            label = { Text(uiState.passwordConfirm.label) },
            value = uiState.passwordConfirm.value,
            error = uiState.passwordConfirm.error,
            isError = uiState.passwordConfirm.error.isNotEmpty(),
            onValueChange = { viewModel.updatePasswordConfirmAction.accept(it) },
            visualTransformation = uiState.passwordConfirm.passwordTransformation,
            trailingIcon = {
                IconButton(
                    onClick = { viewModel.updatePasswordConfirmVisibilityAction.accept() },
                    content = {
                        Icon(
                            imageVector = uiState.passwordConfirm.visibilityIcon,
                            contentDescription = uiState.passwordConfirm.description
                        )
                    }
                )
            },
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .afterFocusChanged(isPasswordConfirmFocused) { viewModel.validatePasswordConfirmAction.accept() }
        )
        Button(
            modifier = Modifier.padding(8.dp),
            enabled = uiState.isFieldsValid,
            onClick = { viewModel.registerClickedAction.accept(onRegistered) },
            content = { Text("Register") }
        )
    }
}

@Composable
private fun renderError(
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
    error: SignUpErrorState
) {
    coroutineScope.launch {
        scaffoldState.snackbarHostState.showSnackbar(
            message = error.message
        )
    }
}