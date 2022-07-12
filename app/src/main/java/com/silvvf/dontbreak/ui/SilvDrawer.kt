package com.silvvf.dontbreak.ui

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.*
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun MyBasicColumn(
    modifier: Modifier = Modifier,
    content: @Composable() () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // List of measured children
        val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(constraints.copy(minHeight = 0))
        }

        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Track the y co-ord we have placed children up to
            var yPosition = 0

            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.place(x = 0, y = yPosition)

                // Record the y co-ord placed up to
                yPosition += placeable.height
            }
        }
    }
}



@Composable
fun DrawerContainer(
    modifier: Modifier = Modifier,
    isDrawerOpened: Boolean = false,
    drawerWidth: Dp,
    onSwipe: (Boolean) -> Unit = {},
    content: @Composable () -> Unit
) {
    val transition = updateTransition(targetState = isDrawerOpened, label = "")

    val collapseFraction =
        transition.animateFloat(transitionSpec = { tween(durationMillis = 400) },
            label = ""
        ) { state ->
            when (state) {
                false -> 0f
                true -> 1f
            }
        }

    Layout(
        modifier = modifier.pointerInput(Unit) {
            detectHorizontalDragGestures { _, dragAmount ->
                if (!transition.isRunning) {
                    onSwipe(dragAmount > 0 && abs(dragAmount) > 15)
                }
            }
        },
        content = content
    ) { measurables, constraints ->
        val drawerPlaceable = runCatching {
            measurables[0].measure(
                Constraints.fixed(
                    drawerWidth.roundToPx(),
                    constraints.maxHeight
                )
            )
        }.getOrNull()
        val contentPlaceable = runCatching { measurables[1].measure(constraints) }.getOrNull()

        val drawerX = lerp(
            -drawerWidth.roundToPx().toFloat().toDp(),
            0.dp.roundToPx().toFloat().toDp(),
            collapseFraction.value
        ).toPx().roundToInt()

        val contentX = lerp(
            0.dp.roundToPx().toFloat().toDp(),
            drawerWidth.roundToPx().toFloat().toDp(),
            collapseFraction.value
        ).toPx().roundToInt()

        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
            drawerPlaceable?.place(x = drawerX, y = 0)
            contentPlaceable?.place(x = contentX, y = 0)
        }
    }
}

@Composable
fun SilvDrawer(
    drawer: @Composable () -> Unit,
    screen: @Composable () -> Unit,
    expansionSize: Dp = 200.dp,
    isDrawerOpened: Boolean,
    onSwipe: (Boolean) -> Unit
) {
    DrawerContainer(
        isDrawerOpened = isDrawerOpened,
        onSwipe = { toOpen -> onSwipe(toOpen)},
        drawerWidth = expansionSize
    ) {
        drawer()
        screen()
    }
}

