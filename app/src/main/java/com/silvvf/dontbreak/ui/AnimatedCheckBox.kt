package com.silvvf.dontbreak.ui


import android.graphics.Typeface
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


@Composable
fun AnimatedCheckBox(
    modifier: Modifier = Modifier,
    canvasModifier: Modifier = Modifier,
    innerBoxModifier: Modifier = Modifier,
    isChecked: Boolean,
    fontSize: Int = 36,
    todoTitle: String = "This is an example text",
    isDarkTheme: Boolean = false,
    darkColor: Color = Color.Blue,
    lightColor: Color = Color.Blue,
    backColor: Color = Color.Gray,
    boxOutline: Color = Color.Black,
    onClick: (Boolean) -> Unit,
    ) {
    val checkColor by remember { mutableStateOf(
        if (isDarkTheme) darkColor else lightColor
    ) }
    val coroutine = rememberCoroutineScope()
    var sizeAnim by remember { mutableStateOf(Size(0f, 0f))}
    var sizeAnim2 by remember { mutableStateOf(Size(0f, 0f))}
    var sizeLine by remember { mutableStateOf(Size(0f, 0f)) }
    val firstBoxAnimLength = remember {
        Animatable(
            initialValue = 0f,
        )
    }
    val secondBoxAnimLength = remember {
        Animatable(
            initialValue = 0f
        )
    }
    fun <T> animateLength(animatable: Animatable<T, AnimationVector1D>, target: T) = coroutine.launch {
        animatable.animateTo(target)
    }
    if (isChecked) {
        animateLength(secondBoxAnimLength, 0f)
        animateLength(firstBoxAnimLength, 0f)
    } else {
        animateLength(firstBoxAnimLength, sizeAnim.height)
        animateLength(secondBoxAnimLength, sizeAnim2.height)
    }
    Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
    ) {
        Box(modifier = modifier) {
            Box(
                    modifier = innerBoxModifier
                            .fillMaxSize()
                            .padding(1.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(backColor)
                            .border(1.dp, boxOutline, RoundedCornerShape(5.dp))
                            .clickable {
                                onClick(isChecked)
                            }
            )
            Canvas(
                    modifier = canvasModifier,
            ) {
                sizeAnim = Size(size.width * 0.2f, size.height * 0.6f)
                sizeAnim2 = Size(size.width * 0.2f, height = size.height * 1.1f)
                rotate(-45f) {
                    drawRoundRect(
                            color = checkColor,
                            topLeft = Offset(x = size.width * 0.1f, y = size.height * 0.1f),
                            cornerRadius = CornerRadius(35f, 35f),
                            size =  Size(sizeAnim.width, firstBoxAnimLength.value)
                    )
                }
                rotate(180f + 45f) {
                    drawRoundRect(
                            color = checkColor,
                            topLeft = Offset(x = size.width * 0.3f, y = -size.height * -0.1f),
                            cornerRadius = CornerRadius(35f, 35f),
                            size = Size(sizeAnim2.width, secondBoxAnimLength.value)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(14.dp))
        Box(modifier = Modifier
                .fillMaxWidth(0.8f),
                contentAlignment = Alignment.CenterStart
        ) {
            val textPaint = Paint().asFrameworkPaint().apply {
                isAntiAlias = true
                textSize = fontSize.sp.value
                color = android.graphics.Color.BLUE
                typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
            }
            Canvas(modifier = Modifier
                    .wrapContentWidth()
                    .height(30.dp)) {

                sizeLine = Size(size.width * 0.2f, size.height * 0.6f)
                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                            "My Jetpack Compose Text",
                            (0f),            // x-coordinates of the origin (top left)
                            (46f) , // y-coordinates of the origin (top left)
                            textPaint
                    )
                }
                drawRoundRect(
                        color = Color.LightGray,
                        topLeft = Offset(x = this.size.width * 0.1f, y = this.size.height * 0.1f),
                        cornerRadius = CornerRadius(35f, 35f),
                        size =  Size(sizeLine.width, 9f)
                )
            }
        }
    }

}
