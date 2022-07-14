package com.silvvf.dontbreak

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.silvvf.dontbreak.ui.*
import com.silvvf.dontbreak.ui.theme.DontBreakTheme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DontBreakTheme {
                val coroutineScope = rememberCoroutineScope()
                val configuration = LocalConfiguration.current
                val screenWidth = configuration.screenWidthDp.dp.value
                var checked by remember { mutableStateOf(false) }
                var opened by remember { mutableStateOf(false) }
                Scaffold(

                ) { it ->
                    it.toString()
                    Column {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                        .background(
                            MaterialTheme.colorScheme.primary
                        )
                        .padding(4.dp)
                    ){
                        IconButton(onClick = { opened = !opened }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                        }
                    }
                    Box {
                        SilvDrawer(
                            onSwipe = {
                                opened = it
                            },
                            isDrawerOpened = opened,
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
                                            resId = R.drawable.profilepic
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
                                        val _isChecked = remember { Channel<Boolean>() }
                                        val isChecked = _isChecked.receiveAsFlow().collectAsState(initial = false)
                                        AnimatedCheckBox(
                                            modifier = Modifier.size(height = 20.dp, width = 20.dp),
                                            canvasModifier = Modifier.fillMaxSize(),
                                            isDarkTheme = true,
                                            lightColor = MaterialTheme.colorScheme.primary,
                                            darkColor = MaterialTheme.colorScheme.primary,
                                            backColor = MaterialTheme.colorScheme.surface,
                                            boxOutline = MaterialTheme.colorScheme.outline,
                                            isChecked = isChecked.value
                                        ) {
                                           coroutineScope.launch {
                                               _isChecked.send(!it)
                                               Log.d("isChecked", isChecked.toString())
                                           }
                                        }
                                        var isDarkTheme by remember { mutableStateOf(false) }
                                        AnimatedText(text = "example", fontSize = 24, isChecked = isChecked.value)
                                        SilvSwitch(
                                            Modifier.align(Alignment.Bottom),
                                            isDarkTheme = isDarkTheme
                                        ){ prevDarkValue ->
                                            isDarkTheme = !prevDarkValue
                                        }
                                    }
                                }
                            },
                            screen = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(MaterialTheme.colorScheme.primary)
                                ) {
                                    Column(Modifier.fillMaxSize()) {

                                    }
                                }
                            },
                            expansionSize = (screenWidth * 0.65).dp
                        )
                    }
                }
                }
            }
        }
    }
}

