package id.my.myspotifyartists.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DetailArtist : Screen("detail/{artistId}") {
        fun createRoute(artistId: Long) = "detail/$artistId"
    }
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
}