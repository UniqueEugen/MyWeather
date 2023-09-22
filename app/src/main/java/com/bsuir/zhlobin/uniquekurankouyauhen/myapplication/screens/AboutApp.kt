package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(){
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "MyWeather",
                        fontSize = 30.sp,
                        color = colorResource(R.color.dimGrey),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        fontFamily = FontFamily.Cursive,
                        style = TextStyle(
                            shadow = Shadow(Color.Cyan, Offset(2.0f, 5.5f), 2f),
                            textGeometricTransform = TextGeometricTransform(
                                scaleX = 1.1f,
                                skewX = 0f
                            )
                        )
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = colorResource(R.color.Orange)
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }

                },
                modifier = Modifier,
                actions = {
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                }


            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "Localized description"
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Edit",
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Like it",
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Info,
                            contentDescription = "My Weather app",
                        )
                    }
                },
                modifier = Modifier.background(colorResource(R.color.dimGrey)),
                containerColor = colorResource(R.color.Slenna),
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /* do something */ },
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.Add, "Localized description")
                    }
                }
            )
        },
        content = {
            innerPadding->
            AboutApp(innerPadding)
        }
    )
}
@Composable
fun AboutApp(innerPadding: PaddingValues) {
    val count = remember { mutableStateOf(0) }
    val myColor: Color = Color(0xFF8C00)
    val context = LocalContext.current
    val intentYouTube = remember {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.youtube.com/channel/UCKIRqumbV83skuPwuNuYVoA")
        )
    }
    val intentTelegram =
        remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Menar_games")) }
    val intentVK = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/eugen1st")) }
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(
                Brush.linearGradient(listOf(Color.Blue, Color.Green)),
                shape = RectangleShape
            )
            .clickable(onClick = { count.value += 1 })
            .verticalScroll(rememberScrollState()),
        //.horizontalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(

            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 30.dp)
                //.weight(1f)
                .size(50.dp, 50.dp)
                .background(
                    Brush.radialGradient(
                        listOf(
                            colorResource(R.color.redOrange),
                            //colorResource(R.color.darkOrange),
                            colorResource(R.color.Orange),
                            Color.Yellow,
                            //colorResource(R.color.sandyYellow)
                        )
                    ),
                    shape = CircleShape
                )
                .fillMaxWidth(0.1f),


            ) {}
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                stringResource(R.string.AboutAppLabel).trimIndent(),
                fontSize = 28.sp,
                softWrap = true,
                modifier = Modifier
                    .padding(top = 70.dp, start = 20.dp, end = 20.dp),
                /*.horizontalScroll(
                        rememberScrollState()

                    )
                    .verticalScroll(rememberScrollState())*/
                color = Color.White,
                textAlign = TextAlign.Justify,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp,
            )


        }
        Box(
            modifier = Modifier
                // .width(400.dp)
                .fillMaxWidth()
        ) {
            Text(
                stringResource(R.string.AboutApp).trimIndent(),
                fontSize = 28.sp,
                softWrap = true,
                modifier = Modifier
                    .padding(top = 70.dp, start = 20.dp, end = 20.dp),
                /*.horizontalScroll(
                        rememberScrollState()

                    )
                    .verticalScroll(rememberScrollState())*/
                color = Color.White,
                textAlign = TextAlign.Justify,
                fontFamily = FontFamily.Cursive,
                fontStyle = FontStyle.Italic,
                letterSpacing = 3.sp,
            )


        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Text(
                stringResource(R.string.clicker).trimIndent() + " ${count.value} " + stringResource(
                    R.string.clickerEnd
                ),
                fontSize = 22.sp,
                softWrap = true,
                modifier = Modifier
                    .padding(top = 70.dp, start = 30.dp, end = 30.dp)
                /*.horizontalScroll(
                            rememberScrollState()

                        )
                        .verticalScroll(rememberScrollState())*/,
                color = MaterialTheme.colorScheme.background,
                textAlign = TextAlign.Justify
            )


        }
        Box(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            stopAttention(count.value)
            /*Text(
                            color = colorResource(stopAttention(count.value)[2]),
                            text = stopAttention(count.value)[1],

                            //modifier = Modifier
                            fontSize = 28.sp
                        )*/
        }
        Box(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.Start)
        ) {
            Text(
                stringResource(R.string.AboutDevLabel).trimIndent(),
                fontSize = 28.sp,
                softWrap = true,
                modifier = Modifier
                    .padding(top = 70.dp, start = 20.dp, end = 20.dp),
                /*.horizontalScroll(
                        rememberScrollState()

                    )
                    .verticalScroll(rememberScrollState())*/
                color = Color.White,
                textAlign = TextAlign.Justify,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp,
            )
        }
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.menar),
            contentDescription = "Hello, it's me!",
            modifier = Modifier
                .padding(10.dp)
                .clip(shape = CircleShape)
                .align(Alignment.CenterHorizontally)
        )
        Box(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.Start)
        ) {
            Text(
                stringResource(R.string.aboutMe).trimIndent(),
                fontSize = 28.sp,
                softWrap = true,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp),
                /*.horizontalScroll(
                        rememberScrollState()

                    )
                    .verticalScroll(rememberScrollState())*/
                color = Color.White,
                textAlign = TextAlign.Justify,
                fontWeight = FontWeight.Light,
                letterSpacing = 1.sp,
            )
        }
        Row(
            modifier = Modifier
                .padding(start = 80.dp, end = 80.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.youtube),
                contentDescription = "YouTube link",
                modifier = Modifier
                    .size(70.dp)
                    .padding(10.dp)
                    .clip(shape = CircleShape)
                    .clickable { context.startActivity(intentYouTube) }
            )
            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.telegram),
                contentDescription = "Telegram link",
                modifier = Modifier
                    .size(70.dp)
                    .padding(10.dp)
                    .clip(shape = CircleShape)
                    .clickable { context.startActivity(intentTelegram) }
            )
            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.vk),
                contentDescription = "VK link",
                modifier = Modifier
                    .size(70.dp)
                    .padding(10.dp)
                    .clip(shape = CircleShape)
                    .clickable { context.startActivity(intentVK) }
            )
        }
    }


}


// A surface container using the 'background' color from the theme



@Composable
fun stopAttention(tap: Int): Unit =
    if(tap<50){ Text(
        color = colorResource(R.color.white),
        text = "Click more",
        modifier = Modifier
            .fillMaxWidth(1f),
        lineHeight = 30.sp,
        fontSize = 28.sp,
        textAlign = TextAlign.Center,
    )
    }
    else if(tap>=50 && tap<=80){
        Text(
            color = colorResource(R.color.Orange),
            text = "You clicked too much, please stop!",
            modifier = Modifier
                .fillMaxWidth(1f),
            lineHeight = 36.sp,
            fontSize = 33.sp,
            textAlign = TextAlign.Center,
        )
    }
    else(Text(
        color = colorResource(R.color.redOrange),
        text = "STOP CLICK RIGHT NOW!!!!",
        modifier = Modifier
            .fillMaxWidth(1f),
        lineHeight = 53.sp,
        fontSize = 50.sp,
        textAlign = TextAlign.Center,
    ))