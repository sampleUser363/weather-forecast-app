package com.example.weatherforecast.ui.screen.weather

import android.content.Context
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
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val weatherForecastRepository: WeatherForecastRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    // 画面遷移時に受け取るパラメータ
    val city: String = checkNotNull(savedStateHandle[Screen.WEATHER.CITY_ARG])
    // enum classのnameから種別を取得
    val cityType = City.valueOf(city)

    var uiState by mutableStateOf<WeatherUiState>(WeatherUiState.Loading)
        private set
    private val _eventFlow = MutableSharedFlow<WeatherUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    init {
        // 画面表示時、APIリクエスト実行
        loadWeatherInfo()
    }

    /** 選択した都市の天気情報を確認する */
    private fun loadWeatherInfoByCity() {
        viewModelScope.launch {
            uiState = WeatherUiState.Loading
            try {
                val result = weatherForecastRepository.getWeatherForecast(
                    cityType = cityType
                )
                uiState = WeatherUiState.Success(result)
            } catch (e: Exception) {
                Log.e(TAG, "loadWeatherInfo failed $e")
                uiState = WeatherUiState.Error
            }
        }
    }

    /** 現在地の緯度経度から天気情報を取得する */
    private fun loadWeatherInfoByCurrentLocation() {
        viewModelScope.launch {
            uiState = WeatherUiState.Loading
            try {
                val location = fusedLocationClient.lastLocation.await()
                val result = weatherForecastRepository.getWeatherForecast(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
                uiState = WeatherUiState.Success(result)
            } catch (e: SecurityException) {
                Log.e(TAG, "need fine location permissions. $e")
                // 権限エラー発生時、イベントを発行してエラー表示に設定
                _eventFlow.emit(WeatherUiEvent.ShowLocationPermissionDialog)
                uiState = WeatherUiState.Error
            } catch (e: Exception) {
                Log.e(TAG, "loadWeatherInfo failed. $e")
                uiState = WeatherUiState.Error
            }
        }
    }

    private fun loadWeatherInfo() {
        if (cityType.isCurrentLocation) {
            loadWeatherInfoByCurrentLocation()
        } else {
            loadWeatherInfoByCity()
        }
    }

    fun retryWeatherInfo() {
        loadWeatherInfo()
    }

    companion object {
        const val TAG = "WeatherViewModel"
    }
}
