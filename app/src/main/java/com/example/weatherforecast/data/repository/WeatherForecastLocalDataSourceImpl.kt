package com.example.weatherforecast.data.repository

import com.example.weatherforecast.data.local.WeatherDao
import com.example.weatherforecast.data.local.WeatherEntity
import com.example.weatherforecast.model.City
import javax.inject.Inject

class WeatherForecastLocalDataSourceImpl @Inject constructor(
    private val weatherDao: WeatherDao
): WeatherForecastLocalDataSource {
    override suspend fun saveCache(weatherEntity: WeatherEntity) {
        weatherDao.insert(weatherEntity)
    }

    override suspend fun updateCache(weatherEntity: WeatherEntity) {
        weatherDao.update(weatherEntity)
    }

    override suspend fun getWeatherEntityListCache(city: City): WeatherEntity? {
        return weatherDao.getWeatherInfoList(city)
    }
}
