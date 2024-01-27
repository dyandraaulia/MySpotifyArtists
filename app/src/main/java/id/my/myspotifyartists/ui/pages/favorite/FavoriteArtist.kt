package id.my.myspotifyartists.ui.pages.favorite

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.my.myspotifyartists.R
import id.my.myspotifyartists.di.Injection
import id.my.myspotifyartists.model.Artist
import id.my.myspotifyartists.ui.ViewModelFactory
import id.my.myspotifyartists.ui.common.UiState
import id.my.myspotifyartists.ui.components.ArtistListItem

@Composable
fun FavoriteArtist(
    modifier: Modifier = Modifier,
    viewModel: FavoriteArtistsViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteArtists()
            }

            is UiState.Success -> {
                FavoriteArtistsContent(
                    modifier = modifier,
                    artists = uiState.data,
                    navigateToDetail = navigateToDetail
                )
            }

            is UiState.Empty -> {
                EmptyContent()
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun EmptyContent() {
    Column {
        Text(
            text = "Favorite Artists",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(20.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.empty_favorite),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(20.dp)
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteArtistsContent(
    modifier: Modifier = Modifier,
    artists: Map<Char, List<Artist>>,
    navigateToDetail: (Long) -> Unit,
) {
    LazyColumn {
        item {
            Text(
                text = "Favorite Artists",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(20.dp)
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