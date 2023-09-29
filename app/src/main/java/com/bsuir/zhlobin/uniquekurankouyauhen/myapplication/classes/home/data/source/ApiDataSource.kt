package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.source

import android.os.SystemClock
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.Memory
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.RemoteDataSource
import kotlinx.coroutines.delay
import java.util.UUID
import javax.inject.Inject

class ApiRemoteDataSource @Inject constructor(): RemoteDataSource {
    private val memoriesCache = ArrayList<Memory>()
    private var lastDelay = 0L

    override suspend fun getMemories(): List<Memory> {
        simulateApiDelay()
        return memoriesCache
    }

    override suspend fun addMemory(memory: Memory): Memory{
        simulateApiDelay()
        val memoryToAdd = if (memory.id.toString() == null) memory.copy(id = UUID.randomUUID()) else memory
        memoriesCache.add(memoryToAdd)
        return memoryToAdd
    }

    override suspend fun deleteMemory(id: UUID) {
        simulateApiDelay()
        memoriesCache.removeIf { it.id == id }
    }

    private suspend fun simulateApiDelay() {
        //(this logic is purely to avoid 3x simulated delays when adding the sample planets)
        if (SystemClock.uptimeMillis() > lastDelay + 500) {
            delay(2000)
            lastDelay = SystemClock.uptimeMillis()
        }
    }
}