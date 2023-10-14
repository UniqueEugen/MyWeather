package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home

import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.WorkResult
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface MemoriesRepository {
    fun getMemoriesFlow(): Flow<WorkResult<List<Memory>>>
    fun getMemoryFlow(memoryId: UUID): Flow<WorkResult<Memory?>>
    suspend fun refreshMemories()
    suspend fun addMemory(memory: Memory)
    suspend fun deleteMemory(memoryId: UUID)
}