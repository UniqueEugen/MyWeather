package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.weather

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.WeatherModel
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.weather.wiewModels.WeatherViewModel
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.theme.BlueLight

@Composable
fun ListItem(item: WeatherModel, currentDay: MutableState<WeatherModel>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp)
            .clickable {
                if (item.hours.isEmpty()) return@clickable
                currentDay.value = item
            },
        backgroundColor = BlueLight,
        elevation = 0.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, top = 5.dp, bottom = 5.dp)
            ) {
                Text(
                    text = item.date,
                    color = Color.White,
                    style = TextStyle(fontSize=20.sp)
                )
                Text(
                    text = item.condition,
                    color = Color.White
                )
            }
            Text(
                text = item.currentTemp.ifEmpty { "${item.maxTemp}°/${item.minTemp}" }+"°",
                color = Color.White,
                style = TextStyle(fontSize=30.sp)
            )
            AsyncImage(
                model = "https:${item.conditionIcon}",
                contentDescription = "wetherImg",
                modifier = Modifier
                    .size(35.dp)
                    .padding(top = 3.dp, end = 8.dp)
            )
        }
    }
}

@Composable
fun MainList(list: List<WeatherModel>, currentDay: MutableState<WeatherModel>){
    LazyColumn(modifier = Modifier
        .fillMaxSize()
    ){
        itemsIndexed(
            list
        ) {
                _, item -> ListItem(item, currentDay)
        }
    }
}


@Composable
fun dialogSearch(viewModel: WeatherViewModel, city:String, onSubmit: ()->Unit) {
    AlertDialog(
        onDismissRequest = {
            viewModel.closeDialog()
        },
        confirmButton = {
            TextButton(onClick = {
                onSubmit()
                viewModel.closeDialog()
            }) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                viewModel.closeDialog()
            }) {
                Text(text = "Cansel")
            }
        },
        title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Type city name: ")
                TextField(value = city, onValueChange = {
                        newName -> viewModel.setCity(newName)
                })
            }
        }

    )
}