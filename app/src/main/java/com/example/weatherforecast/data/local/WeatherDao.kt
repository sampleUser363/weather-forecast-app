package com.example.weatherforecast.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherforecast.model.City

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weatherEntity: WeatherEntity)

    @Update
    suspend fun update(weatherEntity: WeatherEntity)

    @Delete
    suspend fun delete(weatherEntity: WeatherEntity)

    @Query("SELECT * from weather_info_list WHERE city = :city")
    suspend fun getWeatherInfoList(city: City): WeatherEntity?
}
