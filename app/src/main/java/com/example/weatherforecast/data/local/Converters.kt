package com.example.weatherforecast.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.weatherforecast.model.WeatherInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/** Databaseに直接定義できない型のコンバータを定義するクラス */
@ProvidedTypeConverter
class Converters @Inject constructor(
    moshi: Moshi
) {
    private val listType = Types.newParameterizedType(
        List::class.java,
        WeatherInfo::class.java
    )
    private val weatherInfoListAdapter = moshi.adapter<List<WeatherInfo>>(listType)

    private val formatter = DateTimeFormatter.ISO_DATE

    @TypeConverter
    fun fromLocalDate(value: LocalDate): String? = value.format(formatter)

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let {
        LocalDate.parse(it, formatter)
    }

    @TypeConverter
    fun fromWeatherInfoList(weatherInfoList: List<WeatherInfo>): String {
        return weatherInfoListAdapter.toJson(weatherInfoList)
    }

    @TypeConverter
    fun toWeatherInfoList(value: String?): List<WeatherInfo> = value?.let {
        weatherInfoListAdapter.fromJson(it)
    } ?: emptyList()
}
