package com.example.weatherforecast.ui.screen.weather

import com.example.weatherforecast.model.WeatherInfo

sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val weatherInfos: List<WeatherInfo>) : WeatherUiState()
    object Error : WeatherUiState()
}
