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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.Memory
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.superstructures.home.HomeSuperstructure
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import coil.compose.AsyncImage
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.home.viewModels.MemoriesListUiState
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.home.viewModels.MemoriesListViewModel
import java.util.UUID

const val DEFAULT_IMAGE = "//cdn.weatherapi.com/weather/64x64/night/323.png"
@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    controller: NavHostController,
    addMemory: () -> Unit,
    editMemory: (UUID) -> Unit) {
    HomeSuperstructure(innerPadding, controller, addMemory,editMemory);
}
@Composable
fun HomeScreenContent(
    it: PaddingValues,
    innerPadding: PaddingValues,
    controller: NavHostController,
    addMemory: () -> Unit,
    editMemory: (UUID) -> Unit,
    uiState: MemoriesListUiState
) {
    HomeContentList(controller = controller, it, innerPadding, uiState, addMemory, editMemory)
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
    innerPadding: PaddingValues,
    uiState: MemoriesListUiState,
    addMemory: () -> Unit,
    editMemory: (UUID) -> Unit,
    viewModel: MemoriesListViewModel = hiltViewModel()
){
//    val dataList by viewModel.dataFlow.collectAsState(emptyList())
    Column(modifier = Modifier.background(
        Brush.linearGradient(listOf(Color.Black, Color.Green)),
        shape = RectangleShape
    )) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)){
           /* Button(
                onClick = viewModel::refreshMemoriesList
            ) {
                Text("Refresh")
            }*/
            Button(onClick = viewModel::addSampleMemories) {
                Text("Add some default memories")
            }
        }


        if (uiState.memories.isEmpty()) {
            NoMemoiesInfo(it, viewModel)
        }
        else {
            HomeCommonContent(
                editMemory = editMemory,
                deleteMemory = viewModel::deleteMemory,
                dataList = uiState.memories,
                it = it,
                viewModel = viewModel
            )
        }
    }

}


@Composable
private fun MemoryItem(
    item: Memory,
    onEditMemory: (UUID) -> Unit,
    onRemove: (UUID) -> Unit,
   // onAdd: () -> Unit,
){
    Box(
        modifier= Modifier
            .fillMaxSize(0.9f)
            .padding(top = 20.dp, start = 30.dp)
            .clickable { item.id?.let { onEditMemory(it) } }
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFBB86FC))
            .border(
                1.dp, shape = RoundedCornerShape(10.dp),
                brush = Brush.linearGradient(
                    colors = listOf
                        (
                        Color.Yellow,
                        Color.White,
                        Color.Cyan
                    )
                ),
            )



    ){
        Row(
            modifier = Modifier.padding(5.dp)
        ) {
            AsyncImage(
                model = "https:${item.image.ifEmpty{DEFAULT_IMAGE}}",
                contentDescription = "wetherImg",
                modifier = Modifier
                    .size(55.dp)
                    .padding(top = 3.dp, end = 8.dp)
            )
            
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End

                ) {
                    Text(
                        text = item.memory,
                        modifier = Modifier,
                        fontStyle = FontStyle.Italic,
                        fontSize = 24.sp,
                        color = Color.White,

                        )
                }
                Row(
                    modifier = Modifier,
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
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(32.dp)
                    .height(32.dp),
                onClick = { item.id?.let { onRemove(it) } }
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }

    }
}
@Composable
private fun HomeCommonContent(
    dataList: List<Memory>,
    it: PaddingValues,
    viewModel: MemoriesListViewModel,
    editMemory: (UUID) -> Unit,
    deleteMemory: (UUID) -> Unit
) {
    LazyColumn(

        modifier = Modifier
            .padding(it)
            .fillMaxSize()

    ){
        items(dataList) { item ->
            MemoryItem(
                item = item,
                onEditMemory = editMemory,
                onRemove = deleteMemory
            )
        }
    }
}
@Composable
fun NoMemoiesInfo(it:PaddingValues, viewModel: MemoriesListViewModel) {
    Column(modifier = Modifier
        .padding(it)
        .fillMaxSize()
    ) {
        Box(
            modifier=Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(stringResource(R.string.no_memories_label), color = Color.Gray, fontSize = 16.sp)
        }
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

