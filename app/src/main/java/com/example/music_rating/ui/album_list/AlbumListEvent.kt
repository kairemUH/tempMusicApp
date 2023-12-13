package com.example.music_rating.ui.album_list

import com.example.music_rating.data.Album

// All possible events on the Album List page
sealed class AlbumListEvent {
    data class DeleteAlbum(val album: Album): AlbumListEvent()
    object OnUndoDeleteClick: AlbumListEvent()
    data class OnAlbumClick(val album: Album): AlbumListEvent()
    object OnAddAlbumClick: AlbumListEvent()
}