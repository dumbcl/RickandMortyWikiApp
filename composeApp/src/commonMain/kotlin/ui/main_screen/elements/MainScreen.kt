package ui.main_screen.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import getPlatform
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.Palette
import java.nio.file.attribute.UserPrincipalLookupService

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainScreen() {
    //Android or Java
    val platformName = getPlatform().name.split(" ")[0]
    val isAndroid = platformName == "Android"
    val horizontalPadding = if (isAndroid) 25.dp else 100.dp
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Palette.BackgroundColor),
        contentAlignment = Alignment.TopCenter
    ){
        Column (
            modifier = Modifier
                .padding(horizontalPadding, 15.dp)
                .verticalScroll(rememberScrollState()) ,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (isAndroid) {
                IconButton(
                    onClick = { /*TODO*/ },
                ) {
                    Icon(
                        Icons.Default.List,
                        "choose items type",
                        tint = Palette.DetailsTextColor,
                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp),
                    )
                }
            } else {
                Text(
                    text = "Characters",
                    color = Palette.GeneralTextColor,
                    style = TextStyle(
                        color = Palette.GeneralTextColor,
                        fontSize = 26.sp,
                        fontWeight = FontWeight(700),
                    ),
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painterResource("image_rick_and_morty_logo.xml"),
                null
            )
            Spacer(modifier = Modifier.height(45.dp))
            var title = "Characters"
            var queryHint = "Find a character"
            var query by remember { mutableStateOf("") }
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = query,
                onValueChange = { newQuery ->
                    query = newQuery
                },
                placeholder = {
                    Text(
                        queryHint,
                        color = Palette.GeneralTextColor
                    )
                },
                textStyle = TextStyle(
                    color = Palette.GeneralTextColor,
                    fontSize = 22.sp,
                    fontWeight = FontWeight(400),
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Palette.GeneralTextColor,
                    backgroundColor = Palette.SecondaryCardsColor,
                    trailingIconColor = Palette.GeneralTextColor
                ),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )
            Spacer(modifier = Modifier.height(35.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    color = Palette.GeneralTextColor,
                    style = TextStyle(
                        color = Palette.GeneralTextColor,
                        fontSize = 26.sp,
                        fontWeight = FontWeight(700),
                    ),
                )
                Spacer(modifier = Modifier.width(25.dp))
                Divider(color = Palette.DetailsTextColor, thickness = 3.dp, modifier = Modifier.fillMaxWidth())
            }
            UIEpisodeItem("Pilot", "12 december 2003", "S01E01")
            Spacer(modifier = Modifier.height(50.dp))
            TextButton(
                onClick = {},
                modifier = Modifier
                    .width(218.dp)
                    .height(81.dp)
                    .background(color = Palette.PrimaryCardsColor, shape = RoundedCornerShape(size = 15.dp))
                    .border(width = 3.dp, color = Palette.DetailsTextColor, shape = RoundedCornerShape(size = 15.dp))
                    .shadow(elevation = 4.dp, spotColor = Color(0x40000000), ambientColor = Color(0x40000000)),
            ) {
                Text(
                    text = "Load more...",
                    color = Palette.DetailsTextColor,
                    style = TextStyle(
                        color = Palette.DetailsTextColor,
                        fontSize = 26.sp,
                        fontWeight = FontWeight(700),
                    ),
                )
            }
        }
    }

}