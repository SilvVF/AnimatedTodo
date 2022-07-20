package com.silvvf.dontbreak.ui

import androidx.compose.ui.graphics.Color

data class Todo(
        val title: String,
        val content: String,
        val color: Color,
        val isChecked: Boolean,
)
