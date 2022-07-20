package com.silvvf.dontbreak

import com.silvvf.dontbreak.ui.Todo

data class MainScreenState(
        val todos: List<Todo> = emptyList(),
        val isDrawerOpen: Boolean = false
)
