package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.superstructures

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.getValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.R
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.navigation.NavBar
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(navController: NavHostController, context: Context, viewModel: superstructureViewModel = hiltViewModel()) {
    viewModel.setLocation(context)
    Thread.sleep(500L)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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
                    )
                },
                content = {
                        innerPadding -> NavBar(navController, innerPadding, context, uiState.latLon)

                }
            )
        }
    )
}



@SuppressLint("MissingPermission")
suspend fun getLocation(context: Context) = suspendCoroutine {
    LocationServices.getFusedLocationProviderClient(context).lastLocation.addOnSuccessListener { res->
        try {
            it.resume("${res.latitude},${res.longitude}")
        }catch (e: NullPointerException){
            Log.d("MyLog", e.toString())
            it.resume("0.0,0.0")
        }

    }.addOnFailureListener { res-> it.resume("0.0,0.0")  }
}

data class location(
    var latLon: String=""
)
@HiltViewModel
class superstructureViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(location())
    val uiState: StateFlow<location> = _uiState.asStateFlow()
    fun setLocation(context: Context) {
        Log.d("MyLog", "Dfsfdfjsodfhisdf")
        viewModelScope.async {
            _uiState.update { it.copy(latLon = getLocation(context)) }
            Log.d("MyLog", "Df ${_uiState.value.latLon}")
        }
    }
}