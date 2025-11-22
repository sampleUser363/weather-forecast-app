package com.example.weatherforecast.ui.screen.weather

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.model.City
import com.example.weatherforecast.model.WeatherInfo
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

    // FIXME: レイアウト確認用の仮ステータス。API通信機能実装時、レスポンスに応じて管理
    val isError = false
    // FIXME: APIからの取得機能実装時、正しいデータに差し替え
    val dummyData = WeatherInfo(
        iconUrl = "https://openweathermap.org/img/wn/04d@2x.png",
        iconDescription = "曇りがち",
        temperature = 16.28,
        dt = 1763698885
    )
    val dummyList = List(15) { index ->
        dummyData
    }
}
