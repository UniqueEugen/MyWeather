package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.viewModels

import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.WorkResult
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.domain.AddMemoryUseCase
import java.util.UUID

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.MemoriesRepository
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.Memory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

data class MemoriesListUiState(
    val memories: List<Memory> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

@HiltViewModel
class MemoriesListViewModel @Inject constructor(
    private val addMemoryUseCase: AddMemoryUseCase,
    private val memoriesRepository: MemoriesRepository
): ViewModel() {
    private val memories = memoriesRepository.getMemoriesFlow()

    //How many things are we waiting for to load?
    private val numLoadingItems = MutableStateFlow(0)

    val uiState = combine(memories, numLoadingItems) { memories, loadingItems ->
        when (memories) {
            is WorkResult.Error -> MemoriesListUiState(isError = true)
            is WorkResult.Loading -> MemoriesListUiState(isLoading = true)
            is WorkResult.Success -> MemoriesListUiState(memories = memories.data, isLoading = loadingItems > 0)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MemoriesListUiState(isLoading = true)
    )

    fun addSampleMemories() {
        viewModelScope.launch {
            withLoading {
                val memories = arrayOf(
                    Memory(memory = "It was a perfect weather",date= Date()),
                    Memory(memory = "It was a normal weather",date= Date()),
                    Memory(memory = "It was a good weather",date= Date()),
                )
                memories.forEach { addMemoryUseCase(it) }
            }
        }
    }

    fun deleteMemory(memoryId: UUID) {
        viewModelScope.launch {
            withLoading {
                memoriesRepository.deleteMemory(memoryId)
            }
        }
    }

    fun refreshMemoriesList() {
        viewModelScope.launch {
            withLoading {
                memoriesRepository.refreshMemories()
            }
        }
    }

    private suspend fun withLoading(block: suspend () -> Unit) {
        try {
            addLoadingElement()
            block()
        }
        finally {
            removeLoadingElement()
        }
    }

    private fun addLoadingElement() = numLoadingItems.getAndUpdate { num -> num + 1 }
    private fun removeLoadingElement() = numLoadingItems.getAndUpdate { num -> num - 1 }
}