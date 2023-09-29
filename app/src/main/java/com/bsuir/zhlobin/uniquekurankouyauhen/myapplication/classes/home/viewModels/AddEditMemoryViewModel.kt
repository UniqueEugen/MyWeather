package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.R
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.WorkResult
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.MemoriesRepository
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.Memory
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.domain.AddMemoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import javax.inject.Inject

data class AddEditMemoryUiState(
    val memory: String = "",
   // val planetDistanceLy: Float = 1.0F,
    val memoryAdded: Date = Date(),
    val isLoading: Boolean = false,
    val isMemorySaved: Boolean = false,
    val isMemorySaving: Boolean = false,
    val memorySavingError: Int? = null
)

@HiltViewModel
class AddEditMemoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addMemoryUseCase: AddMemoryUseCase,
    private val memoriesRepository: MemoriesRepository
): ViewModel() {
    private val memoryId: UUID = UUID.randomUUID()

    private val _uiState = MutableStateFlow(AddEditMemoryUiState())
    val uiState: StateFlow<AddEditMemoryUiState> = _uiState.asStateFlow()

    init {
        if (memoryId != null) {
            loadMemory(memoryId)
        }
    }

    fun saveMemory() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isMemorySaving = true) }
                addMemoryUseCase(
                    Memory(
                        id = memoryId,
                        memory = _uiState.value.memory
                    )
                )
                _uiState.update { it.copy(isMemorySaved = true) }
            }
            catch (e: Exception) {
                _uiState.update { it.copy(memorySavingError = R.string.error_saving_memory) }
            }
            finally {
                _uiState.update { it.copy(isMemorySaving = false) }
            }
        }
    }

    private fun loadMemory(memoryId: UUID) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = memoriesRepository.getMemoryFlow(memoryId).first()
            if (result !is WorkResult.Success || result.data == null) {
                _uiState.update { it.copy(isLoading = false) }
            }
            else {
                val memory = result.data
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        memory = memory.memory,
                        memoryAdded = memory.date
                    )
                }
            }
        }
    }

    fun setMemoryName(name: String) {
        _uiState.update { it.copy(memory = name) }
    }

}