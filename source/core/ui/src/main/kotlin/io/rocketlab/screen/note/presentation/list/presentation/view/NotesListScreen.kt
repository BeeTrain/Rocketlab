package io.rocketlab.screen.note.presentation.list.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.note.presentation.list.presentation.model.NoteModel
import io.rocketlab.screen.note.presentation.list.presentation.model.NotesListScreenState
import io.rocketlab.screen.note.presentation.list.presentation.view.dialog.DeleteNoteDialog
import io.rocketlab.screen.note.presentation.list.presentation.view.note.NoteCard
import io.rocketlab.screen.note.presentation.list.presentation.view.note.NoteDivider
import io.rocketlab.screen.note.presentation.list.presentation.viewmodel.NotesListViewModel
import io.rocketlab.ui.R
import io.rocketlab.ui.appbar.AppBar
import io.rocketlab.ui.progress.CircularProgress
import io.rocketlab.ui.theme.fabShape
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun NotesListScreen(
    viewModel: NotesListViewModel = getViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val showDeleteNoteDialog = viewModel.showDeleteNoteDialog.collectAsState()

    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

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
                        contentDescription = stringResource(R.string.home_screen_add_note_title),
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
                showDeleteDialogIfNeed(showDeleteNoteDialog, viewModel)

                when (uiState) {
                    is NotesListScreenState.Content -> renderContent(
                        uiState = uiState.asContent(),
                        viewModel = viewModel,
                        scrollState = scrollState,
                        coroutineScope = coroutineScope
                    )
                    is NotesListScreenState.Loading -> renderLoading()
                }
            }
        }
    )
}

@Composable
private fun renderContent(
    uiState: NotesListScreenState.Content,
    viewModel: NotesListViewModel,
    scrollState: LazyListState,
    coroutineScope: CoroutineScope
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Url(stringResource(R.string.notes_screen_empty_state_image_url))
    )
    if (uiState.notes.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            LottieAnimation(composition)
        }
    } else {
        LazyColumn(
            state = scrollState
        ) {
            itemsIndexed(uiState.notes) { index, note ->
                NoteCard(
                    note = note,
                    onCardClick = {
                        viewModel.onNoteClickAction.accept(it)
                        coroutineScope.launch { scrollState.animateScrollToItem(index) }
                    },
                    onEditClick = { viewModel.onNoteEditClickAction.accept(it) },
                    onDeleteClick = { viewModel.onNoteDeleteClickAction.accept(it) }
                )
                NoteDivider(index == uiState.notes.lastIndex)
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
private fun showDeleteDialogIfNeed(
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