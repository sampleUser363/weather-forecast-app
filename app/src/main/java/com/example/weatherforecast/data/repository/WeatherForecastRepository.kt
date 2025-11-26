package com.example.weatherforecast.data.repository

import com.example.weatherforecast.model.City
import com.example.weatherforecast.model.WeatherInfo

interface WeatherForecastRepository {
    /** 都市名を用いて直近5日間の天気情報を取得 */
    suspend fun getWeatherForecast(cityType: City): List<WeatherInfo>

    /** 緯度経度を用いて直近5日間の天気情報を取得 */
    suspend fun getWeatherForecast(latitude: Double, longitude: Double): List<WeatherInfo>
}
