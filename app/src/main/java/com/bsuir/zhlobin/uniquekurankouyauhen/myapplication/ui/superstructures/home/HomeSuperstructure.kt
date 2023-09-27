package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.superstructures.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.R
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.HomeScreenContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomHomeSuperstructure(innerPadding: PaddingValues, controller: NavHostController){
    Scaffold(
        modifier = Modifier.padding(innerPadding),
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
        content = { HomeScreenContent(it, controller) }
    )
}