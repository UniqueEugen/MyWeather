package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home

import java.util.UUID
import java.util.Date

data class Memory(
    val memory: String,
    val date: Date,
    val image: String,
    val id: UUID? = null,
)