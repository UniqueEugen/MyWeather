package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.weather

import androidx.compose.foundation.ExperimentalFoundationApi
import com.google.accompanist.pager.PagerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
) : ViewModel() {

    @OptIn(ExperimentalPagerApi::class)
    fun animate(pagerState: PagerState, index: Int){
        viewModelScope.launch {
            pagerState.animateScrollToPage(index)
        }
    }
}