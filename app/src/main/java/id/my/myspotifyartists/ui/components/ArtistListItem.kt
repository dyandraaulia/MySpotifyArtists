package id.my.myspotifyartists.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import id.my.myspotifyartists.ui.theme.MySpotifyArtistsTheme

@Composable
fun ArtistListItem(
    name: String,
    photoUrl: String,
    latestAlbum: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = "$name image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 20.dp, bottom = 10.dp, top = 10.dp)
                .size(60.dp)
                .clip(CircleShape)
        )
        Column {
            Text(
                text = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp),
                fontWeight = FontWeight.Medium
            )
            Text(
                text = latestAlbum,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtistListItemPreview() {
    MySpotifyArtistsTheme {
        ArtistListItem(name = "Dyandra Aulia", photoUrl = "", latestAlbum = "Hhahaha")
    }
}

