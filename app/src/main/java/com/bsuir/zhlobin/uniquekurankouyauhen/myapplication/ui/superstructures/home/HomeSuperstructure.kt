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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.R
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.home.viewModels.MemoriesListViewModel
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.home.HomeScreenContent
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.navigation.MemoriesNavigationActions
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSuperstructure(
    innerPadding: PaddingValues,
    controller: NavHostController,
    addMemory: () -> Unit,
    editMemory: (UUID) -> Unit,
    snackbarHostState: SnackbarHostState = remember {   SnackbarHostState() },
    viewModel: MemoriesListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        snackbarHost =  { SnackbarHost(snackbarHostState) },
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
                        onClick = addMemory,
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.Add, stringResource(R.string.add_memory))
                    }
                }
            )
        },
        content = {it-> HomeScreenContent(it, innerPadding, controller, addMemory, editMemory, uiState) }
    )
}
/*
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    val padding = PaddingValues(0.dp);
    val controller = rememberNavController()
    val navActions: MemoriesNavigationActions = remember(controller) {
        MemoriesNavigationActions(controller)
    }
    HomeSuperstructure(innerPadding = padding, controller = controller,
        addMemory = {
            navActions.navigateToAddEditMemory(
                R.string.add_memory, null) },
        editMemory = {
                memoryId -> navActions.navigateToAddEditMemory(R.string.edit_memory,
            memoryId) })
}

*/