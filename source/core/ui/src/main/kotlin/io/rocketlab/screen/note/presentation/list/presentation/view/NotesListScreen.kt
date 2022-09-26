package io.rocketlab.screen.note.presentation.list.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.rocketlab.arch.extension.accept
import io.rocketlab.screen.note.presentation.list.presentation.viewmodel.NotesListViewModel
import io.rocketlab.ui.R
import io.rocketlab.ui.appbar.AppBar
import io.rocketlab.ui.theme.fabShape
import org.koin.androidx.compose.getViewModel

@Composable
fun NotesListScreen(
    viewModel: NotesListViewModel = getViewModel()
) {
    val notes by viewModel.notesState.collectAsState()

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
                LazyColumn {
                    itemsIndexed(notes) { index, note ->
                        Text(
                            text = "$index: $note",
                            modifier = Modifier
                                .clickable { viewModel.openNoteAction.accept(note) }
                        )
                    }
                }
            }
        }
    )
}