package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.home.viewModels

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.R
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.WorkResult
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.MemoriesRepository
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.Memory
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.source.RemoteWeatherSourceImpl
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.domain.AddMemoryUseCase
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.navigation.MemoriesDestinationsArgs
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
    val memoryAdded: Date=Date(),
    val image: String = "",
    val currentImage: String="",
    val favoriteItem: Boolean = false,
    val isLoading: Boolean = false,
    val isMemorySaved: Boolean = false,
    val isMemorySaving: Boolean = false,
    val memorySavingError: Int? = null
)

@HiltViewModel
class AddEditMemoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addMemoryUseCase: AddMemoryUseCase,
    private val memoriesRepository: MemoriesRepository,
    private val remoteWeatherSource: RemoteWeatherSourceImpl
): ViewModel() {
    private var memoryId: String? = savedStateHandle[MemoriesDestinationsArgs.MEMORY_ID_ARG]

    private val _uiState = MutableStateFlow(AddEditMemoryUiState())
    val uiState: StateFlow<AddEditMemoryUiState> = _uiState.asStateFlow()

    init {
        if (memoryId != null) {
            loadMemory(UUID.fromString(memoryId))
        }
    }

    fun deleteMemory() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isMemorySaving = true) }
                System.out.println(memoryId.toString())
                if(memoryId!=null) {
                    memoriesRepository.deleteMemory(UUID.fromString(memoryId))
                }
                _uiState.update { it.copy(isMemorySaved = true) }
            }
            catch (e: Exception) {
                System.out.println(e)
                _uiState.update { it.copy(memorySavingError = R.string.error_saving_memory) }
            }
            finally {
                _uiState.update { it.copy(isMemorySaving = false) }
            }
           // withLoading {
           // }
        }
    }
/* private suspend fun withLoading(block: suspend () -> Unit) {
     try {
         addLoadingElement()
         block()
     }
     finally {
         removeLoadingElement()
     }
 }
 private fun addLoadingElent() = numLoadingItems.getAndUpdate { num -> num + 1 }
 private fun removeLoadingElement() = numLoadingItems.getAndUpdate { num -> num - 1 }*/
 fun skipEditMemory(){

 }

 fun saveMemory() {
     viewModelScope.launch {
         try {
             _uiState.update { it.copy(isMemorySaving = true) }
             System.out.println(memoryId.toString() + _uiState.value.memory)
             System.out.println("current Image " + _uiState.value.image)
             if(memoryId!=null) {
                 addMemoryUseCase(
                     Memory(
                         id = UUID.fromString(memoryId),
                         memory = _uiState.value.memory,
                         date = _uiState.value.memoryAdded,
                         image = _uiState.value.image,
                         favorite = _uiState.value.favoriteItem
                 )
                 )
             }else{
                 addMemoryUseCase(
                     Memory(
                         id = null,
                         memory = _uiState.value.memory,
                         date = _uiState.value.memoryAdded,
                         image = _uiState.value.currentImage,
                         favorite = false
                     )
                 )
             }
             _uiState.update { it.copy(isMemorySaved = true) }
         }
         catch (e: Exception) {
             System.out.println(e)
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
                     memoryAdded = memory.date,
                     image = memory.image,
                     favoriteItem = memory.favorite
                 )
             }
         }
     }
 }

 fun setMemoryName(name: String) {
     _uiState.update { it.copy(memory = name) }
 }
fun setMemoryWeather(context: Context,  latitudeAndLongitude:String) {
    viewModelScope.launch {
        _uiState.update { it.copy(currentImage = remoteWeatherSource.getWeatherImage(latitudeAndLongitude, context)) }
        System.out.println("sdfdsf" + _uiState.value.image)
    }
}
 fun setMemoryDate(date: Date) {
     _uiState.update { it.copy(memoryAdded = date) }
     System.out.println(date)
 }

}


