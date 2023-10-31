package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.superstructures

import android.content.res.Resources.Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.R
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.navigation.NavBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val navItems = listOf(
        stringResource(id = R.string.home),
        stringResource(id = R.string.weather),
        stringResource(id = R.string.memories),
        stringResource(id = R.string.aboutApp)
    )
    ModalNavigationDrawer(
        drawerState = drawerState,
        //scrimColor = Color.Cyan,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(200.dp),
                drawerContainerColor = colorResource(R.color.dimGrey),
                content = {
                    navItems.forEach { screen ->
                        NavigationDrawerItem(
                            modifier = Modifier.border(width = 1.dp, brush = Brush.linearGradient(colors = listOf(
                                Color.Red, Color.Yellow, Color.Green)), shape = RoundedCornerShape(3.dp)
                            ),
                            colors = NavigationDrawerItemDefaults.colors(
                                colorResource(R.color.teal_200),
                                colorResource(R.color.dimGrey)
                            ),
                            label = { Text(text = screen, color = Color.LightGray) },
                            selected = false,
                            onClick = {
                                navController.navigate(screen);
                                scope.launch { drawerState.close() }
                            })
                    }
                }
            )
        },
        content = {
            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                modifier = Modifier
                    .fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "MyWeather",
                                fontSize = 30.sp,
                                color = colorResource(R.color.dimGrey),
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                fontFamily = FontFamily.Cursive,
                                style = TextStyle(
                                    shadow = Shadow(Color.Cyan, Offset(2.0f, 5.5f), 2f),
                                    textGeometricTransform = TextGeometricTransform(
                                        scaleX = 1.1f,
                                        skewX = 0f
                                    )
                                )
                            )
                        },
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor =  colorResource(R.color.Orange)
                        ),
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu")
                            }

                        },
                        modifier = Modifier,
                       /* actions = {
                            IconButton(
                                onClick = {  },
                                modifier = Modifier
                            ) {
                                Icon(Icons.Filled.Search, contentDescription = "Search")
                            }
                        }*/


                    )
                },
                /*bottomBar = {
                    BottomAppBar(
                        actions = {
                            IconButton(onClick = { /* do something */ }) {
                                Icon(
                                    Icons.Filled.Check,
                                    contentDescription = "Localized description"
                                )
                            }
                            IconButton(onClick = { /* do something */ }) {
                                Icon(
                                    Icons.Filled.Edit,
                                    contentDescription = "Edit",
                                )
                            }
                            IconButton(onClick = { /* do something */ }) {
                                Icon(
                                    Icons.Filled.Favorite,
                                    contentDescription = "Like it",
                                )
                            }
                            IconButton(onClick = { /* do something */ }) {
                                Icon(
                                    Icons.Filled.Info,
                                    contentDescription = "My Weather app",
                                )
                            }
                        },
                        modifier = Modifier.background(colorResource(R.color.dimGrey)),
                        containerColor = colorResource(R.color.Slenna),
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = { /* do something */ },
                                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                            ) {
                                Icon(Icons.Filled.Add, "Localized description")
                            }
                        }
                    )
                },*/
                content = {
                        innerPadding -> NavBar(navController, innerPadding)

                }
            )
        }
    )
}
