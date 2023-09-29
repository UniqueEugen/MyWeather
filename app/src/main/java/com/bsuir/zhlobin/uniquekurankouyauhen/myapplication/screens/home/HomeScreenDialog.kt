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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditMemoryScreen(
    onPlanetUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    viewModel: AddEditMemoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { if (!uiState.isMemorySaving) viewModel.saveMemory() }
            ) {
                Icon(Icons.Filled.Done, stringResource(R.string.save_memory_description))
            }
        }
    ) { paddingValues ->
        AddEditMemoryContent(
            loading = uiState.isLoading,
            saving = uiState.isMemorySaving,
            name = uiState.memory,
            onNameChanged = { newName -> viewModel.setMemoryName(newName) },
            modifier = Modifier.padding(paddingValues)
        )

        // Check if the planet is saved and call onPlanetUpdate event
        LaunchedEffect(uiState.isMemorySaved) {
            if (uiState.isMemorySaved) {
                onPlanetUpdate()
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
                .padding(all = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.38f)
            )
            OutlinedTextField(
                value = name,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = onNameChanged,
                label = { Text(stringResource(R.string.memory_label)) },
                placeholder = {
                    Text(
                        text = stringResource(R.string.name_hint),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                colors = textFieldColors
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


