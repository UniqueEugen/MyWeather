package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.di

import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.RemoteWeatherSource
import com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.classes.home.data.source.RemoteWeatherSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/*@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {
    @Singleton
    @Provides
    fun provideWeatherSource(
        repository: WeatherRepository
    ): AddMemoryUseCase {
        return AddMemoryUseCase(repository)
    }
}*/

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherModule {
    @Binds
    abstract fun bindWeatherSource(remoteWeatherSourceImpl: RemoteWeatherSourceImpl): RemoteWeatherSource
}