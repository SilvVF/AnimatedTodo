package com.silvvf.dontbreak.ui


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.silvvf.dontbreak.R
import kotlinx.coroutines.launch


@Composable
fun AnimatedCheckBox(
    modifier: Modifier = Modifier,
    canvasModifier: Modifier = Modifier,
    shouldRegress: Boolean = false,
    isDarkTheme: Boolean = false,
    darkColor: Color = Color.Blue,
    lightColor: Color = Color.Blue,
    backColor: Color = Color.Gray,
    onClick: (Boolean) -> Unit
    ) {
    val coroutine = rememberCoroutineScope()
    var shouldDraw by remember { mutableStateOf(false) }
    val vector = ImageVector.vectorResource(id = R.drawable.ic_baseline_check_24)
    val painter = rememberVectorPainter(image = vector)
    var sizeStart by remember { mutableStateOf(Size(0f, 0f))}
    var sizeAnim by remember { mutableStateOf(Size(0f, 0f))}
    var sizeAnim2 by remember { mutableStateOf(Size(0f, 0f))}
    val firstBoxTopLeft = animateSizeAsState(
        sizeAnim,
        animationSpec = tween(1200),
        finishedListener = {
            shouldDraw = true
        }
    )
    val secondBoxAnimLength = remember {
        Animatable(
            initialValue = 0f
        )
    }
    fun animateSecondLength(target: Float) = coroutine.launch {
        secondBoxAnimLength.animateTo(target)
    }
    val secondBoxAnim = animateSizeAsState(
        sizeAnim2,
        animationSpec = tween(1300, delayMillis = 1000),
        finishedListener = { }
    )
    val firstBoxRegress = animateSizeAsState(targetValue = Size(0f, 0f))
    val secondBoxRegress = animateSizeAsState(targetValue = Size(0f, 0f))
    Box(modifier = modifier) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(5.dp))
                .background(backColor)
                .clickable {
                    onClick(shouldRegress)
                }
        )
        Canvas(
            modifier = canvasModifier,
        ) {
            sizeStart = size
            sizeAnim = Size(size.width * 0.2f, size.height * 0.6f)
            sizeAnim2 = Size(size.width * 0.2f, height = size.height * 1.1f)
            rotate(-45f) {
                drawRoundRect(
                    color = darkColor,
                    topLeft = Offset(x = size.width * 0.1f, y = size.height * 0.1f),
                    cornerRadius = CornerRadius(35f, 35f),
                    size = if (shouldRegress) firstBoxRegress.value
                    else firstBoxTopLeft.value
                )
            }
            if (shouldRegress) {
                animateSecondLength(0f)
            } else {
                animateSecondLength(sizeAnim2.height)
            }
            if (shouldDraw) {
                rotate(180f + 45f) {
                    drawRoundRect(
                        color = darkColor,
                        topLeft = Offset(x = size.width * 0.3f, y = -size.height * -0.1f),
                        cornerRadius = CornerRadius(35f, 35f),
                        size = Size(sizeAnim2.width, secondBoxAnimLength.value)
                    )
                }
            }
        }
    }
}
