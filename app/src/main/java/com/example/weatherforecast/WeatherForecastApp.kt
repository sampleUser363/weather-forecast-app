package com.example.weatherforecast

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecast.ui.navigation.WeatherForecastNavHost

@Composable
fun WeatherForecastApp(
    navController: NavHostController = rememberNavController()
) {
    WeatherForecastNavHost(
        navController = navController
    )
}
