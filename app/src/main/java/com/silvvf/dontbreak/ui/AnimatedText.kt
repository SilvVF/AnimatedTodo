package com.silvvf.dontbreak.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun AnimatedText(
    text: String,
    isChecked: Boolean = true,
    textColor: Color = Color.Black,
    modifier: Modifier = Modifier,
    fontSize: Int,
) {
    var size by remember {
        mutableStateOf(Size(0f,0f))
    }
    val coroutineScope = rememberCoroutineScope()
    val offsetLineWidth = remember {
        Animatable(initialValue = Offset.Zero.x)
    }
    LaunchedEffect(key1 = isChecked) {
        when {
            isChecked -> offsetLineWidth.animateTo(size.width)
            !isChecked -> offsetLineWidth.animateTo(0f)
        }
    }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            fontSize = fontSize.sp,
            color = textColor
        )
        Canvas(modifier = Modifier.fillMaxSize()) {
            size = size
            drawRoundRect(
                topLeft = Offset(size.width / 2,size.height / 2),
                color = Color.LightGray,
                size = Size(width = offsetLineWidth.value, height = 3f)
            )
        }
    }
}
