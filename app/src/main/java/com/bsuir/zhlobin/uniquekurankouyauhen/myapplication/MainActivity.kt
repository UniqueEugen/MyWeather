package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.superstructures.Screen
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.theme.MyApplicationTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(this);
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(context: Context) {
    MyApplicationTheme {
        Surface {

            val navController = rememberNavController();
            MyApplicationTheme {
                Screen(navController, context)
            }
        }

    }
}/*
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    App()
}
@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    val padding = PaddingValues(0.dp);
    val navController = rememberNavController();
    MyApplicationTheme {
        AboutApp(padding);
    }
}*/
/*
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    val padding = PaddingValues(0.dp);
    HomeScreen(innerPadding = padding, controller = rememberNavController())
}*/