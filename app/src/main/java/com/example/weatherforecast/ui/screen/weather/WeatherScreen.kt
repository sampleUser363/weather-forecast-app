package com.example.weatherforecast.ui.screen.weather

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.weatherforecast.ui.component.TopBar

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(viewModel.cityType.displayNameResId),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        // TODO: 天気画面のレイアウト実装
    }
}
