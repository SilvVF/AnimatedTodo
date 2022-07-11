package com.silvvf.dontbreak.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SilvSwitch(
    modifier: Modifier = Modifier,
    animColorDuration: Int = 600,
    animOffsetDuration: Int = 600,
    dark: Color = Color(0xff4F4D57),
    light: Color = Color(0xff3F6BA2)
) {
    val animC = tween<Color>(animColorDuration)
    val animO = tween<Offset>(animOffsetDuration)
    val darkColor = animateColorAsState(targetValue = dark, animC)
    val lightColor = animateColorAsState(targetValue = light, animC)
    var center by remember{ mutableStateOf(Offset.Zero) }
    var size by remember{ mutableStateOf(Size.Zero) }
    val offsetDark = animateOffsetAsState(targetValue =  Offset(center.x / 2f , y = size.height / 2), animO)
    val offsetLight = animateOffsetAsState(targetValue =  Offset(center.x + center.x / 2f , y = size.height / 2),animO )
    Spacer(modifier = Modifier.height(8.dp))
    Row (modifier = modifier){
        var isDarkTheme = isSystemInDarkTheme()
        Canvas(
            modifier = Modifier
                .height(30.dp)
                .width(55.dp)
                .clip(RoundedCornerShape(50f))
                .clickable {
                    isDarkTheme = !isDarkTheme
                },
        ){
            size = this.size
            center = this.center
            drawRoundRect(
                color = if (isDarkTheme) darkColor.value else lightColor.value,
                cornerRadius = CornerRadius(x = 30f, y = 30f)
            )
            drawCircle(
                radius = (size.height / 2 - 7),
                color = Color.White,
                center =  if (isDarkTheme) offsetDark.value else offsetLight.value

            )
        }
    }
}