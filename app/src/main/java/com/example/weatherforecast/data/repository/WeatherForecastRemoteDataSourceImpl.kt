package com.example.weatherforecast.data.repository

import com.example.weatherforecast.BuildConfig
import com.example.weatherforecast.data.network.WeatherApiService
import com.example.weatherforecast.model.City
import com.example.weatherforecast.model.WeatherInfo
import javax.inject.Inject

class WeatherForecastRemoteDataSourceImpl @Inject constructor(
    private val weatherApiService: WeatherApiService,
) : WeatherForecastRemoteDataSource {
    private fun City.toApiQuery(): String? = when(this) {
        City.TOKYO -> "Tokyo"
        City.HYOGO -> "Hyogo"
        City.OITA -> "Oita"
        City.HOKKAIDO -> "Hokkaido"
        City.CURRENT_LOCATION -> null
    }

    override suspend fun getWeatherForecast(cityType: City): List<WeatherInfo> {
        val cityParameter = cityType.toApiQuery()
        if (cityParameter == null) return emptyList()
        // ユーザーが指定した都市名とlocal.propertiesに定義したAPI_KEYで通信
        val response = weatherApiService.getWeatherForecast(
            city = cityParameter,
            appId = BuildConfig.OPEN_WEATHER_MAP_API_KEY,
        )
        return response.weatherInfoList
    }

    override suspend fun getWeatherForecast(
        latitude: Double,
        longitude: Double
    ): List<WeatherInfo> {
        // ユーザーが指定した緯度経度とlocal.propertiesに定義したAPI_KEYで通信
        val response = weatherApiService.getWeatherForecast(
            latitude = latitude.toString(),
            longitude = longitude.toString(),
            appId = BuildConfig.OPEN_WEATHER_MAP_API_KEY,
        )
        return response.weatherInfoList
    }
}
