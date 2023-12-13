package com.example.music_rating.ui.add_edit_album

sealed class AddEditAlbumEvent {

    data class OnNameChange(val name: String): AddEditAlbumEvent()
    data class OnArtistChange(val artist: String): AddEditAlbumEvent()
    data class OnRatingChange(val rating: Int): AddEditAlbumEvent()
    object OnSaveAlbumClick: AddEditAlbumEvent()

}