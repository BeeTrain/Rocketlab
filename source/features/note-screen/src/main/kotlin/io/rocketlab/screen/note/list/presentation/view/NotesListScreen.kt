package io.rocketlab.screen.note.list.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.note.R
import io.rocketlab.screen.note.list.presentation.model.NoteModel
import io.rocketlab.screen.note.list.presentation.model.NotesListScreenState
import io.rocketlab.screen.note.list.presentation.model.UpdateNoteStatusAction
import io.rocketlab.screen.note.list.presentation.view.dialog.DeleteNoteDialog
import io.rocketlab.screen.note.list.presentation.view.note.NotesColumn
import io.rocketlab.screen.note.list.presentation.viewmodel.NotesListViewModel
import io.rocketlab.storage.database.model.NoteStatus
import io.rocketlab.ui.appbar.AppBar
import io.rocketlab.ui.draganddrop.LongPressDraggableBox
import io.rocketlab.ui.progress.CircularProgress
import io.rocketlab.ui.theme.fabShape
import org.koin.androidx.compose.getViewModel

@Composable
fun NotesListScreen(
    viewModel: NotesListViewModel = getViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val showDeleteNoteDialog = viewModel.showDeleteNoteDialog.collectAsState()

    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(id = R.string.notes_screen_title),
                onBackPressed = { viewModel.onBackPressedAction.accept() }
            )
        },
        modifier = Modifier
            .navigationBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(
                shape = fabShape,
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = { viewModel.onAddNoteClickAction.accept() },
                content = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
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
                DeleteNoteEventDialog(showDeleteNoteDialog, viewModel)

                when (uiState) {
                    is NotesListScreenState.Content -> ContentState(
                        uiState = uiState.asContent(),
                        viewModel = viewModel
                    )
                    is NotesListScreenState.Loading -> LoadingState()
                }
            }
        }
    )
}

@Composable
private fun ContentState(
    uiState: NotesListScreenState.Content,
    viewModel: NotesListViewModel
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Url(stringResource(R.string.notes_screen_empty_state_image_url))
    )
    if (uiState.todoNotes.isEmpty() && uiState.doneNotes.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            LottieAnimation(composition)
        }
    } else {
        LongPressDraggableBox {
            NotesColumn(
                title = uiState.todoNotes.title,
                notes = uiState.todoNotes.notes,
                onNoteDropped = { viewModel.updateNoteStatus.accept(UpdateNoteStatusAction(it, NoteStatus.TODO)) },
                onNoteClick = { viewModel.onNoteClickAction.accept(it) }
            )
            NotesColumn(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                title = uiState.doneNotes.title,
                notes = uiState.doneNotes.notes,
                onNoteDropped = { viewModel.updateNoteStatus.accept(UpdateNoteStatusAction(it, NoteStatus.DONE)) },
                onNoteClick = { viewModel.onNoteClickAction.accept(it) }
            )
        }
    }
}

@Composable
private fun BoxScope.LoadingState() {
    CircularProgress(
        modifier = Modifier
            .size(56.dp)
            .align(Alignment.Center)
    )
}

@Composable
private fun DeleteNoteEventDialog(
    showDeleteNoteDialog: State<NoteModel?>,
    viewModel: NotesListViewModel
) {
    showDeleteNoteDialog.value?.let { note ->
        DeleteNoteDialog(
            note = note,
            onApply = { viewModel.deleteNoteAction.accept(note) },
            onCancel = { viewModel.onDismissDeleteDialogAction.accept() },
            onDismiss = { viewModel.onDismissDeleteDialogAction.accept() }
        )
    }
}