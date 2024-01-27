package id.my.myspotifyartists.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.my.myspotifyartists.data.Repository
import id.my.myspotifyartists.ui.pages.detailartist.DetailArtistViewModel
import id.my.myspotifyartists.ui.pages.favorite.FavoriteArtistsViewModel
import id.my.myspotifyartists.ui.pages.home.HomeViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(DetailArtistViewModel::class.java)) {
            return DetailArtistViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(FavoriteArtistsViewModel::class.java)) {
            return FavoriteArtistsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}