package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.weather.wiewModels

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.WeatherModel
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.RemoteWeatherSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
data class Weather(
  val mList: MutableState<List<WeatherModel>> = mutableStateOf(listOf<WeatherModel>()),
  val currentDay: MutableState<WeatherModel> = mutableStateOf(WeatherModel())
)
@HiltViewModel
class WeatherViewModel @Inject constructor(
  private val remoteWeatherSource: RemoteWeatherSource
) : ViewModel() {
  private val weather = Weather()
  private val _uiState = MutableStateFlow(weather)
  var uiState: StateFlow<Weather> = _uiState.asStateFlow()
  fun getItem(city:String, context: Context){
    remoteWeatherSource.getWeather(city, context = context, weather)
  }
}

