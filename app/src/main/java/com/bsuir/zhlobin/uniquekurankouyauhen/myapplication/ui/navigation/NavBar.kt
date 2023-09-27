package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.AboutApp
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.HomeScreen
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.Screen2
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.Screen3

@Composable
fun NavBar(navController: NavHostController, innerPadding: PaddingValues){
    NavHost(
        navController = navController,
        startDestination = Screen.About.screenName
    ) {
        composable(Screen.Home.screenName) {
            HomeScreen(innerPadding, navController)
        }
        composable(Screen.Weather.screenName) {
            Screen2(innerPadding)
        }
        composable(Screen.Memories.screenName) {
            Screen3(innerPadding)
        }
        composable(Screen.About.screenName) {
            AboutApp(innerPadding)
        }
    }
}
