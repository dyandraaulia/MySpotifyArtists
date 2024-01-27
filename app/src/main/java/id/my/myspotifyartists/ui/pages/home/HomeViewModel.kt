package id.my.myspotifyartists.ui.pages.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.my.myspotifyartists.ui.common.UiState
import id.my.myspotifyartists.data.Repository
import id.my.myspotifyartists.model.Artist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Map<Char, List<Artist>>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Map<Char, List<Artist>>>>
        get() = _uiState
    private val _query = mutableStateOf("")
    val query: State<String> get() = _query


    fun getAllArtists() {
        viewModelScope.launch {
            repository.getAllOrderedArtist()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { artists ->
                    _uiState.value = UiState.Success(artists)
                }

        }
    }

    fun searchArtists(query: String) {
        _query.value = query
        viewModelScope.launch {
            repository.search(_query.value)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { artists ->
                    _uiState.value = UiState.Success(artists)
                }
        }
    }
}