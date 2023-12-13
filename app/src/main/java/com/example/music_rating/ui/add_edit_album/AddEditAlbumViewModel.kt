package com.example.music_rating.ui.add_edit_album

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.music_rating.data.Album
import com.example.music_rating.data.AlbumRepository
import com.example.music_rating.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditAlbumViewModel @Inject constructor(
    private val repository: AlbumRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var album by mutableStateOf<Album?>(null)
        private set

    var name by mutableStateOf("")
        private set

    var artist by mutableStateOf("")
        private set

    var rating by mutableIntStateOf(0)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val albumId = savedStateHandle.get<Int>("albumId")!!
        if(albumId != -1) {
            viewModelScope.launch {
                repository.getAlbum(albumId)?.let { album ->
                    name = album.name
                    artist = album.artist ?: ""
                    rating = album.rating
                    this@AddEditAlbumViewModel.album = album
                }
            }
        }
    }

    fun onEvent(event: AddEditAlbumEvent) {
        when(event) {
            is AddEditAlbumEvent.OnNameChange -> {
                name = event.name
            }
            is AddEditAlbumEvent.OnArtistChange -> {
                artist = event.artist
            }
            is AddEditAlbumEvent.OnRatingChange -> {
                rating = event.rating
            }
            is AddEditAlbumEvent.OnSaveAlbumClick -> {
                viewModelScope.launch {
                    if (name.isBlank()) {
                        sendUiEvent(UiEvent.ShowSnackbar(message = "The album name cannot be empty."))
                        return@launch
                    }
                    repository.insertAlbum(
                        Album(
                            name = name,
                            artist = artist,
                            rating = rating,
                            id = album?.id
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch { _uiEvent.send(event) }
    }

}