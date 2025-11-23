package com.example.weatherforecast.ui.screen.weather

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.model.City
import com.example.weatherforecast.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    // 画面遷移時に受け取るパラメータ
    val city: String = checkNotNull(savedStateHandle[Screen.WEATHER.CITY_ARG])
    // enum classのnameから種別を取得
    val cityType = City.valueOf(city)
}
