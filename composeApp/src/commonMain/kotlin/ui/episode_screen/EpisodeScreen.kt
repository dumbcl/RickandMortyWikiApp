package ui.episode_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.Episode
import data.util.httpsStringToId
import getPlatform
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ErrorStub
import ui.LoadingStub
import ui.NetworkStub
import ui.Palette
import ui.TwoPartedText
import ui.character_screen.CharacterScreen
import ui.getScreenModel
import ui.main_screen.elements.ContentType
import ui.main_screen.elements.MainScreen

const val KEY_EPISODE_SCREEN = "episodeScreenKey"

class EpisodeScreen(val id: Int) : Screen {
    override val key: ScreenKey
        get() = KEY_EPISODE_SCREEN

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val episodeScreenModel = getScreenModel<EpisodeScreenModel>()
        episodeScreenModel.id = id
        val episodeScreenState = episodeScreenModel.state.collectAsState()

        LaunchedEffect(currentCompositeKeyHash) {
            episodeScreenModel.fetchData()
        }

        when (val state = episodeScreenState.value) {
            is EpisodeScreenState.Init -> EpisodeScreenContent(null, false, true, navigator)
            is EpisodeScreenState.Loading -> EpisodeScreenContent(null, false, true, navigator)
            is EpisodeScreenState.Error -> EpisodeScreenContent(null, true, false, navigator)
            is EpisodeScreenState.EpisodeContent -> EpisodeScreenContent(
                state.episode,
                false,
                false,
                navigator
            )
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun EpisodeScreenContent(
        episode: Episode?,
        isNetworkError: Boolean,
        isLoading: Boolean,
        navigator: Navigator
    ) {
        val platformName = getPlatform().name.split(" ")[0]
        val isAndroid = platformName == "Android"
        val horizontalPadding = if (isAndroid) 25.dp else 50.dp
        val textSize = if (isAndroid) 20.sp else 30.sp
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Palette.BackgroundColor),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontalPadding, 15.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navigator.push(MainScreen(ContentType.EPISODES)) },
                    ) {
                        Icon(
                            Icons.Default.KeyboardArrowLeft,
                            "go back",
                            tint = Palette.GeneralTextColor,
                            modifier = Modifier
                                .width(48.dp)
                                .height(48.dp),
                        )
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = "Episodes",
                        color = Palette.GeneralTextColor,
                        style = TextStyle(
                            color = Palette.DetailsTextColor,
                            fontSize = textSize.times(1.3),
                            fontWeight = FontWeight(700),
                        ),
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Image(
                    painterResource("image_rick_and_morty_logo.xml"),
                    null
                )
                if (isLoading) {
                    LoadingStub()
                } else if (isNetworkError) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ErrorStub()
                    }
                } else {
                    Spacer(modifier = Modifier.height(25.dp))
                    TwoPartedText(
                        "Name: ",
                        episode!!.name,
                        textSize.unaryMinus().unaryMinus(),
                        textSize
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    TwoPartedText(
                        "Air date: ",
                        episode.airDate,
                        textSize.unaryMinus().unaryMinus(),
                        textSize
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    TwoPartedText(
                        "Code: ",
                        episode.code,
                        textSize.unaryMinus().unaryMinus(),
                        textSize
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Characters:",
                        color = Palette.GeneralTextColor,
                        style = TextStyle(
                            color = Palette.GeneralTextColor,
                            fontSize = textSize,
                            fontWeight = FontWeight(700),
                        ),
                    )
                    for (character in episode.characters) {
                        Spacer(modifier = Modifier.height(10.dp))
                        val characterElement = character.split("_")
                        val characterName = characterElement[0]
                        val characterId = characterElement[1]
                        Text(
                            text = characterName,
                            color = Palette.DetailsTextColor,
                            style = TextStyle(
                                color = Palette.DetailsTextColor,
                                fontSize = textSize,
                                fontWeight = FontWeight(500),
                            ),
                            modifier = Modifier.clickable { navigator.push(CharacterScreen(characterId.toInt())) }
                        )
                    }
                }
            }
        }
    }

}