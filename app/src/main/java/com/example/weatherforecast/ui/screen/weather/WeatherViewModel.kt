package com.example.weatherforecast.ui.screen.weather

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.data.repository.WeatherForecastRepository
import com.example.weatherforecast.model.City
import com.example.weatherforecast.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val weatherForecastRepository: WeatherForecastRepository
) : ViewModel() {
    // 画面遷移時に受け取るパラメータ
    val city: String = checkNotNull(savedStateHandle[Screen.WEATHER.CITY_ARG])
    // enum classのnameから種別を取得
    val cityType = City.valueOf(city)

    var uiState by mutableStateOf<WeatherUiState>(WeatherUiState.Loading)
        private set

    init {
        // 画面表示時、APIリクエスト実行
        loadWeatherInfo()
    }

    private fun loadWeatherInfo() {
        viewModelScope.launch {
            uiState = WeatherUiState.Loading
            try {
                val result = weatherForecastRepository.getWeatherForecast(cityType)
                uiState = WeatherUiState.Success(result)
            } catch (e: Exception) {
                Log.e(TAG, "loadWeatherInfo failed $e")
                uiState = WeatherUiState.Error
            }
        }
    }

    fun retryWeatherInfo() {
        loadWeatherInfo()
    }

    companion object {
        const val TAG = "WeatherViewModel"
    }
}
