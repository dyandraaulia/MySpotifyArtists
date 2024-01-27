package id.my.myspotifyartists.model

data class Artist(
    val id: Long,
    val name: String,
    val photoUrl: String,
    val monthlyListener: String,
    val latestAlbum: String,
    val description: String,
    val latestReleasePhotoUrl: String,
    var isFavorite: Boolean = false,
)