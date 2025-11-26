package com.example.weatherforecast.ui.screen.weather

sealed interface WeatherUiEvent {
    /** 権限許可ダイアログ表示イベント */
    object ShowLocationPermissionDialog : WeatherUiEvent
}
