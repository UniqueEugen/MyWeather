package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.di

import android.content.Context
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
        ).build()
    }
}
