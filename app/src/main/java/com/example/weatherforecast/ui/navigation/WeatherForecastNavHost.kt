package com.example.weatherforecast.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherforecast.ui.screen.home.HomeScreen

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
            HomeScreen()
        }
        // TODO: 天気画面
    }
}
