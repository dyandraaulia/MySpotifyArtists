package id.my.myspotifyartists.ui.pages.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.my.myspotifyartists.data.Repository
import id.my.myspotifyartists.model.Artist
import id.my.myspotifyartists.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteArtistsViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Map<Char, List<Artist>>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Map<Char, List<Artist>>>>
        get() = _uiState

    fun getFavoriteArtists() {
        viewModelScope.launch {
            repository.getFavoriteArtist()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { artists ->
                    if (artists.isEmpty()) {
                        _uiState.value = UiState.Empty
                    } else _uiState.value = UiState.Success(artists)
                }
        }
    }
}