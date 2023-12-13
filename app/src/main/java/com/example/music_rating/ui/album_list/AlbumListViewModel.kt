package com.example.music_rating.ui.album_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.music_rating.data.Album
import com.example.music_rating.data.AlbumRepository
import com.example.music_rating.util.Routes
import com.example.music_rating.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor(
    private val repository: AlbumRepository
): ViewModel() {

    val AlbumList = repository.getAllAlbums()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedAlbum: Album? = null     // Save most recently deleted album for snackbar

    // Definition logic for what to do for different events
    fun onEvent(event: AlbumListEvent) {
        when(event) {
            is AlbumListEvent.DeleteAlbum -> {
                viewModelScope.launch {
                    repository.deleteAlbum(event.album)
                    deletedAlbum = event.album  // Save most recently deleted album
                    sendUiEvent(UiEvent.ShowSnackbar(message = "Album deleted", action = "Undo"))
                }
            }

            is AlbumListEvent.OnUndoDeleteClick -> {
                deletedAlbum?.let {
                    album ->  viewModelScope.launch { repository.insertAlbum(album)}
                }
            }

            is AlbumListEvent.OnAlbumClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_ALBUM + "?albumID=${event.album.id}"))
            }

            is AlbumListEvent.OnAddAlbumClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_ALBUM))
            }
        }
    }

    // launch as coroutine
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch { _uiEvent.send(event) }
    }

}