package com.example.weatherforecast.data.network

import retrofit2.http.GET
import retrofit2.http.Query

// RetrofitがHTTPリクエストを使用してウェブサーバーと通信する方法を定義
interface WeatherApiService {
    @GET("data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("q")
        city: String,
        @Query("appId")
        appId: String,
        @Query("units")
        units: String = "metric",
        @Query("lang")
        lang: String = "ja"
    ) : WeatherForecastResponse
}
