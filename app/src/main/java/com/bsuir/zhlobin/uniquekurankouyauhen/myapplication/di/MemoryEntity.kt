package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.di

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.Memory
import java.util.Date
import java.util.UUID

@Entity(tableName = "memories")
data class MemoryEntity(
    @ColumnInfo(name="memory")
    val memory: String="",
    @ColumnInfo(name="image")
    val image: String="",
    @ColumnInfo(name="date")
    val date: Date = Date(),
    @PrimaryKey val id: UUID, // java.util.UUID, сила котлина
){
    fun toMemory(): Memory {
        return Memory(
            id = id,
            memory = memory,
            date = date,
            image = image
        )
    }
}

fun Memory.toMemoryEntity(): MemoryEntity  {
    return MemoryEntity(
        id = id ?: UUID.randomUUID(),
        memory = memory,
        date = date,
        image = image
    )
}