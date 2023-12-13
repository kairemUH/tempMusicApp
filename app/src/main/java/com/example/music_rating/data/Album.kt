package com.example.music_rating.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class Album(
    val name: String,
    val artist: String,
    val rating: Int,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)
