package ui.character_screen

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.Character
import data.util.httpsStringToId
import getPlatform
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ErrorStub
import ui.LoadingStub
import ui.Palette
import ui.TwoPartedText
import ui.episode_screen.EpisodeScreen
import ui.getScreenModel
import ui.location_screen.LocationScreen
import ui.main_screen.elements.ContentType
import ui.main_screen.elements.MainScreen

const val KEY_CHARACTER_SCREEN = "characterScreenKey"

class CharacterScreen(val id: Int) : Screen {

    override val key: ScreenKey
        get() = KEY_CHARACTER_SCREEN

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val characterScreenModel = getScreenModel<CharacterScreenModel>()
        characterScreenModel.id = id
        val characterScreenState = characterScreenModel.state.collectAsState()

        LaunchedEffect(currentCompositeKeyHash) {
            characterScreenModel.fetchData()
        }

        when (val state = characterScreenState.value) {
            is CharacterScreenState.Init -> CharacterScreenContent(null, false, true, navigator)
            is CharacterScreenState.Loading -> CharacterScreenContent(null, false, true, navigator)
            is CharacterScreenState.Error -> CharacterScreenContent(null, true, false, navigator)
            is CharacterScreenState.CharacterContent -> CharacterScreenContent(
                state.character,
                false,
                false,
                navigator
            )
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun CharacterScreenContent(
        character: Character?,
        isNetworkError: Boolean,
        isLoading: Boolean,
        navigator: Navigator,
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
                //horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navigator.push(MainScreen(ContentType.CHARACTERS)) },
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
                        text = "Characters",
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
                Spacer(modifier = Modifier.height(25.dp))
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
                    if (isAndroid) {
                        val imageUrl = character?.image?.let { asyncPainterResource(data = it) }
                        if (imageUrl != null) {
                            KamelImage(
                                resource = imageUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(200.dp),
                                onLoading = { progress ->
                                    CircularProgressIndicator(
                                        color = Color.White,
                                        progress = progress,
                                        modifier = Modifier.size(200.dp),
                                    )
                                },
                                onFailure = {
                                    Image(
                                        painter = painterResource("default_img.png"),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .width(200.dp)
                                            .height(200.dp),
                                    )
                                },
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        TwoPartedText(
                            "Name: ",
                            character!!.name,
                            textSize.unaryMinus().unaryMinus(),
                            textSize
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        TwoPartedText(
                            "Status: ",
                            character.status,
                            textSize.unaryMinus().unaryMinus(),
                            textSize
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        TwoPartedText(
                            "Species: ",
                            character.species,
                            textSize.unaryMinus().unaryMinus(),
                            textSize
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        TwoPartedText(
                            "Gender: ",
                            character.gender ?: "Unknown",
                            textSize.unaryMinus().unaryMinus(),
                            textSize
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        TwoPartedText(
                            "Origin: ",
                            character.origin.name,
                            textSize.unaryMinus().unaryMinus(),
                            textSize,
                            secondColor = Palette.DetailsTextColor,
                            isClickable = true,
                            onClick = { navigator.push(LocationScreen(character.origin.url.httpsStringToId())) }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        TwoPartedText(
                            "Location: ",
                            character.location.name,
                            textSize.unaryMinus().unaryMinus(),
                            textSize,
                            secondColor = Palette.DetailsTextColor,
                            isClickable = true,
                            onClick = { navigator.push(LocationScreen(character.location.url.httpsStringToId())) }
                        )
                    } else {
                        Row {
                            val imageUrl = character?.image?.let { asyncPainterResource(data = it) }
                            if (imageUrl != null) {
                                KamelImage(
                                    resource = imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(200.dp)
                                        .height(200.dp),
                                    onLoading = { progress ->
                                        CircularProgressIndicator(
                                            color = Color.White,
                                            progress = progress,
                                            modifier = Modifier.size(200.dp),
                                        )
                                    },
                                    onFailure = {
                                        Image(
                                            painter = painterResource("default_img.png"),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .width(200.dp)
                                                .height(200.dp),
                                        )
                                    },
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                TwoPartedText(
                                    "Name: ",
                                    character!!.name,
                                    textSize.unaryMinus().unaryMinus(),
                                    textSize
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                TwoPartedText(
                                    "Status: ",
                                    character.status,
                                    textSize.unaryMinus().unaryMinus(),
                                    textSize
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                TwoPartedText(
                                    "Species: ",
                                    character.species,
                                    textSize.unaryMinus().unaryMinus(),
                                    textSize,
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                TwoPartedText(
                                    "Gender: ",
                                    character.gender ?: "Unknown",
                                    textSize.unaryMinus().unaryMinus(),
                                    textSize
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                TwoPartedText(
                                    "Origin: ",
                                    character.origin.name,
                                    textSize.unaryMinus().unaryMinus(),
                                    textSize,
                                    secondColor = if (character.origin.name != "unknown") Palette.DetailsTextColor else Palette.GeneralTextColor,
                                    isClickable = true,
                                    onClick = { navigator.push(LocationScreen(character.origin.url.httpsStringToId())) }
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                TwoPartedText(
                                    "Location: ",
                                    character.location.name,
                                    textSize.unaryMinus().unaryMinus(),
                                    textSize,
                                    secondColor = Palette.DetailsTextColor,
                                    isClickable = true,
                                    onClick = { navigator.push(LocationScreen(character.location.url.httpsStringToId())) }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Episodes:",
                        color = Palette.GeneralTextColor,
                        style = TextStyle(
                            color = Palette.GeneralTextColor,
                            fontSize = textSize,
                            fontWeight = FontWeight(700),
                        ),
                    )
                    for (episode in character!!.episodes) {
                        Spacer(modifier = Modifier.height(10.dp))
                        val episodeElement = episode.split("_")
                        val episodeName = episodeElement[0]
                        val episodeId = episodeElement[1]
                        Text(
                            text = episodeName,
                            color = Palette.DetailsTextColor,
                            style = TextStyle(
                                color = Palette.DetailsTextColor,
                                fontSize = textSize,
                                fontWeight = FontWeight(500),
                            ),
                            modifier = Modifier.clickable { navigator.push(EpisodeScreen(episodeId.toInt())) }
                        )
                    }
                }
            }
        }
    }

}