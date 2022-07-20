package com.silvvf.dontbreak

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.silvvf.dontbreak.ui.MainScreenEvent
import com.silvvf.dontbreak.ui.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(

): ViewModel() {


    var state by mutableStateOf(MainScreenState(
            todos = listOf(
                    Todo(
                            title = "test",
                            content = "content",
                            color = Color.LightGray,
                            isChecked = false
                    )
            )
    ))
        private set

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.OnAboutClick -> {

            }
            is MainScreenEvent.OnDrawerClicked -> {
                state = state.copy(
                        isDrawerOpen = !state.isDrawerOpen
                )
            }
        }
    }
}