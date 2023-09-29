package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.source

import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.WorkResult
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.Memory
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.LocalDataSource
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.di.toMemoryEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID

class RoomLocalDataSource internal constructor(
    private val memoriesDao: MemoriesDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LocalDataSource {
    override fun getMemoriesFlow(): Flow<WorkResult<List<Memory>>> {
        return memoriesDao.observeMemories().map {
            WorkResult.Success(it.map { memoryEntity -> memoryEntity.toMemory() })
        }
    }

    override fun getMemoryFlow(id: UUID): Flow<WorkResult<Memory?>> {
        return memoriesDao.observeMemoryById(id).map {
            WorkResult.Success(it?.toMemory())
        }
    }

    override suspend fun setMemories(memories: List<Memory>) {
        memoriesDao.setMemories(memories.map { it.toMemoryEntity() })
    }

    override suspend fun addMemory(memory: Memory) {
        memoriesDao.insertMemory(memory.toMemoryEntity())
    }

    override suspend fun deleteMemory(id: UUID) = withContext<Unit>(ioDispatcher) {
        memoriesDao.deleteMemoryById(id)
    }
}
