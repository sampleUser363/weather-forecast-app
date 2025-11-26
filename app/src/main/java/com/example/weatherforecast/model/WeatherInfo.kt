package com.example.weatherforecast.model

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

data class WeatherInfo(
    /** アイコン画像を識別できる文字列（例：03n） */
    val weatherIconId: String,
    /** アイコン画像の説明 */
    val iconDescription: String,
    /** 温度（摂氏: Celsius） */
    val temperature: Double,
    /** 予測時刻(UTC) 例：1763698885 */
    val dt: Long
) {
    /** weatherIconIdを用いてWebから画像を取得するためのURL */
    val iconUrl = "https://openweathermap.org/img/wn/$weatherIconId@2x.png"
    /** 四捨五入した予想気温 */
    val roundTemperature = temperature.roundToInt()
    /** UTCを日本のタイムゾーンに合わせ、String型に変換した時刻 */
    val dateTimeString: String = Instant.ofEpochSecond(dt)
        .atZone(ZoneId.of("Asia/Tokyo"))
        .format(DateTimeFormatter.ofPattern("MM/dd HH:mm"))
}
