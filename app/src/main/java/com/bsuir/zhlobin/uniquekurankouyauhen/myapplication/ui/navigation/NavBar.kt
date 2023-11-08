package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.navigation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.R
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.MemoriesRepository
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.source.RemoteWeatherSourceImpl
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.domain.AddMemoryUseCase
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.Screen3
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.abuutApp.AboutApp
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.home.AddEditMemoryScreen
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.home.HomeScreen
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.home.viewModels.AddEditMemoryUiState
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.home.viewModels.AddEditMemoryViewModel
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.weather.weatherScreen
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.navigation.MemoriesDestinationsArgs.MEMORY_ID_ARG
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.navigation.MemoriesDestinationsArgs.TITLE_ARG
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.navigation.MemoriesDestinationsArgs.USER_MESSAGE_ARG
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(
    navController: NavHostController,
    innerPadding: PaddingValues,
    context: Context,
    LatitudeAndLongitude: String,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    startDestination: String = MemoriesDestinations.MEMORIES_ROUTE,
    navActions: MemoriesNavigationActions = remember(navController) {
        MemoriesNavigationActions(navController)
    }
){
    Log.d("MyLog","sdfsdfsdf "+ LatitudeAndLongitude)
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = MemoriesDestinations.MEMORIES_ROUTE,
            arguments = listOf(
                navArgument(USER_MESSAGE_ARG) { type = NavType.IntType; defaultValue = 0 }
            )
        ) {
            HomeScreen(
                innerPadding,
                navController,
                addMemory = {navActions.navigateToAddEditMemory( R.string.add_memory, null) },
                editMemory = { memoryId -> navActions.navigateToAddEditMemory(R.string.edit_memory,memoryId) }
            )
        }
        composable(
            route=MemoriesDestinations.ADD_EDIT_MEMORY_ROUTE,
            arguments = listOf(
                navArgument(TITLE_ARG) { type = NavType.IntType },
                navArgument(MEMORY_ID_ARG) { type = NavType.StringType; nullable = true },
            )
        ) { entry ->
            val memoryId = entry.arguments?.getString(MEMORY_ID_ARG)
            AddEditMemoryScreen(
                innerPadding,
                onMemoryUpdate = {
                    navActions.navigateToMemories(
                        if (memoryId == null) ADD_EDIT_RESULT_OK else EDIT_RESULT_OK
                    )
                },
                context = context,
                latitudeAndLongitude = LatitudeAndLongitude
            )
        }
        composable(Screen.Weather.screenName) {
            weatherScreen(innerPadding, context, LatitudeAndLongitude)
        }
        composable(Screen.Memories.screenName) {
            Screen3(innerPadding)
        }
        composable(Screen.About.screenName) {
            AboutApp(innerPadding)
        }

    }
}
// Keys for navigation
const val ADD_EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 1
const val DELETE_RESULT_OK = Activity.RESULT_FIRST_USER + 2
const val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 3


