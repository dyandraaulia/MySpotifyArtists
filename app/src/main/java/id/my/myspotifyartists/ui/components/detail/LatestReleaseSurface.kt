package id.my.myspotifyartists.ui.components.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import id.my.myspotifyartists.model.Artist

@Composable
fun LatestReleaseSurface(
    artist: Artist,
    modifier: Modifier = Modifier
){
    Surface(
        shape = RoundedCornerShape(15.dp),
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .height(122.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(horizontal = 10.dp)
        ) {
            AsyncImage(
                model = artist.latestReleasePhotoUrl,
                contentDescription = artist.latestAlbum + " album image",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(
                modifier = modifier
                    .fillMaxHeight()
                    .width(20.dp)
            )
            Column {
                Text(text = "Latest Album", fontWeight = FontWeight.Light, fontSize = 13.sp, color = Color.Black)
                Text(
                    text = artist.latestAlbum,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = Color.Black
                )
            }
        }
    }
}