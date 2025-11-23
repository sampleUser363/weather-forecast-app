package com.example.weatherforecast.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.weatherforecast.ui.screen.home.HomeScreen
import com.example.weatherforecast.ui.screen.weather.WeatherScreen

@Composable
fun WeatherForecastNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HOME.route,
        modifier = modifier
    ) {
        // ホーム画面
        composable(route = Screen.HOME.route) {
            HomeScreen(
                onCityClick = { city ->
                    navController.navigate(
                        Screen.WEATHER.createRoute(city)
                    )
                }
            )
        }
        // 天気画面
        composable(
            route = Screen.WEATHER.routeWithArgs,
            arguments = listOf(
                navArgument(Screen.WEATHER.CITY_ARG) {
                    type = NavType.StringType
                }
            )
        ) {
            WeatherScreen(
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}
