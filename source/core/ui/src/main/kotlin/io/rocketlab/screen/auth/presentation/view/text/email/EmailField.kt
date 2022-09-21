package io.rocketlab.screen.auth.presentation.view.text.email

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import io.rocketlab.ui.R
import io.rocketlab.ui.extension.afterFocusChanged
import io.rocketlab.ui.text.TextField
import io.rocketlab.ui.text.TextFieldLabel

@Composable
fun EmailField(
    emailState: EmailFieldState = remember { EmailFieldState() },
    updateValueAction: (String) -> Unit = {},
    validateValueAction: (FocusState) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val isEmailFocused = remember { mutableStateOf(false) }

    TextField(
        label = { TextFieldLabel(stringResource(R.string.e_mail_field_title)) },
        value = emailState.value,
        error = emailState.error,
        isError = emailState.error.isNotEmpty(),
        onValueChange = updateValueAction,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        textStyle = MaterialTheme.typography.bodyMedium,
        singleLine = true,
        maxLines = 1,
        modifier = modifier
            .fillMaxWidth()
            .afterFocusChanged(isEmailFocused) { validateValueAction.invoke(it) }
    )
}