package ui.main_screen.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.Palette

@OptIn(ExperimentalResourceApi::class)
@Composable
fun UICharacterItem(name: String, species: String, image: String = "", onClick: () -> Unit){
    Box(
        modifier = Modifier
        .padding(10.dp)
        .width(290.dp)
        .height(280.dp)
        .background(color = Palette.PrimaryCardsColor, shape = RoundedCornerShape(size = 15.dp))
        .border(width = 3.dp, color = Palette.DetailsTextColor, shape = RoundedCornerShape(size = 15.dp))
        .shadow(elevation = 4.dp, spotColor = Color(0x40000000), ambientColor = Color(0x40000000))
        .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(11.dp))
            val imageUrl = asyncPainterResource(data = image)
            KamelImage(
                resource = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .clip(CircleShape),
                onLoading = { progress ->
                    CircularProgressIndicator(
                        color = Color.White,
                        progress = progress,
                        modifier = Modifier.size(150.dp),
                    )
                },
                onFailure = {
                    Image(
                        painter = painterResource("default_img.png"),
                        contentDescription = null,
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp),
                    )
                },
            )
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = name,
                color = Palette.DetailsTextColor,
                style = TextStyle(
                    color = Palette.DetailsTextColor,
                    fontSize = 25.sp,
                    fontWeight = FontWeight(700),
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = species,
                color = Palette.GeneralTextColor,
                style = TextStyle(
                    color = Palette.GeneralTextColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(500),
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
    Spacer(modifier = Modifier.height(30.dp))
}