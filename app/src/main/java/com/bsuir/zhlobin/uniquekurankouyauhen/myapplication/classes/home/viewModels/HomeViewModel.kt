package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.Memory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class HomeViewModel : ViewModel() {
    private val memoryList = listOf <Memory>(
        Memory("Perfect",date= Date()),
        Memory("Good",date= Date()),
        Memory("Bad",date= Date())
    );

    private val _dataFlow = MutableStateFlow<List<Memory>>(emptyList())
    val dataFlow: StateFlow<List<Memory>> = _dataFlow

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            // Simulate loading data with delay
            _dataFlow.emit(memoryList)
        }
    }
/* val items: SnapshotStateList<Memory> = DefaultWeather.toMutableStateList()

    fun onClickRemoveMemories(memory: Memory) = items.remove(memory)

    private companion object {

        private val DefaultWeather = listOf(
            Memory("Perfect"),
            Memory("Good"),
            Memory("Bad")
        )
    }*/
}