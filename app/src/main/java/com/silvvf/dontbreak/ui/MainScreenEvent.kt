package com.silvvf.dontbreak.ui

sealed class MainScreenEvent{
    object OnDrawerClicked: MainScreenEvent()
    object OnAboutClick: MainScreenEvent()
}
