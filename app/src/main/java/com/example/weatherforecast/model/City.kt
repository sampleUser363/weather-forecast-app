package com.example.weatherforecast.model

import androidx.annotation.StringRes
import com.example.weatherforecast.R

/**
 * アプリで使用する都市を表すenum class
 *
 * ホーム画面で都市のリストとして表示される
 *
 * @property displayNameResId 表示名のStringResId
 * @property isCurrentLocation 現在地を使用するかどうか
 */
enum class City(
    @StringRes
    val displayNameResId: Int,
    val isCurrentLocation: Boolean = false
) {
    /** 東京 */
    TOKYO(
        displayNameResId = R.string.tokyo_city_name
    ),
    /** 兵庫 */
    HYOGO(
        displayNameResId = R.string.hyogo_city_name
    ),
    /** 大分 */
    OITA(
        displayNameResId = R.string.oita_city_name

    ),
    /** 北海道 */
    HOKKAIDO(
        displayNameResId = R.string.hokkaido_city_name
    ),
    /** 現在地 */
    CURRENT_LOCATION(
        displayNameResId = R.string.current_location,
        isCurrentLocation = true
    )
}
