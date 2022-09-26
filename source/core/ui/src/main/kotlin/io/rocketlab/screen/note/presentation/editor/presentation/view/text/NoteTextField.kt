package io.rocketlab.screen.note.presentation.editor.presentation.view.text

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteTextField(
    state: NoteTextFieldState,
    updateValueAction: (String) -> Unit = {},
) {
//    val isFocused = remember { mutableStateOf(false) }
//    val focusRequester = remember { FocusRequester() }
//    val focusManager = LocalFocusManager.current

    TextField(
        value = state.value,
        onValueChange = { updateValueAction.invoke(it) },
        modifier = Modifier
            .fillMaxSize()
//            .focusRequester(focusRequester)
//            .onFocusChanged { focusState ->
//                if (!focusState.isFocused && state.value.isEmpty()) {
//                    focusRequester.requestFocus()
//                }
//                if (isFocused.value != focusState.isFocused) {
//                    isFocused.update { focusState.isFocused }
//
//                    if (!focusState.isFocused) {
//                        focusManager.clearFocus()
//                    }
//                }
//            }
    )
}