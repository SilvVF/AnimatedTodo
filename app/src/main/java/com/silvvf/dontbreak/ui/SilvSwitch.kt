package com.silvvf.dontbreak.ui

import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
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
import kotlinx.coroutines.launch

@Composable
fun SilvSwitch(
    modifier: Modifier = Modifier,
    animColorDuration: Int = 600,
    animOffsetDuration: Int = 600,
    dark: Color = Color(0xff4F4D57),
    light: Color = Color(0xff3F6BA2),
    isDarkTheme: Boolean = false,
    onClick: (Boolean) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var center by remember{ mutableStateOf(Offset.Zero) }
    var size by remember{ mutableStateOf(Size.Zero) }
    val offsetDark = Offset(center.x / 2f , y = size.height / 2)
    val offsetLight =  Offset(center.x + center.x / 2f , y = size.height / 2)
    val color = remember {
        Animatable(
            initialValue = if (isDarkTheme) dark else light
        )
    }
    val amountOfOffset = remember {
        androidx.compose.animation.core.Animatable(
            initialValue = if (isDarkTheme) offsetDark.x else offsetLight.x
        )
    }
    LaunchedEffect(key1 = center) {
            amountOfOffset.animateTo(
                if (isDarkTheme) offsetDark.x else offsetLight.x,
                animationSpec = tween(0)
            )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row (modifier = modifier){
        Canvas(
            modifier = Modifier
                .height(30.dp)
                .width(55.dp)
                .clip(RoundedCornerShape(50f))
                .clickable {
                    onClick(isDarkTheme)
                    when (isDarkTheme) {
                        true -> {
                            coroutineScope.launch {
                                launch {
                                    amountOfOffset.animateTo(offsetLight.x)
                                }
                                launch {
                                    color.animateTo(
                                        targetValue = light,
                                    )
                                }
                            }
                        }
                        false -> {
                            coroutineScope.launch {
                                launch {
                                    amountOfOffset.animateTo(offsetDark.x)
                                }
                                launch {
                                    color.animateTo(targetValue = dark)
                                }
                            }
                        }
                    }
                },
        ){
            size = this.size
            center = this.center
            drawRoundRect(
                color = color.value,
                cornerRadius = CornerRadius(x = 30f, y = 30f)
            )
            drawCircle(
                radius = (size.height / 2 - 7),
                color = Color.White,
                center =  Offset(x = amountOfOffset.value, y = size.height / 2)
            )
        }
    }
}