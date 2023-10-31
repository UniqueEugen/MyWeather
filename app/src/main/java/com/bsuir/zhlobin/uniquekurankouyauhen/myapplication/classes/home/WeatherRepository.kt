package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home

import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.WorkResult
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.WeatherModel
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeatherFlow(): Flow<List<WeatherModel>>
}