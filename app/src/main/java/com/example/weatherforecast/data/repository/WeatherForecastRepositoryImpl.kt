package com.example.weatherforecast.data.repository

import com.example.weatherforecast.data.local.WeatherEntity
import com.example.weatherforecast.model.City
import com.example.weatherforecast.model.WeatherInfo
import java.time.LocalDate
import javax.inject.Inject

class WeatherForecastRepositoryImpl @Inject constructor(
    private val remote: WeatherForecastRemoteDataSource,
    private val local: WeatherForecastLocalDataSource
) : WeatherForecastRepository {
    /**
     * 都市名を用いて直近5日間の天気情報を取得
     *
     * キャッシュを確認し、同日のAPI通信履歴が保存されていれば、その値を返す
     * キャッシュが無い、またはAPI通信履歴が昨日以前の場合、再度APIと通信して取得した値を返す
     */
    override suspend fun getWeatherForecast(cityType: City): List<WeatherInfo> {
        val nowLocalDate = LocalDate.now()
        // cityTypeに応じたキャッシュがあるかどうかをチェック
        val cacheResult = local.getWeatherEntityListCache(cityType)
        if (cacheResult == null) {
            // キャッシュがなければ通常のAPIリクエストを実行し、キャッシュに保存
            val apiResult = remote.getWeatherForecast(cityType)
            local.saveCache(
                WeatherEntity(
                    city = cityType.name,
                    weatherInfoList = apiResult,
                    updateAt = nowLocalDate
                )
            )
            return apiResult
        } else {
            // cityTypeに応じたキャッシュがあれば、日付が同日かどうかを確認
            val isSameDate = cacheResult.updateAt == nowLocalDate
            if (isSameDate) {
                // 日付が同じであれば、キャッシュに保存してある結果を表示
                return cacheResult.weatherInfoList
            } else {
                // 日付が古ければ、APIリクエストを実行し、キャッシュを上書き
                val apiResult = remote.getWeatherForecast(cityType)
                local.updateCache(
                    WeatherEntity(
                        city = cityType.name,
                        weatherInfoList = apiResult,
                        updateAt = nowLocalDate
                    )
                )
                return apiResult
            }
        }
    }

    override suspend fun getWeatherForecast(
        latitude: Double,
        longitude: Double
    ): List<WeatherInfo> {
        // ユーザーが指定した緯度経度とlocal.propertiesに定義したAPI_KEYで通信
        val response = remote.getWeatherForecast(
            latitude = latitude,
            longitude = longitude
        )
        return response
    }
}
