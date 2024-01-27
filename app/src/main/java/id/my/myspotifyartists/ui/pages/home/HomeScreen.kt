package id.my.myspotifyartists.ui.pages.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.my.myspotifyartists.di.Injection
import id.my.myspotifyartists.model.Artist
import id.my.myspotifyartists.ui.ViewModelFactory
import id.my.myspotifyartists.ui.common.UiState
import id.my.myspotifyartists.ui.components.ArtistListItem
import id.my.myspotifyartists.ui.components.home.Header

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllArtists()
            }

            is UiState.Success -> {
                HomeContent(
                    navigateToDetail = navigateToDetail,
                    artists = uiState.data,
                    modifier = modifier,
                    viewModel = viewModel
                )
            }

            is UiState.Empty -> {}

            is UiState.Error -> {}
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    artists: Map<Char, List<Artist>>,
    navigateToDetail: (Long) -> Unit,
) {
    val query by viewModel.query

    Box(modifier = modifier) {
        LazyColumn(
            modifier = modifier
                .testTag("ArtistList")
        ) {
            item {
                Header(
                    query = query,
                    onQueryChange = viewModel::searchArtists,
                    modifier = modifier
                        .background(
                            MaterialTheme.colorScheme.primary
                        )
                )
                Spacer(modifier = modifier.height(10.dp))
            }
            artists.forEach { (_, artists) ->
                items(artists, key = { it.id }) { artist ->
                    ArtistListItem(
                        name = artist.name,
                        photoUrl = artist.photoUrl,
                        latestAlbum = artist.latestAlbum,
                        modifier = modifier
                            .fillMaxWidth()
                            .animateItemPlacement(tween(durationMillis = 100))
                            .clickable { navigateToDetail(artist.id) }
                    )
                    Divider(
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}