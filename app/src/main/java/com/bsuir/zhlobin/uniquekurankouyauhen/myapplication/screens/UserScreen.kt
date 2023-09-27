package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.Memory

@Composable
fun UserScreen(
    model: Memory
) {
    Text("User -> ${model.memory}")
}