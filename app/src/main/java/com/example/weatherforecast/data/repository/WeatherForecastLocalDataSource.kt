package com.example.weatherforecast.data.repository

import com.example.weatherforecast.data.local.WeatherEntity
import com.example.weatherforecast.model.City

interface WeatherForecastLocalDataSource {
    suspend fun saveCache(weatherEntity: WeatherEntity)

    suspend fun updateCache(weatherEntity: WeatherEntity)

    suspend fun deleteCache(weatherEntity: WeatherEntity)

    /** 都市名を用いてキャッシュから直近5日間の天気情報を取得 */
    suspend fun getWeatherEntityListCache(city: City): WeatherEntity?
}
