package id.my.myspotifyartists.ui.pages.detailartist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.my.myspotifyartists.ui.common.UiState
import id.my.myspotifyartists.data.Repository
import id.my.myspotifyartists.model.Artist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailArtistViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Artist>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Artist>>
        get() = _uiState


    fun getArtistById(id: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getArtistById(id))
        }
    }

    fun addToFavorite(id: Long, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateFavorite(id, isFavorite)
        }
    }


}