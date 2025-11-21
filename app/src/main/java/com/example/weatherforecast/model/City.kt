package com.example.weatherforecast.model

import androidx.annotation.StringRes
import com.example.weatherforecast.R

/**
 * アプリで使用する都市を表すenum class
 *
 * ホーム画面で都市のリストとして表示される
 *
 * @property displayNameResId 表示名のStringResId
 * @property propertyResId 画面遷移時に渡すプロパティのStringResId
 */
enum class City(
    @StringRes
    val displayNameResId: Int,
    @StringRes
    val propertyResId: Int
) {
    TOKYO(
        displayNameResId = R.string.tokyo_city_name,
        propertyResId = R.string.tokyo_city_property
    ),
    HYOGO(
        displayNameResId = R.string.hyogo_city_name,
        propertyResId = R.string.hyogo_city_property
    ),
    OITA(
        displayNameResId = R.string.oita_city_name,
        propertyResId = R.string.oita_city_property

    ),
    HOKKAIDO(
        displayNameResId = R.string.hokkaido_city_name,
        propertyResId = R.string.hokkaido_city_property
    )
}
