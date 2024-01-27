package id.my.myspotifyartists

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.my.myspotifyartists.ui.components.home.BottomBar
import id.my.myspotifyartists.ui.navigation.Screen
import id.my.myspotifyartists.ui.pages.detailartist.DetailArtistScreen
import id.my.myspotifyartists.ui.pages.favorite.FavoriteArtist
import id.my.myspotifyartists.ui.pages.home.HomeScreen
import id.my.myspotifyartists.ui.pages.profile.ProfileContent
import id.my.myspotifyartists.ui.theme.MySpotifyArtistsTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MySpotifyArtistsApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailArtist.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { artistId ->
                        navController.navigate(Screen.DetailArtist.createRoute(artistId))
                    }
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteArtist(navigateToDetail = { id ->
                    navController.navigate(Screen.DetailArtist.createRoute(id))
                })
            }
            composable(Screen.Profile.route) {
                ProfileContent()
            }
            composable(
                route = Screen.DetailArtist.route,
                arguments = listOf(navArgument("artistId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("artistId") ?: -1L
                DetailArtistScreen(id = id, navigateBack = { navController.navigateUp() })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MySpotifyArtistsAppPreview() {
    MySpotifyArtistsTheme {
        MySpotifyArtistsApp()
    }
}