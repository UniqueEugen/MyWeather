package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.source

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.RemoteWeatherSource
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.WeatherModel
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.weather.wiewModels.Weather

import org.json.JSONObject
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

const val API_KEY = "2a0efbe6235a4e64952230911233010";
class RemoteWeatherSourceImpl @Inject constructor(): RemoteWeatherSource {
    override fun getWeather(city: String, context: Context, weather: Weather) {
        val url:String
        if (!city.equals("0.0,0.0"))
            url = "https://api.weatherapi.com/v1/forecast.json?" +
                    "key=${API_KEY}&" +
                    "q=" +
                    "${city}" +
                    "&days=" +
                    "14" +
                    "&aqi=no&alerts=no"
        else url ="https://api.weatherapi.com/v1/forecast.json?" +
                "key=${API_KEY}&" +
                "q=" +
                "Zlobin" +
                "&days=" +
                "14" +
                "&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(context)
        val sReq = StringRequest(
            Request.Method.GET,
            url,
            {
                res->
                val list = getWeatherByHours(res)
                weather.currentDay.value = list[0]
                weather.mList.value = list
                weather.city=list[0].city
            },
            {
                Log.d("MyLog", "VolleyError${it}")
            }
        )
        queue.add(sReq)
    }

    override fun getWeatherByHours(res: String): List<WeatherModel> {
        if (res.isEmpty()) return listOf()
        val mainObj = JSONObject(res)
        val days = mainObj.getJSONObject("forecast").getJSONArray("forecastday")
        val city = mainObj.getJSONObject("location").getString("name")
        val localtime = mainObj.getJSONObject("location").getString("localtime")
        val list = ArrayList<WeatherModel>()
        for (i in 0 until days.length()){
            val item = days[i] as JSONObject
            list.add(
                WeatherModel(
                    city=city,
                    localTime=localtime,
                    lastUpdated = "",
                    date = item.getString("date"),
                    currentTemp = "",
                    condition = item.getJSONObject("day").getJSONObject("condition")
                        .getString("text"),
                    conditionIcon = item.getJSONObject("day").getJSONObject("condition")
                        .getString("icon"),
                    maxTemp = item.getJSONObject("day").getString("maxtemp_c"),
                    minTemp =  item.getJSONObject("day").getString("mintemp_c"),
                    hours = item.getJSONArray("hour").toString()

                )
            )
        }
        list[0] = list[0].copy(
            lastUpdated = mainObj.getJSONObject("current").getString("last_updated"),
            currentTemp = mainObj.getJSONObject("current").getString("temp_c"),
            currentConditionIcon = mainObj.getJSONObject("current").getJSONObject("condition").getString("icon"),
            currentCondition = mainObj.getJSONObject("current").getJSONObject("condition").getString("text")
        )
        return list
    }

    override suspend fun getWeatherImage(city: String, context: Context)
    = suspendCoroutine<String>{
        val url:String
        if (!city.equals("0.0,0.0"))
            url = "https://api.weatherapi.com/v1/current.json?" +
                "key=" +
                 API_KEY +
                "&q=" +
                "$city" +
                "&aqi=no"
        else url ="https://api.weatherapi.com/v1/current.json?" +
                "key=" +
                API_KEY +
                "&q=" +
                "Zlobin" +
                "&aqi=no"
        val queue = Volley.newRequestQueue(context)
        val sReq = StringRequest(
            Request.Method.GET,
            url,
            {
                    res->
                it.resume(getResImage(res))
            },
            {
                Log.d("MyLog", "VolleyError${it}")
            }
        )
        queue.add(sReq)
    }

    override fun getResImage(res: String): String {
        if (res.isEmpty()) return ""
        val mainObj = JSONObject(res)
        System.out.println("asd "+res)
        return mainObj.getJSONObject("current").getJSONObject("condition").getString("icon")
    }

}