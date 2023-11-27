package ui.main_screen.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.Palette

@OptIn(ExperimentalResourceApi::class)
@Composable
fun UIEpisodeItem(name: String, date: String, code: String){
    Box(
        modifier = Modifier
            .padding(3.dp)
            .width(262.dp)
            .height(170.dp)
            .background(color = Palette.PrimaryCardsColor, shape = RoundedCornerShape(size = 15.dp))
            .border(width = 3.dp, color = Palette.DetailsTextColor, shape = RoundedCornerShape(size = 15.dp))
            .shadow(elevation = 4.dp, spotColor = Color(0x40000000), ambientColor = Color(0x40000000)),
        contentAlignment = Alignment.Center
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                color = Palette.DetailsTextColor,
                style = TextStyle(
                    color = Palette.DetailsTextColor,
                    fontSize = 25.sp,
                    fontWeight = FontWeight(700),
                ),
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = date,
                color = Palette.GeneralTextColor,
                style = TextStyle(
                    color = Palette.GeneralTextColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(500),
                ),
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = code,
                color = Palette.GeneralTextColor,
                style = TextStyle(
                    color = Palette.GeneralTextColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700),
                ),
            )
        }
    }
}

