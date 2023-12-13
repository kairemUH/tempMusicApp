package com.example.music_rating.data

import kotlinx.coroutines.flow.Flow

class AlbumRepositoryImpl(
    private val albumDao: AlbumDao
): AlbumRepository {

    // There should not be duplicate albums
    override suspend fun insertAlbum(album: Album) = albumDao.insertAlbum(album)

    override suspend fun updateAlbum(album: Album) = albumDao.updateAlbum(album)

    override suspend fun deleteAlbum(album: Album) = albumDao.deleteAlbum(album)

    override suspend fun getAlbum(id: Int): Album? = albumDao.getAlbum(id)

    override fun getAllAlbums(): Flow<List<Album>> = albumDao.getAllAlbums()

}