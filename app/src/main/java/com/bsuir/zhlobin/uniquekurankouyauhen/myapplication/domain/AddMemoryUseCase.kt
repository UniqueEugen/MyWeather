package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.domain

import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.MemoriesRepository
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.Memory
import java.util.Date
import javax.inject.Inject

class AddMemoryUseCase  @Inject constructor(private val memoriesRepository: MemoriesRepository){
    suspend operator fun invoke(memory: Memory) {
        if (memory.memory.isEmpty()) {
            throw Exception("Please specify a planet name")
        }
        /*if (memory.distanceLy < 0) {
            throw Exception("Please enter a positive distance")
        }
        if (memory.discovered.after(Date())) {
            throw Exception("Please enter a discovery date in the past")
        }*/
        memoriesRepository.addMemory(memory)
    }
}