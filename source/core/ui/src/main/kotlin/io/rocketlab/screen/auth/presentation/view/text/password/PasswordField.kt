package io.rocketlab.screen.auth.presentation.view.text.password

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.res.stringResource
import io.rocketlab.ui.R
import io.rocketlab.ui.extension.afterFocusChanged
import io.rocketlab.ui.text.TextField
import io.rocketlab.ui.text.TextFieldLabel

@Composable
fun PasswordField(
    label: String = stringResource(R.string.password_field_title),
    passwordState: PasswordFieldState = remember { PasswordFieldState() },
    updateValueAction: (String) -> Unit = {},
    validateValueAction: (FocusState) -> Unit = {},
    updatePasswordVisibilityAction: () -> Unit = {}
) {
    val isPasswordFocused = remember { mutableStateOf(false) }

    TextField(
        label = { TextFieldLabel(label) },
        value = passwordState.value,
        error = passwordState.error,
        isError = passwordState.error.isNotEmpty(),
        onValueChange = updateValueAction,
        textStyle = MaterialTheme.typography.bodyMedium,
        visualTransformation = passwordState.passwordTransformation,
        trailingIcon = {
            IconButton(
                onClick = updatePasswordVisibilityAction,
                content = {
                    Icon(
                        imageVector = passwordState.visibilityIcon,
                        contentDescription = stringResource(passwordState.descriptionRes)
                    )
                }
            )
        },
        singleLine = true,
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .afterFocusChanged(isPasswordFocused, validateValueAction)
    )
}

@Composable
fun PasswordConfirmField(
    passwordState: PasswordFieldState = remember { PasswordFieldState() },
    updateValueAction: (String) -> Unit = {},
    validateValueAction: (FocusState) -> Unit = {},
    updatePasswordVisibilityAction: () -> Unit = {}
) = PasswordField(
    label = stringResource(R.string.password_confirm_field_title),
    passwordState = passwordState,
    updateValueAction = updateValueAction,
    validateValueAction = validateValueAction,
    updatePasswordVisibilityAction = updatePasswordVisibilityAction
)