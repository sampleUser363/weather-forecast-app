package com.example.weatherforecast.ui.navigation

/**　
 * アプリ内の各画面を定義
 *
 * @property route NavHostで使用する画面のroute
 */
sealed class Screen(val route: String) {
    object HOME : Screen("home")
    object WEATHER : Screen("weather") {
        const val CITY_ARG = "city"
        val routeWithArgs = "$route/{$CITY_ARG}"
        fun createRoute(city: String) = "weather/$city"
    }
}
