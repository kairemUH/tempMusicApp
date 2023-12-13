package com.example.music_rating.di

import android.app.Application
import androidx.room.Room
import com.example.music_rating.data.AlbumDatabase
import com.example.music_rating.data.AlbumRepository
import com.example.music_rating.data.AlbumRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAlbumDatabase(app: Application): AlbumDatabase {
        return Room.databaseBuilder(app, AlbumDatabase::class.java, "Album_database").build()
    }

    @Provides
    @Singleton
    fun provideAlbumRepository(db: AlbumDatabase): AlbumRepository {
        return AlbumRepositoryImpl(db.albumDao())
    }

}