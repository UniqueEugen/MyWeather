package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.favoriteScreen.viewStates

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.WorkResult
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.MemoriesRepository
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.Memory
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.domain.AddMemoryUseCase
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.navigation.MemoriesDestinationsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class FavoriteMemoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val memoriesRepository: MemoriesRepository,
    private val addMemoryUseCase: AddMemoryUseCase
):ViewModel(), FavoriteMemoryMVI {

    private var memoryId: String? = savedStateHandle[MemoriesDestinationsArgs.MEMORY_ID_ARG]

    private val _uiState = MutableStateFlow(FavoriteMemoryMVI.State())
    override val uiState: StateFlow<FavoriteMemoryMVI.State> =
        _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<FavoriteMemoryMVI.Effect>()
    override val effect: SharedFlow<FavoriteMemoryMVI.Effect> =
        _effect.asSharedFlow()

    override fun event(event: FavoriteMemoryMVI.Event) =
        when (event) {
            /*is FavoriteMemoryMVI.Event.OnSetShowFavoriteMemory ->
                onSetShowFavoriteList(showFavoriteList = event.showFavoriteList)*/

            is FavoriteMemoryMVI.Event.OnGetMemory ->
                getData(/*showFavoriteMemory = _uiState.value.showFavoriteMemory*/)

            is FavoriteMemoryMVI.Event.OnFavoriteClick ->
                onFavoriteClick(memory = event.memory)

            FavoriteMemoryMVI.Event.OnRefresh -> refreshing()
            FavoriteMemoryMVI.Event.OnBackPressed -> onBackPressed()
            is FavoriteMemoryMVI.Event.ShowToast -> showToast(event.message)
        }

   /* private fun onSetShowFavoriteList(showFavoriteList: Boolean) {
        _uiState.update {
            it.copy(showFavoriteMemory = showFavoriteList)
        }
    }*/

    private fun refreshing(){
        _uiState.update { it.copy(refreshing = !_uiState.value.refreshing) }
    }

    private fun getData(
        isRefreshing: Boolean = false,
      //  showFavoriteMemory: Boolean = false,
    ) {
        if (isRefreshing)
            _uiState.update {
                FavoriteMemoryMVI.State(
                    refreshing = true,
                )
            }
        viewModelScope.launch {
            if(memoryId!=null) loadMemory(UUID.fromString(memoryId))
        }
    }

    private fun loadMemory(memoryId: UUID) {
        viewModelScope.launch {
            val result = memoriesRepository.getMemoryFlow(memoryId).first()
            if (result !is WorkResult.Success || result.data == null) {
                _uiState.update { it.copy(error = true) }
            }
            else {
                val memory = result.data
                _uiState.update {
                    it.copy(
                        memory = memory
                    )
                }
            }
        }
    }
    private fun onFavoriteClick(memory: Memory) {
        val memory=memory.copy(favorite = !memory.favorite)
        _uiState.update { FavoriteMemoryMVI.State(memory = memory) }
        viewModelScope.launch(Dispatchers.IO) {
            if(memoryId!=null) {
                addMemoryUseCase(
                    memory
                )
            }else{
                /*addMemoryUseCase(
                    Memory(
                        id = null,
                        memory = _uiState.value.memory.memory,
                        date = _uiState.value.memoryAdded,
                        image = _uiState.value.currentImage,
                        favorite = false
                    )
                )*/
            }
        }
        refreshing()
    }

    private fun onBackPressed() {
        viewModelScope.launch {
            _effect.emit(FavoriteMemoryMVI.Effect.OnBackPressed)
        }
    }

    private fun showToast(message: String) {
        Log.d("MyLog"," In coroutine toast ${_uiState.value.memory.favorite}")
        Log.d("MyLog"," In coroutine toas2t ${uiState.value.memory.favorite}")
        viewModelScope.launch {
            _effect.emit(
                FavoriteMemoryMVI.Effect.ShowToast(message = message, uiState.value.memory.favorite)
            )
        }
    }

}
