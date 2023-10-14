package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data

import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.Memory
import java.util.UUID

interface RemoteDataSource {
    suspend fun getMemories(): List<Memory>
    suspend fun addMemory(memory: Memory): Memory
    suspend fun deleteMemory(planetId: UUID)
}
