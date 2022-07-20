package com.silvvf.dontbreak.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.silvvf.dontbreak.MainScreenViewModel

@Composable
fun MainScreen(
        viewModel: MainScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp
        Row(modifier = Modifier
                .fillMaxWidth()
                .height(25.dp)
                .background(
                        MaterialTheme.colorScheme.primary
                )
                .padding(4.dp)
        ){
            IconButton(onClick = { viewModel.onEvent(MainScreenEvent.OnDrawerClicked) }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
            }
        }
        Box {
            SilvDrawer(
                    onSwipe = {
                        viewModel.onEvent(MainScreenEvent.OnDrawerClicked)
                    },
                    isDrawerOpened = state.isDrawerOpen,
                    drawer = {
                        Box(
                                modifier = Modifier
                                        .fillMaxSize()
                                        .background(MaterialTheme.colorScheme.surface)
                        ) {
                            Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Top,
                                    modifier = Modifier.fillMaxSize()
                            ) {
                                ProfilePic(
                                        modifier = Modifier.align(Alignment.Start),
                                        resId = com.silvvf.dontbreak.R.drawable.profilepic
                                )
                                DrawerItem(
                                        modifier = Modifier.fillMaxWidth(0.9f),
                                        fontSize = 18,
                                        imageVector = Icons.Outlined.Inbox
                                ) {

                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                DrawerItem(
                                        modifier = Modifier.fillMaxWidth(0.9f),
                                        fontSize = 18,
                                        itemText = "About",
                                        fontColor = MaterialTheme.colorScheme.primary,
                                        imageVector = Icons.Outlined.Info,
                                        backgroundColor = MaterialTheme.colorScheme.surface
                                ) {
                                }
                            }
                            Row(
                                    Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                            ) {

                            }
                        }
                    },
                    screen = {
                        Box(
                                modifier = Modifier
                                        .fillMaxSize()
                                        .background(MaterialTheme.colorScheme.primary)
                        ) {
                            var isDarkTheme by remember { mutableStateOf(false) }
                            LazyColumn(Modifier.fillMaxSize()) {
                                items(state.todos, key = {
                                    it.title
                                }) {
                                    var isChecked by remember {
                                        mutableStateOf(it.isChecked)
                                    }
                                    AnimatedCheckBox(
                                            modifier = Modifier.size(height = 20.dp, width = 20.dp),
                                            canvasModifier = Modifier.fillMaxSize(),
                                            isDarkTheme = true,
                                            lightColor = MaterialTheme.colorScheme.primary,
                                            darkColor = MaterialTheme.colorScheme.primary,
                                            backColor = MaterialTheme.colorScheme.surface,
                                            boxOutline = MaterialTheme.colorScheme.outline,
                                            isChecked = isChecked,
                                    ) {
                                        isChecked = !it
                                    }
                                }
                                item {
                                    SilvSwitch(
                                            Modifier,
                                            isDarkTheme = isDarkTheme
                                    ) { prevDarkValue ->
                                        isDarkTheme = !prevDarkValue
                                    }
                                }
                            }
                        }
                    },
                    expansionSize = (screenWidth * 0.65).dp
            )
        }
}