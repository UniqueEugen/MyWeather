package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.R

@Composable
fun Screen3(innerPadding: PaddingValues){
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(
                Brush.linearGradient(listOf(Color.Black, Color.DarkGray)),
                shape = RectangleShape
            )
    ) {
        Text(
            text = "Memories",
            modifier = Modifier,
            fontSize = 28.sp,
            color = colorResource(R.color.Slenna)
        )
    }
}