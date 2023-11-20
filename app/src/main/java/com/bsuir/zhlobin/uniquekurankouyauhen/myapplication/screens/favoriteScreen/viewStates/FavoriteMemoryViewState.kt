package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.favoriteScreen.viewStates

import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.Memory
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date
import java.util.UUID


/*
sealed class MainScreenItem {
    object MainScreenAddButtonItem : MainScreenItem()
    data class MainScreenTodoItem(
        val isChecked: Boolean,
        val text: String,
    ) : MainScreenItem()
}

@Immutable
sealed class FavoriteMemoryViewState: UiEvent {
    object MemoryLoadingState : FavoriteMemoryViewState()
    class MemoryLoadedState(val memory: Memory):FavoriteMemoryViewState()
    class MemoryErrorState(val errorText: String):FavoriteMemoryViewState()
}*/


interface UnidirectionalViewModel<STATE, EVENT, EFFECT> {
    val uiState: StateFlow<STATE>
    val effect: SharedFlow<EFFECT>
    fun event(event: EVENT)
}

interface FavoriteMemoryMVI:
    UnidirectionalViewModel<
            FavoriteMemoryMVI.State,
            FavoriteMemoryMVI.Event,
            FavoriteMemoryMVI.Effect> {

    data class State(
        val memory: Memory=Memory("", Date(), "", false, null),
        val refreshing: Boolean = false,
        val showLocalFavoriteMemory: Boolean = false,
        val error:Boolean = false
    )

    sealed class Event {
        data class OnFavoriteClick(val memory: Memory) : Event()
        data class OnGetMemory(val showFavoriteList: Boolean) : Event()
       /* data class OnSetShowFavoriteMemory(val showFavoriteList: Boolean) : Event()*/
        object OnRefresh: Event()
        object OnBackPressed : Event()
        data class OnShowUpdatebleScreen(val memory: Memory): Event()
        data class ShowToast(val message: String) : Event()
    }

    sealed class Effect {
        object OnBackPressed : Effect()
        data class ShowToast(val message: String, val isFavorite: Boolean) : Effect()
    }
}