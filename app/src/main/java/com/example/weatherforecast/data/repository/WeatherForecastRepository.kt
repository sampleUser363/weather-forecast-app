package com.example.weatherforecast.data.repository

import com.example.weatherforecast.model.City
import com.example.weatherforecast.model.WeatherInfo

interface WeatherForecastRepository {
    suspend fun getWeatherForecast(cityType: City): List<WeatherInfo>
}
