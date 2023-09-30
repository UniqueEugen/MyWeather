package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data

import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.WorkResult
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.Memory
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalDataSource {
    fun getMemoriesFlow(): Flow<WorkResult<List<Memory>>>
    fun getMemoryFlow(memoryId: UUID): Flow<WorkResult<Memory?>>
    suspend fun setMemories(memories: List<Memory>)
    suspend fun addMemory(memory: Memory)
    suspend fun deleteMemory(memoryId: UUID)

}