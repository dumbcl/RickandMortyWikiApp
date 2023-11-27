package ui.location_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import getPlatform
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.Palette
import ui.TwoPartedText
import ui.episode_screen.EpisodeActions
import ui.episode_screen.EpisodeEvents
import ui.episode_screen.EpisodeState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LocationScreen(
    uiState: LocationState,
    uiEvent: Flow<LocationEvents>,
    uiAction: (LocationActions) -> Unit,
) {
    val platformName = getPlatform().name.split(" ")[0]
    val isAndroid = platformName == "Android"
    val horizontalPadding = if (isAndroid) 25.dp else 50.dp
    val textSize = if (isAndroid) 20.sp else 30.sp
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Palette.BackgroundColor),
        //contentAlignment = Alignment.TopCenter
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
                    onClick = { /*TODO*/ },
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
                    text = "Locations",
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
            TwoPartedText("Name: ", uiState.name, textSize.unaryMinus().unaryMinus(), textSize)
            Spacer(modifier = Modifier.height(10.dp))
            TwoPartedText(
                "Type: ",
                uiState.type,
                textSize.unaryMinus().unaryMinus(),
                textSize
            )
            Spacer(modifier = Modifier.height(10.dp))
            TwoPartedText(
                "Dimension: ",
                uiState.dimension,
                textSize.unaryMinus().unaryMinus(),
                textSize
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Residents: ",
                color = Palette.GeneralTextColor,
                style = TextStyle(
                    color = Palette.GeneralTextColor,
                    fontSize = textSize,
                    fontWeight = FontWeight(700),
                ),
            )
            for (character in uiState.residents) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = character.name,
                    color = Palette.DetailsTextColor,
                    style = TextStyle(
                        color = Palette.DetailsTextColor,
                        fontSize = textSize,
                        fontWeight = FontWeight(500),
                    ),
                    modifier = Modifier.clickable { }
                )
            }
        }
    }
}