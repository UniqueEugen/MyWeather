package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.di.MemoryEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID
/**
 * Data Access Object for the memories table.
 */
@Dao
interface MemoriesDao {

    /**
     * Observes list of memories.
     *
     * @return all memories.
     */
    @Query("SELECT * FROM Memories")
    fun observeMemories(): Flow<List<MemoryEntity>>

    /**
     * Observes a single memory.
     *
     * @param id the memory id.
     * @return the planet with planetId.
     */
    @Query("SELECT * FROM Memories WHERE id = :id")
    fun observeMemoryById(id: UUID): Flow<MemoryEntity?>

    /**
     * Select all planets from the planets table.
     *
     * @return all planets.
     */
   /* @Query("SELECT * FROM Memories")
    suspend fun getMemories(): List<MemoryEntity>*/

    /**
     * Select a planet by id.
     *
     * @param planetId the planet id.
     * @return the planet with planetId.
     */
    @Query("SELECT * FROM Memories WHERE id = :id")
    suspend fun getMemoryById(id: UUID): MemoryEntity?

    /**
     * Insert a planet in the database. If the planet already exists, replace it.
     *
     * @param planet the planet to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemory(memory: MemoryEntity)

    /**
     * Update a planet.
     *
     * @param planet planet to be updated
     * @return the number of planets updated. This should always be 1.
     */
    @Update
    suspend fun updateMemory(memory: MemoryEntity): Int

    /**
     * Delete a planet by id.
     *
     * @return the number of planets deleted. This should always be 1.
     */
    @Query("DELETE FROM Memories WHERE id = :id")
    suspend fun deleteMemoryById(id: UUID): Int

    /**
     * Delete all planets.
     */
    @Query("DELETE FROM Memories")
    suspend fun deleteMemories()

    @Transaction
    suspend fun setMemories(planets: List<MemoryEntity>) {
        deleteMemories()
        planets.forEach { insertMemory(it) }
    }
}