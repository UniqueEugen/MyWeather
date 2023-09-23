package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.AboutApp
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.Screen1
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.Screen2
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.Screen3
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    companion object {

        val aboutMeStrings = listOf("MyWeather", "ABOUT APPLICATION", "MyWeather is an application that will help you to know the weather for the coming days, make a plan based on the weather and keep the memories of the best days on a walk! Share the good weather with your friends and go for a walk!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            App();
           /* MyApplicationTheme {
                Surface {
                    AboutScreen()
                }

            }*/
            /*{
                    Greeting("World")
                }*/

        }
    }

}







@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    val value = PaddingValues(0.dp);
    MyApplicationTheme {
        AboutApp(value);
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(){
    MyApplicationTheme {
        Surface {
            val navController = rememberNavController();
            Screen(navController)
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val navItems = listOf("Screen1", "Screen2", "Screen3","About App")
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                content = {
                    navItems.forEach { screen ->
                        NavigationDrawerItem(
                            label = { Text(text = screen) },
                            selected = false,
                            onClick = { navController.navigate(screen) })
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
                            containerColor = colorResource(R.color.Orange)
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
                        actions = {
                            IconButton(
                                onClick = { /*TODO*/ },
                                modifier = Modifier
                            ) {
                                Icon(Icons.Filled.Search, contentDescription = "Search")
                            }
                        }


                    )
                },
                bottomBar = {
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
                },
                content = {
                    innerPadding -> NavBar(navController, innerPadding)

                }
            )
        }
    )
}

@Composable
fun NavBar(navController: NavHostController, innerPadding: PaddingValues){
    NavHost(
        navController = navController,
        startDestination = "About App"
    ) {
        composable("Screen1") {
            Screen1()
        }
        composable("Screen2") {
            Screen2()
        }
        composable("Screen3") {
            Screen3()
        }
        composable("About App") {
            AboutApp(innerPadding)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    App()
}