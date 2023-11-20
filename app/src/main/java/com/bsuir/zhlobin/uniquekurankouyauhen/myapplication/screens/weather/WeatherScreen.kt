package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.weather

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.MainActivity
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.R
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.weather.wiewModels.WeatherViewModel
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.theme.BlueLight
import com.google.android.gms.location.FusedLocationProviderClient


@SuppressLint("RememberReturnType")
@Composable
fun weatherScreen(
    innerPadding: PaddingValues,
    context: Context,
    latitudeAndLongitude: String,
    viewModel: WeatherViewModel = hiltViewModel(),
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Log.d("MyLog", "Dfyf ${latitudeAndLongitude}")
    remember {
        viewModel.getItem(latitudeAndLongitude, context)
    }
    viewModel.hasConnection(context = context)
    if (uiState.showDialog){
        dialogSearch(viewModel, uiState.city, onSubmit = {viewModel.getItem(uiState.city, context)})
        Log.d("MyLog", "city? ${uiState.city} ${uiState.mList.value.size}")
    }
    Image(
        bitmap = ImageBitmap.imageResource(R.drawable.weather_back_pic),
        contentDescription = "im1",
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.8f),
        contentScale = ContentScale.Crop
    )
    Column(
        modifier = Modifier
            .padding(paddingValues = innerPadding)
    ) {
        mainCard(uiState.currentDay, sync = {viewModel.getItem(uiState.city, context)},
            search = {
                viewModel.showDialog()
            })
        tabLayout(uiState.mList, uiState.currentDay)
    }
    if(!uiState.isConnection){
        Toast.makeText(context, R.string.noInetConnection,Toast.LENGTH_LONG).show()
    }
}


