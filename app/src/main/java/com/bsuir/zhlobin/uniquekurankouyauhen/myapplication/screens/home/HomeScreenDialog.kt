package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.R
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.viewModels.AddEditMemoryViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.navigation.MemoriesDestinations
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.navigation.NavBar
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditMemoryScreen(
    it: PaddingValues,
    onMemoryUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    viewModel: AddEditMemoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    System.out.println("ui memory: "+ uiState.memory);
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { viewModel.skipEditMemory() }) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "Close whithout saving"
                        )
                    }
                },
                modifier = Modifier.background(colorResource(R.color.dimGrey)),
                containerColor = colorResource(R.color.Slenna),
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { if (!uiState.isMemorySaving) viewModel.saveMemory() }
                    ) {
                        Icon(Icons.Filled.Done, stringResource(R.string.save_memory_description))
                    }
                }
            )
        }

    ) { paddingValues ->
        AddEditMemoryContent(
            loading = uiState.isLoading,
            saving = uiState.isMemorySaving,
            name = uiState.memory,
            onNameChanged = { newName -> viewModel.setMemoryName(newName) },
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding() + paddingValues.calculateTopPadding(),
                    bottom = it.calculateBottomPadding() + paddingValues.calculateBottomPadding()
                )
                .background(
                    brush = Brush.verticalGradient(
                        colorStops =
                        arrayOf(
                            0.2f to Color.Black,
                            0.43f to Color.DarkGray,
                            0.6f to Color.Blue,
                            0.8f to Color.Cyan,
                            1f to colorResource(R.color.dimGrey)
                        )
                    )
                )
        )

        // Check if the planet is saved and call onPlanetUpdate event
        LaunchedEffect(uiState.isMemorySaved) {
            if (uiState.isMemorySaved) {
                onMemoryUpdate()
            }
        }
    }

    //Show the error message as a toast if we've just had an error
    val context = LocalContext.current
    val errorText = uiState.memorySavingError?.let { stringResource(it) }
    LaunchedEffect(errorText) {
        if (errorText != null) {
            Toast.makeText(context, errorText, Toast.LENGTH_LONG).show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddEditMemoryContent(
    loading: Boolean,
    saving: Boolean,
    name: String,
    onNameChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (loading) {
        LoadingContent()
    }
    else {
        Column(
            modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(all = 30.dp)
                .verticalScroll(rememberScrollState())
                .border(
                    1.dp, shape = RectangleShape, brush = Brush.linearGradient(
                        colors = listOf
                            (
                            Color.Yellow,
                            Color.White,
                            Color.Cyan
                        )
                    )
                )
        ) {

            val gradientColors = listOf(Color.Cyan, colorResource(R.color.deepSkyBlue),
                colorResource(R.color.violet), colorResource(R.color.blueviolet) /*...*/)
            TextField(
                value = name,
                modifier = Modifier
                    .fillMaxWidth()
                    //.defaultMinSize(minHeight = 50.dp)
                    .fillMaxHeight()
                   /* .background(
                        brush = Brush.linearGradient(
                            listOf(
                                colorResource(R.color.blueviolet),
                                colorResource(R.color.deepSkyBlue)
                            )
                        ),
                        shape = RectangleShape,
                        alpha = 0.6f
                    )*/
                    .padding(top = 30.dp)
                    .border(
                        1.dp, shape = RectangleShape, brush = Brush.linearGradient(
                            colors = listOf
                                (
                                Color.Yellow,
                                Color.White,
                                Color.Cyan
                            )
                        )
                    ),
                onValueChange = onNameChanged ,
                label = {
                    Text(
                        text = stringResource(R.string.memory_label), style = TextStyle(
                            brush = Brush.linearGradient(
                                colors = gradientColors
                            )
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Transparent),
                        fontSize = 30.sp
                    )

                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.name_hint),
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge

                    )
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 27.sp, brush = Brush.linearGradient(
                    colors = listOf(Color.Magenta, Color.Cyan))),
                leadingIcon = { Icon(Icons.Outlined.Create, contentDescription = "Пишем") },
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors()
            )
         /*   OutlinedTextField(
                value = distanceLy.toString(),
                onValueChange = { try { it.toFloat().run { onDistanceLyChanged(this) }  } catch (_: NumberFormatException) { } },
                label = { Text(stringResource(R.string.distance_label)) },
                placeholder = { Text(stringResource(R.string.distance_description)) },
                modifier = Modifier
                    .height(350.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                colors = textFieldColors
            )*/
        }

        if (saving) {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = Color.DarkGray)
    }
}

@Preview
@Composable
fun AddEditContentPreview() {
    AddEditMemoryContent(
        saving = true,
        loading = true,
        name = "aaa",
        onNameChanged = {},
    )
}


