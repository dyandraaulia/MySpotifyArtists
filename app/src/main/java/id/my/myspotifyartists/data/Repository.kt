package id.my.myspotifyartists.data

import id.my.myspotifyartists.model.Artist
import id.my.myspotifyartists.model.ArtistsData.artists
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class Repository {
    fun getFavoriteArtist(): Flow<Map<Char, List<Artist>>> = flowOf(
        artists.filter { it.isFavorite }
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )

    fun getArtistById(id: Long): Artist = artists.first {
        it.id == id
    }

    fun getAllOrderedArtist(): Flow<Map<Char, List<Artist>>> = flowOf(
        artists
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )

    fun search(query: String): Flow<Map<Char, List<Artist>>> = flowOf(
        artists
            .filter {
                it.name.contains(query, ignoreCase = true)
            }
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )


    fun updateFavorite(id: Long, newIsFavorite: Boolean) {
        val index = artists.indexOfFirst { it.id == id }
        artists[index].isFavorite = newIsFavorite
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(): Repository =
            instance ?: synchronized(this) {
                Repository().apply {
                    instance = this
                }
            }
    }
}