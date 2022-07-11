package com.silvvf.dontbreak.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ProfilePic(
    modifier: Modifier,
    resId: Int,
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .wrapContentHeight()
            .padding(16.dp)
            .border(
                1.75.dp,
                MaterialTheme.colorScheme.secondary,
                CircleShape
            )
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .height(90.dp)
                .width(90.dp),
            contentScale = ContentScale.Crop
        )
    }
}