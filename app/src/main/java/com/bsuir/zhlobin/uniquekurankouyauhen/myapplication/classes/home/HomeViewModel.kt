package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    /* val items: SnapshotStateList<Memory> = DefaultWeather.toMutableStateList()

    fun onClickRemoveMemories(memory: Memory) = items.remove(memory)

    private companion object {

        private val DefaultWeather = listOf(
            Memory("Perfect"),
            Memory("Good"),
            Memory("Bad")
        )
    }*/
    private val _dataFlow = MutableStateFlow<List<Memory>>(emptyList())
    val dataFlow: StateFlow<List<Memory>> = _dataFlow

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            // Simulate loading data with delay
            _dataFlow.emit(listOf(
                Memory("Perfect"),
                Memory("Good"),
                Memory("Bad")
            ))
        }
    }
}