package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.di

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.Memory
import java.util.Date
import java.util.UUID

@Entity(tableName = "memories")
data class MemoryEntity(
    val memory: String,
    val date: Date = Date(),
    @PrimaryKey val id: UUID = UUID.randomUUID(), // java.util.UUID, сила котлина
){
    fun toMemory(): Memory {
        return Memory(
            id = id,
            memory = memory,
            date = date
        )
    }
}

fun Memory.toMemoryEntity(): MemoryEntity  {
    return MemoryEntity(
        id = id ?: UUID.randomUUID(),
        memory = memory,
        date = Date()
    )
}