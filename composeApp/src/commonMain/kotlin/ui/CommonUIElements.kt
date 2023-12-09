package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TwoPartedText(
    firstPart: String,
    secondPart: String,
    firstSize: TextUnit,
    secondSize: TextUnit,
    firstColor: Color = Palette.GeneralTextColor,
    secondColor: Color = Palette.GeneralTextColor,
    isClickable: Boolean = false
) {
    Row {
        Text(
            text = firstPart,
            color = firstColor,
            style = TextStyle(
                color = firstColor,
                fontSize = firstSize,
                fontWeight = FontWeight(700),
            ),
        )
        if (isClickable)
            Text(
                text = secondPart,
                color = secondColor,
                style = TextStyle(
                    color = secondColor,
                    fontSize = secondSize,
                    fontWeight = FontWeight(500),
                ),
            )
        else
            Text(
                text = secondPart,
                color = secondColor,
                style = TextStyle(
                    color = secondColor,
                    fontSize = secondSize,
                    fontWeight = FontWeight(500),
                ),
                modifier = Modifier.clickable { },
            )
    }
}

@Composable
fun NetworkStub() {
    Spacer(modifier = Modifier.height(40.dp))
    Text(
        text = "Internet problems\nClick to refresh",
        style = TextStyle(
            fontSize = 32.sp,
            fontWeight = FontWeight(700),
            color = Palette.GeneralTextColor,
            textAlign = TextAlign.Center,
        )
    )
    Spacer(modifier = Modifier.height(10.dp))
    IconButton(
        onClick = { /*TODO*/ },
    ) {
        Icon(
            Icons.Default.Refresh,
            "refresh",
            tint = Palette.DetailsTextColor,
            modifier = Modifier
                .width(90.dp)
                .height(90.dp),
        )
    }
}

@Composable
fun ErrorStub() {
    Spacer(modifier = Modifier.height(40.dp))
    Text(
        text = "Something went wrong\nSorry...",
        style = TextStyle(
            fontSize = 32.sp,
            fontWeight = FontWeight(700),
            color = Palette.GeneralTextColor,
            textAlign = TextAlign.Center,
        )
    )
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun LoadingStub(){
    CircularProgressIndicator(
        color = Palette.GeneralTextColor
    )
}