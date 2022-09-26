package io.rocketlab.screen.note.presentation.editor.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.note.presentation.editor.presentation.model.NoteEditorScreenState
import io.rocketlab.screen.note.presentation.editor.presentation.view.text.NoteTextField
import io.rocketlab.screen.note.presentation.editor.presentation.viewmodel.NoteEditorViewModel
import io.rocketlab.ui.R
import io.rocketlab.ui.appbar.AppBar
import io.rocketlab.ui.progress.CircularProgress
import org.koin.androidx.compose.getViewModel

@Composable
fun NoteEditorScreen(
    viewModel: NoteEditorViewModel = getViewModel()
) {
    val uiState by viewModel.noteEditorScreenState.collectAsState()
    val isLoading by viewModel.loading.collectAsState()

    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.note_screen_title),
                onBackPressed = { viewModel.onBackPressedAction.accept() },
                actions = {
                    IconButton(
                        content = {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null
                            )
                        },
                        onClick = { viewModel.onCheckClickAction.accept() }
                    )
                    IconButton(
                        content = {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = null
                            )
                        },
                        onClick = { viewModel.onDeleteClickAction.accept() }
                    )
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                when {
                    isLoading -> renderLoading()
                    else -> renderContent(
                        uiState = uiState,
                        viewModel = viewModel
                    )
                }
            }
        }
    )
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
    uiState: NoteEditorScreenState,
    viewModel: NoteEditorViewModel
) {
    NoteTextField(
        state = uiState.textField,
        updateValueAction = { viewModel.updateNoteTextAction.accept(it) }
    )
}