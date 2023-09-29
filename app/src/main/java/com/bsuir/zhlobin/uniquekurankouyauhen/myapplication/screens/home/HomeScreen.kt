package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.hilt.navigation.compose.hiltViewModel
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.R
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.viewModels.HomeViewModel
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.Memory
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.superstructures.home.HomeSuperstructure
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.viewModels.MemoriesListUiState
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.viewModels.MemoriesListViewModel
import java.util.UUID

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    controller: NavHostController,
    addMemory: () -> Unit,
    editMemory: (String) -> Unit) {
    HomeSuperstructure(innerPadding, controller, addMemory,editMemory);
}
@Composable
fun HomeScreenContent(
    it: PaddingValues,
    controller: NavHostController,
    addMemory: () -> Unit,
    editMemory: (String) -> Unit,
    uiState: MemoriesListUiState
) {
    HomeContentList(controller = controller, it, uiState, addMemory, editMemory)
    if (uiState.isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}


@Composable
fun HomeContentList(
    controller: NavHostController,
    it: PaddingValues,
    uiState: MemoriesListUiState,
    addMemory: () -> Unit,
    editMemory: (String) -> Unit,
    viewModel: MemoriesListViewModel = hiltViewModel()
){
//    val dataList by viewModel.dataFlow.collectAsState(emptyList())
    Column(modifier = Modifier.padding(it)) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(onClick = viewModel::refreshMemoriesList) {
                Text("Refresh")
            }
            Button(onClick = viewModel::addSampleMemories) {
                Text("Add sample planets")
            }
        }

        if (uiState.memories.isEmpty()) {
            NoPlanetsInfo()
        }
        else {
            MemoryItem(
                editMemory = editMemory,
                deleteMemory = viewModel::deleteMemory,
                dataList = uiState.memories,
                it = it
            )
        }
    }

}


@Composable
private fun HomeContent(
    item: Memory,
    //onRemove: (Memory) -> Unit,
   // onAdd: () -> Unit,
){
    Box(
        modifier= Modifier
            .fillMaxSize(0.9f)
            .padding(top = 70.dp, start = 20.dp, end = 20.dp)
            .clickable { }
            .border(
                1.dp, shape = RectangleShape, brush = Brush.linearGradient(
                    colors = listOf
                        (
                        Color.Yellow,
                        Color.White,
                        Color.Cyan
                    )
                )
            )



    ){
        Column {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End

            ) {
                Text(
                    text = item.memory,
                    modifier = Modifier,
                    fontStyle = FontStyle.Italic,
                    fontSize = 34.sp,
                    color = Color.White,

                    )
            }
            Row(
                modifier= Modifier,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = item.date.toString(),
                    modifier = Modifier,
                    fontSize = 9.sp,
                    color = colorResource(R.color.lightYellow)
                )
            }
        }


    }
}
@Composable
private fun MemoryItem(
    dataList: List<Memory>,
    it: PaddingValues,
    editMemory: (String) -> Unit,
    deleteMemory: (UUID) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(it)
            .fillMaxSize()
            .background(
                Brush.linearGradient(listOf(Color.Black, Color.Green)),
                shape = RectangleShape
            )
    ){
        items(dataList) { item ->
            HomeContent(item = item)
        }
    }
}
@Composable
fun NoPlanetsInfo() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(stringResource(R.string.no_memories_label), color = Color.Gray)
    }
}
/*
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    val padding = PaddingValues(0.dp);
    val controller = rememberNavController()
    HomeScreen(innerPadding = padding, controller = controller,)
}*/

