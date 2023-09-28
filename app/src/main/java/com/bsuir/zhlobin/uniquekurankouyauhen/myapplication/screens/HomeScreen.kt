package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens

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
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.R
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.HomeViewModel
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.Memory
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.superstructures.home.BottomHomeSuperstructure
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontStyle

@Composable
fun HomeScreen(innerPadding: PaddingValues, controller: NavHostController){
    BottomHomeSuperstructure(innerPadding, controller);
}
@Composable
fun HomeScreenContent(it: PaddingValues, controller: NavHostController) {

    HomeContentList(controller = controller, it)

}


@Composable
fun HomeContentList(controller: NavHostController,it: PaddingValues, viewModel: HomeViewModel= viewModel()){
    val dataList by viewModel.dataFlow.collectAsState(emptyList())
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
   /* HomeContent(
    items = viewModel.items,
    onRemove = viewModel::onClickRemoveMemories,
    onAdd = { controller.navigate(1) },
    )*/

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
    memory: Memory,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) { }
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    val padding = PaddingValues(0.dp);
    val controller = rememberNavController()
    HomeScreen(innerPadding = padding, controller = controller)
}

