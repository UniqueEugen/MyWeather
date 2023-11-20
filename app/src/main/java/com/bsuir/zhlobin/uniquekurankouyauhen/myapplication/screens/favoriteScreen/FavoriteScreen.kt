package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.favoriteScreen

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.TypedArrayUtils.getText
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.R
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.Memory
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.favoriteScreen.viewStates.FavoriteMemoryMVI
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.favoriteScreen.viewStates.FavoriteMemoryViewModel
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.favoriteScreen.viewStates.UnidirectionalViewModel
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.home.DEFAULT_IMAGE
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    innerPadding: PaddingValues,
    onNavigateToDetailScreen: (UUID) -> Unit,
    onReturnBack: ()->Unit,
    context: Context,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    viewModel: FavoriteMemoryViewModel = hiltViewModel(),
    showFavoriteList: Boolean = false) {
    val (state, event, effect) = use(viewModel = viewModel)
    val activity = LocalContext.current as? Activity
    val luchshiy = MediaPlayer.create(context, R.raw.luchshiy_den)

    LaunchedEffect(key1 = Unit) {
        event.invoke(
            FavoriteMemoryMVI.Event.OnGetMemory(
                showFavoriteList = showFavoriteList,
            )
        )
    }

    // Implementation of `collectInLaunchedEffect` in the note section
    effect.collectInLaunchedEffect {
        when (it) {
            FavoriteMemoryMVI.Effect.OnBackPressed -> {
                activity?.onBackPressed()
            }

            is FavoriteMemoryMVI.Effect.ShowToast -> {
                Log.d("MyLog", "Toast:  ${it.isFavorite.toString()}")
                if (it.isFavorite){
                    Toast.makeText(activity, "Added to favorites", Toast.LENGTH_LONG).show()
                    luchshiy.start()
                }else{
                    Toast.makeText(activity, "Removed from favorites", Toast.LENGTH_LONG).show()
                }

            }

            else -> {}
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BottomAppBar(
                actions = {
                    androidx.compose.material.IconButton(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .width(32.dp)
                            .height(32.dp),
                        onClick = { onReturnBack }
                    ) {
                        androidx.compose.material.Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Done"
                        )
                    }
                },
                modifier = Modifier.background(colorResource(R.color.dimGrey)),
                containerColor = colorResource(R.color.Slenna),
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            event.invoke(FavoriteMemoryMVI.Event.OnShowUpdatebleScreen(state.memory))
                            state.memory.id?.let{onNavigateToDetailScreen(it)}
                        }
                    ) {
                        Icon(Icons.Filled.Edit, stringResource(R.string.save_memory_description))
                    }
                }
            )
        }
    ) {paddingValues->
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.favorite_fon),
            contentDescription = "im1",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.8f),
            contentScale = ContentScale.Crop
        )
        setItem(
            memoryState = state,
            onFavoriteClick = { memory -> event.invoke(FavoriteMemoryMVI.Event.OnFavoriteClick(memory = memory)) },
            showToast = { message ->
                event.invoke(FavoriteMemoryMVI.Event.ShowToast(message))
            },
            onRefresh = {
                event.invoke(FavoriteMemoryMVI.Event.OnRefresh)},
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding()+paddingValues.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()+paddingValues.calculateBottomPadding())
                .fillMaxSize()
        )
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun setItem(
    memoryState: FavoriteMemoryMVI.State,
    modifier: Modifier,
    onFavoriteClick: (memory: Memory) -> Unit,
    onRefresh:()->Unit,
    showToast: (message: String) -> Unit) {
    Log.d("MyLog", memoryState.memory.favorite.toString())
    var checked = memoryState.memory.favorite
    val refreshState = rememberPullRefreshState(
        refreshing = memoryState.refreshing,
        onRefresh = onRefresh,
    )
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconToggleButton(
            checked = checked,
            onCheckedChange = {
                onFavoriteClick(memoryState.memory)
                //Thread.sleep(1000)
                Log.d("MyLog", "cc ${memoryState.memory.favorite} $checked")
                showToast(
                    if (checked) {
                        "Added to favorites"
                    } else {
                        "Removed from favorites"
                    }
                )
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 10.dp, end = 10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.favorite),
                contentDescription = "isFavorite",
                tint = if (!memoryState.memory.favorite) Color.Black else Color.Yellow
            )
        }
        AsyncImage(
            // model = "https:${item.image.ifEmpty{ DEFAULT_IMAGE }}",
            model = "https:${memoryState.memory.image.ifEmpty() { DEFAULT_IMAGE }}",
            contentDescription = "wetherImg",
            modifier = Modifier
                .size(120.dp)
                .padding(top = 3.dp, end = 8.dp)
        )
        val gradientColors = listOf(
            Color.Cyan, colorResource(R.color.deepSkyBlue),
            colorResource(R.color.violet), colorResource(R.color.blueviolet) /*...*/
        )
        Text(
            text = memoryState.memory.memory,
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = gradientColors
                ),
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier.background(Color(0f,0f,0f,0.5f)),
            fontSize = 40.sp
        )
        Text(
            text = memoryState.memory.date.toString().substring(0, 19),
            style = TextStyle(Color.White),
            fontSize = 20.sp
        )
    }
}




@Composable
inline fun <reified STATE, EVENT, EFFECT> use(
    viewModel: UnidirectionalViewModel<STATE, EVENT, EFFECT>,
): StateDispatchEffect<STATE, EVENT, EFFECT> {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val dispatch: (EVENT) -> Unit = { event ->
        viewModel.event(event)
    }

    return StateDispatchEffect(
        state = uiState,
        effectFlow = viewModel.effect,
        dispatch = dispatch,
    )
}

data class StateDispatchEffect<STATE, EVENT, EFFECT>(
    val state: STATE,
    val dispatch: (EVENT) -> Unit,
    val effectFlow: SharedFlow<EFFECT>,
)

@Suppress("ComposableNaming")
@Composable
fun <T> SharedFlow<T>.collectInLaunchedEffect(function: suspend (value: T) -> Unit) {
    val sharedFlow = this
    LaunchedEffect(key1 = sharedFlow) {
        sharedFlow.collectLatest(function)
    }
}


/*
@Preview(showBackground = true)
@Composable
fun Favorite(){
    favoriteScreen(innerPadding = PaddingValues(0.dp), {}, Context)
}*/
