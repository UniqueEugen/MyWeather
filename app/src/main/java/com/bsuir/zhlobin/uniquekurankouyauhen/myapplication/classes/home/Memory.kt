package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home

import android.os.Parcelable
import java.util.UUID
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class Memory(
    val memory: String,
    val date: Date = Date(),
    val id: UUID = UUID.randomUUID(), // java.util.UUID, сила котлина
): Parcelable {
    companion object Keys {
        const val USER = "user"
    }
}