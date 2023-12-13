package com.example.music_rating

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.music_rating.ui.add_edit_album.AddEditAlbumScreen
import com.example.music_rating.ui.album_list.AlbumListScreen
import com.example.music_rating.ui.theme.Music_ratingTheme
import com.example.music_rating.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Music_ratingTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.ALBUM_LIST
                ) {
                    composable(Routes.ALBUM_LIST) {
                        AlbumListScreen(onNavigate = { navController.navigate(it.route) })
                    }
                    composable(
                        route = Routes.ADD_EDIT_ALBUM + "?albumID={albumID}",
                        arguments = listOf(
                            navArgument(name = "albumID") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        AddEditAlbumScreen(onPopBackStack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}
