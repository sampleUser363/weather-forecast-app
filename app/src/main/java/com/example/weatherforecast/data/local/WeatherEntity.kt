package com.example.weatherforecast.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherforecast.model.WeatherInfo
import java.time.LocalDate

@Entity("weather_info_list")
data class WeatherEntity(
    /** 都市名 */
    @PrimaryKey val city: String,
    /** 都市名に紐づく天気情報リスト */
    val weatherInfoList: List<WeatherInfo>,
    /** 最終更新日 */
    val updateAt: LocalDate
)
