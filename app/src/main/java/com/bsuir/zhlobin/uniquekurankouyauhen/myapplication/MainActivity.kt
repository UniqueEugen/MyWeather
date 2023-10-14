package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.superstructures.Screen
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App();
        }
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