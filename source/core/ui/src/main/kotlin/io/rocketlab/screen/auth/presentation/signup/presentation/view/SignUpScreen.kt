package io.rocketlab.screen.auth.presentation.signup.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.auth.presentation.signup.presentation.model.SignUpErrorState
import io.rocketlab.screen.auth.presentation.signup.presentation.model.SignUpScreenState
import io.rocketlab.screen.auth.presentation.signup.presentation.viewmodel.SignUpViewModel
import io.rocketlab.screen.auth.presentation.view.text.email.EmailField
import io.rocketlab.screen.auth.presentation.view.text.password.PasswordConfirmField
import io.rocketlab.screen.auth.presentation.view.text.password.PasswordField
import io.rocketlab.ui.R
import io.rocketlab.ui.extension.hideKeyboardOnClick
import io.rocketlab.ui.progress.CircularProgress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = getViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val errorState by viewModel.errorState.collectAsState()

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .hideKeyboardOnClick(LocalFocusManager.current)
        ) {
            when (uiState) {
                is SignUpScreenState.Loading -> renderLoading()
                is SignUpScreenState.Content -> renderContent(uiState.asContent(), viewModel)
            }
            if (errorState.message.isNotEmpty()) {
                renderError(scaffoldState, errorState)
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
    viewModel: SignUpViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center)
            .padding(horizontal = 16.dp)
    ) {
        EmailField(
            emailState = uiState.eMail,
            updateValueAction = { viewModel.updateEmailAction.accept(it) },
            validateValueAction = { viewModel.validateEmailAction.accept() }
        )
        PasswordField(
            passwordState = uiState.password,
            updateValueAction = { viewModel.updatePasswordAction.accept(it) },
            validateValueAction = { viewModel.validatePasswordAction.accept() },
            updatePasswordVisibilityAction = { viewModel.updatePasswordVisibilityAction.accept() }
        )
        PasswordConfirmField(
            passwordState = uiState.passwordConfirm,
            updateValueAction = { viewModel.updatePasswordConfirmAction.accept(it) },
            validateValueAction = { viewModel.validatePasswordConfirmAction.accept() },
            updatePasswordVisibilityAction = { viewModel.updatePasswordConfirmVisibilityAction.accept() }
        )
        Button(
            enabled = uiState.isFieldsValid,
            onClick = { viewModel.registerClickedAction.accept() },
            content = { Text(stringResource(R.string.sign_up_button_title)) },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.End),
        )
    }
}

@Composable
private fun renderError(
    scaffoldState: ScaffoldState,
    error: SignUpErrorState,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    coroutineScope.launch {
        scaffoldState.snackbarHostState.showSnackbar(
            message = error.message
        )
    }
}