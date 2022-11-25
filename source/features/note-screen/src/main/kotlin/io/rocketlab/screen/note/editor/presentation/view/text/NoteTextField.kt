package io.rocketlab.screen.note.editor.presentation.view.text

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import io.rocketlab.ui.extension.update

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteTextField(
    state: NoteTextFieldState,
    updateValueAction: (String) -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }
    val textFieldValueState = remember {
        mutableStateOf(
            TextFieldValue(
                text = state.value,
                selection = TextRange(state.value.length)
            )
        )
    }

    TextField(
        value = textFieldValueState.value,
        onValueChange = { newTextFieldValue ->
            textFieldValueState.update { newTextFieldValue }
            updateValueAction.invoke(newTextFieldValue.text)
        },
        modifier = Modifier
            .fillMaxSize()
            .focusRequester(focusRequester)
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}