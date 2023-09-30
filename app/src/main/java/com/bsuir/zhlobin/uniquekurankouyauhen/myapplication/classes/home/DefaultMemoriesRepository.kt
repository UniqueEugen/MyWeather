package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home

import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.WorkResult
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.LocalDataSource
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class DefaultMemoriesRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val ioCoroutineDispatcher: CoroutineDispatcher
): MemoriesRepository {
    override fun getMemoriesFlow(): Flow<WorkResult<List<Memory>>> {
        return localDataSource.getMemoriesFlow()
    }

    override fun getMemoryFlow(memoryId: UUID): Flow<WorkResult<Memory?>> {
        return localDataSource.getMemoryFlow(memoryId)
    }

    override suspend fun refreshMemories() {
        val memories = remoteDataSource.getMemories()
        localDataSource.setMemories(memories)
    }
    override suspend fun addMemory(memory: Memory) {
        val memoryWithId= remoteDataSource.addMemory(memory)
        localDataSource.addMemory(memoryWithId)
    }

    override suspend fun deleteMemory(memoryId: UUID) {
        remoteDataSource.deleteMemory(memoryId)
        localDataSource.deleteMemory(memoryId)
    }
}