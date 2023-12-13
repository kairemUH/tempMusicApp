package com.example.music_rating.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Album::class], version = 1, exportSchema = false)
abstract class AlbumDatabase: RoomDatabase() {

    abstract fun albumDao(): AlbumDao

    companion object {
        @Volatile
        private var Instance: AlbumDatabase? = null

        fun getDatabase(context: Context): AlbumDatabase {
            // If the Instance is not null, return it, otherwise create a new database instance.
            // Ensures no duplicate databases on race conditions
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AlbumDatabase::class.java, "album_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }

}