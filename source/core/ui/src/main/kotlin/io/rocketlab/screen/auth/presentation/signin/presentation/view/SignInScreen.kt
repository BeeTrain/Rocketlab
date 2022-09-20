package io.rocketlab.screen.auth.presentation.signin.presentation.view

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import io.rocketlab.arch.extension.accept
import io.rocketlab.arch.extension.collectAsCommand
import io.rocketlab.screen.auth.presentation.signin.presentation.model.SignInErrorState
import io.rocketlab.screen.auth.presentation.signin.presentation.model.SignInScreenState
import io.rocketlab.screen.auth.presentation.signin.presentation.viewmodel.SignInViewModel
import io.rocketlab.screen.auth.presentation.view.text.email.EmailField
import io.rocketlab.screen.auth.presentation.view.text.password.PasswordField
import io.rocketlab.ui.R
import io.rocketlab.ui.extension.supportWideScreen
import io.rocketlab.ui.progress.CircularProgress
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
    val interactionSource = remember { MutableInteractionSource() }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val uiState by viewModel.uiState.collectAsState()
    val errorState by viewModel.errorState.collectAsState()

    val openSignUpCommand by viewModel.openSignUpCommand.collectAsCommand()
    val googleSignCommand by viewModel.launchGoogleSignCommand.collectAsCommand()
    val onLoggedCommand by viewModel.onLoggedCommand.collectAsCommand()

    val startForResult = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data != null) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val account = task.getResult(ApiException::class.java)
                account?.let { viewModel.onGoogleAccountReceivedAction.accept(it) }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .supportWideScreen()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { focusManager.clearFocus() }
                )
        ) {
            when (uiState) {
                is SignInScreenState.Content -> renderContent(
                    uiState = uiState.asContent(),
                    viewModel = viewModel
                )
                is SignInScreenState.Loading -> renderLoading()
            }
            if (errorState.message.isNotEmpty()) {
                renderError(coroutineScope, scaffoldState, errorState)
                viewModel.onErrorShowedAction.accept()
            }
        }
    }
    LaunchedEffect(openSignUpCommand) {
        openSignUpCommand?.let { onRegisterClicked.invoke() }
    }
    LaunchedEffect(googleSignCommand) {
        googleSignCommand?.let { startForResult.launch(it) }
    }
    LaunchedEffect(onLoggedCommand) {
        onLoggedCommand?.let { onLogged.invoke() }
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
    uiState: SignInScreenState.Content,
    viewModel: SignInViewModel
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
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = stringResource(R.string.sign_via_google_button_title),
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { viewModel.googleSignClickedAction.accept() }
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                enabled = uiState.isFieldsValid,
                onClick = { viewModel.loginClickedAction.accept() },
                content = { Text(stringResource(R.string.sign_in_button_title)) },
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.CenterVertically)
            )
            Button(
                onClick = { viewModel.registerClickedAction.accept() },
                content = { Text(stringResource(R.string.sign_up_button_title)) },
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
private fun renderError(
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
    error: SignInErrorState
) {
    coroutineScope.launch {
        scaffoldState.snackbarHostState.showSnackbar(
            message = error.message
        )
    }
}