package com.example.weatherforecast.data.repository

import com.example.weatherforecast.BuildConfig
import com.example.weatherforecast.data.network.WeatherApiService
import com.example.weatherforecast.model.City
import com.example.weatherforecast.model.WeatherInfo
import javax.inject.Inject

class WeatherForecastRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService,
) : WeatherForecastRepository {
    private fun City.toApiQuery(): String = when(this) {
        City.TOKYO -> "Tokyo"
        City.HYOGO -> "Hyogo"
        City.OITA -> "Oita"
        City.HOKKAIDO -> "Hokkaido"
    }

    override suspend fun getWeatherForecast(cityType: City): List<WeatherInfo> {
        // ユーザーが指定した都市名とlocal.propertiesに定義したAPI_KEYで通信
        val response = weatherApiService.getWeatherForecast(
            city = cityType.toApiQuery(),
            appId = BuildConfig.OPEN_WEATHER_MAP_API_KEY,
        )
        return response.weatherInfoList
    }
}
