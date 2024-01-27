package id.my.myspotifyartists.ui.pages.detailartist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import id.my.myspotifyartists.R
import id.my.myspotifyartists.di.Injection
import id.my.myspotifyartists.model.Artist
import id.my.myspotifyartists.ui.ViewModelFactory
import id.my.myspotifyartists.ui.common.UiState
import id.my.myspotifyartists.ui.components.detail.LatestReleaseSurface
import id.my.myspotifyartists.ui.theme.MySpotifyArtistsTheme

@Composable
fun DetailArtistScreen(
    id: Long,
    viewModel: DetailArtistViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getArtistById(id)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(artist = data, onBackClick = navigateBack, onAddToFavorite = { id, isFavorite ->
                    viewModel.addToFavorite(id, isFavorite)
                })
            }

            is UiState.Error -> {}
            else -> {}
        }
    }
}

@Composable
fun DetailContent(
    artist: Artist,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onAddToFavorite: (id: Long, isFavorite: Boolean) -> Unit
) {
    val scrollState = rememberScrollState()
    var isFavorite by remember { mutableStateOf(artist.isFavorite) }

    LaunchedEffect(isFavorite) {
        onAddToFavorite(artist.id, isFavorite)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(horizontal = 25.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable { onBackClick() }
            )
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = stringResource(R.string.add_to_favorite),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable {
                        isFavorite = !isFavorite
                    },
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        )
        AsyncImage(
            model = artist.photoUrl,
            contentDescription = artist.name + " image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Text(
            text = artist.name,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(7.dp)
        )
        Text(
            text = stringResource(R.string.monthly_listeners, artist.monthlyListener),
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )
        LatestReleaseSurface(artist = artist)
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(20.dp)
        )
        Text(text = artist.description, fontSize = 14.sp, textAlign = TextAlign.Justify)
    }
}


@Preview(showBackground = true)
@Composable
fun DetailArtistPreview() {
    MySpotifyArtistsTheme {
        DetailArtistScreen(id = 1) {}
    }
}
