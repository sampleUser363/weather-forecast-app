package com.example.weatherforecast.di

import com.example.weatherforecast.data.repository.WeatherForecastLocalDataSource
import com.example.weatherforecast.data.repository.WeatherForecastLocalDataSourceImpl
import com.example.weatherforecast.data.repository.WeatherForecastRemoteDataSource
import com.example.weatherforecast.data.repository.WeatherForecastRemoteDataSourceImpl
import com.example.weatherforecast.data.repository.WeatherForecastRepository
import com.example.weatherforecast.data.repository.WeatherForecastRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWeatherForecastLocalDataSource(
        weatherForecastLocalDataSourceImpl: WeatherForecastLocalDataSourceImpl
    ): WeatherForecastLocalDataSource

    @Binds
    @Singleton
    abstract fun bindWeatherForecastRemoteDataSource(
        weatherForecastRemoteDataSourceImpl: WeatherForecastRemoteDataSourceImpl
    ): WeatherForecastRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindWeatherForecastRepository(
        weatherForecastRepositoryImpl: WeatherForecastRepositoryImpl
    ): WeatherForecastRepository
}
