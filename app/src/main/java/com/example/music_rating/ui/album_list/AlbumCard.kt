package com.example.music_rating.ui.album_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.music_rating.data.Album

// Single Album Card Component
@Composable
fun AlbumCard(album: Album, onEvent: (AlbumListEvent) -> Unit, modifier: Modifier = Modifier) {
    Row (modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Column (modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = album.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(8.dp))

            }

            Text(
                text = album.artist,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            )

            Text(
                text = album.rating.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

        }
        Column () {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")

            IconButton(onClick = { onEvent(AlbumListEvent.DeleteAlbum(album)) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
