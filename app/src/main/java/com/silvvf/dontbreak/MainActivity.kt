package com.silvvf.dontbreak

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.silvvf.dontbreak.ui.*
import com.silvvf.dontbreak.ui.theme.DontBreakTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DontBreakTheme {
                val configuration = LocalConfiguration.current
                val screenWidth = configuration.screenWidthDp.dp.value
                var checked by remember { mutableStateOf(false) }
                Box {
                    SilvDrawer(
                        drawer = {
                            Box(modifier = Modifier
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
                                    var shouldRegress by remember { mutableStateOf(false) }
                                    AnimatedCheckBox(
                                        modifier = Modifier.size(height = 20.dp, width = 20.dp),
                                        canvasModifier = Modifier.fillMaxSize(),
                                        shouldRegress = shouldRegress
                                    ) {
                                        shouldRegress = !it
                                    }
                                    SilvSwitch(Modifier.align(Alignment.Bottom))
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

