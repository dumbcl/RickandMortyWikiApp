package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit

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