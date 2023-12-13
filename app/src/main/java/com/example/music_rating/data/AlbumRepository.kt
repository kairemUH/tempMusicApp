package com.example.music_rating.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {

    // There should not be duplicate albums
    suspend fun insertAlbum(album: Album)

    suspend fun updateAlbum(album: Album)

    suspend fun deleteAlbum(album: Album)

    suspend fun getAlbum(id: Int): Album?

    fun getAllAlbums(): Flow<List<Album>>

}