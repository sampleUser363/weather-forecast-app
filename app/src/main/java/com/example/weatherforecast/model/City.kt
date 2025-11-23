package com.example.weatherforecast.model

import androidx.annotation.StringRes
import com.example.weatherforecast.R

/**
 * アプリで使用する都市を表すenum class
 *
 * ホーム画面で都市のリストとして表示される
 *
 * @property displayNameResId 表示名のStringResId
 */
enum class City(
    @StringRes
    val displayNameResId: Int,
) {
    TOKYO(
        displayNameResId = R.string.tokyo_city_name
    ),
    HYOGO(
        displayNameResId = R.string.hyogo_city_name
    ),
    OITA(
        displayNameResId = R.string.oita_city_name

    ),
    HOKKAIDO(
        displayNameResId = R.string.hokkaido_city_name
    )
}
