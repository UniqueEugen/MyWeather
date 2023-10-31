package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data

import android.content.Context
import androidx.compose.runtime.MutableState
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.WeatherModel
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.weather.wiewModels.Weather
import kotlinx.coroutines.flow.MutableStateFlow

interface RemoteWeatherSource {
    fun getWeather(city: String, context: Context, uiState: Weather)
    fun getWeatherByHours(res:String): List<WeatherModel>
}