package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.WeatherModel
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.weather.wiewModels.WeatherViewModel
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.theme.BlueLight
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

@OptIn(ExperimentalPagerApi::class)
@Composable
fun tabLayout(
    daysWeather: MutableState<List<WeatherModel>>,
    currentDay: MutableState<WeatherModel>
){
    val tabList = listOf("HOURS", "DAYS")
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .clip(RoundedCornerShape(5.dp))
    ) {
        TabRow(
            selectedTabIndex = 0,
            indicator = { pos ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, pos)
                )
            },
            backgroundColor = BlueLight,
            contentColor = Color.White
        ) {
            tabList.forEachIndexed { index, text ->
                Tab(
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = text) }
                )
            }
        }
        HorizontalPager(
            count = tabList.size,
            state = pagerState,
            modifier = Modifier.weight(1.0f)
        ) {
            index->
            val list = when(index){
                0-> getWeatherByHours(currentDay.value.hours)
                1-> daysWeather.value
                else->daysWeather.value
            }
            MainList(list = list, currentDay = currentDay)
        }
    }
}

private fun getWeatherByHours(hours:String): List<WeatherModel>{
    if(hours.isEmpty()) return listOf()
    val hoursArray = JSONArray(hours)
    val list = ArrayList<WeatherModel>()
    for(i in 0 until  hoursArray.length()){
        val item = hoursArray[i] as JSONObject
        var time =  item.getString("time")
        time = time.substring(time.length-6)
        list.add(
            WeatherModel(
                date = time,
                currentTemp = item.getString("temp_c"),
                condition = item.getJSONObject("condition").getString("text"),
                conditionIcon = item.getJSONObject("condition").getString("icon"),
            )
        )
    }
    return list
}
