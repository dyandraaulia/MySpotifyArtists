package id.my.myspotifyartists.ui.pages.detailartist

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import id.my.myspotifyartists.model.Artist
import id.my.myspotifyartists.ui.theme.MySpotifyArtistsTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailContentTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeArtistList = Artist(
        1,
        "Olivia Rodrigo",
        "https://i.scdn.co/image/ab67616100005174e03a98785f3658f0b6461ec4",
        "57.403.721",
        "bad idea right? - 2023",
        "Olivia Isabel Rodrigo (born February 20, 2003) is an American singer-songwriter and actress. She gained recognition in the late 2010s with her lead roles on the Disney television programs Bizaardvark and High School Musical: The Musical: The Series. After signing with Geffen and Interscope Records in 2020, Rodrigo released her debut single \"Drivers License\", which broke various records and became one of the best-selling songs of 2021, propelling her to mainstream fame. She followed it up with singles \"Deja Vu\" and \"Good 4 U\", and released her debut solo studio album, Sour (2021), which was met with critical and commercial success, winning various accolades including three Grammy Awards. A Disney+ documentary, Olivia Rodrigo: Driving Home 2 U, chronicling her creative process with Sour, followed in 2022. Rodrigo has achieved three Billboard Hot 100 number-one singles, one Billboard 200 number-one album, and five multi-Platinum certifications by the Recording Industry Association of America (RIAA). In addition to other recognitions, she has won an American Music Award, seven Billboard Music Awards and three MTV Video Music Awards. Time named her the 2021 Entertainer of the Year and Billboard named her Woman of the Year in 2022. ",
        "https://i.scdn.co/image/ab67616d00001e028bfab8d577ad17a7b74d9f5f",
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MySpotifyArtistsTheme {
                DetailContent(artist = fakeArtistList, onBackClick = {  },  onAddToFavorite = { _, _ ->})
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun detailContent_isDisplayed() {
        composeTestRule.onNodeWithText(fakeArtistList.name).assertIsDisplayed()
        composeTestRule.onNodeWithText("${fakeArtistList.monthlyListener} monthly listeners").assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeArtistList.description).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeArtistList.latestAlbum).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(fakeArtistList.name + " image").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(fakeArtistList.latestAlbum + " album image").assertIsDisplayed()
    }
}