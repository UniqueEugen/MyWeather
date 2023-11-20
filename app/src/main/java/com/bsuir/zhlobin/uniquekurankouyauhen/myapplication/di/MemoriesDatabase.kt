package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.di

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.source.MemoriesDao
import java.util.Date

@Database(entities = [MemoryEntity::class], version = 3,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class MemoriesDatabase : RoomDatabase() {
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