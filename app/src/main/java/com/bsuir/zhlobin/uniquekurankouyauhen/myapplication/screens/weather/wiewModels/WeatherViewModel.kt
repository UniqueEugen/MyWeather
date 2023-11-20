package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.weather.wiewModels

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.RemoteWeatherSource
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


data class Weather(
  val mList: MutableState<List<WeatherModel>> = mutableStateOf(listOf<WeatherModel>()),
  val currentDay: MutableState<WeatherModel> = mutableStateOf(WeatherModel()),
  var city: String="",
  val showDialog:Boolean=false,
  var isConnection: Boolean=true
)
@HiltViewModel
class WeatherViewModel @Inject constructor(
  private val remoteWeatherSource: RemoteWeatherSource
) : ViewModel() {
  private val weather = Weather()
  private val _uiState = MutableStateFlow(weather)
  var uiState: StateFlow<Weather> = _uiState.asStateFlow()
  fun getItem(city:String, context: Context){
    Log.d("MyLog", "Ddfsfdsgf ${city}")
    remoteWeatherSource.getWeather(city, context = context, weather)
    Log.d("MyLog","hhhhh"+ weather.city)
  }

  fun setCity(name: String) {
    _uiState.update { it.copy(city = name) }
  }
  fun showDialog(){
    _uiState.update { it.copy(showDialog = true) }
  }
  fun closeDialog(){
    _uiState.update { it.copy(showDialog = false) }
  }

  fun hasConnection(context: Context){
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    var wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
    if (wifiInfo != null && wifiInfo.isConnected) {
      _uiState.update { it.copy(isConnection=true) }
    }
    wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
    if (wifiInfo != null && wifiInfo.isConnected) {
      _uiState.update { it.copy(isConnection=false) }
    }
    wifiInfo = cm.activeNetworkInfo
    if (wifiInfo != null && wifiInfo.isConnected) {
      _uiState.update { it.copy(isConnection=true) }
    } else _uiState.update { it.copy(isConnection=false) }
  }

}

