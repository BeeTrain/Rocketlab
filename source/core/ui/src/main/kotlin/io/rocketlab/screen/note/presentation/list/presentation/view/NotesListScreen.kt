package io.rocketlab.screen.note.presentation.list.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.note.presentation.list.presentation.view.note.NoteCard
import io.rocketlab.screen.note.presentation.list.presentation.view.note.NoteDivider
import io.rocketlab.screen.note.presentation.list.presentation.viewmodel.NotesListViewModel
import io.rocketlab.ui.R
import io.rocketlab.ui.appbar.AppBar
import io.rocketlab.ui.theme.fabShape
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun NotesListScreen(
    viewModel: NotesListViewModel = getViewModel()
) {
    val notes by viewModel.notesState.collectAsState()
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
                LazyColumn(
                    state = scrollState
                ) {
                    itemsIndexed(notes) { index, note ->
                        NoteCard(
                            note = note,
                            onCardClick = {
                                viewModel.onNoteClickAction.accept(it)
                                coroutineScope.launch { scrollState.animateScrollToItem(index) }
                            },
                            onEditClick = { viewModel.onNoteEditAction.accept(it) },
                            onDeleteClick = { viewModel.onNoteDeleteAction.accept(it) }
                        )
                        NoteDivider(index == notes.lastIndex)
                    }
                }
            }
        }
    )
}