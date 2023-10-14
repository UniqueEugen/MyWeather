package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens.home


import android.app.DatePickerDialog
import android.widget.DatePicker
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import java.util.Calendar
import java.util.Date

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
                    androidx.compose.material.IconButton(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .width(32.dp)
                            .height(32.dp),
                        onClick = { viewModel.deleteMemory() }
                    ) {
                        androidx.compose.material.Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
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
            date = uiState.memoryAdded,
            onNameChanged = { newName -> viewModel.setMemoryName(newName) },
            onDateChanged = { newDate -> viewModel.setMemoryDate(newDate)},
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
    date: Date,
    onNameChanged: (String) -> Unit,
    onDateChanged: (Date) -> Unit,
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
                /*.border(
                    1.dp, shape = RectangleShape, brush = Brush.linearGradient(
                        colors = listOf
                            (
                            Color.Yellow,
                            Color.White,
                            Color.Cyan
                        )
                    )
                )*/
        ) {

            val gradientColors = listOf(Color.Cyan, colorResource(R.color.deepSkyBlue),
                colorResource(R.color.violet), colorResource(R.color.blueviolet) /*...*/)
            val rainbowBrush = remember {
                Brush.linearGradient(
                    colorStops = arrayOf(0.2f to Color.Red, 0.4f to Color.Yellow, 0.6f to  Color.Green,0.8f to Color.Cyan, 0.9f to Color.Blue)
                )
            }
            TextField(
                value = name,
                modifier = Modifier
                    .fillMaxWidth()
                    //.defaultMinSize(minHeight = 50.dp)
                    .defaultMinSize(minHeight = 100.dp)
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

                onValueChange = onNameChanged,
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
                        fontSize = 20.sp,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 27.sp, brush = rainbowBrush//Brush.linearGradient(
                   // colors = listOf(Color.Magenta, Color.Cyan))
                ),
                leadingIcon = { Icon(Icons.Outlined.Create, contentDescription = "Пишем", tint = Color.White) },
                shape = RectangleShape,
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Black, cursorColor = Color.White)
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
            MyCalendar(
                date = date,
                onDateChanged = onDateChanged,
                modifier = Modifier
            )
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


@Composable
fun MyCalendar(
    onDateChanged: (Date) -> Unit,
    date: Date,
    modifier: Modifier
){

    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->run{
                mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
                onDateChanged(Date(mYear, mMonth, mDayOfMonth))
            }
        }, mYear, mMonth, mDay
    )

    Column(modifier = Modifier.fillMaxSize().padding(top = 50.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        // Displaying the mDate value in the Text
        Box(
            modifier = Modifier.background(brush = Brush.linearGradient(listOf(Color.Black, Color.Black)),
                shape= RectangleShape,
                alpha= 0.4f)
                .border(
                    1.dp, shape = RectangleShape, brush = Brush.linearGradient(
                        colors = listOf
                            (
                            Color.Red,
                            Color.Yellow,
                            Color.Blue
                        )
                    )
                )
        ) {
            Text(text = "Selected Date: ${date}",modifier= Modifier.padding(5.dp), fontSize = 20.sp, textAlign = TextAlign.Center, color = Color.White)
        }

        // Adding a space of 100dp height
        Spacer(modifier = Modifier.size(70.dp))

        // Creating a button that on
        // click displays/shows the DatePickerDialog
        Button(onClick = {
            mDatePickerDialog.show()
        }, colors = ButtonDefaults.buttonColors(containerColor = Color(0XFF0F9D58)) ) {
            Text(text = "Open Date Picker", color = Color.White)
        }
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
        onDateChanged = {},
        date = Date()
    )
}


