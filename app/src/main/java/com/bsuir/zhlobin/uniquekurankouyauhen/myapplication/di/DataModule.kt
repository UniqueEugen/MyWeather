package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.di

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.DefaultMemoriesRepository
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.MemoriesRepository
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.LocalDataSource
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.RemoteDataSource
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.domain.AddMemoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.source.ApiRemoteDataSource
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.source.RoomLocalDataSource

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideAddMemoryUseCase(
        repository: MemoriesRepository
    ): AddMemoryUseCase {
        return AddMemoryUseCase(repository)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMemoriesRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): MemoriesRepository {
        return DefaultMemoriesRepository(localDataSource, remoteDataSource, ioDispatcher)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(): RemoteDataSource {
        return ApiRemoteDataSource()
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(
        database: MemoriesDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): LocalDataSource {
        return RoomLocalDataSource(database.memoriesDao(), ioDispatcher)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MemoriesDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MemoriesDatabase::class.java,
            "Memories.db"
        )
            .addMigrations(MIGRATION_1_2)
            .addMigrations(MIGRATION_2_3)
            .build()
    }
}

@VisibleForTesting
internal val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE memories ADD COLUMN image TEXT DEFAULT '//cdn.weatherapi.com/weather/64x64/night/323.png' NOT NULL")
    }
}

@VisibleForTesting
internal val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE memories ADD COLUMN favorite INTEGER DEFAULT 0 NOT NULL")
    }
}