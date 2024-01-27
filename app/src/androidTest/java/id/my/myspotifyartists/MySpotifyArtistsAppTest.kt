package id.my.myspotifyartists

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import id.my.myspotifyartists.model.ArtistsData
import id.my.myspotifyartists.ui.navigation.Screen
import id.my.myspotifyartists.ui.theme.MySpotifyArtistsTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MySpotifyArtistsAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MySpotifyArtistsTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                MySpotifyArtistsApp(navController = navController)
            }
        }
    }


    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("ArtistList").performScrollToIndex(5)
        composeTestRule.onNodeWithText(ArtistsData.artists[5].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailArtist.route)
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesBack() {
        composeTestRule.onNodeWithTag("ArtistList").performScrollToIndex(9)
        composeTestRule.onNodeWithText(ArtistsData.artists[9].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailArtist.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back))
            .performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun isFavorite_updated_successfully() {
        // isFavorite menjadi true
        composeTestRule.onNodeWithTag("ArtistList").performScrollToIndex(8)
        composeTestRule.onNodeWithText(ArtistsData.artists[8].name).performClick()

        navController.assertCurrentRouteName(Screen.DetailArtist.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.add_to_favorite))
            .performClick()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back))
            .performClick()

        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()

        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithText(ArtistsData.artists[8].name, useUnmergedTree = true)
            .assertIsDisplayed()

        // isFavorite menjadi false
        composeTestRule.onNodeWithText(ArtistsData.artists[8].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailArtist.route)

        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.add_to_favorite))
            .performClick()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back))
            .performClick()

        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithText(ArtistsData.artists[8].name, useUnmergedTree = true)
            .assertDoesNotExist()
    }

    @Test
    fun searchBar_input_available() {
        composeTestRule.onNodeWithStringId(R.string.search_artist).performClick()
            .performTextInput("livia")

        val matchingArtists = ArtistsData.artists.filter { it.name.contains("livia") }

        matchingArtists.forEach {
            composeTestRule.onNodeWithText(it.name, useUnmergedTree = true).assertIsDisplayed()
        }
    }
}