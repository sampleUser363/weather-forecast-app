package com.example.weatherforecast.data.network

import com.example.weatherforecast.model.WeatherInfo
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherForecastResponse(
    val list: List<WeatherData>
) {
    val weatherInfoList = list.map {
        WeatherInfo(
            weatherIconId = it.weather.firstOrNull()?.icon ?: "",
            iconDescription = it.weather.firstOrNull()?.description ?: "",
            temperature = it.main.temp,
            dt = it.dt
        )
    }
}

@JsonClass(generateAdapter = true)
data class WeatherData(
    /** Time of data forecasted, unix, UTC */
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
)

@JsonClass(generateAdapter = true)
data class Main(
    /**
     * Temperature
     *
     * Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit
     */
    val temp: Double
)

@JsonClass(generateAdapter = true)
data class Weather(
    /** Weather condition within the group */
    val description: String,
    /** Weather icon id */
    val icon: String
)
