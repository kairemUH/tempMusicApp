package com.example.music_rating.ui.album_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.music_rating.util.UiEvent

@Composable
fun AlbumListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: AlbumListViewModel = hiltViewModel()
) {

    val AlbumList = viewModel.AlbumList.collectAsState(initial = emptyList())
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {   // This is the code that gets run when UIevent functions called
        viewModel.uiEvent.collect { event ->
            when(event) {
                // Undo snackbar
                is UiEvent.ShowSnackbar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = event.message, actionLabel = event.action
                    )
                    if(result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(AlbumListEvent.OnUndoDeleteClick)
                    }
                }
                // Navigate to new screen
                is UiEvent.Navigate -> onNavigate(event)

                // Do nothing
                else -> Unit
            }
        }
    }

    // Page itself
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AlbumListEvent.OnAddAlbumClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(AlbumList.value) { album ->
                AlbumCard(
                    album = album,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier.fillMaxWidth().clickable {  // Whole card is clickable to edit
                        viewModel.onEvent(AlbumListEvent.OnAlbumClick(album))
                    }.padding(16.dp)
                )
            }
        }
    }

}