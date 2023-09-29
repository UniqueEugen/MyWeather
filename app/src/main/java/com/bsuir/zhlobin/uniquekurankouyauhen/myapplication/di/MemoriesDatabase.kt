package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.di

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.source.MemoriesDao
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.di.MemoryEntity
import java.util.Date

@Database(entities = [MemoryEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MemoriesDB : RoomDatabase() {

    abstract fun memoriesDao(): MemoriesDao
}

object Converters {
    @TypeConverter
    fun timestampToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?) = date?.time
}